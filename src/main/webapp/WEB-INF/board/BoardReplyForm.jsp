<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>
<script>
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
	         $("#image1print").css("width","300px");
	         $("#image1print").css("height","230px");
	         $("#image1print").css("display","inline");
	         
	      }
	      reader.readAsDataURL(inputimage.files[0]);
	   }
	}
</script>
<style type="text/css">
.err{
font-size:9pt;
color:red;
font-weight:bold;
}
</style>
<div class="container">
	<div class="row">
		<div class="col-md-10"> 
		<h3>ADMIN답변 작성하기</h3>
<form:form commandName="board" action="reply.bd" method="post" encType="multipart/form-data">

	<input type="hidden" name="num" value="${replyBean.num }">
	<input type="hidden" name="ref" value="${replyBean.ref }">
	<input type="hidden" name="restep" value="${replyBean.restep }">
	<input type="hidden" name="relevel" value="${replyBean.relevel }">
	<input type="hidden" name="passwd" value="1234"><!-- 필요없긴함 -->			
	<input type="hidden" name="pageNumber" value="${pageNumber }">
	<input type="hidden" name="pageSize" value="${pageSize }">
	
	<table class="table table-hover">
		<tr>
		<td>열람종류</td>
		<td>
			<select name="secret">
				<option value="0">일반글</option>
				<option value="1">비밀글</option>							
			</select>
		</td>
		</tr>
		<tr>
			<td width=20%>작성자</td>
			<td><input type="text" name="writer" value="${sessionScope.loginfo.userid }" readonly class="form-control" >
				
			</td>
		<tr>
			<td width=20%>제목</td>
			<td colspan=3><input type="text" name="subject" value="[답변]${replyBean.writer }님 답변입니다." class="form-control">
				<form:errors cssClass="err" path="subject"></form:errors></td>
		</tr>			
		<tr>
			<td width=20%>이미지</td>
			<td><input type="file" name="upload" id="image1"></td>
		</tr>
		
		<tr>
			<td width=20%>이미지 미리보기</td>
			<td><img style="display:none;" src="#" id="image1print" alt="이미지 미리보기" ></td>
		</tr>
		<tr>
			<td colspan=4><textarea style="resize:none;" name="content" class="form-control" rows="20"><form:errors cssClass="err" path="content"/>${replyBean.writer }님 안녕하세요.<br>저희 서비스를 이용하여 주셔서 감사드립니다.</textarea>	
			</td>
		</tr>
		<tr>
			<td align="right" colspan=4><input type="submit" value="답글쓰기" class="btn btn-danger">		
			</td>	
		</tr>
	</table>
</form:form>
</div>
</div>
</div>
<%@ include file="../main/bottom.jsp"%>