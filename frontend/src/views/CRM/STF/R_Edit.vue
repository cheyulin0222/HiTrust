<template >

	<div class="p-3">
		<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
			<div class = "p-2 text-center ">
				{{typeTxt}}手續費費率
			</div>
			
			<div class="row p-3 rounded bg-light m-0">
				<div class=" order-md-1 order-0 col-md-6">
					<Input ID = "settingName" LabelVal = "名稱" :input = "input" :invalids = "invalids" :readOnly="type!='ADD_NAME'"/>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="notes" class="t-12">說明</label>
					<input  type="text" name="notes" id = "notes" v-model="input.notes"
					class="form-control text-left bg-white border-0 selectText"
					:class="{'is-invalid': !!invalids.notes}">
					<div class="invalid-feedback" id = "invalid_notes">{{invalids.notes}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="STFType" class="t-12">手機費率類型</label>
					<SelectView :options="selOption.STFType" :invalidTxt="invalids.STFType"
					@updateVal="updateVal" ref = "STFType"/>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<Input type = "number" ID = "miniNumber" LabelVal = "級距下限" :input = "input.config" :invalids = "invalids" />
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="feeRate" class="t-12">手續費費率(%)</label>
					<input  type="number" name="feeRate" id = "feeRate" v-model="input.config.feeRate" step="0.01"
					class="form-control text-left bg-white border-0"
					:class="{'is-invalid': !!invalids.feeRate}">
					<div class="invalid-feedback" id = "invalid_notes">{{invalids.feeRate}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="maxiNumber" class="t-12">級距上限</label>
					<input  type="number" name="maxiNumber" id = "maxiNumber" v-model="input.config.maxiNumber"
					class="form-control text-left bg-white border-0"
					:class="{'is-invalid': !!invalids.maxiNumber}">
					<div class="invalid-feedback" id = "invalid_notes">{{invalids.maxiNumber}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="fixedFee" class="t-12">固定手續費</label>
					<input  type="number" name="fixedFee" id = "fixedFee" v-model="input.config.fixedFee"
					class="form-control text-left bg-white border-0"
					:class="{'is-invalid': !!invalids.fixedFee}">
					<div class="invalid-feedback" id = "invalid_notes">{{invalids.fixedFee}}</div>
				</div>		
			</div>
		</div>
	</div>
</template>
<script>
import SelectView from "@/components/Select" ;
import Input from "@/components/Input"

export default {
	data(){
		
		return {
			selOption	: {
				STFType	: {
					ID			: "STFType" ,
					func		: this.getSTFType.bind(this),
					selType		: "dropdown" ,
				},
			},
			invalids : {
				
			}
		}		 
	},
    methods : {
	    updateVal( val , id ){
	    	this.input.config.feeType = val ;
	    },
	    getSTFType( callBack ){
	    	callBack( this.STFType ) ;
	    }
    },
    computed: {
    	typeTxt : function() {
    		if( this.type.startsWith("ADD") ) {
    			return "新增"
    		} else {
    			return "編輯"
    		}
    	}
    },
    watch : {

    	"input.config": function(){
    		let feeType = "AMOUNT" ;
			if( this.type === "EDIT_CONFIG" ) {
				feeType = this.input.config.feeType
			}
			this.$refs.STFType.setDefVal(feeType);
			this.input.config.feeType = feeType ;
    	},
    },
    props : {
    	type : {
			type : String 
		},
		input : {
			type : Object
		},
		STFType : {
			type : Object 
		}
    },
    components : {
    	SelectView , Input
    },
    mounted() {
        this.$refs.STFType.setDefVal("AMOUNT");
    },
}
</script>