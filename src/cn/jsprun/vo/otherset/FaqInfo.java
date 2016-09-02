package cn.jsprun.vo.otherset;
public class FaqInfo {
	private String id = null; 
	private String displayorder = null; 
	private String title = null; 
	private String topperTitle = null;
	private boolean ableToDelete = false; 
	private boolean topper = false;
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isAbleToDelete() {
		return ableToDelete;
	}
	public void setAbleToDelete(boolean ableToDelete) {
		this.ableToDelete = ableToDelete;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisplayorder() {
		return displayorder;
	}
	public void setDisplayorder(String displayorder) {
		this.displayorder = displayorder;
	}
	public String getTopperTitle() {
		return topperTitle;
	}
	public void setTopperTitle(String topperTitle) {
		this.topperTitle = topperTitle;
	}
	public boolean isTopper() {
		return topper;
	}
	public void setTopper(boolean topper) {
		this.topper = topper;
	}
}
