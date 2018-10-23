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
	      
	      BoardBean bean=boardDao.getData(num);//원작글의 num을 갖고와
	      
	      
	      mav.addObject("replyBean",bean);
	      mav.addObject("pageNumber",pageNumber);
	      mav.addObject("pageSize",pageSize);
	      mav.setViewName(getPage);//원작글의 num에 해당하는 레코드를 보드리플라이폼에 뿌릴거야
	      
	      
	      return mav;
	   }
	@RequestMapping(value=command,method=RequestMethod.POST)
	   public ModelAndView goReplyPro(@ModelAttribute("board") @Valid BoardBean board,BindingResult result,
			   @RequestParam(value="pageNumber",required=false)int pageNumber,
			   @RequestParam(value="pageSize",required=false)int pageSize,HttpServletRequest request,HttpServletResponse response) throws IOException {
	      ModelAndView mav=new ModelAndView();
	     String uploadPath = servletContext.getRealPath("/resources/image/board");
	     System.out.println(this.getClass()+"/reply.bd(POST)들어옴");	     
	      if(result.hasErrors()) {
	    	  
	    	  System.out.println("에러발생");    	   	  
	    	  System.err.println("result.getErrorCount():"+result.getErrorCount());
	    	  System.err.println(result.getAllErrors().toString());
	    	  
	    	  PrintWriter writer =response.getWriter();
	    	  writer.println("<script type='text/javascript'>");
	    	  writer.println("history.back()");
	    	  writer.println("</script>");
	    	  writer.flush();
	      }else {//에러가없으면
	    	  
	    	 
	    	  if (board.getUpload().getOriginalFilename() == "") { //그림파일 안넣었을때
					System.err.println("그림파일 없는경우 들어옴");
					boardDao.insertReply(board);
					
								
				} else {//그림파일 넣었을때
					System.err.println("그림파일 있는경우들어옴");
					
					MultipartFile multi = board.getUpload(); // ProductDao 에서 MultipartFile upload; 만듬
					// multi에는 name="upload" 인 곳의 정보가 들어간다
					//

					System.out.println("product.getUpload():" + board.getUpload());
					System.out.println("product.getImage(): " + board.getImage());
					// womanpink.jpg(이미지 파일명)
					// 아래 multi.getOrginalFilename() 과 같다.
					// 왜냐? Product에서 setUpload(MultipartFile upload){
					// this.image=this.upload.getOriginalFilename(); 으로 만들었으니까
					// }
					Timestamp tst=new Timestamp(System.currentTimeMillis());
					String tststr=tst.toString();
					String makenumber="";
					StringTokenizer stk=new StringTokenizer(tststr, "-: .");
					while(stk.hasMoreTokens()) {
						makenumber+=stk.nextToken();
					}
															
					File destination = new File(uploadPath + File.separator +makenumber+multi.getOriginalFilename());
					// ㄴFile.separator => " / "
					board.setImage(makenumber+multi.getOriginalFilename());
					
					System.out.println("multi.getOriginalFilename():" + multi.getOriginalFilename());

					// 여기까지는 그냥 어디에 이미지호스팅될것인가를 정의하는 글자들이었고,
					// 요밑에 try catch문 에서 실제로 저장이 되는거야.

					try {
						multi.transferTo(destination);
						// transferTo: 원하는 위치에 저장할 때 사용
						// File 객체를 만든 뒤 MultipartFile 객체의 transferTo 메서드를 실행시켜 실제 물리적인 파일을 만든다

						 boardDao.insertReply(board);
						
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}


				}
				
				//그림파일이 있든 없든  추가는 성공함
				PrintWriter writer;
				response.setContentType("text/html;charset=UTF-8");
				writer = response.getWriter();

				writer.println("<script type='text/javascript'>");
				writer.println("alert('답변추가!');");
				writer.println("location.href='" + request.getContextPath() + "/list.bd?pageNumber="+pageNumber+"&pageSize="+pageSize+"';"); // 1페이지로 돌아감
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
