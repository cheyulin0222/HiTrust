<template >
    <h4 class="sec-title font-weight-bold mt-md-4 mb-3">
        報表(所有商家)
    </h4>
    <div class="row p-3 justify-content-center" ref = 'search' >
        <div class=" order-md-1 order-0 col-md-4">
            <label for="year" class="t-12">年</label>
            <SelectView :options="selOption.year" :invalidTxt="invalids.year"
            @updateVal="updateVal" ref = "year"/>
        </div>
        <div class=" order-md-1 order-0 col-md-4">
            <label for="month" class="t-12">月</label>
            <SelectView :options="selOption.month" :invalidTxt="invalids.month"
            @updateVal="updateVal" ref = "month"/>
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
import Edit from "@/components/Edit" ;
import SelectView from "@/components/Select" ;
import { isEmptyObject } from "@/utils/util";
import { getYearList , getMonthList } from "@/utils/dataList";

export default {
	data(){
		return {
            AcquirerList : [] ,
            invalids     : {} ,
            input        : {
                year        : null ,
                month       : null
            } ,
            selOption : {
                year  : {
                    ID          : "year" ,
                    func        : getYearList(),
                    selType     : "dropdown" ,
                    key         : Date.now().toString(36),
                    checkInput  : true ,
                },
                month  : {
                    ID          : "month" ,
                    func        : getMonthList(),
                    selType     : "dropdown" ,
                    key         : Date.now().toString(36),
                    checkInput  : true ,
                }
            },
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
            let year = input.year ;
            let month = input.month ;
            if( !year || year == "" || !month || month == "") {
                invalids["year"] = "請輸入年"
                invalids["month"] = "請輸入月"
                isInvalids = true ;
            } else {
                if( year < 999 ) {
                    year += 1911
                }
            }
            this.invalids = invalids ;
            if( !isEmptyObject(invalids) ) {
                return
            }
            let _this = this ;
            _this.showLoading(true);
            this.ApiUtil.downloadExcel("/store-transaction-fee/report/"+year+(month<10?"0"+month:month))
            .then(function(){
                _this.showLoading();
            });
        }
    },
    beforeCreate(){

    },
    created(){

    },
    mounted(){

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