<template >
	<Mask ref = "Mask">
		<div class="p-3">
			<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
				<div class = "p-2 text-center ">
					商店交易手續費計算方式資料
				</div>
				
				<div class="row p-3 rounded bg-light m-0">
					<div class=" order-md-1 order-0 col-md-12">
						<label for="name" class="t-12">商店交易手續費計算方式名稱</label>
						<input  type="text" name="name" id = "name" v-model="input.name"
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
			addApi	: "/store-transaction-fee/group-merchant-billing-type",
			editApi	: "/store-transaction-fee/group-merchant-billing-type/"
		}		 
	},
  	extends : Base ,
    methods : {
	    getDefaultInp(){
	    	return {
	    		"id": "",
				"name": "",
	    	}
	    },
    }
}
</script>