<template>
	<Risk :compObj = "compObj" :type = "type" ref="risk" :acqBank = "acqBank"/>
	<CardItem colMd="4">
		<Input ID = "ezwContractNo" LabelVal = "商店手續費合約編號" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="true" maxlength="30"/>
	</CardItem>
	<CardItem colMd="4">
		<Input ID = "ezwExecutorId" LabelVal = "管理員帳號" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="true" maxlength="20"/>
	</CardItem>
	<CardItem colMd="4">
		<Input ID = "ezwMacKey" LabelVal = "安全碼" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="true" maxlength="64"/>
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
				ezwContractNo  	: "" ,
				ezwExecutorId  	: "" ,
				ezwMacKey 		: "" ,
			});
		},
		checkData(){
			let invalids = {} ;
			let compObj = this.compObj ;
			let ezwContractNo =  compObj["ezwContractNo"].trim();
			let ezwExecutorId =  compObj["ezwExecutorId"].trim();
			let ezwMacKey =  compObj["ezwMacKey"].trim();

			if( ezwContractNo === "" ) {
				invalids["ezwContractNo"] = "請輸入商店手續費合約編號" ;
			} else {
				compObj["ezwContractNo"] = ezwContractNo ;
			}

			if( ezwExecutorId === "" ) {
				invalids["ezwExecutorId"] = "請輸入管理員帳號" ;
			} else {
				compObj["ezwExecutorId"] = ezwExecutorId ;
			}

			if( ezwMacKey === "" ) {
				invalids["ezwMacKey"] = "請輸入安全碼" ;
			} else {
				compObj["ezwMacKey"] = ezwMacKey ;
			}

			this.invalids = invalids ;
			
			return isEmpty(invalids);
		}
	}
}	
</script>