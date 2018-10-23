package product.controller;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;

import product.model.ProductBean;
import product.model.ProductDao;

@Controller
public class ProductDeleteController implements ServletContextAware{
	
	private static final String gotoPage = "redirect:/list.prd";
	private static final String command = "delete.prd";
	
	private ServletContext servletContext;
	
	@Autowired
	@Qualifier("myProductDao")
	private ProductDao productDao;
	
	@RequestMapping(value=command, method=RequestMethod.GET)
	public String deletegogo(
				@RequestParam(value="num",required=true) int num){
		
		int cnt = -1;
		
		ProductBean product = productDao.GetData(num);
		
		String uploadDir = servletContext.getRealPath("/resources/image/product"); 
		File delFile = new File( uploadDir + File.separator +product.getImage() );
		cnt = productDao.DeleteData( num );
		delFile.delete();
		
		return gotoPage;
	}

	
	@Override
	public void setServletContext(ServletContext context) {
	
		servletContext=context;
	}

}
