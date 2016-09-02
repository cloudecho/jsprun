package cn.jsprun.struts.form;
import org.apache.struts.action.ActionForm;
import cn.jsprun.utils.FormDataCheck;
public class PruneForm extends ActionForm {
	private static final long serialVersionUID = -7536893559120788928L;
	public PruneForm() {
	}
	private Integer detail = 0;
	private Integer forums = -1;
	private String starttime = null;
	private String endtime = null;
	private Integer cins = -1;
	private String users = null;
	private String useip = null;
	private String keywords = null;
	private String lengthlimit = null;
	private String fid = null;
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public Integer getCins() {
		return cins;
	}
	public void setCins(Integer cins) {
		this.cins = cins;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public Integer getForums() {
		return forums;
	}
	public void setForums(Integer forums) {
		this.forums = forums;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getLengthlimit() {
		return lengthlimit;
	}
	public void setLengthlimit(String lengthlimit) {
		this.lengthlimit = lengthlimit;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getUseip() {
		return useip;
	}
	public void setUseip(String useip) {
		this.useip = useip;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public boolean isForm() {
		if (FormDataCheck.isValueString(starttime)
				|| FormDataCheck.isValueString(endtime)) {
			if (FormDataCheck.isValueString(keywords)
					|| FormDataCheck.isNum(lengthlimit)
					|| FormDataCheck.isValueString(useip)
					|| FormDataCheck.isValueString(users)) {
				return true;
			}
		}
		return false;
	}
	public Integer getDetail() {
		return detail;
	}
	public void setDetail(Integer detail) {
		this.detail = detail;
	}
}
