package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.MagiclogDao;
import cn.jsprun.domain.Magiclog;
import cn.jsprun.utils.BeanFactory;
public class MagiclogService {
	public boolean insertMagiclog(Magiclog magiclog){
		MagiclogDao magiclogDao = (MagiclogDao)BeanFactory.getBean("magiclogDao");
		return magiclogDao.insertMagiclog(magiclog);
	}
	public boolean modifyMagiclog(Magiclog magiclog){
		MagiclogDao magiclogDao = (MagiclogDao)BeanFactory.getBean("magiclogDao");
		return magiclogDao.modifyMagiclog(magiclog);
	}
	public boolean deleteMageiclog(Magiclog magiclog){
		MagiclogDao magiclogDao = (MagiclogDao)BeanFactory.getBean("magiclogDao");
		return magiclogDao.deleteMageiclog(magiclog);
	}
	public List<Magiclog> findAllMagiclog(){
		MagiclogDao magiclogDao = (MagiclogDao)BeanFactory.getBean("magiclogDao");
		return magiclogDao.findAllMagiclog();
	}
	public List<Magiclog> findMagiclogByAction(Byte []action){
		MagiclogDao magiclogDao = (MagiclogDao)BeanFactory.getBean("magiclogDao");
		return magiclogDao.findMagiclogByAction(action);
	}
	public List<Magiclog> findMageiclogByMageicId(Short magicid){
		MagiclogDao magiclogDao = (MagiclogDao)BeanFactory.getBean("magiclogDao");
		return magiclogDao.findMageiclogByMageicId(magicid);
	}
	public List<Magiclog> getMagiclogByActionAndUid(Integer uid,Byte []action){
		MagiclogDao magiclogDao = (MagiclogDao)BeanFactory.getBean("magiclogDao");
		return magiclogDao.getMagiclogByActionAndUid(uid,action);
	}
	public List<Magiclog> getMagiclogByActionAndUid(Integer uid,Byte []action,Integer firstNum,Integer maxNum){
		MagiclogDao magiclogDao = (MagiclogDao)BeanFactory.getBean("magiclogDao");
		return magiclogDao.getMagiclogByActionAndUid(uid,action,firstNum,maxNum);
	}
	public List<Magiclog> getMagiclogByActionAndTargetUid(Integer targetuid,Byte []action){
		MagiclogDao magiclogDao = (MagiclogDao)BeanFactory.getBean("magiclogDao");
		return magiclogDao.getMagiclogByActionAndTargetUid(targetuid,action);
	}
	public List<Magiclog> getMagiclogByActionAndTargetUid(Integer targetuid,Byte []action,Integer firstNum,Integer maxNum){
		MagiclogDao magiclogDao = (MagiclogDao)BeanFactory.getBean("magiclogDao");
		return magiclogDao.getMagiclogByActionAndTargetUid(targetuid,action,firstNum,maxNum);
	}
}
