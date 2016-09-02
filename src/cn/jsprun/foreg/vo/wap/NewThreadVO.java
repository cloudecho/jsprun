package cn.jsprun.foreg.vo.wap;
import java.util.Map;
public class NewThreadVO extends WithFooterAndHead {
	private Map threadtypes = null;
	private String fid = null;
	private String sid = null;
	private String formhash = null;
	public Map getThreadtypes() {
		return threadtypes;
	}
	public void setThreadtypes(Map threadtypes) {
		this.threadtypes = threadtypes;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getFormhash() {
		return formhash;
	}
	public void setFormhash(String formhash) {
		this.formhash = formhash;
	}
}
