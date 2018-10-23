package board.Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.model.BoardBean;
import board.model.BoardDao;
import member.model.MemberBean;

@Controller
public class QnA_BoardDetailViewController {

	
	
	private static final String command="/detailView.bd";
	private static final String getPage="BoardDetailView";

	@Autowired
	private BoardDao boardDao;
	

	
	@RequestMapping(value=command,method=RequestMethod.GET)
	public ModelAndView doAcionGet(@RequestParam(value="num",required=true) int num,
			@RequestParam(value="pageNumber",required=false) int pageNumber,
			@RequestParam(value="pageSize",required=false) int pageSize,
			@RequestParam(value="whatColumn",required=false) String whatColumn,
			@RequestParam(value="keyword",required=false) String keyword,
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
		System.out.println("QnA �̿��� �Խñ� �����Ϻ���Ʈ�ѷ� /get��� ����");	
		System.err.println("num:"+num);				
		BoardBean board=boardDao.getData(num);
		System.err.println("board.getSecret(): "+board.getSecret());
		ModelAndView mav= new ModelAndView();				
		if(board.getSecret()==1) {//��б� ������������ 
			
			if(session.getAttribute("loginfo")==null) { //�α����� �ȵ��������
				
				PrintWriter writer=response.getWriter();
				response.setContentType("text/html; charset(UTF-8)");
				writer.println("<script type='text/javascript'>");
				writer.println("alert('��б� �Դϴ�.');");
				writer.println("history.back()");
				writer.println("</script>");	
				writer.flush();
				
			}else {//�α����� ��������� 
	
				if(((MemberBean)session.getAttribute("loginfo")).getUserid().equals(board.getWriter())==false&&((MemberBean)session.getAttribute("loginfo")).getUserid().equals("admin")==false){								
					PrintWriter writer=response.getWriter();
					response.setContentType("text/html; charset(UTF-8)");
					writer.println("<script type='text/javascript'>");
					writer.println("alert('��б� �Դϴ�');");
					writer.println("history.back()");
					writer.println("</script>");	
					writer.flush();
				}							
			}
			
			
		}
		
		mav.addObject("board",board );
		mav.addObject("pageNumber", pageNumber);
		mav.addObject("pageSize", pageSize);
		mav.addObject("whatColumn",whatColumn);
		mav.addObject("keyword",keyword);
		mav.setViewName(getPage);
		return mav;
	}	
	
	
	
}
