package cn.jsprun.vo.logs;
public class ModslogVO implements java.io.Serializable {
	private static final long serialVersionUID = -1313531715054976685L;
	private String username;
	private String usergroup;
	private String ipaddress;
	private String opaterDate;
	private String forum;
	private String forumid;
	private String thread;
	private String threadid;
	private String opertar;
	private String reason;
	public ModslogVO(){
	}
	public String getForum() {
		return forum;
	}
	public void setForum(String forum) {
		this.forum = forum;
	}
	public String getForumid() {
		return forumid;
	}
	public void setForumid(String forumid) {
		this.forumid = forumid;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getOpaterDate() {
		return opaterDate;
	}
	public void setOpaterDate(String opaterDate) {
		this.opaterDate = opaterDate;
	}
	public String getOpertar() {
		return opertar;
	}
	public void setOpertar(String opertar) {
		this.opertar = opertar;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	public String getThreadid() {
		return threadid;
	}
	public void setThreadid(String threadid) {
		this.threadid = threadid;
	}
	public String getUsergroup() {
		return usergroup;
	}
	public void setUsergroup(String usergroup) {
		this.usergroup = usergroup;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
