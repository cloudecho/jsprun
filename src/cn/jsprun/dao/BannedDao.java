package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Banned;
public interface BannedDao {
	public boolean insertBanned(Banned banned);
	public boolean deleteBanned(Banned banned);
	public List<Banned> findAllBanned();
	public Banned findById(Short id);
	public boolean modifyBanned(Banned banned);
}
