package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Bbcodes;
public interface BbcodesDao {
	public List<Bbcodes> findByAll();
	public Bbcodes findByID(Integer id);
	public boolean updateBbcodes(Bbcodes b);
	public Boolean saveBbcodes(Bbcodes bbcodes);
	public int deleteArray(String[] ids);
	public int updateTagArray(List<Bbcodes> list);
	public int updateIconArray(List<Bbcodes> list);
	public int updateAvailableArray(List<Bbcodes> list);
}
