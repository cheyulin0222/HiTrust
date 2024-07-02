<template> 
  <div class="container-fluid  rounded-top font-weight-bold alert-primary p-2" style="position: relative;">
    <div class = "pb-2 text-center" v-if="title">
      <span class="t-20">{{ title }}</span>
      
      <template v-if = "hasTitleFunc">
        <button type="button" @click.prevent="titleFunc.func" class="btn-danger m-2"
        style="float: right;position: absolute;top: 0;right: 0;border-radius: 8px !important;">
          <span class="mx-2">{{titleFunc.text}}</span>
        </button>
      </template>
    </div>
    <div class="p-3 rounded bg-light m-0 t-16" v-bind:class="mainClass">
      <slot></slot>
    </div>
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
    title : {
      type  : String ,
    },
    mainCenter : {
      type  : Boolean ,
    },
    titleFunc : {
      type : Object ,
      default() {
        return {} ;
      }
    },
    mainRow : {
      type : Boolean ,
      default : true ,
    }
  },
  computed: {
    mainClass(){
      let className = "" ;
      if( this.mainCenter ) {
        className += "justify-content-md-center "
      }
      if( this.mainRow ) {
        className += "row "
      }
      return className ;
    },
    hasTitleFunc(){
      return !isEmpty(this.titleFunc);
    }
  },
  components : {

  },
  methods: {
    
  },
}

</script>