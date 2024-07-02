<template>

	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<div class="p-3">
		<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
			<div class = "p-2 text-center ">
				公司資料
			</div>
			
			<div class="row p-3 rounded bg-light m-0">
				<div class=" order-md-1 order-0 col-md-6">
					<label for="taxIdNumber" class="t-12">統一編號</label>
					<input  type="text" name="taxIdNumber" id = "taxIdNumber" v-model="input.taxIdNumber"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_taxIdNumber">{{invalids.taxIdNumber}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="companyName" class="t-12">公司名稱</label>
					<input  type="text" name="companyName" id = "companyName" v-model="input.companyName"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_companyName">{{invalids.companyName}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="url" class="t-12">公司網址</label>
					<input  type="text" name="url" id = "url" v-model="input.url"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_url">{{invalids.url}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="address" class="t-12">公司地址</label>
					<input  type="text" name="address" id = "address" v-model="input.address"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_address">{{invalids.address}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="phone" class="t-12">公司電話</label>
					<input  type="text" name="phone" id = "phone" v-model="input.phone"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_phone">{{invalids.phone}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="fax" class="t-12">公司傳真</label>
					<input  type="text" name="fax" id = "fax" v-model="input.fax"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_fax">{{invalids.fax}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="salesId" class="t-12">負責業務</label>
					<SelectView :options="selOption.salesId" :invalidTxt="invalids.salesId"
					@updateVal="updateVal" ref = "salesId"/>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="categoryId" class="t-12">產業別</label>
					<SelectView :options="selOption.categoryId" :invalidTxt="invalids.categoryId"
					@updateVal="updateVal" ref = "categoryId"/>
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
import { getCategoryId , getSalesId } from "@/utils/dataListCrm" ;

export default {

	data(){
		let input = this.comDataS ? JSON.parse(this.comDataS) : this.getDefaultInp() ;
		return {
			selOption 	: {
				categoryId : {
		    		ID 			: "categoryId" ,
		    		func 		: "user/getCategoryId" ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown" ,
		    		defVal 		: input.categoryId ? input.categoryId : null ,
		    	},
		    	salesId : {
		    		ID 			: "salesId" ,
		    		func 		: getSalesId.bind(this) ,
		    		addNullText : true ,
		    		selType 	: "dropdown" ,
		    		defVal 		: input.salesId ? input.salesId : null ,
		    	},
			} ,
			invalids	: {} ,
			input 	 	: input ,
			
		}
	},
	props : {
		type : {
			type 	: String ,
			default : "ADD"
		},
		id : {
			type : String ,
		},
		comDataS : {
			type : String ,
		}

	},
	extends : Edit ,
	watch : {
		
	},
	components : {
		SelectView
	},

	mounted () {
		
	},
	computed : {
		title(){
			if( i18n.global.locale == "en") {		
				return this.getTypeText() + " " + this.$t("CI").toString()
			} else {
				return this.$t("CI").toString() + this.getTypeText() ;
			}
		}
	},
	methods : {		
		channel(){
			if( this.type == "EDIT") {
				this.goTo({ name:"CRM1202_1",params:{ id : this.id , updateTab : ""} });
			} else {
				this.clear();
			}
		},
		save(){
			this.showLoading(true);
			let _this = this ;
			if( this.type == "EDIT") {
				this.ApiUtil.put("/company/"+this.id,this.input)
				.then((res) => {
					this.showLoading();
					if( res.success ) {
						_this.showModel({
			                title : '修改成功',
			                message : "修改成功",
			                endFunc : function(){
			                	_this.goTo({ name:"CRM1202_1",params:{ id : _this.id , updateTab : "COMPANY"} });
			                }
			            });
					}
				})
				
			} else {
				this.ApiUtil.post("/company",this.input)
				.then((res) => {
					_this.showLoading();
					_this.clear();
					if( res.success ) {
						_this.showModel({
			                title : '新增成功',
			                message : "新增成功",			            
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
				taxIdNumber 	: "" ,
				companyName 	: "" ,
				url				: "" ,
				address 		: "" ,
				phone 			: "" ,
				fax 			: "" ,
				salesId 		: "" ,
				categoryId 		: "" ,
			}
		}
	},
	beforeMount(){
			
	}
}	
</script>