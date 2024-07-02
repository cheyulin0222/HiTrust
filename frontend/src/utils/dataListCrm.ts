import store from "@/store";
import { firstUpperCase } from "./util";

declare global {
    interface Window { 
        vue : any ,
    }
}

let EnvironmentId = {
	name : "environmentId" ,
}

export function getEnvironmentId ( callBack ) {
	let option = Object.assign({},EnvironmentId);
	option["api"] = "/environment/all";
	option["callBack"] = callBack ;
	getUserList.call( this , option );
}

export function resetEnvironmentId ( arr ) {
	let option = Object.assign({},EnvironmentId);
	option["arr"] = arr;
	getUserList.call( this , option );
}

let SalesId = {
	name : "salesId" , 
	resultFunc : function( item ){
		let text = item.id + '-' + item.nameTw ;
		if( item.nameEn ) {
			if( text != "" ) {
				text += `(${item.nameEn})`
			} else {
				text = item.nameEn
			}
		}
		return {
			val 	: item.id ,
			text 	: text 
		}
	},
}

export function getSalesId ( callBack , isSearch ) {
	let option = Object.assign({},SalesId);
	option["api"] = "/sales/all";
	option["callBack"] = callBack ;
	if( isSearch ) {
		option["addFirst"] = {
			text 	: "請選擇" ,
			val		: "" ,
		}
	}
	getUserList.call( this , option );
}

export function getSalesId2 ( addAll , callBack ) {
	let option = Object.assign({},SalesId);
	option["api"] = "/sales/all";
	option["callBack"] = callBack ;
	if( addAll ) {
		option["addFirst"] = {
			text 	: "全部" ,
			val		: "00" ,
		}
	}
	getUserList.call( this , option );
}

export function resetSalesId ( arr ) {
	let option = Object.assign({},SalesId);
	option["arr"] = arr;
	getUserList.call( this , option );
}

let CategoryId = {
	name : "categoryId" ,
}

export function getCategoryId ( callBack ) {
	let option = Object.assign({},CategoryId);
	option["api"] = "/category/all";
	option["callBack"] = callBack ;
	getUserList.call( this , option );
}

export function resetCategoryId ( arr ) {
	let option = Object.assign({},CategoryId);
	option["arr"] = arr;
	getUserList.call( this , option );
}

let Acquirer = {
	name : "acquirer" ,
	resultFunc : function( item ){
		let text = item.id + '-' + item.nameTw ;
		if( item.nameCode ) {
			if( text != "" ) {
				text += `(${item.nameCode})`
			} else {
				text = item.nameCode
			}
		}
		return {
			val 	: item.id ,
			text 	: text 
		}
	}, 
}

export function getAcquirer ( callBack , isSearch ) {
	let option = Object.assign({},Acquirer);
	option["api"] = "/acquirer/all";
	option["callBack"] = callBack ;
	if( isSearch ) {
		option["addFirst"] = {
			text 	: "不限" ,
			val		: "" ,
		}
	}
	getUserList.call( this , option );
}

export function resetAcquirer ( arr ) {
	let option = Object.assign({},Acquirer);
	option["arr"] = arr;
	getUserList.call( this , option );
}

let TagId = {
	name : "tagId" 
}

export function getTagId ( callBack ) {
	let option = Object.assign({},TagId);
	option["api"] = "/tag/all";
	option["callBack"] = callBack ;
	getUserList.call( this , option );
}

export function resetTagId ( arr ) {
	let option = Object.assign({},TagId);
	option["arr"] = arr;
	getUserList.call( this , option );
}

let NotifyType = {
	name : "notifyType" ,
}

export function getNotifyType ( callBack ) {
	let option = Object.assign({},NotifyType);
	option["api"] = "/contact-notify-type/all";
	option["callBack"] = callBack ;
	getUserList.call( this , option );
}

export function resetNotifyType ( arr ) {
	let option = Object.assign({},NotifyType);
	option["arr"] = arr ;
	getUserList.call( this , option );
}

let BTFType = {
	name : "BTFType" ,
}

export function getBTFType ( callBack ) {
	let option = Object.assign({},BTFType);
	option["api"] = "/bank-transaction-fee/type/all";
	option["callBack"] = callBack ;
	getUserList.call( this , option );
}

