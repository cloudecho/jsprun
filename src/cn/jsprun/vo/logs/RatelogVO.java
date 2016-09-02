package cn.jsprun.vo.logs;
import java.io.Serializable;
public class RatelogVO implements Serializable {
	private static final long serialVersionUID = -8941038734233723707L;
	private String firstUsername; 
	private String secondUsername; 
	private String operateTime; 
	private String usergroup; 
	private String markValue; 
	private String title; 
	private String reason; 
	private String extcredits; 
	private int tid=0; 
	private int uid; 
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getFirstUsername() {
		return firstUsername;
	}
	public void setFirstUsername(String firstUsername) {
		this.firstUsername = firstUsername;
	}
	public String getMarkValue() {
		return markValue;
	}
	public void setMarkValue(String markValue) {
		this.markValue = markValue;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSecondUsername() {
		return secondUsername;
	}
	public void setSecondUsername(String secondUsername) {
		this.secondUsername = secondUsername;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUsergroup() {
		return usergroup;
	}
	public void setUsergroup(String usergroup) {
		this.usergroup = usergroup;
	}
	public RatelogVO(String firstUsername, String secondUsername,
			String operateTime, String usergroup, String markValue,
			String title, String reason) {
		super();
		this.firstUsername = firstUsername;
		this.secondUsername = secondUsername;
		this.operateTime = operateTime;
		this.usergroup = usergroup;
		this.markValue = markValue;
		this.title = title;
		this.reason = reason;
	}
	public RatelogVO() {
	}
	public String getExtcredits() {
		return extcredits;
	}
	public void setExtcredits(String extcredits) {
		this.extcredits = extcredits;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
}
