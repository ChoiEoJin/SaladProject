<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-md-10">
			<h3>아이디 찾기</h3>
			<form action="searchIdPro.member" method="post">
				<table class="table">
					<tr>
						<td>이름</td>
						<td><input type="text" name="username" class="form-control"></td>
					</tr>
					<tr>
						<td>생년월일</td>
						<td><input type="date" name="birthday" class="form-control"></td>
					</tr>
					<tr>
						<td>연락처</td>
						<td><input type="text" name="phone" class="form-control" placeholder="숫자로만 입력해주세요(01011112222)"></td>
					</tr>
					<tr>
						<td colspan=2><input type="submit" class="btn btn-success" style="width:100%"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<%@ include file="../main/bottom.jsp"%>