export function resetBTFType ( arr ) {
	let option = Object.assign({},BTFType);
	option["arr"] = arr;
	getUserList.call( this , option );
}

let STFBType = {
	name : "STFBType" ,
}

export function getSTFBType ( callBack ) {
	let option = Object.assign({},STFBType);
	option["api"] = "/store-transaction-fee/billing-type/all";
	option["callBack"] = callBack ;
	getUserList.call( this , option );
}

export function resetSTFBType ( arr ) {
	let option = Object.assign({},STFBType);
	option["arr"] = arr;
	getUserList.call( this , option );
}

let STFPayer = {
	name : "STFPayer" ,
}

export function getSTFPayer ( callBack ) {
	let option = Object.assign({},STFPayer);
	option["api"] = "/store-transaction-fee/payer/all";
	option["callBack"] = callBack ;
	getUserList.call( this , option );
}

export function resetSTFPayer ( arr ) {
	let option = Object.assign({},STFPayer);
	option["arr"] = arr;
	getUserList.call( this , option );
}

let STFGMBType = {
	name : "STFGMBType" ,
}

export function getSTFGMBType ( callBack ) {
	let option = Object.assign({},STFGMBType);
	option["api"] = "/store-transaction-fee/group-merchant-billing-type/all";
	option["callBack"] = callBack ;
	getUserList.call( this , option );
}

export function resetSTFGMBType ( arr ) {
	let option = Object.assign({},STFGMBType);
	option["arr"] = arr;
	getUserList.call( this , option );
}

export function getContract( id , callBack ){
	window.vue.config.globalProperties.ApiUtil.get("/company-contract/company/"+id)
	.then((res) => {
		if( res.success ) {
			let data = res.data ;
			let result = [];
			for( var i = 0 ; i < data.length ; i++ ) {
				let item = data[i];
				result.push({
					val 	: item.id ,
					text 	: item.contractNumber + " / " + item.startDate + "~" + item.endDate
				});
			}
			
			if( callBack ) {
				callBack( result );				
			}
		}		
	})
}

export function getSTFAll( callBack ){
	window.vue.config.globalProperties.ApiUtil.get("/store-transaction-fee/rate/all")
	.then((res) => {
		if( res.success ) {
			let data = res.data ;
			let result = [];
			for( var i = 0 ; i < data.length ; i++ ) {
				let item = data[i];
				result.push({
					val 	: item.id ,
					text 	: item.settingName 
				});
			}
			
			if( callBack ) {
				callBack( result );				
			}
		}		
	})
}

export function getUserList ( option ) {
	let name = option.name ;
	let api = option.api ;
	let resultFunc = option.resultFunc ;
	let callBack = option.callBack ;
	let addFirst = option.addFirst ;
	let storeGetId = "user/get"+firstUpperCase(name);
	let storeSetId = "user/set"+firstUpperCase(name);
	let storeVal = store.getters[storeGetId] ;
	let arr = option.arr ;
	let reset = option.reset ;

	if( storeVal ) {
		if ( typeof arr == "object" &&  Array.isArray( arr ) ) {
			format( arr );
		} else {
			if( storeVal.length == 0 || reset ) {
				window.vue.config.globalProperties.ApiUtil.get(api)
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
						val 	: item.id ,
						text 	: item.name 
					});
				}
			}
			store.commit( storeSetId , result ) ;
			doAddFirst( result ) ;
		}

		function doAddFirst( storeVal ){
			let result = [] ;
			if( callBack ) {
				if( addFirst ) {
					result.push(addFirst);
				}
				
				result = result.concat(storeVal);
				callBack( result );
				
			}
		}	
	} else {
		window.vue.config.globalProperties.showModel({
            title : '錯誤',
            type  : "error",
            message : '取得資料錯誤: '+ storeGetId 
        });
	}
}

export function getAll ( callBack , isSearch ) {
	getAcquirer.call( this , callBack , isSearch );
    getEnvironmentId.call( this , callBack );
    getSalesId.call( this , callBack , isSearch );
    getCategoryId.call( this , callBack );
    getNotifyType.call( this , callBack );
    getTagId.call( this , callBack );
    getBTFType.call(this , callBack );
    getSTFPayer.call(this , callBack );
    getSTFBType.call(this , callBack );
    getSTFGMBType.call(this , callBack );
}