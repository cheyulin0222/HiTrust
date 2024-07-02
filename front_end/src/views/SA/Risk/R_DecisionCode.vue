<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<template v-if="showType === 'SEARCH' ">
		<div class="row p-3 justify-content-md-center" ref = 'search' >
			<CardItem colMd="7">
				<SelectView :options="selOption.instituteId" :invalidTxt="invalids.instituteId" LabelVal = "機構 ID" v-model ="input.instituteId"	ref = "instituteId"/>
			</CardItem>
			<CardItem colMd="7" v-if = " input.instituteId === 'NEW' ">
				<Input v-model="input.newInstituteId" LabelVal = "新增機構 ID" :invalids = "invalids" ID = "newInstituteId" />
			</CardItem>
		</div>
		<div class="row p-3 justify-content-md-center">
			<div class="col-md-6 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="search"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">
						{{ input.instituteId === 'NEW' ? '新增' : '查詢資料'}}
					</span>
					</button>
				</div>
			</div>
		</div>
	</template>
	<template v-else>
		<div class="p-3">
			<Card :title = " ( input.instituteId === 'NEW' ? '新增' : '修改' ) + '風險決策結果' " :mainCenter="true">
				<CardItem colMd="7">
					<Input ID = "instituteId" LabelVal = "機構 ID" :input = "editObj" :readOnly = "true" :IDRandom="true"/>
				</CardItem>
				<CardItem colMd="7">
					<span class="t-12" style="margin-bottom: 0.5rem; margin-right: 0.5rem; display: inline-block;">
						決策結果代碼
					</span>
					<i class="fa img-fluid onactived pr-md-3 fa-circle-plus" @click = "addCode"/>
					<i class="fa img-fluid onactived pr-md-3 fa-minus-circle" @click = "minusCode"/>
					<div :key=" 'newCodeElement_' + index" v-for =" ( item , index ) in editObj.decisionCodes" :class="{'mt-3': index > 0}">
						<Input v-model = "editObj.decisionCodes[index]" placeholder="若需要刪除該決策結果，留空即可" />
					</div>
				</CardItem>
			</Card>
		</div>
		<div class="row mt-2 mb-5">
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="save"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">儲存資料</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="goBack"
					class="btn btn-secondary  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">返回查詢</span>
					</button>
				</div>
			</div>
		</div>
	</template>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import SelectView from "@/components/Select" ;
import DataTable from '@/components/Table/PageDataTable.vue' ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import { resetDefaultVal , isEmpty } from "@/utils/util"
import { getInstituteList } from "@/utils/dataListSA"

export default {
	data(){
		let Obj = {
			title 		: "風險決策結果管理" ,
			invalids 	: {} ,
			selOption 	: {
				instituteId : {
					ID 			: "instituteId" ,
					func 		: getInstituteList.bind(this, "CREATE" ) ,
					selType 	: "dropdown" ,
					addNullText : true ,
				}
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
		Input , SelectView , DataTable , Card , CardItem
	},
	methods: {
		search(){
			let invalids = {} ;
			let instituteId = this.input.instituteId ; 
			let newInstituteId = this.input.newInstituteId ;
			if( instituteId === '' ) {
				invalids.instituteId = "機構 ID 必填";
			} else if( instituteId === 'NEW' ) {
				if( newInstituteId === '' ) {
					invalids.newInstituteId = "新增機構 ID 必填";
				} else {
					instituteId = newInstituteId ;
				}
				
			}
			this.invalids = invalids ;
			if( isEmpty(invalids) ) {
				let _this = this ;

				_this.showLoading(true);
				
				this.ApiUtilSA.get( "veri-id/get-decision-codes/" + instituteId )
				.then((res) => {
					if( res.success ) {
						let data = res.data ;
						let editObj = {
							instituteId 	: instituteId ,
							decisionCodes 	: data.decisionCodes 
						}

						editObj["previousData"] = JSON.stringify( editObj ) ;

						_this.editObj = editObj ;

						_this.showType = "EDIT" ;
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});

			}
			
		},
		goBack(){
			resetDefaultVal( this , this.getDefault());
			this.dataRow = [] ;
		},
		getDefault(){
			return {
				input : {
					instituteId 	: "" ,
					newInstituteId 	: "" ,
				} ,
				editObj : {
					instituteId 	: "" ,
					decisionCodes 	: [] ,
					previousData 	: "" ,
				},
				showType 	: 'SEARCH' ,
			}
		},
    	save (){
    		let _this = this ;
			
			this.showLoading(true);
			
			this.ApiUtilSA.post("veri-id/update-decision-codes" , this.editObj )
			.then((res) => {
				if( res.success && res.data.success ) {
					this.showModel({
		                title 	: this.title ,
		                message : res.data.message ,
		                endFunc : function(){
		                	_this.search();
		                }
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
    		
    	},
    	addCode(){
    		this.editObj.decisionCodes.push("");
    	},
    	minusCode(){
    		this.editObj.decisionCodes.pop();
    	},
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