<template>
	<div class=" order-md-1 order-0 col-md-6">
		<Input ID = "AAA" LabelVal = "AAA" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit"/>
	</div>
	<div class=" order-md-1 order-0 col-md-6">
		<Input ID = "BBB" LabelVal = "BBB" :input = "compObj" :invalids = "invalids" :readOnly = "readOnlyEdit"/>
	</div>
</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase"

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

	},
	mounted() {

    },
	methods: {
		defaultCompObj(){
			let defaultCompObj = {
				AAA : '',
				BBB : '2',
			}
			return JSON.parse(JSON.stringify(defaultCompObj));
		}
	}
}	
</script>