<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		整批SSL授權測試
	</h4>
	<template v-if="!showResult">
		<div class="p-3">
			<Card title = "整批SSL授權測試" :mainCenter="true">
				<CardItem colMd="7">
					<label for="merIdList" class="pl-2 mb-1 required">
						商家代碼 :(換行分隔多筆商家)
					</label>
					<textarea :class="{'is-invalid': !!invalids.merIdList}" type="text" v-model="input.merIdList" rows="6" style="width:100%" wrap="soft" id = "merIdList"></textarea>
					<div class="invalid-feedback">
	    				{{ invalids.merIdList }}
					</div>
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "transAmount" :input = "input" LabelVal = "交易金額" :invalids = "invalids" :required = "true" />
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "ordPrefix" :input = "input" LabelVal = "訂單前墜 Order prefix" :invalids = "invalids"/>
				</CardItem>
				<CardItem colMd="4">
					<Input ID = "pan" :input = "input" LabelVal = "信用卡號" :invalids = "invalids" :required = "true" />
				</CardItem>
				<CardItem colMd="4">
					<Input ID = "expireDate" :input = "input" LabelVal = "信用卡到期日（YYMM）" :invalids = "invalids" 
					:required = "true" />
				</CardItem>
				<CardItem colMd="4">
					<Input ID = "cvv" :input = "input" LabelVal = "CVV2" :invalids = "invalids" :required = "true" />
				</CardItem>
			</Card>
		</div>
		<div class="row p-3 justify-content-md-center m-0">
			<div class="col-md-4 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="visaCard"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">VisaCard</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="masterCard"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">MasterCard</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="clear"
					class="btn btn-secondary  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">Clear</span>
					</button>
				</div>
			</div>
			<div class="col-md-6 order-1 pt-3">
				<div class="m-0">
					<button type="button" @click.prevent="send"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">執行</span>
					</button>
				</div>
			</div>
		</div>
	</template>
	<template v-else>
		<div class="p-3">
			<DataTable ref="table1" :dataRow="dataRow" :options ="tableOption"/>
		</div>
	</template>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import DataTable from '@/components/Table/PageDataTable.vue' ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import { resetDefaultVal , isEmpty } from "@/utils/util" ;

export default {
	data(){
		let Obj = {
			tableOption : {
				colum : [
					{
						styleT : "width:5%" ,
						classT : "border border-info rounded-0 p-2 text-center alert-primary ",
                    	type : 'index',
                    	value : ['NO'] , 
                	},
                	{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['merId'],
						value : ['商家'],
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['orderNum'],
						value : ['訂單編號'],
					},
                	{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['message'],
						value : ['結果'],
					}
				]
			},
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
		Input , DataTable , Card , CardItem
	},
	methods: {
		init(){
			this.visaCard();
		},
		send(){
			let _this 		= this ;
			let input 		= this.input ;
			let invalids 	= {} ;
			let merIdList 	= input.merIdList.trim() ;
			let transAmount	= input.transAmount.trim() ;
			let orderPrdfix	= input.ordPrefix.trim() ;
			let pan			= input.pan.trim() ;
			let expiryDate	= input.expireDate.trim() ;
			let cvv 		= input.cvv.trim() ;

			if( merIdList == "" ){
				invalids.merIdList = "請輸入商家代碼" ;
			}

			if( pan == "" ){
				invalids.pan = "請輸入信用卡號" ;
			} else if( isNaN( pan ) ){
				invalids.pan = "信用卡卡號 請輸入數字" ;
			}
			
			if( expiryDate == "" ){
				invalids.expireDate = "請輸入信用卡到期日" ;
			} else if( isNaN( expiryDate ) ){
				invalids.expireDate = "信用卡到期日請輸入數字" ;
			}

			if( cvv == "" ){
				invalids.cvv = "請輸入信用卡到期日" ;
			} else if( isNaN( cvv ) ){
				invalids.cvv = "信用卡到期日請輸入數字" ;
			}

			if( transAmount == "" ){
				invalids.transAmount = "請輸入金額" ;
			} else if( isNaN( transAmount ) ){
				invalids.transAmount = "金額請輸入數字" ;
			}

			this.invalids = invalids ;
			
			if( isEmpty( invalids ) ) {
				let param = {
					merIdList 	: merIdList.split("\n") ,
					transAmount	: transAmount ,
					orderPrdfix	: orderPrdfix ,
					pan			: pan ,
					expiryDate	: expiryDate ,
					cvv 		: cvv ,
				}

				this.showLoading(true);
				
        		this.ApiUtilSA.post( "/test-ssl-auth-transaction" , param )
        		.then((res) => {
					if( res.success ) {
						this.showResult = true ;
						_this.dataRow = res.data ;
					} else {
						this.showResult = false ;
						_this.dataRow = [] ;
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
			}
			
		},
		clear(){
			resetDefaultVal( this , this.getDefault())
		},
		getDefault(){
			return {
				input : {
					merIdList 	: "" ,
					transAmount : "100" ,
					pan 		: "" ,
					expireDate	: "" ,
					cvv 		: "" ,
					ordPrefix 	: "HTTS" ,
				},
				dataRow 	: [] ,
				invalids 	: {} ,
				showResult  : false ,
			}
		},
		visaCard(){
			this.input.pan = "4242424242424242";
			this.input.expireDate = "2312";
			this.input.cvv = "123";
		},
		masterCard(){
			this.input.pan = "5454545454545454";
			this.input.expireDate = "2312";
			this.input.cvv = "123";
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