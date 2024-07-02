<template >
	
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		手續費群組設定
	</h4>
	<SearchBar :input = "selInput" :option = "selOption" @search="search" :invalidFunc="invalidFunc" ref = "sel" @clear="clear"/>	
	<div class="p-3" v-if="type =='GROUP'">
		<div class="border-0 pt-3">
			<DataTable ref="table1"
			:dataRow="dataRow"
			:options ="tableOption"/>
		</div>
	</div>
	<div class="p-3" v-if="type =='MERCHANT'">
		<div class="row m-0">
			<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-2">
				群組代號
			</div>
			<div class="border border-info rounded-0 p-3 text-center bg-white col-md-2 selectText" >
				{{merchant.id}}
			</div>
			<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-2">
				統一編號
			</div>
			<div class="border border-info rounded-0 p-3 text-center bg-white col-md-2 selectText">
				{{merchant.company.taxIdNumber}}
			</div>
			<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-2">
				計費類別
			</div>
			<div class="border border-info rounded-0 p-3 text-center bg-white col-md-2 selectText">
				{{merchant.storeTransactionFeeBillingType.name}}
			</div>
			<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-2">
				費率設定
			</div>
			<div class="border border-info rounded-0 p-3 text-center bg-white col-md-2 selectText">
				{{merchant.storeTransactionFeeRate.settingName}}
			</div>
			<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-2">
				付款方式
			</div>
			<div class="border border-info rounded-0 p-3 text-center bg-white col-md-2 selectText">
				{{merchant.storeTransactionFeePayer.name}}
			</div>
			<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-2">
				商店總數
			</div>
			<div class="border border-info rounded-0 p-3 text-center bg-white col-md-2 selectText">
				{{merchant.merchants.length}}
			</div>
		</div>
		<div class="border-0 pt-3">
			<DataTable ref="table2"
			:dataRow="dataRow2"
			:options ="tableOption2"/>
		</div>
		<Mask ref = "Mask">
			<div class="p-3">
				<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
					<div class = "p-2 text-center ">
						{{getMerchantType()}}商代設定
					</div>
					
					<div class="row p-3 rounded bg-light m-0">
						<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-6">
							統一編號
						</div>
						<div class="border border-info rounded-0 p-3 text-center bg-white col-md-6 selectText">
							{{merchant.company.taxIdNumber}}
						</div>
						<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-6">
							計費組態
						</div>
						<div class="border border-info rounded-0 p-3 text-center bg-white col-md-6 selectText">
							{{merchant.storeTransactionFeeRate.settingName}}
						</div>
						<div class=" order-md-1 order-0 col-md-6">
							<label for="nameCode" class="t-12">商店代號</label>
							<textarea type="text" name="phone" id = "phone" v-model="input.storeId"
							class="form-control text-left bg-white border-0"></textarea>
						</div>
						<div class=" order-md-1 order-0 col-md-6">
							<label for="nameTw" class="t-12">計算方式</label>
							<SelectView :options="selOption2.merchantBillingTypeId" :invalidTxt="invalids.merchantBillingTypeId"
							@updateVal="updateVal" ref = "merchantBillingTypeId" :key="selOption2.merchantBillingTypeId.key"/>

						</div>
						<div class=" order-md-1 order-0 col-md-6">
							<label for="nameEn" class="t-12">群組代號</label>
							<input  type="text" name="nameEn" id = "nameEn" v-model="input.storeTransactionFeeGroupId"
							class="form-control text-left bg-white border-0">
						</div>				
					</div>
				</div>
			</div>
			<div class="row mt-2 mb-5">
				<div class="col-md-4 offset-md-2 p-2">
					<div class="m-0">
						<button type="button" @click.prevent="hide"
						class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
							<span class="mx-2">取消</span>
						</button>
					</div>
				</div>
				<div class="col-md-4 p-2">
					<div class="m-0">
						<button type="button" @click.prevent="save"
						class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
							<span class="mx-2">儲存</span>
						</button>
					</div>
				</div>
			</div>
		</Mask>
	</div>
</template>
<script>
import Base from "@/components/Base" ;
import Mask from "@/components/Mask"
import { getSTFAll } from "@/utils/dataListCrm";
import SelectView from "@/components/Select" ;
import DataTable from '@/components/Table/DataTable' ;
import { isEmptyObject } from "@/utils/util";
import SearchBar from "@/components/Search" ;

