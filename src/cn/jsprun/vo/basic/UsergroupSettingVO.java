package cn.jsprun.vo.basic;
import java.io.Serializable;
public class UsergroupSettingVO implements Serializable {
	private static final long serialVersionUID = -2941121093512036321L;
	private String groupid;
	private String checked;
	private String grouptitle;
	private String minValue;
	private String maxValue;
	private String hourMaxValue;
	public UsergroupSettingVO() {
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getGrouptitle() {
		return grouptitle;
	}
	public void setGrouptitle(String grouptitle) {
		this.grouptitle = grouptitle;
	}
	public String getHourMaxValue() {
		return hourMaxValue;
	}
	public void setHourMaxValue(String hourMaxValue) {
		this.hourMaxValue = hourMaxValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
}
