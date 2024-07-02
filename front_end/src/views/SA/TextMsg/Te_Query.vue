<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		簡訊資料查詢
	</h4>
	<div class="row p-3" ref = 'search' >
		<CardItem colMd="6">
			<SelectView :options="selOption.acqId" :invalidTxt="invalids.acqId" LabelVal = "收單銀行代碼" v-model = "input.acqId" ref = "acqId" :required="true"/>
		</CardItem>
		<CardItem colMd="6">
			<Input ID = "orderNum" LabelVal = "訂單編號" :input = "input" :invalids = "invalids"/>
		</CardItem>
		<CardItem colMd="6">
			<Input ID = "merId" LabelVal = "商店代號" v-model="input.merId" :invalids = "invalids" 
				placeholder="若沒輸入則搜尋此收單所有商代"/>
		</CardItem>
		<CardItem colMd="6">
			<Input ID = "telHash" LabelVal = "手機號碼" :input = "input" :invalids = "invalids"/>
		</CardItem>
		<CardItem colMd="6">
			<DatePicker ref="datePicker" LabelVal = "發送時間" v-model="input.date" :timePicker = "true"/>
		</CardItem>

		<CardItem colMd="6">
			<SelectView :options="selOption.smsType" :invalidTxt="invalids.smsType" LabelVal = "簡訊類型" v-model="input.smsType" ref = "smsType" />
		</CardItem>
		<CardItem colMd="6">
			<SelectView :options="selOption.result" :invalidTxt="invalids.result" LabelVal = "發送結果" v-model = "input.result" ref = "result" />
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
		<div class="col-md-4 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="search"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">查詢資料</span>
				</button>
			</div>
		</div>
	</div>
	<DataTable ref="table1"
	:dataRow="dataRow"
	:options ="tableOption">
	</DataTable>
</template>
<script>
import DataTable from '@/components/Table/PageDataTable.vue' ;
import SelectView from "@/components/Select";
import DatePicker from '@/components/DatePicker.vue' ;
import Base from "@/components/RightViewBase" ;
import CardItem from "@/components/CardItem" ;
import Input from "@/components/Input" ;
import moment from 'moment' ;
import { getTeAcqList , getMerIDList , getTxtTypeList , getRsltList} from "@/utils/dataListSA";
import { isEmpty } from "@/utils/util";


let defaultSel = {
	acqId		: "" ,
	orderNum 	: "" ,
	merId 		: "" ,
	telHash 	: "" ,
	date 		: [ moment().subtract(1, 'hours'), moment() ] ,
	smsType 	: "" ,
	result 		: "" ,
}

export default {

	data(){
		let _this = this ;
		let input = Object.assign({},defaultSel) ;
		return {
			selOption 	: {
				acqId : {
		    		ID 			: "acqId" ,
		    		func 		: getTeAcqList.bind(this,true),
		    		selType 	: "dropdown" ,
		    		filter 		: true ,
		    		setFirst 	: true ,
		    		addNullText	: true ,
		    	},
		    	merId : {
		    		ID 			: "merId" ,
		    		func 		: getMerIDList.bind(this , true ),
		    		selType 	: "dropdown" ,
		    		filter 		: true ,
		    		setFirst 	: true ,
		    	},
		    	smsType : {
		    		ID 			: "smsType" ,
		    		func 		: getTxtTypeList.bind(this , true ),
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
		    	},
		    	result : {
		    		ID 			: "result" ,
		    		func 		: getRsltList.bind(this , true ),
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
		    	}
			},
			input : input,
			invalids : { 
				
			},
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['merId'],
						value : ['商家代碼'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderNum'],
						value : ['訂單編號']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['acqId'],
						value : ['收單行'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['content64'],
						value : ['簡訊內容']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['type'],
						value : ['簡訊類型'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['telMask'],
						value : ['手機號碼'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['result'],
						value : ['發送結果'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['sendingTime'],
						value : ['發送時間'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['remark'],
						value : ['備註'],
					},
				],
			},
			dataRow : [],
		}
	},
	extends : Base ,
	mounted() {

    },
	components : {
		DataTable , SelectView , DatePicker , Input , CardItem
	},
	methods: {
		search(){
			let invalids = {} ;
			let input 		= this.input ;
			let acqId 		= input.acqId ;
			let orderNum 	= input.orderNum ;
			let merId 		= input.merId ;
  			let telHash     = input.telHash ;
  			let date 		= input.date ;
  			let smsType  	= input.smsType ;
  			let result  	= input.result ;
  			let _this 		= this ;

  			if( acqId == "" ) {
  				invalids.acqId = "請選擇收單行" ;
  			}

  			if( telHash != "" && isNaN( telHash ) ) {
  				invalids.telHash = "手機號碼請輸入數字" ;
  			}

  			this.invalids = invalids ;

  			if( isEmpty(invalids) ) {
  				let _this = this ;
				let param = {
					acqId 		: acqId ,
					orderNum	: orderNum ,
					merId 		: merId ,
					telHash		: telHash ,
					startTime	: date[0].format('yyyy-MM-DD HH:mm:ss') ,
					endTime		: date[1].format('yyyy-MM-DD HH:mm:ss') ,
					smsType 	: smsType ,
					result		: result ,
				};
				
				_this.showLoading(true);

				this.ApiUtilSA.get('get-sms-log-data',param)
				.then((res) => {
					if( res.success ) {
						_this.dataRow = res.data
					} else {
						_this.dataRow = [] ;
					}
					_this.showLoading(); 
				});
  			}
		},
		doSelPage( page ){

		},
    	clear(){
    		this.input = Object.assign({},defaultSel) ;
    		this.$refs.acqID.clear();
    		this.$refs.merID.clear();
    		this.$refs.smsType.clear();
    		this.$refs.result.clear();
    		this.dataRow = [];
    	},
	}
}	
</script>