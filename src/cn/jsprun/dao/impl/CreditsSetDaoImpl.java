package cn.jsprun.dao.impl;
import java.util.ArrayList;
import java.util.List;
import cn.jsprun.dao.CreditsSetDao;
import cn.jsprun.dao.ProjectsDao;
import cn.jsprun.dao.SettingsDao;
import cn.jsprun.domain.Projects;
import cn.jsprun.domain.Settings;
import cn.jsprun.utils.BeanFactory;
public class CreditsSetDaoImpl implements CreditsSetDao {
	public void creditsSettings(ArrayList<Settings> setList) {
		if(setList!=null)
		{
			SettingsDao setD=(SettingsDao) BeanFactory.getBean("settingDao");
			for(int i=0;i<setList.size();i++)
			{
				Settings set = setList.get(i);
				setD.updateSetting(set);
			}
			setD=null;
		}
	}
	public Projects findById(Short id) {
		return ((ProjectsDao) BeanFactory.getBean("projectsDao")).findById(id);
	}
	public List<Projects> findProjects() {
		return ((ProjectsDao) BeanFactory.getBean("projectsDao")).findByType("extcredit");
	}
	public Settings findExtcredit() {
		return ((SettingsDao) BeanFactory.getBean("settingDao")).findBySettingvariable("extcredits");
	}
	public Settings getSetting(String variable) {
		return ((SettingsDao) BeanFactory.getBean("settingDao")).findBySettingvariable(variable);
	}
	public void creditsUpdate(Settings set) {
		((SettingsDao) BeanFactory.getBean("settingDao")).updateSetting(set);
	}
}
