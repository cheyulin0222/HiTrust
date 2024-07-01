package com.hitrsutpay.sa.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hitrsutpay.sa.backend.domain.TokenPayMapping;
import com.hitrsutpay.sa.backend.domain.TokenPayMappingId;

public interface TokenPayMappingDao extends JpaRepository<TokenPayMapping, TokenPayMappingId> {

	@Query("select t from TokenPayMapping t where t.id.regMerId =:regMerId")
	List<TokenPayMapping> findAllByRegMerId(@Param("regMerId") String regMerId);
}
