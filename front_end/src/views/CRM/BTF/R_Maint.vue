<template >
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		手續費率設定
	</h4>
	<div class="row p-3 justify-content-center">
		<div class=" order-md-1 order-0 col-md-8">
			<label for="acquirerId" class="t-12">收單銀行</label>
			<SelectView :options="selOption.acquirerId" :invalidTxt="invalids.acquirerId"
			@updateVal="updateVal" ref = "acquirerId" :key="selOption.acquirerId.key"/>
		</div>
	</div>

	<div class="row m-0 p-3">
		<div class="p-2  order-0 col-md-1" ></div>		
		<div :class="getClass('t1Tilte')">收單銀行</div>
		<div :class="getClass('tiColumn')">{{acquirerName}}</div>
	</div>

	<div class="p-3">
		<div class="border-0 pt-3">
			<DataTable ref="table1"
			:dataRow="dataRow"
			:options ="tableOption"/>
		</div>
	</div>

	<Mask ref = "Mask">
		<div class="p-3">
				<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
					<div class = "p-2 text-center ">
						{{getType()}}手續費設定
					</div>
					
					<div class="row p-3 rounded bg-light m-0">
						<div class=" order-md-1 order-0 col-md-6">
							<label for="BTFType" class="t-12">收費類型</label>
							<SelectView :options="selOption2.BTFType" :invalidTxt="invalids.BTFType"
							@updateVal="updateVal2" ref = "BTFType" :key="selOption2.BTFType.key"/>
						</div>
						<div class=" order-md-1 order-0 col-md-6">
							<label for="feeCycleMonth" class="t-12">收費週期</label>
							<input  type="number" name="feeCycleMonth" id = "feeCycleMonth" v-model="input.feeCycleMonth"
							class="form-control text-left bg-white border-0">
						</div>
						<div class=" order-md-1 order-0 col-md-6">
							<label for="miniNumber" class="t-12">級距下限</label>
							<input  type="number" name="miniNumber" id = "miniNumber" v-model="input.miniNumber"
							class="form-control text-left bg-white border-0"
							:class="{'is-invalid': !!invalids.miniNumber}">
							<div class="invalid-feedback" id = "invalid_notes">{{invalids.miniNumber}}</div>
						</div>
												<div class=" order-md-1 order-0 col-md-6">
							<label for="maxiNumber" class="t-12">級距上限</label>
							<input  type="number" name="maxiNumber" id = "maxiNumber" v-model="input.maxiNumber"
							class="form-control text-left bg-white border-0"
							:class="{'is-invalid': !!invalids.maxiNumber}">
							<div class="invalid-feedback" id = "invalid_notes">{{invalids.maxiNumber}}</div>
						</div>
						<div class=" order-md-1 order-0 col-md-6">
							<label for="feeRate" class="t-12">手續費費率(%)</label>
							<input  type="number" name="feeRate" id = "feeRate" v-model="input.feeRate" step="0.01"
							class="form-control text-left bg-white border-0"
							:class="{'is-invalid': !!invalids.feeRate}">
							<div class="invalid-feedback" id = "invalid_notes">{{invalids.feeRate}}</div>
						</div>

						<div class=" order-md-1 order-0 col-md-6">
							<label for="fixedFee" class="t-12">固定手續費</label>
							<input  type="number" name="fixedFee" id = "fixedFee" v-model="input.fixedFee"
							class="form-control text-left bg-white border-0"
							:class="{'is-invalid': !!invalids.fixedFee}">
							<div class="invalid-feedback" id = "invalid_notes">{{invalids.fixedFee}}</div>
						</div>
						<div class=" order-md-1 order-0 col-md-6">
							<label for="startDate" class="t-12">起始日期</label>
							<input  type="date" name="startDate" id = "startDate" v-model="input.startDate"
							class="form-control text-left bg-white border-0">
							<div class="invalid-feedback" id = "invalid_uniformNumbers">{{invalids.startDate}}</div>	
						</div>
						<div class=" order-md-1 order-0 col-md-6">
							<label for="endDate" class="t-12">結束日期</label>
							<input  type="date" name="endDate" id = "endDate" v-model="input.endDate"
							class="form-control text-left bg-white border-0">
							<div class="invalid-feedback" id = "invalid_uniformNumbers">{{invalids.endDate}}</div>	
						</div>
						<div class=" order-md-1 order-0 col-md-6">
							<label for="isPostPay" class="t-12">收費方式</label>
							<SelectView :options="selOption2.isPostPay" :invalidTxt="invalids.isPostPay"
							@updateVal="updateVal2" ref = "isPostPay" :key="selOption2.isPostPay.key"/>
						</div>
						<div class=" order-md-1 order-0 col-md-6">
							<label for="notes" class="t-12">說明</label>
							<input  type="text" name="notes" id = "notes" v-model="input.notes"
							class="form-control text-left bg-white border-0"
							:class="{'is-invalid': !!invalids.notes}">
							<div class="invalid-feedback" id = "invalid_notes">{{invalids.notes}}</div>
						</div>
					</div>
				</div>
			</div>
		<div class="row mt-2 mb-5">
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="hide()"
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
</template>
<script>
import Base from "@/components/Base" ;
import Mask from "@/components/Mask"
import { getListVal } from "@/utils/dataList";
import SelectView from "@/components/Select" ;
import DataTable from '@/components/Table/PageDataTable' ;
import { mapGetters, mapState } from 'vuex';
import  moment from 'moment'

