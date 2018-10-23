package board.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sun.mail.iap.Response;

import board.model.ReviewBean;
import board.model.ReviewDao;
import board.model.ReviewLikeBean;
import board.model.ReviewLikeDao;
import board.model.ReviewReplyBean;
import board.model.ReviewReplyDao;
import member.model.MemberBean;
import utility.ReviewPaging;

@Controller
public class Review_BoardDetailViewController {

	private static final String command = "/ReviewDetail.bd";
	private static final String getPage = "Review_BoardDetailView";
	
	@Autowired
	private ReviewDao reviewDao;
	
	@Autowired
	private ReviewReplyDao reviewreplydao;
	
	@Autowired
	private ReviewLikeDao reviewLikedao;
	
	@RequestMapping(value = command, method = RequestMethod.GET) //처음9개 갖고오는 컨트롤러
	private ModelAndView reviewGet(@RequestParam(value="num",required=true) int num,
			@RequestParam(value = "whatColumn", required = false) String whatColumn,
			@RequestParam(value="keyword", required = false) String keyword,HttpServletRequest request,
			@RequestParam(value="pageNumber",required=false)String pageNumber,
			@RequestParam(value="pageSize",required=false)String pageSize,HttpServletResponse response,HttpSession session) throws IOException {
		
		System.out.println("디테일뷰 컨트롤러 도착함");
				
				
		if(whatColumn==null) {
			
			whatColumn="searchAll";
		}
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("whatColumn", whatColumn);
		
		if(whatColumn.equals("searchAll")) {
			//searchAll로 들어오는 경우
			//경우1:맨처음실행시 keyword null로 들어오는경우,whatColumn <select>부분을 변경해서 null로 되는경우
			//경우2:한번 뿌려주고나서 %null%로 들어오는경우
			if(keyword==null) { //경우1
				map.put("keyword", "%" + keyword + "%");				
			}else if(!keyword.equals("%null%")) {	//위에랑 똑같나??			
				map.put("keyword", "%" + keyword + "%");						
			}else {	//경우2
				map.put("keyword", keyword);
			}
		
			
		}else {	//전체검색이 아닐때,	
			if(whatColumn.equals("subject")) {//제목일때
			map.put("keyword", "%" + keyword + "%"); }// 제목으로 검색할때는 포함되어있게 
			else {//작성자일때						
					map.put("keyword",keyword.trim()); //작성자로 검색할때는 앞뒤공백없애고, 정확하게 			
			}	
		}
		
		
		
		int totalCount = reviewDao.GetTotalCount( map );   
		String url = request.getContextPath() +  this.command ;
		
		ModelAndView mav = new ModelAndView();
		
		ReviewPaging reviewPageInfo
		= new ReviewPaging( pageNumber, pageSize, totalCount, url, whatColumn, keyword, null); 
				
		
		ReviewBean reviewbean = reviewDao.getReviewBeanGetByNum(num); //넘버에 해당하는 게시글 객체불러옴
		List<ReviewReplyBean> beanlist = reviewreplydao.getReviewReply(num);//넘버에 해당하는 댓글 가지고옴	
		reviewbean.setReviewRelpyBeanList(beanlist);//댓글빠진 게시글에 댓글들 저장시킴	
		List<ReviewLikeBean> likebeanlist=reviewLikedao.getReviewLikeGetByNum(num);//넘버에 해당하는 좋아요리스트가지고옴
		reviewbean.setReviewLikeBean(likebeanlist);//좋아요까지 합쳐진 게시글임 (최종)
		
		//몇명이 좋아하는지
		int HowManyUserlikethis=likebeanlist.size();
		
		//누구 누구가 좋아하는지 
		String people=reviewbean.getPeople();
		if(HowManyUserlikethis != 0) {
		
		
			for(int i=0;i<HowManyUserlikethis;i++) {
				
				
				if(i==0) {
					
					people+=likebeanlist.get(i).getUserid();
					
				}else {
					people+=","+likebeanlist.get(i).getUserid();
				}
				
				
			}
			//people+="님이 좋아합니다";
		}
		
		reviewbean.setPeople(people);
		
		
		System.out.println("loginfo session test :"+session.getAttribute("loginfo"));
		if(session.getAttribute("loginfo") != null) { //로그인 상태일때만 
			
			Map<String,String> map2=new HashMap<String, String>();
			
			String strNum=Integer.toString(num);
			map2.put("num",strNum);
			map2.put("loginId", ((MemberBean)session.getAttribute("loginfo")).getUserid());		
			
			int whetherLoginId_doesLikethis=reviewLikedao.check_whetherLoginId_doesLikethis(map2);
			
			//접속한 유저가 num에 해당하는 게시글에 좋아요를 했으면 리턴1 ,없으면 0;
			
			mav.addObject("whetherLoginId_doesLikethis",whetherLoginId_doesLikethis);
			
			
		}
		
		
		
		
		mav.addObject("reviewbean", reviewbean);//댓글,좋아요 까지 전부 저장되있다.(pageSize만큼 담겨져있어)(★)
		mav.addObject("HowManyUserlikethis",HowManyUserlikethis);//몇명이 좋아요 눌렀는지 리턴 , 없으면0 	
		
		
		mav.addObject("reviewPageInfo", reviewPageInfo);//★페이지넘버,페이지사이즈,토탈페이지	
		String searchedWhatColumn=map.get("whatColumn");//들고다녀야됨★
		String searchedKeyword=map.get("keyword");//들고다녀야됨★
		//whatColumn 이랑 keyword 출력용 
				System.out.println("=======넘어가는 whatColumn , 넘어가는 keyword========");
				System.err.println("searchedWhatColumn: "+searchedWhatColumn);
				//whatCoulmn=searchAll or subject, writer 3개중 하나
				System.err.println("searchedKeyword: "+searchedKeyword+",searchedKeyword.length(): "+searchedKeyword.length());
				//keyword=searchAll일경우에는 무조건, %null% 이고,  suject or writer의 경우에는 문자그대로 넘어간다.
				System.out.println("=======================================");
		
		
		mav.addObject("searchedWhatColumn",searchedWhatColumn);
		mav.addObject("searchedKeyword",searchedKeyword);
		
		System.out.println("ReviewDetailController 종료. DetailView.jsp로 넘어갑니다.");
		
		
		mav.setViewName(getPage);
		
		
		
		
		return mav;
		
		
	}
	
	
	
