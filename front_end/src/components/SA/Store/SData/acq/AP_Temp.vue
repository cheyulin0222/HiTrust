<template>
	<!--<CardItem colMd="12" class="t-18">
		風險代碼
	</CardItem>
	<template v-for =" ( obj , index ) in risk ">
		<CardItem colMd="3">
			<SelectView :options="selOption[obj.key]" :invalidTxt="invalids[obj.key]" :LabelVal = "obj.value" @updateVal="updateVal" :ref = "obj.key" :readOnly = "readOnly" />
		</CardItem>
	</template>
	<CardItem colMd="12" class="mt-3"/>
	-->
	<CardItem colMd="3">
		<Input ID = "acqMerId" LabelVal = "銀行商家代碼" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" maxlength='14' :required="required"/>
	</CardItem>
	<CardItem colMd="3">
		<Input ID = "serialNumber" LabelVal = "統編" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" maxlength='10' :required="required"/>
	</CardItem>
	<CardItem colMd="3">
		<Input ID = "trxDomainName" LabelVal = "導回商家網址" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" maxlength='100' />
	</CardItem>
	<CardItem colMd="3">
		<Input ID = "barcode2MerId" LabelVal = "Barcode2MerId" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" maxlength='7' :required="true"/>
	</CardItem>
	<CardItem colMd="3">
		<SelectView :options="selOption.apMerBank" :invalidTxt="invalids.apMerBank" LabelVal = "銀行別" v-model="compObj['apMerBank']" ref = "apMerBank" :readOnly = "readOnlyEdit" />
	</CardItem>
	<CardItem colMd="3">
		<SelectView :options="selOption.trxType" :invalidTxt="invalids.trxType" LabelVal = "代收類型" @updateVal="updateVal" ref = "trxType" :readOnly = "readOnlyEdit" />
	</CardItem>
	<CardItem colMd="6"/>
	<template v-if= " compObj['apMerBank'] == 'SPB' " >
		<CardItem colMd="3">
			<Input ID = "spbXKey" LabelVal = "永豐X-KEY" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength='100' :required = "true"/>
		</CardItem>
		<CardItem colMd="3">
			<Input ID = "spbValidDate" LabelVal = "永豐X-KEY到期日(yyyy-MM-dd)" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength='10' :required = "true"/>
		</CardItem>
		<CardItem colMd="3">
			<Input ID = "spbHashA1" LabelVal = "永豐HASH_A1" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" maxlength='100' :required = "true"/>
		</CardItem>
		<CardItem colMd="3">
			<Input ID = "spbHashA2" LabelVal = "永豐HASH_A2" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" maxlength='100' :required = "true"/>
		</CardItem>
		<CardItem colMd="3">
			<Input ID = "spbHashB1" LabelVal = "永豐HASH_B1" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" maxlength='100' :required = "true"/>
		</CardItem>
		<CardItem colMd="3">
			<Input ID = "spbHashB2" LabelVal = "永豐HASH_B2" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit" maxlength='100' :required = "true"/>
		</CardItem>
		<CardItem colMd="6"/>
	</template>
	
	<CardItem colMd="3">
		<SelectView :options="selOption.cvsFee" :invalidTxt="invalids.cvsFee" LabelVal = "新光超商手續費" @updateVal="updateVal" ref = "cvsFee" :readOnly = "readOnlyEdit" />
	</CardItem>
	<CardItem colMd="3">
		<SelectView :options="selOption.cvsLimit" :invalidTxt="invalids.cvsLimit" LabelVal = "新光超商/永豐交易金額限制" @updateVal="updateVal" ref = "cvsLimit" :readOnly = "readOnlyEdit" />
	</CardItem>
	<CardItem colMd="3">
		<SelectView :options="selOption.vaCheck" :invalidTxt="invalids.vaCheck" LabelVal = "新光/台新/永豐虛擬帳號檢核設定" @updateVal="updateVal" ref = "vaCheck" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="3">
		<Input ID = "branch" LabelVal = "新光超商分行" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength='10' />
	</CardItem>
	<div class="order-md-0 col-md-12"/>
</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase"
import { getOpenOffList , getVaCheckList , getCvsLimitList , getApMerBankList , getApTrxType } from "@/utils/dataListSA";
import { isNotBlank , isEmpty } from "@/utils/util";
import  moment from 'moment'

