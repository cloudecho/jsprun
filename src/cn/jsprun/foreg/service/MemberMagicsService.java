package cn.jsprun.foreg.service;
import java.util.List;
import cn.jsprun.dao.MemberMagicsDao;
import cn.jsprun.dao.OtherSetDao;
import cn.jsprun.domain.Magics;
import cn.jsprun.domain.Membermagics;
import cn.jsprun.utils.BeanFactory;
public class MemberMagicsService {
	public boolean validateWeight(int userid,short maxmagicsweight,Short magicsidOfGet,Long magicsNum){
		OtherSetDao osDao = (OtherSetDao) BeanFactory.getBean("otherSetDao");
		List<Membermagics> memberMagicsList = ((MemberMagicsDao) BeanFactory.getBean("memberMagicsDao")).getMemberMagics(userid);
		Magics magicsTemp = osDao.queryMagicById(magicsidOfGet);
		if(magicsTemp==null){
			return false;
		}
		Long nowWeight = magicsTemp.getWeight()*magicsNum;
		short magicNum = 0;
		short magicWeight = 0;
		for(int i = 0;i<memberMagicsList.size();i++){
			Membermagics memberMagics = memberMagicsList.get(i);
			magicNum = memberMagics.getNum();
			short magicid = memberMagics.getId().getMagicid();
			Magics magics = osDao.queryMagicById(magicid);
			magicWeight = magics.getWeight();
			nowWeight += magicNum*magicWeight;
		}
		osDao=null;
		if(nowWeight>maxmagicsweight){
			return false;
		}else{
			return true;
		}
	}
	public Membermagics getMemberMagics(int userid,short magicid){
		List<Membermagics> list = ((MemberMagicsDao) BeanFactory.getBean("memberMagicsDao")).getMemberMagics(userid, magicid);
		if(list==null||list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	public List<Membermagics> getAllMagicOfOneBody(int uid){
		return ((MemberMagicsDao) BeanFactory.getBean("memberMagicsDao")).getMemberMagics(uid);
	}
}
