package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Typemodels;
public interface TypemodelsDao {
	public boolean addTypemodel(Typemodels typemodel);
	public boolean removeTypemodel(Typemodels typemodel);
	public boolean updateTypemodel(Typemodels typemodel);
	public Typemodels findById(Short id);
	public List<Typemodels> findByProperty(String propertyName, Object value);
	public List<Typemodels> findAll();
}
