package com.hitrsutpay.sa.backend.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payload {

	@JsonProperty("Version")
	private String version;
	@JsonProperty("BuildTime")
	private String buildTime;
	@JsonProperty("Payload")
	private String payload;
}
