const express = require('express');
const routes = express.Router();
const bodyParser = require('body-parser');
const { HttpUtil } = require('./utils/httpUtil.js');
const { logger } = require('./utils/logger');
const multer  = require('multer')
var FormData = require('form-data');
var upload = multer()

var app = express();

function checkSession(req, res, next){
    let user = getUser( req ) ;
    if( user.accessToken ) {
        next();
    } else {
        res.status(401).send('Invalid');
    }

}

function getUser( req ) {
    let session = req.session ;
    let user ;
    if( session ) {
        user = req.session.user ;
    } 
    if( !user ) {
        user = {
            "account": "",
        }
    }
    return user ;
}

function userLog( req ){
    let user = getUser( req ) ;
    let method = req.method ;
    let account = user.account ;
    let url = req.apiUrl ;
    return `[EMIAL:${account}]　[METHOD:${method}]　[PATH:${url}]\n`
}

function httpClient( req , headers = {} ){ 
    let url = req.apiUrl ;
    let method = req.method ;
    let http = new HttpUtil();
    let request = JSON.stringify( req.getParament() ) ;
    let header = JSON.stringify( headers ) ;

    return http[method](url,req.getParament() , headers )
    .then(function( response ){
       
        let status = response.status ;
        let data = response.data.data ? response.data.data : response.data ;
        if( method == "downloadExcel" ) {
            data = {} ;
        }

        logger.info("[API]　"+userLog( req )+`[STATUS:${status}]　[DATA:\n[CONTENT:${JSON.stringify({
    header: JSON.stringify( headers ) ,
    request: JSON.stringify( req.getParament() ),
    response: JSON.stringify( data ),
})}]`) ;
        return response ;
    });
}

function uploadForm( req , res , form , headers = {} ){
    let http = new HttpUtil();
    let user = getUser( req ) ;
    let accessToken = user.accessToken ;
    let url = req.apiUrl ;
    let functionId = req.headers["function-id"];

    headers = Object.assign(headers , {
        Authorization: `Bearer ${accessToken}`,
        'contentType' : "multipart/form-data" ,
        'function-id' : functionId ,
    })
    //console.log(headers);
    http['post']( url , form , {
        Authorization: `Bearer ${accessToken}`,
        'contentType' : "multipart/form-data"
    })
    .then((response)=>{
        let status = response.status ;
        let data = response.data ;
        logger.info("[API]　"+userLog( req )+`[STATUS:${status}]　[DATA:${ status != 200 ? JSON.stringify(data) : ''}]`) ; 
        res.status( status ).send( data );
    })

}

routes.use(( req , res , next ) =>{
    let headers = req.headers ;
    let downloadExcel = headers['downloadexcel'];
    let contentType = headers['contenttype'];
    let apiMath = req.method ;
    let method = req.method.toLowerCase() ;
    let data ;
    let url = req.apiUrl + req.url ;
    
    req.method = method ;

    if( contentType === 'multipart/form-data' ) {
        req.apiUrl = url ;
        next();
    } else {
        if( method == 'post' || method == 'put' || method == 'delete') {
            data = req.body ; 
        } else {
            data = req.query ;
            url = url.split("?")[0];
        }

        if( downloadExcel === 'downloadExcel' ) {
            req.method = 'downloadExcel' ;
        } else if( downloadExcel === 'downloadExcel2') {
            req.method = 'downloadExcel2' ;
             data = req.body ; 
        }
        
        req.getParament = function () {
            if( data ) {
                return data ;
            } else {
                return {} ;
            }   
        }
        
        req.apiUrl = url ;
        logger.info("[REQ]　"+userLog( req )+`[PARAM:${JSON.stringify(req.getParament())}]`) ;

        next();
    }
    
});

routes.use(`/user/login` , ( req , res , next ) =>{
    httpClient(req)
    .then(function(response){
        let data = response.data ; 
        if( response.status === 200 ) {
            req.session.user = {
                "accessToken": data.loginToken ,
                "account": data.userId ,
                "name": data.userName ,
            }

            data.loginToken = 'XXXX' ;
        }
        res.status(response.status).send(data);
    });
});

routes.use(`/user/logout`,(req, res, next)=>{
    httpClient(req) ;
    req.session.destroy();
    res.status(401).send('Invalid');
});

