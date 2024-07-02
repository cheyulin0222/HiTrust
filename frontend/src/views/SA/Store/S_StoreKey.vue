<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		存入商家金鑰與設定檔
	</h4>
	<template v-if="showType === '' ">
		<div class="p-3">
			<Card title = "存入商家金鑰與設定檔" :mainCenter="true">
				<CardItem colMd="6">
					<label for="merIdList" class="pl-2 mb-1 required">
						商家代碼 :(換行分隔多筆商家)
					</label>
					<textarea :class="{'is-invalid': !!invalids.merIdList}" type="text" v-model="merIdList" rows="6" wrap="soft" id = "merIdList" style="width:100%"></textarea>
					<div class="invalid-feedback">
	    				{{ invalids.merIdList }}
	    			</div>
				</CardItem>
			</Card>
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
				<span style="color:red;">匯入成功</span>
			</template>
			<template v-else-if=" showType === 'ERROR' ">
				<span style="color:red;">匯入失敗</span>
				<br/>
				<DataTable ref="table1" 
				:dataRow="dataRow"
				:options ="tableOption"/>
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
import { resetDefaultVal } from "@/utils/util"

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
		Input , DataTable , Card , CardItem
	},
	methods: {
		save(){
			if( this.merIdList === '' ) {
				this.showType = "SUCCESS";
			} else {
				this.showType = "ERROR";
				this.dataRow = [{
					errMerId : "88000",
				},{
					errMerId : "88001",
				},{
					errMerId : "88002",
				},]
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