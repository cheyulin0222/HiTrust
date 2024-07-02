<template>
	<CardItem colMd="12" class="t-18">
		速速付
	</CardItem>
	<CardItem colMd="6">
		<SelectView :options="selOption.tokenPay" :invalidTxt="invalids.tokenPay" LabelVal = "是否開啟功能" @updateVal="updateVal" ref = "tokenPay" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "token3DPage" LabelVal = "3D導頁設定" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
	</CardItem>

	<CardItem colMd="12" class="t-18">
		<hr class="hr-store">
		方便付
	</CardItem>
	<CardItem colMd="3">
		<SelectView :options="selOption.easyTemplate" :invalidTxt="invalids.easyTemplate" LabelVal = "頁面型式" @updateVal="updateVal" ref = "easyTemplate" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="3">
		<SelectView :options="selOption.ezpSipFlag" :invalidTxt="invalids.ezpSipFlag" LabelVal = "定期定額開關" @updateVal="updateVal" ref = "ezpSipFlag" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "ezp3MerUodateUrl" LabelVal = "方便付3 MerUpdateURL" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly"  maxlength="256"/>
	</CardItem>

	<CardItem colMd="12" class="t-18">
		<hr class="hr-store">
		QRPay
	</CardItem>
	<CardItem colMd="6">
		<SelectView :options="selOption.qrcRedemPoint" :invalidTxt="invalids.qrcRedemPoint" LabelVal = "支援紅利開關" @updateVal="updateVal" ref = "qrcRedemPoint" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "qrcInstallment" LabelVal = "支援分期欄位" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "discRate" LabelVal = "商家折扣率 ( 單位 % )" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="6">
		<SelectView :options="selOption.reqSource" :invalidTxt="invalids.reqSource" LabelVal = "app來源 (REQ_SOURCE)" @updateVal="updateVal" ref = "reqSource" :readOnly = "readOnly" />
	</CardItem>

	<CardItem colMd="12" class="t-18">
		<hr class="hr-store">
	</CardItem>
	<CardItem colMd="3">
		<SelectView :options="selOption.promote" :invalidTxt="invalids.promote" LabelVal = "商店導購服務開關" @updateVal="updateVal" ref = "promote" :readOnly = "promoteReadOnly" />
	</CardItem>
	<CardItem colMd="3">
		<SelectView :options="selOption.riskServiceFlag" :invalidTxt="invalids.riskServiceFlag" LabelVal = "風險偵測服務" @updateVal="updateVal" ref = "riskServiceFlag" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "instituteId" LabelVal = "機構 ID" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" v-if = "compObj.riskServiceFlag === 'TRANSACTION_MODE' " :required = "true"/>
	</CardItem>
	
	<CardItem colMd="12" class="t-18">
		<hr class="hr-store">
		Shopify
	</CardItem>
	<CardItem colMd="6">
		<SelectView :options="selOption.shopifySipType" :invalidTxt="invalids.shopifySipType" LabelVal = "定期定額種類設定" @updateVal="updateVal" ref = "shopifySipType" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="6">
		<SelectView :options="selOption.suf" :invalidTxt="invalids.suf" LabelVal = "開啟失敗訂單回傳" @updateVal="updateVal" ref = "suf" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="6">
		<template v-if = "showEinstallment" >
			<SelectView :options="selOption.einstallment" :invalidTxt="invalids.einstallment" LabelVal = "信用卡分期服務" @updateVal="updateVal" ref = "einstallment" :readOnly = "readOnly"/>
		</template>
	</CardItem>
	<CardItem colMd="6"/>
	<template v-if=" compObj['einstallment'] === 'OPEN' ">
		<CardItem colMd="12">
			<span class="t-12" style="margin-bottom: 0.5rem; margin-right: 0.5rem; display: inline-block;">
				信用卡分期選項(期數用,隔開)
			</span>
			<i class="fa img-fluid onactived pr-md-3 fa-circle-plus" @click = "addIssuer"/>
			<i class="fa img-fluid onactived pr-md-3 fa-minus-circle" @click = "minusIssuer"/>
			<template v-for=" ( object , index ) in insSettingList">
				<div class="row " :class="{'pt-2': index > 0 }">
					<div class=" order-md-1 col-md-4">
						<SelectView :options="getInstalIssuerOption(index,object)" :invalidTxt="invalids['issuer_'+index]" :ref = "'issuer_'+index" :readOnly = "!selIssuer" v-model = "object.issuer"/>
					</div>
					<div class=" order-md-1 col-md-3">
						<Input :ID = "'period_'+index" v-model = "object.periods" :invalids = "invalids" :readOnly = "readOnly" :required = "true" />
					</div>
					<div class=" order-md-1 col-md-1 text-center d-flex align-items-center p-0 justify-content-between" >
						<span>期</span><span>紅利:</span>
					</div>
					<div class=" order-md-1 col-md-3">
						<SelectView :options="getRedemOption(index,object)" :ref = "'redem_'+index" :readOnly = "readOnly" v-model = "object.redem" />
					</div>
				</div>
			</template>
		</CardItem>
	</template>
	<CardItem colMd="12"/>
