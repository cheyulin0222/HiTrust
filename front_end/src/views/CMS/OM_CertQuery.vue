<template>
	<div class="container-fluid">
		<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
			訂單管理-憑證查詢
		</h4>
		<div class="row p-3" ref = 'search' >
			<div class=" order-md-1 order-0 col-md-6 mb-2">
				<label for="dateType_ORDER_DATE" class="t-12">
					<input id = "dateType_ORDER_DATE" type="radio" name = "dateType" value="ORDER_DATE" v-model="input.dateType" /> 訂購日期範圍
				</label>
				<DatePicker ref="datePicker" :disabled = "input.dateType != 'ORDER_DATE' " 
					v-model="input.date1"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6 mb-2">
				<label for="dateType_EFFECTIVE_DATE" class="t-12">
					<input id = "dateType_EFFECTIVE_DATE" type="radio" name = "dateType" value="EFFECTIVE_DATE" v-model="input.dateType" /> 核發日期範圍
				</label>
				<DatePicker ref="datePicker" :disabled = "input.dateType != 'EFFECTIVE_DATE' " 
					v-model="input.date2"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6 mb-2">
				<Input ID = "saleNo" LabelVal = "銷售編號" :input = "input" :invalids = "invalids" maxlength="20"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6 mb-2">
				<Input ID = "nameCh" LabelVal = "公司名稱" :input = "input" :invalids = "invalids" maxlength="50"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6 mb-2">
				<SelectView :options="selOption.certStatus" :invalidTxt="invalids.certStatus" LabelVal = "憑證狀態"
				v-model = "input.certStatus"  ref = "certStatus"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6 mb-2">
				<SelectView :options="selOption.category" :invalidTxt="invalids.category" LabelVal = "客戶分類"
				v-model = "input.category" ref = "category"/>
			</div>
			<div class=" order-md-1 order-0 col-md-6 mb-2">
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
		<div class="pl-3">
			<span style="color:#A60000;font-weight: bold; font-size: 20px">
				點選銷售編號可查看訂單資料
			</span>
		</div>
		<DataTable ref="table1"
		:dataRow="dataRow"
		:options ="tableOption">
		</DataTable>
	</div>
	
	<div ref="printContent" style="display: none;">
		<ApplyTypeSSLS v-if="printType === 'APPLICATION' && application.certApplyType === 'SSLS' " :application="application"></ApplyTypeSSLS>
		<ApplyTypeSSLT v-if="printType === 'APPLICATION' && application.certApplyType === 'SSLT' " :application="application"></ApplyTypeSSLT>
		<ApplyTypeCS v-if="printType === 'APPLICATION' && application.certApplyType === 'CS' " :application="application"></ApplyTypeCS>
		<SalesNote v-if="printType === 'SALES_NOTE' " :salesNote="salesNote"></SalesNote>
	</div>	
</template>

<script>
import DataTable from '@/components/Table/DataTable.vue' ;
import SelectView from "@/components/Select" ;
import DatePicker from '@/components/DatePicker.vue' ;
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import  moment from 'moment' ;
import { getSalesId2 } from "@/utils/dataListCMS" ;
import { getCertTypeList , getExpiryTypeList , getCategoryList } from "@/utils/dataListCMS" ;
import { isEmptyObject } from "@/utils/util";
import Mask from "@/components/Mask" ;
import ApplyTypeSSLS from '@/components/CMS/Apply/ApplyTypeSSLS.vue';
import ApplyTypeCS from '@/components/CMS/Apply/ApplyTypeCS.vue';
import ApplyTypeSSLT from '@/components/CMS/Apply/ApplyTypeSSLT.vue';
import SalesNote from '@/components/CMS/Apply/SalesNote.vue';

let defaultSel = {
	date1 		: [ moment().subtract(7, 'days'), moment() ] ,
	date2 		: [ moment().subtract(7, 'days'), moment() ] ,
	dateType : "ORDER_DATE",
	saleNo		: "" ,
	nameCh 	: "" ,
	certStatus 		: "" ,
	category 	: "" ,
	cpdType 	: "" ,
	expiryType  : "" ,
	salesId 	: "",
}

