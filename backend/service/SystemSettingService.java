package com.hitrsutpay.sa.backend.service;



import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.hitrsutpay.sa.backend.dao.SystemSettingDao;
import com.hitrsutpay.sa.backend.domain.SystemSetting;
import com.hitrsutpay.sa.backend.enums.Avalibility;
import com.hitrsutpay.sa.backend.enums.ResultMessage;
import com.hitrsutpay.sa.backend.exception.SaApiException;
import com.hitrsutpay.sa.backend.exception.enums.SystemSettingErrorCode;
import com.hitrsutpay.sa.backend.req.SystemSettingUpdateRequest;
import com.hitrsutpay.sa.backend.res.MessageResponse;
import com.hitrsutpay.sa.backend.res.SystemSettingResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TP_CONFIG表格中，對TPID為TRUSTPAY這筆資料的查詢及修改
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SystemSettingService {

	private static final String SYSTEM_SETTING_TPID = "TRUSTPAY";
	private final SystemSettingDao systemSettingDao;

	/**
	 * 取得系統設定資料
	 * @return 
	 */
	public SystemSettingResponse getSystemSetting() {
		// 若TPID存在，則回傳資料
		return getSystemSettingIfExists(systemSetting -> buildSystemSettingResponse(systemSetting));
	}


	/**
	 * 修改系統設定資料
	 * @param request 
	 * @return 
	 */

	public MessageResponse updateSystemSetting(SystemSettingUpdateRequest request) {
		// 若TPID存在，則修改資料，並回傳修改結果
		return getSystemSettingIfExists(systemSetting -> updateSystemSetting(systemSetting, request));
	}
	
	/**
	 * 透過TPID取得該筆系統設定資料
	 * 若資料存在則執行特定方法
	 * 若資料不存在則拋出錯誤
	 * @param <T>
	 * @param handler
	 * @return
	 */
	private <T> T getSystemSettingIfExists(Function<SystemSetting, T> handler) {
		log.info("getSystemSetting tpid={} ", SYSTEM_SETTING_TPID);
		// 取得TPID
		var optionalSysSetting = systemSettingDao.findById(SYSTEM_SETTING_TPID);
		// 若TPID存在，執行特定方法
		if (optionalSysSetting.isPresent()) {
			return handler.apply(optionalSysSetting.get());
		// 若不存在，拋出TPID不存在錯誤
		}else {
			throw new SaApiException(SystemSettingErrorCode._001);
		}
	}
	
	/**
	 * 修改系統設定並回傳
	 * @param systemSetting
	 * @param request
	 * @return
	 */
	private MessageResponse updateSystemSetting(SystemSetting systemSetting, SystemSettingUpdateRequest request) {
		// 修改系統設定
		systemSettingDao.save(buildSystemSetting(systemSetting, request));
		// 回傳修改結果
		return MessageResponse.builder()
				.message(ResultMessage.UPDATE_SUCCESSFUL.getMessage())
				.build();
	}
	
	/**
	 * 將SystemSettingUpdateRequest轉SystemSetting
	 * @param systemSetting
	 * @param request
	 * @return
	 */
	private SystemSetting buildSystemSetting(SystemSetting systemSetting, SystemSettingUpdateRequest request) {
		// SystemSettingUpdateRequest -> SystemSetting
		systemSetting.setTpopEmail(request.getTpopEmail());
		systemSetting.setSmtpHost(request.getSmtpHost());
		systemSetting.setPayPage(request.getPayPage());
		systemSetting.setErrPage(request.getErrPage());
		systemSetting.setTpKey(request.getTpKey());
		systemSetting.setIbmPm(request.getIbmPm());
		systemSetting.setSet(request.getSet());
		systemSetting.setTrace(request.getTrace());
		systemSetting.setLog(request.getLog());
		systemSetting.setBypass(request.getBypass());
		systemSetting.setPmHost(request.getPmHost());
		systemSetting.setPmPort(request.getPmPort());
		systemSetting.setPmId(request.getPmId());
		systemSetting.setPmPwd(request.getPmPwd());
		systemSetting.setPmSSL(request.getPmSSL());
		systemSetting.setVisa3DPage(request.getVisa3DPage());
		systemSetting.setThreedRedirectModeFlag(request.getThreedRedirectModeFlag().getValue());
		systemSetting.setCspSriFlag(request.getCspSriFlag().getValue());
		return systemSetting;
	}
	
	/**
	 * 將SystemSettiing轉SystemSettingResponse
	 * @param systemSetting
	 * @return
	 */
	private SystemSettingResponse buildSystemSettingResponse(SystemSetting systemSetting) {
		// systemSetting -> SystemSettingResponse
		return SystemSettingResponse.builder()
				.tpId(systemSetting.getTpId())
				.tpopEmail(systemSetting.getTpopEmail())
				.smtpHost(systemSetting.getSmtpHost())
				.payPage(systemSetting.getPayPage())
				.errPage(systemSetting.getErrPage())
				.tpKey(systemSetting.getTpKey())
				.ibmPm(systemSetting.getIbmPm())
				.set(systemSetting.getSet())
				.trace(systemSetting.getTrace())
				.log(systemSetting.getLog())
				.bypass(systemSetting.getBypass())
				.pmHost(systemSetting.getPmHost())
				.pmPort(systemSetting.getPmPort())
				.pmId(systemSetting.getPmId())
				.pmPwd(systemSetting.getPmPwd())
				.pmSSL(systemSetting.getPmSSL())
				.visa3DPage(systemSetting.getVisa3DPage())
				.threedRedirectModeFlag(Avalibility.getAvalibilityByValue(systemSetting.getThreedRedirectModeFlag()))
				.cspSriFlag(Avalibility.getAvalibilityByValue(systemSetting.getCspSriFlag()))
				.build();
	}

}
