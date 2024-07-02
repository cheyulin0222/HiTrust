<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		新增商家
	</h4>
	<div class="p-3 font-weight-bold" style="height:40px">
		<template v-if="step != 1">
			<span class="t-18">收單行: {{acqBank}}&nbsp;&nbsp;商代: {{addObj.merId}}</span>
		</template>
	</div>
	<div v-show = " step === 1 || type === 'READONLY' ">
		<div class="p-3">
			<Card title = "第一步驟: 商家基本資料設定" >
				<Common :inputObj = "addObj" :type = "type" ref = "COMMON" />
			</Card>
		</div>
		<div class="row mt-2 mb-5 justify-content-md-center" v-if=" type != 'READONLY' ">
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="step1"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">下一步</span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div v-show= " step === 2 || type === 'READONLY' ">
		<div class="p-3">
			<Card title = "第二步驟: 商家收單行設定" >
				<Acq v-if = "acqBank != '' " :acqBank="acqBank" :inputObj = "addObj" :type = "type" ref = "Acq" :key = "acqBank"/>
			</Card>
		</div>
		<div class="row mt-2 mb-5 " v-show=" type != 'READONLY' " >
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="goBack1"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">上一步</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="step2"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">下一步</span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div v-show= "step === 3 || type === 'READONLY' ">
		<div class="p-3">
			<Card title = "第三步驟: 商家加值設定" >
				<Bouns v-if = "acqBank != '' " :acqBank="acqBank" :inputObj = "addObj" :type = "type" ref = "Bouns" :key = "acqBank"/>
			</Card>
		</div>
		<div class="row mt-2 mb-5 " v-if=" type != 'READONLY' ">
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="goBack2"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">上一步</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="step3"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">下一步</span>
					</button>
				</div>
			</div>
		</div>
		<div class="row mt-2 mb-5 " v-else >
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="goBack3"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">上一步</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="save"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">儲存</span>
					</button>
				</div>
			</div>
		</div>
	</div>	
</template>
<script>
import DataTable from '@/components/Table/PageDataTable.vue'
import SelectView from "@/components/Select" ;
import Mask from "@/components/Mask.vue"
import Input from "@/components/Input"
import Base from "@/components/RightViewBase" ;
import Common from "@/components/SA/Store/SData/S_Com" ;
import Acq from "@/components/SA/Store/SData/acq/Acq_Temp" ;
import Bouns from "@/components/SA/Store/SData/bou/Bou_Temp" ;
import Card from "@/components/Card" ;
import { resetDefaultVal } from "@/utils/util"

export default {

	data(){
		return this.getDefault();
	},
	extends : Base ,
	components : {
		SelectView , Input , Common , Acq , Bouns , Card
	},
	computed: {
	    
	},
	methods: {
		step1(){
			if( this.$refs.COMMON.checkData() ) {
				if( this.acqBank != this.addObj.adapterId ) {
					this.acqBank = this.addObj.adapterId ;
					this.acqObj = {} ;
				}
				
				window.scroll( 0 , 0 ) ;
				let _this = this ;
				let merId = this.addObj.merId ;

				_this.showLoading(true);
				
				this.ApiUtilSA.get("check-merId/" + merId )
				.then((res) => {
					if( res.success ) {
						let data = res.data ;
						if( data.isRepeat ) {
							_this.$refs.COMMON.setinvalids({
								merId : "商代重複" ,
							})
						} else {
							return _this.upload()
							.then(( fileName ) => {
								if( fileName != "ERROR" ) {
									this.addObj["fileName"] = fileName ;
									this.step = 2 ;
								}
							})
						}
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
			}
			
		},
		step2(){			
			let data = this.$refs.Acq.getData() ;
			if( data ) {
				if( this.acqBank == "AP") {
					let barcode2MerId = data["barcode2MerId"] ;
					let _this = this ;
					
					_this.showLoading(true);
				
					this.ApiUtilSA.get("check-barcode2MerId/" + barcode2MerId )
					.then((res) => {
						if( res.success ) {
							let resData = res.data ;
							if( resData.isRepeat ) {
								alert("Barcode2MerId重複");
							} else {
								window.scroll( 0 , 0 ) ;
								_this.acqObj = data ;
								_this.step = 3 ;
							}
						}
					})
					.finally(function () {
			        	_this.showLoading();
					});
				} else {
					window.scroll( 0 , 0 ) ;
					this.acqObj = data ;
					this.step = 3 ;
				}	
			}
			
		},
		step3(){
			let data = this.$refs.Bouns.getData() ;
			if( data ) {
				window.scroll( 0 , 0 ) ;
				this.bouObj = data ;
				this.type = "READONLY";
			}
		},
		async upload(){
			let file = this.$refs.COMMON.getFile();
			let merId = this.addObj.merId ;
			let fileName = "HiTRUST.svg" ;
			
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
		save(){
			let data = Object.assign(this.addObj, this.acqObj,this.bouObj);
			let _this = this ;

			_this.showLoading(true);
			
			this.ApiUtilSA.post("create-store" , data )
			.then((res) => {
				if( res.success && res.data.success) {
					this.showModel({
		                title 	: this.title ,
		                message : res.data.message ,
		                endFunc : function(){
		                	vue.reloadApp();
		                	window.scroll( 0 , 0 ) ;
		                	//resetDefaultVal( _this , _this.getDefault())
		                }
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		goBack1(){
			this.step = 1 ;
		},
		goBack2(){
			this.step = 2 ;
		},
		goBack3(){
			this.type = "ADD";
		},
		getDefault(){
			return {
				step 	: 1 ,
				addObj 	: {} ,
				acqObj 	: {} ,
				bouObj  : {} ,
				type 	: "ADD" ,
				acqBank : "" ,
			}
		}
	}
}	
</script>