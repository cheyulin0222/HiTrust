<template>
	<Risk :compObj = "compObj" :type = "type" ref="risk" :acqBank = "acqBank"/>
	<CardItem colMd="12" class="t-18">
		<hr class="hr-store">
		JKO Pay
	</CardItem>
	<CardItem colMd="12">
		<Input ID = "apiKey" LabelVal = "API-KEY" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="true" maxlength="256"/>
	</CardItem>
	<CardItem colMd="12">
		<Input ID = "storeId" LabelVal = "STORE-ID" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="true" maxlength="256"/>
	</CardItem>
	<CardItem colMd="12">
		<Input ID = "secretKey" LabelVal = "SECRETKEY" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="true" maxlength="256"/>
	</CardItem>

</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase"
import Risk from "@/components/SA/Store/SData/acq/Risk_Example"
import { isEmpty } from "@/utils/util";

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
				apiKey 			: "MTz6CiUPtuBTNkCRpVNxRa1gXD05DNXsy3uo" ,
				storeId 		: "" ,
				secretKey 		: "yuGX9N1W7ZDnH3maBqWBXtIgIIrseOOZdcqB3-Zao7v-XHgYSjFs1wz7PWiuTmqyyTKFfNKHgVELeaD_EMi4gg" ,
			});
		},
		checkData(){
			let invalids = {} ;
			let compObj = this.compObj ;
			if( compObj["apiKey"] === "" ) {
				invalids["apiKey"] = "請輸入API-KEY";
			}
			if( compObj["storeId"] === "" ) {
				invalids["storeId"] = "請輸入STORE-ID";
			}
			if( compObj["secretKey"] === "" ) {
				invalids["secretKey"] = "請輸入SECRETKEY";
			}
			this.invalids = invalids ;
			return isEmpty(invalids);
		}
	}
}	
</script>