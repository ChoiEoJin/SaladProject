<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ include file="../main/top.jsp"%>
<script type="text/javascript">	
var clickCount=0;
function removeWords(){	
	if(clickCount==0){		
		document.getElementById("keyword").value = "";
		clickCount=1;		
	}		
}
</script>
<style type="text/css">
</style>


	<c:if test="${pageNumber==null}">
		<c:set var="pageNumber" value="1" />
	</c:if>

	<c:if test="${pageSize==null}">
		<c:set var="pageSize" value="2" />
	</c:if>
	<center>
	

	<%-- 	<%
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			System.out.println(String.valueOf(timestamp));
		%> --%>
	

<div class="container">
	<div class="row">
		<div class="table-responsive">
		
		<div>
		<h1> Q & A </h1>
		</div>

		<br>
		
		<form action="insert.bd">
			<input type="hidden" name="pageNumber" value="${pageInfo.pageNumber }">
			<input type="hidden" name="pageSize" value="${pageInfo.pageSize }">			
			<table class="table table-hover" >
				<tr>
					<td colspan="6" align="right"><input type="submit"	value="글쓰기" class="form-control" style="background-color:lightseagreen; color:white;"></td>
				</tr>
				<tr style="background:white;font-weight:bold;color:black;">
					<th style="text-align:center;" width=10%>글번호</th>
					<th style="text-align:center;">제목</th>
					<th style="text-align:center;" >작성자</th>
					<th style="text-align:center;">작성일</th>
					<th style="text-align:center;">조회수</th>
				</tr>
				
				 <!-- ▼ 관리자 공지글  -->
				<c:if test="${fn:length(NoticeLists)!=0 }">
								
					<c:forEach var="nList" items="${NoticeLists }">
					
					<tr style="font-weight:bold;color:black;">
					<td align="center" width=10% >
					
					<c:if test="${nList.notice == 1}">
					<span class="badge" style="background-color:#fae100; color:#3b1e1e; font-size:15px;">공지</span>
					</c:if>
					
					</td>
					<td>
					<a href="detailView.bd?num=${nList.num}&pageNumber=${pageInfo.pageNumber}&pageSize=${pageInfo.pageSize}&whatColumn=${pageInfo.whatColumn}&keyword=${pageInfo.keyword}" style="color:black;font-weight:bold;">
					${nList.subject }
					</a>
					</td><!-- 제목 누르면 상세보기  -->
					<td align="center" >${nList.writer }</td>
					<td align="center"><fmt:formatDate value="${nList.regdate}" type="date"
								dateStyle="full" pattern="yyyy/MM/dd HH:mm" /></td>
					<td align="center">${nList.readcount}</td>
					
					</tr>		
					</c:forEach>
				
				</c:if> 
				
				<!--  ▼ 이용자게시글         -->				
				<c:if test="${fn:length(BoardLists)==0 }">				
					<tr>
					<td align="center" colspan="6" valign="middle">작성된 게시글이 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach var="list" items="${BoardLists}" varStatus="status">
					<tr>
						<td align="center" width=10%>${list.num}</td>						
						<c:if test="${list.restep == 0 }">
						<td width=30%>
							<a href="detailView.bd?num=${list.num}&pageNumber=${pageInfo.pageNumber}&pageSize=${pageInfo.pageSize}&whatColumn=${pageInfo.whatColumn}&keyword=${pageInfo.keyword}">
								<c:if test="${list.secret==1}">
									<img src="${pageContext.request.contextPath}/resources/image/board/privateBoard.png" alt="private" width="30px" height="30px">비밀글입니다.
								</c:if>							
								<c:if test="${list.secret==0}"> 
									${list.subject}
								</c:if>								
							</a>					
						</td>
						</c:if>
									
						<c:if test="${list.restep != 0}">
						<td  width=30%>
							<c:forEach begin="0" end="${list.relevel}">		
							&nbsp&nbsp
							</c:forEach>
								<a href="detailView.bd?num=${list.num}&pageNumber=${pageInfo.pageNumber}&pageSize=${pageInfo.pageSize}">				
								<c:if test="${list.secret==1}">
									비밀글입니다.
								</c:if>
								<c:if test="${list.secret==0}"> 
									${list.subject}
								</c:if>				
								</a>
							</td>
						</c:if>									
						<td align="center">${list.writer}</td>
						<td align="center"><fmt:formatDate value="${list.regdate}" type="date"
								dateStyle="full" pattern="yyyy/MM/dd HH:mm" /></td>
						<td align="center">${list.readcount}</td>
					</tr>
				</c:forEach>
			</table>
		</form>
		${pageInfo.pagingHtml}
		
		
		<form name="searchForm" action="list.bd" method="get">
			<table class="table">
			<tr>
			<td>
			<select name="whatColumn" class='form-control'>
				<option value="">전체검색</option>
				<option value="subject">제목</option>
				<option value="writer">작성자</option>
			</select></td>
			<td>
				 <input type="text" id="keyword" name="keyword" value="여기에서 검색하세요" onClick="removeWords();" class="form-control">
			</td>
			<td>
				<input type="submit" value="검색" onClick="removeWords();" class="form-control" style="background-color:gray;color:white;">
			</td>
			</tr>
			</table>			 
		</form>	
		</div>
	</div>
</div>
</center>
<%@ include file="../main/bottom.jsp"%>