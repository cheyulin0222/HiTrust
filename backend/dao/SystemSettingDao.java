package com.hitrsutpay.sa.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitrsutpay.sa.backend.domain.SystemSetting;

public interface SystemSettingDao extends JpaRepository<SystemSetting, String> {

}
