package cn.jsprun.foreg.service;
import cn.jsprun.dao.Member_Magics_Magiclog_MemberMagicsDao;
import cn.jsprun.domain.Magiclog;
import cn.jsprun.domain.Magics;
import cn.jsprun.domain.Membermagics;
import cn.jsprun.domain.Members;
import cn.jsprun.utils.BeanFactory;
public class Member_Magics_Magiclog_MemberMagicsService {
	public boolean userBuyMagic(Magics magic, Magiclog magiclog,
			Members member, Membermagics memberMagics) {
		return ((Member_Magics_Magiclog_MemberMagicsDao) BeanFactory
				.getBean("member_Magics_Magiclog_MemberMagicsDao")).userBuyMagic(magic, magiclog, member, memberMagics);
	}
}
