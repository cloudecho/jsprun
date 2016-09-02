package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Settings;
public interface SettingsDao {
	public boolean updateSetting(Settings setting);
	public Settings findBySettingvariable(String variable);
	public List<Settings> findSettingsAll();
	public boolean saveSetting(Settings setting);
	public boolean removeSetting(Settings setting);
	public List<Settings> findSettingsLikeVariable(String variable);
}
