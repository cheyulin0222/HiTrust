<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		{{ title }}
	</h4>
	<span>SA:</span>
	<table  width="90%" bgcolor="" border="1" cellpadding="4" cellspacing="1">
		<tr>
			<td style="width: 50px">全選</td>
			<td style="width: 250px">大項</td>
			<td>子功能</td>
		</tr>
		<template v-for = " ( item , index ) in roleRight.SA" :key="item.groupId">
			<tr >
				<td :rowspan="item.collapse.length">
					<input type="checkbox" :name = "item.groupId" :ref = "item.groupId" @click="selAll( item , 'SA' )">
				</td>
				<td :rowspan="item.collapse.length">
					{{item.groupName}}
				</td>
				<td>
					<input type="checkbox" v-model = "selData.SA" :ref = "getFID(item.collapse[0])" :name = "getFID(item.collapse[0])" :value="getFID(item.collapse[0])" @click="checkAll(item.collapse[0] , item , 'SA' )">
					{{ item.collapse[0].functionName + '(' + item.collapse[0].functionId + ')' }}
				</td>
			</tr>

			<tr :key=" collapse.functionId" v-for = " ( collapse , index ) in checkCollapse( item ) ">
				<td >
					<input type="checkbox" v-model = "selData.SA" :ref = "getFID(collapse)" :name = "getFID(collapse)" :value="getFID(collapse)" @click="checkAll(collapse , item  , 'SA' )">
					{{ collapse.functionName + '(' + collapse.functionId + ')' }}
				</td>
			</tr>
		</template>
	</table><br><br>

	<span>CRM:</span><br>
	<input type="checkbox" value="CRM" name = "CRM" v-model = "selData.CRM">啟用
	<br><br>

	<span>憑證管理:</span><br>		
	<table  width="90%" bgcolor="" border="1" cellpadding="4" cellspacing="1">
		<tr>
			<td style="width: 50px">全選</td>
			<td style="width: 250px">大項</td>
			<td>子功能</td>
		</tr>
		<template v-for = " ( item , index ) in roleRight.CMS" :key="item.groupId">
			<tr>
				<td :rowspan="item.collapse.length">
					<input type="checkbox" :name = "item.groupId" :ref = "item.groupId" @click="selAll( item , 'CMS' )">
				</td>
				<td :rowspan="item.collapse.length">
					{{item.groupName}}
				</td>
				<td>
					<input type="checkbox" v-model = "selData.CMS" :ref = "getFID(item.collapse[0])" :name = "getFID(item.collapse[0])" :value="getFID(item.collapse[0])" @click="checkAll(item.collapse[0] , item , 'CMS' )">
					{{ item.collapse[0].functionName + '(' + item.collapse[0].functionId + ')' }}
				</td>
			</tr>

			<tr :key=" collapse.functionId" v-for = " ( collapse , index ) in checkCollapse( item ) ">
				<td >
					<input type="checkbox" v-model = "selData.CMS" :ref = "getFID(collapse)" :name = "getFID(collapse)" :value="getFID(collapse)" @click="checkAll(collapse , item  , 'CMS' )">
					{{ collapse.functionName + '(' + collapse.functionId + ')' }}
				</td>
			</tr>
		</template>
	</table>
	<br><br>

	<span>SC:</span>
	<table  width="90%" bgcolor="" border="1" cellpadding="4" cellspacing="1">
		<tr>
			<td style="width: 50px">全選</td>
			<td style="width: 250px">大項</td>
			<td style="width: 350px">子功能</td>
			<td>Tab/Button</td>
		</tr>
		<template v-for = " ( item , index ) in roleRight.SC" :key="item.groupId">
			<tr>
				<td :rowspan="item.rowspan">
					<input type="checkbox" :name = "item.groupId" :ref = "item.groupId" @click="selAll( item , 'SC' )">
				</td>
				<td :rowspan="item.rowspan">
					{{item.groupName}}
				</td>
				<td :rowspan="item.collapse[0].rowspan">
					<input type="checkbox" v-model = "selData.SC" :ref = "getFID(item.collapse[0])" :name = "getFID(item.collapse[0])" :value="getFID(item.collapse[0])" @click="checkAll(item.collapse[0] , item , 'SC' )">
					{{ item.collapse[0].functionName + '(' + item.collapse[0].functionId + ')' }}
				</td>
				<td>
					<template v-if=" item.collapse[0].collapse && item.collapse[0].collapse.length > 0 ">
						<input type="checkbox" v-model = "selData.SC" :ref = "getFID(item.collapse[0].collapse[0])" :name = "getFID(item.collapse[0].collapse[0])" :value="getFID(item.collapse[0].collapse[0])" @click="clickSCTB(item.collapse[0] )">
						{{ item.collapse[0].collapse[0].functionName + '(' + item.collapse[0].collapse[0].functionId + ')' }}	
					</template>
				</td>
			</tr>
			<template :key=" collapse.functionId" v-for = " ( collapse , index ) in checkCollapse( item.collapse[0] )">
				<tr>
					<td >
						<input type="checkbox" v-model = "selData.SC" :ref = "getFID(collapse)" :name = "getFID(collapse)" :value="getFID(collapse)" @click="clickSCTB(item.collapse[0])">
						{{ collapse.functionName + '(' + collapse.functionId + ')' }}
					</td>
				</tr>
			</template>
			<template :key=" collapse.functionId" v-for = " ( collapse , index ) in checkCollapse( item )">
				<tr>
					<td :rowspan="collapse.rowspan">
						<input type="checkbox" v-model = "selData.SC" :ref = "getFID(collapse)" :name = "getFID(collapse)" :value="getFID(collapse)" @click="checkAll(collapse , item  , 'SC' )">
						{{ collapse.functionName + '(' + collapse.functionId + ')' }}
					</td>
					<td>
						<template v-if=" collapse.collapse && collapse.collapse.length > 0 ">
							<input type="checkbox" v-model = "selData.SC" :ref = "getFID(collapse.collapse[0])" :name = "getFID(collapse.collapse[0])" :value="getFID(collapse.collapse[0])" @click="clickSCTB(collapse)">
							{{ collapse.collapse[0].functionName + '(' + collapse.collapse[0].functionId + ')2' }}	
						</template>
					</td>
				</tr>
				<template :key=" collapse2.functionId" v-for = " ( collapse2 , index ) in checkCollapse( collapse )">
					<tr>
						<td >
							<input type="checkbox" v-model = "selData.SC" :ref = "getFID(collapse2)" :name = "getFID(collapse2)" :value="getFID(collapse2)" @click="clickSCTB(collapse)">
							{{ collapse2.functionName + '(' + collapse2.functionId + ')' }}
						</td>
					</tr>
				</template>
			</template>
		</template>
	</table>
	<div class="row mt-2 mb-5">
		<div class="col-md-4 offset-md-2 col-12 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="clear"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">取消</span>
				</button>
			</div>
		</div>
		<div class="col-md-4  col-12 p-2">
			<div class="m-0">
				<button type="button" @click.prevent="save"
				class="btn btn-danger  btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">儲存</span>
				</button>
			</div>
		</div>
	</div>
