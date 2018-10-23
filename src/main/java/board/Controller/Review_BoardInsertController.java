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
	
	@RequestMapping(value=command,method=RequestMethod.GET) //�ı�Խ��ǿ��� �Ѿ��
	private String reviewInsertGET(HttpServletResponse response,HttpSession session) throws IOException {
		
		if((MemberBean)session.getAttribute("loginfo")==null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer=response.getWriter();
			
			writer.println("<script 'text/javascript'>");
			writer.println("alert('�α��� �� �̿����ּ���');");
			writer.println("history.back()");
			writer.println("</script>");
			writer.flush();
			
			
		}else {
			
			return getPage;						
		}
		
		return "�����Ǿȿ�";
		
	}
	
	@RequestMapping(value=command,method=RequestMethod.POST)
	private ModelAndView reviewInsertPOST(@ModelAttribute("review") @Valid ReviewBean reviewBean,BindingResult result,HttpServletResponse response,HttpServletRequest request) throws IOException {	
		System.out.println(this.getClass()+"���� POST����");
		if(result.hasErrors()) {		
			System.err.println("��������:"+result.getErrorCount());
			System.err.println("�������:"+result.getAllErrors().toString());
			ModelAndView mav=new ModelAndView();
			mav.setViewName(getPage);
			return mav;
			
			
		}else {//������,�̹������ε���� ���ۼ������� ����ο´�.
			
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
			// ��File.separator => " / "
			reviewBean.setImage(makenumber+multi.getOriginalFilename());
			
			System.out.println("multi.getOriginalFilename():" + multi.getOriginalFilename());

			// ��������� �׳� ��� �̹���ȣ���õɰ��ΰ��� �����ϴ� ���ڵ��̾���,
			// ��ؿ� try catch�� ���� ������ ������ �Ǵ°ž�.

			try {
				multi.transferTo(destination);
				// transferTo: ���ϴ� ��ġ�� ������ �� ���
				// File ��ü�� ���� �� MultipartFile ��ü�� transferTo �޼��带 ������� ���� �������� ������ �����

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
			writer.println("alert('���������� �߰�!');");
			writer.println("location.href='" + request.getContextPath() + "/ReviewList.bd?pageNumber=1&pageSize=9';"); // 1�������� ���ư�
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
