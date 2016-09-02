package cn.jsprun.foreg.vo.wap;
public class FooterVO {
	private String time = null;
	private boolean isNotHome = false; 
	private boolean isLogin = false;
	private String formhash = null;
	private String userName = null;
	private String sid = null;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getFormhash() {
		return formhash;
	}
	public void setFormhash(String formhash) {
		this.formhash = formhash;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean getIsNotHome() {
		return isNotHome;
	}
	public void setIsNotHome(boolean isNotHome) {
		this.isNotHome = isNotHome;
	}
	public boolean getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
}
