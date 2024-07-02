<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<div class="row p-3 justify-content-md-center" ref = 'search' >
		<CardItem colMd="7">
			<SelectView :options="selOption.type" :invalidTxt="invalids.type" LabelVal = "頁面樣式" v-model="sel.type" ref = "selType" />
		</CardItem>
	</div>
	<div class="row p-3 justify-content-md-center">
		<div class="col-md-6 order-1">
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
			<Card title = "上傳付款頁樣式" :mainCenter="true">
				<CardItem colMd="7">
						<SelectView :options="selOption2.type" :invalidTxt="invalids.type2" LabelVal = "頁面樣式" v-model="upload.type" ref = "type2" />
				</CardItem>
				<CardItem colMd="7">
					<Input ID = "FILE" type = "file" LabelVal = "選擇您的付款頁檔案" :invalids = "invalids" ref = "files"/>
				</CardItem>
			</Card>
		</div>
		<div class="row mt-2 mb-5">
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="hideMask(false)"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">取消</span>
					</button>
				</div>
			</div>
			<div class="col-md-4  p-2">
				<div class="m-0">
					<button type="button" @click.prevent="updateFile"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">上傳</span>
					</button>
				</div>
			</div>
		</div>
	</Mask>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import DataTable from '@/components/Table/PageDataTable.vue' ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import SelectView from "@/components/Select" ;
import Mask from "@/components/Mask.vue" ;
import { resetDefaultVal , isEmpty } from "@/utils/util" ;
import { getOpenOffYNList } from "@/utils/dataListSA" ;
import { getListVal } from "@/utils/dataList" ;

let typeList = [{
	text 	: "付款頁" ,
	val		: "PAYPAGE" ,
},
{
	text 	: "方便付" ,
	val		: "EZP" ,
}] ;

