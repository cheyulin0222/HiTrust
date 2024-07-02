<template>
	<Risk :compObj = "compObj" :type = "type" ref="risk" :acqBank = "acqBank"/>
</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase"
import Risk from "@/components/SA/Store/SData/acq/Risk_Upop_Example"

export default {

	data(){
		let _this = this ;
		let compObj = this.editCopyObj() ;

		return {			
			compObj 	: compObj ,
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

			return Object.assign(this.defaultComp2() , {
				refund 		: "OPEN" ,
				unCapture	: "CLOSED" ,
				alert 		: "CLOSED" ,
			});
		},
		checkData(){
			return true ;
		},
	}
}	
</script>