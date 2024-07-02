<template>

	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ getTypeText() }}合約資料
	</h4>
	<div class="p-3">
		<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
			<div class = "p-2 text-center ">
				合約資料
			</div>
			
			<div class="row p-3 rounded bg-light m-0">
				<div class=" order-md-1 order-0 col-md-6">
					<label for="contractNumber" class="t-12">合約編號</label>
					<input  type="text" name="contractNumber" id = "contractNumber" v-model="input.contractNumber"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_uniformNumbers">{{invalids.contractNumber}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="validYear" class="t-12">效期(年)</label>
					<input  type="number" name="validYear" id = "validYear" v-model="input.validYear"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_uniformNumbers">{{invalids.validYear}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-3">
					<label for="startDate" class="t-12">合約生效日</label>
					<input  type="date" name="startDate" id = "startDate" v-model="input.startDate"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_uniformNumbers">{{invalids.startDate}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-3">
					<label for="endDate" class="t-12">合約到期日</label>
					<input  type="date" name="endDate" id = "endDate" v-model="input.endDate"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_uniformNumbers">{{invalids.endDate}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-3">
					<label for="taxAmount" class="t-12">合約金額(已稅)</label>
					<input  type="number" name="taxAmount" id = "taxAmount" v-model="input.taxAmount"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_uniformNumbers">{{invalids.taxAmount}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-3">
					<label for="amount" class="t-12">合約金額(未稅)</label>
					<input  type="number" name="amount" id = "amount" v-model="input.amount"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_uniformNumbers">{{invalids.amount}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="renewalContractId" class="t-12">續約編號</label>
					<SelectView :options="selOption.renewalContractId" :invalidTxt="invalids.renewalContractId"
					@updateVal="updateVal" ref = "renewalContractId"/>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="notes" class="t-12">備註</label>
					<input  type="text" name="notes" id = "notes" v-model="input.notes"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_uniformNumbers">{{invalids.notes}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-3">
					<label for="endDate" class="t-12">簽約狀態</label>
					<SelectView :options="selOption.isNewContract" :invalidTxt="invalids.isNewContract"
					@updateVal="updateVal" ref = "isNewContract"/>
				</div>
				<div class=" order-md-1 order-0 col-md-3">
					<label for="taxAmount" class="t-12">簽約業務</label>
					<SelectView :options="selOption.salesId" :invalidTxt="invalids.salesId"
					@updateVal="updateVal" ref = "salesId"/>
				</div>
			</div>
		</div>
	</div>
	<div class="row mt-2 mb-5">
		<div class="col-md-4 offset-md-2 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="channel"
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
	

</template>
<script>
import i18n from '@/i18n' ;
import SelectView from "@/components/Select" ;
import Edit from "@/components/Edit" ;
import { getContract } from "@/utils/dataListCrm";

export default {

	data(){
		let input = this.dataS ? JSON.parse(this.dataS) : this.getDefaultInp() ;
		if( this.type == "ADD" ) {
			input.companyId = this.companyId ;
		}		
		return {
			selOption	: {
				salesId	: {
					ID			: "salesId" ,
					func		: "user/getSalesId" ,
					selType		: "dropdown" ,
					setFirst	: true ,
					defVal		: input.salesId ? input.salesId : null ,
				},
				renewalContractId	: {
					ID			: "renewalContractId" ,
					func		: getContract.bind(this,this.companyId),
					selType		: "dropdown" ,
					filter 		: true ,
					defVal		: input.renewalContractId ? input.renewalContractId : null ,
				},
				isNewContract	: {
					ID			: "isNewContract" ,
					func		: function( callBack ){
									callBack ([{
										text 	: "新客戶" ,
										val		: true ,
									},
									{
										text 	: "續約" ,
										val		: false ,
									}])
								},
					selType		: "dropdown" ,
					defVal		: input.isNewContract  ,
				},
			} ,
			invalids	: {} ,
			input		: input ,
			
		}
	},
	props : {
		type : {
			type 	: String ,
		},
		id : {
			type : String ,
		},
		dataS : {
			type : String ,
		},
		companyId : {
			type : String ,
		},
		comDataS : {
			type : String ,
		} ,
	},
	extends : Edit ,
	watch : {
		
	},
	components : {
		SelectView
	},
	beforeMount(){
			
	},
	mounted () {
		
	},
	computed : {

	},
	methods : {		
		channel(){
			this.goBack("");
		},
		save(){

			this.showLoading(true);
			let _this = this ;
			if( this.type == "EDIT") {
				this.ApiUtil.put("/company-contract/"+this.id,this.input)
				.then((res) => {
					this.showLoading();
					if( res.success ) {
						_this.showModel({
			                title : '修改成功',
			                message : "修改成功",
			                endFunc : function(){
			                	_this.goBack("TRUE");
			                }
			            });
					}
				})
				
			} else {
				this.ApiUtil.post("/company-contract",this.input)
				.then((res) => {
					_this.showLoading();
					_this.clear();
					if( res.success ) {
						_this.showModel({
			                title : '新增成功',
			                message : "新增成功",
			                endFunc : function(){
			                	_this.goBack("TRUE");
			                }			            
			            });
					}
				})
			}

		},
		getClass( type , setCol , defaultClass ){
			let className = "" ;
			switch ( type ) {
				case "t1Tilte" :
					className = "border border-info rounded-0 p-3 text-center alert-primary " ;
					break;
				case "tiColumn" : 
					className = "border border-info rounded-0 p-3 text-center bg-white";
					break
			}
			if( setCol && setCol != "" ) {
				className += " col-md-" + setCol ;
			} else {
				className += " col-md-2" ;
			}
			return className
		},
		getDefaultInp () {
			return {
				amount 				: 0 ,
				companyId 			: "" ,
				contractNumber  	: "" ,
				endDate 			: "" ,
				isNewContract		: true ,
				notes 				: "" ,
				salesId 			: "" ,
				renewalContractId	: "" ,
				startDate			: "" ,
				taxAmount			: 0 ,
				validYear			: "" ,
			}
		},
		goBack( reload ){
			this.goTo({ name:"CRM1101_1",params:{ id : this.companyId , reload : reload , comDataS : this.comDataS}});
		}
	},

}	
</script>