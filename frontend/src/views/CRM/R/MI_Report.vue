<template >
    <h4 class="sec-title font-weight-bold mt-md-4 mb-3">
        {{ $t('Merchant Information Report' ) }}
    </h4>
    <div class="row p-3 justify-content-center" ref = 'search' >
        <div class=" order-md-1 order-0 col-md-4">
            <label for="formfield01"
                class="t-12">日期區間</label>
            <div class="btn card ">
                <div class="card-body d-flex w-100 align-items-center text-left py-1 pl-0 pr-2">

                    <div class="reportrange w-100">
                        <div class="row">
                            <div class="col t-14">
                                {{ $filters.time( input.sdate ) }}
                                ~
                                {{ $filters.time( input.edate ) }}
                            </div>
                            <div clas storeNoTypes="col-auto ml-auto">
                                <img src="@/assets/img/ic-calendar.svg"
                                    class="img-fluid" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>        
    </div>
    <div class="row p-3 justify-content-md-center">
        <div class="col-md-6 order-1">
            <div class="m-0">
                <button type="button" @click.prevent="download"
                class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
                    <span class="mx-2">下載</span>                  
                </button>
            </div>
        </div>
    </div>
</template>
<script>
import { mount , unMount } from "@/utils/daterange";
import Edit from "@/components/Edit" ;
import SelectView from "@/components/Select" ;
import { isEmptyObject } from "@/utils/util";
import { getYearList , getMonthList } from "@/utils/dataList";
import  moment from 'moment'

export default {
	data(){
		return {
            AcquirerList : [] ,
            invalids     : {} ,
            input        : {
                sdate       : moment().subtract(7, 'days') ,
                edate       : moment().subtract('days') ,
            } ,
		}
		 
	},
	props : {
	},
	computed : {
	},
	watch : {        
    },
    components : {
        SelectView
    },
    extends : Edit ,
    methods : {
        download(){
            let invalids = {} ;
            let isInvalids = false ;
            let input = this.input ;
            let startDate = moment(input.sdate).format("YYYY-MM-DD");
            let endDate  = moment(input.edate).format("YYYY-MM-DD");
            let _this = this ;
            _this.showLoading(true);
            this.ApiUtil.downloadExcel(`/merchant-information-report/${startDate}/${endDate}`)
            .then(function(){
                _this.showLoading();
            });
        },
        renderDatePicker: function () {
            var self = this;
            let sel = $(this.$refs.search).find('.reportrange') ;
            if( sel.length > 0 ) {
                mount(sel, this.input.sdate, this.input.edate, function (s, e) {
                    self.input.sdate = s;
                    self.input.edate = e;
                });
            }
        },
    },
    beforeCreate(){

    },
    created(){

    },
    mounted(){
        this.renderDatePicker();
    },
    beforeUpdate(){

    },
    updated(){

    },
    //銷毀前
    beforeUnmount(){
    },
    unmounted() {
	},
}
</script> 