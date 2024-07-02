import { createApp } from "vue"

import DialogView from './DialogView.vue';
import LoadingView from './LoadingView.vue';
import $ from 'jquery'
const dialog = {
  dialog    : null,
  Loading   : null,
  install(Vue:any) {
    if ( this.dialog ) {
      Vue.config.globalProperties.showModel = this.dialog.showModel;
      
    } else {
      let instance = createApp(DialogView);
      instance.use({
      install: function(Vue, options){
          Vue.config.globalProperties.$jQuery= $
        }
      });
      let parent = document.createElement("div")
      let bodyDom = document.body
      bodyDom.appendChild(parent)
      this.dialog = instance.mount(parent)
      Vue.config.globalProperties.showModel = this.dialog.showModel;
    }

    if ( this.Loading ) {
      Vue.config.globalProperties.showLoading = this.dialog.showLoading;
      
    } else {
      let instance = createApp(LoadingView);
      instance.use({
      install: function(Vue, options){
          Vue.config.globalProperties.$jQuery= $
        }
      });
      let parent = document.createElement("div")
      let bodyDom = document.body
      bodyDom.appendChild(parent)
      this.Loading = instance.mount(parent)
      Vue.config.globalProperties.showLoading = this.Loading.showLoading;
    }


  }
};
export default dialog
