<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>샐러드프로젝트</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>

	#sidenavbar{
		position: fixed;
		right:20px;
		top:400px;
		width:200px;
		z-index: 1;
	}
	.sidebar{
		width:100%;
		background:#009E6B;
		font-size:18px;
		border:2px solid white;
		color:white;
		padding:5px 0px;
		border-radius:5px;
	}
	.jumbotron {
   text-shadow: black 0.2em 0.2em 0.2em;
   color: white;
   background-image: url('resources/image/main/boximage1.png');
   background-size: cover;
   width: 100%;
   height: 100%;
   font-size: 5px;
}

	blockquote {
	   border-left: 5px solid;
	   font-size: 30px;
	}
	

</style>
<script type="text/javascript">
$(document).ready(function(){
	
	$("#displaybar").mouseenter(function(){
		$("#hiddenbar").slideDown(200);
	});

	$("#hiddenbar").mouseenter(function(){
		$("#hiddenbar").show();
	})
	$("#hiddenbar").mouseleave(function(){
		$("#hiddenbar").slideUp(200);
	})
});



function consult(){
	window.open("loginChkForTalk.talk","talk","width=450,height=600,left=800,top=200,menubar=no,toolbar=no,location=no,status=no,resizable=no,scrollbars=yes");
}
</script>

</head>
<body>


	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-md-12" style="background: #104835;height:100px;text-align: center;">
			<br>
			<a href="main.main">
				<span style="font-size: 33px;font-weight: bold;color:white;">샐러드 프로젝트</span></a>
			</div>
			
			<div class="col-md-12" style="background: #104835;height:50px;text-align:right;">
			
				<c:choose>
					<c:when test="${loginfo eq null }">
						<a href="loginForm.member" style="color:white;">로그인</a>
						<a href="joinform.member" style="color:white;">회원가입</a>
					</c:when>
					<c:otherwise>
						<c:if test="${loginfo.userid eq 'admin' }">
						<span style="color:white">${loginfo.userid}님 접속</span><br>
						<a href="SalesAnalytics.pay" style="color:white;">매출분석 페이지로 이동 |</a>
						<a href="#" style="color:white;">정보수정 |</a>
						<a href="logout.member" style="color:white;">로그아웃</a>
						</c:if>
						<c:if test="${loginfo.userid ne 'admin' }">
						<span style="color:white">${loginfo.userid}님 접속</span><br>
						<a href="updateMemberInfo.member" style="color:white;">정보수정 |</a>
						<a href="viewCartLoginCheck.cart" style="color:white;">장바구니 |</a>
						<a href="goPayList.pay" style="color:white;">결제내역보기 |</a>
						<a href="logout.member" style="color:white;">로그아웃</a>
						</c:if>
					</c:otherwise>	
				</c:choose>
			</div>
		</div>
	</div>	
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-md-12" style="background:#009E6B;color:white;font-weight:bold;" id="displaybar" >
				<table width=100% style="height:20px;margin-top:5px;margin-bottom:5px;">
					<tr align="center">
						<td width=12%></td>
						<td width=12%><a href="costomsalad.salad" style="color:white;">Salad<br>Customizing</a></td>
						<td width=12%>About</td>
						<td width=12%><a href="plist.prd" style="color:white;">STORE</a></td>
						<td width=12%><a href="flist.prd" style="color:white;">SALAD</a></td>
						<td width=12%><a href="list.bd?pageNumber=1&pageSize=10" style="color:white;">Q&A</a></td>
						<td width=12%><a href="ReviewList.bd?pageNumber=1&pageSize=9&whatColumn=searchAll" style="color:white;">REVIEW</a></td>
						<td width=12%></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="container-fluid" style="position: absolute;z-index: 6;width:100%;opacity: 0.9;">
		<div class="row-fluid">
			<div class="col-md-12"
				style="background: white; color: #104835; display: none; font-weight: bold;"
				id="hiddenbar">
				<table width="100%">
					<tr align="center" style="height: 40px;">
						<td width=12%></td>
						<td width=12% style="border-right: 2px solid white;"></td>
						<td width=12% style="border-right: 2px solid white;">공지사항</td>
						<td width=12% style="border-right: 2px solid white;">야채</td>
						<td width=12% style="border-right: 2px solid white;">다이어트용</td>
						<td width=12% style="border-right: 2px solid white;"></td>
						<td width=12%></td>
						<td width=12%></td>
					</tr>
					<tr align="center" style="height: 40px;">
						<td width=12%></td>
						<td width=12% style="border-right: 2px solid white;"></td>
						<td width=12% style="border-right: 2px solid white;">인사의말</td>
						<td width=12% style="border-right: 2px solid white;">과일</td>
						<td width=12% style="border-right: 2px solid white;">영양섭취</td>
						<td width=12% style="border-right: 2px solid white;"></td>
						<td width=12%></td>
						<td width=12%></td>
					</tr>
					<tr align="center" style="height: 40px;">
						<td width=12%></td>
						<td width=12% style="border-right: 2px solid white;"></td>
						<td width=12% style="border-right: 2px solid white;"><a
							href="map.main">매장정보</a></td>
						<td width=12% style="border-right: 2px solid white;">토핑</td>
						<td width=12% style="border-right: 2px solid white;">몸만들기용</td>
						<td width=12% style="border-right: 2px solid white;"></td>
						<td width=12%></td>
						<td width=12%></td>
					</tr>
					<tr align="center" style="height: 40px;">
						<td width=12%></td>
						<td width=12% style="border-right: 2px solid white;"></td>
						<td width=12% style="border-right: 2px solid white;"></td>
						<td width=12% style="border-right: 2px solid white;">사이드메뉴</td>
						<td width=12% style="border-right: 2px solid white;"></td>
						<td width=12% style="border-right: 2px solid white;"></td>
						<td width=12%></td>
						<td width=12%></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!-- 컨테이너 끝 -->

<div id="sidenavbar" style="z-index: 9;">

<button class="sidebar" onclick="javascript:location.href='main.main';">HOME</button>
<button class="sidebar" onclick="javascript:location.href='costomsalad.salad';">나만의샐러드</button>
<button class="sidebar" onclick="javascript:location.href='plist.prd';">상품목록</button>
<c:choose>
	<c:when test="${loginfo.userid eq 'admin' }">
		<button class="sidebar" onclick="javascript:location.href='list.prd';">상품관리</button>
	</c:when>
	<c:otherwise>
		<c:if test="${loginfo.userid ne 'admin' }">
		<button class="sidebar" onclick="javascript:location.href='viewCartLoginCheck.cart';">장바구니</button>
		</c:if>
	</c:otherwise>
</c:choose>
<button class="sidebar" onclick="javascript:location.href='goPayList.pay';">결제내역</button>
<button class="sidebar" onclick="javascript:location.href='ReviewList.bd?pageNumber=1&pageSize=9&whatColumn=searchAll';">상품후기</button>
<button class="sidebar" onclick="consult()">
1:1상담
<c:if test="${loginfo ne null }">
	<c:if test="${loginfo.userid eq 'admin'}">
		[${sessionScope.newChatCount }]
	</c:if>
</c:if>
</button>
</div>
