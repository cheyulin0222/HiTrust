<script>
import Base from "@/components/SA/Store/SData/Base"
import { isEmpty , resetInputtVal } from "@/utils/util";

export default {
	data(){
		return {}
	},
	props : {
		// EDIT/ADD/READONLY
		type : {
			type : String ,
		},
		inputObj : {
			type : Object ,
		},
		acqBank : {
			type : String ,
		},
	},
	extends : Base ,
	computed: {
		
	},
	components : {
		
	},
	created(){
		
	},
	mounted() {
		
    },
	methods: {
		getData(){
			if( this.checkData() ) {
				return JSON.parse(JSON.stringify(this.compObj));
			} else {
				return false ;
			}
		},
		editCopyObj(){
			let defaultCompObj = this.defaultCompObj() ;
			
			if( this.type === "EDIT" ) {
				resetInputtVal(defaultCompObj,this.inputObj);
			}
			return defaultCompObj ;
		},
		defaultComp(){
			return {
				merVisaId 		: "" ,
				merVisaPWD		: "" ,
				merVisaName		: "" ,
				merMasterId		: "" ,
				merMasterPWD	: "" ,
				merMasterName	: "" ,
				merJcbId 		: "" ,
				merJcbPWD		: "" ,
				merJcbName 		: "" ,
				merAeId 		: "" ,
				merAePWD 		: "" ,
				merAeName 		: "" ,
				riskAE 			: "CLOSED" ,
				riskVisa		: "CLOSED" ,
				riskMaster		: "CLOSED" ,
				riskJcb			: "CLOSED" ,
				riskCvc			: "CLOSED" ,
				rtnPan 			: "CLOSED" ,
				panTemp			: "F6L4" ,
				refund			: "CLOSED" ,
				otp 			: "CLOSED" ,
				ots				: "CLOSED" ,
				rtu 			: "CLOSED" ,
				alert			: "CLOSED" ,
				applePay 		: "CLOSED" ,
				googlePay 		: "CLOSED" ,
				samsungPay 		: "CLOSED" ,
			}
		},
		defaultComp2(){
			return {
				riskAE 			: "CLOSED" ,
				riskVisa		: "CLOSED" ,
				riskMaster		: "CLOSED" ,
				riskJcb			: "CLOSED" ,
				riskCvc			: "CLOSED" ,

			}
		}
	}
}	
</script>