package cn.jsprun.foreg.vo.statistic;
import java.util.ArrayList;
import java.util.List;
public class SubPostlog {
	private List<PageInfo> ereryMonthPost = new ArrayList<PageInfo>();
	private List<PageInfo> ereryDayPost = new ArrayList<PageInfo>();
	public List<PageInfo> getEreryDayPost() {
		return ereryDayPost;
	}
	public List<PageInfo> getEreryMonthPost() {
		return ereryMonthPost;
	}
}
