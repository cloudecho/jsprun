package cn.jsprun.dao;
import java.util.List;
import java.util.Map;
import cn.jsprun.domain.Advertisements;
public interface AdvertisementsDao {
	public Advertisements queryAdvById(Integer id);
	public boolean addAdv(Advertisements adv);
	public boolean updateAdv(Advertisements adv);
	public boolean updateAdvs(List<Advertisements> advList);
	public boolean delAnyAdvs(String deleteIds[]);
	public Map queryAdvWithPagination(String sqlStatement,int firstResult,int maxResult,String countKey,String listKey);
}
