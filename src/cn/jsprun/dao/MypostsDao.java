package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Myposts;
public interface MypostsDao {
	public boolean deleteMypostByUid(int uid);
	public boolean insertMypost(Myposts mypost);
	public List<Myposts> findMypostByUid(int uid);
	public void deleteMyposts(Integer tid);
}
