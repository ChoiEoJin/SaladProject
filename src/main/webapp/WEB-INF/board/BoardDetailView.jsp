<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
function Update(num,pageNumber,pageSize){
	
	location.href="update.bd?num="+num+"&pageNumber="+pageNumber+"&pageSize="+pageSize;
}

function Delete(num,pageNumber,pageSize){
	
	location.href="delete.bd?num="+num+"&pageNumber="+pageNumber+"&pageSize="+pageSize;
}

function AdminReply(num,pageNumber,pageSize){	
	
	location.href="reply.bd?num="+num+"&pageNumber="+pageNumber+"&pageSize="+pageSize;	
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<body>
<div class="container" style="margin-top:10px;">
	<div class="row">
		<div class="col-md-12">
		
		<h3>글 상세보기</h3>
		
		<center>
	<form>			
	
	<table class="table table-bordered">
	
	
	<tr>
	<td width=10% >조회수</td>
	<td width=40%>${board.readcount}</td>

	<td rowspan="4" align="center" width=45%>
		
			<c:choose>
				<c:when test="${board.image eq null}">
					첨부된 이미지가 없습니다
				
				</c:when>
				
				<c:otherwise>
				<br>첨부이미지<br>
				<br>
				<img src="${pageContext.request.contextPath}/resources/image/board/${board.image }" alt="이미지" width="100%" height="400px"><br>
				</c:otherwise>
			
			</c:choose>		

		</td>
	
	
	
	
	</tr>
	<tr>
		<td width=10% >글작성자</td>
		
		<td >
			${board.writer }
			<input type="hidden" name="writer" value="${board.writer }">
		</td>	
		
		
		
		
		
		
	</tr>	
	<tr>
		<td width=10% >글제목</td>
		<td >
			${board.subject }
			<input type="hidden" name="subject" value="${board.subject }">
		</td>	
		
		
		
			
	</tr>	
	<tr height=30%>
		<td colspan="2">
		<div style="width:100%; height:450px; border:1px solid #dddddd; padding:15px;">
		${board.content }
		<input type="hidden" name="content" value="${board.content }">
		
		</div>
		
		</td>
		
	</tr>
	
	<tr >
		<td colspan="2"><a href="list.bd?pageNumber=${pageNumber}&pageSize=${pageSize}&whatColumn=${whatColumn}&keyword=${keyword}">리스트로 돌아가기</a></td>		
	</tr>	
	
	
	
	
	<c:if test="${sessionScope.loginfo.userid eq board.writer || sessionScope.loginfo.userid eq 'admin'}">	
	<tr>
	<td colspan="2">
		
		<input type="button" value="삭제하기" onclick="Delete('${board.num}','${pageNumber }','${pageSize }','${whatColumn }','${keyword }');">			
		<c:if test="${sessionScope.loginfo.userid eq board.writer}">
		<input type="button" value="수정하기" onclick="Update('${board.num}','${pageNumber }','${pageSize }','${whatColumn }','${keyword }');">
		</c:if>
	
	</td>
	</tr>
	
	
	
	<!-- 관리자가 아니면 답변달기 버튼이 안보임 -->
	<c:if test="${sessionScope.loginfo.userid eq 'admin' }">
		<tr>
			<td colspan="2">
				<input type="button" value="답변작성" onclick="AdminReply('${board.num}','${pageNumber }','${pageSize }');">
			</td>
		</tr>
	</c:if>	
	</c:if>
	</table>		
	<input type="hidden" name="passwd" value="${board.passwd }">
	</form>
	</center>
</div>
</div>
</div>	
	    
<%@ include file="../main/bottom.jsp"%>

</body>
</html>