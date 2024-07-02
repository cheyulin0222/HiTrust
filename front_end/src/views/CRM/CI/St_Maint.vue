<template>

	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ $t('StMain.title') }}
	</h4>

	<SearchBar :input = "input" :option = "option" @search="search" :invalidFunc="invalidFunc" key="companyG"/>
	<DataTable ref="table"
		:dataRow="dataRow"
		:options ="tableOption">
	</DataTable>

</template>
<script>
import SearchBar from '@/components/Search.vue'
import DataTable from '@/components/Table/DataTable.vue'
import  moment from 'moment';
import Base from "@/components/Base" ;
import { mapGetters, mapState } from 'vuex';
import { getListVal } from "@/utils/dataList";

export default {

	data(){
		return {
			input : {
				storeId		 		: "" ,
				environmentId 		: "" ,
				acquirerMerchantId	: "" ,
				acquirerId 			: "" ,
				tagIds 				: [] ,
			},
			option : {
				size : 4 // SearchBar預設一行顯示3個
			},
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['environment'],
						value : ['環境'] ,
						filter 	: function ( value , key ){							
                            return getListVal( this.EnvironmentId , value.id) ;
						}.bind(this)
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['storeId'],
						value : ['商家代號']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['acquirerMerchantId'],
						value : ['MID']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ["nameTw","/","nameEn"],
						value : ['商店名稱']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['currency'],
						value : ['幣別']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['acquirer'],
						value : ['收單銀行'],
						filter 	: function ( value , key ){
							return getListVal( this.Acquirer , value.id) ;
						}.bind(this)
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['technologyType'],
						value : ['串接方式']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['createdAt'],
						value : ['建立日期']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['status'],
						value : ['閘道狀態']
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
								class : "" ,
								key : "" ,
								icon: "fa-pen" ,
								filter : function( payment ){
									return true
								},
								func : function ( payment ){
									this.goTo({name:"CRM1203_1",params:{type:"EDIT",
										companyStoreId:payment.id,goBackName:"CRM1203"}});
								}.bind(this)
							},
							{
								class : "" ,
								key : "" ,
								icon: "fa-trash-can" ,
								filter : function( payment ){
									return true
								},
								func : function ( payment ){
									this.delStore( payment );
								}.bind(this)
							},
						]
					}

				] 
			},
			dataRow : [] ,
			invalidFunc : {} ,
		}
	},
	extends : Base ,
	computed: {
		...mapGetters( 'user' , {
	        Acquirer 		: "getAcquirer",
	        EnvironmentId 	: "getEnvironmentId",
	        SalesId 		: "getSalesId",
	        CategoryId 		: "getCategoryId",
	        NotifyType 		: "getNotifyType" ,
      	})
	},
	watch: {     

    },
	components : {
		DataTable , SearchBar
	},
	methods: {
		search() {
			let params = {};
			let input = this.input ;
			let _this = this ;
			_this.showLoading(true);
			
			Object.keys( input ).forEach(function(key){
				if( input[key] != "" ) {
					params[key] = input[key] ;
				}
			})

			//ajax
			this.ApiUtil.get("/store/args",params)
			.then((res) => {
				if( res.success ) {
					if( _this.Acquirer.length == 0 || _this.EnvironmentId.length == 0) {
						let interval = setInterval(()=>{
							if( _this.Acquirer.length > 0 && _this.EnvironmentId.length > 0) {
									_this.clearInterval2(interval);
									return res.data ;
								}

						} , 500 ) ;
						_this.intervals.push(interval);
					} else {
						return res.data ;
					}
				} else {
					return [] ;
				}
				
			})
			.then((data)=>{
				_this.dataRow = data ;
				_this.showLoading();
			});

		},
		delStore( store ){
			let _this = this ;	
			this.ApiUtil.delete("/store/"+store.id)
			.then((res) => {
				if( res.success ) {
					_this.showModel({
		                title : '刪除成功',
		                message : "刪除成功",
		            });
		            _this.search();
				}
			})
		},
	}
}
</script>