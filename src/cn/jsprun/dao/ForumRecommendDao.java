package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Forumrecommend;
public interface ForumRecommendDao {
	public List<Forumrecommend> getForumrecommendByTid(List<Integer> tidList);
	public void deleteForumrecommend(List<Integer> tidList);
	public void addForumrecommend(List<Forumrecommend> forumcommendList);
}
