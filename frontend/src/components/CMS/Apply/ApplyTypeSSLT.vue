<template>
<div>
<span style="width:50px;"><img alt="" src="@/assets/img/TWCALogo.jpg"></span><span style="width:900px; font-size:22px; font-weight:bold; text-align:center;">臺灣網路認證股份有限公司&nbsp;&nbsp;&nbsp;SSL伺服器數位憑證</span>
<div style="width:1000px; font-size:22px; font-weight:bold; text-align:center;">憑證申請單</div>
本公司（機構）茲向臺灣網路認證股份有限公司(TWCA)申請簽發SSL伺服器數位憑證 （SSL Server Certificate,以下簡稱憑證），並聲明以下事項︰<br>
<span>一、</span><span>本公司（機構）提供之本申請書、資料、證明文件、印鑑及簽章皆為合法且經本公司（機構）驗證無誤。</span><br>
<span>二、</span><span style="width:850px;">本公司（機構）業經依網頁伺服器軟體指示製作金鑰對及用戶憑證申請檔（簡稱CSR），並依規定程序填入貴公司網站註冊表格。</span><br>
<span>三、</span><span style="width:850px;">本公司（機構）業經詳細審閱下列「SSL伺服器數位憑證用戶申請註冊及使用須知」及  TWCA「憑證實務作業基準」之內容，並同意遵守一切規定。</span>
<table>
    <tr>
        <td style="width:10px;" rowspan="6">公司基本資料</td>
        <td style="width:10px;" colspan="2">公司（機構）中文名稱</td>
        <td colspan="2">{{ application.nameCh }}</td>
    </tr>
    <tr>
        <td style="width:10px;" colspan="2">公司（機構）英文名稱</td>
        <td colspan="2">{{ application.nameEn }}</td>
    </tr>
    <tr>
        <td style="width:10px;" colspan="2">公司負責人姓名</td>
        <td colspan="2">{{ application.ownerName }}</td>
    </tr>
    <tr>
        <td style="width:10px;" colspan="2">營利事業（機關）統一編號</td>
        <td colspan="2">{{ application.regNo }}</td>
    </tr>
    <tr>
        <td style="width:10px;" colspan="2">公司（機構）登記地址</td>
        <td colspan="2">{{ application.addrCh }}</td>
    </tr>
    <tr>
        <td style="width:10px;" colspan="2">公司聯絡地址</td>
        <td colspan="2">{{ application.contactAddr }}</td>
    </tr>
    <tr>
        <td rowspan="3">憑證相關資訊</td>
        <td colspan="2" style="height:50px; ">網站名稱（Common Name）</td>
        <td colspan="2" style="height:50px; ">
            {{ application.commonName }}
            <p><span style="color:#DCDCDC; ">(請以正楷書寫，如申請多個網站請填寫附件一表單)</span></p>
        </td>
    </tr>
    <tr>
        <td colspan="2">憑證類別</td>
        <td>申請種類</td>
        <td>伺服器種類</td>
    </tr>
    <tr>
        <td colspan="2">
            □ TWCA SSL（一年期）<br>
            □ TWCA SSL（二年期）<br>
            □ TWCA SSL（三年期）<br>
            □ 其它
        </td>
        <td>
            □ 首次申請<br>
            □ 更新憑證(展期)<br>
            □ 增購新憑證<br>
            □ 重新申請
        </td>
        <td>
            □ IIS 5.0     □ IIS 6.0     □ Apache<br>
            □ Netscape (iPlanet 4.0)<br>
            □ Netscape (iPlanet 6.0)<br>
            □ 其他
        </td>
    </tr>
    <tr>
        <td rowspan="7">聯絡人資料</td>
        <td colspan="2">姓      名</td>
        <td>電  話 / 傳  真</td>
        <td>電    子    郵    件    地    址</td>
    </tr>
    <tr>
        <td style="width:10px;" rowspan="2">技術</td>
        <td rowspan="2">{{ application.techNameCh }}</td>
        <td>Tel：{{ application.techTel }}</td>
        <td rowspan="2">{{ application.techEmail }}</td>
    </tr>
    <tr>
        <td>Fax：{{ application.techFax }}</td>
    </tr>
    <tr>
        <td style="width:10px;" rowspan="2">採購</td>
        <td rowspan="2">{{ application.salesNameCh }}</td>
        <td>Tel：{{ application.salesTel }}</td>
        <td rowspan="2">{{ application.salesEmail }}</td>
    </tr>
    <tr>
        <td>Fax：{{ application.salesFax }}</td>
    </tr>
    <tr>
        <td style="width:10px;" rowspan="2">帳務</td>
        <td rowspan="2">{{ application.fincNameCh }}</td>
        <td>Tel：{{ application.fincTel }}</td>
        <td rowspan="2">{{ application.fincEmail }}</td>
    </tr>
    <tr>
        <td>Fax：{{ application.fincFax }}</td>
    </tr>
    <tr>
        <td>
            憑證<br>
            費用
        </td>
        <td colspan="4">
            <span style="width:30px;">※</span>本公司將以  □支票  □匯款 方式支付憑證費用 NTD$           元<br>
            <br>
            <span style="width:30px;">※</span>發票資訊<br> 
            <span style="width:30px;"></span>□請依上述「公司基本資料」開立三聯式發票  <br>  
            <span style="width:30px;"></span>□請以 統一編號：                買受人(發票抬頭)：                        開立三聯式發票
        </td>
    </tr>
