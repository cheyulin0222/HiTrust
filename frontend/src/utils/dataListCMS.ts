import store from "@/store";
import { firstUpperCase } from "./util";

declare global {
    interface Window { 
        vue : any ,
    }
}

export function getSalesId (isSearch, callBack) {
 
	let option = {
		callBack  : callBack ,
		api   : "get-all-sales"
	}
	
	
	if( isSearch ) {
		option["addFirst"] = {
			text  : "請選擇" ,
			val  : "" ,
		}
	}
	getUserList.call( this , option );
}

export function getSalesId2 (isSearch, callBack) {
 
	let option = {
		callBack  : callBack ,
		api   : "get-all-sales"
	}
	
	
	if( isSearch ) {
		option["addFirst"] = {
			text  : "全部" ,
			val  : "" ,
		}
	}
	getUserList.call( this , option );
}

export function getCpdTypeList ( callBack ){
	let option = {
		callBack : callBack ,
		//api : ""
	}

	let result = [] ;

	option["arr"] = result.concat([
	{
		key 	: "CPD00006" ,
		value 	: "Symantec Secure Site Pro SSL 一年期"  
	}]);

	getUserList.call( this , option );
}

export function getCertTypeList ( addAll , callBack ){
	let option = {
		callBack : callBack ,
		api : "get-all-certs"
	}

	if( addAll ) {
		option["addFirst"] = {
			text 	: "全部" ,
			val		: "" ,
		}
	}

	getUserList.call( this , option );
}

export function getCategoryList ( addAll , callBack ){
	let option = {
		callBack : callBack ,
		api : "get-all-category"
	}

	// let result = [] ;

	if( addAll ) {
		option["addFirst"] = {
			text  : "全部" ,
			val  : "" ,
		}
	}
	getUserList.call( this , option );
}

export function getExpiryTypeList ( addAll , callBack ){
	let option = {
		callBack : callBack ,
		//api : ""
	}

	let result = [] ;

	if( addAll ) {
		option["addFirst"] = {
			text  : "請選擇" ,
			val  : "" ,
		}
	}

	option["arr"] = result.concat([
	{
		value 	: "1年期" ,
		key		: "ONE_YEAR" ,
	},
	{
		value 	: "2年期" ,
		key		: "TWO_YEARS" ,
	},
	{
		value 	: "3年期" ,
		key		: "THREE_YEARS" ,
	}
	]);

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
			window.vue.config.globalProperties.ApiUtilCMS.get(api)
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
				result.push(addFirst);
			}
			
			result = result.concat(storeVal);
			callBack( result );
			
		}
	}	

}