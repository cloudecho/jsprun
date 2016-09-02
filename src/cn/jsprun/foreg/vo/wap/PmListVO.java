package cn.jsprun.foreg.vo.wap;
import java.util.ArrayList;
import java.util.List;
public class PmListVO extends WithFooterAndHead {
	private String wapmulti = "";
	private List<PmInfo> pmInfoList = new ArrayList<PmInfo>();
	public static class PmInfo{
		private String pmid  = null;
		private int number = 0;
		private boolean unread = false;
		private String subject = null;
		private String dateline = null;
		private String msgfrom = null;
		public String getPmid() {
			return pmid;
		}
		public void setPmid(String pmid) {
			this.pmid = pmid;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public boolean getUnread() {
			return unread;
		}
		public void setUnread(boolean unread) {
			this.unread = unread;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getDateline() {
			return dateline;
		}
		public void setDateline(String dateline) {
			this.dateline = dateline;
		}
		public String getMsgfrom() {
			return msgfrom;
		}
		public void setMsgfrom(String msgfrom) {
			this.msgfrom = msgfrom;
		}
	}
	public PmInfo getPmInfo(){
		return new PmInfo();
	}
	public String getWapmulti() {
		return wapmulti;
	}
	public void setWapmulti(String wapmulti) {
		this.wapmulti = wapmulti;
	}
	public List<PmInfo> getPmInfoList() {
		return pmInfoList;
	}
	public boolean getShowList(){
		return pmInfoList!=null && pmInfoList.size()>0;
	}
}
