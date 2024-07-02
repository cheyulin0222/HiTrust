<template>
	<!-- Sidebar -->
	<div id="sidebar-wrapper"
		v-show="visible"
		class="bg-white sidebar-wrapper-scrollbar"
		v-cloak
		:class="elClass"
		style=" padding-left:0.5rem;padding-right: 0,5rem;overflow-y: hidden;">
		<div class="sidebar-heading" style="min-height: 100px">
			<img src="@/assets/img/logo_hitrust.svg"
				class="img-fluid" />
		</div>
		<ul class="nav nav-tabs siderBar" id="myTab" role="tablist">
			<li v-if=" showCRM " class="nav-item" role="presentation">
    			<a class="nav-link" :class=" getSystemType == 'CRM' ? 'active':''" id="CRM-tab" data-toggle="tab" data-target="#CRM" type="button" role="tab" aria-controls="CRM" aria-selected="true">CRM</a>
  			</li>
  			<li v-if=" showSA " class="nav-item" role="presentation">
    			<a class="nav-link" :class=" getSystemType == 'SA' ? 'active':''" id="SA-tab" data-toggle="tab" data-target="#SA" type="button" role="tab" aria-controls="SA" aria-selected="true">SA</a>
  			</li>
  			<li v-if=" showCMS " class="nav-item" role="presentation">
    			<a class="nav-link" :class=" getSystemType == 'CMS' ? 'active':''" id="CMS-tab" data-toggle="tab" data-target="#CMS" type="button" role="tab" aria-controls="CMS" aria-selected="true">憑證</a>
  			</li>
  			
  		</ul>
  		<div class="siderBar-content">
	  		<div v-if=" showCRM || showSA || showCMS " class="tab-content p-0 " id="myTabContent" style="background: #fff!important;padding-top:10px!important;border:0px; ">
	  			<div v-if=" showCRM " class="tab-pane fade " :class=" getSystemType == 'CRM' ? 'show active':''" 
	  			id="CRM" role="tabpanel" aria-labelledby="CRM-tab">
					<div class="list-group flex-md-column flex-row flex-fill">
						<div v-for="item in CRM" class = "mobile-hide" >
							<MenuItem  :key="item.functionId" :MENU="item" :collapse="collapse"/>
						</div>	
					</div>
				</div>
				<div v-if=" showSA " class="tab-pane fade " :class=" getSystemType == 'SA' ? 'show active':''" 
	  			id="SA" role="tabpanel" aria-labelledby="SA-tab">
					<div class="list-group flex-md-column flex-row flex-fill">
						<div v-for="item in SA" class = "mobile-hide" >
							<MenuItem  :key="item.functionId" :MENU="item" :collapse="collapse"/>
						</div>	
					</div>
				</div>
				<div v-if=" showCMS " class="tab-pane fade " :class=" getSystemType == 'CMS' ? 'show active':''" 
	  			id="CMS" role="tabpanel" aria-labelledby="CMS-tab">
					<div class="list-group flex-md-column flex-row flex-fill">
						<div v-for="item in CMS" class = "mobile-hide" >
							<MenuItem  :key="item.functionId" :MENU="item" :collapse="collapse"/>
						</div>	
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>

import MenuItem from "@/components/MenuItem.vue";
import { mapGetters, mapState } from 'vuex';

export default {
	data() {
		let env = this.env.getData();

		return {
			TEST : sessionStorage.getItem("USER")['TEST'],
	        visible: true,
	        elClass: {
	            'd-none': false,
	        },
	        apName: '',
	        pagePath: '',
	        screenWidth: document.body.clientWidth,
	        local: '',
	        collapse : env.MENU_COLLAPSE == 'true' ? "collapse":"",
    	}
    },
    
    components : {
    	MenuItem
    },
    computed: {
    	SA () {
    		return this.permissions.sa  ;
    	},
    	CRM () {
    		return this.permissions.crm ;
    	},
    	CMS () {
    		return this.permissions.cms ;
    	},
    	showSA () {
    		return this.SA.length > 0  ;
    	},
    	showCRM () {
    		return this.CRM.length > 0 ;
    	},
    	showCMS () {
    		return this.CMS.length > 0 ;
    	},
    	getSystemType () {
    		let systemType = this.systemType ;
    		if( systemType == "" ) {
    			if( this.showCRM ) {
    				systemType = "CRM" ;
    			} else if( this.showSA ) {
    				systemType = "SA" ;
    			} else if( this.showCMS ) {
    				systemType = "CMS" ;
    			}
    		}
    		return systemType ;
    	},
    	...mapGetters( 'menu' , {
	        permissions : "getMenu" ,
	        systemType  : "getSystemType" 
      	})
    },

    mounted() {
        const that = this
        window.addEventListener("resize", function () {
            return (() => {
                window.screenWidth = document.body.clientWidth;
                that.screenWidth = window.screenWidth;
            })();
        });
        //$('#SA-tab').tab('show')
    },
    watch: {
        
    },
    methods: {       

    },
}
</script>