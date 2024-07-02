<template>
  <router-view />
</template>
<script>
import { onMounted } from "vue";
import { checkSession } from "@/utils/user"
export default {
  name: "App",
  setup () {
      onMounted(()=>{
        //console.log("onMounted")
        checkSession();
      })
  },
}
</script>