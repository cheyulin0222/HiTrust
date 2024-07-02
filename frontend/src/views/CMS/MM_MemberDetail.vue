<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		會員查詢-詳細資料
	</h4>
	<div class="p-3 mb-2">
		<Card title = "會員詳細資料" >
			<CardItem colMd="3">
				<Input ID = "userId" LabelVal = "帳號" :input = "cmsUser" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="3">
				<Input ID = "userStatus" LabelVal = "狀態" :input = "cmsUser" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="3">
				<Input ID = "pwdErrCnt" LabelVal = "密碼錯誤次數" :input = "cmsUser" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="3">
				<Input ID = "lastLoginDttm" LabelVal = "最近登入時間" :input = "cmsUser" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="3">
				<Input ID = "nameCh" LabelVal = "公司(機構)中文名稱" :input = "cmsUser" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="3">
				<Input ID = "nameEn" LabelVal = "公司(機構)英文名稱" :input = "cmsUser" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="3">
				<Input ID = "addrCh" LabelVal = "公司(機構)中文地址" :input = "cmsUser" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="3">
				<Input ID = "addrEn" LabelVal = "公司(機構)英文地址" :input = "cmsUser" :readOnly="true"/>
			</CardItem>
		</Card>
	</div>
	<div class="p-3" v-for="( contact , index ) in userContactList">
		<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2" style="position: relative;">
			<div class = "p-2 text-center ">
				{{contact.id.contactType}}聯絡人
			</div>
			<div class="row rounded bg-light m-0 justify-content-md-center text-center">
				<div class="order-0 col-md-3 p-3" style="border: 1px solid #cce5ff;">
					姓名
				</div>
				<div class="order-0 col-md-3 p-3" style="border: 1px solid #cce5ff;">
					部門名稱
				</div>
				<div class="order-0 col-md-3 p-3" style="border: 1px solid #cce5ff;">
					電話
				</div>
				<div class="order-0 col-md-3 p-3" style="border: 1px solid #cce5ff;">
					電子郵件地址
				</div>
				<div class="order-0 col-md-3 p-3" style="border: 1px solid #cce5ff;">
					{{contact.nameCh}}<br/>{{contact.nameEn}}
				</div>
				<div class="order-0 col-md-3 p-3" style="border: 1px solid #cce5ff;">
					{{contact.deptNameCh}}<br/>{{contact.deptNameEn}}
				</div>
				<div class="order-0 col-md-3 p-3" style="border: 1px solid #cce5ff;">
					{{ getTels(contact.tel) }}
					<template v-if="contact.fax">
						<br/>
						{{ contact.fax}}
					</template>
				</div>
				<div class="order-0 col-md-3 p-3" style="border: 1px solid #cce5ff;">
					{{contact.email}}
				</div>
			</div>
		</div>
	</div>
	<div class="row mt-2 mb-5">
		<div class="col-md-4 offset-md-4 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="goBack"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">回上一頁</span>
				</button>
			</div>
		</div>
	</div>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input"
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;

function getDefault () {
	return {
		dataRow 		: [] ,
		invalids    	: {} ,
		certApplyType 	: "" ,
		categoryList 	: [] ,
 	}
}
export default {
	data(){
		return {
			cmsUser 		: {} ,
			userContactList : [] ,
		}
	},
	props : {
		
	},
	extends : Base ,
	computed: {
		
	},
	watch: {     
       
    },
	components : {
		Input , Card , CardItem
	},
	methods: {
		init(){
			let _this = this;
			_this.showLoading(true);
			this.ApiUtilCMS.get("/get-member-detail/" + this.params.userId)
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					_this.cmsUser = data;
					_this.userContactList = data.userContact;
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		goBack(){
			this.$router.back();
		},
		getTels( tel ) {
			let tels = tel.split("-") ;
			let tel2 ;
			let result = [] ;
			if( tels.length > 1 ) {
				result.push(tels[0]);
				tel2 = tels[1];
			} else {
				result.push("");
				tel2 = tel ;
			}
			tels = tel2.split("#") ;
			if( tels.length > 1 ) {
				result.push(tels[0]);
				result.push(tels[1]);
			} else {
				result.push(tel2);
				result.push("");
			}
			return result[0] + " - " + result[1] + " # " + result[2];
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