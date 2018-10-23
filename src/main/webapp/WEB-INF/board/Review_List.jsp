<%@page import="java.util.ArrayList"%>
<%@page import="board.model.ReviewLikeDao"%>
<%@page import="board.model.ReviewLikeBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../main/top.jsp"%>
<script type="text/javascript">

var searchClickCount=0;

function showAddr(){
	var aa=$("#aa").val();


}
function insertReview(){
	
	location.href="reviewInsert.bd";
	
}


function focusTextInputBox(){
	
	document.getElementById("keyword").value="";
	searchClickCount=1;//이거없으면, 아무리 박스입력했어도 searchClickCount=0 focus기능떔에 인풋박스온거기때문에 실제클릭안했을시 클릭카운트=0이므로 넘어갈때 value="" 으로 넘어간다(null)
	document.getElementById("keyword").focus();	
}

function removeWords(){	//여기에서 검색하세요 눌렀을때 혹은 그냥 여기에서 검색하세요 안누르고 바로 검색눌렀을때
	if(searchClickCount==0){
		document.getElementById("keyword").value = "";
		searchClickCount=1;
		
	}		
}


function ajaxMoreView(){
	
	
	var searchedWhatColumn=$("#searchedWhatColumn").val();
	var searchedKeyword=$("#searchedKeyword").val();
	var moreViewPageSize=$("#moreViewPageSize").val();//3
	var moreViewPageNumber=$("#currentPageNumber").val()*1+1;
	var totalPage=$("#totalPage").val()*1;

	//totalCount 22개기준 총필요한 페이지수 6개 9+3+3+3+3+1
	
	//alert(moreViewPageNumber+moreViewPageNumber);
	
	
	
	if(moreViewPageNumber<=totalPage){
	
		 $.ajax({
			url:"moreView.bd",
			data:{"showedPageNumber":moreViewPageNumber,"searchedWhatColumn":searchedWhatColumn,"searchedKeyword":searchedKeyword,"moreViewPageSize":3},
			success:function(resultdata){
				$("#maintable").append(resultdata);
				$("#currentPageNumber").attr("value",moreViewPageNumber);
			}
		}) 
	
	}else{
		moreViewPageNumber=totalPage;
		alert("마지막페이지입니다"+moreViewPageNumber);	
	}
	
	
	
	
	
	
}
function abcde(a,b,c,d,e){
	alert("a= "+a+",b= "+b+",c= "+c+",d= "+d+",e= "+e);

} 



function deleteFunction(Num,Pagenumber,Pagesize,Whatcolumn,Keyword){
	
	alert("deleteFunction도착");
	var num=Num;
	var pageNumber=Pagenumber;
	var pageSize=Pagesize;
	var whatColumn=Whatcolumn;
	var keyword=Keyword;
	
	 var delCheck=confirm("정말 글을 지우시겠습니까? 글을 지우시면 댓글까지 모두 지워집니다");	
	if(delCheck==false){
				
	}else{		
		 $.ajax({
				url:"delReview.bd",
				data:{"num":num,"pageNumber":pageNumber,"whatColumn":whatColumn,"keyword":keyword,"pageSize":pageSize},
				success:function(resultdata){
					alert(resultdata);
					$("#maintable").replaceWith(resultdata);				
				}
			}) 	
	}  	
}

				
function detailPopup(a,b,c,d,e){
window.open("ReviewDetail.bd?num="+a+"&pageNumber="+b+"&pageSize="+c+"&whatColumn="+d+"&keyword="+e,"detailview","width=1050,height=600,left=400,top=150,menubar=no,toolbar=no,location=no,status=no,resizable=no,scrollbars=yes");
}

</script>
<style>
#maintable{
border-spacing:20px;
border-collapse: separate;
}
</style>

