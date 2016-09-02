package cn.jsprun.vo;
public class FieldVO {
	private String field;
	private String type;
	private String allowNull;
	private String key;
	private String defaultValue;
	private String extra;
	public String getAllowNull() {
		return allowNull;
	}
	public void setAllowNull(String allowNull) {
		this.allowNull = allowNull;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
