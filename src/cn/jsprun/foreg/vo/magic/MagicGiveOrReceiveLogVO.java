package cn.jsprun.foreg.vo.magic;
public class MagicGiveOrReceiveLogVO extends MagicLogShowMagicNumberVO{
	private String username;
	private String userId;
	private String contextPath;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUrlOnUsername() {
		return contextPath+"/space.jsp?action=viewpro&uid="+userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
}
