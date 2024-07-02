import { createApp } from "vue";
import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import i18n from "./i18n";
import dialog  from "./components/Dialog";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.css"; 
import "./assets/css/hitrust.css"
import "./assets/css/hitrust2.css"
import $ from 'jquery'
import { initListen } from "@/utils/session" ;
import moment from "moment" ;
import HttpUtil from "@/utils/httpUtil";
import ApiUtil from "@/utils/apiUtil_CRM";
import ApiUtilSA from "@/utils/apiUtil_SA";
import ApiUtilCMS from "@/utils/apiUtil_CMS";
import env from "@/utils/env";
import { setNextPath , getParam } from "@/utils/nextPathUtil";

let app = createApp(App).use(store).use(router)

declare global {
    interface Window { 
        $   : any ,
        vue : any ,
    }
}

app.use({
    install: function(Vue, options){    
        Vue.config.globalProperties.LoginChannel = initListen() ;
        Vue.config.globalProperties.goTo =  function( path ) {
            if( path == "back" ) {
                router.back();
            } else if ( typeof path === 'object' ) {
                router.push(path);
            } else if( path.startsWith('/')){
                router.push(path);
            } else {
                
                let fullPath = this.$route.fullPath ;
                router.push(fullPath+'/'+path);
            }
        }
        Vue.config.globalProperties.hasRoute = function ( routerName ) {
            return router.hasRoute(routerName) ;
        }

        Vue.config.globalProperties.goNextPath = setNextPath ;
        Vue.config.globalProperties.getParam = getParam ;

        window.vue = Vue ;
        window.$ = $ ;
    }
});

app.config.globalProperties.$filters = {
    time( value , format ) {
        if( value ) {
            let d = new Date(value) ;
            if( d instanceof Date && !isNaN(d.getTime())) {
                return moment(d).format(format || 'YYYY/MM/DD');
            } else {
                return moment(String(value)).format(format || 'YYYY/MM/DD');
            }
        }
    }
};


app.use(i18n);
app.use(dialog); 

let envObj = new env();

envObj.init(function(){
    app.config.globalProperties.env = envObj ;
    app.config.globalProperties.HttpUtil = new HttpUtil( app.config.globalProperties ) ;
    app.config.globalProperties.ApiUtil  = new ApiUtil( app.config.globalProperties ) ;
    app.config.globalProperties.ApiUtilSA  = new ApiUtilSA( app.config.globalProperties ) ;
    app.config.globalProperties.ApiUtilCMS  = new ApiUtilCMS( app.config.globalProperties ) ;
    app.mount("#app");    
});








