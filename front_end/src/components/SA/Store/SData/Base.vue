<script>
import SelectView from "@/components/Select";
import Input from "@/components/Input"
import CardItem from "@/components/CardItem" ;

export default {
	data(){
		let _this = this ;
		return {		
			invalids : {},
			required : true ,
		}
	},
	props : {
		// EDIT/ADD/READONLY
		type : {
			type : String ,
		}
	},
	computed: {
		readOnly() {
			let type = this.type ;
			if( type && type === "READONLY" ) {
				return true ;
			} else {
				return false ;
			}
		},
		readOnlyEdit(){
			let type = this.type ;
			if( type && ( type === "EDIT" || type === "READONLY" ) ) {
				return true ;
			} else {
				return false ;
			}
		}
	},
	components : {
		SelectView , Input , CardItem
	},
	mounted() {

    },
	methods: {
		checkData(){
			return true ;
		},
		checkReqValue( value ){
			return value && value != "" ;
		}
	}
}	
</script>