export default {
	data(){
		return { 
			type 		: "GROUP" ,
			invalidFunc : {
	    		"storeId&taxIdNumber" : function( storeId , taxIdNumber ) {
	    			if( storeId === "" && taxIdNumber === "" ) {
	    				return "需有一個條件"
	    			} else {
	    				return "" ;
	    			}
	    		}
	    	},
			selInput 	: {
				storeId		 	: "" ,
				taxIdNumber 	: "" ,
			},
			selOption 	: {
				size : 2 // SearchBar預設一行顯示3個
			},
			dataRow 	: [] ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['id'],
						value : ['群組代號'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['company.taxIdNumber'],
						value : ['統一編號'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['storeTransactionFeeBillingType.name'],
						value : ['計費類型'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['storeTransactionFeeRate.settingName'],
						value : ['費率設定'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['storeTransactionFeePayer.name'],
						value : ['手續費付款方式'],
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
								icon: "fa-bars" ,
								func : function ( payment , index ){
									this.getMerchant( payment , index  ) ;
								}.bind(this)
							},
							{
								class : "" ,
								key : "" ,
								icon: "fa-pen" ,
								func : function ( payment , index ){
									this.edit( payment , index  ) ;
								}.bind(this)
							},
						]
					}
				],
				endButton : [
					{
						value : [""] ,
						icon: "fa-circle-plus" ,
						func : function ( payment ){
							this.goTo({name:"CRM1302_1",params:{type:"ADD",
							seloptionS:JSON.stringify(this.selInput)}});
						}.bind(this)
					}
				]	
			},
			merchant 		:{

			},
			dataRow2 		: [] ,
			input 			: this.getDefaultInp() ,
			type2  			: "ADD" ,
			tableOption2 	: {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['storeId'],
						value : ['商店代號'],
						filter 	: function ( value , key ){
                            return value.replace("\n","<BR>") ;
						}.bind(this)
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['merchantBillingType.name'],
						value : ['計算方式'],
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
								func : function ( payment , index ){
									this.editMerchant( payment , index  ) ;
								}.bind(this)
							},
							{
								class : "" ,
								key : "" ,
								icon: "fa-trash-can" ,
								func : function ( payment , index ){
									this.delMerchant( payment , index );
								}.bind(this)
							},
						]
					}
				],
				endButton : [
					{
						value : [""] ,
						icon: "fa-circle-plus" ,
						func : function ( payment ){
							this.addMerchant();
						}.bind(this)
					}
				]	
			},
			selOption2 : {
				merchantBillingTypeId  : {
                    ID          : "merchantBillingTypeId" ,
                    func        : "user/getSTFGMBType",
                    selType     : "dropdown" ,
                    key         : Date.now().toString(36)
                }
			},
			invalids:{},
		}
		 
	},
    components : {
    	Mask , SelectView , DataTable , SearchBar
    },
    extends : Base ,
    props : {
    	seloptionS : {
    		type : String ,
    		default: "",
    	}
    },
    watch : {    
    	selInput : function( new_value, old_value ){
    		console.log(new_value);
    	}    
    },
    mounted () {
    	let seloptionS = this.seloptionS ;

    	if( seloptionS ) {
    		seloptionS = JSON.parse(this.seloptionS);
    		this.selInput.storeId = seloptionS.storeId ;
    		this.selInput.taxIdNumber = seloptionS.taxIdNumber ;
    		if( seloptionS.storeId || seloptionS.taxIdNumber ) {
    			this.search();
    		}
    		
    	}
    },
    methods : {
    	
    	search (){
    		let _this = this ;
    		let params = {};
    		let selInput = this.selInput ;
    		this.type = "GROUP"
    		this.loading = true ;
    		Object.keys( selInput ).forEach(function(key){
    			let val = selInput[key] ;
    			if( val && val != "" ) {
    				if( key == "taxIdNumber" ) {
    					params["companyTaxIdNumber"] = val ;
    				} else {
    					params[key] = val ;
    				}
    			}
    		});
    		this.ApiUtil.get("store-transaction-fee/group/args",params)
			.then((res) => {
				if( res.success ) {
					_this.dataRow = res.data ;
				} else {
					_this.dataRow = [] ;
				}
				_this.loading = false ;
			});
    		
    		//console.log(this.selInput);
    	},
    	clear() {
			this.input = this.getDefaultInp() ;
			this.type = "GROUP" ;
			this.dataRow = [] ;
			this.dataRow2 = [] ;
			this.merchant = {} ;
     	},
    	edit( payment ) {
    		this.goTo({name:"CRM1302_1",params:{type:"EDIT",dataS:JSON.stringify(payment),
			seloptionS:JSON.stringify(this.selInput)}});
    	},
    	checkData( data ){
    		return typeof data === "object" && !isEmptyObject( data )
    	},
    	getMerchant(params){
    		let _this = this ;
    		this.loading = true ;
    
    		this.ApiUtil.get("/store-transaction-fee/group/id/"+params.id)
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					_this.merchant = data ;
					let merchants = data.merchants ;
					_this.dataRow2 = merchants ;
					_this.type = "MERCHANT";
				} else {
					_this.merchant = {} ;
					_this.dataRow2 = [] ;
					_this.type = "GROUP";
				}
				_this.loading = false ;
			});
    	},
    	getDefaultInp(){
    		return {
			  "merchantBillingTypeId"		: null ,
			  "storeId"						: "" ,
			  "storeTransactionFeeGroupId"	: null ,
			  "merchantId" 					: null 
			}
    	},
    	getMerchantType(){
			if( this.type2 == "EDIT") {	
				return this.$t("Maintenance").toString();
			} else {
				return this.$t("Add").toString() ;
			}
		},
		addMerchant(){
			let input = this.getDefaultInp();
			input.storeTransactionFeeGroupId = this.merchant.id ;
			this.setDefVal();
    		this.input = input ;
    		this.type2 = "ADD";		
    		this.$refs.Mask.show();
    	},
    	setDefVal(){
    		this.selOption2.merchantBillingTypeId.defVal = this.input.merchantBillingTypeId ;
    		this.selOption2.merchantBillingTypeId.key = Date.now().toString(36) ;
    	},
    	editMerchant( payment , index ){

    		this.input = {
    			merchantBillingTypeId 		: payment.merchantBillingType.id ,
    			storeId 					: payment.storeId ,
    			storeTransactionFeeGroupId 	: this.merchant.id ,
    			merchantId 					: payment.id
    		}
    		this.setDefVal();
    		this.type2 = "EDIT";
    		this.$refs.Mask.show();
    	},
    	delMerchant( payment , index ){
    		let _this = this ;
    		this.ApiUtil.delete("/store-transaction-fee/group-merchant/"+payment.id)
			.then((res) => {
				if( res.success ) {
					_this.showModel({
		                title : '刪除成功',
		                message : "刪除成功",
		                endFunc : function(){
		                	_this.getMerchant(_this.merchant);
		                }
		            });
				}
			});
    	},
    	save(){
    		let type = this.type2 ;
    		let input = this.input ;
    		let data = this.data ;
    		//this.$refs.Mask.hide();
    		let _this = this ;
    		let storeTransactionFeeGroupMerchantId = input.merchantId;
    		delete input.merchantId ; 
			this.loading = true ;
    		if( type == "ADD") {
    			input.config = [input.config];
    			this.ApiUtil.post("/store-transaction-fee/group-merchant",input)
				.then((res) => {
					_this.loading = false;
					if( res.success ) {
						_this.showModel({
			                title : '新增成功',
			                message : "新增成功",
			                endFunc : function(){
			                	_this.getMerchant(_this.merchant);
			                	_this.hide();
			                }
			            });
					} else {
						_this.hide();
					}
					
				})	
    		} else {
	    		this.ApiUtil.put("/store-transaction-fee/group-merchant/"+storeTransactionFeeGroupMerchantId,input)
				.then((res) => {
					_this.loading = false;
					if( res.success ) {
						_this.showModel({
			                title : '修改成功',
			                message : "修改成功",
			                endFunc : function(){
			                	_this.getMerchant(_this.merchant);
			                	_this.hide();
			                }
			            });
					} else {
						_this.hide();
					}
				})
    		}
    		
    		
    	},
    	hide(){
    		this.input = this.getDefaultInp();
    		this.$refs.Mask.hide();
    	},
    	updateVal( val , ID ) {
    		this.input[ID] = val ;
    	}
    }
}
</script>