<template>

	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ getTypeText() }}手續費群組
	</h4>
	<div class="p-3">
		<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
			<div class = "p-2 text-center ">
				手續費群組
			</div>
			
			<div class="row p-3 rounded bg-light m-0">
				<template v-if="type=='EDIT'">
					<div class=" order-md-1 order-0 col-md-6">
						<label for="contractNumber" class="t-12">群組代號</label>	
							<div style="min-height: 45px; padding: 10px;" >{{STFGId}}</div>
					</div>
					<div class=" order-md-1 order-0 col-md-6">
					</div>
				</template>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="companyTaxIdNumber" class="t-12">統一編號</label>
					<input  type="text" name="companyTaxIdNumber" id = "companyTaxIdNumber" v-model="input.companyTaxIdNumber"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_taxIdNumber">{{invalids.companyTaxIdNumber}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="storeTransactionFeeBillingTypeId" class="t-12">計費類型</label>
					<SelectView :options="selOption.storeTransactionFeeBillingTypeId" :invalidTxt="invalids.storeTransactionFeeBillingTypeId"
					@updateVal="updateVal" ref = "storeTransactionFeeBillingTypeId"/>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="storeTransactionFeeRateId" class="t-12">費率設定</label>
					<SelectView :options="selOption.storeTransactionFeeRateId" :invalidTxt="invalids.storeTransactionFeeRateId"
					@updateVal="updateVal" ref = "storeTransactionFeeRateId"/>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="storeTransactionFeePayer" class="t-12">手續費付款方式</label>
					<SelectView :options="selOption.storeTransactionFeePayerId" :invalidTxt="invalids.storeTransactionFeePayerId"
					@updateVal="updateVal" ref = "storeTransactionFeePayerId"/>
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
import { getContract , getSTFAll } from "@/utils/dataListCrm";

export default {

	data(){
		let input = this.getDefaultInp() ;	
		let STFGID = "" ;
		if( this.type == 'EDIT' ) {
			let data = JSON.parse(this.dataS);
			STFGID = data.id ;
			input.companyTaxIdNumber = data.company.taxIdNumber ;
			input.storeTransactionFeeRateId = data.storeTransactionFeeRate.id ;
			input.storeTransactionFeePayerId = data.storeTransactionFeePayer.id ;
			input.storeTransactionFeeBillingTypeId = data.storeTransactionFeeBillingType.id ;
		}
		return {
			selOption	: {
				storeTransactionFeeRateId	: {
					ID			: "storeTransactionFeeRateId" ,
					func		: getSTFAll.bind(this),
					selType		: "dropdown" ,
					filter 		: true ,
					defVal		: input.storeTransactionFeeRateId  ,
				},
				storeTransactionFeePayerId	: {
					ID			: "storeTransactionFeePayerId" ,
					func		: "user/getSTFPayer",
					selType		: "dropdown" ,
					filter 		: true ,
					defVal		: input.storeTransactionFeePayerId  ,
				},
				storeTransactionFeeBillingTypeId	: {
					ID			: "storeTransactionFeeBillingTypeId" ,
					func		: "user/getSTFBType",
					selType		: "dropdown" ,
					filter 		: true ,
					defVal		: input.storeTransactionFeeBillingTypeId ,
				},
			} ,
			invalids	: {} ,
			input		: input ,
			STFGId		: STFGID ,
			
		}
	},
	props : {
		type : {
			type 	: String ,
		},
		dataS : {
			type : String ,
		},
		seloptionS : {
			type : String ,
		}

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
			this.goBack();
		},
		save(){

			this.showLoading(true);
			let _this = this ;
			if( this.type == "EDIT") {
				this.ApiUtil.put("/store-transaction-fee/group/"+this.STFGId,this.input)
				.then((res) => {
					this.showLoading();
					if( res.success ) {
						_this.showModel({
			                title : '修改成功',
			                message : "修改成功",
			                endFunc : function(){
			                	_this.goBack();
			                }
			            });
					}
				})
				
			} else {
				this.ApiUtil.post("/store-transaction-fee/group/",this.input)
				.then((res) => {
					_this.showLoading();
					_this.clear();
					if( res.success ) {
						_this.showModel({
			                title : '新增成功',
			                message : "新增成功",
			                endFunc : function(){
			                	_this.goBack();
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
				"companyTaxIdNumber": "",
				"storeTransactionFeeBillingTypeId": null,
				"storeTransactionFeePayerId": null,
				"storeTransactionFeeRateId": null
			}
		},
		goBack( reload ){
			this.goTo({ name:"CRM1302",params:{ seloptionS : this.seloptionS}});
		}
	},

}	
</script>