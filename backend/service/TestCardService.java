package com.hitrsutpay.sa.backend.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitrsutpay.sa.backend.acq.hitrust.dao.TestCardDao;
import com.hitrsutpay.sa.backend.acq.hitrust.domain.TestCard;
import com.hitrsutpay.sa.backend.enums.ResultMessage;
import com.hitrsutpay.sa.backend.enums.TestCardStatus;
import com.hitrsutpay.sa.backend.exception.SaApiException;
import com.hitrsutpay.sa.backend.exception.enums.TestCardErrorCode;
import com.hitrsutpay.sa.backend.req.TestCardCreateRequest;
import com.hitrsutpay.sa.backend.req.TestCardRequestBase;
import com.hitrsutpay.sa.backend.req.TestCardUpdateRequest;
import com.hitrsutpay.sa.backend.res.MessageResponse;
import com.hitrsutpay.sa.backend.res.TestCardQueryResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 測試卡號(B2BCREDITLOG資料表)查詢，修改，新增
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TestCardService {

	private final TestCardDao mTestCardDao;

	/**
	 * 用信用卡號取得測試卡號資料
	 * @param testCardQueryRequest
	 * @param userid 
	 * @return
	 */
	public TestCardQueryResponse getTestCardbyCreditCardNum(String cardNumber) {
		log.info("TestCardService.getTestCardbyCreditCardNum cardNumber={}",cardNumber);
		
		// 用測試卡號去B2BCREDITLOG表格找資料
		var optionalTestCard = mTestCardDao.findById(cardNumber);
		// 回傳查詢結果
		return respondTestCardQuery(optionalTestCard, cardNumber);
	}

	/**
	 * 新增測試卡號
	 * @param testCardCreateRequest
	 * @param userid
	 * @param userid
	 * @return
	 */
	public MessageResponse createTestCardData(TestCardCreateRequest request, String userid) {
		log.info("TestCardService.createTestCardData request={}, userid={}", 
				request.getTestCardInfo(), userid);
		
		// 效期是否大於現在日期
		validateExpiry(request.getTestCardInfo().getExpiry());
		
		// 判斷測試卡號是否存在於 B2BCREDITLOG 
		// 若存在則拋出錯誤
		if (mTestCardDao.existsById(request.getTestCardInfo().getCreditCardNum())) {
			throw new SaApiException(TestCardErrorCode._003);
		}
		// 新增並回傳結果
		return saveAndResponse(request.getTestCardInfo(), userid, ResultMessage.INSERT_SUCCESSFUL);
	}

	/**
	 * 更新測試卡號
	 * @param testCardUpdateRequest
	 * @param userid 
	 * @return
	 */
	public MessageResponse updateTestCardData(TestCardUpdateRequest request, String userid) {
		log.info("TestCardService.updateTestCardData request={}, userid={}", 
				request.getTestCardInfo(), userid);
		
		// 效期是否大於現在日期
		validateExpiry(request.getTestCardInfo().getExpiry());
		
		// 判斷測試卡號是否存在於 B2BCREDITLOG 
		// 若不存在則拋出錯誤
		if (!mTestCardDao.existsById(request.getTestCardInfo().getCreditCardNum())) {
			throw new SaApiException(TestCardErrorCode._002);
		}
		// 修改並回傳結果
		return saveAndResponse(request.getTestCardInfo(), userid, ResultMessage.UPDATE_SUCCESSFUL);
	}
	
	/**
	 * 判斷查詢結果並回應
	 * @param optional
	 * @param cardNumber
	 * @return
	 */
	private TestCardQueryResponse respondTestCardQuery(Optional<TestCard> optional, String cardNumber) {
		// 測試卡號不存在，就印出log並拋出錯誤訊息
		if (optional.isEmpty()) {
			log.error("測試卡號={}不存在。", cardNumber);
			throw new SaApiException(TestCardErrorCode._001);
		}
		// 存在則回傳結果
		return buildTestCardQueryResponse(optional.get());
	}
	
	/**
	 * TestCard -> TestCardQueryResponse
	 * @param testCard
	 * @return
	 */
	private TestCardQueryResponse buildTestCardQueryResponse(TestCard testCard) {
		// TestCard -> TestCardQueryResponse
		return TestCardQueryResponse.builder()
				.creditCardNum(testCard.getCreditCard())
				.creditLimit(testCard.getCreditLimit())
				.debitLimit(testCard.getDebitLimit())
				.expiry(testCard.getExpiry())
				.redemPoint(testCard.getRedemPoint())
				.cvc2(testCard.getCvc2())
				.status(TestCardStatus.get(testCard.getStatus()))
				.updateUser(testCard.getUpdateUser())
				.build();
	}
	
	/**
	 * 新增或修改，並回傳結果
	 * @param testCardInfo
	 * @param userid
	 * @param message
	 * @return
	 */
	private MessageResponse saveAndResponse(TestCardRequestBase testCardInfo, String userid, ResultMessage message) {
		// 新增或修改
		mTestCardDao.save(buildTestCard(testCardInfo, userid));
		
		// 回傳結果
		return MessageResponse.builder()
				.success(true)
				.message(message.getMessage())
				.build();
	}
	
	/**
	 * TestCardRequestBase -> TestCard
	 * @param request
	 * @param userid
	 * @return
	 */
	private TestCard buildTestCard(TestCardRequestBase request, String userid) {
		// TestCardRequestBase -> TestCard
		return TestCard.builder()
				.creditCard(request.getCreditCardNum())
				.creditLimit(request.getCreditLimit())
				.debitLimit(request.getDebitLimit())
				.redemPoint(request.getRedemPoint())
				.expiry(request.getExpiry())
				.cvc2(request.getCvc2())
				.updateUser(userid)
				.status(request.getStatus().getValue())
				.build();
	}
	
	/**
	 * 驗證效期，必須大於當下日期
	 * @param expiry
	 */
	private void validateExpiry(String expiry) {
		LocalDate today = LocalDate.now();
		int year = today.getYear() % 100;
		int month = today.getMonthValue();
		int expiryYear =  Integer.parseInt(expiry.substring(0, 2));
		int expiryMonth =  Integer.parseInt(expiry.substring(2, 4));
		
		log.info("year={}, month={}, expiryYear={}, expiryMonth={}", 
				year, month, expiryYear, expiryMonth);
			
		if (expiryYear < year || (expiryYear == year && expiryMonth <= month)) {
			throw new SaApiException(TestCardErrorCode._004);
		}
	}
}
