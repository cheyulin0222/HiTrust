<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<div class="p-3">
		<Card title = "系統設定" >
			<CardItem colMd="6">
				<Input ID = "tpId" LabelVal = "TPId" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "tpopEmail" LabelVal = "TPOPEMAIL" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "smtpHost" LabelVal = "SMTPHOST" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "payPage" LabelVal = "PAYPAGE" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "errPage" LabelVal = "ERRPAGE" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "tpKey" LabelVal = "TPKEY" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "ibmPm" LabelVal = "IBMPM" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "set" LabelVal = "SET" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "trace" LabelVal = "TRACE" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "log" LabelVal = "Log" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "bypass" LabelVal = "BYPASS" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "pmHost" LabelVal = "PMHOST" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "pmPort" LabelVal = "PMPORT" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "pmId" LabelVal = "PMId" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "pmPwd" LabelVal = "PMPWD" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "pmSSL" LabelVal = "PMSSL" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "visa3DPage" LabelVal = "VISA3DPAGE" :input = "input" :invalids = "invalids" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="6">
				<SelectView :options="selOption.threedRedirectModeFlag" :invalidTxt="invalids.threedRedirectModeFlag" LabelVal = "THREED_REDIRECT_MODE_FLAG" v-model = "input.threedRedirectModeFlag" ref = "threedRedirectModeFlag"/>
			</CardItem>
			<CardItem colMd="6">
				<SelectView :options="selOption.cspSriFlag" :invalidTxt="invalids.cspSriFlag" LabelVal = "CSP_SRI_FLAG" v-model = "input.cspSriFlag" ref = "cspSriFlag"/>
			</CardItem>
		</Card>
	</div>
	<div class="row p-3 mb-5 justify-content-md-center">
		<div class="col-md-6 order-1">
			<div class="m-0">
				<button type="button" @click.prevent="save"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">確定</span>
				</button>
			</div>
		</div>
	</div>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import SelectView from "@/components/Select" ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import { getOpenOffList } from "@/utils/dataListSA" ;
import { deepCopy } from "@/utils/util" ;

export default {
	data(){
		return {
			title 		: "系統設定" ,
			selOption 	: {
				threedRedirectModeFlag : {
		    		ID 			: "threedRedirectModeFlag" ,
		    		func 		: getOpenOffList ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
		    	cspSriFlag : {
		    		ID 			: "threedRedirectModeFlag" ,
		    		func 		: getOpenOffList ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
		    },
		    invalids: {} ,
		    input : {
			    "tpId": "",
			    "tpopEmail": "",
			    "smtpHost": "",
			    "payPage": "",
			    "errPage": "",
			    "tpKey": "",
			    "ibmPm": "",
			    "set": "",
			    "trace": "",
			    "log": "",
			    "bypass": "",
			    "pmHost": "",
			    "pmPort": "",
			    "pmId": "",
			    "pmPwd": "",
			    "pmSSL": "",
			    "visa3DPage": "",
			    "threedRedirectModeFlag" : "CLOSED" ,
			    "cspSriFlag" : "CLOSED" ,
			},
			

		}
	},
	extends : Base ,
	computed: {

	},
	watch: {     
       
    },
	components : {
		Input , SelectView , Card , CardItem
	},
	methods: {
		init(){
			let _this = this ;
			_this.showLoading(true);
			this.ApiUtilSA.get("get-system-setting")
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					_this.input = data ;
					_this.previousData = JSON.stringify(data);
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});

		},
		save(){
			let _this = this ;
			_this.showLoading(true);
			
			let param = deepCopy( this.input ) ;
			param.previousData = this.previousData ;
			
			this.ApiUtilSA.post("update-system-setting" , param )
			.then((res) => {
				if( res.success && res.data.success ) {
					this.showModel({
		                title 	: this.title ,
		                message : "修改成功",
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
			
		},
	},
	mounted () {

	},
	beforeUpdate () {

	},
	beforeMount(){
		
	},
	//銷毀前
    beforeUnmount(){
    	
    },
}	
</script>