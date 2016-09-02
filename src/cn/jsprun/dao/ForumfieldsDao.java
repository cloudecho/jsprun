package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Forumfields;
public interface ForumfieldsDao {
	public boolean addForumfield(Forumfields forumfield);
	public boolean removeForumfield(Forumfields forumfield);
	public boolean updateForumfield(Forumfields forumfield);
	public Forumfields findById(Short fid);
	public List<Forumfields> findAll();
	public List<Forumfields> findById(List<Short> id);
	public void updateForumfields(List<Forumfields> forumsfUpdateList);
}
