import { getToken } from '@/utils/user'
import HttpUtil from "./httpUtil";
import { sendLogOut } from "@/utils/session"
import { getMenuActive } from "@/utils/user"

class ApiUtil {
    
    //#apiUrl : string ;

    #vue : any ;    
    #HttpUtil : HttpUtil ;
    env : any ;

    constructor( vue ) {
        //this.#apiUrl = ( process.env.VUE_APP_API_URL_TEST ? process.env.VUE_APP_API_URL_TEST : process.env.VUE_APP_API_URL ) ;
        this.#HttpUtil = new HttpUtil( vue );
        this.#vue = vue ;
        this.env = vue.env.getData();
    }

    #headers( token ) {
        if( token == "" ) {
           return {}
        } else {
        	return {
                Authorization: `Bearer ${token}`,
                'function-id': getMenuActive() 
            };  	 
        }        
    }

    url( path ) {
        if( !path.startsWith("/") ) {
            path = "/" + path;
        }
        return  '/'+this.env.AP_NAME+'/api' + path;
    }

    get( path, params = null, headers = {} , reqLogin = true ) {
    	return this.#checkToken( "get" , path , params , headers , reqLogin ) ;
    }

    post( path, params , headers = {} , reqLogin = true ) {
        return this.#checkToken( "post" , path , params , headers , reqLogin ) ;
    }

    put( path, params = null, headers = {} , reqLogin = true ) {
        return this.#checkToken( "put" , path , params , headers , reqLogin ) ;
    }

    delete( path, params = null, headers = {} , reqLogin = true ) {
        return this.#checkToken( "delete" , path , params , headers , reqLogin ) ;
    }

    downloadExcel ( path, params = null, headers = {} , reqLogin = true ) {
        return this.#checkToken( "downloadExcel" , path , params , headers , reqLogin ) ;
    }

    downloadExcel2 ( path, params = null, headers = {} , reqLogin = true ) {
        return this.#checkToken( "downloadExcel2" , path , params , headers , reqLogin ) ;
    }

    downloadView( path, params = null, headers = {} , reqLogin = true ) {
        return this.#checkToken( "downloadView" , path , params , headers , reqLogin ) ;
    }

    download ( path, params = null, headers = {} , reqLogin = true ) {
        return this.#checkToken( "download" , path , params , headers , reqLogin ) ;
    }

    #checkToken( method , path, params , headers , reqLogin ) {
    	let token = "" ;
    	if( reqLogin ) {
    		token = getToken();
    		if( !token && token == "" ) {
    			this.#vue.showModel({
	                title : '呼叫API失敗',
	                message : '已被登出',
	                endFunc : function(){
	                	sendLogOut();
	                }
	            });
	            return new Promise((resolve, reject) => {
	            	resolve({
	            		success : false 
	            	})
	            });
	        }
    	}
    	let url =  this.url(path) ;
        if( !headers['contentType'] ) {
            headers['contentType'] = 'application/json'
        }
    	let header 	= Object.assign( this.#headers( token ) , headers );
        /*if( params && process.env.NODE_ENV != 'development' ) {
            params.jsonData = JSON.stringify(params) ;
        }*/
        
        if( method === 'downloadView' ) {
            return this.#HttpUtil['downloadExcel']( url, params , header , "VIEW")
        } else {
            return this.#HttpUtil[method]( url, params , header )
        }
    }

}

export default ApiUtil ;


