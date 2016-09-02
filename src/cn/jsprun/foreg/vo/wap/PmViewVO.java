package cn.jsprun.foreg.vo.wap;
public class PmViewVO extends WithFooterAndHead {
	private boolean existPm = false;
	private String subject = null;
	private String msgfrom = null;
	private String dateline = null;
	private String message = null;
	private String pmid = null;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMsgfrom() {
		return msgfrom;
	}
	public void setMsgfrom(String msgfrom) {
		this.msgfrom = msgfrom;
	}
	public String getDateline() {
		return dateline;
	}
	public void setDateline(String dateline) {
		this.dateline = dateline;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPmid() {
		return pmid;
	}
	public void setPmid(String pmid) {
		this.pmid = pmid;
	}
	public boolean getExistPm() {
		return existPm;
	}
	public void setExistPm(boolean existPm) {
		this.existPm = existPm;
	}
}
