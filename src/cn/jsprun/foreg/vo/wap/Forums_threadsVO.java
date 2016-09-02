package cn.jsprun.foreg.vo.wap;
import java.util.ArrayList;
import java.util.List;
public class Forums_threadsVO extends WithFooterAndHead {
	private boolean showForum = false;
	private List<ForumInfo> forumList = new ArrayList<ForumInfo>();
	private String multipage = null;
	private String forumName = null;
	private String forumId = null;
	private String subfrums = null;
	private boolean allowsearch = false;
	private List<ThreadInfo> threadList = new ArrayList<ThreadInfo>();
	private static class ForumInfoImp implements ForumInfo{
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
	private static class ThreadInfoImp implements ThreadInfo{
		private String tid = null;
		private String number = null;
		private String subject = null;
		private String prefix = null;
		private String author = null;
		private String replies = null;
		private String views = null;
		public String getTid() {
			return tid;
		}
		public void setTid(String tid) {
			this.tid = tid;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getPrefix() {
			return prefix;
		}
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getReplies() {
			return replies;
		}
		public void setReplies(String replies) {
			this.replies = replies;
		}
		public String getViews() {
			return views;
		}
		public void setViews(String views) {
			this.views = views;
		}
	}
	public ForumInfo getForumInfo(){
		return new ForumInfoImp();
	}
	public ThreadInfo getThreadInfo(){
		return new ThreadInfoImp();
	}
	public boolean getShowForum() {
		return showForum;
	}
	public boolean getAllowsearch() {
		return allowsearch;
	}
	public void setAllowsearch(boolean allowsearch) {
		this.allowsearch = allowsearch;
	}
	public void setShowForum(boolean showForum) {
		this.showForum = showForum;
	}
	public List<ForumInfo> getForumList() {
		return forumList;
	}
	public void setForumList(List<ForumInfo> forumList) {
		this.forumList = forumList;
	}
	public String getMultipage() {
		return multipage;
	}
	public void setMultipage(String multipage) {
		this.multipage = multipage;
	}
	public String getForumName() {
		return forumName;
	}
	public void setForumName(String forumName) {
		this.forumName = forumName;
	}
	public String getForumId() {
		return forumId;
	}
	public void setForumId(String forumId) {
		this.forumId = forumId;
	}
	public List<ThreadInfo> getThreadList() {
		return threadList;
	}
	public void setThreadList(List<ThreadInfo> threadList) {
		this.threadList = threadList;
	}
	public String getSubfrums() {
		return subfrums;
	}
	public void setSubfrums(String subfrums) {
		this.subfrums = subfrums;
	}
}
