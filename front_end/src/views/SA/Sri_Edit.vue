<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<div class="row p-3 justify-content-md-center" ref = 'search' >
		<CardItem colMd="7">
			<Input ID = "uri" LabelVal = "網址(模糊查詢)" v-model = "sel.uri" :invalids = "invalids" />
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
	<DataTable ref="table1" :dataRow="dataRow" :options ="tableOption" @doSelPage="doSelPage"/>
	<Mask ref = "Mask">
		<div class="p-3">
			<Card :title = "type === 'ADD' ? '新增' : '修改' + '網頁完整性'" :mainCenter="true">
				<CardItem colMd="7">
					<Input ID = "uri2" LabelVal = "網址" v-model = "input.uri"  :invalids = "invalid2s" :readOnly = "readOnly" :required = "true"/>
				</CardItem>
				<CardItem colMd="7">
					<Input ID = "sri" LabelVal = "網頁完整性" v-model = "input.sri" :invalids = "invalid2s" />
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
					<button type="button" @click.prevent="save"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">儲存</span>
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
import { resetDefaultVal , isEmpty , genNonDuplicateID } from "@/utils/util" ;

export default {
	data(){
		return {
			title 	: "網頁完整性維護" ,
			sel 	: {
				uri : "" ,
			},
			invalids: {} ,
			invalid2s : {} ,
			tableOption : {
				colum : [{
					styleT : "width:70px" ,
					classT : "border border-info rounded-0 p-2 text-center alert-primary ",
                	type : 'index',
                	value : ['項次'] , 
            	},
            	{
					classT : "border border-info rounded-0 p-3 text-center alert-primary ",
					type : 'text',
					key : ['uri'],
					value : ['網址'],
				},
				{
					classT : "border border-info rounded-0 p-3 text-center alert-primary ",
					type : 'text',
					key : ['sri'],
					value : ['完整性值'],
				},
				{
					classT : "border border-info rounded-0 p-3 text-center alert-primary ",
					type : 'text',
					key : ['updateTime'],
					value : ['更新時間']
				},
				{
					classT : "border border-info rounded-0 p-3 text-center alert-primary ",
					type : 'text',
					key : ['createTime'],
					value : ['建立時間']
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
                            	if(confirm('確定修改子資源完整性資料??')){
                                	this.edit( payment );
                                }
                            }.bind(this),
                        },
                        {
                            span: `<span class="material-icons pr-2 t-14">
                                        reorder
                                    </span>`,
                            text: '刪除',
                            func: function (payment) {
                                if(confirm('確定刪除子資源完整性資料??')){
                                	this.del( payment ) ;
                                }		
                            }.bind(this),
                        }
                    ],
                }],
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
							this.type = "ADD" ;
							this.showMask();
						}.bind(this)
					}
				],
			},
			dataRow : [],
			...this.getDefault() ,
		} ;
	},
	extends : Base ,
	computed: {
		readOnly () { 
			return this.type != "ADD" 
		}
	},
	watch: {     

    },
	components : {
		Input , SelectView , Card , CardItem , Mask , DataTable
	},
	methods: {
		edit( item ){
			this.type = "EDIT" ;
			this.input = {
				uri : item.uri ,
				sri : item.sri
			}
			this.previousData = item.previousData ;
			this.showMask();
		},
		showMask(){
    		this.$refs.Mask.show();
    	},
    	hideMask( reload ){
    		resetDefaultVal( this , this.getDefault() ) ;
    		if( reload ) {
    			let pate = this.tableOption.paginate.page ;
    			this.doSelPage( pate );
    		}
    		this.$refs.Mask.hide();
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
				uri 	: this.sel.uri ,
			};

			this.showLoading(true);
			this.ApiUtilSA.get( "/sri/get-all-sri-data" , params )
			.then( function( res ) {
				if( res.success ) {
					let data = res.data ;
					let content = data.content ;
					content.forEach(function( item ){
						let uuid = genNonDuplicateID();
						item.previousData = JSON.stringify({
							uri 	: item.uri ,
    						sri 	: item.sri ,
						})
						item.id = uuid ;
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
    	clear(){
    		this.sel.uri = "" ;
    		this.this.clearTable();
    	},
    	save(){
    		let type = this.type ;
    		let uri = this.input.uri ;
    		let sri = this.input.sri ;
    		let invalid2s = {} ;

    		if( uri == "" ) {
    			invalid2s.uri2 = "請輸入網址" ;
    		}

    		this.invalid2s = invalid2s ;
    		if( isEmpty( invalid2s ) ) {
    			let apiUrl ;
    			let _this = this ;
    			let param = {
	    			uri 	: uri ,
					sri 	: sri ,
	    		}

	    		if( type === "EDIT" ) {
	    			apiUrl = "sri/update-sri-data";
	    			param["previousData"] = this.previousData ;
    			} else {
    				apiUrl = "/sri/create-sri-data";
    			}

    			_this.showLoading(true);
        		this.ApiUtilSA.post( apiUrl , param )
        		.then((res) => {
					if( res.success ) {
						let message = _this.type === 'ADD' ? '新增' : '修改' ;
						message += "子資源完整性成功" ;
						
						this.showModel({
			                title : this.title ,
			                message : message ,
			                endFunc : function(){
			                	_this.hideMask( true );
			                }
			            });
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});

    		}
    	},
    	del( item ) {
    		let _this = this ;
    		let param = {
    			previousData 	: item.previousData ,
    			uri 			: item.uri 
    		}

    		_this.showLoading(true);

    		this.ApiUtilSA.delete( "/sri/delete-sri-data" , param )
    		.then((res) => {
				if( res.success ) {
					this.showModel({
		                title : this.title ,
		                message : "刪除子資源完整性成功",
		                endFunc : function(){
		                	_this.hideMask( true );
		                }
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
    	},
    	getDefault(){
    		return {
				input		: {
					uri 	: "" ,
					sri 	: "" ,
				},
				type 		: "ADD" ,
				previousData: "" ,
    		}
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