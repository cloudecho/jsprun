package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Magiclog;
public interface MagiclogDao {
	public boolean insertMagiclog(Magiclog magiclog);
	public boolean modifyMagiclog(Magiclog magiclog);
	public boolean deleteMageiclog(Magiclog magiclog);
	public List<Magiclog> findAllMagiclog();
	public List<Magiclog> findMagiclogByAction(Byte []action);
	public List<Magiclog> findMageiclogByMageicId(Short magicid);
	public List<Magiclog> getMagiclogByActionAndUid(Integer uid,Byte[] action);
	public List<Magiclog> getMagiclogByActionAndUid(Integer uid,Byte[] action,Integer firstNum,Integer maxNum);
	public List<Magiclog> getMagiclogByActionAndTargetUid(Integer targetuid,Byte []action);
	public List<Magiclog> getMagiclogByActionAndTargetUid(Integer targetuid,Byte []action,Integer firstNum,Integer maxNum);
}
