<template>
	<VmjData :compObj = "compObj" :type = "type" ref="VmjData" :acqBank = "acqBank"/>
	<Risk :compObj = "compObj" :type = "type" ref="risk" :acqBank = "acqBank"/>
</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase"
import VmjData from "@/components/SA/Store/SData/acq/VMJ_Data_Example"
import Risk from "@/components/SA/Store/SData/acq/Risk_Example"

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
				merVisaPWD		: "SKBmc123" ,
				merMasterPWD	: "SKBmc123" ,
				merJcbPWD		: "SKBMC001" ,
				riskVisa		: "OPEN" ,
				riskMaster		: "OPEN" ,
				riskJcb			: "OPEN" ,
			});
		},
		checkData(){
			let VmjData = this.$refs.VmjData.checkData();
			return VmjData ;
		}
	}
}	
</script>