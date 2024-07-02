<template>
  <div v-bind:style="style" @scroll="listContainerScroll" ref="scrollList">
    <div :style="listStyle">
      <div v-for=" item in showList" :key=getKey(item) :style="itemStyle">
        <slot :item="item" ></slot>
      </div>
    </div>
  </div>
</template>
<script>
import { genNonDuplicateID , strToObjForStyle , htmlEscape } from "@/utils/util"

export default {
    data() {
      return {
        style : {
          overflow    : "scroll" ,
          maxHeight   : this.maxHeight ,
        },
        startIndex : 0 ,
        showNum : 15 ,
      }
    },
    watch : {        
      orderList : function ( new_value, old_value ) {
        this.resetScroll();
      },
    },
    computed: {
      endIndex () {
        let end = this.startIndex + this.showNum ;
        if( end > this.orderList.length ) {
          end = this.orderList.length ;
        }
        return end ;
      },
      showList () {
        return this.orderList.slice(this.startIndex , this.endIndex)
      },
      listStyle() {
        let endIndex = this.endIndex;
        return {
          paddingBottom: ( ( this.orderList.length - endIndex ) * this.itemHeight ) + 'px',
          paddingTop: this.startIndex * this.itemHeight + 'px'
        }
      },
      itemStyle(){
        return {
          height : this.itemHeight + 'px'
        }
      }
    },
    methods : {
      setShowNum( num ){
        this.showNum = num ;
      },
      listContainerScroll() {
        let scrollTop = this.$refs.scrollList.scrollTop;
        let startIndex = Math.floor(scrollTop / this.itemHeight);
        if( startIndex < 0 ) {
          startIndex = 0 ;
        }
        this.startIndex = startIndex ;
      },
      setMaxHeight( height ){
        this.style.maxHeight = height ;
      },
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
      },
      resetScroll(){
        this.$refs.scrollList.scrollTop = 0 ;
        this.startIndex = 0 ;
      }
    },
    props:{
      orderList : {
        type : [ Array ] , 
        required : true
      },
      maxHeight : {
        type : String
      },
      itemHeight : {
        type : String
      },
      itemKey : {
        type : String
      }
    }

}
</script>