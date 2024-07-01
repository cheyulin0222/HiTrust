package com.hitrsutpay.sa.backend.domain;


import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TPCONFIG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class SystemSetting {

	@Id
	@Column(name="TPID")
	private String tpId;
	
	@Column(name="TPOPEMAIL")
	private String tpopEmail;
	
	@Column(name="SMTPHOST")
	private String smtpHost;
	
	@Column(name="PAYPAGE")
	private String payPage;
	
	@Column(name="ERRPAGE")
	private String errPage;
	
	@Column(name="TPKEY")
	private String tpKey;
	
	@Column(name="IBMPM")
	private String ibmPm;
	
	@Column(name="SET")
	private String set;
	
	@Column(name="TRACE")
	private String trace;
	
	@Column(name="LOG")
	private String log;
	
	@Column(name="BYPASS")
	private String bypass;
	
	@Column(name="PMHOST")
	private String pmHost;
	
	@Column(name="PMPORT")
	private String pmPort;
	
	@Column(name="PMID")
	private String pmId;
	
	@Column(name="PMPWD")
	private String pmPwd;
	
	@Column(name="PMSSL")
	private String pmSSL;
	
	@Column(name="VISA3DPAGE")
	private String visa3DPage;
	
	@Column(name="THREED_REDIRECT_MODE_FLAG")
	private String threedRedirectModeFlag;
	
	@Column(name="CSP_SRI_FLAG")
	private String cspSriFlag;

}
