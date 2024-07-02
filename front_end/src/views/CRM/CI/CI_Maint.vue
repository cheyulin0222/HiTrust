<template>

	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ $t('CIMain.title') }}
	</h4>
	<ul class="nav nav-tabs"
		id="myTab" role="tablist">
		<li class="nav-item">
			<a class="nav-link active"
				id="company-tab"
				data-toggle="tab"
				href="" 
				@click="tabChange('company')"
				role="tab"
				aria-controls="cancel"
				aria-selected="true">依公司查詢</a>
		</li>
		<li class="nav-item">
			<a class="nav-link"
				id="gateway-tab"
				data-toggle="tab"
				href=""
				@click="tabChange('gateway')"
				role="tab"
				aria-controls="capture"
				aria-selected="false">依閘道查詢</a>
		</li>
	</ul>
	<div class = "tab-content w-100" v-show="tabName=='company'">
		<SearchBar :input = "input" :option = "option" @search="search" :invalidFunc="invalidFunc" key="companyS"/>	
		<DataTable ref="table1"
		:dataRow="dataRow"
		:options ="tableOption">
		</DataTable>
	</div>
	<div class = "tab-content w-100" v-show="tabName=='gateway'">
		<SearchBar :input = "input2" :option = "option2" @search="search2" :invalidFunc="invalidFunc" key="companyG"/>
		<DataTable ref="table2"
			:dataRow="dataRow2"
			:options ="tableOption2">
		</DataTable>
	</div>
	

</template>
<script>
import SearchBar from '@/components/Search.vue'
import DataTable from '@/components/Table/DataTable.vue'
import  moment from 'moment'

function getDefault () {
	return {
		tabName : "company" ,
		dataRow 	: [] ,
		dataRow2 	: [] ,
		selData : [] ,
		selData2: [] ,
	}
}
export default {

	data(){
		let _this = this
		return Object.assign({
			
			input : {
				companyName 	: "" ,
				taxIdNumber 	: "" ,
				categoryId		: "" ,
			},
			input2 : {
				storeId		 		: "" ,
				environmentId 		: "" ,
				acquirerMerchantId	: "" ,
				acquirerId 			: "" ,
				tagIds 				: [] ,
			},
			option : {
				size : 3 // SearchBar預設一行顯示3個
			},
			option2 : {
				size : 4 // SearchBar預設一行顯示3個
			},			
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['taxIdNumber'],
						value : ['統編']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['companyName'],
						value : ['公司名稱']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['status'],
						value : [ ],
						classR: "d-flex" ,
						styleT: "width:100px;",
						button : [
							{
								class : "" ,
								key : "" ,
								icon: "fa-circle-play" ,
								filter : function( payment ){
									return true
								},
								func : function ( payment ){
									this.getDetail( payment.id );
								}.bind(this)
							},
						]
					} 

				] ,
				paginate: {
					page: 1,
					size: 10,
					total: 0,
				},
				
			},
			tableOption2 : {
				colum : [
					
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['taxIdNumber'],
						value : ['統編']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['companyName'],
						value : ['公司名稱']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['status'],
						value : [ ],
						classR: "d-flex" ,
						styleT: "width:100px;",
						button : [
							{
								class : "" ,
								key : "" ,
								icon: "fa-circle-play" ,
								func : function ( payment ){
									this.getDetail( payment.id );
								}.bind(this)
							}, 
						]
					} 

				] ,
				paginate: {
					page: 1,
					size: 10,
					total: 0,
				},
			},
			invalidFunc : { // 自訂SearchBar 檢核Func
				
			}
		},getDefault())
	},
	props : {
		
	}, 
	watch : {
		
	},
	components : {
		SearchBar , DataTable 
	},
	mounted () {
	},
	methods: {
		search(){
			this.doSelPage();
		},
		search2(){
			this.doSelPage2();
		},
		doSelPage( page ){
			let params = {};
			let input = this.input ;
			let _this = this ;
			_this.showLoading(true);
			Object.keys( input ).forEach(function(key){
				if( input[key] != "" ) {
					params[key] = input[key] ;
				}
			});
			//console.log(params);
			this.ApiUtil.get("/company/company",params)
			.then((res) => {
				if( res.success ) {
					this.dataRow = res.data
				} else {
					this.dataRow = [] ;
				}
				//console.log(res);
				_this.showLoading();
			});
			
			/*this.tableOption.paginate.page = 1 ;
			this.tableOption.paginate.total = 20 ;
			this.tableOption.paginate.size = 3 ;*/
		},
		doSelPage2( page ){
			let params = {};
			let input = this.input2 ;
			let _this = this ;
			_this.showLoading(true);
			Object.keys( input ).forEach(function(key){
				if( input[key] != "" ) {
					params[key] = input[key] ;
				}
			});
			//console.log(params);
			this.ApiUtil.get("/company/store",params)
			.then((res) => {
				if( res.success ) {
					this.dataRow2 = res.data
				} else {
					this.dataRow2 = [] ;
				}
				//console.log(res);
				_this.showLoading();
			});
		},
		tabChange ( tabName ) {
			this.tabName = tabName ;
		},
		getDetail( ID ) {
			this.goTo({ name:"CRM1202_1",params:{ id : ID } });
		},
		updateSelData( selData) {
			this.selData = selData ;
		},
		updateSelData2( selData) {
			this.selData2 = selData ;
		},
	}
}	
</script>