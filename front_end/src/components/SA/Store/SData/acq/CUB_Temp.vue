<template>
	<VmjData :compObj = "compObj" :type = "type" ref="VmjData" :acqBank = "acqBank"/>
	<Risk :compObj = "compObj" :type = "type" ref="risk" :acqBank = "acqBank"/>
	<CardItem colMd="12" class="mt-3"/>
	<CardItem colMd="6">
		<Input ID = "macKeyId" LabelVal = "MACKEY" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="required"/>
	</CardItem>
</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase"
import VmjData from "@/components/SA/Store/SData/acq/VMJ_Data_Example"
import Risk from "@/components/SA/Store/SData/acq/Risk_Example"
import { getOpenOffList } from "@/utils/dataListSA";
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
		VmjData , Risk
	},
	mounted() {

    },
	methods: {
		defaultCompObj(){
			return Object.assign(this.defaultComp() , {
				macKeyId 		: "" ,
			});
		},
		checkData(){
			let invalids = {} ;
			let compObj = this.compObj ;
			let macKeyId =  compObj["macKeyId"].trim();
			let VmjData = this.$refs.VmjData.checkData();

			compObj["macKeyId"] = macKeyId ;
			if( macKeyId === "" ) {
				invalids["macKeyId"] = "請輸入MACKEY";
			}
			this.invalids = invalids ;
			
			return VmjData && isEmpty(invalids);
		}
	}
}	
</script>