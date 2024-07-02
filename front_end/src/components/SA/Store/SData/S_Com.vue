<template>
	<CardItem colMd="6">
		<SelectView :options="selOption.adapterId" :invalidTxt="invalids.adapterId" LabelVal = "收單銀行代碼" @updateVal="updateVal" ref = "adapterId" :readOnly = "readOnlyEdit" :required="required" tooltip="收單銀行"/>
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "merId" LabelVal = "商家代碼" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnlyEdit" :required="required" tooltip="商家代碼" maxlength="5" :canSelect= "true"/>
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "merNameCh" LabelVal = "商家名稱" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnly" :required="required" maxlength="256"/>
	</CardItem>
	<CardItem colMd="6">
		<template v-if="showNickName">
			<Input ID = "nickNameCh" LabelVal = "商家中文暱稱" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnly" :required="required" maxlength="256"/>
		</template>
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "merName" LabelVal = "商家英文名稱" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnly" :required="required" maxlength="25"/>
	</CardItem>
	<CardItem colMd="6">
		<template v-if="showNickName">
			<Input ID = "nickNameEn" LabelVal = "商家英文暱稱" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnly" :required="required" maxlength="256"/>
		</template>
	</CardItem>
	
	<CardItem colMd="6">
		<Input ID = "adminEmail" LabelVal = "管理者電子郵件" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnly"  maxlength="50" :required="!showNickName" />
	</CardItem>
	<CardItem colMd="6">
		<template v-if="showNickName">
			<Input ID = "adminTel" LabelVal = "管理者電話(用,分隔)" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="50"/>
		</template>
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "merTel" LabelVal = "電話" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "merFax" LabelVal = "傳真" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "merAddr" LabelVal = "地址" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "merUrl" LabelVal = "網址" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnly" :required="required" maxlength="256"/>
	</CardItem>
	<CardItem colMd="6">
		<Input ID = "merEmail" LabelVal = "電子郵件" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnly" maxlength="256"/>
	</CardItem>
	<CardItem colMd="6"/>
	<CardItem colMd="6">
		<SelectView :options="selOption.merchantType" :invalidTxt="invalids.merchantType" LabelVal = "交易類型" @updateVal="updateVal" ref = "merchantType" :readOnly = "readOnly" :required="required"/>
	</CardItem>
	<template v-if=" inputObj.merchantType === 'WI' ">
		<CardItem colMd="5">
			<Input ID = "wiMerUrl" LabelVal = "WI 網址(用;隔開)" :input = "inputObj" :invalids = "invalids" :readOnly = "readOnly" :required="required" maxlength="256"/>
		</CardItem>
		<CardItem colMd="1" style="padding-top:32px;">
			<button type="button" @click.prevent="copyWiMerUrl"	class="btn btn-danger btn-block d-flex justify-content-center align-items-center" title="複製WI 網址">
				<i class="fa-solid fa-copy"/>
			</button>
		</CardItem>
	</template>
	<template v-else>
		<CardItem colMd="6">
			<template v-if=" inputObj.merchantType === 'TK' && type === 'EDIT' " >
				<span class="pl-2 mb-2 d-inline-block" style="margin-bottom: 0.5rem;display:inline-block;">
	                商家金鑰檔案下載
	            </span>
	            <div class="d-flex bg-white" style="min-height: 48px;padding: 0.375rem 0.75rem;border-radius: 8px !important;">
		            <div class="store_key_down_link" role="button" style="padding-right:10px;" @click="download( inputObj.merId )">
		            	{{ inputObj.merId + '_PRIV.DER'}}
		            </div>
	        	</div>
			</template>
		</CardItem>
	</template>
	<CardItem colMd="6">
		<SelectView :options="selOption.country" :invalidTxt="invalids.country" LabelVal = "國家" @updateVal="updateVal" ref = "country" :readOnly = "readOnly" :required="required"/>
	</CardItem>
	<CardItem colMd="6">
		<SelectView :options="selOption.templet" :invalidTxt="invalids.templet" LabelVal = "型式" @updateVal="updateVal" ref = "templet" :readOnly = "readOnly" :required="required"/>
	</CardItem>
	<CardItem colMd="6">
		<SelectView :options="selOption.exp10" :invalidTxt="invalids.exp10" LabelVal = "EXP10" @updateVal="updateVal" ref = "exp10" :readOnly = "readOnly" />
	</CardItem>
	
	<CardItem colMd="6">
		<Input ID = "file" type = "file" LabelVal = "商標" :invalids = "invalids" ref = "files" :readOnly = "readOnly" :required="checkFile"/>
	</CardItem>
	<CardItem colMd="6"/>
