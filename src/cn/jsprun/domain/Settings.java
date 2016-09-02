package cn.jsprun.domain;
public class Settings implements java.io.Serializable {
	private static final long serialVersionUID = -6388193158746313775L;
	private String variable;
	private String value;
	public Settings() {
	}
	public Settings(String variable, String value) {
		this.variable = variable;
		this.value = value;
	}
	public String getVariable() {
		return this.variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}