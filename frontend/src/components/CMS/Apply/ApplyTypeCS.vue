<template>
<div><img alt="" src="@/assets/img/HiLogo.jpg"></div>
<div style="width:1000px; text-align:center; font-size:20px; font-weight:bold;">Symantec Code Signing 程式碼簽章數位憑證 用戶申請書</div>

<table>
<tr>
<td colspan="2">
申請日期：中<span style="width:15px;"></span>華<span style="width:15px;"></span>民<span style="width:15px;"></span>國
<span style="width:100px;"></span>年
<span style="width:100px;"></span>月
<span style="width:100px;"></span>日
</td>
</tr>
<tr>
<td width="50%">
申請效期：
<span>{{ application.expiryType === '1' ? '■' : '□' }}</span> 一年期
<span style="width:30px;"></span>
<span>{{ application.expiryType === '2' ? '■' : '□' }}</span> 二年期
<span style="width:30px;"></span>
<span>{{ application.expiryType === '3' ? '■' : '□' }}</span> 三年期
</td>
<td>
<span style="width:200px;"></span>□ 首次申請 <span style="width:30px;"></span>□ 續約
</td>
</tr>
</table>
<table class="tb_border">
<tr style="background-color:lightgray;">
<td colspan="4">
公司(機構)申請資料:
</td>
</tr>
    <tr>
        <td style="width:20%;">
            公司(機構)中文名稱<br>
            （請填寫中文全名）
        </td>
        <td style="width:30%">{{ application.nameCh }}</td>
        <td style="width:20%;">
            公司(機構)英文名稱<br> 
            （請填寫英文全名）
        </td>
        <td style="width:30%">{{ application.nameEn }}</td>
    </tr>
    <tr>
        <td colspan="4">公司(機構)中文地址：{{ application.addrCh }}</td>
    </tr>
    <tr>
        <td colspan="4">公司(機構)英文地址：{{ application.addrEn }}</td>
    </tr>
    <tr>
        <td colspan="4">
            憑證申請類型(請擇一勾選欲申請之憑證類型)<br>
            <span style="width:50%;">
                {{ application.csType === "MICROSOFT_AUTHENTICODE" ? '■' : '□' }} Microsoft&#xae;&nbsp;Authenticode&#xae;
            </span>
                {{ application.csType === "SUN_JAVA_SIGNING_DIGITAL_ID" ? '■' :'□' }} Sun Java&trade;&nbsp;Signing Digital ID<br>
            <span style="width:50%;">
                {{ application.csType === "MICROSOFT_OFFICE_AND_VBA" ? '■' : '□' }} Microsoft&#xae;&nbsp;Office and VBA
            </span>
                {{ application.csType === "ADOBE_AIR" ? '■' : '□' }} Adobe&#xae;&nbsp;AIR&trade;
        </td>
    </tr>
