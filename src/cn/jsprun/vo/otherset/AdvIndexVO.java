package cn.jsprun.vo.otherset;
import java.util.ArrayList;
import java.util.List;
public class AdvIndexVO {
	private List<Advertisement> advertisementList = new ArrayList<Advertisement>();
	private String queryTitle=null;	
	private String queryTime = null;	
	private String queryType = null;	
	private String queryOrderby = null;	
	public List<Advertisement> getAdvertisementList() {
		return advertisementList;
	}
	public void setAdvertisementList(List<Advertisement> advertisementList) {
		this.advertisementList = advertisementList;
	}
	public String getQueryTitle() {
		return queryTitle;
	}
	public void setQueryTitle(String queryTitle) {
		this.queryTitle = queryTitle;
	}
	public String getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getQueryOrderby() {
		return queryOrderby;
	}
	public void setQueryOrderby(String queryOrderby) {
		this.queryOrderby = queryOrderby;
	}
}
