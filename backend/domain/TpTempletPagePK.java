package com.hitrsutpay.sa.backend.domain;

import java.io.Serializable;

import com.hitrsutpay.sa.backend.enums.TempletPageType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TpTempletPagePK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String country ;
	
	@Column(name="TEMPLET_ID")
	private String templetId ;
	
	@Column(name="\"TYPE\"")
    @Enumerated(EnumType.STRING)
	private TempletPageType type ;
	
	public TpTempletPagePK() {
	}
	
	public String getCountry() {
		return this.country ;
	}
	
	public void setCountry( String country) {
		this.country = country ;
	}
	
	public String getTempletId() {
		return this.templetId ;
	}
	
	public void setTempleteId( String templetId) {
		this.templetId = templetId ;
	}
	
	public TempletPageType getType() {
		return this.type ;
	}

	public void setType(TempletPageType type) {
		this.type = type ;
	}
}
