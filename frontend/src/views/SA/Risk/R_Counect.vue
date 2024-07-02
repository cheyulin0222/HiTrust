<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<div class="p-3">
		<Card title = "風險偵測連線ID管理">
			<CardItem colMd="6">
				<Input ID = "connectorId" LabelVal = "風險偵測連線ID" :input = "input" :invalids = "invalids" :required="true"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "apiToken" LabelVal = "API Token" :input = "input" :invalids = "invalids" :required="true"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "operatorId" LabelVal = "Operator ID" :input = "input" :invalids = "invalids" :required="true"/>
			</CardItem>
			<CardItem colMd="6"/>		
			<CardItem colMd="6">
				<SelectView :options="selOption.diiaFlag" :invalidTxt="invalids.diiaFlag" LabelVal = "DIIA總開關"
				@updateVal="updateVal" ref = "diiaFlag" :required="true"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "apiKey" LabelVal = "DIIA用API KEY" :input = "input" :invalids = "invalids" :required="true"/>
			</CardItem>
			<CardItem colMd="6">
				<SelectView :options="selOption.predictLearningFlag" :invalidTxt="invalids.predictLearningFlag" LabelVal = "PREDICT學習模式總開關"
				@updateVal="updateVal" ref = "predictLearningFlag" :required="true"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "predictConnectTimeout" LabelVal = "PREDICT連線逾時時間(ms)" :input = "input" :invalids = "invalids" :required="true"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "predictReadTimeout" LabelVal = "PREDICT等待逾時時間" :input = "input" :invalids = "invalids" :required="true"/>
			</CardItem>
		</Card>
	</div>
	<div class="row p-3 mb-5 justify-content-md-center">
		<div class="col-md-6 order-1">
			<div class="m-0">
				<button type="button" @click.prevent="save"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">確定</span>
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
import { getOpenOffList } from "@/utils/dataListSA" ;
import { isEmpty } from "@/utils/util" ;

export default {
	data(){
		return {
			title 		: "風險偵測連線ID管理" ,
			selOption 	: {
				diiaFlag : {
		    		ID 			: "diiaFlag" ,
		    		func 		: getOpenOffList ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
		    	predictLearningFlag : {
		    		ID 			: "predictLearningFlag" ,
		    		func 		: getOpenOffList ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
		    },
		    invalids: {} ,
		    input : {}
		}
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
		init(){
			let _this = this ;

			_this.showLoading(true);
			
			this.ApiUtilSA.get( "veri-id/get-connection-data" )
			.then((res) => {
				if( res.success ) {
					let data = this.input = res.data ;
					_this.input.previousData = JSON.stringify( data );
					_this.$refs.diiaFlag.setDefVal( data.diiaFlag );
					_this.$refs.predictLearningFlag.setDefVal( data.predictLearningFlag );
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		save(){
			let input 					= this.input ;
  			let previousData 			= input.previousData ;
  			let connectorId     		= input.connectorId ;
  			let apiToken				= input.apiToken ;
  			let apiKey					= input.apiKey ;
  			let operatorId				= input.operatorId ;
  			let diiaFlag				= input.diiaFlag ;
  			let predictLearningFlag		= input.predictLearningFlag ;
  			let predictConnectTimeout	= input.predictConnectTimeout ;
  			let predictReadTimeout		= input.predictReadTimeout ;
  			let invalids 				= {} ;

  			if( connectorId == "" ) {
				invalids.connectorId = "不可為空"
  			}

  			if( apiToken == "" ) {
				invalids.apiToken = "不可為空"
  			}

  			if( apiKey == "" ) {
				invalids.apiKey = "不可為空"
  			}

  			if( operatorId == "" ) {
				invalids.operatorId = "不可為空"
  			}

  			if( predictConnectTimeout == "" ) {
				invalids.predictConnectTimeout = "不可為空"
  			}

  			if( predictReadTimeout == "" ) {
				invalids.predictReadTimeout = "不可為空"
  			}

  			this.invalids = invalids ;

  			if( isEmpty( invalids ) ) {
  				let _this = this ;
  				
  				let param = {
					previousData			: previousData ,
					connectorId				: connectorId ,
					apiToken				: apiToken ,
					apiKey					: apiKey ,
					operatorId				: operatorId ,
					diiaFlag				: diiaFlag ,
					predictLearningFlag		: predictLearningFlag ,
					predictConnectTimeout	: predictConnectTimeout ,
					predictReadTimeout		: predictReadTimeout
				}

				_this.showLoading(true);
				
				this.ApiUtilSA.post("veri-id/update-connection-data" , param )
				.then((res) => {
					if( res.success && res.data.success ) {
						this.showModel({
			                title 	: this.title ,
			                message : "修改成功",
			            });
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
  			}			
		},
		updateVal( val , ID ){
    		this.input[ID] = val ;
    	},
	},
	mounted () {
		this.init();
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