<template>

	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<div class="row p-3" ref = 'search' >
		<CardItem colMd="12">
			<SelectView :options="selOption.roleId" :invalidTxt="invalids.roleId" LabelVal = "角色代號"
			v-model="input.roleId" ref = "roleId" :required="true"/>
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
	@doSelPage="doSelPage">
	</DataTable>

	<Mask ref = "Mask">
		<div class="p-3">
			<Card :title = "getTitle" :mainCenter="true">
				<CardItem colMd="7" v-if="type != 'EDIT' ">
					<Input ID = "roleId" LabelVal = "角色代號(長度限制為32碼，請使用英文或數字)" :invalids = "invalids2" :required = "true" v-model="roleObj.roleId" :IDRandom="true" maxlength = "32"/>
				</CardItem>
				<CardItem colMd="7">
					<Input ID = "roleName" LabelVal = "角色名稱(長度限制為16碼)" :invalids = "invalids2" :required = "true" v-model="roleObj.roleName" maxlength = "16" @cickUpFunc="checkRoleNameLength"/>
				</CardItem>
				<CardItem colMd="7">
					<Input ID = "roleDesc" LabelVal = "角色說明(長度限制為32碼)" :invalids = "invalids2" v-model="roleObj.roleDesc" maxlength = "32" @cickUpFunc="checkRoleDescLength"/>
				</CardItem>
			</Card>
		</div>
		<div class="row m-0 mt-2 mb-3">
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="hideMask"
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
import Base from "@/components/RightViewBase" ;
import DataTable from '@/components/Table/PageDataTable.vue' ;
import SelectView from "@/components/Select";
import Mask from "@/components/Mask.vue"
import Input from "@/components/Input" ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import { getRoleNameList } from "@/utils/dataListSA" ;
import { isEmpty , deepCopy , inputMaxLength , hasChinese } from "@/utils/util";

