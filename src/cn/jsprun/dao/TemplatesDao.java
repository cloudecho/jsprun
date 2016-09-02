package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Templates;
public interface TemplatesDao {
	public boolean addTemplate(Templates template);
	public boolean removeTemplate(Templates template);
	public boolean updateTemplate(Templates template);
	public Templates findById(Short templateid);
	public List<Templates> findByProperty(String propertyName,Object value);
	public List<Templates> findAll();
}
