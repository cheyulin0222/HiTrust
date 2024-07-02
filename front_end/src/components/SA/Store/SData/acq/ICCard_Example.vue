<template>
	<CardItem colMd="3">
		<Input ID = "merAccount" LabelVal = "銀行轉入帳號(For IC Card Only)" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="3">
		<Input ID = "trxLtd" LabelVal = "交易金額限定(For IC Card Only)" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="3">
		<Input ID = "trxChk" LabelVal = "商店無交易警訊(For IC Card Only)" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" />
	</CardItem>
	<CardItem colMd="3">
		<Input ID = "masterPass" type = "file" LabelVal = "上傳MasterPass" :invalids = "invalids" ref = "masterPass" :readOnly = "readOnly"/>
	</CardItem>
	<CardItem colMd="3">
		<Input ID = "merDirect" LabelVal = "第二商代(MERDIRECT)" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" />
	</CardItem>
	<div class="order-md-1 col-md-12" />
	
</template>
<script>
import Base from "@/components/SA/Store/SData/Base";
import { isEmpty } from "@/utils/util";

export default {

	data(){
		return {
		}
	},
	extends : Base ,
	props : {
		compObj : {
			type : Object ,
		},
	},
	computed: {
		
	},
	components : {

	},
	mounted() {

    },
	methods: {
		
		setInvalids( invalids ){
			this.invalids = invalids ;
		},
		getMasterPass(){
			return this.$refs.masterPass.getFile();
		}
	}
}	
</script>