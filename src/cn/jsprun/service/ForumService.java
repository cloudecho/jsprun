package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.ForumsDao;
import cn.jsprun.domain.Forums;
import cn.jsprun.utils.BeanFactory;
public class ForumService {
	public boolean addForum(Forums forum){
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).addForum(forum);
	}
	public boolean removeForum(Forums forum){
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).removeForum(forum);
	}
	public boolean updateForum(Forums forum){
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).updateForum(forum);
	}
	public boolean updatePost(Short sourcefid,Short targetfid){
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).updatePost(sourcefid,targetfid);
	}
	public boolean updateThread(Short sourcefid,Short targetfid){
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).updateThread(sourcefid,targetfid);
	}
	public Forums findById(Short fid){
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).findById(fid);
	}
	public List<Forums> getForumsList(List<Short> fidList){
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).getForumsList(fidList);
	}
	public List<Forums> findByUp(Short fup){
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).findByUp(fup);
	}
	public List<Forums> findByType(String type){
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).findByType(type);
	}
	public List<Forums> findByProperty(String propertyName, Object value){
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).findByProperty(propertyName, value);
	}
	public List<Forums> findAll(){
		return ((ForumsDao) BeanFactory.getBean("forumsDao")).findAll();
	}
}