export default {

	data(){
		let _this = this ;
		let input = Object.assign({},defaultSel) ;
		return {
			printType : "",
			salesNote : {},
			application : {},
			selOption : {
				certStatus : {
		    		ID 			: "certStatus" ,
		    		func 		: [{
		    			text 	: "全部" ,
						val		: "" ,
		    		},
		    		{
		    			text 	: "尚未付款" ,
						val		: "UNPAID" ,
		    		},
		    		{
		    			text 	: "待上傳文件" ,
						val		: "PENDING" ,
		    		},
		    		{
		    			text 	: "審核中" ,
						val		: "UNDER_REVIEW" ,
		    		},
		    		{
		    			text 	: "已生效" ,
						val		: "EFFECTIVE" ,
		    		},
		    		{
		    			text 	: "已過期" ,
						val		: "EXPIRED" ,
		    		},
		    		{
		    			text 	: "已刪單" ,
						val		: "CANCELED" ,
		    		}],
		    		//placeholder : "請輸入產業別",
		    		setFirst 	: true ,
		    		selType 	: "dropdown",
		    		defVal 		: input.certStatus 
		    	},
		    	category : {
		    		ID 			: "category" ,
		    		func 		: getCategoryList.bind(true, this) ,
		    		addNullText : true ,
		    		selType 	: "dropdown" ,
					setFirst	: true
		    		// defVal 		: input.category  ,
		    	},
		    	cpdType : {
		    		ID 			: "cpdType" ,
		    		func 		: getCertTypeList.bind(true, this) ,
		    		addNullText : true ,
		    		selType 	: "dropdown" ,
		    		defVal 		: input.cpdType  ,
		    	},
		    	expiryType : {
		    		ID 			: "expiryType" ,
		    		func 		: getExpiryTypeList.bind(true , this),
		    		setFirst 	: true ,
		    		selType 	: "dropdown" ,
		    		defVal 		: input.expiryType 
		    	},
		    	salesId : {
		    		ID 			: "salesId" ,
		    		func 		: getSalesId2.bind(true , this) ,
		    		addNullText : true ,
		    		selType 	: "dropdown" ,
		    		defVal 		: input.salesId,
		    	},
			},
			input : input,
			invalids : { 
				
			},
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'link',
						key : ['saleNo'],
						value : ['銷售編號'],
						func : function( payment ){
	                        this.goNextPath("SA2802_1",{
                            	orderDtlNo : payment.orderDtlNo
                            })
	                    }.bind(this)
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderDate'],
						value : ['訂購日期']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['nameCh'],
						value : ['公司（機構）名稱'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['category'],
						value : ['客戶類別']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['cpdType'],
						value : ['憑證類型']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['expiryType'],
						value : ['效期'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderDetailStatus'],
						value : ['憑證狀態'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['effectiveDate'],
						value : ['核發日期'],
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['salesName'],
						value : ['業務'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['status'],
						value : ["列印"],
						classR: "" ,
						styleT: "width:140px;",
						button : [
							{
								class : "cms-query-print rounded-pill mt-2 mb-2" ,
								key : "SALES NOTE" ,
								func : function ( payment ){
									this.doSalesNote(payment.orderDtlNo);
								}.bind(this)
							},
							{
								class : "cms-query-print rounded-pill mb-2" ,
								key : "申請書" ,
								func : function ( payment ){
									this.doApplication(payment.orderDtlNo);
								}.bind(this)
							},
						]
					},
				],
			},
			dataRow : [],
		}
	},
	mounted() {
        
    },
	components : {
		DataTable , SelectView , DatePicker , Input, Mask, ApplyTypeSSLS, ApplyTypeCS, ApplyTypeSSLT, SalesNote
	},
	extends : Base ,
	methods: {
		search(){
			let input = {
				dateType 	: this.input.dateType,
				startDate 	: this.input.dateType === "ORDER_DATE" ? this.input.date1[0].format('YYYYMMDD') : this.input.date2[0].format('YYYYMMDD'),
				endDate 	: this.input.dateType === "ORDER_DATE" ? this.input.date1[1].format('YYYYMMDD') : this.input.date2[1].format('YYYYMMDD'),
				saleNo 		: this.input.saleNo,
				nameCh 		: this.input.nameCh,
				certStatus 	: this.input.certStatus,
				category 	: this.input.category,
				cpdType 	: this.input.cpdType,
				expiryType 	: this.input.expiryType,
				salesId 	: this.input.salesId
			}

			let invalids = {};
			if (!this.isValidStr(input.saleNo)) {
				invalids["saleNo"] = "請輸入0~20位英數字"
			}

			this.invalids = invalids;
			if ( !isEmptyObject(invalids)) {
				return
			}

			let _this = this ;
			let params = {};
			_this.showLoading(true);

			Object.keys(input).forEach(key => {
				if (input[key] != "") {
					params[key] = input[key];
				}
			});
			this.ApiUtilCMS.get("/get-certs", params)
			.then((res) => {
				if( res.success ) {
					let data = res.data;
					_this.dataRow  = data.certs ;
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		doSelPage( page ){

		},
		doSalesNote(orderDtlNo) {
			
			let _this = this ;
			_this.showLoading(true);

		
			this.ApiUtilCMS.get("/get-salesnotes/" + orderDtlNo)
			.then((res) => {
				if( res.success ) {
					let data = res.data;
					let currentDate = moment().format('YYYYMMDD');
					console.log("cpdType: " + data.cpdType);
					data.currentDate = currentDate;
					_this.salesNote = data;
					_this.printType = "SALES_NOTE";

					_this.$nextTick(() => {
						_this.print();
        			});
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
	
		},
		doApplication(orderDtlNo) {
		
			let _this = this ;
			_this.showLoading(true);

			this.ApiUtilCMS.get("/get-application/" + orderDtlNo)
			.then((res) => {
				if( res.success ) {
					let data = res.data;
					data.salesTels = _this.getTels( data.salesTel );
					data.techTels = _this.getTels( data.techTel );
					data.fincTels = _this.getTels( data.fincTel );
					_this.application = data;
					_this.printType = "APPLICATION";

					_this.$nextTick(() => {
						_this.print();
        			});
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
    	updateVal( val , ID ){
    		this.input[ID] = val ;
    	},
    	clear(){
			this.dataRow = [];
    		this.input = Object.assign({},defaultSel) ;
    		this.$refs.roleId.clear();
    	},
    	showMask(){
			this.$refs.Mask.show();
    	},
    	hideMask(){
    		this.$refs.Mask.hide();
    	},
		isValidStr(str) {
			const regex = /^[a-zA-Z0-9]{0,20}$/;
			return regex.test(str);
		},
		cvtChiDate(date) {
			let year = date.substring (0, 4);
			let month = date.substring (4, 6);
			let month1 = month % 10;
			let month2 = Math.floor(month / 10).toString();
			month2 = (month2 == '0') ? '' : month2;
			month = month2 + month1;
			let day = date.substring (6, 8);
			let day1 = day % 10;
			let day2 = Math.floor(day / 10).toString();
			day2 = (day2 == '0') ? '' : day2;
			day = day2 + day1;
			let strYear = this.cvtChiNum(year);
			let strMonth = this.cvtChiNum(month);
			let strDay = this.cvtChiNum(day);
			return strYear + '年' + strMonth + '月' + strDay + '日'
		},
		cvtChiNum(num){
			var result = ''
			const chineseNum = ['〇', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十'];
			if (num != null) {
				num = num.toString();
				for(let i = 0; i < num.length; i++){
					result = result + chineseNum[num.substr(i, 1)];
				}
			}
			return result;
		},
		print() {
			// 創建iframe
			let iframe = document.getElementById("print-iframe");
			if (!iframe) {
				iframe = document.createElement('iframe');
				iframe.style.display = 'none';
				iframe.setAttribute("id", "print-iframe");
				document.body.appendChild(iframe);
			}
			// 將print內容加入iframe  
			const content = this.$refs.printContent.innerHTML;
			const doc = iframe.contentDocument || iframe.contentWindow.document;
			doc.body.innerHTML = content;

			// 監聽所有圖片加載完成
			const images = doc.querySelectorAll('img');
			let imagesLoaded = 0;
			images.forEach(image => {
				image.onload = () => {
					imagesLoaded++;
					if (imagesLoaded === images.length) {
						// 加載完成後，添加樣式
						const style = document.createElement('style');
						if (this.printType === "SALES_NOTE"){
							style.innerHTML = `
								.container {
									font-size: 14px;
								}
								.tb_border {
									border: 2px black solid;
								}
								span {
									display: inline-block;
									border-bottom: 1px solid;
								}
								.container-2 {
									page-break-before: always;
								}   
							`;
						}else {
							style.innerHTML = `
								table, td {
									border: 1px solid black;
									border-collapse: collapse;
									padding: 4px;    
								}
								table{
									width:1000px;
								}
								ol{
									width:960px;
								}
								span{
									display: inline-block;
								}
								.container-2 {
									page-break-before: always;
								} 
							`;
						}
						doc.head.appendChild(style);

						// 呼叫print()
						iframe.contentWindow.print();

						// 呼叫完移除iframe
						setTimeout(() => {
							document.body.removeChild(iframe);
							this.printType = "";
						}, 50);
					}
				};
			});
		},
		getTels( tel ) {
			let tels = tel.split("-") ;
			let tel2 ;
			let result = [] ;
			if( tels.length > 1 ) {
				result.push(tels[0]);
				tel2 = tels[1];
			} else {
				result.push("");
				tel2 = tel ;
			}
			tels = tel2.split("#") ;
			if( tels.length > 1 ) {
				result.push(tels[0]);
				result.push(tels[1]);
			} else {
				result.push(tel2);
				result.push("");
			}
			return result ;
		}
	}
}	
</script>