</table>
<span style="width:1000px; text-align:center; color:#DCDCDC; ">(以上欄位請務必確實填寫)</span>
<table>
<tr>
<td>
<span style="width:30px;">※</span>憑證申請應注意事項<br>
<span style="width:35px;"></span>網域名稱使用授權書：如所申請之網站名稱(Common Name)非申請單位所註冊擁有者需填具。<br>
<span style="width:30px;">※</span>如為首次申請憑證者請檢附下列文件乙份<br>
<span style="width:35px;"></span>營利事業:營利事業登記證影本或變更事項登記表影本(請於影本上加蓋公司印鑑及負責人印鑑)。<br>
<span style="width:35px;"></span>社團財團法人:法人立案證照影本或統一編號編配通知書(請於影本上加蓋公司印鑑及負責人印鑑) 。<br>
<span style="width:35px;"></span>政府機構: 統一編號編配通知書或稅籍證明(請於影本加蓋公司印鑑及負責印鑑) 。<br>
<span style="width:35px;">※</span>如為首次申請請務必將本申請單正本及檢附證明文件郵寄至『臺灣網路認證公司  電話行銷組 收』。<br>
<span style="width:35px;">※</span>將本申請件交寄至本公司後，需產製用戶憑證申請檔(CSR)，上傳至TWCA網頁，方得進行憑證簽發作業。<br>
<span style="width:35px;">※</span>本作業時間約需1~3個工作日，如送件已超過基本作業期間者，可來電洽詢申辦情形。
</td>
</tr>
</table>
<br><br><br>
</div>


