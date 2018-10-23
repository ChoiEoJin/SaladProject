<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>
<div class="container" style="margin-top:10px;">
	<div class="row">
		<div class="col-md-12" style="border-top:2px solid #104835;border-bottom:2px solid #104835;">
			<h3>총 결제내역</h3>
			<table class="table">
			<c:if test="${fn:length(paylist)==0 }">
				<tr>
					<td>결제 내역이 없습니다</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(paylist)!=0 }">
				<tr>
					<td width=10%>주문번호</td>
					<td width=50%>주문품목</td>
					<td width=10%>결제금액</td>
					<td width=15%>주문일자</td>
					<td width=15%>주문자</td>
				</tr>
				<c:forEach var="i" items="${paylist }">
				<tr>
					<td><span style="color:#009E6B;font-size: 18px;font-weight:bold;">${i.paynum}</span></td>
					<td>
					<c:forTokens items="${i.paypname }" delims="/" var="j">
						${j }<br>
					</c:forTokens>
					</td>
					<td><span style="color:red;font-size: 18px;font-weight:bold;">${i.payprice }</span></td>
					<td><fmt:formatDate value="${i.paydate }" pattern="yyyy-MM-dd hh:mm"/></td>
					<td><span style="color:#416de9;font-size: 15px;font-weight:bold;">${i.paywho }</span></td>
				</tr>	
				</c:forEach>
			</c:if>

			</table>
		<form action="makePayListForAdmin.pay" method="get">
		<table class="table">
			<tr>
				<td colspan=3>${payPaging.pagingHtml}</td>
			</tr>
			<tr>
				<td>
					<select name="whatColumn" class="form-control">
						<option value="paywho">주문자</option>
						<option value="paypname">주문품목</option>
					</select>
				</td>
				<td>
					<input type="text" name="keyword" placeholder="검색어를 입력하세요" class="form-control">
				</td>
				<td>
					<input type="submit" value="검색" class="form-control">
				</td>
			</tr>
		</table>
		</form>
		</div>
	</div>
</div>
<%@ include file="../main/bottom.jsp"%>