</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase"
import { isEmpty , parseInt } from "@/utils/util";
import { getOpenOffList , getYesNoList , getinstalIssuerList , getEasyTemplateList , getRiskServiceFlagList , getReqSourceList , getShopifySipTypeList , getPromoteList } from "@/utils/dataListSA";

export default {

	data(){
		let _this = this ;
		let compObj = this.editCopyObj() ;

		/*let instalIssuerList = [];

		if( compObj.installmentSetting != "" ) {
			let installment_setting = JSON.parse(compObj.installmentSetting).installment_setting;
			installment_setting.forEach(function( item ){
				item.periods = item.periods.toString();
				if( item.redem == undefined ) {
					item.redem = "0" ;
				}
			});
			instalIssuerList = installment_setting ;
		}*/
		return {		
			selOption 	: {
				tokenPay : {
		    		ID 			: "tokenPay" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.tokenPay ? compObj.tokenPay : "CLOSED"  ,
		    	},
		    	easyTemplate : {
		    		ID 			: "easyTemplate" ,
		    		func 		: getEasyTemplateList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
		    		defVal 		: compObj.easyTemplate ? compObj.easyTemplate : ""  ,
		    	},
		    	ezpSipFlag : {
		    		ID 			: "ezpSipFlag" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.ezpSipFlag ? compObj.ezpSipFlag : "CLOSED"  ,
		    	},
		    	qrcRedemPoint : {
		    		ID 			: "qrcRedemPoint" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.qrcRedemPoint ? compObj.qrcRedemPoint : "CLOSED"  ,
		    	},
		    	promote : {
		    		ID 			: "promote" ,
		    		func 		: getPromoteList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.promote ? compObj.promote : "DISABLED"  ,
		    	},
		    	riskServiceFlag : {
		    		ID 			: "riskServiceFlag" ,
		    		func 		: getRiskServiceFlagList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.riskServiceFlag ? compObj.riskServiceFlag : "LEARNING_MODE_ON"  ,
		    	},
		    	einstallment : {
		    		ID 			: "einstallment" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.einstallment ? compObj.einstallment : "CLOSED"  ,
		    	},
		    	reqSource : {
		    		ID 			: "reqSource" ,
		    		func 		: getReqSourceList.bind(this),
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
		    		defVal 		: compObj.reqSource ? compObj.reqSource : "Nan"  ,
		    	},
		    	shopifySipType : {
		    		ID 			: "shopifySipType" ,
		    		func 		: getShopifySipTypeList ,
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
		    		defVal 		: compObj.shopifySipType ? compObj.shopifySipType : "HiTRUST"  ,
		    	},
		    	suf : {
		    		ID 			: "suf" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
		    		defVal 		: compObj.suf ? compObj.suf : "CLOSED"  ,
		    	},
			},
			compObj 			: compObj ,
			insSettingList 		: JSON.parse(JSON.stringify(compObj.insSettingList)) ,
		}
	},
	extends : Base ,
	components : {

	},
	computed: {
		selIssuer(){
			return !this.readOnly && ( this.acqBank === "NCCC" || this.acqBank === "HSBC" )
		},
		promoteReadOnly(){
			return this.readOnly || this.compObj.promote == "DISABLED"
		},
		showEinstallment(){
			let acqBanks = ["SPB","TSB","DBS","HSBC","SKB","CTCB","ESB","CUB","TFB","NCCC"] ;
			return acqBanks.indexOf(this.acqBank) > -1 ;
		}
	},
	mounted() {

    },
	methods: {
		updateVal( val , ID ){
			this.compObj[ID] = val ;
		},
		updateVal2( val , ID ){
			let ids = ID.split("_") ;
			this.insSettingList[ids[1]][ids[0]] = val ;
		},
		checkData(){
			let invalids = {} ;
			let compObj = JSON.parse(JSON.stringify(this.compObj)) ;
			let qrcInstallment = compObj["qrcInstallment"];
			let reqSource = compObj["reqSource"];
			let discRate = compObj["discRate"];
			
			if( qrcInstallment != "" ) {
				var rules = /^[\d+;?]+$/ ;
				if( !rules.test(qrcInstallment) ) {
					invalids["qrcInstallment"] = "分期欄位只能輸入數字與分號";
				}
			}

			if( reqSource != "Nan" ) {
				if( discRate === "" ) {
					compObj["discRate"] = "100";
				}
			} else {
				compObj["discRate"] = "" ;
			}

			//let installmentSetting = "" ;
			if( compObj["einstallment"] == 'OPEN' ) {
				let issuers = [] ;
				let insSettingList = this.insSettingList ;
				if( insSettingList.length > 0 ) {
					//let arr = [] ;
					let isOK = true ;
					insSettingList.forEach(function( item , index ){
						let periods = item.periods.trim() ;
						if( periods != "" ) {
							var rules = /^[\d+,?]+$/ ;
							if( issuers.indexOf( item.issuer ) > -1 ) {
								invalids["issuer_"+index] = "信用卡分期選項-發卡行不可重複";
								isOK = false ;
							} else if( !rules.test( item.periods ) ) {
								invalids["period_"+index] = "信用卡分期欄位只能輸入數字與逗號";
								isOK = false ;
							} else {
								issuers.push(item.issuer);
								/*let periods = [] ;
								item.periods.split(",").forEach(function( periods ) {
									periods.push(parseInt(periods));
								});
								arr.push({
									issuer : item.issuer ,
					    			periods : periods ,
					    			redem  : item.redem ,
								}) ;*/
							}
						} else {
							invalids["period_"+index] = "信用卡分期欄位不能為空白";
							isOK = false ;
						}
					});
					if( isOK ) {
						compObj["insSettingList"] = JSON.parse(JSON.stringify(insSettingList)) ;
					}
					//installmentSetting = JSON.stringify( arr ) ;
				} else {
					invalids["einstallment"] = "分期狀態啟用，至少要有一組分期設定";
				}
			}

			if( compObj.riskServiceFlag === 'TRANSACTION_MODE' ) {
				if( compObj["instituteId"] == "" ) {
					invalids["instituteId"] = "風險偵測服務為交易模式,機構 ID必填";
				}
			}

			//compObj["installmentSetting"] = installmentSetting ;
			
			this.invalids = invalids ;
			
			if( isEmpty(invalids) ) {
				this.compObj = compObj ;
				return true ;
			} else {
				return false ;
			}
		},
		defaultCompObj(){
			return {
				tokenPay 			: "CLOSED" ,
				token3DPage 		: ""  ,
				easyTemplate    	: ""  ,
				ezpSipFlag 			: "CLOSED" ,
				ezp3MerUodateUrl	: ""  ,
				qrcRedemPoint   	: "CLOSED"  ,
				qrcInstallment 		: ""  ,
				discRate 			: ""  ,
				reqSource 			: "Nan"  ,
				riskServiceFlag 	: "LEARNING_MODE_ON" ,
				instituteId 		: ""  ,
				promote 			: "DISABLED" ,
				einstallment 		: "CLOSED" ,
				installmentSetting 	: ""  ,
				shopifySipType 		: "HiTRUST" ,
				suf					: "CLOSED" ,
				insSettingList 		: [] ,
			}
		},
		getInstalIssuerOption( index , instalIssuer ){
			return {
	    		ID 			: "instalIssuer_"+index  ,
	    		func 		: getinstalIssuerList.bind(this) ,
	    		selType 	: "dropdown" ,
	    		setFirst 	: true ,
	    		defVal 		: instalIssuer.issuer ,
	    	}
		},
		getRedemOption( index , instalIssuer ){
			return {
	    		ID 			: "redem_"+index  ,
	    		func 		: [{
	    			val 	: "OPEN" ,
					text 	: "啟用"
	    		},
	    		{
	    			val 	: "CLOSED" ,
					text 	: "關閉"
	    		}] ,
	    		selType 	: "dropdown" ,
	    		setFirst 	: true ,
	    		defVal 		: instalIssuer.redem ,
	    	}
		},
		addIssuer(){
			let issuer = "" ;
			if( !this.selIssuer ) {
				
				if( this.insSettingList.length > 0 ) {
					this.showModel({
		                title : "信用卡分期" ,
		                type  : "error" ,
		                message : "信用卡分期選項-發卡行不可重複",
		            });
		            return ;
				}

				issuer = this.acqBank ;
			}
			this.insSettingList.push({
    			issuer 	: issuer ,
    			periods : "" ,
    			redem  	: "CLOSED" ,
    		});
		},
		minusIssuer(){
			this.insSettingList.pop();
		}
	}
}	
</script>