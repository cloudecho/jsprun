package cn.jsprun.vo.logs;
import java.io.Serializable;
public class IllegallogVO implements Serializable{
	private static final long serialVersionUID = 2338400560304132990L;
	private String username;
	private String pssword;
	private String ipAddress;
	private String aiquwenda;
	private String datetimes;
	public String getDatetimes() {
		return datetimes;
	}
	public void setDatetimes(String datetimes) {
		this.datetimes = datetimes;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getPssword() {
		return pssword;
	}
	public void setPssword(String pssword) {
		this.pssword = pssword;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAiquwenda() {
		return aiquwenda;
	}
	public void setAiquwenda(String aiquwenda) {
		this.aiquwenda = aiquwenda;
	}
}
