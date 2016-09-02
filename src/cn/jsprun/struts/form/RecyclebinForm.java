package cn.jsprun.struts.form;
import org.apache.struts.action.ActionForm;
public class RecyclebinForm extends ActionForm {
	private static final long serialVersionUID = 3731032372153696517L;
	private Short inforum = -1;
	private String authors = null;
	private String keywords = null;
	private String admins = null;
	private String pstarttime = null;
	private String pendtime = null;
	private String mstarttime = null;
	private String mendtime = null;
	public RecyclebinForm() {
	}
	public String getAdmins() {
		return admins;
	}
	public void setAdmins(String admins) {
		this.admins = admins;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public Short getInforum() {
		return inforum;
	}
	public void setInforum(Short inforum) {
		this.inforum = inforum;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getMendtime() {
		return mendtime;
	}
	public void setMendtime(String mendtime) {
		this.mendtime = mendtime;
	}
	public String getMstarttime() {
		return mstarttime;
	}
	public void setMstarttime(String mstarttime) {
		this.mstarttime = mstarttime;
	}
	public String getPendtime() {
		return pendtime;
	}
	public void setPendtime(String pendtime) {
		this.pendtime = pendtime;
	}
	public String getPstarttime() {
		return pstarttime;
	}
	public void setPstarttime(String pstarttime) {
		this.pstarttime = pstarttime;
	}
}
