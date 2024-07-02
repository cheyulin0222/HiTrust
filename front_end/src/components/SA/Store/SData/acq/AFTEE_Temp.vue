<template>
	<Risk :compObj = "compObj" :type = "type" ref="risk" :acqBank = "acqBank"/>
	<CardItem colMd="12" class="t-18">
		<hr class="hr-store">
		AFTEE
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "afteePublicKey" LabelVal = "Public Key" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="true"/>
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "afteeSecretKey" LabelVal = "Secret Key" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="true" />
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
				afteePublicKey  : "" ,
				afteeSecretKey  : "" ,
			})
		},
		checkData(){
			let invalids = {} ;
			let compObj = this.compObj ;
			let afteePublicKey =  compObj["afteePublicKey"].trim();
			let afteeSecretKey =  compObj["afteeSecretKey"].trim();
			
			if( afteePublicKey === "" ) {
				invalids["afteePublicKey"] = "請輸入Public Key" ;
			} else {
				compObj["afteePublicKey"] = afteePublicKey ;
			}

			if( afteeSecretKey === "" ) {
				invalids["afteeSecretKey"] = "請輸入Secret Key" ;
			} else {
				compObj["afteeSecretKey"] = afteeSecretKey ;
			}

			this.invalids = invalids ;
			
			return isEmpty(invalids);
		}
	}
}	
</script>