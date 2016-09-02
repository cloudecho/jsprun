package cn.jsprun.dao;
import cn.jsprun.domain.Magiclog;
import cn.jsprun.domain.Magicmarket;
import cn.jsprun.domain.Membermagics;
public interface MemberMagic_MagiclogDao {
	public void dropMagicOfOnebody(Magiclog magiclog,Membermagics memberMagics) throws Exception;
	public void sendMagic(Magiclog magiclog,Membermagics memberMagics_send,Membermagics memberMagics_get) throws Exception;
	public void sellMagic(Magiclog magiclog,Membermagics memberMagics,Magicmarket magicmarket) throws Exception;
}
