<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../main/top.jsp"%>

<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<title>Insert title here</title>


<script type="text/javascript">
	function insert() {
		location.href = 'insert.prd';
	}
	function goUpdate(num) {
		location.href = 'update.prd?pmkey=' + num; // ProductUpdateController get 
	}
	
	/* 드랍다운 js */
	$('.nav-tabs-dropdown').each(function(i, elm) {
	    
	    $(elm).text($(elm).next('ul').find('li.active a').text());
	    
	});
	  
	$('.nav-tabs-dropdown').on('click', function(e) {

	    e.preventDefault();
	    
	    $(e.target).toggleClass('open').next('ul').slideToggle();
	    
	});

	$('#nav-tabs-wrapper a[data-toggle="tab"]').on('click', function(e) {

	    e.preventDefault();
	    
	    $(e.target).closest('ul').hide().prev('a').removeClass('open').text($(this).text());
	      
	});   //드랍다운의 끝
</script>

<style type="text/css">
/* 드랍다운 css */
.nav-tabs-dropdown {
	display: none;
	border-bottom-left-radius: 0;
	border-bottom-right-radius: 0;
}

.nav-tabs-dropdown:before {
	content: "\e114";
	font-family: 'Glyphicons Halflings';
	position: absolute;
	right: 30px;
}

@media screen and (min-width: 769px) {
	#nav-tabs-wrapper {
		display: block !important;
	}
}

@media screen and (max-width: 768px) {
	.nav-tabs-dropdown {
		display: block;
		
	}
	#nav-tabs-wrapper {
		display: none;
		border-top-left-radius: 0;
		border-top-right-radius: 0;
		text-align: center;
	}
	.nav-tabs-horizontal {
		min-height: 20px;
		padding: 19px;
		background-color: #f5f5f5;
		border: 1px solid #e3e3e3;
		border-radius: 4px;
		-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
		box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
	}
	.nav-tabs-horizontal>li {
		float: none;
	}
	.nav-tabs-horizontal>li+li {
		margin-left: 2px;
	}
	.nav-tabs-horizontal>li, .nav-tabs-horizontal>li>a {
		background: transparent;
		width: 100%;
	}
	.nav-tabs-horizontal>li>a {
		border-radius: 4px;
	}
	.nav-tabs-horizontal>li.active>a, .nav-tabs-horizontal>li.active>a:hover,
		.nav-tabs-horizontal>li.active>a:focus {
		color: #ffffff;
		background-color: #428bca;
	}
} /* 드랍다운 끝 */


	li{
		font-size:20px;
		
	}

	.active{
		border:1px solid white;
	}
	
