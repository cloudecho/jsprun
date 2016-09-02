package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Projects;
public interface ProjectsDao {
	public boolean addProject(Projects project);
	public boolean removeProject(Projects project);
	public boolean updateProject(Projects project);
	public Projects findById(Short id);
	public List<Projects> findByType(String type);
	public List<Projects> findByProperty(String propertyName,Object value);
	public List<Projects> findAll();
	public void saveProject(Projects project);
}