</table>
<table class="tb_border">
<tr style="background-color:lightgray;">
<td colspan="4">
聯絡資料
</td>
</tr>
<tr>
<td colspan="4">
業務聯絡人：Symantec 會致電確認 貴公司申請資料的聯絡人
</td>
</tr>
<tr>
<td>
姓名
</td>
<td>
部門名稱
</td>
<td>
公司(機構)電話
</td>
<td>
Email (不可為免費註冊email)
</td>
</tr>
<tr>
<td>中文：{{ application.salesNameCh }}</td>
<td>中文：{{ application.salesDeptNameCh }}</td>
<td rowspan="2">
({{  application.salesTels[0] }}){{  application.salesTels[1] }}分機:{{  application.salesTels[2] }}
</td>
<td rowspan="2">{{ application.salesEmail }}
</td>
</tr>
<tr>
<td>
英文：{{ application.salesNameEn }}
</td>
<td>
英文：{{ application.salesDeptNameEn }}
</td>
</tr>
<tr>
<td colspan="4">
技術聯絡人：Symantec 會致電確認 貴公司申請資料的聯絡人
</td>
</tr>
<tr>
<td>
姓名
</td>
<td>
部門名稱
</td>
<td>
公司(機構)電話
</td>
<td>
Email (不可為免費註冊email)
</td>
</tr>
<tr>
<td>
中文：{{ application.techNameCh }}
</td>
<td>
中文：{{ application.techDeptNameCh }}
</td>
<td rowspan="2">({{  application.techTels[0] }}){{  application.techTels[1] }}分機:{{  application.techTels[2] }}</td>
<td rowspan="2">{{ application.techEmail }}
</td>
</tr>
<tr>
<td>
英文：{{ application.techNameEn }}
</td>
<td>
英文：{{ application.techDeptNameEn }}
</td>
</tr>
<tr>
<td colspan="4">
帳務聯絡人：Symantec 會致電確認 貴公司申請資料的聯絡人
</td>
</tr>
<tr>
<td>
姓名
</td>
<td>
部門名稱
</td>
<td>
公司(機構)電話
</td>
<td>
Email (不可為免費註冊email)
</td>
</tr>
<tr>
<td>
中文：{{ application.fincNameCh }}
</td>
<td>
中文：{{ application.fincDeptNameCh }}
</td>
<td rowspan="2">
({{  application.fincTels[0] }}){{  application.fincTels[1] }}>分機:{{  application.fincTels[2] }}
</td>
<td rowspan="2">{{ application.fincEmail }}
</td>
</tr>
<tr>
<td>
英文：{{ application.fincNameEn }}
</td>
<td>
英文：{{ application.fincDeptNameEn }}
</td>
</tr>
</table>
<table class="tb_border">
<tr style="background-color:lightgray;">
<td colspan="6">
憑證申請資料
</td>
</tr>
<tr>
<td>
Company (Common Name)<br>
(例: HiTRUST Inc.)
</td>
<td>{{ application.commonName }}
</td>
<td>
City<br>
(例:Taipei)
</td>
<td>{{ application.locality }}
</td>
<td>
Country<br>
(例: TW)
</td>
<td>{{ application.country }}
</td>
</tr>
<tr>
<td>Division (Department)<br>
(例: RD Division)
</td>
<td>{{ application.orgUnitName }}
</td>
<td>
State<br>
(例:Taiwan)
</td>
<td colspan="3">{{ application.state }}
</td>
</tr>
</table>
<table class="tb_border">
<tr style="background-color:lightgray;">
<td>
申請文件
</td>
</tr>
<tr>
<td>
請附下列文件各乙份<br>
<span style="width: 180px;">1. 公司(機構)證明文件：</span>□ 營利事業單位：公司營利事業登記證影本<br>
<span style="width: 180px;"></span>□ 社團法人、財團法人：法人立案證照影本<br>
2. 公司(機構)電話帳單影本(須與申請書上之電話號碼一致; 不可為個人電信帳單或手機號碼帳單)
</td>
</tr>
</table>
<table class="tb_border">
<tr style="background-color:lightgray;">
<td colspan="2">
蓋章欄
</td>
</tr>
<tr>
<td style="width:50%;" height="100" valign="top">
公司(機構)：公司登記印鑑大小章或關防
</td>
<td valign="top">
公司(機構)：業務連絡人簽字
</td>
</tr>
</table>
<span style="width: 25px;">＊</span>請填具本申請書並檢附申請文件，傳真或郵寄至網際威信股份有限公司（地址:臺北市港墘路200號7樓 憑證業務部收）<br>
<span style="width: 25px;"></span>傳真專線: 02-26586063 ; 業務聯絡專線: 02-26586000分機 505<br><br>
<span style="width: 25px;">＊</span>注意事項：
<ol>
<li>
本公司係代理美國Symantec 公司收取申請文件及服務費用，並提供與憑證申請及下載相關的中文諮詢服務。有關憑證的申請流程、適用範圍及相關權利義務，悉以Symantec所公布者(包括但不限於其CPS及憑證用戶合約)為準。
</li>
<li>
本公司收到填寫完成的申請書及全額服務費用，並確認您已檢附必要的申請文件後，將通知您申請憑證所需資料(如申請流程說明等)，請確實遵循通知信函指示進行申請。
</li>
<li>
Symantec保留准駁憑證簽發的最終決定權。除Symantec因身份認證不符而拒絕簽發憑證，本公司將無息退還已收之服務費用外，其他情形，您已繳之服務費用概不得請求退還，且本公司不負其他損害賠償責任。
</li>
<li>
本公司對您所為的各項通知，得以電子郵件方式為之，亦得以書面向本申請書所載地址為送達。若登記資料有異動，您      應立即向本公司申請異動登記，否則本公司向原載電子郵件位址或地址發出通知即為生效。前各項通知，以本公司發出書面或電子郵件時視為已合法送達。
</li>
</ol> 

</template>

<script>
    export default {
        data() {
            return {}
        },
        props: {
            application : {
                type    : Object,
                required: true
            }
        }
    }
</script>

<style scoped>
table, td {
    border: 1px solid black;
    border-collapse: collapse;
    padding: 4px;    
}
table{
    width:1000px;
}
ol{
    width:960px;
}
span{
    display: inline-block;
}

</style>