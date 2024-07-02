<template>

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
			return {}
		}
	}
}	
</script>