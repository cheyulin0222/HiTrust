
<script>
import Edit from "@/components/CRM/SS/BTFT_Edit.vue"
import Base from "@/components/CRM/SS/Maint_Base" ;
import { resetBTFType } from "@/utils/dataListCrm" ;

export default {
	data(){
		return {
			title 	: "Bank Transaction Fee Type Maintenance" ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['name'],
						value : ['銀行交易手續費收費類型名稱']
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
			delApi 		: "/bank-transaction-fee/type/" ,
			searchApi	: "/bank-transaction-fee/type/all" ,
		}
		 
	},
    extends : Base ,
    components : {
    	Edit
    },
    methods : {  	
    	reset( data ) {
    		resetBTFType( data );
    	}
    },
}
</script>