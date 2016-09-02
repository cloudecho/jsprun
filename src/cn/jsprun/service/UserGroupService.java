package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.OtherSetDao;
import cn.jsprun.dao.PmsDao;
import cn.jsprun.dao.ProjectsDao;
import cn.jsprun.dao.UserGroupDao;
import cn.jsprun.domain.Admingroups;
import cn.jsprun.domain.Medals;
import cn.jsprun.domain.Pms;
import cn.jsprun.domain.Projects;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.utils.BeanFactory;
public class UserGroupService {
	public List<Usergroups> findAdminGroups() {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).findAdminGroups();
	}
	public Admingroups findAdminGroupById(Short adminGid) {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).findAdminGroupById(adminGid);
	}
	public boolean deleteUserGroup(Short groupId) {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).deleteUserGroup(groupId);
	}
	public boolean insertUserGroup(Usergroups userGroup) {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).insertUserGroup(userGroup);
	}
	public boolean modifyAdminGroup(Admingroups adminGroup) {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).modifyAdminGroup(adminGroup);
	}
	public List<Usergroups> findAllGroups() {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).findAllGroups();
	}
	public Usergroups findUserGroupById(Short userGroupId) {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).findUserGroupById(userGroupId);
	}
	public List<Usergroups> getUsergroupList(List<Short> usergroupIdList){
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).getUsergroupsList(usergroupIdList);
	}
	public Usergroups findUserGroupByCredits(int credits) {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).findUserGroupByCredits(credits);
	}
	public boolean modifyUserGroup(Usergroups userGroup) {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).modifyUserGroup(userGroup);
	}
	public List<Usergroups> findUserGroupsByType(String type) {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).findUserGroupsByType(type);
	}
	public boolean insertProjects(Projects project) {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).insertProjects(project);
	}
	public boolean modifyProjects(Projects project) {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).modifyProjects(project);
	}
	public boolean deleteProjects(Projects project) {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).deleteProjects(project);
	}
	public List<Projects> findAllProject() {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).findAllProjects();
	}
	public Projects findProjectsById(Short projectId) {
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).findProjectsById(projectId);
	}
	public List<Projects> findProjectsByType(String type){
		return ((ProjectsDao)BeanFactory.getBean("projectsDao")).findByType(type);
	}
	public void insertPmsList(List<Pms> pmsList){
		((PmsDao) BeanFactory.getBean("pmsDao")).insertPmsList(pmsList);
	}
	public List<Usergroups> findUserGroupByAddMember(){
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).findUserGroupByAddMember();
	}
	public List<Usergroups> findUsergroupInCredits(int Credits,short groupid){
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).findUsergroupInCredits(Credits, groupid);
	}
	public boolean deleteUsergroups(Usergroups usergroup){
		return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).deleteUsergroups(usergroup);
	}
	 public Medals queryMedalById(short id){
		 return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).queryMedalById(id);
	 }
	 public List<Usergroups> findUsergropsByHql(String hql){
		 return ((UserGroupDao) BeanFactory.getBean("userGroupDao")).findUsergropsByHql(hql);
	 }
}
