<template>
	<span class="pl-2 d-inline-block input-text m-0" v-if=" !!LabelVal " >
		{{ LabelVal }} 
	</span>
	<div class="btn card " :class="{'disabled': !!disabled}">
		<div class="card-body d-flex w-100 align-items-center text-left py-1 pl-0 pr-2">
			<div class="reportrange w-100" ref= "reportrange" :class="{'div-stop-click': !!disabled}" >
				<div class="row">
					<div class="col t-14">
						{{ $filters.time( modelValue[0] , format ) }}
						~
						{{ $filters.time( modelValue[1] , format ) }}
					</div>
					<div clas storeNoTypes="col-auto ml-auto">
						<img src="@/assets/img/ic-calendar.svg"
							class="img-fluid" />
					</div>
				</div>
			</div>
		</div>
	</div>	
</template>
<script>

import  moment from 'moment'
import { mount , setStartDate , setEndDate }  from '@/utils/daterange'


export default {

	data(){
		return {
			isEmit : false ,
		} ;
	},
	mounted() {
        this.resetDate();
    },
	components : {
		
	},
	props: {
        id 			: String,
        type 		: String,
        disabled 	: Boolean ,
        modelValue 	: Array ,
        LabelVal 	: String ,
        timePicker 	: {
        	type 	: Boolean ,
        	default : false ,
        },
        limitTime 	: {
        	type 	: Boolean ,
        	default : false ,
        },
        limitType   : {
           type     : String ,
            default : "month" , 
        }
    },
    computed : {
    	format () {
    		return this.timePicker ? "YYYY-MM-DD H:m" : "YYYY/MM/DD"
    	}
    },
    emits : ["update:modelValue"] ,
    watch: {
    	modelValue (newVal, oldVal) {
    		if( this.isEmit ) {
                this.isEmit = false ;
            } else {
                this.resetDate2();
            }
    	},
    },
	methods: {
    	resetDate(){
            let _this = this ;

            let date = this.modelValue ;
            mount(this.$refs.reportrange , date[0], date[1], function (s, e) {
            	let time = [s , e ] ;
            	_this.$emit('update:modelValue', time )
            	_this.isEmit = true ;
            }, this.limitTime , this.timePicker , this.limitType );      
        },
        resetDate2(){
        	let date = this.modelValue ;
        	setStartDate(this.$refs.reportrange, date[0] );
            setEndDate(this.$refs.reportrange , date[1]);   
        }
	}
}	
</script>