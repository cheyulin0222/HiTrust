package com.hitrsutpay.sa.backend.constant;

import java.math.BigDecimal;

public class MerchantConstant {
	// TpMerReplace
	public static String MERLOGO			= "MERLOGO";
	public static String MERNAME			= "MERNAME";
	public static String MERTEL				= "MERTEL";
	public static String MERFAX				= "MERFAX";
	public static String MERADDR			= "MERADDR";
	public static String MERURL				= "MERURL";
	public static String MEREMAIL			= "MEREMAIL";
	public static String MERVISAID			= "MERVISAID";
	public static String MERVISANAME		= "MERVISANAME";
	public static String MERVISAPWD			= "MERVISAPWD";
	public static String MERMASTERID		= "MERMASTERID";
	public static String MERMASTERPWD 		= "MERMASTERPWD";
	public static String MERMASTERNAME		= "MERMASTERNAME";
	public static String MERJCBID			= "MERJCBID";
	public static String MERJCBPWD			= "MERJCBPWD";
	public static String MERJCBNAME			= "MERJCBNAME";
	public static String MERAEID			= "MERAEID";
	public static String MERAEPWD			= "MERAEPWD";
	public static String MERAENAME			= "MERAENAME";
	public static String TRX_LTD			= "TRX_LTD";
	public static String TRX_CHK			= "TRX_CHK";
	public static String COUNTRY			= "COUNTRY";
	public static String CURRENCY			= "CURRENCY";
	public static String MCBIN				= "MCBIN";
	public static String NICKNAMEEN			= "NICKNAMEEN";
	public static String NICKNAMECH			= "NICKNAMECH";
	public static String EASYTEMPLATE		= "EASYTEMPLATE";
	public static String EZP3MERUPDATEURL	= "EZP3MERUPDATEURL";
	public static String REQ_SOURCE			= "REQ_SOURCE";
	public static String DISC_RATE			= "DISC_RATE";
	public static String TOKEN3DPAGE		= "TOKEN3DPAGE";
	public static String ADMINTEL			= "ADMINTEL";
	public static String NOTICE_TYPE		= "NOTICE_TYPE";
	public static String EZP_SIP_FLAG		= "EZP_SIP_FLAG";
	public static String SHOPIFY_SIP_TYPE	= "SHOPIFY_SIP_TYPE";
	public static String RISK_SERVICE_FLAG  = "RISK_SERVICE_FLAG";
	public static String PROMOTE  			= "PROMOTE";
	public static String CFC_FREE_TYPE		= "CFC_FREE_TYPE";
	public static String LINE_CAP			= "LINE_CAP";
	public static String BRANCH				= "BRANCH";
	public static String VACHECK			= "VACHECK";
	public static String E_INVOICE			= "E_INVOICE";
	public static String RENTER_ID			= "RENTER_ID";
	public static String CETUSTEK_ACNT		= "CETUSTEK_ACNT";
	public static String CETUSTEK_PWD		= "CETUSTEK_PWD";
	public static String TAX_IN				= "TAX_IN";
	public static String INVOICE_TYPE		= "INVOICE_TYPE";
	public static String TAX_TYPE			= "TAX_TYPE";
	public static String TAX_RATE			= "TAX_RATE";
	public static String PRODUCT_TYPE		= "PRODUCT_TYPE";
	public static String AUTO_INVOICE		= "AUTO_INVOICE";
	public static String NPOBAN				= "NPOBAN";
	public static String E_INSTALLMENT		= "E_INSTALLMENT";
	public static String QRC_REDEM_POINT 	= "QRC_REDEM_POINT";
	public static String QRC_INSTALLMENT 	= "QRC_INSTALLMENT";

	
	public static String DEFAULT_MER_LOGO	= "HiTRUST.svg";
	public static String DEFAULT_MERCHANT_URL = "https://www.hitrustpay.com.tw";
	// TpMerRisk
	public static String VISA3D 	= "VISA3D";
	public static String REFUND		= "REFUND";
	public static String OTP		= "OTP";
	public static String OTS 		= "OTS";
	public static String ALERT		= "ALERT";
	public static String TOKENPAY 	= "TOKENPAY";
	public static String AMEX_3D	= "AMEX_3D";
	public static String AP			= "AP";
	public static String GP 		= "GP";
	public static String SP			= "SP";
	public static String SPBUPOP 	= "SPBUPOP";
	public static String LINE_PAY	= "LINE_PAY";
	public static String CVSLIMIT	= "CVSLIMIT";
	public static String UNCAPTURE	= "UNCAPTURE";
	public static String PANTEMP 	= "PANTEMP" ;
	public static String RTNPAN 	= "RTNPAN" ;
	public static String RTU 		= "RTU" ;
	public static String SUF 		= "SUF" ;
	
	// TPMEREXTRADATA
	public static String INSATLLMENT_SETTING = "INSATLLMENT_SETTING";
	
	// TP_MER_VID_CFG
	public static String INSTITUTE_ID = "INSTITUTE_ID";
	
	// B2bMerConfig
	public static String PUBLIC_KEY				= "Public_Key";
	public static String MER_TYPE_SSL			= "SSL";
	public static String MER_TYPE_MAN			= "MAN";
	public static String DEFULT_CUT_OFF_MONTH	=	"P";
	public static String DEFULT_AUTO_SETTLE		=	"N";
	public static String AUTO_SETTLE_YES		=	"Y";
	public static String DEFULT_AUTO_TERM		=	"N";
	public static String DEFAULT_FTP_BATCH_FLAG		=	"N";
	public static String DEFAULT_SETTLE_HOUR		=	"0";
	public static BigDecimal DEFULT_BIGDECIMAL	= new BigDecimal(0);
	public static String DEFULT_ACH_DATE 		= "2008-01-01" ;
	public static int DEFULT_ACHPERIOD			= 1 ;
	public static int DEFULT_AP_MARGIN			= 1 ;
	public static int DEFULT_AP_PAYRANGE		= 0 ;
	public static int DEFULT_EXCEEDMAXAMTCOUNT	= 0 ;
	public static String AMEX_ACQ_NAME			= "NCCC" ;
	// B2bMerMapping
	public static String DEFAULT_ACQUIRERID = "888888";
	public static String DEFAULT_MCC_CODE = "4011";
	
	// common
	public static String NA = "N/A";
	public static String CURRENCY_TWD	= "TWD";
	public static String CURRENCY_NTD	= "NTD";
	public static String DEFULT_EXP		= "-10";
	public static boolean SUCCESS		= true;
	public static boolean FAIL			= false;
	public static String DEFULT_AP_FTP_BATCH_FLAG	= "N";
	public static String DEFULT_DISC_RATE	= "100";
	public static String DEFULT_FTP_BATCH_GROUP	= "00";
	public static String DEFULT_EXP10	= "-2";
	public static String MERCHANT_TYPE_TK	= "TK";
	public static String MERCHANT_TYPE_WI	= "WI";
	public static String BOOLEAN_CLOSE_VALUE = "0";
	public static String BOOLEAN_OPEN_VALUE = "1";
	public static String DEFULT_ACQUIRER_ID_VALUE = "888888";
	public static String DEFULT_STRING_BLANK = "";
	//other
	public static String MCBIN_TSB="517852";
	public static String SPBUPOP_MCCCODE = "4011" ;
	public static String AP_MCCCODE = "000" ;
	public static String REQ_SOURCE_NULL = "Nan" ;
	
}
