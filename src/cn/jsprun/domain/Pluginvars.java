package cn.jsprun.domain;
public class Pluginvars  implements java.io.Serializable {
	private static final long serialVersionUID = 6680829832590128984L;
     private Integer pluginvarid;
     private Short pluginid;
     private Short displayorder;
     private String title;
     private String description;
     private String variable;
     private String type;
     private String value;
     private String extra;
    public Pluginvars() {
    }
    public Pluginvars(Short pluginid, Short displayorder, String title, String description, String variable, String type, String value, String extra) {
        this.pluginid = pluginid;
        this.displayorder = displayorder;
        this.title = title;
        this.description = description;
        this.variable = variable;
        this.type = type;
        this.value = value;
        this.extra = extra;
    }
    public Integer getPluginvarid() {
        return this.pluginvarid;
    }
    public void setPluginvarid(Integer pluginvarid) {
        this.pluginvarid = pluginvarid;
    }
    public Short getPluginid() {
        return this.pluginid;
    }
    public void setPluginid(Short pluginid) {
        this.pluginid = pluginid;
    }
    public Short getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Short displayorder) {
        this.displayorder = displayorder;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getVariable() {
        return this.variable;
    }
    public void setVariable(String variable) {
        this.variable = variable;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getExtra() {
        return this.extra;
    }
    public void setExtra(String extra) {
        this.extra = extra;
    }
}