package cn.jsprun.foreg.vo.archiver;
import java.util.Map;
public class Header_inc {
	private String boardurl = null; 
	private Map<String,String> settingMap = null;
	private String navtitle = null;
	private String meta_contentadd = null;
	private String headerbanner = null;
	public String getNavtitle() {
		return navtitle;
	}
	public void setNavtitle(String navtitle) {
		this.navtitle = navtitle;
	}
	public String getBbname() {
		if(settingMap!=null){
			return settingMap.get("bbname");
		}else{
			return "";
		}
	}
	public String getSeotitle() {
		if(settingMap!=null){
			return settingMap.get("seotitle");
		}else{
			return "";
		}
	}
	public String getSeohead() {
		if(settingMap!=null){
			return settingMap.get("seohead");
		}else{
			return "";
		}
	}
	public String getSeokeywords() {
		if(settingMap!=null){
			return settingMap.get("seokeywords");
		}else{
			return "";
		}
	}
	public String getMeta_contentadd() {
		return meta_contentadd;
	}
	public void setMeta_contentadd(String meta_contentadd) {
		this.meta_contentadd = meta_contentadd;
	}
	public String getVersion() {
		if(settingMap!=null){
			return settingMap.get("version");
		}else{
			return "";
		}
	}
	public String getStyleid() {
		if(settingMap!=null){
			return settingMap.get("styleid");
		}else{
			return "";
		}
	}
	public boolean getHaveHeaderbanner() {
		return headerbanner!=null&&!headerbanner.equals("");
	}
	public String getHeaderbanner() {
		return headerbanner;
	}
	public void setHeaderbanner(String headerbanner) {
		this.headerbanner = headerbanner;
	}
	public String getBoardurl() {
		return boardurl;
	}
	public void setBoardurl(String boardurl) {
		this.boardurl = boardurl;
	}
	public void setSettingMap(Map<String, String> settingMap) {
		this.settingMap = settingMap;
	}
	public String getSeodescription(){
		if(settingMap!=null){
			return settingMap.get("seodescription");
		}else{
			return "";
		}
	}
}
