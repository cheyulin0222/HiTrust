<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>

	<div class="p-3">
		<Card title = "變更密碼" :mainCenter="true">
			<CardItem colMd="7">
				<Input ID = "email" LabelVal = "使用者" :input = "user" :readOnly = "true" />
			</CardItem>
			<CardItem colMd="7">
				<Input ID = "oldPswd" LabelVal = "請輸入舊密碼" v-model= "input.oldPswd"  type="password" :required = "true" maxlength = "16" :invalids = "invalids"/>
			</CardItem>
			<CardItem colMd="7">
				<Input ID = "newPswd" LabelVal = "請輸入新密碼" v-model= "input.newPswd" type="password" :required = "true" maxlength = "16" :invalids = "invalids"/>
			</CardItem>
			<CardItem colMd="7">
				<Input ID = "confirmPswd" LabelVal = "再次確認新密碼" v-model= "input.confirmPswd" type="password" :required = "true" maxlength = "16" :invalids = "invalids"/>
			</CardItem>
			<CardItem colMd="7" class="text-center">
				<span style="color:blue;">注意： 密碼長度12至16位，英數字夾雜</span>
			</CardItem>
			<CardItem colMd="7" class="text-center">
				<span style="color:red;">※ 網路密碼請留意英文字母大小寫 ※</span>
			</CardItem>
		</Card>
	</div>
	<div class="row mt-2 mb-5">
		<div class="col-md-4 offset-md-2 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="clear"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">清除重填</span>
				</button>
			</div>
		</div>
		<div class="col-md-4 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="save"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">確定</span>
				</button>
			</div>
		</div>
	</div>
	
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input" ;
import SelectView from "@/components/Select";
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import { mapGetters, mapState } from 'vuex';
import { resetDefaultVal , isEmpty } from "@/utils/util"
import { checkPasswd , checkAccount , getSHAPassword , getSHAOldPassword } from "@/utils/user" ;

export default {
	data(){
		return {
			title 		: "變更密碼" ,
			input 		: this.getDefault() ,
			invalids 	: {} ,
		} ;
	},
	extends : Base ,
	computed: {
		...mapGetters( 'user' , {
	    	user : "getUser",
	    }),
	},
	watch: {     
       
    },
	components : {
		Input , SelectView , Card , CardItem
	},
	methods: {
		save(){
			let input 		= this.input ;
    		let oldPswd 	= input.oldPswd	;
			let newPswd 	= input.newPswd	;
			let confirmPswd = input.confirmPswd	;
    		let invalids = {} ;
    		
    		if ( oldPswd == "" ) {
    			invalids.oldPswd = "請輸入舊密碼" ;
    		}

    		if ( newPswd == "" ) {
    			invalids.newPswd = "請輸入新密碼" ;
    		}

    		if ( confirmPswd == "" ) {
    			invalids.confirmPswd = "請輸入再次確認新密碼" ;
    		}

    		if( isEmpty(invalids) ) {
    			if( newPswd != confirmPswd ) {
    				invalids.newPswd  = "密碼確認錯誤" ;
    				invalids.confirmPswd = "密碼確認錯誤" ;
    			} else {
    				let msg = checkPasswd( newPswd ) ;
    				if( msg != "" ) {
    					invalids.newPswd  = msg ;
    				}
    			}
    		}

    		this.invalids = invalids ;
    		if( isEmpty(invalids) ) {
    			let param = {
  					oldPswd 	: getSHAOldPassword( oldPswd ) ,
  					newPswd   	: getSHAPassword( newPswd ), 
    			}
    			let _this = this ;

    			_this.showLoading(true);
    			
    			this.ApiUtilSA.put("change-self-password" , param )
    			.then((res) => {
					if( res.success && res.data.success) {
						
						this.showModel({
			                title : this.titile ,
			                message :"重設密碼成功",
			                endFunc : function(){
			                	_this.clear();
			                }
			            });
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
			}
		},
		clear(){
			this.input = this.getDefault();
		},
		getDefault(){
			return {
				oldPswd 	: "" ,
				newPswd 	: "" ,
				confirmPswd : "" 
			}
		}
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