<script>
	import Base from "@/components/Base" ;
	export default {
		data () {
			return {
				input 		: {},
				default 	: {} ,
				selOption 	: {} ,
			}
			
		},
		extends : Base ,
		mounted () {
			let def = this.getDefaultInp(); 
			if( Object.keys( def ).length > 0 ) {
				this.default =  def ;
			}
		},
		methods : {
			updateVal( val , ID ) {
	    		this.input[ID] = val;
	    	},
	    	clear(){
	    		let _this = this ;
	    		Object.keys(_this.default).forEach(function(key){
	    			if( _this.selOption.hasOwnProperty( key ) ) {
	    				let refs = _this.$refs[key] ;
	    				if( refs && refs.clear ) {
	    					refs.clear();
	    				}    				
	    			} else {
	    				_this.input[key] = _this.default[key];
	    			}
	    			
	    		})
	    	},
    		getDefaultInp() {
    			return {} ;
    		},
    		getTypeText(){
    			if( this.type == "EDIT") {	
					return this.$t("Maintenance").toString();
				} else {
					return this.$t("Add").toString() ;
				}
    		}
		}
	}
</script>