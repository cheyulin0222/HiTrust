package com.hitrsutpay.sa.backend.domain;

import java.io.Serializable;
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

@Entity
@Table(name="TP_TEMPLET_PAGE")
@Builder
@AllArgsConstructor
@DynamicUpdate
public class TpTempletPage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private TpTempletPagePK id;
	
	private String status;
	
	@Column(name="UPDATE_TIME")
	private Timestamp updateTime;
	
	@Column(name="CREATE_TIME", updatable = false)
	private Timestamp createTime;
	
	public TpTempletPage() {
		
	}
	
	@PrePersist
	public void prePersist() {
		this.createTime = new Timestamp(System.currentTimeMillis());
		this.updateTime = new Timestamp(System.currentTimeMillis());
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updateTime = new Timestamp(System.currentTimeMillis()); 
	}
	
	public TpTempletPagePK getId() {
		return this.id;
	}

	public void setId(TpTempletPagePK id) {
		this.id = id;
	}
	
	public String getStatus() {
		return this.status ;
	}
	
	public void setStaus( String status ) {
		this.status = status ;
	}
	
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
