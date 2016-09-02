package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Creditslog;
public interface CreditslogDao {
	public boolean insertCreditslog(Creditslog creditslog);
	public boolean modifyCreditslog(Creditslog creditslog);
	public boolean deleteCreditslog(Creditslog creditslog);
	public List<Creditslog> findAllCreditslog();
	public List<Creditslog> findAllCreditslogByOperation(String []operation);
	public List<Creditslog> findCreditslogByKeys(String keyword);
	public List<Creditslog> findCreditslogByUid(int uid,int maxrow);
	public List<Creditslog> findCreditsLogByHql(String hql,int startrow,int maxrow);
	public int findCreditslogCountbyHql(String hql);
}
