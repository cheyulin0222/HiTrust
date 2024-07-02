<template>
<div><img alt="" src="@/assets/img/HiLogo.jpg"></div>
<div style="width:1000px; text-align:center; font-size:20px; font-weight:bold;">網際威信股份有限公司</div>
<div style="width:1000px; text-align:center; font-size:20px; font-weight:bold;">伺服器數位憑證  用戶註冊申請同意書</div>
<span style="width: 25px;"></span>本公司（機構）向網際威信股份有限公司申請 SSL 伺服器數位憑證，同意遵照如下約定事項：
<ol>
<li>
本公司（機構）所提供之申請文件皆為合法且正確之文件，否則願無條件廢止所申請之憑證，且已付費用不得要求退還。
</li>
<li>
本公司（機構）願保證絕不以所申請之SSL伺服器數位憑證、或於憑證所記載之網址上，從事任何違反國家相關法令之行為，否則願無條件廢止所申請之憑證，且已付費用不得要求退還。
</li>
<li>
本公司（機構）同意於所申請SSL伺服器數位憑證效期屆滿或廢止時，立即停止使用憑證。
</li>
<li>
本公司（機構）若需申請置換伺服器數位憑證，則同意自伺服器數位憑證有效期之起始日起，30日內申請置換為免費; 若超過30日則依CA中心置換核發規定辦理。
</li>
<li>
SSL憑證申請流程：申請文件->完成付款->CSR提供->CA中心身分驗證資料確認->憑證核發->憑證安裝與備份
</li>
</ol>
<span style="width: 25px;">＊</span>請填寫下列基本資料以便能核對驗證您的資料，並加速處理您的申請。

<table>
    <tr>
        <td colspan="2" style="background-color:lightgray;">基本資料</td>
    </tr>
    <tr>
        <td>
            <span style="width:160px;">公司（機構）中文名稱<br>（請填寫公司全名）</span>：{{ application.nameCh }}
        </td>
        <td rowspan="3">
        您所申請伺服器數位憑證類別:<br>
        一、 Symantec 動態加密型(Secure ID)伺服器數位憑證：<br>
        □ 一年  □二年  □ 三年  □ EV一年  □ EV二年<br>
        二、 Symantec 強制高加密型(Global ID)伺服器數位憑證：<br>
        □ 一年  □ 二年  □ 三年  □ EV一年  □ EV二年<br>
        三、 Symantec GEO Trust 伺服器數位憑證：<br>
        □ 一年  □ 二年 
        </td>
    </tr>
    <tr>
        <td>
            <span style="width:160px;">公司（機構）英文名稱</span>：{{ application.nameEn }}
        </td>
    </tr>
    <tr>
        <td>
            <span style="width:160px;">公司（機構）中文地址</span>：<span style="width:350px;">{{ application.addrCh }}</span>
        </td>
    </tr>
    <tr>
        <td>網域名稱(Domain Name, 例如 hitrust.com.tw)：{{ application.domainName }}</td>
        <td>
        <span style="width:50px;">用途:</span>
        <span>{{ application.useType === "WEBSITE_SERVER" ? '■' : '□' }}</span> 網站伺服器
        <span>{{ application.useType === "EMAIL_SERVER" ? '■' : '□' }}</span> 郵件伺服器
        <span>{{ application.useType === "FTP_SERVER" ? '■' : '□' }}</span> FTP 伺服器 <br>
        <span style="width:50px;"></span>
        <span>{{ application.useType === "OTHER" ? '■' : '□' }} 其他 <u>{{ application.useType === "OTHER" ? application.useOther : '' }}</u></span>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            Web Server管理軟體：
            <span>{{ application.webServer === "IIS" ? '■' : '□' }}</span> IIS
            v.<u>{{ application.webServer === "IIS" ? application.webServerOther : '' }}</u>
            <span>{{ application.webServer === "APACHE" ? '■' : '□' }}</span> Apache
            <span>{{ application.webServer === "TOMCAT" ? '■' : '□' }}</span> Tomcat
            <span>{{ application.webServer === "IBM_HTTP_SERVER" ? '■' : '□' }}</span> IBM HTTP Server
            <span>{{ application.webServer === "OTHER" ? '■' : '□' }}</span> 其它
            <u>{{ application.webServer === "OTHER" ? application.webServerOther : '' }}</u>
        </td>
    </tr>
