package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.ForumfieldsDao;
import cn.jsprun.domain.Forumfields;
import cn.jsprun.utils.BeanFactory;
public class ForumfieldService {
	public boolean addForumfield(Forumfields forumfield){
		return ((ForumfieldsDao) BeanFactory.getBean("forumfieldsDao")).addForumfield(forumfield);
	}
	public boolean removeForumfield(Forumfields forumfield){
		return ((ForumfieldsDao) BeanFactory.getBean("forumfieldsDao")).removeForumfield(forumfield);
	}
	public boolean updateForumfield(Forumfields forumfield){
		return ((ForumfieldsDao) BeanFactory.getBean("forumfieldsDao")).updateForumfield(forumfield);
	}
	public Forumfields findById(Short fid){
		return ((ForumfieldsDao) BeanFactory.getBean("forumfieldsDao")).findById(fid);
	}
	public List<Forumfields> findAll(){
		return ((ForumfieldsDao) BeanFactory.getBean("forumfieldsDao")).findAll();
	}
}
