<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<div class="row p-3" ref = 'search' >
		<CardItem colMd="12">
			<Input ID = "creCard" LabelVal = "信用卡號" :input = "input" :invalids = "invalids" />
		</CardItem>
	</div>
	<div class="row mt-2 mb-5">
		<div class="col-md-4 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="clear"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">清除重設</span>
				</button>
			</div>
		</div>
		<div class="col-md-4  p-2">
			<div class="m-0">
				<button type="button" @click.prevent="search"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">查詢</span>
				</button>
			</div>
		</div>
		<div class="col-md-4 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="add"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">新增</span>
				</button>
			</div>
		</div>
	</div>
	<Mask ref = "Mask">
		<div class="p-3">
			<Card :title = " ( type === 'ADD' ? '新增' : '維護' ) + '測試卡號' ">
				<CardItem colMd="6">
					<Input ID = "creditCardNum" LabelVal = "信用卡號" :input = "cardObj" :invalids = "invalids" maxlength="16" :readOnly = "type === 'EDIT' " :required="true" />
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "expiry" LabelVal = "效期(YYMM)" :input = "cardObj" :invalids = "invalids" :required="true" maxlength="4" />
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "cvc2" LabelVal = "CVC2" :input = "cardObj" :invalids = "invalids" :required="true" maxlength="3" />
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "creditLimit" LabelVal = "CREDIT額度" :input = "cardObj" :invalids = "invalids" :required="true" maxlength="12" type="number" />
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "debitLimit" LabelVal = "DEBIT額度" :input = "cardObj" :invalids = "invalids" :required="true" maxlength="12" type="number" />
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "redemPoint" LabelVal = "紅利點數" :input = "cardObj" :invalids = "invalids" :required="true" maxlength="12" type="number" />
				</CardItem>
				<CardItem colMd="6">
					<SelectView :options="selOption.status" :invalidTxt="invalids.status" LabelVal = "使用狀態"
					v-model = "cardObj.status"  ref = "status"/>
				</CardItem>
			</Card>
		</div>
		<div class="row m-0 mt-2 mb-5" style="padding-left:15px;padding-right: 15px;">
			<div class="col-md-4 p-2" v-if="type === 'ADD' ">
				<div class="m-0">
					<button type="button" @click.prevent="clear2"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">清空欄位</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2" :class="{ 'offset-md-2': type === 'EDIT' }">
				<div class="m-0">
					<button type="button" @click.prevent="hideMask"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">取消</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="save"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">{{ type === "ADD" ? "新增" : "修改"}}</span>
					</button>
				</div>
			</div>
		</div>
	</Mask>
</template>
<script>
import SelectView from "@/components/Select" ;
import Mask from "@/components/Mask.vue" ;
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ; 
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import { mapGetters, mapState } from 'vuex';
import { isEmpty } from "@/utils/util" ;
import { getOpenOffList } from "@/utils/dataListSA" ;

