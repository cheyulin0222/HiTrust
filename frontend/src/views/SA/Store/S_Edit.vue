<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		商店管理維護
	</h4>
	<div class="row p-3" ref = 'search' >
		<CardItem colMd="6">
			<Input ID = "merId" LabelVal = "商家代碼" :input = "input" :invalids = "invalids" maxlength = "10"/>
		</CardItem>
		<CardItem colMd="6">
			<Input ID = "merName" LabelVal = "商家英文名稱" :input = "input" :invalids = "invalids" maxlength= "30"/>
		</CardItem>
		<CardItem colMd="6">
			<SelectView :options="selOption.adapterId" :invalidTxt="invalids.adapterId" LabelVal = "收單銀行"
				v-model = "input.adapterId" ref = "adapterId"/>
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
		<div class="col-md-4  p-2">
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
	:options ="tableOption"
	@doSelPage = "doSelPage" >
	</DataTable>
</template>
<script>
import DataTable from '@/components/Table/PageDataTable.vue' ;
import SelectView from "@/components/Select" ;
import Mask from "@/components/Mask.vue" ;
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import CardItem from "@/components/CardItem" ;
import { getAcqBankList } from "@/utils/dataListSA";

let defaultSel = {
	adapterId 	: "",
	merNm 		: "",
	merId 		: "",
}

export default {

	data(){
		let _this = this ;
		let env = this.env.getData();
		
		return {
			selOption : {
				adapterId : {
		    		ID 			: "adapterId" ,
		    		func 		: getAcqBankList.bind(this,true),
		    		filter 	 	: true ,
		    		setFirst 	: true ,
		    		addNullText	: true ,
		    		selType 	: "dropdown" ,		    		
		    	},
			},
			input : Object.assign({},defaultSel),
			invalids : { 
				
			},
			tableOption : {
				colum : [
					{
						styleT : "width:70px" ,
						classT : "border border-info rounded-0 p-2 text-center alert-primary ",
                    	type : 'index',
                    	value : ['項次'] , 
                	},
                	{
                		styleT : "width:100px" ,
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'link',
						key : ['merId'],
						value : ['商家代碼'],
						func : function( payment ){
	                        this.selDetail(payment)
	                    }.bind(this)
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['merName'],
						value : ['商家英文名稱'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['merNameCh'],
						value : ['商家名稱']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['adapterId'],
						value : ['收單銀行'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['adminEmail'],
						value : ['管理者電子郵件'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['merchantType'],
						value : ["商家金鑰下載"],
						classR: "d-flex" ,
						button : [
							{
								class : "store_key_down" ,
								key : "(WI商家)不需下載" ,
								filter : function( payment ){
									return payment.merchantType === "WI" ;
								},
							},
							{
								class : "store_key_down" ,
								key : "(TK商家)" ,
								filter : function( payment ){
									return payment.merchantType === "TK" ;
								},
							},
							{
								class : "store_key_down_link" ,
								key : "derUrl" ,
								filter : function( payment ){
									return payment.merchantType === "TK" ;
								},
			                    func : function ( payment ){
			                        this.download( payment )
			                    }.bind(this),
			                    text : function ( payment ){
			                    	return payment.merId + "_PRIV.DER";
			                    }
							},
						]
					}, 

				],
				paginate: {
					page: 1,
					size: 10,
					total: 0,
				},
			},
			dataRow : [],
		}
	},
	extends : Base ,
	components : {
		SelectView , DataTable , Mask , Input , CardItem
	},
	methods: {
		search(){
			this.doSelPage(1);
		},
		doSelPage( page ){
			let _this = this ;
			let input = this.input ;
			let size = this.$refs.table1 ? this.$refs.table1.resize() : this.options.paginate.size ;
			let params = {
				merId 		: input.merId ,
				merName 	: input.merName ,
				adapterId 	: input.adapterId ,
				page 		: page - 1 ,
				size		: size
			};
			
			this.showLoading(true); 
			this.ApiUtilSA.get('get-stores',params)
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					_this.dataRow = data.content ;
					_this.tableOption.paginate.page = page ;
	                _this.tableOption.paginate.total = res.data.totalElements ;
	                _this.tableOption.paginate.size = size ;
				} else {
					_this.clearTable();
				}
				//console.log(res);
				_this.showLoading(); 
			});
		},
    	clear(){
    		this.input = Object.assign({},defaultSel) ;
    		this.$refs.adapterId.clear();
    	},
    	showMask(){
    		this.$refs.Mask.show();
    	},
    	hideMask(){
    		this.$refs.Mask.hide();
    	},
    	showMask2(){
    		this.$refs.Mask2.show();
    	},
    	hideMask2(){
    		this.$refs.Mask2.hide();
    	},
    	selDetail( obj ){
    	
			this.goNextPath("SA1302_1",{
            	merId 		: obj.merId
            })

    	},
    	download( item ) {
			let _this = this ;

			_this.showLoading(true);
			
			this.ApiUtilSA.downloadExcel("downlad-priv-der/" + item.merId , null , {
				
			})
			.finally(function () {
	        	_this.showLoading();
			});
		}
	}
}	
</script>