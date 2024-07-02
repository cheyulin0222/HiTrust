import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import HomeView from "../views/HomeView.vue";
import LoginView from "../views/LoginView.vue";
import naPageView from "../views/404View.vue";
import { SESSION_NAME } from '@/utils/user';
import store from "@/store";
import routerList from "./routerList" ;
import Home from "@/components/Home.vue" ;
import { getNextName } from "@/utils/nextPathUtil" ;
import BatchDesc from "@/views/SA/Store/Batch_Dir.vue" ;
import { otherList } from "./routerList" ;
import { sendLogOut } from "@/utils/session"
const routes: Array<RouteRecordRaw> = [
  {
    path: "/login",
    name: "login",
    component: LoginView,
  },
  {
    path: '/404',
    name: '404',
    meta: { title: '404' },
    component: naPageView,
  },
  {
    path: '/error',
    name: 'error',
    component: naPageView,
    props: true,//參數會自動轉成props
  },
  { 
    path:'/batchDesc',
    name: 'batchDesc',
    component: BatchDesc,
  },
  { 
    path:'/:pathMatch(.*)*',
    name: '403',
    component: naPageView,
  },

 
].concat(otherList());

const router = createDefRouter()

let homePath = "/Home" ;
let hasMenu = false ;
//console.log(router);
router.beforeEach((to, from, next) => {
  let params = to.params ;
  let fName = from.name ;

  window.setTimeout(() => {

    let meta = to.meta;
    let isLogin = store.getters["user/getLogin"] ;
    if( !isLogin ) {
      if ( (to.path != '/login' && !meta.requireAuth )) {
        next('/login')
      } else {
        next()
      }
    } else {
      if( !hasMenu ) {
        //動態產生路由
        const menuList = createRoutes(store.getters["menu/getMenu"]);
        menuList.forEach(function( route ){
          router.addRoute(route);
        });
        if ( to.path === '/login' || to.path ==='/home' || to.path ==='/'){
          next(homePath)
        } else {
          next({ path: to.path })
        } 
      } else {
        //判斷是否有設定NEXT
        let routerName = getNextName();
        if( routerName != "" && routerName != to.name ) {
          if( fName === routerName ) {
            window.vue.reloadApp();
          } else {
            next({name : routerName});
          } 
        } else if ( to.path === '/login' || to.path ==='/home' || to.path ==='/' ){
          next(homePath)
        } else {
          store.commit("menu/setMenuActive",meta.active?meta.active:"");
          store.commit("menu/setSystemType",meta.systemType?meta.systemType:"");
          store.commit("menu/setHash",to.hash);
          next()
        } 
      }  
    } 
  },100);
  
});

function createDefRouter(){

  return createRouter({
    history: createWebHistory( process.env.VUE_APP_AP_NAME ),
    routes,
  });
}

export function resetRouter() {
  //homePath = "" ;
  hasMenu = false ;
  let routeList = router.getRoutes();
  routeList.forEach(function(route){
    router.removeRoute(route.name);
  }) 
  routes.forEach(function(route){
    router.addRoute(route);
  }) 
}

function createRoutes( menuObj ){
  let result : Array<RouteRecordRaw> = []
  let children = [{
    path: "/Home",
    component: () => import("@/components/Home.vue") ,
    meta: { active: 'Home' },
    name: "Home",
  }]

  result.push({
      path: '/',
      name: '/',
      meta: { requireAuth: true }, // 用來作為此頁是否需要權限驗證的設定
      component: HomeView,
      children,
  });

  let CRMList = menuObj.crm ;
  let SAList  = menuObj.sa ;
  let CMSList = menuObj.cms ;

  CRMList.forEach( item => {
    generateRoutes(children, item)
  });

  SAList.forEach( item => {
    generateRoutes(children, item)
  });

  CMSList.forEach( item => {
    generateRoutes(children, item)
  });
  
  hasMenu = true ;

  return result ;
}

function generateRoutes(children, item) {
  if( item.hasOwnProperty("path") ) {
    if( item.children ) {
      let child = [] ;
      item.children.forEach(e => {
        generateRoutes(child, e );
      });
      item.children = child ;
    }
    if( item.checkName ) {
      item.beforeEnter = (to, from, next) =>{
        if( item.checkName.indexOf(from.name) < 0 ) {
          next({name:item.checkName[0]});
        } else {
          next();
        }
      }
    }
    children.push(item);
  } else if( item.functionId ) {
    let functionId = item.functionId ;
    let routerObj = routerList[functionId] ; 
    if ( routerObj ){
      /*if( homePath === "" ) {
        homePath = routerObj.path.split(":")[0] ;
      }*/
      generateRoutes(children, routerObj);
    } else {
      if( item.children ) {
         item.children.forEach(e => {
          generateRoutes(children, e)
        });
      }
    }
  } else if ( item.children ) {
    item.children.forEach(e => {
      generateRoutes(children, e)
    });
  }
}

export default router;