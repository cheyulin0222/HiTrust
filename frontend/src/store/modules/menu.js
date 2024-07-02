const getDefaultState = () => {
	return {
		menu : {
			"cms" 	: [] ,
			"crm" 	: [] ,
			"sa"	: [] 
		},
		menuActive: "" ,
		systemType: "" ,
		hash : "" ,
	}
}

const state = getDefaultState();

const getters = {
	getMenu 	: ( state , getter , rootState ) => {
		return state.menu ;
	},
	getMenuActive : ( state , getter , rootState ) => {
		return state.menuActive ;
	},
	getSystemType : ( state , getter , rootState ) => {
		return state.systemType ;
	},
	getHash : ( state , getter , rootState ) => {
		return state.hash ;
	},
}

//commit 呼叫
const mutations = {
	
	setMenu ( state , menu ) {
		state.menu = menu ;
	},

	setMenuActive ( state , menuActive ) {
		state.menuActive = menuActive ;
	},

	setSystemType ( state , systemType ) {
		state.systemType = systemType ;
	},

	setHash ( state , hash ) {
		state.hash = hash ;
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