</table>
<table>
    <tr>
        <td>業務連絡人: {{ application.salesNameCh }}</td>
        <td>部門: {{ application.salesDeptNameCh }}</td>
        <td>
            電話:({{  application.salesTels[0] }}){{  application.salesTels[1] }}分機:{{  application.salesTels[2] }}
        </td>
        <td>Email: {{ application.salesEmail }}</td>
    </tr>
    <tr>
        <td>技術連絡人: {{ application.techNameCh }}</td>
        <td>部門: {{ application.techDeptNameCh }}</td>
        <td>
            電話:({{  application.techTels[0] }}){{  application.techTels[1] }}分機:{{  application.techTels[2] }}
        </td>
        <td>Email: {{ application.techEmail }}</td>
    </tr>
    <tr>
        <td colspan="4">PS：至2011年8月起，Email 不能填寫網路免費信箱（例如：Yahoo!、gmail、hotmail等等，建議填寫公司電子信箱為佳）</td>
    </tr>
</table>
<table>
    <tr>
        <td colspan="2" style="width:600px;">
            申請日期：<span style="width:100px;"></span>年<span style="width:100px;"></span> 月<span style="width:100px;"></span> 日
        </td>
        <td colspan="2">
            預計安裝日期：<span style="width:50px;"></span>年<span style="width:50px;"></span>月<span style="width:50px;"></span>日
        </td>
    </tr>
    <tr>
    <td colspan="2">憑證資料 (請詳細填寫並確認與上線登錄的CSR資料相同)</td>
    <td colspan="2"></td>
    </tr>
    <tr>
        <td style="width:250px;">
            Common Name<br>
            憑證申請完整域名<br>
            (例: ssl.hitrust.com.tw)
        </td>
        <td style="width:350px;">{{ application.commonName }}</td>
        <td style="width:150px;">
            Locality<br>
            公司所在地<br>
            (例: Taipei)
        </td>
        <td style="width:250px;">{{ application.locality }}</td>
    </tr>
    <tr>
        <td>
            Organization公司英文全名<br>
            (例: HiTRUST, Inc.)
        </td>
        <td>{{ application.orgNameEn }}</td>
        <td>
            State<br>
            (例: Taiwan)
        </td>
        <td>{{ application.state }}</td>
    </tr>
    <tr>
        <td>
            Organization Unit<br>
            部門名稱(例: MIS Division)
        </td>
        <td>{{ application.orgUnitName }}</td>
        <td>
            Country<br>
            (例:TW)
        </td>
        <td>{{ application.country }}</td>
    </tr>
</table>
<table>
    <tr>
        <td>註冊文件</td>
    </tr>
    <tr>
        <td>
            請附下列文件乙份<br>
            □ 營利事業：公司營利事業登記證影本<br>
            □ 社團法人、財團法人：法人立案證照影本<br>
            □ 政府、教育、國防單位：用印之正式公文及正本(敘述單位名稱、業務承辦人姓名、連絡方式)
        </td>
    </tr>
</table>
<table>
    <tr style="background-color:lightgray;">
        <td colspan="2">蓋章欄</td>
    </tr>
    <tr>
        <td style="width:50%;" height="100" valign="top">公司(機構)：公司登記印鑑大小章或關防</td>
        <td valign="top">公司(機構)：業務連絡人簽字</td>
    </tr>
</table>
<span style="width: 25px;">＊</span>請將本同意書及檢附註冊文件，傳真或郵寄至網際威信股份有限公司 數位憑證業務部收<br>
<span style="width: 25px;">＊</span>傳真專線: 02-26586063 ; 業務聯絡專線: 02-26586000分機 505 ;<br>
<span style="width: 25px;">＊</span>地址 : 臺北市內湖區11494港墘路200號7樓<br>

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
    padding: 10px;    
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