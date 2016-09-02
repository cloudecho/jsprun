package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Threadtypes;
public interface ThreadtypesDao {
	public boolean addThreadtype(Threadtypes threadtype);
	public boolean removeThreadtype(Threadtypes threadtype);
	public boolean updateThreadtype(Threadtypes threadtype);
	public Threadtypes findById(Short typeId);
	public List<Threadtypes> findByProperty(String propertyName, Object value);
	public List<Threadtypes> findBySpecial(Short value);
	public List<Threadtypes> findAll();
	public List<Threadtypes> findThreadtypeByHql(String hql);
}
