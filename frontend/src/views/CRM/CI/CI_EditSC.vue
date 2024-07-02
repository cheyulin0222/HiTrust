<template>

	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		公司資料維護
	</h4>
	<div class="row p-3 m-0" >
		<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-2">公司統編</div>
		<div class="border border-info rounded-0 p-3 text-center bg-white col-md-3 selectText" >{{comData.taxIdNumber}}</div>
		<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-2">公司名稱</div>
		<div class="border border-info rounded-0 p-3 text-center bg-white col-md-5 selectText">{{comData.companyName}}</div>	
	</div>
	<div class="p-3">
		<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
			<div class = "p-2 text-center ">
				{{getTypeText()}}聯絡人 
				<div v-if="type == 'EDIT'" style="float: right;" role="button" @click.prevent="del">
					<i class="fa img-fluid onactived pr-md-3 fa-trash-can" style="font-size: 24px;"></i> 
				</div>
			</div>
			
			<div class="row p-3 rounded bg-light m-0">
				<div class=" order-md-1 order-0 col-md-6">
					<label for="taxIdNumber" class="t-12">通知</label>
					<SelectView :options="selOption.notifyType" :invalidTxt="invalids.notifyType"
					@updateVal="updateVal" ref = "notifyType"/>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="fullName" class="t-12">姓名</label>
					<input  type="text" name="fullName" id = "fullName" v-model="input.fullName"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_fullName">{{invalids.fullName}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="mobilePhone" class="t-12">手機</label>
					<input  type="text" name="mobilePhone" id = "mobilePhone" v-model="input.mobilePhone"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_mobilePhone">{{invalids.mobilePhone}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="telephone" class="t-12">電話</label>
					<input  type="text" name="telephone" id = "telephone" v-model="input.telephone"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_telephone">{{invalids.telephone}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="email" class="t-12">Email</label>
					<input  type="text" name="email" id = "email" v-model="input.email"
					class="form-control text-left bg-white border-0">
					<div class="invalid-feedback" id = "invalid_email">{{invalids.email}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="notes" class="t-12">備註</label>
					<textarea  type="text" name="notes" id = "notes" v-model="input.notes"
					class="form-control text-left bg-white border-0"></textarea>
					<div class="invalid-feedback" id = "invalid_notes">{{invalids.notes}}</div>
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
import i18n from '@/i18n'
import SelectView from "@/components/Select";
import Edit from "@/components/Edit" ;

export default {

	data(){
		this.comData = JSON.parse(this.comDataS) ;
		let input = this.peoDataS ? JSON.parse(this.peoDataS) : this.getDefaultInp() ;

		let defVal = input.contactNotifyTypeId ;

		return {
			selOption 	: {
				notifyType : {
		    		ID 			: "contactNotifyTypeId" ,
		    		func 		: "user/getNotifyType" ,
		    		selType 	: "selMore" ,
		    		defVal 		: defVal 
	    		}
	    	} ,
			invalids 	: {} ,
			input 		: input ,
		}
	},
	props : {
		type : {
			type : String ,
		},
		id : {
			type : String ,
		},
		comDataS : {
			type : String ,
		},
		peoDataS : {
			type : String ,
		},

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

	},
	methods : {
		getDefaultInp(){
			return {
				contactNotifyTypeId : [] ,
				email 				: "" ,
				fullName 			: "" ,
				mobilePhone 		: "" ,
				notes 				: "" ,
				telephone 			: "" ,
				companyId 			: this.comData.id 
			}
		},
		channel(){
			this.goTo({ name:"CRM1202_1",params:{ id : this.id , updateTab : "PEOPLE"} });
		},
		del(){
			if( type == "EDIT" ) {
				let _this = this ;
	
				this.ApiUtil.delete("/company-contact-people/"+this.input.id)
				.then((res) => {

					if( res.success ) {
						_this.showModel({
			                title : '刪除成功',
			                message : "刪除成功",
			                endFunc : function(){
			                	_this.goTo({ name:"CRM1202_1",params:{ id : _this.id , updateTab : "PEOPLE"} });
			                }
			            });
					}
				})
			}
		},
		save(){
			let _this = this ;
			if( this.type == "EDIT") {	
				this.ApiUtil.put("/company-contact-people/"+this.input.id,this.input)
				.then((res) => {
					this.showLoading();
					if( res.success ) {
						_this.showModel({
			                title : '修改成功',
			                message : "修改成功",
			                endFunc : function(){
			                	_this.goTo({ name:"CRM1202_1",params:{ id : _this.id , updateTab : "PEOPLE"} });
			                }
			            });
					}
				})
			} else {
				this.ApiUtil.post("/company-contact-people",this.input)
				.then((res) => {
					_this.showLoading();
					if( res.success ) {
						_this.showModel({
			                title : '新增成功',
			                message : "新增成功",
			                endFunc : function(){
			                	_this.goTo({ name:"CRM1202_1",params:{ id : _this.id , updateTab : "PEOPLE"} });
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
	},
	beforeMount(){
		
	}
}	
</script>