<template>
  <SiderBar/>
  <div id="page-content-wrapper" class="p-md-3">
    <HeaderView/>
    <div class="container-fluid">
      <router-view v-if="isRouterAlive" />
    </div>
  </div>
</template>

<script lang="ts">

import HeaderView from "@/components/Header.vue"; // @ is an alias to /src
import SiderBar from "@/components/SiderBar.vue"; // @ is an alias to /src
import { getAll } from "@/utils/dataListCrm";

export default {
  data(){
    return {
      isRouterAlive : true ,
    }
  },
  components: {
    SiderBar,
    HeaderView
  },
  mounted(){
    window.vue.reloadApp = this.reload.bind(this);
    getAll.call(this);
  },
  methods: {
    reload : function(){
      this.isRouterAlive = false ;
      this.$nextTick(function(){
        this.isRouterAlive = true ;
      })
    }
  },
}

</script>
