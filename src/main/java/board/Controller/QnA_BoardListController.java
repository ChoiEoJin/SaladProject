package board.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.model.BoardBean;
import board.model.BoardDao;
import utility.Paging;
@Controller
public class QnA_BoardListController {

	
	private static final String command = "/list.bd";
	private static final String getPage = "BoardList";

	@Autowired
	private BoardDao boardDao;
	

	@RequestMapping(value = command, method = RequestMethod.GET)//맨 처음 top에서 옴
	public ModelAndView doActionGET(@RequestParam(value = "whatColumn", required = false) String whatColumn,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "pageSize", required = false) String pageSize, HttpServletRequest request) {
		System.out.println("===========================");
		System.out.println("QnA리스트컨트롤러 들어옴");
		ModelAndView mav = new ModelAndView();
		
		
		

		System.err.println(request.getRequestURI());
		System.err.println(request.getContextPath());
		System.out.println("method:" + request.getMethod());
		System.out.println("검색할 컬럼(whatColumn) : " + whatColumn + ", ");
		System.out.println("검색할 단어(keyword) : " + keyword + ", ");
		System.out.println("pageNumber : " + pageNumber + ", ");
		System.out.println("pageSize : " + pageSize + ", "); // 한 페이지에 보여줄 건수(레코드갯수)

		Map<String, String> map = new HashMap<String, String>(); // 키, 값
	
		map.put("whatColumn", whatColumn);
		
	
		
		if(whatColumn==null) {
			map.put("keyword", "%" + keyword + "%");	
		}else {		
			if(whatColumn.equals("subject")) {
			map.put("keyword", "%" + keyword + "%"); }// 제목으로 검색할때는 포함되어있게 
			else {
				map.put("keyword",keyword.trim()); //작성자로 검색할때는 앞뒤공백없애고, 정확하게 
			}	
		}
		
		int totalCount = boardDao.GetTotalCount( map );   //일반게시글 계산
		System.out.println("일반게시글총갯수 : " + totalCount + ", ");
		
		String url = request.getContextPath() +  this.command ;
		System.out.println("url : "+url); // url : /ex/list.ab
	
		Paging pageInfo 
		= new Paging( pageNumber, pageSize, totalCount, url, whatColumn, keyword, null); 
		
		System.out.println();
		System.out.println( "offset : " + pageInfo.getOffset() + ", " ) ; //  offset : 0
		System.out.println( "limit : " + pageInfo.getLimit() + ", " ) ;  //limit : 2
		System.out.println( "url : " + pageInfo.getUrl() + ", " ) ; // url : /ex/list.ab

		List<BoardBean> BoardLists = boardDao.GetBoardList( pageInfo, map );//일반게시글
		// map에는 whatColumn, keyword가 담겨있다.				
		List<BoardBean> NoticeLists=boardDao.GetNoticeList();//모든 공지글 갖고오기
		
		int number= totalCount-(pageInfo.getPageNumber()-1)*pageInfo.getPageSize();		
		System.out.println("조회된 건수: " + BoardLists.size());
		mav.addObject( "BoardLists", BoardLists );	//BoardLists:한페이지에 나올 레코드	
		mav.addObject("NoticeLists",NoticeLists);
		mav.addObject( "pageInfo", pageInfo ); 
		mav.addObject("number",number);
		mav.setViewName(getPage); // "boardList.jsp"		
		return mav;
	}
}
