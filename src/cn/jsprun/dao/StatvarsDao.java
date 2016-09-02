package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Statvars;
import cn.jsprun.domain.StatvarsId;
public interface StatvarsDao {
	public List<Statvars> getStatvarsByType(String type);
	public void updateStatvarsForMain(List<Statvars> statvarsList);
	public Statvars getStatvarsById(StatvarsId statvarsId);
	public void saveStatvars(Statvars statvars);
}
