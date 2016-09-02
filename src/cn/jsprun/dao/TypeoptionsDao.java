package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Typeoptions;
public interface TypeoptionsDao {
	public boolean addTypeoption(Typeoptions typeoption);
	public boolean removeTypeoption(Typeoptions typeoption);
	public boolean updateTypeoption(Typeoptions typeoption);
	public Typeoptions findById(Short optionid);
	public List<Typeoptions> findByClassId(Short classid);
	public Typeoptions findByIdentifier(String identifier);
	public List<Typeoptions> findByProperty(String propertyName, Object value);
	public List<Typeoptions> findAll();
}
