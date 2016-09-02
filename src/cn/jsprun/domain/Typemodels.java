package cn.jsprun.domain;
public class Typemodels  implements java.io.Serializable {
	private static final long serialVersionUID = 9050975388943004106L;
     private Short id;
     private String name;
     private Short displayorder;
     private Byte type;
     private String options;
     private String customoptions;
    public Typemodels() {
    }
    public Typemodels(String name, Short displayorder, Byte type, String options, String customoptions) {
        this.name = name;
        this.displayorder = displayorder;
        this.type = type;
        this.options = options;
        this.customoptions = customoptions;
    }
    public Short getId() {
        return this.id;
    }
    public void setId(Short id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Short getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Short displayorder) {
        this.displayorder = displayorder;
    }
    public Byte getType() {
        return this.type;
    }
    public void setType(Byte type) {
        this.type = type;
    }
    public String getOptions() {
        return this.options;
    }
    public void setOptions(String options) {
        this.options = options;
    }
    public String getCustomoptions() {
        return this.customoptions;
    }
    public void setCustomoptions(String customoptions) {
        this.customoptions = customoptions;
    }
}