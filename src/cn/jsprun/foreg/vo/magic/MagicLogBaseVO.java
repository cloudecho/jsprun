package cn.jsprun.foreg.vo.magic;
public class MagicLogBaseVO  {
	private String magicName;
	private String magicId;
	private String operatingTime;
	public MagicLogBaseVO(){
	}
	public void setMagicId(String magicId) {
		this.magicId = magicId;
	}
	public String getMagicName() {
		return magicName;
	}
	public void setMagicName(String magicName) {
		this.magicName = magicName;
	}
	public String getOperatingTime() {
		return operatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		this.operatingTime = operatingTime;
	}
	public String getUrlOnMagicName() {
		return "magic.jsp?action=prepareShopping&magicid="+magicId;
	}
}
