package cn.jsprun.dao;
import cn.jsprun.domain.Memberspaces;
public interface MemberSpaceDao {
	public boolean addMemberSpace(Memberspaces memberspace);
	public boolean modifyMemberspace(Memberspaces memberspace);
	public boolean deleteMemberspace(Memberspaces memberspace);
	public Memberspaces findMemberspace(int uid);
}
