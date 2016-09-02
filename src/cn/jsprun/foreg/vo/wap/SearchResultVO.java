package cn.jsprun.foreg.vo.wap;
import java.util.ArrayList;
import java.util.List;
public class SearchResultVO extends WithFooterAndHead {
	private String multipage = null;
	private List<ThreadInfo> threadInfoList = new ArrayList<ThreadInfo>();
	private static class ThreadInfo_ implements ThreadInfo{
		private String tid = null;
		private String number = null;
		private String subject = null;
		private String views = null;
		private String replies = null;
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
		public String getViews() {
			return views;
		}
		public void setViews(String views) {
			this.views = views;
		}
		public String getReplies() {
			return replies;
		}
		public void setReplies(String replies) {
			this.replies = replies;
		}
		public String getAuthor() {
			return null;
		}
		public String getPrefix() {
			return null;
		}
		public void setAuthor(String author) {
		}
		public void setPrefix(String prefix) {
		}
	}
	public ThreadInfo geThreadInfo(){
		return new ThreadInfo_();
	}
	public String getMultipage() {
		return multipage;
	}
	public void setMultipage(String multipage) {
		this.multipage = multipage;
	}
	public List<ThreadInfo> getThreadInfoList() {
		return threadInfoList;
	}	
}
