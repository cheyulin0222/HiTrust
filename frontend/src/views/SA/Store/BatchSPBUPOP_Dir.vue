<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		整批開啟銀聯功能-CSV檔欄位說明
	</h4>
	<span style="color:red;">CSV檔各欄位以逗點區隔，請注意欄位內容不要含有逗點</span><br/>
	<span style="color:red;">每行為一筆商家資料</span>
	<br/>
	<DataTable ref="table1" :dataRow="dataRow" :options ="tableOption"/>
</template>

<script>
	import DataTable from '@/components/Table/PageDataTable.vue'

	export default {
		data(){
			return {
				tableOption : {
					colum : [
						{
							styleT : "width:5%" ,
							classT : "border border-info rounded-0 p-2 text-center alert-primary ",
	                    	type : 'index',
	                    	value : ['NO'] , 
	                	},
	                	{
	                		styleT : "width:40%" ,
							classT : "border border-info rounded-0 p-3 text-center alert-primary ",
							type : 'text',
							key : ['NAME'],
							value : ['欄位名稱'],
						},
						{
	                		styleT : "width:55%" ,
							classT : "border border-info rounded-0 p-3 text-center alert-primary ",
							type : 'text',
							key : ['DESC'],
							value : ['說明'],
						},
					]
				},
				dataRow : [
					{
						NAME : "MID" ,
						DESC : "15碼數字" ,
					},
					{
						NAME : "TID" ,
						DESC : "8 碼數字" ,
					},
					{
						NAME : "MACKEY" ,
						DESC : "請注意不要有空格" ,
					}
				],
			}
			
		},
		components : {
			DataTable 
		},
	}
</script>