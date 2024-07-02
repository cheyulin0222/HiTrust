import { createI18n } from 'vue-i18n'
// 載入語言
import tw from './locales/zh-tw';
import en from './locales/en.js';

export default createI18n({
  locale: 'zh-tw',           // 設定語言
  fallbackLocale: 'zh-tw',   // 若選擇的語言缺少翻譯則退回的語言
  messages: {
    'zh-tw' : tw , 
    'en' : en ,
  }

});