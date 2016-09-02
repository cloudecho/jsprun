package cn.jsprun.dao;
import cn.jsprun.domain.Sessions;
public interface SessionsDao {
	public boolean addSessions(Sessions sessions);
	public boolean deleteSessions(Sessions sessions);
	public boolean deleteSessionsBySid(String sid);
	public Sessions findSessionsBySid(String sid);
	public int findSessionsCountByType(boolean members);
	public boolean findSessionByUid(int uid);
}
