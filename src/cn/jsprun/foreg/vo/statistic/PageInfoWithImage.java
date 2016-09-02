package cn.jsprun.foreg.vo.statistic;
public class PageInfoWithImage extends PageInfo {
	public String getImageName() {
		return getInformation().toLowerCase().equals("os/2")?getInformation().toLowerCase().replace("/", ""):getInformation().toLowerCase();
	}
}
