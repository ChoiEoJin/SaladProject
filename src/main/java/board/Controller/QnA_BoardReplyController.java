package board.Controller;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import board.model.BoardBean;
import board.model.BoardDao;

@Controller
public class QnA_BoardReplyController implements ServletContextAware {
	
	private static final String command = "/reply.bd";
	private static final String getPage = "BoardReplyForm";
	
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	ServletContext servletContext;

	@RequestMapping(value=command,method=RequestMethod.GET)
	   public ModelAndView goReplyForm(@RequestParam("num") int num,
			   @RequestParam(value="pageNumber",required=false)int pageNumber,
			   @RequestParam(value="pageSize",required=false)int pageSize) {
		
			
	      ModelAndView mav=new ModelAndView();
	      
	      BoardBean bean=boardDao.getData(num);//���۱��� num�� �����
	      
	      
	      mav.addObject("replyBean",bean);
	      mav.addObject("pageNumber",pageNumber);
	      mav.addObject("pageSize",pageSize);
	      mav.setViewName(getPage);//���۱��� num�� �ش��ϴ� ���ڵ带 ���帮�ö������� �Ѹ��ž�
	      
	      
	      return mav;
	   }
	@RequestMapping(value=command,method=RequestMethod.POST)
	   public ModelAndView goReplyPro(@ModelAttribute("board") @Valid BoardBean board,BindingResult result,
			   @RequestParam(value="pageNumber",required=false)int pageNumber,
			   @RequestParam(value="pageSize",required=false)int pageSize,HttpServletRequest request,HttpServletResponse response) throws IOException {
	      ModelAndView mav=new ModelAndView();
	     String uploadPath = servletContext.getRealPath("/resources/image/board");
	     System.out.println(this.getClass()+"/reply.bd(POST)����");	     
	      if(result.hasErrors()) {
	    	  
	    	  System.out.println("�����߻�");    	   	  
	    	  System.err.println("result.getErrorCount():"+result.getErrorCount());
	    	  System.err.println(result.getAllErrors().toString());
	    	  
	    	  PrintWriter writer =response.getWriter();
	    	  writer.println("<script type='text/javascript'>");
	    	  writer.println("history.back()");
	    	  writer.println("</script>");
	    	  writer.flush();
	      }else {//������������
	    	  
	    	 
	    	  if (board.getUpload().getOriginalFilename() == "") { //�׸����� �ȳ־�����
					System.err.println("�׸����� ���°�� ����");
					boardDao.insertReply(board);
					
								
				} else {//�׸����� �־�����
					System.err.println("�׸����� �ִ°�����");
					
					MultipartFile multi = board.getUpload(); // ProductDao ���� MultipartFile upload; ����
					// multi���� name="upload" �� ���� ������ ����
					//

					System.out.println("product.getUpload():" + board.getUpload());
					System.out.println("product.getImage(): " + board.getImage());
					// womanpink.jpg(�̹��� ���ϸ�)
					// �Ʒ� multi.getOrginalFilename() �� ����.
					// �ֳ�? Product���� setUpload(MultipartFile upload){
					// this.image=this.upload.getOriginalFilename(); ���� ��������ϱ�
					// }
					Timestamp tst=new Timestamp(System.currentTimeMillis());
					String tststr=tst.toString();
					String makenumber="";
					StringTokenizer stk=new StringTokenizer(tststr, "-: .");
					while(stk.hasMoreTokens()) {
						makenumber+=stk.nextToken();
					}
															
					File destination = new File(uploadPath + File.separator +makenumber+multi.getOriginalFilename());
					// ��File.separator => " / "
					board.setImage(makenumber+multi.getOriginalFilename());
					
					System.out.println("multi.getOriginalFilename():" + multi.getOriginalFilename());

					// ��������� �׳� ��� �̹���ȣ���õɰ��ΰ��� �����ϴ� ���ڵ��̾���,
					// ��ؿ� try catch�� ���� ������ ������ �Ǵ°ž�.

					try {
						multi.transferTo(destination);
						// transferTo: ���ϴ� ��ġ�� ������ �� ���
						// File ��ü�� ���� �� MultipartFile ��ü�� transferTo �޼��带 ������� ���� �������� ������ �����

						 boardDao.insertReply(board);
						
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}


				}
				
				//�׸������� �ֵ� ����  �߰��� ������
				PrintWriter writer;
				response.setContentType("text/html;charset=UTF-8");
				writer = response.getWriter();

				writer.println("<script type='text/javascript'>");
				writer.println("alert('�亯�߰�!');");
				writer.println("location.href='" + request.getContextPath() + "/list.bd?pageNumber="+pageNumber+"&pageSize="+pageSize+"';"); // 1�������� ���ư�
				writer.println("</script>");
				writer.flush();
				
			}
	    	  
	      	return mav;
	      }
	     
	
	@Override
	public void setServletContext(ServletContext context) {		
		servletContext = context;
	}
}
