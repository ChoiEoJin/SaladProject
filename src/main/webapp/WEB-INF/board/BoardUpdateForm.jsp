<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>



<style type="text/css">

.err{
font-size:9pt;
color:red;
font-weight:bold;

}


</style>
<script type="text/javascript">

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
	         $("#hideImg").css("display","none");//먼저 가리고
	         $("#image1print").css("display","inline");//대체이미지 	         	        		         
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

function confirmMessage(){
	
	var reply = confirm("이대로 글을 수정할까요?");
	
	if(reply==true){
		
		/* return true; */
		
	}else{
		return false;		
	}
	
}

function goBack(){
	
	history.back();
	
}

</script>



<div class="container">
	<div class="row">
		<div class="col-md-10"> 

	<form:form commandName="board"  action="update.bd" method="post" enctype="multipart/form-data">			
	
	<input type="hidden" name="num" value="${board.num }">
	<input type="hidden" name="passwd" value="${board.passwd }"><form:errors cssClass="err" path="passwd"/>	
	<input type="hidden" name="pageNumber" value="${pageNumber }">
	<input type="hidden" name="pageSize" value="${pageSize }">
	<input type="hidden" name="notice" value="${board.notice }">
	<input type="hidden" name="whatColumn" value="${whatColumn }">
	<input type="hidden" name="keyword" value="${keyword }">
	
	<table class="table table-hover">	
	<tr>
		<td width=20%>읽기권한</td>
		<td width=80%><select name="secret">
			<option value=0>일반글</option>
			<option value=1>비밀글</option>
		</select></td>
	</tr>
	
	<tr>
		<td width=20%>글작성자</td>
		<td width=80%><input type="text" name="writer" value="${board.writer }" readonly class="form-control"><form:errors cssClass="err" path="writer"/></td>
		
	</tr>	
	<tr>
		<td width=20%>글제목</td>
		<td width=80% class="form-control"><input type="text" name="subject" value="${board.subject }"><form:errors cssClass="err" path="subject"/></td>
		<form:errors cssClass="err" path="subject"/>
		
	</tr>	
	
	<tr>	
	<td colspan="2">그림파일:<input type="file" name="upload" id="image1"/><br>
	<c:if test='${board.image ne null}'>
			<img style="float:right;" id="hideImg" src="${pageContext.request.contextPath}/resources/image/board/${board.image }" alt="이미지" width="100" height="100"><br>		
	</c:if>
	<img style="display:none; float:right;" src="#" id="image1print" alt="이미지 미리보기" >
	<input type="button" value="미리보기 지우기" onclick="deleteImg();">
	<input type="hidden" name="upload2" value="${board.image }">
	</td>
	</tr>
	<tr  height="30%">
		<td>글내용</td>
<td><textarea name="content" style="resize:none;" class="form-control" rows="20">${board.content }</textarea></td>
	</tr>	
	</table>	
	<input style="float:right; background-color:lightseagreen; color:white;" type="submit" value="작성하기" onClick="return confirmMessage();">	
	<input style="float:left; background-color:lightseagreen; color:white;" type="button" value="뒤로가기" onClick="goBack();">
	</form:form>
	
	</div>
	</div>
	</div>
	
	<%@ include file="../main/bottom.jsp"%>

