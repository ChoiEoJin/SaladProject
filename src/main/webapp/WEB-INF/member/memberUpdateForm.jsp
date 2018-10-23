<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>

<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
<script>

    $(function() { $("#postcodify").postcodify({
        insertPostcode5 : "#postcode",
        insertAddress : "#address",
        insertDetails : "#details",
       
        hideOldAddresses : false
    }); });
    
    function passwd_keyup(){ 
    	
    	if($("input[name=userpw]").val() == $("input[name=userpwchk]").val()){
    		$('#pwmessage').html("<font color=red>비번 일치</font>");
    		$('#pwmessage').show();
    	}else{
    		$('#pwmessage').html("<font color=red>비번 불일치</font>");
    		$('#pwmessage').show();
    	}
    	
    } 
    function chk(){

    	
    	if($("input[name=userid]").val()==""){
    		alert("아이디를 입력해주세요");
    		$("#idmessage").text("아이디가 입력되지 않았습니다");
    		return false;
    	}
    	
    	
     	if(userid.length<5||userid.length>15){
    		alert("아이디는 5자 이상 15자 미만으로 입력해주세요");
    		$("#idmessage").text("아이디가 입력되지 않았습니다");
    		return false;
    	} 
    	
    	if($("input[name=userpw]").val()==""){
    		alert("비밀번호를 입력해주세요");
    		$("#pwmessage1").text("비밀번호가 입력되지 않았습니다");
    		return false;
    	}
    	
    	if($("input[name=userpwchk]").val()==""){
    		alert("비밀번호를 입력해주세요");
    		$("#pwmessage2").text("비밀번호를 확인해주세요");
    		return false;
    	}
    	
    	if($("input[name=username]").val()==""){
    		alert("이름을 입력해주세요");
    		$("#namemessage").text("이름이 입력되지 않았습니다");
    		return false;
    	}
    	
    	if($("input[name=birthday]").val()==""){
    		alert("생일을 입력해주세요");
    		$("#birthdaymessage").text("생일이 입력되지 않았습니다");
    		return false;
    	}
    	
    	if($("input[name=email]").val()==""){
    		alert("이메일을 입력해주세요");
    		$("#emailmessage").text("이메일이 입력되지 않았습니다");
    	}

    	if($("input[name=phone]").val()==""){
    		alert("핸드폰번호를 입력해주세요");
    		$("#phonemessage").text("번호 입력이되지 않았습니다");
    		return false;
    	}
		if(isNaN($("input[name=phone]").val())){
			alert("연락처에는 숫자만 입력해주세요");
			return false;
		}    	
    	
    }
    
$(document).ready(function(){
	$("input[name=userpw]").blur(function(){
		var userpw=$("input[name=userpw]").val();
		var reg=/^[a-zA-Z0-9!@#$%^&*()]{6,15}$/;
		
		var lengthresult=false;
		
		
		if(reg.test(userpw)){
			lengthresult=true;
		}
		
		var regNum=/[0-9]/;
		var regText=/[a-zA-Z]/;
		var regEntity=/[!@#$%^&*()_+=-]/;
		
		if(userpw.match(regNum)==null||userpw.match(regText)==null||userpw.match(regEntity)==null||lengthresult==false){
		$("#pwmessage1").html("비밀번호 조건이 맞지 않습니다");
		$("#pwmessage1").css("color","red");
		$("input[name=userpw]").val("");
		$("input[name=userpw]").focus();
		}else{
			
			$("#pwmessage1").html("조건에 맞는 비밀번호입니다");
			$("#pwmessage1").css("color","red");
		}
	});
});
    
</script>


<div class="container" style="margin-top:10px;">
	<div class="row">
		<div class="col-md-12">
			<h3>개인정보수정</h3>
			
			<form action="updateMember.member" method="POST">
			<input type="hidden" name="usernum" value="${mBean.usernum }">
<table class="table">
<tr>
	<td width=15% >아이디</td>
	<td width=25%><input type="text" name="userid" class="form-control" value="${mBean.userid }" readonly></td>
	<td width=10%></td>
	<td width=50%></td>
</tr>
<tr>
	<td>비밀번호</td>
	<td colspan=2><input type="password" name="userpw" class="form-control" value="" ></td>
	<td><span id="pwmessage1">비밀번호는 필수정보입니다</span></td>
</tr>
<tr>
	<td>비밀번호확인</td>
	<td colspan=2><input type="password" name="userpwchk" class="form-control" value="" onkeyup="passwd_keyup()">
	<span id="pwmessage" style="display:none;"></span></td>

	<td><span id="pwmessage2">위의 비밀번호와 같게 입력해주세요</span></td>
	
</tr>
<tr>
	<td>이름</td>
	<td colspan=2><input type="text" name="username" class="form-control" value="${mBean.username }"></td>
	<td><span id="namemessage">이름은 필수정보입니다</span></td>
</tr>
<tr>
	<td>주소</td>
	<td colspan=3>
		<div id="postcodify">검색버튼을 눌러 주소를 입력해주세요</div>
			우편번호 <input type="text" name="zipcode" id="postcode" value="${mBean.zipcode }" class="form-control"/><br />
			도로명 주소 <input type="text" name="address1" id="address" value="${mBean.address1 }" class="form-control"/><br />
			상세주소 <input type="text" name="address2" id="details" value="${mBean.address2 }" class="form-control"/><br />
	</td>
	
</tr>
<tr>
	<td>생년월일</td>
	<td colspan=2><input type="date" name="birthday" class="form-control" value="${mBean.birthday }"></td>
	<td><span id="birthdaymessage">생일을 입력해주세요</span></td>
</tr>
<tr>
	<td>email</td>
	<td colspan=2><input type="email" name="email" class="form-control" value="${mBean.email }"></td>
	<td><span id="emailmessage">이메일을 입력해주세요</span></td>
</tr>
<tr>
	<td>연락처</td>
	<td colspan=2><input type="text" name="phone" class="form-control" value="${mBean.phone }"></td>
	<td><span id="phonemessage">연락처는 숫자로만 입력해주세요</span></td>
</tr>
<tr>
	<td colspan=4> 
		<input type="submit" value="정보수정하기" class="form-control" style="background:#0a82ff;color:white;" onclick="return chk();">
	</td>
</tr>
</table>
</form>
		
		</div>
	</div>
</div>
<%@ include file="../main/bottom.jsp"%>