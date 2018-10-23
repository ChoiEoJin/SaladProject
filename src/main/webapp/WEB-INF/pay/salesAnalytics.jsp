<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script>
	google.charts.load('current', {packages:['corechart']});
</script>

<script>

</script>
<script>


function datesum(){
	var date1=$("#date1").val();
	var date2=$("#date2").val();
	var date3=date1.replace(/-/gi,"");
	date3=date3*1;
	var date4=date2.replace(/-/gi,"");
	date4=date4*1;
	if(date3>date4){
		alert("끝 날짜가 시작 날짜보다 큽니다");
	}else{
	$.ajax({
		url:"chart.pay",
		data:{"date1":date1,"date2":date2},
		success:function(result){
			$("#script_div").html(result);
		}
	})
	}
}

function openPersonAnal(){
	$.ajax({
		url:"salesPersonAnalytics.pay",
		success:function(result){
			$("#scriptdiv2").html(result);
			google.charts.setOnLoadCallback(drawChart);
			google.charts.setOnLoadCallback(drawChart2);
			sungbiprint();
			avgAgeprint();
			$("#personTable").css("display","");
		}
	})
}

</script>



<style>
#today{
	background: red;
	height:23px;
	width:${todaySales/10/2}px;
	color:white;
	text-align:right;
}
</style>
<div class="container">

	<div class="row">
		<div class="col-md-12">
			<h3>매출관리</h3>
			<table class="table table-bordered">
			<tr align="center">
				<td colspan=4 style="font-weight:bold;">매출조회를 하고 싶은 기간을 입력하세요</td>
			</tr>
			<tr align="center">
				<td colspan=2><b>시작 날짜</b></td>
				<td colspan=2><b>끝 날짜</b></td>
			</tr>
			<tr align="center">
				<td colspan=2>
				<input type="date" name="date1" class="form-control" id="date1" value="2017-12-12">
				</td>
				<td colspan=2>
				<input type="date" name="date2" class="form-control" id="date2" value="2018-01-03">
				</td>
			</tr>
			<tr align="center">
				<td colspan=4>
				<button onclick="datesum()" class="form-control" style="background:#009E6B;color:white;">조회하기</button>
				</td>
			</tr>
			<tr align="center">
				<td colspan=4>
					<div id="script_div">
					
					</div>
					<div id="chart_div" style="width:100%">
					</div>
				</td>
			</tr>
			
			
			<tr align="center">
				<td><b>오늘의 매출</b></td>
				<td><b>어제 매출</b></td>
				<td><b>어제와의 차이</b></td>
				<td><b>지난 7일 평균 매출</b></td>

			</tr>
			<tr align="center">
				<td><span style="color:red;font-size:28px;">
				<fmt:formatNumber value="${todaySales}" pattern="###,###"/>
				원</span>

				</td>
				<td><span style="color:red;font-size:28px;">
				<fmt:formatNumber value="${yesterdaySales}" pattern="###,###"/>
				원</span></td>
				<td>
				<span style="color:blue;font-size:28px;">
				<c:if test="${todaySales-yesterdaySales>0}">
				+
				</c:if>
				<fmt:formatNumber value="${todaySales-yesterdaySales}" pattern="###,###"/>
				원</span></td>
				<td><span style="color:red;font-size:28px;">
				<fmt:formatNumber value="${divideWeekSales}" pattern="###,###"/>
				원</span></td>

			</tr>
			<tr align="center">
				<td><b>객 단가(평균 결제 금액)</b>
				</td>
				<td><b>이번달 총 매출</b></td>
				<td><b>지난 달 총 매출</b></td>
				<td><b>최고 인기 상품 TOP5</b></td>
			</tr>
			<tr align="center">
				<td style="vertical-align:middle;"><span style="color:red;font-size:28px;">
				<fmt:formatNumber value="${personByPrice}" pattern="###,###"/>
				원</span></td>
				<td style="vertical-align:middle;"><span style="color:red;font-size:28px;">
				<fmt:formatNumber value="${monthSales}" pattern="###,###"/>
				원</span></td>
				<td style="vertical-align:middle;"><span style="color:red;font-size:28px;">
				<fmt:formatNumber value="${beforeMonthSales}" pattern="###,###"/>
				원</span></td>
				<td style="vertical-align:middle;">
					<span style="color:#104835;font-size: 19px; font-weight:bold;">
					<c:forEach var="i" items="${bestSales}" varStatus="status">
					${status.index+1}위: ${i}<br>
					</c:forEach>
					</span>
				</td>
			</tr>
			<tr>
			<td colspan=4>
			<button class="form-control" style="background:#104835;color:white;" onclick="openPersonAnal();"> 고객 분석 데이터 보기</button>
			</td></tr>
			</table>
			
			
			
			<table style="display:none;" class="table table-bordered" id="personTable">
			<tr>
			<td style="background:#009E6B;color:white;text-align:center;font-weight: bold;" width=50%>
				결제고객 성비
			</td>
			<td style="background:#009E6B;color:white;text-align:center;font-weight: bold;" width=50%>
				결제고객 평균연령
			</td>
			</tr>
			<tr>
			<td>
				<table width=100%>
				<tr align="center">
				<td width=25%>
				<img src="resources/image/main/man.png" style="width:100px;">
				</td>
				<td width=25% style="color:#00adef;font-size:30px;" id="mansungbi">
				
				</td>
				<td width=25% style="color:#ed1b24;font-size:30px;" id="womansungbi">
				
				</td>
				<td width=25%>
				<img src="resources/image/main/woman.png" style="width:100px;">
				</td>
				</tr>
				</table>
			</td>
			<td>
				<div id="div4" style="font-size:30px;text-align:center;margin-top:15px;">
				
				</div>
			</td>
			</tr>
			<tr>
			<td style="background:#009E6B;color:white;text-align:center;font-weight: bold;" width=50%>
				결제수단 분포
			</td>
			<td style="background:#009E6B;color:white;text-align:center;font-weight: bold;" width=50%>
				거주지 분포
			</td>
			</tr>
			<tr>
			<td>
				<div id="div1" style="width:100%;height:300px;"></div>
			</td>
			<td>
				<div id="div2" style="width:100%;height:300px;"></div>
			</td>
			</tr>
			
			</table>
			
			
			<div id="scriptdiv2"></div>	
		</div>
	</div>
</div>
<%@ include file="../main/bottom.jsp"%>