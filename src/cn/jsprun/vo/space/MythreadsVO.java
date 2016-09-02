package cn.jsprun.vo.space;
public class MythreadsVO {
	private String tid;
	private String subjcet;
	private String fid;
	private String forums;
	private int viewnum;
	private int replaynum;
	private int lastpost;
	private String lastposter;
	private String rewards;
	private boolean isnew;
	private boolean isattc;
	private String price;
	private String special;
	public boolean isIsattc() {
		return isattc;
	}
	public void setIsattc(boolean isattc) {
		this.isattc = isattc;
	}
	public boolean isIsnew() {
		return isnew;
	}
	public void setIsnew(boolean isnew) {
		this.isnew = isnew;
	}
	public String getRewards() {
		return rewards;
	}
	public void setRewards(String rewards) {
		this.rewards = rewards;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getForums() {
		return forums;
	}
	public void setForums(String forums) {
		this.forums = forums;
	}
	public int getLastpost() {
		return lastpost;
	}
	public void setLastpost(int lastpost) {
		this.lastpost = lastpost;
	}
	public String getLastposter() {
		return lastposter;
	}
	public void setLastposter(String lastposter) {
		this.lastposter = lastposter;
	}
	public int getReplaynum() {
		return replaynum;
	}
	public void setReplaynum(int replaynum) {
		this.replaynum = replaynum;
	}
	public String getSubjcet() {
		return subjcet;
	}
	public void setSubjcet(String subjcet) {
		this.subjcet = subjcet;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public int getViewnum() {
		return viewnum;
	}
	public void setViewnum(int viewnum) {
		this.viewnum = viewnum;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
}
