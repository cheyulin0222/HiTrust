const SESSION_NAME = 'USER' ;
const SESSION_MENU = 'MENU' ;

import router from "@/router";
import store from "@/store";
import { updateSession , reqSession } from "@/utils/session";
var CryptoJS = require("crypto-js");

function setUser ( user , setSession = true ) {

	store.commit("user/setUser",user);
	if( setSession ) {
		setUserSession();
	}
}

function getLocale() {
	return store.getters["user/getLocale"] ;
}

function setLocale( locale , setSession = true ) {
	store.commit( "user/setLocale" , locale );
	if( setSession ) {
		setUserSession();
	}
	updateSession();
}

function getMenu () {
	return store.getters["menu/getMenu"] ;
}

function setMenu ( menu , setSession = true) {
	store.commit( "menu/setMenu" , menu ) ;
	if( setSession ) {
		setMenuSession();
	}
	
}

function setUserSession(){
	sessionStorage.setItem(SESSION_NAME , JSON.stringify(store.getters["user/getUser"]) ) ;
}

function setMenuSession(){
	sessionStorage.setItem(SESSION_MENU , JSON.stringify(store.getters["menu/getMenu"]) ) ;
}

function getMenuActive(){
	return store.getters["menu/getMenuActive"] ;
}

function getToken() {
	return store.getters["user/getToken"] ;
}

function setToken ( menu , setSession = true) {
	store.commit( "user/setToken" , menu ) ;	
}

function checkSession(){
	let isLogin = store.getters["user/getLogin"] ;
	if( !isLogin ) {
    let session = sessionStorage.getItem(SESSION_NAME) ;
    if( !session ) {
      reqSession();
    } else {
      setUser( JSON.parse(session) , false );
      setMenu( JSON.parse(sessionStorage.getItem(SESSION_MENU)) , false );
    }
  }
}

function logOut(){
	sessionStorage.clear();
	store.commit("user/resetState");
	store.commit("menu/resetState");
	router.push('/login');
}

export function checkPasswd( pass ){
	let errMsg = "" ;
	let idx = 0;
	var flag = 0;
	var numcount = 0;
	var encount = 0;
	
	if(pass.length < 12){
		return "密碼長度不得少於12碼" ;
	}
	
	while (idx < pass.length){		
  	
	  	if ((pass.charAt(idx) >= '0') && (pass.charAt(idx)<= '9')){
  			flag = 1;
  			numcount++; 			
		}
		if ( pass.charAt(idx)>='a' && pass.charAt(idx)<='z'){
  			flag = 1;
  			encount++;		
		}
		if ( pass.charAt(idx)>='A' && pass.charAt(idx)<='Z'){
  			flag = 1;
  			encount++;		
		}
		idx++;
  	}

  	if( (flag==1) && (numcount!=pass.length) && (encount!=pass.length)){
	    return "";
	} else{
		return "密碼請以英數字夾雜" ;
	}
}

export function checkAccount( account ){
	let flagOne = 0;
	let idx = 0;
	while (idx < account.length){		
  		if (account.charAt(idx)>= 'a' && account.charAt(idx) <= 'z') {
  			flagOne = 1; 			
		}
	        else if (account.charAt(idx)>= 'A' && account.charAt(idx) <= 'Z') {
  			flagOne = 1; 			
		}
	        else if (account.charAt(idx)>= '0' && account.charAt(idx) <= '9') {
  			flagOne = 1; 			
		}
	        else{
	        	flagOne = 0; 	
	        }
		idx++ ;
  	}
  	
	if( flagOne == 1 ){
	    return "";
	}else{
		return "帳號格式錯誤" ;
	}
	
}

export function getSHAOldPassword( password ){
	return getSHAPassword( password , "SHA1-SHA512") ;
}

export function getSHAPassword( password , type ){
	if( type === "SHA1-SHA512" ) {
		return CryptoJS.SHA1( password ).toString() + ',' + CryptoJS.SHA512( password ).toString() ;
	} else {
		return CryptoJS.SHA512( password ).toString() ;
	}
	
}

export { setUser , SESSION_NAME , SESSION_MENU , getLocale , setLocale , setMenu , 
	getToken , setToken , checkSession , logOut , getMenuActive }


