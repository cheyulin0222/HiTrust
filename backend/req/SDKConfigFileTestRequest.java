package com.hitrsutpay.sa.backend.req;

import java.util.List;

import com.hitrsutpay.sa.backend.domain.MerChantConf;

import lombok.Data;

@Data
public class SDKConfigFileTestRequest {

	private List<MerChantConf> testList;
}
