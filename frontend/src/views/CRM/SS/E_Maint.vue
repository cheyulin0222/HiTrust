
<script>
import Edit from "@/components/CRM/SS/E_Edit.vue"
import Base from "@/components/CRM/SS/Maint_Base" ;
import { resetEnvironmentId } from "@/utils/dataListCrm" ;

export default {
	data(){
		return {
			title 	: "Environment Maintenance" ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['name'],
						value : ['環境名稱']
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
			delApi 		: "/environment/" ,
			searchApi	: "/environment/all" ,
		}
		 
	},
    extends : Base ,
    components : {
    	Edit
    },
    methods : {  	
    	reset( data ) {
    		resetEnvironmentId( data );
    	}
    },
}
</script>