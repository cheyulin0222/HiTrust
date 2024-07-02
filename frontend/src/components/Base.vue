<script>
	export default {
		data(){
			return {
				loading   	: false ,
				intervals 	: [] ,
			}
			 
		},
		watch : {        
	        loading : function ( new_value, old_value ) {
	        	this.showLoading(new_value);
	        },
	    },
	    methods : {
	    	clearInterval2 (Interval){
				let intervals = this.intervals ;
				let index = intervals.indexOf(Interval) ;
				if( index > -1 ) {
					intervals.splice(index, 1);
				}
				clearInterval(Interval);
			},
			setAutoComOff() {
	  			$( this.$el.parentElement ).find('input').each(function( index , input ){
		      		input.autocomplete = 'off';
		      	})
		      	$( this.$el.parentElement ).find('input[type="date"]').each(function( index , input ){
		      		input.max = '9999-12-31';
		      	})
	  		},
	  		setInputNumber() {
	  			$( this.$el.parentElement ).find('input[type=number]').each(function( index , input ){
	        		$(input).on("keypress",function(){
	        			let step = $(this).attr('step');
	        			return (/[\d]/.test(String.fromCharCode(event.keyCode))) || (event.key === "." && typeof step != 'undefined')
	        		});
	        	})  
	  		},
	  		init(){

	  		}
	    },
	    //銷毀前
	    beforeUnmount(){
	    	
	    	let _this = this ;
	    	this.intervals.forEach(function(interval){
	    		_this.clearInterval2(interval); 
	    	})
	    },
	    mounted () {
			this.init();
			this.setAutoComOff();
			this.setInputNumber();
		},
	}
</script>