package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Typevars;
import cn.jsprun.domain.TypevarsId;
public interface TypevarsDao {
	public boolean addTypevar(Typevars typevar);
	public boolean removeTypevar(Typevars typevar);
	public boolean updateTypevar(Typevars typevar);
	public Typevars findById(TypevarsId id);
	public Typevars findByTO(Short typeid,Short optionid);
	public List<Typevars> findByTId(Short typeid);
	public List<Typevars> findAll();
}