package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Onlinelist;
import cn.jsprun.domain.Usergroups;
public interface OnLineListDao {
	public void addOnlinelist(List<Onlinelist> list);
	public List<Usergroups> queryAllSystemUserGroup();
	public List<Onlinelist> queryAllOnlineList();
	public boolean updateOlList(List<Onlinelist> olBeanList);
	public boolean updateUserGroup(List<Usergroups> ugList);
	public Usergroups queryUserGroupById(Short id);
	public boolean delAllOlList();
}
