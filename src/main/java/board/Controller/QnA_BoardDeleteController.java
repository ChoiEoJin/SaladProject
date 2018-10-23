package board.Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.model.BoardBean;
import board.model.BoardDao;

@Controller
public class QnA_BoardDeleteController {
	
	private static final String command="delete.bd";
	private static final String getPage="BoardDeleteForm";
	
	
	@Autowired
	private BoardDao boardDao;
	
	@RequestMapping(value=command,method=RequestMethod.GET)
	private ModelAndView deleteDataGET(@RequestParam (value="num",required=true)int num,
									   @RequestParam (value="pageNumber",required=false)int pageNumber,
									   @RequestParam (value="pageSize",required=false)int pageSize,
									   @RequestParam (value="whatColumn",required=false)String whatColumn,
									   @RequestParam(value="keyword",required=false)String keyword) { //num,pageNumber,pageSize를 담고있다.
		
		ModelAndView mav=new ModelAndView();
		mav.addObject("num",num);
		mav.addObject("pageNumber",pageNumber);
		mav.addObject("pageSize",pageSize);
		mav.addObject("whatColumn",whatColumn);
		mav.addObject("keyword",keyword);
		mav.setViewName(getPage);
		return mav;
				
	}
	
	@RequestMapping(value=command,method=RequestMethod.POST)
	private void deleteDataPOST(@RequestParam (value="num",required=true)int num,
				 				  @RequestParam(value="rePasswd",required=false)String rePasswd,
				 				  HttpServletResponse response,HttpServletRequest request,
				 				  @RequestParam(value="pageNumber",required=false)int pageNumber,
				 				  @RequestParam(value="pageSize",required=false)int pageSize,
				 				  @RequestParam(value="whatColumn",required=false)String whatColumn,
				 				  @RequestParam(value="keyword",required=false)String keyword) throws IOException {	
		
		BoardBean board=boardDao.getData(num);	
		String passwd=board.getPasswd();
		PrintWriter writer;
		response.setContentType("text/html;charset=UTF-8");
		writer = response.getWriter();
		writer.println("<script type='text/javascript'>");		
		if(!rePasswd.equals(passwd)) { //비밀번호가 틀리면			
			writer.println("alert('비밀번호가 일치하지않습니다!');");
			writer.println("history.back()");			
		}else {//비밀번호가 일치하면
			writer.println("var reply=confirm('정말로 글을 삭제하실건가요?');");
			writer.println("alert(reply);");//작동잘됨
			writer.println("if(reply==false){"  //글삭제여부 if시작					
					+"history.go(-2)");					
			writer.println("}else{"); //글삭제 else시작						
			boardDao.deleteData(num);	//삭제시킴
			writer.println("alert('삭제완료!');");
			Map<String,String> map=new HashMap<String,String>();			
			map.put("whatColumn","");
			map.put("keyword","");
			int totalCount=boardDao.GetTotalCount(map);	//삭제시킨후 전체 레코드수 조회			
			int endPage; //끝페이지 설정		
			if(totalCount%pageSize==0) {//90개 %10 나머지0			
				endPage=totalCount/pageSize; 						
			}else {//92개 %10 나머지 2				
				endPage=(totalCount/pageSize+1); 
			}				
			//현재 페이지가 삭제후 수정된 끝페이지 보다 작으면 현재페이지로 돌아간다
			if(pageNumber<endPage) {	
				writer.println("location.href='"+ request.getContextPath()+"/list.bd?pageNumber="+pageNumber+"&pageSize="+pageSize+"&whatColumn="+whatColumn+"&keyword="+keyword+"';");				
			}else{//현재페이가 삭제후수정된 끝페이지보다 같거나,크면 끝페이지로간다.				
				writer.println("location.href='"+ request.getContextPath()+"/list.bd?pageNumber="+endPage+"&pageSize="+pageSize+"&whatColumn="+whatColumn+"&keyword="+keyword+"';");							
			}				
			writer.println("}");
			writer.println("</script>");
			writer.flush();	
		}//비밀번호 일치 else끝
		
		
		
		
		
	}	
}