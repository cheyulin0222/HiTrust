<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<template v-if="showType === 'SEARCH' ">
		<div class="row p-3" ref = 'search' >
			<CardItem colMd="6">
				<Input ID = "virtualId" LabelVal = "虛擬代號" :input = "input"/>
			</CardItem>
			<CardItem colMd="6">
				<SelectView :options="selOption.serviceType" LabelVal = "服務類別"
				v-model="input.serviceType" ref = "serviceType" :required="required"/>
			</CardItem>
		</div>
		<div class="row p-3 justify-content-md-center">
			<div class="col-md-6 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="search"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">查詢資料</span>
					</button>
				</div>
			</div>
		</div>
		<DataTable ref="table1"
		:dataRow="dataRow"
		:options ="tableOption"
		@doSelPage = "doSelPage" >
		</DataTable>
	</template>
	<template v-else>
		<div class="p-3">
			<Card title = "虛擬代號資訊">
			
				<CardItem colMd="6">
					<Input ID = "virtualId" LabelVal = "虛擬代號" :input = "selObj" :readOnly = "showType === 'EDIT' " :invalids = "invalids" :required = "true"/>
				</CardItem>
				<CardItem colMd="6">
					<SelectView :options="selOption.serviceType" LabelVal = "服務類別"
					v-model = "selObj.serviceType" ref = "serviceType" :readOnly = "readOnly"/>
				</CardItem>
				<CardItem colMd="6">
					<span class="t-12" style="margin-bottom: 0.5rem; margin-right: 0.5rem; display: inline-block;">
						支援分期
					</span>
					<i class="fa img-fluid onactived pr-md-3 fa-circle-plus"  @click = "addIns"/>
					<i class="fa img-fluid onactived pr-md-3 fa-minus-circle" @click = "minusIns"
						:class = "{'is-invalid':invalids.insList}"/>
					<div :key=" 'newInsElement_' + index" v-for =" ( item , index ) in selObj.insList" :class="{'mt-1': index > 0 } ">
						期別
						<input type = "number" size = "4" v-model = "item.installment"
							class = "text-left bg-white border-0 dropdown-toggle" 
							:class = "{'form-control is-invalid':invalids['installment_' + index]}" 
							style="padding:.375rem .75rem" 
							:style = "{'width:5rem;': !invalids['installment_' + index]}" 
							@change = "changeInsPersent(item)">
						<div class="invalid-feedback">{{ invalids['installment_' + index] }}</div>
						期  前半期數
						<input type="number" size="2" min="1" max="99" v-model = "item.first_period" class = "text-left bg-white border-0 dropdown-toggle" @change = "changeInsPersent(item)" style="width:5rem;padding:.375rem .75rem">
						 後半期數
						<input type="number" size="2" min="1" max="99" v-bind:value= "item.latter_period" class = "text-left bg-white border-0 dropdown-toggle disable " disabled style="width:5rem;padding:.375rem .75rem">
					</div>
					<div class="invalid-feedback">{{ invalids.insList }}</div>
				</CardItem>
				<CardItem colMd="6">
					<span class="t-12" style="margin-bottom: 0.5rem; margin-right: 0.5rem;display: inline-block;">
						商代分期比例對應設定
					</span>
					<i class="fa img-fluid onactived pr-md-3 fa-circle-plus"  @click = "addType"/>
					<i class="fa img-fluid onactived pr-md-3 fa-minus-circle" @click = "minusType"
					:class = "{'is-invalid':invalids.insTypeList}"/>
					<div :key=" 'newInsType_' + index" v-for =" ( item , index ) in selObj.insTypeList" :class="{'mt-1': index > 0}">
						商店代號
						<input type = "text" v-model = "item.store_id" 
							class = "text-left bg-white border-0 dropdown-toggle" 
							:class = "{'form-control is-invalid':invalids['store_id_' + index]}" 
							:style = "{'width:5rem;': !invalids['store_id_' + index]}"
							style="padding:.375rem .75rem">
						<div class="invalid-feedback">{{ invalids['store_id_' + index] }}</div>
						分期類型 前半
						<input type="number" size="2" min="1" max="99" v-model = "item.first_half" class = "text-left bg-white border-0 dropdown-toggle" @change = "changeInsType(item)" style="width:5rem;padding:.375rem .75rem">
						% 後半
						<input type="number" size="2" min="1" max="99" v-bind:value= "item.latter_half" class = "text-left bg-white border-0 dropdown-toggle disable " disabled style="width:5rem;padding:.375rem .75rem">
						%
					</div>
					<div class="invalid-feedback">{{ invalids.insTypeList }}</div>
				</CardItem>
			</Card>
		</div>
		<div class="row mt-2 mb-5">
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="save"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">{{showType === 'EDIT' ?'修改':'新增'}}資料</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="goBack"
					class="btn btn-secondary btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">返回查詢</span>
					</button>
				</div>
			</div>
		</div>
	</template>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import SelectView from "@/components/Select" ;