export default {
	data(){
		return {
			title 	: "頁面樣式維護" ,
			sel 	: {
				type : "" ,
			},
			upload	: {
				type 	: "" ,
			},
			selOption : {
				type : {
					ID 			: "type" ,
		    		func 		: typeList ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
				}
			},
			selOption2 : {
				type : {
					ID 			: "type2" ,
		    		func 		: typeList ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
				}
			},
			invalids : {} ,
			tableOption : {
				colum : [],
				paginate: {
					page: 1,
					size: 10,
					total: 0,
				},
				endButton : [
					{
						value : [""] ,
						icon: "fa-circle-plus" ,
						func : function ( payment ){
							this.showMask();
						}.bind(this)
					}
				],
			},
			dataRow : [],
		} ;
	},
	extends : Base ,
	computed: {

	},
	watch: {     
       "sel.type" : function( new_value, old_value  ){
       		let colum = [] ;
       		if( new_value === "PAYPAGE" ) {
				colum = [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['country'],
						value : ['國家'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['templetId'],
						value : ['樣式'],
					},
				]
       		} else {
       			colum = [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['templetId'],
						value : ['樣式'],
					}
				]
       		}
       		colum.push(
				{	
					classT : "border border-info rounded-0 p-3 text-center alert-primary ",
					type : 'text',
					key : ['updateTime'],
					value : ['異動時間']
				},
				{	
					classT : "border border-info rounded-0 p-3 text-center alert-primary ",
					type : 'text',
					key : ['status'],
					value : ['狀態'],
					filter : function( value , key , payment ){
						return getListVal( this.openOffList , value ) ;
					}.bind(this)
				},
				{
					classT : "border border-info rounded-0 p-3 text-center alert-primary ",
                    type: 'drop',
                    key: ['status'],
                    styleT : "width:50px;",
                    func: [
                        {
                            span: `<span class="material-icons pr-2 t-14">
                                        reorder
                                    </span>`,
                            text: '啟用',
                            filter : function( payment ){
								return payment.status === "NO" ;
							},
                            func: function (payment) {
                                this.open( payment );
                            }.bind(this),
                        },
                        {
                            span: `<span class="material-icons pr-2 t-14">
                                        reorder
                                    </span>`,
                            filter : function( payment ){
								return payment.status === "YES" ;
							},
                            text: '停用',
                            func: function (payment) {
                                this.close( payment );
                            }.bind(this),
                        }
                    ]
                }
			)
       		this.tableOption.colum = colum ;
       		this.clearTable();
       }
    },
	components : {
		Input , SelectView , Card , CardItem , Mask , DataTable
	},
	methods: {
		updateFile(){
			let _this = this ;
			let invalids = {} ;
			let file = this.$refs.files.getFile();
			if( file.length == 0 ) {
				invalids.FILE = "請選擇匯入的檔案";
			}

			this.invalids = invalids ;

			if( isEmpty(invalids) ) {
				let param = {
					type 	: this.upload.type ,
					fileName: file[0].name 
				}

				let _this = this ;

				_this.showLoading(true);
				
				this.ApiUtilSA.post("create-templet-page" , param )
				.then((res) => {
					if( res.success && res.data.success ) {
						this.showModel({
		                    title : this.title ,
		                    message : "新增成功" ,
		                    endFunc : function(){
		                    	this.hideMask(true);

		                    }.bind(this)
		                });
					}
				})
				.finally(function () {
					
		        	_this.showLoading();
				});
			}
			
		},
		showMask(){
    		this.$refs.Mask.show();
    	},
    	hideMask( reload ){
    		this.$refs.files.clearFile();
		    this.$refs.type2.clear();
    		this.$refs.Mask.hide();
    		if( reload ) {
    			let pate = this.tableOption.paginate.page ;
    			this.search( pate );
    		}
    		
    	},
    	search(){
    		this.doSelPage(1);
    	},
    	doSelPage( page ){
    		let _this = this ;
    		let size = this.$refs.table1 ? this.$refs.table1.resize() : this.options.paginate.size ;
			let params = {
				page 	: page - 1 ,
				size	: size ,
			};

			this.showLoading(true);
			this.ApiUtilSA.get( "get-templet-page/" + this.sel.type , params )
			.then( function( res ) {
				if( res.success ) {
					let data = res.data ;
					let content = data.content ;
					content.forEach(function( item ){
						item.previousData = JSON.stringify({
							id 		: {
								country 	: item.country ,
    							templetId	: item.templetId ,
    							type 		: item.type 
    						},
    						status	: item.status 
						})
					})

					_this.dataRow = data.content ;
					_this.tableOption.paginate.page = page ;
 	                _this.tableOption.paginate.total = res.data.totalElements ;
 	                _this.tableOption.paginate.size = size ;
				} else {
					_this.clearTable();
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
    	},
    	open( item ){
    		let _this = this ;
    		let param = {
    			previousData 	: item.previousData ,
    			id 				: {
    				country 	: item.country ,
					templetId	: item.templetId ,
					type 		: item.type
    			},
    			status	: "YES" 
    		}

    		_this.showLoading(true);
    		
    		this.ApiUtilSA.post( "update-templet-page" , param )
    		.then((res) => {
				if( res.success ) {
					this.showModel({
		                title : this.title ,
		                message :"啟用成功",
		                endFunc : function(){
		                	let page = _this.tableOption.paginate.page ;
		                	_this.doSelPage( page ) ;
		                }
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
        	
    	},
    	close( item ){
    		let _this = this ;
    		let param = {
    			previousData 	: item.previousData ,
    			id 				: {
    				country 	: item.country ,
					templetId	: item.templetId ,
					type 		: item.type
    			},
    			status	: "NO" 
    		}

    		_this.showLoading(true);
    		
    		this.ApiUtilSA.post( "update-templet-page" , param )
    		.then((res) => {
				if( res.success ) {
					this.showModel({
		                title : this.title ,
		                message :"停用成功",
		                endFunc : function(){
		                	let page = _this.tableOption.paginate.page ;
		                	_this.doSelPage( page ) ;
		                }
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
    	}
	},
	mounted () {
		let _this = this ;
		getOpenOffYNList(function(list){
			_this.openOffList = list ;
		})
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