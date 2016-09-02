package cn.jsprun.foreg.vo.wap;
import java.util.ArrayList;
import java.util.List;
public class HomeVO extends WithFooterAndHead {
	private String bbname = null; 
	private boolean isLogin = false; 
	private boolean allowsearch = false; 
	private int newthreads = 0; 
	private List<Forum> forumList = new ArrayList<Forum>(10); 
	private boolean existMoreForum = false;
	private int memberCount = 0;
	private int guestCount = 0; 
	private String sid = null;
	public static class Forum{
		private String fid = null; 
		private String name = null; 
		public String getFid() {
			return fid;
		}
		public void setFid(String fid) {
			this.fid = fid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	public Forum getForum(){
		return new Forum();
	}
	public String getBbname() {
		return bbname;
	}
	public void setBbname(String bbname) {
		this.bbname = bbname;
	}
	public boolean getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	public boolean getAllowsearch() {
		return allowsearch;
	}
	public void setAllowsearch(boolean allowsearch) {
		this.allowsearch = allowsearch;
	}
	public int getNewthreads() {
		return newthreads;
	}
	public void setNewthreads(int newthreads) {
		this.newthreads = newthreads;
	}
	public boolean isExistMoreForum() {
		return existMoreForum;
	}
	public void setExistMoreForum(boolean existMoreForum) {
		this.existMoreForum = existMoreForum;
	}
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	public int getGuestCount() {
		return guestCount;
	}
	public void setGuestCount(int guestCount) {
		this.guestCount = guestCount;
	}
	public List<Forum> getForumList() {
		return forumList;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
}
