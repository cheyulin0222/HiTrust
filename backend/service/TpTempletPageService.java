package com.hitrsutpay.sa.backend.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitrsutpay.sa.backend.dao.TpTempletPageDao;
import com.hitrsutpay.sa.backend.domain.TpTempletPage;
import com.hitrsutpay.sa.backend.domain.TpTempletPagePK;
import com.hitrsutpay.sa.backend.enums.ResultMessage;
import com.hitrsutpay.sa.backend.enums.TempletPageType;
import com.hitrsutpay.sa.backend.enums.TwoFactor;
import com.hitrsutpay.sa.backend.exception.SaApiException;
import com.hitrsutpay.sa.backend.exception.enums.TempletPageErrorCode;
import com.hitrsutpay.sa.backend.req.TempletPageCreateRequest;
import com.hitrsutpay.sa.backend.req.TempletPageRequestBase;
import com.hitrsutpay.sa.backend.req.TempletPageUpdateRequest;
import com.hitrsutpay.sa.backend.res.MessageResponse;
import com.hitrsutpay.sa.backend.res.Option;
import com.hitrsutpay.sa.backend.res.TempletPageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TpTempletPageService {
	
	private final TpTempletPageDao tpTempletPageDao ;
	
	public List<Option> getCountryOptions(){
		log.info("getFIDOptions");
		List<Option> result = tpTempletPageDao.getAllCountry().stream().map( (r) -> {
    		return new Option(r,r) ;
		}).toList();
		return result ;
	}
	
	public Map<String , List<Option>> getAllTemplageOptions(){
		log.info("getAllTemplageOptions");
		List<TpTempletPage> list = tpTempletPageDao.getAllTemplet();
		HashMap<String , List<Option>> map = new HashMap<String , List<Option>>();
		for( int i = 0 ; i < list.size(); i++ ) {
			TpTempletPage temp = list.get(i);
			String country = temp.getId().getCountry();
			List<Option> list2 ;
			if( map.containsKey(country) ) {
				list2 = map.get(country);
			} else {
				list2 = new ArrayList<Option>();
			}
			list2.add(new Option(temp.getId().getTempletId(),temp.getId().getTempletId()));
			map.put(country, list2);
		}
		return map ;
	}
	
	public List<Option> getAllEZPTemplate(){
		log.info("getAllEZPTemplate");
		List<Option> result = tpTempletPageDao.getAllEZPTemplet().stream().map( (r) -> {
    		return new Option(r.getId().getTempletId(),r.getId().getTempletId()) ;
		}).toList();
		return result ;
	}
	
	public List<Option> getAllPayPageTemplate(){
		log.info("getAllPAYPAGETemplate");
		List<Option> result = tpTempletPageDao.getAllPayPageTemplet().stream().map( (r) -> {
    		return new Option(r.getId().getTempletId(),r.getId().getTempletId()) ;
		}).toList();
		return result ;
	}

	
	public boolean checkCountry( String country ) {
		List<TpTempletPage> temp = tpTempletPageDao.findByCountry(country);
		return !temp.isEmpty();
	}
	
	public boolean checkTemplet( String country , String templet) {
		List<TpTempletPage> temp = tpTempletPageDao.findByCountryTemplet( country, templet);
		return !temp.isEmpty();
	}
	
	public boolean checkEasyTemplate( String templet) {
		List<TpTempletPage> temp = tpTempletPageDao.findByEasyTemplate( templet);
		return !temp.isEmpty();
	}
	
	/**
	 * 查詢方便付或付款頁頁面樣式資料
	 * @param type
	 * @param pageable
	 * @return
	 */
	public Page<TempletPageResponse> getTempletPage(TempletPageType type, Pageable pageable) {
		log.info("type={}", type.name());
		
		Page<TpTempletPage> result = tpTempletPageDao.getTemplatePagesCountByType(type, pageable);
		
		// 回傳Page格式資料
		return result.map( tempPage -> TempletPageResponse.builder()
				.country( tempPage.getId().getCountry() )
				.templetId( tempPage.getId().getTempletId() )
				.type( tempPage.getId().getType() )
				.updateTime(tempPage.getUpdateTime())
				.status( TwoFactor.get( tempPage.getStatus() ) )
				.build());
	}
	
	/**
	 * 新增方便付、付款頁頁面樣式資料
	 * @param request
	 * @return
	 */
	public MessageResponse createTempletPage(TempletPageCreateRequest request) {
		TempletPageType type = request.getType() ;
		String fileName = request.getFileName() ;
		log.info("type={}, fileName={}, type={}", type , fileName );
		
		String country = "" ;
		String templetId = "" ;
		String[] fileNames = fileName.split("\\.");
		if( TempletPageType.EZP == type ) {
			if( fileNames.length != 2 ) {
				throw new SaApiException(TempletPageErrorCode._004);
			}
			templetId = fileNames[0] ;
		} else if( TempletPageType.PAYPAGE == type) {
			if( fileNames.length != 3 ) {
				throw new SaApiException(TempletPageErrorCode._004);
			}
			country = fileNames[0];
			templetId = fileNames[1];
		}
		
		TempletPageRequestBase temp = new TempletPageRequestBase();
		temp.setCountry(country);
		temp.setTempletId(templetId);
		temp.setType(type);
		
		// 取得 TempletPage表格 的 id
		var id = buildId(temp);
		// 若id存在，則拋出錯誤
		if (isIdExist(id)) {
			throw new SaApiException(TempletPageErrorCode._002);
		}
		// 新增並回傳結果
		return saveTpTempletPage(id, TwoFactor.YES, ResultMessage.INSERT_SUCCESSFUL);
	}
	
	/**
	 * 修改方便付、付款頁頁面樣式資料
	 * @param request
	 * @return
	 */
	public MessageResponse updateTempletPage(TempletPageUpdateRequest request) {
		log.info("country={}, templetId={}, type={}, status={}", 
				request.getId().getCountry(), request.getId().getTempletId(), request.getId().getType().name(), request.getStatus().name());
		
		// 取得 TempletPage表格 的 id
		var id = buildId(request.getId());
		
		// 若id不存在，則拋出錯誤
		if (!isIdExist(id)) {
			throw new SaApiException(TempletPageErrorCode._003);
		}
		// 修改並回傳結果
		return saveTpTempletPage(id, request.getStatus(), ResultMessage.UPDATE_SUCCESSFUL);
	}
	
	/**
	 * 透過id(country、templetId、type)找付款頁資料
	 * 用來生成previousData(測試用)
	 * @param id
	 * @return
	 */
	public TempletPageResponse findTempletPageById(TpTempletPagePK id) {
		var optional = tpTempletPageDao.findById(id);
		TempletPageResponse response = null;
		if (optional.isPresent()) {
			 var templetPage = optional.get();
			 response = TempletPageResponse.builder()
				.country(templetPage.getId().getCountry())
				.templetId(templetPage.getId().getTempletId())
				.type(templetPage.getId().getType())
				.updateTime(templetPage.getUpdateTime())
				.status(TwoFactor.get(templetPage.getStatus()))
				.build();
		}
		return response;
	}
	
	/**
	 * 根據ID刪除頁面樣式資料(測試用)
	 * @param id
	 */
	public void deleteTempletPage(TpTempletPagePK id) {
		tpTempletPageDao.deleteById(id);
	}
	
	/**
	 * 透過ID確認TempletPage是否存在
	 * @param id
	 * @return
	 */
	private boolean isIdExist(TpTempletPagePK id) {
		// 該筆TempletPage是否存在
		return tpTempletPageDao.existsById(id);
	}
	
	/**
	 * 新增或修改付款頁樣式並回傳結果
	 * @param id
	 * @param status
	 * @param message
	 * @return
	 */
	private MessageResponse saveTpTempletPage(TpTempletPagePK id, TwoFactor status, ResultMessage message) {
		// 新增或修改資料
		tpTempletPageDao.save(buildTpTempletPage(id, status));
		
		// 回傳結果
		return MessageResponse.builder()
				.success(true)
				.message(message.getMessage())
				.build();
	}
	
	/**
	 * build TempletPage
	 * @param id
	 * @param status
	 * @return
	 */
	private TpTempletPage buildTpTempletPage(TpTempletPagePK id, TwoFactor status) {
		// 透過id、status產生Entity
		return TpTempletPage.builder()
			.id(id)
			.status(status.getValue())
			.build();
	}
	
	/**
	 * buildTempletPage的ID
	 * @param request
	 * @return
	 */
	private TpTempletPagePK buildId(TempletPageRequestBase request) {
		// 產生id
		return TpTempletPagePK.builder()
			.country(request.getCountry())
			.templetId(request.getTempletId())
			.type(request.getType())
			.build();
	}
}
