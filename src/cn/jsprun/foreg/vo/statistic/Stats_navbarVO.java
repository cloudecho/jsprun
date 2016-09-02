package cn.jsprun.foreg.vo.statistic;
public class Stats_navbarVO {
	private boolean statistic;
	private boolean modworkstatus;
	private String type;
	public boolean isStatistic() {
		return statistic;
	}
	public void setStatistic(boolean statistic) {
		this.statistic = statistic;
	}
	public boolean isModworkstatus() {
		return modworkstatus;
	}
	public void setModworkstatus(boolean modworkstatus) {
		this.modworkstatus = modworkstatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
