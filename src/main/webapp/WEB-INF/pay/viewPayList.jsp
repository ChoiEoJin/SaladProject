<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-md-12" style="border-top:2px solid #104835;border-bottom:2px solid #104835;margin-top:10px;">
			<h3>${loginfo.userid}님의 결제내역입니다</h3>
			<table class="table">
			<c:if test="${fn:length(paylist)==0 }">
				<tr>
					<td>결제 내역이 없습니다</td>
				</tr>
			</c:if>
			<c:if test="${fn:length(paylist)!=0 }">
				<tr>
					<th width=25%>주문번호</th>
					<th width=40%>주문품목(수량)</th>
					<th width=10%>결제금액</th>
					<th width=25%>주문일자</th>
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
				</tr>	
				</c:forEach>
			</c:if>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12" align="center" style="margin-top:15px;">
		<a href="main.main"> <button class="btn btn-success" style="width:48%;">메인으로 돌아가기</button></a>
		<a href="viewCartLoginCheck.cart"> <button class="btn btn-warning" style="width:48%;">장바구니 가기</button></a>
		</div>
	</div>
</div>
<%@ include file="../main/bottom.jsp"%>