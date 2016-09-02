package cn.jsprun.domain;
public class Styles  implements java.io.Serializable {
	private static final long serialVersionUID = 3219206542565139746L;
     private Short styleid;
     private String name;
     private Byte available;
     private Short templateid;
    public Styles() {
    }
    public Styles(String name, Byte available, Short templateid) {
        this.name = name;
        this.available = available;
        this.templateid = templateid;
    }
    public Short getStyleid() {
        return this.styleid;
    }
    public void setStyleid(Short styleid) {
        this.styleid = styleid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Byte getAvailable() {
        return this.available;
    }
    public void setAvailable(Byte available) {
        this.available = available;
    }
    public Short getTemplateid() {
        return this.templateid;
    }
    public void setTemplateid(Short templateid) {
        this.templateid = templateid;
    }
}