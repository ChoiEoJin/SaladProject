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
	
	

	@RequestMapping(value = command, method = RequestMethod.GET) // ����Ʈ���� �߰��ϱ� ������ �´�. �α��λ��¿����� ���� �� ���ִ�.
	public String doActionGet(HttpSession session, HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "pageNumber", required = false) int pageNumber,
			@RequestParam(value = "pageSize", required = false) int pageSize) throws IOException {
		System.out.println(this.getClass() + "get��� ����");

	
		if ((MemberBean)session.getAttribute("loginfo")== null) {// �α����� ��������
			PrintWriter writer;
			response.setContentType("text/html;charset=UTF-8");
			writer = response.getWriter();
			writer.println("<script type='text/javascript'>");
			writer.println("alert('�۾���� �α����� �̿����ּ���');");
			writer.println("location.href='" + request.getContextPath() + "/list.bd?pageNumber=" + pageNumber
					+ "&pageSize=" + pageSize + "';");// �����ִ� �������� ���ư���
			
			writer.println("</script>");
			writer.flush();
			
			
		} else {// �α����� ������
			
			return "BoardInsertForm";
			/*String a=File.separator;*/
			
			/*writer.println("location.href='"+request.getContextPath()+a+"WEB-INF"+a+"board"+a+"BoardInsertForm"+"';");		*/
		}
		return "�����Ǿȿ�";
	}

	
	
	
	// ����� �̹� �α��� �����ϱ� ���°���
	@RequestMapping(value = command, method = RequestMethod.POST)
	public void doActionPost(@ModelAttribute("board") @Valid BoardBean board, BindingResult result,
			HttpServletResponse response, HttpServletRequest request) throws IOException {	
		
		
		if(board.getWriter().equals("admin")) {
			
			board.setWriter("������");
			
		}
		
		System.out.println(this.getClass() + " /POST��� ����");
		String uploadPath = servletContext.getRealPath("/resources/image/board");
		System.err.println(uploadPath);
		// �̹��� ȣ���� �� �ּ�(���ε���)

		if (result.hasErrors()) {  //����������, ���� �׻��·εڷ� �� 
			System.err.println("��������");			
			PrintWriter writer;
			response.setContentType("text/html;charset=UTF-8");
			writer = response.getWriter();
			writer.println("<script type='text/javascript'>");
			writer.println("history.back();");		//���� �׻��·� ���ư�	
			writer.println("</script>");						
			
		} else { //������ �������� ���� ������������ �׸��� �ֵ� ���� �߰��� ��

			if (board.getUpload().getOriginalFilename() == "") { //�׸����� �ȳ־�����
				System.err.println("�׸����� ���°�� ����");
				int insertresult = boardDao.insertData(board);
				System.out.println(insertresult);
							
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

					int insertresult = boardDao.insertData(board);
					System.out.println(insertresult);

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
			writer.println("alert('���������� �߰�!');");
			writer.println("location.href='" + request.getContextPath() + "/list.bd';"); // 1�������� ���ư�
			writer.println("</script>");
			writer.flush();
			
		}

	}
	

	
	
	@Override
	public void setServletContext(ServletContext context) {
		servletContext = context;

	}
}
