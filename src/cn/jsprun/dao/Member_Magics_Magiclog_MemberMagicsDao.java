package cn.jsprun.dao;
import cn.jsprun.domain.Magiclog;
import cn.jsprun.domain.Magics;
import cn.jsprun.domain.Membermagics;
import cn.jsprun.domain.Members;
public interface Member_Magics_Magiclog_MemberMagicsDao {
	public boolean userBuyMagic(Magics magic,Magiclog magiclog,Members member,Membermagics memberMagics);
}
