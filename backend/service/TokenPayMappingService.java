package com.hitrsutpay.sa.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hitrsutpay.sa.backend.dao.TokenPayMappingDao;
import com.hitrsutpay.sa.backend.dao.TpMerConfDao;
import com.hitrsutpay.sa.backend.domain.TokenPayMapping;
import com.hitrsutpay.sa.backend.domain.TokenPayMappingId;
import com.hitrsutpay.sa.backend.enums.ResultMessage;
import com.hitrsutpay.sa.backend.enums.TokenPayStatus;
import com.hitrsutpay.sa.backend.exception.SaApiException;
import com.hitrsutpay.sa.backend.exception.enums.MerchantErrorCode;
import com.hitrsutpay.sa.backend.exception.enums.TokenPayMappingErrorCode;
import com.hitrsutpay.sa.backend.req.TokenPayMappingRequest;
import com.hitrsutpay.sa.backend.req.TokenPayMappingSaveRequest;
import com.hitrsutpay.sa.backend.res.MessageResponse;
import com.hitrsutpay.sa.backend.res.TokenPayMappingResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 速速付Token授權
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TokenPayMappingService {
	
	private final TpMerConfDao merconfDao;
	private final TokenPayMappingDao tokenPayMappingDao;
	
	/**
	 * 透過商代查詢速速付授權資料
	 * @param regMerId
	 * @return
	 */
	public List<TokenPayMappingResponse> getTokenPayMapping(String regMerId) {
		log.info("getTokenPayMapping regMerid={}", regMerId);
		
		// 若商代不存在，拋出商代不存在錯誤
		if (!merconfDao.existsById(regMerId)) {
			throw new SaApiException(MerchantErrorCode._001);
		}	
		
		// 回傳該商代所有授權資料
		var tokenMappingList = tokenPayMappingDao.findAllByRegMerId(regMerId);
		return tokenMappingList.stream()
				.map(mapping -> buildTokenPayMappingResponse(
					mapping.getId().getAuthMerId(), 
					TokenPayStatus.getTokenPayStatusByValue(mapping.getStatus())))
				.collect(Collectors.toList());
	}

	/**
	 * 新增或修改商代速速付授權資料
	 * @param request
	 * @param userid
	 * @return
	 */
	public MessageResponse saveTokenPayMapping(TokenPayMappingSaveRequest request, String userid) {
		var regMerId = request.getRegMerId();
		var checkList = new ArrayList<String>();
		
		checkList.add(regMerId);
		
		// 創建商代檢核列表及儲存列表
		var saveList = request.getSaveList().stream()
				.peek(saveRequest -> {
					// 若被授權商代和授權商代一樣，拋出錯誤
					if (saveRequest.getAuthMerId().equals(regMerId)) {
						throw new SaApiException(TokenPayMappingErrorCode._001);
					}// 將商代加入檢查列
					checkList.add(saveRequest.getAuthMerId());
				})// 建立Entity
				.map(saveRequest -> buildTokenPayMapping(saveRequest, regMerId, userid))
				.peek(saveItem -> log.info("saveItem: regMerid={}, authMerid={}, status={}, updateUser={}",
		                regMerId, saveItem.getId().getAuthMerId(), saveItem.getStatus(), userid))
				.collect(Collectors.toList());


		// 若任一商代不存在，拋出錯誤(判斷所有商代存不存在)
		List<String> returnList = merconfDao.findMerIdByMerIdIn(checkList);
		if (checkList.size() != returnList.size()) {
			throw new SaApiException(MerchantErrorCode._001);
		}	
		
		// 儲存所有資料
		tokenPayMappingDao.saveAll(saveList);
		
		// 回傳儲存結果
		return MessageResponse.builder()
				.message(ResultMessage.SAVE_SUCCESSFUL.getMessage())
				.build();
	}
	
	/**
	 * 透多regMerid、authMerid刪除速速付授權資料(測試用)
	 * @param regMerid
	 * @param authMerid
	 */
	public void deleteTokenPayMappingById(String regMerid, String authMerid) {
		log.info("deleteTokenPayMapping regMerid={}, authMerid={}", regMerid, authMerid);
		
		tokenPayMappingDao.deleteById(buildTokenPayMappingId(regMerid, authMerid));
	}
	
	
	// Request -> Entity
	private TokenPayMapping buildTokenPayMapping(TokenPayMappingRequest request, String regMerid, String userid) {
		return TokenPayMapping.builder()
			.id(buildTokenPayMappingId(regMerid, request.getAuthMerId()))
			.status(request.getAuthStatus().getValue())
            .updateUser(userid)
			.build();
	}
	
	// build TokenPayMappingId
	private TokenPayMappingId buildTokenPayMappingId(String regMerid, String authMerid) {
		return TokenPayMappingId.builder()
			.regMerId(regMerid)
			.authMerId(authMerid)
			.build();
	}
	
	// build TokenPayMappingResponse
	private TokenPayMappingResponse buildTokenPayMappingResponse(String authMerid, TokenPayStatus status) {
		return TokenPayMappingResponse.builder()
			.authMerId(authMerid)
			.authStatus(status)
			.build();
	}
}
