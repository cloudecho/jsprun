package cn.jsprun.service;
import java.util.ArrayList;
import java.util.List;
import cn.jsprun.dao.CreditsSetDao;
import cn.jsprun.dao.ForumfieldsDao;
import cn.jsprun.dao.ForumsDao;
import cn.jsprun.dao.MembersDao;
import cn.jsprun.dao.ProjectsDao;
import cn.jsprun.dao.UserGroupDao;
import cn.jsprun.domain.Forumfields;
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Projects;
import cn.jsprun.domain.Settings;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.utils.BeanFactory;
public class CreditsSetService {
	public Settings getSetting(String variable){
		CreditsSetDao creD = (CreditsSetDao) BeanFactory.getBean("creditsSetDao");
		return creD.getSetting(variable);
	}
	public Settings toCredits()
	{
		CreditsSetDao creD = (CreditsSetDao) BeanFactory.getBean("creditsSetDao");
		return creD.findExtcredit();
	}
	public Projects findBuId(Short id)
	{
		CreditsSetDao creD = (CreditsSetDao) BeanFactory.getBean("creditsSetDao");
		return creD.findById(id);
	}
	public List<Projects> findProjects()
	{
		CreditsSetDao creD = (CreditsSetDao) BeanFactory.getBean("creditsSetDao");
		return creD.findProjects();
	}
	public void creditsSettings(ArrayList<Settings> setList)
	{
		CreditsSetDao creD = (CreditsSetDao) BeanFactory.getBean("creditsSetDao");
		creD.creditsSettings(setList);
	}
	public void creditsUpdate(Settings set)
	{
		CreditsSetDao creD = (CreditsSetDao) BeanFactory.getBean("creditsSetDao");
		creD.creditsUpdate(set);
	}
	public void saveProject(Projects project){
		ProjectsDao projectsDao = (ProjectsDao)BeanFactory.getBean("projectsDao");
		projectsDao.saveProject(project);
	}
	public void updateProject(Projects project){
		ProjectsDao projectsDao = (ProjectsDao)BeanFactory.getBean("projectsDao");
		projectsDao.updateProject(project);
	}
	public List<Forums>  findExceptFupEqualZero(){
		ForumsDao forumsDao = (ForumsDao)BeanFactory.getBean("forumsDao");
		return forumsDao.findExceptFupEqualZero();
	}
	public List<Forumfields> findForumfieldsById(List<Short> id){
		ForumfieldsDao forumfieldsDao = (ForumfieldsDao)BeanFactory.getBean("forumfieldsDao");
		return forumfieldsDao.findById(id);
	}
	public void updateForumfields(List<Forumfields> forumsfUpdateList){
		ForumfieldsDao forumfieldsDao = (ForumfieldsDao)BeanFactory.getBean("forumfieldsDao");
		forumfieldsDao.updateForumfields(forumsfUpdateList);
	}
	public List<Usergroups> getUsergroupsList(){
		UserGroupDao userGroupDao = (UserGroupDao)BeanFactory.getBean("userGroupDao");
		return userGroupDao.findAllGroups();
	}
	public void updateUsergroup(List<Usergroups> updateUsergroupList){
		UserGroupDao userGroupDao = (UserGroupDao)BeanFactory.getBean("userGroupDao");
		userGroupDao.updateUsergroups(updateUsergroupList);
	}
	public void resetUserCredits(String columeName,Integer resetValue,String creditsExpiression) throws Exception{
		MembersDao memberDao = (MembersDao)BeanFactory.getBean("memberDao");
		StringBuffer hqlBuffer = new StringBuffer("UPDATE Members SET "+columeName+"="+resetValue+" ");
		if(creditsExpiression.indexOf(columeName)>0){
			hqlBuffer.append(", credits="+creditsExpiression+" ");
		}
		memberDao.updateMembers(hqlBuffer.toString());
	}
}
