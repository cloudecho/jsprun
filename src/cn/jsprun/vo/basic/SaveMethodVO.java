package cn.jsprun.vo.basic;
import java.io.Serializable;
public class SaveMethodVO implements Serializable {
	private Integer all;
	private Integer projectSetting;
	private Integer expressionsSetting;
	private Integer useSetting;
	public SaveMethodVO() {
	}
	public SaveMethodVO(Integer all,Integer projectSetting,Integer expressionsSetting,Integer userSetting){
		this.all = all;
		this.projectSetting = projectSetting;
		this.expressionsSetting = expressionsSetting;
		this.useSetting = userSetting;
	}
	public Integer getAll() {
		return all;
	}
	public void setAll(Integer all) {
		this.all = all;
	}
	public Integer getExpressionsSetting() {
		return expressionsSetting;
	}
	public void setExpressionsSetting(Integer expressionsSetting) {
		this.expressionsSetting = expressionsSetting;
	}
	public Integer getProjectSetting() {
		return projectSetting;
	}
	public void setProjectSetting(Integer projectSetting) {
		this.projectSetting = projectSetting;
	}
	public Integer getUseSetting() {
		return useSetting;
	}
	public void setUseSetting(Integer useSetting) {
		this.useSetting = useSetting;
	}
}
