<template>
	<div class="container-fluid">
		<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
			訂單管理-訂單查詢
		</h4>
		<div class="row p-3" ref = 'search' >
			<div class=" order-md-1 order-0 col-md-6 mb-2">
				<label for="dateType_ORDER_DATE" class="t-12">
					<input id = "dateType_ORDER_DATE" type="radio" name = "dateType" value="ORDER_DATE" v-model="input.dateType" /> 訂購日期範圍
				</label>
				<DatePicker ref="datePicker" :disabled = "input.dateType != 'ORDER_DATE' " 
					v-model="input.date1"/>
			</div>
			<div class="order-md-1 order-0 col-md-6 mb-2">
				<label for="dateType_PAY_DATE" class="t-12">
					<input id = "dateType_PAY_DATE" type="radio" name = "dateType" value="PAY_DATE" v-model="input.dateType" /> 付款日期範圍
				</label>
				<DatePicker ref="datePicker" :disabled = "input.dateType != 'PAY_DATE' " 
					v-model="input.date2"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6 mb-2">
				<Input ID = "orderNo" LabelVal = "訂單編號" :input = "input" :invalids = "invalids" maxlength="20"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6 mb-2">
				<Input ID = "nameCh" LabelVal = "公司名稱" :input = "input" :invalids = "invalids" maxlength="50"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6">
				<SelectView :options="selOption.queryStatus" :invalidTxt="invalids.queryStatus" LabelVal = "銷售狀態"
				v-model = "input.queryStatus"  ref = "queryStatus"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6">
				<SelectView :options="selOption.orderInfoSalesId" :invalidTxt="invalids.salesId" LabelVal = "負責業務"
				v-model = "input.orderInfoSalesId" ref = "orderInfoSalesId"/>
			</div>
		</div>
		<div class="row mt-2 mb-5">
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="clear"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
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
		<div class="pl-3">
			<span style="color:#A60000;font-weight: bold; font-size: 20px">
				點選訂單編號可查看訂單資料
			</span>
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
import moment from 'moment' ;
import { getSalesId2 } from "@/utils/dataListCMS" ;
import { isEmptyObject } from "@/utils/util";

let defaultSel = {
	date1 		: [ moment().subtract(7, 'days'), moment() ] ,
	date2 		: [ moment().subtract(7, 'days'), moment() ] ,
	orderInfoSalesId 	: "",
	dateType 	: "ORDER_DATE",
	updateUser 	: "" ,
	orderNo 	: "" ,
	nameCh 		: "" ,
	queryStatus : "" ,
}

export default {

	data(){
		let input = Object.assign({},defaultSel) ;
		return {
			selOption : {
				queryStatus : {
		    		ID 			: "queryStatus" ,
		    		func 		: [{
		    			text 	: "全部" ,
						val		: "" ,
		    		},
		    		{
		    			text 	: "尚未付款" ,
						val		: "UNPAID" ,
		    		},
		    		{
		    			text 	: "已付款" ,
						val		: "PAID" ,
		    		},
		    		{
		    			text 	: "已刪單" ,
						val		: "CANCELED" ,
		    		}],
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
		    	orderInfoSalesId : {
		    		ID 			: "orderInfoSalesId" ,
		    		func 		: getSalesId2.bind(true, this) ,
		    		addNullText : true ,
		    		selType 	: "dropdown" ,
					setFirst 	: true ,
		    		// defVal 		: input.salesId ? input.salesId : null ,
		    	},
			},
			input : input,
			invalids : { 
				
			},
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'link',
						key : ['orderNo'],
						value : ['訂單編號'],
						func : function( payment ){
	                        this.goNextPath("SA2801_1",{
                            	orderNo : payment.orderNo
                            })
	                    }.bind(this)
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderDate'],
						value : ['訂購日期']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['nameCh'],
						value : ['訂購公司（機構）名稱'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['discountAmt'],
						value : ['銷售金額(優惠價差)']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['status'],
						value : ['銷售狀態']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['payDate'],
						value : ['付款日'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['salesName'],
						value : ['業務'],
					},
				],
			},
			dataRow : [],
		}
	},
	mounted() {
        
    },
	components : {
		DataTable , SelectView , DatePicker , Input 
	},
	extends : Base ,
	methods: {
		init(){

		},
		search(){
			let input = {
				dateType : this.input.dateType,
				startDate : this.input.dateType === "ORDER_DATE" ? this.input.date1[0].format('YYYYMMDD') : this.input.date2[0].format('YYYYMMDD'),
				endDate : this.input.dateType === "ORDER_DATE" ? this.input.date1[1].format('YYYYMMDD') : this.input.date2[1].format('YYYYMMDD'),
				orderNo : this.input.orderNo,
				nameCh : this.input.nameCh,
				queryStatus : this.input.queryStatus,
				salesId : this.input.orderInfoSalesId
			}
			let invalids = {};
			if (!this.isValidOrderNo(input.orderNo)) {
				invalids["orderNo"] = "請輸入0~20位英數字"
			}

			this.invalids = invalids;
			if ( !isEmptyObject(invalids)) {
				return
			}

			let _this = this ;
			let params = {};
			_this.showLoading(true);

			Object.keys(input).forEach(key => {
				if (input[key] != "") {
					params[key] = input[key];
				}
			});

			this.ApiUtilCMS.get("/get-orders", params)
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					data.orderData.forEach(e => {
						if (!e.discountAmt) {
							e.discountAmt = "0";
						}
					})
					_this.dataRow  = data.orderData ;
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
    		this.input = Object.assign({},defaultSel) ;
    		this.$refs.roleId.clear();
    	},
    	showMask(){
    		this.$refs.Mask.show();
    	},
    	hideMask(){
    		this.$refs.Mask.hide();
    	},
		isValidOrderNo(str) {
			const regex = /^[a-zA-Z0-9]{0,20}$/;
			return regex.test(str);
		}
	}
}	
</script>