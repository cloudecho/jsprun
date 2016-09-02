package cn.jsprun.foreg.vo.magic;
public class MagicBuyLogVO extends MagicLogShowMagicNumberVO {
	private String price;
	private String util;
	public String getUtil() {
		return util;
	}
	public void setUtil(String util) {
		this.util = util;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
