package com.hitrsutpay.sa.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitrsutpay.sa.backend.domain.StatusCodeMapping;

public interface StatusCodeMappingDao extends JpaRepository<StatusCodeMapping, Integer>{

	public boolean existsByStatusCode(String name);
	
	public StatusCodeMapping findByStatusCode(String name);
}
