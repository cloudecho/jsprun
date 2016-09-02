package cn.jsprun.foreg.vo.statistic;
import java.util.Map;
public class Stats_tradeCompositorVO  {
	private Stats_navbarVO navbar = new Stats_navbarVO();
	private String lastUpdate = null;
	private String nextUpdate = null;
	private Map<Integer,Map<String,String>> totalitems = null;
	private Map<Integer,Map<String,String>> tradesums = null;
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getNextUpdate() {
		return nextUpdate;
	}
	public void setNextUpdate(String nextUpdate) {
		this.nextUpdate = nextUpdate;
	}
	public Map<Integer,Map<String,String>> getTotalitems() {
		return totalitems;
	}
	public Map<Integer,Map<String,String>> getTradesums() {
		return tradesums;
	}
	public void setTotalitems(Map<Integer,Map<String,String>> totalitems) {
		this.totalitems = totalitems;
	}
	public void setTradesums(Map<Integer,Map<String,String>> tradesums) {
		this.tradesums = tradesums;
	}
	public Stats_navbarVO getNavbar() {
		return navbar;
	}
}
