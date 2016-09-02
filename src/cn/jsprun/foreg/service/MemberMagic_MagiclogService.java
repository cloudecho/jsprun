package cn.jsprun.foreg.service;
import cn.jsprun.dao.MemberMagic_MagiclogDao;
import cn.jsprun.domain.Magiclog;
import cn.jsprun.domain.Magicmarket;
import cn.jsprun.domain.Membermagics;
import cn.jsprun.utils.BeanFactory;
public class MemberMagic_MagiclogService {
	public boolean dropMagicOfOneBoday(Magiclog magiclog,Membermagics memberMagics) {
		try {
			((MemberMagic_MagiclogDao) BeanFactory.getBean("memberMagic_MagiclogDao")).dropMagicOfOnebody(magiclog, memberMagics);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public boolean sendMagics(Magiclog magiclog,Membermagics memberMagics_send,Membermagics memberMagics_get){
		try{
			((MemberMagic_MagiclogDao) BeanFactory.getBean("memberMagic_MagiclogDao")).sendMagic(magiclog, memberMagics_send, memberMagics_get);
			return true;
		}catch(Exception exception){
			return false;
		}
	}
	public boolean sellMagics(Magiclog magiclog,Membermagics memberMagics,Magicmarket magicmarket){
		try{
			((MemberMagic_MagiclogDao) BeanFactory.getBean("memberMagic_MagiclogDao")).sellMagic(magiclog, memberMagics, magicmarket);
			return true;
		}catch(Exception exception){
			return false;
		}
	}
}
