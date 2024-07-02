<template >
	<Mask ref = "Mask">
		<div class="p-3">
			<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
				<div class = "p-2 text-center ">
					業務人員資料
				</div>
				
				<div class="row p-3 rounded bg-light m-0">
					<div class=" order-md-1 order-0 col-md-6">
						<label for="employeeId" class="t-12">員工編號</label>
						<input  type="text" name="employeeId" id = "employeeId" v-model="input.employeeId"
						class="form-control text-left bg-white border-0">
					</div>
					<div class=" order-md-1 order-0 col-md-6">
						<label for="nameTw" class="t-12">中文姓名</label>
						<input  type="text" name="nameTw" id = "nameTw" v-model="input.nameTw"
						class="form-control text-left bg-white border-0">
					</div>
					<div class=" order-md-1 order-0 col-md-6">
						<label for="nameEn" class="t-12">英文姓名</label>
						<input  type="text" name="nameEn" id = "nameEn" v-model="input.nameEn"
						class="form-control text-left bg-white border-0">
					</div>
					<div class=" order-md-1 order-0 col-md-6">
						<label for="extensionNumber" class="t-12">分機號碼</label>
						<input  type="text" name="extensionNumber" id = "extensionNumber" v-model="input.extensionNumber"
						class="form-control text-left bg-white border-0">
					</div>				
				</div>
			</div>
		</div>
		<div class="row mt-2 mb-5">
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="hide()"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">取消</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="save"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">儲存</span>
					</button>
				</div>
			</div>
		</div>
	</Mask>
</template>
<script>
import Base from "@/components/CRM/SS/Edit_Base.vue" ;

export default {
	data(){
		return {
			addApi	: "/sales",
			editApi	: "/sales/"
		}
		 
	},
  	extends : Base ,
    methods : {
	    getDefaultInp(){
	    	return {
	    		"id": "",
			    "employeeId": "",
			    "nameTw": "",
			    "nameEn": "",
			    "extensionNumber": ""
	    	}
	    },
    },
}
</script>