<template>
	<div class="container-fluid">
		<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
			訂單管理-到期憑證查詢
		</h4>
		<div class="row p-3" ref = 'search' >
			<div class=" order-md-1 order-0 col-md-12">
				<DatePicker ref="datePicker" LabelVal = "到期日範圍"
					v-model="input.date"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6">
				<Input ID = "saleNo" LabelVal = "銷售編號" :input = "input" :invalids = "invalids" maxlength="20"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6">
				<Input ID = "nameCh" LabelVal = "公司名稱" :input = "input" :invalids = "invalids" maxlength="50"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6">
				<SelectView :options="selOption.cpdType" :invalidTxt="invalids.cpdType" LabelVal = "憑證類別"
				v-model = "input.cpdType" ref = "cpdType"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6">
				<SelectView :options="selOption.expiryType" :invalidTxt="invalids.expiryType" LabelVal = "效期"
				v-model = "input.expiryType" ref = "expiryType"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6">
				<SelectView :options="selOption.salesId" :invalidTxt="invalids.salesId" LabelVal = "業務"
				v-model = "input.salesId" ref = "salesId"/>
			</div>
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
		<table class="table table-hover m-0" style="border-bottom: 1px solid rgb(222, 226, 230);">
			<thead>
				<tr>
					<th colspan="1" class="border border-info rounded-0 p-3 text-center alert-primary text-center" scope="col">
						<span class="selectText">銷售編號</span>
					</th>
					<th colspan="1" class="border border-info rounded-0 p-3 text-center alert-primary text-center" scope="col">
						<span class="selectText">訂購日期</span>
					</th>
					<th colspan="1" class="border border-info rounded-0 p-3 text-center alert-primary text-center" scope="col">
						<span class="selectText">公司（機構）名稱</span>
					</th>
					<th colspan="1" class="border border-info rounded-0 p-3 text-center alert-primary text-center" scope="col">
						<span class="selectText">憑證類型</span>
					</th>
					<th colspan="1" class="border border-info rounded-0 p-3 text-center alert-primary text-center" scope="col">
						<span class="selectText">效期</span>
					</th>
					<th colspan="1" class="border border-info rounded-0 p-3 text-center alert-primary text-center" scope="col">
						<span class="selectText">到期日期</span>
					</th>
					<th colspan="1" class="border border-info rounded-0 p-3 text-center alert-primary text-center" scope="col">
						<span class="selectText">續約</span>
					</th>
					<th colspan="1" class="border border-info rounded-0 p-3 text-center alert-primary text-center" scope="col">
						<span class="selectText">系統通知</span>
					</th>
					<th colspan="1" class="border border-info rounded-0 p-3 text-center alert-primary text-center" scope="col">
						<span class="selectText">業務</span>
					</th>
					<th colspan="1" class="border border-info rounded-0 p-3 text-center alert-primary text-center" scope="col">
						<span class="selectText">操作功能</span>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="( row , index ) in dataRow" >
					<td colspan="1" class="text-center">
						<span class="selectText">{{row.saleNo}}</span>
					</td>
					<td colspan="1" class="text-center">
						<span class="selectText">{{row.orderDate}}</span>
					</td>
					<td colspan="1" class="text-center">
						<span class="selectText">{{row.nameCh}}</span>
					</td>
					<td colspan="1" class="text-center">
						<span class="selectText">{{row.cpdType}}</span>
					</td>
					<td colspan="1" class="text-center">
						<span class="selectText">{{row.expiryType}}</span>
					</td>
					<td colspan="1" class="text-center">
						<span class="selectText">{{row.expiryDate}}</span>
					</td>
					<td colspan="1" class="text-center">
						<span class="selectText">{{row.isRenew}}</span>
					</td>
					<td colspan="1" class="text-center">
						<span class="selectText">{{row.noticeResult}}</span>
					</td>
					<td colspan="1" class="text-center">
						<span class="selectText">{{row.salesName}}</span>
					</td>
					<td colspan="1" class="d-flex align-items-center justify-content-center" >
						<div class="sip-status-resend rounded-pill" role="button" style="line-height:200%;" @click.prevent="updateUnrenew(row)">不續約</div>
						<div class="pl-2 pr-2" style="width:170px;">
							<SelectView :options="getNoCountOption(index)" :invalidTxt="invalids[row.orderDtlNo]"
							v-model = "row.noCount" ref = "noCount"/>
						</div>
						<div class="sip-status-resend rounded-pill" role="button" style="line-height:200%;" @click.prevent="updateRenew(row)">已續約</div>
					</td>
				</tr>
			</tbody>
		</table>
		<template v-if="dataRow.length == 0 ">
			<div class="text-center p-1" style="font-size: 18px;">查無資料</div>
		</template>
	</div>
	
</template>
<script>
import Base from "@/components/RightViewBase" ;
import DataTable from '@/components/Table/DataTable.vue'
import SelectView from "@/components/Select";
import Input from "@/components/Input"
import DatePicker from '@/components/DatePicker.vue'
import  moment from 'moment'
import { getSalesId2 } from "@/utils/dataListCMS" ;
import { getCertTypeList , getExpiryTypeList } from "@/utils/dataListCMS" ;
import { isEmptyObject } from "@/utils/util";

let defaultSel = {
	date 		: [ moment(), moment().add(1,'months') ] ,
	salesNo 	: "" , 
	nameCh	: "" ,
	cpdType 	: "" ,
	expiryType 	: "" ,
	salesId 	: "",
}

