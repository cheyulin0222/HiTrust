<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		開通商家數交易統計報表
	</h4>
	<div class="row p-3 justify-content-md-center" ref = 'search' >
		<CardItem colMd="8">
			<DatePicker ref="datePicker" LabelVal = "交易時間" v-model="input.date" :limitTime = "true" limitType = "year"/>
		</CardItem>
	</div>
	<div class="row mt-2 mb-5 justify-content-md-center">
		<div class="col-md-4 p-2 ">
			<div class="m-0">
				<button type="button" @click.prevent="download"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">產生報表</span>
				</button>
			</div>
		</div>
	</div>	
</template>
<script>
import DataTable from '@/components/Table/PageDataTable.vue'
import moment from 'moment'
import DatePicker from '@/components/DatePicker.vue'
import Base from "@/components/RightViewBase" ;
import CardItem from "@/components/CardItem" ;
import Input from "@/components/Input" ;

let defaultSel = {
	date 		: [ moment().subtract(1, 'year'), moment() ] ,
}

export default {

	data(){
		let _this = this ;
		let input = Object.assign({},defaultSel) ;
		return {
			input : input ,
		}
	},
	extends : Base ,
	mounted() {

    },
	components : {
		DatePicker , CardItem
	},
	methods: {
		download(){
			let _this = this ;
            let date = this.input.date ;

            let param = {
                startTime 	:  date[0].format('yyyy-MM-DD') ,
                endTime 	:  date[1].format('yyyy-MM-DD')
            }

            _this.showLoading(true);
            
            this.ApiUtilSA.downloadExcel("download-store-transaction-statistic-report" , param ,  {
                accept      : 'application/octet-stream'
            })
            .finally(function () {
                _this.showLoading();
            });
		},
		doSelPage( page ){

		},
    	clear(){
    		this.input = Object.assign({},defaultSel) ;
    	},
	}
}	
</script>