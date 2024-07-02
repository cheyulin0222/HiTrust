<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		憑證查詢-詳細資料
	</h4>
	<div class="p-3">
		<Card title = "訂單資訊" >
			<CardItem colMd="6">
				<Input ID = "orderNum" LabelVal = "訂單編號" :input = "orderInfo" :invalids = "invalids" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "salesId" LabelVal = "服務業務" :input = "orderInfo" :invalids = "invalids" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "payDate" LabelVal = "付款日期" :input = "orderInfo" :invalids = "invalids" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "payType" LabelVal = "付款方式" :input = "orderInfo" :invalids = "invalids" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "totalAmount" LabelVal = "總價(優惠價)" :input = "orderInfo" :invalids = "invalids" :readOnly="true"/>
			</CardItem>
		</Card>
	</div>
	<div class="p-3 mt-2">
		<Card>
			<CardItem colMd="6">
				<Input ID = "saleNo" LabelVal = "銷售編號" :input = "orderInfo" :invalids = "invalids" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "certType" LabelVal = "憑證類別" :input = "orderInfo" :invalids = "invalids" :readOnly="true"/>
			</CardItem>
			<CardItem colMd="6">
				<span class="t-12" style="margin-bottom: 0.5rem; display: inline-block;">&nbsp;</span>
				<Input ID = "salePrice" :input = "orderInfo" :invalids = "invalids" :readOnly="true" />
			</CardItem>
			<CardItem colMd="6">
				<SelectView :options="selOption.status" v-model = "cmsOrderDetail.orderDetailStatus" :invalidTxt="invalids.status" LabelVal = "狀態" ref = "status"/>
			</CardItem>
		</Card>
	</div>
	<div class="p-3 mt-2">
		<Card>
			<CardItem colMd="4">
				<Input ID = "oemNo" LabelVal = "原廠編號" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="10"/>
			</CardItem>
			<CardItem colMd="4">
				<Input ID = "effectiveDate" LabelVal = "核發日期(YYYYMMDD)" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="8"/>
			</CardItem>
			<CardItem colMd="4">
				<Input ID = "expiryDate" LabelVal = "到期日(YYYYMMDD)" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="8"/>
			</CardItem>
		</Card>
	</div>
	<div class="p-3 mt-2">
		<Card title = "公司(機構)申請資料" :titleFunc="loadUserProfileFunc">
			<CardItem colMd="6">
				<Input ID = "nameCh" LabelVal = "公司（機構）中文名稱" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="100"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "nameEn" LabelVal = "公司（機構）英文名稱" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="100"/>
			</CardItem>
			<template v-if=" certApplyType === 'SSLT' ">
				<CardItem colMd="6">
					<Input ID = "ownerName" LabelVal = "公司負責人姓名" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="50"/>
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "regNo" LabelVal = "營利事業（機關）統一編號" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="10"/>
				</CardItem>
			</template>
			<CardItem colMd="6">
				<Input ID = "addrCh" LabelVal = "公司（機構）中文地址" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="500"/>
			</CardItem>
			<CardItem colMd="6" v-if=" certApplyType === 'CS'">
				<Input ID = "addrEn" LabelVal = "公司（機構）英文地址" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="100"/>
			</CardItem>
			<CardItem colMd="6" v-if=" certApplyType === 'SSLT' ">
				<Input ID = "contactAddr" LabelVal = "公司聯絡地址" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="500"/>
			</CardItem>
			<CardItem colMd="6" v-if=" certApplyType === 'SSLS' ">
				<Input ID = "domainName" LabelVal = "網域名稱（Domain Name）" tooltip = "例如：hitrust.com.tw" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="100"/>
			</CardItem>
			<CardItem colMd="12">
				<template v-for="( category , index ) in categoryList">
					<input type="radio" name = "category" :value="category.val" v-model="cmsOrderDetail.industryCategory" />
					&nbsp;{{category.text}}&nbsp;
				</template>
			</CardItem>
		</Card>
	</div>
	<div class="p-3 mt-2">
		<Card title = "憑證相關資訊" >
		
			<template v-if = " certApplyType != 'CS' " >
				<CardItem colMd="6">
					<span class="pl-2 mb-1" style="display: inline-block;">用途</span><br/>
					<input type="radio" name="useType" value="WEBSITE_SERVER" v-model="cmsOrderDetail.useType">&nbsp;網站伺服器&nbsp;
					<input type="radio" name="useType" value="EMAIL_SERVER" v-model="cmsOrderDetail.useType">&nbsp;郵件伺服器&nbsp;
					<input type="radio" name="useType" value="FTP_SERVER" v-model="cmsOrderDetail.useType">&nbsp;FTP伺服器<br/>
					<input type="radio" name="useType" value="OTHER" v-model="cmsOrderDetail.useType">&nbsp;其他&nbsp;
					<input class="form-control text-left bg-white border-0" type="text" name="useOther" style="width:150px;margin:13px;display: inline;" v-model="cmsOrderDetail.useOther">
				</CardItem>
				<CardItem colMd="6">
					<span class="pl-2 mb-1" style="display: inline-block;">Web Server管理軟體</span><br/>
					<input type="radio" name="webServer" value="IIS" v-model="cmsOrderDetail.webServer">&nbsp;IIS&nbsp;
					<select class = "form-control text-left bg-white border-0 mt-0" style="width:100px;display:inline;margin:13px;" name="iISType" v-model="cmsOrderDetail.iISType">
		               <option value="5.0">5.0</option>
		               <option value="6.0">6.0</option>
		               <option value="7.0">7.0</option>
		               <option value="7.5">7.5</option>
		               <option value="8.5">8.5</option>
		           	</select>
					<input type="radio" name="webServer" value="APACHE" v-model="cmsOrderDetail.webServer">&nbsp;Apache&nbsp;
					<input type="radio" name="webServer" value="IBM_HTTP_SERVER" v-model="cmsOrderDetail.webServer">&nbsp;IBM HTTP Server<br/>
					<input type="radio" name="webServer" value="OTHER" v-model="cmsOrderDetail.webServer">&nbsp;其他&nbsp;
					<input type="text" class = "form-control text-left bg-white border-0" name="webServerOther" style="width:150px;display: inline;" v-model="cmsOrderDetail.webServerOther">
				</CardItem>
			</template>
			
			<CardItem colMd="6" v-if=" certApplyType === 'CS' ">
				<input type="radio" name="csType" value="MICROSOFT_AUTHENTICODE" v-model="cmsOrderDetail.csType">&nbsp;Microsoft Authenticode&nbsp;
				<input type="radio" name="csType" value="MICROSOFT_OFFICE_AND_VBA" v-model="cmsOrderDetail.csType">&nbsp;Microsoft Office and VBA&nbsp;
				<input type="radio" name="csType" value="SUN_JAVA_SIGNING_DIGITAL_ID" v-model="cmsOrderDetail.csType">&nbsp;Sun Java Signing Digital ID&nbsp;
				<input type="radio" name="csType" value="ADOBE_AIR" v-model="cmsOrderDetail.csType">&nbsp;Adobe AIR&nbsp;
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "commonName" LabelVal = "Common Name 憑證申請完整域名" tooltip = "例：ssl.hitrust.com.tw" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="100"/>
			</CardItem>
			<CardItem colMd="6" v-if=" certApplyType === 'SSLS'">
				<Input ID = "orgNameEn" LabelVal = "Organization公司英文全名" tooltip = "例：HiTRUST, Inc." :input = "cmsOrderDetail" :invalids = "invalids" maxlength="100"/>
			</CardItem>
			<CardItem colMd="6" v-if=" certApplyType != 'SSLT'">
				<Input ID = "orgUnitName" LabelVal = "Organization Unit部門名稱" tooltip = "例：HiTRUST, Inc." :input = "cmsOrderDetail" :invalids = "invalids" maxlength="100"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "locality" LabelVal = "Locality公司所在地" tooltip = "例：Taipei" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="20"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "state" LabelVal = "State" tooltip = "例：Taiwan" :input = "cmsOrderDetail" :invalids = "invalids" :IDRandom = "true" maxlength="20"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "country" LabelVal = "Country" tooltip = "例：TW" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="20"/>
			</CardItem>
			<CardItem colMd="12">
				<label for="csr" class="pl-2 mb-1">CSR</label>
				<textarea  type="text" name="csr" id = "csr" v-model="cmsOrderDetail.csr" style="height: 100px;" 
				class="form-control text-left bg-white border-0 " :class="{'is-invalid': !!isCSRInvalid}"/>
				<div class="invalid-feedback" >
					請輸入合法的CSR內容
				</div>
				<button type="button" @click.prevent="ajaxValidateCSR"
					class="btn-danger" style="margin-top:10px;border-radius: 8px !important;padding:5px;width:100%">
					<span class="mx-2">驗證(自動帶入CSR相關資訊)</span>
				</button>
			</CardItem>
			<CardItem colMd="12">
				<span>*請根據您的伺服器軟體，查看產生CSR的詳細方法，如果有任何問題，請洽網際威信服務業務：02-2658-6000#505</span>
			</CardItem>
		</Card>
	</div>
	<div class="p-3 mt-2">
		<Card title = "聯絡資料" :titleFunc="loadUserContactProfileFunc">
		
			<CardItem colMd="12" class = "t-18">
				業務聯絡人
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "salesNameCh" LabelVal = "姓名" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="20"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "salesDeptNameCh" LabelVal = "部門名稱" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="50"/>
			</CardItem>
			<CardItem colMd="6">
				 <span class="pl-2 mb-2 d-inline-block">
            		電話
        		</span>
        		<div class = "row pl-3 pr-3 align-items-center">
					<input type="text" name="salesTel1" class = "form-control text-left bg-white border-0 mr-2" style="width:50px;" v-model = "cmsOrderDetail.salesTels[0]"  maxlength="3"/>-
					<input type="text" name="salesTel2" class = "form-control text-left bg-white border-0 ml-2 mr-2" style="width:170px;" v-model = "cmsOrderDetail.salesTels[1]" maxlength="10"/>
					#
					<input type="text" name="salesTel3" class = "form-control text-left bg-white border-0 ml-2" style="width:70px;" v-model = "cmsOrderDetail.salesTels[2]" maxlength="5"/>
				</div>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "salesEmail" LabelVal = "電子郵件地址" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="100"/>
			</CardItem>
			<CardItem colMd="12" class = "t-18">
				技術聯絡人
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "techNameCh" LabelVal = "姓名" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="20"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "techDeptNameCh" LabelVal = "部門名稱" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="50"/>
			</CardItem>
			<CardItem colMd="6">
				 <span class="pl-2 mb-2 d-inline-block">
            		電話
        		</span>
        		<div class = "row pl-3 pr-3 align-items-center">
					<input type="text" name="techTel1" class = "form-control text-left bg-white border-0 mr-2" style="width:50px;" v-model = "cmsOrderDetail.techTels[0]" maxlength="3"/>-
					<input type="text" name="techTel2" class = "form-control text-left bg-white border-0 ml-2 mr-2" style="width:170px;" v-model = "cmsOrderDetail.techTels[1]" maxlength="10"/>
					#
					<input type="text" name="techTel3" class = "form-control text-left bg-white border-0 ml-2" style="width:70px;" v-model = "cmsOrderDetail.techTels[2]" maxlength="5"/>
				</div>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "techEmail" LabelVal = "電子郵件地址" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="100"/>
			</CardItem>
			<CardItem colMd="12" class = "t-18">
				帳務聯絡人
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "fincNameCh" LabelVal = "姓名" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="20"/>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "fincDeptNameCh" LabelVal = "部門名稱" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="50"/>
			</CardItem>
			<CardItem colMd="6">
				<span class="pl-2 mb-2 d-inline-block">
            		電話
        		</span>
				<div class = "row pl-3 pr-3 align-items-center">
					<input type="text" name="fincTel1" class = "form-control text-left bg-white border-0 mr-2" style="width:50px;" v-model = "cmsOrderDetail.fincTels[0]" maxlength="3"/>-
					<input type="text" name="fincTel2" class = "form-control text-left bg-white border-0 ml-2 mr-2" style="width:170px;" v-model = "cmsOrderDetail.fincTels[1]" maxlength="10"/>
					#
					<input type="text" name="fincTel3" class = "form-control text-left bg-white border-0 ml-2" style="width:70px;" v-model = "cmsOrderDetail.fincTels[2]" maxlength="5"/>
				</div>
			</CardItem>
			<CardItem colMd="6">
				<Input ID = "fincEmail" LabelVal = "電子郵件地址" :input = "cmsOrderDetail" :invalids = "invalids" maxlength="100"/>
			</CardItem>
		</Card>
	</div>
	<div class="row mt-2 mb-5">
		<div class="col-md-4 offset-md-2 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="doSave"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">確認送出</span>
				</button>
			</div>
		</div>
		<div class="col-md-4 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="goBack"
				class="btn btn-secondary  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">返回</span>
				</button>
			</div>
		</div>
	</div>
	<div class="p-3">
		文件管理
		<div class="border-0 pt-3">
			<DataTable ref="table1"
			:dataRow="dataRow"
			:options ="tableOption"/>
		</div>
	</div>
	<Mask ref = "Mask">
		<div class="p-3">
			<div class="container-fluid  rounded-top font-weight-bold alert-primary p-2">
				<div class = "p-2 text-center ">
					上傳檔案
				</div>				
				<div class="row p-3 rounded bg-light m-0 justify-content-md-center">
					<div class="order-0 col-md-7">
						<Input ID = "FILE" type = "file" LabelVal = "選擇您的檔案(jpg\pdf)" :invalids = "invalids" ref = "files"/>
					</div>
					<div class="order-0 col-md-7">
						<SelectView :options="selOption.type" :invalidTxt="invalids.type" LabelVal = "檔案類別"
						v-model = "updateFile.type" ref = "type" :required="true" :IDRandom = "true"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row mt-2 mb-5">
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
					<button type="button" @click.prevent="saveNego"
					class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
						<span class="mx-2">確認</span>
					</button>
				</div>
			</div>
		</div>
	</Mask>

	<!-- Modal -->
    <div class="modal fade" id="fileModal" tabindex="-1" role="dialog" aria-labelledby="imageModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered" role="document" :style="isPdf ? 'max-width: 70vw;' : ''">
        <div class="modal-content" :style="isPdf ? 'height: 90vh;' : ''">
        	<div class="modal-body" style="padding: 0;">
				<img v-if="isImage" :src="fileUrl" alt="Fetched Image" class="img-fluid" @load="adjustModalSize"/>
				<iframe v-if="isPdf" :src="fileUrl" style="width: 100%; height: 100%;"></iframe>
			</div>
        </div>
      </div>
    </div>

