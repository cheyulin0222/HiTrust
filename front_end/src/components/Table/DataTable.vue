<template>
    <div class="col-12 my-3 ">
        <div class="table-responsive" style="overflow-x: visible;">
        	<table class="table table-hover m-0" v-bind:class="tableClass" v-bind:style="styleTable()" style="border-bottom:1px solid #dee2e6;">
        		<thead>
        			<tr class="colTr">
        				<template v-for="titleObj in getTitle">								
        					<th v-bind:colspan="colspan(titleObj)" 
            					v-bind:class="className(titleObj)"
            					v-bind:style="style(titleObj)"
                                class="text-center" scope="col"
        					>
        						<template v-if = "titleObj.type == 'checkBox'">
                                    <input type="checkbox"
            						@click="selectAll"
            						v-model="allSelected"
            						:value="dataRow">
        					    </template>     
                                <template v-else-if = "titleObj.endButton"  >
                                    <div class="row m-0 p-0 justify-content-center">
                                        <div v-if = "titleObj.text" :class="titleObj.endClass" style="max-width: 100%;">
                                            <span v-html = "titleObj.text" ></span>
                                        </div>
                                        <div v-for="endButton in titleObj.endButton" style="max-width: 100%;"
                                        @click.prevent= "endButton.func" role="button"
                                        :class="titleObj.endClass"
                                        >   
                                            <span>
                                                <i v-if ="endButton.icon" class="fa img-fluid onactived"
                                                :class = "endButton.icon" style="fontSize:24px" />
                                                {{ endButton.text }}
                                            </span>
                                        </div>
                                    </div>
                                </template>
                                <template v-else>
                                    <div v-if="titleObj.order" @click="doOrder(titleObj)">
                                        <span class="selectText" v-html = "titleObj.text"></span>
                                        <i v-show="titleObj.isClicked && titleObj.isUp" class="fa-solid fa-arrow-up"></i>
                                        <i v-show="titleObj.isClicked && !titleObj.isUp" class="fa-solid fa-arrow-down"></i>
                                        <i class="fa-solid fa-angle-up"></i>
                                    </div>
                                    <span v-else class="selectText" v-html = "titleObj.text"></span>
                                </template>
        					</th>
        				</template>
        			</tr>
        		</thead>
        		<tbody>
        			<template v-for="( payment , index ) in dataRow" :key="getRowKey(payment)" visisble="true">
        				<tr>
        					<template v-for="dataObj in getData(payment , index)" 
        							visisble="true">
        						<td 
        						v-if = "dataObj.type == 'checkBox' "
        						v-bind:colspan="colspan(dataObj)" 
        						v-bind:class="className(dataObj)"
        						v-bind:style="style(dataObj)"
                                class="text-center"
        						>
        							<input type="checkbox"
        							ref="checkbox"
        							v-model="selData"
        							:value="payment"
        							v-if="dataObj.selOption(payment)">
        						</td>
        						<td v-else-if = "dataObj.type == 'drop'" 
        						v-bind:colspan="colspan(dataObj)"
        						v-bind:class="className(dataObj,'dropdown')"
        						v-bind:style="style(dataObj)"
        						>
        							<span class="material-icons dropdown-toggle"
        							id="dropdownMenuButton"
        							data-toggle="dropdown"
        							aria-haspopup="true"
        							aria-expanded="false">
        							more_vert
        							</span>
        							<div class="dropdown-menu t-12"
        								aria-labelledby="dropdownMenuButton">

        								<div class="d-flex p-2" v-for="dropFun in checkArr(dataObj,payment)">				
        									<div v-html='dropFun.span'></div>
        									<a class=""
        										href="#"
        										@click.prevent= "dropFun.func">
        										{{ dropFun.text	}}
        									</a>
        								</div>
        							</div>
        						</td>
        						<td v-else-if = "dataObj.type == 'button'" 
        						v-bind:colspan="colspan(dataObj)"
        						v-bind:class="className(dataObj)"
        						v-bind:style="style(dataObj)"
        						>
        							<template v-for="buttonFun in checkArr(dataObj,payment)">
        								<div v-bind:class="className(buttonFun)"
        									@click.prevent= "buttonFun.func" :role="buttonFun.hasFunc ? 'button' : ''"
        								>
        									<i v-if ="buttonFun.icon" class="fa img-fluid onactived pr-md-3"
        									:class = "buttonFun.icon" style="fontSize:24px" />
        									{{ buttonFun.text }}
        								</div>
        							</template>
        						</td>
                                <td v-else-if = "dataObj.type == 'input'" 
                                v-bind:colspan="colspan(dataObj)"
                                v-bind:class="className(dataObj)"
                                >
                                    <input type="text"
                                    autofocus
                                    autocomplete="off"
                                    name = "tdInput"
                                    v-model="payment[dataObj.key]"
                                    @keyup="dataObj.keyup"
                                    @keydown="dataObj.keydown"
                                    @keypress="dataObj.keypress"
                                    class="form-control btn card text-left"
                                    style="font-size:0.75rem; min-height:auto;"/>
                                </td>
                                <td v-else-if = "dataObj.type == 'link'" 
                                    v-bind:colspan="colspan(dataObj)"
                                    v-bind:class="className(dataObj)"
                                    class="text-center"
                                >
                                    <a class="store_key_down_link" href="#"
                                        @click.prevent= "dataObj.func">
                                        {{ dataObj.text }}
                                    </a>
                                </td>	
        						<td v-else 
        						v-bind:colspan="colspan(dataObj)"
        						v-bind:class="className(dataObj)"
        						v-bind:style="style(dataObj)"
                                class="text-center"
        						>
        							<span class="selectText" v-html = "dataObj.text" ></span>
        						</td>
        					</template>
        				</tr>
        			</template>
        		</tbody>
        	</table>                 
            <div v-if="dataRow.length == 0 " class="text-center p-1 " style="fontSize:18px">查無資料</div>
        </div>
    </div>
