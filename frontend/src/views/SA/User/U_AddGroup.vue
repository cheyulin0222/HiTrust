<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		商店群組代號維護
	</h4>
	<div class="p-3">
		<Card :title = " '商家群組代碼 : ' + mGId " >
			<template v-if="  userEmail != '' ">
				<CardItem colMd="6">
					<Input ID = "userEmail" LabelVal = "人員Email" :input = "this" :readOnly = "true"/>
				</CardItem>
				<CardItem colMd="6">
					<Input ID = "userMerGroupType" LabelVal = "使用者型別" :input = "this" :readOnly = "true"/>
				</CardItem>
			</template>
			<template v-if="  merGroupType == 'MULTIPLE' ">
				<CardItem colMd="5" class = "pr-0 text-center ">
					<label class = "pl-2 mb-1" >群組商家</label>
					<Input ID = "filterSelUser" v-model = "filterSelUser"/>
					<select id='select1' class = "mt-2" v-model = "selUserArr" multiple='multiple' style='width:100%;height:400px;'>
						<option v-for='item in userMerList ' :value='item.merId' :key="item.merId" class="dropdown-item" style="font-size:18px;" :class="{'d-none': filer1(item)}">
							{{ item.merId }}({{ item.merName }})
						</option>
					</select>
				</CardItem>
				<CardItem colMd="2">
					<button type="button"
						v-on:click="add()"
						style="width: 100%;margin-top:100px;min-height: auto;"
						class="btn btn-secondary justify-content-center align-items-center">
						<span>
							<i class="fas fa-angle-left"></i>
						</span>
					</button>
					<button type="button"
						v-on:click="remove()"
						style="width: 100%;margin-top: 10px;min-height: auto;"
						class="btn btn-secondary justify-content-center align-items-center">
						<span>
							<i class="fas fa-angle-right"></i>
						</span>
					</button>
				</CardItem>
				<CardItem colMd="5" class = " pr-0 text-center ">
					<label>可加入商家</label>
					<Input ID = "filterAllMerList" v-model = "filterAllMerList"/>
					<select id='select1' class = "mt-2" multiple='multiple' v-model = "selAllMerList" style='width:100%;height:400px;'>
						<option v-for='item in allmerlist ' :value='item.merId' :key="item.merId" class="dropdown-item" style="font-size:18px;" :class="{'d-none': filer2(item)}">
							{{ item.merId }}({{ item.merName }})
						</option>
					</select>
				</CardItem>
			</template>
		</Card>
	</div>
	<div class="row mt-2 mb-5 justify-content-md-center">
		<div class="col-md-4 " >
			<div class="m-0">
				<button type="button" @click.prevent="goBack"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">返回</span>
				</button>
			</div>
		</div>
		<div class="col-md-4 " v-if=" merGroupType === 'MULTIPLE' ">
			<div class="m-0">
				<button type="button" @click.prevent="save"
				class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
					<span class="mx-2">{{ type === "ADD" ? "新增群組" : "確定修改" }}</span>
				</button>
			</div>
		</div>
	</div>
</template>
<script>
import Base from "@/components/RightViewBase" ;
import Input from "@/components/Input";
import Card from "@/components/Card" ;
import CardItem from "@/components/CardItem" ;
import { getListVal } from "@/utils/dataList";
import { getMerGroupTypeList  } from "@/utils/dataListSA" ;

