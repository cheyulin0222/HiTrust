<template>
	<div class="container-fluid">
		<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
			憑證商品管理-產品成本查詢
		</h4>
		<div class="row p-3" ref = 'search' >
			<CardItem colMd="6">
				<DatePicker ref="datePicker" v-model="input.date" LabelVal = "編輯日範圍" />
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "price" LabelVal = "成本" :input = "input" :invalids = "invalids" maxlength="12"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "year" LabelVal = "年份(YYYY)" :input = "input" :invalids = "invalids" maxlength="4"/>
			</CardItem>
			<CardItem colMd="6">
				<SelectView :options="selOption.cpdType" :invalidTxt="invalids.cpdType" LabelVal = "憑證類別"
				v-model = "input.cpdType"  ref = "cpdType"/>
			</CardItem>
			<CardItem colMd="6">
				<SelectView :options="selOption.salesId" :invalidTxt="invalids.salesId" LabelVal = "業務"
				v-model = "input.salesId" ref = "salesId"/>
			</CardItem>
		</div>
		<div class="row mt-2 mb-5">
			<div class="col-md-4 offset-md-4 p-2">
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
		:options ="tableOption">
		</DataTable>
	</div>
	<Mask ref = "Mask">
		<div class="p-3">
			<Card :title = " ( type === 'ADD' ? '新增' : '維護' ) + '產品成本' ">
				<CardItem colMd="6">
					<SelectView :options="selOption.cpdType" :invalidTxt="invalids.cpdType" LabelVal = "憑證類別"
					v-model = "prodObj.cpdType" ref = "cpdType2" :IDRandom="true" :readOnly="type === 'EDIT' ? true : false"/>
				</CardItem>
				<CardItem colMd="6">
					<SelectView :options="selOption.expiryType" :invalidTxt="invalids.expiryType" LabelVal = "效期"
					v-model = "prodObj.expiryType" ref = "expiryType" :readOnly="type === 'EDIT' ? true : false"/>
				</CardItem>
				<CardItem colMd="6" v-if="type === 'ADD' ">
					<SelectView :options="yearOption" :invalidTxt="invalids.year" LabelVal = "調整年份"
					v-model = "prodObj.year" ref = "year" :IDRandom="true" :readOnly="type === 'EDIT' ? true : false"/>
				</CardItem>
				<CardItem colMd="6" v-if="type === 'EDIT' ">
					<Input ID = "year" LabelVal = "調整年份" :input = "prodObj" :invalids = "invalids" :readOnly ="true"/>
				</CardItem>
				<CardItem colMd="6" v-if=" type === 'ADD'">
					<Input ID = "otherYear" LabelVal = "新增年份(YYYY)" :input = "prodObj" :invalids = "invalids" v-if="prodObj.year === '99' "/>
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "price" LabelVal = "成本(NTD)" :input = "prodObj" :invalids = "invalids" :IDRandom="true" maxlength="12"/>
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
					<button type="button" @click.prevent="validate"
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
import SelectView from "@/components/Select" ;
import Mask from "@/components/Mask.vue" ;
import Input from "@/components/Input" ;
import DatePicker from '@/components/DatePicker.vue' ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import  moment from 'moment' ;
import { getExpiryTypeList , getCertTypeList } from "@/utils/dataListCMS" ;
import { getSalesId2 } from "@/utils/dataListCMS" ;
import { isEmptyObject } from "@/utils/util";

let defaultSel = {
	date 		: [ moment().subtract(1, 'months'), moment() ] ,
	price		: "",
	year 		: "",
	cpdType 	: "",
	salesId 	: "" ,
}

