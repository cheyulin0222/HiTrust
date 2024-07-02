<template>
	<div class="container-fluid">
		<div>
			<div>新訂單（已付款，待處理 ) </div>
			<DataTable ref="table1" :dataRow="dataRow" :options ="tableOption"/>
		</div>	
		<div>
			<div>新訂單（待付款）</div>
			<DataTable ref="table1" :dataRow="dataRow2" :options ="tableOption2"/>
		</div>
		<div>
			<div>近一個月內到期未續約憑證</div>
			<DataTable ref="table1" :dataRow="dataRow3" :options ="tableOption3"/>
		</div>
		<div>
			<div>已到期三個月內未續約憑證</div>
			<DataTable ref="table1" :dataRow="dataRow4" :options ="tableOption4"/>
		</div>
	</div>
</template>

<script>
import DataTable from '@/components/Table/DataTable.vue' ;
import Base from "@/components/RightViewBase" ; 
import { mapGetters, mapState } from 'vuex';

export default {

	data(){
		let _this = this ;

		return {
			dataRow 	: [] ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderNo'],
						value : ['訂單編號']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['nameCh'],
						value : ['訂購會員名稱']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['count'],
						value : ['憑證張數']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderDate'],
						value : ['訂購時間']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['amount'],
						value : ['銷售金額','<br>','(折扣金額)']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['salesName'],
						value : ['業務']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['keySalesName'],
						value : ['KEY單業務']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['status'],
						value : ["操作功能"],
						classR: "d-flex" ,
						styleT: "width:100px;",
						button : [
							{
								class : "sip-status-resend rounded-pill" ,
								key : "詳細資料" ,
								func : function ( payment ){
									this.goNextPath("SA2701_1",{
                            			orderNo : payment.orderNo
                           		 	});
								}.bind(this),
							},
						]
					}
				]
			},
			dataRow2 	: [] ,
			tableOption2: {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderNo'],
						value : ['訂單編號']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['nameCh'],
						value : ['訂購會員名稱']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['count'],
						value : ['憑證張數']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderDate'],
						value : ['訂購時間']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['amount'],
						value : ['銷售金額']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['salesName'],
						value : ['業務']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['keySalesName'],
						value : ['KEY單業務']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['status'],
						value : ["操作功能"],
						classR: "d-flex" ,
						styleT: "width:100px;",
						button : [
							{
								class : "sip-status-resend rounded-pill" ,
								key : "詳細資料" ,
								func : function ( payment ){
									this.goNextPath("SA2701_1", {
                            			orderNo : payment.orderNo
                           		 	});
								}.bind(this),
							},
						]
					}
				]
			},
			dataRow3 	: [] ,
			tableOption3: {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderDtlNo'],
						value : ['憑證銷售編號']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['cpdType'],
						value : ['憑證產品']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['nameCh'],
						value : ['公司（機構）名稱']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderDate'],
						value : ['訂購時間']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['expiryDate'],
						value : ['到期時間']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['salesName'],
						value : ['業務']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['isRenew'],
						value : ['續約狀態']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['status'],
						value : ["操作功能"],
						classR: "d-flex" ,
						styleT: "width:100px;",
						button : [
							{
								class : "sip-status-resend rounded-pill" ,
								key : "詳細資料" ,
								func : function ( payment ){
									this.goNextPath("SA2701_1", {
                            			orderNo : payment.orderNo
                           		 	});
								}.bind(this),
							},
						]
					}
				]
			},
			dataRow4 	: [] ,
			tableOption4: {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderDtlNo'],
						value : ['憑證銷售編號']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['cpdType'],
						value : ['憑證產品']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['nameCh'],
						value : ['公司（機構）名稱']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderDate'],
						value : ['訂購時間']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['expiryDate'],
						value : ['到期時間']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['salesName'],
						value : ['業務']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['isRenew'],
						value : ['續約狀態']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['status'],
						value : ["操作功能"],
						classR: "d-flex" ,
						styleT: "width:100px;",
						button : [
							{
								class : "sip-status-resend rounded-pill" ,
								key : "詳細資料" ,
								func : function ( payment ){
									this.goNextPath("SA2701_1", {
                            			orderNo : payment.orderNo
                           		 	});
								}.bind(this),
							},
						]
					}
				]
			}
		}
	},
	extends : Base ,
	components : {
		DataTable
	},
	computed : {
		...mapGetters( 'user' , {
	        userId 	: "getEmail"
      	})
	},
	methods: {
		init(){
			this.search();
		},
		search(){
			let _this = this ;
			_this.showLoading(true);
			this.ApiUtilCMS.get("/get-overview-data/" + this.userId)
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					_this.dataRow  = data.paidPendingOrder ;
					_this.dataRow2 = data.unpaidOrder ;
					_this.dataRow3 = data.certsExpiringWithinOneMonth ;
					_this.dataRow4 = data.certsExpiredWithinThreeMonth ;
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		
		
	}
}	
</script>