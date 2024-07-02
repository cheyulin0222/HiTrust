<template>	
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<div class="row p-3" ref = 'search' >
		<CardItem colMd="6">
			<Input ID = "userId" LabelVal = "使用者代碼" :input = "input" :invalids = "invalids"/>
		</CardItem>
		<CardItem colMd="6">
			<SelectView :options="selOption.roleId" :invalidTxt="invalids.roleId" LabelVal = "使用者角色"
			v-model = "input.roleId"  ref = "roleId"/>
		</CardItem>
		<CardItem colMd="6">
			<Input ID = "merId" LabelVal = "商家代碼" :input = "input" :invalids = "invalids" />
		</CardItem>
		<CardItem colMd="6">
			<Input ID = "updateUser" LabelVal = "更新人員" :input = "input" :invalids = "invalids" />
		</CardItem>
	</div>
	<div class="row mt-2 mb-5">
		<div class="col-md-4 offset-md-2 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="clear"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">清除重設</span>
				</button>
			</div>
		</div>
		<div class="col-md-4 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="search"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">查詢</span>
				</button>
			</div>
		</div>
	</div>
	<DataTable ref="table1" :dataRow="dataRow" :options ="tableOption" @doSelPage="doSelPage"/>
	<Mask ref = "Mask">
		<div class="p-3">
			<Card title = "人員資料" >
				<CardItem colMd="6">
					<SelectView :options="selOption.roleId2" :invalidTxt="invalid2s.roleId2" LabelVal = "使用者角色"
					v-model = "userObj.roleId" ref = "roleId2" :required = "true"/>
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "userId" LabelVal = "使用者代碼(email帳號/手機號碼)" :input = "userObj" :invalids = "invalid2s" :required = "true" :IDRandom = "true" :readOnly=" type === 'EDIT' " maxlength = "50"/>
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "userName" LabelVal = "使用者姓名" :input = "userObj" :invalids = "invalid2s" :required = "true" :IDRandom = "true" maxlength = "16" />
				</CardItem>
				<CardItem colMd="6"/>
				<CardItem colMd="6">
					<SelectView :options="selOption.merGroupType" :invalidTxt="invalid2s.merGroupType" LabelVal = "商店群組型別" v-model = "userObj.merGroupType" ref = "merGroupType" :required = "true"/>
				</CardItem>
				<CardItem colMd="6">
					<div v-show = " userObj['merGroupType'] == 'MULTIPLE' ">
						<SelectView  :options="selOption.mGId" :invalidTxt="invalid2s.mGId" LabelVal = "群組代號"
						v-model = "userObj.mGId" ref = "mGId" :required = "true"/>
					</div>
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "merId" LabelVal = "商家代碼" :input = "userObj" :invalids = "invalid2s" :required = "true"  maxlength = "5" :IDRandom = "true"/>
				</CardItem>
				<CardItem colMd="6">
					<SelectView :options="selOption.twoFactor" :invalidTxt="invalid2s.twoFactor" LabelVal = "是否啟用兩階段驗證" v-model = "userObj.twoFactor" ref = "twoFactor" :required = "true"/>
				</CardItem>
				<CardItem colMd="6">
					<SelectView :options="selOption.loginFlag" :invalidTxt="invalid2s.loginFlag" LabelVal = "管理系統權限" v-model = "userObj.loginFlag" ref = "loginFlag" :required = "true"/>
				</CardItem>
			</Card>
		</div>
		<div class="row mt-2 mb-5">
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
	<Mask ref = "Mask2">
		<div class="p-3">
			<Card title = "重設使用者密碼" :mainCenter="true">
				<CardItem colMd="7">
					<Input ID = "updateUserId" LabelVal = "使用者" v-model = "userId" :invalids = "invalid2s" :readOnly=" true "/>
				</CardItem>
				<CardItem colMd="7">
					<Input ID = "updateUserPswd" LabelVal = "請輸入密碼" v-model = "resetObj.updateUserPswd" :invalids = "invalid3s" :required = "true" maxlength = "16" type="password"/>
				</CardItem>
				<CardItem colMd="7">
					<Input ID = "resetUserId" LabelVal = "待重設使用者" v-model = "resetObj.resetUserId" :invalids = "invalid3s" :readOnly=" true " />
				</CardItem>
			</Card>
		</div>
		<div class="row mt-2 mb-5">
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="hideMask2"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">取消</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="resetPWD"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">儲存</span>
					</button>
				</div>
			</div>
		</div>
	</Mask>
