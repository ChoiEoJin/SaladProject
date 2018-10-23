<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<head>
<style type="text/css">
.err{
font-size:9pt;
color:red;
font-weight:bold;

}

</style>
<script type="text/javascript">
function confirmMessage(){	
	var reply = confirm("이대로 글을 작성할까요?");	
	if(reply==true){		
		/* return true; */		
	}else{
		return false;		
	}	
}

$(document).ready(function(){
	   $("#image1").change(function(){
	      asd(this);
	   });

	});

function asd(inputimage){
	   if(inputimage.files&&inputimage.files[0]){
	      var reader=new FileReader();
	      reader.onload=function(e){
	         $("#image1print").attr("src",e.target.result);
	         $("#image1print").css("border-radius","15px");
	         $("#image1print").css("width","200px");
	         $("#image1print").css("height","230px");
	         $("#image1print").css("display","inline");
	        
	         
	      }
	      reader.readAsDataURL(inputimage.files[0]);
	   }
	}

function deleteImg(){
	 $("#image1print").attr("src","#");
   
     $("#image1print").css("width","0px");
     $("#image1print").css("height","0px");
     $("#image1print").css("display","none");
     $("#image1").attr("value","");
}

function goBack(){
	
	history.back();
	
}
</script>
<title>보드 글쓰기</title>
</head>
<body>
 <div class="container">
	<div class="row">
		<div class="col-md-10"> 
		<h3>글쓰기</h3>
	<form:form commandName="board"  action="insert.bd" method="post" enctype="multipart/form-data">			
	<table class="table table-hover">	
		
			
		<c:if test="${sessionScope.loginfo.userid eq 'admin' }">
		
			<tr>
				<td width=20%>게시종류
				</td>
				
				<td colspan=2 width=80%>
					<select name="notice">
						<option value="1" selected>공지</option>				<!-- 관리자글 -->
					</select>
					<input type="hidden" name="secret" value="0"> <!-- 일반글(공개글) -->
				</td>
			</tr>
		
		
		</c:if>
	
	
	   <c:if test="${sessionScope.loginfo.userid ne 'admin' }">
			<tr>
				<td width=20%>읽기권한</td>
				<td colspan=2 width=80%>
					<select name="secret">
						<option value=0 selected>일반글</option>
						<option value=1>비밀글</option>
					</select>
				</td>
			</tr>	
			<input type="hidden" name="notice" value="0">	<!-- 이용자글 -->
		</c:if>
		
		
	<tr>
		<td width=20%>글작성자</td>
		<td colspan=2 width=80%><input type="text" name="writer" value="${sessionScope.loginfo.userid }" readonly class="form-control"><form:errors cssClass="err" path="writer" /></td>
	</tr>	
	
	
	<tr>
		<td width=20%>글제목 </td>
		<td colspan=2 width=80%><input type="text" name="subject" value="글제목">
		<form:errors cssClass="err" path="subject"/>
		</td>

	</tr>	
		
	<tr>
	<td width=20%>이미지(업로드)</td>
	<td><input type="file" name="upload" id="image1"/><input type="button" value="미리보기 지우기" onclick="deleteImg();"></td>
	<td><img style="display:none;" src="#" id="image1print" alt="이미지 미리보기" ></td>

	</tr>		
	<tr height="30%">
		<td width=20%>글내용</td>
<td colspan=2 width=80%><textarea style="resize:none;" name="content" class="form-control" rows="20"><form:errors cssClass="err" path="content"/></textarea></td>
	</tr>		
	</table>		
	<input style="float:right; background-color:lightseagreen; color:white;" type="submit" value="작성하기" onClick="return confirmMessage();" class="btn btn-danger">
	<input style="float:left; background-color:lightseagreen; color:white;" type="button" value="뒤로가기" onClick="goBack();">
	<input type="hidden" name="passwd" value="1234">	
	</form:form>
	
	
	
	
	</div>
	</div>
	</div> 
	
	
	
	
	<%@ include file="../main/bottom.jsp"%>
</body>
</html>