const path = require('path');
require('dotenv').config();
const baseLogPath = path.resolve(process.env.LOG4J_PATH); //log4j的路徑

const log4jsConfig = {
  appenders: {
    console: {
      type: 'console', // 控制台顯示
      layout : {
        type : 'Awesome-nest' ,
      },
    },
    api: {
      type: 'dateFile', // 每日
      filename: `${baseLogPath}/api.log`, // 檔案名稱: api.20220101.log
      alwaysIncludePattern: true,
      pattern: 'yyyyMMdd',
      daysToKeep: 60,
      numBackups: 3,
      keepFileExt: true, // 是否保留文件後缀
      layout : {
        type : 'Awesome-nest' ,
      },
    },
    app: {
      type: 'dateFile',
      filename: `${baseLogPath}/crm_front.log`,
      alwaysIncludePattern: true,
      pattern: 'yyyyMMdd',
      daysToKeep: 60,
      // maxLogSize: 10485760,
      numBackups: 3,
      keepFileExt: true,
      layout : {
        type : 'Awesome-nest' ,
      },
    },
    errorFile: {
      type: 'dateFile',
      filename: `${baseLogPath}/crm_front_error.log`,
      alwaysIncludePattern: true,
      layout: {
        type: 'Awesome-nest',
      },
      pattern: 'yyyyMMdd',
      daysToKeep: 60,
      // maxLogSize: 10485760,
      numBackups: 3,
      keepFileExt: true,
    },
    errors: {
      type: 'logLevelFilter',
      level: 'ERROR',
      appender: 'errorFile',
    },
  },
  categories: {
    default: {
      //appenders: ['console', 'app', 'errors'],
      appenders: [ 'app', 'errors'],
      level: 'DEBUG',
    },
    info: { appenders: ['console', 'app', 'errors'], level: 'info' },
    access: { appenders: ['console', 'app', 'errors'], level: 'info' },
    //http: { appenders: ['api'], level: 'DEBUG' },
  },
};

module.exports = log4jsConfig;