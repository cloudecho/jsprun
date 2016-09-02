package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Tags;
public interface TagsDao {
	public Integer deleteArray(List<String> deleteList);
	public Integer updateToClosedTags(List<String> closedList);
	public Integer updateToOpenTags(List<String> openList);
	public List<Tags> findTagsByHql(String hql,int maxrow);
	public boolean updateTags(Tags tags);
	public boolean deleteTags(Tags tags);
	public Tags findTagsByName(String name);
}
