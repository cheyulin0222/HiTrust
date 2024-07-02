<template>
	<VmjData :compObj = "compObj" :type = "type" ref="VmjData" :acqBank = "acqBank"/>
	<Risk :compObj = "compObj" :type = "type" ref="risk" :acqBank = "acqBank"/>
	<CardItem colMd="12">
		<span class="t-12" style="margin-bottom: 0.5rem; margin-right: 0.5rem; display: inline-block;">
			分期
		</span>
		<i class="fa img-fluid onactived pr-md-3 fa-circle-plus" @click = "addInstTenor"/>
		<i class="fa img-fluid onactived pr-md-3 fa-minus-circle" @click = "minusInstTenor"/>
		<div class="row">
			<template v-if="instTenorList2.length < 1 ">
				<div class=" order-md-0 col-md-3" style="height:48px"/>
			</template>
			<template v-else v-for=" ( object , index ) in instTenorList2 ">
				<div class=" order-md-0 col-md-3" :class="{'pt-2': index > 3 } ">
					<Input :ID = "'instTenor_' + index " v-model = "object.instTenor" :invalids = "invalids" :readOnly = "readOnly" />
				</div>
			</template>
		</div>
	</CardItem>
	<CardItem colMd="12" class="mt-3"/>
</template>
<script>
import Base from "@/components/SA/Store/SData/CompBase"
import VmjData from "@/components/SA/Store/SData/acq/VMJ_Data_Example"
import Risk from "@/components/SA/Store/SData/acq/Risk_Example"
import { isEmpty } from "@/utils/util";

export default {

	data(){
		let _this = this ;
		let compObj = this.editCopyObj() ;
		let instTenorList2 = [] ;
		compObj["instTenorList"].forEach(function( instTenor ){
			instTenorList2.push({
				instTenor : instTenor ,
			});
		})

		return {
			compObj 		: compObj ,
			instTenorList2 	: instTenorList2 ,
		}
	},
	extends : Base ,
	components : {
		VmjData , Risk
	},
	mounted() {

    },
	methods: {
		defaultCompObj(){
			return Object.assign(this.defaultComp() , {
				instTenorList	: [] ,
			})
		},
		checkData(){
			let VmjData = this.$refs.VmjData.checkData();
			let instTenorList = [] ;
			let invalids = {} ;
			let _this = this ;

			this.instTenorList2.forEach(function( obj , index ){
				let val = obj.instTenor.trim() ;
				if( val != "" ){
					if( isNaN( val ) ) {
						invalids['instTenor_'+index] = "必須為數字";
					} else {
						instTenorList.push(val);
					}
				}
			});
			
			this.invalids = invalids ;
			
			if( isEmpty( invalids ) && VmjData ) {
				this.compObj["instTenorList"] = instTenorList ;
				return true ;
			} else {
				return false ;
			}
		},
		addInstTenor(){
			this.instTenorList2.push({
				instTenor : "" ,
			});
		},
		minusInstTenor(){
			this.instTenorList2.pop();
		}
	}
}	
</script>