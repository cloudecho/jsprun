package cn.jsprun.vo.space;
public class UserInfoVO {
	private String username;
	private String avoras;
	private int width;
	private int height;
	private String bios;
	private boolean isonline;
	public boolean isIsonline() {
		return isonline;
	}
	public void setIsonline(boolean isonline) {
		this.isonline = isonline;
	}
	public String getAvoras() {
		return avoras;
	}
	public void setAvoras(String avoras) {
		this.avoras = avoras;
	}
	public String getBios() {
		return bios;
	}
	public void setBios(String bios) {
		this.bios = bios;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
}
