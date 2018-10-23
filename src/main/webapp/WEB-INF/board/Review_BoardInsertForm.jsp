<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>
<script type="text/javascript">
	function confirmMessage() {
		var reply = confirm("이대로 글을 작성할까요?");
		if (reply == true) {
			/* return true; */
		} else {
			return false;
		}
	}

	$(document).ready(function() {
		$("#image1").change(function() {
			previewImg(this);
		});

	});

	
	
	function previewImg(inputimage) {
		if (inputimage.files && inputimage.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$("#image1print").attr("src", e.target.result);
				$("#image1print").css("border-radius", "15px");
				$("#image1print").css("width", "200px");
				$("#image1print").css("height", "230px");
				$("#image1print").css("display", "inline");

			}
			reader.readAsDataURL(inputimage.files[0]);
		}
	}

	
	
	
	function deleteImg() {
		$("#image1print").attr("src", "#");
		$("#image1print").css("width", "0px");
		$("#image1print").css("height", "0px");
		$("#image1print").css("display", "none");
		$("#image1").attr("value", "");
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
			<form:form commandName="review" action="reviewInsert.bd" method="post" enctype="multipart/form-data">
				<table class="table table-bordered">
					<tr>
						<td>작성자</td>
						<td colspan="2"><input type="text" name="writer" value="${sessionScope.loginfo.userid }" readonly class="form-control"></td>
					</tr>
					<tr>
						<!-- 반드시적어야 -->
						<td>글제목</td>
						<td colspan="2"><input type="text" name="subject" value="${review.subject}">
						<form:errors cssClass="err" path="subject"/>
						</td>
					</tr>

					<tr><!-- 반드시업로드 해야 -->
						<td width=20%>이미지(업로드)</td>
						<td><form:errors cssClass="err" path="image"/>
			 				<input type="file" name="upload" id="image1"/>	
							<input type="button" value="이미지지우기" onclick="deleteImg();"></td>
						<td><img style="display: none;" src="#" id="image1print" alt="이미지 미리보기"></td>
					</tr>

					<tr height="30%"><!-- 반드시적어야 -->
						<td width=20%>글내용</td>
						<td colspan=2 width=80%>
							<textarea style="resize: none;"	name="content" class="form-control" rows="20">
							${review.content }
							</textarea></td>
					</tr>
					<tr>
						<td colspan="3">
							<input type="submit" value="작성하기" onClick="return confirmMessage();" class="btn btn-danger">
						</td>
					</tr>
				</table>


			</form:form>
		</div>
	</div>
</div>

<%@ include file="../main/bottom.jsp"%>