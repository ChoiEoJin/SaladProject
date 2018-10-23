package product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import product.model.ProductBean;
import product.model.ProductDao;

@Controller
public class ProductDetailViewController { // ProductList.jsp에서 넘어옴
	private static final String getPage = "ProductDetailView";
	private static final String command = "detail.prd";	
	
	@Autowired
	@Qualifier("myProductDao")
	private ProductDao productDao;	
	
	@RequestMapping(value=command , method=RequestMethod.GET)
	public String GoDetail( @RequestParam(value="num",required=true) int num ,
			Model model){
		
		System.out.println("여기");
		ProductBean product =  productDao.GetData( num);
		model.addAttribute("product" , product);
		return getPage;
	}
	
	
	
}

