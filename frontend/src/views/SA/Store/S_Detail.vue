<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		商店管理維護
	</h4>
	<div class="row" style="padding-left:15px;padding-right:15px;" >
		<div class="col-md-4 p-3" v-if="showTest">
			<div class="m-0">
				<button type="button" @click.prevent="testSSL"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">授權交易測試</span>
				</button>
			</div>
		</div>
	</div>
	<template v-if = "ready">
		<div class="p-3">
			<Card title = "商家基本資料設定">
				<Common :inputObj = "detailObj" :type = "type" ref = "COMMON"/>
			</Card>
		</div>

		<div class="p-3">
			<Card title = "商家收單行設定">
					<Acq :acqBank="detailObj.adapterId" :inputObj = "detailObj" :type = "type" ref = "Acq" />
			</Card>
		</div>
			
		<div class="p-3">
			<Card title = "商家加值設定">
				<Bouns :acqBank="detailObj.adapterId" :inputObj = "detailObj" :type = "type" ref = "Bouns" />
			</Card>
		</div>
			
		<div class="row mt-2 mb-5 " >
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="cancel"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">取消</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="save"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">確定</span>
					</button>
				</div>
			</div>
		</div>
	</template>
	<template v-else >
		<div class="row p-3 justify-content-md-center">
			<div class="col-md-6 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="cancel"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">回上一頁</span>
					</button>
				</div>
			</div>
		</div>
	</template>
</template>
<script>
import DataTable from '@/components/Table/PageDataTable.vue' ;
import SelectView from "@/components/Select" ;
import Mask from "@/components/Mask.vue"
import Input from "@/components/Input"
import Base from "@/components/RightViewBase" ;
import Common from "@/components/SA/Store/SData/S_Com" ;
import Acq from "@/components/SA/Store/SData/acq/Acq_Temp" ;
import Bouns from "@/components/SA/Store/SData/bou/Bou_Temp" ;
import Card from "@/components/Card" ;

export default {

	data(){
		return {
			detailObj 	: {} ,
			type 		: "EDIT" ,
			invalids 	: {},
			ready 		: false ,
			previousData: "" ,
		}
	},
	extends : Base ,
	components : {
		SelectView , Input , Common , Acq , Bouns , Card
	},
	computed: {
	    showTest(){
	    	let acqBank = this.detailObj.adapterId ;
	    	return acqBank && acqBank != "AP" && !acqBank.endsWith("UPOP")
	    }
	},
	created(){

		let merId = this.params["merId"] ;
		let _this = this ;
		
		_this.showLoading(true); 

		this.ApiUtilSA.get('get-store/' + merId )
		.then((res) => {
			if( res.success ) {
				_this.ready = true ;
				_this.detailObj = res.data ;
				_this.previousData = JSON.stringify(res.data) ;
			}
			_this.showLoading(); 
		});
	},
	mounted() {
    	
    },
	methods: {
		save(){
			let step1 = this.$refs.COMMON.checkData() ;
			let step2 = this.$refs.Acq.getData() ;
			let step3 = this.$refs.Bouns.getData() ;
			let _this = this ;

			if( step1 && step2 && step3 ) {
				let data = Object.assign({},this.detailObj,step2,step3);
				data["previousData"] = this.previousData ;
				
				_this.showLoading(true);
				
				this.upload( data.merId )
				.then( function( fileName ){
					if( fileName != "ERROR" ) {
						if( fileName != "" ) {
							data["fileName"] = fileName ;
						}
						return _this.ApiUtilSA.post("update-store" , data )
						.then((res) => {
							if( res.success && res.data.success) {
								_this.showModel({
					                title 	: "商店管理維護" ,
					                message : res.data.message ,
					                endFunc : function(){
					                	_this.cancel();
					                }
					            });
							}
						})
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
			}
		},
		async upload( merId ){
			let file = this.$refs.COMMON.getFile();
			let fileName = "" ;
			
			if( file ) {
				const form = new FormData()
				form.append('file', file)
				form.append('merId', merId );

				let _this = this ;
				
				await this.ApiUtilSA.post("upload-icon" , form )
				.then((res) => {
					if( res.success ) {
						fileName = res.data.fileName ;
					} else {
						fileName = "ERROR";
					}
					
				})
			}

			return fileName ;

		},
		cancel(){
			this.goNextPath("SA1302")
		},
		testSSL(){
			let TEST_PATH = this.env.getData().TEST_PATH ;
			window.open( TEST_PATH +"?storeid="+this.params["merId"]+"&authType=authSSL")
		}
	}
}	
</script>