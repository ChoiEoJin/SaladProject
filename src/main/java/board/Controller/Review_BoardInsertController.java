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
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import board.model.ReviewBean;
import board.model.ReviewDao;
import member.model.MemberBean;

@Controller
public class Review_BoardInsertController implements ServletContextAware {

	private static final String command="/reviewInsert.bd";
	private static final String getPage="Review_BoardInsertForm";
	private static final String gotoPage="/ReviewDetail.bd";
	
	
	@Autowired
	private ReviewDao reviewDao;
	
	ServletContext servletContext;
	
	@RequestMapping(value=command,method=RequestMethod.GET) //후기게시판에서 넘어옴
	private String reviewInsertGET(HttpServletResponse response,HttpSession session) throws IOException {
		
		if((MemberBean)session.getAttribute("loginfo")==null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer=response.getWriter();
			
			writer.println("<script 'text/javascript'>");
			writer.println("alert('로그인 후 이용해주세요');");
			writer.println("history.back()");
			writer.println("</script>");
			writer.flush();
			
			
		}else {
			
			return getPage;						
		}
		
		return "어차피안옴";
		
	}
	
	@RequestMapping(value=command,method=RequestMethod.POST)
	private ModelAndView reviewInsertPOST(@ModelAttribute("review") @Valid ReviewBean reviewBean,BindingResult result,HttpServletResponse response,HttpServletRequest request) throws IOException {	
		System.out.println(this.getClass()+"리뷰 POST들어옴");
		if(result.hasErrors()) {		
			System.err.println("에러갯수:"+result.getErrorCount());
			System.err.println("에러잡기:"+result.getAllErrors().toString());
			ModelAndView mav=new ModelAndView();
			mav.setViewName(getPage);
			return mav;
			
			
		}else {//글제목,이미지업로드까지 잘작성했으면 여기로온다.
			
			String uploadPath = servletContext.getRealPath("/resources/image/board");
			
			MultipartFile multi = reviewBean.getUpload();
			Timestamp tst=new Timestamp(System.currentTimeMillis());
			String tststr=tst.toString();
			String makenumber="";
			StringTokenizer stk=new StringTokenizer(tststr, "-: .");
			while(stk.hasMoreTokens()) {
				makenumber+=stk.nextToken();
			}
			
			
			
			File destination = new File(uploadPath + File.separator +makenumber+multi.getOriginalFilename());
			// ㄴFile.separator => " / "
			reviewBean.setImage(makenumber+multi.getOriginalFilename());
			
			System.out.println("multi.getOriginalFilename():" + multi.getOriginalFilename());

			// 여기까지는 그냥 어디에 이미지호스팅될것인가를 정의하는 글자들이었고,
			// 요밑에 try catch문 에서 실제로 저장이 되는거야.

			try {
				multi.transferTo(destination);
				// transferTo: 원하는 위치에 저장할 때 사용
				// File 객체를 만든 뒤 MultipartFile 객체의 transferTo 메서드를 실행시켜 실제 물리적인 파일을 만든다

				int insertresult = reviewDao.insertReview(reviewBean);
				System.out.println(insertresult);

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			PrintWriter writer;
			response.setContentType("text/html;charset=UTF-8");
			writer = response.getWriter();

			writer.println("<script type='text/javascript'>");
			writer.println("alert('성공적으로 추가!');");
			writer.println("location.href='" + request.getContextPath() + "/ReviewList.bd?pageNumber=1&pageSize=9';"); // 1페이지로 돌아감
			writer.println("</script>");
			writer.flush();
			
		}
		return null;
	}

	@Override
	public void setServletContext(ServletContext context) {
		
		servletContext=context;
	}
	
}
