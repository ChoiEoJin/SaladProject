package board.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("myReviewLikeDao")
public class ReviewLikeDao {

private final String namespace="board.model.ReviewLikeBean";
	
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;


	public ReviewLikeDao() {
		super();
	}
	
	
	public List<ReviewLikeBean> getReviewLikeGetByNum(int num) {		
		List<ReviewLikeBean> lists=new ArrayList<ReviewLikeBean>();
		lists=sqlSessionTemplate.selectList(namespace+".getReviewLikeGetByNum",num);//where num에 해당하는 모든 댓글들을 리스트로 묶어온다.
		return lists;
	}
	
	public int check_whetherLoginId_doesLikethis(Map<String,String> map2) {
		int count=0;
		
		count=sqlSessionTemplate.selectOne(namespace+".check_whetherLoginId_doesLikethis",map2);
		
		return count;
	}
	
	public int insertReviewLike(Map<String,String> insertMap) {
		
		int count=0;
		count =sqlSessionTemplate.insert(namespace+".insertReviewLike",insertMap);	
		return count;
		
	}
	
	
	
public int deleteReviewLike(Map<String,String> deleteMap) {
		
		int count=0;
		count =sqlSessionTemplate.insert(namespace+".deleteReviewLike",deleteMap);	
		return count;
		
	}
	
	
}
