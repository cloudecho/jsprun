package cn.jsprun.vo.otherset;
import java.io.Serializable;
public class FaqsVO implements Serializable {
	private Short id; 
	private Short fpid; 
	private Short displayorder; 
	private String identifier; 
	private String keyword; 
	private String title; 
	private String message; 
	private boolean ableToDelete; 
	public FaqsVO() {
	}
	public FaqsVO(Short fpid, Short displayorder, String identifier,
			String keyword, String title, String message, boolean ableToDelete) {
		this.fpid = fpid;
		this.displayorder = displayorder;
		this.identifier = identifier;
		this.keyword = keyword;
		this.title = title;
		this.message = message;
		this.ableToDelete = ableToDelete;
	}
	public Short getId() {
		return this.id;
	}
	public void setId(Short id) {
		this.id = id;
	}
	public Short getFpid() {
		return this.fpid;
	}
	public void setFpid(Short fpid) {
		this.fpid = fpid;
	}
	public Short getDisplayorder() {
		return this.displayorder;
	}
	public void setDisplayorder(Short displayorder) {
		this.displayorder = displayorder;
	}
	public String getIdentifier() {
		return this.identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getKeyword() {
		return this.keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isAbleToDelete() {
		return ableToDelete;
	}
	public void setAbleToDelete(boolean ableToDelete) {
		this.ableToDelete = ableToDelete;
	}
}
