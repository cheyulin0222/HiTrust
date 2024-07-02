<template>
	<template v-for =" ( obj , index ) in other ">
		<CardItem colMd="3">
			<SelectView :options="selOption[obj.key]" :invalidTxt="invalids[obj.key]" :LabelVal = "obj.value" @updateVal="updateVal" :ref = "obj.key" :readOnly = "readOnly" />
		</CardItem>
	</template>
	<div class="order-0 col-md-12 "/>

	<template v-for =" ( obj , index ) in pay ">
		<CardItem colMd="3">
			<SelectView :options="selOption[obj.key]" :invalidTxt="invalids[obj.key]" :LabelVal = "obj.value" @updateVal="updateVal" :ref = "obj.key" :readOnly = "readOnly" />
		</CardItem>
	</template>
	<CardItem colMd="12" class="mt-3"/>

	<template v-if="acqBank === 'LINE_PAY' ">
		<CardItem colMd="12" class="t-18">
			<hr class="hr-store">
			LINE PAY
		</CardItem>
		<CardItem colMd="3">
			<SelectView :options="selOption['linePay']" :invalidTxt="invalids['linePay']" LabelVal = "啟用" @updateVal="updateVal" ref = "linePay" :readOnly = "true" />
		</CardItem>
		<CardItem colMd="3">
			<Input ID = "channelId" LabelVal = "Channel ID" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" :required="true"/>
		</CardItem>
		<CardItem colMd="3">
			<Input ID = "channelSerect" LabelVal = "CHANNEL_SECRET" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="true"/>
		</CardItem>
		<CardItem colMd="3">
			<SelectView :options="selOption['lineCap']" :invalidTxt="invalids['lineCap']" LabelVal = "請款模式" @updateVal="updateVal" ref = "lineCap" :readOnly = "readOnly" />
		</CardItem>
		<CardItem colMd="12" class="mt-3"/>
	</template>
</template>
<script>
import Base from "@/components/SA/Store/SData/Base";
import { isEmpty } from "@/utils/util";
import { getOpenOffList , getLineCapList } from "@/utils/dataListSA";

export default {

	data(){

		let selOption = {} ;

		let pay = [
		{
			key 	: "applePay",
			value 	: "ApplePay"
		},
		{
			key 	: "googlePay",
			value 	: "GooglePay"
		},
		{
			key 	: "samsungPay",
			value 	: "SamsungPay"
		}];	

		let other = [{
			key 	: "refund",
			value 	: "退款功能"
		},
		{
			key 	: "otp",
			value 	: "圖形驗証"
		},
		{
			key 	: "ots",
			value 	: "開啟OTS(one time session)"
		},
		{
			key 	: "rtu",
			value 	: "開啟RTU"
		},
		{
			key 	: "alert",
			value 	: "未請款提示(授權後15~60天)"
		}]	

		this.setSelOption( selOption , pay );
		this.setSelOption( selOption , other );

		let compObj = this.compObj ;

		if( this.acqBank === "LINE_PAY" ) {
			selOption["linePay"] = {
				ID 			: "linePay" ,
	    		func 		: getOpenOffList.bind(this) ,
	    		selType 	: "dropdown" ,
	    		//filter 		: true ,
	    		defVal 		: compObj["linePay"] ? compObj["linePay"] : "OPEN"  ,
			}

			selOption["lineCap"] = {
				ID 			: "lineCap" ,
	    		func 		: getLineCapList.bind(this) ,
	    		selType 	: "dropdown" ,
	    		//filter 		: true ,
	    		defVal 		: compObj["lineCap"] ? compObj["lineCap"] : "MERCHANT"  ,
			}
		}

		return {
			selOption 	: selOption ,
			pay 		: pay ,
			other 		: other ,
		}
	},
	extends : Base ,
	props : {
		compObj : {
			type : Object ,
		},
		acqBank : {
			type : String ,
		}		
	},
	computed: {
		
	},
	components : {

	},
	mounted() {

    },
	methods: {
		updateVal( val , ID ){
			this.compObj[ID] = val ;
		},
		setSelOption( obj , arr ){
			let compObj = this.compObj ;
			arr.forEach(function( o ){
				let key = o.key ;
				let defVal = compObj[key] ? compObj[key] : "CLOSED"  ;
				obj[key] = {
					ID 			: key ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: defVal
				}
			});
		},
		setInvalids( invalids ){
			this.invalids = invalids ;
		},
		checkData (){
			let invalids = {} ;
			let compObj = this.compObj ;
			if( this.acqBank === 'LINE_PAY' ) {
				if( !this.readOnlyEdit ) {
					if( compObj["channelId"] === "" ) {
						invalids["channelId"] = "請輸入Channel ID";
					}
				}
				
				if( compObj["channelSerect"] === "" ) {
					invalids["channelSerect"] = "請輸入CHANNEL_SECRET";
				}
			}
			this.invalids = invalids ;
			return isEmpty(invalids);
		}
	}
}	
</script>