import store from "@/store";
import { firstUpperCase } from "./util";

declare global {
    interface Window { 
        vue : any ,
    }
}

export function getRoleNameList ( addAll , callBack ){
	let option = {
		callBack : callBack ,
		api : "/role-options",
		resultFunc : function( data ){
			return {
				val 	: data.key ,
				text 	: data.key + '(' + data.value + ')'
			}
		}
	}

	if( addAll ) {
		option["addFirst"] = {
			text 	: "全部" ,
			val		: "" ,
		}
	}

	getUserList.call( this , option );
}

export function getCountryList ( callBack ){
	let option = {
		callBack : callBack ,
		api : "get-country-options"
	}

	getUserList.call( this , option );
}

export function getAcqBankList ( isSearch , callBack ){
	let option = {
		callBack : callBack ,
		api : "get-adapter-options"
	}

	if( isSearch ) {
		option["addFirst"] = {
			text 	: "請選擇銀行" ,
			val		: "" ,
		}
	}

	getUserList.call( this , option );

}

export function getTeAcqList ( isSearch , callBack ){
	let option = {
		callBack : callBack ,
		api : "get-acquirer-options"
	}

	if( isSearch ) {
		option["addFirst"] = {
			text 	: "請選擇銀行" ,
			val		: "" ,
		}
	}

	getUserList.call( this , option );

}



export function getExp10List ( callBack ) {
	 let option = {
		callBack : callBack ,
	}

	option["arr"] = [{
		key 	: "-2" ,
		value 	: "-2"  
	}]

	getUserList.call( this , option );
}

export function getReqSourceList ( callBack ){
	let option = {
		callBack : callBack ,
		api : "get-reqsource-options"
	}

	getUserList.call( this , option );

}

export function getShopifySipTypeList ( callBack ){
	let option = {
		callBack : callBack ,
		//api : ""
	}

	option["arr"] = [{
		key 	: "HiTRUST" ,
		value 	: "HiTRUST"  
	},
	{
		key 	: "AKOHUB" ,
		value 	: "AKOHUB"  
	}]

	getUserList.call( this , option );

}

export function getMerchantTypeList( setNull , callBack ){
	let option = {
		callBack : callBack ,
	}
	let result = [] ;
	if( setNull ) {
		result = [{
			key 	: "" ,
			value 	: "請選擇"  
		}]
	}
	

	option["arr"] = result.concat([
	{
		key 	: "TK" ,
		value 	: "TK"  
	},
	{
		key 	: "WI" ,
		value 	: "WI"  
	}]);
	getUserList.call( this , option );
}

export function getOpenOffList( callBack ){
	let option = {
		callBack : callBack ,
	}

	option["arr"] = [
	{
		key 	: "OPEN" ,
		value 	: "啟用"  
	},
	{
		key 	: "CLOSED" ,
		value 	: "關閉"  
	}]

	getUserList.call( this , option );
}

export function getPromoteList( callBack ){
	let option = {
		callBack : callBack ,
	}

	option["arr"] = [
	{
		key 	: "OPEN" ,
		value 	: "啟用"  
	},
	{
		key 	: "CLOSED" ,
		value 	: "關閉"  
	},
	{
		key 	: "DISABLED" ,
		value 	: "未啟用"  
	}]

	getUserList.call( this , option );
}

export function getOpenOffYNList( callBack ){
	let option = {
		callBack : callBack ,
	}

	option["arr"] = [
	{
		key 	: "YES" ,
		value 	: "啟用"  
	},
	{
		key 	: "NO" ,
		value 	: "關閉"  
	}]

	getUserList.call( this , option );
}

export function getStoreStatusList( callBack ){
	let option = {
		callBack : callBack ,
	}

	option["arr"] = [
	{
		key 	: "ACTIVE" ,
		value 	: "Active"  
	},
	{
		key 	: "LOCKED" ,
		value 	: "Locked"  
	},
	{
		key 	: "SETTLEMENT" ,
		value 	: "Settlement"  
	}]

	getUserList.call( this , option );
}

