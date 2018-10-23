package salad.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale.Category;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import product.model.ProductBean;
import product.model.ProductDao;
import salad.model.SaladDao;

@Controller
public class SaladController {

	@Autowired
	SaladDao saladDao;
	
	@RequestMapping(value="costomsalad.salad")
	public void goCustomSalad(HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		System.out.println("샐러드컨트롤러 들어옴");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			if(session.getAttribute("loginfo")==null) {
				System.out.println("로긴인포null입니다");
				out.println("<script>");
				out.println("alert('로그인 후에 이용해주세요');");
				out.println("location.href='"+request.getContextPath()+"/loginForm.member';");
				out.println("</script>");
				out.flush();
			}else {
				out.println("<script>");
				out.println("location.href='"+request.getContextPath()+"/gosaladCustom.salad';");
				out.println("</script>");
				out.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value="gosaladCustom.salad")
	public ModelAndView gosaladCuston() {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("saladCustom");
		
		List<ProductBean> vegetableList=saladDao.getVegetable();
		List<ProductBean> fruitList=saladDao.getFruitList();
		List<ProductBean> sidemenuList=saladDao.getSideMenu();
		List<ProductBean> toppingList=saladDao.getTopping();
		List<ProductBean> dressingList=saladDao.getDressing();
		
		mav.addObject("vegetableList",vegetableList);
		mav.addObject("fruitList",fruitList);
		mav.addObject("sidemenuList",sidemenuList);
		mav.addObject("toppingList",toppingList);
		mav.addObject("dressingList",dressingList);
		
		return mav;
	}
}
