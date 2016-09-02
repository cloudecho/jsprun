package cn.jsprun.dao;
import cn.jsprun.domain.Pmsearchindex;
public interface PmsearchindexDao {
	public int insertPmsearchindex(Pmsearchindex pmssearchindex);
	public boolean deletePmsearchindex(Pmsearchindex pmssearchindex);
	public Pmsearchindex findPmssearchindexByHql(String hql);
	public Pmsearchindex findPmsearchindexById(int searchid);
}
