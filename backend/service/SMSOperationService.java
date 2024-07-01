package com.hitrsutpay.sa.backend.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitrsutpay.sa.backend.constant.MerchantConstant;
import com.hitrsutpay.sa.backend.dao.SmsSendLogDao;
import com.hitrsutpay.sa.backend.domain.SmsSendLog;
import com.hitrsutpay.sa.backend.enums.SMSResult;
import com.hitrsutpay.sa.backend.enums.SMSType;
import com.hitrsutpay.sa.backend.req.SMSLogDataRequest;
import com.hitrsutpay.sa.backend.req.SmsFeeReportRequest;
import com.hitrsutpay.sa.backend.res.Option;
import com.hitrsutpay.sa.backend.res.SMSLogDataResponse;

import jakarta.persistence.EntityManager;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SMS_SEND_LOG資料表查詢並下載簡訊資料
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SMSOperationService {
	
	
	private final SmsSendLogDao mSmsSendLogDao;
	private final EntityManager entityManager;
	
	public static Properties smsRsltProperties;
	
	public static Properties smsTypeProperties;
	
	public static Properties acquireBankProperties;
	
	static {
		acquireBankProperties = new Properties();
		smsTypeProperties = new Properties();
		smsRsltProperties = new Properties();
		try {
			acquireBankProperties.load(new ClassPathResource("acquirer.properties").getInputStream());
			smsTypeProperties.load(new ClassPathResource("smsType.properties").getInputStream());
			smsRsltProperties.load(new ClassPathResource("smsRslt.properties").getInputStream());
		} catch (IOException e) {
			log.error("load properties failed !",e);
		}
	}
	
	/**
	 * 從acquirer.properties轉成下拉
	 * @return
	 */
	public List<Option> getAcquirerOption(){
		return acquireBankProperties.entrySet().stream().map(e -> 
			new Option((String)e.getKey(),(String)e.getValue())
		).toList();
	}
	
	public ByteArrayInputStream exportSmsOperationFeeReport(SmsFeeReportRequest smsFeeReportRequest) throws Exception{
		log.info("exportSmsOperationFeeReport {} ",smsFeeReportRequest);
		// 取得收單行及查詢時間
		var acqid = smsFeeReportRequest.getAcqId().name();
		var startDateTime = Timestamp.valueOf(LocalDate.of(smsFeeReportRequest.getYear(),smsFeeReportRequest.getMonth(), 1).atTime(LocalTime.MIN));
		var endDateTime = Timestamp.valueOf(LocalDate.of(smsFeeReportRequest.getYear(),smsFeeReportRequest.getMonth(),1).with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX));
		log.info("exportSmsOperationFeeReport startDateTime={} ,endDateTime={} ",startDateTime,endDateTime);
		
		// 從資料庫取得資料
		var smsSendLogList = mSmsSendLogDao.getSmsSendLogs(smsFeeReportRequest.getAcqId().name(), startDateTime, endDateTime);
		// 轉乘response格式
		
		var resList = buildSMSLogExcelData(smsSendLogList);
		
		log.info("resList={}", resList);
		
		// 將回傳資料轉成excel格式
		WritableWorkbook smsBook = null;
		try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			// 基本格式設定
			var total = smsSendLogList.size();
			var smsfee = smsFeeReportRequest.getFee();
			WritableCellFormat wcf = null;
			WritableFont wf = null;
			Label label = null;
			smsBook = Workbook.createWorkbook(out);
			//Excel樣式
			var formatter = new DecimalFormat("#,###.##");
			var fn = WritableFont.createFont("新細明體");
			wf = new WritableFont(fn, 11);
			wf.setBoldStyle(WritableFont.BOLD);
			wcf = new WritableCellFormat(wf);
			wcf.setAlignment(jxl.format.Alignment.CENTRE);
			wcf.setBackground(jxl.format.Colour.GRAY_25);
			wcf.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.MEDIUM);
			wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM);
			wcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.MEDIUM);
			wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.MEDIUM);

			//Excel-0 title
			WritableSheet sheetFirst = smsBook.createSheet("簡訊筆數總計", 0);
			String countTitle[] = { "收單行","成功筆數", "失敗筆數", "總筆數" ,"單筆金額"," 費用總計"};

			for (int i = 0; i < countTitle.length; i++) {
				label = new Label(i, 0, countTitle[i]);
				label.setCellFormat(wcf);
				sheetFirst.setColumnView(i, 15);
				sheetFirst.addCell(label);
			}
			 
			// 將資料轉成期望的格式
			//Excel-1 title
			WritableSheet sheetSuccess = smsBook.createSheet("簡訊費用明細", 1);
			String title[] = { "商家代碼", "訂單編號", "簡訊類型", "手機號碼", "發送結果", "發送時間" };
			for (int i = 0; i < title.length; i++) {
				sheetSuccess.setColumnView(i, 22);
				label = new Label(i, 0, title[i]);
				label.setCellFormat(wcf);
				sheetSuccess.addCell(label);
			}
			//Excel樣式
			wf = new WritableFont(fn, 11);
			wf.setBoldStyle(WritableFont.NO_BOLD);
			wcf = new WritableCellFormat(wf);
			wcf.setAlignment(jxl.format.Alignment.CENTRE);
			wcf.setBackground(jxl.format.Colour.WHITE);
			wcf.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.MEDIUM);
			wcf.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.MEDIUM);
			wcf.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.MEDIUM);

			//Excel-0 content
			label = new Label(0, 1, acquireBankProperties.getProperty(acqid));
			label.setCellFormat(wcf);
			sheetFirst.addCell(label);
			
			label = new Label(1, 1, String.valueOf(resList.size()));
			label.setCellFormat(wcf);
			sheetFirst.addCell(label);

			label = new Label(2, 1, String.valueOf(total - resList.size()));
			label.setCellFormat(wcf);
			sheetFirst.addCell(label);

			label = new Label(3, 1, String.valueOf(total));
			label.setCellFormat(wcf);
			sheetFirst.addCell(label);
			
			label = new Label(4, 1, String.valueOf(smsfee));
			label.setCellFormat(wcf);
			sheetFirst.addCell(label);

			label = new Label(5, 1,String.valueOf(formatter.format(total * (smsfee))) + " 元");
			label.setCellFormat(wcf);
			sheetFirst.addCell(label);

			//Excel-1 content
			for (int j = 0; j < resList.size(); j++) {
					label = new Label(0, j + 1, resList.get(j).getMerId());
					label.setCellFormat(wcf);
					sheetSuccess.addCell(label);

					label = new Label(1, j + 1, resList.get(j).getOrderNum());
					label.setCellFormat(wcf);
					sheetSuccess.addCell(label);

					label = new Label(2, j + 1, resList.get(j).getType());
					label.setCellFormat(wcf);
					sheetSuccess.addCell(label);

					label = new Label(3, j + 1, resList.get(j).getTelMask());
					label.setCellFormat(wcf);
					sheetSuccess.addCell(label);

					label = new Label(4, j + 1, resList.get(j).getResult());
					label.setCellFormat(wcf);
					sheetSuccess.addCell(label);

					label = new Label(5, j + 1, resList.get(j).getSendingTime());
					label.setCellFormat(wcf);
					sheetSuccess.addCell(label);

			}
			
			smsBook.write();
			smsBook.close();
			return new ByteArrayInputStream(out.toByteArray());
		}
	}


	/**
	 * 依動態條件(收單行、訂單編號、商店代號、手機號碼、發送時間、簡訊類型、發送結果動)
	 * 至SMS_SEND_LOG資料表查詢簡訊資料
	 * @param request
	 * @return
	 */
	public List<SMSLogDataResponse> getSMSLogData(SMSLogDataRequest request) {
		log.info("SMSOperationService.getSMSLogData SMSLogDataRequest={}", request);
		// 檢查時間，如果開始時間大於結束時間，則將兩個時間對調
		checkTime(request);
		// 依條件取得簡訊資料
		var smsSendLogList = mSmsSendLogDao.findByCriteria(request, entityManager);
		// 回傳查詢結果
		return buildSMSLogDataResponseList(smsSendLogList);
		
	}

	/**
	 * 檢查時間，如果開始時間大於結束時間，則將兩個時間對調
	 * @param request
	 */
	private void checkTime(SMSLogDataRequest request) {
		// 若時間不為null，則做檢查
		if(request.getStartTime() != null && request.getEndTime() != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startDate = sdf.parse(request.getStartTime());
				Date endDate = sdf.parse(request.getEndTime());
				// 若時間大於結束時間，則將兩個時間對調
				if(startDate.compareTo(endDate) > 0) {
	            	String startTime = request.getEndTime();
	            	request.setEndTime(request.getStartTime());
	            	request.setStartTime(startTime);
	            }
			// parse失敗，則將時間都設為null
			} catch (ParseException e) {
				log.info("ParseException");
				request.setStartTime(null);
				request.setEndTime(null);
			}
		}
	}

	/**
	 * buildSMSLogDataResponseListForQuery
	 * @param smsSendLogList
	 * @return
	 */
	private List<SMSLogDataResponse> buildSMSLogDataResponseList(List<SmsSendLog> smsSendLogList) {
		log.info("buildSMSLogDataResponseList smsSendLogList={}", smsSendLogList);
		// List<SmsSendLog> -> List<SMSLogDataResponse>
		return smsSendLogList.stream().map(log -> buildSMSLogDataResponse(log)).collect(Collectors.toList());
	}
	
	/**
	 * buildSMSLogDataResponseListForExcel
	 * @param smsSendLogList
	 * @return
	 */
	private List<SMSLogDataResponse> buildSMSLogExcelData(List<SmsSendLog> smsSendLogList) {
		var responseList = new ArrayList<SMSLogDataResponse>();
		smsSendLogList.stream().forEach(log -> {
			if (SMSResult.SUCCESS.getValue().equals(log.getRslt())) {
				responseList.add(buildSMSLogDataResponse(log));
			}
		});
		return responseList;
	}
	
	/**
	 * buildSMSLogDataResponse
	 * @param log
	 * @return
	 */
	private SMSLogDataResponse buildSMSLogDataResponse(SmsSendLog log) {
		// SmsSendLog -> SMSLogDataResponse
		return SMSLogDataResponse.builder()
			.merId(log.getMerid() == null ? MerchantConstant.NA : log.getMerid())
			.orderNum(log.getOrdernum() == null ? MerchantConstant.NA : log.getOrdernum())
			.acqId(log.getAcqid() == null ? MerchantConstant.NA : log.getAcqid())
			.content64(log.getContentB64() == null ? MerchantConstant.NA : new String(Base64.decodeBase64(log.getContentB64())))
			.type(SMSType.get(log.getType()).getLabel())
			.telMask(log.getTelMask() == null ? MerchantConstant.NA : log.getTelMask())
			.result(SMSResult.get(log.getRslt()).getLabel())
			.sendingTime(log.getDttm())
			.remark(log.getRsltData() == null ? MerchantConstant.NA : log.getRsltData())
			.build();
	}

}
