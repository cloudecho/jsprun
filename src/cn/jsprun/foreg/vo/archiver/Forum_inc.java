package cn.jsprun.foreg.vo.archiver;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class Forum_inc extends WithHeaderAndFoot {
	private String redirect = null;
	private boolean refuse = false; 
	private String bbname = null; 
	private boolean navsub = false;
	private String qm = null;
	private Forum currentForum = null;
	private Forum superForum = null;
	private Integer start = null; 
	private List<Thread> threadList = new ArrayList<Thread>();
	private Map<String,String> fullversion = null;
	private Multi_inc multi_inc = null;
	public static class Thread{
		private String tid = null;
		private String subject = null;
		private String replies = null;
		public String getTid() {
			return tid;
		}
		public void setTid(String tid) {
			this.tid = tid;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getReplies() {
			return replies;
		}
		public void setReplies(String replies) {
			this.replies = replies;
		}
	}
	public Thread getThread(){
		return new Thread();
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
	public Integer getStart() {
		return start + 1;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public List<Thread> getThreadList() {
		return threadList;
	}
	public String getTitle() {
		if(fullversion==null){
			return "";
		}else{
			return fullversion.get("title");
		}
	}
	public String getLink() {
		if(fullversion==null){
			return "";
		}else{
			return fullversion.get("link");
		}
	}
	public void setFullversion(Map<String, String> fullversion) {
		this.fullversion = fullversion;
	}
	public String getRedirect() {
		return redirect;
	}
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	public void setCurrentForum(Forum currentForum) {
		this.currentForum = currentForum;
	}
	public void setSuperForum(Forum superForum) {
		this.superForum = superForum;
	}
	public Forum getCurrentForum() {
		return currentForum;
	}
	public Forum getSuperForum() {
		return superForum;
	}
	public Multi_inc getMulti_inc() {
		return multi_inc;
	}
	public void setMulti_inc(Multi_inc multi_inc) {
		this.multi_inc = multi_inc;
	}
}
