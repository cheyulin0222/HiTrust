<template> 
  <div ref= "dialog"
  class="modal fade" :id="id"
  tabindex="-1"
  role="dialog"
  aria-labelledby="status-error"
  aria-hidden="true" @click="clickFun()">
  <div class="modal-dialog modal-dialog-centered"
    role="document"  >
    <div class="modal-content" @click.stop="function(){}">
      <div class="modal-header justify-content-center">
        <h5 id="dialogTitle"
        class="modal-title font-weight-bold">{{title}}</h5>
      </div>
      <div class="modal-body">
        <div id="dialogContent"
          v-bind:class="className" style="word-wrap:break-word;" 
          v-html='msg'></div>
      </div>
      <div class="modal-footer border-top-0">
        <button v-if="showOk" type="button" @click.stop="this.$refs.dialog.click()" 
        class="btn btn-outline-danger btn-block"
        data-dismiss="modal">OK</button>
      </div>
    </div>
  </div>
  </div>
</template>
<script>

import { genNonDuplicateID } from "@/utils/util"

export default {
  data() {
    return{
      id          : genNonDuplicateID(),
      title       : "" ,
      bodyHtml    : "" ,
      modal       : null ,
      showOk      : false ,
      className   : "" ,
      msg         : "" ,
      clickOk     : function(){},
    }
  },

  mounted() {
    this.modal = $(this.$refs.dialog);
    if( this.OPTIONS ) {
      let o = this.OPTIONS ;
      if( o.title ) {
        this.title = o.title ;
      }
      if( o.className ) {
        this.className = o.className ;
      }
      if( o.msg ) {
        this.msg = o.msg ;
      }
      if( o.clickOk ) {
        this.clickOk = o.clickOk.bind(this);
      }
    }
    this.init();
  },
  props : {
    OPTIONS : {
      type  : Object ,
    }
  },
  methods: {
    init() {

    },
    show(){
      this.modal.modal('show');
    },
    hide(){
      this.modal.modal('hide');
    },
    clickFun(){
      setTimeout(() => {this.clickOk()},100)
    } 
  },
  watch: {     
    "OPTIONS.msg" : function( new_value, old_value ){
      this.msg = new_value ;
    },
  },
}

</script>