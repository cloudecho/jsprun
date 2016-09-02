package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Smilies;
public interface SmiliesDao {
	public List<Smilies> getIcons();
	public List<Smilies> getSmilies();
	public Integer deleteIcons(String[] ids);
	public Integer updateDisplayorderIcons(List<Smilies> list);
	public Integer deleteSmiliesIds(List<Short> list);
	public Integer updateSmiliesDisplayorderCode(List<Smilies> list);
	public Integer save(Smilies s);
	public boolean findSmiliesbytypeid(short typeid,String url);
	public List<Smilies> findSmiliesBytypeid(short typeid,int start,int max);
	public int findSmiliesCountBytypeId(short typeid);
	public Smilies findSmiliesById(short id);
	public boolean addSmilies(Smilies smilies);
}
