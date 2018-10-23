<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<body style="background:#a0c0d7;">


<table style="width:100%;margin-top:10px;" id="viewtable">
<tr>
<td>
<span style="font-size: 16px;font-weight: bold;">${roomhoner}님의 채팅방입니다</span>
<input type="hidden" id="roomhoner" value="${roomhoner }">
</td>
</tr>
<c:forEach var="i" items="${list }" varStatus="status">
		
		<c:if test="${status.last }">
			<c:choose>
			<c:when test="${i.talker=='admin'}">
			<tr style="height:28px;">
				<td>
				<span style="background:white;padding:5px 10px;color:black;" class="badge">${i.talk }</span>
				<span style="font-size: 4px;"><fmt:formatDate value="${i.talktime }" type="date" pattern="yyyy/MM/dd"/></span>
				<input type="hidden" value="${i.talknum }" id="talknum">
				<input type="hidden" value="${i.roomhoner }">
				</td>
				<c:set var="roomhoner" value="${i.roomhoner }"></c:set>
			</tr>
			</c:when>
			<c:otherwise>
			<tr style="height:28px;">
				<td align="right">
					<span style="font-size: 4px;"><fmt:formatDate value="${i.talktime }" type="both" pattern="yyyy/MM/dd hh:mm"/></span>
					<span style="background:#ffeb00;padding:5px 10px;color:black;" class="badge">${i.talk }</span>
					<input type="hidden" value="${i.talknum }" id="talknum">
					<input type="hidden" value="${i.roomhoner }">
				</td>
				<c:set var="roomhoner" value="${i.roomhoner }"></c:set>
			</tr>
			</c:otherwise>
		</c:choose>
		</c:if>
		<c:if test="${not status.last }">
		<c:choose>
			<c:when test="${i.talker=='admin'}">
			<tr style="height:28px;">
				<td>
				<span style="background:white;padding:5px 10px;color:black;" class="badge">${i.talk }</span>
				<span style="font-size: 4px;"><fmt:formatDate value="${i.talktime }" type="date" pattern="yyyy/MM/dd"/></span>
				</td>
			</tr>
			</c:when>
			<c:otherwise>
			<tr style="height:28px;">
				<td align="right">
					<span style="font-size: 4px;"><fmt:formatDate value="${i.talktime }" type="both" pattern="yyyy/MM/dd hh:mm"/></span>
					<span style="background:#ffeb00;padding:5px 10px;color:black;" class="badge">${i.talk }</span>
				</td>
				<c:set var="roomhoner" value="${i.roomhoner }"></c:set>
			</tr>
			</c:otherwise>
		</c:choose>
		</c:if>
</c:forEach>
</table>



</body>
</html>