<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<template v-if ="showType === 'SEARCH' ">
		<div class="row p-3 justify-content-md-center" ref = 'search' >
			<CardItem colMd="7">
				<SelectView :options="selOption.instituteId" :invalidTxt="invalids.instituteId" LabelVal = "機構 ID"
				v-model="instituteId" ref = "instituteId"/>
			</CardItem>
		</div>
		<div class="row p-3 justify-content-md-center">
			<div class="col-md-6 order-1">
				<div class="m-0">
					<button type="button" @click.prevent="search"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">查詢資料</span>
					</button>
				</div>
			</div>
		</div>
	</template>
	<template v-else-if = " showType === 'EDIT' ">
		<div class="col-md-12 pr-0 text-center font-weight-bold t-18 ">機構 ID:{{instituteId}}</div>
		<div class="row ">
			<div class="col-md-6 mb-3">
				<div class="row p-2">
					<div class="col-md-5 pr-0"
						style="text-align:center;">
						<div style="height: 30px;">
							<span class="t-16">風險策略通過</span>
						</div>
						<select id='select1' 
							multiple='multiple'
							v-model='selPassCodes'
							style='width:100%;height:100px;'>
							<option v-for='item in passCodes'
								:value='item'
								class="dropdown-item"
								style="font-size:18px;">
								{{item}}
							</option>
						</select>
						<div style="height: 30px;">
							<span class="t-16">策略待審查</span>
						</div>
						<select id='select2'
							multiple='multiple'
							v-model='selPendingCodes'
							style='width:100%;height:100px'>
							<option v-for='item in pendingCodes'
								:value='item'
								class="dropdown-item"
								style="font-size:18px;">
								{{item}}
							</option>
						</select>
						<div style="height: 30px;">
							<span class="t-16">風險策略拒絕</span>
						</div>
						<select id='select2'
							multiple='multiple'
							v-model='selRejectCodes'
							style='width:100%;height:100px'>
							<option v-for='item in rejectCodes'
								:value='item'
								class="dropdown-item"
								style="font-size:18px;">
								{{item}}
							</option>
						</select>
					</div>
					<div class="col-md-2">
						<div style="height:30px;"></div>
						<div style="height:100px">
							<button type="button"
								v-on:click="add('passCodes')"
								style="width: 100%;margin-top:8px;min-height: auto;"
								class="btn btn-secondary justify-content-center align-items-center">
								<span>
									<i class="fas fa-angle-left"></i>
								</span>
							</button>
							<button type="button"
								v-on:click="remove('passCodes')"
								style="width: 100%;margin-top: 10px;min-height: auto;"
								class="btn btn-secondary justify-content-center align-items-center">
								<span>
									<i class="fas fa-angle-right"></i>
								</span>
							</button>
						</div>
						<div style="height:30px;"></div>
						<div style="height:100px">
							<button type="button"
								v-on:click="add('pendingCodes')"
								style="width: 100%;margin-top:8px;min-height: auto;"
								class="btn btn-secondary justify-content-center align-items-center">
								<span>
									<i class="fas fa-angle-left"></i>
								</span>
							</button>
							<button type="button"
								v-on:click="remove('pendingCodes')"
								style="width: 100%;margin-top: 10px;min-height: auto;"
								class="btn btn-secondary justify-content-center align-items-center">
								<span>
									<i class="fas fa-angle-right"></i>
								</span>
							</button>
						</div>
						<div style="height:30px;"></div>
						<div style="height:100px">
							<button type="button"
								v-on:click="add('rejectCodes')"
								style="width: 100%;margin-top:8px;min-height: auto;"
								class="btn btn-secondary justify-content-center align-items-center">
								<span>
									<i class="fas fa-angle-left"></i>
								</span>
							</button>
							<button type="button"
								v-on:click="remove('rejectCodes')"
								style="width: 100%;margin-top: 10px;min-height: auto;"
								class="btn btn-secondary justify-content-center align-items-center">
								<span>
									<i class="fas fa-angle-right"></i>
								</span>
							</button>
						</div>
					</div>
					<div class="col-md-5 pl-0"
						style="text-align:center;">
						<div style="height:30px;">
							<span class="t-16">風險決策結果</span>
						</div>
						<select id='selUnsetCodes'
							multiple='multiple'
							v-model='selUnsetCodes'
							style='width:100%;height:360px;'>
							<option v-for='item in unsetCodes'
								:value='item'
								class="dropdown-item"
								style="font-size:18px;">
								{{item}}
							</option>
						</select>
					</div>
				</div>
			</div>
			<div class="col-md-6 mb-3 p-2">
				<div class="col-12 form-group">
					<SelectView :options="defaultFdStatusOption" :invalidTxt="invalids.defaultFdStatus"
					LabelVal = "預設風險作為 (當風險預測發生問題時或是AI回傳風險決策結果無法對應，交易會採用這個)"
					v-model= "detail.defaultFdStatus"  ref = "defaultFdStatus"/>
				</div>
				<div class="col-12 form-group">
					<SelectView :options="defaultTimeOutFdStatusOption" :invalidTxt="invalids.defaultTimeOutFdStatus" 
					LabelVal = "逾時風險作為(當與AI連線發生逾時, 交易會採用這個)"
					v-model= "detail.defaultTimeOutFdStatus" ref = "defaultTimeOutFdStatus"/>
				</div>
				<div class="col-md-12 col-12"
					style="bottom: 1rem;position: absolute;padding-right:2rem;">
					<div class="row m-0">
						<div class="col-md-6 d-flex justify-content-center pl-0">
							<button type="button"
								v-on:click="goBack"
								class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
								<span class="mx-2">取消</span>
							</button>
						</div>
						<div class="col-md-6 d-flex justify-content-center pr-0">
							<button type="button"
								v-on:click="save"
								:disabled="loading"
								class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
								<i class="material-icons loading"
									v-if="loading">refresh</i>
								<span class="mx-2">儲存</span>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</template>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import SelectView from "@/components/Select" ;
