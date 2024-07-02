<template>
<div class="container-fluid">
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ $t('CCMain.title') }}
	</h4>
	<SearchBar :input = "input" :option = "option" @search="search" :invalidFunc="invalidFunc"/>

	<DataTable ref="table1"
	:dataRow="dataRow"
	:options ="tableOption">
	</DataTable>
</div>
</template>
<script>
import SearchBar from '@/components/Search.vue';
import DataTable from '@/components/Table/DataTable.vue'

export default {

	data(){
		let _this = this ;

		return {
			input : {
				"companyName" 	: "",
				"taxIdNumber" 	: ""
			},
			option : {
				size : 2 // SearchBar預設一行顯示3個
			},
			invalidFunc : { // 自訂SearchBar 檢核Func
				
			},
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['taxIdNumber'],
						value : ['統編'],
						styleT: "width:30%;",
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
									this.goTo({ name:"CRM1101_1",params:{ comDataS:JSON.stringify(payment), id : payment.id  } });
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
			dataRow : [],
		}
	},

	components : {
		SearchBar , DataTable 
	},
	methods: {
		search(){
			let _this = this ;
			let params = {};
			let input = this.input ;
			_this.showLoading(true);
			Object.keys( input ).forEach(function(key){
				if( input[key] != "" ) {
					params[key] = input[key] ;
				}
			});
			this.ApiUtil.get('/company/company',params)
			.then((res) => {
				if( res.success ) {
					_this.dataRow = res.data
				} else {
					_this.dataRow = [] ;
				}
				//console.log(res);
				_this.showLoading();
			});
		},
		doSelPage( page ){

		}
	}
}	
</script>