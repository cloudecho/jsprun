package cn.jsprun.struts.form;
import org.apache.struts.action.ActionForm;
public class PostsForm extends ActionForm {
	private static final long serialVersionUID = 1685250449032061435L;
	private int pid;
	private short fid = -1;
	private String filter = "normal";
	public PostsForm() {
	}
	public PostsForm(Short fid) {
		this.fid = fid;
	}
	public Short getFid() {
		return fid;
	}
	public void setFid(Short fid) {
		this.fid = fid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
}
