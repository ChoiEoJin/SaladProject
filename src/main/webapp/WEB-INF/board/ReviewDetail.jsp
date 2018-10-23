<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="./../../top.jsp"%>
<script type="text/javascript">	


var clickCount=0;
var currentPage=1;
function testMorviewResponse(){
	alert("testMorviewResponse");
	
}

function focusTextInputBox(){
	
	alert("focusTextInputBox도착");
	clickCount=0;
	document.getElementById("keyword").value="";
	document.getElementById("keyword").focus();
	
}

function removeWords(){	
	if(clickCount==0){
		document.getElementById("keyword").value = "";
		clickCount=1;		
	}		
}

function delReviewBoard(num,pageNumber,pageSize){
	var delCheck=confirm("정말 글을 지우시겠습니까? 글을 지우시면 댓글까지 모두 지워집니다");	
	if(delCheck==false){
				
	}else{		
			location.href="delReview.bd?num="+num+"&pageNumber="+pageNumber+"&pageSize="+pageSize;			
		}	
	}
function reviewReplyChk(num){
	
	if(document.getElementById(num).value==""){
		alert('내용을 입력해주세요');
		return false;
	}else{	
		var replyChek=confirm("이대로 댓글작성할까요?");
		if(replyChek==false){
			return false;
		}else{	
			alert('전달받은 num값'+num+'이 도착함');
			return true;
		}
	}
}	
function insertReview(){
	
	location.href="reviewInsert.bd";
	
}

function MoreView(){//2개 더보기 버튼을 클릭하면
	
	var pageNum=$("#pageNum").val()*1+1; //처음오면 1페이지받았으니 바로 2페이지로 + 시켜주는거지
	var pageSize=$("#pageSize").val(); //항상 2
	var totalPage=$("#totalPage").val()*1; //조건검색한 마지막페이지
	alert("totalPage="+totalPage);
	var searchedWhatColumn=$("#searchedWhatColumn").val();
	var searchedKeyword=$("#searchedKeyword").val();
	
		if(pageNum<totalPage || pageNum==totalPage){
			
			$.ajax({
				type:"POST",
				url:"moreView.bd",//detailViewController
				data:{"nextPageNumber":pageNum,"searchedWhatColumn":searchedWhatColumn,"searchedKeyword":searchedKeyword,"pageSize":pageSize},
				success:function(resultData){
					
					$("#maintable").append(resultData);
					$("#pageNum").attr("value",pageNum);// 추가한후에는 페이지넘버 올려준걸 적용 
				}
			});
			
		}else{//마지막페이지보다 커지면 더볼수없음(aajx안넘어감)
			
			pageNum=totalPage;
			alert("마지막 페이지 입니다.");
		}
	
}


 
 
