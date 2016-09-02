package cn.jsprun.vo.otherset;
import java.util.ArrayList;
import java.util.List;
public class FaqsIndexVO {
	private List<FaqInfo> allFaqList = new ArrayList<FaqInfo>();
	private List<FaqInfo> topperFaqList = new ArrayList<FaqInfo>();
	public List<FaqInfo> getAllFaqList() {
		return allFaqList;
	}
	public List<FaqInfo> getTopperFaqList() {
		return topperFaqList;
	}
}
