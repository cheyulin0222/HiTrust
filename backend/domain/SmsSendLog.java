package com.hitrsutpay.sa.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;



/**
 * The persistent class for the SMS_SEND_LOG database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="SMS_SEND_LOG")
public class SmsSendLog{

	@Id
	private String uuid;

	private String acqid;

	@Lob
	@Column(name="\"CONTENT\"")
	private String content;

	@Lob
	@Column(name="CONTENT_B64")
	private String contentB64;

	private Timestamp dttm;

	private String merid;

	private String ordernum;

	private String rslt;

	@Column(name="RSLT_DATA")
	private String rsltData;

	@Column(name="TEL_HASH")
	private String telHash;

	@Column(name="TEL_MASK")
	private String telMask;

	@Column(name="\"TYPE\"")
	private String type;

}