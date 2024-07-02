const getDefaultState = () => {
  return {
    routerName : "" ,
    param : {} ,
  }
}

const state = getDefaultState();

const getters = {
  getRouterName   : ( state , getters , rootState ) => {
    return state.routerName ;
  },
  getParam : ( state , getters , rootState ) => {
    return state.param
  },
}

//commit 呼叫
const mutations = {
  setNext ( state , next ) {
    state.routerName = next.routerName ;
    state.param = next.param ;
  },
  setRouterNmae ( state , routerName ) {
    state.routerName = routerName 
  },
  setParam ( state , param ) {
    state.param = param ;
  },
  
  resetState(state) {
    const initState = getDefaultState()
    Object.keys(initState).forEach(key => {
        state[key] = initState[key]
    })
  },
}

//用dispatch 呼叫
const actions = {

}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}