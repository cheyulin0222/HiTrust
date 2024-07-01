package com.hitrsutpay.sa.backend.domain;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Entity
@Table(name = "TPTRXTOKEN_AUTHMER_MAPPING")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@DynamicUpdate
public class TokenPayMapping {

	@EmbeddedId
	private TokenPayMappingId id;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATEUSER", updatable = false)
	private String createUser;
	
	@Column(name = "CREATEDTTM", updatable = false)
	private Timestamp createTime;
	
	@Column(name = "UPDATEUSER")
	private String updateUser;
	
	@Column(name = "UPDATEDTTM")
	private Timestamp updateTime;

	@PrePersist
	public void prePersist() {
		this.createUser = this.updateUser;
		this.createTime = new Timestamp(System.currentTimeMillis());
		this.updateTime = new Timestamp(System.currentTimeMillis());
		
		log.info("[PrePersist] createUser={}, updateUser{}, createTime={}, updateTime={}", 
                this.createUser, this.updateUser, this.createTime, this.updateTime);
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updateTime = new Timestamp(System.currentTimeMillis()); 
		
		log.info("[PreUpdate] updateTime={}", 
				this.updateTime);
	}
	 
}
