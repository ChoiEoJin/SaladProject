<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp" %>

<script type="text/javascript">
function chk(){
	if($("input[name=userid]").val()==""){
		alert("아이디를 입력해주세요");
		return false;
	}
	if($("input[name=userpw]").val()==""){
		alert("비밀번호를 입력해주세요");
		return false;
	}
	if($("input[name=joincode]").val()==""){
		alert("조인코드를 입력해주세요");
		return false;
	}
}
</script>

<div class="container">
<div class="row">
<div class="col-md-10">
<h3>이메일 인증</h3>
<form action="emailchk.member" method="post">
	<table class="table table-hover">
	<tr>
		<td colspan=2 align="center">
			서비스를 이용하기 위해선 이메일 인증이 필요합니다<br>
			가입하신 아이디,비밀번호와 함께 인증번호를 입력해주세요<br>
			
		</td>
	</tr>
	<tr>
		<td>아이디</td>
		<td><input type="text" name="userid" class="form-control"></td>
	</tr>
	<tr>
		<td>비밀번호</td>
		<td><input type="password" name="userpw" class="form-control"></td>
	</tr>
	<tr>
		<td>인증번호</td>
		<td><input type="text" name="joincode" class="form-control"></td>
	</tr>
	<tr>
		<td colspan=2><input type="submit" class="form-control" style="background:#FFE400" onclick="return chk()"></td>
	</tr>
	</table>
</form>
</div>
</div>
</div>
<%@ include file="../main/bottom.jsp"%>