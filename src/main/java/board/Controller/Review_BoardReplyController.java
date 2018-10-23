package board.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import board.model.ReviewReplyBean;
import board.model.ReviewReplyDao;

@Controller
public class Review_BoardReplyController {

	private static final String command = "/ReviewReply.bd";
	private static final String getPage = "redirect:/ReviewDetail.bd";

	@Autowired
	private ReviewReplyDao reviewReplyDao;


	@RequestMapping(value = command, method = RequestMethod.POST)
	private void ReviewReplyGET(@RequestParam(value = "num", required = true) int num,
			ReviewReplyBean replyReviewBean,
			@RequestParam(value="pageNumber",required=false)String pageNumber,
			@RequestParam(value="pageSize",required=false)String pageSize,
			@RequestParam(value="searchedWhatColumn",required=false)String searchedWhatColumn,
			@RequestParam(value="searchedKeyword",required=false)String searchedKeyword,
			HttpServletRequest request,HttpServletResponse response) throws IOException {

		System.out.println(this.getClass() + "/�����۴ޱ�POST����");
		System.out.println("�Ѱܹ���list.num=" + num);

		System.out.println("============================");
		System.out.println(replyReviewBean.getNum());// 2
		System.out.println(replyReviewBean.getReplyContent());
		System.out.println(replyReviewBean.getWriter());// testid
		System.out.println("============================");

		int cnt = -1;
		cnt = reviewReplyDao.insertReviewReply(replyReviewBean);// DB�߰��Ϸ�
		if (cnt == 1) {
			System.out.println("���DB�߰��۾� �Ϸ�");
		}

		List<ReviewReplyBean> ReplyBeanList = reviewReplyDao.getReviewReply(num);// ��۵��� ����Ʈ �ϳ��� ����(�ش�ѹ����ش��ϴ�)(�ֱٵ�ϼ�
																					// ��������)

		for (ReviewReplyBean bean : ReplyBeanList) { //������ ��ȣ�� num�� ���� �޸� ���(��)�� ����

			System.out.println(bean.getNum());
			System.out.println(bean.getWriter());
			System.out.println(bean.getRegdate());
			System.out.println(bean.getReplyContent());
		}
		System.out.println("=======���� ���ö��̿���Ʈ�ѷ����� Ȯ���ؾ��ϴ� �������ѹ� ������������======");
		System.out.println("pageNumber= "+pageNumber+"  ,  pageSize= "+ pageSize );
		System.out.println("whatColumn="+searchedWhatColumn+" ,keyword="+searchedKeyword);
		
		System.out.println("==============================================");
		PrintWriter writer;
		response.setContentType("text/html;charset=UTF-8");
		writer = response.getWriter();

		writer.println("<script type='text/javascript'>");
		writer.println("alert('��� �߰�!');");
		writer.println("location.href='" + request.getContextPath() + "/ReviewDetail.bd?num="+num+"&pageNumber="+pageNumber+"&pageSize="+pageSize+"&whatColumn="+searchedWhatColumn+"&keyword="+searchedKeyword+"';"); // 1�������� ���ư�
		writer.println("</script>");
		writer.flush();
		
		

	}
	
	
	
	
}
