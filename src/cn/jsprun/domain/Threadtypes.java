package cn.jsprun.domain;
public class Threadtypes  implements java.io.Serializable {
	private static final long serialVersionUID = 8853301199570695450L;
     private Short typeid;
     private Short displayorder;
     private String name;
     private String description;
     private Short special;
     private Short modelid;
     private Byte expiration;
     private String template;
    public Threadtypes() {
    }
    public Threadtypes(Short displayorder, String name, String description, Short special, Short modelid, Byte expiration, String template) {
        this.displayorder = displayorder;
        this.name = name;
        this.description = description;
        this.special = special;
        this.modelid = modelid;
        this.expiration = expiration;
        this.template = template;
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Short getSpecial() {
        return this.special;
    }
    public void setSpecial(Short special) {
        this.special = special;
    }
    public Short getModelid() {
        return this.modelid;
    }
    public void setModelid(Short modelid) {
        this.modelid = modelid;
    }
    public Byte getExpiration() {
        return this.expiration;
    }
    public void setExpiration(Byte expiration) {
        this.expiration = expiration;
    }
    public String getTemplate() {
        return this.template;
    }
    public void setTemplate(String template) {
        this.template = template;
    }
}