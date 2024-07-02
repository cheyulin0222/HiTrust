import { createStore } from "vuex";
import user from "./modules/user"
import menu from "./modules/menu"
import nextPath from "./modules/nextPath"

export default createStore({
 
  modules: {
    user , menu , nextPath 
  },
  strict : true 
});