export default {

	data(){
		let _this = this ;
		let compObj = this.editCopyObj() ;
		let risk = [{
			key 	: "riskAE",
			value 	: "AE-3D"
		},
		{
			key 	: "riskVisa",
			value 	: "Visa-3D"
		},
		{
			key 	: "riskMaster",
			value 	: "MasterCard-3D"
		},
		{
			key 	: "riskJcb",
			value 	: "JCB-3D"
		},
		{
			key 	: "riskCvc",
			value 	: "CVC2"
		}];
		let selOption = {} ;

		this.setSelOption( selOption , risk , compObj );

		selOption["apMerBank"] = {
			ID 			: "apMerBank" ,
    		func 		: getApMerBankList ,
    		selType 	: "dropdown" ,
    		//filter 		: true ,
    		defVal 		: compObj.apMerBank ? compObj.apMerBank : "SKB" ,
		}

		selOption["trxType"] = {
			ID 			: "trxType" ,
    		func 		: getApTrxType ,
    		selType 	: "dropdown" ,
    		//filter 		: true ,
    		defVal 		: compObj.trxType ? compObj.trxType : "SKB"  ,
		}
		
		selOption["cvsFee"] = {
			ID 			: "cvsFee" ,
    		func 		: [{
    			val 	: "MERCHANT" ,
				text 	: "商店吸收"
    		},
    		{
    			val 	: "CONSUMER1" ,
				text 	: "繳款人吸收"
    		}] ,
    		selType 	: "dropdown" ,
    		//filter 		: true ,
    		defVal 		: compObj.cvsFee ? compObj.cvsFee : "MERCHANT"  ,
		}

		selOption["cvsLimit"] = {
			ID 			: "cvsLimit" ,
    		func 		: getCvsLimitList ,
    		selType 	: "dropdown" ,
    		//filter 		: true ,
    		defVal 		: compObj.cvsLimit ? compObj.cvsLimit : "TYPE"  ,
		}
		
		selOption["vaCheck"] = {
			ID 			: "vaCheck" ,
    		func 		: getVaCheckList ,
    		selType 	: "dropdown" ,
    		//filter 		: true ,
    		defVal 		: compObj.vaCheck ? compObj.vaCheck : "TYPE"  ,
		}

		return {
			compObj 	: compObj ,
			risk 		: risk ,
			selOption 	: selOption ,
		}
	},
	extends : Base ,
	components : {

	},
	mounted() {

    },
	methods: {
		updateVal( val , ID ){
			this.compObj[ID] = val ;
		},
		defaultCompObj(){

			return Object.assign(this.defaultComp2() , {
				acqMerId 		: "" ,
				serialNumber 	: "" ,
				apMerBank 		: "SKB" ,
				trxDomainName 	: "" ,
				barcode2MerId 	: "" ,
				trxType 		: "SKB" ,
				cvsFee 			: "MERCHANT" ,
				cvsLimit 		: "TYPE" ,
				vaCheck 		: "TYPE1" ,
				branch 			: "" ,
				spbXKey 		: "" ,
				spbValidDate 	: "" ,
				spbHashA1 		: "" ,
				spbHashA2 		: "" ,
				spbHashB1		: "" ,
				spbHashB2 		: "" ,
			});
		},
		checkData(){
			let compObj = this.compObj ;
			let invalids = {} ;
			
			let serialNumber = compObj["serialNumber"].trim();

			if( !this.readOnlyEdit ) {
				let acqMerId =  compObj["acqMerId"].trim();
				if ( !isNotBlank( acqMerId )){
					invalids["acqMerId"] = "請輸入銀行商家代碼" ;
				} else {
					compObj["acqMerId"] = acqMerId ;
				}

				let barcode2MerId =  compObj["barcode2MerId"].trim();
				if ( !isNotBlank( acqMerId )){
					invalids["barcode2MerId"] = "請輸入Barcode2MerId" ;
				} else {
					compObj["barcode2MerId"] = barcode2MerId ;
				}
			}
			
			if ( !isNotBlank( serialNumber )){
				invalids["serialNumber"] = "請輸入統編" ;
			} else {
				compObj["serialNumber"] = serialNumber ;
			}

			if( compObj['apMerBank'] == 'SPB' ) {
				let spbXKey 		= compObj['spbXKey'].trim();
				let spbValidDate 	= compObj['spbValidDate'].trim();
				let spbHashA1 		= compObj['spbHashA1'].trim();
				let spbHashA2 		= compObj['spbHashA2'].trim();
				let spbHashB1		= compObj['spbHashB1'].trim();
				let spbHashB2 		= compObj['spbHashB2'].trim();
				
				if ( !isNotBlank( spbXKey )){
					invalids["spbXKey"] = "請輸入永豐X-KEY" ;
				} else {
					compObj["spbXKey"] = spbXKey ;
				}

				if ( !isNotBlank( spbValidDate )){
					invalids["spbValidDate"] = "請輸入永豐X-KEY到期日" ;
				} else {
					let formatData = moment(spbValidDate).format("YYYY-MM-DD")
					if ( formatData == 'Invalid date' ) {
						invalids["spbValidDate"] = "永豐X-KEY到期日格式錯誤" ;
					} else {
						compObj["spbValidDate"] = formatData ;
					}
				}

				if( !this.readOnlyEdit ) {
					if ( !isNotBlank( spbHashA1 )){
						invalids["spbHashA1"] = "請輸入永豐HASH_A1" ;
					} else {
						compObj["spbHashA1"] = spbHashA1 ;
					}

					if ( !isNotBlank( spbHashA2 )){
						invalids["spbHashA2"] = "請輸入永豐HASH_A2" ;
					} else {
						compObj["spbHashA2"] = spbHashA2 ;
					}

					if ( !isNotBlank( spbHashB1 )){
						invalids["spbHashB1"] = "請輸入永豐HASH_B1" ;
					} else {
						compObj["spbHashB1"] = spbHashB1 ;
					}

					if ( !isNotBlank( spbHashB2 )){
						invalids["spbHashB2"] = "請輸入永豐HASH_B2" ;
					} else {
						compObj["spbHashB2"] = spbHashB2 ;
					}
				}
			}
			
			this.invalids = invalids ;
			
			return isEmpty(invalids);
		},
		setSelOption( obj , arr , compObj ){

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
		}
	}
}	
</script>