package board.Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import board.model.ReviewBean;
import board.model.ReviewDao;
import board.model.ReviewLikeBean;
import board.model.ReviewLikeDao;
import board.model.ReviewReplyBean;
import board.model.ReviewReplyDao;
import member.model.MemberBean;
import utility.ReviewPaging;

@Controller
public class Review_BoardListController {

	private static final String command1 = "/ReviewList.bd";//top.jsp���� �Ϸο´�.
	private static final String getPage = "Review_List";
	
	

	@Autowired
	private ReviewDao reviewDao;
	@Autowired
	private ReviewReplyDao reviewreplydao;

	@Autowired
	private ReviewLikeDao reviewlikeDao;
	
	//top���� ���°��, ���ǰ˻��� �ϴ°�� 
	@RequestMapping(value = command1, method = RequestMethod.GET) //ó��9�� ������� ��Ʈ�ѷ�
	private ModelAndView reviewGet(@RequestParam(value = "whatColumn", required = false) String whatColumn,//top���� ������
			@RequestParam(value="keyword", required = false) String keyword,HttpServletRequest request,//top���� ������
			@RequestParam(value="pageNumber",required=false)String pageNumber,//top���� ������
			@RequestParam(value="pageSize",required=false)String pageSize,HttpServletResponse response) throws IOException {
			//top���� ������
		
		System.out.println("==================================================================");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println( "����Ʈ��Ʈ�ѷ�  �⺻�Խñۻѷ��ֱ� ����");

		Map<String, String> map = new HashMap<String, String>();
				
		if(whatColumn==null) {
			
			whatColumn="searchAll";
		}
		
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
		System.out.println("�˻��� �����Ͱ���(totalCount) : " + totalCount + ", ");		
		String url = request.getContextPath() +  this.command1 ;
		//System.out.println("url : "+url); 
		ModelAndView mav = new ModelAndView();
		
		ReviewPaging reviewPageInfo
		= new ReviewPaging( pageNumber, pageSize, totalCount, url, whatColumn, keyword, null); 
			
		List<ReviewBean> reviewLists = reviewDao.getAllReviewList(reviewPageInfo,map);//��۵� ��ü������ ��� ����
		
		//List<List<ReviewReplyBean>> AllReplyBeanList = new ArrayList<List<ReviewReplyBean>>(); //��� ��۵��� ��Ƶ� �����

			for (int i = 0; i < reviewLists.size(); i++) {
				List<ReviewReplyBean> beanlist = reviewreplydao.getReviewReply(reviewLists.get(i).getNum()); //�ش�Num������ �󸮽�Ʈ�� �����
				//System.out.println(i+"���� beanlist ������:"+beanlist.size());//�������
				List<ReviewLikeBean> likelist=reviewlikeDao.getReviewLikeGetByNum(reviewLists.get(i).getNum());//�ش� num������ ���ƿ�
				reviewLists.get(i).setReviewRelpyBeanList(beanlist);//���� ���Ͱ� 
				reviewLists.get(i).setReviewLikeBean(likelist);//���ƿ� ��������ϱ����� ���
			
				//System.out.println(i+"��° "+reviewLists.get(i).getReviewRelpyBeanList().size());
				
				//AllReplyBeanList.add(beanlist);//�ش� Num������ �󸮽�Ʈ���� ������ ä����
			}

			/*for (ReviewBean bean : reviewLists) { //�����߉糪 ���Ͱ���
				System.out.println("==============================");
				System.out.println("���۳ѹ�" + bean.getNum());
				System.out.println("�����ۼ���" + bean.getWriter());
				System.out.println(bean.getNum()+"�� ���ۿ� �޸� ����� ����:" + bean.getReviewRelpyBeanList().size());
				System.out.println("==============================");
			}*/

		int number= totalCount-(reviewPageInfo.getPageNumber()-1)*reviewPageInfo.getPageSize();		
		System.out.println("number:"+number);//��ó�������ϴ� ��ȣ
		mav.addObject("number", number);//��¿�
		
		//detailView���
		mav.addObject("reviewLists", reviewLists);//��۱��� ���� ������ִ�.(pageSize��ŭ ������־�)(��)
		mav.addObject("reviewPageInfo", reviewPageInfo);//���������ѹ�,������������,��Ż������	
		mav.addObject("totalCount",totalCount);
		mav.addObject("currentPageNumber",reviewPageInfo.getPageNumber());
		
		
		String searchedWhatColumn=map.get("whatColumn");//���ٳ�ߵʡ�
		String searchedKeyword=map.get("keyword");//���ٳ�ߵʡ�
		System.out.println("");
		
		//whatColumn �̶� keyword ��¿� 
		System.out.println("=======�Ѿ�� whatColumn====== , ========�Ѿ�� keyword========");
		
		System.out.println("����Ʈ��Ʈ�ѷ�  searchedWhatColumn: "+searchedWhatColumn);
		//whatCoulmn=searchAll or subject, writer 3���� �ϳ�
		System.out.println("����Ʈ��Ʈ�ѷ�  searchedKeyword: "+searchedKeyword+",searchedKeyword.length(): "+searchedKeyword.length());
		//keyword=searchAll�ϰ�쿡�� ������, %null% �̰�,  suject or writer�� ��쿡�� ���ڱ״�� �Ѿ��.
		System.out.println("=======================================");
		
		
		
		
		mav.addObject("searchedWhatColumn",searchedWhatColumn);
		mav.addObject("searchedKeyword",searchedKeyword);
		
		
		
		System.out.println("list��Ʈ�ѷ� ���� ����Ʈjsp�� �̵��մϴ�");
		mav.setViewName(getPage);
		return mav;

	}//����Ʈ.jsp
	
	
	
	
	
	
	
	
	//������ ajax��û�޴� 
	@RequestMapping(value="moreView.bd",method=RequestMethod.GET)
	private void ajaxMoreViewGET(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		System.out.println("=====================================================");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		System.out.println("<������ ��Ʈ�ѷ�  ��û�޾���>");
		String showedPageNumber=request.getParameter("showedPageNumber");//2
		String searchedWhatColumn=request.getParameter("searchedWhatColumn");
		String searchedKeyword=request.getParameter("searchedKeyword");
		String moreViewPageSize=request.getParameter("moreViewPageSize");	
		
		System.out.println("������ ��Ʈ�ѷ����� ��������� ��������ȣ= "+showedPageNumber);//2
		//System.out.println("�����Ʈ�ѷ� searchedWhatColumn= "+searchedWhatColumn);
		//System.out.println("�����Ʈ�ѷ� searchedKeyword= "+searchedKeyword);
		//System.out.println("�����Ʈ�ѷ� moreViewPageSize= "+moreViewPageSize);//3
		
		
		
		Map<String,String> map=new HashMap<String,String>();
		
		
			if(searchedWhatColumn==null) {
						
				searchedWhatColumn="searchAll";
			}
				
				map.put("whatColumn", searchedWhatColumn);
				
				if(searchedWhatColumn.equals("searchAll")) {
					//searchAll�� ������ ���
					//���1:��ó������� keyword null�� �����°��,whatColumn <select>�κ��� �����ؼ� null�� �Ǵ°��
					//���2:�ѹ� �ѷ��ְ��� %null%�� �����°��
					if(searchedKeyword==null) { //���1
						map.put("keyword", "%" + searchedKeyword + "%");				
					}else if(!searchedKeyword.equals("%null%")) {	//������ �Ȱ���??			
						map.put("keyword", "%" + searchedKeyword + "%");						
					}else {	//���2
						map.put("keyword", searchedKeyword);
					}
				
					
				}else {	//��ü�˻��� �ƴҶ�,	
					if(searchedWhatColumn.equals("subject")) {//�����϶�
					map.put("keyword", "%" + searchedKeyword + "%"); }// �������� �˻��Ҷ��� ���ԵǾ��ְ� 
					else {//�ۼ����϶�						
							map.put("keyword",searchedKeyword.trim()); //�ۼ��ڷ� �˻��Ҷ��� �յڰ�����ְ�, ��Ȯ�ϰ� 			
					}	
				}
		
				//9+3*(showedPage-1) ��������� ������ 2���ͽ�����
				int totalCount = reviewDao.GetTotalCount( map );   
				String url = request.getContextPath() +  "/moreView.bd" ;
				ReviewPaging reviewPageInfo
				= new ReviewPaging( showedPageNumber, moreViewPageSize, totalCount, url, searchedWhatColumn, searchedKeyword, null); 
				//���� 2���� ����ΰ� showedPageNumber,moreViewPageSize ��ġ������ �̰Űǵ������� �ؿ��� ��������
				
				
				
				
				System.err.println("moreViewPageSize= "+moreViewPageSize);				
				reviewPageInfo.setOffset(9+3*(Integer.parseInt(showedPageNumber)-2));
				
				
				
				
				
		//����+��� +���ƿ� ��Ʈ �����(��)
				List<ReviewBean> reviewLists = reviewDao.getAllReviewList(reviewPageInfo,map);//��۵� ��ü������ ��� ����
				for (int i = 0; i < reviewLists.size(); i++) {
					List<ReviewReplyBean> beanlist = reviewreplydao.getReviewReply(reviewLists.get(i).getNum()); //�ش�Num������ �󸮽�Ʈ�� �����
					List<ReviewLikeBean> likelist=reviewlikeDao.getReviewLikeGetByNum(reviewLists.get(i).getNum());//�ش� num������ ���ƿ�
					
					//System.out.println(i+"���� beanlist ������:"+beanlist.size());//�������
					
					reviewLists.get(i).setReviewRelpyBeanList(beanlist);//���� ���Ͱ� 
					reviewLists.get(i).setReviewLikeBean(likelist);//���ƿ� ��������ϱ����� ���
					//System.out.println(i+"��° "+reviewLists.get(i).getReviewRelpyBeanList().size());
				}
					
		//�ѷ�������		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer=response.getWriter();
		int lastIndex=reviewLists.size()-1;
		System.out.println("�����Ʈ�ѷ� lastIndex= "+lastIndex);
		
		
		for(int i=0;i<=lastIndex;i++) { // 1�� or2�� or3�� �������
			
			int num=reviewLists.get(i).getNum();
			String pageNumber=showedPageNumber;
			String pageSize=moreViewPageSize;
				
								
				System.out.println("�����Ʈ�ѷ� for��  moreViewPageSize="+moreViewPageSize);
				String whatColumn=searchedWhatColumn;
				
				System.out.println("�����Ʈ�ѷ� for�� searchedWhatColumn="+searchedWhatColumn);
				String keyword=searchedKeyword;
				
				System.out.println("�����Ʈ�ѷ� for�� searchedKeyword="+searchedKeyword);
				
				//System.err.println("request.getContextPath()= "+request.getContextPath());
			if(i==0) {
			writer.println("<tr>");
			}
			//${pageContext.request.contextPath}/resources/image/board"
			writer.println("<td>");
			//writer.println("<span style='float:left;'>�������ѹ�:"+pageNumber+"</span>");
				
			if(reviewLists.get(i).getReviewLikeBean().size()>=2) {
				writer.println("<span style=\"color:red; float:left;\">HOT</span>");
			}
			
			String a="<input style='float:right;' class=\"btn btn-link btn-xs\" type=\"button\" value=\"X\" onclick=\"deleteFunction('";			
			String b=num+"','";
			String c=pageNumber+"','";
			String d=pageSize+"','";
			String e=whatColumn+"','";
			String f=keyword+"')\">";
			String str=a+b+c+d+e+f;
			//System.out.println(str);
			
					
			if(session.getAttribute("loginfo")!=null) {
				String Writer=reviewLists.get(i).getWriter();

				if(((MemberBean)session.getAttribute("loginfo")).getUserid().equals("admin") || ((MemberBean)session.getAttribute("loginfo")).getUserid().equals(Writer) ) {
					writer.println(str);//�ۻ��� input��������
				}													
			}	
			
			//writer.println("<a href='ReviewDetail.bd?num="+num+"&pageNumber="+pageNumber+"&pageSize="+pageSize+"&whatColumn="+whatColumn+"&keyword="+keyword+"'>");
			writer.println("<img class=\"img-thumbnail\" src='"		
					+request.getContextPath()+"/resources"+"/image"+"/board"
					+(String) File.separator + reviewLists.get(i).getImage()+"'"
					+ " alt='"+reviewLists.get(i).getImage()+"' style='height:300px; width:370px; display:block;'"
							+ "onclick=\"detailPopup('"+num+"','"+pageNumber+"','"+pageSize+"','"+whatColumn+"','"+keyword+"');\">");
									
		  // writer.println("</a>");
		   writer.println("</td>");			
			if(i==lastIndex) {
			writer.println("</tr>");
			}	
			System.out.println("  ���������  ������"+showedPageNumber+"��"+i+"��° �������� num="+reviewLists.get(i).getNum());		
			System.out.println("  ���������  ������"+showedPageNumber+"��"+i+"��° ������ ����");
		}
		
		System.out.println("������ ��û��! Review_List.jsp�� �̵��մϴ�!");
		writer.flush();
		
	}
	
	
	
}
