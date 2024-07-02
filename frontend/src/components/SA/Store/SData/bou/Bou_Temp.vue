<template>
	<template v-if=" showComp ">
		<ComTemp ref = "bouTemplate" :type = "type" :inputObj = "inputObj" :acqBank="acqBank"/>
	</template>
	<CardItem colMd="12" class="t-18">
		<hr class="hr-store" v-if=" showComp ">
		電子發票
	</CardItem>
	<CardItem colMd="6">
		<SelectView :options="selOption.einvoice" :invalidTxt="invalids.einvoice" LabelVal = "服務啟用" @updateVal="updateVal" ref = "einvoice" :readOnly = "readOnly" />
	</CardItem>
	<template v-if=" compObj['einvoice'] === 'OPEN' ">
		<CardItem colMd="6">
			<Input ID = "renterId" LabelVal = "租賃者之統一編號" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
		</CardItem>
		<CardItem colMd="6">
			<Input ID = "cetustekAcnt" LabelVal = "鯨躍帳號" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
		</CardItem>
		<CardItem colMd="6">
			<Input ID = "cetustekPwd" LabelVal = "鯨躍密碼" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
		</CardItem>
		<CardItem colMd="6">
			<SelectView :options="selOption.taxIn" :invalidTxt="invalids.taxIn" LabelVal = "明細總價是否含稅" @updateVal="updateVal" ref = "taxIn" :readOnly = "readOnly" />
		</CardItem>
		<CardItem colMd="6">
			<SelectView :options="selOption.invoiceType" :invalidTxt="invalids.invoiceType" LabelVal = "發票種類" @updateVal="updateVal" ref = "invoiceType" :readOnly = "readOnly" />
		</CardItem>
		<CardItem colMd="6">
			<SelectView :options="selOption.taxType" :invalidTxt="invalids.taxType" LabelVal = "稅別" @updateVal="updateVal" ref = "taxType" :readOnly = "readOnly"/>
		</CardItem>
		<CardItem colMd="6">
			<Input ID = "taxRate" LabelVal = "稅率(稅別4必填，否則可為空，例0.05。)" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
		</CardItem>
		<CardItem colMd="6">
			<Input ID = "productType" LabelVal = "販售商品種類名稱" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
		</CardItem>
		<CardItem colMd="6">
			<SelectView :options="selOption.autoInvoice" :invalidTxt="invalids.autoInvoice" LabelVal = "是否自動開票" @updateVal="updateVal" ref = "autoInvoice" :readOnly = "readOnly" />
		</CardItem>
		<CardItem colMd="6">
			<Input ID = "npoban" LabelVal = "捐贈機構" placeholder = "輸入可被選擇的社福機構愛心碼，以,分隔" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
		</CardItem>
	</template>
	<CardItem colMd="6"/>
	
	
</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase";
import ComTemp from "@/components/SA/Store/SData/bou/COM_Temp.vue";
import { defineComponent, defineAsyncComponent } from "vue";
import { getOpenOffList , getTaxInList , getInvoiceTypeList , getTaxTypeList , getYesNoList , getinstalIssuerList , getEasyTemplateList } from "@/utils/dataListSA";
import { isEmpty } from "@/utils/util";

export default {

	data(){
		let _this = this ;
		let compObj = this.editCopyObj() ;
		return {
			selOption 	: {
				einvoice : {
		    		ID 			: "einvoice" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.einvoice ? compObj.einvoice : "CLOSED"  ,
		    	},
				taxIn : {
		    		ID 			: "taxIn" ,
		    		func 		: getTaxInList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.taxIn ? compObj.taxIn : "INCLUDED_TAX"  ,
		    	},
		    	invoiceType : {
		    		ID 			: "invoiceType" ,
		    		func 		: getInvoiceTypeList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.invoiceType ? compObj.invoiceType : "GENERAL_TAX"  ,
		    	},
		    	taxType : {
		    		ID 			: "taxType" ,
		    		func 		: getTaxTypeList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.taxType ? compObj.taxType : "TAXABLE"  ,
		    	},
		    	autoInvoice : {
		    		ID 			: "autoInvoice" ,
		    		func 		: getYesNoList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.autoInvoice ? compObj.autoInvoice : "YES"  ,
		    	},
			},
			compObj : compObj ,
		}
	},
	computed: {
		/*getAcqTemplate() {
	    	let acqBank = this.acqBank ;
	    	return defineAsyncComponent(() => {
	    		if( acqBank === "AP" || acqBank.endsWith("UPOP") ) {
	    			return import(`@/components/SA/Store/SData/bou/AP_Temp.vue`)
	    		} else {
	    			return import(`@/components/SA/Store/SData/bou/COM_Temp.vue`)
	    		}    			
	    	})
	    }*/
	    showComp(){
	    	let acqBank = this.acqBank ;
	    	return acqBank && acqBank != "AP" && !acqBank.endsWith("UPOP")
	    }
	},
 	props: {
 		acqBank : {
			type : String ,
		},
    },
    extends : Base ,
	components : {
		ComTemp
	},
	methods: {
		getData(){
			let obj2 = {} ;
			if( this.showComp ) {
				obj2 = this.$refs.bouTemplate.getData();
			}

			if( this.checkData() && obj2 ) {
				let obj = JSON.parse(JSON.stringify(this.compObj));		
				return Object.assign({},obj,obj2) ;
			} else {
				return false ;
			}
		},
		defaultCompObj(){
			return {
				einvoice 		: "CLOSED" ,
				renterId 		: "" ,
				cetustekAcnt 	: "" ,
				cetustekPwd 	: "" ,
				taxIn 			: "INCLUDED_TAX" ,
				invoiceType 	: "GENERAL_TAX" ,
				taxType 		: "TAXABLE" ,
				taxRate 		: "" ,
				productType 	: "" ,
				autoInvoice 	: "YES" ,
				npoban 			: "" ,
			}			
		},
		checkData(){
			let invalids = {} ;
			let compObj = this.compObj ;
			let taxType =  compObj["taxType"];
			let taxRate =  compObj["taxRate"];
			let einvoice =  compObj["einvoice"];
			
			if( einvoice === "OPEN" ) {
				if( taxType === "TAXABLE_SPECIAL" && taxRate === "" ) {
					invalids["taxRate"] = "請輸入稅率" ;
				}
			} 
			
			this.invalids = invalids ;
			return isEmpty(invalids);
		},
		updateVal( val , ID ){
			this.compObj[ID] = val ;
		},
	}
}	
</script>
