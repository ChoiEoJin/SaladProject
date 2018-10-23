package cart.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cart.model.cartBean;
import cart.model.cartDao;
import member.model.MemberBean;

@Controller
public class CartInsertController {
	
	@Autowired
	cartDao cartDao;
	
	@RequestMapping(value="goodsAddCart.cart",method=RequestMethod.POST)
	public ModelAndView goodsAddCart(cartBean bean,HttpSession session) {
		ModelAndView mav=new ModelAndView();
		String userid=((MemberBean)session.getAttribute("loginfo")).getUserid();
		System.out.println("userid"+userid);
		bean.setUserid(userid);
		bean.setMultipleprice(bean.getPrice()*bean.getQty());
		
		cartDao.insertCart(bean);
		
		mav.setViewName("redirect:viewCartList.cart");
		return mav;
	}
	
	@RequestMapping(value="customSaladAddCart.cart",method=RequestMethod.POST)
	public ModelAndView customSaladAddCart(HttpServletRequest request,
			HttpSession session) {
		ModelAndView mav=new ModelAndView();
		
		System.out.println("카트컨트롤러들어옴");
		String totalorder=request.getParameter("totaldata");
		String priceStr=request.getParameter("price");
		String userid=((MemberBean)session.getAttribute("loginfo")).getUserid();
		
		cartBean bean=new cartBean();
		bean.setPnum("나만의샐러드");
		bean.setPname("나만의샐러드["+totalorder+"]");
		bean.setPrice(Integer.parseInt(priceStr));
		bean.setQty(1);
		bean.setUserid(userid);
		bean.setMultipleprice(bean.getPrice()*bean.getQty());
		
		cartDao.insertCart(bean);
		
		mav.setViewName("redirect:viewCartList.cart");
		return mav;
	}
	
}
