package cn.jsprun.foreg.vo.statistic;
import java.util.ArrayList;
import java.util.List;
public class Stats_viewsVO {
	private Stats_navbarVO navbar = new Stats_navbarVO();
	private List<PageInfo> pageInfoWeekList = new ArrayList<PageInfo>();
	private List<PageInfo> pageInfoHourList = new ArrayList<PageInfo>();
	public List<PageInfo> getPageInfoHourList() {
		return pageInfoHourList;
	}
	public void setPageInfoHourList(List<PageInfo> pageInfoHourList) {
		this.pageInfoHourList = pageInfoHourList;
	}
	public List<PageInfo> getPageInfoWeekList() {
		return pageInfoWeekList;
	}
	public void setPageInfoWeekList(List<PageInfo> pageInfoWeekList) {
		this.pageInfoWeekList = pageInfoWeekList;
	}
	public Stats_navbarVO getNavbar() {
		return navbar;
	}
}
