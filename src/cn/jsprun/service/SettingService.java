package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.SettingsDao;
import cn.jsprun.domain.Settings;
import cn.jsprun.utils.BeanFactory;
public class SettingService {
	public boolean updateSetting(Settings setting){
		return ((SettingsDao) BeanFactory.getBean("settingDao")).updateSetting(setting);
	}
	public Settings findBySettingvariable(String variable){
		return ((SettingsDao) BeanFactory.getBean("settingDao")).findBySettingvariable(variable);
	}
	public List<Settings> findSettingsAll(){
		return ((SettingsDao) BeanFactory.getBean("settingDao")).findSettingsAll();
	}
	public boolean saveSetting(Settings setting){
		return ((SettingsDao) BeanFactory.getBean("settingDao")).saveSetting(setting);
	}
	public boolean removeSetting(Settings setting){
		return ((SettingsDao) BeanFactory.getBean("settingDao")).removeSetting(setting);
	}
}