</template>
<script>
import Mask from "@/components/Mask.vue"
import Base from "@/components/RightViewBase" ;
import { resetNextPath , getParam } from "@/utils/nextPathUtil"
import { isNotBlank } from "@/utils/util"

let defaultSel = {
	userId 		: "",
	roleId 		: "",
	merId 		: "",
	updateUser 	: "" ,

}

export default {

	data(){
		let _this = this ;
		let params = this.params ;
		return {
			title : "權限設定" ,
			input : Object.assign({},defaultSel),
			roleRight   : {
				SA 	: [] ,
				CRM	: [] ,
				SC 	: [] ,
				CMS : [] ,
			} ,
			selData 	: {
				SA 	: [] ,
				CRM	: [] ,
				SC 	: [] ,
				CMS: [] ,
			},
			resetAll : false ,
		}
	},
	extends : Base ,
	components : {
		Mask 
	},
	watch : {
		roleRight : function( new_value, old_value  ){
			this.resetAll = true ;
		}
	},
	updated() { 
		if( this.resetAll ) {
			this.initAll( this.roleRight.SA  , "SA");
	
			this.initAll( this.roleRight.SC  , "SC");
	
			this.initAll( this.roleRight.CMS  , "CMS");
			
			this.resetAll = false ;
		}
		
	},
	mounted() {
		
  	},
	methods: {
		init(){
			this.search();
		},
		search(){
			let _this = this ;
			let roleId = this.params.roleId ;
			if( isNotBlank( roleId ) ) {

				_this.showLoading(true);
				this.ApiUtilSA.get("get-roleright/" + roleId )
				.then((res) => {
					if( res.success ) {
						let data = res.data ;
						let roleRight = data.roleRight ;
						
						roleRight.sc.forEach(function( group , index ){
							_this.comSCAllRow(group);
						})
						this.roleRight = {
							SA 	: roleRight.sa ,
							CRM	: roleRight.crm ,
							SC 	: roleRight.sc ,
							CMS : roleRight.cms ,
						}
						let selData = data.selData ;
						this.selData = {
							SA 	: selData.sa ,
							CRM	: selData.crm.length > 0 ? ["CRM"] : [] ,
							SC 	: selData.sc ,
							CMS : selData.cms ,
						}
						let previousData = {
							roleId 	: roleId , 
							selData : selData 
						}
						
						_this.previousData = JSON.stringify(previousData);
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
			} else {
				_this.showModel({
	                title 	: this.title ,
	                message : "無角色參數",
	                type 	: "error" ,
	                endFunc : function(){
	                	_this.search();
	                }
	            });
			}		
		},
		doSelPage( page ){

		},
    	updateVal( val , ID ){
    		this.input[ID] = val ;
    	},
    	clear(){
    		this.goNextPath("SA1201")
    	},
    	save(){
    		let selData = this.selData ;
    		let _this = this ;
    		let param = {
    			previousData 	: this.previousData ,
    			roleId 			: this.params.roleId ,
    			selData 		: {
    				sa 	: selData.SA ,
    				crm : selData.CRM.length > 0 ? this.roleRight.CRM : [] ,
    				sc  : selData.SC ,
    				cms : selData.CMS ,
    			}
    		}

    		this.showLoading(true);
			this.ApiUtilSA.post("/update-roleright" , param )
			.then((res) => {
				if( res.success && res.data.success ) {
					this.showModel({
		                title 	: this.title,
		                message : "修改成功",
		                endFunc : function(){
		                	_this.goNextPath("SA1201");
		                }
		            });
				}
			})
			.finally(function () {
	        	_this.showLoading();
			});

    	},
    	checkCollapse( group ){
    		if( !group.collapse || group.collapse.length == 1) {
    			return [] ;
    		} else {
    			let arr = [] ;    			
    			group.collapse.forEach(function( collapse , index ){
    				if( index > 0 ) {
    					arr.push(collapse);
    				}
    			})
    			return arr ;
    		}
    	},
    	initAll( groupList , system ) {
    		let _this = this ;
    		let selData = this.selData[system] ;
    		groupList.forEach(function( group , index ){
    			let all = group.collapse.every(function( collapse , i ) {
    				if( system == "SC" && collapse.collapse.length > 0 ) {
    					return collapse.collapse.every(function( tab , i ) {
    						let ID = tab.functionId ;
    						return selData.indexOf( ID ) > -1 
    					});
    				}  else {
    					let ID = collapse.functionId ;
    					return selData.indexOf( ID ) > -1 
    				}
    			});

				_this.$refs[group.groupId][0].checked = all ;
    		});
    	},
    	checkAll( collapse , group , system ){
    		let checked = event.target.checked ;
    		let selData = this.selData[system] ;
    		let FID = collapse.functionId ;
    		let box = this.$refs[group.groupId][0];
    		let _this = this ;
    		if( checked ) {
    			let all = true ;
    			if( system != "SC") {
    				group.collapse.every(function( collapse , index ){
		    			let ID = collapse.functionId
		    			if( ID === FID ) {
		    				return true ;
		    			} else {
		    				return all = selData.indexOf( ID ) > -1 
		    			}
						
					});
    			} else {
    				group.collapse.forEach(function( collapse , i ){
    					let ID = collapse.functionId
    					if( all && ID != FID && !_this.checkSCTab( collapse ) ) {
    						all = false ;
    					}
    					if( collapse.collapse && ID === FID) {
    						selData.push( FID );
    						collapse.collapse.forEach(function( collapse2 , i ){
				    			let functionId = collapse2.functionId ;
								let index = selData.indexOf( functionId ) ;
				    			if( index == -1 ) {
				    				selData.push( functionId );
				    			}
					    		
					    	});
    					}
    					
    				});
    			}
	    		
				checked = this.$refs[group.groupId][0].checked;
				if( checked != all ) {
					box.checked = all ;
				}
    		} else {
				box.checked = false ;
				if( system === "SC" && collapse.collapse && collapse.collapse.length > 0 ) {
					let index = selData.indexOf( collapse.functionId ) ;
					if( index > -1 ) {
	    				selData.splice( index,1 );
	    			}

    				collapse.collapse.forEach(function( collapse2 , i ){
		    			let functionId = collapse2.functionId ;
						let index = selData.indexOf( functionId ) ;

		    			if( index > -1 ) {
		    				selData.splice( index,1 );
		    			}
			    	});
				}
    		}
    	},
    	checkSCTab( collapse ){
    		let check = true ;
    		let selData = this.selData["SC"] ;
    		if( collapse.collapse.length > 0 ) {
    			collapse.collapse.every(function( collapse , index ){
	    			let ID = collapse.functionId
	    			return check = selData.indexOf( ID ) > -1 ;
				});
    		} else {
    			check = selData.indexOf( collapse.functionId ) > -1 ;
    		}

    		return check ;
    		
    	},
    	selAll( group , system ){
    		let checked = event.target.checked ;
    		let selData = this.selData[system] ;
    		let $refs = this.$refs ;
    		group.collapse.forEach(function( collapse , i ){
    			let functionId = collapse.functionId ;
				let index = selData.indexOf( functionId ) ;
	    		if( checked ) {
	    			if( index == -1 ) {
	    				selData.push( functionId );
	    			}
	    		} else {
	    			if( index > -1 ) {
	    				selData.splice( index,1 );
	    			}
	    		}
	    		if( system === "SC" && collapse.collapse ) {
	    			collapse.collapse.forEach(function( collapse2 , i ){
		    			let functionId = collapse2.functionId ;
						let index = selData.indexOf( functionId ) ;
			    		if( checked ) {
			    			if( index == -1 ) {
			    				selData.push( functionId );
			    			}
			    		} else {
			    			if( index > -1 ) {
			    				selData.splice( index,1 );
			    			}
			    		}
			    	});
	    		}
			})
    	},
    	clickSCTB( collapse ){
    		let checked = event.target.checked ;
    		let selData = this.selData["SC"];
    		if( checked ) {
    			let index = selData.indexOf( event.target.value ) ;
    			if( index == -1 ) {
    				selData.push(  event.target.value );
    			}
    			index = selData.indexOf( collapse.functionId ) ;
    			if( index == -1 ) {
    				selData.push( collapse.functionId );
    			}
    		}
    		this.resetAll = true ;
    	},
    	getFID( collapse ){
    		return collapse.functionId ;
    	},
    	comSCAllRow( group ){
    		let num = 0 ;
    		group.collapse.forEach(function( collapse , i ){
    			if( collapse.collapse && collapse.collapse.length > 1 ) {
    				collapse.rowspan = collapse.collapse.length ;
    			} else {
    				collapse.rowspan = 1 ;
    			}
    			num += collapse.rowspan ;
    		});
    		group.rowspan = num ;
    	}
	}
}	
</script>