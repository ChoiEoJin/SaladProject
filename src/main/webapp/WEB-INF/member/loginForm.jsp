<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>
<%
    String ctxPath = (String) request.getContextPath();
%>



<script type="text/javascript" src="resources/js/jsbn.js"></script>
<script type="text/javascript" src="resources/js/rsa.js"></script>
<script type="text/javascript" src="resources/js/prng4.js"></script>
<script type="text/javascript" src="resources/js/rng.js"></script>



<script type="text/javascript">


function rsalogin(){
    var id = $("#id");
    var pw = $("#pw");

    if(id.val() == ""){
        alert("아이디를 입력 해주세요.");
        id.focus();
        return false;
    }
    
    if(pw.val() == ""){
        alert("비밀번호를 입력 해주세요.");
        pw.focus();
        return false;
    }
    
    // rsa 암호화
    var rsa = new RSAKey();
    rsa.setPublic($('#RSAModulus').val(),$('#RSAExponent').val());
    
    $("#userid").val(rsa.encrypt(id.val()));
    $("#userpw").val(rsa.encrypt(pw.val()));
    
    id.val("");
    pw.val("");

    return true;
}

function searchid(){
	location.href="searchid.member";
}

function searchpw(){

	location.href="searchpw.member";
}

function gojoinform(){
	location.href="joinform.member";
}

</script>



<div class="container">
	<div class="row">
		<div class="col-md-10">
		<h3>로그인</h3>
		<form action="login.member" method="post"  onsubmit="return rsalogin()">
	
		<input type="hidden" id="RSAModulus" value="${RSAmodulus}"/>
        <input type="hidden" id="RSAExponent" value="${RSAExponent}"/>
        
		<table class="table">
		<tr>
		<td align=center>아이디</td>
		<td><input type="text" placeholder="아이디" id="id" class="form-control"/></td>
		<td rowspan=2><input type="submit" value="로그인" class="form-control" style="height:90px;background:#009E6B;color:white;font-weight:bold;"/></td>
		</tr>
		<tr>
		<td align=center>비밀번호</td>
		<td><input type="password" placeholder="비밀번호" id="pw" class="form-control"/></td>
		</tr>
		</table>
		
		<input type="hidden" id="userid" name="userid">
        <input type="hidden" id="userpw" name="userpw">
		
		</form>
		
		<table class="table">
			<tr>
				<td><input type="button" value="아아디찾기" style="color:#104835;align:center;font-weight:bold;" class="form-control" onclick="searchid()"></td>
				<td><input type="button" value="비밀번호찾기" style="color:#104835;align:center;font-weight:bold;" class="form-control" onclick="searchpw()"></td>
				<td><input type="button" value="회원가입" style="color:#104835;align:center;font-weight:bold;" class="form-control" onclick="gojoinform()"></td>
			<tr>
		</table>

		</div>
	</div>
</div>

<%@ include file="../main/bottom.jsp"%>