export default {

	data(){
		let _this = this ;
		
		return {
			title 				: "商店群組代號維護" ,
			allmerlist 			: [] ,
			userMerList			: [] ,
			userMerGroupType 	: "" ,
			merGroupType 		: "" ,
			userEmail 			: "" ,
			mGId				: "" ,
			selAllMerList 		: [] ,
			selUserArr 			: [] ,
			type 				: "" ,
			previousData 		: "" ,
			filterSelUser 		: "" ,
			filterAllMerList	: "" ,
		}
	},
	extends : Base ,
	components : {
		Input , Card , CardItem
	},
	mounted() {

    },
	methods: {
		init(){
			let param = this.params ;
			let merGroupType = this.merGroupType = param.merGroupType ;

			let type = this.type = param.type ? param.type : "ADD" ;
			this.userEmail = param.userId ? param.userId : "" ;

			let _this = this ;
			getMerGroupTypeList(function( arr ){
				_this.userMerGroupType = getListVal( arr , merGroupType )
			})

			if( merGroupType === "MULTIPLE" ){
				let mGId = this.mGId = param.mGId ;
				_this.showLoading(true);
				this.ApiUtilSA.get( "get-all-ap-mer-user-group-options")
				.then((res) => {
					if( res.success ) {
						let allmerlist = res.data ;
						
						if( type === "ADD" ) {
							return _this.getUserList( [] , allmerlist ) ;
						} else {
							return _this.ApiUtilSA.get( "/get-ap-mer-user-group/" + mGId )  
							.then((res) => {
								if( res.success ) {
									let userListObj = _this.getUserList( res.data.apMerGroupOptions , allmerlist ) ;
									let previousData = {
										merGroupId 	: mGId , 
										merIds		: userListObj.merIds 
									}
									_this.previousData = JSON.stringify(previousData) ;
									return userListObj ;
								} else {
									return {} ;
								}
							});
						}
					}
				})
				.then(( selObj ) => {
					_this.allmerlist = selObj.allmerlist ;
					_this.userMerList= selObj.userMerList ;
				})
				.finally(function () {
		        	_this.showLoading();
				});
			}
		},
		add( type ){
            let noArr = this.allmerlist ;
            let arr = this.userMerList ;
            this.selAllMerList.forEach(function( value ){
                let index = noArr.findIndex( obj => obj.merId == value );
                if( index > -1 ) {
                    arr.push(noArr[index]);
                    noArr.splice(index, 1);
                }
            })
            
            this.sortMerId( arr ) ;
            this.selAllMerList = [] ;

        },
        remove( type ){
           	let noArr = this.allmerlist ;
            let arr = this.userMerList ;
            
            this.selUserArr.forEach(function( value ){
                let index = arr.findIndex( obj => obj.merId == value );
                if( index > -1 ) {
                    noArr.push(arr[index]);
                    arr.splice(index, 1);
                }
            })

            this.sortMerId( noArr ) ;
            this.selUserArr = [] ;

        },
        save(){
        	let type = this.type ;
        	let merIds = [] ;
        	let _this = this ;

        	this.userMerList.forEach(function( item ) {
        		merIds.push( item.merId ) ;
        	});
        	
        	let param = {
        		merGroupId 	: this.mGId ,
        		merIds 		: merIds 
        	}

        	if( type == "ADD" ) {
        		_this.showLoading(true);
        		this.ApiUtilSA.post( "/create-ap-mer-user-group" , param )
        		.then((res) => {
					if( res.success ) {
						this.showModel({
			                title : this.title ,
			                message :"新增商家群組代號成功",
			                endFunc : function(){
			                	_this.goTo("back");
			                }
			            });
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
        	} else {
        		param["previousData"] = this.previousData ;

        		_this.showLoading(true);
        		this.ApiUtilSA.post( "/update-ap-mer-user-group" , param )
        		.then((res) => {
					if( res.success ) {
						this.showModel({
			                title : this.title ,
			                message :"更新商家群組代號成功",
			                endFunc : function(){
			                	_this.goTo("back");
			                }
			            });
					}
				})
				.finally(function () {
		        	_this.showLoading();
				});
        	}
        },
        goBack(){
        	this.goTo("back");
        },
        getUserList( apMerGroups , allList ) {
        	let allmerlist 	= [] ;
        	let userMerList = [] ;
        	let merIds		= [] ;
        	allList.forEach(function( item ){
        		
        		let merObj = {
        			merId 	: item.key ,
        			merName	: item.value 
        		}

        		let index = apMerGroups.findIndex( obj => obj.merId == merObj.merId );

        		if( index > -1 ) {
        			userMerList.push( merObj );
        			merIds.push( merObj.merId ) ;
        		} else {
        			allmerlist.push( merObj ) ;
        		}
        	});

        	return {
        		merIds		: merIds ,
        		allmerlist	: allmerlist ,
        		userMerList : userMerList 
        	}
        },
        sortMerId( arr ){
        	arr.sort(function(a, b) {
            	let merIdA = a.merId ;
            	let merIdB = b.merId ;
                if ( merIdA < merIdB ) {
					return -1;
				}
				
				if ( merIdA > merIdB ) {
					return 1;
				}

				// names must be equal
				return 0;
            });
        },
        filer1( item ){
        	let filterText = this.filterSelUser ;
        	return filterText != "" && item.merId.indexOf(filterText) < 0 ;
        },
        filer2( item ){
        	let filterText = this.filterAllMerList ;
        	return filterText != "" && item.merId.indexOf(filterText) < 0 ;
        }
	},
}	
</script>