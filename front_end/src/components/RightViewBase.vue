<script>
	import Base from "@/components/Base" ;
	import { resetNextPath , getParam } from "@/utils/nextPathUtil"

	export default {
		data(){
			let params = getParam()
			resetNextPath();
			return {
				params : params ,
				previousData : "{}" ,
				isFirstLoad: true,
			}
		},
		extends : Base ,
		watch : {        

	    },
	    methods : {
	    	clearTable(){
	    		this.dataRow = [] ;
	    		this.tableOption.paginate.page = 1 ;
		        this.tableOption.paginate.total = 0 ;
	    	}
	    },
	    //銷毀前
	    beforeUnmount(){
	    	
	    },
	    mounted () {
			
		},
		activated() {
			if( this.isFirstLoad ) {
				this.isFirstLoad = false ;
			} else {
				let params = getParam();
				this.params = params ;
				resetNextPath();
			}
		}
	}
</script>