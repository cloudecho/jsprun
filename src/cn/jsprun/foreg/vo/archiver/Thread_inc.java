package cn.jsprun.foreg.vo.archiver;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class Thread_inc extends WithHeaderAndFoot {
	private boolean refuse = false; 
	private String bbname = null;
	private boolean navsub = false;
	private String qm = null;
	private Forum superForum = null;
	private Forum currentForum = null;
	private String threadSubject = null;
	private List<Posts> postsList = new ArrayList<Posts>();
	private Map<String,String> fullversion = null;
	private Multi_inc multi_inc = null;
	public static class Posts{
		private String author = null;
		private String dateline = null;
		private String message = null;
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
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
	public Posts getPosts(){
		return new Posts();
	}
	public boolean getRefuse() {
		return refuse;
	}
	public void setRefuse(boolean refuse) {
		this.refuse = refuse;
	}
	public String getBbname() {
		return bbname;
	}
	public void setBbname(String bbname) {
		this.bbname = bbname;
	}
	public boolean getNavsub() {
		return navsub;
	}
	public void setNavsub(boolean navsub) {
		this.navsub = navsub;
	}
	public String getQm() {
		return qm;
	}
	public void setQm(String qm) {
		this.qm = qm;
	}
	public Forum getSuperForum() {
		return superForum;
	}
	public void setSuperForum(Forum superForum) {
		this.superForum = superForum;
	}
	public Forum getCurrentForum() {
		return currentForum;
	}
	public void setCurrentForum(Forum currentForum) {
		this.currentForum = currentForum;
	}
	public String getThreadSubject() {
		return threadSubject;
	}
	public void setThreadSubject(String threadSubject) {
		this.threadSubject = threadSubject;
	}
	public List<Posts> getPostsList() {
		return postsList;
	}
	public String getLink() {
		if(fullversion!=null){
			return fullversion.get("link");
		}
		return "";
	}
	public String getTitle() {
		if(fullversion!=null){
			return fullversion.get("title");
		}
		return "";
	}
	public void setFullversion(Map<String, String> fullversion) {
		this.fullversion = fullversion;
	}
	public Multi_inc getMulti_inc() {
		return multi_inc;
	}
	public void setMulti_inc(Multi_inc multi_inc) {
		this.multi_inc = multi_inc;
	}
}