</template>
<script >
/*
options : {
    colum : [ 
        {
            // 表格欄位
            type : 'checkBox'/'text'/'drop'/'button/input'
            value : [] , //表頭
            key : [], //欄位值，對應資料名稱
            colspan : 2 
            selOption : function ( payment ) checkBox用，可決定checkbox要不要顯示
            filter : function( value , key , payment ) text用，把顯示的值在加工 key 是FOR 上面key使用，可對某一KEY
            classT:'11', //title的class
            classR:'22', // Row的class
            styleT:'11', //title的style
            styleR:'22', // Row的style
            func : [  drop 用， 顯示<span></span><a func >{text}</a>
                {
                    span : `<span class="material-icons pr-2 t-14">
                                reorder
                            </span>`, //span的HTML
                    text : 'easypay.button.detail | t ' , //span的HTML
                    func : function (payment) {
                        sip.onShowDetail(payment);
                    },
                } ,
                {
                    span : `<span class="material-icons pr-2 t-14">
                                do_not_disturb_on
                            </span>`,
                    text : 'easypay.button.stop | t ' ,
                    func : function (payment) {
                        alert('stop SIP transaction')
                    }
                }
            ]
            {
           
            button : [ //For Button
                {
                    class : "sip-status-undone rounded-pill" ,
                    key : "statusDesc" ,          //顯示的值，會對應rowdata
                    filter : function( payment ){ // 條件
                        return payment.statusDesc!='失敗'
                    }
                },
                {
                    class : "sip-status-error  rounded-pill mr-1" ,
                    key : "statusDesc" ,
                    filter : function( payment ){
                        return payment.statusDesc==='失敗'
                    }
                },
                {
                    class : "sip-status-resend rounded-pill" ,
                    key : "重送" ,
                    filter : function( payment ){
                        return payment.statusDesc==='失敗'
                    },
                    func : function ( payment ){
                        sip.resend(payment);
                    }
                },
                
            ]
        } 
    ],
    rowKey     : "statusId" , //指定rowData的KEY
    tableClass : "table-scrollbar ",
    tableStyle : {
        'display': 'block' ,
        'overflow-x': 'auto' ,
        'white-space': 'nowrap' 
    },
    endButton : [
        {
            value : [""] ,
            icon: "fa-circle-plus" ,
            func : function ( payment ){
                _this.goTo({name:"StoreEdit",type:"add"})
            }
        }
    ],
}
*/
import { genNonDuplicateID , strToObjForStyle , htmlEscape } from "@/utils/util"

