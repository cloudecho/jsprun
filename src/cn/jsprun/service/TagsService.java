package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.TagsDao;
import cn.jsprun.dao.ThreadtagDao;
import cn.jsprun.domain.Tags;
import cn.jsprun.domain.Threadtags;
import cn.jsprun.utils.BeanFactory;
public class TagsService {
	public Integer deleteArray(List<String> deleteList) {
		if (deleteList != null) {
			return ((TagsDao)BeanFactory.getBean("tagDao")).deleteArray(deleteList);
		}
		return -1;
	}
	public Integer updateToClosedTags(List<String> closedList) {
		if(closedList != null){
			return ((TagsDao)BeanFactory.getBean("tagDao")).updateToClosedTags(closedList);
		}
		return -1;
	}
	public Integer updateToOpenTags(List<String> openList) {
			if(openList != null){
				return ((TagsDao)BeanFactory.getBean("tagDao")).updateToOpenTags(openList);
			}
		return -1;
	}
	public List<Threadtags> findThreadtagsByTagname(String tagname,int start,int max){
		ThreadtagDao threadtagDao = (ThreadtagDao) BeanFactory	.getBean("threadtagDao");
		return threadtagDao.findThreadtagsByTagname(tagname, start, max);
	}
	public boolean deleteThreadtags(Threadtags threadtags){
		ThreadtagDao threadtagDao = (ThreadtagDao) BeanFactory	.getBean("threadtagDao");
		return threadtagDao.deleteThreadtags(threadtags);
	}
	public List<Tags> findTagsByHql(String hql,int maxrow){
		return ((TagsDao)BeanFactory.getBean("tagDao")).findTagsByHql(hql, maxrow);
	}
	public boolean updateTags(Tags tags){
		return ((TagsDao)BeanFactory.getBean("tagDao")).updateTags(tags);
	}
	public boolean deleteTags(Tags tags){
		return ((TagsDao)BeanFactory.getBean("tagDao")).deleteTags(tags);
	}
	public int findThreadtagsCountByTagname(String tagname){
		return ((ThreadtagDao) BeanFactory.getBean("threadtagDao")).findThreadtagsCountByTagname(tagname);
	}
	public Tags findTagsByName(String name){
		return ((TagsDao)BeanFactory.getBean("tagDao")).findTagsByName(name);
	}
}
