package product.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import product.model.ProductBean;
import product.model.ProductDao;

@Controller
public class ProductInsertController implements ServletContextAware{
	private static final String getPage = "ProductInsertForm";
	private static final String gotoPage = "redirect:/list.prd";
	private static final String command = "/insert.prd";

	@Autowired
	@Qualifier("myProductDao")
	private ProductDao productDao;
	
	
	
    ServletContext servletContext; 
	
	@RequestMapping(value = command, method = RequestMethod.GET)
	public String doActionGet(	HttpSession session ) {
	
		
		
			return getPage;	
		
	}

	
	@RequestMapping(value = command, method = RequestMethod.POST)
	public ModelAndView insertgogo(
			@ModelAttribute("product") @Valid ProductBean product, 
			BindingResult bindingResult) {
		
		ModelAndView mav = new ModelAndView();
		
		String uploadPath=servletContext.getRealPath("/resources/image/product");   
		
		
		if (bindingResult.hasErrors()) {
			System.out.println("유효성 검사 오류입니다");
			mav.setViewName(getPage); // ProductInsertForm.jsp
			return mav;
		} 

		MultipartFile multi = product.getUpload();

		Integer cnt = -1;
		cnt = productDao.InsertData(product);
		
		if (cnt != -1) {
			mav.setViewName(gotoPage); // redirect:/list.prd

			File destination = new File(uploadPath + File.separator + multi.getOriginalFilename());
			
			try {
				multi.transferTo(destination);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {// 실패
			mav.setViewName(getPage); //ProductInsertForm.java

		}
		return mav;
	}

	@Override
	public void setServletContext(ServletContext context) {
		// TODO Auto-generated method stub
		servletContext=context;		
	}
}
















