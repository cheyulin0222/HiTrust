<template> 
  <div style="height:100%; width:100%; position:fixed; _position:absolute; top:0;left:0; z-index:1000; filter: alpha(opacity=30);" v-bind:style="dispalyCss" @click="clickOut" ref="maskSpace">
    <div class="modal-dialog modal-dialog-centered" style="max-height:80%;max-width: 80%;margin: 1.75rem auto;" v-bind:style="{width:width}" role="document" >
      <div class="modal-content" style="border:0" v-bind:style="style" ref = "content">
        <slot></slot>
      </div>
    </div>
  </div>
</template>
<script>

import { genNonDuplicateID } from "@/utils/util"

let maxheightStyle = "calc( 100vh - 150px)";

export default {
  data() {
    let style = {
      height    : "auto" ,
      maxHeight : maxheightStyle,
      overflowY : "auto",
    }
    
    if( this.maxHeight ) {
      style["height"] = maxheightStyle ;
    }
    
    if( this.contentBackTran ) {
      style["backgroundColor"] = "transparent";
    }

    return{
      showFlag    : false ,
      opacity     : 0.7 ,
      width       : '100%',
      style : style ,
    }
  },

  mounted() {
    $( this.$el.parentElement ).find('input').each(function( index , input ){
      input.autocomplete = 'off';
    })    
    this.init();
    //window.addEventListener("resize", this.resize);
  },
  unmounted () {
    //window.removeEventListener("resize",this.resize);
  },
  watch : {
    maxHeight : function ( new_value, old_value ) {
      this.resize();
    },
  },
  props : {
    tag : {
      type  : String ,
    },
    contentBackTran : {
      type  : Boolean ,
    },
    clickesc : {
      type  : Boolean ,
    },
    maxHeight : {
      type    : Boolean ,
      default : false
    }
  },
  computed: {
    dispalyCss(){
      if( this.showFlag ) {
        return `display:block;background:rgba(0,0,0,${this.opacity});`
      } else {
        return `display:none;background:rgba(0,0,0,${this.opacity});`
      }
    },
  },
  components : {

  },
  methods: {
    init() {

    },
    show(){
      this.showFlag = true ;
    },
    hide(){
      this.showFlag = false ;
      this.$refs.content.scrollTop = 0 ;
    },
    setOpacity(opacity){
      if( typeof opacity === 'number') {
        this.opacity = opacity ;
      }
    },
    setWidth( width ){
      this.width = width ;
    },
    setMaxWidth( maxWidth ){
      this.maxWidth = maxWidth ;
    },
    resize(){
      let height ;
      if( this.maxHeight ) {
        height = maxheightStyle ;
      } else {
        height = "auto" ; 
      }
      if( this.style.height != height ) {
        this.style.height = height ;
      }
    },
    clickOut(){
      let maskSpace = this.$refs.maskSpace;
      if( event.target == maskSpace && this.clickesc ) {
        this.hide();
      }
    },
    stop(e){
      //e.stopPropagation();
    },
    scroll(){
      let content = this.$refs.content ;
    }
  },
}

</script>