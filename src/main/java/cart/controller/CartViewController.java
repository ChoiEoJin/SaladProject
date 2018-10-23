package cart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cart.model.cartBean;
import cart.model.cartDao;
import member.model.MemberBean;

@Controller
public class CartViewController {
	
	@Autowired
	cartDao cartDao;
	

	
	@RequestMapping(value="viewCartLoginCheck.cart")
	public void viewCartLoginCheck(HttpServletResponse response,
			HttpSession session,HttpServletRequest request) {
		response.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out=response.getWriter();
			if(session.getAttribute("loginfo")==null) {
			out.println("<script>");
			out.println("alert('로그인이 필요한 서비스입니다');");
			out.println("location.href='"+request.getContextPath()+"/loginForm.member';");
			out.println("</script>");
			
			}else {
			out.println("<script>");
			out.println("location.href='"+request.getContextPath()+"/viewCartList.cart';");
			out.println("</script>");
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	@RequestMapping(value="viewCartList.cart")
	public ModelAndView viewCartList(HttpSession session,HttpServletRequest request) {
		ModelAndView mav=new ModelAndView();
		
		String userid=((MemberBean)session.getAttribute("loginfo")).getUserid();
		List<cartBean> cartlist=cartDao.getListbyUserid(userid);
		if(cartlist.size()!=0) {
			int totalprice=cartDao.getTotalPrice(userid);
			mav.addObject("totalprice",totalprice);
		}
		
		mav.addObject("cartlist",cartlist);
		mav.setViewName("viewCartList");
		
			
		return mav;
	}
	
	@RequestMapping(value="getTotalPay")
	public void getTotalPay(HttpServletResponse response,HttpServletRequest request) {
		response.setContentType("UTF-8");
		String totalpaylist=request.getParameter("totalpaylist");
		int totalpay=0;
		if(totalpaylist!="") {
			
			totalpaylist=totalpaylist.substring(0,totalpaylist.length()-1);
			totalpay=cartDao.getTotalPay(totalpaylist);
			
		}
		
		try {
			PrintWriter out=response.getWriter();
			out.println(totalpay);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
