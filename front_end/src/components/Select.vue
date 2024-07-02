<template >
	<template v-if= "showLabel">
		<template v-if=" readOnly || options.selType == 'selMore' ">
			<span class = "pl-2 d-inline-block input-text m-0" v-if=" !!LabelVal " :class="getLabelClass" v-bind:style="styleTxt">
				{{LabelVal}}
			</span>
		</template>
		<template v-else>
			<label class = "pl-2 input-text m-0" :for="getID" :class="getLabelClass" v-bind:style="styleTxt">
				{{LabelVal}}
			</label>
		</template>
		<i v-if="!!tooltip" class="fas fa-question-circle input-tooltip" data-toggle="tooltip" :title="tooltip"/>
	</template>

	<template v-if =" readOnly ">
		<div class="selectText disable input disabled form-control text-left border-0 dropdown-toggle">
            {{ text }}
        </div>
	</template>
	<template v-else-if="options.selType == 'dropdown' ">		
		<div class="dropdown" ref="select">
			<input  type="text"  v-model="text" data-toggle="dropdown" v-on:keyup="checkInput" 
			aria-haspopup="true" aria-expanded="false" :placeholder = "placeholder"
			class="form-control text-left bg-white border-0 dropdown-toggle" :id = "getID"
			:class="{'is-invalid': !!invalidTxt }">
			<div class="invalid-feedback">{{ invalidTxt }}</div>
			<div class="dropdown-menu merchant-dropdown-menu pre-scrollable" style="max-height: 300px;">
    			<a class="dropdown-item" href="#" v-for=" item in filterByInput "
    			:key="options.ID+item.val"
				@click.prevent="selectOne(item.val,item.text)">
					<span>{{item.text}}</span>
				</a>
  			</div>
		</div>
	</template>
	<template v-else-if="options.selType == 'selMore' ">
		<div class="dropdown" ref="select">
			<div data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false" :placeholder = "placeholder" class="form-control text-left bg-white border-0 dropdown-toggle btn " :class="{'is-invalid': !!invalidTxt }" style="display: flex;align-items: center;">
				{{text}}
			</div>
			<div class="invalid-feedback">{{ invalidTxt }}</div>
			<div class="dropdown-menu merchant-dropdown-menu pre-scrollable" style="max-height: 300px; ">
				<div style="padding:.25rem .5rem ;">
					<input :id = " getID + '_MORE'" type="text" v-model = "filterText" style="width: 100%;padding: .25rem 1rem;min-height:28px;">
				</div>
    			<a class="dropdown-item" href="#" v-for=" item in filterByInput "
    			:key="options.ID+item.val"
				@click.prevent="selectMore(item.val,item.text)">
					<span>{{item.text}}</span>
					<i v-if="checkSel(item)" class="fa fa-check" style="float: right;" />
				</a>
				
  			</div>
		</div>
	</template>

