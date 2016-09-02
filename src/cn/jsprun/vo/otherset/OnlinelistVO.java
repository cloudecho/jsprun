package cn.jsprun.vo.otherset;
import java.io.Serializable;
public class OnlinelistVO implements Serializable {
	private static final long serialVersionUID = -3405153896733608964L;
	private String groupid;
	private String displayorder;
	private String title;
	private String quondamTitle;
	private String url;
	public boolean isExistent() {
		return groupid.equals("0")||(url!=null&&!url.equals(""));
	}
	public boolean isShowImage() {
		if(url.equals("")){
			return false;
		}else{
			return true;
		}
	}
	public String getDisplayorder() {
		return displayorder;
	}
	public void setDisplayorder(String displayorder) {
		this.displayorder = displayorder;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getQuondamTitle() {
		return quondamTitle;
	}
	public void setQuondamTitle(String quondamTitle) {
		this.quondamTitle = quondamTitle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
