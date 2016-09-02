package cn.jsprun.struts.form;
import org.apache.struts.action.ActionForm;
public class AttachmentsForm extends ActionForm {
	private static final long serialVersionUID = -7762719691071446533L;
	private Byte nomatched = 0;
	private Integer inforum = -1;
	private Integer sizeless = -1;
	private Integer sizemore = -1;
	private Integer dlcountless = -1;
	private Integer dlcountmore = -1;
	private Integer daysold = -1;
	private String filename = null;
	private String keywords = null;
	private String author = null;
	public AttachmentsForm() {
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getDaysold() {
		return daysold;
	}
	public void setDaysold(Integer daysold) {
		this.daysold = daysold;
	}
	public Integer getDlcountless() {
		return dlcountless;
	}
	public void setDlcountless(Integer dlcountless) {
		this.dlcountless = dlcountless;
	}
	public Integer getDlcountmore() {
		return dlcountmore;
	}
	public void setDlcountmore(Integer dlcountmore) {
		this.dlcountmore = dlcountmore;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer getInforum() {
		return inforum;
	}
	public void setInforum(Integer inforum) {
		this.inforum = inforum;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Integer getSizeless() {
		return sizeless;
	}
	public void setSizeless(Integer sizeless) {
		this.sizeless = sizeless;
	}
	public Integer getSizemore() {
		return sizemore;
	}
	public void setSizemore(Integer sizemore) {
		this.sizemore = sizemore;
	}
	public Byte getNomatched() {
		return nomatched;
	}
	public void setNomatched(Byte nomatched) {
		this.nomatched = nomatched;
	}
}
