<script>
import { Options, Vue } from "vue-class-component";
import Base from "@/components/Dialog/DialogBase" ;

export default {
  data () {
    return {
      showOk      : true ,
    }  
  },
  extends : Base ,
  methods: {
    init() {
      let _this = this ;
      this.modal.on('hidden.bs.modal',function(){
        _this.title = '';
        _this.msg = '' ;
      });
    },
    showModel( options ) {
      let title = options.title ;
      let message = options.message ;
      let type = options.type ;
      
      let className = 'alert alert-primary' ;
      if( type ) {
        if( type == 'error') {
          className = 'alert alert-danger'
        } else if( type == 'warning') {
          className = 'alert alert-warning'
        }
      }
      this.className = className;
      this.title = title ;
      this.msg = message ;
      this.show();
      if( options.endFunc ) {
        //this.clickOk = options.endFunc
        this.clickOk = options.endFunc
      } else {
        this.clickOk = function(){

        }
      }
    },
   
  },
}

</script>