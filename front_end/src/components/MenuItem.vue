<template>
	<template v-if = "MENU.children">
		<div class="media align-items-center border-0 "
			:class = "getClass()"
			style =" margin-bottom:.2rem; border-radius: 8px ;"
			:data-toggle="collapse"
			:data-target="`#${key}`"
			aria-expanded="false"
			:aria-controls="`#${key}`">
			<div class="d-flex-flow">
				<i v-if ="MENU.icon" class="fa img-fluid onactived pr-md-3"
				:class = "MENU.icon" style="fontSize:24px" />
			</div>
			<div class="media-body">
				<p class="mb-0">
					<span class="d-md-inline-block d-none">{{ getTxt( text ) }}</span>
				</p>
			</div>
			<div  v-if = "setCollapse" class="collapse-icon-plus ml-auto">
				<i class="easypay"></i>
			</div>

		</div>
		<div :id="key" :class="collapse" style="padding-left: 1.25rem;">		
			<MenuItem v-for="item in MENU.children" :key="item.functionId" :MENU="item" :collapse="collapse" />
		</div>
	</template>
	<template v-else-if="hasRoute( key )">
		<router-link :to = "{ name : key  , params : { init : true } }" 
			class="list-group-item list-group-item-action border-0 "
			:class=" active == key ? 'active':''"
			style="padding: 0.75rem 0rem 0.75rem 0.75rem ;border-radius: 8px ;margin-bottom:.2rem;" :id=" key ">
			<div class="d-flex-flow">
				<i v-if ="MENU.icon" class="fa img-fluid onactived pr-md-3"
				:class = "MENU.icon" style="fontSize:24px" />
				<p class="mb-0">
					<span class="d-md-inline-block d-none">{{ getTxt( text ) }}</span>
				</p>
			</div>
		</router-link>
	</template>

</template>
<script>
import MenuItem from "@/components/MenuItem.vue"
import { mapGetters, mapState } from 'vuex';

export default {
	props: ['MENU','collapse'],
	data() {
		return {
			key				: this.MENU.functionId.replace(/\s+/g, ''),
			text 			: this.MENU.menuText ,
			setCollapse 	: this.collapse == 'collapse' ,
		}
	},
	created() {
	    // props are exposed on `this`
	    //console.log(process.env.VUE_APP_MENU_COLLAPSE == 'true' ? "collapse":"")
	},
	methods : {
		getTxt( txt ) {
			return txt.trim().toString()
			//暫時拿掉多國語言
        	//return this.$t(txt.trim()).toString()
        },
        getClass(){
        	//console.log(typeof process.env.VUE_APP_MENU_COLLAPSE)
        	//return process.env.VUE_APP_MENU_COLLAPSE == 'false' ? "alert alert-primary" :"" ;
        	return "alert alert-primary" ;
        },
        setShowHide () {
    		if( this.setCollapse ) {
    			if( this.active == this.key ) {
					$(this.$el.parentElement).collapse('show');
				}
    		}
    	}
	},
	components : {
    	MenuItem
    },
    computed : {
    	...mapGetters( 'menu' , {
	        active : "getMenuActive",
	    }),
    },
    watch : {
    	active : function( new_value, old_value ){
    		//this.setShowHide();
    	}
    },
    mounted() {
    	this.setShowHide();
    }
}	


</script>