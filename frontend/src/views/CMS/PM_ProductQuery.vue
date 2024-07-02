<template>
	<div class="container-fluid">
		<template v-for="( certType , index ) in certTypeList" >
			<div>
				<div class="text-center p-2 font-weight-bold mt-3" >{{ certType.cpdType }}({{ certType.cpdName }})</div>
				<DataTable :ref="certType.cpdType" :dataRow="certType.certProductsListVO" :options ="tableOption"/>
			</div>
		</template>	
	</div>
	<div class="row mt-2">
		<div class="col-md-4 offset-md-4 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="doAdd"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">新增</span>
				</button>
			</div>
		</div>
	</div>
	<Mask ref = "Mask">
		<div class="p-3">
			<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
				<div class = "p-2 text-center ">
					{{ type === "ADD" ? "新增" : "維護" }}產品
				</div>	
				<div class="row p-3 rounded bg-light m-0">
					<div class=" order-md-1 order-0 col-md-6 mb-2">
						<SelectView :options="selOption.cpdType" :invalidTxt="invalids.cpdType" LabelVal = "憑證類型"
						v-model = "prodObj.cpdType" ref = "cpdType" :readOnly=" type === 'EDIT' " :required = "true"/>
					</div>
					<template v-if=" type === 'ADD' && prodObj.cpdType === 'CREATE' " >
						<div class=" order-md-1 order-0 col-md-6 mb-2"></div>
						<div class=" order-md-1 order-0 col-md-6 mb-2">
							<Input ID = "otherCpdType" LabelVal = "新增憑證類型(類型命名規則為品牌+憑證類型 範例：TWCA EV SSL )" :input = "prodObj" :invalids = "invalids" maxlength="100" :required = "true"/>
						</div>
						<div class=" order-md-1 order-0 col-md-6 mb-2">
							<Input ID = "cpdName" :LabelVal = "'新增憑證名稱(顯示於憑證販售頁的商品名稱)'" :input = "prodObj" :invalids = "invalids" maxlength="100"/>
						</div>
					</template>
					<template v-else >
						<div class=" order-md-1 order-0 col-md-6 mb-2">
							<Input ID = "cpdName" :LabelVal = "'憑證名稱(顯示於憑證販售頁的商品名稱)'" :input = "prodObj" :invalids = "invalids" maxlength="100" :readOnly=" type === 'EDIT' " />
						</div>
					</template>					
					<div class=" order-md-1 order-0 col-md-6  mb-2">
						<SelectView :options="selOption.expiryType" :invalidTxt="invalids.expiryType" LabelVal = "效期"
						v-model = "prodObj.expiryType" ref = "expiryType" :readOnly="type === 'EDIT'" :required = "true" />
					</div>
					<div class=" order-md-1 order-0 col-md-6  mb-2">
						<SelectView :options="selOption.certApplyType" :invalidTxt="invalids.certApplyType" LabelVal = "申請書種類" v-model = "prodObj.certApplyType" ref = "certApplyType" :readOnly=" type === 'EDIT' " :required = "true" />
					</div>
					<div class=" order-md-1 order-0 col-md-6">
						<Input ID = "price" LabelVal = "售價(NTD)" :input = "prodObj" :invalids = "invalids" maxlength="12" :required = "true" type = "number"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row mt-2 mb-2">
			<div class="col-md-4 offset-md-2 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="hideMask"
					class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">取消</span>
					</button>
				</div>
			</div>
			<div class="col-md-4 p-2">
				<div class="m-0">
					<button type="button" @click.prevent="validate"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">儲存</span>
					</button>
				</div>
			</div>
		</div>
	</Mask>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import DataTable from '@/components/Table/DataTable.vue' ;
import Mask from "@/components/Mask" ;
import Input from "@/components/Input" ;
import SelectView from "@/components/Select" ;
import { getExpiryTypeList } from "@/utils/dataListCMS" ;
import { isEmptyObject } from "@/utils/util";

