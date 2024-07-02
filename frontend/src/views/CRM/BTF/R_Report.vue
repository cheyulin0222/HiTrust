<template >
    <h4 class="sec-title font-weight-bold mt-md-4 mb-3">
        手續費報表
    </h4>
    <div class="row p-3" ref = 'search' >

        <div class=" order-md-1 order-0 col-md-3">
            <label for="acquirerCode" class="t-12">收單銀行</label>
            <SelectView :options="selOption.acquirerCode" :invalidTxt="invalids.acquirerCode "
            @updateVal="updateVal" ref = "acquirerCode " :key="selOption.acquirerCode.key"/>
        </div>
        <div class=" order-md-1 order-0 col-md-3">
            <label for="usdRate" class="t-12">美金匯率</label>
            <input  type="number" name="usdRate" id = "usdRate" v-model="input.usdRate " step="0.1"
            class="form-control text-left bg-white border-0"
            :class="{'is-invalid': !!invalids.usdRate }">
            <div class="invalid-feedback" id = "invalid_usdRate">{{invalids.usdRate }}</div>
        </div>
        <div class=" order-md-1 order-0 col-md-3">
            <label for="year" class="t-12">年</label>
            <SelectView :options="selOption.year" :invalidTxt="invalids.year"
            @updateVal="updateVal" ref = "year"/>
        </div>
        <div class=" order-md-1 order-0 col-md-3">
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
import SelectView from "@/components/Select" ;
import Base from "@/components/Base" ;
import { isEmptyObject } from "@/utils/util";
import { getYearList , getMonthList } from "@/utils/dataList";

export default {
	data(){
        let now = new Date();
		return {
            AcquirerList : [] ,
            invalids     : {} ,
            input        : {
                acquirerCode: "" ,
                usdRate     : 0 ,
                year        : now.getFullYear() ,
                month       : now.getMonth()+1
            } ,
		}
		 
	},
	props : {
	},
	computed : {
        selOption   : function(){
            return {
                acquirerCode  : {
                    ID          : "acquirerCode" ,
                    func        : this.AcquirerList,
                    selType     : "dropdown" ,
                    filter      : true ,
                    key         : Date.now().toString(36)
                },
                year  : {
                    defVal      : this.input.year ,
                    ID          : "year" ,
                    func        : getYearList(),
                    selType     : "dropdown" ,
                    key         : Date.now().toString(36),
                    checkInput  : true ,
                },
                month  : {
                    defVal      : this.input.month ,
                    ID          : "month" ,
                    func        : getMonthList(),
                    selType     : "dropdown" ,
                    key         : Date.now().toString(36),
                    checkInput  : true ,
                }
            }
        },
	},
	watch : {        
    },
    components : {
        SelectView
    },
    extends : Base ,
    methods : {
        updateVal( val , ID ) {
            this.input[ID] = val ;
        },
        download(){
            let invalids = {} ;
            let isInvalids = false ;
            let input = this.input ;
            let acquirerCode = input.acquirerCode ;
            if( !acquirerCode || acquirerCode == "" ) {
                invalids["acquirerCode"] = "請選擇收單銀行";
                isInvalids = true ;
            }
            let usdRate  = input.usdRate  ;   
            if( typeof usdRate != 'number' && ( !usdRate || usdRate == "" )) {
                invalids["usdRate"] = "請輸入美金匯率";
                isInvalids = true ;
            }
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
            this.ApiUtil.downloadExcel("bank-transaction-fee/report/"+acquirerCode+"/"+year+(month<10?"0"+month:month)+"/"+usdRate)
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
        let _this = this ;
        this.ApiUtil.get("/acquirer/all")
        .then((res) => {
            if( res.success ) {
                let AcquirerList = [] ;
                res.data.forEach(function( item ){
                    let text = item.id + '-' + item.nameTw ;
                    if( item.nameCode ) {
                        if( text != "" ) {
                            text += `(${item.nameCode})`
                        } else {
                            text = item.nameCode
                        }
                    }
                    AcquirerList.push({
                        val     : item.nameCode ,
                        text    : text
                    })
                });
                _this.AcquirerList = AcquirerList ;
            }
        });

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