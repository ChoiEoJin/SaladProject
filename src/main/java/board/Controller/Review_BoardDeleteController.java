package board.Controller;

import java.io.File;
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

import board.model.ReviewBean;
import board.model.ReviewDao;
import board.model.ReviewReplyBean;
import board.model.ReviewReplyDao;
import member.model.MemberBean;
import utility.ReviewPaging;

@Controller
public class Review_BoardDeleteController {

	private static final String command="/delReview.bd";
	private static final String getPage="ReviewList.bd";
	
	
	@Autowired
	private ReviewDao reviewDao;
	
	@Autowired
	private ReviewReplyDao reviewreplydao; 
	
	@RequestMapping(value=command,method=RequestMethod.GET)//1페이지 삭제   //pageSize=상관안함
	private void deleteReviewGET(@RequestParam (value="num",required=true) int num,HttpServletResponse response,HttpServletRequest request,
								 @RequestParam (value="pageNumber",required=false)String pageNumber,
								 @RequestParam (value="pageSize",required=false)String pageSize,
								 @RequestParam(value="whatColumn",required=false)String whatColumn,
								 @RequestParam(value="keyword",required=false)String keyword,
								 HttpSession session) throws IOException {
		System.out.println("=====================================================");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		System.out.println("딜리트컨트롤러 /delReview.bd GET들어옴");
		System.out.println("딜리트컨트롤러 num:"+num);
		System.out.println("딜리트컨트롤러 pageNumber:"+pageNumber);
		System.out.println("딜리트컨트롤러 whatColumn:"+whatColumn);
		System.out.println("딜리트컨트롤러 keyword:"+keyword); //%null%
		String Writer=reviewDao.getReviewBeanGetByNum(num).getWriter();
		reviewDao.deleteReview(num);//삭제작업 완료

		System.out.println("삭제완료");
		
		
		System.err.println("딜리트 컨트롤러 ajax로 보낼 resultdata 작업진행 printwrier이용");
		
		
		
		Map<String,String> map = new HashMap<String,String>();
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
	
			int intPageNumber=Integer.parseInt(pageNumber);
			
			int totalCount = reviewDao.GetTotalCount( map );  //삭제후 검색조건에 맞는 전체 데이터수 조회
			int totalPage;
			
			
			if(totalCount>9) {//출력하는데 필요한 페이지수가 2개 이상일때(∵1페이지 최대 게시갯수9개)
				
				int appendedTotalData = totalCount-9;//사이즈9짜리 1페이지갯수 뻄  
				System.out.println("   딜리트컨트롤러 appendedTotalData=  "+appendedTotalData); //4
				int X= appendedTotalData-appendedTotalData%3;
				System.out.println("   딜리트컨트롤러 X= "+X); //3
				int Q= X/3;				
				System.out.println("   딜리트컨트롤러 Q= "+Q);//1
				if(appendedTotalData%3>0) { //1<1.xx					
					Q=Q+1;
					System.out.println("   딜리트컨트롤러 수정된Q=Q+1결과: "+Q); //2
				}				
				 totalPage = Q+1;//페이지사이즈9였던 1페이지 갯수 다시더함//3
				 
			}else {				
				totalPage=1;
			}
			
			
			System.out.println(" 검색되서 필요한 총페이지갯수= "+totalPage);
			if(intPageNumber>totalPage || intPageNumber==totalPage) {
				intPageNumber=totalPage;
			}
			
			//pageSize는 여기전까지는 뭐든상관안함.
			//pageSize는 여기서부터 새로 조정들어감.

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer =response.getWriter();
		
			writer.println("<table id=\"maintable\">");
			//제일바깥for문 시작
			for(int i=1;i<=intPageNumber;i++) {
				
				String url = request.getContextPath() +  "/moreView.bd" ;
				if(i==1) {//첫페이지일때
					pageSize="9";
				}
				
				ReviewPaging reviewPageInfo
				= new ReviewPaging(Integer.toString(i), pageSize, totalCount, url, whatColumn, keyword, null); 
							
				if(i>1) {//2번째 페이지부터
					
					reviewPageInfo.setOffset(9+3*(i-2));
					reviewPageInfo.setLimit(3);
				}			
				
				System.out.println("딜리트 컨트롤러 "+i+"페이지 getLimit="+reviewPageInfo.getLimit());
					//원글+댓글 세트 갖고옴(★)
					List<ReviewBean> reviewLists = reviewDao.getAllReviewList(reviewPageInfo,map);//댓글들 객체가빠진 모든 원글
					int lastIndex=reviewLists.size()-1;
					
					System.out.println("딜리트 컨트롤러"+i+"페이지 lastIndex= "+lastIndex);
									
					for (int j = 0; j <=lastIndex; j++) { 
						List<ReviewReplyBean> beanlist = reviewreplydao.getReviewReply(reviewLists.get(j).getNum()); //해당Num에대한 빈리스트를 갖고옴
						//System.out.println("before) "+j+"번쨰 beanlist 사이즈:"+beanlist.size());//정상출력						
						reviewLists.get(j).setReviewRelpyBeanList(beanlist);//여기 세터가 					
						//System.out.println("after) "+j+"번쨰 beanlist 사이즈:"+reviewLists.get(j).getReviewRelpyBeanList().size());											
						int forNum=reviewLists.get(j).getNum();
						String forPageNum=Integer.toString(i);											
						//System.err.println("request.getContextPath()= "+request.getContextPath());
						
						if(j%3==0) {
						writer.println("<tr>");
						}
						
						writer.println("<td>");
						
						if(reviewLists.get(j).getReviewLikeBean().size()>=2) {
						writer.println("<span style='float:left; color:red;'>");
						//writer.println("페이지넘버:"+forPageNum);
						writer.println("HOT");
						writer.println("</span>");
						}
								
						String a="<input style=\"float:right;\" class=\"btn btn-link btn-xs\" type=\"button\" value=\"X\" onclick=\"deleteFunction('";			
						String b=forNum+"','";
						String c=forPageNum+"','";
						String d=pageSize+"','";
						String e=whatColumn+"','";
						String f=keyword+"')\">";
						String str=a+b+c+d+e+f;
						System.out.println(str);	
						
						if(session.getAttribute("loginfo")!=null) {
							
							if(((MemberBean)session.getAttribute("loginfo")).getUserid().equals("admin") ||((MemberBean)session.getAttribute("loginfo")).getUserid().equals(Writer) ) {
								writer.println(str);//글삭제 input생성구문
							}													
						}				
						//writer.println("<a href='ReviewDetail.bd?num="+forNum+"&pageNumber="+forPageNum+"&pageSize="+pageSize+"&whatColumn="+whatColumn+"&keyword="+keyword+"'>");
						
						writer.println("<img class=\"img-thumbnail\" src='"		
								+request.getContextPath()+"/resources"+"/image"+"/board"
								+(String) File.separator + reviewLists.get(j).getImage()+"'"
								+ " alt='"+reviewLists.get(j).getImage()+"' style='height:300px; width:370px; display:block;' onclick=\"detailPopup('"+forNum+"','"+forPageNum+"','"+pageSize+"','"+whatColumn+"','"+keyword+"');\">");						   
					   //writer.println("</a>");					  					   
					   writer.println("</td>");						   
						if(j%3==2||j==lastIndex) {
						writer.println("</tr>");						
						}																		
					}//가장 안쪽for문			
			}//가장바깥쪽 for문	
			

			System.out.println("딜리트 컨트롤러 완료후 마지막까지찍혀야하는 페이지넘버: "+intPageNumber);
			
			
			writer.println("<input type=\"hidden\" id=\"searchedWhatColumn\" value=\""+whatColumn+"\">");
			writer.println("<input type=\"hidden\" id=\"searchedKeyword\" value=\""+keyword+"\">");
			writer.println("<input type=\"hidden\" id=\"moreViewPageSize\" value=\""+"3"+"\">");
			writer.println("<input type=\"hidden\" id=\"currentPageNumber\" value=\""+intPageNumber+"\">");
			writer.println("<input type=\"hidden\" id=\"totalPage\" value=\""+totalPage+"\">");
			writer.println("</table>");		
			writer.flush();	

	}
	
	
}
