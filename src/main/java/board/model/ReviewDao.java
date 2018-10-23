package board.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utility.ReviewPaging;

@Component("myReviewDao")
public class ReviewDao {

	private final String namespace = "board.model.ReviewBean";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public ReviewDao() {

	}
	
	public int GetTotalCount(Map<String, String> map) {
		System.out.println(map.get("keyword").toString()+"의 길이: "+map.get("keyword").length());	
		int cnt=sqlSessionTemplate.selectOne(namespace+".GetTotalCount",map);
		return cnt;
	}
	
	public List<ReviewBean> getAllReviewList(ReviewPaging reviewPaging,Map<String, String> map){
		
		List<ReviewBean> reviewLists=new ArrayList<ReviewBean>();
		RowBounds rowBounds = new RowBounds(reviewPaging.getOffset(), reviewPaging.getLimit());
		reviewLists=sqlSessionTemplate.selectList(namespace+".getAllReviewList",map,rowBounds);	
		System.out.println("(ReviewDao에서 실행되었음) getAllReviewList 사이즈:"+reviewLists.size());
		return reviewLists;
		
		
	}
	
	public int insertReview(ReviewBean reviewBean) {
		int cnt=0;
		cnt=sqlSessionTemplate.insert(namespace+".insertReview",reviewBean);
		return cnt;
	}
	
	public void deleteReview(int num) {
		sqlSessionTemplate.delete(namespace+".deleteReview",num);
		
	}
	
	public ReviewBean getReviewBeanGetByNum(int num) {
		ReviewBean ReviewBean=sqlSessionTemplate.selectOne(namespace+".getReviewBeanGetByNum",num);
		return ReviewBean;
	}
	
	public List<ReviewBean> getNewData(int nextRowNumber){
		
		
		List<ReviewBean> newlist=sqlSessionTemplate.selectList(namespace+".getNewData",nextRowNumber);
		return newlist;
	}
	
	
	
}
