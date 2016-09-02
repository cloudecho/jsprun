package cn.jsprun.vo.logs;
import java.io.Serializable;
public class MedalslogVO implements Serializable{
	private static final long serialVersionUID = -2919146180304791406L;
	private String firstName;
	private String secondName;
	private String ipAddress;
	private String imgUrl;
	private String operate;
	private String reason;
	private String operateDate;
	private String medalName;
	public String getMedalName() {
		return medalName;
	}
	public void setMedalName(String medalName) {
		this.medalName = medalName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
}
