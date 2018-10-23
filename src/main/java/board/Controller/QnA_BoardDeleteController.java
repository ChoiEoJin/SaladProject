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
									   @RequestParam(value="keyword",required=false)String keyword) { //num,pageNumber,pageSize�� ����ִ�.
		
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
		if(!rePasswd.equals(passwd)) { //��й�ȣ�� Ʋ����			
			writer.println("alert('��й�ȣ�� ��ġ�����ʽ��ϴ�!');");
			writer.println("history.back()");			
		}else {//��й�ȣ�� ��ġ�ϸ�
			writer.println("var reply=confirm('������ ���� �����Ͻǰǰ���?');");
			writer.println("alert(reply);");//�۵��ߵ�
			writer.println("if(reply==false){"  //�ۻ������� if����					
					+"history.go(-2)");					
			writer.println("}else{"); //�ۻ��� else����						
			boardDao.deleteData(num);	//������Ŵ
			writer.println("alert('�����Ϸ�!');");
			Map<String,String> map=new HashMap<String,String>();			
			map.put("whatColumn","");
			map.put("keyword","");
			int totalCount=boardDao.GetTotalCount(map);	//������Ų�� ��ü ���ڵ�� ��ȸ			
			int endPage; //�������� ����		
			if(totalCount%pageSize==0) {//90�� %10 ������0			
				endPage=totalCount/pageSize; 						
			}else {//92�� %10 ������ 2				
				endPage=(totalCount/pageSize+1); 
			}				
			//���� �������� ������ ������ �������� ���� ������ ������������ ���ư���
			if(pageNumber<endPage) {	
				writer.println("location.href='"+ request.getContextPath()+"/list.bd?pageNumber="+pageNumber+"&pageSize="+pageSize+"&whatColumn="+whatColumn+"&keyword="+keyword+"';");				
			}else{//�������̰� �����ļ����� ������������ ���ų�,ũ�� ���������ΰ���.				
				writer.println("location.href='"+ request.getContextPath()+"/list.bd?pageNumber="+endPage+"&pageSize="+pageSize+"&whatColumn="+whatColumn+"&keyword="+keyword+"';");							
			}				
			writer.println("}");
			writer.println("</script>");
			writer.flush();	
		}//��й�ȣ ��ġ else��
		
		
		
		
		
	}	
}