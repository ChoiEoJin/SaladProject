<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../main/top.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<title>상품 추가 화면</title>
<meta charset="UTF-8">

<script type="text/javascript">

/* 이미지 미리보기 */

var InputImage = 
 (function loadImageFile() {
    if (window.FileReader) {
        var ImagePre; 
        var ImgReader = new window.FileReader();
        var fileType = /^(?:image\/bmp|image\/gif|image\/jpeg|image\/png|image\/x\-xwindowdump|image\/x\-portable\-bitmap)$/i; 
 
        ImgReader.onload = function (Event) {
            if (!ImagePre) {
                var newPreview = document.getElementById("imagePreview");
                ImagePre = new Image();
                ImagePre.style.width = "400px";
                ImagePre.style.height = "300px";
                newPreview.appendChild(ImagePre); 
                $("#upload").css('margin-top','0px');
                $("#imagePreview").children("img").attr("class","img-circle");
            }
            ImagePre.src = Event.target.result;
            
            
        };
 
        return function () {
         
            var img = document.getElementById("upload").files;
           
            if (!fileType.test(img[0].type)) { 
             alert("이미지 파일을 업로드 하세요"); 
             return; 
            }
            
            ImgReader.readAsDataURL(img[0]);
        }
 
    }
   
             document.getElementById("imagePreview").src = document.getElementById("image").value; 
 			
      
})();
/* 이미지 미리보기 끝 */

 function chk5(){
	
	 
	 
	 
    	if($("input[name=name]").val()==""){
    		alert("상품명을 입력해주세요");
    		return false;
    	}
    	
    	if($("input[name=price]").val()==""){
    		alert("가격을 입력해주세요");
    		return false;
    	}
    	
    	if($("input[name=category]").val()==""){
    		alert("카테고리를 선택하세요");
    		return false;
    	}
    	
    	if($("input[name=country]").val()==""){
    		alert("원산지를 입력해주세요");
    		return false;
    	}
    	
    	if($("input[name=company]").val()==""){
    		alert("배급사 입력해주세요");
    		return false;
    	}
    	
    	if($("input[name=weight]").val()==""){
    		alert("중량을 입력해주세요");
    	}

    	if($("input[name=count]").val()==""){
    		alert("개수를 입력해주세요");
    		return false;
    	}
		if($("input[name=contents]").val()==""){
			alert("설명을 입력해주세요");
			return false;
		}    	
    }

</script>

<style type="text/css">
.err {
	font-size: 9pt;
	color: red;
	font-weight: bold;
}
p{
	
	margin-bottom:15px;
	font-size:18px;
}
input{
	margin-left:15px;
}
</style>
</head>
<body>
<br>
<br>
<br>
<br>
<br>
	<div class="container">
		<div class="row">
		<form:form commandName="product" method="post" action="insert.prd"
					enctype="multipart/form-data">
			<div class="col-md-5">
			
					
				
					
					<br>
					<div id="imagePreview"></div>
					<p class="form-controller">
						<label for="pictureurl">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> <input type="file"
							name="upload" id="upload"  onchange="InputImage();" style="margin-top:120px;">
					</p>
					

					
			
			</div>
			<div class="col-md-7">
					
				<table class="table">
				<tr>
					<td>
						<label for="name">상품명</label> <input type="text" name="name"
							id="name" class="form-controller">
					</td>
				</tr>
				
				<tr>
					<td>
						<label for="category">카테고리</label> <select name="category"
							id="category">
							<option value="finishedgoods">완제품</option>
							<option value="fruit">과일</option>
							<option value="vegetable">야채</option>
							<option value="dressing">드레싱</option>
							<option value="topping">토핑</option>
							<option value="sidemenu">사이드메뉴</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>
						<label for="price">가격&nbsp;&nbsp;&nbsp;</label> <input type="text" name="price"
							id="price" value="1000">원
					</td>
				</tr>
				
				<tr>
					<td>
						<label for="country">원산지</label> <input type="text" name="country"
							id="country">
					</td>
				</tr>
				
				<tr>
					<td>
						<label for="company">배급사</label> <input type="text" name="company"
							id="company">
					</td>
				</tr>
				
				<tr>
					<td>
						<label for="price">중량&nbsp;&nbsp;&nbsp;</label> <input type="text" name="weight"
							id="weight">g
					</td>
				</tr>
				
				<tr>
					<td>
						<label for="count">개수&nbsp;&nbsp;&nbsp;</label> <input type="text" name="count"
							id="count">
					</td>
				</tr>
				
				<tr>
					<td>
						<label for="contents">설명&nbsp;&nbsp;&nbsp;</label> <input type="text"
							name="contents" id="contents">
					</td>
				</tr>
				
				<tr>
					<td>
							<input type="submit" value="추가하기" style="margin-left:130px;"  onclick="return chk5();">
					</td>
				</tr>
				
				</table>
			
					</div>
				</form:form>
			</div>
		</div>

<%@ include file="../main/bottom.jsp" %>

