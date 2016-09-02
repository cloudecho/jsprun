package cn.jsprun.vo.logs;
public class InviteslogVO implements java.io.Serializable{
	private static final long serialVersionUID = -8249834658313461683L;
	private String username;
	private String buytime;
	private String termtime;
	private String buyIp;
	private String invitecode;
	private String status;
	public String getBuyIp() {
		return buyIp;
	}
	public void setBuyIp(String buyIp) {
		this.buyIp = buyIp;
	}
	public String getBuytime() {
		return buytime;
	}
	public void setBuytime(String buytime) {
		this.buytime = buytime;
	}
	public String getInvitecode() {
		return invitecode;
	}
	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTermtime() {
		return termtime;
	}
	public void setTermtime(String termtime) {
		this.termtime = termtime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
