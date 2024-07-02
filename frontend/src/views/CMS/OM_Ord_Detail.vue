<template>

	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		訂單查詢-詳細資料
	</h4>
	<div class="p-3">
		<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
			<div class = "p-2 text-center ">
				訂單資訊
			</div>
			<div class="row p-3 rounded bg-light m-0">
				<div class="order-0 col-md-6">
					<Input ID = "orderNo" LabelVal = "訂單編號" :input = "orderObj" :invalids = "invalids" :readOnly="true"/>
				</div>
				<div class="order-0 col-md-6">
					<SelectView :options="selOption.orderInfoSalesId" :invalidTxt="invalids.orderInfoSalesId" LabelVal = "業務人員"
					v-model = "orderObj.orderInfoSalesId"  ref = "orderInfoSalesId"/>
				</div>
				<div class="order-0 col-md-4">
					<Input ID = "orderDate" LabelVal = "訂購日期" :input = "orderObj" :invalids = "invalids" :readOnly="true"/>
				</div>
				<div class="order-0 col-md-4">
					<Input ID = "amount" LabelVal = "銷售金額" :input = "orderObj" :invalids = "invalids" :readOnly="true"/>
				</div>
				<div class="order-0 col-md-4">
					<Input ID = "dueDate" LabelVal = "優惠價期限" :input = "orderObj" :invalids = "invalids" :readOnly="true"/>
				</div>
				<div class="order-0 col-md-4">
					<Input ID = "payType" LabelVal = "付款方式" :input = "orderObj" :invalids = "invalids" :readOnly="true"/>
				</div>
				<div class="order-0 col-md-4">
					<SelectView  :options="selOption.orderInfoStatus" :invalidTxt="invalids.orderInfoStatus" LabelVal = "銷售狀態"
					v-model = "orderObj.orderInfoStatus" ref = "orderInfoStatus" :readOnly="isReadOnly"/>
					
				</div>
				<div class="order-0 col-md-4">
					<Input ID = "payDate" LabelVal = "付款日期(EX:20160101)" :input = "orderObj" :invalids = "invalids" :readOnly="isReadOnly"/>
				</div>
				<div class="order-0 col-md-12">
					<Input ID = "invoiceTitle" LabelVal = "發票抬頭" :input = "orderObj" :invalids = "invalids" :readOnly="true"/>
				</div>
				<div class="order-0 col-md-12">
					<Input ID = "regNo" LabelVal = "統一編號" :input = "orderObj" :invalids = "invalids" :readOnly="true"/>
				</div>
				<div class="order-0 col-md-12">
					<Input ID = "shipAddr" LabelVal = "收件地址" :input = "orderObj" :invalids = "invalids" :readOnly="true"/>
				</div>
				<div class="order-0 col-md-6">
					<SelectView :options="selOption.processStatus" :invalidTxt="invalids.processStatus" LabelVal = "處理狀態"
					v-model = "orderObj.processStatus" ref = "processStatus"/>
				</div>
				<div class="order-0 col-md-6 justify-content-md-center">
					<div style="margin-top: 30px">
						<button type="button" @click.prevent="save"
						class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
							<span class="mx-2">儲存修改</span>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="p-3">
		<div class = "p-1 text-center font-weight-bold">
			銷售憑證
		</div>
		<div class="border-0 pt-3">
			<DataTable ref="table1"
			:dataRow="dataRow"
			:options ="tableOption"/>
		</div>
	</div>
	<div class="p-3">
		<div class = "p-1 text-center font-weight-bold">
			議價記錄
		</div>
		<div class="border-0 pt-3">
			<DataTable ref="table2"
			:dataRow="dataRow2"
			:options ="tableOption2"/>
		</div>
		<div class="row mt-2 mb-5">
			<div class="col-md-4 offset-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="doNego"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">議價</span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<Mask ref = "Mask">
		<div class="p-3">
			<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
				<div class = "p-2 text-center ">
					SALES NOTE
				</div>				
				<div class="row p-3 rounded bg-light m-0 justify-content-md-center">
					<div class="order-0 col-md-7">
						<SelectView :options="selOption.cpdType" :invalidTxt="invalids.cpdType" LabelVal = "憑證類別"
						v-model = "negoObj.cpdId" ref = "cpdType" :required="true"/>
					</div>
					<div class="order-0 col-md-7">
						<Input ID = "negoAmount" LabelVal = "議價金額(範例：13000)" :input = "negoObj" :invalids = "invalids" :required="true" maxlength="12"/>
					</div>
					<div class="order-0 col-md-7">
						<Input ID = "dueDate" LabelVal = "優惠期限(範例：20150101)" :input = "negoObj" :invalids = "invalids" :required="true" maxlength="8"/>
					</div>
					<div class="order-0 col-md-7">
						<Input ID = "memo" LabelVal = "說明(50個字)" :input = "negoObj" :invalids = "invalids" maxlength="50"/>
					</div>
					<div class="order-0 col-md-7">
						<SelectView :options="selOption.orderInfoSalesId" :invalidTxt="invalids.orderInfoSalesId2" LabelVal = "業務人員"
						v-model = "negoObj.orderInfoSalesId" ref = "orderInfoSalesId2" :IDRandom = "true" :required="true"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row mt-2 mb-5">
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="hideMask"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">取消</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="saveNego"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">確認</span>
					</button>
				</div>
			</div>
		</div>
	</Mask>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import DataTable from '@/components/Table/DataTable.vue' ;
