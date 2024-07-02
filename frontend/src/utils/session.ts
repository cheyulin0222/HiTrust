import { setUser , setMenu , SESSION_NAME , SESSION_MENU , logOut } from '@/utils/user'
import store from "@/store";
import { resetRouter } from "@/router";
//import ApiUtil from "./apiUtil_CRM";

const LOGIN_CHANNEL = new BroadcastChannel('LOGIN_CHANNEL') ;

function setAllSession( allSession , reset ){
	if( !sessionStorage.length || reset ) {
		//console.log('doSetSession');
		for(let key in allSession ) {
			let data = allSession[key] ;
			if( key === SESSION_NAME ) {
				setUser(JSON.parse(data));
			} else if( key === SESSION_MENU) {
				setMenu(JSON.parse(data));
			} else {
				sessionStorage.setItem(key,data);
			}			
		} 
	}
}

function initListen(){

	LOGIN_CHANNEL.onmessage = e => {
		console.log('MAIN');
		const data = e.data
			switch (data.action) {
				case 'reqSession' :
					//console.log('reqSession');
					if( sessionStorage.length ) {
  					LOGIN_CHANNEL.postMessage({
		                action : 'setSession' ,
		                session : JSON.stringify(sessionStorage)
		            })
  				}
				break;
				case 'setSession' : 
					//console.log('setSession');
  					setAllSession(JSON.parse(data.session) , !sessionStorage.length)
				break ;
				case 'updateSession' :
					if( store.getters["user/getSendMessage"] ) {
						store.commit( "user/setSendMessage" , false ) ;
					} else {
						setAllSession(JSON.parse(data.session),true)
					}
				break;
				case 'logOut' : 
					if( store.getters["user/getSendMessage"] ) {
						store.commit( "user/setSendMessage" , false ) ;
					} else {
						logOut();
					}
				break ;
			}
	}
	return LOGIN_CHANNEL ;
}

function reqSession () {
	LOGIN_CHANNEL.postMessage({
    action : 'reqSession'
  });
}

function updateSession(){
	store.commit( "user/setSendMessage" , true ) ;
	LOGIN_CHANNEL.postMessage({
        action : 'updateSession',
        session : JSON.stringify(sessionStorage)
  	});
}

function sendLogOut (){
	/*if( process.env.NODE_ENV != "development") {
		new ApiUtil(null).get("/user/logout")
	}*/
	store.commit( "user/setSendMessage" , true ) ;
	LOGIN_CHANNEL.postMessage({
      action : 'logOut'
    });
    logOut();
    resetRouter();
}

export { setAllSession , initListen , reqSession , updateSession , sendLogOut }