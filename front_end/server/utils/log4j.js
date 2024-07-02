// src/utils/log4js.ts
require('dotenv').config();

let IS_CLOUD = process.env.IS_CLOUD ;

let LogUtil ;

if( IS_CLOUD == 'true') {
  console.log(IS_CLOUD);
  const {Logging} = require('@google-cloud/logging');
  const logging = new Logging( process.env.CLOUD_PROJECT_ID);
  const log = logging.log( process.env.CLOUD_LOGGING_NAME);
  LogUtil =  {
    trace(...args) {
      const metadata = {
        severity: 'INFO',
      }
      log.write(log.entry(metadata, ...args));
    },

    debug(...args) {
      const metadata = {
        severity: 'INFO',
      }
      log.write(log.entry(metadata, ...args));
    },

    log(...args) {
      const metadata = {
        severity: 'INFO',
      }
      log.write(log.entry(metadata, ...args));
    },

    info(...args) {
      const metadata = {
        severity: 'INFO',
      }
      log.write(log.entry(metadata, ...args));
    },

    warn(...args) {
      const metadata = {
        severity: 'WARNING',
      }
      log.write(log.entry(metadata, ...args));
    },

    warning(...args) {
      const metadata = {
        severity: 'WARNING',
      }
      log.write(log.entry(metadata, ...args));
    },

    error(msg , ...args ) {
      const now = moment().format('MM/DD HH:mm:ss');
      const metadata = {
        severity: 'ERROR',
      }
      if( typeof msg === 'object') {
        log.write(log.entry(metadata, `${msg.message}\n${msg.stack}`));
      } else {
        log.write(log.entry(metadata, msg));
      }
      
    },

    fatal(...args) {
      const now = moment().format('MM/DD HH:mm:ss');
      const metadata = {
        severity: 'ERROR',
      }
      if( typeof msg === 'object') {
        log.write(log.entry(metadata, `${msg.message}\n${msg.stack}`));
      } else {
        log.write(log.entry(metadata, msg));
      }
    },

    /*access(...args) {
      const loggerCustom = Log4js.getLogger('http');
      loggerCustom.info(getStackTrace(), ...args);
    }*/
  }
} else {
  const Log4js = require('log4js');
  const moment = require('moment');
  const config = require('../log4j_conf');
  const colors = require('colors/safe');

  Log4js.addLayout('Awesome-nest', function ( logConfig ){
    return function(logEvent) {
      let moduleName = '';
      let position = '';
      let StackTraceAll = ''
      // 重寫log格式
      const messageList = [];
      logEvent.data.forEach((value) => {      
        if ( value.basename ) {
          moduleName = value.basename;
          // 設定顯示行數格式
          if (value.lineNumber && value.columnNumber) {
            position = `${value.lineNumber},${value.columnNumber}`;
          }

          if( value.StackTraceAll ){
            StackTraceAll = value.StackTraceAll ;
          }
          return;
        } 
        

        if (typeof value !== 'string') {
          //console.log(value.toString());
          return;
        }
        messageList.push(value);
      });

      // 訊息組成
      const messageOutput = messageList.join(' ');
      //行數戳記
      const positionOutput = position ? `${position}` : '';
      //時間戳記
      const dateOutput  = `[${moment(logEvent.startTime).format('YYYY-MM-DD HH:mm:ss')}]`;
      //程式戳記 
      const moduleOutput  = moduleName ? `[${moduleName}:${positionOutput}]` : '[LoggerService]';
      let levelOutput  = `[${logEvent.level}] - ${messageOutput}`;
      let msg = `${dateOutput}　${moduleOutput}　${levelOutput}${StackTraceAll}` ;
      console.log(msg)
      return msg ;
    }
  });


  Log4js.configure(config);


  const logger = Log4js.getLogger();
  //logger.level = LoggerLevel.TRACE;

  LogUtil =  {
    trace(...args) {
      logger.trace( ...args);
    },

    debug(...args) {
      logger.debug( ...args);
    },

    log(...args) {
      logger.info( ...args);
    },

    info(...args) {
      logger.info( ...args);
    },

    warn(...args) {
      logger.warn( ...args);
    },

    warning(...args) {
      logger.warn( ...args);
    },

    error(msg , ...args ) {
      const now = moment().format('MM/DD HH:mm:ss');
      if( typeof msg === 'object') {
        logger.error(`${msg.message}\n${msg.stack}`, ...args);
      } else {
        logger.error( msg , ...args);
      }
     
    },

    fatal(...args) {
      logger.fatal(getStackTrace(), ...args);
    },

  }

}

module.exports = LogUtil ;

