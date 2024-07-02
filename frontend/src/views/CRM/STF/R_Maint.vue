<template >
	
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<div class="row p-3 justify-content-center">
		<div class=" order-md-1 order-0 col-md-8">
			<SelectView :options="selOption.STFName" :invalidTxt="invalids.STFName" LabelVal = "費率名稱"
			@updateVal="updateVal" ref = "STFName"/>
		</div>
		<div class="p-2  order-md-1 order-0 col-md-1 align-self-end" >
			<div class="col-3 p-0" @click.prevent= "AddName" role="button">
				<i class="fa img-fluid onactived fa-circle-plus text-danger" style="fontSize:24px" />
			</div>
		</div>
	</div>

	<div class="row m-0 p-3">		
		<div :class="getClass('t1Tilte')">名稱</div>
		<div :class="getClass('tiColumn')" class="selectText">{{data.settingName}}</div>
		<div :class="getClass('t1Tilte')">說明</div>
		<div :class="getClass('tiColumn')" class="selectText" style=" word-break: break-all;">{{data.notes}}</div>
	</div>

	<div class="p-3">
		<div class="border-0 pt-3">
			<DataTable ref="table1"
			:dataRow="dataRow"
			:options ="tableOption"/>
		</div>
	</div>

	<Mask ref = "Mask">
		<Edit :type="type" :input="input" ref="Edit" :STFType="getSTFType()"/>
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
import Edit from "@/views/CRM/STF/R_Edit"
import Base from "@/components/Base" ;
import Mask from "@/components/Mask"
import { getSTFAll } from "@/utils/dataListCrm";
import { getListVal } from "@/utils/dataList";
import SelectView from "@/components/Select" ;
import DataTable from '@/components/Table/DataTable' ;
import { isEmptyObject } from "@/utils/util";

export default {
	data(){
		return {
			title 	: "手續費率設定",
			type 	: "ADD_NAME" ,
			invalids : {},
			input : this.getDefaultInp(),
			data : {
			},
			configIndex : null ,
			selOption	: {
				STFName	: {
					ID			: "STFName" ,
					func		: getSTFAll.bind(this),
					selType		: "dropdown" ,
					filter 		: true ,
				},
			},
			dataRow : [] ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['feeType'],
						value : ['手續費類型'],
						filter 	: function ( value , key ){
                            return getListVal( this.getSTFType() , value) ;
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
									this.EditConfig( payment , index  ) ;
								}.bind(this)
							},
							{
								class : "" ,
								key : "" ,
								icon: "fa-trash-can" ,
								func : function ( payment , index ){
									this.doDel( payment , index );
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
							this.AddConfig();
						}.bind(this)
					}
				]	
			},
		}
		 
	},
    components : {
    	Edit , Mask , SelectView , DataTable
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
    	updateVal( val , ID ) {
    		let _this = this ;
	    	this.ApiUtil.get("store-transaction-fee/rate/id/"+val)
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					_this.data = res.data ;
					if( res.data.config ) {
						_this.dataRow = res.data.config ;	
					} else {
						_this.dataRow = [];
					}
					
				} else {
					_this.data = {};
					_this.input = _this.getDefaultInp();
					_this.dataRow = [] ;
				}
			})
	    },
    	hide(){
    		this.input = this.getDefaultInp();
    		this.$refs.Mask.hide();
    	},
    	save(){
    		let type = this.type ;
    		let input = this.input ;
    		let data = this.data ;
    		this.$refs.Mask.hide();
    		let _this = this ;

    		if( type == "ADD_NAME") {
    			this.loading = true ;
    			input.config = [input.config];
    			this.ApiUtil.post("/store-transaction-fee/rate",input)
				.then((res) => {
					_this.loading = false;
					if( res.success ) {
						_this.showModel({
			                title 	: this.title ,
			                message : "新增成功",
			                endFunc : function(){
			                	getSTFAll.call(_this,_this.$refs.STFName.setArr)
			                }
			            });
					}
				})	
    		} else if( this.checkData(data) ) {
    			if( type == "ADD_CONFIG" ) {
    				this.loading = true ;
    				data.config.push(input.config);
	    			input.config = data.config ;
	    			this.ApiUtil.put("/store-transaction-fee/rate/"+data.id,input)
					.then((res) => {
						_this.loading = false;
						if( res.success ) {
							_this.showModel({
				                title 	: this.title ,
				                message : "新增成功",
				                endFunc : function(){
				                	_this.updateVal( data.id ) ;
				                }
				            });
						}
					})	
	    		} else if( type == "EDIT_CONFIG" ) {
	    			this.loading = true ;
	    			data.config[this.configIndex] = input.config ;
	    			input.config = data.config ;
	    			this.ApiUtil.put("/store-transaction-fee/rate/"+data.id,input)
					.then((res) => {
						_this.loading = false;
						if( res.success ) {
							_this.showModel({
				                title 	: this.title ,
				                message : "修改成功",
				                endFunc : function(){
				                	_this.updateVal( data.id ) ;
				                }
				            });
						}
					})
	    		} else {
	    			input = this.input = this.getDefaultInp();
	    			this.showModel({
	                    title 	: this.title ,
	                    message : '資料有誤',
	                });
	    		}
    		} else {
    			input = this.input = this.getDefaultInp();
    			this.showModel({
                    title 	: this.title ,
                    message : '資料有誤',
                });
    		}
    		
    		
    	},
    	doDel( payment , index ){
    		let _this = this ;
    		this.loading = true ;
    		let data = this.data
    		data.config.splice(index,1);
    		this.ApiUtil.put("/store-transaction-fee/rate/"+data.id,data)
			.then((res) => {
				if( res.success ) {
					_this.showModel({
		                title 	: this.title ,
		                message : "刪除成功",
		                endFunc : function(){
		                	_this.updateVal( data.id ) ;
		                }
		            });
				}
				//console.log(res);
				this.loading = false ;
			});
    	},
    	checkData( data ){
    		return typeof data === "object" && !isEmptyObject( data )
    	},
    	AddName(){
    		this.input = this.getDefaultInp();
    		this.type = "ADD_NAME";
    		this.$refs.Mask.show();
    		this.configIndex = null ;
    	},
    	AddConfig(){
    		this.input = {
    			notes 		: this.data.notes ,
    			settingName : this.data.settingName,
    			config 		: this.getDefaulConfig()
    		}
    		this.configIndex = null ;
    		this.type = "ADD_CONFIG";
    		this.$refs.Mask.show();
    	},
    	EditConfig( payment , index ){
    		this.input = {
    			notes 		: this.data.notes ,
    			settingName : this.data.settingName,
    			config 		: Object.assign({},payment) ,
    		}
    		this.configIndex = index ;
    		this.type = "EDIT_CONFIG";
    		this.$refs.Mask.show();
    	},
    	getDefaultInp(){
    		let def = this.getDefaulOther() ;
    		def["config"] = this.getDefaulConfig() ;
    		return def ;
    	},
    	getDefaulConfig(){
    		return {
				"feeRate"		: 0,
				"feeType"		: "",
				"fixedFee"		: 0,
				"maxiNumber"	: 0,
				"miniNumber"	: 0
    		}
    	},
    	getDefaulOther(){
    		return {
    			"notes"			: "",
				"settingName"	: ""
    		}
    	},
    	getSTFType (){
			return [
				{
					text 	: "金額(AMOUNT)" ,
					val		: "AMOUNT" ,
				},
				{
					text 	: "筆數(COUNT)" ,
					val		: "COUNT" ,
				}
			]
		},
    },
}
</script>