<template >

	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		閘道資訊
	</h4>
	<div class="row p-3 m-0" >
		<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-2 ">
			<span>環境</span>
		</div>
		<template v-if="type=='EDIT'">
			<div class="border border-info rounded-0 p-3 text-center bg-white col-md-2">{{getEnv()}}</div>
		</template>
		<template v-else>
			<div class="border border-info rounded-0 text-center col-md-2" style="padding:10px;">
				<SelectView :options="selOption.environmentId" :invalidTxt="invalids.environmentId"
				@updateVal="updateVal" ref = "environmentId"/>
			</div>
		</template>
		<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-2">
			<span>商代</span>
		</div>
		<template v-if="type=='EDIT'">
			<div class="border border-info rounded-0 p-3 text-center col-md-2" >{{store.storeId}}</div>
		</template>
		<template v-else>
			<div class="border border-info rounded-0 text-center col-md-2" style="padding:10px;">
				<input  type="text" name="storeId" id = "storeId" v-model="input.storeId"
					class="form-control text-left bg-white border-0"
					:class="{'is-invalid': !!invalids.storeId}">
				<div class="invalid-feedback" id = "invalid_storeId">{{invalids.storeId}}</div>
			</div>
		</template>
		<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-2">
			<span>MID</span>
		</div>
		<template v-if="type=='EDIT'">
			<div class="border border-info rounded-0 p-3 text-center bg-white col-md-2" >{{store.acquirerMerchantId}}</div>
		</template>
		<template v-else>
			<div class="border border-info rounded-0 text-center col-md-2" style="padding:10px;">
				<input  type="text" name="acquirerMerchantId" id = "acquirerMerchantId" v-model="input.acquirerMerchantId"
					class="form-control text-left bg-white border-0"
					:class="{'is-invalid': !!invalids.acquirerMerchantId}">
				<div class="invalid-feedback" id = "invalid_acquirerMerchantId">{{invalids.acquirerMerchantId}}</div>
			</div>
		</template>
		<template v-if="type=='EDIT'">
			<div class="border border-info rounded-0 p-3 text-center alert-primary col-md-2">建立日期</div>
			<div class="border border-info rounded-0 p-3 text-center bg-white col-md-10">{{store.createdAt}}</div>	
		</template>
	</div>
	<div class="p-3">
		<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
			<div class = "p-2 text-center ">
				閘道資料
				<div v-if="type=='EDIT'" style="float: right;" role="button" @click.prevent="del">
					<i class="fa img-fluid onactived pr-md-3 fa-trash-can" style="font-size: 24px;"></i> 
				</div>
			</div>
			
			<div class="row p-3 rounded bg-light m-0">
				<div v-if="type=='EDIT'" class=" order-md-1 order-0 col-md-6">
					<label for="taxIdNumber" class="t-12">商店中文名稱</label>
					<div style="min-height: 45px;padding: 10px;" class="selectText">{{store.nameTw}}</div>
				</div>
				<div v-if="type=='EDIT'" class=" order-md-1 order-0 col-md-6">
					<label for="companyName" class="t-12">商店英文名稱</label>
					<div style="min-height: 45px;padding: 10px;" class="selectText">{{store.nameEn}}</div>
				</div>
				<div v-if="type=='EDIT'" class=" order-md-1 order-0 col-md-6">
					<label for="url" class="t-12">網站名稱</label>
					<div style="min-height: 45px;padding: 10px;" class="selectText">{{store.url}}</div>
				</div>
				<div v-if="type=='EDIT'" class=" order-md-1 order-0 col-md-6">
					<label for="address" class="t-12">閘道狀態</label>
					<div style="min-height: 45px;padding: 10px;" class="selectText">{{store.status}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="phone" class="t-12">收單行</label>
					<template v-if="type=='EDIT'">
						<div style="min-height: 45px;padding: 10px;" class="selectText">{{getAcq()}}</div>
					</template>
					<template v-else>	
						<SelectView :options="selOption.acquirerId" :invalidTxt="invalids.acquirerId"
						@updateVal="updateVal" ref = "acquirerId"/>
					</template>
					
				</div>
				<div v-if="type=='EDIT'" class=" order-md-1 order-0 col-md-6">
					<label for="fax" class="t-12">幣別</label>
					<div style="min-height: 45px;padding: 10px;" class="selectText">{{store.currency}}</div>
				</div>
				<div v-if="type=='EDIT'" class=" order-md-1 order-0 col-md-6">
					<label for="phone" class="t-12">串接方式</label>
					<div style="min-height: 45px;padding: 10px;" class="selectText">{{store.technologyType}}</div>
				</div>
				<div v-if="type=='EDIT'" class=" order-md-1 order-0 col-md-6">
					<label for="fax" class="t-12">付款頁面</label>
					<div style="min-height: 45px;padding: 10px;" class="selectText">{{store.paymentPage}}</div>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<label for="phone" class="t-12">備註</label>
					<textarea type="text" name="phone" id = "phone" v-model="input.notes"
					class="form-control text-left bg-white border-0"></textarea>
				</div>
				<div class=" order-md-1 order-0 col-md-6">
					<SelectView :options="selOption2.tagIds" :invalidTxt="invalids.tagIds" LabelVal = "標籤" v-model="input['tagIds']" ref = "tagIds" />
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
		<div class="col-md-4  p-2">
			<div class="m-0">
				<button type="button" @click.prevent="save"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">儲存</span>
				</button>
			</div>
		</div>
	</div>
	

</template>
<script>
import i18n from '@/i18n'
import { getTagId , getEnvironmentId , getAcquirer } from "@/utils/dataListCrm";
import { getListVal } from "@/utils/dataList";
import { mapGetters, mapState } from 'vuex';
import SelectView from "@/components/Select";
import Edit from "@/components/Edit" ;

export default {

	data(){
		let input = this.getDefaultInp() ;
		return {
			store       : {
				tagIds      : [] ,
			} ,
			input 		: input ,
			selOption	: {
				environmentId : {
		    		ID 			: "environmentId" ,
		    		func 		: "user/getEnvironmentId" ,
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
	    		},
	    		acquirerId : {
		    		ID 			: "acquirerId" ,
		    		func 		: "user/getAcquirer",
					defVal 		: input.acquirerId ,
		    		filter 	 	: true ,
		    		selType 	: "dropdown"
		    		//placeholder : "請輸入產業別",
		    	},
			},
			invalids    : {} ,
			tagIds      : [] ,
		}
	},
	props : {
		type : {
			type : String ,
		},
		id : {
			type : String ,
		},
		companyStoreId : {
			type : String ,
		},
		goBackName : {
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
		selOption2 : function(){
			return {
				tagIds : {
		    		ID 		: "tagIds" ,
		    		func 	: "user/getTagId" ,
		    		selType : "selMore" ,
		    		key 	: Date.now().toString(36) ,
		    		defVal  : this.tagIds 
	    		},
	    	}
		},
		...mapGetters( 'user' , {
	        Acquirer 		: "getAcquirer",
	        EnvironmentId 	: "getEnvironmentId",
	        SalesId 		: "getSalesId",
	        CategoryId 		: "getCategoryId",
	        NotifyType 		: "getNotifyType" ,
      	})
	},
	methods : {		
		getDefaultInp(){
			if( this.type == "EDIT" ) {
				return {
					notes : "" ,
					tagIds: []
				}
			} else {
				return {
					acquirerId 			: "1" ,
					acquirerMerchantId 	: "" ,
					companyId			: "" ,
					environmentId 		: "" ,
					notes 				: "" ,
					storeId 			: "" ,
					tagIds 				: [] ,
					companyId 			: this.id ,
				}
			}
			
		},
		channel(){
			this.goBack();
		},
		save(){
			if( this.checkInputVal() ) {
				let _this = this ;
				if( this.type == "EDIT") {	
					this.ApiUtil.put("/store/"+this.companyStoreId,this.input)
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
					this.ApiUtil.post("/store",this.input)
					.then((res) => {
						_this.showLoading();
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
    	getEnv(){
    		if( this.store.environment) {
    			return getListVal( this.EnvironmentId , this.store.environment.id ) ;	
    		} else {
    			return "" ;
    		}
    		
    	},
    	getAcq(){
    		if( this.store.acquirer) {
    			return getListVal( this.Acquirer , this.store.acquirer.id ) ;	
    		} else {
    			return "" ;
    		}
    	},
    	checkInputVal() {
			this.invalids = {} ;

			let result = true ;
			let _this = this ;
			Object.keys( this.input ).forEach(function(key){
				let value = _this.input[key];
				let text = "" ;

				switch (key) {
					case 'storeId' : 
						if( !value || value == "" ) {
							text = "請輸入商家代號"
						}
					break;
				}

				if( text != "" ) {
					_this.invalids[key] = text ;
					result = false ;
				}
			});
			return result ;
		},
		del( ){
			if( this.type == "EDIT") {
				let _this = this ;
		
				this.ApiUtil.delete("/store/"+this.companyStoreId)
				.then((res) => {
					if( res.success ) {
						_this.showModel({
			                title : '刪除成功',
			                message : "刪除成功",
			                endFunc : function(){
			                	_this.goBack();
			                }
			            });
			            
					}
				})
			}
		},
		goBack() {
			if( this.goBackName == "CRM1203" ) {
				this.goTo({ name:"CRM1203"});
			} else {
				this.goTo({ name:"CRM1202_1",params:{ id : this.id , updateTab : "STORE"} });
			}
			
		}
	},
	beforeMount(){
		
		let id = this.companyStoreId ; 
		let _this = this ;

		//ajax
		_this.showLoading(true);
		if( id && !isNaN(parseInt(id)) ) {
			this.ApiUtil.get("/store/id/"+id)
			.then((res) => {
				if( res.success ) {
					return res.data ;
				} else {
					return {} ;
				}
				
			})
			.then((data)=>{
				_this.input.notes = data.notes ;
				_this.input.tagIds = data.tagIds ;
				_this.tagIds = data.tagIds ;
				_this.store = data ;
				_this.showLoading();
			});
		} else if(this.type == "EDIT"){
			_this.showLoading();
			_this.showModel({
                title : '查詢閘道失敗',
                message : 'storeID錯誤:' + id,
                type    : "error" ,
                endFunc : function(){
                	_this.goTo({name:"CRM1202"});
                }
            });
		} else {
			_this.showLoading();
		}
	},
}	
</script>