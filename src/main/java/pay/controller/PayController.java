package pay.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import member.model.MemberBean;
import pay.model.PayBean;
import pay.model.PayDao;
import utility.payPaging;

@Controller
public class PayController {

	@Autowired
	PayDao paydao;
	
	
	@RequestMapping("goPayFromCart.pay")
	public ModelAndView goPay(HttpServletRequest request,HttpSession session) {
		
		ModelAndView mav=new ModelAndView();
		String userid=((MemberBean)session.getAttribute("loginfo")).getUserid();
		String totalpaylist=request.getParameter("totalpaylist");
		String paymet=request.getParameter("paymet");
		totalpaylist=totalpaylist.substring(0,totalpaylist.length()-1);
		paydao.InsertPay(totalpaylist,userid,paymet);
		mav.setViewName("redirect:makePayList.pay");
		
		return mav;
	}
	
	@RequestMapping("goPayList.pay")
	public void payListLoginChk(HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		response.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out=response.getWriter();
			if(session.getAttribute("loginfo")==null) {
				out.println("<script>");
				out.println("alert('로그인이 필요합니다');");
				out.println("location.href='"+request.getContextPath()+"/loginForm.member';");
				out.println("</script>");
			}else {
				if(((MemberBean)session.getAttribute("loginfo")).getUserid().equals("admin")) {
					out.println("<script>");
					out.println("location.href='"+request.getContextPath()+"/makePayListForAdmin.pay';");
					out.println("</script>");
				}else {
					out.println("<script>");
					out.println("location.href='"+request.getContextPath()+"/makePayList.pay';");
					out.println("</script>");
				}
				
			}
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@RequestMapping(value="makePayList.pay")
	public ModelAndView makePayList(HttpSession session) {
		ModelAndView mav=new ModelAndView();
		String userid=((MemberBean)session.getAttribute("loginfo")).getUserid();
		List<PayBean> paylist=paydao.getListByUserid(userid);
		mav.addObject("paylist",paylist);
		mav.setViewName("viewPayList");
		return mav;
	}
	
	@RequestMapping(value="makePayListForAdmin.pay")
	public ModelAndView makePayListForAdmin(@RequestParam(value = "whatColumn", required = false) String whatColumn,
	         @RequestParam(value = "keyword", required = false) String keyword,
	         @RequestParam(value = "pageNumber", required = false) String pageNumber,
	         @RequestParam(value = "pageSize", required = false) String pageSize, HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		Map<String, String> map = new HashMap<String, String>();
		map.put("whatColumn", whatColumn);
	    if(whatColumn==null) {
          map.put("keyword", "%" + keyword + "%");   
       }else {      
          if(whatColumn.equals("paypname")) {
          map.put("keyword", "%" + keyword + "%"); }
          else {
             map.put("keyword",keyword.trim()); //작성자로 검색할때는 앞뒤공백없애고, 정확하게 
          }   
       }
		
	    System.out.println("keyword:"+map.get("keyword"));
	    System.out.println("whatColumn:"+map.get("whatColumn"));
	    
	    int totalCount=paydao.getTotalCount(map);
	    
		payPaging payPaging=new payPaging(pageNumber, pageSize, totalCount, "url", whatColumn, keyword, null);
		
		
		List<PayBean> paylist=paydao.getListForAdmin(payPaging,map);
		
		mav.addObject("paylist", paylist);
		mav.addObject("payPaging",payPaging);
		mav.setViewName("payListForAdmin");
		
		return mav;
	}
	
	@RequestMapping("SalesAnalytics.pay")
	public ModelAndView SalesAnalytics() {

		ModelAndView mav=new ModelAndView();
		
		String todaySales=this.todaySales();
		mav.addObject("todaySales",todaySales);
		
		String beforeMonthSales=this.beforeMonthSales();
		mav.addObject("beforeMonthSales",beforeMonthSales);
		
		ArrayList<String> bestSales=this.bestSales();
		mav.addObject("bestSales",bestSales);
		
		String yesterdaySales=this.yesterdaySales();
		mav.addObject("yesterdaySales",yesterdaySales);
		
		int divideWeekSales=this.weekSales();
		mav.addObject("divideWeekSales",divideWeekSales);
		
		int personByPrice=this.personByPrice();
		mav.addObject("personByPrice",personByPrice);
		
		String monthSales=this.monthSales();
		mav.addObject("monthSales",monthSales);
		
		String beforeYesterdaySales=this.beforeYesterdaySales();
		mav.addObject("beforeYesterdaySales",beforeYesterdaySales);
		
		mav.setViewName("salesAnalytics");
		return mav;
	}
	
	public String beforeMonthSales() {
		String beforeMonthSales=paydao.beforeMonthSales();
		System.out.println("beforeMonthSales: "+beforeMonthSales);
		if(beforeMonthSales==null) {
			beforeMonthSales="0";
					}
		return beforeMonthSales;
	}
	
	public String todaySales() {
		String todaySales=paydao.todaySales();
		if(todaySales==null) {
			todaySales="0";
		}
		return todaySales;
	}
	
	public ArrayList<String> bestSales(){
		List<String> list=paydao.productList();
		ArrayList<String> list2=paydao.SalesCount(list);
		return list2;
	}
	
	public String yesterdaySales() {
		String yesterdaySales=paydao.yesterdaySales();
		if(yesterdaySales==null) {
			yesterdaySales="0";
		}
		return yesterdaySales;
	}
	
	public int weekSales() {
		String weekSales=paydao.weekSales();
		if(weekSales==null) {
			weekSales="0";
		}
		int weekSales2=Integer.parseInt(weekSales);
		int divideWeekSales=weekSales2/7;
		
		System.out.println("divideWeekSales:"+divideWeekSales);
		return divideWeekSales;
	}
	
	public int personByPrice() {
		int personcount=paydao.personCount();
		int AlltotalPrice=paydao.AlltotalPrice();
		int personByPrice=0;
		if(personcount!=0) {
		personByPrice=AlltotalPrice/personcount;
		}
		return personByPrice;
	}
	
	public String monthSales() {
		String monthSales=paydao.monthSales();
		if(monthSales==null) {
			monthSales="0";
		}
		return monthSales;
	}
	
	public String beforeYesterdaySales() {
		String beforeYesterdaySales=paydao.beforeYesterdaySales();
		if(beforeYesterdaySales==null) {
			beforeYesterdaySales="0";
		}
		return beforeYesterdaySales;
	}


	
}