package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.TemplatesDao;
import cn.jsprun.domain.Templates;
import cn.jsprun.utils.BeanFactory;
public class TemplateService {
	public boolean addTemplate(Templates template){
		return ((TemplatesDao) BeanFactory.getBean("templatesDao")).addTemplate(template);
	}
	public boolean removeTemplate(Templates template){
		return ((TemplatesDao) BeanFactory.getBean("templatesDao")).removeTemplate(template);
	}
	public boolean updateTemplate(Templates template){
		return ((TemplatesDao) BeanFactory.getBean("templatesDao")).updateTemplate(template);
	}
	public Templates findById(Short templateid){
		return ((TemplatesDao) BeanFactory.getBean("templatesDao")).findById(templateid);
	}
	public List<Templates> findByProperty(String propertyName,Object value){
		return ((TemplatesDao) BeanFactory.getBean("templatesDao")).findByProperty(propertyName, value);
	}
	public List<Templates> findAll(){
		return ((TemplatesDao) BeanFactory.getBean("templatesDao")).findAll();
	}
}
