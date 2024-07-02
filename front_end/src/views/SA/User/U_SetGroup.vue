<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		人員群組維護
	</h4>
	<div class="row p-3" ref = 'search' >
		<CardItem colMd="6">
			<SelectView :options="selOption.mGId" :invalidTxt="invalids.mGId" LabelVal = "群組代號"
			@updateVal="updateVal" ref = "serviceType"/>
		</CardItem>
		<CardItem colMd="6" v-show = "input.mGId === '' " >
			<Input ID = "userId" LabelVal = "人員E-mail(若有值，以此條件搜尋)" :input = "input" :invalids = "invalids" />
		</CardItem>
		
	</div>
	<div class="row p-3 justify-content-md-center">
		<div class="col-md-4 order-1">
			<div class="m-0">
				<button type="button" @click.prevent="search"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">查詢資料</span>
				</button>
			</div>
		</div>
		<div class="col-md-4 order-1">
			<div class="m-0">
				<button type="button" @click.prevent="addGroup"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">新增群組</span>
				</button>
			</div>
		</div>
	</div>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import SelectView from "@/components/Select" ;
import CardItem from "@/components/CardItem" ;
import { getMGIDList } from "@/utils/dataListSA" ;
import { isEmpty } from "@/utils/util" ;

export default {
	data(){
		return {
			selOption : {
				mGId : {
		    		ID 			: "mGId" ,
		    		func 		: getMGIDList.bind(this,true) ,
		    		setFirst 	: true ,
		    		addNullText	: true ,
		    		filter 		: true ,
		    		selType 	: "dropdown"
		    	},
			},
			invalids 	: {} ,
			input : {
				mGId 	: "" ,
				userId	: "" ,
			}
		}
	},
	extends : Base ,
	computed: {

	},
	watch: {     
       
    },
	components : {
		Input , SelectView , CardItem
	},
	methods: {
		search(){
			let input = this.input ;
			let invalids = {} ;
			if( input.userId === "" && input.mGId === "" ) {
				invalids["userId"] = invalids["mGId"] = "必填寫群組代號或人員E-mail"
			}
			this.invalids = invalids ;
			if( isEmpty(invalids) ) {
				if( input.mGId != "" ) {
					this.setGroup({
						userId 			: "" , 
						mGId 			: input.mGId , 
						merGroupType 	: "MULTIPLE" ,
					})
				} else {
					this.setGroup({
						userId 			: input.userId , 
					})
				}
				
			}
			
		},
		setGroup( user ){
			let userId = user.userId ;
			if( userId != "" ) {
				let _this = this ;
				
				_this.showLoading(true);
				this.ApiUtilSA.get("/get-ap-mer-user-group-info/" + userId )
				.then((res) => {
					if( res.success ) {
						let data = res.data ;
						_this.goNextPath("SA1404_1",{
							userId 			: userId ,
			        		merGroupType 	: data.merGroupType ,
			        		mGId 			: data.merGroupId ,
			        		type 			: "EDIT" ,
			        	})
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
			} else {
				this.goNextPath("SA1404_1",{
	        		merGroupType 	: user.merGroupType ,
	        		mGId 			: user.mGId ,
	        		type 			: "EDIT" ,
	        	})
			}
			
		},
		addGroup( user ){
			let _this = this ;
			
			_this.showLoading(true);
			this.ApiUtilSA.get("next-ap-mer-user-group-id" )
			.then((res) => {
				if( res.success ) {
					this.goNextPath("SA1404_1",{
		        		merGroupType 	: "MULTIPLE" ,
		        		mGId 			: res.data ,
		        		type 			: "ADD" ,
		        	})
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
			
			
		},
		updateVal( val , ID ){
			if( ID === "mGId" && val != "" ) {
				this.input["userId"] = "" ;
			}
    		this.input[ID] = val ;
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