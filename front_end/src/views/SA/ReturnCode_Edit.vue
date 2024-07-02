<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<div class="p-3">
		<Card title = "新增狀態碼" >
			<CardItem colMd="6">
				<Input ID = "statusCode" :input = "addObj" LabelVal = "狀態碼" :invalids = "invalids" :required = "true" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "statusDesc" :input = "addObj" LabelVal = "狀態碼描述" :invalids = "invalids" :required = "true" />
			</CardItem>
		</Card>
	</div>
	<div class="row p-3 justify-content-md-center">
		<div class="col-md-6 order-1">
			<div class="m-0">
				<button type="button" @click.prevent="add"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">新增</span>
				</button>
			</div>
		</div>
	</div>
	<DataTable ref="table1" :dataRow="dataRow" :options ="tableOption" />
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import DataTable from '@/components/Table/PageDataTable.vue' ;
import SelectView from "@/components/Select" ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import { resetDefaultVal } from "@/utils/util" ;
import { isEmpty } from "@/utils/util";

export default {
	data(){
		return {
			title 		: "狀態碼維護" ,
			addObj 		: this.getDefault() ,
			tableOption : {
				colum : [
					{
						styleT : "width:44%" ,
						classT : "border border-info rounded-0 p-2 text-center alert-primary ",
                    	type : 'input',
                    	key : ['statusCode'],
                    	value : ['狀態碼'] , 
                	},
                	{
						styleT : "width:35%" ,
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'input',
						key : ['statusDesc'],
						value : ['狀態碼描述'],
					},
					{
						styleT : "width:21%" ,
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						classR: "d-flex justify-content-md-center" ,
						key : ['statusId'],
						value : ['動作'],
						button : [
							{
								class : "sip-status-resend rounded-pill mr-3" ,
								key : "修改" ,
								func : this.edit.bind(this)
							},
							{
								class : "sip-status-resend rounded-pill" ,
								key : "刪除" ,
								func : this.del.bind(this)
							},
						]
					}
				],
				rowKey : "statusId" ,
			},
			dataRow : [] ,
			invalids : {} ,
		};
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
		add(){
			let _this = this ;
			
			let statusCode = this.addObj.statusCode ;
			let statusDesc = this.addObj.statusDesc ;
			let invalids = {} ;
			if( statusCode == "" ) {
				invalids.statusCode = "請輸入狀態碼" ;
			}
			if( statusDesc == "" ) {
				invalids.statusDesc = "請輸入狀態碼描述" ;
			}

			this.invalids = invalids ;
			
			if( isEmpty(invalids) ) {
				_this.showLoading(true);
				this.ApiUtilSA.post("create-status-code-mapping" , this.addObj )
				.then( function( res ) {
					if( res.success && res.data.success) {
						_this.showModel({
			                title 	: _this.title ,
			                message : "新增成功",
			                endFunc : function(){
			                	_this.search();
								_this.addObj = _this.getDefault();
			                }
			            });
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
			}
			
		},
		edit( payment ){
			let _this = this ;
			_this.showLoading(true);

			this.ApiUtilSA.post("update-status-code-mapping" , payment )
			.then( function( res ) {
				if( res.success && res.data.success) {
					_this.showModel({
		                title 	: _this.title,
		                message : "修改成功"
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		del( payment ){
			let _this = this ;
			_this.showLoading(true);
			let param = { 
				previousData : payment.previousData 
			}
			this.ApiUtilSA.delete("delete-status-code-mapping/" + payment.statusId , param )
			.then( function( res ) {
				if( res.success && res.data.success) {
					_this.showModel({
		                title 	: _this.title,
		                message : "刪除成功",
		                endFunc : function(){
		                	_this.search();
							_this.addObj = _this.getDefault();
		                }
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		init(){
			this.search();
		},
		search(){
			let _this = this ;
			_this.showLoading(true);
			this.ApiUtilSA.get("get-status-code-mapping")
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					data.forEach(function( item ){
						item.previousData = JSON.stringify(item);
					});
					_this.dataRow = data ;
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		getDefault(){
			return {
				statusCode 	: "" ,
				statusDesc 	: "" 
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