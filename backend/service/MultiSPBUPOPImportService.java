package com.hitrsutpay.sa.backend.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hitrsutpay.sa.backend.acq.spb.dao.SpbB2bmerconfigDAO;
import com.hitrsutpay.sa.backend.acq.spbupop.dao.SpbupopB2bmerconfigDAO;
import com.hitrsutpay.sa.backend.acq.spbupop.dao.SpbupopB2bmermappingDAO;
import com.hitrsutpay.sa.backend.acq.spbupop.domain.B2bmermapping;
import com.hitrsutpay.sa.backend.constant.MerchantConstant;
import com.hitrsutpay.sa.backend.dao.TpMerConfDao;
import com.hitrsutpay.sa.backend.dao.TpMerRiskDao;
import com.hitrsutpay.sa.backend.domain.TpMerConf;
import com.hitrsutpay.sa.backend.domain.TpMerRisk;
import com.hitrsutpay.sa.backend.domain.TpMerRiskPK;
import com.hitrsutpay.sa.backend.enums.AcqBank;
import com.hitrsutpay.sa.backend.enums.Currency;
import com.hitrsutpay.sa.backend.enums.MerchantStatus;
import com.hitrsutpay.sa.backend.exception.SaApiException;
import com.hitrsutpay.sa.backend.exception.enums.MultiSPBUPOPImportErrorCode;
import com.hitrsutpay.sa.backend.res.SPBUPOPImportResponse;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 讀取CSV檔，檢核資料，批次新增SPBUPOP商店
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MultiSPBUPOPImportService {
	
	private final TpMerConfDao tpMerConfDao;
	private final TpMerRiskDao tpMerRiskDao;
	private final SpbB2bmerconfigDAO spbB2bMerConfigDAO;
	private final SpbupopB2bmerconfigDAO spbupopB2bMerConfigDAO;
	private final SpbupopB2bmermappingDAO spbupopB2bMerMappingDAO;
	private final Validator validator;
	
	/**
	 * 讀取CSV檔，檢核資料，批次新增SPBUPOP商店
	 * @param file
	 * @return
	 */
	public List<SPBUPOPImportResponse> importMultiSPBStoreUPOP(MultipartFile file) {
		log.info("MultiSPBStoreUPOPService.importMultiSPBStoreUPOP file={}", file.getOriginalFilename());
		
		// 讀取CSV，產生以acqMerid為key的匯入清單，
		// 包含index, acqMerid, acqTerminalId, mackey, 匯入結果, 失敗原因描述
		// 產生acqMerid清單
		// 產生acqTerminalid清單
		// 產生mackey清單
		var acqMeridList = new ArrayList<String>();
		var acqTerminalIdList = new ArrayList<String>();
		var mackeyList = new ArrayList<String>();
		Map<String, SPBUPOPImportResponse> importCheckingList = readCSVFile(file, acqMeridList, acqTerminalIdList, mackeyList);

		
		// 驗證acqMerid、acqTerminalId、mackey是否已存在於SPBUPOP
		var acqMerIdCheckingList = new ArrayList<String>();
		importCheckingList.values().stream().forEach(ckeckingItem -> {
			// 若acqTerminalId已存在於SPBUPOPB2BMerMapping資料表，標記為匯入失敗，並描述原因
			if (isAcqTerminalIdExistsInSPBUPOPB2BMerMapping(spbupopB2bMerMappingDAO.findByAcqterminalidsIn(acqTerminalIdList), ckeckingItem)) return;
			// 若mackey已存在於SPBUPOPB2BMerConfig資料表，標記為匯入失敗，並描述原因
			if (isMackeyExistsInSPBUPOPB2BMerConfig(spbupopB2bMerConfigDAO.findByMackeyIn(mackeyList), ckeckingItem)) return;
			// 若acqMerId已存在於SPBUPOPB2BMerMapping資料表，標記為匯入失敗，並描述原因
			if (isAcqMerIdExistsInSPBUPOPB2BMerMapping(spbupopB2bMerMappingDAO.findByAcqMerIdIn(acqMeridList), ckeckingItem)) return;
			// 加入送交TPMERCONF驗證清單
			acqMerIdCheckingList.add(ckeckingItem.getAcqMerId());
		});
		
		// 送交TPMERCONF驗證acqMerid是否有對應的SPB商代存在
		var tpMerConfReturnList = tpMerConfDao.findByAdapterIdAndAcqMerIdIn(acqMerIdCheckingList, AcqBank.SPB.name());
		// 若acqMerid無法在TpMerConf找到對應的SPB商代，標記為匯入失敗，並描述原因
		checkAcqMerIdExistsInTpMerConf(acqMerIdCheckingList, tpMerConfReturnList, importCheckingList);
		
		// 紀錄每個acqMerid對應的商代筆數
		var acqIdToMerid = new HashMap<String, List<String>>();
		var merIdToAcqId = new HashMap<String, String>();
		var frequencyMap = buildFrequencyMap(tpMerConfReturnList, acqIdToMerid, merIdToAcqId, importCheckingList);
		// 若acqMerid在TpMerConf對應到多個SPB商代，標記為匯入失敗，並描述原因
		var tpMerConfValidatedMerIdList = checkDuplicateAcqMerId(frequencyMap, acqIdToMerid, importCheckingList);
		
		// 透過 merId取得SPB B2BMerConfig資料
		var sPBMerConfigList = spbB2bMerConfigDAO.findAllById(tpMerConfValidatedMerIdList);
		// 若無法在SPB.B2BMerConfig資料表找到對應的商店代碼，標記為匯入失敗，並描述原因
		var sPBMerConfigValidatedMeridList = checkMerIdExistsInSPBB2BMerCofig(tpMerConfValidatedMerIdList,sPBMerConfigList, importCheckingList,merIdToAcqId);
		
		var invalidMerIdList = new ArrayList<String>();
		// 若merId已存在於SPBUPOPB2BMerConfig資料表，標記為匯入失敗，並描述原因
		var returnSpbupopB2bMerConfigList = spbupopB2bMerConfigDAO.findAllById(sPBMerConfigValidatedMeridList);
		checkMerIdExistsInSPBUPOPB2BMerCofig(returnSpbupopB2bMerConfigList, importCheckingList, merIdToAcqId, invalidMerIdList);
		// 若merId已存在於SPBUPOPB2BMerMapping資料表，標記為匯入失敗，並描述原因
		var returnSpbupopB2bMerMappingList = spbupopB2bMerMappingDAO.findAllById(sPBMerConfigValidatedMeridList);
		checkMerIdExistsInSPBUPOPB2BMerMapping(returnSpbupopB2bMerMappingList, importCheckingList, merIdToAcqId, invalidMerIdList);
	
		
		// 建立新增清單
		var tpMerRiskSaveList = new ArrayList<TpMerRisk>();
		var sPBUPOPMerMappingSaveList = new ArrayList<B2bmermapping>();
		var sPBUPOPMerConfigSaveList = new ArrayList<com.hitrsutpay.sa.backend.acq.spbupop.domain.B2bmerconfig>();
		sPBMerConfigList.stream().forEach(sPBMerConfig -> {
			if (!invalidMerIdList.contains(sPBMerConfig.getMerid())) {
				// 建立TPMerRisk最終新增清單
				tpMerRiskSaveList.add(buildTpMerRisk(sPBMerConfig.getMerid()));
				// 建立SPBUPOPB2BMerMapping最終新增清單
				sPBUPOPMerMappingSaveList.add(buildSPBUPOPMerMapping(sPBMerConfig.getMerid(), merIdToAcqId, importCheckingList));
				// 建立SPBUPOPB2BMerConfig最終新增清單
				sPBUPOPMerConfigSaveList.add(buildSPBUPOPMerConfig(sPBMerConfig, merIdToAcqId, importCheckingList));
			}
		});
		
		// 新增 TPMerRisk 資料表
		tpMerRiskDao.saveAllAndFlush(tpMerRiskSaveList);
		// 新增 SPBUPOP B2BMerConfig 資料表
		spbupopB2bMerConfigDAO.saveAllAndFlush(sPBUPOPMerConfigSaveList);
		// 新增 SPBUPOP B2BMerMapping 資料表
		spbupopB2bMerMappingDAO.saveAllAndFlush(sPBUPOPMerMappingSaveList);
		
		// 回傳每筆資料的匯入結果
        return importCheckingList.values().stream().collect(Collectors.toList());
	}
	
	 /**
	 * 讀取CSV檔，基本資料檢核，
	 * 產生初步匯入清單
	 * 產生acqMerid清單
	 * 產生acqTerminalid清單
	 * 產生mackey清單
	 * @param file
	 * @return
	 */
	private Map<String, SPBUPOPImportResponse> readCSVFile(
			MultipartFile file, List<String> acqMeridList, List<String> acqTerminlaIdList, List<String> mackeyList) {
		var checkingList = new HashMap<String, SPBUPOPImportResponse>();
		var acqMerIdSet= new HashSet<String>();
		var acqTerminalIdSet= new HashSet<String>();
		var mackeySet= new HashSet<String>();
		String line;
        int index = 1;
        
		try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			while ((line = br.readLine()) != null) {
            	String[] parts = line.split(","); 
            	// 檢查是否有重複的參數
        		checkDuplicateParams(acqMerIdSet, acqTerminalIdSet, mackeySet, parts[0], parts[1], parts[2]);
            	// 產生匯入清單
        		buildImportCheckingList(index, checkingList, parts);
        		// 產生acqMeridList作為資料庫檢核用
        		acqMeridList.add(parts[0]);
        		// 產生acqTerminalidList作為資料庫檢核用
        		acqTerminlaIdList.add(parts[1]);
        		// 產生mackeyList作為資料庫檢核用
        		mackeyList.add(parts[2]);
            	
            	index++;
            }
		} catch (IOException e) {
		    log.error("讀取檔案失敗 Exception={}", e.toString());
		    throw new SaApiException(MultiSPBUPOPImportErrorCode._004, e);
		}
		// 回傳匯入清單
		return checkingList;
	}
	
	/**
	 * 檢查請求參數是否重複
	 * @param acqMerIdSet
	 * @param acqTerminalIdSet
	 * @param mackeySet
	 * @param acqMerId
	 * @param acqTerminalId
	 * @param mackey
	 */
	private void checkDuplicateParams(Set<String> acqMerIdSet, Set<String> acqTerminalIdSet, Set<String> mackeySet,
			String acqMerId, String acqTerminalId, String mackey) {
		// 檢查acqMerid是否重複
		if (!acqMerIdSet.add(acqMerId)) {
			log.error("重複的acqMerid acqMerid={}", acqMerId);
			throw new SaApiException(MultiSPBUPOPImportErrorCode._001);
		}
		// 檢查acqTerminalId是否重複
		if (!acqTerminalIdSet.add(acqTerminalId)) {
			log.error("重複的acqTerminalId acqTerminalId={}", acqTerminalId);
			throw new SaApiException(MultiSPBUPOPImportErrorCode._002);
		}
		// 檢查mackey是否重複
		if (!mackeySet.add(mackey)) {
			log.error("重複的mackey mackey={}", mackey);
			throw new SaApiException(MultiSPBUPOPImportErrorCode._003);
		}
	}
	
	/**
	 * 產生匯入清單
	 * @param index
	 * @param checkingList
	 * @param parts
	 */
	private void buildImportCheckingList(int index, Map<String, SPBUPOPImportResponse> checkingList, String[] parts) {
		// 產生匯入清單
		var importCkeckingItem = SPBUPOPImportResponse.builder()
									.index(index)
									.acqMerId(parts[0])
									.acqTerminalId(parts[1])
									.mackey(parts[2])
									.build();
		// 資料檢核
    	var violations = validator.validate(importCkeckingItem);
    	if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    	// 以acqMerid作為key
    	checkingList.put(parts[0], importCkeckingItem);
	}
	
	/**
	 * 若acqMerid已存在於SPBUPOPB2BMerMapping資料表，標記為匯入失敗，並描述原因
	 * @param acqMerIdList
	 * @param importItem
	 * @return
	 */
	private boolean isAcqMerIdExistsInSPBUPOPB2BMerMapping(List<B2bmermapping> b2bmermappingList, SPBUPOPImportResponse importItem) {
		var optionalB2bmermapping = b2bmermappingList.stream()
				.filter(merchant -> merchant.getAcqmerid().equals(importItem.getAcqMerId()))
				.findFirst();
		if (optionalB2bmermapping.isPresent()) {
			importItem.setMerId(optionalB2bmermapping.get().getMerid());
			importItem.setSuccess(false);
			importItem.setMessage("[匯入失敗] 失敗原因: AcqMerid已存在於SPBUPOP.B2BMerMapping");
			log.error("AcqMerid已存在於SPBUPOP.B2BMerMapping，merid={}, acqMerId={}", optionalB2bmermapping.get().getMerid(), importItem.getAcqMerId());
			return true;
		}
		return false;
	}
	
	/**
	 * 若acqTerminalId已存在於SPBUPOPB2BMerMapping資料表，標記為匯入失敗，並描述原因
	 * @param acqTerminalIdList
	 * @param importItem
	 * @return
	 */
	private boolean isAcqTerminalIdExistsInSPBUPOPB2BMerMapping(List<B2bmermapping> b2bmermappingList, SPBUPOPImportResponse importItem) {
		var optionalB2bmermapping = b2bmermappingList.stream()
											.filter(merchant -> merchant.getAcqterminalid().equals(importItem.getAcqTerminalId()))
											.findFirst();
		if (optionalB2bmermapping.isPresent()) {
			importItem.setMerId(optionalB2bmermapping.get().getMerid());
			importItem.setSuccess(false);
			importItem.setMessage("[匯入失敗] 失敗原因: AcqTerminalId已存在於SPBUPOP.B2BMerMapping");
			log.error("AcqTerminalId已存在於SPBUPOP.B2BMerMapping，merid={}, AcqTerminalId={}", optionalB2bmermapping.get().getMerid(), importItem.getAcqTerminalId());
			return true;
		}
		return false;
	}
	
	/**
	 * 若mackey已存在於SPBUPOPB2BMerConfig資料表，標記為匯入失敗，並描述原因
	 * @param mackeyList
	 * @param importItem
	 * @return
	 */
	private boolean isMackeyExistsInSPBUPOPB2BMerConfig(List<com.hitrsutpay.sa.backend.acq.spbupop.domain.B2bmerconfig> b2bmerconfigList, SPBUPOPImportResponse importItem) {
		var optionalB2bmerconfig = b2bmerconfigList.stream()
				.filter(merchant -> merchant.getMackey().equals(importItem.getMackey()))
				.findFirst();
		if (optionalB2bmerconfig.isPresent()) {
			importItem.setMerId(optionalB2bmerconfig.get().getMerid());
			importItem.setSuccess(false);
			importItem.setMessage("[匯入失敗] 失敗原因: Mackey已存在於SPBUPOP.B2BMerConfig");
			log.error("Mackey已存在於SPBUPOP.B2BMerConfig，merid={}, Mackey={}", optionalB2bmerconfig.get().getMerid(), importItem.getMackey());
			return true;
		}
		return false;
	}
	
	/**
	 * 若acqMerid無法在TpMerConf找到對應的SPB商代，標記為匯入失敗，並描述原因
	 * @param submitAcqMeridList
	 * @param returnMerchantList
	 * @param ckeckingItem
	 */
	private void checkAcqMerIdExistsInTpMerConf(List<String> submitAcqMeridList, List<TpMerConf> returnMerchantList, Map<String, SPBUPOPImportResponse> ckeckingList) {
		submitAcqMeridList.stream().forEach(acqMerid -> {
			var optionalTpMerConf = returnMerchantList.stream()
				    .filter(merchant -> merchant.getAcqMerId().equals(acqMerid))
				    .findFirst();
			if (optionalTpMerConf.isEmpty()) {
				ckeckingList.get(acqMerid).setMerId(MerchantConstant.NA);
				ckeckingList.get(acqMerid).setSuccess(false);
				ckeckingList.get(acqMerid).setMessage("[匯入失敗] 失敗原因: AcqMerid無法在TPMERCONF找到對應的商店");
	    		log.error("AcqMerid無法在TPMERCONF找到對應的商店，acqMerid={}", acqMerid);
			}
		});
	}
	
	/**
	 * 紀錄每個acqMerid對應的商代筆數
	 * @param tpMerConfReturnList
	 * @param acqIdToMerid
	 * @param merIdToAcqId
	 * @return
	 */
	private Map<String, Integer> buildFrequencyMap(List<TpMerConf> tpMerConfReturnList, Map<String, List<String>> acqIdToMerid, Map<String, String> merIdToAcqId, Map<String, SPBUPOPImportResponse> ckeckingList) {
		var frequencyMap = new HashMap<String, Integer>();
		tpMerConfReturnList.stream().forEach(merchant -> {
			frequencyMap.compute(merchant.getAcqMerId(), (key, value) -> (value == null) ? 1 : value + 1);
			merIdToAcqId.put(merchant.getMerId(), merchant.getAcqMerId());
			acqIdToMerid.computeIfAbsent(merchant.getAcqMerId(), k -> new ArrayList<>()).add(merchant.getMerId());
			ckeckingList.get(merchant.getAcqMerId()).setMerId(merchant.getMerId());
		});
		return frequencyMap;
	}
	
	/**
	 * 檢查acqMerid是否對應多個商店
	 * @param frequencyMap
	 * @param acqIdToMerid
	 * @param ckeckingList
	 * @return
	 */
	private List<String> checkDuplicateAcqMerId(Map<String, Integer> frequencyMap, Map<String, List<String>> acqIdToMerid, Map<String, SPBUPOPImportResponse> ckeckingList) {
		var validatedMeridList = new ArrayList<String>();
		// 若acqMerid在TpMerConf對應到多個SPB商代，標記為匯入失敗，並描述原因
		frequencyMap.entrySet().stream().forEach(frequency -> {
			if (frequency.getValue() > 1) {
				List<String> meridList = acqIdToMerid.get(frequency.getKey());
				ckeckingList.get(frequency.getKey()).setMerId(String.join(", ", meridList));
				ckeckingList.get(frequency.getKey()).setSuccess(false);
				ckeckingList.get(frequency.getKey()).setMessage("[匯入失敗] 失敗原因: AcqMerid在TPMERCONF對應到多個商店");
				log.error("AcqMerid在TPMERCONF對應到多個商店，merid={}，acqMerid={}", 
						String.join(", ", meridList), frequency.getKey());
				return;
			} 
			validatedMeridList.add(acqIdToMerid.get(frequency.getKey()).get(0));
		});
		return validatedMeridList;
	}
	
	/**
	 * 若無法在SPB.B2BMerConfig資料表找到對應的商店代碼，標記為匯入失敗，並描述原因
	 * @param submitMeridList
	 * @param returnMerchantList
	 * @param ckeckingList
	 * @param merIdToAcqId
	 */
	private List<String> checkMerIdExistsInSPBB2BMerCofig(List<String> submitMeridList, List<com.hitrsutpay.sa.backend.acq.spb.domain.B2bmerconfig> returnMerchantList, Map<String, SPBUPOPImportResponse> ckeckingList, Map<String, String> merIdToAcqId) {
		var returnMeridList = new ArrayList<String>();
		submitMeridList.stream().forEach(merid -> {
			var optionalB2BMerConfig = returnMerchantList.stream()
					.filter(merchant -> merchant.getMerid().equals(merid))
					.findFirst();
			if (optionalB2BMerConfig.isEmpty()) {
				ckeckingList.get(merIdToAcqId.get(merid)).setSuccess(false);
				ckeckingList.get(merIdToAcqId.get(merid)).setMessage("[匯入失敗] 失敗原因: SPB.B2BMERCONFIG不存在該商店代碼");
				log.error("SPB.B2BMERCONFIG不存在該商店資料，merid={}，acqMerid={}", 
						merid, merIdToAcqId.get(merid));
				return;
			}
			returnMeridList.add(merid);
		});
		return returnMeridList;
	}
	
	/**
	 * 若merid已存在於SPBUPOPB2BMerConfig資料表，標記為匯入失敗，並描述原因
	 * @param returnMerchantList
	 * @param ckeckingList
	 * @param merIdToAcqId
	 * @param invalidMerIdList
	 */
	private void checkMerIdExistsInSPBUPOPB2BMerCofig(List<com.hitrsutpay.sa.backend.acq.spbupop.domain.B2bmerconfig> returnMerchantList, Map<String, SPBUPOPImportResponse> ckeckingList, Map<String, String> merIdToAcqId, List<String> invalidMerIdList) {
		returnMerchantList.stream().forEach(merchant -> {
			ckeckingList.get(merIdToAcqId.get(merchant.getMerid())).setSuccess(false);
			ckeckingList.get(merIdToAcqId.get(merchant.getMerid())).setMessage("[匯入失敗] 失敗原因: SPBUPOP.B2BMERCONFIG已存在該商店資料");
			invalidMerIdList.add(merchant.getMerid());
			log.error("SPBUPOP.B2BMERCONFIG已存在該商店資料，merid={}，acqMerid={}", 
					merchant.getMerid(), merIdToAcqId.get(merchant.getMerid()));
		});
	}
	
	/**
	 * 若merid已存在於SPBUPOPB2BMerMapping資料表，標記為匯入失敗，並描述原因
	 * @param returnMerchantList
	 * @param ckeckingList
	 * @param merIdToAcqId
	 * @param invalidMerIdList
	 */
	private void checkMerIdExistsInSPBUPOPB2BMerMapping(List<B2bmermapping> returnMerchantList, Map<String, SPBUPOPImportResponse> ckeckingList, Map<String, String> merIdToAcqId, List<String> invalidMerIdList) {
		returnMerchantList.stream().forEach(spbupopB2bMerMapping -> {
			ckeckingList.get(merIdToAcqId.get(spbupopB2bMerMapping.getMerid())).setSuccess(false);
			ckeckingList.get(merIdToAcqId.get(spbupopB2bMerMapping.getMerid())).setMessage("[匯入失敗] 失敗原因: SPBUPOP.B2BMERMAPPING已存在該商店資料");
			invalidMerIdList.add(spbupopB2bMerMapping.getMerid());
			log.error("SPBUPOP.B2BMERMAPPING已存在該商店資料，merid={}，acqMerid={}", 
					spbupopB2bMerMapping.getMerid(), merIdToAcqId.get(spbupopB2bMerMapping.getMerid()));
		});
	}

	/**
	 * 透過商代 buildTpMerRisk的SPBUPOP
	 * @param merid
	 * @return
	 */
	private TpMerRisk buildTpMerRisk(String merid) {
		return TpMerRisk.builder()
				.id(TpMerRiskPK.builder()
					.merId(merid)
					.riskId(MerchantConstant.SPBUPOP)
					.build())
				.enable("1")
				.build();
	}
	
	/**
	 * 透過商代、檔案中的mackey buildSPBUPOPMerConfig
	 * @param sPBmerConfig
	 * @param meridToAcqMerid
	 * @param checkingList
	 * @return
	 */
	private com.hitrsutpay.sa.backend.acq.spbupop.domain.B2bmerconfig buildSPBUPOPMerConfig(com.hitrsutpay.sa.backend.acq.spb.domain.B2bmerconfig sPBmerConfig, Map<String, String> meridToAcqMerid, Map<String, SPBUPOPImportResponse> checkingList) {
		return com.hitrsutpay.sa.backend.acq.spbupop.domain.B2bmerconfig.builder()
					.merid(sPBmerConfig.getMerid())
					.mername(sPBmerConfig.getMername())
					.mernum(sPBmerConfig.getMerid())
					.pubkey(MerchantConstant.PUBLIC_KEY)
					.mertype(MerchantConstant.MER_TYPE_SSL)
					.currency(Currency.TWD.getValue())
					.settlehour(sPBmerConfig.getSettlehour())
					.cutoffmonth(MerchantConstant.DEFULT_CUT_OFF_MONTH)
					.cutoffday(MerchantConstant.DEFULT_BIGDECIMAL)
					.autosettle(MerchantConstant.DEFULT_AUTO_SETTLE)
					.settlenums(MerchantConstant.DEFULT_BIGDECIMAL)
					.autoterm(MerchantConstant.DEFULT_AUTO_TERM)
					.status(MerchantStatus.ACTIVE.getValue())
					.mackey(checkingList.get(meridToAcqMerid.get(sPBmerConfig.getMerid())).getMackey())
					.build();
	}
	
	/**
	 * 透過商代、檔案中的acqMerid、acqTerminalId buildSPBUPOPMerMapping
	 * @param merid
	 * @param meridMap
	 * @param checkingList
	 * @return
	 */
	private B2bmermapping buildSPBUPOPMerMapping(String merid, Map<String, String> meridMap, Map<String, SPBUPOPImportResponse> checkingList) {
		return B2bmermapping.builder()
				.merid(merid)
				.acquirerid(MerchantConstant.DEFAULT_ACQUIRERID)
				.acqmerid(meridMap.get(merid))
				.mcccode(MerchantConstant.DEFAULT_MCC_CODE)
				.acqterminalid(checkingList.get(meridMap.get(merid)).getAcqTerminalId())
				.build();
	}
	
	
	
}
