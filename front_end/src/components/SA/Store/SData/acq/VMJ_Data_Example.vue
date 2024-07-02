<template>
	<CardItem colMd="4">
		<SelectView :options="selOption['riskVisa']" :invalidTxt="invalids['riskVisa']" LabelVal = "Visa-3D" v-model = "compObj.riskVisa"  ref = "riskVisa" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="8"/>
	<template v-if= " showVMJ " >
		<CardItem colMd="4" v-show = " compObj.riskVisa === 'OPEN' ">
			<Input ID = "merVisaId" LabelVal = "商家VISA代碼" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required = "true"/>
		</CardItem>
		<CardItem colMd="4" v-show = " compObj.riskVisa === 'OPEN' ">
			<Input ID = "merVisaPWD" LabelVal = "商家VISA PWD" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required = "true"/>
		</CardItem>
		<CardItem colMd="4" v-show = " compObj.riskVisa === 'OPEN' ">
			<Input ID = "merVisaName" LabelVal = "商家VISA名稱" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required = "true"/>
		</CardItem>
	</template>
	
	<CardItem colMd="4">
		<SelectView :options="selOption['riskMaster']" :invalidTxt="invalids['riskMaster']" LabelVal = "MasterCard-3D" v-model = "compObj.riskMaster"  ref = "riskMaster" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="8"/>
	<template v-if= " showVMJ " >
		<CardItem colMd="4" v-show = " compObj.riskMaster === 'OPEN' ">
			<Input ID = "merMasterId" LabelVal = "商家MasterCard代碼" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required = "true"/>
		</CardItem>
		<CardItem colMd="4" v-show = " compObj.riskMaster === 'OPEN' ">
			<Input ID = "merMasterPWD" LabelVal = "商家MasterCard PWD" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required = "true"/>
		</CardItem>
		<CardItem colMd="4" v-show = " compObj.riskMaster === 'OPEN' ">
			<Input ID = "merMasterName" LabelVal = "商家MasterCard名稱" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required = "true"/>
		</CardItem>
	</template>
	
	<CardItem colMd="4">
		<SelectView :options="selOption['riskJcb']" :invalidTxt="invalids['riskJcb']" LabelVal = "JCB-3D" v-model = "compObj.riskJcb"  ref = "riskJcb" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="8"/>
	<template v-if= " showVMJ " >
		<CardItem colMd="4" v-show = " compObj.riskJcb === 'OPEN' ">
			<Input ID = "merJcbId" LabelVal = "商家JCB代碼" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required = "true"/>
		</CardItem>
		<CardItem colMd="4" v-show = " compObj.riskJcb === 'OPEN' ">
			<Input ID = "merJcbPWD" LabelVal = "商家JCB PWD" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required = "true"/>
		</CardItem>
		<CardItem colMd="4" v-show = " compObj.riskJcb === 'OPEN' ">
			<Input ID = "merJcbName" LabelVal = "商家JCB名稱" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required = "true"/>
		</CardItem>
	</template>
	
	<CardItem colMd="4">
		<SelectView :options="selOption['riskAE']" :invalidTxt="invalids['riskAE']" LabelVal = "AE-3D" v-model = "compObj.riskAE"  ref = "riskAE" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="8"/>
	<template v-if= " showVMJ " >
		<CardItem colMd="4" v-show = " compObj.riskAE === 'OPEN' ">
			<Input ID = "merAeId" LabelVal = "商家AE代碼" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required = "true"/>
		</CardItem>
		<CardItem colMd="4" v-show = " compObj.riskAE === 'OPEN' ">
			<Input ID = "merAePWD" LabelVal = "商家AE PWD" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required = "true"/>
		</CardItem>
		<CardItem colMd="4" v-show = " compObj.riskAE === 'OPEN' ">
			<Input ID = "merAeName" LabelVal = "商家AE名稱" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required = "true"/>
		</CardItem>
	</template>

	<CardItem colMd="4">
		<SelectView :options="selOption['riskCvc']" :invalidTxt="invalids['riskCvc']" LabelVal = "CVC2" v-model = "compObj.riskCvc"  ref = "riskCvc" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="12" class="t-18">
		<hr class="hr-store">
		回傳卡號
	</CardItem>
	<CardItem colMd="3">
		<SelectView :options="selOption['rtnPan']" :invalidTxt="invalids['rtnPan']" LabelVal = "是否回傳" v-model = "compObj.rtnPan" ref = "rtnPan" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="3">
		<SelectView :options="selOption['panTemp']" :invalidTxt="invalids['panTemp']" LabelVal = "回傳樣式" v-model = "compObj.panTemp" ref = "panTemp" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="12" class="mt-3"/>
</template>
<script>
import Base from "@/components/SA/Store/SData/Base";
import { isEmpty , resetDefaultVal } from "@/utils/util";
import { getOpenOffList , getPanTempList } from "@/utils/dataListSA";

