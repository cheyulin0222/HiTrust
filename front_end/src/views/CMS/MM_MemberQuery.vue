<template>
	<div class="container-fluid">
		<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
			會員查詢
		</h4>
		<div class="row p-3" ref = 'search' >
			<CardItem colMd="6">
				<Input ID = "userId" LabelVal = "會員帳號(EMAIL)" :input = "input" :invalids = "invalids" maxlength="50"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "nameCh" LabelVal = "公司名稱" :input = "input" :invalids = "invalids" maxlength="50"/>
			</CardItem>
			<CardItem colMd="6">
				<DatePicker ref="datePicker" LabelVal = "最近登入日期"
					v-model="input.date"/>
			</CardItem>
			<CardItem colMd="6">
				<SelectView :options="selOption.orderType" :invalidTxt="invalids.orderType" LabelVal = "排序"
				v-model = "input['orderType']"  ref = "orderType"/>
			</CardItem>
		</div>
		<div class="row mt-2 mb-5">
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="clear"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">清除重設</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="search"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">查詢</span>
					</button>
				</div>
			</div>
		</div>
		<DataTable ref="table1"
		:dataRow="dataRow"
		:options ="tableOption">
		</DataTable>
	</div>
	
</template>
<script>
import Base from "@/components/RightViewBase" ;
import DataTable from '@/components/Table/DataTable.vue' ;
import SelectView from "@/components/Select" ;
import DatePicker from '@/components/DatePicker.vue' ;
import Input from "@/components/Input" ;
import CardItem from "@/components/CardItem" ;
import moment from 'moment' ;
import { isEmptyObject , isValidEmail } from "@/utils/util";

let defaultSel = {
	date 		: [ moment().subtract(1, 'months'), moment() ] ,
	userId 		: "",
	nameCh 	: "" ,
	orderType		: "USER_ID" ,
}

export default {

	data(){
		let _this = this ;
		let input = Object.assign({},defaultSel) ;
		return {
			selOption : {
				orderType : {
		    		ID 			: "orderType" ,
		    		func 		: [{
		    			text 	: "會員帳號" ,
						val		: "USER_ID" ,
		    		},
		    		{
		    			text 	: "公司名稱" ,
						val		: "NAME_CH" ,
		    		},
		    		{
		    			text 	: "登入時間" ,
						val		: "LOGIN_DTTM" ,
		    		}],
		    		setFirst 	: true ,
		    		selType 	: "dropdown",
		    		defVal 		: input.orderType 
		    	},
			},
			input : input,
			invalids : { 
				
			},
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['userId'],
						value : ['會員帳號'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['nameCh'],
						value : ['公司名稱']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['userStatus'],
						value : ['狀態'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['lastLoginDttm'],
						value : ['最近登入時間']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['status'],
						value : ["操作功能"],
						classR: "d-flex" ,
						styleT: "width:240px;",
						button : [
							{
								class : "sip-status-resend rounded-pill  mr-3" ,
								key : "詳細內容" ,
								func : function ( payment ){
									this.goNextPath("SA3001_1",{
		                            	userId : payment.userId
		                            })
								}.bind(this)
							},
							{
								class : "sip-status-resend rounded-pill" ,
								key : "密碼重置" ,
								func : function ( payment ){
									this.resetPassword(payment);
								}.bind(this)
							},
						]
					},
				],
			},
			dataRow : [],
		}
	},
	mounted() {
        
    },
	components : {
		DataTable , SelectView , DatePicker , Input , CardItem
	},
	extends : Base ,
	methods: {
		search(){
			let invalids = {};
			if (!isValidEmail(this.input.userId.trim())) {
				invalids["userId"] = "請輸入50字內email格式帳號"
			}

			this.invalids = invalids;
			if ( !isEmptyObject(invalids)) {
				return
			}

			let input = {
				startDate 	: this.input.date[0].format('YYYYMMDD'),
				endDate 	: this.input.date[1].format('YYYYMMDD'),
				userId 		: this.input.userId.trim(),
				nameCh 		: this.input.nameCh.trim(),
				OrderType 	: this.input.OrderType
			}

			let _this = this ;
			let params = {};
			_this.showLoading(true);

			Object.keys(input).forEach(key => {
				if (input[key] != "") {
					params[key] = input[key];
				}
			});

			this.ApiUtilCMS.get("/get-member", params)
			.then((res) => {
				if( res.success ) {
					let data = res.data;
					_this.dataRow  = data.member ;
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		resetPassword(member) {
			let previousData = JSON.stringify(member);
			let params = {
				previousData : previousData,
				userId : member.userId
			}
			
			let _this = this;
			_this.showLoading(true);
			this.ApiUtilCMS.post("/reset-member-password", params)
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					_this.showModel({
						title : data.message,
						message : data.message,
					});
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		doSelPage( page ){

		},
    	updateVal( val , ID ){
    		this.input[ID] = val ;
    	},
    	clear(){
			this.dataRow = [];
			this.invalids = {};
    		this.input = Object.assign({},defaultSel) ;
    	},
    	showMask(){
    		this.$refs.Mask.show();
    	},
    	hideMask(){
    		this.$refs.Mask.hide();
    	}
	}
}	
</script>