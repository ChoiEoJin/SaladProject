package board.model;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("myReviewReplyDao")
public class ReviewReplyDao {

	private final String namespace="board.model.ReviewReplyBean";
	
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;


	public ReviewReplyDao() {
	
	}
	
	
	public int insertReviewReply(ReviewReplyBean ReviewReplyBean) {
		int cnt=-1;
		cnt=sqlSessionTemplate.insert(namespace+".insertReviewReply",ReviewReplyBean);
		return cnt;
	}
	
	public List<ReviewReplyBean> getReviewReply(int num) {		
		List<ReviewReplyBean> lists=new ArrayList<ReviewReplyBean>();
		lists=sqlSessionTemplate.selectList(namespace+".getReviewReply",num);//where num에 해당하는 모든 댓글들을 리스트로 묶어온다.
		return lists;
	}
	
	
	
}
