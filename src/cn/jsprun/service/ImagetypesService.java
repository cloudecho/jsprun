package cn.jsprun.service;
import java.util.List;
import java.util.Map;
import cn.jsprun.dao.ImagetypesDao;
import cn.jsprun.dao.SmiliesDao;
import cn.jsprun.domain.Imagetypes;
import cn.jsprun.domain.Smilies;
import cn.jsprun.utils.BeanFactory;
public class ImagetypesService {
	public Integer saveList(List<Imagetypes> list) {
		if (list != null && list.size() > 0) {
			return ((ImagetypesDao) BeanFactory.getBean("imageDao")).saveList(list);
		}
		return 0;
	}
	public Integer deleteImagetypesAll(Short[] ids) {
		if (ids != null && ids.length > 0) {
			return ((ImagetypesDao) BeanFactory.getBean("imageDao")).deleteImagetypesAll(ids);
		}
		return 0;
	}
	public Integer updateNameImagetypes(List<Imagetypes> list) {
		if (list != null && list.size() > 0) {
			((ImagetypesDao) BeanFactory.getBean("imageDao")).updateNameImagetypes(list);
		}
		return -1;
	}
	public Map showImagesToID(Short typeid, Integer page) {
		if (typeid > 0 && page > 0) {
			return ((ImagetypesDao) BeanFactory.getBean("imageDao")).showImagesToID(typeid, page);
		}
		return null;
	}
	public Integer deleteSmiliesIds(List<Short> list) {
		if (list != null && list.size() > 0) {
			((SmiliesDao) BeanFactory.getBean("smiliesDao")).deleteSmiliesIds(list);
		}
		return 0;
	}
	public Integer updateSmiliesDisplayorderCode(List<Smilies> list) {
		if (list != null && list.size() > 0) {
			((SmiliesDao) BeanFactory.getBean("smiliesDao")).updateSmiliesDisplayorderCode(list);
		}
		return 0;
	}
	public List<Imagetypes> findImagetypeBytype(String type){
		return ((ImagetypesDao) BeanFactory.getBean("imageDao")).findImagetypeBytype(type);
	}
	public Imagetypes findImagetypeById(short id){
		return ((ImagetypesDao) BeanFactory.getBean("imageDao")).findImagetypeById(id);
	}
	public List<Imagetypes> findImagetypeByName(String name){
		return ((ImagetypesDao) BeanFactory.getBean("imageDao")).findImagetypeByName(name);
	}
	public boolean addImagetype(Imagetypes imagetype){
		return ((ImagetypesDao) BeanFactory.getBean("imageDao")).addImagetype(imagetype);
	}
}
