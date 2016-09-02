package cn.jsprun.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.MembersDao;
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
import cn.jsprun.utils.HibernateUtil;
public class MembersDaoImpl implements MembersDao {
	public Members findMemberById(int memberId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Members member = (Members) session.get(Members.class, memberId);
			tr.commit();
			return member;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Members findMemberByName(String username) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Members m where m.username =?");
			query.setString(0, username);
			Members member=(Members)query.uniqueResult();
			tr.commit();
			return member;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Members findByNameAndGroups(String memberName, String groupIds) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session
					.createQuery("from Members m where m.username = :memberName and m.groupid in ("
							+ groupIds + ")");
			query.setString("memberName", memberName);
			List<Members> list = query.list();
			tr.commit();
			if (list != null && list.size() == 1) {
				return list.get(0);
			}
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertMember(Members member) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(member);
			session.flush();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean modifyMember(Members member) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.update(member);
			session.flush();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteMember(Members member) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(member);
			session.flush();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteValidating(short id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("delete from Validating where uid = ?");
			query.setShort(0, id);
			query.executeUpdate();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public List<Validating> findAllValidatings() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Validating as v where v.status=0");
			List<Validating> validatingList = query.list();
			tr.commit();
			return validatingList;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Validating findValidatingById(int id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Validating validating = (Validating) session.get(Validating.class, id);
			tr.commit();
			return validating;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertValidating(Validating validating) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(validating);
			session.flush();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public int modifyAllValidating(byte status) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("update Validating set status = ?");
			query.setByte(0, status);
			int count = query.executeUpdate();
			tr.commit();
			return count;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public boolean modifyValidating(Validating validating) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.update(validating);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteProfile(Short filedId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session
					.createQuery("delete from Profilefields p where p.fieldid = ?");
			query.setShort(0, filedId);
			query.executeUpdate();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public List<Profilefields> findAllProfilefields() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Profilefields");
			List<Profilefields> profileList = query.list();
			tr.commit();
			return profileList;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertProfile(Profilefields profile) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(profile);
			session.flush();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean modifyProfile(Profilefields profile) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.update(profile);
			session.flush();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public Profilefields findProfileById(Short filedId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Profilefields profile = (Profilefields) session.get(
					Profilefields.class, filedId);
			tr.commit();
			return profile;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean deleteRanks(Ranks rank) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(rank);
			session.flush();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public List<Ranks> findAllRanks() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Ranks order by postshigher");
			List<Ranks> list = query.list();
			tr.commit();
			return list;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Ranks findRanksById(short rankId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Ranks rank = (Ranks) session.get(Ranks.class, rankId);
			tr.commit();
			return rank;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertRanks(Ranks rank) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(rank);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean modifyRanks(Ranks rank) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.update(rank);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteAccess(Access access) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(access);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public List<Access> findAccessByUserId(int userId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Access as a where a.uid=?");
			query.setParameter(0, userId);
			List<Access> list = query.list();
			tr.commit();
			return list;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertAccess(Access access) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(access);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean modifyAccess(Access access) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.saveOrUpdate(access);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteMemberfields(Memberfields memberfields) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(memberfields);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if (tr != null) {
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public Memberfields findMemberfieldsById(int memberFieldsId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Memberfields memberFields = (Memberfields) session.get(Memberfields.class, memberFieldsId);
			tr.commit();
			return memberFields;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertMemberfields(Memberfields memberfields) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(memberfields);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean modifyMemberfields(Memberfields memberfields) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.saveOrUpdate(memberfields);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteOnlineTime(Onlinetime onlinetime) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(onlinetime);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public Onlinetime findOnlinetimeById(int onlinetimeId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Onlinetime onlinetime = (Onlinetime) session.get(Onlinetime.class,
					onlinetimeId);
			tr.commit();
			return onlinetime;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertOnlineTime(Onlinetime onlinetime) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(onlinetime);
			session.flush();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean modifyOnlineTime(Onlinetime onlinetime) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.saveOrUpdate(onlinetime);
			session.flush();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public List<Styles> findAllStyles() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Styles");
			List<Styles> list = query.list();
			tr.commit();
			return list;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertPms(Pms pms) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(pms);
			session.flush();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public List<Forums> findForumsByType() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Forums as f where f.type<>'group' and f.status=1");
			List<Forums> list = query.list();
			tr.commit();
			return list;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public Access findAccessByFid(short fid, int uid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session
					.createQuery("from Access as a where a.id.fid=? and a.id.uid=?");
			query.setParameter(0, fid);
			query.setParameter(1, uid);
			List<Access> accessList = query.list();
			tr.commit();
			if (accessList != null && accessList.size() == 1) {
				return accessList.get(0);
			}
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean deleteSpacecaches(int uid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session
					.createQuery("delete from Spacecaches s where s.id.uid=?");
			query.setParameter(0, uid);
			query.executeUpdate();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public List<Medals> findMedalsByAvailable(byte available) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Medals where available=? order by medalid");
			query.setParameter(0, available);
			List<Medals> list = query.list();
			tr.commit();
			return list;
		}catch(HibernateException e){
			if(tr!=null && tr.isActive()){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public List<Usergroups> findUsergroupByExt() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Usergroups as u where u.type=? or u.radminid<>0 order by u.groupid");
			query.setParameter(0, "special");
			List<Usergroups> list = query.list();
			tr.commit();
			return list;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean deleteValidating(Validating validate) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.delete(validate);
			tr.commit();
			return true;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public List<Members> findMembersByGroupid(Short groupid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Members as m where m.groupid = ?");
			query.setParameter(0, groupid);
			List<Members> memberlist = query.list();
			tr.commit();
			return memberlist;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List findMembersByInId(int lowerId, int upperId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("SELECT m.uid from Members as m where m.uid>=? and m.uid<=?");
			query.setParameter(0,lowerId);
			query.setParameter(1,upperId);
			List memberlist = query.list();
			tr.commit();
			return memberlist;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertMemberByJDBC(Members member) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		String sql = "insert into jrun_members(uid,username,password,email,groupid) values(?,?,?,?,?)";
		PreparedStatement pstmt=null;
		Connection conn =null;
		try {
			tr = session.beginTransaction();
			conn = session.connection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member.getUid());
			pstmt.setString(2, member.getUsername());
			pstmt.setString(3, member.getPassword());
			pstmt.setString(4, member.getEmail());
			pstmt.setShort(5, member.getGroupid());
			pstmt.executeUpdate();
			tr.commit();
			return true;
		} catch (SQLException e) {
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}finally {
			try {
				if(tr!=null){
					tr=null;
				}
				if(pstmt!=null){
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null){
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public void resetUserCredits(String columeName, Integer resetValue) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr =  session.beginTransaction();
		String hql = "from Members";
		try {
			Query query = session.createQuery(hql);
			List<Members> membersListFormDB = query.list();
			for (int i = 0; i < membersListFormDB.size(); i++) {
				Members member = membersListFormDB.get(i);
				if (columeName.equals("extcredits1")) {
					member.setExtcredits1(resetValue);
				} else if (columeName.equals("extcredits2")) {
					member.setExtcredits2(resetValue);
				} else if (columeName.equals("extcredits3")) {
					member.setExtcredits3(resetValue);
				} else if (columeName.equals("extcredits4")) {
					member.setExtcredits4(resetValue);
				} else if (columeName.equals("extcredits5")) {
					member.setExtcredits5(resetValue);
				} else if (columeName.equals("extcredits6")) {
					member.setExtcredits6(resetValue);
				} else if (columeName.equals("extcredits7")) {
					member.setExtcredits7(resetValue);
				} else if (columeName.equals("extcredits8")) {
					member.setExtcredits8(resetValue);
				} else {
					throw new Exception(
							"columeName NOT IN ( " +
							"extcredits1, extcredits2, extcredits3,extcredits4, " +
							"extcredits5, extcredits6, extcredits7,extcredits8 )");
				}
				session.update(member);
			}
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(tr!=null){
				tr.rollback();
			}
			throw new Exception("DATA ERROR");
		}
	}
	@SuppressWarnings("unchecked")
	public List<Members> findMembersByHql(String hql,int startrow,int maxrows) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setFirstResult(startrow);
			query.setMaxResults(maxrows);
			List<Members> list = query.list();
			tr.commit();
			return list;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public int findMembersCount() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("select count(*) from Members");
			List list = query.list();
			tr.commit();
			if(list!=null && list.size()>0){
				return Integer.valueOf(list.get(0)+"");
			}
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public boolean updateMembersByHql(String hql) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.executeUpdate();
			tr.commit();
			return true;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public List<Profilefields> findprofilefieldByAvaliable(byte avaliable) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery("from Profilefields as p where p.available=?");
			query.setParameter(0, avaliable);
			List<Profilefields> profilelist = query.list();
			tr.commit();
			return profilelist;
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<Members> findByProperty(String propertyName, Object value) {
		Session session = null;
		Transaction tran = null;
		Query query = null;
		List<Members> members = null;
		try {
			members = new ArrayList<Members>();
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery("from Members as m where m."+ propertyName + "=?");
			query.setString(0, value.toString());
			members = query.list();
			tran.commit();
		} catch (HibernateException e) {
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return members;
	}
	public List<Members> getMemberListWithMemberIdList(List<Integer> memberIdList) {
		if(memberIdList.size()<1){
			return null;
		}
		StringBuffer hqlBuffer = new StringBuffer("FROM Members AS m WHERE m.uid IN (");
		for(int i : memberIdList){
			hqlBuffer.append(i+",");
		}
		hqlBuffer.replace(hqlBuffer.length()-1, hqlBuffer.length(), ")");
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(hqlBuffer.toString());
			List<Members> list = query.list();
			transaction.commit();
			return list;
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public int findMemberCountByHql(String hql) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			Query query = session.createQuery(hql);
			List list = query.list();
			tr.commit();
			if(list!=null && list.size()>0){
				return (Integer)list.get(0);
			}
		}catch(HibernateException e){
			if(tr!=null){
				tr.rollback();
			}
			e.printStackTrace();
		}
		return 0;
	}
	public Integer getAdminCount() {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "SELECT COUNT(*) FROM Members WHERE adminid>?";
			Query query = session.createQuery(hql);
			query.setByte(0, (byte)0);
			List list = query.list();
			if(list.size()<=0){
				throw new Exception();
			}
			transaction.commit();
			return (Integer)list.get(0);
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public Integer getMembersCount(){
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "SELECT COUNT(*) FROM Members";
			Query query = session.createQuery(hql);
			List list = query.list();
			if(list.size()<=0){
				throw new Exception();
			}
			transaction.commit();
			return (Integer)list.get(0);
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public Integer getMembersaddtoday(){
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "SELECT COUNT(*) FROM Members WHERE regdate>=?";
			Query query = session.createQuery(hql);
			Integer nowTime = (int)(System.currentTimeMillis()/1000);
			query.setInteger(0, nowTime-86400);
			List list = query.list();
			if(list.size()<=0){
				throw new Exception();
			}
			transaction.commit();
			return (Integer)list.get(0);
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public Integer getMemnonpost(){
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "SELECT COUNT(*) FROM Members WHERE posts=?";
			Query query = session.createQuery(hql);
			query.setInteger(0, 0);
			List list = query.list();
			if(list.size()<=0){
				throw new Exception();
			}
			transaction.commit();
			return (Integer)list.get(0);
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public Members getLastMember() {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction  = session.beginTransaction();
			String hql = "FROM Members ORDER BY uid DESC";
			Query query  = session.createQuery(hql);
			query.setMaxResults(1);
			List<Members> members = query.list();
			transaction.commit();
			if(members!=null&&members.size()>0){
				return members.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}
		return null;
	}
	public List<Members> getMembersByNames(List<String> membersNames) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Members as m WHERE m.username=?";
			Query query = session.createQuery(hql);
			List<Members> memberList = new ArrayList<Members>();
			for(int i = 0;i<membersNames.size();i++){
				String membersName = membersNames.get(i);
				if(membersName!=null){
					query.setString(0, membersName);
					List<Members> listTemp = query.list();
					if(listTemp.size()>0){
						Members members = listTemp.get(0);
						memberList.add(members);
					}
				}
			}
			transaction.commit();
			return memberList;
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public List<Members> getAllMembers() {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			String hql =  "FROM Members";
			Query query = session.createQuery(hql);
			List<Members> resultList = query.list();
			transaction.commit();
			return resultList;
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}
	}
	public void updateMembers(List<Members> memberList) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			for(int i = 0;i<memberList.size();i++){
				Members members = memberList.get(i);
				if(members!=null){
					session.update(members);
				}
			}
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}
	}
	public void updateMembers(String hql) {
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.executeUpdate();
			transaction.commit();
		}catch(Exception exception){
			if(transaction!=null){
				transaction.rollback();
			}
			exception.printStackTrace();
		}
	}
}