const getDefaultState = () => {
	return {
		user : {
			name 			: "" ,
			email 		: "" ,
			login 		: false ,
			locale 		: "zh-tw" ,
			token 		: "" ,	
		},
		selOptin : {
			acquirer 		 	: [] ,
			categoryId 	 	: [] ,
			environmentId	: [] ,
			tagId 				: [] ,
			salesId  			: [] ,
			notifyType 		: [] ,
			BTFType 			: [] ,
			STFBType 			: [] ,
			STFPayer 			: [] ,
			STFGMBType 		: [] ,
		},
		sendMessage : false ,
	}
}

const state = getDefaultState();

const getters = {
	getName		: ( state , getters , rootState ) => {
		return state.user.name ;
	},
	getEmail	: ( state , getters , rootState ) => {
		return state.user.email ;
	},
	getUser 	: ( state , getters , rootState ) => {
		return state.user ;
	},
	getLogin 	: ( state , getter , rootState ) => {
		return state.user.login ;
	},
	getLocale	: ( state , getter , rootState ) => {
		return state.user.locale ;
	},
	getSendMessage : ( state , getter , rootState ) => {
		return state.sendMessage ;
	},
	getToken : ( state , getter , rootState ) => {
		return state.user.token ;
	},
	getAcquirer : ( state , getter , rootState ) => {
		return state.selOptin.acquirer ;
	},

	getCategoryId : ( state , getter , rootState ) => {
		return state.selOptin.categoryId ;
	},

	getEnvironmentId : ( state , getter , rootState ) => {
		return state.selOptin.environmentId ;
	},

	getTagId : ( state , getter , rootState ) => {
		return state.selOptin.tagId ;
	},

	getSalesId: ( state , getter , rootState ) => {
		return state.selOptin.salesId ;
	},

	getNotifyType: ( state , getter , rootState ) => {
		return state.selOptin.notifyType ;
	},

	getBTFType: ( state , getter , rootState ) => {
		return state.selOptin.BTFType ;
	},
	
	getSTFBType: ( state , getter , rootState ) => {
		return state.selOptin.STFBType ;
	},

	getSTFPayer: ( state , getter , rootState ) => {
		return state.selOptin.STFPayer ;
	},

	getSTFGMBType: ( state , getter , rootState ) => {
		return state.selOptin.STFGMBType ;
	},
	
}

//commit 呼叫
const mutations = {
	setName ( state , name ) {
		state.user.name = name ;
		state.login = true ;
	},

	setEmail ( state , email ) {
		state.user.email = email ;
		state.login = true ;
	},

	setUser ( state , user ) {
		state.user = Object.assign(state.user, user);
		state.user.login = true ;
	},

	setLocale ( state , locale ) {
		state.user.locale = locale ;
	},

	setSendMessage ( state , sendMessage ) {
		state.sendMessage = sendMessage ;
	},

	setToken ( state , token ) {
		state.user.token = token ;
	},

	setAcquirer ( state , acquirer ) {
		state.selOptin.acquirer = acquirer ;
	},

	setCategoryId ( state , categoryId ) {
		state.selOptin.categoryId = categoryId ;
	},

	setEnvironmentId ( state , environmentId ) {
		state.selOptin.environmentId = environmentId ;
	},

	setTagId ( state , tagId ) {
		state.selOptin.tagId = tagId ;
	},

	setSalesId ( state , salesId ) {
		state.selOptin.salesId = salesId ;
	},

	setNotifyType ( state , notifyType ) {
		state.selOptin.notifyType = notifyType ;
	},

	setBTFType ( state , BTFType ) {
		state.selOptin.BTFType = BTFType ;
	},

	setSTFBType ( state , STFBType ) {
		state.selOptin.STFBType = STFBType ;
	},

	setSTFPayer ( state , STFPayer ) {
		state.selOptin.STFPayer = STFPayer ;
	},

	setSTFGMBType( state , STFGMBType ) {
		state.selOptin.STFGMBType = STFGMBType ;
	},
	
	resetState(state) {
	    const initState = getDefaultState()
	    Object.keys(initState).forEach(key => {
	        state[key] = initState[key]
	    })
	 }
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