</script>
<div class="container">
	<div class="row">
		<div class="col-md-14" id="maintable">
		
				<table class="table table-bordered" >
					<tr>
						<td><input type="button" value="글쓰기" class="form-control"
							style="background: #0a82ff; color: white;" onclick="insertReview();"></td>
					</tr>
				</table>

				<c:choose>
					<c:when test="${fn:length(reviewLists)==0}">
						<!-- 작성글없을때 -->
				<table class="table table-bordered">
					<tr>
						<td align="center" class="form-control" colspan="3">작성된 사용후기가 없습니다</td>
					</tr>
				</table>
					</c:when>
					<c:otherwise>

					<!-- 작성글있을때 -->
					<c:forEach var="list" items="${reviewLists }" varStatus="status"><!-- list는  각각의  ReviewBean -->
					<table  class="table table-bordered" >
					
					<!-- height:500px;width:100% -->
					<tr>
					<td width=65% rowspan="3">
					글번호${number-status.index }
					<img src="${pageContext.request.contextPath}/resources/image/board/${list.image }" alt="${list.image }" height=500px width=100%>
					</td>
					<td height=7% align="center" width=28%><h4>${list.subject}(${list.writer })</h4></td>
					<td width=17% align="center">${list.regdate }</td>
					</tr>
					
					<tr>
					<td colspan="2">
					<div style="height:80px; overflow-y:auto;">
					${list.content }<br>
					DB번호:${list.num }<br>
					지금페이지는 ${reviewPageInfo.pageNumber}이고,<br>
					현재 보고있는 페이지에서 시작하는 beginRow는 ${reviewPageInfo.beginRow }<br>
					현재 보고있는 페이지에서 끝나는 endRow는${reviewPageInfo.endRow}이다.<br>
					페이지 마지막페이지는${reviewPageInfo.endPage }
					
					</div>
					</td>					
					</tr> 
						
					<tr>	
					<td colspan="2">
					<c:choose>
					
					<c:when test="${fn:length(list.reviewRelpyBeanList)==0}">
					<div style="height:200px; overflow-y:auto; ">
						작성된 댓글이 없습니다
					</div>
					</c:when>
					
					<c:otherwise>
					<div style="height:200px; overflow-y:auto; ">
						
						<c:forEach var="rList" items="${list.reviewRelpyBeanList }">
						<span style="color:gray">${rList.writer }(${rList.regdate })</span><br>
						${rList.replyContent }<hr>
								
						</c:forEach>
						
					</div>
					</c:otherwise>
					
					</c:choose>
					
					<hr>
					<c:choose>		
						<c:when test="${sessionScope.userid != null}">
						<form action="ReviewReply.bd" method="POST"> 						
							<input type="hidden" name="num" value="${list.num }">  
							<input type="hidden" name="writer" value="${sessionScope.userid }">
							<input type="hidden" name="searchedWhatColumn" value="${searchedWhatColumn }">
							<input type="hidden" name="searchedKeyword" value="${searchedKeyword }">
							<input type="hidden" name="pageNumber" value="${reviewPageInfo.pageNumber}">
							<input type="hidden" name="pageSize" value="${reviewPageInfo.pageSize}">
							
							
							
							<textarea style="resize:none;" rows="2" cols="50" name="replyContent" id="${list.num }"></textarea>						
							<input type="submit" value="댓글달기" onclick="return reviewReplyChk('${list.num}');">
						 </form>
						</c:when>					
						<c:otherwise>
							<div style="height:10px">댓글 기능은 로그인 후 이용하실 수 있습니다</div>
						</c:otherwise>							
					</c:choose>					
					</td>
					</tr>												
					<c:if test="${sessionScope.userid eq list.writer }">
					<tr>
					<td colspan="3" align="right">
					<input type="button" value="글삭제" onclick="delReviewBoard('${list.num}','${reviewPageInfo.pageNumber }','${reviewPageInfo.pageSize }');"  style="background: #0a82ff; color: white;">
					</td>
					</tr>		
					</c:if>
					</table>
					</c:forEach> <!-- for문 밖에나왔음 -->
					</c:otherwise>
				</c:choose>
				
				</div>
					<form name="searchForm" action="ReviewDetail.bd" method="get">
						<table class="table table-bordered ">
							<tr>
								<td><select name="whatColumn" class='form-control' onchange="focusTextInputBox();">
										<option value="all"  <c:if test="${searchedWhatColumn eq 'all' }">selected</c:if> >전체검색</option>
										<option value="subject" <c:if test="${searchedWhatColumn eq 'subject' }">selected</c:if>>제목</option>
										<option value="writer" <c:if test="${searchedWhatColumn eq 'writer' }">selected</c:if>>작성자</option>
								</select></td>
								<td><input type="text" id="keyword" name="keyword"
									value="여기에서 검색하세요" onClick="removeWords();" class="form-control">
								</td>
								<td><input type="submit" value="검색" onClick="removeWords();"
									class="form-control" style="background: #0a82ff; color: white;">
								</td> 
							</tr>
						</table>
					</form>
					
						
						<input type="hidden" name="pageNumber" value="${reviewPageInfo.pageNumber }" id="pageNum">
						<input type="hidden" name="pageSize" value="${reviewPageInfo.pageSize }" id="pageSize">
						<input type="hidden" name="searchedWhatColumn" value="${searchedWhatColumn }" id="searchedWhatColumn">
						<input type="hidden" name="searchedKeyword" value="${searchedKeyword }" id="searchedKeyword">
						<input type="hidden" name="totalPage" value="${reviewPageInfo.totalPage}" id="totalPage">
						<button  onClick="return MoreView();">2개 더보기</button>
								
				
				 	
		</div>
</div>
<%@include file="../../bottom.jsp"%> --%>