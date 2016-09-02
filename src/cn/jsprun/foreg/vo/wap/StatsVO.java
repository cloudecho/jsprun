package cn.jsprun.foreg.vo.wap;
public class StatsVO extends WithFooterAndHead {
	private String members = null;
	private String threads = null;
	private String posts = null;
	public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public String getThreads() {
		return threads;
	}
	public void setThreads(String threads) {
		this.threads = threads;
	}
	public String getPosts() {
		return posts;
	}
	public void setPosts(String posts) {
		this.posts = posts;
	}
}
