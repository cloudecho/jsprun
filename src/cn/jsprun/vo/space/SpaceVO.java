package cn.jsprun.vo.space;
public class SpaceVO {
	private String subject;
	private int tid;
	private int fid;
	private String forums;
	private String message;
	private String operdate;
	private boolean isnew;
	private int viewnum;
	private int replicenum;
	private String rewards;
	private boolean isattc;
	private String price;
	private String special;
	private int pid;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public boolean isIsattc() {
		return isattc;
	}
	public void setIsattc(boolean isattc) {
		this.isattc = isattc;
	}
	public String getRewards() {
		return rewards;
	}
	public void setRewards(String rewards) {
		this.rewards = rewards;
	}
	public int getReplicenum() {
		return replicenum;
	}
	public void setReplicenum(int replicenum) {
		this.replicenum = replicenum;
	}
	public int getViewnum() {
		return viewnum;
	}
	public void setViewnum(int viewnum) {
		this.viewnum = viewnum;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getForums() {
		return forums;
	}
	public void setForums(String forums) {
		this.forums = forums;
	}
	public boolean isIsnew() {
		return isnew;
	}
	public void setIsnew(boolean isnew) {
		this.isnew = isnew;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOperdate() {
		return operdate;
	}
	public void setOperdate(String operdate) {
		this.operdate = operdate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
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
