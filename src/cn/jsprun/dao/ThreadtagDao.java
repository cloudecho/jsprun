package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Threadtags;
public interface ThreadtagDao {
	public List<Threadtags> findThreadtagsByTagname(String tagname,int start,int max);
	public boolean deleteThreadtags(Threadtags threadtags);
	public int findThreadtagsCountByTagname(String tagname);
}
