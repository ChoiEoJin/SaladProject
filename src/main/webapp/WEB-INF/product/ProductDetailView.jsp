<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../main/top.jsp"%>
<html>
<head>
<title>상품 상세 화면</title>

<meta charset="UTF-8">
<style type="text/css">
</style>
<script>
function qtychk(id){
	if(id==""){
		alert("로그인 후 이용해주세요");
		return false;
	}
	if($("#orderqty").val()==""||$("#orderqty").val()==0){
		alert("수량을 제대로 입력하지 않았습니다");
		return false;
	}
}
</script>

</head>
<body>
	<c:set var="img"
		value="resources/image/product/${product.image }" />
	<div class="container">
		<div class="row">
			<div class="col-md-12" style="margin-top:10px;">
			<h3>상품 상세보기</h3>
			<table class="table">
				<tr align=center>
					<td colspan=3 align=center>
					
					<img src="${img}" style="width: 55%; height: 500px;border-radius:10px;margin-top:10px;margin-bottom:20px;"/>
					</td>
					
				</tr>
				<tr align=center>
					<td align=center width=30% style="border-right:1px solid #dddddd;">제품명</td>
					<td colspan=2 style="font-size:20px;">${product.name}</td>
				</tr>
				<tr align=center>
					<td align=center width=30% style="border-right:1px solid #dddddd;">원산지</td>
					<td colspan=2>${product.country}</td>
				</tr>
				<tr align=center>
					<td align=center width=30% style="border-right:1px solid #dddddd;">중량</td>
					<td colspan=2>${product.weight}(g)</td>
				</tr>
				<tr align=center>
					<td align=center width=30% style="border-right:1px solid #dddddd;">가격</td>
					<td colspan=2 style="color:red;">${product.price}원</td>
				</tr>
				<tr align=center>
					<td align=center width=30% style="border-right:1px solid #dddddd;">상세설명</td>
					<td colspan=2>${product.contents }</td>
				</tr>
				<tr align=center>
					<td colspan=2 align=right>
						<form action="goodsAddCart.cart" method="post">
                        <input type="hidden" name="pname" value="${product.name}">
                        <input type="hidden" name="price" value="${product.price }">
                        <input type="hidden" name="pnum" value="${product.num}">
                        <input type="number" name="qty" id="orderqty" value="1" style="width:30%"> 
                        <input type="submit" value="장바구니담기" class="btn btn-success" onclick="return qtychk('${sessionScope.loginfo.userid}')">
                    	</form> 
					</td>
				</tr>
				<tr>
					<td colspan=3 align=right><a href="plist.prd"><button class="form-control" style="background:#009E6B;color:white;">상품리스트로 돌아가기</button></a></td>
				</tr>
			</table>
			</div>
			
		</div>
	</div>

	<%@ include file="../main/bottom.jsp"%>