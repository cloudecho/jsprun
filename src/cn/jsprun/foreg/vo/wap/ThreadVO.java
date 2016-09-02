package cn.jsprun.foreg.vo.wap;
import java.util.ArrayList;
import java.util.List;
public class ThreadVO extends WithFooterAndHead {
	private boolean viewThread = false;
	private boolean allowreply = false;
	private String subject = null;
	private String authorid = null;
	private String author = null;
	private String dateline = null;
	private String sid = null;
	private String formhash = null;
	private String threadposts = null;
	private boolean existNextPage = false;
	private boolean existLastPage = false;
	private int offset_next = 0;
	private int offset_last = 0;
	private String tid = null;
	private String fid = null;
	private String replies = null;
	private List<PostsInfo> postsList = new ArrayList<PostsInfo>();
	private PostsInfo postsInfo = null;
	private String wapmulti = null;
	public static class PostsInfo{
		private String pid = null;
		private int number = 1;
		private String message = null;
		private String author = null;
		private String dateline = null;
		private boolean anonymous = false;
		private String authorid = null;
		public String getAuthorid() {
			return authorid;
		}
		public void setAuthorid(String authorid) {
			this.authorid = authorid;
		}
		public boolean isAnonymous() {
			return anonymous;
		}
		public void setAnonymous(boolean anonymous) {
			this.anonymous = anonymous;
		}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
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
	public PostsInfo getPostsInfoInstance(){
		return new PostsInfo();
	}
	public String getThreadposts() {
		return threadposts;
	}
	public void setThreadposts(String threadposts) {
		this.threadposts = threadposts;
	}
	public boolean getExistNextPage() {
		return existNextPage;
	}
	public void setExistNextPage(boolean existNextPage) {
		this.existNextPage = existNextPage;
	}
	public boolean getExistLastPage() {
		return existLastPage;
	}
	public void setExistLastPage(boolean existLastPage) {
		this.existLastPage = existLastPage;
	}
	public int getOffset_next() {
		return offset_next;
	}
	public void setOffset_next(int offset_next) {
		this.offset_next = offset_next;
	}
	public int getOffset_last() {
		return offset_last;
	}
	public void setOffset_last(int offset_last) {
		this.offset_last = offset_last;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public boolean getViewThread() {
		return viewThread;
	}
	public void setViewThread(boolean viewThread) {
		this.viewThread = viewThread;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getAuthorid() {
		return authorid;
	}
	public void setAuthorid(String authorid) {
		this.authorid = authorid;
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
	public String getReplies() {
		return replies;
	}
	public void setReplies(String replies) {
		this.replies = replies;
	}
	public boolean getExistPosts() {
		return postsList.size()>0;
	}
	public List<PostsInfo> getPostsList() {
		return postsList;
	}
	public void setPostsList(List<PostsInfo> postsList) {
		this.postsList = postsList;
	}
	public String getWapmulti() {
		return wapmulti;
	}
	public void setWapmulti(String wapmulti) {
		this.wapmulti = wapmulti;
	}
	public PostsInfo getPostsInfo() {
		if(postsInfo == null){
			postsInfo = new PostsInfo();
		}
		return postsInfo;
	}
	public void setPostsInfo(PostsInfo postsInfo) {
		this.postsInfo = postsInfo;
	}
	public boolean getAllowreply() {
		return allowreply;
	}
	public void setAllowreply(boolean allowreply) {
		this.allowreply = allowreply;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getFormhash() {
		return formhash;
	}
	public void setFormhash(String formhash) {
		this.formhash = formhash;
	}
}
