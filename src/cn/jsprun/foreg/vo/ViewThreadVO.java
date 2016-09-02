package cn.jsprun.foreg.vo;
import java.util.List;
import java.util.Map;
@SuppressWarnings("unchecked")
public class ViewThreadVO {
	private String honor;
	private String avatars;
	private String signatures;
	private Map custominfo;
	private List medalslist;
	private List attaurl;
	private int onlineauthors = 0;
	private Map<String,String> usermap;
	private String stars;
	private String color;
	private String lastpostanchor;
	private String newpostanchor;
	private int ratings;   
	public int getRatings() {
		return ratings;
	}
	public void setRatings(int ratings) {
		this.ratings = ratings;
	}
	public String getLastpostanchor() {
		return lastpostanchor;
	}
	public void setLastpostanchor(String lastpostanchor) {
		this.lastpostanchor = lastpostanchor;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Map<String,String> getUsermap() {
		return usermap;
	}
	public void setUsermap(Map<String,String> usermap) {
		this.usermap = usermap;
	}
	public int getOnlineauthors() {
		return onlineauthors;
	}
	public void setOnlineauthors(int onlineauthors) {
		this.onlineauthors = onlineauthors;
	}
	public List getMedalslist() {
		return medalslist;
	}
	public void setMedalslist(List medalslist) {
		this.medalslist = medalslist;
	}
	public String getAvatars() {
		return avatars;
	}
	public void setAvatars(String avatars) {
		this.avatars = avatars;
	}
	public String getHonor() {
		return honor;
	}
	public void setHonor(String honor) {
		this.honor = honor;
	}
	public String getSignatures() {
		return signatures;
	}
	public void setSignatures(String signatures) {
		this.signatures = signatures;
	}
	public Map getCustominfo() {
		return custominfo;
	}
	public void setCustominfo(Map custominfo) {
		this.custominfo = custominfo;
	}
	public List getAttaurl() {
		return attaurl;
	}
	public void setAttaurl(List attaurl) {
		this.attaurl = attaurl;
	}
	public String getNewpostanchor() {
		return newpostanchor;
	}
	public void setNewpostanchor(String newpostanchor) {
		this.newpostanchor = newpostanchor;
	}
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
}
