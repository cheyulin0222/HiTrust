<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<template v-if="showResultType === '' ">
		<div class="p-3">
			<Card title = "匯入商家" :mainCenter="true">
				<CardItem colMd="7">
						<SelectView :options="selOption.qrPayMerchantType" :invalidTxt="invalids.qrPayMerchantType" LabelVal = "選擇特定商家類型" v-model = " input['qrPayMerchantType'] " ref = "qrPayMerchantType" />
				</CardItem>
				<CardItem colMd="7">
					<Input ID = "FILE" type = "file" LabelVal = "選擇您的csv檔案" :invalids = "invalids" ref = "files"/>
				</CardItem>
			</Card>
		</div>
		<div class="row p-3 justify-content-md-center">
			<div class="col-md-6 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="updateFile"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">上傳</span>
					</button>
				</div>
			</div>
		</div>
	</template>
	<template v-else >
		<div class="p-3">
			<template v-if="showResultType === 'empty' ">
				<span style="color:red;">上傳資料為空，請檢查上傳檔案</span>
			</template>
			<template v-else>
				<DataTable ref="table1" :dataRow="dataRow" :options ="tableOption"/>
			</template>
			<div class="row p-3 justify-content-md-center">
				<div class="col-md-6 order-1">
					<div class="m-0">
						<button type="button" @click.prevent="goBack"
						class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
							<span class="mx-2">重新上傳</span>
						</button>
					</div>
				</div>
			</div>
		</div>
	</template>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import DataTable from '@/components/Table/PageDataTable.vue' ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import SelectView from "@/components/Select" ;
import { resetDefaultVal , getFileExtension , isEmpty } from "@/utils/util" ;
import { getQrPayList } from "@/utils/dataListSA";

export default {
	data(){
		let obj = {
			title 		: "整批新增QRPay商家" ,
			tableOption : {
				colum : [
					{
						styleT : "width:5%" ,
						classT : "border border-info rounded-0 p-2 text-center alert-primary ",
                    	type : 'index',
                    	value : ['NO'] , 
                	},
                	{
                		styleT : "width:10%" ,
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['merId'],
						value : ['商家代碼'],
					},
                	{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['message'],
						value : ['結果'],
					}
				]
			},
			selOption : {
				qrPayMerchantType : {
					ID 			: "qrPayMerchantType" ,
		    		func 		: getQrPayList.bind(this , false ) ,
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
				}
			},
			...this.getDefault(),
		}

		return obj ;
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
		updateFile(){
			let invalids = {} ;
			let file = this.$refs.files.getFile();
			
			if( file.length == 0 ) {
				invalids.FILE = "請選擇匯入的csv檔";
			} else if( getFileExtension(file[0].name) != 'csv' ) {
				invalids.FILE = "只允許上傳csv檔";
			}

			this.invalids = invalids ;

			if( isEmpty(invalids) ) {
				const form = new FormData();
				
				form.append('merchantsFile', file[0] ) ;
				form.append('qrPayMerchantType', this.input["qrPayMerchantType"] ) ;

				let _this = this ;

				_this.showLoading(true);
				
				this.ApiUtilSA.post("create-multi-stores-qrpay" , form )
				.then((res) => {
					if( res.success ) {
						_this.showResultType = "result" ;
						_this.dataRow = res.data ;
					} else {
						_this.dataRow = [] ;
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
			}
		},
		goBack(){
			resetDefaultVal( this , this.getDefault()) ;
		},
		getDefault(){
			return {
				showResultType  : "" ,
				dataRow 		: [] ,
				invalids 		: {} ,
				input 			: {
					qrPayMerchantType 	: "" ,
				}
				
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