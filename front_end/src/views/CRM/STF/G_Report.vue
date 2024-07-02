<template >
    <h4 class="sec-title font-weight-bold mt-md-4 mb-3">
        群組報表
    </h4>
    <div class="row p-3 justify-content-center">
        <div class=" order-md-1 order-0 col-md-8">
            <label for="companyName" class="t-12">統一編號</label>
            <div class="row">
                <input  type="text" name="companyTaxIdNumber" id = "companyTaxIdNumber" v-model="input.companyTaxIdNumber"
                class="form-control text-left bg-white border-0" style="width: 80%">
                <div class="invalid-feedback" id = "invalid_taxIdNumber">{{invalids.companyTaxIdNumber}}</div>
                <div role="button" class = "btn" @click.prevent="search" style="border: 0px !important;">
                    <span>
                        <i class="fa img-fluid onactived fa-search" style="font-size: 24px;"></i> 
                    </span>
                </div>
            </div>
        </div>
        
    </div>
    <div class="row m-0 p-3">
        <div class="p-2  order-0 col-md-1" ></div>       
        <div :class="getClass('t1Tilte')">統一編號</div>
        <div :class="getClass('tiColumn')" class="selectText">{{companyTaxIdNumber}}</div>
    </div>
    <div class="p-3">
        <div class="border-0 pt-3 col-md-12">
            <table class="table table-hover m-0">
                <thead>
                    <tr>
                        <td class="border border-info rounded-0 p-3 text-center alert-primary text-center col-md-6">
                            群組代碼
                        </td>
                        <td class="border border-info rounded-0 p-3 text-center alert-primary text-center col-md-2">
                            年度
                        </td>
                        <td class="border border-info rounded-0 p-3 text-center alert-primary text-center col-md-2">
                            月份
                        </td>
                        <td class="border border-info rounded-0 p-3 text-center alert-primary text-center col-md-2">
                        </td>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="( payment , index ) in dataRow" :key="payment.id?payment.id:UUID()" visisble="true">
                        <td class="text-center">
                            <span class="selectText">{{payment.id}}</span>
                        </td>
                        <td class="text-center">
                            <SelectView :options="selOption.year" :payment ="payment" 
                            @updateVal="updateVal" ref = "year"/>
                        </td>
                        <td class="text-center">
                            <SelectView :options="selOption.month" :payment ="payment"
                            @updateVal="updateVal" ref = "month"/>
                        </td>
                        <td class="text-center"
                            style="width:100px;">
                            <div role="button" @click.prevent= "download(index)">
                                <i class="fa img-fluid onactived pr-md-3 fa-download"/>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
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
		return {
            invalids     : {} ,
            companyTaxIdNumber : "",
            input        : {
                companyTaxIdNumber: "" ,
            },
            dataRow : [] ,
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
    extends : Base ,
    methods : {
        updateVal( val , ID , payment ) {
            payment[ID] = val;
        },
        getClass( type , setCol , defaultClass ){
            let className = "" ;
            switch ( type ) {
                case "t1Tilte" :
                    className = "border border-info rounded-0 p-3 text-center alert-primary col-md-2" ;
                    break;
                case "tiColumn" : 
                    className = "border border-info rounded-0 p-3 text-center bg-white col-md-4";
                    break
            }
            return className
        },
        search(){
            let companyTaxIdNumber = this.input.companyTaxIdNumber ;
            let _this = this ;
            let params = {
                companyTaxIdNumber : companyTaxIdNumber
            };
            this.loading = true ;
            this.ApiUtil.get("store-transaction-fee/group/args",params)
            .then((res) => {
                if( res.success ) {
                    _this.dataRow = res.data ;
                    _this.companyTaxIdNumber = companyTaxIdNumber ;
                } else {
                    _this.dataRow = [] ;
                    _this.companyTaxIdNumber = "" ;
                }
                _this.loading = false ;
            });
            
        },
        download(index){
            console.log(index);
            let obj = this.dataRow[index];

            let year = obj.year ;
            let month = obj.month ; 
            let groupId = obj.id ;
            let text = "" ;
            if( !year || year == "" || !month || month == "") {
                text = "請輸入年/月"
            } else {
                if( year < 999 ) {
                    year += 1911
                }
            }
           
            if( text != "" ) {
                this.showModel({
                    type : "error" , 
                    title : '時間範圍選取錯誤',
                    message : text
                });
                return
            }
            let _this = this ;
            _this.showLoading(true);
            this.ApiUtil.downloadExcel("/store-transaction-fee/report/"+groupId+"/"+year+(month<10?"0"+month:month))
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