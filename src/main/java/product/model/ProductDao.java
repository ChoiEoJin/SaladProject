package product.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utility.Paging;

@Component("myProductDao")
public class ProductDao {

	
	public final String namespace = "product.model.ProductBean";


	@Autowired
	SqlSessionTemplate sqlSessionTemplate; 

	public ProductDao() { }

	
	public int GetTotalCount( Map<String, String> map ) {
		int cnt = -1;
		cnt = sqlSessionTemplate.selectOne(namespace + ".GetTotalCount", map);
		return cnt;
	}
	
	
	
	public List<ProductBean> GetDataList( Paging pageInfo,  Map<String, String> map ) {		//list
		List<ProductBean> lists = new ArrayList<ProductBean>();
		RowBounds rowBounds = new RowBounds(pageInfo.getOffset(), pageInfo.getLimit() );
		lists = sqlSessionTemplate.selectList(namespace + ".GetDataList", map, rowBounds);
		return lists;
	}
	
	
	public Integer InsertData(ProductBean bean) {		//insert
		Integer cnt = -1;
		cnt = sqlSessionTemplate.insert(namespace + ".InsertData", bean);
		return cnt;
	}


	public ProductBean GetData(int num) {
		ProductBean bean = null;
		bean = sqlSessionTemplate.selectOne(namespace + ".GetData",	num);
		return bean;
	}
	

	public Integer UpdateData(ProductBean bean) { 
		Integer cnt = -1;
		cnt = sqlSessionTemplate.update(namespace + ".UpdateData", bean);
		return cnt;
	}
	

	public int DeleteData(int num) {
		int cnt = -1;
		cnt = sqlSessionTemplate.delete(namespace + ".DeleteData", num);
		return cnt;
	}
	
	
/*	public Integer UpdateData(Integer pnum) {            //   ,Integer qty
		ProductBean bean = new ProductBean() ;
		bean.setNum( pnum );
		//bean.setStock( qty );
		
		Integer cnt = -1;
		cnt = sqlSessionTemplate.update(namespace + ".UpdateData2", bean);
		return cnt;
	}*/
	

	public List<ProductBean> DisplayVegetable(){
		List<ProductBean> list = sqlSessionTemplate.selectList(namespace + ".DisplayVegetable");
		return list;
	}
	public List<ProductBean> DisplayFruit(){
		List<ProductBean> list = sqlSessionTemplate.selectList(namespace + ".DisplayFruit");
		return list;
	}
	public List<ProductBean> DisplayTopping(){
		List<ProductBean> list = sqlSessionTemplate.selectList(namespace + ".DisplayTopping");
		return list;
	}
	public List<ProductBean> DisplayDressing(){
		List<ProductBean> list = sqlSessionTemplate.selectList(namespace + ".DisplayDressing");
		return list;
	}
	public List<ProductBean> DisplaySideMenu(){
		List<ProductBean> list = sqlSessionTemplate.selectList(namespace + ".DisplaySideMenu2");
		return list;
	}
	public List<ProductBean> DisplayFinishedgoods(){
		List<ProductBean> list = sqlSessionTemplate.selectList(namespace + ".DisplayFinishedgoods");
		return list;
	}

}
