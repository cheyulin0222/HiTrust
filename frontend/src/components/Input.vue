<template>
    <template v-if="showLable" >
        <template v-if=" getType === 'file' || readOnly " >
            <span class = "pl-2 d-inline-block input-text m-0" v-if=" !!LabelVal " :class="getLabelClass" v-bind:style="styleTxt">
                {{LabelVal}}
            </span>
        </template>
        <template v-else>
            <label class = "pl-2 input-text  m-0" :for="getID" v-if=" !!LabelVal " :class="getLabelClass" v-bind:style="styleTxt">
                {{LabelVal}}
            </label>
        </template>
        <i v-if="!!tooltip" class="fas fa-question-circle input-tooltip" data-toggle="tooltip" :title="tooltip"/>
    </template>
    
    <template v-if=" getType === 'file' " >
        <input type="file" ref="files" :class="getInputClass" :disabled="readOnly" />
    </template>
    <template v-else-if=" readOnly " >
        <div class="selectText disable input disabled" :class="getInputClass" v-bind:style="disableStyle">
            <template v-if="isModelValue" >
                {{ modelValue }}
            </template>
            <template v-else >
                {{ input[getInputID] }}
            </template>
        </div>
    </template>
    <template v-else >
        <template v-if=" getType === 'text' || getType === 'number' || type === 'password' " >
            <template v-if="isModelValue" >
                <input :type="getType" :name="getID" :id = "getID" v-model="modelValue" :class="getInputClass" :placeholder = "placeholder ? placeholder : '' " :maxlength = "maxlength" @keyup="inputKeyup">
            </template>
            <template v-else >
                <input :type="getType" :name="getID" :id = "getID" v-model="input[getInputID]" :class="getInputClass" :placeholder = "placeholder ? placeholder : '' " :maxlength = "maxlength" @keyup="inputKeyup">
            </template>

        </template>
        <template v-else-if=" getType === 'checkbox'" >
            <input type="checkbox" @click="click">
        </template>
    </template>
    
    <div v-if =" invalids " class="invalid-feedback">
        {{ getInvalidsTxt }}
    </div>
    
</template>
<script>
import { genNonDuplicateID } from "@/utils/util"

export default {

	data(){
		return {		
			
		}
	},
	props: {
        //有值就會顯示標題(Label)
        LabelVal : {
        	type : String,
        } ,
        //必填
        ID : {
        	type : String,
        },
        //會抓input[ID]的值,雙向綁定
        input : {
            type : Object ,
        },
        //顯示錯誤值
        invalids : {
        	type : Object ,
        },
        // 目前:number/text
        type : {
        	type : String,
        },
        // input的class ,會全部替換,沒帶有預設CLASS
        inputClass : {
        	type : String,
        },
        // input的style ,會全部替換,沒帶有預設style
        inputStyle : {
            type : String,
        },
        //是否唯讀 true/false
        readOnly : {
            type : Boolean ,
        },
        required : {
            type : Boolean ,
        },
        tooltip : {
            type : String ,
        },
        placeholder : {
            type : String ,
        },
        IDRandom : {
            type : Boolean ,
        },
        maxlength : {
            type : String ,
        },
        modelValue : {
            type : [String, Number],
        },
        canSelect : {
            type : Boolean ,
        }
    },
    emits:["clickFunc","update:modelValue","cickUpFunc"],
    components : {

	},
	mounted() {

    },
    watch: {
        modelValue (newVal, oldVal) {
            this.$emit('update:modelValue', newVal )
        },
    },
    computed: {
    	getType () {
    		if( !!this.type ) {
    			return this.type ;
    		} else {
    			return "text";
    		}
    	},
    	getInputClass () {
    		let className ;
    		if( this.inputClass ) {
    			className = this.inputClass ;
    		} else {
                if( this.type === "file" ) {
                    className = "form-control border-0" ;
                } else {
                    className = "form-control text-left  border-0" ;
                    if ( !this.readOnly ) {
                        className += " bg-white" ;
                    }
                }
    		}
    		if( this.getInvalidsTxt != "" ) {
    			className += " is-invalid"
    		}
    		return className ;
    	},
        getLabelClass(){
            if( this.required ) {
                return 'required' ;
            }
        },
    	getInputID(){
    		let ID = this.ID ;
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
    	getInvalidsTxt(){
            let ID = this.getInputID ;
    		if( this.invalids && !!this.invalids[ID] ) {
    			return this.invalids[ID] ;
    		} else {
    			return "" ;
    		}
    	},
        showLable(){
            let show = !!this.LabelVal
            return  show ;
        },
        getID(){
            let id = this.getInputID ;
            if( this.IDRandom ) {
                id += genNonDuplicateID() ;
            }
            return id ;
        },
        isModelValue(){
            return this.modelValue != undefined ;
        },
        styleTxt(){
            if( this.inputStyle ) {
                return inputStyle;
            }
            let style = {
                "max-width" : "calc( 100% - 5px )" ,
            }
            if( !!this.tooltip ) {
                style["max-width"] = "calc( 100% - 25px )";
            }

            
            return style ;
        },
        disableStyle(){
            let style = {} ;
            
            if( this.canSelect && this.readOnly ) {
                style["pointerEvents"] = "auto";
            } 

            return style ;
        }
    },
	methods: {
		getFile(){
            if( this.type === "file" ) {
                return this.$refs.files.files;
            }
        },
        clearFile(){
            if( this.type === "file" ) {
                this.$refs.files.value = "" ;
            }
        },
        UUID(){
            return genNonDuplicateID();
        },
        click(){
            this.$emit('clickFunc');
        },
        inputKeyup( event ){
            this.$emit('cickUpFunc' , event );
        }
	}
}	
</script>