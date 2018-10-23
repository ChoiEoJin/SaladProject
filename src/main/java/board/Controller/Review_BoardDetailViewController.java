package board.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sun.mail.iap.Response;

import board.model.ReviewBean;
import board.model.ReviewDao;
import board.model.ReviewLikeBean;
import board.model.ReviewLikeDao;
import board.model.ReviewReplyBean;
import board.model.ReviewReplyDao;
import member.model.MemberBean;
import utility.ReviewPaging;

@Controller
public class Review_BoardDetailViewController {

	private static final String command = "/ReviewDetail.bd";
	private static final String getPage = "Review_BoardDetailView";
	
	@Autowired
	private ReviewDao reviewDao;
	
	@Autowired
	private ReviewReplyDao reviewreplydao;
	
	@Autowired
	private ReviewLikeDao reviewLikedao;
	
	@RequestMapping(value = command, method = RequestMethod.GET) //ó��9�� ������� ��Ʈ�ѷ�
	private ModelAndView reviewGet(@RequestParam(value="num",required=true) int num,
			@RequestParam(value = "whatColumn", required = false) String whatColumn,
			@RequestParam(value="keyword", required = false) String keyword,HttpServletRequest request,
			@RequestParam(value="pageNumber",required=false)String pageNumber,
			@RequestParam(value="pageSize",required=false)String pageSize,HttpServletResponse response,HttpSession session) throws IOException {
		
		System.out.println("�����Ϻ� ��Ʈ�ѷ� ������");
				
				
		if(whatColumn==null) {
			
			whatColumn="searchAll";
		}
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("whatColumn", whatColumn);
		
		if(whatColumn.equals("searchAll")) {
			//searchAll�� ������ ���
			//���1:��ó������� keyword null�� �����°��,whatColumn <select>�κ��� �����ؼ� null�� �Ǵ°��
			//���2:�ѹ� �ѷ��ְ��� %null%�� �����°��
			if(keyword==null) { //���1
				map.put("keyword", "%" + keyword + "%");				
			}else if(!keyword.equals("%null%")) {	//������ �Ȱ���??			
				map.put("keyword", "%" + keyword + "%");						
			}else {	//���2
				map.put("keyword", keyword);
			}
		
			
		}else {	//��ü�˻��� �ƴҶ�,	
			if(whatColumn.equals("subject")) {//�����϶�
			map.put("keyword", "%" + keyword + "%"); }// �������� �˻��Ҷ��� ���ԵǾ��ְ� 
			else {//�ۼ����϶�						
					map.put("keyword",keyword.trim()); //�ۼ��ڷ� �˻��Ҷ��� �յڰ�����ְ�, ��Ȯ�ϰ� 			
			}	
		}
		
		
		
		int totalCount = reviewDao.GetTotalCount( map );   
		String url = request.getContextPath() +  this.command ;
		
		ModelAndView mav = new ModelAndView();
		
		ReviewPaging reviewPageInfo
		= new ReviewPaging( pageNumber, pageSize, totalCount, url, whatColumn, keyword, null); 
				
		
		ReviewBean reviewbean = reviewDao.getReviewBeanGetByNum(num); //�ѹ��� �ش��ϴ� �Խñ� ��ü�ҷ���
		List<ReviewReplyBean> beanlist = reviewreplydao.getReviewReply(num);//�ѹ��� �ش��ϴ� ��� �������	
		reviewbean.setReviewRelpyBeanList(beanlist);//��ۺ��� �Խñۿ� ��۵� �����Ŵ	
		List<ReviewLikeBean> likebeanlist=reviewLikedao.getReviewLikeGetByNum(num);//�ѹ��� �ش��ϴ� ���ƿ丮��Ʈ�������
		reviewbean.setReviewLikeBean(likebeanlist);//���ƿ���� ������ �Խñ��� (����)
		
		//����� �����ϴ���
		int HowManyUserlikethis=likebeanlist.size();
		
		//���� ������ �����ϴ��� 
		String people=reviewbean.getPeople();
		if(HowManyUserlikethis != 0) {
		
		
			for(int i=0;i<HowManyUserlikethis;i++) {
				
				
				if(i==0) {
					
					people+=likebeanlist.get(i).getUserid();
					
				}else {
					people+=","+likebeanlist.get(i).getUserid();
				}
				
				
			}
			//people+="���� �����մϴ�";
		}
		
		reviewbean.setPeople(people);
		
		
		System.out.println("loginfo session test :"+session.getAttribute("loginfo"));
		if(session.getAttribute("loginfo") != null) { //�α��� �����϶��� 
			
			Map<String,String> map2=new HashMap<String, String>();
			
			String strNum=Integer.toString(num);
			map2.put("num",strNum);
			map2.put("loginId", ((MemberBean)session.getAttribute("loginfo")).getUserid());		
			
			int whetherLoginId_doesLikethis=reviewLikedao.check_whetherLoginId_doesLikethis(map2);
			
			//������ ������ num�� �ش��ϴ� �Խñۿ� ���ƿ並 ������ ����1 ,������ 0;
			
			mav.addObject("whetherLoginId_doesLikethis",whetherLoginId_doesLikethis);
			
			
		}
		
		
		
		
		mav.addObject("reviewbean", reviewbean);//���,���ƿ� ���� ���� ������ִ�.(pageSize��ŭ ������־�)(��)
		mav.addObject("HowManyUserlikethis",HowManyUserlikethis);//����� ���ƿ� �������� ���� , ������0 	
		
		
		mav.addObject("reviewPageInfo", reviewPageInfo);//���������ѹ�,������������,��Ż������	
		String searchedWhatColumn=map.get("whatColumn");//���ٳ�ߵʡ�
		String searchedKeyword=map.get("keyword");//���ٳ�ߵʡ�
		//whatColumn �̶� keyword ��¿� 
				System.out.println("=======�Ѿ�� whatColumn , �Ѿ�� keyword========");
				System.err.println("searchedWhatColumn: "+searchedWhatColumn);
				//whatCoulmn=searchAll or subject, writer 3���� �ϳ�
				System.err.println("searchedKeyword: "+searchedKeyword+",searchedKeyword.length(): "+searchedKeyword.length());
				//keyword=searchAll�ϰ�쿡�� ������, %null% �̰�,  suject or writer�� ��쿡�� ���ڱ״�� �Ѿ��.
				System.out.println("=======================================");
		
		
		mav.addObject("searchedWhatColumn",searchedWhatColumn);
		mav.addObject("searchedKeyword",searchedKeyword);
		
		System.out.println("ReviewDetailController ����. DetailView.jsp�� �Ѿ�ϴ�.");
		
		
		mav.setViewName(getPage);
		
		
		
		
		return mav;
		
		
	}
	
	
	
