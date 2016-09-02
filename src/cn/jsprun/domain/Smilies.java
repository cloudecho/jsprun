package cn.jsprun.domain;
public class Smilies  implements java.io.Serializable {
	private static final long serialVersionUID = -3931545491201553288L;
     private Short id;
     private Short typeid;
     private Short displayorder;
     private String type;
     private String code;
     private String url;
    public Smilies() {
    }
    public Smilies(Short typeid, Short displayorder, String type, String code, String url) {
        this.typeid = typeid;
        this.displayorder = displayorder;
        this.type = type;
        this.code = code;
        this.url = url;
    }
    public Short getId() {
        return this.id;
    }
    public void setId(Short id) {
        this.id = id;
    }
    public Short getTypeid() {
        return this.typeid;
    }
    public void setTypeid(Short typeid) {
        this.typeid = typeid;
    }
    public Short getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Short displayorder) {
        this.displayorder = displayorder;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}