</template>
<script>
import DataTable from '@/components/Table/DataTable.vue' ;
import Base from "@/components/RightViewBase" ;
import Mask from "@/components/Mask" ;
import Input from "@/components/Input"
import SelectView from "@/components/Select" ;
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import { getCategoryList } from "@/utils/dataListCMS" ;
import { isEmptyObject } from "@/utils/util";

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
			fileUrl: '',
      		isImage: false,
      		isPdf: false,
			isCSRInvalid : false,
			previousData : "",
			selOption 	: {
				status : {
					ID 			: "status" ,
		    		func 		: [{
		    			text 	: "待上傳文件" ,
						val		: "PENDING" ,
		    		},
		    		{
		    			text 	: "審核中" ,
						val		: "UNDER_REVIEW" ,
		    		},
		    		{
		    			text 	: "已生效" ,
						val		: "EFFECTIVE" ,
		    		},
					{
		    			text 	: "已過期" ,
						val		: "EXPIRED" ,
		    		}] ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown" ,
				},
				type : {
					ID 			: "type" ,
		    		func 		: [{
		    			text 	: "請選擇" ,
						val		: "" ,
		    		},
		    		{
		    			text 	: "營利事業登記證" ,
						val		: "BUSINESS_REGISTRATION" ,
		    		},
		    		{
		    			text 	: "公司變更登記表" ,
						val		: "COMPANY_CHANGE_REGISTRATION_FORM" ,
		    		},
		    		{
		    			text 	: "電話帳單" ,
						val		: "PHONE_BILL" ,
		    		},
		    		{
		    			text 	: "憑證核發資訊圖檔(jpg)" ,
						val		: "CERTIFICATE_ISSUANCE_INFO_IMAGE" ,
		    		}] ,
		    		setFirst 	: true ,
		    		selType 	: "dropdown" ,
				},
			},
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						styleT : "width:80px;" ,
						key : ['doctTypeCh'],
						value : ['文件名稱'] ,
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['uploadDttm'],
						styleT : "width:300px;" ,
						value : ['上傳時間']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'button',
						key : ['status'],
						value : [""],
						classR: "d-flex" ,
						styleT: "width:100px;",
						button : [
							{
								class : "sip-status-resend rounded-pill" ,
								key : "檢視" ,
								func : function ( payment ){
									this.doView(payment);
								}.bind(this)
							},
							{
								class : "sip-status-resend rounded-pill" ,
								key : "下載" ,
								func : function ( payment ){
									this.doDownload(payment);
									// alert("下載")
								}.bind(this)
							}
						]
					}

				] ,
				endButton : [
			        {
			            value 	: [] ,
			            icon 	: "fa-cloud-arrow-up" ,
			            func 	: function ( payment ){
			                this.showMask()
			            }.bind(this)
			        }
			    ]
			},
			fileContent: null,
			orderInfo : this.getDefaulOrderInfo(),
			cmsOrderDetail : this.getDefaultCmsOrderDetail(),
			updateFile : {
				type : "" ,
			},
			loadUserProfileFunc : {
				func : this.loadUserProfile.bind(this),
				text : "帶入會員資料" ,
			},
			loadUserContactProfileFunc : {
				func : this.loadUserContactProfile.bind(this),
				text : "帶入會員資料" ,
			},
			...getDefault(),
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
		DataTable , Mask , Input , SelectView , Card , CardItem
	},
	methods: {
		goBack(){
			this.$router.back();
		},
		init(){
			this.search();
		},
		search() {
			let _this = this ;
			_this.showLoading(true);
			this.ApiUtilCMS.get("/get-cert-detail/" + this.params.orderDtlNo)
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					_this.orderInfo = {
						orderNum 	: data.orderNo,
						salesId 	: data.salesName,
						payDate 	: data.payDate,
						payType 	: data.payType,
						totalAmount : data.amount,
						saleNo 		: data.saleNo,
						certType 	: data.cpdType,
						salePrice 	: data.price,
					}

					_this.$refs.status.setDefVal(data.orderDetailStatus);
				
					_this.cmsOrderDetail = {
						orderDetailStatus	: data.orderDetailStatus,
						orderDtlNo			: data.orderDtlNo,
						oemNo 				: data.oemNo,
						effectiveDate 		: data.effectiveDate,
						expiryDate 			: data.expiryDate,
						nameCh 				: data.nameCh,
						nameEn 				: data.nameEn,
						ownerName 			: data.ownerName,
						regNo 				: data.regNo,
						addrCh 				: data.addrCh,
						addrEn				: data.addrEn,
						contactAddr			: data.contactAddr,
						domainName 			: data.domainName,
						industryCategory 	: data.industryCategory,
						useType 			: data.useType,
						useOther 			: data.useOther,
						webServer 			: data.webServer,
						webServerOther 		: data.webServerOther,
						csType 				: data.csType,
						commonName 			: data.commonName,
						orgNameEn 			: data.orgNameEn,
						orgUnitName 		: data.orgUnitName,
						locality 			: data.locality,
						state 				: data.state,
						country 			: data.country,
						csr 				: data.csr,
						salesNameCh 		: data.salesNameCh,
						salesDeptNameCh 	: data.salesDeptNameCh,
						salesTel 			: data.salesTel,
						salesEmail			: data.salesEmail,
						techNameCh 			: data.techNameCh,
						techDeptNameCh 		: data.techDeptNameCh,
						techTel 			: data.techTel,
						techEmail 			: data.techEmail,
						fincNameCh 			: data.fincNameCh,
						fincDeptNameCh 		: data.fincDeptNameCh,
						fincTel 			: data.fincTel,
						fincEmail 			: data.fincEmail,
						userId				: data.userId
					}

					_this.cmsOrderDetail.previousData = JSON.stringify(_this.cmsOrderDetail);
					_this.cmsOrderDetail.salesTels = _this.getTels(data.salesTel);
					_this.cmsOrderDetail.techTels = _this.getTels(data.techTel);
					_this.cmsOrderDetail.fincTels = _this.getTels(data.fincTel);

					if ( data.webServer === 'IIS' ) {
						_this.cmsOrderDetail.iISType = data.webServerOther;
						_this.cmsOrderDetail.webServerOther = "" ;
					} else {
						_this.cmsOrderDetail.iISType = "5.0";
					}

					_this.certApplyType = data.certApplyType;

					data.docs.forEach(e => {
						switch (e.docType) {
							case "BUSINESS_REGISTRATION_CERTIFICATE":
								e.doctTypeCh = "營利事業登記證";
								break;
							case "COMPANY_CHANGE_REGISTRATION_FORM":
								e.doctTypeCh = "公司變更登記表";
								break;
							case "TELEPHONE_BILL":
								e.doctTypeCh = "電話帳單";
								break;
							case "CERTIFICATE_ISSUANCE_INFO_IMAGE":
								e.doctTypeCh = "憑證核發資訊圖檔";
								break;
							default :
								e.doctTypeCh = "";
						}
					});

					_this.dataRow = data.docs;

					_this.categoryList = getCategoryList();

				}
			})
			.finally(function () {
				_this.showLoading();
			});
		},
		loadUserProfile(){
			let _this = this ;
			_this.showLoading(true);
			this.ApiUtilCMS.get("/get-user-profile/" + this.cmsOrderDetail.userId)
			.then((res) => {
				if( res.success ) {
					let data = res.data ;
					
					_this.cmsOrderDetail.nameCh = data.nameCh;
					_this.cmsOrderDetail.nameEn = data.nameEn;
					_this.cmsOrderDetail.addrCh = data.addrCh;
					_this.cmsOrderDetail.addrEn = data.addrEn;
				}
			})
			.finally(function () {
				_this.showLoading();
			});
		},
		loadUserContactProfile(){
			let _this = this ;
			_this.showLoading(true);
			this.ApiUtilCMS.get("/get-user-contact-profile/" + this.cmsOrderDetail.userId)
			.then((res) => {
				if( res.success ) {
					let data = res.data.userContactProfile;

					data.forEach(element => {
						if (element.contactType === "SALE") {
							_this.cmsOrderDetail.salesNameCh 	= element.nameCh;
							_this.cmsOrderDetail.salesDeptNameCh= element.deptNameCh;
							_this.cmsOrderDetail.salesTel 		= element.tel;
							_this.cmsOrderDetail.salesEmail		= element.email;
							_this.cmsOrderDetail.salesTels		= _this.getTels( element.tel );
						}else if (element.contactType === "TECH") {
							_this.cmsOrderDetail.techNameCh 	= element.nameCh;
							_this.cmsOrderDetail.techDeptNameCh = element.deptNameCh;
							_this.cmsOrderDetail.techTel 		= element.tel;
							_this.cmsOrderDetail.techEmail 		= element.email;
							_this.cmsOrderDetail.techTels		= _this.getTels( element.tel );
						}else if (element.contactType === "FINC") {
							_this.cmsOrderDetail.fincNameCh 	= element.nameCh;
							_this.cmsOrderDetail.fincDeptNameCh = element.deptNameCh;
							_this.cmsOrderDetail.fincTel 		= element.tel;
							_this.cmsOrderDetail.fincEmail 		= element.email;
							_this.cmsOrderDetail.fincTels		= _this.getTels( element.tel );
						}
					})
				}
			})
			.finally(function () {
				_this.showLoading();
			});
		},
		ajaxValidateCSR(){
			let params = {
				csr : this.cmsOrderDetail.csr
			}

			if (!this.isValidCSR(params.csr)) {
				this.isCSRInvalid = true;
				return 
			}

			this.isCSRInvalid = false;

			let _this = this ;
			_this.showLoading(true);
			this.ApiUtilCMS.post("/verify-csr", params)
			.then((res) => {
				if( res.success ) {
					let data = res.data;

					_this.cmsOrderDetail.commonName 	= data.commonName;
					_this.cmsOrderDetail.orgNameEn 		= data.orgNameEn;
					_this.cmsOrderDetail.orgUnitName 	= data.orgUnitName;
					_this.cmsOrderDetail.locality 		= data.locality;
					_this.cmsOrderDetail.state 			= data.state;
					_this.cmsOrderDetail.country 		= data.country;
				}
			})
			.finally(function () {
				_this.showLoading();
			});
		},
		doSave(){
			let params = this.cmsOrderDetail;
			params.salesTel = this.getTel(this.cmsOrderDetail.salesTels);
			params.techTel = this.getTel(this.cmsOrderDetail.techTels);
			params.fincTel = this.getTel(this.cmsOrderDetail.fincTels);

			let invalids = {};
			if (params.effectiveDate && !this.isValidDate(params.effectiveDate)) {
				invalids["effectiveDate"] = "請輸入合法的付款日期"
			}
			if (params.expiryDate && !this.isValidDate(params.expiryDate)) {
				invalids["expiryDate"] = "請輸入合法的到期日"
			}
			if (params.fincTel.length > 20) {
				invalids["expiryDate"] = "請輸入合法的到期日"
			}
			this.invalids = invalids;
			if ( !isEmptyObject(invalids)) {
				return
			}
			
			let _this = this;
			_this.showLoading(true);
			this.ApiUtilCMS.post("/update-order-detail", params)
			.then(res => {
				_this.showLoading();
				if ( res.success ) {
					
					let data = res.data
					_this.showModel({
						title : data.message,
						message : data.message,
						// endFunc : function(){
						// 	_this.search();
						// }
					});
				}
			})
			.finally(() => {
				_this.showLoading();
			})
		},
		showMask(){
			this.invalids = {};
			this.$refs.Mask.show();
		},
		hideMask(){
			this.invalids = {};
			this.getDefaultUpload();
			this.$refs.Mask.hide();
		},
		getDefaulOrderInfo(){
			return {
				orderNum 	: "" ,
				salesId 	: "" ,
				payDate 	: "" ,
				payType 	: "" ,
				totalAmount : "" ,
				saleNo 		: "" ,
				certType 	: "" ,
				salePrice 	: "" ,
				status 		: "" ,
			}
		
		},
		getDefaultCmsOrderDetail(){
			return {
				oemNo 			: "" ,
				effectiveDate 	: "" ,
				expiryDate 		: "" ,
				nameCh 			: "" ,
				nameEn 			: "" ,
				ownerName 		: "" ,
				regNo 			: "" ,
				addrCh 			: "",
				addrEn			: "" ,
				domainName 		: "" ,
				industryCategory: "" ,
				useType 		: "" ,
				useOther 		: "" ,
				webServer 		: "" ,
				iISType 		: "" ,
				webServerOther 	: "" ,
				csType 			: "" ,
				commonName 		: "" ,
				orgNameEn 		: "" ,
				orgUnitName 	: "" ,
				locality 		: "" ,
				state 			: "" ,
				country 		: "" ,
				csr 			: "" ,
				salesNameCh 	: "",
				salesDeptNameCh : "" ,
				salesTels 		: ["","",""] ,
				salesEmail		: "",
				techNameCh 		: "",
				techDeptNameCh 	: "" ,
				techTels 		: ["","",""] ,
				techEmail 		: "",
				fincNameCh 		: "",
				fincDeptNameCh 	: "" ,
				fincTels 		: ["","",""] ,
				fincEmail 		: "",
			}
		
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
			return result ;
		},
		getTel(tels) {
			return tels[0] + "-" + tels[1] + "#" + tels[2];
		},
		saveNego(){
			const file = this.$refs.files.getFile();

			let invalids = {};

			if (!file.length) {
				invalids["FILE"] = "請上傳檔案";
			}else {
				const fileName = file[0].name;
				const fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
				if (!this.isValidFileExtension(fileExtension)) {
					invalids["FILE"] = "請上傳jpg或pdf檔";
				}
			}
			if (this.updateFile.type === "") {
				invalids["type"] = "請選擇檔案類別";
			}
			
			this.invalids = invalids;
			if ( !isEmptyObject(invalids)) {
				return
			}

			const form = new FormData();

			form.append("orderDtlNo", this.cmsOrderDetail.orderDtlNo);
			form.append("type", this.updateFile.type);
			form.append("img", file[0]);
			form.append("userId", this.cmsOrderDetail.userId);

			let _this = this ;
			_this.showLoading(true);

			this.ApiUtilCMS.post("/upload-file", form)
			.then((res) => {
				if( res.success ) {
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
			.finally(function () {
				_this.showLoading();
			});
		},
		isValidCSR(str) {
			const CSR_BEGIN = "-----BEGIN NEW CERTIFICATE REQUEST-----";
			const CSR_END	= "-----END NEW CERTIFICATE REQUEST-----";

			if (str == null || !str.startsWith(CSR_BEGIN) || !str.endsWith(CSR_END)) {
				return false;
			}
			return true;
		},
		doView(payment) {
			let params = {
				orderDtlNo 	: this.cmsOrderDetail.orderDtlNo,
				type 		: payment.docType,
				action		: "VIEW"
			}

			let _this = this ;
			_this.showLoading(true);
			this.ApiUtilCMS.downloadView("/view-or-download-file", params)
			.then((res) => {
				if( res.success ) {
					
					if (res.contentType.indexOf('application/pdf') > -1) {
						_this.isImage = false;
              			_this.isPdf = true;
					}else if (res.contentType.indexOf('image/jpeg') > -1) {
						_this.isImage = true;
              			_this.isPdf = false;
					}
					_this.fileUrl = res.data;
					$('#fileModal').modal('show');
				}
			})
			.finally(function() {
				_this.showLoading();
			})
		},
		doDownload(payment) {
			let params = {
				orderDtlNo 	: this.cmsOrderDetail.orderDtlNo,
				type 		: payment.docType,
				action		: "DOWNLOAD"
			}

			let _this = this ;
			_this.showLoading(true);
			this.ApiUtilCMS.downloadExcel("/view-or-download-file", params)
			.finally(function() {
				_this.showLoading();
			})
		},
		adjustModalSize() {
			const img = document.querySelector('#fileModal .modal-body img');
			const modalDialog = document.querySelector('#fileModal .modal-dialog');
			modalDialog.style.maxWidth = img.width + 'px';
			modalDialog.style.maxHeight = img.height + 'px';
		},
		isValidDate(str) {
			const regex = /^\d{4}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])$/;
			return regex.test(str);
		},
		isValidFileExtension(fileExtension) {
			const validExtensions = ['pdf', 'jpeg', 'jpg'];
			return validExtensions.includes(fileExtension.toLowerCase());
		},
		getDefaultUpload() {
			this.$refs.files.clearFile();
			this.updateFile.type = "";
		}
	},
	mounted () {
		$('#fileModal').on('hidden.bs.modal', () => {
			this.fileUrl = '';
			this.isImage = false;
			this.isPdf = false;
		});
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