export function getStoreApStatusList( callBack ){
	let option = {
		callBack : callBack ,
	}

	option["arr"] = [
	{
		key 	: "02" ,
		value 	: "Enable"  
	},
	{
		key 	: "03" ,
		value 	: "Disable"  
	}]

	getUserList.call( this , option );
}

export function getTaxInList( callBack ){
	let option = {
		callBack : callBack ,
	}

	option["arr"] = [
	{
		key 	: "EXCLUSIVE_TAX" ,
		value 	: "未稅價"  
	},
	{
		key 	: "INCLUDED_TAX" ,
		value 	: "含稅價"  
	}]

	getUserList.call( this , option );
}

export function getInvoiceTypeList( callBack ){
	let option = {
		callBack : callBack ,
	}

	option["arr"] = [
	{
		key 	: "GENERAL_TAX" ,
		value 	: "一般稅額"  
	},
	{
		key 	: "SPECIAL_TAX" ,
		value 	: "特種稅額"  
	}]

	getUserList.call( this , option );
}

export function getTaxTypeList( callBack ){
	let option = {
		callBack 	: callBack ,
		api 		: "get-tax-type-options"
	}

	getUserList.call( this , option );
}

export function getYesNoList( callBack ){
	let option = {
		callBack : callBack ,
	}

	option["arr"] = [
	{
		key 	: "NO" ,
		value 	: "否"  
	},
	{
		key 	: "YES" ,
		value 	: "是"  
	}]

	getUserList.call( this , option );
}

export function getinstalIssuerList( callBack ){
	let option = {
		callBack : callBack ,
		api 	 : "get-issuer-options" ,
	}

	getUserList.call( this , option );
}

export function getEasyTemplateList( callBack ){
	let option = {
		callBack : callBack ,
		api 	 : "get-ezp-templet-options" ,
	}

	getUserList.call( this , option );
}

export function getRiskServiceFlagList( callBack ){
	let option = {
		callBack : callBack ,
	}

	option["arr"] = [
	{
		key 	: "LEARNING_MODE_OFF" ,
		value 	: "學習模式OFF"  
	},
	{
		key 	: "TRANSACTION_MODE" ,
		value 	: "交易模式"  
	},
	{
		key 	: "LEARNING_MODE_ON" ,
		value 	: "學習模式ON"  
	}]

	getUserList.call( this , option );
}

export function getCurrencyList( callBack ){
	let option = {
		callBack : callBack ,
		api 	 : "get-currency-options" ,
	}

	getUserList.call( this , option );
}

export function getSCountryList( callBack ){
	let option = {
		callBack : callBack ,
		api 	 : "get-merchant-country-options" ,
	}

	getUserList.call( this , option );
}

export function getNoticeTypeList( callBack ){
	let option = {
		callBack : callBack ,
	}

	option["arr"] = [
	{
		key 	: "NO_NOTICE" ,
		value 	: "無"  
	},
	{
		key 	: "MAIL" ,
		value 	: "信箱"  
	},
	{
		key 	: "SMS" ,
		value 	: "簡訊"  
	}]

	getUserList.call( this , option );
}

export function getVaCheckList( callBack ){
	let option = {
		callBack : callBack ,
		api : "/get-virtual-account-check-options"
	}

	getUserList.call( this , option );
}

export function getCvsLimitList( callBack ){
	let option = {
		callBack : callBack ,
		api : "/get-cvs-limit-options"
	}

	getUserList.call( this , option );
}

export function getApMerBankList( callBack ){
	let option = {
		callBack : callBack ,
		api : "/get-ap-merchant-bank-options"
	}

	getUserList.call( this , option );
}

export function getApTrxType( callBack ){
	let option = {
		callBack : callBack ,
		api : "/get-ap-trx-type-options"
	}

	getUserList.call( this , option );
}

export function getPanTempList( callBack ){
	let option = {
		callBack : callBack ,
		api : "/get-pan-temp-options"
	}

	getUserList.call( this , option );
}

