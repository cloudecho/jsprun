package cn.jsprun.foreg.service;
import java.util.List;
import cn.jsprun.dao.SearchindexDao;
import cn.jsprun.dao.ThreadtypesDao;
import cn.jsprun.domain.Searchindex;
import cn.jsprun.domain.Threadtypes;
import cn.jsprun.utils.BeanFactory;
public class SearchService {
	public int insertSearchindex(Searchindex searchindex){
		return ((SearchindexDao)BeanFactory.getBean("searchindexDao")).insertSearchindex(searchindex);
	}
	public Searchindex findSearchindexById(int searchid){
		return ((SearchindexDao)BeanFactory.getBean("searchindexDao")).findSearchindexById(searchid);
	}
	public Searchindex findSearchindexByHql(String hql){
		return ((SearchindexDao)BeanFactory.getBean("searchindexDao")).findSearchindexByHql(hql);
	}
	public boolean deleteSearchindex(Searchindex searchindex){
		return ((SearchindexDao)BeanFactory.getBean("searchindexDao")).deleteSearchindex(searchindex);
	}
	public List<Threadtypes> findThreadtypeByHql(String hql){
		return ((ThreadtypesDao)BeanFactory.getBean("threadtypesDao")).findThreadtypeByHql(hql);
	}
	public int findseachindexcountbyHql(String hql){
		return ((SearchindexDao)BeanFactory.getBean("searchindexDao")).findseachindexcountbyHql(hql);
	}
}
