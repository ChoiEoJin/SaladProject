package board.Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import board.model.BoardBean;
import board.model.BoardDao;

@Controller
public class QnA_BoardUpdateController {

	private static final String command = "/update.bd";
	private static final String getPage = "BoardUpdateForm";
	
	@Autowired
	private BoardDao boardDao;
	@Autowired
	ServletContext servletcontext;
	
	@RequestMapping(value=command,method=RequestMethod.GET) //����ȭ�� ����
	private ModelAndView updateDataGET(@RequestParam (value="num",required=true)int num,
									   @RequestParam (value="pageNumber",required=false)int pageNumber,
									   @RequestParam (value="pageSize",required=false)int pageSize,
									   @RequestParam(value="whatColumn",required=false)String whatColumn,
									   @RequestParam(value="keyword",required=false)String keyword) { //num,pageNumber,pageSize�� ����ִ�.		
		ModelAndView mav=new ModelAndView();	
		BoardBean board=boardDao.getData(num);	
		mav.addObject("board",board);
		mav.addObject("pageNumber",pageNumber);
		mav.addObject("pageSize",pageSize);
		mav.addObject("whatColumn",whatColumn);
		mav.addObject("keyword",keyword);
		mav.setViewName(getPage);
		return mav;				
	}
	

	@RequestMapping(value=command,method=RequestMethod.POST)//�����۾�����
	private void updateDataPOST(@RequestParam (value="num",required=true) int num,BoardBean board,
								@RequestParam(value="pageNumber",required=false)int pageNumber,
								@RequestParam(value="pageSize",required=false)int pageSize,
								@RequestParam(value="whatColumn",required=false) String whatColumn,
								@RequestParam(value="keyword",required=false) String keyword,
								HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		  String uploadpath=servletcontext.getRealPath("/resources/image/board");		  
		  if (board.getUpload().getOriginalFilename() == "") { //�׸����� �ȳ־�����
				System.out.println("�׸����� ���°�� ����");								
		  }else {
			  	System.out.println("�׸����� �ִ°�� ����");
			  	
			  	Timestamp tst=new Timestamp(System.currentTimeMillis());
				String tststr=tst.toString();			
				String makenumber="";
				StringTokenizer stk=new StringTokenizer(tststr, "-: .");
				while(stk.hasMoreTokens()) {
					makenumber+=stk.nextToken();
				} 
			  	
		        MultipartFile multi=board.getUpload();
	            File destination=new File(uploadpath+File.separator+makenumber+multi.getOriginalFilename());         
	            board.setImage(makenumber+multi.getOriginalFilename());	     
	            
	            File destination2=new File(uploadpath+File.separator+board.getUpload2());
	            destination2.delete();
	            System.out.println("������ �ִ� �̹�������");
	            
	            try {
	               multi.transferTo(destination); 
	               System.out.println("�� �̹��� ���� ����");
	            } catch (IllegalStateException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	            } catch (IOException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	            }
	         }
		  		  
		  	System.out.println("updateData�۾���");
		  	boardDao.updateData(board);	//�����۾� ����	
		  	System.out.println("updateData�۾���");
		  	
		  	response.setContentType("text/html; charset=UTF-8");
		  	
			PrintWriter writer=response.getWriter();			
			writer.println("<script type='text/javascript'>");
			writer.println("alert('�����Ϸ�');");
			writer.println("location.href='"+ request.getContextPath()+"/list.bd?pageNumber="+pageNumber+"&pageSize="+pageSize+"&whatColumn="+whatColumn+"&keyword="+keyword+"';");//�����ִ� �������� ���ư���					
			writer.println("</script>");
			writer.flush();			
			
		
		
	}

	
}
