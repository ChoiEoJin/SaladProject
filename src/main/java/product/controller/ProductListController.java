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
public class ProductListController {
	private static final String getPage = "ProductList";
	private static final String command = "/list.prd";

	@Autowired
	@Qualifier("myProductDao")
	private ProductDao productDao;
	
	@RequestMapping(value = command )
	public ModelAndView gogolist(
			@RequestParam(value = "whatColumn", required = false ) String whatColumn,
			@RequestParam(value = "keyword", required = false ) String keyword,
			@RequestParam(value = "pageNumber", required = false ) String pageNumber,
			@RequestParam(value = "pageSize", required = false ) String pageSize,
			HttpServletRequest request) {
		
		System.out.println("1234");
		Map<String, String> map = new HashMap<String, String>() ;	
		
		map.put("whatColumn", whatColumn ) ;
		map.put("keyword", "%" + keyword + "%" ) ;
		
		int totalCount = productDao.GetTotalCount( map );
		String url = request.getContextPath() + "/" + this.command ;
		
		ModelAndView mav = new ModelAndView();
		
		Paging pageInfo 
			= new Paging( pageNumber, pageSize, totalCount, url, whatColumn, keyword, null);
		
		
		List<ProductBean> productLists = productDao.GetDataList( pageInfo, map );
		
		mav.addObject( "productLists", productLists );		
		mav.addObject( "pageInfo", pageInfo );
		mav.setViewName(getPage);
		return mav;
	}
	
}