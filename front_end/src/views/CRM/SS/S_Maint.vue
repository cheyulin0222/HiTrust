
<script>
import Edit from "@/components/CRM/SS/S_Edit"
import Base from "@/components/CRM/SS/Maint_Base" ;
import { resetSalesId } from "@/utils/dataListCrm" ;

export default {
	data(){
		return {
			title 	: "Sales Maintenance" ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['employeeId'],
						value : ['員工編號']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['nameTw'],
						value : ['中文姓名']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['nameEn'],
						value : ['英文姓名']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['extensionNumber'],
						value : ['分機號碼']
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
			delApi 		: "/sales/",
			searchApi	: "/sales/all",
		}
		 
	},
    extends : Base ,
    components : {
    	Edit 
    },
    methods : {
    	reset( data ) {
    		resetSalesId( data ) ;
    	}
    },
}
</script>