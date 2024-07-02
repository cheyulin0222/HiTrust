import axios from 'axios' ;

class env { 
	#envData : any ;

    constructor() {        
       
    }


    init( callback ){
    	if( process.env.NODE_ENV === 'development' ) {
    		this.#envData = {
    			AP_NAME                 : process.env.VUE_APP_AP_NAME ,
                MENU_COLLAPSE           : process.env.VUE_APP_MENU_COLLAPSE ,
                API_URL                 : process.env.VUE_APP_API_URL ,
                API_TIMEOUT             : process.env.VUE_APP_API_TIMEOUT ,
                TEST_PATH               : process.env.VUE_APP_TEST_PATH
    		}
            callback.bind(this)();
    	} else {
    		let _this = this ;
            axios.get( '/'+process.env.VUE_APP_AP_NAME+'/getEnv' ) 	    	
			.then(function(response) {
				_this.#envData = response.data ;
                callback.bind(this)();
			});
    	}
    }

    getData(){
    	return this.#envData ;
    }

}

export default env ;