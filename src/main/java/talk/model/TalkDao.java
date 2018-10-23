package talk.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TalkDao {

	final String namespace="alias.mybatis.talkmapper";
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public TalkDao() {
		
	}
	
	
	public List<TalkBean> selectListTalk(String userid){
		Map<String, String> map=new HashMap<String, String>();
		map.put("userid", userid);
		
		List<TalkBean> list=sqlSessionTemplate.selectList(namespace+".selectListTalk",map);

		return list;
	}
	
	public void insertTalk(TalkBean bean) {
		sqlSessionTemplate.insert(namespace+".insertData",bean);
		
	}
	
	public List<TalkBean> viewNewTalk(){
		List<String> talkerList=sqlSessionTemplate.selectList(namespace+".selectRoomHoner");
		
		
		List<TalkBean> newTalkList=new ArrayList<TalkBean>();
		if(talkerList.size()!=0) {
		for(int i=0;i<talkerList.size();i++) {
			String roomhoner=talkerList.get(i);
			TalkBean bean=sqlSessionTemplate.selectOne(namespace+".selectNewTalk",roomhoner);
			
			if(bean!=null) {
			newTalkList.add(bean);
			}
			
		}
		}
		
		
		return newTalkList;
	}
	public List<TalkBean> newtalklist(String roomhoner){
		Map<String, String> map=new HashMap<String, String>();
		
		map.put("roomhoner", roomhoner);
		List<TalkBean> list=sqlSessionTemplate.selectList(namespace+".newtalklist",map);
		return list;
	}

	
}
