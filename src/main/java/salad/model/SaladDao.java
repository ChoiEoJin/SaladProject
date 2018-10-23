package salad.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import product.model.ProductBean;

@Component
public class SaladDao {

	@Autowired
	SqlSessionTemplate sqlSessiontemplate;
	
	final String namespace="alias.mybatis.saladmapper";
	
	
public List<ProductBean> getVegetable(){
		List<ProductBean> list=sqlSessiontemplate.selectList(namespace+".getVegetable");
		return list;
	}

public List<ProductBean> getFruitList(){
	List<ProductBean> list=sqlSessiontemplate.selectList(namespace+".getFruitList");
	return list;
}

public List<ProductBean> getSideMenu(){
	List<ProductBean> list=sqlSessiontemplate.selectList(namespace+".getSideMenu");
	return list;
}

public List<ProductBean> getTopping(){
	List<ProductBean> list=sqlSessiontemplate.selectList(namespace+".getTopping");
	return list;
}
public List<ProductBean> getDressing(){
	List<ProductBean> list=sqlSessiontemplate.selectList(namespace+".getDressing");
	return list;
}


}
