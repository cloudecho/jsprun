package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.AttachmentsDao;
import cn.jsprun.dao.BuddysDao;
import cn.jsprun.dao.FavoritesDao;
import cn.jsprun.dao.MemberSpaceDao;
import cn.jsprun.dao.PostsDao;
import cn.jsprun.dao.SessionsDao;
import cn.jsprun.dao.SettingsDao;
import cn.jsprun.dao.TradesDao;
import cn.jsprun.domain.Attachments;
import cn.jsprun.domain.Buddys;
import cn.jsprun.domain.Favorites;
import cn.jsprun.domain.Memberspaces;
import cn.jsprun.domain.Posts;
import cn.jsprun.domain.Settings;
import cn.jsprun.domain.Trades;
import cn.jsprun.utils.BeanFactory;
public class SpaceService {
	public List<Favorites> findFavoritesByHql(String hql, int startrow,
			int maxlength) {
		return ((FavoritesDao) BeanFactory.getBean("favoritesDao")).findFavoritesByHql(hql, startrow, maxlength);
	}
	public Settings findBySettingvariable(String variable) {
		return ((SettingsDao) BeanFactory .getBean("settingDao")).findBySettingvariable(variable);
	}
	public boolean addMemberSpace(Memberspaces memberspace) {
		return ((MemberSpaceDao) BeanFactory .getBean("memberspaceDao")).addMemberSpace(memberspace);
	}
	public boolean modifyMemberspace(Memberspaces memberspace) {
		return ((MemberSpaceDao) BeanFactory .getBean("memberspaceDao")).modifyMemberspace(memberspace);
	}
	public boolean deleteMemberspace(Memberspaces memberspace) {
		return ((MemberSpaceDao) BeanFactory .getBean("memberspaceDao")).deleteMemberspace(memberspace);
	}
	public Memberspaces findMemberspace(int uid) {
		return ((MemberSpaceDao) BeanFactory .getBean("memberspaceDao")).findMemberspace(uid);
	}
	public Posts findPostByThreadId(int tid) {
		return ((PostsDao) BeanFactory.getBean("postsDao")).findPostByThreadId(tid);
	}
	public boolean findSessionByUid(int uid) {
		return ((SessionsDao) BeanFactory .getBean("sessionDao")).findSessionByUid(uid);
	}
	public List<Buddys> findBuddysByUid(int uid) {
		return ((BuddysDao) BeanFactory.getBean("buddysDao")).findBuddysByUid(uid);
	}
	public int findPostCount() {
		return ((PostsDao) BeanFactory.getBean("postsDao")).findPostCount();
	}
	public List<Trades> findTradesByHql(String hql) {
		return ((TradesDao) BeanFactory.getBean("tradesDao")).findTradesByHql(hql);
	}
	public Attachments findAttachmentsById(int aid) {
		return ((AttachmentsDao) BeanFactory .getBean("attachmentsDao")).findAttachmentsById(aid);
	}
	public int findFavoritesCountByHql(String hql){
		return ((FavoritesDao) BeanFactory.getBean("favoritesDao")).findFavoritesCountByHql(hql);
	}
}
