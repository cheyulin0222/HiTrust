<template>
	<h4 class="sec-title font-weight-bold mt-md-4 mb-3">
		批次匯入商家-CSV檔欄位說明
	</h4>
	<span style="color:red;">CSV檔第一行為說明欄，不執行匯入，新增商家請從第二行開始輸入</span><br/>
	<span style="color:red;">CSV檔各欄位以逗點區隔，請注意欄位內不要含有逗點</span>
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
						NAME : "商家代碼" ,
						DESC : "5 碼數字" ,
					},
					{
						NAME : "統編" ,
						DESC : "8 碼數字" ,
					},
					{
						NAME : "商家英文名稱" ,
						DESC : "若有逗號請以#取代" ,
					},
					{
						NAME : "收單銀行代碼" ,
						DESC : "" ,
					},
					{
						NAME : "國家" ,
						DESC : "" ,
					},
					{
						NAME : "型式" ,
						DESC : "" ,
					},
					{
						NAME : "管理者電子郵件" ,
						DESC : "" ,
					},
					{
						NAME : "付款頁面商家名稱" ,
						DESC : "若有逗號請以#取代" ,
					},
					{
						NAME : "付款頁面商家電話" ,
						DESC : "" ,
					},
					{
						NAME : "付款頁面商家傳真" ,
						DESC : "" ,
					},
					{
						NAME : "付款頁面商家地址" ,
						DESC : "若有逗號請以#取代" ,
					},
					{
						NAME : "付款頁面商家網址" ,
						DESC : "" ,
					},
					{
						NAME : "商家VISA代碼" ,
						DESC : "銀行提供visa代碼" ,
					},
					{
						NAME : "商家VISA PWD" ,
						DESC : "銀行提供visa密碼" ,
					},
					{
						NAME : "VISA驗證商家名稱" ,
						DESC : "請填英文" ,
					},
					{
						NAME : "商家商家Master代碼" ,
						DESC : "銀行提供商家Master代碼" ,
					},
					{
						NAME : "商家商家Master PWD" ,
						DESC : "銀行提供商家Master密碼" ,
					},
					{
						NAME : "Master驗證商家名稱" ,
						DESC : "請填英文" ,
					},
					{
						NAME : "商家商家JCB代碼" ,
						DESC : "銀行提供商家JCB代碼" ,
					},
					{
						NAME : "商家商家JCB PWD" ,
						DESC : "銀行提供商家JCB密碼" ,
					},
					{
						NAME : "JCB驗證商家名稱" ,
						DESC : "請填英文" ,
					},
					{
						NAME : "商家國別" ,
						DESC : "TWN-台灣:158<br/>USA-美國:840<br/>CHN-中國:156<br/>HKG-香港:344" ,
					},
					{
						NAME : "貨幣名稱" ,
						DESC : "TWD-台幣:TWD<br/>USD-美金:USD<br/>CNY-人民幣:CNY<br/>	HKD-港幣:HKD" ,
					},
					{
						NAME : "是否開啟VISA 3D驗證" ,
						DESC : "TRUE:1	False:0" ,
					},
					{
						NAME : "是否開啟Master 3D驗證" ,
						DESC : "TRUE:1	False:0" ,
					},
					{
						NAME : "是否開啟JCB 3D驗證" ,
						DESC : "TRUE:1	False:0" ,
					},
					{
						NAME : "是否開啟末三碼驗證(CVC2)" ,
						DESC : "TRUE:1	False:0" ,
					},
					{
						NAME : "是否開啟退款功能" ,
						DESC : "TRUE:1	False:0" ,
					},
					{
						NAME : "是否開啟VISA 3D驗證" ,
						DESC : "TRUE:1	False:0" ,
					},
					{
						NAME : "是否開啟圖形驗証" ,
						DESC : "TRUE:1	False:0" ,
					},
					{
						NAME : "啟用OTS(one time session)" ,
						DESC : "TRUE:1	False:0" ,
					},
					{
						NAME : "未請款請示(授權後15~60天)" ,
						DESC : "TRUE:1	False:0" ,
					},
					{
						NAME : "清算時間" ,
						DESC : "0~23<br/>(預設0，若收單行為HSBC且清算方式為File，則為24)" ,
					},
					{
						NAME : "清算方式" ,
						DESC : "nline:O	File:F<br/>(若收單行為HSBC，必填)" ,
					},
					{
						NAME : "商家狀態狀態" ,
						DESC : "Active:A	Locked:L	Settlement:S" ,
					},
					{
						NAME : "檔案批次處理" ,
						DESC : "Disable:N	Enable:Y" ,
					},
					{
						NAME : "銀行商家代碼" ,
						DESC : "MID<br/>(銀行提供)" ,
					},
					{
						NAME : "銀行終端機代碼" ,
						DESC : "TID<br/>(銀行提供)" ,
					},
					{
						NAME : "銀行轉入帳號" ,
						DESC : "(For IC Card Only)" ,
					},
					{
						NAME : "交易金額限定" ,
						DESC : "預設 0<br/>(For IC Card Only)" ,
					},
					{
						NAME : "商店無交易警訊" ,
						DESC : "(For IC Card Only)" ,
					},
					{
						NAME : "金鑰版本" ,
						DESC : "JAVA=DER	COM=pem" ,
					},
					{
						NAME : "csv、設定檔、金鑰 FTP 路徑" ,
						DESC : "請先與工程師確認產生路徑" ,
					},
					{
						NAME : "自動請款標記" ,
						DESC : "1為自動請款，0為手動請款" ,
					},
					{
						NAME : "MACKEY" ,
						DESC : "MACKEY<br/>(若收單行為ESB，必填，由銀行提供)" ,
					},
				],
			}
			
		},
		components : {
			DataTable 
		},
	}
</script>