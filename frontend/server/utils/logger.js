const colors = require('colors/safe');
const log4j = require('./log4j');
const moment = require('moment');
const { getStackTraceAll } = require('./log4j');
const logger = {
    log(...values) {
        //console.log(...values);
        log4j.info(...values);
    },
    info(...values) {
        //console.log(...values);
        log4j.info(...values);
    },
    error( msg , ...values) {
        const now = moment().format('MM/DD HH:mm:ss');
        let content ;
        if( typeof msg === 'object') {
            content = `[${now}] ${msg.message}\n${msg.stack}` ;
        } else {
            content = `[${now}] ${msg}` ;
        }
        //console.log(...values);
        log4j.error(msg ,...values);
    },
    warn(...values) {
        //console.log(...values);
        log4j.warn(...values);
    },
    success(...values) {
        //console.log(...values);
        log4j.info(...values);
    },
};
module.exports.logger = logger;
