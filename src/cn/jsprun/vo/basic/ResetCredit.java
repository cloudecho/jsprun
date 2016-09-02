package cn.jsprun.vo.basic;
import java.io.Serializable;
public class ResetCredit implements Serializable {
	private static final long serialVersionUID = 8079012056451963787L;
	private String extcreditsName;
	private String resetValue;
	private String extcreditid;
	public ResetCredit() {}
	public ResetCredit(String extcreditsName,String resetValue,String extcreditid){
		this.extcreditsName = extcreditsName;
		this.resetValue = resetValue;
		this.extcreditid = extcreditid;
	}
	public String getExtcreditsName() {
		return extcreditsName;
	}
	public void setExtcreditsName(String extcreditsName) {
		this.extcreditsName = extcreditsName;
	}
	public String getResetValue() {
		return resetValue;
	}
	public void setResetValue(String resetValue) {
		this.resetValue = resetValue;
	}
	public String getExtcreditid() {
		return extcreditid;
	}
	public void setExtcreditid(String extcreditid) {
		this.extcreditid = extcreditid;
	}
}
