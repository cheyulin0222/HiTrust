
<script>
import Edit from "@/components/CRM/SS/STFBT_Edit.vue"
import Base from "@/components/CRM/SS/Maint_Base" ;
import { resetSTFBType } from "@/utils/dataListCrm" ;

export default {
	data(){
		return {
			title 	: "Store Transaction Fee Billing Type Maintenance" ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['name'],
						value : ['商店交易手續費計費類型名稱']
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
								class : "" ,
								key : "" ,
								icon: "fa-pen" ,
								func : function ( payment ){
									this.doEdit( payment ) ;
								}.bind(this)
							},
							{
								class : "" ,
								key : "" ,
								icon: "fa-trash-can" ,
								func : function ( payment ){
									this.doDel( payment.id );
								}.bind(this)
							},
						]
					}
				],
				endButton : [
					{
						value : [""] ,
						icon: "fa-circle-plus" ,
						func : function ( payment ){
							this.doAdd();
						}.bind(this)
					}
				]	
			},
			delApi 		: "/store-transaction-fee/billing-type/" ,
			searchApi	: "/store-transaction-fee/billing-type/all" ,
		}
		 
	},
    extends : Base ,
    components : {
    	Edit
    },
    methods : {  	
    	reset( data ) {
    		resetSTFBType( data );
    	}
    },
}
</script>