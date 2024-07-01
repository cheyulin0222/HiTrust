package com.hitrsutpay.sa.backend.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hitrsutpay.sa.backend.domain.TpTempletPage;
import com.hitrsutpay.sa.backend.domain.TpTempletPagePK;
import com.hitrsutpay.sa.backend.enums.TempletPageType;

public interface TpTempletPageDao extends JpaRepository<TpTempletPage,TpTempletPagePK> ,JpaSpecificationExecutor<TpTempletPage>{
	@Query("SELECT DISTINCT id.country FROM TpTempletPage temp WHERE id.type = 'PAYPAGE' AND temp.status = 'Y' ORDER BY id.country ")
	List<String> getAllCountry();
	
	@Query("SELECT temp FROM TpTempletPage temp WHERE id.type = 'PAYPAGE' AND temp.status = 'Y' ORDER BY id.country ")
	List<TpTempletPage> getAllTemplet();
	
	@Query("SELECT temp FROM TpTempletPage temp WHERE id.type = 'EZP' AND temp.status = 'Y' ORDER BY id.country ")
	List<TpTempletPage> getAllEZPTemplet();
	
	@Query("SELECT temp FROM TpTempletPage temp WHERE id.type = 'PAYPAGE' AND temp.status = 'Y' ORDER BY id.country ")
	List<TpTempletPage> getAllPayPageTemplet();
	
	@Query("SELECT temp FROM TpTempletPage temp WHERE id.type = 'PAYPAGE' AND id.country = :country AND temp.status = 'Y' ORDER BY id.country ")
	List<TpTempletPage> findByCountry(@Param("country") String country);
	
	@Query("SELECT temp FROM TpTempletPage temp WHERE id.type = 'EZP' AND id.templetId = :templetId AND temp.status = 'Y' ORDER BY id.country ")
	List<TpTempletPage> findByEasyTemplate( @Param("templetId") String templetId );

	@Query("SELECT temp FROM TpTempletPage temp WHERE id.type = 'PAYPAGE' AND id.country = :country AND id.templetId = :templetId AND temp.status = 'Y' ORDER BY id.country ")
	List<TpTempletPage> findByCountryTemplet(@Param("country") String country,@Param("templetId") String templetId);
	
	@Query("SELECT temp FROM TpTempletPage temp WHERE temp.id.type = :type")
	Page<TpTempletPage> getTemplatePagesCountByType(@Param("type") TempletPageType type , Pageable pageable );
	

	
}
