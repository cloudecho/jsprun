package cn.jsprun.dao;
import java.util.ArrayList;
import java.util.List;
import cn.jsprun.domain.Projects;
import cn.jsprun.domain.Settings;
public interface CreditsSetDao {
	public void creditsSettings(ArrayList<Settings> setList);
	public Projects findById(Short id);
	public List<Projects> findProjects();
	public Settings findExtcredit();
	public Settings getSetting(String variable);
	public void creditsUpdate(Settings set);
}
