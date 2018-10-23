<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>

<script>
$(document).ready(function(){
	
	var total="";
	var totalprice=0*1;
	var totalorder="";

 	$("select").change(function(){
		
 		var selqty=$(this).val()*1;
 		var selname=$(this).attr("name");
 		var beforeqty=$(this).siblings("input").val()*1;
 		var price=$(this).attr("accesskey")*1;
 		
 		
 		if(selqty==0){
 			totalorder+=selname+selqty;
 			if(beforeqty==4){
 				total+=selname+",";
 		 		totalprice+=selqty*price;
 		 		$(this).siblings("input").val('0');
 			}else if(beforeqty==2){
 				totalprice-=2*price;
 				total=total.replace(selname+",","");
 				$(this).siblings("input").val('0');
 			}else if(beforeqty==1){
 				totalprice-=1*price;
 				total=total.replace(selname+",","");
 				$(this).siblings("input").val('0');
 				
 			}else if(beforeqty==0){
 				
 				$(this).siblings("input").val('0');
 			}
 			
 		}else if(selqty==1){
 			totalorder+=selname+selqty;
 			if(beforeqty==4){
 				
 				total+=selname+",";
 		 		totalprice+=1*price;
 				$(this).siblings("input").val('1');
 			}else if(beforeqty==2){
 				totalprice-=1*price;
 				$(this).siblings("input").val('1');
 			}else if(beforeqty==1){
 				$(this).siblings("input").val('1');
 			}else if(beforeqty==0){
 				total+=selname+",";
 		 		totalprice+=selqty*price;
 				$(this).siblings("input").val('1');
 			}
 		}else{
 			totalorder+=selname+selqty;
 			if(beforeqty==4){
 				
 				total+=selname+",";
 		 		totalprice+=selqty*price;
 		 		$(this).siblings("input").val('2');
 			}else if(beforeqty==2){
 				
 				$(this).siblings("input").val('2');
 			}else if(beforeqty==1){
 				totalprice-=1*price;
 				$(this).siblings("input").val('2');
 			}else if(beforeqty==0){
 				total+=selname+",";
 		 		totalprice+=selqty*price;
 		 		$(this).siblings("input").val('2');
 			}		
 		}
 		
 		$("#totalprice").html(totalprice);
 		$("#total").html(total);
 		
	}); 
 	
 	$('.modal-content').attr("style","margin-top: 200px");
})
function printvegetable(){
	$("#vagetable").css("display","block");
	$("#fruit").css("display","none");
	$("#sidemenu").css("display","none");
	$("#topping").css("display","none");
	$("#dressing").css("display","none");

}
function printfruit(){
	$("#vagetable").css("display","none");
	$("#fruit").css("display","block");
	$("#sidemenu").css("display","none");
	$("#topping").css("display","none");
	$("#dressing").css("display","none");

}
function printtopping(){
	$("#vagetable").css("display","none");
	$("#fruit").css("display","none");
	$("#sidemenu").css("display","none");
	$("#topping").css("display","block");
	$("#dressing").css("display","none");

}
function printsidemenu(){
	$("#vagetable").css("display","none");
	$("#fruit").css("display","none");
	$("#sidemenu").css("display","block");
	$("#topping").css("display","none");
	$("#dressing").css("display","none");

}

function printdressing(){
	$("#vagetable").css("display","none");
	$("#fruit").css("display","none");
	$("#sidemenu").css("display","none");
	$("#topping").css("display","none");
	$("#dressing").css("display","block");
	
}

function makeOrder(){
	
	var data = '';
	$.each( $('#fm').serializeArray(), function(key, val){
	if(val['value']!=0&&val['value']!=4&&val['name']!='totaldata'&&val['name']!='price'){
	 data +=val['name']+"("+val['value']+"),";
	}
	});
	
	if(data==""){
		alert("아무것도 선택하지 않으셨습니다");
		return false;
	}
	
	$("#totaldata").attr("value",data);
	var price=$("#totalprice").html();
	$("#price").attr("value",price);
	

	return true;
	
}

</script>

<div class="container" style="margin-top:10px;">
	<div class="row">
		<div class="col-md-12">
			<h3>나만의 샐러드 만들기</h3>
			<table class="table">
			<tr>
				<td style="text-align:center;font-size:18px;">원하는 것만 담아 만드는 나만의 샐러드입니다<br>
				야채, 과일, 토핑, 사이드메뉴, 드레싱 모두 원하는 것만 담아<br>
				나만의 샐러드를 만들어보세요
				</td>
			</tr>
			
			<tr>
				<td align="center">
					<img src="resources/image/main/customsalad.png" style="border:2px solid #104835;border-radius:15px;width:40%;">
				</td>
			</tr>
			
			<tr>
				<td>
					<button type="button" style="height:50px;width:100%;color:white;background:#009E6B;" data-toggle="modal" data-target="#myModal">
						나만의 샐러드 만들기
					</button>
				</td>
			</tr>
			
			</table>

