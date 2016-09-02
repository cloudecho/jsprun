package cn.jsprun.vo.otherset;
import java.text.SimpleDateFormat;
import cn.jsprun.utils.Common;
public class Advertisement {
	private String id = null;			
	private boolean userable = false;	
	private String displayorder = null;	
	private String title = null;		
	private String type = null;			
	private String style = null;		
	private int starttime = 0;	
	private int endtime = 0;		
	private String targets = null;		
	private boolean overdue = false;	
	private SimpleDateFormat simpleDateFormat = null;
	public Advertisement() {}
	public Advertisement(SimpleDateFormat simpleDateFormat){
		this.simpleDateFormat = simpleDateFormat;
	}
	public boolean isOverdue() {
		return overdue;
	}
	public void setOverdue(boolean overdue) {
		this.overdue = overdue;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isUserable() {
		return userable;
	}
	public void setUserable(boolean userable) {
		this.userable = userable;
	}
	public String getDisplayorder() {
		return displayorder;
	}
	public void setDisplayorder(String displayorder) {
		this.displayorder = displayorder;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		if(null==type){
			return "";
		}else if(type.equals("headerbanner")){
			return "a_other_adv_type_headerbanner";
		}else if(type.equals("footerbanner")){
			return "a_other_adv_type_footerbanner";
		}else if(type.equals("text")){
			return "a_other_adv_type_text";
		}else if(type.equals("thread")){
			return "a_other_adv_type_thread";
		}else if(type.equals("interthread")){
			return "a_other_adv_type_interthread";
		}else if(type.equals("float")){
			return "a_other_adv_type_float";
		}else if(type.equals("couplebanner")){
			return "a_other_adv_type_couplebanner";
		}else if(type.equals("intercat")){
			return "a_other_adv_type_intercat";
		}else{
			return "";
		}
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStarttime() {
		return Common.gmdate(simpleDateFormat, starttime);
	}
	public void setStarttime(int starttime) {
		this.starttime = starttime;
	}
	public boolean getEndtimeExist(){
		return endtime!=0;
	}
	public String getEndtime() {
		return endtime==0?"":Common.gmdate(simpleDateFormat, endtime);
	}
	public void setEndtime(int endtime) {
		this.endtime = endtime;
	}
	public String getTargets() {
		return targets;
	}
	public void setTargets(String targets) {
		this.targets = targets;
	}
	public String getStyle() {
		if(style.equals("text")){
			return "a_other_adv_style_text";
		}else if(style.equals("code")){
			return "a_other_adv_style_code";
		}else if(style.equals("flash")){
			return "a_other_adv_style_flash";
		}else if(style.equals("image")){
			return "a_post_smilies_edit_image";
		}else{
			return "";
		}
	}
	public void setStyle(String style) {
		this.style = style;
	}
}
