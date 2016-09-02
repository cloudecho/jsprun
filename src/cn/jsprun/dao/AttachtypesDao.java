package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Attachtypes;
public interface AttachtypesDao {
	public List<Attachtypes> getAll();
	public boolean saveAttachtypes(Attachtypes attachtypes);
	public Integer deleteList(String[] id);
	public Integer updateExtensionList(List<Attachtypes> list);
	public Integer updateMaxsizeList(List<Attachtypes> list);
	public boolean isSave(String name);
}