</style>
</head>
<body>
	<div class="container" style="margin-top:10px;">
		<div class="row">
		<h3>상품 목록</h3>
			<div class="col-sm-2">
				<a href="#" class="nav-tabs-dropdown" >Tabs</a>
				<ul id="nav-tabs-wrapper"
					class="nav nav-tabs nav-pills nav-stacked well" style="background:#009E6B;">
					<li class="active"><a href="#vtab1" data-toggle="tab" style="color:white;background:#009E6B;">
							Vegetable </a></li><br>
					<li><a href="#vtab2" data-toggle="tab" style="color:white;background:#009E6B;">Fruit</a></li><br>
					<li><a href="#vtab3" data-toggle="tab" style="color:white;background:#009E6B;">Topping</a></li><br>
					<li><a href="#vtab4" data-toggle="tab" style="color:white;background:#009E6B;">Dressing</a></li><br>
					<li><a href="#vtab5" data-toggle="tab" style="color:white;background:#009E6B;">Sidemenu</a></li>
				</ul>
			</div>
			<div class="col-md-10">
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane fade in active" id="vtab1">

						<table class="table">
						<tr>
						<c:set var="count" value="0"></c:set>
						<c:forEach var="i" items="${DisplayVegetable}">
							
                        	<td width=25% align="center" >
                        		<a href='detail.prd?num=${i.num}'><img src="resources/image/product/${i.image}" alt="${i.name}" 
                        		style="width:85%;height:160px;border-radius:300px;"></a>
                        		<br><br>
                        		<span style="color:#009E6B;font-size:17px;font-weight:bold;">${i.name}</span><br>
                        		<span style="color:#99abca;font-size:14px;">${i.price}</span>
                       	 	</td>
                       	 	<c:set var="count" value="${count+1 }"></c:set>
   						<c:if test="${count==4 }">
   						<c:set var="count" value="0"></c:set>
   						</tr><tr>
   						</c:if>
                    	</c:forEach>
						</tr>
					</table>
				</div>

					<div role="tabpanel" class="tab-pane fade in" id="vtab2">
						<table class="table">
							<tr>
						<c:set var="count" value="0"></c:set>
						<c:forEach var="i" items="${DisplayFruit}">
							
                        	<td width=25% align="center" >
                        		<a href='detail.prd?num=${i.num}'><img src="resources/image/product/${i.image}" alt="${i.name}" 
                        		style="width:85%;height:160px;border-radius:300px;"></a>
                        		<br><br>
                        		<span style="color:#009E6B;font-size:17px;font-weight:bold;">${i.name}</span><br>
                        		<span style="color:#99abca;font-size:14px;">${i.price}</span>
                       	 	</td>
                       	 	<c:set var="count" value="${count+1 }"></c:set>
   						<c:if test="${count==4 }">
   						<c:set var="count" value="0"></c:set>
   						</tr><tr>
   						</c:if>
                    	 </c:forEach>
						</tr>
						</table>
					</div>

					<div role="tabpanel" class="tab-pane fade in" id="vtab3">
						<table class="table">
							<tr>
						<c:set var="count" value="0"></c:set>
						<c:forEach var="i" items="${DisplayTopping}">
							
                        	<td width=25% align="center" >
                        		<a href='detail.prd?num=${i.num}'><img src="resources/image/product/${i.image}" alt="${i.name}" 
                        		style="width:85%;height:160px;border-radius:300px;"></a>
                        		<br><br>
                        		<span style="color:#009E6B;font-size:17px;font-weight:bold;">${i.name}</span><br>
                        		<span style="color:#99abca;font-size:14px;">${i.price}</span>
                       	 	</td>
                       	 	<c:set var="count" value="${count+1 }"></c:set>
   						<c:if test="${count==4 }">
   						<c:set var="count" value="0"></c:set>
   						</tr><tr>
   						</c:if>
                    	 </c:forEach>
						</tr>
						</table>
					</div>

					<div role="tabpanel" class="tab-pane fade in" id="vtab4">
						<table class="table">
							<tr>
						<c:set var="count" value="0"></c:set>
						<c:forEach var="i" items="${DisplayDressing}">
							
                        	<td width=25% align="center" >
                        		<a href='detail.prd?num=${i.num}'><img src="resources/image/product/${i.image}" alt="${i.name}" 
                        		style="width:85%;height:160px;border-radius:300px;"></a>
                        		<br><br>
                        		<span style="color:#009E6B;font-size:17px;font-weight:bold;">${i.name}</span><br>
                        		<span style="color:#99abca;font-size:14px;">${i.price}</span>
                       	 	</td>
                       	 	<c:set var="count" value="${count+1 }"></c:set>
   						<c:if test="${count==4 }">
   						<c:set var="count" value="0"></c:set>
   						</tr><tr>
   						</c:if>
                    	 </c:forEach>
						</tr>
						</table>
					</div>
					<div role="tabpanel" class="tab-pane fade in" id="vtab5">
						<table class="table">
							<tr>
						<c:set var="count" value="0"></c:set>
						<c:forEach var="i" items="${DisplaySideMenu}">
							
                        	<td width=25% align="center" >
                        		<a href='detail.prd?num=${i.num}'><img src="resources/image/product/${i.image}" alt="${i.name}" 
                        		style="width:85%;height:160px;border-radius:300px;"></a>
                        		<br><br>
                        		<span style="color:#009E6B;font-size:17px;font-weight:bold;">${i.name}</span><br>
                        		<span style="color:#99abca;font-size:14px;">${i.price}</span>
                       	 	</td>
                       	 	<c:set var="count" value="${count+1 }"></c:set>
   						<c:if test="${count==4 }">
   						<c:set var="count" value="0"></c:set>
   						</tr><tr>
   						</c:if>
                    	 </c:forEach>
						</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

<%@include file="../main/bottom.jsp"%>