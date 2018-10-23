<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function passwdFocus(){	
	document.myDelForm.rePasswd.focus();
}
</script>



</head>
<body onload="passwdFocus();">

<form name="myDelForm" action="delete.bd?num=${num}&pageNumber=${pageNumber}&pageSize=${pageSize}" method="post">
<input type="hidden" name="whatColumn" value=${whatColumn }>
<input type="hidden" name="keyword" value=${keyword }>
<table>
	<tr>
		<td>비밀번호 </td>
		<td><input type="password" name="rePasswd" value=""></td>
	</tr>
	<tr>
	<td colspan="2"><a href="detailView.bd?num=${num}&pageNumber=${pageNumber}&pageSize=${pageSize}">뒤로가기 </a></td>
	</tr>
	<tr>
	<td colspan="2"><input type="submit" value="삭제하기"></td></tr>
</table>
</form>




</body>
</html>