export function getLineCapList( callBack ){
	let option = {
		callBack : callBack ,
	}

	option["arr"] = [
	{
		key 	: "AUTO" ,
		value 	: "自動請款"  
	},
	{
		key 	: "MANUAL" ,
		value 	: "手動請款"  
	},
	{
		key 	: "MERCHANT" ,
		value 	: "由商家帶入參數決定"  
	}]

	getUserList.call( this , option );
}

export function getMerGroupTypeList( callBack ){
	let option = {
		callBack : callBack ,
	}

	option["arr"] = [
	{
		key 	: "ONE" ,
		value 	: "單一商家"  
	},
	{
		key 	: "MULTIPLE" ,
		value 	: "群組商家"  
	},
	{
		key 	: "ALL" ,
		value 	: "全部商家"  
	}]

	getUserList.call( this , option );
}

export function getMGIDList( isSearch , callBack ){
	let option = {
		callBack : callBack ,
		api : "get-ap-mgids" ,
		resultFunc : function( data ){
			return {
				val 	: data ,
				text 	: data
			}
		}
	}

	if( isSearch ) {
		option["addFirst"] = {
			text 	: "使用人員E-mail查詢" ,
			val		: "" ,
		}
	}

	getUserList.call( this , option );
}

export function getIntList( start , end , callBack ){
	let option = {
		callBack : callBack ,
	}

	let arr = [] ;

	for(var i = start ; i<= end ; i++){
		let num = i.toString();
		arr.push({
			key 	: num ,
			value 	: num
		})
	}
	option["arr"] = arr ;

	getUserList.call( this , option );
}

export function getMerIDList( addAll , callBack ){
	let option = {
		callBack : callBack ,
		//api : ""
	}

	let result = [] ;

	if( addAll ) {
		result.push({
			key 	: "" ,
			value 	: "全部"  
		})
	}

	option["arr"] = result.concat([
	{
		key 	: "D0124" ,
		value 	: "D0124"  
	},
	{
		key 	: "10002" ,
		value 	: "10002"  
	},
	{
		key 	: "10003" ,
		value 	: "10003"  
	},
	{
		key 	: "88000" ,
		value 	: "88000"  
	},
	{
		key 	: "88001" ,
		value 	: "88001"  
	},
	{
		key 	: "88002" ,
		value 	: "88002"  
	}]);

	getUserList.call( this , option );
}

export function getTxtTypeList( addAll , callBack ){
	let option = {
		callBack : callBack ,
		//api : ""
	}

	let result = [] ;

	if( addAll ) {
		result.push({
			key 	: "" ,
			value 	: "全部"  
		})
	}

	option["arr"] = result.concat([
	{
		key 	: "UPDATE" ,
		value 	: "更新"  
	},
	{
		key 	: "CREATE" ,
		value 	: "新建"  
	},
	{
		key 	: "TRANS" ,
		value 	: "交易"  
	}]);

	getUserList.call( this , option );
}

export function getRsltList( addAll , callBack ){
	let option = {
		callBack : callBack ,
		//api : ""
	}

	let result = [] ;

	if( addAll ) {
		result.push({
			key 	: "" ,
			value 	: "全部"  
		})
	}

	option["arr"] = result.concat([
	{
		key 	: "FAIL99" ,
		value 	: "失敗(99)"  
	},
	{
		key 	: "FAIL98" ,
		value 	: "失敗(98)"  
	},
	{
		key 	: "SUCCESS" ,
		value 	: "成功"  
	}]);

	getUserList.call( this , option );
}

export function getRiskMerIDList( isSelect , callBack ){
	let option = {
		callBack : callBack ,
		api : "veri-id/get-stores"
	}

	if( isSelect ) {
		option["addFirst"] = {
			text 	: "請選擇商店ID" ,
			val		: "" ,
		}
	}

	getUserList.call( this , option );
}

