export function firstUpperCase( text ){
  return text.length > 0 ? text[0].toLocaleUpperCase().concat(text.slice(1)) : text ;
}

export function genNonDuplicateID(){
  let idStr = Date.now().toString(36)
    idStr += Math.random().toString(36).substr(3)
    return idStr
}

export function isEmptyObject(value) {
  return value && Object.keys(value).length === 0 && value.constructor === Object;
}

export function isValidEmail(str) {
  let regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]*$/;
  return  (!str || str.length <= 50 && regex.test(str)) 
}

export function htmlEscape(str) { //HTML轉譯
  var escapesMap = {
      '<': '&lt;',
      '>': '&gt;',
      '"': '&quot;',
      "'": '&#039;'
  }
  var reUnescapedHtml = new RegExp(/[<>"']/g);
  return (str && reUnescapedHtml.test(str)) ? 
  str.replace(reUnescapedHtml, function(chr) {
      return escapesMap[chr];
  }) : (str || "");
}

export function htmlUnEscape(str) { //反轉譯
  var unescapes = {
      '&amp;': '&',
      '&lt;': '<',
      '&gt;': '>',
      '&quot;': '"',
      '&#39;': "'"
  }
  var reEscapedHtml = new RegExp(/&(?:amp|lt|gt|quot|#39);/g);
  return (str && reEscapedHtml.test(str)) ? 
  str.replace(reEscapedHtml, function(entity) {
      return unescapes[entity];
  }) : (str || '')
}

export function strToObjForStyle(str){
   return stringToObj(str,";");
}

export function stringToObj(str,split){
  let obj = {};
  str.split(split).forEach(function(text){
      let t = text.split(":");
      obj[t[0]] = t[1];
  });
  return obj ;
}

export function isEmpty ( val ){
  if (val == null) return true;

  if (typeof val === 'boolean') return false;

  if (typeof val === 'number') return !val;

  if (val instanceof Error) return val.message === '';

  switch (Object.prototype.toString.call(val)) {
    // String or Array
    case '[object String]':
    case '[object Array]':
      return !val.length;

    // Map or Set or File
    case '[object File]':
    case '[object Map]':
    case '[object Set]': {
      return !val.size;
    }
    // Plain Object
    case '[object Object]': {
      return !Object.keys(val).length;
    }
  }

  return false;
}

export function isNotBlank ( value ){
  return value && value != "" ;
}

export function resetDefaultVal ( data , defaultData ){
  resetVal ( data , defaultData , Object.keys(defaultData) )
}

export function resetInputtVal ( data , defaultData ){
  resetVal ( data , defaultData , Object.keys(data) )
}

export function resetVal ( data , defaultData , keys = [] ){
  let defaultObj = JSON.parse(JSON.stringify(defaultData));
  keys.forEach(function( key ){
    let value = defaultObj[key];
    if( typeof value !== 'undefined' || value != null ) {
      data[key] = value;
    }
  })
}

export function parseInt( value ){
  if( typeof value === "string") {
    //value = value.split('.')[0];
    value = value.replace(',','');
  }
  return window.parseInt(value);
}

export function parseDouble( value ){
  if( typeof value === "string") {
    //value = value.split('.')[0];
    value = value.replace(',','');
    return parseFloat(value);
  } else {
    return value
  }
}

export function checkDifference( val1 , val2 ) {
  let type1 = typeof val1 ;
  let type2 = typeof val2 ;
  if( val1 === null ) {
    type1 = "undefined";
  }
  if( val2 === null ) {
    type2 = "undefined";
  }
  if( type1 != type2 ) {
    return true ;
  } else if( type1 === "object" ){
    let keys1 = Object.keys( val1 ) ;
    let keys2 = Object.keys( val2 ) ;
    if( keys1.length != keys2.length ) {
      return true ;
    }
    let diff = false ;
    keys1.every(function( key ){
      if( val1[key] != val2[key] ) {
        diff = true ;
        return false ;
      } else {
        return true ;
      }
    })
    return diff ;
  } else {
    return val1 != val2 ;
  }
}

export function deepCopy(data, hash = new WeakMap()) {
  
  if(typeof data !== 'object' || data === null){
    throw new TypeError('傳入參數不是物件')
  }
  
  // 判斷傳入物件是否存在HASH中
  if(hash.has(data)) {
    return hash.get(data)
  }
  
  let newData = {};
  
  const dataKeys = Object.keys(data);
  
  dataKeys.forEach(value => {
    const currentDataValue = data[value];
    // 基本數據類型的值和函數直接賦值拷貝
    if (typeof currentDataValue !== "object" || currentDataValue === null) {
      newData[value] = currentDataValue;
    } else if (Array.isArray(currentDataValue)) {
       // 實現數據組的深拷貝
       newData[value] = [...currentDataValue];
    } else if (currentDataValue instanceof Set) {
      // 實現set數據的深拷貝
      newData[value] = new Set([...currentDataValue]);
    } else if (currentDataValue instanceof Map) {
      // 實現MAP數據的深拷貝
      newData[value] = new Map([...currentDataValue]);
    } else { 
      // 將這待拷貝對象的引用存入hash中
      hash.set(data,data)
      // 普通對象則遞迴賦值
      newData[value] = deepCopy(currentDataValue, hash);
    } 
  }); 
  return newData;
}

export function getRandomString( size , upper ){
  let str = [] ;
  for (let i = 97; i < 122; i++) {
    let char = String.fromCharCode(i)
    str.push(char)
    if( upper ) {
      str.push(char.toUpperCase())
    }
    
  }
  for (let i = 0; i <= 9; i++) {
    str.push(i)
  }
  var result = []
  
  for (let i = 1; i <= size; i++) {
    result.push( str[Math.floor(Math.random() * str.length)] ) ;
  }
  return result.join("");
}

export function inputMaxLength( event , byteLength , title = "" , showAlert = true ){
  let target = event.target ;
  let value = target.value ;
  let isComposing = event.isComposing ;
  if( !isComposing ) {
    let replaceTxt = "" ;
    var chinSize = 3 ;
    for( var i = 0 ; i < 3 ; i++ ) {
      replaceTxt += "*"
    }

    var newvalue = value.replace(/[^\x00-\xff]/g, replaceTxt ); 
    var length = newvalue.length; 

    //當填寫的字節數小於設置的字節數 
    if (length * 1 <=byteLength * 1){ 
        return; 
    } 
    var limitDate = newvalue.substr(0, byteLength); 
    var count = 0; 
    var limitvalue = ""; 
    for (var i = 0; i < limitDate.length; i++) { 
      var flat = limitDate.substr(i, 1); 
      if (flat == "*") { 
        count++; 
      } 
    } 
    var size = 0; 
    var istar = newvalue.substr(byteLength * 1 - 1, 1);//校驗點是否爲“×” 
    
    //if 基點是×; 判斷在基點內有×爲偶數還是奇數
    let num = count % chinSize
    if ( num == 0) { 
      //當爲偶數時 
      size = count / chinSize + (byteLength * 1 - count); 
      limitvalue = value.substr(0, size); 
    } else { 
      //當爲奇數時 
      size = (count - num ) / chinSize + (byteLength * 1 - count); 
      limitvalue = value.substr(0, size); 
    }
    if( showAlert ) {
      alert( (title ? "[ "+title+" ] " : "" )+ "最大可輸入" + byteLength + "個字節（相當於"+ Math.floor( byteLength / chinSize ) +"個漢字）！");     
    }
    if( limitvalue != target.value ) {
      target.value = limitvalue; 
    }
    return limitvalue ;
  }
}

export function hasChinese(str) {
  return /[\u4E00-\u9FA5]+/g.test(str);
}

export function isAllChinese(str) {
  return /^[\u4E00-\u9FA5]+$/.test(str);
}

export function getFileExtension( filename ) {
  return filename.slice((filename.lastIndexOf(".") - 1 >>> 0) + 2);
}
