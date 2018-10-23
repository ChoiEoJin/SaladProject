package board.Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import board.model.ReviewBean;
import board.model.ReviewDao;
import board.model.ReviewLikeBean;
import board.model.ReviewLikeDao;
import board.model.ReviewReplyBean;
import board.model.ReviewReplyDao;
import member.model.MemberBean;
import utility.ReviewPaging;

@Controller
public class Review_BoardListController {

	private static final String command1 = "/ReviewList.bd";//top.jsp에서 일로온다.
	private static final String getPage = "Review_List";
	
	

	@Autowired
	private ReviewDao reviewDao;
	@Autowired
	private ReviewReplyDao reviewreplydao;

	@Autowired
	private ReviewLikeDao reviewlikeDao;
	
	//top에서 오는경우, 조건검색을 하는경우 
	@RequestMapping(value = command1, method = RequestMethod.GET) //처음9개 갖고오는 컨트롤러
	private ModelAndView reviewGet(@RequestParam(value = "whatColumn", required = false) String whatColumn,//top에서 지정함
			@RequestParam(value="keyword", required = false) String keyword,HttpServletRequest request,//top에서 지정함
			@RequestParam(value="pageNumber",required=false)String pageNumber,//top에서 지정함
			@RequestParam(value="pageSize",required=false)String pageSize,HttpServletResponse response) throws IOException {
			//top에서 지정함
		
		System.out.println("==================================================================");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println( "리스트컨트롤러  기본게시글뿌려주기 들어옴");

		Map<String, String> map = new HashMap<String, String>();
				
		if(whatColumn==null) {
			
			whatColumn="searchAll";
		}
		
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
		System.out.println("검색된 데이터갯수(totalCount) : " + totalCount + ", ");		
		String url = request.getContextPath() +  this.command1 ;
		//System.out.println("url : "+url); 
		ModelAndView mav = new ModelAndView();
		
		ReviewPaging reviewPageInfo
		= new ReviewPaging( pageNumber, pageSize, totalCount, url, whatColumn, keyword, null); 
			
		List<ReviewBean> reviewLists = reviewDao.getAllReviewList(reviewPageInfo,map);//댓글들 객체가빠진 모든 원글
		
		//List<List<ReviewReplyBean>> AllReplyBeanList = new ArrayList<List<ReviewReplyBean>>(); //모든 답글들을 모아둘 저장소

			for (int i = 0; i < reviewLists.size(); i++) {
				List<ReviewReplyBean> beanlist = reviewreplydao.getReviewReply(reviewLists.get(i).getNum()); //해당Num에대한 빈리스트를 갖고옴
				//System.out.println(i+"번쨰 beanlist 사이즈:"+beanlist.size());//정상출력
				List<ReviewLikeBean> likelist=reviewlikeDao.getReviewLikeGetByNum(reviewLists.get(i).getNum());//해당 num에대한 좋아요
				reviewLists.get(i).setReviewRelpyBeanList(beanlist);//여기 세터가 
				reviewLists.get(i).setReviewLikeBean(likelist);//좋아요 갯수출력하기위한 재료
			
				//System.out.println(i+"번째 "+reviewLists.get(i).getReviewRelpyBeanList().size());
				
				//AllReplyBeanList.add(beanlist);//해당 Num에대한 빈리스트들을 저위에 채워둠
			}

			/*for (ReviewBean bean : reviewLists) { //저장잘됬나 게터검증
				System.out.println("==============================");
				System.out.println("원글넘버" + bean.getNum());
				System.out.println("원글작성자" + bean.getWriter());
				System.out.println(bean.getNum()+"번 원글에 달린 댓글의 갯수:" + bean.getReviewRelpyBeanList().size());
				System.out.println("==============================");
			}*/

		int number= totalCount-(reviewPageInfo.getPageNumber()-1)*reviewPageInfo.getPageSize();		
		System.out.println("number:"+number);//맨처음시작하는 번호
		mav.addObject("number", number);//출력용
		
		//detailView재료
		mav.addObject("reviewLists", reviewLists);//댓글까지 전부 저장되있다.(pageSize만큼 담겨져있어)(★)
		mav.addObject("reviewPageInfo", reviewPageInfo);//★페이지넘버,페이지사이즈,토탈페이지	
		mav.addObject("totalCount",totalCount);
		mav.addObject("currentPageNumber",reviewPageInfo.getPageNumber());
		
		
		String searchedWhatColumn=map.get("whatColumn");//들고다녀야됨★
		String searchedKeyword=map.get("keyword");//들고다녀야됨★
		System.out.println("");
		
		//whatColumn 이랑 keyword 출력용 
		System.out.println("=======넘어가는 whatColumn====== , ========넘어가는 keyword========");
		
		System.out.println("리스트컨트롤러  searchedWhatColumn: "+searchedWhatColumn);
		//whatCoulmn=searchAll or subject, writer 3개중 하나
		System.out.println("리스트컨트롤러  searchedKeyword: "+searchedKeyword+",searchedKeyword.length(): "+searchedKeyword.length());
		//keyword=searchAll일경우에는 무조건, %null% 이고,  suject or writer의 경우에는 문자그대로 넘어간다.
		System.out.println("=======================================");
		
		
		
		
		mav.addObject("searchedWhatColumn",searchedWhatColumn);
		mav.addObject("searchedKeyword",searchedKeyword);
		
		
		
		System.out.println("list컨트롤러 종료 리스트jsp로 이동합니다");
		mav.setViewName(getPage);
		return mav;

	}//리스트.jsp
	
	
	
	
	
	
	
	
	//더보기 ajax요청받는 
	@RequestMapping(value="moreView.bd",method=RequestMethod.GET)
	private void ajaxMoreViewGET(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		System.out.println("=====================================================");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		System.out.println("<더보기 컨트롤러  요청받았음>");
		String showedPageNumber=request.getParameter("showedPageNumber");//2
		String searchedWhatColumn=request.getParameter("searchedWhatColumn");
		String searchedKeyword=request.getParameter("searchedKeyword");
		String moreViewPageSize=request.getParameter("moreViewPageSize");	
		
		System.out.println("더보기 컨트롤러에서 더보고싶은 페이지번호= "+showedPageNumber);//2
		//System.out.println("모어컨트롤러 searchedWhatColumn= "+searchedWhatColumn);
		//System.out.println("모어컨트롤러 searchedKeyword= "+searchedKeyword);
		//System.out.println("모어컨트롤러 moreViewPageSize= "+moreViewPageSize);//3
		
		
		
		Map<String,String> map=new HashMap<String,String>();
		
		
			if(searchedWhatColumn==null) {
						
				searchedWhatColumn="searchAll";
			}
				
				map.put("whatColumn", searchedWhatColumn);
				
				if(searchedWhatColumn.equals("searchAll")) {
					//searchAll로 들어오는 경우
					//경우1:맨처음실행시 keyword null로 들어오는경우,whatColumn <select>부분을 변경해서 null로 되는경우
					//경우2:한번 뿌려주고나서 %null%로 들어오는경우
					if(searchedKeyword==null) { //경우1
						map.put("keyword", "%" + searchedKeyword + "%");				
					}else if(!searchedKeyword.equals("%null%")) {	//위에랑 똑같나??			
						map.put("keyword", "%" + searchedKeyword + "%");						
					}else {	//경우2
						map.put("keyword", searchedKeyword);
					}
				
					
				}else {	//전체검색이 아닐때,	
					if(searchedWhatColumn.equals("subject")) {//제목일때
					map.put("keyword", "%" + searchedKeyword + "%"); }// 제목으로 검색할때는 포함되어있게 
					else {//작성자일때						
							map.put("keyword",searchedKeyword.trim()); //작성자로 검색할때는 앞뒤공백없애고, 정확하게 			
					}	
				}
		
				//9+3*(showedPage-1) 여긴어차피 들어오면 2부터시작함
				int totalCount = reviewDao.GetTotalCount( map );   
				String url = request.getContextPath() +  "/moreView.bd" ;
				ReviewPaging reviewPageInfo
				= new ReviewPaging( showedPageNumber, moreViewPageSize, totalCount, url, searchedWhatColumn, searchedKeyword, null); 
				//위에 2개는 내비두고 showedPageNumber,moreViewPageSize 고치지말자 이거건들지말고 밑에서 직접수정
				
				
				
				
				System.err.println("moreViewPageSize= "+moreViewPageSize);				
				reviewPageInfo.setOffset(9+3*(Integer.parseInt(showedPageNumber)-2));
				
				
				
				
				
		//원글+댓글 +좋아요 세트 갖고옴(★)
				List<ReviewBean> reviewLists = reviewDao.getAllReviewList(reviewPageInfo,map);//댓글들 객체가빠진 모든 원글
				for (int i = 0; i < reviewLists.size(); i++) {
					List<ReviewReplyBean> beanlist = reviewreplydao.getReviewReply(reviewLists.get(i).getNum()); //해당Num에대한 빈리스트를 갖고옴
					List<ReviewLikeBean> likelist=reviewlikeDao.getReviewLikeGetByNum(reviewLists.get(i).getNum());//해당 num에대한 좋아요
					
					//System.out.println(i+"번쨰 beanlist 사이즈:"+beanlist.size());//정상출력
					
					reviewLists.get(i).setReviewRelpyBeanList(beanlist);//여기 세터가 
					reviewLists.get(i).setReviewLikeBean(likelist);//좋아요 갯수출력하기위한 재료
					//System.out.println(i+"번째 "+reviewLists.get(i).getReviewRelpyBeanList().size());
				}
					
		//뿌려줄차례		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer=response.getWriter();
		int lastIndex=reviewLists.size()-1;
		System.out.println("모어컨트롤러 lastIndex= "+lastIndex);
		
		
		for(int i=0;i<=lastIndex;i++) { // 1개 or2개 or3개 갖고왔음
			
			int num=reviewLists.get(i).getNum();
			String pageNumber=showedPageNumber;
			String pageSize=moreViewPageSize;
				
								
				System.out.println("모어컨트롤러 for문  moreViewPageSize="+moreViewPageSize);
				String whatColumn=searchedWhatColumn;
				
				System.out.println("모어컨트롤러 for문 searchedWhatColumn="+searchedWhatColumn);
				String keyword=searchedKeyword;
				
				System.out.println("모어컨트롤러 for문 searchedKeyword="+searchedKeyword);
				
				//System.err.println("request.getContextPath()= "+request.getContextPath());
			if(i==0) {
			writer.println("<tr>");
			}
			//${pageContext.request.contextPath}/resources/image/board"
			writer.println("<td>");
			//writer.println("<span style='float:left;'>페이지넘버:"+pageNumber+"</span>");
				
			if(reviewLists.get(i).getReviewLikeBean().size()>=2) {
				writer.println("<span style=\"color:red; float:left;\">HOT</span>");
			}
			
			String a="<input style='float:right;' class=\"btn btn-link btn-xs\" type=\"button\" value=\"X\" onclick=\"deleteFunction('";			
			String b=num+"','";
			String c=pageNumber+"','";
			String d=pageSize+"','";
			String e=whatColumn+"','";
			String f=keyword+"')\">";
			String str=a+b+c+d+e+f;
			//System.out.println(str);
			
					
			if(session.getAttribute("loginfo")!=null) {
				String Writer=reviewLists.get(i).getWriter();

				if(((MemberBean)session.getAttribute("loginfo")).getUserid().equals("admin") || ((MemberBean)session.getAttribute("loginfo")).getUserid().equals(Writer) ) {
					writer.println(str);//글삭제 input생성구문
				}													
			}	
			
			//writer.println("<a href='ReviewDetail.bd?num="+num+"&pageNumber="+pageNumber+"&pageSize="+pageSize+"&whatColumn="+whatColumn+"&keyword="+keyword+"'>");
			writer.println("<img class=\"img-thumbnail\" src='"		
					+request.getContextPath()+"/resources"+"/image"+"/board"
					+(String) File.separator + reviewLists.get(i).getImage()+"'"
					+ " alt='"+reviewLists.get(i).getImage()+"' style='height:300px; width:370px; display:block;'"
							+ "onclick=\"detailPopup('"+num+"','"+pageNumber+"','"+pageSize+"','"+whatColumn+"','"+keyword+"');\">");
									
		  // writer.println("</a>");
		   writer.println("</td>");			
			if(i==lastIndex) {
			writer.println("</tr>");
			}	
			System.out.println("  더보고싶은  페이지"+showedPageNumber+"에"+i+"번째 데이터의 num="+reviewLists.get(i).getNum());		
			System.out.println("  더보고싶은  페이지"+showedPageNumber+"에"+i+"번째 데이터 생성");
		}
		
		System.out.println("더보기 요청끝! Review_List.jsp로 이동합니다!");
		writer.flush();
		
	}
	
	
	
}
