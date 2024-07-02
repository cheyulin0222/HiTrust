<template>
	<template v-if=" acqBank && acqBank != '' ">
		<component :is="getAcqTemplate" ref = "acqTemplate" :type = "type" :inputObj = "inputObj" :acqBank = "acqBank" />
	</template>
	<template v-if="isNoApUpop">
		<CardItem colMd="3">
			<Input ID = "acqMerId" LabelVal = "銀行商家代碼" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" :maxlength=" acqBank === 'EZW' ? '16' : '15'" :required="checkAcqMerId"/>
		</CardItem>
		<CardItem colMd="3">
			<Input ID = "acqTerminalId" LabelVal = "銀行終端機代碼" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" :required="checkAcqTerminalId" maxlength="8"/>
		</CardItem>		
	</template>
	<template v-else-if="isUpop">
		<CardItem colMd="3">
			<Input ID = "acqUirerId" LabelVal = "收單行代碼" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" maxlength='11' :required="true"/>
		</CardItem>
		<CardItem colMd="3">
			<Input ID = "acqMerId" LabelVal = "銀行商家代碼" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" maxlength='15' :required="true"/>
		</CardItem>
		<CardItem colMd="3">
			<Input ID = "mccCode" LabelVal = "MCC Code" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" maxlength='4' :required="true"/>
		</CardItem>
	</template>

	<CardItem colMd="3">
			<Input ID = "mcc" LabelVal = "商戶類別代碼(MCC)" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="5"/>
		</CardItem>
	<CardItem colMd="12" class="mt-3"/>

	<CardItem colMd="6">
		<SelectView :options="selOption.currency" :invalidTxt="invalids.currency" LabelVal = "貨幣名稱" @updateVal="updateVal" ref = "currency" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="6">
		<SelectView :options="selOption.merCountry" :invalidTxt="invalids.merCountry" LabelVal = "商家國別" @updateVal="updateVal" ref = "merCountry" :readOnly = "readOnly" />
	</CardItem>
	<CardItem v-if="isNoApUpop" colMd="6">
		<SelectView :options="selOption.noticeType" :invalidTxt="invalids.noticeType" LabelVal = "交易完成通知類型" @updateVal="updateVal" ref = "noticeType" :readOnly = "readOnly" />
	</CardItem>
	

	<CardItem colMd="12" class="mt-3"/>
	<template v-if="isNoAp">
		<CardItem colMd="3">
			<SelectView :options="selOption.ftpBatchFlag" :invalidTxt="invalids.ftpBatchFlag" LabelVal = "檔案批次處理" @updateVal="updateVal" ref = "ftpBatchFlag" :readOnly = "readOnly" />
		</CardItem>
		<CardItem colMd="3">
			<SelectView :options="selOption.settleHour" :invalidTxt="invalids.settleHour" LabelVal = "清算時間" @updateVal="updateVal" ref = "settleHour" :readOnly = "readOnly" />
		</CardItem>
	</template>
	<CardItem colMd="3">
		<SelectView :options="statusOption" :invalidTxt="invalids.status" LabelVal = "狀態" @updateVal="updateVal" ref = "status" :readOnly = "readOnly" />
	</CardItem>
	
</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase";
import { defineComponent, defineAsyncComponent } from "vue";
import { getCurrencyList , getNoticeTypeList , getSCountryList , getIntList , getOpenOffList , getStoreStatusList , getStoreApStatusList } from "@/utils/dataListSA";
import { isEmpty , parseDouble , parseInt , isNotBlank } from "@/utils/util";

