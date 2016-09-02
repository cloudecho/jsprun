package cn.jsprun.foreg.vo.archiver;
import java.util.Map;
public class Foot_inc {
	private Map<String,String> fullversion = null;
	private String footerbanner1 = null;
	private String footerbanner2 = null;
	private String footerbanner3 = null;
	private String version = null; 
	public Map<String, String> getFullversion() {
		return fullversion;
	}
	public void setFullversion(Map<String, String> fullversion) {
		this.fullversion = fullversion;
	}
	public String getFooterbanner1() {
		return footerbanner1;
	}
	public void setFooterbanner1(String footerbanner1) {
		this.footerbanner1 = footerbanner1;
	}
	public String getFooterbanner2() {
		return footerbanner2;
	}
	public void setFooterbanner2(String footerbanner2) {
		this.footerbanner2 = footerbanner2;
	}
	public String getFooterbanner3() {
		return footerbanner3;
	}
	public void setFooterbanner3(String footerbanner3) {
		this.footerbanner3 = footerbanner3;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTitle() {
		if(fullversion==null){
			return "";
		}else{
			return fullversion.get("title");
		}
	}
	public String getLink() {
		if(fullversion==null){
			return "";
		}else{
			return fullversion.get("link");
		}
	}
}
