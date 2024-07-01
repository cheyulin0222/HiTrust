package com.hitrsutpay.sa.backend.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hitrsutpay.sa.backend.domain.MerChantConf;
import com.hitrsutpay.sa.backend.domain.Payload;
import com.hitrsutpay.sa.backend.exception.SaApiException;
import com.hitrsutpay.sa.backend.exception.enums.IOSConfigErrorCode;
import com.hitrsutpay.sa.backend.req.SDKConfigFileEncryptRequest;
import com.hitrsutpay.sa.backend.req.SDKConfigFileDownloadRequest;
import com.hitrsutpay.sa.backend.res.SDKConfigFileReadResponse;
import com.hitrsutpay.sa.backend.res.SDKConfigFileEncryptResponse;
import com.hitrsutpay.sa.backend.utils.AES128;
import com.hitrsutpay.sa.backend.utils.FileUtil;
import com.hitrsutpay.sa.backend.utils.JsonUtil;
import com.hitrsutpay.sa.backend.vo.MerChantConfVO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SDKConfigFileService
 * 讀取檔案回傳檔案內容
 * 修改、新增IOS Config資料加密並回傳
 * 下載加密後檔案
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SDKConfigFileService {

	@Value("${ios.config.version}")
	private String version;
	@Value("${ios.config.aeskey}")
	private String aesKey;
	private final Validator validator;
	
	/**
	 * 讀取json檔得到Payload物件
	 * 將Payload物件中的payload屬性值進行解密得到List<MerChantConf>
	 * 回傳讀取結果
	 * @param file
	 * @param sdkVersion
	 * @return
	 */
	public SDKConfigFileReadResponse readSDKConfigFile(MultipartFile file) {
		log.info("SDKConfigFileService.readSDKConfigFile file={}", file.getOriginalFilename());
		
		// 讀取檔案內容轉成Payload物件(version, buildTime, 加密後的payload）
		Payload payload = convertBytesToPayload(FileUtil.readFile(file));
		// 若Payload物件不存在,拋出錯誤
		if (payload == null) {
			log.error("Payload不存在");
			throw new SaApiException(IOSConfigErrorCode._007);
		}
		
		// 將加密後的 payload 進行解密得到 jsonString
		String jsonString = aes128Decode(payload.getPayload());
		log.info("jsonString={}", jsonString);
		// JsonString -> List<MerChantConf>
		List<MerChantConf> confList = covertJsonStringToList(jsonString);
		// 回傳讀取結果
		return buildSDKConfigFileReadResponse(confList);
	}
	
	/**
	 * 將List<MerChantConf>轉成加密String並回傳
	 * @param request
	 * @return
	 */
	public SDKConfigFileEncryptResponse encryptSDKConfigFile(SDKConfigFileEncryptRequest request) {
		log.info("SDKConfigFileService.convertSDKConfigFile parseAjson={}", request.getParseAjson());
		
		Map<String, Integer> frequency = new HashMap<>();
		List<String> duplicateMerIds = new ArrayList<>();
		List<MerChantConf> encryptList = new ArrayList<>();
		
		request.getParseAjson().forEach(merConf -> {
			// 計算merId出現次數
		    frequency.merge(merConf.getMerId(), 1, Integer::sum);
		    // merId出現次出大於1，加入重複的merId列表
		    if (frequency.get(merConf.getMerId()) > 1) {
		        duplicateMerIds.add(merConf.getMerId());
		    }
		    encryptList.add(buildMerChantConf(merConf));
		});
		
		if (duplicateMerIds.size() > 0) {
			log.error("重複的merid={}", duplicateMerIds);
			throw new SaApiException(IOSConfigErrorCode._010);
		}
		
		// 將List<MerChantConf>轉成jsonString
		// 將jsonString加密
		String payload = aes128Encode(convertListToJsonString(encryptList));
		// 回傳加密資料
		return buildSDKConfigFileEncryptResponse(payload);
	}
	
	/**
	 * 下載IOS Config檔案
	 * @param request
	 * @return
	 */
	public byte[] convertToBytes(SDKConfigFileDownloadRequest request) {
		log.info("SDKConfigFileService.convertToBytes request= {}", request);
		
		// SDKConfigFileDownloadRequest -> Payload
		Payload payload = buildPayload(request);
		// 回傳 Payload -> byte[]
		return convertPayloadToBytes(payload);	
	}
	
	/**
	 * 建立不合法檔案測試用
	 * @param testList
	 * @return
	 */
	public byte[] createTestFile(List<MerChantConf> testList) {
		String payload = aes128Encode(convertListToJsonString(testList));
		
		var timestamp = new Timestamp(System.currentTimeMillis());

        // 建立時間
        var timeZone = TimeZone.getTimeZone("GMT+8");
        var sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setTimeZone(timeZone);

        String formattedDate = sdf.format(timestamp);
		
        return convertPayloadToBytes(Payload.builder()
					        		.version(version)
					        		.buildTime(formattedDate)
					        		.payload(payload)
					        		.build());	
	}

	/**
	 * Bytes轉Payload物件
	 * @param bytes
	 * @return
	 */
	private Payload convertBytesToPayload(byte[] bytes) {
		// byte[] -> Payload
		Payload payload = null;
		try {
			payload = JsonUtil.readValue(bytes, Payload.class);
		} catch (JsonParseException e) {
			log.error("解析json失敗,Exception={}", e.toString());
			throw new SaApiException(IOSConfigErrorCode._004, e);
		} catch (JsonMappingException e) {
			log.error("json轉java物件失敗,Exception={}", e.toString());
			throw new SaApiException(IOSConfigErrorCode._006, e);
		} catch (IOException e) {
			log.error("檔案讀取失敗,Exception={}", e.toString());
			throw new SaApiException(IOSConfigErrorCode._005, e);
		}
		return payload;
	}
	
	/**
	 * JsonString -> List<MerChantConf>
	 * @param jsonString
	 * @return
	 */
	private List<MerChantConf> covertJsonStringToList(String jsonString) {
		// JsonString -> List<MerChantConf>
		List<MerChantConf> confList = null;
		try {
			confList = JsonUtil.readValue(jsonString, new TypeReference<ArrayList<MerChantConf>>() {});
		} catch (JsonParseException e) {
			log.error("解析json失敗,Exception={}", e.toString());
			throw new SaApiException(IOSConfigErrorCode._004, e);
		} catch (JsonMappingException e) {
			log.error("json轉java物件失敗,Exception={}", e.toString());
			throw new SaApiException(IOSConfigErrorCode._006, e);
		} catch (IOException e) {
			log.error("檔案讀取失敗,Exception={}", e.toString());
			throw new SaApiException(IOSConfigErrorCode._005, e);
		}
		return confList;
	}
	
	/**
	 * List<MerChantConf> -> JstonString
	 * @param merChantConfList
	 * @return
	 */
	private String convertListToJsonString(List<MerChantConf> merChantConfList) {
		// JsonString -> List<MerChantConf>
		String str = null;
		try {
			str = JsonUtil.writeValueAsString(merChantConfList);
		} catch (JsonParseException e) {
			log.error("解析json失敗,Exception={}", e.toString());
			throw new SaApiException(IOSConfigErrorCode._004, e);
		} catch (JsonMappingException e) {
			log.error("json轉java物件失敗,Exception={}", e.toString());
			throw new SaApiException(IOSConfigErrorCode._006, e);
		} catch (IOException e) {
			log.error("檔案讀取失敗,Exception={}", e.toString());
			throw new SaApiException(IOSConfigErrorCode._005, e);
		}
		return  str;
	}
	
	/**
	 * Payload -> byte[]
	 * @param payload
	 * @return
	 */
	private byte[] convertPayloadToBytes(Payload payload) {
		// Payload -> byte[]
		byte[] bytes = null;
		try {
			bytes = JsonUtil.writeValueAsBytes(payload);
		} catch (IOException e) {
			log.error("檔案讀取失敗,Exception={}", e.toString());
			throw new SaApiException(IOSConfigErrorCode._005, e);
		}
		return bytes;
	}
	
	/**
	 * buildSDKConfigFileReadResponse
	 * @param merConfList
	 * @param sdkVersion
	 * @return
	 */
	private SDKConfigFileReadResponse buildSDKConfigFileReadResponse(List<MerChantConf> merConfList) {
		// List<MerChantConf> -> List<SDKConfigFileReadResponse>
		return SDKConfigFileReadResponse.builder()
				.parseAjson(merConfList.stream().map(conf -> {
					MerChantConfVO build = MerChantConfVO.builder()
						.merId(conf.getMerId())
						.verifyPaResURL(conf.getVerifyPaResURL())
						.verifyReqURL(conf.getVerifyReqURL())
						.otherReqURL(conf.getOtherReqURL())
						.cvc2(conf.isCvc2())
						.installment(conf.getInstallment())
						.ae(conf.isAE())
						.trxToken(conf.isTrxToken())
						.currency(conf.getCurrency())
						.acqBank(conf.getAcquirBank())
						.build();
			        Set<ConstraintViolation<MerChantConfVO>> violations = validator.validate(build);
			        
			        if (!violations.isEmpty()) {
			            throw new ConstraintViolationException(violations);
			        }
					return build;
				})
				.collect(Collectors.toList()))
				.build();
	}
	
	/**
	 * merChantConfVO -> MerChantConf
	 * @param merChantConfVO
	 * @return
	 */
	private MerChantConf buildMerChantConf(MerChantConfVO merChantConfVO) {
		// merChantConfVO -> MerChantConf
		return MerChantConf.builder()
				.merId(merChantConfVO.getMerId())
	            .verifyReqURL(merChantConfVO.getVerifyReqURL())
	            .verifyPaResURL(merChantConfVO.getVerifyPaResURL())
	            .otherReqURL(merChantConfVO.getOtherReqURL())
	            .cvc2(merChantConfVO.isCvc2())
	            .installment(merChantConfVO.getInstallment())
	            .isAE(merChantConfVO.isAe())
	            .trxToken(merChantConfVO.isTrxToken())
	            .currency(merChantConfVO.getCurrency())
	            .acquirBank(merChantConfVO.getAcqBank())
				.build();
	}
	
	/**
	 * buildSDKConfigFileCovertResponse
	 * @param payload
	 * @return
	 */
	private SDKConfigFileEncryptResponse buildSDKConfigFileEncryptResponse(String payload) {
		return SDKConfigFileEncryptResponse.builder()
				.version(version)
				.buildTime(new Timestamp(System.currentTimeMillis()))
				.payload(payload)
				.build();
	}
	
	/**
	 * SDKConfigFileDownloadRequest -> Payload
	 * @param request
	 * @return
	 */
	private Payload buildPayload(SDKConfigFileDownloadRequest request) {
		// SDKConfigFileDownloadRequest -> Payload
		return Payload.builder()
				.version(version)
				.buildTime(request.getBuildTime())
				.payload(request.getPayload())
				.build();
	}
	
	/**
	 * Aes128解密
	 * @param src
	 * @return
	 */
	private String aes128Decode(String src) {
		String str = null;
		try {
			str = AES128.Aes128Decode(src, hex2Byte(aesKey));
		} catch (Exception e) {
			log.error("Aes1280解密失敗,Exception={}", e.toString());
			throw new SaApiException(IOSConfigErrorCode._008, e);
		}
		return str;
	}
	
	/**
	 * Aes128加密
	 * @param src
	 * @return
	 */
	private String aes128Encode(String src) {
		String str = null;
		try {
			str = AES128.Aes128Encode(src, hex2Byte(aesKey));
		} catch (Exception e) {
			log.error("Aes1280解密失敗,Exception={}", e.toString());
			throw new SaApiException(IOSConfigErrorCode._009, e);
		}
		return str;
	}

	/**
	 * 建立keyBytes
	 * @param hexString
	 * @return
	 */
	private byte[] hex2Byte(String hexString) {
		byte[] bytes = new byte[hexString.length() / 2];
		for (int i = 0; i < bytes.length; i++)
			bytes[i] = (byte) Integer.parseInt(hexString.substring(2 * i, 2 * i + 2), 16);
		return bytes;
	}
}
