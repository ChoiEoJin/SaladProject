
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ include file="top.jsp"%>

<style type="text/css">
.jumbotron {
   text-shadow: black 0.2em 0.2em 0.2em;
   color: white;
   background-image: url('resources/image/main/boximage1.png');
   background-size: cover;
   width: 100%;
   height: 100%;
   font-size: 5px;
}

blockquote {
   border-left: 5px solid;
   font-size: 40px;
}

botton {
   width: 1000px;
   height: 1000px;
}
</style>
<br>
<br>

<div class="container-fluid">
   <div class="row-fluid">
      <div class="col-md-12">
         <div id="myCarousel" class="carousel slide" data-ride="carousel"
            style="width: 100%; height: 700px;">
            <!-- Indicators -->
            <ol class="carousel-indicators">
               <li data-target="#myCarousel1" data-slide-to="0" class="active"></li>
               <li data-target="#myCarousel1" data-slide-to="1"></li>
               <li data-target="#myCarousel1" data-slide-to="2"></li>
            </ol>

            <!-- 슬라이드 -->
            <div class="carousel-inner" style="text-align: center;">
               <div class="item active" style="text-align: center;">
                  <img src="resources/image/main/mainimg1.png"
                     style="width: 100%; height: 700px; margin: 0px;">
               </div>

               <div class="item">
                  <img src="resources/image/main/mainimg2.png"
                     style="width: 100%; height: 700px; margin: 0px;">
               </div>

               <div class="item">
                  <img src="resources/image/main/mainimg3.png"
                     style="width: 100%; height: 700px; margin: 0px;">
               </div>
            </div>

            <!-- 버튼 컨트롤 -->
            <a class="left carousel-control" href="#myCarousel"
               data-slide="prev"> <span
               class="glyphicon glyphicon-chevron-left"></span> <span
               class="sr-only">Previous</span>
            </a> <a class="right carousel-control" href="#myCarousel"
               data-slide="next"> <span
               class="glyphicon glyphicon-chevron-right"></span> <span
               class="sr-only">Next</span>
            </a>
         </div>
      </div>
   </div>
</div>
<br>
<br>
<br>
<br>




<!-- 2*2 점보트론 -->

<!-- 점보 1 -->
<div class="container" style="margin-top: 10px;">
   <div class="row">
      <div class="col-md-8"
         style="height: 350px; background-repeat: no-repeat; margin:0px; padding:1px;">
         <div class="jumbotron">
            <div class="row">
               <br>
               <br>
               <br>
               <blockquote>
                  당신이 원하는 샐러드를 <br> 세팅해보세요! <br>
                  <button class="btn" style="background-color:black;color:white;border:1px soild white;font-size:13px;opacity:0.5;">나만의 샐러드 바로가기</button>
               </blockquote>
            </div>
         </div>
      </div>
      
      <!-- 점보 2 -->
      <div class="col-md-4"
         style="height: 350px; background-repeat: no-repeat; margin:0px; padding:1px;">

         <div class="jumbotron"
            style="background-image: url('resources/image/main/boximage2.png');">
            <div class="row">
               <br>
               <br>
               <br>
               <blockquote>
                  문의사항이 있으신가요? <br>
                  <button class="btn" style="background-color:black;color:white;border:1px soild white;font-size:13px;opacity:0.5;">Q&A 바로가기</button>
               </blockquote>
            </div>
         </div>
      </div>
   </div>
   
   
   <!-- 점보 3  -->
   <div class="row" style="margin-top: 2px;">
      <div class="col-md-8"
         style="height: 350px; background-repeat: no-repeat; margin:0px; padding:1px;">
         
         <div class="jumbotron"
            style="background-image: url('resources/image/main/boximage3.png');">
            <div class="row">
               <br>
               <br>
               <br>
               <br>
               <blockquote>
                  당신을 위해 준비한 60여가지의<br> 재료들을 만나보세요 <br>
                  <button class="btn" style="background-color:black;color:white;border:1px soild white;font-size:13px;opacity:0.5;">보러가기</button>
               </blockquote>
            </div>
         </div>
         
         </div>
         
         <!-- 점보 4 -->
      <div class="col-md-4"
         style="height: 350px; background-repeat: no-repeat; margin:0px; padding:1px;">
         
         <div class="jumbotron"
            style="background-image: url('resources/image/main/boximage4.png');">
            <div class="row">
               <br>
               <br>
               
               <blockquote>
                  당신의 <br>샐러드를 <br>뽐내보세요!<br>
                  <button class="btn" style="background-color:black;color:white;border:1px soild white;font-size:13px;opacity:0.5;">후기 바로가기</button>
               </blockquote>
            </div>
         </div>
         
         </div>
   </div>
</div>
<!-- 컨테이너 끝 -->

<%@ include file="bottom.jsp"%>


</body>
</html>