	//���ƿ� �߰� ��Ʈ�ѷ� (�����Ϻ�jsp ajax�����Ѿ��)
	@RequestMapping(value="/LoginUserLikeThisReview.bd")
	private void doAction(@RequestParam(value="num",required=false)String num,HttpSession session,
			HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		System.out.println("���ƿ� �߰� ��Ʈ�ѷ� ����");
		String userid=((MemberBean)session.getAttribute("loginfo")).getUserid();
		System.out.println("�ش� �Խñ� ��ȣ:"+num);
		System.out.println("���ƿ並 ���� �������̵�: "+userid);
		
		Map<String,String> insertMap=new HashMap<String, String>();
		
		insertMap.put("num", num);//�ش� �Խñ� �ѹ� 
		insertMap.put("userid",userid);//������ ���� ���̵� 
		
		
		
		
		int cnt=reviewLikedao.insertReviewLike(insertMap); //���ƿ� ���̺� num�� �������̵� �߰� 
		
		if(cnt==0) {
			System.out.println("���ƿ�  �߰��۾�����");
			
		}else {
			
			System.out.println("���ƿ� �߰��۾�����");
		}
		
		List<ReviewLikeBean> likebeanlist=reviewLikedao.getReviewLikeGetByNum(Integer.parseInt(num));	
		int HowManyUserlikethis=likebeanlist.size();
		ReviewBean reviewbean=reviewDao.getReviewBeanGetByNum(Integer.parseInt(num));
		//���� ������ �����ϴ��� 
				String people=reviewbean.getPeople();
				if(HowManyUserlikethis != 0) {
				
				
					for(int i=0;i<HowManyUserlikethis;i++) {
						
						
						if(i==0) {
							
							people+=likebeanlist.get(i).getUserid();
							
						}else {
							people+=","+likebeanlist.get(i).getUserid();
						}
						
						
					}
					//people+="���� �����մϴ�";
				}
				
				reviewbean.setPeople(people);
		
		PrintWriter writer=response.getWriter();		
		writer.println("<div id=\"favoriteDIV\">");
		
		
		//writer.println("<input type=\"button\" onclick=\"FavoriteBTN();\" value=\"���ƿ����\" id=\"favorite\">");
		writer.println("<img src='"+request.getContextPath()+"/resources/image/board/dislikebtn.png' onclick=\"FavoriteBTN();\"  style=\"width:100px;height:50px;\" >");
		writer.println(HowManyUserlikethis+"���� �����մϴ�");
		if(HowManyUserlikethis !=0) {
			writer.println("<br><span style=\"color:#819FF7;\">"+reviewbean.getPeople()+"</span>���� �����մϴ�");	
		}
		writer.println("</div>");
		writer.flush();

	}
		
	
	
	
	//���ƿ� ��� ��Ʈ�ѷ�(�����Ϻ�jsp���� ajax�����Ѿ��)
	@RequestMapping(value="/LoginUserDoesNotLikeThisReview.bd")
	private void doAction2(@RequestParam(value="num",required=false)String num,HttpSession session,
			HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		System.out.println("���ƿ� ���� ��Ʈ�ѷ� ����");
		String userid=((MemberBean)session.getAttribute("loginfo")).getUserid();
		System.out.println("�ش� �Խñ� ��ȣ:"+num);
		System.out.println("���ƿ� '���'�� ���� �������̵�: "+userid);
		
		Map<String,String> deleteMap=new HashMap<String, String>();
		
		deleteMap.put("num", num);//�ش� �Խñ� �ѹ� 
		deleteMap.put("userid",userid);//������ ���� ���̵� 
		
		int cnt=reviewLikedao.deleteReviewLike(deleteMap); //���ƿ� ���̺� num�� �������̵� �߰� 
		
		if(cnt==0) {
			System.out.println("���ƿ� �����۾�����");
			
		}else {
			
			System.out.println("���ƿ� �����۾�����");
		}
		
		
		List<ReviewLikeBean> likebeanlist=reviewLikedao.getReviewLikeGetByNum(Integer.parseInt(num));	
		int HowManyUserlikethis=likebeanlist.size();
		ReviewBean reviewbean=reviewDao.getReviewBeanGetByNum(Integer.parseInt(num));
		//���� ������ �����ϴ��� 
		String people=reviewbean.getPeople();
		if(HowManyUserlikethis != 0) {
				
				
			for(int i=0;i<HowManyUserlikethis;i++) {
						
						
				if(i==0) {
							
					people+=likebeanlist.get(i).getUserid();
							
				}else {
					people+=","+likebeanlist.get(i).getUserid();
				}
						
						
			}
			//people+="���� �����մϴ�";
		}
				
		reviewbean.setPeople(people);
		PrintWriter writer=response.getWriter();		
		writer.println("<div id=\"favoriteDIV\">");
		//writer.println("<input type=\"button\" onclick=\"FavoriteBTN();\" value=\"���ƿ�\" id=\"favorite\">");
		writer.println("<img src='"+request.getContextPath()+"/resources/image/board/likebutton.png' onclick=\"FavoriteBTN();\"  style=\"width:100px;height:50px;\" >");
		if(HowManyUserlikethis==0) {
			writer.println("<span style=\"color:#819FF7;\">���� ���� ���ƿ並 �����ּ���!</span>");
		}else {
			writer.println(HowManyUserlikethis+"���� �����մϴ�");
			writer.println("<br><span style=\"color:#819FF7;\">"+reviewbean.getPeople()+"</span>���� �����մϴ�");
		}
		writer.println("</div>");
		writer.flush();
	}	
}