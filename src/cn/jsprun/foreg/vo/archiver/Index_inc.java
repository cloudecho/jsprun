package cn.jsprun.foreg.vo.archiver;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class Index_inc extends WithHeaderAndFoot{
	private Map<String,String> settingMap = null;
	private Map<String,String> fullversion = null;
	private List<Forums> groupList = new LinkedList<Forums>();
	private String qm = null;
	public String getQm() {
		return qm;
	}
	public void setQm(String qm) {
		this.qm = qm;
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
	public String getBbname() {
		if(settingMap!=null){
			return settingMap.get("bbname");
		}else{
			return "";
		}
	}
	public void setSettingMap(Map<String, String> settingMap) {
		this.settingMap = settingMap;
	}
	public void setFullversion(Map<String, String> fullversion) {
		this.fullversion = fullversion;
	}
	public static class Forums{
		private String name = null;
		private String fid = null;
		private List<Forums> forumList = null;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getFid() {
			return fid;
		}
		public void setFid(String fid) {
			this.fid = fid;
		}
		public List<Forums> getForumList() {
			if(forumList==null){
				forumList = new LinkedList<Forums>();
			}
			return forumList;
		}
	}
	public Forums getForums(){
		return new Forums();
	}
	public List<Forums> getGroupList() {
		return groupList;
	}
}
