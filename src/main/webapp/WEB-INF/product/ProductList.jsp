<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@include file="../main/top.jsp" %>


   <script type="text/javascript">
      function insert(){
         location.href='insert.prd'; 
      }
      function goUpdate( num ){
         location.href='update.prd?pmkey=' +  num ; // ProductUpdateController get 
      }
   </script>

<div class="container">
<div class="row">
<div class="col-md-12">
   <h2 align="center">관리자용 상품리스트 항목</h2>
   <form action="list.prd" method="get">
   <table class="table" class="form-controller">
   
      <tr class="form-controller">
      <td>
      <select name="whatColumn" class="form-control">
         <option value="all">전체 검색</option>
         <option value="name">상품명</option>
         <option value="category">카테고리</option>
      </select>
      </td>
      <td>
      <input type="text" name="keyword" class="form-control">
      </td>
      <td>
      <input type="submit" value="검색" class="form-control">
      </td>
   </tr>
   </table>
   </form>
   
   
   <table class="table" align="center" width="500px" class="form-controller">
      <tr class="form-controller">
         <td colspan="12" align="right">
            <input type="button" value="상품추가" onclick="insert();" class="form-controller">
         </td>
      </tr>
      <tr class="form-controller">
         <th><span>번호</span></th>
         <th><span>상품명</span></th>
         <th><span>카테고리</span></th>
         <th><span>가격</span></th>
         <th><span>원산지</span></th>
         <th><span>배급사</span></th>
         <th><span>중량</span></th>
         <th><span>개수</span></th>
         <th><span>이미지</span></th>
         <th><span>설명</span></th>
         <th><span>수정</span></th>
         <th><span>삭제</span></th>
      </tr>
      <c:forEach items="${productLists}" var="product">
         <tr class="form-controller">
            <td class="form-controller">
               <c:out value="${product.num}" />
            </td>
            
            <td>
            <%-- 	<a href='detail.prd?num=${product.num}'> --%>
               <a href='detail.prd?num=${product.num}'>
               <c:out value="${product.name}" /></a><br>
               <!-- detail.prd=>ProductDetailViewController get-->
            </td>
            
            <td class="form-controller">
               <c:out value="${ product.category }" />
            </td>
            
            <td class="form-controller">
               <c:out value="${ product.price }" />원
            </td >
            
            <td class="form-controller">
               <c:out value="${ product.country }" />
            </td>
            
            <td class="form-controller">
               <c:out value="${ product.company }" />
            </td>            
            
            <td class="form-controller">
               <c:out value="${ product.weight }" />
            </td>
            
            <td class="form-controller">
               <c:out value="${ product.count }" />
            </td>   
            
            <td class="form-controller">
               <c:out value="${ product.image }" />
            </td>   
            
            <td class="form-controller">
               <c:out value="${ product.contents }" />
            </td>   
            
            
            <td class="form-controller">
               <input type="button" value="수정" value="${product.num}" onclick="goUpdate('${product.num}')"/>
            </td>
            
            <td align="right" class="form-controller">
               <a href='delete.prd?num=${product.num}'>삭제</a><br>
               <!-- delete.prd => ProductDeleteController -->
            </td>
         </tr>
      </c:forEach>
   </table>
   <br>
   <center>
      ${pageInfo.pagingHtml}
   </center>
   
   </div>   
   </div>
   </div>   


<%@ include file="../main/bottom.jsp" %>