export default {

	data(){
		let input = Object.assign({},defaultSel);
		return {
			selOption : {
				cpdType : {
		    		ID 			: "cpdType" ,
		    		func 		: getCertTypeList.bind(false, this) ,
		    		addNullText : true ,
		    		selType 	: "dropdown" ,
		    		defVal 		: input.cpdType ,
		    	},
				salesId : {
		    		ID 			: "salesId" ,
		    		func 		: getSalesId2.bind(true, this) ,
		    		addNullText : true ,
		    		selType 	: "dropdown" ,
		    		defVal 		: input.salesId ,
		    	},
		    	expiryType : {
		    		ID 			: "expiryType" ,
		    		func 		: getExpiryTypeList.bind(false, this),
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
			},
			input : input,
			previousData : "",
			invalids : { 
				
			},
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['cpdType'],
						value : ['產品類別'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['year'],
						value : ['年份']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['price'],
						value : ['成本'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['salesName'],
						value : ['業務'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['updatedTtm'],
						value : ['編輯日'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['cpdId'],
						value : [""],
						classR: "" ,
						styleT: "width:140px;",
						button : [
							{
								class : "cms-query-print rounded-pill mt-2 mb-2" ,
								key : "調整成本" ,
								func : function ( productCost ){
									this.selectProductCost( productCost )
								}.bind(this)
							}
						]
					},
				] ,
				endButton : [
					{
						value : [""] ,
						icon: "fa-circle-plus" ,
						func : function ( payment ){
							this.doAdd();
						}.bind(this)
					}
				],
			},
			dataRow 		: [],
			type 			: "ADD" ,
			prodObj 		: this.getDefaultProd() ,
			nowYear 		: moment().year() ,
			canModYearNum 	: 3 ,
		}
	},
	extends : Base ,
	components : {
		SelectView , DataTable , Mask  , Input , DatePicker , Card , CardItem
	},
	computed: {
		yearOption(){
			let nowYear = this.nowYear ;
			let canModYearNum = this.canModYearNum ;
			let arr = [];
			for( var i = canModYearNum - 1 ; i >= 0  ; i -- ) {
				let year = ( nowYear - i ).toString();
				arr.push({
					text 	: year ,
					val		: year ,
				})
			}
			arr.push({
				text 	: "新增" ,
				val		: "99" ,
			})
			return {
				ID 			: "year" ,
	    		func 		: arr ,
	    		selType 	: "dropdown" ,
	    		setFirst 	: true ,
			}
		}
	},
	methods: {
		search(){
			let invalids = {};
			if (this.input.price.trim() != "" && !this.isNonZeroNumeric(this.input.price)) {
				invalids["price"] = "請輸入大於0的數字"
			}
			if (this.input.year.trim() != "" && !this.isValidDate(this.input.year)) {
				invalids["year"] = "請輸入合法的年份"
			}

			this.invalids = invalids;
			if ( !isEmptyObject(invalids)) {
				return
			}

			let input = {
				startDate 	: this.input.date[0].format('YYYYMMDD'),
				endDate 	: this.input.date[1].format('YYYYMMDD'),
				price   	: this.input.price.trim(),
				year		: this.input.year.trim(),
				cpdType 	: this.input.cpdType,
				salesId 	: this.input.salesId
			}
			let _this = this ;
			let params = {};
			_this.showLoading(true);

			Object.keys(input).forEach(key => {
				if (input[key] != "") {
					params[key] = input[key];
				}
			});

			if (params.price) {
				params.price = parseInt(params.price);
			}

			this.ApiUtilCMS.get("/get-cert-product-cost", params)
			.then((res) => {
				if( res.success ) {
					let data = res.data;
					_this.dataRow  = data.certProductCost;
					_this.reset();
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});

		},
		doSelPage( page ){

		},
    	updateVal( val , ID ){
    		this.input[ID] = val ;
    	},
    	updateVal2( val , ID ){
    		this.prodObj[ID] = val ;
    	},
    	showMask(){
			this.invalids = {};
    		this.$refs.Mask.show();
    	},
    	hideMask(){
			this.invalids = {};
    		this.prodObj = this.getDefaultProd();
    		this.$refs.Mask.hide();
    	},
    	validate(){
			let invalids = {};

			if (this.type === "ADD") {
				if (this.prodObj.cpdType === "") {
					invalids["cpdType"] = "請選擇憑證類型"
				}
				if (this.prodObj.expiryType === "") {
					invalids["expiryType"] = "請選擇效期"
				}
				if (this.prodObj.year === "") {
					invalids["year"] = "請選擇年份"
				}
				if (this.prodObj.year === "99" && !this.isValidDate(this.prodObj.otherYear)) {
					invalids["otherYear"] = "請輸入合法的年份"
				}
			}
			if (!this.isNonZeroNumeric(this.prodObj.price)) {
				invalids["price"] = "請輸入大於0的數字"
			}

			this.invalids = invalids;
			if ( !isEmptyObject(invalids)) {
				return
			}

			if (this.type === "ADD") {
				let params = {
					previousData: JSON.stringify({}),
					cpdType 	: this.prodObj.cpdType,
					expiryType 	: this.prodObj.expiryType,
					year 		: this.prodObj.year === "99" ? this.prodObj.otherYear : this.prodObj.year,
					price 		: parseInt(this.prodObj.price),
				}
	
				this.create(params);
			}else {
				let params = {
					previousData: this.previousData,
					cpdType 	: this.prodObj.cpdType,
					expiryType 	: this.prodObj.expiryType,
					year 		: this.prodObj.year,
					price 		: parseInt(this.prodObj.price),
				}
	
				this.update(params);
			}

    	},
		create(params) {
			let _this = this;
			_this.showLoading(true);

			this.ApiUtilCMS.post("/create-cert-product-cost", params)
			.then(res => {
				_this.showLoading();
				if ( res.success ) {
					let data = res.data
					_this.showModel({
						title : data.message,
						message : data.message,
						endFunc : function(){
							_this.hideMask();
							_this.setReSearchInput(params);
							_this.search();
						}
					});
				}
			})
			.finally(() => {
				_this.showLoading();
			})
		},
		update(params) {
			let _this = this;
			_this.showLoading(true);
			this.ApiUtilCMS.post("/update-cert-product-cost", params)
			.then(res => {
				_this.showLoading();
				if ( res.success ) {
					let data = res.data
					_this.showModel({
						title 	: data.message,
						message : data.message,
						endFunc : function(){
							_this.hideMask();
							_this.setReSearchInput(params);
							_this.search();
						}
					});
				}
			})
			.finally(() => {
				_this.showLoading();
			})
		},
    	selectProductCost( productCost ){
    		this.type = "EDIT" ;
    		
			let cpdType = productCost.cpdType.substring(0, productCost.cpdType.length - 4);
			let expiryType = productCost.cpdType.substring(productCost.cpdType.length - 3);
			
			switch (expiryType) {
				case '一年期':
					expiryType = "ONE_YEAR";
					break;
				case '二年期':
					expiryType = "TWO_YEARS";
					break;
				case '三年期':
					expiryType = "THREE_YEARS";
					break;
				default:
					expiryType = "";
			}

    		this.prodObj = {
    			cpdType 	: cpdType,
            	expiryType 	: expiryType,
            	price 		: productCost.price ,
            	year 		: productCost.year,
    		}

			this.previousData = JSON.stringify(this.prodObj);

    		this.showMask();
    	},
    	doAdd(){
    		this.type = "ADD" ;
			this.$refs.cpdType2.text = "請選擇";
			this.$refs.cpdType2.arr[0].text = "請選擇";
    		this.prodObj = this.getDefaultProd();
    		this.showMask();
    	},
    	getDefaultProd(){
    		return {
    			cpdType 	: "" ,
            	expiryType 	: "" ,
            	price 		: "" ,
            	year 		: this.nowYear ,
            	otherYear   : "" ,
    		}
    	},
		isValidDate(str) {
			const regex = /^[0-9]{4}$/;
			return regex.test(str);
		},
		isNonZeroNumeric(str) {
			return /^[1-9][0-9]*$/.test(str);
		},
		reset() {
			this.input = Object.assign({}, defaultSel);
		},
		setReSearchInput(prductCost) {
			this.input = {
				date 		: [moment(), moment().add(1, 'days')],
				price		: prductCost.price.toString(),
				year 		: prductCost.year,
				cpdType 	: prductCost.cpdType,
			}
		}
	}
}
</script>