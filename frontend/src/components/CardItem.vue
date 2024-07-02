<template> 
  <div class="order-0 mb-2" v-bind:class="itemClass">
    <slot></slot> 
  </div>
</template>
<script>
import { isEmpty } from "@/utils/util"

export default {
  data() {

    return{
      
    }
  },

  mounted() {
    
  },
  unmounted () {
    //window.removeEventListener("resize",this.resize);
  },
  watch : {
    
  },
  props : {
    colMd : {
      type  : String ,
    },
  },
  computed: {
    itemClass(){
      let className = "col-md-" ;
      if( this.colMd ) {
        className += this.colMd ;
      } else {
        className += "6" ;
      }
      return className ;
    },
  },
  components : {

  },
  methods: {
    
  },
}

</script>