export default {

	data(){
		let compObj = this.compObj ;
		return {
			selOption : {
				riskAE : {
					ID 			: "riskAE" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.riskAE ? compObj.riskAE : "CLOSED" ,
				},
				riskVisa : {
					ID 			: "riskVisa" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.riskVisa ? compObj.riskVisa : "CLOSED" ,
				},
				riskMaster : {
					ID 			: "riskMaster" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.riskMaster ? compObj.riskMaster : "CLOSED" ,
				},
				riskJcb : {
					ID 			: "riskJcb" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.riskJcb ? compObj.riskJcb : "CLOSED" ,
				},
				riskCvc : {
					ID 			: "riskCvc" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj.riskCvc ? compObj.riskCvc : "CLOSED" ,
				},
				rtnPan : {
					ID 			: "rtnPan" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj["rtnPan"] ? compObj["rtnPan"] : "CLOSED"  ,
				},
				panTemp : {
					ID 			: "panTemp" ,
		    		func 		: getPanTempList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj["panTemp"] ? compObj["panTemp"] : ""  ,
				}
			}
		}
	},
	extends : Base ,
	props : {
		compObj : {
			type : Object ,
		},
		acqBank : {
			type : String ,
		},
	},
	computed: {
		showVMJ(){
			return [ "ESB" , "CUB" , "TFB" , "NCCC" ].indexOf(this.acqBank) == -1  ;
		}
	},
	components : {

	},
	mounted() {

    },
	methods: {
		checkData (){

			let compObj = JSON.parse(JSON.stringify(this.compObj)) ;
			let invalids = {} ;

			let riskVisa = compObj["riskVisa"] ;
			let riskMaster = compObj["riskMaster"] ;
			let riskJcb = compObj["riskJcb"] ;
			let riskAE = compObj["riskAE"] ;
			let checkArr = [] ;
			
			if( riskVisa === "OPEN" && this.showVMJ ) {
				checkArr.push({
					key 	: "merVisaId" ,
					value 	: "商家Visa代碼" 
				},
				{
					key 	: "merVisaPWD" ,
					value 	: "商家Visa PWD" 
				},
				{
					key 	: "merVisaName" ,
					value 	: "商家Visa名稱" 
				});
			} else {
				compObj["merVisaId"] = "" ;
				compObj["merVisaPWD"] = "" ;
				compObj["merVisaName"] = "" ;
			}

			if( riskMaster === "OPEN" && this.showVMJ ) {
				checkArr.push({
					key 	: "merMasterId" ,
					value 	: "商家MasterCard代碼" 
				},
				{
					key 	: "merMasterPWD" ,
					value 	: "商家MasterCard PWD" 
				},
				{
					key 	: "merMasterName" ,
					value 	: "商家MasterCard名稱" 
				});
			} else {
				compObj["merMasterId"] = "" ;
				compObj["merMasterPWD"] = "" ;
				compObj["merMasterName"] = "" ;
			}

			if( riskJcb === "OPEN" && this.showVMJ ) {
				checkArr.push({
					key 	: "merJcbId" ,
					value 	: "商家JCB代碼" 
				},
				{
					key 	: "merJcbPWD" ,
					value 	: "商家JCB PWD" 
				},
				{
					key 	: "merJcbName" ,
					value 	: "商家JCB名稱" 
				});
			} else {
				compObj["merJcbId"] = "" ;
				compObj["merJcbPWD"] = "" ;
				compObj["merJcbName"] = "" ;
			}

			if( riskAE === "OPEN" && this.showVMJ ) {
				checkArr.push({
					key 	: "merAeId" ,
					value 	: "商家AE代碼" 
				},
				{
					key 	: "merAePWD" ,
					value 	: "商家AE PWD" 
				},
				{
					key 	: "merAeName" ,
					value 	: "商家AE名稱" 
				});
			} else {
				compObj["merAeId"] = "" ;
				compObj["merAePWD"] = "" ;
				compObj["merAeName"] = "" ;
			}

			checkArr.forEach(function( obj ){
				let key = obj.key ;
				let value = compObj[key].trim();
				compObj[key] = value ;
				if( value === "" ) {
					invalids[key] = "請輸入"+ obj.value ;
				}
				//VISA NAME 超過20字元，會造成無法正常導向3d頁面(pares回錯誤代碼5)
				if( key === "merVisaName" && value.length > 20 ) {
					invalids[key] = obj.value + "不可超過20個字元！！" ;
				}
			})
			
			this.invalids = invalids ;
			if( isEmpty(invalids) ) {
				resetDefaultVal(this.compObj,compObj);
				return true ;
			} else {
				return false ;
			}
		},
		setInvalids( invalids ){
			this.invalids = invalids ;
		},
	}
}	
</script>