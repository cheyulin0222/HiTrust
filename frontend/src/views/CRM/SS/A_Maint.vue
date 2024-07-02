
<script>
import Edit from "@/components/CRM/SS/A_Edit.vue"
import Base from "@/components/CRM/SS/Maint_Base" ;
import { resetAcquirer } from "@/utils/dataListCrm" ;

export default {
	data(){
		return {
			title 	: "Acquire Maintenance" ,
			tableOption : {
				colum : [
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['nameCode'],
						value : ['收單行代碼']
					},
					{
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['nameTw'],
						value : ['收單行中文名稱']
					},
					{	
						classT : "border border-info rounded-0 p-3 text-center alert-primary ",
						type : 'text',
						key : ['nameEn'],
						value : ['收單行英文文名稱']
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
			delApi 		: "/acquirer/" ,
			searchApi	: "/acquirer/all" ,
		}
		 
	},
    extends : Base ,
    components : {
    	Edit
    },
    methods : {  	
    	reset( data ) {
    		resetAcquirer( data );
    	}
    },
}
</script>