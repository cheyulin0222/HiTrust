import { getToken } from '@/utils/user'
import axios from 'axios' ;
import { ErrHitrust }from "../errors/ErrHitrust";
import { sendLogOut } from "@/utils/session"

class HttpUtil {
    
    #vue : any ;    
    #env : any ;
    constructor( vue ) {
        this.#vue = vue ;
        this.#env = vue.env.getData();
    }
    
    get( path, params = null, headers = {} ) {
        let text = this.parseGetData( params ) ;
        let url = path ;
        
        if( text != "" ) {
            url += "?" + text.slice(0, -1)
        }

        const promise = axios.get( url , {
            timeout: this.#env.API_TIMEOUT,
            headers: {
                ...headers,
            },
        });
        return this.#saveRequest(promise, {
            url: path ,
            method: 'get',
            request: params,
        });
    }

    post( path, data, headers = {} ) {
        const promise = axios({
            url : path, 
            method: 'post',
            timeout: this.#env.API_TIMEOUT,
            data,
            headers: {
                ...headers,
            },
        });
        return this.#saveRequest(promise, {
            url: path,
            method: 'post',
            request: data,
        });
    }

    put( path, data, headers = {} ) {
        const promise = axios({
            url : path, 
            method: 'put',
            timeout: this.#env.API_TIMEOUT,
            data,
            headers: {
                ...headers,
            },
        });
        return this.#saveRequest(promise, {
            url: path,
            method: 'put',
            request: data,
        });
    }

    delete( path, data, headers = {} ) {
        const promise = axios({
            url : path, 
            method: 'delete',
            timeout: this.#env.API_TIMEOUT,
            data,
            headers: {
                ...headers,
            },
        });
        return this.#saveRequest(promise, {
            url: path,
            method: 'put',
            request: data,
        });
    }

    downloadExcel(url,data={},headers = {}, type="DOWNLOAD"){
        let text = this.parseGetData( data ) ;
        let _this = this ;
        headers['downloadExcel'] = "downloadExcel";

        if( text != "" ) {
            url += "?" + text.slice(0, -1)
        }

        const promise = fetch(url,{
            headers: {
                ...headers,
            }
        });
        
        return this.#saveRequest(promise, {
            url: url,
            method: 'downloadExcel',
            request: data,
        })
        .then(function(response) {
            if(response.ok) {
                return response.blob().then(myBlob=>{
                    let contentType = response.headers.get('content-type') ;
                    if( contentType.indexOf('octet-stream') > -1 || contentType.indexOf('vnd.ms-excel') > -1 || contentType.indexOf('image/jpeg') > -1 || contentType.indexOf('application/pdf') > -1) {
                        if( type == "DOWNLOAD" ) {
                            let disposition = response.headers.get('content-disposition')
                            let filename = disposition && disposition.split(';')[1].split('filename=')[1];
                            filename = decodeURI( filename ) ;
                            let a = document.createElement('a')
                            a.href = URL.createObjectURL(myBlob);
                            a.download = filename ;
                            document.body.appendChild(a)
                            a.click()
                            a.remove()
                        } else {
                            return{
                                success         : true ,
                                data            : URL.createObjectURL(myBlob),
                                contentType     : contentType
                            }
                        }
                        
                    }               
                });
            } else {
                response.json().then(text=>{
                    let res = {
                        response : {
                            message : text.message ,
                            data    : text ,
                            status  : response.status
                        }
                    }
                    return _this.#setErr.call( _this, res ) ;
                })
                
            }
        })
        .catch(function(error) {
            var message = error.message ;
            _this.#vue.showModel({
                type    : "error" ,
                title   : "ERROR" ,
                message : message
            });
        })
    }

    downloadExcel2(url,data={},headers = {}, type="DOWNLOAD"){
       
        let _this = this ;
        headers['downloadExcel'] = "downloadExcel2";
        headers['Content-Type'] = 'application/json';
        const promise = fetch(url,{
            headers: {
                ...headers,
            },
            body: JSON.stringify(data),
            method: "POST",
        });
        
        return this.#saveRequest(promise, {
            
            url: url,
            method: 'downloadExcel',
            request: data,
        })
        .then(function(response) {
            if(response.ok) {
                return response.blob().then(myBlob=>{
                    let contentType = response.headers.get('content-type') ;
                    if( contentType.indexOf('octet-stream') > -1 || contentType.indexOf('vnd.ms-excel') > -1 || contentType.indexOf('image/jpeg') > -1 || contentType.indexOf('application/pdf') > -1) {
                        if( type == "DOWNLOAD" ) {
                            let disposition = response.headers.get('content-disposition')
                            let filename = disposition && disposition.split(';')[1].split('filename=')[1];
                            filename = decodeURI( filename ) ;
                            let a = document.createElement('a')
                            a.href = URL.createObjectURL(myBlob);
                            a.download = filename ;
                            document.body.appendChild(a)
                            a.click()
                            a.remove()
                        } else {
                            return{
                                success         : true ,
                                data            : URL.createObjectURL(myBlob),
                                contentType     : contentType
                            }
                        }
                        
                    }               
                });
            } else {
                response.json().then(text=>{
                    let res = {
                        response : {
                            message : text.message ,
                            data    : text ,
                            status  : response.status
                        }
                    }
                    return _this.#setErr.call( _this, res ) ;
                })
                
            }
        })
        .catch(function(error) {
            var message = error.message ;
            _this.#vue.showModel({
                type    : "error" ,
                title   : "ERROR" ,
                message : message
            });
        })
    }

    parseGetData( params = {} ){
        let text = "" ;
        if( params ) {
            Object.keys(params).forEach(function( key ){

                let val = params[key] ;
                if( typeof val !== 'undefined' || val != null ) {
                    if( typeof val === "object" )  {
                        if( Array.isArray( val ) ) {
                            val.forEach(function( value){
                                
                                text += key+"="+value+"&" ;
                            });
                        } else {

                            text += key+"="+JSON.stringify(val)+"&" ;
                        }
                    } else {
                        text += key+"="+val+"&" ;
                    }
                }
            });
        }

        return text ;
    }

    async #saveRequest ( promise , data ) {
        let _this = this ;
        return promise
        .then((res) => {
            if( res.data && res.data.data ) {
                res = res.data ;
            }
            res.success = true ;
            return res;
        })
        .catch((res) => {
            return _this.#setErr.call( _this, res ) ;

            //_this.#parseAxiosError(res);
        })
    }

    #setErr( res ){
        let result = {
            success : false 
        }
        if (res.response) {
            console.log( "status " + res.response ? res.response.status : -1 );
            console.log( "response " + JSON.stringify(res.response.data) );
        } else {
            console.log( "response ");
            console.log( "error: " + res.message );
        };
        //console.log(res);
        const response = res.response || { status: 0, data: '' };
        let status = response.status;
        if( status == 401 || ( status == 403 && response.data.code == 'AUTHORITY' )) {
            this.#vue.showModel({
                title : '呼叫API失敗',
                message : '已被登出',
                endFunc : function(){
                    sendLogOut();
                }
            });
            return result ;
        }
        
        let message = "" ;
        if( typeof response.data === 'object' ) {
            if( !response.data.message && status == 404 ) {
                message = "找不到頁面("+response.config.url+")"
            } else {
                let description = response.data.description ;
                if( description == undefined ) {
                    description = response.data.data
                }
                message = response.data.message + "</br>" + description ;
            }   
        } else {
            message = response.data ;
        }
        this.#vue.showModel({
            type    : "error" ,
            title   : response.status ,
            message : message
        });
        return result ;
    }

    #parseAxiosError (err) {
        const response = err.response || { status: 0, data: '' };
        const msg = [
            `[${response.status}] - ${err.config.method}@${err.config.url} : ${err.message}`,
            `response: `,
            `\t ${JSON.stringify(response.data || '')}`,
        ].join('\n');
        
        console.trace(msg);

        throw new ErrHitrust(err.message, response.data ? response.data.code : '0');
    };

}

export default HttpUtil ;