export default {

	data(){
		let _this = this ;

		return {
			title 		: "測試卡號管理" ,
			type 		: "ADD" ,
			selOption 	: {
				status : {
		    		ID 			: "status" ,
		    		func 		: getOpenOffList.bind( this ),
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
			},
			input : {
				creCard : "" ,
			},
			previousData : "" ,
			cardObj : this.getDefaultCard() ,
			invalids : { 
				
			},
		}
	},
	extends : Base ,
	components : {
		SelectView , Mask , Input , Card , CardItem
	},
	computed: {
		...mapGetters( 'user' , {
        	user : "getUser",
      	}),
	},
	methods: {
		search(){
			let _this 		= this ;
			let invalids 	= {} ;
			let creCard 	= this.input.creCard ;

			if( creCard === "" ) {
				invalids.creCard = "請輸入測試卡號";
			}

			this.invalids = invalids ;

			if( isEmpty(invalids) ) {
				_this.showLoading(true);
			
				this.ApiUtilSA.get("get-test-card-data/" + creCard )
				.then((res) => {
					if( res.success ) {
						let cardObj = res.data ;
						_this.previousData = JSON.stringify(cardObj);
						_this.type = "EDIT";
						_this.setMaskObj(cardObj);
						_this.showMask();
					} else {
						_this.clear2() ;
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
			}			
		},
		add(){
			this.type = "ADD" ;
			this.setMaskObj(this.getDefaultCard());
			this.showMask();
		},
		setMaskObj( cardObj ){
			this.cardObj = cardObj ;
			this.$refs.status.setDefVal(cardObj.status);
		},
    	clear(){
    		this.input = {
    			creCard : "" ,
    		}
    	},
    	clear2(){
    		this.setMaskObj(this.getDefaultCard());
    	},
    	showMask(){
    		this.$refs.Mask.show();
    	},
    	hideMask(){
    		this.clear2();
    		this.$refs.Mask.hide();
    	},
    	save(){
    		let isADD = this.type === "ADD" ;
    		let msg = isADD ? "新增" : "修改"
    		let _this = this ;
    		let cardObj = this.cardObj ;
    		let invalids = {} ;
    		let creditCardNum 	= cardObj.creditCardNum.trim() ;
    		let	expiry 			= cardObj.expiry.trim() ;
    		let	cvc2 			= cardObj.cvc2.trim() ;
    		let creditLimit 	= cardObj.creditLimit ;
    		let redemPoint		= cardObj.redemPoint ;
    		let debitLimit 		= cardObj.debitLimit ;

    		if( creditCardNum == "" ) {
    			invalids.creditCardNum = "請輸入信用卡號" ;
    		}

    		if( expiry == "" ) {
    			invalids.expiry = "請輸入有效日期" ;
    		}

    		if( cvc2 == "" ) {
    			invalids.cvc2 = "請輸入後三碼" ;
    		} else if( isNaN( cvc2 ) ) {
    			invalids.cvc2 = "後三碼必須為數字" ;
    		}

    		if( creditLimit === "" ) {
    			invalids.creditLimit = "請輸入CREDIT金額" ;
    		} else if( isNaN( creditLimit ) ) {
    			invalids.creditLimit = "CREDIT金額必須為數字" ;
    		}

    		if( redemPoint === "" ) {
    			invalids.redemPoint = "請輸入REDEMPOINT金額" ;
    		} else if( isNaN( redemPoint ) ) {
    			invalids.redemPoint = "REDEMPOINT金額必須為數字" ;
    		}

    		if ( debitLimit === "" ) {
    			invalids.debitLimit = "請輸入DEBIT金額" ;
    		} else if( isNaN( debitLimit ) ) {
    			invalids.debitLimit = "DEBIT金額必須為數字" ;
    		}

    		this.invalids = invalids ;
    		if( isEmpty( invalids ) ) {
    			let param = {
    				testCardInfo : {
    					creditCardNum	: creditCardNum ,
					    expiry			: expiry ,
					    creditLimit		: creditLimit ,
					    debitLimit		: debitLimit ,
					    status 			: cardObj.status ,
					    redemPoint		: redemPoint ,
					    cvc2 			: cvc2 
    				}
				}
				let uri ;
				if( isADD ) {
					uri = "create-test-card-data" ;
				} else {
					uri = "update-test-card-data" ;
					param["previousData"] = this.previousData ;
				}
					
				this.showLoading( true );

				this.ApiUtilSA.post( uri , param )
				.then((res) => {
					if( res.success && res.data.success ) {
						this.showModel({
			                title : this.title ,
			                message : msg + "成功",
			                endFunc : function(){
			                	_this.hideMask();
			                }
			            });
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
    		}	
    	},
    	getDefaultCard(){
    		return {
    			creditCardNum 	: "" ,
    			expiry 			: "" ,
    			cvc2 			: "" ,
    			creditLimit 	: "1000000000" ,
    			debitLimit 		: "1000000000" ,
    			redemPoint 		: "1000000000" ,
    			status 			: "OPEN" ,
    		}
    	}
	}
}	
</script>