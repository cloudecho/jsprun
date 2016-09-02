package cn.jsprun.foreg.vo.topicadmin;
import java.util.ArrayList;
import java.util.List;
public class OtherBaseVO extends TopicPublicVO {
	private String currentPage;	
	private String threadPage; 
	private String sbuttonInfo; 
	private List<String> postsIdList = new ArrayList<String>(); 
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public List<String> getPostsIdList() {
		return postsIdList;
	}
	public void setPostsIdList(List<String> postsIdList) {
		this.postsIdList = postsIdList;
	}
	public String getSbuttonInfo() {
		return sbuttonInfo;
	}
	public void setSbuttonInfo(String sbuttonInfo) {
		this.sbuttonInfo = sbuttonInfo;
	}
	public String getThreadPage() {
		return threadPage;
	}
	public void setThreadPage(String threadPage) {
		this.threadPage = threadPage;
	}
}
