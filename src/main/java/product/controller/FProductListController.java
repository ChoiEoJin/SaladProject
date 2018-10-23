package product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import product.model.ProductBean;
import product.model.ProductDao;
import utility.Paging;

@Controller
public class FProductListController {
	private static final String getPage = "Finishedgoods";
	private static final String command = "/flist.prd";

	@Autowired
	@Qualifier("myProductDao")
	private ProductDao ProductDao;
	
	@RequestMapping(value= command)
	public ModelAndView Publicprd() {
		
		
		//ProductDao dao = new ProductDao();
		ModelAndView mav=new ModelAndView();
		mav.setViewName("Finishedgoods");
		
		
		List<ProductBean> DisplayFinishedgoods = ProductDao.DisplayFinishedgoods();
		
		
		mav.addObject("DisplayFinishedgoods",DisplayFinishedgoods);
		
		/*System.out.println(DisplayVegetable.size());
		for(int i=0;i<DisplayVegetable.size();i++) {
			System.out.println(DisplayVegetable.get(i).getName());
		}
		*/
		
		//System.out.println(DisplaySideMenu2.size());
		return mav;
	}
	
}