export default {

	data(){
		return {
			certTypeList 	: [],
			dataRow 		: {},
			type 			: "ADD" ,
			prodObj 		: this.getDefaultProd(),
			isWatch 		: false ,
			editStorage 	: this.getDefaultEditStorage(),
			cpdTypeArr 		: [] ,
			certTypeObj 	: {},
			previousData 	: "{}" ,
			invalids 		: {},
			selOption 		: {
				cpdType : {
					ID 			: "cpdType" ,
					addNullText : true ,
		    		selType 	: "dropdown" ,
				},
		    	expiryType : {
		    		ID 			: "expiryType" ,
		    		func 		: getExpiryTypeList.bind(true, this),
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
		    	certApplyType : {
		    		ID 			: "certApplyType" ,
		    		func 		: [
					{
		    			text 	: "請選擇" ,
						val		: "" ,
		    		},
		    		{
		    			text 	: "Symantec Code Sign" ,
						val		: "CS" ,
		    		},
		    		{
		    			text 	: "Symantec Secure Site SSL" ,
						val		: "SSLS" ,
		    		},
		    		{
		    			text 	: "TWCA Secure Site SSL" ,
						val		: "SSLT" ,
		    		}
		    		],
		    		setFirst 	: true ,
		    		selType 	: "dropdown"
		    	},
			},
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['expiryType'],
						value : ['期別']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['price'],
						value : ['售價']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['productStatus'],
						value : ['狀態']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['status'],
						value : ["操作功能"],
						classR: "d-flex" ,
						styleT: "width:100px;",
						button : [
							{
								class : "sip-status-resend rounded-pill" ,
								key : "調整售價" ,
								func : function ( product ){
									this.type = "EDIT";	
									this.selectProduct( product.cpdId )
								}.bind(this),
							},
							{
								class : "sip-status-resend rounded-pill" ,
								key : "下架" ,
								func : function ( product ){
									this.type = "PROCESS_STATUS";	
									this.selectProduct(product.cpdId);
								}.bind(this),
								filter : function( product ){
			                        return product.productStatus==='販售中'
			                    },
							},
							{
								class : "sip-status-resend rounded-pill" ,
								key : "上架" ,
								func : function ( product ){
									this.type = "PROCESS_STATUS";
									this.selectProduct(product.cpdId);
								}.bind(this),
								filter : function( product ){
			                        return product.productStatus==='停售中'
			                    },
							},
						]
					}
				]
			},
		}
	},
	extends : Base ,
	components : {
		DataTable , Input , SelectView , Mask
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
    watch:{
    	"listenChange" : function ( newV , oldV ){
    		if( this.isWatch ) {
    			let cpdType = newV.cpdType ;
    			let expiryType = newV.expiryType ;
    			let _this = this ;
    			if( newV.cpdType != "SELECT" && newV.cpdType != "CREATE" && newV.expiryType != "" ) {
    				this.showLoading(true);
    				this.ApiUtilCMS.get(`/get-cpdId/${cpdType}/${expiryType}`)
					.then(res => {
						if( res.success && res.data.cpdId != "" ) {
							let data = res.data ;
							_this.prodObj.cpdName = data.cpdName ;
							_this.prodObj.price = data.price ;
							_this.prodObj.certApplyType = data.certApplyType ;
							_this.setEditStorage( data ) ;
						} else {
							let def = _this.getDefaultProd();
							_this.prodObj.cpdName = def.cpdName ;
							_this.prodObj.price = def.price ;
							_this.prodObj.certApplyType = def.certApplyType ;
							_this.clearAddSelect() ;
						}
					})
					.finally(function () {
			        	_this.showLoading();
					});
    			} else {
    				let def = this.getDefaultProd();
					this.prodObj.cpdName = def.cpdName ;
					this.prodObj.price = def.price ;
					this.prodObj.certApplyType = def.certApplyType ;
    				this.clearAddSelect() ;
    			}
    		}
    	},
    	"prodObj.otherCpdType" : function ( newV , oldV ){
    		if( newV != "" ){
    			let cpdType = this.cpdTypeArr.filter( ( obj ) => {
    				return obj.val === newV
    			});

    			if( cpdType.length > 0 ) {
    				this.prodObj.cpdType = cpdType[0];
    				this.prodObj.otherCpdType = "";
    			}
    		}
    	}
    },
    computed: {
    	listenChange () {
    		const { cpdType , expiryType } = this.prodObj ;
    		return { cpdType , expiryType } ;
    	}
    },
	methods: {
		init(){
			this.search();
		},
		search(){
			let _this = this ;
			_this.showLoading(true);
			this.ApiUtilCMS.get("/get-cert-products")
			.then((res) => {
				if( res.success ) {
					let data = res.data ;

					let cpdTypeArr = this.cpdTypeArr = this.getCpdTypeList(data.certProducts);

					_this.certTypeList  = data.certProducts;
					_this.$refs.cpdType.setArr(cpdTypeArr, "SELECT");
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		getCpdTypeList(certProducts) {
			let cpdTypeArr = [];
			
			let optionSelect = {
				text 	: "請選擇",
				val		: "SELECT"	
			}
			cpdTypeArr.push(optionSelect);
			
			certProducts.forEach(element => {
				let option = {
					text : element.cpdType,
					val  : element.cpdType
				}
				cpdTypeArr.push(option);
			})
			
			let optionCreate = {
				text 	: "新增憑證類型",
				val		: "CREATE"
			}
			cpdTypeArr.push(optionCreate);

			return cpdTypeArr;
		},
    	selectProduct(cpdId){
    		let _this = this;
			_this.showLoading(true);
			this.ApiUtilCMS.get("/get-cert-product/" + cpdId)
			.then(res => {
				if (res.success) {
					let data = res.data

					if(_this.type === "EDIT") {
						_this.prodObj = {
							cpdType 		: data.cpdType,
							cpdName 		: data.cpdName,
							expiryType 		: data.expiryType,
							price 			: data.price ,
							certApplyType 	: data.certApplyType,
						}
						_this.setEditStorage( data ) ;
						_this.showMask();

					}else {
						let params = data;
						params.previousData = JSON.stringify(data);
						params.productStatus = params.productStatus === "ACTIVE" ? "INACTIVE" : "ACTIVE";
						this.update(params);
					}
				}
			})
			.finally(() => {
				_this.showLoading();
			})
    	},
    	doAdd(){
    		this.isWatch = true ;
    		this.cpdId = "" ;
			this.type = "ADD" ;
    		this.prodObj = this.getDefaultProd();
    		this.showMask();
    	},
    	showMask(){
    		this.$refs.Mask.show();
    	},
    	hideMask(){
    		this.isWatch = false ;
			this.invalids = {};
    		this.prodObj = this.getDefaultProd();
    		this.$refs.Mask.hide();
    	},
    	validate(){
			let invalids = {};
			let prodObj = this.prodObj ;
			let editStorage = this.editStorage ;

			if (this.type === "ADD") {
				if (prodObj.cpdType === "SELECT") {
					invalids["cpdType"] = "請選擇憑證類型"
				}
				if (prodObj.cpdType === "CREATE" && prodObj.otherCpdType.trim() === "") {
					invalids["otherCpdType"] = "請輸入新增憑證類型"
				}
				if (prodObj.cpdType === "CREATE" && prodObj.cpdName.trim() === "") {
					invalids["cpdName"] = "請輸入新增憑證名稱"
				}
				if (prodObj.expiryType === "") {
					invalids["expiryType"] = "請選擇效期"
				}
				if (prodObj.certApplyType === "") {
					invalids["certApplyType"] = "請選擇申請書種類"
				}
			}

			if (!this.isNonZeroNumeric(prodObj.price)) {
				invalids["price"] = "請輸入大於0的數字"
			}

			this.invalids = invalids;
			
			if ( !isEmptyObject(invalids)) {
				return
			}

			let params = {
				cpdType 		: prodObj.cpdType ,
				cpdName 		: prodObj.cpdName.trim() ,
				expiryType 		: prodObj.expiryType ,
				certApplyType 	: prodObj.certApplyType,
				price 			: parseInt(prodObj.price)
			}

			if( this.type === "ADD" && editStorage.cpdId === "" )  {
				
				if( prodObj.cpdType === "CREATE" ) {
					params["cpdType"] = prodObj.otherCpdType.trim() ;
				}

				this.create(params);
			} else {
				params["cpdId"] = editStorage.cpdId ;
				params["productStatus"] = editStorage.productStatus ;
				params["previousData"] = editStorage.previousData ;

				this.update(params);
			}

    	},
		create(params) {
			let _this = this;
			_this.showLoading(true);

			this.ApiUtilCMS.post("/create-cert-product", params)
			.then(res => {
				_this.showLoading();
				if ( res.success ) {
					let data = res.data
					_this.showModel({
						title : data.message,
						message : data.message,
						endFunc : function(){
							_this.hideMask();
							_this.search();
						}
					});
				}
			})
			.finally(() => {
				_this.showLoading();
			})
		},
		update(params) {
			let _this = this;
			_this.showLoading(true);
			this.ApiUtilCMS.post("/update-cert-product", params)
			.then(res => {
				_this.showLoading();
				if ( res.success ) {
					let data = res.data
					_this.showModel({
						title 	: data.message,
						message : data.message,
						endFunc : function(){
							_this.hideMask();
							_this.clearAddSelect();
							_this.search();
						}
					});
				}
			})
			.finally(() => {
				_this.showLoading();
			})
		},
    	getDefaultProd(){
    		return {
    			cpdType 		: "SELECT",
    			otherCpdType 	: "",
				cpdName			: "",
            	expiryType 		: "",
            	price 			: 0,
            	certApplyType 	: "",
    		}
    	},
		isNonZeroNumeric(str) {
			return /^[1-9][0-9]*$/.test(str);
		},
		getDefaultEditStorage(){
			return {
				cpdId 			: "" ,
				previousData 	: "" ,
				productStatus 	: "" 
			}
		},
		setEditStorage( data ){
			this.editStorage = {
				cpdId 			: data.cpdId ,
				previousData 	: JSON.stringify(data) ,
				productStatus 	: data.productStatus 
			}

		},
		clearAddSelect(){
			this.editStorage = this.getDefaultEditStorage();
		}
		
	}
}	
</script>