<div class="container-2">
<p><span style="width:1000px; text-align:center; font-size:22px; " >SSL伺服器數位憑證用戶申請註冊及使用須知︰</span></p>
<span style="width:30px;">一、</span>憑證用途︰<br>
<span style="width:30px;"></span><span style="width:940px;">臺灣網路認證股份有限公司（以下簡稱認證中心）簽發之SSL伺服器數位憑證，係供用戶使用SSL協定進行資料傳輸時，提供網站身分確認及傳輸資料加解密之用。</span><br>
<span style="width:30px;">二、</span>申請註冊︰<br>
<span style="width:70px; text-align:right; ">(一)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">申請人需自備網頁伺服器軟體，並依該伺服器軟體之指示製作金鑰對及用戶憑證申請檔（簡稱CSR）後，連上認證中心網站SSL伺服器數位憑證用戶註冊網頁，依規定程序將CSR填入線上註冊表格中；同時填寫本申請單用印後，連同證明文件及服務費用（得以本公司為受款人之即期、劃線、禁止背書轉讓之支票或以電匯方式付款），一併送交或掛號郵寄至臺灣網路認證股份有限公司（郵遞區號：100，地址：臺北市中正區延平南路八十五號十樓）。</span><br>
<span style="width:70px; text-align:right; ">(二)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">認證中心於查核並確認收到費用後，依照申請人填送之資料簽發數位憑證置於認證中心網站，並以電子郵件（E-mail）通知用戶技術事項連絡人，由申請人自認證中心網站下載使用。</span><br>
<span style="width:70px; text-align:right; ">(三)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">認證中心保留拒絕簽發憑證之權利，且得免向申請人說明理由；申請經拒絕者，認證中心將無息退還憑證費用予申請人。</span><br>
<span style="width:70px; text-align:right; ">(四)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">憑證有效期間為自憑證簽發日起依申請年限開始計算。憑證有效期間屆滿者，用戶應重新申請簽發憑證，並依規定繳付憑證費用。</span><br>
<span style="width:70px; text-align:right; ">(五)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">申請人提出申請後，如需更換用戶憑證申請檔（CSR）或憑證者，應以書面載明更動事項，送交認證中心審核，憑證簽發前或憑證效期起始日起三十日內（以認證中心收件日為準，以下同）申請更改者，免收服務手續費用。</span><br>
<span style="width:30px;">三、</span>憑證使用規範︰<br>
<span style="width:70px; text-align:right; ">(一)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">用戶需依網頁伺服器軟體之指示安裝認證中心簽發之數位憑證，並應使用憑證啟動SSL加密機制。</span><br>
<span style="width:70px; text-align:right; ">(二)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">用戶需在其網頁中明顯之處安裝本公司提供之認證標章，以供使用者查詢憑證狀態。</span><br>
<span style="width:70px; text-align:right; ">(三)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">用戶應依據認證中心制定並公布於認證中心網站之「憑證實務作業基準」（以下簡稱CPS）規定使用憑證及其他相關服務。認證中心將不定期修改CPS，並公佈於認證中心網站。</span><br>
<span style="width:70px; text-align:right; ">(四)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">憑證限供申請用戶使用於申請書所載之公司（機構）、網站名稱下之單一伺服器。</span><br>
<span style="width:70px; text-align:right; ">(五)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">用戶不得使用憑證或於憑證中記載之網站上，從事任何違反法令之行為。</span><br>
<span style="width:70px; text-align:right; ">(六)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">憑證有效期間屆滿或經本公司廢止者，用戶應即停止使用憑證，並將憑證及認證標章自伺服器中移除。</span><br>
<span style="width:30px;">四、</span>憑證廢止︰<br>
<span style="width:60px; text-align:right; "></span><span style="width:880px;">除CPS規範之條款外，認證中心在下列情形下亦得主動廢止用戶所擁有之憑證，且不退還用戶已繳之任何費用︰</span><br>
<span style="width:70px; text-align:right; ">(一)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">用戶名稱或網站名稱更改，而未通知認證中心者。</span><br>
<span style="width:70px; text-align:right; ">(二)&nbsp;&nbsp;&nbsp;</span><span style="width:880px;">申請書填載事項或證明文件不實或用戶違反憑證使用規定者。</span><br>
<span style="width:30px;">五、</span>責任限制︰<br>
<span style="width:30px; text-align:right; "></span><span style="width:880px; text-decoration:underline; "><span style="width:32px; "></span>認證中心處理用戶註冊資料及憑證簽發作業，因故意或過失造成用戶損失，如該損害得以補行程序方式加以填補者，以補行程序方式為之;如損害不得以補行程序方式加以填補者，認證中心於憑證有效期間內之累計賠償金額以第二條第四項之服務費用九十倍為上限。</span><br>
<br>
<table>
<tr>
<td style="width:600px;">
<p><span>※如為「透過經銷商申請者」請填妥下列相關資料：</span></p>
<p><span>經銷商名稱：</span></p>
<p><span>聯絡人姓名：</span></p>
<p><span>聯絡人電話：</span></p>
<p><span>電子郵件地址：</span></p>
</td>
<td style="text-align:center; vertical-align:top; ">
<p><span>代理經銷商蓋章</span></p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p><span style="color:#DCDCDC; ">(用印處)</span></p>
</td>
</tr>
</table>
<br>
<table>
<tr>
<td>
<p><span style="width:950px; text-align:center; font-size:20px; font-weight:bold; ">申  請  單  位  確  認</span></p>
<span style="color:#DCDCDC; ">※ 首次申請之單位須用印公司大章及負責人章</span>
<span style="color:#DCDCDC; ">※ 憑證更新或重新申請或增購新憑證者可用公司大章加負責人章或申請單位部門章加申請人簽章</span>
</td>
</tr>
<tr>
<td style="color:#DCDCDC; height:250px; vertical-align:bottom;">
<span style="width:475px; text-align:center; ">公司大章</span><span style="width:475px; text-align:center; ">負責人章</span>
</td>
</tr>
</table>
臺灣網路認證(股)公司<br>
網址:http://www.twca.com.tw<br>
地址:臺北市延平南路85號10樓<br>
電話:(02)2370-8886  傳真(02)2370-0728
</div>

<div class="container-2">
<p><span style="font-size:22px; ">附件一：</span></p>
<table>
<tr>
<td style="width:50px; text-align:center;">
編號
</td>
<td style="width:200px; text-align:center;">
網站名稱（Common Name）
</td>
<td style="width:100px; text-align:center;">
技術聯絡人
</td>
<td style="width:100px; text-align:center;">
電話
</td>
<td style="text-align:center;">
E-MAIL
</td>
<td style="width:100px; text-align:center;">
申請日期
</td>
</tr>
<tr>
<td style="text-align:center;">
1
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
2
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
3
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
4
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
5
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
6
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
7
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
8
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
9
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
10
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
11
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
12
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
13
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
14
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
15
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
16
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
17
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
18
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
19
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
<tr>
<td style="text-align:center;">
20
</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
<td>

</td>
</tr>
</table>
</div>

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
    vertical-align: top;    
}
</style>
