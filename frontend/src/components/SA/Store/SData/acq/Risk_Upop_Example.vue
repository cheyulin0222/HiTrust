<template>
	<template v-for =" ( obj , index ) in other ">
		<CardItem colMd="3">
			<SelectView :options="selOption[obj.key]" :invalidTxt="invalids[obj.key]" :LabelVal = "obj.value" @updateVal="updateVal" :ref = "obj.key" :readOnly = "readOnly" />
		</CardItem>
	</template>
	<CardItem colMd="12" class="mt-3"/>
</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase"
import { getOpenOffList } from "@/utils/dataListSA";

export default {

	data(){
		let _this = this ;
		let selOption = {} ;
		let other = [{
			key 	: "refund",
			value 	: "退款功能"
		},
		{
			key 	: "unCapture",
			value 	: "請退款金額檢核"
		},
		{
			key 	: "alert",
			value 	: "未請款提示(授權後15~60天)"
		}]	

		this.setSelOption( selOption , other );

		return {
			selOption 	: selOption ,
			other 		: other ,
		}
	},
	extends : Base ,
	components : {

	},
	props : {
		compObj : {
			type : Object ,
		},
		acqBank : {
			type : String ,
		}		
	},
	mounted() {

    },
	methods: {
		updateVal( val , ID ){
			this.compObj[ID] = val ;
		},
		setSelOption( obj , arr ){
			
			let compObj = this.compObj ;

			arr.forEach(function( o ){
				let key = o.key ;
				let defVal = compObj[key] ? compObj[key] : "CLOSED"  ;
				obj[key] = {
					ID 			: key ,
		    		func 		: getOpenOffList.bind(this) ,
		    		selType 	: "dropdown" ,
		    		//filter 		: true ,
		    		defVal 		: defVal
				}
			});
		},
	}
}	
</script>