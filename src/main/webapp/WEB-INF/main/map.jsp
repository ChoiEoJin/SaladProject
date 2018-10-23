<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="top.jsp" %>
	<div class="container" style="margin-top:10px;">
		<div class="row">
			<div class="col-md-12">
			    <div id="map" style="height:400px;width:100%"></div>
				<form name="f">
				<table class="table">
				<tr>
				<td>
				<select name="jijum" onchange="mapp()" class="form-control">
					<option value="one">1호점</option>
					<option value="two">2호점</option>
					<option value="three">3호점</option>
				</select>
				</td>
				</tr>
				<tr>
					<td align="center">
						<span id="jusoprint" style="font-size:20px;font-weight:bold;">1호점 위치: 서울시 신촌구 마포대교 44-5 중앙빌딩 1층</span>
					</td>
				</tr>
				</table>
				</form>
			</div>
		</div>
	</div>
	
	
    <script>
    var jijum = {lat: 37.556535, lng: 126.945307};
      function initMap() {
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 17,
          center: jijum
        });
        var marker = new google.maps.Marker({
          position: jijum,
          map: map
        });
      }
	
      function mapp(){
			var i=document.f.jijum.selectedIndex;
			if(i==0){
				jijum={lat: 37.556535, lng: 126.945307};
				$("#jusoprint").html("1호점 위치: 서울시 신촌구 마포대교 44-5 중앙빌딩 1층 ");
			}else if(i==1){
				jijum={lat: 37.715933, lng: 126.765339};
				$("#jusoprint").html("2호점 위치: 서울시 도봉구 창1동 삼성아파트 103동 901호");
			}else if(i==2){
				jijum={lat: 37.651006, lng: 127.047919};
				$("#jusoprint").html("3호점 위치: 일산시 일산서구 햇빛마을 125-22 센트럴파트 102");
			}
			initMap();
      }
      
    </script>
    
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBEsdMWppywms_LbwFd222M_vQ50BeCqgQ&callback=initMap" async defer>
    </script>

<%@ include file="bottom.jsp"%>