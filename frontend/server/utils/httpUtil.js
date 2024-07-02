const axios = require('axios') ;
const fetch = require('node-fetch');

class HttpUtil {
    constructor( token = '' ) {
        this.token = token
    }

    getGetUrl( path, params = null, ){
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
        
        let url = path ;
        if( text != "" ) {
            url += "?" + text.slice(0, -1)
        }
        return url ;
    }
     
    get( path, params = null, headers = {} ) {
        let url = this.getGetUrl( path, params );
        const promise = axios.get( url , {
            timeout: process.env.VUE_APP_API_TIMEOUT,
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
            timeout: process.env.VUE_APP_API_TIMEOUT,
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
            timeout: process.env.VUE_APP_API_TIMEOUT,
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
            timeout: process.env.VUE_APP_API_TIMEOUT,
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

    doFetch(path, data = null, headers = {}){
        let url = this.url(path) + "?" + new URLSearchParams(data);
        const promise = fetch(url, {
            method: 'GET',
            headers: {
                ...this.headers(),
                ...headers,
            },
        });
        return saveRequest(promise, {
            url: this.url(path),
            method: 'put',
            request: data,
            email: this.email,
        }).then(function(response) {
            //console.log(response);
            if(response.ok) {
                return response.blob().then( myBlob => {
                    let contentType = response.headers.get('content-type') ;
                    if( contentType.indexOf('vnd.ms-excel') > -1 || contentType.indexOf('text/plain') > -1 || contentType.indexOf('octet-stream') > -1) {
                        let disposition = response.headers.get('content-disposition')
                        let filename = disposition && disposition.split(';')[1].split('filename=')[1]
                        if (filename) {
                            if (filename.indexOf('%') < 0) {
                                filename = decodeURIComponent((filename))
                                response.data = {
                                    fileName : filename
                                }
                            } else {
                                filename = decodeURIComponent(filename)
                                response.data = {
                                    fileName : filename
                                }
                            }
                        }
                    }
                    return {
                        response: response,
                        blob: myBlob
                      }
                });
            } else {
                return {
                    response: response,
                }
            }
        });
    }

    downloadExcel( path , params = {} , headers = {} ){
        let _this = this ;
        let url = this.getGetUrl( path, params );
        return fetch(url,{
            method: 'GET',
            headers: {
                ...headers,
            },
        })
        .then(function( res ) {      
            if(res.ok) {
                return res.blob().then(myBlob=>{
                    let headers = res.headers ;

                    let contentType = headers.get('content-type') ;
                    if( contentType.indexOf('octet-stream') > -1 || contentType.indexOf('vnd.ms-excel') > -1 
                        || contentType.indexOf('image/jpeg') > -1 || contentType.indexOf('application/pdf') > -1) {

                        return myBlob.arrayBuffer().then((buf) => {

                            let result = {
                                headers : {
                                    'content-type' : contentType ,
                                    'content-disposition' : headers.get('content-disposition') ,
                                } ,
                                status : res.status ,
                                data : Buffer.from(buf)
                            }
                            return result ;
                        });
                       
                    } else {
                        return {
                            status : res.status ,
                            data : res.data 
                        }
                    }               
                });
            } else {
                return res.json().then(text=>{
                    return {
                        message : text.message ,
                        data    : text ,
                        status  : res.status
                    }
                });
               
                
            }
        })
        .catch((res) => {
            let response = res.response ;
            if( response ) {
                return {
                    status : response.status ,
                    data : response.data 
                }
            } else {
                return {
                    status : 500 ,
                    data : "連線異常"
                }
            }
        })
    }

    downloadExcel2( path , params = {} , headers = {} ){
        let _this = this ;
        headers['Content-Type'] = 'application/json';
        return fetch(path,{
            body: JSON.stringify(params),
            method: "POST",
            headers: {
                ...headers,
            },
        })
        .then(function( res ) {      
            if(res.ok) {
                return res.blob().then(myBlob=>{
                    let headers = res.headers ;

                    let contentType = headers.get('content-type') ;
                    if( contentType.indexOf('octet-stream') > -1 || contentType.indexOf('vnd.ms-excel') > -1 
                        || contentType.indexOf('image/jpeg') > -1 ) {

                        return myBlob.arrayBuffer().then((buf) => {

                            let result = {
                                headers : {
                                    'content-type' : contentType ,
                                    'content-disposition' : headers.get('content-disposition') ,
                                } ,
                                status : res.status ,
                                data : Buffer.from(buf)
                            }
                            return result ;
                        });
                       
                    } else {
                        return {
                            status : res.status ,
                            data : res.data 
                        }
                    }               
                });
            } else {
                return res.json().then(text=>{
                    return {
                        message : text.message ,
                        data    : text ,
                        status  : res.status
                    }
                });
               
                
            }
        })
        .catch((res) => {
            let response = res.response ;
            if( response ) {
                return {
                    status : response.status ,
                    data : response.data 
                }
            } else {
                return {
                    status : 500 ,
                    data : "連線異常"
                }
            }
            
        })
    }

    async #saveRequest ( promise , data ) {
        let _this = this ;
        return promise
        .then((res) => {
            return {
                status : res.status ,
                data : res.data 
            }
        })
        .catch((res) => {
            //console.log(res);
           let response = res.response ;
            if( response ) {
                return {
                    status : response.status ,
                    data : response.data 
                }
            } else {
                return {
                    status : 500 ,
                    data : "連線異常"
                }
            }
        })
    }

    #setErr( res ){
        let result = {
            success : false 
        }
        //console.log( "header" + res.request ? res.request._header : '');
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
        if( status == 401 ) {
            
            return result ;
        }
        
        let message = "" ;
        if( typeof response.data === 'object' ) {
            if( !response.data.message && status == 404 ) {
                message = "找不到頁面("+response.config.url+")"
            } else {
                message = response.data.message + "</br>" + response.data.description ;
            }   
        } else {
            message = response.data ;
        }
        
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
    };

}

module.exports = {
    HttpUtil
};



