<template>
<div class="row p-3" ref = 'search' >
	<div v-if="checkInput('companyName')" class=" order-md-1 order-0"
	:class="getSizeCalss()">
		<label for="companyName" class="t-12">公司名稱</label>
		<input  type="text" name="companyName" id = "companyName" v-model="input.companyName"
		class="form-control text-left bg-white border-0"
		:class="{'is-invalid': !!invalids.companyName}">
		<div class="invalid-feedback" id = "invalid_companyName">{{invalids.companyName}}</div>
	</div>
	<div v-if="checkInput('taxIdNumber')" class=" order-md-1 order-0"
	:class="getSizeCalss()">
		<label for="taxIdNumber" class="t-12">統一編號</label>
		<input  type="text" name="taxIdNumber" id = "taxIdNumber" v-model="input.taxIdNumber"
		class="form-control text-left bg-white border-0"
		:class="{'is-invalid': !!invalids.taxIdNumber}">
		<div class="invalid-feedback" id = "invalid_uniformNumbers">{{invalids.taxIdNumber}}</div>
	</div>
	<div v-if="checkInput('categoryId')" class=" order-md-1 order-0"
	:class="getSizeCalss()">
		<div class="dropdown">
			<label for="categoryId" class="t-12">產業別</label>
			<SelectView :options="selOption.categoryId" :invalidTxt="invalids.categoryId"
			@updateVal="updateVal" ref = "categoryId"/>
		</div>
	</div>
	<div v-if="checkInput('storeId&environmentId')" class=" order-md-1 order-0"
	:class="getSizeCalss()">
		<label for = "storeId"
			class="t-12">商家代號</label>
		<input type="hidden" name="environmentId" id = "environmentId" v-model="input.environmentId">
		<div class="row">
		<div class="dropdown">
  			<button class="btn btn-secondary dropdown-toggle" href="#"  
  			data-toggle="dropdown" aria-expanded="false"
  			style="border-radius: 8px 0px 0px 8px !important;border: 0px !important; width:60px;">
    			<span>{{selOption.environmentId.text}}</span>
  			</button>
  			<div class="dropdown-menu">
    			<a class="dropdown-item" href="#" v-for=" item in selOption.environmentId.arr "
    			:key="'environmentId'+item.val"
				@click.prevent="onSelect('environmentId' ,item.val,item.text)">
					<span>{{item.text}}</span>
				</a>
  			</div>
		</div>
		<input type="text" name="storeId" id = "storeId" v-model="input.storeId"
			class="form-control text-left bg-white border-0"
			:class="{'is-invalid': !!invalids.storeId}"
			style="width:0px;display: inline;border-radius:0px 8px 8px 0px !important; flex: 1;">
		</div>
		<div class="invalid-feedback" id = "invalid_storeId">{{invalids.storeId}}</div>
	</div>
	<div v-else-if="checkInput('storeId')" class=" order-md-1 order-0"
	:class="getSizeCalss()">
		<label for="storeId" class="t-12">商家代號</label>
		<input type="text" name="storeId" id = "storeId" v-model="input.storeId"
			class="form-control text-left bg-white border-0"
			:class="{'is-invalid': !!invalids.storeId}">
		<div class="invalid-feedback" id = "invalid_storeId">{{invalids.storeId}}</div>
	</div>
	<div v-if="checkInput('acquirerMerchantId')" class=" order-md-1 order-0"
		:class="getSizeCalss()">
		<label for="acquirerMerchantId" class="t-12">MID</label>
		<input  type="text" name="acquirerMerchantId" id = "acquirerMerchantId" v-model="input.acquirerMerchantId"
		class="form-control text-left bg-white border-0"
		:class="{'is-invalid': !!invalids.acquirerMerchantId}">
		<div class="invalid-feedback" id = "invalid_acquirerMerchantId">{{invalids.acquirerMerchantId}}</div>
	</div>
	<div v-if="checkInput('acquirerId')" class=" order-md-1 order-0"
		:class="getSizeCalss()">
		<label for="acquirerId" class="t-12">收單銀行</label>
		<SelectView :options="selOption.acquirerId" :invalidTxt="invalids.acquirerId"
		@updateVal="updateVal" ref = "acquirerId"/>
		
	</div>
	<div v-if="checkInput('tagIds')" class=" order-md-1 order-0"
		:class="getSizeCalss()">
		<SelectView :options="selOption.tagIds" :invalidTxt="invalids.tagIds" LabelVal = "標籤"
		@updateVal="updateVal" ref = "tagIds"/>
	</div>	
	<div v-if="checkInput('sdate&edate')" class=" order-md-1 order-0"
	:class="getSizeCalss()">
		<label for="formfield01"
			class="t-12">交易日期區間</label>
		<div class="btn card ">
			<div class="card-body d-flex w-100 align-items-center text-left py-1 pl-0 pr-2">

				<div class="reportrange w-100">
					<div class="row">
						<div class="col t-14">
							{{ $filters.time( input.sdate ) }}
							~
							{{ $filters.time( input.edate ) }}
						</div>
						<div clas storeNoTypes="col-auto ml-auto">
							<img src="@/assets/img/ic-calendar.svg"
								class="img-fluid" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	
