package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Favorites;
public interface FavoritesDao {
	public List<Favorites> findFavoritesByUid(int uid);
	public boolean deleteFavoritesByUid(int uid);
	public boolean insertFavorites(Favorites favorite);
	public List<Favorites>findFavoritesByHql(String hql,int startrow,int maxlength);
	public boolean deleteFavoritesByTid(int tid);
	public boolean delteFavoritesByFid(short fid);
	public int findFavoritesCountByHql(String hql);
}