export default {
	data(){
		return { 
			type : "ADD" ,
			invalids : {},
			input : this.getDefaultInp() ,
			data : {
			},
			configIndex : null ,
			acquirerId 	 : "" ,
			acquirerName : "" ,
			dataRow : [] ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['bankTransactionFeeTypeId'],
						value : ['收費類型'],
						filter 	: function ( value , key ){
                            return getListVal( this.BTFTypeList , value) ;
						}.bind(this)
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['feeCycleMonth'],
						value : ['收費週期'],
						filter 	: function ( value , key ){
                            return value + " 月" ;
						}.bind(this)
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['miniNumber'],
						value : ['級距下限'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['maxiNumber'],
						value : ['級距上限'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['feeRate'],
						value : ['手續費費率(%)'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['fixedFee'],
						value : ['固定手續費'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['startDate'],
						value : ['起始日期'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['endDate'],
						value : ['結束日期'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['isPostPay'],
						value : ['收費方式'],
						filter 	: function ( value , key ){
							return getListVal( this.isPostPayList , value )                          
						}.bind(this)
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['notes'],
						value : ['備註'],
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
									this.EDIT( payment , index  ) ;
								}.bind(this)
							},
							{
								class : "" ,
								key : "" ,
								icon: "fa-trash-can" ,
								func : function ( payment , index ){
									this.DEL( payment , index );
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
							this.ADD();
						}.bind(this)
					}
				]	
			},
		}
		 
	},
    components : {
    	Mask , SelectView , DataTable
    },
    computed 	: {
    	isPostPayList : function(){
    		return [
    			{
    				val 	: true ,
					text 	: "後收"
    			},
    			{
    				val 	: false ,
					text 	: "先收"
    			}
    		]
    	},
    	selOption	: function(){
			return {
				acquirerId	: {
					ID			: "acquirerId" ,
					func		: this.AcquirerList,
					selType		: "dropdown" ,
					filter 		: true ,
					key 		: Date.now().toString(36)
				}
			}
		},
		selOption2	: function(){
			return {
				BTFType	: {
					ID			: "bankTransactionFeeTypeId" ,
					func		:this.BTFTypeList,
					selType		: "dropdown" ,
					defVal 		: this.input.bankTransactionFeeTypeId ,
					key 		: Date.now().toString(36)
				},
				isPostPay : {
					ID			: "isPostPay" ,
					func		:this.isPostPayList,
					defVal 		: this.input.isPostPay ,
					selType		: "dropdown" ,
					key 		: Date.now().toString(36)
				}
			}
		},
    	...mapGetters( 'user' , {
	        BTFTypeList 	: "getBTFType",
	        AcquirerList 	: "getAcquirer" ,
      	})
    },
    watch : {
    	acquirerId : function( nVal , oVal) {
    		this.acquirerName = getListVal(this.AcquirerList , nVal )
    	}
    },
    extends : Base ,
    methods : {  	
    	getClass( type , setCol , defaultClass ){
			let className = "" ;
			switch ( type ) {
				case "t1Tilte" :
					className = "border border-info rounded-0 p-3 text-center alert-primary col-md-2" ;
					break;
				case "tiColumn" : 
					className = "border border-info rounded-0 p-3 text-center bg-white col-md-4";
					break
			}
			
			return className
		},
		getType(){
			if( this.type == "EDIT") {	
				return this.$t("Maintenance").toString();
			} else {
				return this.$t("Add").toString() ;
			}
		},
    	updateVal( val , ID ) {
    		this.acquirerId = val ;
    		let _this = this ;
    		this.loading = true ;
	    	this.ApiUtil.get("bank-transaction-fee/rate/acquirer/"+val)
			.then((res) => {
				if( res.success ) {
					_this.dataRow = res.data ;
				} else {
					_this.dataRow = [] ;
				}
			})
			.finally(function () {
				_this.loading = false ;
			});
	    },
	    updateVal2( val , ID ) {
	    	this.input[ID] = val ;
	    },
    	hide(){
    		this.input = this.getDefaultInp();
    		this.$refs.Mask.hide();
    	},
    	save(){
    		this.$refs.Mask.hide();
    		let acquirerId = this.acquirerId ;
    		if( acquirerId ) {
	    		let type = this.type ;
	    		let input = this.input ;
	    		let data = this.dataRow ;		
	    		this.loading = true ;
	    		if( type == "ADD") {
	    			data.push(input);    	
	    		} else if( type == "EDIT" ) {
	    			data[this.configIndex] = input ;
		    	} else if( type = "DELETE" ) {
		    		data.splice(this.configIndex,1);
		    	} else {
		    		this.loading = false ;
	    			input = this.input = this.getDefaultInp();
	    			this.showModel({
	                    title : '資料有誤',
	                    message : '資料有誤',
	                });
	                return ;
	    		}
	    		let _this = this ;

	    		this.ApiUtil.put("/bank-transaction-fee/rate/" + acquirerId,data)
				.then((res) => {
					if( res.success ) {
						_this.showModel({
			                title : '修改成功',
			                message : "修改成功",
			                endFunc : function(){
			                	_this.updateVal( acquirerId ) ;
			                }
			            });
					}
					//console.log(res);
					this.loading = false ;
				});
	    		this.loading = false ;
	    	} else {
    			this.showModel({
	                type    : "error" ,
	                title   : "修改失敗" ,
	                message : "請先選擇收單銀行"
	            });
    		}
    		
    	},
    	DEL( payment , index ){
    		if( this.acquirerId ) {
	    		this.type = "DELETE" ;
	    		this.configIndex = index ;
	    		this.save();
	    	} else {
    			this.showModel({
	                type    : "error" ,
	                title   : "刪除失敗" ,
	                message : "請先選擇收單銀行"
	            });
    		}
    	},
    	ADD(){
    		if( this.acquirerId ) {
    			this.input = this.getDefaultInp();
	    		this.type = "ADD";
	    		this.$refs.Mask.show();
	    		this.configIndex = null ;
    		} else {
    			this.showModel({
	                type    : "error" ,
	                title   : "新增失敗" ,
	                message : "請先選擇收單銀行"
	            });
    		}
    		
    	},
    	EDIT( payment , index ){
    		if( this.acquirerId ) {
	    		this.input = Object.assign({},payment)
	    		this.configIndex = index ;
	    		this.type = "EDIT";
	    		this.$refs.Mask.show();
	    	} else {
    			this.showModel({
	                type    : "error" ,
	                title   : "修改失敗" ,
	                message : "請先選擇收單銀行"
	            });
    		}
    	},
    	getDefaultInp(){
    		let today = moment().format('YYYY-MM-DD') ;
    		return {
				"bankTransactionFeeTypeId": null ,
				"endDate": today,
				"feeCycleMonth": 0,
				"feeRate": 0,
				"fixedFee": 0,
				"isPostPay": true,
				"maxiNumber": 0,
				"miniNumber": 0,
				"notes": "",
				"startDate": today
			}
    	},
    },
}
</script>