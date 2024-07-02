<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		商家註冊ApplePay
	</h4>
	<template v-if="showType === '' ">
		<div class="p-3">
			<div class="row p-3 rounded bg-light m-0 justify-content-md-center">
				<div class=" order-md-1 order-0 col-md-6">
					<label for="merIdList"  class = "pl-2 mb-1 required">
						商家代碼 :(換行分隔多筆商家)
					</label>
					<textarea :class="{'is-invalid': !!invalids.merIdList}" type="text" v-model="merIdList" rows="6" wrap="soft" id = "merIdList" style="width:100%"></textarea>
					<div class="invalid-feedback">
	    				{{ invalids.merIdList }}
					</div>
				</div>
			</div>
		</div>
		<div class="row p-3 justify-content-md-center">
			<div class="col-md-6 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="save"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">註冊</span>
					</button>
				</div>
			</div>
		</div>
	</template>
	<template v-else>
		<div class="p-3">
			<template v-if=" showType === 'SUCCESS' ">
				<span style="color:red;">全部成功</span>
			</template>
			<template v-else-if=" showType === 'ERROR' ">
				<span style="color:red;">註冊失敗商家如下</span>
				<br/>
				<table class="table table-hover m-0" style="border-bottom: 1px solid rgb(222, 226, 230);">
				<thead>
					<tr>
						<th class="border border-info rounded-0 p-2 text-center alert-primary text-center col-1">
							項次
						</th>
						<th class="border border-info rounded-0 p-2 text-center alert-primary text-center col-11">
							商代
						</th>
					</tr>
				</thead>
				<tbody>
					<template v-for="( data , index ) in dataRow" :key="data" visisble="true">
						<tr>
							<td class="text-center">
								{{ index + 1 }}
							</td>
							<td class="text-center">
								{{ data }}
							</td>
						</tr>
					</template>
				</tbody>
			</table>
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
import Input from "@/components/Input"
import DataTable from '@/components/Table/PageDataTable.vue'
import { resetDefaultVal , isEmpty } from "@/utils/util"

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
						key : ['errMerId'],
						value : ['商家代碼'],
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
		Input , DataTable
	},
	methods: {
		save(){
			let _this 		= this ;
			let invalids 	= {} ;
			let merIdList 	= this.merIdList.trim() ;
			

			if( merIdList == "" ){
				invalids.merIdList = "請輸入商家代碼" ;
			}

			this.invalids = invalids ;

			if( isEmpty( invalids ) ) {
				let param = {
					merIdList 	: merIdList.split("\n") ,
				}

				this.showLoading(true);
				
        		this.ApiUtilSA.post( "register-applepay-store-data" , param )
        		.then((res) => {
					if( res.success ) {
						let data = res.data ;
						if( data.success ) {
							_this.showType = "SUCCESS" ;
							_this.dataRow = [] ;
						} else {
							_this.showType = "ERROR" ;
							_this.dataRow = data.failMerIdList ;
						}
					} else {
						_this.showType = "" ;
						_this.dataRow = [] ;
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
			}
		},
		goBack(){
			resetDefaultVal( this , this.getDefault())
		},
		getDefault(){
			return {
				merIdList 	: "" ,
				showType	: "" ,
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