<div class="container">
	 <div class="row">
		<div class="table-responsive" style="background-color:none;"> 	
		<div>
		<h3>나만의 샐러드를 소개해보세요</h3>
		</div>			
		<table class="table table-bordered" >
				<tr>
					<td><input type="button" value="글쓰기" class="form-control"
							style="background: #104835; color: white;" onclick="insertReview();">									
					</td>
				</tr>
		</table>	
			
			<c:if test="${fn:length(reviewLists)==0 }">
				<center><h3>작성된 후기가 없습니다</h3></center>
			</c:if>
		
				
			<table id="maintable">
					
				<tr>					
				<c:set var="count" value="0"></c:set>				
				<c:forEach var="list" items="${reviewLists }">		
					
					<c:set var="count" value="${count+1 }"></c:set>
						<td>
						
						<c:if test="${fn:length(list.reviewLikeBean)>=2 }">
						<span style="float:left; color:red;">HOT</span>						
						</c:if>
										
						<c:if test="${sessionScope.loginfo.userid eq list.writer  || sessionScope.loginfo.userid eq 'admin'}">				
						<input style="float:right;" class="btn btn-link btn-xs" type="button" value="X" onclick="deleteFunction('${list.num }','${reviewPageInfo.pageNumber}','${reviewPageInfo.pageSize}','${searchedWhatColumn}','${searchedKeyword}');">						
						</c:if>											
						<%-- <a href="ReviewDetail.bd?num=${list.num }&pageNumber=${reviewPageInfo.pageNumber}&pageSize=${reviewPageInfo.pageSize}&whatColumn=${searchedWhatColumn}&keyword=${searchedKeyword}" target="_blank"> --%>
						<img  class="img-thumbnail" src="${pageContext.request.contextPath}/resources/image/board/${list.image }" alt="${list.image }" style="height:300px; width:370px; display:block;" onclick="detailPopup('${list.num }','${reviewPageInfo.pageNumber}','${reviewPageInfo.pageSize}','${searchedWhatColumn}','${searchedKeyword}');">
						<!-- </a> -->
						
						</td>						
						<c:if test="${count==3 }">
						<c:set var="count" value="0"></c:set>
						</tr><tr>
						</c:if>
				</c:forEach>
				</tr>							
			<input type="hidden" id="searchedWhatColumn" value="${searchedWhatColumn}">
			<input type="hidden" id="searchedKeyword" value="${searchedKeyword}">
			<input type="hidden" id="moreViewPageSize" value=3><!-- 3개 더보기 페이지사이즈 detail.bd -->
			<input type="hidden" id="currentPageNumber" value=1>
			
			
			<%-- <input type="hidden" id="currentPageNumber" value=${currentPageNumber }> --%>
			
			<input type="hidden" id="totalCount" value="${totalCount }">
			
			<c:if test="${totalCount<=9 }">			
				<c:set var="totalPage" value="1"></c:set>			
			</c:if>	
			<c:if test="${totalCount > 9 }">				
				<c:set var="appendedTotalData" value="${totalCount-9 }"/>
				<c:set var="X" value="${appendedTotalData-appendedTotalData%3 }"/><!--  13 - 1 =  12  -->
				<c:set var="Q" value="${X/3 }"/> <!-- 4 -->				
				<c:if test="${Q<appendedTotalData/3 }">
					<c:set var="Q" value="${Q+1 }"/>				
				</c:if>			
				<c:set var="totalPage" value="${Q+1}"/>						
			</c:if> 		
			<input type="hidden" id="totalPage" value="${totalPage }">						
			</table>				
</div> 

<!-- ajax function(더보기,삭제) 변수설정하는곳(반드시 maintable밖) -->
<!-- 컨트롤러에서 넘어올때 반드시 value 안쪽 이름으로 넘겨줘야함 -->
<!--  searchedWhatColumn,searchedKeyword,currentPageNumber,totalCount -->
		<table class="table">
		<tr>
			<td colspan="3">			
				<input style="background-color:#104835; color:white;" type="button" value="3개 더보기" class='form-control' onclick="ajaxMoreView();" >
			</td>
		</tr>
		</table>	
					<form name="searchForm" action="ReviewList.bd" method="get">
						<table class="table table-bordered ">
							<tr>
								<td style="width:2%; border=0;">
								<img src="${pageContext.request.contextPath}/resources/image/board/searchImg.png" alt="searchImg"
								style=" height:25px; width:25px; float: right: ;">
								</td>
								<td><select name="whatColumn" class='form-control' onchange="focusTextInputBox();">
										<option value="searchAll"  <c:if test="${searchedWhatColumn eq 'searchAll' }">selected</c:if>>전체검색</option>
										<option value="subject" <c:if test="${searchedWhatColumn eq 'subject' }">selected</c:if>>제목</option>
										<option value="writer" <c:if test="${searchedWhatColumn eq 'writer' }">selected</c:if>>작성자</option>
								</select>
								</td>
								<td>					
									<input type="text" id="keyword" name="keyword"
										value="여기에서 검색하세요" onClick="removeWords();" class="form-control">
								</td>													
								<td><input type="submit" value="검색" onClick="removeWords();"
									class="form-control" style="background: gray; color: white;">
								</td> 
							</tr>							
						</table>
					</form>				
					<input type="hidden" id="aa" value="${pageContext.request.contextPath}">
					
</div> 
</div>
						<!-- <script>
						function windowOpenTest(){
														
							alert("열렸습니다");
							window.open('windowOpenTest.bd','pop','width=450 height=400');
							
						}
						</script> -->
						
						<%-- <img style="width:150px; height:150px;" src="${pageContext.request.contextPath}/resources/image/board/searchImg.png" onclick="windowOpenTest();">
 --%>
<%@ include file="../main/bottom.jsp"%>