</template>
<script>
	import {getListVal} from "@/utils/dataList";
	import store from "@/store";
	import { genNonDuplicateID } from "@/utils/util"
	/*

		PS:若addNullText為TURE FUNC要用dataList的FUNC，其他直接查store :'user/getEnvironmentId'
		ID:對應父類別 input 的 key 
		options : {
	    	ID 			: "environmentId" ,
	    	func 		: getEnvironmentId ,
	    	setFirst 	: true , // 預設第一個
	    	addNullText : true ,   // 新增請選擇(文字由 dataList.ts 裡的func控制)
	    	selType 	: "dropdown" //"dropdown","selMore" ,
	    	defVal 		: "" ,
	    	filter 		: true ,
	    }

	*/

	export default {
		props : {
			options : {
				type : Object ,
				required : true
			},
			invalidTxt : {
				type : String
			},
			payment : {
				type : Object
			},
			LabelVal : {
	        	type : String,
	        },
	        readOnly : {
	        	type : Boolean,
	        },
	        required : {
	            type : Boolean ,
	        },
	        tooltip : {
	            type : String ,
	        },
	        IDRandom : {
	            type : Boolean ,
	        },
	        modelValue : {
	            type : [String, Number,Array],
	        }
		},
		emits:['updateVal',"update:modelValue"],
		data () {
			let selType = this.options.selType ;
			let val = selType == "selMore" ? [] : "" ;
			return {
				val 			: selType == "selMore" ? [] : "" ,
				arr 			: [] ,
				filterText 		: "" ,
				text 			: "" ,
				defText 		: "" ,
				defVal 			: selType == "selMore" ? [] : "" ,
				placeholderText : this.options.placeholderText ,
				nullText 		: "" ,
				intervals 		: [] ,
				isEmit 			: false ,
			}
		},
		methods : {
			selectOne ( value , text ) {
				this.val = value ;
        		this.text = this.getText( text ) ;
        		this.updateVal();
			},
			updateVal() {
				if( this.isModelValue ) {
					this.isEmit = true ;
					this.$emit('update:modelValue', this.val ) ;
				} else if( this.payment ) {
					this.$emit("updateVal" , this.val , this.options.ID , this.payment )
				} else {
					this.$emit("updateVal" , this.val , this.options.ID )
				}
				
			},
			selectMore ( value , text ) {
				let val = this.val ;
				let index = val.indexOf(value) ;
				if( index > -1 ) {
					val.splice(index, 1);
					this.text = this.text.replace(text+",","").replace(text,"");
					if( this.text.endsWith(",") ) {
						this.text = this.text.slice(0, -1)
					} 
				} else {
					val.push(value);
					if( this.text != "" ) {
						this.text  += "," ;
					}
					this.text  += text ;
				}
				this.updateVal();
			},
			checkSel( item ) {
				if( item && item.val != "") {
					return this.val.indexOf(item.val) > -1 ;
				} else {
					return false ;
				}
				
			},
			clear(){
				if( this.options.selType === 'selMore' ) {
					this.val = Array.from(this.defVal)
				} else {
					this.val = this.defVal ;
				}

        		this.text = this.defText;
        		this.updateVal();
			},
			clearInterval2 (Interval){
				let intervals = this.intervals;
				let index = intervals.indexOf(Interval) ;
				if( index > -1 ) {
					intervals.splice(index, 1);
				}
				clearInterval(Interval);
			},
			doCheckDef( arr , checkDef ) {
				this.arr = arr ;
				let options = this.options ;
				let update = false ;
				let checkFirst = true ;
				if( checkDef ) {
					let val = getListVal(arr,this.defVal) ;
					if( val != "" ) {
						this.defText = this.text = this.getText( val ) ;
						
						checkFirst = false ;
					} else {
						this.val = this.defVal = "" ;
						update = true ;
					}
    			} 
    			
    			if( checkFirst && options.selType == "dropdown" ){
    				let first = arr[0] ;
	    			let setFirst = options.setFirst ;
	    			if( options.addNullText && first.val == "" ) {
	    				this.nullText = first.text ;
	    				setFirst = true ;
	    			}
	    			if( first && setFirst ){
	    				this.defText = this.text = this.getText( first.text ) ;

	    				if( first.val && first.val != "" )  {
	    					this.defVal = first.val ;
	    					this.val = first.val ;
	    					update = true ;
	    				}
	    			}
    			}

    			if( update) {
    				this.updateVal();
    			}
    			
			},
			setDefVal( defVal ){
				let text = "" ;
				if( Array.isArray( defVal ) ) {
					let arr = [] ;
					let _this = this ;
					defVal.forEach(function( def ){
						let val = getListVal(_this.arr,def);
						if( val != "" ) {
							arr.push(def);
							if( text != "" ) {
								text  += "," ;
							}
							text  += val ;
						}
					});

					this.text 	= this.getText( text );
					this.val 	= arr ;
				} else {
					let text = getListVal(this.arr,defVal) ;
					if( text === "" ) {
						this.val = "" ;
						if( this.nullText != "" ) {
							this.text  = "" ;
						} else {
							this.text = "請選擇" ;
						}
						
						if( defVal != "" ) {
							this.updateVal();
						}
						
					} else {
						this.val = defVal ; 
			    		this.text = text ;
					}
				}
			},
			setArr ( arr , defVal ) {
				this.arr = arr ;
				if( defVal ) {
					this.defVal = defVal ;
				}
				this.doCheckDef( arr , !!defVal);
			},
			getArr () {
				return this.arr ;
			},
			getDefVal () {
				return this.defVal ;
			},
			checkInput () {
				if( this.options.checkInput  ) {
					var _this = this;
					let arr = this.arr.filter( ( merchantOptions ) => {
		            	let value = _this.text.toLowerCase() ;		            
		            	if( value != "" && value != this.nullText ) {
		            		if( typeof merchantOptions.text === "string" ) {
		            			return merchantOptions.text.toLowerCase() === value ;
		            		}
		            		return merchantOptions.text.toString() === value ;
		            	} else {
		            		return false ;
		            	}
		            });
		            if( arr.length == 1 ) {
		            	let obj = arr[0];
		        		_this.selectOne(obj.val , obj.text);
		            }
				}
			},
			getText( text ){
				if( text === this.nullText ) {
					return "" ;
				} else {
					return text ;
				}
			}
		},
		computed: {
    		filterByInput : function () { 
				if( this.options.filter || this.options.selType === 'selMore' ) {
					var _this = this;
					let value = this.text.toLowerCase() ;
					let selOption = this.options ;
		            let nullText = this.nullText ;
		            return this.arr.filter( ( merchantOptions ) => {
		            	
		            	if( _this.options.selType === 'selMore') {
							value = _this.filterText.toLowerCase();
		            	}
		            	
		            	if( value != "" && value != this.nullText ) {
		            		if( typeof merchantOptions.text === "string" ) {
		            			return merchantOptions.text.toLowerCase().indexOf(value) > -1 ;
		            		}
		            		return merchantOptions.text.toString().indexOf(value) > -1 
		            	} else {
		            		return true ;
		            	}
		            	
		            });
				} else {
					return this.arr ;
				}
	    		
	    	},
	    	getLabelClass(){
	            if( this.required ) {
	                return 'required' ;
	            }
	        },
	        showLabel(){
	            let show = !!this.LabelVal
	            return  show ;
	        },
	        getID(){
	        	let ID = this.options.ID ;
	            if( ID ) {
	                let IDs = ID.split(".");
	                if( IDs.length > 1 ) {
	                    ID = IDs[IDs.length-1];
	                }
	                return ID;
	            } else {
	                return genNonDuplicateID();
	            }
	        },
	        isModelValue(){
	            return this.modelValue != undefined ;
	        },
	        styleTxt(){
	            let style = {
	                "max-width" : "100%" ,
	            }
	            if( !!this.tooltip ) {
	                style["max-width"] = "calc( 100vw - 18px )";
	            }
	            return style ;
	        },
	        placeholder(){
	        	let options = this.options ;
	        	if( options.placeholderText ) {
	        		return options.placeholderText ;
	        	} else {
	        		return this.nullText ;
	        	}
	        }
    	},
    	mounted (){

    		if( this.options.selType === 'selMore' ) {
	    		$(this.$refs.select).on("click", "a" ,function (e) {
			        e.stopPropagation();
			    });
    		}

    		$( this.$el.parentElement ).find('input').each(function( index , input ){
        		input.autocomplete = 'off';
        	})     
    	},
    	watch:{
		    "options.defVal" : function ( newV , oldV ){
		    	/*if( newV ) {
		    		this.defVal = newV ;
			    	let selType = this.options.selType ;
			    	if( ( selType == "dropdown" && this.val == "" ) ||
			    		( selType == "selMore" && this.val.length == 0 )){
			    		this.val = newV ; 
			    		this.text = getListVal(this.arr,newV) ; 
			    	}
		    	}*/
		    	
		    },
		    "options.placeholderText" : function ( newV , oldV ){
		      this.placeholderText = newV
		    },
		    "options.func" : function ( newV , oldV ){
		      //console.log(newV);
		    },
		    modelValue : function ( newV , oldV ){
		      	if( this.isEmit ) {
		      		this.isEmit = false ;
		     	} else {
		      		this.setDefVal(newV);
		      	}
		    },
  		},
  		beforeMount(){
  			let options = this.options
  			let _this = this ;
    		let ID    = options.ID ;
    		let checkDef = false ;
    		let selType = options.selType ;
    		
    		if( typeof options.defVal != "undefined" && options.defVal != null ) {
    			if( selType == "dropdown" && !( typeof options.defVal === "string" && options.defVal === "" )) {
    				this.defVal = options.defVal ;
    				this.val = options.defVal ;
    				checkDef = true ;
    			} else if( selType == "selMore" && options.defVal.length > 0 ) {
    				this.defVal = Array.from(options.defVal);
    				this.val = Array.from(options.defVal);
    				checkDef = true ;
    			}
    		}
    		let func = options.func ;
    		if( typeof func === "string" ) {
    			let arr = store.getters[func];
    			if( arr.length > 0 ) {
    				this.doCheckDef(arr , checkDef);
    			} else {
    				let interval = setInterval(()=>{
    					let arr = store.getters[func];
						if( arr.length > 0 ) {
							_this.doCheckDef( arr , checkDef );
							_this.clearInterval2(interval);
						}
					} , 500 ) ;
					this.intervals.push(interval);
    			}
    		} else if( Array.isArray(func) ) {
    			this.doCheckDef( func  , checkDef);
    		} else if( typeof func  === "function" ){
    			func( function( arr ){
	    			_this.doCheckDef(arr , checkDef);
	    		},options.addNullText);  
    		}
  		},
		//銷毀前
	    beforeUnmount(){
	    	let _this = this ;
	    	this.intervals.forEach(function(interval){
	    		_this.clearInterval2(interval); 
	    	})
	    }
	}

</script>