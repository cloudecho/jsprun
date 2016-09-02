package cn.jsprun.foreg.vo.wap;
public class LoginVO extends WithFooterAndHead {
	private String sid = null;
	private boolean failedAnswer = false;
	private String username = null;
	private String loginauth = null;
	public String getLoginauth() {
		return loginauth;
	}
	public void setLoginauth(String loginauth) {
		this.loginauth = loginauth;
	}
	public boolean getFailedAnswer() {
		return failedAnswer;
	}
	public void setFailedAnswer(boolean failedAnswer) {
		this.failedAnswer = failedAnswer;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
}