export default {

	data(){
		let _this = this ;
		let compObj = this.editCopyObj() ;
		let star = 0 ;
		let end = this.acqBank === "HSBC" ? 24 : 23 ;
		let acqBank = this.acqBank ;

	    let currency = {
	    	ID 			: "currency" ,
    		func 		: getCurrencyList.bind(this) ,
    		selType 	: "dropdown" ,
    		//filter 		: true ,
    		defVal 		: compObj.currency ? compObj.currency : "TWD"  ,
    	}

    	let merCountry = {
    		ID 			: "merCountry" ,
    		func 		: getSCountryList.bind(this) ,
    		selType 	: "dropdown" ,
    		//filter 		: true ,
    		defVal 		: compObj.merCountry ? compObj.merCountry : "TWN"  ,
    	}

    	if( acqBank == "AP" ) {
    		currency["func"] = [{
				val 	: "TWD" ,
				text 	: "TWD-台幣"
			}]

			merCountry["func"] = [{
				val 	: "TWN" ,
				text 	: "TWN-台灣"
			}]
    	}

		return {
			selOption : {
				currency : currency ,
		    	merCountry : merCountry ,
		    	noticeType : {
		    		ID 			: "noticeType" ,
		    		func 		: getNoticeTypeList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
		    		defVal 		: compObj.noticeType ? compObj.noticeType : "NO_NOTICE"  ,
		    	},
		    	settleHour : {
		    		ID 			: "settleHour" ,
		    		func 		: getIntList.bind(this,star,end) ,
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
		    		defVal 		: compObj.settleHour ? compObj.settleHour : "0"  ,
		    	},
		    	ftpBatchFlag : {
		    		ID 			: "ftpBatchFlag" ,
		    		func 		: getOpenOffList ,
		    		selType 	: "dropdown" ,
		    		defVal 		: compObj.ftpBatchFlag ? compObj.ftpBatchFlag : "CLOSED"  ,
		    	},
			},
			compObj 	: compObj ,
			isUpop 		: this.isUpopFun(),
			isNoApUpop 	: this.isNoApUpopFun(),
	    	isNoAp 		: this.isNoApFun(),
		}
	},
	computed: {
		getAcqTemplate() {
	    	let acqBank = this.acqBank ;
	    	return defineAsyncComponent(() => {
	    		if( acqBank != "" ) {
	    			return import(`@/components/SA/Store/SData/acq/${acqBank}_Temp.vue`)
	    		} else {
	    			return null ;
	    		}
    			
	    	})
	    },
	    checkAcqMerId(){	   
	    	return ["CFC","JKO","LINE_PAY","HITRUST"].indexOf(this.acqBank) == -1
	    },
	    checkAcqTerminalId(){
	    	return this.checkAcqMerId && ["DBS"].indexOf(this.acqBank) == -1 
	    },
	    statusOption(){

	    	let compObj = this.compObj ;

    		return {
	    		ID 			: "status" ,
	    		func 		: getStoreStatusList ,
	    		selType 	: "dropdown" ,
	    		defVal 		: compObj.status ? compObj.status : "A"  ,
	    	}
	    	
	    },
	},
 	props: {
 		acqBank : {
			type : String ,
		},
    },
    extends : Base ,
	components : {

	},
	mounted () {

	},
	methods: {
		getData(){
			let obj2 = this.$refs.acqTemplate.getData();
			if( this.checkData() && obj2 ) {
				let obj = JSON.parse(JSON.stringify(this.compObj));
				obj.settleHour = parseInt(obj.settleHour);
				let result = Object.assign({},obj,obj2) ;
				
				return result ;
			} else {
				return false ;
			}
		},
		defaultCompObj(){

			let acqBank = this.acqBank ;
			let obj = {
				currency 	: "TWD" ,
				merCountry 	: "TWN" ,
				noticeType 	: "NO_NOTICE" ,
				mcc 		: "" ,
				ftpBatchFlag: "CLOSED" ,
				settleHour 	: "0" ,
				status 		: "ACTIVE" ,
				acqMerId 	: "" ,
				mccCode 	: "" ,
			}

			if( this.isNoApUpopFun() ) {
				if( acqBank === "AFTEE" ) {
					obj["acqTerminalId"] = "PXLpuI4dqA" ;
				} else if( acqBank === "CUB") {
					obj["acqTerminalId"] = "T000001" ;
				} else if( acqBank === "EVONET") {
					obj["acqTerminalId"] = "88888888" ;
				} else {
					obj["acqTerminalId"] = "" ;
				}
				
			} else if( this.isUpopFun() ) {
				if ( acqBank === "TSBUPOP" ) {
					obj["acqUirerId"] = "26010158" ;
				} else {
					obj["acqUirerId"] = "" ;
				}
				
			}

			return obj
		},
		checkData(){
			let invalids = {} ;
			let compObj = this.compObj ;
	
			if( !this.readOnlyEdit ) {
				if( this.checkAcqMerId && this.acqBank != "AP" ) {
					let acqMerId =  compObj["acqMerId"].trim();
					if ( !isNotBlank( acqMerId )){
						invalids["acqMerId"] = "請輸入銀行商家代碼" ;
					} else {
						compObj["acqMerId"] = acqMerId ;
					}
				}

				if( this.isNoApUpop && this.checkAcqTerminalId ) {
					let acqTerminalId =  compObj["acqTerminalId"].trim();
					if( !isNotBlank( acqTerminalId )){
						invalids["acqTerminalId"] = "請輸入銀行終端機代碼" ;
					} else {
						compObj["acqTerminalId"] = acqTerminalId ;
					}
				} else if( this.isUpop ) {
					let acqUirerId =  compObj["acqUirerId"].trim();
					if( !isNotBlank( acqUirerId )){
						invalids["acqUirerId"] = "請輸入收單行代碼" ;
					} else {
						compObj["acqUirerId"] = acqUirerId ;
					}

					let mccCode =  compObj["mccCode"].trim();
					if( !isNotBlank( mccCode )){
						invalids["mccCode"] = "請輸入MCC Code" ;
					} else {
						compObj["mccCode"] = mccCode ;
					}
				}
			}
			
			this.invalids = invalids ;
			return isEmpty(invalids);
		},
		updateVal( val , ID ){
			this.compObj[ID] = val ;
		},
		isNoApUpopFun(){
	    	let acqBank = this.acqBank ;
	    	return acqBank && acqBank != "AP" && !acqBank.endsWith("UPOP") ;
	    },
	    isUpopFun(){
	    	let acqBank = this.acqBank ;
	    	return acqBank && acqBank.endsWith("UPOP") ;
	    },
	    isNoApFun(){
	    	let acqBank = this.acqBank ;
	    	return acqBank && acqBank != "AP" ;
	    },
		
	}
}	
</script>
