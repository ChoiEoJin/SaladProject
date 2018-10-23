package member.model;


import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MemberDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	private final String namespace="alias.mybatis.membermapper";
	
	public void insertMember(MemberBean member) {
		sqlSessionTemplate.insert(namespace+".insertMember",member);
	}
	
	public int login(String userid,String userpw) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("userid", userid);
		map.put("userpw", userpw);
		System.out.println(userid+userpw);
	
		int cnt=sqlSessionTemplate.selectOne(namespace+".login",map);
		System.out.println("cnt+"+cnt);
		return cnt;
	}
	
	public MemberBean getBean(String userid,String userpw) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("userid", userid);
		map.put("userpw", userpw);
		System.out.println(userid+","+userpw);
		MemberBean bean=sqlSessionTemplate.selectOne(namespace+".getBean",map);
		return bean;
		
	}
	
	public int emailchk(String userid,String userpw,String joincode) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("userid", userid);
		map.put("userpw", userpw);
		map.put("joincode", joincode);
		int cnt=sqlSessionTemplate.selectOne(namespace+".emailchk",map);
		return cnt;
		
	}
	
	public void emailChkResult(String userid,String userpw) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("userid", userid);
		map.put("userpw", userpw);
		sqlSessionTemplate.update(namespace+".emailchkResult",map);
		
	}
	
	public String idchk(String userid) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("userid", userid);
		String result="Exist";
		int cnt=sqlSessionTemplate.selectOne(namespace+".idchk",map);
		if(cnt==0) {
			result="NotExist";
		}
		return result;

	}
	
	public int searchpw(MemberBean member) {
		int cnt=sqlSessionTemplate.selectOne(namespace+".searchpw",member);
		return cnt;
		
	}
	
	public void updatePW(MemberBean member) {
		sqlSessionTemplate.update(namespace+".updatePW",member);
	}
	
	public int searchId(MemberBean bean) {
		int cnt=0;
		cnt=sqlSessionTemplate.selectOne(namespace+".searchID",bean);
		return cnt;
	}
	
	public String searchIdResult(MemberBean bean) {
		String userid=sqlSessionTemplate.selectOne(namespace+".searchIdResult",bean);
		return userid;
	}
	
	public void updateMember(MemberBean bean) {

		sqlSessionTemplate.update(namespace+".updateMember",bean);
	}
	
}