import DataTable from '@/components/Table/PageDataTable.vue' ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import { resetDefaultVal , isEmpty } from "@/utils/util" ;

export default {
	data(){
		let Obj = {
			title 		: "設置虛擬代號(永豐UP分期)" ,
			selOption 	: {
				serviceType : {
		    		ID 			: "serviceType" ,
		    		func 		: [{
		    			text 	: "永豐UP分期" ,
						val		: "UP_INSTALLMENT" ,
		    		}],
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
			},
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['virtualId'],
						value : ['虛擬代號'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['serviceType'],
						value : ['服務類別'] , 
						filter: function (value, key) {
                            if ( value == 'UP_INSTALLMENT') {
                            	return "永豐UP分期" ;
                            } 
                            return value;
                        }.bind(this)
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['merGroupType'],
						value : [],
						classR: "d-flex" ,
						styleT: "width:120px;",
						button : [
							{
								class : "sip-status-resend rounded-pill" ,
								key : "修改" ,
								func : function ( payment ){
									this.edit(payment);
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
							this.add();
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
			readOnly : true ,
			required : true ,
			invalids 	: {} ,
			...this.getDefault(),
		}
		
		return Obj ;
	},
	extends : Base ,
	computed: {

	},
	watch: {     
       
    },
	components : {
		Input , SelectView , DataTable , Card , CardItem
	},
	methods: {
		search(){
			this.doSelPage( 1 ) ;
		},
		doSelPage( page ){
			let _this = this ;
			let size = this.$refs.table1 ? this.$refs.table1.resize() : this.options.paginate.size ;
			let params = {
				page 		: page - 1 ,
				size 		: size ,
				virtualId 	: this.input.virtualId 
			};			
			
			_this.showLoading(true);
			this.ApiUtilSA.get("get-all-virtualId/" + this.input.serviceType , params )
			.then((res) => {
				if( res.success ) {
					let content = res.data.content ;
					_this.dataRow = content ;
					_this.tableOption.paginate.page = page ;
	                _this.tableOption.paginate.total = res.data.totalElements ;
	                _this.tableOption.paginate.size = size ;
				} else {
					this.clearTable();
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		edit( payment ){
			let _this = this ;
			
			_this.showLoading(true);
			
			this.ApiUtilSA.get("/get-virtualId-stores-data/" + payment.virtualId + "/" + payment.serviceType )
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					let virtualData = JSON.parse( data.virtualData)
					let selObj = {
						virtualId 	: data.virtualId ,
						serviceType : data.serviceType ,
						insList 	: virtualData.support_installment ,
						insTypeList : virtualData.support_store_id ,
					}
					this.previousData = JSON.stringify( selObj );
					this.showType = "EDIT" ;
					this.selObj = selObj ;
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		add(){
			this.showType = "ADD" ;
		},
		goBack(){
			resetDefaultVal( this , this.getDefault());
			this.clearTable();
		},
		getDefault(){
			return {
				input : {
					serviceType : "UP_INSTALLMENT" ,
					virtualId 	: "" ,
				} ,
				selObj : {
					virtualId 	: "" ,
					serviceType : "UP_INSTALLMENT" ,
					insList 	: [] ,
					insTypeList : [] ,
				},
				showType 	: 'SEARCH' ,
				previousData: "" ,
			}
		},
    	save (){
    		let showType 	= this.showType ;
    		let selObj 		= this.selObj ;
    		let virtualId  	= selObj.virtualId ;
			let serviceType = selObj.serviceType ;
			let insList 	= selObj.insList ;
			let insTypeList = selObj.insTypeList ;
			let invalids 	= {} ;

			if( virtualId == "" ) {
				invalids.virtualId = "請輸入虛擬代號" ;
			}

    		if( insList.length < 1 ) {
				invalids.insList = "請新增支援分期" ;
			} else {
				insList.forEach(function( item , index  ){
					let installment = item.installment ;
					if( installment =="" ){
						invalids['installment_'+index] = "請輸入期數" ;
					}
				});
			}

			if( insTypeList.length < 1 ) {
				invalids.insTypeList = "請新增商代分期比例對應設定" ;
			} else {
				let merIds = [] ;
				insTypeList.forEach(function( item , index  ){
					let storeId = item.store_id ;
					if( storeId =="" ){
						invalids['store_id_'+index] = "請輸入商店代號" ;
					} else if( merIds.indexOf( storeId ) > -1 ) {
						invalids['store_id_'+index] = "方案商代不可重複" ;
					} else {
						merIds.push(storeId);
					}
				});
			}

			this.invalids = invalids ; 

			if( isEmpty( invalids ) ) {
				let param = {
				  	virtualId 		: virtualId ,
				  	serviceType		: serviceType ,
				  	virtualSetting	: {
					    support_installment : insList ,
					    support_store_id	: insTypeList
				  	}
				}

				let _this = this ;

				if( showType === "ADD" )  {
	    			this.ApiUtilSA.post( "create-virtualId-stores-data" , param )
					.then((res) => {
						if( res.success && res.data.success ) {
							this.showModel({
				                title 	: this.title ,
				                message : "新增成功",
				                endFunc : function(){
				                	_this.edit({
				                		virtualId 	: res.data.virtualId , 
				                		serviceType : res.data.serviceType
				                	})
				                }
				            });
						}
					})
					.finally(function () {
			        	_this.showLoading();
					});
	    		} else {
	    			param["previousData"] = _this.previousData ;
	    			this.ApiUtilSA.post( "update-virtualId-stores-data" , param )
					.then((res) => {
						if( res.success && res.data.success ) {
							this.showModel({
				                title 	: this.title ,
	                    		message : "修改成功"
				            });
						}
					})
					.finally(function () {
			        	_this.showLoading();
					});
	    		}
			}	
    	},
    	addIns(){
    		this.selObj.insList.push({
    			installment 	: 3 ,
    			first_period 	: 1 ,
    			latter_period 	: 2
    		})
    	},
    	minusIns(){
    		this.selObj.insList.pop();
    	},
    	addType(){
			this.selObj.insTypeList.push({
				store_id 	: "" ,
				first_half 	: 1 ,
				latter_half : 99 ,
			})
    	},
    	minusType(){
    		this.selObj.insTypeList.pop();
    	},
    	changeInsPersent( insObj ){
    		if( insObj.installment < insObj.first_period + 1 ) {
    			this.showModel({
                    type : "error" , 
                    title : this.title ,
                    message : "支援分期-前半期數不可超過總期數"
                });
                insObj.first_period = insObj.installment - 1 ;
    		}
    		insObj.latter_period = insObj.installment - insObj.first_period
    	},
    	changeInsType( insTypeObj ){
    		if( insTypeObj.first_half > 99 ) {
    			this.showModel({
                    type : "error" , 
                    title : this.title ,
                    message : "商代分期比例對應-前半趴數不可大於99%"
                });
                insTypeObj.first_half = 99 ;
    		}
    		insTypeObj.latter_half = 100 - insTypeObj.first_half
    	}
	},
	mounted () {
		
	},
	beforeUpdate () {

	},
	beforeMount(){
		
	},
	//銷毀前
    beforeUnmount(){
    	
    },
}	
</script>