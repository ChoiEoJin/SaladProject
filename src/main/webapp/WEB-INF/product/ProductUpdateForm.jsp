<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../main/top.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>

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
                $("#upload").css('margin-top','3px');
                $("#imagePreview").children("img").attr("class","img-circle");
                $("#hideImg").css("display","none");
                $("#upload").css("display","inline");
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
</style>
</head>
<body>
	<br>
	<br>
	<h2 align="center">상품 수정</h2>
	<c:set var="theString"
		value="http://localhost:9090/${pageContext.request.contextPath}/resources/image/product/${product.image }" />


	<form:form commandName="product" action="update.prd" method="post"
		enctype="multipart/form-data">

		<div class="container">
			<div class="row">
				<div class="col-md-5">
					<br> <img id="hideImg" src="${theString}" width="400px"
						height="300px" />

					<div id="imagePreview"></div>
					<p class="form-controller">
						<label for="pictureurl"></label> <input type="file" name="upload"
							id="upload" onchange="InputImage();" style="margin-top: 3px;">

						<input type="hidden" name="upload2" id="upload"
							value="${product.image}">

					</p>
					
					<input type="hidden" name="num" value="${product.num}">
				</div>
				<div class="col-md-7">

					<table class="table">
						<tr>
							<td style="width: 20%; text-align: center;"><label
								for="name">상품명</label></td>
							<td><input type="text" name="name" value="${product.name}"
								style="width: 120px; text-align: right;"></td>
						</tr>

						<tr>
							<td style="width: 20%; text-align: center;"><label
								for="category">카테고리</label></td>
							<td><input type="text" name="category"
								value="${product.category}"
								style="width: 120px; text-align: right;"></td>
						</tr>

						<tr>
							<td style="width: 20%; text-align: center;"><label
								for="price">가격</label></td>
							<td><input type="text" name="price" value="${product.price}"
								style="width: 120px; text-align: right;">원</td>
						</tr>

						<tr>
							<td style="width: 20%; text-align: center;"><label
								for="country">원산지</label></td>
							<td><input type="text" name="country"
								value="${product.country}"
								style="width: 120px; text-align: right;"></td>
						</tr>

						<tr>
							<td style="width: 20%; text-align: center;"><label
								for="company">배급사</label></td>
							<td><input type="text" name="company"
								value="${product.company}"
								style="width: 120px; text-align: right;"></td>
						</tr>

						<tr>
							<td style="width: 20%; text-align: center;"><label
								for="price">중량</label></td>
							<td><input type="text" name="weight"
								value="${product.weight}"
								style="width: 120px; text-align: right;">g</td>
						</tr>

						<tr>
							<td style="width: 20%; text-align: center;"><label
								for="count">개수</label></td>
							<td><input type="text" name="count" value="${product.count}"
								style="width: 120px; text-align: right;"></td>
						</tr>

						<tr>
							<td style="width: 20%; text-align: center;"><label
								for="contents">설명</label></td>
							<td><input type="text" name="contents"
								value="${product.contents}"
								style="width: 120px; text-align: right;"></td>
						</tr>

						<tr>
							<td style="width: 20%; text-align: center;"><label
								for="contents">설명</label></td>
							<td><input type="text" name="contents"
								value="${product.contents}"
								style="width: 120px; text-align: right;"></td>
						</tr>

						<tr>
							<td><input type="submit" value="수정하기"
								style="margin-left: 130px;" onclick="return chk5();"></td>
						</tr>

					</table>

				</div>
			</div>
		</div>
	</form:form>


	<%@ include file="../main/bottom.jsp"%>