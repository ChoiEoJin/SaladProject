<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@include file="../main/top.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
		margin-bottom: 20px;
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
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<h3>인기 샐러드</h3>
						<table class="table table-bordered">
						<tr>
						<c:set var="count" value="0"></c:set>
						<c:forEach var="i" items="${DisplayFinishedgoods}">
                        	<td width=33% align="center" >
                        		<a href='detail.prd?num=${i.num}'><img src="resources/image/product/${i.image}" alt="${i.name}" 
                        		style="width:85%;height:310px;border:2px solid #104835;margin-top:15px;border-radius: 10px;"></a>
                        		<br>
                        		<span style="font-size:20px;font-weight: bold;">${i.name}</span><br>
                        		<span style="color:red;font-size:16px;font-weight: bold;">${i.price}원</span>
                        		<br><br>
                       	 	</td>
                       	 	<c:set var="count" value="${count+1 }"></c:set>
   						<c:if test="${count==3 }">
   						<c:set var="count" value="0"></c:set>
   						</tr><tr>
   						</c:if>
                    	</c:forEach>
						</tr>
						
					</table>
				</div>
</div>
</div>


</body>
</html>

<%@include file="../main/bottom.jsp"%>