<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-md-10">
		<h3>비밀번호 찾기</h3>
			<form action="searchpw.member" method="post">
			<table class="table">
				<tr>
				<td>아이디</td>
				<td><input type="text" name="userid" class="form-control"></td>
				</tr>
				<tr>
				<td>이름</td>
				<td><input type="text" name="username" class="form-control"></td>
				</tr>
				<tr>
				<td>생년월일</td>
				<td><input type="date" name="birthday" class="form-control"></td>
				</tr>
				<tr>
				<td>Email</td>
				<td><input type="email" name="email" class="form-control"></td>
				</tr>
				<tr>
				<td colspan=2><input type="submit" value="비밀번호 찾기" class="form-control" style="background:#104835;color:white;"></td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</div>
<%@ include file="../main/bottom.jsp"%>