export default {

	data(){
		let _this = this ;
		let input = Object.assign({},defaultSel);
		return {
			selOption : {
				expiryType : {
		    		ID 			: "expiryType" ,
		    		func 		: getExpiryTypeList.bind(true, this),
					addNullText : true ,
		    		//placeholder : "請輸入產業別",
		    		selType 	: "dropdown",
					defVal 		: input.cpdType,
		    	},
				cpdType : {
		    		ID 			: "cpdType" ,
		    		func 		: getCertTypeList.bind(true, this),
		    		//placeholder : "請輸入產業別",
		    		setFirst 	: true ,
		    		selType 	: "dropdown",
					defVal 		: input.cpdType,
		    	},
		    	salesId : {
		    		ID 			: "salesId" ,
		    		func 		: getSalesId2.bind(true , this),
		    		addNullText : true ,
		    		selType 	: "dropdown" ,
		    		defVal 		: input.salesId,
		    	},
		    	
			},
			input : input,
			invalids : { 
				
			},
			dataRow : [],
		}
	},
	mounted() {
        
    },
	components : {
		Input , SelectView , DatePicker
	},
	extends : Base ,
	methods: {
		search(){
			
			let invalids = {};
			if (!this.isValidStr(this.input.saleNo)) {
				invalids["saleNo"] = "請輸入0~20位英數字"
			}

			this.invalids = invalids;
			if ( !isEmptyObject(invalids)) {
				return
			}

			let input = {
				startDate 	: this.input.date[0].format('YYYYMMDD'),
				endDate 	: this.input.date[1].format('YYYYMMDD'),
				saleNo 		: this.input.saleNo,
				nameCh 		: this.input.nameCh,
				cpdType 	: this.input.cpdType,
				expiryType 	: this.input.expiryType,
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
			this.ApiUtilCMS.get("/get-expire-certs", params)
			.then((res) => {
				if( res.success ) {
					let data = res.data;
					let expireCerts = data.expireCerts ;
					expireCerts.forEach(function( item ){
						item.noCount = "";
					})
					_this.dataRow  = data.expireCerts ;
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		updateUnrenew(product){
			let invalids = {};
				if (product.noCount === "") {
					invalids[product.orderDtlNo] = "請選擇不續約原因"
				}
			
			this.invalids = invalids;
			if ( !isEmptyObject(invalids)) {
				return
			}
			
			let previousData = JSON.parse(JSON.stringify(product));
			delete previousData.noCount;
			let previousDataString = JSON.stringify(previousData);

			let data = {
				orderDtlNo		: product.orderDtlNo,
				isRenew 		: "NO_RENEW",
				reasonNorenew 	: product.noCount
			}

			let params = data;
			params.previousData = previousDataString;

			let _this = this;
			_this.showLoading(true);
			this.ApiUtilCMS.post("/update-unrenew-cert", params)
			.then(res => {
				_this.showLoading();
				if ( res.success ) {
					let data = res.data
					_this.showModel({
						title : data.message,
						message : data.message,
						endFunc : function(){
							product.isRenew = "不續約";
							product.noCount = "";
						}
					});
				}
			})
			.finally(() => {
				_this.showLoading();
			})
    	},
		updateRenew(product) {
			let previousData = JSON.parse(JSON.stringify(product));
			delete previousData.noCount;
			let previousDataString = JSON.stringify(previousData);

			let data = {
				orderDtlNo		: product.orderDtlNo,
				isRenew 		: "IS_RENEW",
			}
			
			let params = data;
			params.previousData = previousDataString;
			
			let _this = this;
			_this.showLoading(true);

			this.ApiUtilCMS.post("/update-renew-cert", params)
			.then(res => {
				_this.showLoading();
				if ( res.success ) {
					let data = res.data
					_this.showModel({
						title : data.message,
						message : data.message,
						endFunc : function(){
							product.isRenew = "已續約";
						}
					});
				}
			})
			.finally(() => {
				_this.showLoading();
			})
		},
		doSelPage( page ){

		},
    	updateVal2( val , ID ){
    		let IDS = ID.split("_");
    		let id = IDS[0];
    		let index = IDS[1];

    		this.dataRow[index][ID] = val ;
    	},
    	getNoCountOption( index ){
    		return  {
	    		ID 			: "noCount_" + index ,
	    		func 		: [{
	    			text 	: "請選擇不續約原因" ,
					val		: "" ,
	    		},
	    		{
	    			text 	: "商品價格太高" ,
					val		: "TOO_EXPENSIVE" ,
	    		},
	    		{
	    			text 	: "服務差" ,
					val		: "BAD_SERVICE" ,
	    		},
	    		{
	    			text 	: "商品種類不齊全" ,
					val		: "LIMITED_PRODUCT_RANGE" ,
	    		},
	    		{
	    			text 	: "沒需求" ,
					val		: "NO_DEMEND" ,
	    		}],
	    		setFirst 	: true ,
	    		selType 	: "dropdown"
	    	}
    	},
    	clear(){
    		this.input = Object.assign({},defaultSel) ;
    		this.$refs.cpdType.clear();
    		this.$refs.expiryType.clear();
    		this.$refs.salesId.clear();
    		this.dataRow = [] ;
    	},
		isValidStr(str) {
			const regex = /^[a-zA-Z0-9]{0,20}$/;
			return regex.test(str);
		},
		getExpiryTypeByLabel(label) {
			switch(label) {
				case "一年期" :
					return "ONE_YEAR";
				case "二年期" :
					return "TWO_YEARS";
				case "三年期" :
					return "THREE_YEARS";
			}
		},
	}
}	
</script>