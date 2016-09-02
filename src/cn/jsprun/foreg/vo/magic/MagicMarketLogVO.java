package cn.jsprun.foreg.vo.magic;
public class MagicMarketLogVO extends MagicLogShowMagicNumberVO {
	private String price;
	private String operationInfo;
	private String util;
	public String getUtil() {
		return util;
	}
	public void setUtil(String util) {
		this.util = util;
	}
	public String getOperationInfo() {
		return operationInfo;
	}
	public void setOperationInfo(String operationInfo) {
		this.operationInfo = operationInfo;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
