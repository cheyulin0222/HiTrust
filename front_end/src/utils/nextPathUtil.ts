import router from "@/router";
import store from "@/store";

function setNextPath( routerName , param = {} ) {
	store.commit( "nextPath/setNext" , {routerName,param});
	router.push('/XXX');
}

function getNextName(){
	return store.getters["nextPath/getRouterName"] ;
}

function getParam(){
	return store.getters["nextPath/getParam"] ;
}

function resetNextPath(){
	store.commit( "nextPath/resetState" );
}

export { setNextPath , getNextName , getParam , resetNextPath }