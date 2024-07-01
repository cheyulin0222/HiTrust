package com.hitrsutpay.sa.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hitrsutpay.sa.backend.dao.StatusCodeMappingDao;
import com.hitrsutpay.sa.backend.domain.StatusCodeMapping;
import com.hitrsutpay.sa.backend.enums.ResultMessage;
import com.hitrsutpay.sa.backend.exception.SaApiException;
import com.hitrsutpay.sa.backend.exception.enums.StatusCodeMapingErrorCode;
import com.hitrsutpay.sa.backend.req.StatusCodeMappingUpdateRequest;
import com.hitrsutpay.sa.backend.res.MessageResponse;
import com.hitrsutpay.sa.backend.res.StatusCodeMappingResponse;
import com.hitrsutpay.sa.backend.utils.StringTool;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 狀態碼對應系統
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StatusCodeMappingService {

	private final StatusCodeMappingDao mappingDao;

	/**
	 * 查詢全部狀態碼對應
	 * @return
	 */
	public List<StatusCodeMappingResponse>getStatusCodeMapping() {
		
		// 取得狀態碼資料
		List<StatusCodeMapping> mappingList = mappingDao.findAll();
		
		// 回傳List<StatusCodeMapping> -> List<StatusCodeMappingResponse> 
		// 將狀態碼描述進行轉碼
		return mappingList.stream()
				.map(mapping -> StatusCodeMappingResponse.builder()
					.statusId(mapping.getStatusId())
					.statusCode(mapping.getStatusCode())
					.statusDesc(StringTool.converChineseFromDB(mapping.getStatusDesc()))
					.build())
				.collect(Collectors.toList());
	}

	/**
	 * 新增狀態碼對應
	 * @param code
	 * @param desc
	 * @return
	 */
	public MessageResponse createStatusCodeMapping(String code, String desc) {
		log.info("createStatusCodeMapping name={} desc={}", code, desc);
		
		// 若狀態碼已存在於資料庫，拋出重複的狀態碼錯誤
		if (mappingDao.existsByStatusCode(code)) {
			throw new SaApiException(StatusCodeMapingErrorCode._001);
		}
		
		// 將狀態碼描述轉碼
		// 新增資料
		mappingDao.save(StatusCodeMapping.builder()
				.statusCode(code)
				.statusDesc(StringTool.escapeChineseToDB(desc))
				.build());
		
		// 回傳新增結果
		return MessageResponse.builder()
				.message(ResultMessage.INSERT_SUCCESSFUL.getMessage())
				.build();
	}

	/**
	 * 修改狀態碼對應
	 * @param request
	 * @return
	 */
	public MessageResponse updateStatusCodeMapping(StatusCodeMappingUpdateRequest request) {
		Integer id = request.getStatusId();
		String code = request.getStatusCode();
		String desc = request.getStatusDesc();
		
		log.info("updateStatusCodeMapping id={} code={} desc={}",id, code, desc);

		var optionalMapping = mappingDao.findById(id);
		
		// 若id不存在，拋出id不存在錯誤
		if (!optionalMapping.isPresent()) {
			throw new SaApiException(StatusCodeMapingErrorCode._002);
		}
		
		StatusCodeMapping mapping = optionalMapping.get();
		// 若資料庫已經有它筆一樣的狀態碼資料，拋出重複的狀態碼錯誤
		if (!code.equals(mapping.getStatusCode()) && mappingDao.existsByStatusCode(code)) {
			throw new SaApiException(StatusCodeMapingErrorCode._004);
		}
		
		// 將狀態碼描述轉碼
		// 修改資料
		mapping.setStatusCode(code);
		mapping.setStatusDesc(StringTool.escapeChineseToDB(desc));
		mappingDao.save(mapping);
	
		// 回傳修改結果
		return MessageResponse.builder()
				.message(ResultMessage.UPDATE_SUCCESSFUL.getMessage())
				.build();	
	}

	/**
	 * 刪除狀態碼對應
	 * @param id
	 * @return
	 */
	public MessageResponse deleteStatusCodeMapping(Integer id) {
		log.info("deleteStatusCodeMapping id={}", id);
		
		// 若id不存在，拋出id不存在錯誤
		if (!mappingDao.existsById(id)) {
			throw new SaApiException(StatusCodeMapingErrorCode._003);
		}
		// 刪除該筆資料
		mappingDao.deleteById(id);
		// 回傳刪除結果
		return MessageResponse.builder()
				.message(ResultMessage.DELETE_SUCCESSFUL.getMessage())
				.build();	
	}
	
	/**
	 * 透過狀態碼查詢狀態碼對應ID(為了測試刪除資料用)
	 * @param code
	 * @return
	 */
	public Integer getStatusCodeMappingIdByCode(String code) {
		log.info("getStatusCodeMappingByName statusCode={}", code);

		// 若參數為null，拋出錯誤
		if (code == null) {
			throw new SaApiException(StatusCodeMapingErrorCode._005);
		}
		// 使用狀態碼查詢該筆資料
		var mapping = mappingDao.findByStatusCode(code);
		// 若資料不存在回傳null
		if(mapping == null) {
			return null;
		}
		// 回傳id
		return mapping.getStatusId();
	}
	

}