</template>
<script>
import Base from "@/components/SA/Store/SData/Base";
import { isEmpty , isNotBlank } from "@/utils/util";
import { getCountryList , getAcqBankList , getExp10List , getMerchantTypeList } from "@/utils/dataListSA";

export default {

	data(){
		let _this = this ;
		if( this.type === "ADD" ){
			Object.assign( this.inputObj , this.getDefault() ) ;
		}
		return {
			tempList	: {} ,
			ready       : false ,
			selOption 	: {
				adapterId : {
		    		ID 			: "adapterId" ,
		    		func 		: getAcqBankList.bind(this,false),
		    		selType 	: "dropdown" ,
		    		filter 		: true ,
		    		defVal 		: this.inputObj.adapterId ? this.inputObj.adapterId : ""  ,
		    	},
		    	country : {
		    		ID 			: "country" ,
		    		func 		: getCountryList.bind(this),
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
		    		defVal 		: this.inputObj.country ? this.inputObj.country : ""  ,
		    	},
		    	templet : {
		    		ID 			: "templet" ,
		    		func 		: [{
						val 	: "" ,
						text 	: "請選擇型式" 
						
					}],
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
		    		addNullText	: true ,
		    		filter 		: true ,
		    	},
		    	exp10 : {
		    		ID 			: "exp10" ,
		    		func 		: getExp10List.bind(this),
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
		    		defVal 		: this.inputObj.exp10 ? this.inputObj.exp10 : ""  ,
		    	},
		    	merchantType : {
		    		ID 			: "merchantType" ,
		    		func 		: getMerchantTypeList.bind(this , this.type === "ADD"),
		    		selType 	: "dropdown" ,
		    		setFirst 	: true ,
		    		defVal 		: this.inputObj.merchantType ? this.inputObj.merchantType : ""  ,
		    	}
			},
		}
	},
	props : {
		//帶入的input
    	inputObj : {
			type : Object ,
		},
	},
	extends : Base ,
	components : {

	},
	computed: {
		checkFile(){
			let acqBank = this.inputObj["adapterId"]
			return this.type === "ADD" && ( acqBank === "AP" || acqBank.endsWith("UPOP") ) ;
		},
		showNickName(){
			let acqBank = this.inputObj["adapterId"]
			return acqBank != "AP" && !acqBank.endsWith("UPOP") ;
		}
	},
	watch:{
		"inputObj.country" : function ( newVal , oldVal ) {
			//this.$nextTick(()=>{
				if( this.ready ) {
					//console.log("watch");
					this.changeCountry( newVal );
				}
				
			//})
		},
		"inputObj.adapterId" : function ( newVal , oldVal ) {
			//this.$nextTick(()=>{
				if( this.ready && newVal != "" && this.type == "ADD" && this.inputObj.merId == "" ) {
					let _this = this ;
					_this.showLoading(true);
					this.ApiUtilSA.get("get-last-merid/" + newVal )
					.then((res) => {
						if( res.success ) {
							_this.inputObj.merId = res.data.merId 
						}
					})
					.finally(function () {
			        	_this.showLoading();
					});
				}
			//})
		}
	},
	created(){
		
	},
	mounted() {
		this.init();
    },
	methods: {
		init(){
			this.ApiUtilSA.get("get-templet-options")
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					let tempList = {} ;
					Object.keys(data).forEach(function( key ){
						let arr = data[key] ;
						let result = [{
							val 	: "" ,
							text 	: "請選擇型式" 
							
						}] ;
						for( var i = 0 ; i < arr.length ; i++ ) {
							let item = arr[i] ;
							result.push({
								val 	: item.key ,
								text 	: item.value 
							});
						}
						tempList[key] = result ;
					});
					this.tempList = tempList ;
					this.ready = true ;
					//console.log("read");
					this.changeCountry();
				}
			});
		},
		changeCountry( country ){
			let templet ;
			if( !country ) {
				country = this.inputObj.country ;
				templet = this.inputObj.templet ;
				if( templet == "" || !templet ) {
					templet = null ;
				}
			}

			let arr = this.tempList[country] ;
			if( !arr ) {
				arr = [] ;
				this.showModel({
                    type : "error" , 
                    title : this.title ,
                    message : "取得型式錯誤"
                });
			}

			this.$refs.templet.setArr( arr , templet );
		},
		updateVal( val , ID ){
			this.inputObj[ID] = val ;
		},
		checkData(){
			let invalids = {} ;
			let inputObj = this.inputObj ;
			[{
				key 	: "adapterId" ,
				text 	: "請輸入收單銀行"
			},
			{
				key 	: "merId" ,
				text 	: "請輸入商家代碼"
			},
			{
				key 	: "merNameCh" ,
				text 	: "請輸入商家名稱"
			},
			{
				key 	: "merName" ,
				text 	: "請輸入商家英文名稱"
			},
			{
				key 	: "merchantType" ,
				text 	: "請選擇交易類型"
			},
			{
				key 	: "templet" ,
				text 	: "請選擇型式"
			},
			{
				key 	: "merUrl" ,
				text 	: "請輸入網址"
			}].forEach(function( obj ){
				if( !isNotBlank( inputObj[obj.key] )){
					invalids[obj.key] = obj.text ;
				} else if( obj.key == "merId" ) {
					inputObj[obj.key] = inputObj[obj.key].toUpperCase();
				}
			});

			if( this.showNickName ) {
				if( !isNotBlank( inputObj["nickNameCh"] ) ){
					invalids["nickNameCh"] = "請輸入商家中文暱稱" ;
				}

				if( !isNotBlank( inputObj["nickNameEn"] ) ){
					invalids["nickNameEn"] = "請輸入商家英文暱稱" ;
				}

				/*if( !isNotBlank( inputObj["adminEmail"] ) && !isNotBlank( inputObj["adminTel"] )){
					invalids["adminEmail"] = invalids["adminTel"] = "管理者電子郵件或管理者電話需填一個" ;
				}
			} else {
				if( !isNotBlank( inputObj["adminEmail"] )){
					invalids["adminEmail"] = "請輸入管理者電子郵件" ;
				}*/
			}
			
			if( inputObj["merchantType"] === "WI" &&  !isNotBlank( inputObj["wiMerUrl"] )){
				invalids["wiMerUrl"] = "請輸入WI 網址" ;
			}

			let acqBank =  inputObj["adapterId"] ;
			if( this.checkFile ) {
				let file = this.getFile();
				if( !file ) {
					invalids["file"] = "請上傳商標" ;
				}
			}

			this.invalids = invalids ;
			return isEmpty(invalids);
		},
		getDefault(){
			return {
				adapterId 	: "" ,
				merId 		: "" ,
				merName 	: "" ,
				merNameCh 	: "" ,
				nickNameEn 	: "" ,
				nickNameCh 	: "" ,
				country 	: "ALL" ,
				templet 	: "" ,
				exp10 		: "-2",
				adminEmail 	: "" ,
				adminTel 	: "" ,
				reqSource 	: "" ,
				adminEmail 	: "" ,
				merTel 		: "" ,
				merFax 		: "" ,
				merUrl 		: "https://www.hitrustpay.com.tw" ,
				merEmail 	: "" ,
				merAddr 	: "" ,
				merchantType: "" ,
				discRate 	: "" ,
				wiMerUrl 	: "" ,
			}
		},
		getFile(){
			let file = this.$refs.files.getFile();
			if( file.length > 0 ) {
				return file[0];
			} else {
				return false ;
			}
		},
		download( merId ) {
			let _this = this ;

			_this.showLoading(true);
			
			this.ApiUtilSA.downloadExcel("downlad-priv-der/" + merId , null , {
				
			})
			.finally(function () {
	        	_this.showLoading();
			});
		},
		setinvalids( invalids ) {
			this.invalids = invalids ;
		},
		copyWiMerUrl(){
    		navigator.clipboard.writeText(this.inputObj.wiMerUrl);
    		alert("複製成功!!");
    	},
	}
}	
</script>