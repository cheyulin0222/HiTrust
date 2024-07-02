<template>
	<Risk :compObj = "compObj" :type = "type" ref="risk" :acqBank = "acqBank"/>
	<CardItem colMd="12" class="t-18">
		<hr class="hr-store">
		EVONET
	</CardItem>
	<CardItem colMd="12">
		<Input ID = "evonetSignKey" LabelVal = "SignKey" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="true"/>
	</CardItem>
</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase" ;
import Risk from "@/components/SA/Store/SData/acq/Risk_Example" ;
import { isEmpty } from "@/utils/util" ;

export default {

	data(){
		let _this = this ;
		let compObj = this.editCopyObj() ;
		return {
			compObj : compObj ,
		}
	},
	extends : Base ,
	components : {
		Risk
	},
	mounted() {

    },
	methods: {
		defaultCompObj(){
			return Object.assign(this.defaultComp() , {				
				evonetSignKey  : "" ,
			});
		},
		checkData(){
			let invalids = {} ;
			let compObj = this.compObj ;
			let evonetSignKey =  compObj["evonetSignKey"].trim();
			
			if( evonetSignKey === "" ) {
				invalids["evonetSignKey"] = "請輸入EVONET_SignKey" ;
			} else {
				compObj["evonetSignKey"] = evonetSignKey ;
			}

			this.invalids = invalids ;
			
			return isEmpty(invalids);
		}
	}
}	
</script>