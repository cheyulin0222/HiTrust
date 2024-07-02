<template>

	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		公司資料維護{{id}}
	</h4>
	<div class="p-3">
		公司資料
		<div class="row m-0 pt-3">		
			<div :class="getClass('t1Tilte')">公司名稱</div>
			<div :class="getClass('tiColumn')" class="selectText">{{comData.companyName}}</div>
			<div :class="getClass('t1Tilte')">公司網址</div>
			<div :class="getClass('tiColumn')" class="selectText" style=" word-break: break-all;">{{comData.url}}</div>
			<div :class="getClass('t1Tilte')">公司電話</div>
			<div :class="getClass('tiColumn')" class="selectText">{{comData.phone}}</div>
			<div :class="getClass('t1Tilte')">公司傳真</div>
			<div :class="getClass('tiColumn')" class="selectText">{{comData.fax}}</div>
			<div :class="getClass('t1Tilte')">負責業務</div>
			<div :class="getClass('tiColumn')" class="selectText">{{getSelect("salesId")}}</div>
			<div :class="getClass('t1Tilte')">公司統編</div>
			<div :class="getClass('tiColumn')" class="selectText">{{comData.taxIdNumber}}</div>
			<div :class="getClass('t1Tilte')">公司地址</div>
			<div :class="getClass('tiColumn',6)" class="selectText">{{comData.address}}</div>
			<div :class="getClass('t1Tilte')">產業別</div>
			<div :class="getClass('tiColumn')">
				<div class="row m-0">
					<div class="col-9 p-0">
						{{getSelect("categoryId")}}
					</div>
					<div class="col-3 p-0" 
						@click.prevent= "editCom" role="button"
					>
						<i class="fa img-fluid onactived fa-pen text-danger" style="fontSize:24px" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="p-3">
		閘道資料
		<label style="float: right;min-height: auto;" role="button" @click.prevent="add" class="btn btn-primary d-flex justify-content-center align-items-center">
			批次
		</label>
		<div class="border-0 pt-3">
			<DataTable ref="table1"
			:dataRow="dataRow"
			:options ="tableOption"
			@doOrder="doOrder"/>
		</div>
	</div>
	<div class="p-3">
		聯絡人資料
		<div class="border-0 pt-3">
			<DataTable ref="table2"
			:dataRow="dataRow2"
			:options ="tableOption2"/>
		</div>
	</div>
	<Mask ref = "Mask">
		<div class="p-3">
			<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
				<div class = "p-2 text-center ">
					批次建閘道
				</div>
				
				<div class="row p-3 rounded bg-light m-0">
					<input style="padding:10px" type="file" ref="files" :class="{'is-invalid': !!invalids.files}"/>
					<div class="invalid-feedback" id = "invalid_files">{{invalids.files}}</div>
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
					<button type="button" @click.prevent="updateFile"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">上傳</span>
					</button>
				</div>
			</div>
		</div>
	</Mask>
</template>
<script>
import DataTable from '@/components/Table/DataTable.vue' ;
import  moment from 'moment' ;
import { getListVal } from "@/utils/dataList";
import { mapGetters, mapState } from 'vuex';
import Base from "@/components/Base" ;
import Mask from "@/components/Mask.vue"

