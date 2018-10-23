<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="i" items="${newtalklist}">
<tr><td>새로운 정보1: ${i.talk }</td></tr>
<tr><td>새로운 정보2: ${i.roomhoner }</td></tr>
</c:forEach>
