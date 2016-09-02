package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Pms;
public interface PmsDao {
	public void insertPmsList(List<Pms> pmsList);
	public void updatePms(Pms pms);
	public List<Pms> findPmsByMsgtoid(int touid);
	public List<Pms> findPmsByHql(String hql,int begin,int maxlength);
	public int findPmsCountByHql(String hql);
	public Pms findPmsBypmid(int pmid);
	public boolean deletePms(Pms pms);
}
