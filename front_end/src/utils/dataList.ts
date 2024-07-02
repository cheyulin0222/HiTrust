import store from "@/store";

export function getListVal ( arr , val , joinTxt = ",") {
	let obj = null ;
	let text = "" ;
	let list ;
	if( typeof arr === "string" ) {
		list = store.getters[arr] ;
	} else {
		list = arr ;
	}
	if( Array.isArray( list ) ) {
		let isString = typeof val != "object"
		for(var i = 0 ; i < list.length ; i ++ ) {
			let o = list[i];
			if( isString ){
				if( val == o.val ) {
					text = o.text ;
					break ;
				}
			} else {
				if( val.indexOf( o.val ) >-1 ) {
					if( text != "" ) {
						text += joinTxt ;
					}
					text += o.text
				}
			}
		}
		return text ;
	} else {
		return "" ;
	}
	
}

export function getYearList (){
	let result = [] ;
	let year = new Date().getFullYear();
	
	for( var i = 1999 ; i <= year ; i++ ) {
		result.push({
			val 	: i ,
			text 	: i
		});
	}
	return result ;
}

export function getMonthList (){
	let result = [] ;
	
	for( var i = 1 ; i <= 12 ; i++ ) {
		result.push({
			val 	: i ,
			text 	: i
		});
	}
	return result ;
}