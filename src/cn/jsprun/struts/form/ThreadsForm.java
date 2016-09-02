package cn.jsprun.struts.form;
import cn.jsprun.utils.FormDataCheck;
import org.apache.struts.action.ActionForm;
public class ThreadsForm extends ActionForm {
	private static final long serialVersionUID = -5595946869264046425L;
	private Integer detail = 0; 
	private Integer inforum = -1;
	private String starttime = null;
	private String endtime = null;
	private Boolean cins = false;
	private String users = null;
	private String keywords = null;
	private Integer intype = -1;
	private Integer viewsless = -1;
	private Integer viewsmore = -1;
	private Integer repliesless = -1;
	private Integer repliesmore = -1;
	private Integer readpermmore = -1;
	private Integer pricemore = -1;
	private Integer noreplydays = -1;
	private Integer specialthread = -1;
	private Integer[] special = null;
	private Integer sticky = -1;
	private Integer digest = -1;
	private Integer blog = -1;
	private Integer attach = -1;
	private Integer rate = -1;
	private Integer highlight = -1;
	public Boolean getCins() {
		return cins;
	}
	public void setCins(Boolean cins) {
		this.cins = cins;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public Integer getInforum() {
		return inforum;
	}
	public void setInforum(Integer inforum) {
		this.inforum = inforum;
	}
	public Integer getIntype() {
		return intype;
	}
	public void setIntype(Integer intype) {
		this.intype = intype;
	}
	public Integer getAttach() {
		return attach;
	}
	public void setAttach(Integer attach) {
		this.attach = attach;
	}
	public Integer getBlog() {
		return blog;
	}
	public void setBlog(Integer blog) {
		this.blog = blog;
	}
	public Integer getDigest() {
		return digest;
	}
	public void setDigest(Integer digest) {
		this.digest = digest;
	}
	public Integer getHighlight() {
		return highlight;
	}
	public void setHighlight(Integer highlight) {
		this.highlight = highlight;
	}
	public Integer getNoreplydays() {
		return noreplydays;
	}
	public void setNoreplydays(Integer noreplydays) {
		this.noreplydays = noreplydays;
	}
	public Integer getPricemore() {
		return pricemore;
	}
	public void setPricemore(Integer pricemore) {
		this.pricemore = pricemore;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public Integer getReadpermmore() {
		return readpermmore;
	}
	public void setReadpermmore(Integer readpermmore) {
		this.readpermmore = readpermmore;
	}
	public Integer getRepliesless() {
		return repliesless;
	}
	public void setRepliesless(Integer repliesless) {
		this.repliesless = repliesless;
	}
	public Integer getRepliesmore() {
		return repliesmore;
	}
	public void setRepliesmore(Integer repliesmore) {
		this.repliesmore = repliesmore;
	}
	public Integer getSticky() {
		return sticky;
	}
	public void setSticky(Integer sticky) {
		this.sticky = sticky;
	}
	public Integer getViewsless() {
		return viewsless;
	}
	public void setViewsless(Integer viewsless) {
		this.viewsless = viewsless;
	}
	public Integer getViewsmore() {
		return viewsmore;
	}
	public void setViewsmore(Integer viewsmore) {
		this.viewsmore = viewsmore;
	}
	public Integer[] getSpecial() {
		return special;
	}
	public void setSpecial(Integer[] special) {
		this.special = special;
	}
	public void setSpecialthread(Integer specialthread) {
		this.specialthread = specialthread;
	}
	public Integer getSpecialthread() {
		return specialthread;
	}
	public Integer getDetail() {
		return detail;
	}
	public void setDetail(Integer detail) {
		this.detail = detail;
	}
	public boolean isFindBy() {
		if (inforum <= 0 && !FormDataCheck.isValueString(starttime)
				&& !FormDataCheck.isValueString(endtime)
				&& !FormDataCheck.isValueString(users)
				&& !FormDataCheck.isValueString(keywords) && sticky <= 0
				&& digest <=0 && attach <= 0 && blog <= 0 && rate <= 0
				&& highlight <= 0) {
			return false;
		}
		return true;
	}
}
