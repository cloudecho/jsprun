package cn.jsprun.foreg.vo.topicadmin;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class BaseVO {
	private String fid;	
	private String forumName; 
	private List<Map<String,String>> fid_NameMapList = new ArrayList<Map<String,String>>();
	private String topicId;	
	private String topicName;  
	private String pageInfo;
	private boolean showTopicName = false;  
	private boolean singleThread;	
	private List<String> reasonList = new ArrayList<String>();
	private boolean necesseryInfo = false; 
	private boolean necessaryToSendMessage = false;  
	private String minTime; 
	private String maxTime;
	private String threadId;
	private boolean showLogList;
	private List<Log> logList = new ArrayList<Log>(); 
	private List<ThreadInfo> threadInfoList = new ArrayList<ThreadInfo>(); 
	public static class Log{
		private String uid;
		private String username;
		private String operationTime;
		private String css;
		private String operation;
		private String expiretion;
		private Log(){
		}
		public String getCss() {
			return css;
		}
		public void setCss(String css) {
			this.css = css;
		}
		public String getExpiretion() {
			return expiretion;
		}
		public void setExpiretion(String expiretion) {
			this.expiretion = expiretion;
		}
		public String getOperation() {
			return operation;
		}
		public void setOperation(String operation) {
			this.operation = operation;
		}
		public String getOperationTime() {
			return operationTime;
		}
		public void setOperationTime(String operationTime) {
			this.operationTime = operationTime;
		}
		public boolean isShowUsername() {
			if(uid==null||uid.equals("")){
				return false;
			}else{
				return true;
			}
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
	}
	public static class ThreadInfo{
		private String threadId;
		private String pageNumber;
		private String title; 
		private String authorId;
		private String authorName;
		private String replies; 
		private String lastpost; 
		private String lastPosterName; 
		private ThreadInfo(){
		}
		public String getAuthorId() {
			return authorId;
		}
		public void setAuthorId(String authorId) {
			this.authorId = authorId;
		}
		public String getAuthorName() {
			return authorName;
		}
		public void setAuthorName(String authorName) {
			this.authorName = authorName;
		}
		public String getLastpost() {
			return lastpost;
		}
		public void setLastpost(String lastpost) {
			this.lastpost = lastpost;
		}
		public String getLastPosterName() {
			return lastPosterName;
		}
		public void setLastPosterName(String lastPosterName) {
			this.lastPosterName = lastPosterName;
		}
		public String getPageNumber() {
			return pageNumber;
		}
		public void setPageNumber(String pageNumber) {
			this.pageNumber = pageNumber;
		}
		public String getReplies() {
			return replies;
		}
		public void setReplies(String replies) {
			this.replies = replies;
		}
		public boolean isShowAuthor() {
			if(authorId==null||authorId.equals("")||authorId.equals("0")){
				return false;
			}else{
				return true;
			}
		}
		public boolean isShowLastPoster() {
			if(lastPosterName==null||lastPosterName.equals("")){
				return false;
			}
			return true;
		}
		public String getThreadId() {
			return threadId;
		}
		public void setThreadId(String threadId) {
			this.threadId = threadId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
	}
	public ThreadInfo geThreadInfo(){
		return new ThreadInfo();
	}
	public Log getLog(){
		return new Log();
	}
	public List<Log> getLogList() {
		return logList;
	}
	public boolean isShowLogList() {
		return showLogList;
	}
	public void setShowLogList(boolean showLogList) {
		this.showLogList = showLogList;
	}
	public boolean isSingleThread() {
		return singleThread;
	}
	public void setSingleThread(boolean singleThread) {
		this.singleThread = singleThread;
	}
	public String getThreadId() {
		return threadId;
	}
	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	public List<ThreadInfo> getThreadInfoList() {
		return threadInfoList;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getForumName() {
		return forumName;
	}
	public void setForumName(String forumName) {
		this.forumName = forumName;
	}
	public List<String> getReasonList() {
		return reasonList;
	}
	public boolean isNecesseryInfo() {
		return necesseryInfo;
	}
	public void setNecesseryInfo(boolean necesseryInfo) {
		this.necesseryInfo = necesseryInfo;
	}
	public boolean isNecessaryToSendMessage() {
		return necessaryToSendMessage;
	}
	public void setNecessaryToSendMessage(boolean necessaryToSendMessage) {
		this.necessaryToSendMessage = necessaryToSendMessage;
	}
	public String getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}
	public String getMinTime() {
		return minTime;
	}
	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public boolean isShowTopicName() {
		return showTopicName;
	}
	public void setShowTopicName(boolean showTopicName) {
		this.showTopicName = showTopicName;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(String pageInfo) {
		this.pageInfo = pageInfo;
	}
	public List<Map<String, String>> getFid_NameMapList() {
		return fid_NameMapList;
	}
}
