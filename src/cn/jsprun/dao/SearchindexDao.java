package cn.jsprun.dao;
import cn.jsprun.domain.Searchindex;
public interface SearchindexDao {
	public int insertSearchindex(Searchindex searchindex);
	public Searchindex findSearchindexById(int searchid);
	public Searchindex findSearchindexByHql(String hql);
	public boolean deleteSearchindex(Searchindex searchindex);
	public int findseachindexcountbyHql(String hql);
}
