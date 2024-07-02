<template>
	<template v-for="( item , index ) in orderList" :key= "getKey( item )" visisble="true" >
		<slot :item="item" :index="index"/>
	</template>
</template>
<script>
import { genNonDuplicateID , strToObjForStyle , htmlEscape } from "@/utils/util"

export default {
	data() {
      return {
      }
  	},
	methods : {
		getKey( itme ){
			if( this.itemKey && this.itemKey != "" ) {
				let keys = this.itemKey.split("+") ;
	          	let result = "" ;
	          	keys.forEach(function(key){
	            	result += itme[key] ? itme[key] : genNonDuplicateID() ;
	          	});
	          	return result
			} else {
	        	return genNonDuplicateID() ;
	        }
      	}
	},
	props:{
		itemKey : {
	        type : String
	    },
	    orderList : {
        	type : [ Array ] , 
        	required : true
      	},
	}
}
</script>