<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>

<script>
var totalpaylist="";
	$(document).ready(function(){
		
		getPayList();
		getTotalpay(totalpaylist);
		$("input[name=paychk]").change(function(){
			totalpaylist="";
			getPayList();
			getTotalpay(totalpaylist);
		});
		

	});
	
	function getPayList(){
		$("input[name=paychk]").each(function(){
			if($(this).is(":checked")){
				totalpaylist+=$(this).val()+",";
			}
		});
	}
	
	function getTotalpay(totalpaylist){
		
		$.ajax({
			url:"getTotalPay.cart",
			data:{"totalpaylist":totalpaylist},
			success:function(data){
				$("#totalpay").html(data);}
		});
	}
	
	function goPay(){
		
		
		if(totalpaylist==""){
			alert("결제할 항목을 선택하지 않으셨습니다");
			return false;
		}else{
			var paymet=$("input[name=paymet]:checked").val();
			if(paymet!=undefined){
				alert("결제완료");
				location.href='goPayFromCart.pay?totalpaylist='+totalpaylist+"&paymet="+paymet;
				
			}else{
				alert("결제수단이 선택되지 않았습니다");
				return false;
			}
			
		}
		
	}
	
</script>

<div class="container" style="margin-top:10px;">
	<div class="row">
		<div class="col-md-12"  style="border-top:2px solid #104835;border-bottom:2px solid #104835;">
			<h3>장바구니</h3>
			
			<c:if test="${fn:length(cartlist)==0 }">
			<table class="table" >
			<tr>
				<td>장바구니에 담긴 품목이 없습니다</td>
			</tr>
			</table>
			</c:if>
			<c:if test="${fn:length(cartlist)!=0 }">
			<table class="table">
				<tr style="font-size:15px;">
					<th><input type="checkbox" class="form-control" name="allchk" onclick="chkcontrol()" checked></th>
					<th width=55%>품목</th>
					<th width=20%>수량</th>
					<th width=10%>가격</th>
					<th width=10%></th>
					
				</tr>
<c:forEach var="i" items="${cartlist }">
				<tr>
					<td><input type="checkbox" value="${i.cartnum}" name="paychk" checked></td>
					<td>${i.pname}</td>
					<td>
					<form action="cartUpdate.cart" method="post">
					<input type="hidden" name="cartnum" value="${i.cartnum }">
					<input type="number" name="qty" value="${i.qty}" style="width:20%;display:inline;">
					<input type="submit" value="수정" style="width:30%;" class="btn btn-success">
					</form>
					</td>
					<td>${i.multipleprice}</td>
					<td>
					<a href="cartDelete.cart?pmkey=${i.cartnum}"><button class="form-control" style="background:#be282a;color:white;">삭제</button></a>
					</td>
				</tr>
</c:forEach>
</table>
<table class="table">
<tr>
	<td>${loginfo.userid }님의 장바구니 총 금액:</td>
	<td style="color:blue;font-size: 20px;" colspan=3>${totalprice }</td>
	
</tr>
<tr>
	<td>${loginfo.userid }님의 선택항목 총 금액:</td>
	<td style="color:red;font-size:20px;" id="totalpay"></td>
	
	<td><input type="button" value="장바구니 모두 삭제" class="form-control" style="background:#be282a;color:white;"></td>
	<td><input type="button" value="선택한 항목 결제" class="form-control" data-toggle="modal" data-target="#myModal" style="background:#009E6B;color:white;">
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
         <div class="modal-content" style="margin-top:250px;">
            <div class="modal-header">
            	결제 수단을 선택해주세요
			</div>
            <div class="modal-body">
            	
            	계좌이체 <input type="radio" name="paymet" value="계좌이체"><br>
            	휴대폰결제 <input type="radio" name="paymet" value="휴대폰결제"><br>
            	카드결제<input type="radio" name="paymet" value="카드결제"><br>
            	무통장입금<input type="radio" name="paymet" value="무통장입금"><br><br>
            	
            	<input type="button" value="결제하기" onclick="return goPay()" class="form-control" style="background:#be282a;color:white;">
			</div>
		</div>
		</div>
	</div>
	</td>
</tr>
</table>		

			</c:if>


		</div>
	</div>
	<div class="row">
		<div class="col-md-12" align="center" style="margin-top:15px;">
			<img src="resources/image/main/cardinfo.png" >
		</div>
	</div>
</div>
<%@ include file="../main/bottom.jsp"%>