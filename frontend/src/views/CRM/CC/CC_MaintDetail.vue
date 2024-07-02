<template>

	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		公司合約維護
	</h4>
	<div class="row m-0 pt-3">		
		<div :class="getClass('t1Tilte')">統編</div>
		<div :class="getClass('tiColumn')" class="selectText">{{comData.taxIdNumber}}</div>
		<div :class="getClass('t1Tilte')">公司名稱</div>
		<div :class="getClass('tiColumn')" class="selectText">{{comData.companyName}}</div>
	</div>
	<br><br><br>
	<DataTable ref="table"
	:dataRow="dataRow"
	:options ="tableOption"/>
	
	<Mask ref = "Mask">
		<div class="p-3">
			<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
				<div class = "p-2 text-center ">
					商代清單		
				</div>
				<div class="row p-3 rounded bg-light m-0">
					<table class="table table-hover m-0">
						<template v-for="id in storeList">
							<tr>
								<td class="text-center"> {{ id }}</td>
							
								<td>
									<div class="text-center" role="button" @click.prevent="delStorid(id)">
										<i class="fa img-fluid onactived pr-md-3 fa-trash-can" style="font-size: 24px;"></i> 
									</div>
								</td>
							</tr>
						</template>
							<tr>
								<td class="text-center"> 
									<input type="text" name="storeId" ref = "storid" style="width:150px ; border: 0; padding:10px">
								</td>
							
								<td>
									<div class="text-center" role="button" @click.prevent="addStorid">
										<i class="fa img-fluid onactived pr-md-3 fa-circle-plus" style="font-size: 24px;"></i> 
									</div>
								</td>
							</tr>	
					</table>		
				</div>
			</div>
			<div class="row mt-2">
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
		</div>
	</Mask>
</template>
<script>
import DataTable from '@/components/Table/DataTable.vue' ;
import Dialog from '@/components/Dialog/DialogBase.vue' ;
import { getListVal } from "@/utils/dataList";
import { mapGetters, mapState } from 'vuex';
import Base from "@/components/Base" ;
import Mask from "@/components/Mask"

