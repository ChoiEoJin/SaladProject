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
	

	@RequestMapping(value = command, method = RequestMethod.GET)//�� ó�� top���� ��
	public ModelAndView doActionGET(@RequestParam(value = "whatColumn", required = false) String whatColumn,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "pageSize", required = false) String pageSize, HttpServletRequest request) {
		System.out.println("===========================");
		System.out.println("QnA����Ʈ��Ʈ�ѷ� ����");
		ModelAndView mav = new ModelAndView();
		
		
		

		System.err.println(request.getRequestURI());
		System.err.println(request.getContextPath());
		System.out.println("method:" + request.getMethod());
		System.out.println("�˻��� �÷�(whatColumn) : " + whatColumn + ", ");
		System.out.println("�˻��� �ܾ�(keyword) : " + keyword + ", ");
		System.out.println("pageNumber : " + pageNumber + ", ");
		System.out.println("pageSize : " + pageSize + ", "); // �� �������� ������ �Ǽ�(���ڵ尹��)

		Map<String, String> map = new HashMap<String, String>(); // Ű, ��
	
		map.put("whatColumn", whatColumn);
		
	
		
		if(whatColumn==null) {
			map.put("keyword", "%" + keyword + "%");	
		}else {		
			if(whatColumn.equals("subject")) {
			map.put("keyword", "%" + keyword + "%"); }// �������� �˻��Ҷ��� ���ԵǾ��ְ� 
			else {
				map.put("keyword",keyword.trim()); //�ۼ��ڷ� �˻��Ҷ��� �յڰ�����ְ�, ��Ȯ�ϰ� 
			}	
		}
		
		int totalCount = boardDao.GetTotalCount( map );   //�ϹݰԽñ� ���
		System.out.println("�ϹݰԽñ��Ѱ��� : " + totalCount + ", ");
		
		String url = request.getContextPath() +  this.command ;
		System.out.println("url : "+url); // url : /ex/list.ab
	
		Paging pageInfo 
		= new Paging( pageNumber, pageSize, totalCount, url, whatColumn, keyword, null); 
		
		System.out.println();
		System.out.println( "offset : " + pageInfo.getOffset() + ", " ) ; //  offset : 0
		System.out.println( "limit : " + pageInfo.getLimit() + ", " ) ;  //limit : 2
		System.out.println( "url : " + pageInfo.getUrl() + ", " ) ; // url : /ex/list.ab

		List<BoardBean> BoardLists = boardDao.GetBoardList( pageInfo, map );//�ϹݰԽñ�
		// map���� whatColumn, keyword�� ����ִ�.				
		List<BoardBean> NoticeLists=boardDao.GetNoticeList();//��� ������ �������
		
		int number= totalCount-(pageInfo.getPageNumber()-1)*pageInfo.getPageSize();		
		System.out.println("��ȸ�� �Ǽ�: " + BoardLists.size());
		mav.addObject( "BoardLists", BoardLists );	//BoardLists:���������� ���� ���ڵ�	
		mav.addObject("NoticeLists",NoticeLists);
		mav.addObject( "pageInfo", pageInfo ); 
		mav.addObject("number",number);
		mav.setViewName(getPage); // "boardList.jsp"		
		return mav;
	}
}
