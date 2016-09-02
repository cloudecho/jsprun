package cn.jsprun.service;
import java.util.List;
import cn.jsprun.dao.BuddysDao;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.dao.MembersDao;
import cn.jsprun.dao.SubscriptiosDao;
import cn.jsprun.domain.Access;
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Medals;
import cn.jsprun.domain.Memberfields;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Onlinetime;
import cn.jsprun.domain.Pms;
import cn.jsprun.domain.Profilefields;
import cn.jsprun.domain.Ranks;
import cn.jsprun.domain.Styles;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.domain.Validating;
import cn.jsprun.struts.form.UserForm;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
public class MemberService {
	public boolean deleteValidating(short id) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).deleteValidating(id);
	}
	public boolean modifyValidating(Validating validating) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).modifyValidating(validating);
	}
	public int modifyAllValidating(byte status) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).modifyAllValidating(status);
	}
	public boolean insertValidating(Validating validating) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).insertValidating(validating);
	}
	public List<Validating> findAllValidatings() {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findAllValidatings();
	}
	public Validating findValidatingById(int id) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findValidatingById(id);
	}
	public boolean insertProfile(Profilefields profile) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).insertProfile(profile);
	}
	public Profilefields findProfileById(Short fieldId) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findProfileById(fieldId);
	}
	public List<Profilefields> findAllProfilefields() {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findAllProfilefields();
	}
	public boolean deleteProfile(Short fieldId) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).deleteProfile(fieldId);
	}
	public boolean modifyProfile(Profilefields profile) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).modifyProfile(profile);
	}
	public boolean mergeMembers(Members source, Members target) {
		DataBaseDao databaseDao = ((DataBaseDao) BeanFactory.getBean("dataBaseDao"));
		databaseDao.runQuery("update jrun_posts set authorid="+target.getUid()+",author='"+target.getUsername()+"' where authorid='"+source.getUid()+"'");
		databaseDao.runQuery("delete from jrun_access where uid="+source.getUid());
		databaseDao.runQuery("update jrun_adminsessions set uid="+target.getUid()+" where uid="+source.getUid());
		databaseDao.runQuery("update jrun_announcements set author='"+target.getUsername()+"' where author='"+source.getUsername()+"'");
		databaseDao.runQuery("update jrun_banned set admin='"+target.getUsername()+"' where admin='"+source.getUsername()+"'");
		databaseDao.runQuery("delete from jrun_moderators where uid ="+source.getUid());
		databaseDao.runQuery("update jrun_threadsmod set uid="+target.getUid()+",username='"+target.getUsername()+"' where uid="+source.getUid());
		databaseDao.runQuery("update jrun_threads set lastposter='"+target.getUsername()+"' where lastposter='"+source.getUsername()+"'");
		databaseDao.runQuery("UPDATE jrun_ratelog SET uid='"+target.getUid()+"', username='"+target.getUsername()+"' WHERE uid="+source.getUid());
		databaseDao.runQuery("update jrun_adminnotes set admin='"+target.getUsername()+"' where admin='"+source.getUsername()+"'");
		databaseDao.runQuery("update jrun_threads set authorid="+target.getUid()+",author='"+target.getUsername()+"' where authorid="+source.getUid());
		target.setCredits(source.getCredits() + target.getCredits());
		target.setExtcredits1(source.getExtcredits1()	+ target.getExtcredits1());
		target.setExtcredits2(source.getExtcredits2()+ target.getExtcredits2());
		target.setExtcredits3(source.getExtcredits3()+ target.getExtcredits3());
		target.setExtcredits4(source.getExtcredits4()+ target.getExtcredits4());
		target.setExtcredits5(source.getExtcredits5()+ target.getExtcredits5());
		target.setExtcredits6(source.getExtcredits6()+ target.getExtcredits6());
		target.setExtcredits7(source.getExtcredits7()+ target.getExtcredits7());
		target.setExtcredits8(source.getExtcredits8()+ target.getExtcredits8());
		target.setPosts(source.getPosts()+target.getPosts());
		int digestpost = source.getDigestposts()+target.getDigestposts();
		target.setDigestposts(digestpost);
		target.setPageviews(source.getPageviews()+target.getPageviews());
		int oltime = source.getOltime()+target.getOltime();
		target.setOltime(Short.valueOf(oltime+""));
		((BuddysDao) BeanFactory.getBean("buddysDao")).deleteBuddysByUid(source.getUid());
		databaseDao.runQuery("update jrun_favorites set uid="+target.getUid()+" where uid="+source.getUid());
		databaseDao.runQuery("delete from jrun_memberfields where uid="+source.getUid());
		databaseDao.runQuery("update jrun_myposts set uid="+target.getUid()+" where uid="+source.getUid());
		databaseDao.runQuery("update jrun_mythreads set uid="+target.getUid()+" where uid="+source.getUid());
		databaseDao.runQuery("update jrun_pms set msgfromid="+target.getUid()+",msgfrom='"+target.getUsername()+"' where msgfromid="+source.getUid());
		databaseDao.runQuery("update jrun_pms set msgtoid="+target.getUid()+" where msgtoid="+source.getUid());
		((SubscriptiosDao) BeanFactory .getBean("subscriptionsDao")).deleteSubscriptionByUid(source.getUid());
		databaseDao = null;
		((MembersDao) BeanFactory.getBean("memberDao")).deleteMember(source);
		((MembersDao) BeanFactory.getBean("memberDao")).modifyMember(target);
		return true;
	}
	public boolean insertMember(Members member) {
		Members m = ((MembersDao) BeanFactory.getBean("memberDao")).findMemberByName(member.getUsername());
		if (m != null) {
			return false;
		}
		if (member.getGroupid() == null || member.getGroupid() == 0) {
			member.setGroupid(new Short("10"));
		}
		return ((MembersDao) BeanFactory.getBean("memberDao")).insertMember(member);
	}
	public Members findByName(String userName) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findMemberByName(userName);
	}
	public boolean deleteMember(Members member) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).deleteMember(member);
	}
	public boolean modifyMember(Members member) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).modifyMember(member);
	}
	public String searchValidate(int submitTimes, int submitDate,int modDate, String regip) {
		StringBuffer sql = new StringBuffer(" from jrun_validating v left join jrun_members m on v.uid = m.uid ");
		String and = "";
		String where = " where ";
		if (submitTimes != 0) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append("v.submittimes >" + submitTimes);
		}
		if (submitDate != 0) {
			sql.append(where);
			where = "";
			sql.append(and);
			and = " and ";
			sql.append("m.regdate <" + submitDate);
		}
		if (modDate != 0) {
			sql.append(where);
			where = "";
			sql.append(and);
			and = " and ";
			sql.append("v.moddate < " + modDate);
		}
		if (!regip.equals("")) {
			sql.append(where);
			where = "";
			sql.append(and);
			and = " and ";
			sql.append("m.regip like '" + Common.addslashes(regip) + "%'");
		}
		return sql.toString();
	}
	public String returnsearsql(UserForm userForm,boolean includeManager,String timeoffset){
		StringBuffer sql = new StringBuffer("from jrun_members m left join jrun_usergroups g on m.groupid = g.groupid ");
		String and = "";
		String where = " where ";
		String[] uids = userForm.getUids();
		String userNamesStr = userForm.getUsername();
		String[] userGroups = userForm.getUsergroupids();
		String email = userForm.getEmail();
		String lower_credits = userForm.getLower_credits();
		String higher_credits = userForm.getHigher_credits();
		String lower_extcredits1 = userForm.getLower_extcredits1();
		String higher_extcredits1 = userForm.getHigher_extcredits1();
		String lower_extcredits2 = userForm.getLower_extcredits2();
		String higher_extcredits2 = userForm.getHigher_extcredits2();
		String lower_extcredits3 = userForm.getLower_extcredits3();
		String lower_extcredits4 = userForm.getLower_extcredits4();
		String lower_extcredits5 = userForm.getLower_extcredits5();
		String lower_extcredits6 = userForm.getLower_extcredits6();
		String lower_extcredits7 = userForm.getLower_extcredits7();
		String lower_extcredits8 = userForm.getLower_extcredits8();
		String higher_extcredits3 = userForm.getHigher_extcredits3();
		String higher_extcredits4 = userForm.getHigher_extcredits4();
		String higher_extcredits5 = userForm.getHigher_extcredits5();
		String higher_extcredits6 = userForm.getHigher_extcredits6();
		String higher_extcredits7 = userForm.getHigher_extcredits7();
		String higher_extcredits8 = userForm.getHigher_extcredits8();
		String lower_posts = userForm.getLower_posts();
		String higher_posts = userForm.getHigher_posts();
		String regip = userForm.getRegip();
		String lastip = userForm.getLastip();
		String regdatebefore = userForm.getRegdatebefore();
		String regdateafter = userForm.getRegdateafter();
		String lastvisitbefore = userForm.getLastvisitbefore();
		String lastvisitafter = userForm.getLastvisitafter();
		String lastpostbefore = userForm.getLastpostbefore();
		String lastpostafter = userForm.getLastpostafter();
		String birthyear = userForm.getBirthyear();
		String birthmonth = userForm.getBirthmonth();
		String birthday = userForm.getBirthday();
		String cins = userForm.getCins();
		if (!cins.equals("yes")) {
			cins = " binary ";
		} else {
			cins = "";
		}
		if (!includeManager) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" g.radminid = 0 ");
		}
		if (!uids[0].equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			StringBuffer uidStr = new StringBuffer();
			for (String uid : uids) {
				uidStr.append(","+uid);
			}
			sql.append(" m.uid in (" + uidStr.substring(1) + ") ");
		}
		if (!userNamesStr.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			String userName = "";
			String[] userNames = userNamesStr.split(",");
			userName = userNames[0].replace("*", "%");
			sql.append(cins + " username like '" + userName.trim() + "' ");
			for (int i = 1; i < userNames.length; i++) {
				userName = userNames[i].replace("*", "%");
				sql.append(" or "+cins+" username like '" + userName.trim() + "' ");
			}
		}
		if (!userGroups[0].equals("") && !userGroups[0].equals("all")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			StringBuffer userGroupIds = new StringBuffer();
			for (String id : userGroups) {
				userGroupIds.append(","+id);
			}
			if(userGroupIds.length()>0){
				sql.append(" m.groupid in (" + userGroupIds.substring(1) + ") ");
			}
		}
		if (!email.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			email = email.replace("*", "%");
			sql.append(cins + " m.email like '" + email + "'");
		}
		if (!lower_credits.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" credits < " + lower_credits);
		}
		if (!higher_credits.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" credits > " + higher_credits);
		}
		if (!lower_extcredits1.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits1 < " + lower_extcredits1);
		}
		if (!higher_extcredits1.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits1 > " + higher_extcredits1);
		}
		if (!lower_extcredits2.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits2 < " + lower_extcredits2);
		}
		if (!higher_extcredits2.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits2 > " + higher_extcredits2);
		}
		if (!higher_extcredits3.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits3 > " + higher_extcredits3);
		}
		if (!higher_extcredits4.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits4 > " + higher_extcredits4);
		}
		if (!higher_extcredits5.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits5 > " + higher_extcredits5);
		}
		if (!higher_extcredits6.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits6 > " + higher_extcredits6);
		}
		if (!higher_extcredits7.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits7 > " + higher_extcredits7);
		}
		if (!higher_extcredits8.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits8 > " + higher_extcredits8);
		}
		if (!lower_extcredits3.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits3 < " + lower_extcredits3);
		}
		if (!lower_extcredits4.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits4 < " + lower_extcredits4);
		}
		if (!lower_extcredits5.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits5 < " + lower_extcredits5);
		}
		if (!lower_extcredits6.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits6 < " + lower_extcredits6);
		}
		if (!lower_extcredits7.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits7 < " + lower_extcredits7);
		}
		if (!lower_extcredits8.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" extcredits8 < " + lower_extcredits8);
		}
		if (!lower_posts.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" posts < " + lower_posts);
		}
		if (!higher_posts.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" posts > " + higher_posts);
		}
		if (!regip.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" regip like '" + regip + "%' ");
		}
		if (!lastip.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" lastip like '" + lastip + "%' ");
		}
		if (!regdatebefore.equals("")) {
			regdatebefore = Common.dataToInteger(regdatebefore, "yyyy-MM-dd",timeoffset)+"";
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" regdate < " + regdatebefore);
		}
		if (!regdateafter.equals("")) {
			regdateafter = Common.dataToInteger(regdateafter, "yyyy-MM-dd",timeoffset)+"";
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" regdate > " + regdateafter);
		}
		if (!lastvisitbefore.equals("")) {
			lastvisitbefore = Common.dataToInteger(lastvisitbefore, "yyyy-MM-dd",timeoffset)+"";
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" lastvisit < " + lastvisitbefore);
		}
		if (!lastvisitafter.equals("")) {
			lastvisitafter =Common.dataToInteger(lastvisitafter, "yyyy-MM-dd",timeoffset)+"";
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" lastvisit > " + lastvisitafter);
		}
		if (!lastpostbefore.equals("")) {
			lastpostbefore = Common.dataToInteger(lastpostbefore, "yyyy-MM-dd",timeoffset)+"";
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" lastpost < " + lastpostbefore);
		}
		if (!lastpostafter.equals("")) {
			lastpostafter = Common.dataToInteger(lastpostafter, "yyyy-MM-dd",timeoffset)+"";
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" lastpost > " + lastpostafter);
		}
		if (!birthyear.equals("")||!birthmonth.equals("")||!birthday.equals("")) {
			sql.append(where);
			where = " ";
			sql.append(and);
			and = " and ";
			sql.append(" bday like '" + (!birthyear.equals("")?birthyear:"%") + "-"+(!birthmonth.equals("")?birthmonth:"%")+"-"+(!birthday.equals("")?birthday:"%")+"'");
		}
		return sql.toString();
	}
	public boolean insertRanks(Ranks rank) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).insertRanks(rank);
	}
	public boolean modifyRanks(Ranks rank) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).modifyRanks(rank);
	}
	public boolean deleteRanks(Ranks rank) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).deleteRanks(rank);
	}
	public Ranks findRanksById(short rankId) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findRanksById(rankId);
	}
	public List<Ranks> findAllRanks() {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findAllRanks();
	}
	public boolean insertAccess(Access access) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).insertAccess(access);
	}
	public boolean modifyAccess(Access access) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).modifyAccess(access);
	}
	public boolean deleteAccess(Access access) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).deleteAccess(access);
	}
	public List<Access> findAccessByUserId(int userId) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findAccessByUserId(userId);
	}
	public boolean insertMemberfields(Memberfields memberfields) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).insertMemberfields(memberfields);
	}
	public boolean modifyMemberfields(Memberfields memberfields) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).modifyMemberfields(memberfields);
	}
	public boolean deleteMemberfields(Memberfields memberfields) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).deleteMemberfields(memberfields);
	}
	public Memberfields findMemberfieldsById(int memberFieldsId) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findMemberfieldsById(memberFieldsId);
	}
	public boolean insertOnlineTime(Onlinetime onlinetime) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).insertOnlineTime(onlinetime);
	}
	public boolean modifyOnlineTime(Onlinetime onlinetime) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).modifyOnlineTime(onlinetime);
	}
	public boolean deleteOnlineTime(Onlinetime onlinetime) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).deleteOnlineTime(onlinetime);
	}
	public Onlinetime findOnlineTimeById(int onlinetimeId) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findOnlinetimeById(onlinetimeId);
	}
	public List<Styles> findAllStyles() {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findAllStyles();
	}
	public boolean insertPms(Pms pms) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).insertPms(pms);
	}
	public Members findMemberById(int memberId) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findMemberById(memberId);
	}
	public List<Forums> findForumsByType() {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findForumsByType();
	}
	public Access findAccessByFid(short fid, int uid) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findAccessByFid(fid, uid);
	}
	public boolean deleteSpacecaches(int uid) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).deleteSpacecaches(uid);
	}
	public List<Medals> findMedalsByAvailable(byte available) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findMedalsByAvailable(available);
	}
	public List<Usergroups> findUsergroupByExt() {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findUsergroupByExt();
	}
	public boolean deleteValidating(Validating validate) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).deleteValidating(validate);
	}
	public List<Members> findMembersByGroupid(Short groupid) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findMembersByGroupid(groupid);
	}
	public List findMembersByInId(int lowerId, int upperId) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findMembersByInId(lowerId, upperId);
	}
	public boolean insertMemberByJDBC(Members member) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).insertMemberByJDBC(member);
	}
	public List<Members> getMemberListWithMemberIdList(List<Integer> memberIdList){
		return ((MembersDao) BeanFactory.getBean("memberDao")).getMemberListWithMemberIdList(memberIdList);
	}
	public List<Profilefields> findprofilefieldByAvaliable(byte avaliable){
		return ((MembersDao) BeanFactory.getBean("memberDao")).findprofilefieldByAvaliable(avaliable);
	}
	public List<Members> findByProperty(String propertyName, Object value) {
		return ((MembersDao) BeanFactory.getBean("memberDao")).findByProperty(propertyName, value);
	}
	public List<Members> findMembersByHql(String hql,int startrow,int maxrows){
		return ((MembersDao) BeanFactory.getBean("memberDao")).findMembersByHql(hql, startrow, maxrows);
	}
	public int findMemberCountByHql(String hql){
		return ((MembersDao) BeanFactory.getBean("memberDao")).findMemberCountByHql(hql);
	}
	public List<Members> getAllMembers(){
		return ((MembersDao) BeanFactory.getBean("memberDao")).getAllMembers();
	}
	public void updateMembers(List<Members> membersList){
		((MembersDao) BeanFactory.getBean("memberDao")).updateMembers(membersList);
	}
	public void updateMembers(String hql){
		((MembersDao) BeanFactory.getBean("memberDao")).updateMembers(hql);
	}
}
