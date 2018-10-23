package cart.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import pay.model.PayDao;

@Component
public class cartDao {

	private final String namespace="alias.mybatis.cartmapper";
	
	@Autowired
	SqlSessionTemplate sqlSessiontemplate;
	
	public void insertCart(cartBean bean) {

		int cnt=sqlSessiontemplate.selectOne(namespace+".searchPname",bean);
		if(cnt==1) {
			sqlSessiontemplate.update(namespace+".qtyPlus",bean);
		}else {
			sqlSessiontemplate.selectOne(namespace+".insertCart",bean);
		}
		
	}
	
	public List<cartBean> getListbyUserid(String userid){
		List<cartBean> list=sqlSessiontemplate.selectList(namespace+".getListbyUserid",userid);
		return list;
	}
	
	public int getTotalPrice(String userid) {
		int totalprice=sqlSessiontemplate.selectOne(namespace+".getTotalPrice",userid);
		return totalprice;
	}
	
	public void deleteCart(String pmkey) {
		sqlSessiontemplate.delete(namespace+".deleteCart",pmkey);
	}
	
	public void updateCart(String cartnum,String qty) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		map.put("cartnum", Integer.parseInt(cartnum));
		map.put("qty", Integer.parseInt(qty));
		sqlSessiontemplate.update(namespace+".updateqty",map);
	}
	
	public int getTotalPay(String totalpaylist) {
		int totalpay=0;
		StringTokenizer stk=new StringTokenizer(totalpaylist, ",");
		while(stk.hasMoreTokens()) {
			String cartnum=stk.nextToken();
			int getPrice=sqlSessiontemplate.selectOne(namespace+".getTotalPay",cartnum);
			totalpay+=getPrice;
		}
		return totalpay;
		
	}
	
	
	
}
