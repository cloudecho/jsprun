package cn.jsprun.foreg.vo.statistic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class Stats_CompositorVO {
	private Stats_navbarVO navbar = new Stats_navbarVO();
	private List<Map<String, CompositorInfo>> compositorMapList = new ArrayList<Map<String,CompositorInfo>>();
	public List<Map<String, CompositorInfo>> getCompositorMapList() {
		return compositorMapList;
	}
	public void setCompositorMapList(
			List<Map<String, CompositorInfo>> compositorMapList) {
		this.compositorMapList = compositorMapList;
	}
	public Stats_navbarVO getNavbar() {
		return navbar;
	}
}
