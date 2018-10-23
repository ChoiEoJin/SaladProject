package product.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import product.model.ProductBean;
import product.model.ProductDao;

@Controller
public class ProductUpdateController implements ServletContextAware {  
	private static final String getPage = "ProductUpdateForm";
	private static final String gotoPage =  "redirect:/list.prd";
	private static final String command = "/update.prd";

	private ServletContext servletContext;

	@Autowired
	@Qualifier("myProductDao")
	private ProductDao productDao;


	// ProductList.jsp의 goUpdate에서 GET으로 넘어옴
	@RequestMapping(value=command , method=RequestMethod.GET)
	public String updatego( HttpSession session,
			@RequestParam(value="pmkey",required=true) int pmkey ,
			Model model){

			ProductBean product =  productDao.GetData( pmkey );
			model.addAttribute("product" , product);
			return getPage;
	
	}

	
	
	
	
	
	@RequestMapping(value=command , method=RequestMethod.POST)
	public ModelAndView updategogo(ProductBean product){		
		
		
		MultipartFile multi = product.getUpload();
		ModelAndView mav = new ModelAndView();
		System.out.println("여기");
		
		String uploadDir=servletContext.getRealPath("/resources/image/product");
		System.out.println("여기2");	
		
		productDao.UpdateData(product);	
		System.out.println("여기3");
		

			File destination = new File( uploadDir + File.separator +multi.getOriginalFilename() ); 
			File destination2 = new File( uploadDir + File.separator +product.getUpload2() ); 
			System.out.println("여기4");
			destination2.delete();
			System.out.println("여기5");
			
			try {
				multi.transferTo(destination);
				System.out.println("여기5-2");
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("여기6");
			mav.setViewName(gotoPage);
			
		
		System.out.println("여기7");
		return mav;

	}

	@Override
	public void setServletContext(ServletContext context) {
		// TODO Auto-generated method stub
		servletContext=context;		
	}
}