	//좋아요 추가 컨트롤러 (디테일뷰jsp ajax에서넘어옴)
	@RequestMapping(value="/LoginUserLikeThisReview.bd")
	private void doAction(@RequestParam(value="num",required=false)String num,HttpSession session,
			HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		System.out.println("좋아요 추가 컨트롤러 들어옴");
		String userid=((MemberBean)session.getAttribute("loginfo")).getUserid();
		System.out.println("해당 게시글 번호:"+num);
		System.out.println("좋아요를 누른 유저아이디: "+userid);
		
		Map<String,String> insertMap=new HashMap<String, String>();
		
		insertMap.put("num", num);//해당 게시글 넘버 
		insertMap.put("userid",userid);//접속한 유저 아이디 
		
		
		
		
		int cnt=reviewLikedao.insertReviewLike(insertMap); //좋아요 테이블에 num과 유저아이디 추가 
		
		if(cnt==0) {
			System.out.println("좋아요  추가작업실패");
			
		}else {
			
			System.out.println("좋아요 추가작업성공");
		}
		
		List<ReviewLikeBean> likebeanlist=reviewLikedao.getReviewLikeGetByNum(Integer.parseInt(num));	
		int HowManyUserlikethis=likebeanlist.size();
		ReviewBean reviewbean=reviewDao.getReviewBeanGetByNum(Integer.parseInt(num));
		//누구 누구가 좋아하는지 
				String people=reviewbean.getPeople();
				if(HowManyUserlikethis != 0) {
				
				
					for(int i=0;i<HowManyUserlikethis;i++) {
						
						
						if(i==0) {
							
							people+=likebeanlist.get(i).getUserid();
							
						}else {
							people+=","+likebeanlist.get(i).getUserid();
						}
						
						
					}
					//people+="님이 좋아합니다";
				}
				
				reviewbean.setPeople(people);
		
		PrintWriter writer=response.getWriter();		
		writer.println("<div id=\"favoriteDIV\">");
		
		
		//writer.println("<input type=\"button\" onclick=\"FavoriteBTN();\" value=\"좋아요취소\" id=\"favorite\">");
		writer.println("<img src='"+request.getContextPath()+"/resources/image/board/dislikebtn.png' onclick=\"FavoriteBTN();\"  style=\"width:100px;height:50px;\" >");
		writer.println(HowManyUserlikethis+"명이 좋아합니다");
		if(HowManyUserlikethis !=0) {
			writer.println("<br><span style=\"color:#819FF7;\">"+reviewbean.getPeople()+"</span>님이 좋아합니다");	
		}
		writer.println("</div>");
		writer.flush();

	}
		
	
	
	
	//좋아요 취소 컨트롤러(디테일뷰jsp에서 ajax에서넘어옴)
	@RequestMapping(value="/LoginUserDoesNotLikeThisReview.bd")
	private void doAction2(@RequestParam(value="num",required=false)String num,HttpSession session,
			HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		System.out.println("좋아요 삭제 컨트롤러 들어옴");
		String userid=((MemberBean)session.getAttribute("loginfo")).getUserid();
		System.out.println("해당 게시글 번호:"+num);
		System.out.println("좋아요 '취소'를 누른 유저아이디: "+userid);
		
		Map<String,String> deleteMap=new HashMap<String, String>();
		
		deleteMap.put("num", num);//해당 게시글 넘버 
		deleteMap.put("userid",userid);//접속한 유저 아이디 
		
		int cnt=reviewLikedao.deleteReviewLike(deleteMap); //좋아요 테이블에 num과 유저아이디 추가 
		
		if(cnt==0) {
			System.out.println("좋아요 삭제작업실패");
			
		}else {
			
			System.out.println("좋아요 삭제작업성공");
		}
		
		
		List<ReviewLikeBean> likebeanlist=reviewLikedao.getReviewLikeGetByNum(Integer.parseInt(num));	
		int HowManyUserlikethis=likebeanlist.size();
		ReviewBean reviewbean=reviewDao.getReviewBeanGetByNum(Integer.parseInt(num));
		//누구 누구가 좋아하는지 
		String people=reviewbean.getPeople();
		if(HowManyUserlikethis != 0) {
				
				
			for(int i=0;i<HowManyUserlikethis;i++) {
						
						
				if(i==0) {
							
					people+=likebeanlist.get(i).getUserid();
							
				}else {
					people+=","+likebeanlist.get(i).getUserid();
				}
						
						
			}
			//people+="님이 좋아합니다";
		}
				
		reviewbean.setPeople(people);
		PrintWriter writer=response.getWriter();		
		writer.println("<div id=\"favoriteDIV\">");
		//writer.println("<input type=\"button\" onclick=\"FavoriteBTN();\" value=\"좋아요\" id=\"favorite\">");
		writer.println("<img src='"+request.getContextPath()+"/resources/image/board/likebutton.png' onclick=\"FavoriteBTN();\"  style=\"width:100px;height:50px;\" >");
		if(HowManyUserlikethis==0) {
			writer.println("<span style=\"color:#819FF7;\">제일 먼저 좋아요를 눌러주세요!</span>");
		}else {
			writer.println(HowManyUserlikethis+"명이 좋아합니다");
			writer.println("<br><span style=\"color:#819FF7;\">"+reviewbean.getPeople()+"</span>님이 좋아합니다");
		}
		writer.println("</div>");
		writer.flush();
	}	
}