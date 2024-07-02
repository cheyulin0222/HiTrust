<script>
import Mask from "@/components/Mask.vue"
import Base from "@/components/Base" ;

export default {
	data(){
		return {
			input 	: this.getDefaultInp() ,
			type 	: "ADD" ,
			callBack: function(){

			},
		}
		 
	},
    components : {
    	Mask 
  	},
  	extends : Base ,
    methods : {
    	show( type , sales ){
    		this.type = type ;
    		if( type === "EDIT" ) {
    			this.input = sales ;
    		}
	      	this.$refs.Mask.show();
	    },
	    hide( reload ){
	    	this.input = this.getDefaultInp();
	      	this.$refs.Mask.hide();
	      	if( reload ) {
	      		this.callBack();
	      	}  	
	    },
	    save(){
	    	this.loading = true;
			let _this = this ;
			let input = Object.assign({},this.input);
			let id = input.id ;
			delete input.id ;
			if( this.type == "EDIT") {
				this.ApiUtil.put(this.editApi+id,input)
				.then((res) => {
					_this.loading = false;
					if( res.success ) {
						_this.showModel({
			                title : '修改成功',
			                message : "修改成功",
			                endFunc : function(){
			                	_this.hide(true);
			                }
			            });
					}
				})		
			} else {
				this.ApiUtil.post(this.addApi,input)
				.then((res) => {
					_this.loading = false;			
					if( res.success ) {
						_this.showModel({
			                title : '新增成功',
			                message : "新增成功",
			                endFunc : function(){
			                	_this.hide(true);
			                }
			            });
					}
				})
			}
	    },
	    setCallBack( callBack ){
	    	this.callBack = callBack ;
	    }
    }
}
</script>