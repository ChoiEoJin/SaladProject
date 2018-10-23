package pay.model;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cart.model.cartBean;
import utility.payPaging;

@Component
public class PayDao {
	@Autowired
	SqlSessionTemplate sqlSessiontemplate;
	private final String namespace="alias.mybatis.paymapper";
	
	
	public void InsertPay(String totalpaylist,String userid,String paymet) {
		
		StringTokenizer stk=new StringTokenizer(totalpaylist, ",");
		String payPname="";
		String totalPname="";
		int totalPrice=0;
		while(stk.hasMoreTokens()) {
			String cartnum=stk.nextToken();
			cartBean bean=sqlSessiontemplate.selectOne(namespace+".getBean",cartnum);
			int multiplePrice=bean.getMultipleprice();
			String Pname=bean.getPname();
			totalPname+=Pname+"("+bean.getQty()+")"+"/";
			totalPrice+=multiplePrice;
		}
		
		
		//주문번호 만들기(날짜+난수코드)
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmm");
		String sysdate3=sdf.format(ts);
		System.out.println("sysdate3: "+sysdate3);
		int randomcode=new Random().nextInt(10000)+1000;
		
		//pay 테이블에 인서트
		Map<String, String> map=new HashMap<String, String>();
		map.put("paynum", sysdate3+randomcode);
		map.put("payname", totalPname);
		map.put("payprice", String.valueOf(totalPrice));
		map.put("paywho", userid);
		map.put("paymet", paymet);
		sqlSessiontemplate.insert(namespace+".insertPay",map);
		
		
		//카트에 있던 거 지우기
		StringTokenizer stk3=new StringTokenizer(totalpaylist, ",");
		while(stk3.hasMoreTokens()) {
			String cartnum=stk3.nextToken();
			sqlSessiontemplate.delete(namespace+".deleteCart",cartnum);
		}
		
		
	}
	
	public List<PayBean> getListByUserid(String userid){
		
		List<PayBean> list=sqlSessiontemplate.selectList(namespace+".ListByUserid",userid);
		return list;
	}
	
	public List<PayBean> getListForAdmin(payPaging payPaging,Map<String, String> map){
		RowBounds rowbounds=new RowBounds(payPaging.getOffset(),payPaging.getLimit());
		List<PayBean> list=sqlSessiontemplate.selectList(namespace+".payListForAdmin",map,rowbounds);
		return list;
	}
	
	public int getTotalCount(Map<String, String> map) {
		int cnt=0;
		cnt=sqlSessiontemplate.selectOne(namespace+".getTotalCount",map);
		return cnt;
	}
	
	public String beforeMonthSales() {
		String month=sqlSessiontemplate.selectOne(namespace+".beforeMonth");
		System.out.println("month: "+month);
		month=month+"%";
		String beforeMonthSales=sqlSessiontemplate.selectOne(namespace+".beforeMonthSales",month);

		return beforeMonthSales;
	}
	
	public String todaySales() {
		String today=sqlSessiontemplate.selectOne(namespace+".gettoday");
		today=today+"%";
		System.out.println("today: "+today);
		String todaySales=sqlSessiontemplate.selectOne(namespace+".todaySales",today);
		
		return todaySales;
	}
	
	public List<String> productList(){
		List<String> list=sqlSessiontemplate.selectList("product.model.ProductBean"+".makePnameList");
		return list;
	}
	
	public ArrayList<String> SalesCount(List<String> list) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		
		for(int i=0;i<list.size();i++) {
			String str=list.get(i);
			String str3="%"+str+"%";
			int cnt=sqlSessiontemplate.selectOne(namespace+".countSales",str3);
			map.put(str, cnt);
		}
		
		ArrayList<String> alist=new ArrayList<String>();
		for(int i=1;i<=5;i++) {
			Set<String> set=map.keySet();
			String name="";
			int count=0;
			for(String st:set) {
				int qty=map.get(st);
				if(qty>count) {
					count=qty;
					name=st;
				}
			}
			map.remove(name);
			alist.add(name+"("+count+")회");
		}
		
