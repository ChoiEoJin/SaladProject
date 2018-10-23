<%@page import="member.model.MemberBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">	


function reviewReplyChk(num){
	
	if(document.getElementById(num).value==""){
		alert('내용을 입력해주세요');
		return false;
	}else{	
		var replyChek=confirm("이대로 댓글작성할까요?");
		if(replyChek==false){
			return false;
		}else{	

			return true;
		}
	}
}	

function backList(){
	
	history.back();
	
}


function closeWindowFnc(){
	
	window.close(this);
	
}

function FavoriteBTN(){
	
	
	var num=$("#Num").val()*1;
	var chkFavorite=$("#whetherLoginId_doesLikethis").val()*1; //-1,0,1 
	
	if(chkFavorite == -1){
		
		alert("로그인 후에 이용해주세요!");
		
	}else if(chkFavorite ==0){ //좋아요추가 작업
		
		//alert("로그인상태이지만, 좋아요누르지않았습니다 :"+chkFavorite);
		
		
		$.ajax({ //추가작업 실시간 동기화 
			url:"LoginUserLikeThisReview.bd",
			data:{"num":num},
			success:function(resultdata){
				
				$("#favoriteDIV").replaceWith(resultdata);	
				$("#whetherLoginId_doesLikethis").attr("value",1);	
				//alert("좋아요 추가! :"+$("#whetherLoginId_doesLikethis").val());
			}
		});
		

		
		
		
		

	}else{//좋아요취소 작업
		
		//alert("로그인 상태이고, 이미 좋아요를 눌렀습니다");		
		
		 $.ajax({//삭제작업 실시간 동기화 
			url:"LoginUserDoesNotLikeThisReview.bd",
			data:{"num":num},
			success:function(resultdata){
				
				$("#favoriteDIV").replaceWith(resultdata);		
				$("#whetherLoginId_doesLikethis").attr("value",0);	
				//alert("좋아요 삭제! :"+$("#whetherLoginId_doesLikethis").val());
				
			}
		});
	}
}

</script>
<style type="text/css">


</style>

<title>Insert title here</title>
</head>
<body style="background-image: url('resources/image/board/test/frame9.jpg'); opacity: 80%;">
	<div class="container" >
		<div class="row">
			<div class="col-md-14" id="maintable"  style="background-image: url('resources/image/board/test/frame5.jpg');">
				<br>
				<br>
				<br>
				<input type="hidden" value="${reviewbean.num }" id="Num">
				<c:if test="${sessionScope.loginfo ne null}"> <!--로그인 상태일때-->
				<input type="hidden" value="${whetherLoginId_doesLikethis }" id="whetherLoginId_doesLikethis">
				</c:if>
				
				<c:if test="${sessionScope.loginfo eq null }"><!-- 로그인 상태가아닐때  -->
				<input type="hidden" value="-1" id="whetherLoginId_doesLikethis">
				</c:if>
				
				<table class="table table-bordered" style="background-color: #F6F6F6; ">
				<tr>
					<td width=65% rowspan="3">				
					<img class="img-thumbnail" src="${pageContext.request.contextPath}/resources/image/board/${reviewbean.image }" alt="${reviewbean.image }" height=500px width=100%>					
					</td>					
					<td>
					${reviewbean.subject }(${reviewbean.writer })
					</td>				
					<td>
					(${reviewbean.regdate })
					</td>
				</tr>			
				<tr>
					<td colspan="2">
						<div style="height:80px; overflow-y:auto;">
						${ reviewbean.content}
					</div>
					</td>					
				</tr> 
				<tr>	
					<td colspan="2">
					<c:choose>				
					<c:when test="${fn:length(reviewbean.reviewRelpyBeanList)==0}">
					<div style="height:200px; overflow-y:auto; ">
						작성된 댓글이 없습니다
					</div>
					</c:when>					
					<c:otherwise>
					<div style="height:200px; overflow-y:auto; ">					
						<c:forEach var="rList" items="${reviewbean.reviewRelpyBeanList }">
						<span style="color:gray">${rList.writer }(${rList.regdate })</span><br>
						${rList.replyContent }<hr>								
						</c:forEach>					
					</div>
					</c:otherwise>				
					</c:choose>				
					<hr>
					<c:choose>		
						<c:when test="${sessionScope.loginfo != null}">
						<form action="ReviewReply.bd" method="POST"> 						
							<input type="hidden" name="num" value="${reviewbean.num }">  
							<input type="hidden" name="writer" value="${sessionScope.loginfo.userid }">
							<input type="hidden" name="searchedWhatColumn" value="${searchedWhatColumn }">
							<input type="hidden" name="searchedKeyword" value="${searchedKeyword }">
							<input type="hidden" name="pageNumber" value="${reviewPageInfo.pageNumber}">
							<input type="hidden" name="pageSize" value="${reviewPageInfo.pageSize}">																		
							<textarea style="resize:none;" rows="2" cols="50" name="replyContent" id="${reviewbean.num }"></textarea>						
							<input type="submit" value="댓글달기" onclick="return reviewReplyChk('${reviewbean.num}');">
						 </form>
						</c:when>					
						<c:otherwise>
							<div style="height:10px">댓글 기능은 로그인 후 이용하실 수 있습니다</div>
						</c:otherwise>							
					</c:choose>	
					<hr>	
					<div id="favoriteDIV">				
					<!-- <input type="button" onclick="FavoriteBTN();" value="좋아요" id="favorite"> -->	
					
					<c:choose>
					<c:when test="${whetherLoginId_doesLikethis == 1}"><!-- 이전에 좋아요 이미 눌렀을때 -->
					<img src="${pageContext.request.contextPath}/resources/image/board/dislikebtn.png" alt="likebutton.png" onclick="FavoriteBTN();" style="width:100px;height:50px;">
					</c:when>
					<c:otherwise><!-- 로그인 상태가 아니거나, 좋아요를 누르지 않았을 때 -->
					<img src="${pageContext.request.contextPath}/resources/image/board/likebutton.png" alt="likebutton.png" onclick="FavoriteBTN();" style="width:100px;height:50px;">
					</c:otherwise>
					</c:choose>	
					<c:choose>
						<c:when test="${HowManyUserlikethis == 0 }">
							<span style="color:#819FF7;">제일 먼저 좋아요를 눌러주세요!</span>
						</c:when>
						<c:otherwise>
							${HowManyUserlikethis }명이 좋아합니다<br>
							<span style="color:#819FF7;">${reviewbean.people }</span>님이 좋아합니다.
						</c:otherwise>														
					</c:choose><br>				
					</div>				
					</td>
					</tr>				
					<tr>
					<td colspan="3"><input type="button" value="닫기" onclick="closeWindowFnc();" class="form-control" style="background-color:lightseagreen; color:white;"></td>
					</tr>																				
				</table>			
			</div>
		</div>
	</div>
