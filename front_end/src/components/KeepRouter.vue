<template>
	<div :key="active">
	  <router-view v-slot="{ Component , route}">
	    <keep-alive>
	      <component :is="Component" :key="route.name" v-if="route.meta.keepAlive"/>
	    </keep-alive>
	    <component :is="Component" :key="route.name" v-if="!route.meta.keepAlive"/>
	  </router-view> 
	</div>
</template>
<script>
export default {
	data (){
		return {
			
		}
	},
	computed: {
		active() {
        	return this.$route.meta.active;
    	}
  },
}

</script>