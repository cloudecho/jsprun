package cn.jsprun.foreg.vo.wap;
public class MyVO extends WithFooterAndHead {
	private String uid = null;
	private String username = null;
	private String gender = null;
	private String birthday = null;
	private String location = null;
	private String bio = null;
	private boolean sameMember = false;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public boolean getShowBirthday() {
		return birthday != null;
	}
	public boolean getShowLocation() {
		return location != null;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean getShowBio() {
		return bio != null;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public boolean isSameMember() {
		return sameMember;
	}
	public void setSameMember(boolean sameMember) {
		this.sameMember = sameMember;
	}
}
