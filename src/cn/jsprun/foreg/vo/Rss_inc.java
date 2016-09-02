package cn.jsprun.foreg.vo;
import java.util.ArrayList;
import java.util.List;
public class Rss_inc {
	private boolean notAccess = false;
	private boolean accessIndex = true;
	private boolean forumError = false;
	private String bbname = null;
	private String boardurl = null;
	private String indexname = null;
	private String num = null;
	private String forumname = null;
	private String rssfid = null;
	private String nowTime = null;
	private String ttl = null;
	private List<Thread> threadList = new ArrayList<Thread>();
	public static class Thread{
		private String subject = null;
		private String tid = null;
		private String description = null;
		private String forum = null;
		private String author = null;
		private String dateline = null;
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getTid() {
			return tid;
		}
		public void setTid(String tid) {
			this.tid = tid;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getForum() {
			return forum;
		}
		public void setForum(String forum) {
			this.forum = forum;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getDateline() {
			return dateline;
		}
		public void setDateline(String dateline) {
			this.dateline = dateline;
		}
	}
	public Thread getThread(){
		return new Thread();
	}
	public boolean getAccessIndex() {
		return accessIndex;
	}
	public void setAccessIndex(boolean accessIndex) {
		this.accessIndex = accessIndex;
	}
	public String getBbname() {
		return bbname;
	}
	public void setBbname(String bbname) {
		this.bbname = bbname;
	}
	public String getBoardurl() {
		return boardurl;
	}
	public void setBoardurl(String boardurl) {
		this.boardurl = boardurl;
	}
	public String getIndexname() {
		return indexname;
	}
	public void setIndexname(String indexname) {
		this.indexname = indexname;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getForumname() {
		return forumname;
	}
	public void setForumname(String forumname) {
		this.forumname = forumname;
	}
	public String getRssfid() {
		return rssfid;
	}
	public void setRssfid(String rssfid) {
		this.rssfid = rssfid;
	}
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	public String getTtl() {
		return ttl;
	}
	public void setTtl(String ttl) {
		this.ttl = ttl;
	}
	public List<Thread> getThreadList() {
		return threadList;
	}
	public boolean getNotAccess() {
		return notAccess;
	}
	public void setNotAccess(boolean notAccess) {
		this.notAccess = notAccess;
	}
	public boolean getForumError() {
		return forumError;
	}
	public void setForumError(boolean forumError) {
		this.forumError = forumError;
	}
}
