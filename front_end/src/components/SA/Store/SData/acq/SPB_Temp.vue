<template>
	<VmjData :compObj = "compObj" :type = "type" ref="VmjData" :acqBank = "acqBank"/>
	<Risk :compObj = "compObj" :type = "type" ref="risk" :acqBank = "acqBank"/>
	<CardItem colMd="12" class="mt-3"/>
	<CardItem colMd="6">
		<SelectView :options="selOption['spbUpopEnable']" :invalidTxt="invalids['spbUpopEnable']" LabelVal = "開啟銀聯功能" @updateVal="updateVal" ref = "spbUpopEnable" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "macKeyId" LabelVal = "MACKEY" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="macKeyReq"/>
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
			selOption : {
				spbUpopEnable : {
					ID 			: "spbUpopEnable" ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: compObj["spbUpopEnable"] ? compObj["spbUpopEnable"] : "CLOSED"  ,
				}
			}
		}
	},
	extends : Base ,
	components : {
		VmjData , Risk
	},
	mounted() {

    },
    computed: {
		macKeyReq() {
			return this.compObj["spbUpopEnable"] === "OPEN" ;
		}
	},
	methods: {
		defaultCompObj(){
			return Object.assign(this.defaultComp() , {
				macKeyId 		: "" ,
				spbUpopEnable 	: "CLOSED" ,
			});
		},
		checkData(){
			let VmjData = this.$refs.VmjData.checkData();
			let invalids = {} ;
			let macKeyId = this.compObj["macKeyId"].trim();
			this.compObj["macKeyId"] = macKeyId ;
			if( this.compObj["spbUpopEnable"] === "OPEN" && macKeyId === "" ) {
				invalids["macKeyId"] = "請輸入MACKEY";
			}
			this.invalids = invalids ;
			return VmjData && isEmpty(invalids) ;
		},
		updateVal( val , ID ){
			this.compObj[ID] = val ;
		}
	}
}	
</script>