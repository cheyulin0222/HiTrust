package com.hitrsutpay.sa.backend.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenPayMappingId {
	
	@Column(name = "REGMERID")
	private String regMerId;
	
	@Column(name = "AUTHMERID")
	private String authMerId;
}
