package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.SmiliesDao;
import cn.jsprun.dao.impl.SmiliesDaoImple;
import cn.jsprun.domain.Smilies;
import cn.jsprun.utils.BeanFactory;
public class SmiliesService {
	public List<Smilies> getIcons() {
		return ((SmiliesDaoImple) BeanFactory.getBean("smiliesDao")).getIcons();
	}
	public Integer deleteIcons(String[] ids) {
		if (ids != null){
			return ((SmiliesDaoImple) BeanFactory.getBean("smiliesDao")).deleteIcons(ids);
		}
		return -1;
	}
	public Integer updateDisplayorderIcons(List<Smilies> list) {
		if (list != null) {
			return ((SmiliesDaoImple) BeanFactory.getBean("smiliesDao")).updateDisplayorderIcons(list);
		}
		return null;
	}
	public Integer saveList(List<Smilies> list) {
		Integer num = 0;
		if (list != null) {
			SmiliesDao smiliesDao =(SmiliesDaoImple) BeanFactory.getBean("smiliesDao");
			for (int i = 0; i < list.size(); i++) {
				num = smiliesDao.save(list.get(i));
			}
			smiliesDao=null;
		}
		return num;
	}
	public boolean findSmiliesbytypeid(short typeid, String url){
		return ((SmiliesDaoImple) BeanFactory.getBean("smiliesDao")).findSmiliesbytypeid(typeid, url);
	}
	public List<Smilies> findSmiliesBytypeid(short typeid,int start,int max) {
		return ((SmiliesDaoImple) BeanFactory.getBean("smiliesDao")).findSmiliesBytypeid(typeid, start, max);
	}
	public int findSmiliesCountBytypeId(short typeid){
		return ((SmiliesDaoImple) BeanFactory.getBean("smiliesDao")).findSmiliesCountBytypeId(typeid);
	}
	public Smilies findSmiliesById(short id){
		return ((SmiliesDaoImple) BeanFactory.getBean("smiliesDao")).findSmiliesById(id);
	}
	public List<Smilies> getSmilies(){
		return ((SmiliesDaoImple) BeanFactory.getBean("smiliesDao")).getSmilies();
	}
	public boolean addSmilies(Smilies smilies){
		return ((SmiliesDaoImple) BeanFactory.getBean("smiliesDao")).addSmilies(smilies);
	}
}