		return alist;
		
	}
	
	public String yesterdaySales() {
		String yesterday=sqlSessiontemplate.selectOne(namespace+".getYesterday");
		yesterday=yesterday+"%";
		String yesterdaySales=sqlSessiontemplate.selectOne(namespace+".yesterdaySales",yesterday);
		return yesterdaySales;
	}
	
	public String weekSales() {
		String lastWeek=sqlSessiontemplate.selectOne(namespace+".getLastWeek");
		System.out.println(lastWeek);
		String weekSales=sqlSessiontemplate.selectOne(namespace+".weekSales",lastWeek);
		return weekSales;
	}
	
	public int personCount() {
		int cnt=sqlSessiontemplate.selectOne(namespace+".personCount");
		return cnt;
	}
	
	public int AlltotalPrice() {
		String AlltotalPrice=sqlSessiontemplate.selectOne(namespace+".AlltotalPrice");
		if(AlltotalPrice==null) {
			AlltotalPrice="0";
		}
		int totalPrice=Integer.parseInt(AlltotalPrice);
		return totalPrice;
	}
	
	public String monthSales() {
		String month=sqlSessiontemplate.selectOne(namespace+".getMonth");
		month=month+"%";
		String monthSales=sqlSessiontemplate.selectOne(namespace+".monthSales",month);
		return monthSales;
	}
	
	public String beforeYesterdaySales() {
		String beforeYesterday=sqlSessiontemplate.selectOne(namespace+".getBeforeYesterday");
		beforeYesterday=beforeYesterday+"%";
		String beforeYesterdaySales=sqlSessiontemplate.selectOne(namespace+".beforeYesterdaySales",beforeYesterday);
		return beforeYesterdaySales;
		
	}
	
	public ArrayList<String> chart(ArrayList<String> list) {
		ArrayList<String> list2=new ArrayList<String>();
		for(int i=0;i<list.size();i++) {
			String day=list.get(i)+"%";
			String str=sqlSessiontemplate.selectOne(namespace+".PriceForchart",day);
			if(str==null) {
				str="0";
			}
			String str2=list.get(i)+","+str;
			list2.add(str2);		
		}
		
		return list2;
	}
	
	public int getTotalCount2() {
		int cnt=0;
		cnt=sqlSessiontemplate.selectOne(namespace+".getTotalCount2");
		return cnt;
	}
	
	public double getavgAge(int totalcount2) {
		double avgAge=0.0;
		avgAge=sqlSessiontemplate.selectOne(namespace+".getavgAge",totalcount2);
		avgAge=Double.parseDouble(String.format("%.2f", avgAge));
		return avgAge;
	}
	
	public double getSungbi(int totalCount2) {
		int manCount=sqlSessiontemplate.selectOne(namespace+".getSungbi");
		double sungbi=manCount/(totalCount2*1.00);
		double sungbi2=Double.parseDouble(String.format("%.2f",sungbi));

		return sungbi2;
	}
	
	public ArrayList<String> getAddress(int totalCount2) {
		ArrayList<String> list=new ArrayList<String>();
		Set<String> setlist=new HashSet<String>();
		List<String> addressList=sqlSessiontemplate.selectList(namespace+".getAddress");
		for(int i=0;i<addressList.size();i++) {
			String si=addressList.get(i);
			si=si.substring(0,2);
			setlist.add(si);
		}
		ArrayList<String> addressPerList=new ArrayList<String>();
		
		for(String i:setlist) {
			String addressName=i;
			String str=addressName+"%";
			addressBean bean=new addressBean();
			bean.setAddress1(str);
			bean.setTotalcount(totalCount2);
			int addressPer=sqlSessiontemplate.selectOne(namespace+".getAddressPer",bean);
			addressPerList.add(addressName+","+addressPer);
		}

		return addressPerList;
		
	}
	
	public ArrayList<String> getPayMet(int totalCount2) {
		ArrayList<String> list= new ArrayList<String>();
		List<String> paymetList=sqlSessiontemplate.selectList(namespace+".getPaymetList");
		for(int i=0;i<paymetList.size();i++) {
			String paymet=paymetList.get(i);
			addressBean bean=new addressBean();
			bean.setPaymet(paymet);
			bean.setTotalcount(totalCount2);
			int paymetPer=sqlSessiontemplate.selectOne(namespace+".getPayMetPer",bean);
			list.add(paymet+","+paymetPer);

		}
		
		return list;
	}
	
}
