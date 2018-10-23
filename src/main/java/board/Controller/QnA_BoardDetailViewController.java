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
		System.out.println("QnA 이용자 게시글 디테일뷰컨트롤러 /get방식 들어옴");	
		System.err.println("num:"+num);				
		BoardBean board=boardDao.getData(num);
		System.err.println("board.getSecret(): "+board.getSecret());
		ModelAndView mav= new ModelAndView();				
		if(board.getSecret()==1) {//비밀글 설정되있으면 
			
			if(session.getAttribute("loginfo")==null) { //로그인이 안되있을경우
				
				PrintWriter writer=response.getWriter();
				response.setContentType("text/html; charset(UTF-8)");
				writer.println("<script type='text/javascript'>");
				writer.println("alert('비밀글 입니다.');");
				writer.println("history.back()");
				writer.println("</script>");	
				writer.flush();
				
			}else {//로그인이 되있을경우 
	
				if(((MemberBean)session.getAttribute("loginfo")).getUserid().equals(board.getWriter())==false&&((MemberBean)session.getAttribute("loginfo")).getUserid().equals("admin")==false){								
					PrintWriter writer=response.getWriter();
					response.setContentType("text/html; charset(UTF-8)");
					writer.println("<script type='text/javascript'>");
					writer.println("alert('비밀글 입니다');");
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
