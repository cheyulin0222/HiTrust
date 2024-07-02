<template>

	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<div class="p-3">
		<Card title = "批次人員管理新增" >
			<CardItem colMd="12">
				<Input ID = "FILE" type = "file" LabelVal = "選擇您的xls檔案(僅接受xls類型之檔案)" :invalids = "invalids" ref = "files"/>
			</CardItem>
			<CardItem colMd="6">
				<SelectView :options="selOption.mailTemplate" :invalidTxt="invalids.mailTemplate" LabelVal = "選擇信件格式"
				@updateVal="updateVal" ref = "mailTemplate"/>
			</CardItem>
			<CardItem colMd="6">
				<SelectView :options="selOption.roleId" :invalidTxt="invalids.roleId" LabelVal = "選擇權限群組"
				@updateVal="updateVal" ref = "roleId"/>
			</CardItem>
		</Card>
	</div>
	<div class="row p-3 mb-5 justify-content-md-center">
		<div class="col-md-6 order-1">
			<div class="m-0">
				<button type="button" @click.prevent="updateFile"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">確定</span>
				</button>
			</div>
		</div>
	</div>
	<DataTable ref="table1" :dataRow="dataRow" :options ="tableOption"/>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import SelectView from "@/components/Select";
import DataTable from "@/components/Table/PageDataTable.vue" ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import { resetDefaultVal } from "@/utils/util"
import { getRoleNameList } from "@/utils/dataListSA" ;

export default {
	data(){
		return {
			title 		: "批次人員管理新增" ,
			selOption 	: {
				roleId : {
		    		ID 			: "roleId" ,
		    		func 		: getRoleNameList.bind(this, false ) ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown",
		    		defVal 		: "MerN_Easypay2"
		    	},
		    	mailTemplate : {
		    		ID 			: "mailTemplate" ,
		    		func 		: [
		    		{
		    			text 	: "一般商家" ,
						val		: "HT" ,
		    		},
		    		{
		    			text 	: "永豐多元支付商家" ,
						val		: "SPB" ,
		    		},
		    		
		    		{
		    			text 	: "台北富邦聚合支付商家" ,
						val		: "TFB" ,
		    		},
		    		{
		    			text 	: "台新ONE碼支付商家" ,
						val		: "TSB" ,
		    		},
		    		{
		    			text 	: "台新GoShare商家" ,
						val		: "TSB_GOS" ,
		    		}] ,
		    		defVal 		: "SPB"  ,
		    		selType 	: "dropdown"
		    	}
		    },
		    tableOption : {
		    	colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['merId'],
						value : ['商家代碼'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['userId'],
						value : ['SC帳號']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['userPwd'],
						value : ['SC密碼'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['createResult'],
						value : ['開通結果'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['mailTemplate'],
						value : ['通知類型']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['notifyResult'],
						value : ['寄送結果'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['genre'],
						value : ['處理類別'],
					}
				],
				tableStyle : {
					'font-size' : '9pt'
				}
		    },
		    invalids 	: {} ,
		    dataRow		: [] ,
		    input : {
		    	roleId 			: "" ,
		    	mailTemplate 	: "" ,
		    }
		}
	},
	extends : Base ,
	computed: {

	},
	watch: {     
       
    },
	components : {
		Input , DataTable , SelectView , Card , CardItem
	},
	methods: {
		updateFile(){
			let file = this.$refs.files.getFile();
			let input = this.input ;
			let invalids = {} ;
			if( file.length < 1 ) {
				invalids.FILE = "未上傳檔案" ;
				this.invalids = invalids ;
				return ;
			}

			this.invalids = invalids ;

			const form = new FormData()
			form.append('file', file[0])
			form.append('mailTemperlate', input.mailTemplate );
			form.append('roleId', input.roleId );

			let _this = this ;

			_this.showLoading(true);
			
			this.ApiUtilSA.post("create-multi-user" , form )
			.then((res) => {
				if( res.success ) {
					this.showModel({
		                title 	: this.title ,
		                message :"批次人員新增成功",
		                endFunc : function(){
		                	_this.dataRow = res.data ;
		                }
		            });
				} else {
					_this.dataRow = [] ;
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		updateVal( val , ID ){
    		this.input[ID] = val ;
    	},
    	getDefaultInput(){
    		return {
		    	roleId 			: "" ,
		    	mailTemplate 	: "" ,
		    }
    	}
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