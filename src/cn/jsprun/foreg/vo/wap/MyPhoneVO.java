package cn.jsprun.foreg.vo.wap;
import java.util.Map;
public class MyPhoneVO extends WithFooterAndHead {
	private String serverInfo = null;
	private Map<String,String> otherInfoMap = null;
	public String getServerInfo() {
		return serverInfo;
	}
	public void setServerInfo(String serverInfo) {
		this.serverInfo = serverInfo;
	}
	public Map<String, String> getOtherInfoMap() {
		return otherInfoMap;
	}
	public void setOtherInfoMap(Map<String, String> otherInfoMap) {
		this.otherInfoMap = otherInfoMap;
	}
}
