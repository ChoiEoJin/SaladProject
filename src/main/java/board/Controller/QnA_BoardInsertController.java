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

import board.model.BoardBean;
import board.model.BoardDao;
import member.model.MemberBean;

@Controller
public class QnA_BoardInsertController implements ServletContextAware {

	private static final String command = "/insert.bd";


	ServletContext servletContext;

	@Autowired
	private BoardDao boardDao;
	
	

	@RequestMapping(value = command, method = RequestMethod.GET) // 리스트에서 추가하기 누르면 온다. 로그인상태에서만 글을 쓸 수있다.
	public String doActionGet(HttpSession session, HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "pageNumber", required = false) int pageNumber,
			@RequestParam(value = "pageSize", required = false) int pageSize) throws IOException {
		System.out.println(this.getClass() + "get방식 들어옴");

	
		if ((MemberBean)session.getAttribute("loginfo")== null) {// 로그인이 안했을때
			PrintWriter writer;
			response.setContentType("text/html;charset=UTF-8");
			writer = response.getWriter();
			writer.println("<script type='text/javascript'>");
			writer.println("alert('글쓰기는 로그인후 이용해주세요');");
			writer.println("location.href='" + request.getContextPath() + "/list.bd?pageNumber=" + pageNumber
					+ "&pageSize=" + pageSize + "';");// 보고있던 페이지로 돌아가기
			
			writer.println("</script>");
			writer.flush();
			
			
		} else {// 로그인이 했을때
			
			return "BoardInsertForm";
			/*String a=File.separator;*/
			
			/*writer.println("location.href='"+request.getContextPath()+a+"WEB-INF"+a+"board"+a+"BoardInsertForm"+"';");		*/
		}
		return "어차피안옴";
	}

	
	
	
	// 여기는 이미 로그인 됫으니까 들어온거임
	@RequestMapping(value = command, method = RequestMethod.POST)
	public void doActionPost(@ModelAttribute("board") @Valid BoardBean board, BindingResult result,
			HttpServletResponse response, HttpServletRequest request) throws IOException {	
		
		
		if(board.getWriter().equals("admin")) {
			
			board.setWriter("관리자");
			
		}
		
		System.out.println(this.getClass() + " /POST방식 들어옴");
		String uploadPath = servletContext.getRealPath("/resources/image/board");
		System.err.println(uploadPath);
		// 이미지 호스팅 될 주소(업로드경로)

		if (result.hasErrors()) {  //에러잡히면, 적은 그상태로뒤로 감 
			System.err.println("에러있음");			
			PrintWriter writer;
			response.setContentType("text/html;charset=UTF-8");
			writer = response.getWriter();
			writer.println("<script type='text/javascript'>");
			writer.println("history.back();");		//적은 그상태로 돌아감	
			writer.println("</script>");						
			
		} else { //에러는 안잡히면 정상 과정진행중임 그림이 있든 없든 추가는 됨

			if (board.getUpload().getOriginalFilename() == "") { //그림파일 안넣었을때
				System.err.println("그림파일 없는경우 들어옴");
				int insertresult = boardDao.insertData(board);
				System.out.println(insertresult);
							
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

					int insertresult = boardDao.insertData(board);
					System.out.println(insertresult);

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
			writer.println("alert('성공적으로 추가!');");
			writer.println("location.href='" + request.getContextPath() + "/list.bd';"); // 1페이지로 돌아감
			writer.println("</script>");
			writer.flush();
			
		}

	}
	

	
	
	@Override
	public void setServletContext(ServletContext context) {
		servletContext = context;

	}
}
