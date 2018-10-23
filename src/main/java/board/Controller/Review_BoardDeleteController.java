package board.Controller;

import java.io.File;
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

import board.model.ReviewBean;
import board.model.ReviewDao;
import board.model.ReviewReplyBean;
import board.model.ReviewReplyDao;
import member.model.MemberBean;
import utility.ReviewPaging;

@Controller
public class Review_BoardDeleteController {

	private static final String command="/delReview.bd";
	private static final String getPage="ReviewList.bd";
	
	
	@Autowired
	private ReviewDao reviewDao;
	
	@Autowired
	private ReviewReplyDao reviewreplydao; 
	
	@RequestMapping(value=command,method=RequestMethod.GET)//1������ ����   //pageSize=�������
	private void deleteReviewGET(@RequestParam (value="num",required=true) int num,HttpServletResponse response,HttpServletRequest request,
								 @RequestParam (value="pageNumber",required=false)String pageNumber,
								 @RequestParam (value="pageSize",required=false)String pageSize,
								 @RequestParam(value="whatColumn",required=false)String whatColumn,
								 @RequestParam(value="keyword",required=false)String keyword,
								 HttpSession session) throws IOException {
		System.out.println("=====================================================");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		System.out.println("����Ʈ��Ʈ�ѷ� /delReview.bd GET����");
		System.out.println("����Ʈ��Ʈ�ѷ� num:"+num);
		System.out.println("����Ʈ��Ʈ�ѷ� pageNumber:"+pageNumber);
		System.out.println("����Ʈ��Ʈ�ѷ� whatColumn:"+whatColumn);
		System.out.println("����Ʈ��Ʈ�ѷ� keyword:"+keyword); //%null%
		String Writer=reviewDao.getReviewBeanGetByNum(num).getWriter();
		reviewDao.deleteReview(num);//�����۾� �Ϸ�

		System.out.println("�����Ϸ�");
		
		
		System.err.println("����Ʈ ��Ʈ�ѷ� ajax�� ���� resultdata �۾����� printwrier�̿�");
		
		
		
		Map<String,String> map = new HashMap<String,String>();
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
	
			int intPageNumber=Integer.parseInt(pageNumber);
			
			int totalCount = reviewDao.GetTotalCount( map );  //������ �˻����ǿ� �´� ��ü �����ͼ� ��ȸ
			int totalPage;
			
			
			if(totalCount>9) {//����ϴµ� �ʿ��� ���������� 2�� �̻��϶�(��1������ �ִ� �Խð���9��)
				
				int appendedTotalData = totalCount-9;//������9¥�� 1���������� �M  
				System.out.println("   ����Ʈ��Ʈ�ѷ� appendedTotalData=  "+appendedTotalData); //4
				int X= appendedTotalData-appendedTotalData%3;
				System.out.println("   ����Ʈ��Ʈ�ѷ� X= "+X); //3
				int Q= X/3;				
				System.out.println("   ����Ʈ��Ʈ�ѷ� Q= "+Q);//1
				if(appendedTotalData%3>0) { //1<1.xx					
					Q=Q+1;
					System.out.println("   ����Ʈ��Ʈ�ѷ� ������Q=Q+1���: "+Q); //2
				}				
				 totalPage = Q+1;//������������9���� 1������ ���� �ٽô���//3
				 
			}else {				
				totalPage=1;
			}
			
			
			System.out.println(" �˻��Ǽ� �ʿ��� ������������= "+totalPage);
			if(intPageNumber>totalPage || intPageNumber==totalPage) {
				intPageNumber=totalPage;
			}
			
			//pageSize�� ������������ ����������.
			//pageSize�� ���⼭���� ���� ������.

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer =response.getWriter();
		
			writer.println("<table id=\"maintable\">");
			//���Ϲٱ�for�� ����
			for(int i=1;i<=intPageNumber;i++) {
				
				String url = request.getContextPath() +  "/moreView.bd" ;
				if(i==1) {//ù�������϶�
					pageSize="9";
				}
				
				ReviewPaging reviewPageInfo
				= new ReviewPaging(Integer.toString(i), pageSize, totalCount, url, whatColumn, keyword, null); 
							
				if(i>1) {//2��° ����������
					
					reviewPageInfo.setOffset(9+3*(i-2));
					reviewPageInfo.setLimit(3);
				}			
				
				System.out.println("����Ʈ ��Ʈ�ѷ� "+i+"������ getLimit="+reviewPageInfo.getLimit());
					//����+��� ��Ʈ �����(��)
					List<ReviewBean> reviewLists = reviewDao.getAllReviewList(reviewPageInfo,map);//��۵� ��ü������ ��� ����
					int lastIndex=reviewLists.size()-1;
					
					System.out.println("����Ʈ ��Ʈ�ѷ�"+i+"������ lastIndex= "+lastIndex);
									
					for (int j = 0; j <=lastIndex; j++) { 
						List<ReviewReplyBean> beanlist = reviewreplydao.getReviewReply(reviewLists.get(j).getNum()); //�ش�Num������ �󸮽�Ʈ�� �����
						//System.out.println("before) "+j+"���� beanlist ������:"+beanlist.size());//�������						
						reviewLists.get(j).setReviewRelpyBeanList(beanlist);//���� ���Ͱ� 					
						//System.out.println("after) "+j+"���� beanlist ������:"+reviewLists.get(j).getReviewRelpyBeanList().size());											
						int forNum=reviewLists.get(j).getNum();
						String forPageNum=Integer.toString(i);											
						//System.err.println("request.getContextPath()= "+request.getContextPath());
						
						if(j%3==0) {
						writer.println("<tr>");
						}
						
						writer.println("<td>");
						
						if(reviewLists.get(j).getReviewLikeBean().size()>=2) {
						writer.println("<span style='float:left; color:red;'>");
						//writer.println("�������ѹ�:"+forPageNum);
						writer.println("HOT");
						writer.println("</span>");
						}
								
						String a="<input style=\"float:right;\" class=\"btn btn-link btn-xs\" type=\"button\" value=\"X\" onclick=\"deleteFunction('";			
						String b=forNum+"','";
						String c=forPageNum+"','";
						String d=pageSize+"','";
						String e=whatColumn+"','";
						String f=keyword+"')\">";
						String str=a+b+c+d+e+f;
						System.out.println(str);	
						
						if(session.getAttribute("loginfo")!=null) {
							
							if(((MemberBean)session.getAttribute("loginfo")).getUserid().equals("admin") ||((MemberBean)session.getAttribute("loginfo")).getUserid().equals(Writer) ) {
								writer.println(str);//�ۻ��� input��������
							}													
						}				
						//writer.println("<a href='ReviewDetail.bd?num="+forNum+"&pageNumber="+forPageNum+"&pageSize="+pageSize+"&whatColumn="+whatColumn+"&keyword="+keyword+"'>");
						
						writer.println("<img class=\"img-thumbnail\" src='"		
								+request.getContextPath()+"/resources"+"/image"+"/board"
								+(String) File.separator + reviewLists.get(j).getImage()+"'"
								+ " alt='"+reviewLists.get(j).getImage()+"' style='height:300px; width:370px; display:block;' onclick=\"detailPopup('"+forNum+"','"+forPageNum+"','"+pageSize+"','"+whatColumn+"','"+keyword+"');\">");						   
					   //writer.println("</a>");					  					   
					   writer.println("</td>");						   
						if(j%3==2||j==lastIndex) {
						writer.println("</tr>");						
						}																		
					}//���� ����for��			
			}//����ٱ��� for��	
			

			System.out.println("����Ʈ ��Ʈ�ѷ� �Ϸ��� �����������������ϴ� �������ѹ�: "+intPageNumber);
			
			
			writer.println("<input type=\"hidden\" id=\"searchedWhatColumn\" value=\""+whatColumn+"\">");
			writer.println("<input type=\"hidden\" id=\"searchedKeyword\" value=\""+keyword+"\">");
			writer.println("<input type=\"hidden\" id=\"moreViewPageSize\" value=\""+"3"+"\">");
			writer.println("<input type=\"hidden\" id=\"currentPageNumber\" value=\""+intPageNumber+"\">");
			writer.println("<input type=\"hidden\" id=\"totalPage\" value=\""+totalPage+"\">");
			writer.println("</table>");		
			writer.flush();	

	}
	
	
}