</div>

<div class="row mt-2 mb-5">
	<div class="col-md-4 offset-md-2 p-2">
		<div class="m-0">
			<button type="button" @click.prevent="search"
			class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
				<span class="mx-2">查詢</span>
			</button>
		</div>
	</div>
	<div class="col-md-4 p-2">
		<div class="m-0">
			<button type="button" @click.prevent="clear"
			class="btn btn-secondary  btn-block d-flex justify-content-center align-items-center">
				<span class="mx-2">取消</span>
			</button>
		</div>
	</div>
</div>

</template>
<script>

import { mount , unMount } from "@/utils/daterange";
import { getEnvironmentId , getCategoryId , getAcquirer , getTagId , getList } from "@/utils/dataListCrm";
import { mapGetters, mapState } from 'vuex';
import SelectView from "@/components/Select";

export default {
	props: {
		input : {
			type : Object ,//String,Number,Array,Object,Function,Boolean,Date,Symbol
			required : true
		},
		invalidFunc : {
			type : Object
		},
		option : {
			type : Object
		}
	},
	emits: {
		search : {
			type : Function ,
			required : true
		},
		clear : {
			type : Function ,
			required : false
		}
	},
	data() {
    	return {
           	size 		: 3 ,     
           	default 	: {} ,
           	invalids	: {} ,
           	selOption	: {/*{
	    		ID 			: "environmentId" ,
	    		func 		: getEnvironmentId.bind(this) ,
	    		setFirst 	: true ,
	    		selType 	: "dropdown"
	    	},*/
		    	categoryId : {
		    		ID 			: "categoryId" ,
		    		func 		: "user/getCategoryId",
		    		//placeholder : "請輸入產業別",
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
		    	acquirerId : {
		    		ID 			: "acquirerId" ,
		    		func 		: getAcquirer.bind(this) ,
		    		addNullText : true ,
		    		filter 	 	: true ,
		    		selType 	: "dropdown"
		    		//placeholder : "請輸入產業別",
		    	},
		    	tagIds : {
		    		ID 		: "tagIds" ,
		    		func 	: "user/getTagId" ,
		    		selType : "selMore" ,
		    	},
	    	}
        }
    },
        
    watch: {

    },
    computed: {
    	
    },
    components : {
    	SelectView
    },
    mounted() {
    	if( this.option && this.option.size ) {
    		this.size = this.option.size ;
    	}
    	this.default = Object.assign({},this.input);
    	this.renderDatePicker();
    	this.init();
    	let _this = this ;
    	$( this.$el.parentElement ).find('input').keyup(function( event ){
    		var keycode = ( event.keyCode ? event.keyCode : event.which );
            if ( keycode == '13' ) {
                _this.search();
            }
    	})    
    },
    methods: {
    	checkInput( key ) {
    		let arr = key.split('&');
    		let result = true ;
    		let _this = this ;
    		arr.forEach(function( k ){
    			if( result )
    			result = _this.input.hasOwnProperty( k );
    			if( !result )  {
    				return false ;
    			}
    		});
    		return result ;
    	},
    	getSizeCalss () {
    		return 'col-md-'+ parseInt ( 12 / this.size ) ;
    	},
    	clear(){
    		let _this = this ;
    		Object.keys(_this.default).forEach(function(key){
    			if( _this.selOption.hasOwnProperty( key ) ) {
    				let refs = _this.$refs[key] ;
    				if( refs && refs.clear ) {
    					refs.clear();
    				} else if( key == "environmentId" ) {
    					let selOption = _this.selOption[key];
    					_this.input.environmentId = selOption.defVal;
    					selOption.text = selOption.defText ;
    				}
    				
    			} else {
    				_this.input[key] = _this.default[key];
    			}
    			
    		});
    		this.$emit("clear");
    	},
    	search(){
    		let invalid = [] ;
    		if( checkInputVal.call( this ) ) {
    			this.$emit("search");
    		}
    	},
    	renderDatePicker: function () {
            var self = this;
            let sel = $(this.$refs.search).find('.reportrange') ;
            if( sel.length > 0 ) {
	            mount(sel, this.input.sdate, this.input.edate, function (s, e) {
	                self.input.sdate = s;
	                self.input.edate = e;
	            });
        	}
        },
        onSelect( id , value , text , index ) {
        	this.input[id] = value ;
        	this.selOption[id].text = text ;
        },
        init() {
        	$(this.$refs.search).find('input').each(function( index , input ){
        		input.autocomplete = 'off';
        	});

    	},
    	updateVal( val , ID ){
    		this.input[ID] = val ;
    	}
    },
    //銷毀前
    beforeUnmount(){
    	let sel = $(this.$refs.search).find('.reportrange') ;
    	if( sel.length > 0 ) {
    		unMount(sel);
    	}
    	
    },
    beforeMount(){

    	let _this = this ;
    	let getApiValues = [
	    	{
	    		ID 			: "environmentId" ,
	    		func 		: getEnvironmentId.bind(this) ,
	    		setFirst 	: true ,
	    		selType 	: "dropdown"
	    	}
    	]

    	getApiValues.forEach(function( obj ){
			let ID    = obj.ID ;
    		let selType = obj.selType ;
    		if( _this.input.hasOwnProperty( ID ) ) {
    			let selOption  = _this.selOption[ID] = {
    				arr 	: [] ,
		           	ID 		: ID ,
		           	defVal  : selType == "selMore" ? [] : "" ,
		           	defText : "" ,
		           	selType : selType ,
    			};
	    		obj.func( function( arr ){
	    			let selOption = _this.selOption[ID] ;
	    			selOption.arr = arr ;
	    			let first = arr[2] ;
	    			let setFirst = obj.setFirst ;
	    			if( obj.setNull && first.val == "" ) {
	    				selOption.nullText = first.text ;
	    				setFirst = true ;
	    			}
	    			if( obj.placeholder ) {
	    				selOption.placeholderText = obj.placeholder ;
	    			} else if( first && setFirst ){
	    				selOption.defText = first.text ;
	    				if( first.val )  {
	    					selOption.defVal = first.val ;
	    				}
	    				_this.input[ID] = first.val ;
	    			}
	    			if( ID == "environmentId" ) {
    					selOption.text = selOption.defText ;
    				}
	    		},obj.setNull); 		
    		}
    	});    			
    }
}

function checkInputVal() {
	let invalidFunc = this.invalidFunc ;
	let invalids = this.invalids = {} ;
	let result = true ;
	let input = this.input ;
	Object.keys( input ).forEach(function(key){
		let value = input[key];
		let text = "" ;
		if( invalidFunc && invalidFunc[key] ) {
			text = invalidFunc[key](value);
		} else {
			switch (key) {
				case 'companyName' : 
					/*
					if( !value || value == "" ) {
						text = "請輸入正確公司名稱"
					}*/
				break;
				case 'acquirerId' : 

				break ;
			}
		}
		if( text != "" ) {
			invalids = text ;
			result = false ;
		}
	});
	if( invalidFunc ) {
		let obj = Object.fromEntries(Object.entries(invalidFunc).filter(([key]) => key.includes("&")));
		Object.keys( obj ).forEach(function(key){
			let arr = key.split("&");
			let arr2 = [] ;
			for( var i = 0 ; i < arr.length ; i++ ) {			
				arr2.push(input[arr[i]]);
			}

			let text = invalidFunc[key](...arr2 )
			if( text != "" ) {
				for( var i = 0 ; i < arr.length ; i++ ) {
					let k = arr[i] ;
					let invalid = invalids[k] ;
					if( invalid && invalid != "" ) {
						invalids[k] = invalid + text ;
					} else {
						invalids[k] = text ;
					}
					
				}
				result = false ;
			}
		});
	}
	
	return result ;
}
</script>
