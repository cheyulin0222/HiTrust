<template >
    <h4 class="sec-title font-weight-bold mt-md-4 mb-3">
        業務月報
    </h4>
    <div class="row p-3 justify-content-md-center">
        <div class="col-md-6 col-12 order-1">
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

import Base from "@/components/Base" ;

export default {
	data(){
        		 
	},
	props : {
	},
	computed : {
    
	},
	watch : {        
    },
    components : {
       
    },
    extends : Base ,
    methods : {
        download(){
            let _this = this ;
            _this.showLoading(true);
            this.ApiUtil.downloadExcel("/sales-monthly-report")
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