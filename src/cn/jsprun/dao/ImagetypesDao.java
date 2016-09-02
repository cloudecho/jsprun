package cn.jsprun.dao;
import java.util.List;
import java.util.Map;
import cn.jsprun.domain.Imagetypes;
public interface ImagetypesDao {
	static final String SMILIES = "smilies";
	static final String CURRENTPAGE = "currentPage";
	static final String TOTALPAGE = "totalpage";
	static final String TOTALSIZE = "totalsize";
	static final String DIRECTORY = "directory";
	static final Integer PAGESIZE = 10;
	public Integer saveList(List<Imagetypes> list);
	public Integer updateNameImagetypes(List<Imagetypes> list);
	public Integer deleteImagetypesAll(Short[] ids);
	public Map showImagesToID(Short typeid,Integer page);
	public List<Imagetypes>findImagetypeBytype(String type);
	public Imagetypes findImagetypeById(short id);
	public List<Imagetypes>findImagetypeByName(String name);
	public boolean addImagetype(Imagetypes imagetype);
}
