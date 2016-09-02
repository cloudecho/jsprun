package cn.jsprun.vo.otherset;
public class AdvVO {
	private String selectContent = null;
	private String style = null;
	private String title = null;
	private String type = null;
	private Integer postperpage = null;
	private String starttime = null;
	public String getExplain() {
		if(type==null){
			return "";
		}else if(type.equals("intercat")){
			return "advertisements_type_intercat_tips";
		}else if(type.equals("couplebanner")){
			return "advertisements_type_couplebanner_tips";
		}else if(type.equals("float")){
			return "advertisements_type_float_tips";
		}else if(type.equals("interthread")){
			return "advertisements_type_interthread_tips";
		}else if(type.equals("thread")){
			return "advertisements_type_thread_tips";
		}else if(type.equals("text")){
			return "advertisements_type_text_tips";
		}else if(type.equals("footerbanner")){
			return "advertisements_type_footerbanner_tips";
		}else if(type.equals("headerbanner")){
			return "advertisements_type_headerbanner_tips";
		}else{
			return "";
		}
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public Integer getPostperpage() {
		return postperpage;
	}
	public void setPostperpage(Integer postperpage) {
		this.postperpage = postperpage;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSelectContent() {
		return selectContent;
	}
	public void setSelectContent(String selectContent) {
		this.selectContent = selectContent;
	}
}
