package cn.jsprun.foreg.vo.topicadmin;
import java.util.ArrayList;
import java.util.List;
public class TopicPublicVO {
	private String fid;	
	private String forumName;	
	private String topicId;	
	private String topicName;	
	private boolean beingSupperForum = false;
	private String supperForumName;
	private String supperFid;
	private List<String> reasonList = new ArrayList<String>();
	private boolean necesseryInfo = false;
	private boolean necessaryToSendMessage = false;
	public boolean isNecessaryToSendMessage() {
		return necessaryToSendMessage;
	}
	public void setNecessaryToSendMessage(boolean necessaryToSendMessage) {
		this.necessaryToSendMessage = necessaryToSendMessage;
	}
	public boolean isNecesseryInfo() {
		return necesseryInfo;
	}
	public void setNecesseryInfo(boolean necesseryInfo) {
		this.necesseryInfo = necesseryInfo;
	}
	public List<String> getReasonList() {
		return reasonList;
	}
	public void setReasonList(List<String> reasonList) {
		this.reasonList = reasonList;
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
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public boolean isBeingSupperForum() {
		return beingSupperForum;
	}
	public void setBeingSupperForum(boolean beingSupperForum) {
		this.beingSupperForum = beingSupperForum;
	}
	public String getSupperFid() {
		return supperFid;
	}
	public void setSupperFid(String supperFid) {
		this.supperFid = supperFid;
	}
	public String getSupperForumName() {
		return supperForumName;
	}
	public void setSupperForumName(String supperForumName) {
		this.supperForumName = supperForumName;
	}
}
