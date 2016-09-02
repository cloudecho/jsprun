package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Styles;
public interface StylesDao {
	public boolean addStyle(Styles style);
	public boolean removeStyle(Styles style);
	public boolean updateStyle(Styles style);
	public Styles findById(Short styleid);
	public List<Styles> findByProperty(String propertyName,Object value);
	public List<Styles> findAll();
}