routes.use(`/store/batch`,upload.single('file'),[checkSession,(req, res, next)=>{
    let companyId = req.body.companyId ;
    
    
    const form = new FormData();
    let file = req.file ;

    form.append('file', file.buffer , file.originalname );
    form.append('companyId ', companyId);
    
    logger.info(userLog( req )+`[PARAM:{companyId:${companyId}}]`) ;
    uploadForm( req , res , form );
    
}]);

routes.use(`/read-sdk-config-file`,upload.single('file'),[checkSession,(req, res, next)=>{
    const form = new FormData();
    let file = req.file ;

    form.append('file', file.buffer , file.originalname );

    uploadForm( req , res , form );
    
}]);

routes.use(`/import-multi-spb-stores-upop`,upload.single('file'),[checkSession,(req, res, next)=>{
    const form = new FormData();
    let file = req.file ;

    form.append('file', file.buffer , file.originalname );

    uploadForm( req , res , form );
    
}]);

routes.use(`/create-multi-stores`,upload.single('merchantsFile'),[checkSession,(req, res, next)=>{
    const form = new FormData();
    let file = req.file ;

    form.append('merchantsFile', file.buffer , file.originalname );

    uploadForm( req , res , form );
    
}]);

routes.use(`/create-multi-stores-qrpay`,upload.single('merchantsFile'),[checkSession,(req, res, next)=>{
    let qrPayMerchantType = req.body.qrPayMerchantType ; 
    const form = new FormData();
    let file = req.file ;

    form.append('merchantsFile', file.buffer , file.originalname );
    form.append('qrPayMerchantType ', qrPayMerchantType);

    logger.info(userLog( req )+`[PARAM:{qrPayMerchantType:${qrPayMerchantType}}]`) ;
    
    uploadForm( req , res , form );
    
}]);

routes.use(`/create-multi-user`,upload.single('file'),[checkSession,(req, res, next)=>{
    const form = new FormData();
    let file = req.file ;
    let body = req.body ;
    let mailTemperlate = body.mailTemperlate ;
    let roleId = body.roleId ;

    form.append('file', file.buffer , file.originalname );
    form.append('mailTemperlate', mailTemperlate );
    form.append('roleId', roleId );
    
    logger.info(userLog( req )+`[PARAM:{mailTemperlate:${mailTemperlate};roleId:${roleId}}]`) ;

    uploadForm( req , res , form );
    
}]);

routes.use(`/upload-icon`,upload.single('file'),[checkSession,(req, res, next)=>{
    const form = new FormData();
    let file = req.file ;
    let body = req.body ;
    let merId = body.merId ;
    
    form.append('file', file.buffer , file.originalname );
    form.append('merId', merId );

    logger.info(userLog( req )+`[PARAM:{merId:${merId}}]`) ;
    
    uploadForm( req , res , form );
    
}]);

routes.use(`/cms/upload-file`,upload.single('img'),[checkSession,(req, res, next)=>{
    const form = new FormData();
    let file = req.file ;
    let body = req.body ;
    let orderDtlNo  = body.orderDtlNo;
    let userId      = body.userId;
    let type        = body.type;

    form.append('img', file.buffer , file.originalname );
    form.append("orderDtlNo", orderDtlNo );
    form.append("userId", userId );
    form.append("type", type );
    logger.info(userLog( req )+`[PARAM:{orderDtlNo:${orderDtlNo},userId:${userId},type:${type}}]`) ;
    
    uploadForm( req , res , form );
    
}]);

routes.use([checkSession,( req, res, next ) => {
    //console.log(req.headers);
    let user = getUser( req ) ;

    let accessToken = user.accessToken ;
    let method = req.method ;
    let functionId = req.headers["function-id"];
    httpClient(req,{
        Authorization: `Bearer ${accessToken}`,
        "function-id" : functionId ,
    })
    .then((response)=>{
        let status = response.status ;
        let data = response.data ;

        let headers = response.headers ;

        if( headers ) {
            Object.keys(headers).forEach(function(key){
                //console.log(key+":"+response.headers[key]);
                res.setHeader(key,response.headers[key]);
            });
        }
        res.status( status ).send( data );

    })
 
}]);

module.exports = routes;