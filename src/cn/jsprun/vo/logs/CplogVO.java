package cn.jsprun.vo.logs;
import java.io.Serializable;
public class CplogVO implements Serializable {
	public CplogVO() {
	}
	private static final long serialVersionUID = -3110620015545019215L;
	private String username;
	private String operaterDate;
	private String usergroups;
	private String ipAddress;
	private String others;
	private String operater;
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	public String getOperaterDate() {
		return operaterDate;
	}
	public void setOperaterDate(String operaterDate) {
		this.operaterDate = operaterDate;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public String getUsergroups() {
		return usergroups;
	}
	public void setUsergroups(String usergroups) {
		this.usergroups = usergroups;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
