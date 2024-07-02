<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<div class="p-3">
		<Card title = "速速付Token授權" :mainCenter="true">
			<CardItem colMd="7">
				<Input ID = "merId" LabelVal = "商店代號" v-model="input.merId" :invalids = "invalids"/>
			</CardItem>
		</Card>
	</div>
	<div class="row p-3 justify-content-md-center">
		<div class="col-md-6 order-1">
			<div class="m-0">
				<button type="button" @click.prevent="search"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">查詢</span>
				</button>
			</div>
		</div>
	</div>
	<table class="table table-hover m-0" style="border-bottom: 1px solid rgb(222, 226, 230);">
		<thead>
			<tr class="colTr">
				<th class="border border-info rounded-0 p-2 text-center alert-primary text-center col-1">
					
				</th>
				<th class="border border-info rounded-0 p-2 text-center alert-primary text-center col-4">
					Token註冊商代
				</th>
				<th class="border border-info rounded-0 p-2 text-center alert-primary text-center col-4">
					被授權商代
				</th>
				<th class="border border-info rounded-0 p-2 text-center alert-primary text-center col-3">
					狀態
				</th>
			</tr>
		</thead>
		<tbody>
			<template v-for="( data , index ) in dataRow" :key="getKey( data )" visisble="true">
				<tr>
					<td class="text-center">
						<template v-if=" data.new">
							<i class="fa img-fluid onactived pr-md-3 fa-minus-circle" @click = "minus(index)"/>
						</template>
					</td>
					<td class="text-center">
						{{ merId }}
					</td>
					<td>
						<template v-if=" !data.new ">
							{{ data.authMerId }}
						</template>
						<template v-else >
							<Input :ID = "'authMerId_' + index " v-model="data.authMerId" :invalids = "invalids"/>
						</template>
					</td>
					<td>
						<SelectView :options="statusOptions( data , index )" v-model="data.authStatus"/>
					</td>
				</tr>
			</template>
			<tr v-if=" merId != '' " >
				<td class="text-center">
					<i class="fa img-fluid onactived pr-md-3 fa-circle-plus" @click = "add"/>
				</td>
				<td class="text-center"></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
	<div class="row p-3 justify-content-md-center">
		<div class="col-md-6 order-1">
			<div class="m-0">
				<button type="button" @click.prevent="send"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">送出</span>
				</button>
			</div>
		</div>
	</div>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import SelectView from "@/components/Select" ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import { resetDefaultVal , isEmpty , genNonDuplicateID } from "@/utils/util" ;
import { getMerIDList } from "@/utils/dataListSA";

export default {
	data(){

		return {
			title 		: "速速付Token授權" ,
			input 		: this.getDefault() ,
			selOption 	: {
				merId : {
		    		ID 			: "merId" ,
		    		func 		: getMerIDList.bind(this , false ),
		    		selType 	: "dropdown" ,
		    		filter 		: true ,
		    	},
			} ,
			dataRow 	: [] ,
			invalids 	: {} ,
			merId 		: "" ,
			previousData: "" ,
		};
	},
	extends : Base ,
	computed: {

	},
	watch: {     
       
    },
	components : {
		Input , SelectView , Card , CardItem
	},
	methods: {
		search(){
			let _this = this ;
			let merId = this.input.merId ;
			let invalids = {} ;
			
			if( merId == "" ) {
				invalids.merId = "請輸入商代!" ;
			}

			this.invalids = invalids ;
			
			if( isEmpty(invalids) ) {
  				let _this = this ;
				
				_this.showLoading(true);

				this.ApiUtilSA.get('get-tokenpay-store-authorization-data/' + merId )
				.then((res) => {
					if( res.success ) {
						_this.merId = merId ;
						_this.dataRow = res.data
						_this.previousData = JSON.stringify({
							regMerId : merId ,
							saveList : res.data	
						});
					} else {
						_this.dataRow = [] ;
					}
					_this.showLoading(); 
				});

			} else {
				this.merId = "" ;
				this.dataRow = [] ;
			}
		},
		send(){
			let dataRow = this.dataRow ;
			let merId = this.merId ;
			let saveList = [] ;
			let invalids = {} ;
			let authMerIds = [] ;
			dataRow.forEach(function( data , index ){
				let authMerId = data.authMerId ;
				if( authMerId != "" ) {
					if( authMerIds.indexOf( authMerId ) > -1 )  {
						invalids['authMerId_'+index] = "資料重複!"
					} else {
						saveList.push({
							authMerId 		: authMerId,
      						authStatus 		: data.authStatus 	
						});
						authMerIds.push(authMerId);
					}
				}
			});

			this.invalids = invalids ;
			
			if( isEmpty(invalids) ) {
				let _this = this ;
				let param = {
					previousData	: this.previousData ,
					regMerId		: this.merId ,
					saveList		: saveList
				}

				_this.showLoading(true);
				
				this.ApiUtilSA.post("update-tokenpay-store-authorization-data" , param )
				.then((res) => {
					if( res.success && res.data.success) {
						this.showModel({
			                title : this.title ,
			                message : res.data.message ,
			                endFunc : function(){
			                	_this.search() ;
			                }
			            });
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
			}
		},
		getKey( data ){
			if( data.new ) {
				return data["key"]
			} else {
				return data.authMerId ;
			}
		},
		add(){
			let merId = this.merId ;
			if( merId == "" ){
				this.showModel({
                    type : "error" , 
                    title : this.title ,
                    message : "請先查詢"
                });
			} else {
				this.dataRow.push({
					key 		: genNonDuplicateID() ,
					regMerId 	: merId ,
					authMerId 	: "" ,
					authStatus	: "AUTH" ,
					new 		: true ,
				})
			}
		},
		minus( index ) {
			if( this.dataRow.length > index ){
				this.dataRow.splice( index , 1 ) ;
			}
		},
		merIdOptions( data , index ){
			return {
	    		ID 			: "merId_" + index ,
	    		func 		: getMerIDList.bind(this , false ),
	    		selType 	: "dropdown" ,
	    		filter 		: true ,
	    		defVal 		: data.authMerId ,
			}
		},
		statusOptions( data , index ){
			return {
	    		ID 			: "status_" + index ,
	    		func 		: [{
	    			text 	: "授權" ,
					val		: "AUTH" ,
	    		},
	    		{
	    			text 	: "取消授權" ,
					val		: "CANCEL" ,
	    		}],
	    		selType 	: "dropdown" ,
	    		defVal 		: data.authStatus ,
			}
		},
		getDefault(){
			return {
				merId 	: "" ,
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