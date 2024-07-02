<template>
  Error
</template>

<script lang="ts">

import HeaderView from "@/components/Header.vue"; // @ is an alias to /src
import SiderBar from "@/components/SiderBar.vue"; // @ is an alias to /src

const getDefaultCom = () => {
  return {
    taxIdNumber   : "" ,
    companyName   : "" ,
    url       : "" ,
    address     : "" ,
    phone       : "" ,
    fax       : "" ,
    salesId     : "" ,
    categoryId    : "" ,
  }
}

export default {
  props : {
    ErrorObj : {
      type : String ,
      default : JSON.stringify(getDefaultCom()),
    }
  },
  computed : {
    error() {
      console.log(JSON.parse(this.ErrorObj));
      return JSON.parse(this.ErrorObj);
    }
  }
}

</script>
