<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<template v-if=" type === '' ">
		<div class="p-3">
			<Card title = "iOS Config檔設定" :mainCenter="true">
				<CardItem colMd="7">
					<Input ID = "FILE" type = "file" LabelVal = "商店iOS Config檔" :invalids = "invalids" ref = "files"/>
				</CardItem>
				<CardItem colMd="7">
					<SelectView :options="selOption.sdkVer" :invalidTxt="invalids.sdkVer" LabelVal = "SDK版本" v-model = "input.sdkVer" ref = "sdkVer" />
				</CardItem>
			</Card>
		</div>
		<div class="row p-3 justify-content-md-center">
			<div class="col-md-6 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="updateFile"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">讀取設定檔</span>
					</button>
				</div>
			</div>
			<div class="col-md-6 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="add"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">新增設定檔</span>
					</button>
				</div>
			</div>
		</div>
	</template>
	<template v-else-if = " type === 'ADD' || type === 'EDIT' "  >
		<div class="p-3">
			<div class = "p-2 text-center m-3">
				<h4>商店iOS Config檔設定(SDK Version : {{input.sdkVer}})</h4>
			</div>
				
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table-data">
				<tr>
					<td class="border border-info rounded-0 p-2 text-center alert-primary text-center" style="width:100px;">
						商店代號
					</td>
					<td class="border border-info rounded-0 p-2 text-center alert-primary text-center">商店VereqURL</td>
					<td class="border border-info rounded-0 p-2 text-center alert-primary text-center">商店VeresURL</td>
					<td class="border border-info rounded-0 p-2 text-center alert-primary text-center">商店OtherURL</td>
					<td class="border border-info rounded-0 p-2 text-center alert-primary text-center" style="width:80px;">商店CVC2</td>
					<td class="border border-info rounded-0 p-2 text-center alert-primary text-center"  style="width:150px;">商店分期期數</td>
					<td class="border border-info rounded-0 p-2 text-center alert-primary text-center" style="display: none">AE閘道</td>
					<td class="border border-info rounded-0 p-2 text-center alert-primary text-center" style="width:80px;">
						支援速速付
					</td>
					<td class="border border-info rounded-0 p-2 text-center alert-primary text-center" style="width:80px;">
						商店幣別
					</td>
					<td class="border border-info rounded-0 p-2 text-center alert-primary text-center" style="width:100px;">
						收單行庫
					</td>
				</tr>
				<tr v-for="( row , index ) in dataRow" :key = "row.key">
					<td class="border border-info p-2" >
						<Input :ID = "'merId_' + index " v-model= "row.merId" :invalids = "invalids2" />
					</td>
					<td class="border border-info p-2">
						<input type="radio" :name = " 'selVereqURL' + index " value="P" v-model="row.selVereqURL" /> PROD URL<br/>
						<input type="radio" :name = " 'selVereqURL' + index " value="T" v-model="row.selVereqURL" /> TEST URL<br/>
						<input type="radio" :name = " 'selVereqURL' + index " value="U" v-model="row.selVereqURL" /> 自訂URL<br/>
						<Input :ID = " 'uVereqURL_' + index " v-model = "row.uVereqURL" 
						:readOnly=" row.selVereqURL != 'U' " :invalids = "invalids2"/>
					</td>
					<td class="border border-info p-2">
						<input type="radio" :name = " 'selVeresURL' + index " value="P" v-model="row.selVeresURL" /> PROD URL<br/>
						<input type="radio" :name = " 'selVeresURL' + index " value="T" v-model="row.selVeresURL" /> TEST URL<br/>
						<input type="radio" :name = " 'selVeresURL' + index " value="U" v-model="row.selVeresURL" />
						自訂URL<br/>
						<Input :ID = "'uVeresURL_'+index" v-model = "row.uVeresURL" 
						:readOnly=" row.selVeresURL != 'U' " :invalids = "invalids2"/>
					</td>
					<td class="border border-info p-2">
						<input type="radio" :name = " 'selOtherURL' + index " value="P" v-model="row.selOtherURL" /> PROD URL<br/>
						<input type="radio" :name = " 'selOtherURL' + index " value="T" v-model="row.selOtherURL" /> TEST URL<br/>
						<input type="radio" :name = " 'selOtherURL' + index " value="U" v-model="row.selOtherURL" /> 自訂URL<br/>
						<Input :ID = "'uOtherURL_' + index " v-model = "row.uOtherURL" 
						:readOnly=" row.selOtherURL != 'U' " :invalids = "invalids2"/>
					</td>
					<td class="border border-info p-2">
						<input type="checkbox" v-model="row.cvc2" value="true">支援
					</td>
					<td class="border border-info p-2">
						<input type="radio" :name = " 'selInstallment' + index " value="Y" v-model="row.selInstallment" /> 分期<br/>
						<template v-if=" row.selInstallment === 'Y' " >
							<input type="checkbox" name = "installment_3" value="3" v-model="row.installment" />3
							<input type="checkbox" name = "installment_3" value="6" v-model="row.installment" />6
							<input type="checkbox" name = "installment_3" value="9" v-model="row.installment" />9
							<input type="checkbox" name = "installment_3" value="12" v-model="row.installment" />12
							<input type="checkbox" name = "installment_3" value="18" v-model="row.installment" />18
							<input type="checkbox" name = "installment_3" value="24" v-model="row.installment" />24
							<input type="checkbox" name = "installment_3" value="30" v-model="row.installment" />30<br/>
						</template>
						<input type="radio" :name = " 'selInstallment' + index " value="NC" v-model="row.selInstallment" /> 不檢核<br/>
						<input type="radio" :name = " 'selInstallment' + index " value="N" v-model="row.selInstallment" /> 不支援分期
					</td>
					<td style="display: none" class="border border-info p-2">
						<input type="checkbox" v-model="row.IsAE" value="true">是
					</td>
					<td class="border border-info p-2">
						<input type="checkbox" v-model="row.trxToken" value="true">支援
					</td>
					<td class="border border-info p-2">
						<input type="radio" :name = " 'currency' + index " value="TWD" v-model="row.currency" /> 台幣<br/>
						<input type="radio" :name = " 'currency' + index " value="USD" v-model="row.currency" :disabled = "row.acquirBank != 'HSBC' " /> 美金
					</td>
					<td class="border border-info p-2">
						<SelectView :options="getAcqBankOption(row,index)" :invalidTxt="invalids.acquirBank" v-model = "row.acqBank"  ref = "acquirBank" />
					</td>
				</tr>
			</table>	
		</div>
		<div class="row p-3 justify-content-md-center">
			<div class="col-md-4 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="goBack"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">回上一頁</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="addRow"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">增加一組</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="save"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">修改送出</span>
					</button>
				</div>
			</div>
		</div>
	</template>
	<template v-else >
		<div class="p-3">
			<Card :title = " '商店iOS Config檔設定(SDK Version : ' + input.sdkVer + ')' " :mainCenter="true">
				<textarea style="width: 1200px; height: 200px;">{{ type === "PARSE" ? dataRow2 : genData }}</textarea>
			</Card>
		</div>
		<div class="row p-3 justify-content-md-center">
			<div class="col-md-4 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="goBack"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">回上一頁</span>
					</button>
				</div>
			</div>
			<template v-if=" type === 'PARSE'">
				<div class="col-md-4 order-1">
					<div class="m-0">
						<button type="button" @click.prevent="edit"
						class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
							<span class="mx-2">修改</span>
						</button>
					</div>
				</div>
			</template>
			<template v-else >
				<div class="col-md-4 order-1">
					<div class="m-0">
						<button type="button" @click.prevent="clear"
						class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
							<span class="mx-2">回iOS Config檔設定頁</span>
						</button>
					</div>
				</div>
				<div class="col-md-4 order-1">
					<div class="m-0">
						<button type="button" @click.prevent="downLoad"
						class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
							<span class="mx-2">下載檔案</span>
						</button>
					</div>
				</div>
			</template>
			
		</div>
	</template>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input"