export default {
	data(){
		return {
			input : {

			},
			option : {
				size : 3 // SearchBar預設一行顯示3個
			},
			invalidFunc : { // 自訂SearchBar 檢核Func
				
			},
			storeList :[] ,
			companyContractId : "" ,
			dataRow : [] ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['contractNumber'],
						value : ['合約編號'] 
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['validYear','年'],
						value : ['效期']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ["startDate"],
						value : ['合約生效日']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['endDate'],
						value : ['合約到期日']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['amount'],
						value : ['合約金額(未稅)'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['taxAmount'],
						value : ['合約金額(已稅)'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['isNewContract'],
						value : ['簽約狀態'],
						filter 	: function ( value , key ){
							if( value ) {
								return "新客戶"
							} else {
								return "續約" 
							}                       
                        }.bind(this)
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['salesId'],
						value : ['簽約業務'],
						filter 	: function ( value , key ){
							return getListVal( this.SalesId , value ) ;                        
                        }.bind(this)
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['renewalContractText'],
						value : ['續約編號'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['notes'],
						value : ['備註']
					},
					{
						classT : "border border-info rounded-0 text-center alert-primary ",
						type : 'drop',
						key : ['status'],
						value : [""],
						classR: "d-flex" ,

						func : [
							/*{
			                    span : `<span class="fas fa-print img-fluid onactived pr-md-3"></span>`, //span的HTML
			                    text : '列印' , //span的HTML
			                    func : function (payment) {
			                        console.log("print")
			                    }.bind(this),
			                },*/
			                {
			                    span : `<span class="fas fa-money-check-alt img-fluid onactived pr-md-3"></span>`, //span的HTML
			                    text : '一鍵續約' , //span的HTML
			                    func : function (payment) {
			                    	let _this = this ;
			                        this.ApiUtil.post("/company-contract/renewal/"+payment.id)
									.then((res) => {
										if( res.success ) {
											_this.showModel({
								                title : '續約成功',
								                message : "續約成功",
								            });
								            this.search();
										}
									});
			                    }.bind(this),
			                },
			                {
			                    span : `<span class="fas fa-list-ul img-fluid onactived pr-md-3"></span>`, //span的HTML
			                    text : '商代清單' , //span的HTML
			                    func : function (payment) {
			                    	this.storeList = payment.storeList ? payment.storeList : [] ;
			                    	this.companyContractId = payment.id ;
			                    	this.$refs.Mask.show();
			                    }.bind(this),
			                },
			                {
			                    span : `<span class="fas fa-trash-can img-fluid onactived pr-md-3"></span>`, //span的HTML
			                    text : '刪除' , //span的HTML
			                    func : function (payment) {
			                        this.del( payment.id );
			                    }.bind(this),
			                },
			                {
			                    span : `<span class="fas fa-pen img-fluid onactived pr-md-3"></span>`, //span的HTML
			                    text : '編輯' , //span的HTML
			                    func : function (payment) {
			                        this.goTo({name:"CRM1101_2",params:{type:"EDIT",id:payment.id ,dataS :JSON.stringify(payment),comDataS:this.comDataS,companyId:this.id}});
			                    }.bind(this),
			                },
						]
					}

				] ,
				endButton : [
					{
						value : [""] ,
						icon: "fa-circle-plus" ,
						func : function ( payment ){
							this.goTo({name:"CRM1101_2",params:{type:"ADD",companyId:this.id,comDataS:this.comDataS}});
						}.bind(this)
					}
				]
			},
			DialogOption : {
				title : "商代清單",
				className : "text-center",
				clickOk : function(){
					this.DialogOption.msg = "" ;
				}.bind(this)
			}
		}
	},
	extends : Base ,
	computed: {
		comData : function () {
			return JSON.parse(this.comDataS) ;
		},
		...mapGetters( 'user' , {
	        SalesId 		: "getSalesId",
      	})
	},
	props : {
		comDataS : {
			type : String ,
		} ,
		id : {
			type : [ Number , String ] , 
			required : true
		},
		reload 	: {
			type: String ,
		}
	},
	components : {
		DataTable , Dialog , Mask
	},
	methods: {
		search(){
			let id = this.id ; 
			let _this = this ;
			//ajax
			this.loading = true ;
			if( id && !isNaN(parseInt(id)) ) {
				this.ApiUtil.get("/company-contract/company/"+id)
				.then((res) => {
					if( res.success ) {
						if( _this.SalesId.length == 0 ) {
							let interval = setInterval(()=>{
								if( _this.SalesId.length > 0) {
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
					let map = new Map() ;
					data.forEach(function( obj ){
						map.set(obj.id,obj.contractNumber);
					});
					data.forEach(function( obj) {
						let renewalContractText = "" ;
						if( obj.renewalContractId ) {
							renewalContractText = map.get(obj.renewalContractId);
							if( !renewalContractText ) {
								renewalContractText = "" ;
								obj.renewalContractId = null ;
							}
						}
						obj.renewalContractText = renewalContractText ;
					})

					_this.dataRow = data ;
					_this.loading = false ;
				});
			} else {
				_this.loading = false ;
				_this.showModel({
	                title : '查詢合約失敗',
	                message : 'companyID錯誤:' + id,
	                type    : "error" ,
	                endFunc : function(){
	                	_this.isFail();
	                }
	            });
			}
		},
		getClass( type , setCol , defaultClass ){
			let className = "" ;
			switch ( type ) {
				case "t1Tilte" :
					className = "border border-info rounded-0 p-3 text-center alert-primary col-md-3" ;
					break;
				case "tiColumn" : 
					className = "border border-info rounded-0 p-3 text-center bg-white col-md-3";
					break
			}
			
			return className
		},
		init(){
			this.search();
		},
		isFail(){
			this.fail = true ;
			this.goTo({name:"CRM1101"});
		},
		del( companyContractId ){
			let _this = this ;
	
			this.ApiUtil.delete("/company-contract/"+ companyContractId )
			.then((res) => {

				if( res.success ) {
					_this.showModel({
		                title : '刪除成功',
		                message : "刪除成功",
		            });
		            this.search();
				}
			})

		},
		delStorid(id){
			let index = this.storeList.indexOf(id);
			if( index > -1 ) {
				this.storeList.splice(index, 1);
			}
		},
		addStorid(){
			var id = this.$refs.storid.value ;
			let index = this.storeList.indexOf(id);
			if( index < 0 ) {
				this.storeList.push(id);
			}
			this.$refs.storid.value = "" ;
		}
		,hide(){
    		this.storeList=[];
    		this.$refs.storid.value = "" ;
    		this.companyContractId = "" ;
    		this.$refs.Mask.hide();
    		this.search();
    	},
    	save(){
    		let id = this.companyContractId ;
    		let _this = this ;
    		if( id && !isNaN(parseInt(id)) ) {
    			this.loading = true ;

    			this.ApiUtil.put("/company-contract/store/"+id,this.storeList)
				.then((res) => {
					_this.loading = false;
					if( res.success ) {
						_this.showModel({
			                title : '修改商代成功',
			                message : "修改商代成功",
			                endFunc : function(){
			                	//_this.search();
			                	_this.hide();
			                }
			            });
					}
				})	
    		} else {
				_this.loading = false ;
				_this.showModel({
	                title : '修改商代失敗',
	                message : 'companyContractId錯誤:' + id,
	                type    : "error" ,
	                endFunc : function(){
	                	_this.hide();
	                }
	            });
			}	
    	},
	},
	watch: {     
        id : function( new_value, old_value ){
        	this.init();
        },
    },
    //keep-alive用
	activated () {
		if( this.fail ) {
			this.fail = false ;
			this.init();
		} else if( this.reload == "TRUE" ) {
			this.init();
		}
		
	},
	//keep-alive用
	deactivated() {
		let _this = this ;
    	this.intervals.forEach(function(interval){
    		_this.clearInterval2(interval); 
    	})
	},
	mounted () {
		this.$refs.Mask.setWidth('400px');
	}
}	
</script>