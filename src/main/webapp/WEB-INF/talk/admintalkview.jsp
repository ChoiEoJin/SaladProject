<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<title>Insert title here</title>
</head>
<body>
<div class="container-fluid">
<div class="row-fluid">
<div class="col-md-12">
<table class="table">
<caption>답변이 필요한 메시지</caption>
<tr>
	<th width=20%>
		아이디
	</th>
	<th width=50%>
		메시지
	</th>
	<th width=30%>
		시간
	</th>
		
</tr>
<c:forEach var="i" items="${newTalkList}">
<tr>
	<td>
		${i.talker }
	</td>
	<td>
		<a href="adminview.talk?roomhoner=${i.roomhoner}">${i.talk }</a>
	</td>
	<td>
		<fmt:formatDate value="${i.talktime }" pattern="MM/dd hh:mm"/>
	</td>
</tr>
</c:forEach>

</table>
</div>
</div>
</div>


</body>
</html>