export default {
	data () {
        return {
            allSelected : false,
            selData     : [] ,
        };
    },
    props: {
        options : {
        	type : Object,
        } ,
        dataRow : {
        	type : Array ,
        },
    },
    emits:['updateSelData'],
    model: {

    },
    components : {

    },
    watch: { 
        selData : function(){
            this.$emit('updateSelData', this.selData);
            if(this.selData.length > 0 && this.selData.length == this.dataRow.length ) {
                this.allSelected = true
            } else {
                this.allSelected = false
            }
        } ,       
        dataRow : function ( a , b ) {
            this.selData = [] ;
        }
    },
    computed: {
        rowKey (){
            let rowKey = this.options.rowKey ;
            if( rowKey == undefined) {
                rowKey = "id" ;
            }
            return rowKey  ;
        },
        tableClass () {
            return this.options.tableClass ;
        } ,
        tableStyle (){
            return this.options.tableStyle ;
        } , 
        colum () {
            return this.options.colum ;
        } ,
        endTitle () {
            return this.options.endTitle ; 
        } ,
        getTitle () {
            let titles = [] ;
            let _this = this ;
            let length = this.colum.length ;
            this.colum.forEach(function( obj ,index){
                let result = {
                    class : obj.classT ,
                    colspan : obj.colspanT ,
                    style   : obj.styleT
                }
                let type = obj.type ;
                if( type == 'checkBox' ) {
                    result.type = 'checkBox';
                    if( obj.noSelectAll) {
                        result.text = _this.getTextValue(obj.value);
                        result.type =  'text'
                    }
                } else if ( type == 'drop') {
                    result.type = 'drop';
                    result.text = '';
                } else {
                    let value = obj.value ;
                    result.type = 'text';
                    result.text = _this.getTextValue(value);
                    if (obj.order) {
                        result.order = true;
                        result.isClicked = obj.isClicked;
                        result.isUp = obj.isUp;
                        result.key = obj.key[0];
                    }
                }
                if( index == ( length - 1 ) ) {
                    let endButton = _this.options.endButton ;
                    if( endButton ) {
                        endButton.forEach(function(item){
                            let value = item.value ;
                            item.text =  _this.getTextValue(value);
                        });
                        let endClass = "col-12 col-md-";
                        let length = result.text === '' ? 0 : 1 ;
                        length += endButton.length ;
                        endClass += (12/length);
                        result.endClass = endClass ;
                        result.endButton = endButton ; 
                    }
                }
                titles.push(result);
                            
            });             
            return titles ;   
        },
        setPage(){
            if( this.options.paginate ) {
                return true ;
            } else {
                return false ;
            }
        },
    },
    mounted() {
        
    },
    methods: {
        selectAll: function () {
            this.selData = [];
            if (!this.allSelected) {
                for(var i in this.dataRow){
                    this.selData.push(this.dataRow[i])
                }
            }
        },
        getTextValue( value ) {
            let txt = '' ;
            let _this = this ;
            value.forEach(function( v ){
                let txts = v.split('|');
                if( txts.length > 1 && txts[1].trim() == 't') {
                    txt += _this.$t(txts[0].trim()).toString();
                } else {
                    txt += v ;
                }
            })
            return txt ;
        },
        getValue( rowData , value , filter ) {
            let txts = value.split('|');
            let keys = txts[0].trim().split(".") ;
            let data = rowData[keys[0]];
            if( keys.length > 1 ) {
                for( var i = 1 ; i < keys.length ; i++ ) {
                    let key = keys[i];
                    data = data[key]
                }
            }
            let text ;
            if( !data && typeof data != "undefined" && data != 0 ) {
                data = "" ;
            }
            if( data || data == "" || data == 0 ) {
                data = htmlEscape(data);
                if( txts.length > 1 && txts[1].trim() == 'm') {
                    data = numeral(data/100).format('0,0.00');
                }
                if( filter ) {
                  data = filter(data,txts[0],rowData);
                }
                text = data ;
            } else {
                text = this.getTextValue([value])
            }
            return text ;
        },
        getDropFunS ( rowData , funs ) {
            let arr = [] ;
            let _this = this ;
            funs.forEach(function( obj ){
                let text = "" ;
                let result = {
                    span : obj.span ,
                    filter : obj.filter ,
                }
                if( obj.func ) {
                    result.func = obj.func.bind( null , rowData);
                }
                
                if( obj.text && obj.text != "" ) {
                    let objText = "" ;
                    if( typeof obj.text === "function" ) {
                        objText = obj.text( rowData ) ;
                    } else {
                        objText = obj.text ;
                    }
                    result.text = _this.getTextValue( [objText] ) ;
                } else {
                    result.text = text ;
                }
                arr.push(result);
            });
            return arr ;
            
        },
        getButtonFunS ( rowData , funs , index ) {
            let arr = [] ;
            let _this = this ;
            funs.forEach(function( obj ){
                let text =  _this.getValue( rowData , obj.key ) ;
                let result = {
                    filter  : obj.filter ,
                    class   : obj.class ,
                    icon 	: obj.icon
                }
                if( obj.func ) {
                    result.func = obj.func.bind( null , rowData , index );
                    result.hasFunc = true;
                } else {
                    result.func = function(){};
                    result.hasFunc = false;
                }
                
                if( obj.text && obj.text != "" ) {
                    let objText = "" ;
                    if( typeof obj.text === "function" ) {
                        objText = obj.text( rowData ) ;
                    } else {
                        objText = obj.text ;
                    }
                    result.text = _this.getTextValue( [objText] ) ;
                } else {
                    result.text = text ;
                }

                arr.push(result);
            });
            return arr ;
            
        },
        getData ( rowData , index ) {
            let Data = [] ;
            let _this = this ;
            this.colum.forEach(function( obj ){
                let key = obj.key ;
                let type = obj.type ;
                let result = {
                    class : obj.classR ,
                    colspan : obj.colspanR ,
                    style   : obj.styleR
                }
                if ( type == 'checkBox') {
                    result.type = 'checkBox' ;
                    if( obj.selOption ) {
                        result.selOption = obj.selOption ;   
                    } else {
                        result.selOption = function () { 
                            return true ; 
                        }
                    }
                        
                } else if ( type == 'index') {
                    result.type = 'index' ;
                    result.text = index + 1 ;
                } else if ( type == 'drop') {
                    result.type = 'drop' ;
                    result.arr = _this.getDropFunS(rowData , obj.func )
                } else if ( type == 'button') {
                    result.type = 'button' ;
                    result.arr = _this.getButtonFunS(rowData , obj.button , index ) ;

                } else if( type == 'text' || type == 'link'){
                    let text = ''
                    key.forEach(function( v ){                      
                        text += _this.getValue( rowData , v , obj.filter) ; 
                    });
                    result.type = 'text' ;

                    if( obj.text && obj.text != "" ) {
                        let objText = "" ;
                        if( typeof obj.text === "function" ) {
                            objText = obj.text( rowData ) ;
                        } else {
                            objText = obj.text ;
                        }
                        result.text = _this.getTextValue( [objText] ) ;
                    } else {
                        result.text = text ;
                    }
                    
                    if( type == 'link' ) {
                        result.func = obj.func.bind(_this,rowData);
                        result.type = 'link' ;
                    }   
                } else if( type == 'input' ){
                    let text = ''
                    key.forEach(function( v ){                      
                        text += _this.getValue( rowData , v , obj.filter) ; 
                    });
                    result.type = 'input' ;
                    result.key = key[0].split('|')[0].trim();
                    
                    if( obj.text && obj.text != "" ) {
                        let objText = "" ;
                        if( typeof obj.text === "function" ) {
                            objText = obj.text( rowData ) ;
                        } else {
                            objText = obj.text ;
                        }
                        result.text = _this.getTextValue( [objText] ) ;
                    } else {
                        result.text = text ;
                    }
                    
                    let resultKey = result.key ;
                    ["keyup","keydown","keypress"].forEach(function( e ){
                        if( obj[e] ) {
                            result[e] = obj[e].bind( null , rowData , resultKey ) ;
                        } else {
                            result[e] = function () { };
                        }
                    });
                }
                Data.push(result);                                
            });
            return Data ;   
        },
        colspan : function ( colum ) {
            return colum.colspan ? colum.colspan : 1
        },
        className : function ( colum , defaultClass ) {
            let result = "" ;
            if( colum.class ) {
                result = colum.class ;
            }
            if( defaultClass ) {
                if( result != "" ) {
                    result += " " ;
                }
                result += defaultClass ;
            }

            return result ;
        },
        style : function ( colum , defaultStyle = {} ) {

            let result = {} ;
            if( defaultStyle ) {

                if( typeof defaultStyle === "string" ) {
                    defaultStyle = strToObjForStyle(defaultStyle);
                }
                result = Object.assign({},defaultStyle) ;
            }
            if( colum.style ) {
                let style = colum.style ;
                if( typeof style === "string") {
                    style = strToObjForStyle(style);
                }
                result = Object.assign(result,style) ;
            }            
            return result ;
        },
        styleTable : function ( defaultStyle = {} ) {
            return this.style( {
                style : this.tableStyle 
            },defaultStyle) ;
        },
        checkArr : function ( colum , rowData ) {
            
            let arr = colum.arr ; 
            arr = arr.filter(function( ele ){
                let print = true ;
                if( ele.filter ) {
                    print = ele.filter( rowData ) ;
                }
                return print ;
            })
            return arr ;
        },
        reSetSel(){
            this.selData = [] ;
        },
        UUID(){
            return genNonDuplicateID();
        },
        getRowKey( payment ){
            let rowKey = this.rowKey ;
            let result = "" ;
            let _this = this ;
            rowKey.split("|").forEach(function( key , index ){
                key = key.trim();
                if( index > 0 ) {
                    result += "_" ;
                }
                result += payment[key] != undefined ? payment[key] : _this.UUID() ;
            })
            return result ;
        },
        doOrder(obj) {
            this.colum.forEach(e => {
                e.isClicked = false;
                if (e.value[0] === obj.text) {
                    e.isClicked = true;
                    e.isUp = !e.isUp
                }else {
                    e.isUp = false;
                }
            });
            this.$emit("re-order", obj.key);
        },
    },
}

</script>