function getDefault () {
	return {
		dataRow 	: [] ,
		dataRow2 	: [] ,
		comData 	: {

		},
		upCompany 	: true ,
		upStore   	: true ,
		upPeople  	: true ,
		fail 		: false,
		invalids    : {} ,
 	}
}
export default {
	data(){
		return {
			orderedColumn : "ENVIRONMENT",
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['environment'],
						value : ['環境'] ,
						order: true,
						filter 	: function ( value , key ){							
                            return getListVal( this.EnvironmentId , value.id) ;
						}.bind(this)
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['storeId'],
						order: true,
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
						key : ['tags'],
						value : ['標籤']
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
									this.goTo({name:"CRM1202_3",params:{type:"EDIT",
										companyStoreId:payment.id,id:this.id}});
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

				] ,
				endButton : [
					{
						value : [""] ,
						icon: "fa-circle-plus" ,
						func : function ( payment ){
							this.goTo({name:"CRM1202_3",params:{type:"ADD",id:this.id}});
						}.bind(this)
					}
				]	
			},
			tableOption2 : {
				colum : [
					{	
						classT 	: "border border-info rounded-0 p-3 text-center alert-primary ",
						type 	: 'text',
						key 	: ['contactNotifyTypeId'],
						value 	: ['通知'],
						styleT  : "width:130px;",
						filter 	: function ( value , key ){
							return getListVal( this.NotifyType , value , "<br/>") ;                        
                        }.bind(this)
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['fullName'],
						styleT  : "width:80px;",
						value : ['姓名']
					},
										{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['mobilePhone'],
						styleT  : "width:115px;",
						value : ['手機']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['telephone'],
						styleT  : "width:110px;",
						value : ['電話']
					},
										{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['email'],
						value : ['電子郵件']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['notes'],
						value : ['備註']
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
									this.goTo({name:"CRM1202_4",params:{type:"EDIT",comDataS:JSON.stringify(this.comData),
										peoDataS:JSON.stringify(payment),id:this.id}});
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
									this.delPeople( payment );
								}.bind(this)
							},
						]
					}

				] ,
				endButton : [
					{
						value : [""] ,
						icon: "fa-circle-plus" ,
						func : function ( payment ){
							this.goTo({name:"CRM1202_4",params:{type:"ADD",comDataS:JSON.stringify(this.comData),id:this.id}})
						}.bind(this)
					}
				]	
			},
			...getDefault(),
		}
	},
	props : {
		id : {
			type : [ Number , String ] , 
			required : true
		} ,
		updateTab : {
			type : String , 
			default : '' ,
		}
	},
	extends : Base ,
	computed: {
		load : function () {
			this.loading = this.upCompany || this.upStore || this.upPeople ;
			return this.upCompany || this.upStore || this.upPeople ;
		},
		...mapGetters( 'user' , {
	        Acquirer 		: "getAcquirer",
	        EnvironmentId 	: "getEnvironmentId",
	        SalesId 		: "getSalesId",
	        CategoryId 		: "getCategoryId",
	        NotifyType 		: "getNotifyType" ,
      	})
	},
	watch: {     
        id : function( new_value, old_value ){
        	this.init();
        },
        load : function ( new_value, old_value ) {
        	this.loading = new_value ;
        },
    },
	components : {
		DataTable , Mask 
	},
	methods: {
		goBack(){
			this.$router.back();
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
		selComData() {
			let id = this.id ; 
			let _this = this ;
			//ajax
			this.upCompany = true ;
			if( id && !isNaN(parseInt(id)) ) {
				this.ApiUtil.get("company/id/" + id)
				.then((res) => {
					if( res.success ) {
						_this.comData = res.data ; 
						_this.upCompany = false ;
					} else {
						_this.upCompany = false ;
						let interval = setInterval(()=>{
							_this.isFail();
						} , 500 ) ;
						_this.intervals.push(interval);
					}
					
				});
			} else {
				this.upCompany = false ;
				_this.showModel({
	                title : '查詢公司資料失敗',
	                message : 'companyID錯誤:' + id,
	                type    : "error" ,
	                endFunc : function(){
	                	_this.isFail();
	                }
	            });
			}
			
		},
		selPeople(){
			let id = this.id ; 
			let _this = this ;
			//ajax
			this.upPeople = true ;
			if( id && !isNaN(parseInt(id)) ) {
				this.ApiUtil.get("/company-contact-people/company/"+id)
				.then((res) => {
					if( res.success ) {
						if( _this.NotifyType.length == 0 ) {
							let interval = setInterval(()=>{
								if( _this.NotifyType.length > 0 ) {
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
					_this.dataRow2 = data ;
					_this.upPeople = false ;
				});
			} else {
				_this.upPeople = false ;
				_this.showModel({
	                title : '查詢聯絡人失敗',
	                message : 'companyID錯誤:' + id,
	                type    : "error" ,
	                endFunc : function(){
	                	_this.isFail();
	                }
	            });
			}			
		},
		selStore() {
			let id = this.id ; 
			let _this = this ;
			//ajax
			this.upStore = true ;
			if( id && !isNaN(parseInt(id)) ) {
				this.ApiUtil.get("/store/company/" + id)
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
					_this.upStore = false ;
				});
			} else {
				_this.upStore = false ;
				_this.showModel({
	                title : '查詢閘道失敗',
	                message : 'companyID錯誤:' + id,
	                type    : "error" ,
	                endFunc : function(){
	                	_this.isFail();
	                }
	            });
			}
		},
		editCom () {
			this.goTo({name:"CRM1202_2",params:{type:"EDIT",comDataS:JSON.stringify(this.comData),id:this.id}})
		},
		init(){
			this.clear();
			this.selComData();
			this.selPeople();
			this.selStore();
		},
		getSelect ( id ) {
			let val = this.comData[id];
			let _this = this ;
			let result = "" ;
			let arr ;
			switch( id ) {
				case "salesId" : 
					arr = this.SalesId ;
				break ;
				case "categoryId" : 
					arr = this.CategoryId ;
				break ;
			}
			if( arr ) {
				if( arr.length == 0 ) {
					let interval = setInterval(()=>{
						let arr ; 
						switch( id ) {
							case "salesId" : 
								arr = _this.SalesId ;
							break ;
							case "categoryId" : 
								arr = _this.CategoryId ;
							break ;
						}
						if( arr.length > 0 ) {
							_this.clearInterval2(interval);
							return getListVal( arr , val ) ;
						}

					} , 500 ) ;
					_this.intervals.push(interval);

				} else {
					return getListVal( arr , val ) ;
				}
			} else {
				return result ;
			}
		},
		clear(){
			let _this = this ;
			let def = getDefault();
    		Object.keys(def).forEach(function(key){
    			_this[key] = def[key];
    			
    		})
		},
		delPeople( people ){
			let _this = this ;
	
			this.ApiUtil.delete("/company-contact-people/"+people.id)
			.then((res) => {

				if( res.success ) {
					_this.showModel({
		                title : '刪除成功',
		                message : "刪除成功",
		            });
		            this.selPeople();
				}
			})

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
		            this.selStore();
				}
			})
		},
		isFail(){
			this.fail = true ;
			this.goTo({name:"CRM1202"});
		},
		add(){
			this.$refs.Mask.show();
		},
		hide(){
			this.$refs.files.value = '' ;
			this.$refs.Mask.hide();
		},
		updateFile(){
			let invalids = this.invalids = {} ;
			const file = this.$refs.files.files[0];
			let _this = this ;
			if( file ) {
				let name = file.name ;
				if( name.endsWith(".xls") ) {
					const form = new FormData()
					form.append('file', file)
					form.append('companyId', this.id);
					_this.showLoading(true);
					this.ApiUtil.post("/store/batch",form,{
						'contentType' : "multipart/form-data"
					})
					.then((res) => {
						_this.showLoading();
						if( res.success ) {
							_this.showModel({
				                title : '新增成功',
				                message : "新增成功",
				                endFunc : function(){
				                	_this.hide();
				                	_this.selStore();
				                }			            
				            });
						}
					})
				} else {
					invalids.files = "請上傳正確格式的檔案";
				}
			} else {
				invalids.files = "請先選擇上傳檔案";
			}
		},
		doOrder(column) {
			this.orderedColumn = column;
			this.selStore();
		}
	},
	mounted () {
		this.loading = true ;
	},
	beforeUpdate () {

	},
	//keep-alive用
	activated () {
		if( this.fail ) {
			this.fail = false ;
			this.init();
		} else {
			switch ( this.updateTab ) {
	    		case "COMPANY" :
	    			this.selComData();
	    			break;
	    		case "PEOPLE" : 
	    			this.selPeople();
	    			break ;
	    		case "STORE" : 
	    			this.selStore();
	    			break ;
	    	}
		}
		
	},
	//keep-alive用
	deactivated() {
		let _this = this ;
    	this.intervals.forEach(function(interval){
    		_this.clearInterval2(interval); 
    	})
	},
	beforeMount(){
		
	},
	//銷毀前
    beforeUnmount(){
    	console.log("beforeUnmount");
    	/*intervals.forEach(function(interval){
    		clearInterval(interval); 
    	})    	*/
    },
}	
</script>