<!-- ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ -->

   <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
      aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
         <div class="modal-content">
            <div class="modal-header">
               <button onclick="printvegetable()"  class="btn btn-success">야채</button>
				<button onclick="printfruit()"  class="btn btn-success">과일</button>
				<button onclick="printtopping()"  class="btn btn-success">토핑</button>
				<button onclick="printsidemenu()"  class="btn btn-success">사이드메뉴</button>
				<button onclick="printdressing()"  class="btn btn-success">드레싱</button>
               <button type="button" class="close" data-dismiss="modal"
                  aria-label="Close">
                  <span aria-hidden="true">&times;</span>
               </button>
            </div>
            <div class="modal-body">
              <form id="fm" name="fm" action="customSaladAddCart.cart" method="post">
              
        <div id="printArea" style="width:550px;height:350px;overflow-y:auto;">
		
		<table class="table" id="vagetable">
			<c:forEach var="i" items="${vegetableList}">
				<tr>
					<td width=20%><img src="resources/image/product/${i.image}" style="width:50px;height:50px;border-radius:25px;"><td>
					<td width=40%>${i.name }</td>
					<td width=15%>${i.price }</td>
					<td width=25%>
					수량선택<br>
					<select name="${i.name }" class="form-control" accesskey="${i.price }">
						<option value="0">0</option>
						<option value="1">1</option>
						<option value="2">2</option>
					</select>
					<input type="hidden" value="4">
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<table class="table" id="fruit" style="display:none;">
			<c:forEach var="i" items="${fruitList}">
				<tr>
					<td width=20%><img src="resources/image/product/${i.image}" style="width:50px;height:50px;border-radius:25px;"><td>
					<td width=40%>${i.name }</td>
					<td width=15%>${i.price }</td>
					<td width=25%>
					수량선택<br>
					<select name="${i.name }" class="form-control" accesskey="${i.price }">
						<option value="0">0</option>
						<option value="1">1</option>
						<option value="2">2</option>
					</select>
					<input type="hidden" value="4">
					</td>
				</tr>
			</c:forEach>
		</table>
		
				<table class="table" id="topping" style="display:none;" >
			<c:forEach var="i" items="${toppingList}">
				<tr>
					<td width=20%><img src="resources/image/product/${i.image}" style="width:50px;height:50px;border-radius:25px;"><td>
					<td width=40%>${i.name }</td>
					<td width=15%>${i.price }</td>
					<td width=25%>
					수량선택<br>
					<select name="${i.name }" class="form-control" accesskey="${i.price }">
						<option value="0">0</option>
						<option value="1">1</option>
						<option value="2">2</option>
					</select>
					<input type="hidden" value="4">
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<table class="table" id="sidemenu" style="display:none;">
			<c:forEach var="i" items="${sidemenuList}">
				<tr>
					<td width=20%><img src="resources/image/product/${i.image}" style="width:50px;height:50px;border-radius:25px;"><td>
					<td width=40%>${i.name }</td>
					<td width=15%>${i.price }</td>
					<td width=25%>
					수량선택<br>
					<select name="${i.name }" class="form-control" accesskey="${i.price }">
						<option value="0">0</option>
						<option value="1">1</option>
						<option value="2">2</option>
					</select>
					<input type="hidden" value="4">
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<table class="table" id="dressing" style="display:none;">
			<c:forEach var="i" items="${dressingList}">
				<tr>
					<td width=20%><img src="resources/image/product/${i.image}" style="width:50px;height:50px;border-radius:25px;"><td>
					<td width=40%>${i.name }</td>
					<td width=15%>${i.price }</td>
					<td width=25%>
					수량선택<br>
					<select name="${i.name }" class="form-control" accesskey="${i.price }">
						<option value="0">0</option>
						<option value="1">1</option>
						<option value="2">2</option>
					</select>
					<input type="hidden" value="4">
					</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		
		<div id="printName" style="width:100%;">
		선택품목: <span style="color:red;font-weight:bold;" id="total"></span>
		</div>
		<div id="printPrice" style="width:100%;">
		가격: <span style="color:red;font-weight:bold;" id="totalprice"></span>
		</div>

		<input type="hidden" name="totaldata" value="" id="totaldata">
		<input type="hidden" name="price" value="" id="price">
		<input type="submit" value="장바구니담기" onclick="return makeOrder()" style="background:#009E6B;color:white;width:100%;border:1px solid white;border-radius:5px;">
         </form>
               
          </div>
         </div>
      </div>
   </div>

<!-- ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ -->		
									
		</div>
	</div>
</div>

<%@ include file="../main/bottom.jsp"%>