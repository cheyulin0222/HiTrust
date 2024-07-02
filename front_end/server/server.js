const express = require('express');
var path = require('path');
const session = require('express-session');
var apiServer = require('./apiServer.js');
const bodyParser = require('body-parser');
require('dotenv').config();
var app = express();

app.set('trust proxy', true);


app.use(session({
	secret: process.env.AP_NAME, // sessionID
	name : "CRM",
	resave: false,
	saveUninitialized: true,
	//cookie: { maxAge: process.env.SESSION_TIMEOUT * 60 * 1000 } // env.SESSION_TIMEOUT 分鐘到期
	rolling:true
}));

function checkSession(req, res, next){
	let user = req.session.user
	if( user && user.accessToken) {
		next();
	} else {
		res.status(401).send('Invalid');
	}

}
app.use("*",(req, res, next)=>{
	next();
});

app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.json());

app.use(`/${process.env.AP_NAME}/getEnv`, function (req, res, next) {
  	res.send({
		AP_NAME 	            : process.env.AP_NAME ,
		MENU_COLLAPSE 			: process.env.MENU_COLLAPSE ,
		API_URL			 		: process.env.API_URL ,
		API_TIMEOUT				: process.env.API_TIMEOUT ,
		ICON_PATH 				: process.env.ICON_PATH ,
		TEST_PATH 				: process.env.TEST_PATH ,
  	});
});

app.use(`/${process.env.AP_NAME}/api`,function (req, res, next) {
 	req.apiUrl = process.env.API_URL ;
 	req.sysType = "CRM";
 	next();
} , apiServer);

app.use(`/${process.env.AP_NAME}/apiCMS`,function (req, res, next) {
 	req.apiUrl = process.env.API_CMS_URL ;
 	req.sysType = "CMS";
 	next();
} , apiServer);

app.use(`/${process.env.AP_NAME}/apiSA`,function (req, res, next) {
 	req.apiUrl = process.env.API_SA_URL ;
 	req.sysType = "SA";
 	next();
} , apiServer);

app.use(`/${process.env.AP_NAME}/static`,express.static(__dirname + '/build/static'));
app.use(`/${process.env.AP_NAME}/favicon.ico`,express.static(__dirname + '/build/favicon.ico'));
app.use(`/${process.env.AP_NAME}/*`,express.static(__dirname + '/build'));

const port = process.env.PORT || 3001;

app.listen(port, () => {
	console.log(`Server running at ${port}`);
});	

function getIp( req ){
	var ip = request.headers['x-forwarded-for'] ;
    if( !ip ) {
        ip = request.socket.remoteAddress.split(':').pop();
    } else {
        ip = ip.split(',')[0].split(":")[0];
    }
	return ip ;
}