package cn.jsprun.foreg.vo.statistic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Stats_manageTeamVO {
	private Stats_navbarVO navbar = new Stats_navbarVO();
	private List<Map<String,Object>> manageTeamMapList = new ArrayList<Map<String,Object>>();
	private List<ForumTableGroup> forumTableGroupList = new ArrayList<ForumTableGroup>();
	private List<String> adminTableTitleList = new ArrayList<String>();
	private List<String> forumTableTitleList = new ArrayList<String>();
	private String lastTime;
	private String nextTime;
	public static class ForumTableGroup{
		private String groupId;
		private String groupName;
		private List<Forum> forumList = new ArrayList<Forum>();
		public static class Forum{
			private boolean selectFroumName;
			private String rowspan;
			private String uri;
			private String froumName;
			private String uid;
			private String username;
			private String managerName;
			private String lastAccessTime;
			private String offDays;
			private String credits;
			private String posts;
			private String thisMonthPosts;
			private String thisMonthManage;
			private boolean showOnline;
			private String allTimeOnline;
			private String thisMonthTimeOnline;
			public String getAllTimeOnline() {
				return allTimeOnline;
			}
			public void setAllTimeOnline(String allTimeOnline) {
				this.allTimeOnline = allTimeOnline;
			}
			public String getCredits() {
				return credits;
			}
			public void setCredits(String credits) {
				this.credits = credits;
			}
			public String getFroumName() {
				return froumName;
			}
			public void setFroumName(String froumName) {
				this.froumName = froumName;
			}
			public String getLastAccessTime() {
				return lastAccessTime;
			}
			public void setLastAccessTime(String lastAccessTime) {
				this.lastAccessTime = lastAccessTime;
			}
			public String getManagerName() {
				return managerName;
			}
			public void setManagerName(String managerName) {
				this.managerName = managerName;
			}
			public String getOffDays() {
				return offDays;
			}
			public void setOffDays(String offDays) {
				this.offDays = offDays;
			}
			public String getPosts() {
				return posts;
			}
			public void setPosts(String posts) {
				this.posts = posts;
			}
			public String getRowspan() {
				return rowspan;
			}
			public void setRowspan(String rowspan) {
				this.rowspan = rowspan;
			}
			public boolean isShowOnline() {
				return showOnline;
			}
			public void setShowOnline(boolean showOnline) {
				this.showOnline = showOnline;
			}
			public String getThisMonthManage() {
				return thisMonthManage;
			}
			public void setThisMonthManage(String thisMonthManage) {
				this.thisMonthManage = thisMonthManage;
			}
			public String getThisMonthPosts() {
				return thisMonthPosts;
			}
			public void setThisMonthPosts(String thisMonthPosts) {
				this.thisMonthPosts = thisMonthPosts;
			}
			public String getThisMonthTimeOnline() {
				return thisMonthTimeOnline;
			}
			public void setThisMonthTimeOnline(String thisMonthTimeOnline) {
				this.thisMonthTimeOnline = thisMonthTimeOnline;
			}
			public String getUid() {
				return uid;
			}
			public void setUid(String uid) {
				this.uid = uid;
			}
			public String getUri() {
				return uri;
			}
			public void setUri(String uri) {
				this.uri = uri;
			}
			public String getUsername() {
				return username;
			}
			public void setUsername(String username) {
				this.username = username;
			}
			public boolean isSelectFroumName() {
				return selectFroumName;
			}
			public void setSelectFroumName(boolean selectFroumName) {
				this.selectFroumName = selectFroumName;
			}
		}
		public List<Forum> getForumList() {
			return forumList;
		}
		public void setForumList(List<Forum> forumList) {
			this.forumList = forumList;
		}
		public String getGroupId() {
			return groupId;
		}
		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}
		public String getGroupName() {
			return groupName;
		}
		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getNextTime() {
		return nextTime;
	}
	public void setNextTime(String nextTime) {
		this.nextTime = nextTime;
	}
	public boolean isBeingAdmin() {
		return manageTeamMapList.size()>0;
	}
	public List<Map<String, Object>> getManageTeamMapList() {
		return manageTeamMapList;
	}
	public List<String> getAdminTableTitleList() {
		return adminTableTitleList;
	}
	public List<String> getForumTableTitleList() {
		return forumTableTitleList;
	}
	public List<ForumTableGroup> getForumTableGroupList() {
		return forumTableGroupList;
	}
	public void setForumTableGroupList(List<ForumTableGroup> forumTableGroupList) {
		this.forumTableGroupList = forumTableGroupList;
	}
	public Stats_navbarVO getNavbar() {
		return navbar;
	}
	public void setNavbar(Stats_navbarVO navbar) {
		this.navbar = navbar;
	}
}
