package cn.jsprun.foreg.vo.topicadmin;
public class TopicRefundVO extends TopicPublicVO {
	private String creditstransTitle;
	private String creditstransUnit;
	private String payersCount;
	private String netincome;
	public String getPayersCount() {
		return payersCount;
	}
	public void setPayersCount(String payersCount) {
		this.payersCount = payersCount;
	}
	public String getNetincome() {
		return netincome;
	}
	public void setNetincome(String netincome) {
		this.netincome = netincome;
	}
	public String getCreditstransTitle() {
		return creditstransTitle;
	}
	public void setCreditstransTitle(String creditstransTitle) {
		this.creditstransTitle = creditstransTitle;
	}
	public String getCreditstransUnit() {
		return creditstransUnit;
	}
	public void setCreditstransUnit(String creditstransUnit) {
		this.creditstransUnit = creditstransUnit;
	}
}
