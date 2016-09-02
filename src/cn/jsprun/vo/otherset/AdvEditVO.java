package cn.jsprun.vo.otherset;
public class AdvEditVO {
	private String advid = null;			
	private String title = null;			
	private String type = null;				
	private String starttime = null;		
	private String endtime = null;			
	private String parameters_style = null;	
	private String selectContent = null;	
	private String parameters_position = null; 
	private String parameters_floath = null;	
	private String parameters_title = null;	
	private String parameters_link = null;  
	private String parameters_size = null;	
	private String parameters_url = null;	
	private String parameters_width = null;	
	private String parameters_height = null;
	private String parameters_alt = null;	
	private String postperpage = null;		
	private boolean pp_selectedAll = false; 
	private boolean selected_all = false; 	
	private boolean selected_index = false;	
	private boolean selected_register = false;	
	private boolean selected_redirect = false; 	
	private boolean selected_archiver = false;	
	private String code=null;		
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
	public String getPostperpage() {
		return postperpage;
	}
	public void setPostperpage(String postperpage) {
		this.postperpage = postperpage;
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
	public String getAdvid() {
		return advid;
	}
	public void setAdvid(String advid) {
		this.advid = advid;
	}
	public String getParameters_style() {
		return parameters_style;
	}
	public void setParameters_style(String parameters_style) {
		this.parameters_style = parameters_style;
	}
	public String getParameters_position() {
		return parameters_position;
	}
	public void setParameters_position(String parameters_position) {
		this.parameters_position = parameters_position;
	}
	public String getParameters_floath() {
		return parameters_floath;
	}
	public void setParameters_floath(String parameters_floath) {
		this.parameters_floath = parameters_floath;
	}
	public String getParameters_title() {
		return parameters_title;
	}
	public void setParameters_title(String parameters_title) {
		this.parameters_title = parameters_title;
	}
	public String getParameters_link() {
		return parameters_link;
	}
	public void setParameters_link(String parameters_link) {
		this.parameters_link = parameters_link;
	}
	public String getParameters_size() {
		return parameters_size;
	}
	public void setParameters_size(String parameters_size) {
		this.parameters_size = parameters_size;
	}
	public String getParameters_url() {
		return parameters_url;
	}
	public void setParameters_url(String parameters_url) {
		this.parameters_url = parameters_url;
	}
	public String getParameters_width() {
		return parameters_width;
	}
	public void setParameters_width(String parameters_width) {
		this.parameters_width = parameters_width;
	}
	public String getParameters_height() {
		return parameters_height;
	}
	public void setParameters_height(String parameters_height) {
		this.parameters_height = parameters_height;
	}
	public String getParameters_alt() {
		return parameters_alt;
	}
	public void setParameters_alt(String parameters_alt) {
		this.parameters_alt = parameters_alt;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public boolean isSelected_all() {
		return selected_all;
	}
	public void setSelected_all(boolean selected_all) {
		this.selected_all = selected_all;
	}
	public boolean isSelected_index() {
		return selected_index;
	}
	public void setSelected_index(boolean selected_index) {
		this.selected_index = selected_index;
	}
	public boolean isSelected_register() {
		return selected_register;
	}
	public void setSelected_register(boolean selected_register) {
		this.selected_register = selected_register;
	}
	public boolean isSelected_redirect() {
		return selected_redirect;
	}
	public void setSelected_redirect(boolean selected_redirect) {
		this.selected_redirect = selected_redirect;
	}
	public boolean isSelected_archiver() {
		return selected_archiver;
	}
	public void setSelected_archiver(boolean selected_archiver) {
		this.selected_archiver = selected_archiver;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isPp_selectedAll() {
		return pp_selectedAll;
	}
	public void setPp_selectedAll(boolean pp_selectedAll) {
		this.pp_selectedAll = pp_selectedAll;
	}
}
