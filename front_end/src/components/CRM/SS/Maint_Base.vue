<template >
	<div class="container-fluid">
		<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
			{{ $t(this.title) }}
		</h4>
		<DataTable ref="table1"
		:dataRow="dataRow"
		:options ="tableOption">
		</DataTable>
	</div>
	<Edit ref= "Edit" />
</template>
<script>

import DataTable from '@/components/Table/DataTable.vue' ;
import Base from "@/components/Base" ;

export default {
	data(){
		return {
			title 	: "" ,
			dataRow : [] ,
			tableOption : {},
		}
		 
	},
    extends : Base ,
    components : {
   		DataTable
    },
    methods : {
    	doAdd(){
    		this.$refs.Edit.show( "ADD" );
    	},
    	doEdit( payment ){
    		this.$refs.Edit.show( "EDIT" , Object.assign( {} , payment ) );
    	},
    	doDel( id ) {
    		let _this = this ;
    		this.loading = true ;
    		this.ApiUtil.delete(this.delApi+id)
			.then((res) => {
				if( res.success ) {
					_this.showModel({
		                title : '刪除成功',
		                message : "刪除成功",
		                endFunc : function(){
		                	_this.search(true);
		                }
		            });
				}
				//console.log(res);
				this.loading = false ;
			});
    	},
    	search( reload ){
    		this.loading = true ;
    		let _this = this ;
    		this.ApiUtil.get(this.searchApi)
			.then((res) => {
				if( res.success ) {
					_this.dataRow = res.data
				} else {
					_this.dataRow = [] ;
				}

				if( reload ) {
					_this.reset(_this.dataRow.slice());
				}
				_this.loading = false ;
			});
    	},
    	reset( data ) {
    		
    	}
    },
    mounted(){
    	this.search();
    	this.$refs.Edit.setCallBack(this.search.bind(this,true));
    },
}
</script>