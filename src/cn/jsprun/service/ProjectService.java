package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.ProjectsDao;
import cn.jsprun.domain.Projects;
import cn.jsprun.utils.BeanFactory;
public class ProjectService {
	public boolean addProject(Projects project){
		return ((ProjectsDao) BeanFactory.getBean("projectsDao")).addProject(project);
	}
	public boolean removeProject(Projects project){
		return ((ProjectsDao) BeanFactory.getBean("projectsDao")).removeProject(project);
	}
	public boolean updateProject(Projects project){
		return ((ProjectsDao) BeanFactory.getBean("projectsDao")).updateProject(project);
	}
	public Projects findById(Short id){
		return ((ProjectsDao) BeanFactory.getBean("projectsDao")).findById(id);
	}
	public List<Projects> findByType(String type) {
		return ((ProjectsDao) BeanFactory.getBean("projectsDao")).findByType(type);
	}
	public List<Projects> findByProperty(String propertyName, Object value) {
		return ((ProjectsDao) BeanFactory.getBean("projectsDao")).findByProperty(propertyName, value);
	}
	public List<Projects> findAll(){
		return ((ProjectsDao) BeanFactory.getBean("projectsDao")).findAll();
	}
}
