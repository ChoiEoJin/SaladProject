package board.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utility.Paging;

@Component("myBoardDao")
public class BoardDao {

	private final String namespace = "board.model.BoardBean";

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public BoardDao() {

	}

	public int GetTotalCount(Map<String, String> map) {

		int cnt = -1;
		cnt = sqlSessionTemplate.selectOne(namespace + ".GetTotalCount", map);
		// Travel.TravelBean.GetTotalCount
		System.out.println("cnt:" + cnt);
		return cnt;

	}

	public List<BoardBean> GetBoardList(Paging pageInfo, Map<String, String> map) {

		List<BoardBean> lists = new ArrayList<BoardBean>();

		RowBounds rowBounds = new RowBounds(pageInfo.getOffset(), pageInfo.getLimit());
		// offset : 0, limit : 2

		lists = sqlSessionTemplate.selectList(namespace + ".GetBoardList", map, rowBounds);
		/*
		 * while(rs.next()){ String name = rs.getString("name")
		 * 
		 * bean add }
		 */
		System.out.println("lists.size() : " + lists.size());

		return lists;

	}
	
	
	
	public List<BoardBean> GetNoticeList(){
		List<BoardBean> lists = new ArrayList<BoardBean>();
		lists = sqlSessionTemplate.selectList(namespace + ".GetNoticeList");
		return lists;		
	}

	public int insertData(BoardBean board) {

		int cnt = -1;

		cnt = sqlSessionTemplate.insert(namespace + ".insertData", board);

		return cnt;

	}

	public BoardBean getData(int num) {

		sqlSessionTemplate.update(namespace + ".updateReadCount", num);
		BoardBean board = sqlSessionTemplate.selectOne(namespace + ".getData", num);

		return board;

	}

	public int deleteData(int num) {

		int cnt = -1;
		cnt = sqlSessionTemplate.delete(namespace + ".deleteData", num);
		return num;
	}

	public void updateData(BoardBean board) {

		sqlSessionTemplate.update(namespace + ".updateData", board);

	}

	public void insertReply(BoardBean board) {
		int ref = board.getRef();
		int restep = board.getRestep() + 1;
		int relevel = board.getRelevel() + 1;
		sqlSessionTemplate.update(namespace + ".restepUpdate", board);
		System.out.println("restep수정 완료");
		board.setRelevel(relevel);
		board.setRestep(restep);

		System.err.println(board.getContent());
		System.err.println(board.getRef());
		System.err.println(board.getRelevel());
		System.err.println(board.getRestep());

		sqlSessionTemplate.insert(namespace + ".insertReply", board);
		System.out.println("답글 인서트 완료");
	}

}