import DataTable from '@/components/Table/PageDataTable.vue' ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import SelectView from "@/components/Select" ;
import { resetDefaultVal , isEmpty , genNonDuplicateID } from "@/utils/util" ;

export default {
	data(){
		let Obj = {
			title 		: "iOS Config檔設定" ,
			selOption 	: {
				sdkVer : {
					ID 			: "sdkVer" ,
		    		func 		: [{
						val 	: "1" ,
						text 	: "1"  
					},
					{
						val 	: "2" ,
						text 	: "2"  
					}] ,
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
				}
			},
			invalids : {} ,
			invalids2: {} ,
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
		Input , DataTable , SelectView , Card , CardItem
	},
	methods: {
		addRow(){
			this.dataRow.push({
				key 			: genNonDuplicateID() ,
				merId 			: "" ,
				selVereqURL 	: "U",
				uVereqURL 		: "" ,
				selVeresURL 	: "U",
				uVeresURL 		: "" ,
				selOtherURL 	: "U",
				uOtherURL 		: "" ,
				selInstallment 	: "NC" ,
				cvc2 			: "" ,
				installment 	: [] ,
				IsAE 			: "" ,
				trxToken 		: "" ,
				currency		: "TWD",
				acqBank 		: "CTCB",
			})
		},
		updateFile(){
			let invalids = {} ;
			let file = this.$refs.files.getFile();
			
			if( file.length == 0 ) {
				invalids.FILE = "請先上傳檔案" ;
			}

			this.invalids = invalids ;

			if( isEmpty( invalids ) ) {
				const form = new FormData();
				form.append('file', file[0] ) ;
				let _this = this ;

				_this.showLoading(true);
				
				this.ApiUtilSA.post("read-sdk-config-file" , form )
				.then((res) => {
					if( res.success ) {
						_this.type = "PARSE" ;
						_this.dataRow = _this.dataRow2 = res.data.parseAjson ;
					} else {
						_this.dataRow = [] ;
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
			}
		},
		save(){

			let invalids2 = {} ;
			let dataRow = this.dataRow ;
			let merIds = [] ;
			let parseAjson = [] ;

			dataRow.forEach(function( data , index ){
				let merId = data.merId ;
				if( merId != "" ) {
					if( merIds.indexOf( merId ) > -1 )  {
						invalids2['merId_'+index] = "商店代號重複"
					} else {
						merIds.push( merId );
					}

					if( data["selVereqURL"] === "U" && data["uVereqURL"] == "" ) {
						invalids2['uVereqURL_'+index] = "不可為空白"
					}

					if( data["selVeresURL"] === "U" && data["uVeresURL"] == "" ) {
						invalids2['uVeresURL_'+index] = "不可為空白"
					}

					if( data["selOtherURL"] === "U" && data["uOtherURL"] == ""  ) {
						invalids2['uOtherURL_'+index] = "不可為空白"
					}
				}		
			});

			this.invalids2 = invalids2 ;

			if( isEmpty( invalids2 ) ) {
				parseAjson = this.parseData( this.dataRow , "GEN" );
				if( parseAjson.length < 1 ) {
					this.showModel({
		                title 	: this.title ,
		                message : "請輸入資料" ,
		                type 	: "error"
		            });
				} else {
					let param = {
						parseAjson : parseAjson 
					}
					let _this = this ;

					_this.showLoading(true);
					
					this.ApiUtilSA.post("encrypt-sdk-config-file" , param )
					.then((res) => {
						if( res.success ) {
							_this.type = "GEN" ;
							_this.genData = res.data ;
						} else {
							_this.genData = {} ;
						}
					})
					.finally(function () {
			        	_this.showLoading();
					});
				}
			}
		},
		add(){
			this.type = "ADD" ;
		},
		edit(){
			this.type = "EDIT" ;
			this.dataRow = this.parseData( this.dataRow , "EDIT" );
		},
		parseData( dataRow , type ){
			
			let _this = this ;
			let result = [] ;
			if( type === 'EDIT' ) {

				dataRow.forEach(function( row ){
					let verifyReqURL = row["verifyReqURL"]
					let selVereqURL = _this.parseUrl( verifyReqURL ); 
					let uVereqURL = "" ;
					if( selVereqURL === "U" ) {
						uVereqURL = verifyReqURL ;
					}

					let verifyPaResURL = row["verifyPaResURL"] ;
					let selVeresURL = _this.parseUrl( verifyPaResURL );
					let uVeresURL = "" ;
					if( selVeresURL === "U" ) {
						uVeresURL = verifyPaResURL ;
					}

					let otherReqURL = row["otherReqURL"] ;
					let selOtherURL = _this.parseUrl( otherReqURL );
					let uOtherURL = "" ;
					if( selOtherURL === "U" ) {
						uOtherURL = otherReqURL ;
					}

					let selInstallment = "" ;
					let installment = row["installment"] ;
					if( installment == null ) {
						selInstallment = "NC" ;
						installment = [] ;
					} else if( installment[0] == 0 ) {
						selInstallment = "N" ;
						installment = [] ;
					} else {
						selInstallment = "Y" ;
					}

					result.push({
						key 			: genNonDuplicateID() ,
						merId 			: row["merId"] ,
						selVereqURL 	: selVereqURL ,
						uVereqURL		: uVereqURL ,
						selVeresURL		: selVeresURL ,
						uVeresURL		: uVeresURL ,
						selOtherURL		: selOtherURL ,
						uOtherURL		: uOtherURL ,
						installment 	: installment ,
						selInstallment 	: selInstallment ,
						IsAE 			: row["ae"] ,
						cvc2 			: row["cvc2"] ,
						trxToken 		: row["trxToken"] ,
						currency		: row["currency"] ,
						acqBank			: row["acqBank"] ,
					})
				});
				return result ;
			} else {
				let VerifyReqURL_P = "https://trustpay.hitrust.com.tw/TRUSTPAY/VerifyreqAPP" + this.input.sdkVer ;
				let VerifyReqURL_T = "https://testtrustpay.hitrust.com.tw/TRUSTPAY/VerifyreqAPP" + this.input.sdkVer ;
				let VerifyPaResURL_P = "https://trustpay.hitrust.com.tw/TRUSTPAY/VerifyPAResAPP" ;
				let VerifyPaResURL_T = "https://testtrustpay.hitrust.com.tw/TRUSTPAY/VerifyPAResAPP" ;
				let OtherReqURL_P = "https://trustpay.hitrust.com.tw/TRUSTPAY/TXServiceAPP" ;
				let OtherReqURL_T = "https://testtrustpay.hitrust.com.tw/TRUSTPAY/TXServiceAPP" ;
				
				dataRow.forEach(function( row ){

					let merId = row.merId ;
					if( merId != "" ) {
						let vereqURL ;
						let selVereqURL = row["selVereqURL"] ;
						if( selVereqURL === 'U' ) {
							vereqURL = row["uVereqURL"] ;
						} else if( selVereqURL === "T" ) {
							vereqURL = VerifyReqURL_T ;
						} else if( selVereqURL === "P" ) {
							vereqURL = VerifyReqURL_P ;
						}

						let veresURL ;
						let selVeresURL = row["selVeresURL"] ;
						if( selVeresURL === 'U' ) {
							veresURL = row["uVereqURL"] ;
						} else if( selVeresURL === "T" ) {
							veresURL = VerifyPaResURL_T ;
						} else if( selVeresURL === "P" ) {
							veresURL = VerifyPaResURL_P ;
						}

						let otherURL ;
						let selOtherURL = row["selOtherURL"] ;
						if( selOtherURL === 'U' ) {
							otherURL = row["uOtherURL"] ;
						} else if( selOtherURL === "T" ) {
							otherURL = OtherReqURL_T ;
						} else if( selOtherURL === "P" ) {
							otherURL = OtherReqURL_P ;
						}

						let selInstallment = row["selInstallment"] ;
						let installment = null ;
						if( selInstallment === 'N' ) {
							installment = [0];
						} else if( selInstallment === 'Y' ) {
							installment = row["installment"]
						}

						result.push({
							merId 			: merId ,
							verifyReqURL	: vereqURL ,
							verifyPaResURL	: veresURL ,
							otherReqURL		: otherURL ,
							installment 	: installment ,
							ae				: row["IsAE "] ,
							cvc2 			: row["cvc2"] ,
							trxToken 		: row["trxToken"] ,
							currency		: row["currency"] ,
							acqBank			: row["acqBank"] ,
						})
					}
				});

				return result ;
			}
		},
		parseUrl( url ){
			if( url.startsWith("https://trustpay.hitrust.com.tw") ) {
				return "P" ;
			} else if( url.startsWith("https://testtrustpay.hitrust.com.tw") ) {
				return "T" ;
			} else {
				return "U" ;
			}
		},
		goBack(){
			let type = this.type ;
			if( type === "ADD" || type === "PARSE") {
				this.clear();
			} else if( type === "EDIT" ) {
				this.type = "PARSE" ;
			} else if( type === "GEN" ) {
				this.type = "EDIT" ;
			}
			 
		},
		getDefault(){
			return {
				type 		: "" ,
				dataRow 	: [] ,
				dataRow2 	: [] ,
				genData 	: {} ,
				input 		: {
					sdkVer : "" ,
				}
			}
		},
		clear(){
			resetDefaultVal( this , this.getDefault()) ;
		},
		getAcqBankOption( row , index ){
			let acqBank = row.acqBank ;
			return {
				ID 			: "acquirBank_"+index  ,
	    		func 		: [{
					val 	: "CTCB" ,
					text 	: "CTCB"  
				},
				{
					val 	: "HSBC" ,
					text 	: "HSBC"  
				},
				{
					val 	: "TSB" ,
					text 	: "TSB"  
				},
				{
					val 	: "CITI" ,
					text 	: "CITI"  
				},
				{
					val 	: "SKB" ,
					text 	: "SKB"  
				},
				{
					val 	: "AMEX" ,
					text 	: "AMEX"  
				},
				{
					val 	: "SPB" ,
					text 	: "SPB"  
				},
				{
					val 	: "TCB" ,
					text 	: "TCB"  
				},
				{
					val 	: "ESB" ,
					text 	: "ESB"  
				},
				{
					val 	: "CUB" ,
					text 	: "CUB"  
				}] ,
	    		selType 	: "dropdown" ,
	    		defVal 		: acqBank 
			}
		},
		downLoad(){
			let _this = this ;
			
			let param = {
				buildTime 	: _this.genData.buildTime ,
				payload 	: _this.genData.payload ,
			}

			_this.showLoading(true);
			
			this.ApiUtilSA.downloadExcel2("download-sdk-config-file" , param ,  {
				
			})
			.finally(function () {
	        	_this.showLoading();
			});
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