export default {

	data(){
		let _this = this ;

		return {
			title 		: "角色管理維護" ,
			selOption 	: {
				roleId : {
		    		ID 			: "roleId" ,
		    		func 		: [{
						val 	: "" ,
						text 	: "請選擇角色代號" 
						
					}],
		    		//placeholder : "請輸入產業別",
		    		addNullText	: true ,
		    		filter 		: true ,
		    		selType 	: "dropdown"
		    	},
			},
			input : {
				roleId : "" ,
			},
			type  : "ADD" ,
			roleObj : this.getRoleObj(),
			invalids : {},
			invalids2 : {} ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['roleId'],
						value : ['角色代號'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['roleName'],
						value : ['使用者名稱']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['roleDesc'],
						value : ['角色說明'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['createTime'],
						value : ['角色建立日期'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
                        type: 'drop',
                        key: [],
                        func: [
                            {
                                span: `<span class="material-icons pr-2 t-14">
                                            reorder
                                        </span>`,
                                text: '複製現有權限到新的角色',
                                func: function ( item ) {
                                    this.copy( item );
                                }.bind(this),
                            },
                            {
                                span: `<span class="material-icons pr-2 t-14">
                                            reorder
                                        </span>`,
                                text: '修改',
                                func: function ( item ) {
                                    this.edit( item );
                                }.bind(this),
                            },
                            {
                                span: `<span class="material-icons pr-2 t-14">
                                            reorder
                                        </span>`,
                                text: '刪除',
                                func: this.del.bind(this) ,
                            },
                            {
                                span: `<span class="material-icons pr-2 t-14">
                                            reorder
                                        </span>`,
                                text: '變更角色權限',
                                func: function ( payment ) {
                                    this.goNextPath("SA1201_1",{
                                    	roleId : payment.roleId ,
                                    })
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
							this.showMask();
						}.bind(this)
					}
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
		SelectView , DataTable , Mask  , Input , Card , CardItem
	},
	computed : {
		getTitle () {
			let type = this.type ;
			if( type === 'ADD' ) {
				return "新增角色" ;
			} else if( type === "EDIT" ) {
				return "維護角色" ;
			} else {
				return "複製角色" ;
			}
		}
	},
	methods: {
		init() {
			let _this = this ;
			getRoleNameList( false , function( arr ){
				let defVal = "" ;
				
				arr.unshift({
					val 	: "" ,
					text 	: "請選擇角色代號" 
					
				})
				_this.$refs.roleId.setArr( arr , defVal ) ;
				_this.input.roleId = defVal ;
			})
			this.clearTable();
		},
		search(){
			let invalids = {} ;
			
			if( this.input.roleId == "" ) {
				invalids.roleId = "請先選擇角色代號" ;
			}
			
			this.invalids = invalids ;
			
			if( isEmpty(invalids) ) {
				this.doSelPage(1);
			}
		},
		doSelPage( page ){
			let _this = this ;
			let size = this.$refs.table1 ? this.$refs.table1.resize() : this.options.paginate.size ;
			let params = {
				"page" : page - 1 ,
				"size" : size
			};			
			
			_this.showLoading(true);
			this.ApiUtilSA.get("get-role/"+ this.input.roleId , params )
			.then((res) => {
				if( res.success ) {
					let content = res.data.content ;
					content.forEach(function( item ){
						item.previousData = JSON.stringify(item);
					});
					_this.dataRow = content ;
					_this.tableOption.paginate.page = page ;
	                _this.tableOption.paginate.total = res.data.totalElements ;
	                _this.tableOption.paginate.size = size ;
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
    	clear(){
    		this.$refs.roleId.clear();
    		this.type = "ADD" ;
	        this.clearTable();
    		this.roleObj = this.getRoleObj();
    	},
    	showMask(){
    		this.$refs.Mask.show();
    	},
    	hideMask(){
    		this.$refs.Mask.hide();
    		this.type = "ADD" ;
    		this.invalids2 = {} ;
    		this.roleObj = this.getRoleObj();
    	},
    	copy( item ){
    		this.type = "COPY" ;
    		this.roleObj = {
    			roleId 		: "" ,
				roleName 	: "" ,
				roleDesc 	: "" ,
				copyId 		: item.roleId ,
    		}
    		this.showMask();
    	},
    	edit( item ) {
    		this.type = "EDIT" ;
    		this.roleObj = {
    			roleId 		: item.roleId ,
				roleName 	: item.roleName ,
				roleDesc 	: item.roleDesc ,
				copyId 		: "" ,
				previousData: item.previousData ,
    		}
    		this.showMask();
    	},
    	add(){
    		this.type = "ADD" ;
    		this.roleObj = this.getRoleObj();
    		this.showMask();
    	},
    	del( payment ){
			let _this = this ;
			_this.showLoading(true);
			let param = { 
				previousData : payment.previousData 
			}
			this.ApiUtilSA.delete("delete-role/" + payment.roleId , param )
			.then( function( res ) {
				if( res.success && res.data.success) {
					_this.showModel({
		                title : _this.title ,
		                message : "刪除成功",
		                endFunc : function(){
		                	_this.init() ;
		                }
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
    	getRoleObj(){
    		return {
    			roleId 		: "" ,
				roleName 	: "" ,
				roleDesc 	: "" ,
				copyId 		: "" ,
    		}
    	},
    	save(){
    		let type = this.type ;
    		let roleObj = this.roleObj ;
    		let _this = this ;
    		
    		if( type === 'ADD' || type === "COPY" || type === "EDIT") {
    			let invalids2 = {} ;
    			let roleId 	 = roleObj.roleId ;
				let roleName = roleObj.roleName ;
				let roleDesc = roleObj.roleDesc ;
				let copyId   = roleObj.copyId ;

				if( roleId === '' ) {
					invalids2.roleId = "請輸入角色代號" ;
				}

				if( roleName === '' ) {
					invalids2.roleName = "請輸入角色名稱" ;
				}

				if( hasChinese( roleId ) ){
					invalids2.roleId = "角色代號不可有中文" ;
				}

				let param = {
					roleId 		: roleId ,
					roleName 	: roleName ,
					roleDesc 	: roleDesc ,
				}
				if( !isEmpty( invalids2 ) ) {
				} else if( type === 'ADD' ) {
					this.showLoading(true);
					this.ApiUtilSA.post("create-role" , param )
					.then((res) => {
						if( res.success && res.data.success ) {
							this.showModel({
				                title : this.title,
				                message : "新增成功",
				                endFunc : function(){
				                	_this.goNextPath("SA1201_1",{
						            	roleId : roleId 
						            });
				                }
				            });
						}
					})
					.finally(function () {
			        	_this.showLoading();
					});
    			} else if( type === 'COPY' ) {
					param.previousData = roleObj.previousData ;
					param.copyRoleId = copyId ;
					
					this.showLoading(true);
					this.ApiUtilSA.post("copy-roleright" , param )
					.then((res) => {
						if( res.success && res.data.success ) {
							this.showModel({
				                title : this.title,
				                message : "複製成功",
				                endFunc : function(){
				                	_this.goNextPath("SA1201_1",{
						            	roleId : roleId 
						            });
				                }
				            });
						}
					})
					.finally(function () {
			        	_this.showLoading();
					});
    			} else if( type === 'EDIT' ) {
    				param.previousData = roleObj.previousData ;
    				
    				this.showLoading(true);
    				this.ApiUtilSA.post("update-role" , param )
					.then((res) => {
						if( res.success && res.data.success ) {
							this.showModel({
				                title : this.title,
				                message : "修改成功",
				                endFunc : function(){
				                	_this.hideMask();
				                	_this.init();
				                }
				            });
						}
					})
					.finally(function () {
			        	_this.showLoading();
					});
    			}
    			this.invalids2 = invalids2 ;    			
    		} else {
    			this.hideMask();
    		}
    	},
    	checkRoleDescLength( event ){
    		let RoleDesc = inputMaxLength(event, 32, "角色說明" ) ;
    		if( RoleDesc ) {
    			this.roleObj.roleDesc = RoleDesc ;
    		}
    	},
    	checkRoleNameLength( event ){
    		let roleName = inputMaxLength(event, 16, "角色名稱" ) ;
    		if( roleName ) {
    			this.roleObj.roleName = roleName ;
    		}
    	},
	}
}	
</script>