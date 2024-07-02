<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		整批開啟銀聯功能
	</h4>
	<template v-if="!showResult">
		<div class="p-3">
			<Card title = "匯入欲開通永豐銀聯商家" :mainCenter="true" :titleFunc="titleFunc">
				<CardItem colMd="6">
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
	<template v-if="showResult">
		<div class="p-3">
			<DataTable ref="table1" 
				:dataRow="dataRow"
				:options ="tableOption"/>
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
import { resetDefaultVal , getFileExtension , isEmpty } from "@/utils/util" ;

export default {
	data(){
		let obj = {
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
			titleFunc : {
				func : this.batchDesc.bind(this),
				text : "CSV檔欄位說明" ,
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
		Input , DataTable , Card , CardItem
	},
	methods: {
		batchDesc(){
			let env = this.env.getData();
			window.open("/" + env.AP_NAME + "/batchSPBUPOPDesc");
		},
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
				
				form.append('file', file[0] ) ;
				
				let _this = this ;

				_this.showLoading(true);
				
				this.ApiUtilSA.post("import-multi-spb-stores-upop" , form )
				.then((res) => {
					if( res.success ) {
						_this.showResult = true ;
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
				showResult  : false ,
				dataRow 	: [] ,
				invalids 	: {} ,
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