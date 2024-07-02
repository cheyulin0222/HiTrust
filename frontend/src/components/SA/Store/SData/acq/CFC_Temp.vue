<template>
	<Risk :compObj = "compObj" :type = "type" ref="risk" :acqBank = "acqBank"/>
	<CardItem colMd="12" class="t-18">
		<hr class="hr-store">
		中租
	</CardItem>
	<CardItem colMd="4">
		<Input ID = "macKeyId" LabelVal = "API連線金鑰" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" :required="true" maxlength="256"/>
	</CardItem>
	<CardItem colMd="4">
		<Input ID = "cfcStoreId" LabelVal = "商店代號" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
	</CardItem>
	<CardItem colMd="4">
		<Input ID = "cfcSubStoreId" LabelVal = "子商店代號" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
	</CardItem>
	<CardItem colMd="4">
		<Input ID = "cfcSubStoreName" LabelVal = "子商店名稱" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
	</CardItem>
	<CardItem colMd="4">
		<Input ID = "cfcTopVenderId" LabelVal = "總公司代號" :input = "compObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
	</CardItem>
	<CardItem colMd="4"/>
	<CardItem colMd="12">
		<span class="t-12" style="margin-bottom: 0.5rem; margin-right: 0.5rem; display: inline-block;">
			手續費負擔對象
		</span>
		<i class="fa img-fluid onactived pr-md-3 fa-circle-plus" @click = "addInstal"/>
		<i class="fa img-fluid onactived pr-md-3 fa-minus-circle" @click = "minusInstal"/>
		<template v-for=" ( object , index ) in instalList ">
			<div class="row " :class="{'pt-2': index > 0 }">
				<div class="order-md-1 d-flex align-items-center pb-2" style="padding-left:15px;" >
					<span>第</span>
				</div>
				<div class=" order-md-1 col-md-2 pb-2">
					<Input ID = "installment" v-model = "object.installment"  :invalids = "invalids" :readOnly = "readOnly" :IDRandom = "required" />
				</div>
				<div class=" order-md-1 col-md-1 text-center d-flex align-items-center pb-2 " >
					<span>期，由</span>
				</div>
				<div class=" order-md-1 col-md-2 pb-2">
					<SelectView :options="getFreeTypeOption(index,object)" :invalidTxt="invalids.freeType"  v-model = "object.freePay" :ref = "'freeType'+index" :readOnly = "readOnly"/>
				</div>
				<div class="order-md-1 d-flex align-items-center pb-2" >
					<span> 負擔，利率 </span>
				</div>
				<div class=" order-md-1 col-md-2 pb-2">
					<Input ID = "ratio" v-model = "object.radio" :invalids = "invalids" :readOnly = "readOnly" :IDRandom = "required" />
				</div>
				<div class="order-md-1 d-flex align-items-center pb-2" >
					<span>%</span>
				</div>
			</div>
		</template>
	</CardItem>
	<CardItem colMd="12" class="mt-3"/>
</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase" ;
import Risk from "@/components/SA/Store/SData/acq/Risk_Example" ;
import { getCFCFreeTypeList } from "@/utils/dataListSA";
import { isEmpty , parseDouble , parseInt} from "@/utils/util";

export default {

	data(){
		let _this = this ;
		let compObj = this.editCopyObj() ;
		/*
		let instalList = [] ;
		if( compObj.cfcFreeType != "" ) {
			instalList = JSON.parse(compObj.cfcFreeType);
			instalList.forEach(function( item ){
				item.r = item.r.toString();
				item.i = item.i.toString();
			});
		}
		return {
			compObj 	: compObj ,
			instalList 	: instalList ,
		}*/
		
		return {
			compObj 	: compObj ,
			instalList 	: compObj.cfcFreeTypeList ,
		}
	},
	extends : Base ,
	components : {
		Risk
	},
	mounted() {

    },
	methods: {
		defaultCompObj(){
			return Object.assign(this.defaultComp() , {
				macKeyId 		: "" ,
				cfcStoreId 		: "" ,
				cfcSubStoreId 	: "" ,
				cfcSubStoreName : "" ,
				cfcTopVenderId 	: "" ,
				cfcFreeType 	: "" ,
				cfcFreeTypeList : [] ,
			});
		},
		checkData(){
			
			return true ;
		},
		getFreeTypeOption( index , obj ){
			return {
	    		ID 			: "freeType_"+index  ,
	    		func 		: getCFCFreeTypeList ,
	    		selType 	: "dropdown" ,
	    		setFirst 	: true ,
	    		defVal 		: obj.freePay ,
	    	}
		},
		addInstal(){
			this["instalList"].push({
    			installment : "" ,
    			freePay : "MERCHANT" ,
    			radio : "0" ,
    		});
		},
		minusInstal(){
			this["instalList"].pop();
		},
		checkData(){
			let invalids = {} ;
			let compObj = this.compObj ;
			let macKeyId =  compObj["macKeyId"].trim();
			
			if( macKeyId === "" ) {
				invalids["macKeyId"] = "請輸入API連線金鑰" ;
			} else {
				compObj["macKeyId"] = macKeyId ;
			}
			this.invalids = invalids ;
			
			if( isEmpty(invalids) ) {
				
				/*
				let arr = [] ;
				this.instalList.forEach(function( item , index ){
					let period = item.r.trim() ;
					let installment = item.i.trim() ;
					if( period != "" && installment != "" ) {
						arr.push({
							i : parseInt(installment) ,
			    			t : item.t.trim() ,
			    			r : parseDouble(period) ,
						}) ;

					}
				});
				let cfcFreeType = "" ;
				if( arr.length > 0 ) {
					cfcFreeType = JSON.stringify( arr ) ;
				}
				compObj["cfcFreeType"] = cfcFreeType ;*/

				return true ;
			} else {
				return false ;
			}
		}
	}
}	
</script>