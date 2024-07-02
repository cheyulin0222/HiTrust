<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		操作記錄查詢
	</h4>
	<div class="row p-3" ref = 'search' >
		<CardItem colMd="6">
			<DatePicker ref="datePicker" LabelVal = "執行時間" v-model="input.date" :timePicker = "true"/>
		</CardItem>
		<CardItem colMd="6">
			<Input ID = "userId" LabelVal = "使用者代碼" :input = "input" :invalids = "invalids"/>
		</CardItem>
		<CardItem colMd="6">
			<SelectView :options="selOption.functionId" :invalidTxt="invalids.functionId" LabelVal = "功能名稱"
			v-model = "input.functionId" ref = "functionId"/>
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
			<Card title = "詳細資料" :mainCenter = "true">
				<template v-if=" detilData.diffData.length > 0 ">
					<CardItem colMd="12" class="text-center">
						差異資料
					</CardItem>
					<CardItem colMd="10">
						<table class="table table-hover m-0" style="border-bottom: 1px solid rgb(222, 226, 230);">
							<thead>
								<tr>
									<th class="border border-info rounded-0 p-2 text-center alert-primary text-center col-2">
										參數
									</th>
									<th class="border border-info rounded-0 p-2 text-center alert-primary text-center col-5">
										原始資料
									</th>
									<th class="border border-info rounded-0 p-2 text-center alert-primary text-center col-5">
										新資料
									</th>
								</tr>
							</thead>
							<tbody>
								<template v-for="( data , index ) in detilData.diffData" :key="UUID()" visisble="true">
									<tr>
										<td>
											{{ data.key }}
										</td>
										<td>
											{{ data.oldData }}
										</td>
										<td>
											{{ data.newData }}
										</td>
									</tr>
								</template>
							</tbody>
						</table>
					</CardItem>
				</template>
				<CardItem colMd="12" class="text-center">
					<template v-if=" detilData.diffData.length > 0 ">
						<hr class="hr-store">
					</template>
						傳送資料
				</CardItem>
				<CardItem colMd="8" class = "pt-1 pb-1 pl-3 pr-3 bg-white" style="white-space:pre-wrap;word-break: break-all;">
					{{ detilData.request}}
				</CardItem>
				<CardItem colMd="12" class = "text-center">
					<hr class="hr-store">
					回傳資料
				</CardItem>
				<CardItem colMd="8" class = "pt-1 pb-1 pl-3 pr-3 bg-white" style="white-space:pre-wrap;word-break: break-all;">
					{{ detilData.response}}
				</CardItem>

			</Card>
		</div>
		<div class="row mt-2 mb-5">
			<div class="col-md-4 offset-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="hideMask"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">關閉</span>
					</button>
				</div>
			</div>
		</div>
	</Mask>
</template>
<script>
import DataTable from '@/components/Table/PageDataTable.vue' ;
import SelectView from "@/components/Select" ;
import DatePicker from '@/components/DatePicker.vue' ;
import Mask from "@/components/Mask.vue" ;
import Input from "@/components/Input" ;
import Base from "@/components/RightViewBase" ;
import Card from "@/components/Card";
import CardItem from "@/components/CardItem";
import { getFIDList } from "@/utils/dataListSA" ;
import { checkDifference , genNonDuplicateID , isEmpty } from "@/utils/util" ;

import moment from 'moment' ;

export default {

	data(){
		let _this = this ;

		return {
			selOption : {
				functionId : {
		    		ID 			: "functionId" ,
		    		func 		: getFIDList ,
		    		selType 	: "dropdown"
		    	},
			},
			input 		: this.getDefaultSel() ,
			invalids 	: {} ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['functionId'],
						value : ['功能ID'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['userId'],
						value : ['使用者ID']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['uri'],
						value : ['URI'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['httpMethod'],
						value : ['Http Method'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['createTime'],
						value : ['操作時間']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['auditId'],
						value : [""],
						classR: "d-flex" ,
						styleT: "width:100px;",
						button : [
							{
								class : "sip-status-resend rounded-pill" ,
								key : "顯示詳細資料" ,								
								func : function ( payment ){
									_this.showDetail( payment );
								}.bind(this)
							},
						]
					}
				] ,
				paginate: {
					page: 1,
					size: 10,
					total: 0,
				},
				tableStyle : {
					'font-size' : '9pt'
				}
			},
			dataRow 	: [],
			detilData 	: {
				diffData : [] ,
			} ,
		}
	},
	extends : Base ,
	components : {
		SelectView , DataTable , Mask , Input , Card , CardItem , DatePicker
	},
	methods: {
		search(){
			this.doSelPage(1);
		},
		doSelPage( page ){
			let _this = this ;
			let params = {};
			let input = this.input ;
			
			Object.keys( input ).forEach(function(key){
				let data = input[key] ;
				if( !isEmpty(data) ) {
					if( key === 'date' ) {
						params["startDateTime"] = data[0].format('yyyy-MM-DD HH:mm');
						params["endDateTime"] = data[1].format('yyyy-MM-DD HH:mm');
					} else {
						params[key] = data ;
					}
				}
			});

			let size = this.$refs.table1 ? this.$refs.table1.resize() : this.options.paginate.size ;
			params["page"] =  page - 1 ; 
			params["size"] = size ; 
			
			_this.showLoading(true);


			this.ApiUtilSA.get('/get-ap-audit-log',params)
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					_this.dataRow = data.content ;
					_this.tableOption.paginate.page = page ;
	                _this.tableOption.paginate.total = data.totalElements ;
	                _this.tableOption.paginate.size = size ;
				}
			})
			.finally(function () {
  				_this.showLoading();
  			});
		},
		showDetail( data ){
			let request = JSON.parse(data.request);
			let response = JSON.parse(data.response);
			if( data.previousData != "" ) {
				let previousData = JSON.parse(data.previousData);
				let diffData = [] ;
				Object.keys( previousData ).forEach(function( key ){
					let oldData	= previousData[key] ;
					let newData = request[key] 
					let diff = checkDifference( oldData , newData ) ;
					if( diff ) {
						diffData.push({
							key 	: key ,
							oldData	: oldData ,
							newData : newData ,
						})
					}
					
					delete request[key];
				});
				Object.keys( request ).forEach(function( key ){
					diffData.push({
						key 	: key ,
						oldData	: "" ,
						newData : request[key] ,
					})
				});
				this.detilData = {
					request 	: JSON.stringify(JSON.parse(data.request),null,'\t') ,
					response 	: JSON.stringify(response,null,'\t') ,
					diffData 	: diffData 
				}
			} else {
				this.detilData = {
					request 	: JSON.stringify(request,null,'\t') ,
					response 	: JSON.stringify(response,null,'\t') ,
					diffData 	: [] 
				}
			}
			this.showMask();
		},
    	getDefaultSel(){
    		return {
    			date 		: [ moment().subtract(1, 'day'), moment() ] ,
    			userId 		: "" ,
    			functionId 	: "" ,
    		}
    	},
    	getDefaultDetail(){
    		return {
				diffData : [] ,
			}
    	},
    	clear(){
    		this.input = this.getDefaultSel() ;
    		this.$refs.functionId.clear();
    		this.clearTable();
    	},
    	showMask( type ){
    		this.$refs.Mask.show();
    	},
    	hideMask(){
    		this.detilData = this.getDefaultDetail() ;
    		this.$refs.Mask.hide();
    	},
    	UUID(){
			return genNonDuplicateID();
		},
	}
}	
</script>