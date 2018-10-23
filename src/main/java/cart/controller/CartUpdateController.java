package cart.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cart.model.cartDao;

@Controller
public class CartUpdateController {
	
	@Autowired
	cartDao cartDao;
	
	@RequestMapping(value="cartDelete.cart")
	public void cartDelete(@RequestParam("pmkey") String pmkey,
			HttpServletResponse response,
			HttpServletRequest request) {
		
		cartDao.deleteCart(pmkey);
		response.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('해당 장바구니에서 제거되었습니다');");
			out.println("location.href='"+request.getContextPath()+"/viewCartList.cart';");
			out.println("</script>");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="cartUpdate.cart")
	public String cartUpdate(HttpServletRequest request) {
		String cartnum=request.getParameter("cartnum");
		String qty=request.getParameter("qty");
		cartDao.updateCart(cartnum,qty);
		return "redirect:viewCartList.cart";
	}
	
}
