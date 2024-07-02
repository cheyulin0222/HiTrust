<template >
    <h4 class="sec-title font-weight-bold mt-md-4 mb-3">
        簡訊費用報表(月)
    </h4>
    <div class="row p-3" ref = 'search' >
        <CardItem colMd="6">
            <SelectView :options="selOption.acqId" :invalidTxt="invalids.acqId" LabelVal = "收單銀行代碼" @updateVal="updateVal" ref = "acqId" :required="true"/>
        </CardItem>
        <CardItem colMd="6">
            <SelectView :options="selOption.year" :invalidTxt="invalids.year" LabelVal = "年" @updateVal="updateVal" ref = "year"/>
        </CardItem>
        <CardItem colMd="6">
            <SelectView :options="selOption.month" :invalidTxt="invalids.month" LabelVal = "月"
            @updateVal="updateVal" ref = "month"/>
        </CardItem>
        <CardItem colMd="6">
            <Input ID = "smsFee" LabelVal = "簡訊費用(封)" :input = "input" :invalids = "invalids" :required="true"/>
        </CardItem>
    </div>
    <div class="row mt-2 mb-5">
        <div class="col-md-4 offset-md-2 p-2">
            <div class="m-0">
                <button type="button" @click.prevent="clear"
                class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
                    <span class="mx-2">清除重設</span>
                </button>
            </div>
        </div>
        <div class="col-md-4  p-2">
            <div class="m-0">
                <button type="button" @click.prevent="download"
                class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
                    <span class="mx-2">Excel 報表</span>
                </button>
            </div>
        </div>
    </div>
</template>
<script>
import SelectView from "@/components/Select" ;
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import CardItem from "@/components/CardItem" ;
import { isEmptyObject , isEmpty } from "@/utils/util" ;
import { getYearList , getMonthList } from "@/utils/dataList" ;
import { getTeAcqList } from "@/utils/dataListSA" ;

export default {
	data(){
        
        let input = this.getDefaultVal() ;

		return {
            input : input,
            invalids : { 
                
            },
            selOption : {
                acqId : {
                    ID          : "acqId" ,
                    func        : getTeAcqList.bind(this,true),
                    selType     : "dropdown" ,
                    filter      : true ,
                    setFirst    : true ,
                    addNullText : true ,
                },
                year  : {
                    defVal      : input.year ,
                    ID          : "year" ,
                    func        : getYearList(),
                    selType     : "dropdown" ,
                    key         : Date.now().toString(36),
                    checkInput  : true ,
                },
                month  : {
                    defVal      : input.month ,
                    ID          : "month" ,
                    func        : getMonthList(),
                    selType     : "dropdown" ,
                    key         : Date.now().toString(36),
                    checkInput  : true ,
                }
            }
		}
		 
	},
	props : {
	},
	computed : {
        
	},
	watch : {        
    },
    components : {
        SelectView , Input , CardItem
    },
    extends : Base ,
    methods : {
        updateVal( val , ID ) {
            this.input[ID] = val ;
        },
        download(){
            let _this = this ;
            let acqId = this.input.acqId ;
            let smsFee = this.input.smsFee ;
            let invalids = {} ;

            if( acqId == "" ) {
                invalids.acqId = "請選擇收單行" ;
            }

            if( smsFee == "" ) {
                invalids.smsFee = "請輸入簡訊費" ;
            }

            this.invalids = invalids ;

            if( isEmpty( invalids) ) {
                let param = {
                    acqId       : _this.input.acqId ,
                    year        : _this.input.year ,
                    month       : _this.input.month ,
                    fee         : _this.input.smsFee ,
                }

                _this.showLoading(true);
                
                this.ApiUtilSA.downloadExcel("download-sms-fee-report" , param ,  {
                   
                })
                .finally(function () {
                    _this.showLoading();
                });
            }
            
        },
        clear(){
            this.input = this.getDefaultVal();
            this.$refs.acqId.clear();
            this.$refs.year.clear();
            this.$refs.month.clear();
        },
        getDefaultVal(){
            let now = new Date();
            return {
                acqId   : "" ,
                year    : now.getFullYear() ,
                month   : now.getMonth()+1 ,
                smsFee  : "1.5" ,
            }
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