</template>
<script>
import DataTable from '@/components/Table/PageDataTable.vue' ;
import SelectView from "@/components/Select" ;
import Mask from "@/components/Mask.vue" ;
import Input from "@/components/Input" ;
import Base from "@/components/RightViewBase" ;
import Card from "@/components/Card";
import CardItem from "@/components/CardItem";
import { getRoleNameList , getMerGroupTypeList , getOpenOffYNList , getMGIDList } from "@/utils/dataListSA" ;
import { mapGetters, mapState } from 'vuex';
import { checkPasswd , checkAccount , getSHAOldPassword } from "@/utils/user" ;
import { isEmpty , getRandomString } from "@/utils/util";
import { getListVal } from "@/utils/dataList";

let defaultSel = {
	userId 		: "",
	roleId 		: "",
	merId		: "",
	updateUser 	: "" ,
}

let defaultSel2 = {
	roleId 		: "",
	userId  	: "",
	merGroupType: "",
	twoFactor 	: "NO",
	loginFlag 	: "",
	mGId 		: "",	
	merId 		: "",
	userName	: "",
}

let defaultSel3 = {
	resetUserId		: "",
	updateUserPswd	: "",
}

export default {

	data(){
		let _this = this ;

		return {
			title 		: "人員管理維護" ,
			selOption 	: {
				roleId : {
		    		ID 			: "roleId" ,
		    		func 		: getRoleNameList.bind(this, true ) ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
		    	roleId2 : {
		    		ID 			: "roleId2" ,
		    		func 		: getRoleNameList.bind( this , false ) ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
		    	merGroupType : {
		    		ID 			: "merGroupType" ,
		    		func 		: getMerGroupTypeList ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
		    	mGId : {
		    		ID 			: "mGId" ,
		    		func 		: getMGIDList.bind( this , false ) ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
		    	twoFactor : {
		    		ID 			: "twoFactor" ,
		    		func 		: getOpenOffYNList ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown",
		    		defVal 		: "NO" ,
		    	},
		    	loginFlag : {
		    		ID 			: "loginFlag" ,
		    		func 		: [{
		    			text 	: "商店管理櫃檯" ,
						val		: "SC" ,
		    		},
		    		{
		    			text 	: "系統管理櫃檯" ,
						val		: "SA" ,
		    		},
		    		{
		    			text 	: "商店管理櫃檯 & 系統管理櫃檯" ,
						val		: "SC_SA" ,
		    		}] ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	}
		    	
			},
			input 		: Object.assign({},defaultSel),
			userObj 	: Object.assign({},defaultSel2),
			resetObj	: Object.assign({},defaultSel3),
			type 		: "" ,
			invalids 	: {},
			invalid2s 	: {},
			invalid3s 	: {},
			userInvalids: {} ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['userId'],
						value : ['使用者代碼'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['userName'],
						value : ['使用者名稱']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['roleId'],
						value : ['角色代碼'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['merGroupType'],
						value : ['商店群組型別'],
						filter: function (value, key) {
                            if (key == 'merGroupType') {
                            	return getListVal( this.merGroupTypeList , value ) ;
                            }
                            return value;
                        }.bind(this)
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['merId'],
						value : ['商店代號']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['twoFactor'],
						value : ['雙因子','<br>','驗證登入'],
						filter: function (value, key) {
                            if (key == 'twoFactor') {
                                if (value == "YES" ) {
                                    return '啟用';
                                }else {
                                    return '關閉';
                                }
                            }
                            return value;
                        }
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['updateUser'],
						value : ['更新人員']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['userStatus'],
						value : ['狀態'],
						filter: function (value, key) {
							if (key == 'userStatus') {
                                if( value == "NEW") {
                                	return '新戶';
                                } else if( value == "ENABLE") {
                                	return '啟用';
                                } else if( value == "DISABLE") {
                                	return '停用';
                                } else if( value == "NEED_UPDATE_PASSWORD") {
                                	return '須變更密碼';
                                } else {
                                    return '停用需變更密碼';
                                }
                            }
                            return value;
						}
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['loginFlag'],
						value : ['管理系統權限'],
						filter: function (value, key) {
                            if (key == 'loginFlag') {
                                if( value == "SC") {
                                	return '商店管理櫃檯';
                                }else if ( value == "SA" ) {
                                    return '系統管理櫃檯';
                                }else if( value == "SC_SA" ){
                                    return '商店管理櫃檯 & 系統管理櫃檯';
                                }
                            }
                            return value;
                        }
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['merGroupType'],
						value : ["商店群組代號"],
						classR: "d-flex" ,
						styleT: "width:100px;",
						button : [
							{
								class : "sip-status-resend rounded-pill" ,
								key : "merGroupId" ,
								filter : function( payment ){
									return payment.merGroupType === "MULTIPLE"
								},
								func : function ( payment ){
									this.getSameMgID( payment.merGroupId )
								}.bind(this)
							},
						]
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
                        type: 'drop',
                        key: ['status'],
                        func: [
                            {
                                span: `<span class="material-icons pr-2 t-14">
                                            reorder
                                        </span>`,
                                text: '修改',
                                func: function (payment) {
                                	if(confirm('確定修改人員資料??')){
                                    	this.edit( payment );
                                    }
                                }.bind(this),
                            },
                            {
                                span: `<span class="material-icons pr-2 t-14">
                                            reorder
                                        </span>`,
                                text: '重設密碼',
                                func: function (payment) {
                                    if(confirm('確定重設人員密碼資料??')){
                                    	this.resetObj["resetUserId"] = payment.userId ;
                                    	this.showMask2();
                                    }		
                                }.bind(this),
                            },
                            {
                                span: `<span class="material-icons pr-2 t-14">
                                            reorder
                                        </span>`,
                                text: '刪除',
                                func: function (payment) {
                                	if(confirm('確定刪除人員資料??')){
                                    	this.del( payment );
                                	}
                                }.bind(this),
                            },
                            {
                                span: `<span class="material-icons pr-2 t-14">
                                            reorder
                                        </span>`,
                                text: '停用',
                                func: function (payment) {
                                	if(confirm('確定停用人員資料??')){
                                    	this.disable( payment );
                                	}
                                }.bind(this),
                                filter : function( payment ){
                                	return payment.userStatus != "DISABLE" && payment.userStatus != "NEW" ;
                                }
                            },
                            {
                                span: `<span class="material-icons pr-2 t-14">
                                            reorder
                                        </span>`,
                                text: '啟用',
                                func: function (payment) {
                                	if(confirm('確定啟用人員資料??')){
                                    	this.enalbe( payment );
                                    }
                                }.bind(this),
                                filter : function( payment ){
                                	return payment.userStatus != "ENABLE"  && payment.userStatus != "NEW" ;
                                }
                            },
                            /*{
                                span: `<span class="material-icons pr-2 t-14">
                                            reorder
                                        </span>`,
                                text: '變更商店群組代號',
                                func: function (payment) {
                                    let _this = this ;
                                    if(confirm('確定新增群組並修改此人員之群組代號?')){

                                    	_this.goNextPath("SA1401_1",{
                                    		userId 			: payment.userId ,
                                    		merGroupType 	: payment.merGroupType ,
                                    		mGId 			: "999" ,
                                    	})
                                    }
                                }.bind(this),
                                filter : function( payment ){
									return payment.merGroupType != "ALL"
								},
                            },*/
                        ]
                    } 

				] ,
				endButton : [
					{
						value : [""] ,
						icon: "fa-circle-plus" ,
						func : function ( payment ){
							this.add();
						}.bind(this)
					}
				],
				paginate: {
					page: 1,
					size: 10,
					total: 0,
				},
				tableStyle : {
					'font-size' : '9pt'
				}
			},
			dataRow 			: [],
			merGroupTypeList 	: [] ,
		}
	},
	extends : Base ,
	components : {
		SelectView , DataTable , Mask , Input , Card , CardItem
	},
	computed : {
		...mapGetters( 'user' , {
	        userId 	: "getEmail"
      	})
	},
	methods: {
		init(){
			let _this = this ;
			getMerGroupTypeList(function( arr ){
				_this.merGroupTypeList = arr ;
			})
		},
		search(){
			this.doSelPage(1);
		},
		doSelPage( page ){
			let _this = this ;
			let size = this.$refs.table1 ? this.$refs.table1.resize() : this.options.paginate.size ;
			let input = this.input ;
			let params = {
				"page" 		: page - 1 ,
				"size" 		: size , 
				userId 		: input.userId ,
				roleId 		: input.roleId ,
				merId  		: input.merId ,
				updateUser 	: input.updateUser ,
			};

			_this.showLoading(true);
			this.ApiUtilSA.get("get-users" , params )
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
		getSameMgID( mgID ){
			let _this = this ;
			_this.showLoading(true);
			this.ApiUtilSA.get("get-users-by-mgid/" + mgID )
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					let msg = "相同群組的使用者" ;
					data.forEach(function( userId ){
						msg += "<br>" + userId ;
					});
					_this.showModel({
		                title : _this.title ,
		                message : msg,
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		add(){
			this.type = "ADD";
			this.userObj["roleId"] = this.$refs.roleId2.getDefVal();
			this.userObj["mGId"] = this.$refs.mGId.getDefVal();
			this.userObj["merGroupType"] = this.$refs.merGroupType.getDefVal();
			this.userObj["twoFactor"] = this.$refs.twoFactor.getDefVal();
			this.userObj["loginFlag"] = this.$refs.loginFlag.getDefVal();
			this.initEdit(this.userObj);
			this.showMask();
		},
		edit( payment ){
			let userId = payment.userId ;

			let userObj = {
				roleId 		: payment.roleId ,
				userId  	: payment.userId ,
				merGroupType: payment.merGroupType ,
				twoFactor 	: payment.twoFactor ,
				loginFlag 	: payment.loginFlag ,
				mGId 		: payment.merGroupId ? payment.merGroupId : this.$refs.mGId.getDefVal(),
				merId 		: payment.merId ,
				userName	: payment.userName
			}

			userObj.previousData = JSON.stringify(userObj);
			
			this.initEdit(userObj);
			this.type = "EDIT";
			this.userObj = userObj ;
			this.showMask();
		},
		save ( ) {
			let userObj = this.userObj ;
			let _this = this ;
			let invalid2s = {} ;
			let type = this.type ; 
			if( userObj.userName == "" ) {
				invalid2s.userName = "請輸入使用者姓名" ;
			}

			if( userObj.merId == "" ) {
				invalid2s.merId = "請輸入商家代號" ;
			}

			let param = {
				userId 		: userObj.userId ,
  				roleId 		: userObj.roleId ,
  				userName 	: userObj.userName ,
  				merGroupType: userObj.merGroupType ,
  				merId 		: userObj.merId ,
				twoFactor	: userObj.twoFactor ,
  				loginFlag 	: userObj.loginFlag ,
			}

			if( type === "ADD" ) {
				
				let msg = checkAccount(userObj.userId) ;
				
				if( msg != "" ) {
					invalid2s.userId = msg ;
				}
			} else {
				param["previousData"] = userObj.previousData ;
			}

			if( userObj.merGroupType === "MULTIPLE" ) {
				param.merGroupId = userObj.mGId ;
			}

			this.invalid2s = invalid2s ;

			if( isEmpty(invalid2s) ) {
				this.showLoading(true);

				if( type === "ADD" ) {
					this.ApiUtilSA.post("create-user" , param )
					.then((res) => {
						if( res.success ) {
							let data = res.data ;
							let msg = "新增" ;
							let notifySuccess = data.notifySuccess ;
							let success = data.success ;
							
							if( success ) {
								msg += "成功";
							} else {
								msg += "失敗";
							}
							
							msg += ",寄送信件" ;
							
							if( notifySuccess ) {
								msg += "成功";
							} else {
								msg += "失敗";
							}
							
							this.showModel({
				                title : this.title ,
				                message : msg ,
				                endFunc : function(){
				                	_this.hideMask();
				                	//_this.input = Object.assign({},defaultSel,{userId:userObj.userId}) ;
				                	//_this.search();
				                }
				            });
						}
					})
					.finally(function () {
			        	_this.showLoading();
					});
				} else {
					this.ApiUtilSA.post("update-user" , param )
					.then((res) => {
						if( res.success && res.data.success ) {
							this.showModel({
				                title : this.title ,
				                message : "修改成功",
				                endFunc : function(){
				                	_this.hideMask();
				                	_this.search();
				                }
				            });
						}
					})
					.finally(function () {
			        	_this.showLoading();
					});
				}
			}		
		},
		initEdit( userObj ){
			this.$refs.merGroupType.setDefVal(userObj.merGroupType);
			this.$refs.loginFlag.setDefVal(userObj.loginFlag);
			this.$refs.twoFactor.setDefVal(userObj.twoFactor);
			if( userObj.mGId != "" ) {
				this.$refs.mGId.setDefVal(userObj.mGId);	
			}
			this.$refs.roleId2.setDefVal(userObj.roleId);
		},
    	clear(){
    		this.input = Object.assign({},defaultSel) ;
    		this.$refs.roleId.clear();
    		this.clearTable();
    	},
    	showMask( type ){
    		this.$refs.Mask.show();
    	},
    	hideMask(){
    		this.userObj = Object.assign({},defaultSel2) ;
    		this.$refs.Mask.hide();
    	},
    	showMask2( type ){
    		this.$refs.Mask2.show();
    	},
    	hideMask2(){
    		this.resetObj = Object.assign({},defaultSel3) ;
    		this.$refs.Mask2.hide();
    	},
    	resetPWD(){
    		let resetObj = this.resetObj ;
    		let resetUserId 	= resetObj.resetUserId ;
    		let updateUserPswd 	= resetObj.updateUserPswd ;
    		let invalid3s = {} ;
    		
    		if ( updateUserPswd == "" ) {
    			invalid3s.updateUserPswd = "請輸入密碼" ;
    		}

    		this.invalid3s = invalid3s ;
    		if( isEmpty(invalid3s) ) {
    			let param = {
  					updateUserPswd 	: getSHAOldPassword ( updateUserPswd ),
  					resetUserId   	: resetUserId
    			}
    			let _this = this ;

    			_this.showLoading(true);
    			
    			this.ApiUtilSA.put("change-user-password" , param )
    			.then((res) => {
					if( res.success ) {
						let msg = "重設密碼" ;
						let notifySuccess = res.data.notifySuccess ;
						let updateSuccess = res.data.updateSuccess ;
						if( updateSuccess ) {
							msg += "成功";
						} else {
							msg += "失敗";
						}
						msg += ",寄送信件" ;
						if( notifySuccess ) {
							msg += "成功";
						} else {
							msg += "失敗";
						}
						this.showModel({
			                title : this.title ,
			                message : msg,
			                endFunc : function(){
			                	_this.hideMask2();
			                }
			            });
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
    		}
    	},
    	disable( payment ) {
    		let param = {
    			previousData 	: payment.previousData  ,
    			userId 			: payment.userId 
    		}
    		let _this = this ;

    		this.showLoading(true);
    		
    		this.ApiUtilSA.put("disable-user" , param )
			.then((res) => {
				if( res.success && res.data.success ) {
					this.showModel({
		                title : this.title ,
		                message : "停用成功",
		                endFunc : function(){
		                	_this.search();
		                }
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
    	},
    	enalbe( payment ) {
    		let param = {
    			previousData 	: payment.previousData  ,
    			userId 			: payment.userId 
    		}
    		let _this = this ;

    		this.showLoading(true);
    		
    		this.ApiUtilSA.put("enable-user" , param )
			.then((res) => {
				if( res.success && res.data.success ) {
					this.showModel({
		                title : this.title ,
		                message : "啟用成功",
		                endFunc : function(){
		                	_this.search();
		                }
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
    	},
    	del( payment ) {
    		let param = {
    			previousData 	: payment.previousData  ,
    		}
    		let _this = this ;

    		this.showLoading(true);
    		this.ApiUtilSA.delete("delete-user/"+ payment.userId  , param )
			.then((res) => {
				if( res.success && res.data.success ) {
					this.showModel({
		                title : this.title ,
		                message : "刪除成功",
		                endFunc : function(){
		                	_this.clear();
		                }
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
    	}
	}
}	
</script>