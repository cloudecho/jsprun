package cn.jsprun.foreg.vo.statistic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class Stats_onlineCompositorVO {
	private Stats_navbarVO navbar = new Stats_navbarVO();
	private List<Map<String, Map<String,String>>> compositorMapList = new ArrayList<Map<String,Map<String,String>>>();
	private String lastTime;
	private String nextTime;
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public List<Map<String, Map<String, String>>> getCompositorMapList() {
		return compositorMapList;
	}
	public void setCompositorMapList(
			List<Map<String, Map<String, String>>> compositorMapList) {
		this.compositorMapList = compositorMapList;
	}
	public String getNextTime() {
		return nextTime;
	}
	public void setNextTime(String nextTime) {
		this.nextTime = nextTime;
	}
	public Stats_navbarVO getNavbar() {
		return navbar;
	}
}