export function getInstituteList( type , callBack ){
	let option = {
		callBack : callBack ,
		api : "veri-id/get-institute-ids"
	}

	if( type && type === "SEARCH" || type === "CREATE" ) {
		let addFirst = [] ;
		
		addFirst.push({
			text 	: "請選擇機構ID" ,
			val		: "" ,
		});
		
		if( type === "CREATE" ) {
			addFirst.push({
				val 	: "NEW" ,
				text 	: "新增機構ID"  
			})
		}

		option["addFirst"] = addFirst ;
	}

	getUserList.call( this , option );
}

export function getRiskStatusList( callBack ){
	let option = {
		callBack : callBack ,
		//api : ""
	}

	let result = [] ;

	option["arr"] = result.concat([
	{
		key 	: "REAA" ,
		value 	: "風險策略通過(REAA)"  
	},
	{
		key 	: "REAP" ,
		value 	: "策略待審查  (REAP)"  
	},
	{
		key 	: "REAR" ,
		value 	: "風險策略拒絕(REAR)"  
	}]);

	getUserList.call( this , option );
}

export function getCFCFreeTypeList( callBack ){
	let option = {
		callBack : callBack ,
		//api : ""
	}

	let result = [] ;

	option["arr"] = result.concat([
	{
		key 	: "MERCHANT" ,
		value 	: "商家"  
	},
	{
		key 	: "CONSUMER" ,
		value 	: "消費者"  
	}]);

	getUserList.call( this , option );
}

export function getFIDList ( callBack ){
	let option = {
		callBack : callBack ,
		api : "/get-function-id-options"
	}

	/*let result = [] ;

	option["arr"] = result.concat([
	{
		key 	: "SA1301" ,
		value 	: "SA1301(新增商店)"  
	},
	{
		key 	: "SA1302" ,
		value 	: "SA1302(商店維護)"  
	},
	{
		key 	: "SA1303" ,
		value 	: "SA1303(批次匯入商家)"  
	},
	{
		key 	: "SA1304" ,
		value 	: "SA1304(整批SSL授權測試)"  
	}
	]);*/

	getUserList.call( this , option );
}

export function getQrPayList ( search , callBack ){
	let option = {
		callBack : callBack ,
		api : "/get-qrpay-options",
	}

	if( search ) {
		option["addFirst"] = {
			text 	: "請選擇" ,
			val		: "" ,
		}
	}

	getUserList.call( this , option );
}


export function getUserList ( option ) {
	let name = option.name ? option.name : "" ;
	let api = option.api ;
	let resultFunc = option.resultFunc ;
	let callBack = option.callBack ;
	let addFirst = option.addFirst ;
	let storeGetId = "user/get"+firstUpperCase(name);
	let storeSetId = "user/set"+firstUpperCase(name);
	let storeVal = store.getters[storeGetId] ;
	let arr = option.arr ;
	let reset = option.reset ;
	let setStroe = !!storeVal ;

	if ( typeof arr == "object" &&  Array.isArray( arr ) ) {
		format( arr );
	} else {
		let snedApi = true ;
		if( !reset && setStroe && storeVal.length > 0 ) {
			snedApi = false ;
		}

		if( snedApi ) {
			window.vue.config.globalProperties.ApiUtilSA.get(api)
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					format(data);
				}
			});
		} else {
			doAddFirst( storeVal.slice() );
		}
	}

	function format( data ){
		let result = [] ;
		for( var i = 0 ; i < data.length ; i++ ) {
			if( resultFunc ) {
				result.push(resultFunc(data[i]));
			} else {
				let item = data[i] ;
				result.push({
					val 	: item.key ,
					text 	: item.value 
				});
			}
		}
		if( setStroe ) {
			store.commit( storeSetId , result ) ;
		}
		doAddFirst( result ) ;
	}

	function doAddFirst( storeVal ){
		let result = [] ;
		if( callBack ) {
			if( addFirst ) {
				if( Array.isArray( addFirst ) ) {
					addFirst.forEach(function( item ){
						result.push( item );
					});
				} else {
					result.push(addFirst);
				}
				
			}
			
			result = result.concat(storeVal);
			callBack( result );
			
		}
	}	

}