import CardItem from "@/components/CardItem" ;
import { resetDefaultVal , isEmpty , firstUpperCase } from "@/utils/util" ;
import { getInstituteList , getRiskStatusList } from "@/utils/dataListSA" ;


export default {
	data(){
		return {
			title 			: "預設風險策略管理" ,
			selPassCodes	: [] ,
            selPendingCodes	: [] ,
            selRejectCodes 	: [] ,
            passCodes   	: [] ,
            pendingCodes 	: [] ,
            rejectCodes    	: [] ,
            selUnsetCodes   : [] ,
            unsetCodes      : [] ,
            instituteId 	: '',
            detail      	: {
            	defaultFdStatus : "" ,
            } ,
            invalids 		: {} ,
            showType 		: "SEARCH" ,
            selOption 		: {
				instituteId : {
					ID 			: "instituteId" ,
					func 		: getInstituteList.bind(this, "SEARCH" ) ,
					selType 	: "dropdown" ,
					addNullText : true ,
				}
			},
			previousData	: "" ,
		}
	},
	extends : Base ,
	computed: {
		defaultFdStatusOption : function() {
			let defVal = this.detail.defaultFdStatus ;
			return {
				ID 			: "defaultFdStatus" ,
	    		func 		: getRiskStatusList ,
	    		selType 	: "dropdown" ,
	    		defVal 		: defVal
	    	}
	    },
	    defaultTimeOutFdStatusOption : function() {
	    	let defVal = this.detail.defaultTimeOutFdStatus ;
	    	return {
	    		ID 			: "defaultTimeOutFdStatus" ,
	    		func 		: getRiskStatusList ,
	    		defVal 		: defVal ,
	    		selType 	: "dropdown" ,
	    	}
	    },
	    instituteIDOption : function() {
	    	let defVal = this.instituteId ;
	    	return {
	    		ID 			: "instituteId" ,
	    		func 		: getInstituteList.bind(this, "SEARCH" ) ,
	    		addNullText : true ,
	    		selType 	: "dropdown" ,
	    		defVal 		: defVal
	    	}
	    }
	},
	watch: {     
       
    },
	components : {
		Input , SelectView , CardItem
	},
	methods: {
		remove( type ){
            let unsetCodes = this.unsetCodes ;
            let arr = this[type];
            let selList =this[ 'sel' + firstUpperCase(type) ];
            
            selList.forEach(function( value ){
                let index = arr.findIndex( obj => obj == value );
                if( index > -1 ) {
                    unsetCodes.push(arr[index]);
                    arr.splice(index, 1);
                }
            })                   
            unsetCodes.sort(function(a, b) {
                return a - b
            });
            this.unsetCodes = unsetCodes ;
            this[type] = arr ;
        },
        add( type ){
            let unsetCodes = this.unsetCodes ;
            let arr = this[type] ;
            let selUnsetCodes = this.selUnsetCodes ;
            selUnsetCodes.forEach(function( value ){
                let index = unsetCodes.findIndex( obj => obj == value);
                if( index > -1 ) {
                    arr.push(unsetCodes[index]);
                    unsetCodes.splice(index, 1);
                }
            })
            arr.sort(function(a, b) {
                return a - b
            });
            this.unsetCodes = unsetCodes ;
            this[type] = arr ;
        },
        search(){
        	let invalids = {} ;
            
            let instituteId = this.instituteId ;
            if( ! instituteId ) {
                invalids["instituteId"] = "機構 ID 必填";
            }

            this.invalids = invalids ;
            if( isEmpty( invalids) ) {
            	let _this = this ;

            	_this.showLoading(true);
				
				this.ApiUtilSA.get( "veri-id/get-default-strategy/" + instituteId )
				.then((res) => {
					if( res.success ) {
						let data = res.data ;
						data["instituteId"] 	= instituteId
						_this["previousData"] 	= JSON.stringify( data ) ;
						_this.passCodes 		= data["passCodes"];
						_this.pendingCodes 	 	= data["pendingCodes"] ;
						_this.rejectCodes 		= data["rejectCodes"] ;
						_this.unsetCodes 		= data["unsetCodes"];
						_this.detail = {
		                	defaultFdStatus 		: data["defaultFdStatus"] ,
		                	defaultTimeOutFdStatus 	: data["defaultTimeOutFdStatus"] ,
		                }
						_this.showType = 'EDIT';
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
            }            
        },
        save(){
        	let _this = this ;
			let param = {
				previousData			: this.previousData ,
				instituteId 			: this.instituteId ,
				defaultFdStatus 		: this.detail.defaultFdStatus ,
				defaultTimeOutFdStatus 	: this.detail.defaultTimeOutFdStatus ,
				passCodes 				: this["passCodes"] ,
				pendingCodes 			: this["pendingCodes"] ,
				rejectCodes 			: this["rejectCodes"] ,
			}

			this.showLoading(true);
			
			this.ApiUtilSA.post("veri-id/update-default-strategy" , param )
			.then((res) => {
				if( res.success && res.data.success ) {
					this.showModel({
		                title 	: this.title ,
		                message : "修改成功",
		                endFunc : function(){
		                	_this.search();
		                }
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});

        },
        goBack(){
			this.clear();
		},
        clear(){
        	this.showType = "SEARCH" ;
        	this.instituteId = "" ;
        	this.detail = {
        		defaultFdStatus : "" ,
        	};
        	this.passCodes = [] ;
            this.pendingCodes = [] ;
            this.rejectCodes = [] ;
            this.unsetCodes = [] ;
        },
	},
	mounted () {
		
	},
	beforeUpdate () {

	},
	beforeMount(){
		
	},
	//銷毀前
    beforeUnmount(){
    	
    },
}	
</script>