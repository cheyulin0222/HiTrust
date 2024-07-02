<template>
<div class="container" style="width: 800px; height: 1155px;">
	<table style="width: 800px;">
		<tr>
			<td colspan="3">
				<table style="width: 800px;">
					<tr>
						<td align="center">
                            <span style="font-size:30px;">SALES NOTE</span>
							<template v-if="salesNote.cpdType.includes('TWCA')">(TWCA憑證專用)</template>
    						<template v-else>(SYMC憑證專用)</template>
                        </td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="3" align="right"><img src="@/assets/img/hitrust.jpg" />
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<table>
					<tr>
						<td style="width: 150px; " align="right">編號：</td>
						<td width="150px" align="center">{{ salesNote.saleNo }}</td>
					</tr>
				</table>
			</td>
		</tr>

		<tr>
			<td colspan="3">
				<table>
					<tr>
						<td>
							<table class="tb_border" rules="all" cellpadding="5"
								style="width: 400px;">
								<tr>
									<td><input type="checkbox"></td>
									<td>新單</td>
									<td>S/N NO.</td>
									<td width="200px"></td>
								</tr>
								<tr>
									<td><input type="checkbox"></td>
									<td>更動</td>
									<td>原S/N NO.</td>
									<td></td>
								</tr>
								<tr>
									<td><input type="checkbox"></td>
									<td>取消</td>
									<td>原S/N NO.</td>
									<td></td>
								</tr>
							</table>
						</td>
						<td>
							<table style="width: 200px;">
								<tr>
									<td><input type="checkbox">TAIWAN</td>
								</tr>
								<tr>
									<td><input type="checkbox">其他： <span
										style="width: 100px; text-align: center; display: inline-block;
	border-bottom: 1px solid;"></span></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</table>
						</td>
						<td>
							<table>
								<tr>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>填單日期(西元)：<span
										style="width: 70px; text-align: center; 
	border-bottom: 1px solid;">{{ formatDate(salesNote.currentDate) }}</span></td>
								</tr>
								<tr>
									<td>頁&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次： <span
										style="width: 110px; text-align: center; display: inline-block;
	border-bottom: 1px solid;"></span>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table style="width: 800px; border: 2px black solid" class="tb_border" rules="all"
					cellpadding="5">
					<tr>
						<td>
							<table>
								<tr>
									<td>
										<table>
											<tr>
												<td>一.</td>
												<td style="white-space:nowrap;">客戶名稱：</td>
												<td style="width: 300px; border-bottom: 1px solid; text-align: center;">{{ salesNote.nameCh }}</td>
												<td style="white-space:nowrap;">統一編號：</td>
												<td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.regNo }}</td>
											</tr>
											<tr>
												<td colspan="5"></td>
											</tr>
											<tr>
												<td></td>
												<td style="white-space:nowrap;">發票/請款地址：</td>
												<td style="width: 300px; border-bottom: 1px solid; text-align: center;">{{ salesNote.shipAddr }}</td>
												<td style="white-space:nowrap;">發票收件人：</td>
                                                <td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.salesNameCh }}</td>
											</tr>
											<tr>
												<td colspan="5"></td>
											</tr>
											<tr>
												<td></td>
												<td style="white-space:nowrap;">客戶公用名稱：</td>
                                                <td style="width: 300px; border-bottom: 1px solid; text-align: center;">{{ salesNote.commonName }}</td>
												<td></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td>二.</td>
												<td style="white-space:nowrap;">客戶業務聯絡人：</td>
												<td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.salesNameCh }}</td>
												<td>TEL：</td>
												<td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.salesTel }}</td>
												<td>Email：</td>
												<td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.salesEmail }}</td>
											</tr>
											<tr>
												<td colspan="5"></td>
											</tr>
											<tr>
												<td></td>
												<td style="white-space:nowrap;">客戶技術聯絡人：</td>
												<td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.techNameCh }}</td>
												<td>TEL：</td>
												<td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.techTel }}</td>
												<td>Email：</td>
												<td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.techEmail }}</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr>
									<td>
										<table>
											<tr>
												<td>三.</td>
												<td style="white-space:nowrap;">合約名稱：</td>
												<td>{{ !salesNote.cpdType.toLowerCase().includes('code') ?  '■' : '□' }}資料加密</td>
												<td>{{ salesNote.cpdType.toLowerCase().includes('code') ?  '■' : '□' }}元件加密</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td>四.</td>
												<td style="white-space:nowrap;">憑證有效期間：</td>
                                                <td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.effectiveDate }}</td>
                                                <td>至</td>
                                                <td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.expiryDate }}</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td>五.</td>
												<td style="white-space:nowrap;">服務總價：NTD.</td>
                                                <td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.amount }}</td>
                                                <td style="white-space:nowrap;">(未稅)&nbsp;&nbsp;&nbsp;NTD.</td>
                                                <td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.amountAddedTax }}</td>
                                                <td style="white-space:nowrap;">(含稅)</td>    
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td>六.</td>
												<td style="white-space:nowrap;">產品訂價：NTD.</td> 
                                                <td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.price }}</td>
                                                <td style="white-space:nowrap;">(未稅) </td>
												<td style="white-space:nowrap;">產品成本：NTD.</td>
                                                <td style="width: 200px; border-bottom: 1px solid; text-align: center;">{{ salesNote.costPrice }}</td>
                                                <td style="white-space:nowrap;">(未稅)</td>
											</tr>
											<tr>
												<td colspan="3"></td>
											</tr>
											<tr>
												<td></td>
												<td style="white-space:nowrap;">訂單編號：</td>
                                                <td style="width: 250px; border-bottom: 1px solid; text-align: center;"></td>
                                                <td style="white-space:nowrap;">下單日期：</td>
                                                <td style="width: 100px; border-bottom: 1px solid; text-align: center;"></td>
												<td></td>
                                                <td></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td>七.</td>
												<td>銷貨內容：</td>
											</tr>
											<tr>
												<td></td>
												<td>
													<table class="tb_border" rules="all" cellpadding="5"
														style="width: 750px; border: 2px black solid">
														<tr>
															<th style="width: 100px;">項目編號(S)</th>
															<th>續約狀態<br>(new/renew/add)
															</th>
															<th>銷貨項目</th>
															<th>年期</th>
															<th>數量</th>
															<th>總計</th>
														</tr>
														<tr>
															<td style="height: 30px;"></td>
															<td></td>
															<td style="text-align: center">{{ salesNote.cpdType }}</td>
															<td style="text-align: center">{{ salesNote.expiryType }}</td>
															<td style="text-align: center">1</td>
															<td style="text-align: center">{{ salesNote.total }}</td>
														</tr>
														<tr>
															<td style="height: 30px;">&nbsp;</td>
															<td></td>
															<td style="text-align: center"></td>
															<td style="text-align: center"></td>
															<td style="text-align: center"></td>
															<td style="text-align: center"></td>
														</tr>
														<tr>
															<td style="height: 30px;">&nbsp;</td>
															<td></td>
															<td style="text-align: center"></td>
															<td style="text-align: center"></td>
															<td style="text-align: center"></td>
															<td style="text-align: center"></td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td>八.</td>
												<td>收款條件：(開立發票後&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日內付款)</td>
											</tr>
											<tr>
												<td></td>
												<td>
													<table class="tb_border" rules="all" cellpadding="5"
														style="width: 750px; height: 30px; border: 2px black solid">
														<tr>
															<th style="width: 50px;">項次</th>
															<th style="width: 150px;">合約期款名稱</th>
															<th style="width: 100px;">金額(未稅)</th>
															<th style="width: 100px;">預計請款日</th>
															<th style="width: 100px;">發票號碼</th>
														</tr>
														<tr>
															<td style="text-align: center;">1</td>
															<td></td>
															<td align="right">{{ salesNote.amountExcludedTax }}</td>
															<td align="right"></td>
															<td></td>
														</tr>
														<tr>
															<td style="text-align: center;">2</td>
															<td></td>
															<td></td>
															<td></td>
															<td></td>
														</tr>
														<tr>
															<td style="text-align: center;">3</td>
															<td></td>
															<td></td>
															<td></td>
															<td></td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td>十.</td>
												<td>備註：</td>
											</tr>
											<tr>
												<td></td>
												<td>
													<table class="tb_border" rules="all" cellpadding="5"
														style="width: 750px; height: 90px; border: 2px black solid">
														<tr>
															<td></td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
			<td style="width: 5px;" valign="top">本單簽核完成後由營業管理部掃描存檔<br>，<br>正本留存於財務部<br>。
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<table>
					<tr>
						<td colspan="5">業務代表填妥本通知連同銷售合約或客戶簽認知報價單經核准後，送交財務部覆核後，轉送有關部門。</td>
					</tr>
                    <tr>
                        <td colspan="5">&nbsp;</td>
                    </tr>
					<tr>
						<td style="width: 100px;">業務代表：</td>
						<td><span style="width: 100px; display: inline-block;
	border-bottom: 1px solid;">&nbsp;</span></td>
						<td style="width: 100px;">副總經理：</td>
						<td><span style="width: 100px; display: inline-block;
	border-bottom: 1px solid;">&nbsp;</span></td>
						<td style="width: 380px;"></td>
					</tr>
                    <tr>
                        <td colspan="5">&nbsp;</td>
                    </tr>
					<tr>
						<td>業務主管：</td>
						<td><span style="width: 100px; display: inline-block;
	border-bottom: 1px solid;">&nbsp;</span></td>
						<td>經營管理處：</td>
						<td><span style="width: 100px; display: inline-block;
	border-bottom: 1px solid;">&nbsp;</span></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="5">&nbsp;</td>
					</tr>
					<tr>
						<td>技術主管：</td>
						<td><span style="width: 100px; display: inline-block;
	border-bottom: 1px solid;">&nbsp;</span></td>
						<td>財務部：</td>
						<td><span style="width: 100px; display: inline-block;
	border-bottom: 1px solid;">&nbsp;</span></td>
						<td align="right">True&nbsp;&nbsp;&nbsp;v.201602</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<div class="container container-2" style="width: 800px; height: 1155px; font-family: 標楷體">
	<table style="width: 800px;">
		<tr>
			<td><img alt="檔案不存在" src="@/assets/img/HiLogo.jpg"></td>
			<td><span style="width: 600px; text-align: right; display: inline-block;
	border-bottom: 1px solid;">Secure
					eCommerce, Trust HiTRUST</span></td>
		</tr>
		<tr>
			<td colspan="2" style="text-align: center;"><h2>伺服器憑證購買證明書</h2></td>
		</tr>
		<tr>
			<td colspan="2">
				<div
					style="font-size: 20px; line-height: 55px; letter-spacing: 5px;">
					茲證明 貴公司確於西元
					    {{ cvtChiDate(salesNote.orderDate) }}
					向本公司申購
					    {{ salesNote.cpdType }}
					憑證
					    {{ cvtChiNum(salesNote.expiryType) }}
					年期壹張，費用為新台幣NT$
					{{ salesNote.amountAddedTax }}
					元(含稅)， 並已由原廠核發供：
					{{ salesNote.commonName }}
					之網站使用， 憑證效期自西元
					{{ cvtChiDate(salesNote.effectiveDate) }}
					起至西元
					{{ cvtChiDate(salesNote.expiryDate) }}
					止。
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div
					style="font-size: 20px; line-height: 55px; letter-spacing: 5px;">
					此致<br>
					{{ salesNote.nameCh }}
				</div>
				<div
					style="font-size: 20px; line-height: 25px; letter-spacing: 5px; text-align: right;">
					立書人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
					網際威信股份有限公司<br> <br> <br> <br> <span
						style="width: 250px;">&nbsp;</span>
				</div>
				<div
					style="font-size: 24px; line-height: 25px; letter-spacing: 5px; text-align: center;">
					<br>西元
					{{ cvtChiDate(salesNote.currentDate) }}
					<br> <br> <br>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2"><span style="width: 800px; text-align: left; display: inline-block;
	border-bottom: 1px solid;">http://www.HiTRUST.com</span>
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
            salesNote : {
                type    : Object,
                required: true
            }
        },
        methods: {
            cvtChiDate(date) {
			let year = date.substring (0, 4);
			let month = date.substring (4, 6);
			let month1 = month % 10;
			let month2 = Math.floor(month / 10).toString();
			month2 = (month2 == '0') ? '' : month2;
			month = month2 + month1;
			let day = date.substring (6, 8);
			let day1 = day % 10;
			let day2 = Math.floor(day / 10).toString();
			day2 = (day2 == '0') ? '' : day2;
			day = day2 + day1;
			let strYear = this.cvtChiNum(year);
			let strMonth = this.cvtChiNum(month);
			let strDay = this.cvtChiNum(day);
			return strYear + '年' + strMonth + '月' + strDay + '日'
		    },
            cvtChiNum(num){
                var result = ''
                const chineseNum = ['o', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十'];
                if (num != null) {
                    num = num.toString();
                    for(let i = 0; i < num.length; i++){
                        result = result + chineseNum[num.substr(i, 1)];
                    }
                }
            
                return result;
            },
            formatDate(dateString) {
                const date = new Date(dateString.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3'));
                const year = date.getFullYear();
                const month = (date.getMonth() + 1).toString().padStart(2, '0');
                const day = date.getDate().toString().padStart(2, '0');
                return `${year}/${month}/${day}`;
            } 
        }
    }
</script>

<style scoped>
.container {
    font-size: 14px;
}
.tb_border {
    border: 2px black solid;
} 
span{
	display: inline-block;
	border-bottom: 1px solid;
}
</style>