import Mask from "@/components/Mask" ;
import Input from "@/components/Input" ;
import SelectView from "@/components/Select" ;
import { getSalesId } from "@/utils/dataListCMS" ;
import { getCpdTypeList } from "@/utils/dataListCMS" ;
import { isEmptyObject } from "@/utils/util";

function getDefault () {
	return {
		dataRow 	: [] ,
		dataRow2 	: [] ,
		orderObj 	: {

		},
		invalids    : {} ,
 	}
}
export default {
	data(){
		return {
			isReadOnly : false,
			selOption 	: {
				orderInfoSalesId : {
					ID 			: "orderInfoSalesId" ,
		    		func 		: getSalesId.bind(false, this) ,
		    		addNullText : true ,
		    		selType 	: "dropdown" ,
				},
				orderInfoStatus : {
					ID 			: "orderInfoStatus" ,
		    		func 		: [{
		    			text 	: "狀態不明" ,
						val		: "UNDEFINED" ,
		    		},
		    		{
		    			text 	: "待付款" ,
						val		: "PENDING" ,
		    		},
		    		{
		    			text 	: "已付款" ,
						val		: "PAID" ,
		    		}] ,
					selType 	: "dropdown" ,
				},
				processStatus : {
					ID 			: "processStatus" ,
		    		selType 	: "dropdown" ,
				},
				cpdType: {
					ID 			: "cpdType" ,
		    		addNullText : true ,
		    		selType 	: "dropdown" ,
				},
			},
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						styleT : "width:80px;" ,
						key : ['saleNo'],
						value : ['銷售編號'] ,
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['cpdType'],
						styleT : "width:300px;" ,
						value : ['憑證種類']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['price'],
						styleT : "width:100px;" ,
						value : ['定價']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ["salePrice"],
						styleT : "width:100px;" ,
						value : ['售價']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderDetailStatus'],
						styleT : "width:100px;" ,
						value : ['憑證狀態'],
						filter: function ( value, key , rowData) {
                            if ( value === '1') {
                                return "待上傳文件";
                            } else if( value === '2' ) {
                            	return "審核中";
                            } else if( value === '3' ) {
                            	return "已生效";
                            } else if( value === '4' ) {
                            	return "已過期";
                            }
                            return value;
                        }

					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['expiryDate'],
						value : ['到期日'],
						styleT : "width:80px;"
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['status'],
						value : [""],
						classR: "d-flex" ,
						styleT: "width:100px;",
						button : [
							{
								class : "sip-status-resend rounded-pill" ,
								key : "設定" ,
								filter : function( payment ){
									return payment.orderDtlNo && (payment.orderDetailStatus === '待上傳文件' || payment.orderDetailStatus === '審核中')
								},
								func : function ( payment ){
									// 還原為DB資料(預防修改卻未儲存的狀況)
									Object.assign(this.orderObj, this.previousData);

									const currentRouteName = this.$route.name;
									const newRouteName = currentRouteName.slice(0, -1) + '2';
									this.goNextPath(newRouteName,{
										orderDtlNo : payment.orderDtlNo
									})
								}.bind(this)
							},
							{
								class : "sip-status-resend rounded-pill" ,
								key : "檢視" ,
								filter : function( payment ){
									return payment.orderDtlNo && (payment.orderDetailStatus === '已生效' || payment.orderDetailStatus === '已過期')
								},
								func : function ( payment ){
									const currentRouteName = this.$route.name;
									const newRouteName = currentRouteName.slice(0, -1) + '2';
									this.goNextPath(newRouteName,{
										orderDtlNo : payment.orderDtlNo
									})
								}.bind(this)
							}
						]
					}

				] ,
			},
			tableOption2 : {
				colum : [
					{	
						classT 	: "border border-info rounded-0 p-3 text-center alert-primary ",
						type 	: 'text',
						key 	: ['negoDate'],
						value 	: ['議價日'],
						styleT  : "width:80px;",
						
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['cpdType'],
						styleT  : "width:300px;",
						value : ['憑證種類']
					},
										{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['negoAmount'],
						styleT  : "width:100px;",
						value : ['議價後金額']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['dueDate'],
						styleT  : "width:100px;",
						value : ['優惠價期限']
					},
										{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['memo'],
						value : ['說明']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['negoDetailSales'],
						value : ['業務人員']
					}
				] ,
			},
			negoObj : this.getDefaultNego(),
			...getDefault(),
		}
	},
	props : {
		
	},
	extends : Base ,
	computed: {
		
	},
	activated() {
		if (this.params.orderNo) {
			this.search();
		}
	},
	watch: {     
       
    },
	components : {
		DataTable , Mask , Input , SelectView
	},
	methods: {
		init(){
		},
		doIsReadOnly() {
      		return !['PENDING', 'UNDEFINED'].includes(this.orderObj.orderInfoStatus);
    	},
		search() {
			let _this = this ;
			_this.showLoading(true);
			this.ApiUtilCMS.get("/get-order-detail/" + this.params.orderNo)
			.then((res) => {
				if( res.success ) {
					let data = res.data ;

					_this.previousData = {
						orderNo: data.orderNo,
						orderInfoStatus: data.orderInfoStatus,
						processStatus: data.processStatus,
						payDate: data.payDate,
						orderInfoSalesId: data.orderInfoSalesId,
					}

					_this.orderObj = {
						orderNo 		: data.orderNo,
						orderInfoSalesId: data.orderInfoSalesId,
						orderDate 		: data.orderDate,
						amount 			: data.amount,
						dueDate 		: data.dueDate,
						payType 		: data.payType,
						orderInfoStatus : data.orderInfoStatus,
						payDate 		: data.payDate,
						invoiceTitle	: data.invoiceTitle,
						regNo 			: data.regNo,
						shipAddr 		: data.shipAddr,
						processStatus 	: data.processStatus,
						previousData   	: JSON.stringify(_this.previousData)
					}

					let prcessStatusArr = [];
					data.processStatusOption.forEach(element => {
						let option = {
							text : element.value,
							val  : element.key
						}
						prcessStatusArr.push(option);
					});

					let cpdTypeArr = [];
					data.certDetail.forEach(element => {
						let exists = cpdTypeArr.some(option => option.text === element.cpdType || option.val === element.cpdId);
						if (!exists) {
							let option = {
								text : element.cpdType,
								val  : element.cpdId
							}
							cpdTypeArr.push(option);
						}
					});

					_this.dataRow = data.certDetail;
					_this.dataRow2 = data.negoDetail;

					if (_this.orderObj.orderInfoStatus === "CANCELED") {
						const option = {
							text	: " ",
							val		: "CANCELED"
						};
						_this.selOption.orderInfoStatus.func.push(option);
					}

					_this.$refs.processStatus.setArr(prcessStatusArr,  _this.orderObj.processStatus);
					_this.$refs.cpdType.setArr(cpdTypeArr, "");

					_this.isReadOnly = _this.doIsReadOnly();
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		save() {
			let invalids = {};
			if (!this.isValidDate(this.orderObj.payDate)) {
				invalids["payDate"] = "請輸入合法的付款日期"
			}
			this.invalids = invalids;
			if ( !isEmptyObject(invalids)) {
				return
			}

			let params = {
				orderNo			: this.orderObj.orderNo,
				orderInfoStatus	: this.orderObj.orderInfoStatus,
				processStatus	: this.orderObj.processStatus,
				payDate			: this.orderObj.payDate,
				orderInfoSalesId: this.orderObj.orderInfoSalesId,
				previousData	: this.orderObj.previousData
			}

			let _this = this;
			_this.showLoading(true);
			this.ApiUtilCMS.post("/update-order-detail-data", params)
			.then(res => {
				_this.showLoading();
				if ( res.success ) {
					let data = res.data
					_this.showModel({
						title : data.message,
						message : data.message,
						endFunc : function(){
							_this.search();
						}
					});
				}
			})
			.finally(() => {
				_this.showLoading();
			})
		},
		doSave(){
			alert("SAVE");
		},
		doNego(){
			this.showMask();
		},
		showMask(){
			this.invalids = {};
			this.$refs.Mask.show();
		},
		hideMask(){
			this.invalids = {};
			this.$refs.Mask.hide();
		},
		resetNego(){
			let negoObj = this.getDefaultNego();
			this.$refs.cpdType.setDefVal(negoObj.cpdType);
			this.$refs.orderInfoSalesId2.setDefVal(negoObj.orderInfoSalesId);
			this.negoObj = negoObj ;
		},
		saveNego(){
			let invalids = {};
			if (!this.negoObj.cpdId || this.negoObj.cpdId === "") {
				invalids["cpdType"] = "請選擇憑證類別"
			}
			if (!this.isNonZeroNumeric(this.negoObj.negoAmount)) {
				invalids["negoAmount"] = "請輸入大於0且小於12位數字"
			}
			if (!this.isValidDate(this.negoObj.dueDate)) {
				invalids["dueDate"] = "請輸入合法的優惠期限"
			}
			if ( !!this.negoObj.memo && this.negoObj.memo.length > 50) {
				invalids["memo"] = "請勿超過50個字"
			}
			if (!this.negoObj.orderInfoSalesId || this.negoObj.orderInfoSalesId === "") {
				invalids["orderInfoSalesId2"] = "請選擇業務人員"
			}
			this.invalids = invalids;
			if ( !isEmptyObject(invalids)) {
				return
			}
			
			let params = {
				orderNo		: this.orderObj.orderNo,
				cpdId		: this.negoObj.cpdId,
				negoAmount	: parseInt(this.negoObj.negoAmount),
				dueDate		: this.negoObj.dueDate,
				memo		: this.negoObj.memo,
				salesId		: this.negoObj.orderInfoSalesId
			}

			let _this = this;
			_this.showLoading(true);
			this.ApiUtilCMS.post("/create-negotiated-price-log", params)
			.then(res => {
				_this.showLoading();
				if ( res.success ) {
					let data = res.data
					_this.showModel({
						title : data.message,
						message : data.message,
						endFunc : function(){
							_this.resetNego();
							_this.hideMask();
							_this.search();
						}
					});
				}
			})
			.finally(() => {
				_this.showLoading();
			})
		},
		updateVal( val , ID ){
			this.orderObj[ID] = val ;
		},
		updateVal2( val , ID ){
			this.negoObj[ID] = val ;
		},
		getDefaultNego(){
			return {
				cpdId : "" ,
				negoAmount : "" ,
				duedate : "" ,
				memo : "" ,
				orderInfoSalesId : "" 
			}
		},
		isValidDate(str) {
			const regex = /^\d{4}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])$/;
			return regex.test(str);
		},
		isNonZeroNumeric(str) {
			return /^[1-9][0-9]*$/.test(str);
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