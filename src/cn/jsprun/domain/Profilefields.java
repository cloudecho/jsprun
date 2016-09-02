package cn.jsprun.domain;
public class Profilefields  implements java.io.Serializable {
	private static final long serialVersionUID = -554397475591355029L;
     private Short fieldid;
     private Byte available;
     private Byte invisible;
     private String title;
     private String description;
     private Short size;
     private Short displayorder;
     private Byte required;
     private Byte unchangeable;
     private Byte showinthread;
     private Byte selective;
     private String choices;
    public Profilefields() {
    }
    public Profilefields(Byte available, Byte invisible, String title, String description, Short size, Short displayorder, Byte required, Byte unchangeable, Byte showinthread, Byte selective, String choices) {
        this.available = available;
        this.invisible = invisible;
        this.title = title;
        this.description = description;
        this.size = size;
        this.displayorder = displayorder;
        this.required = required;
        this.unchangeable = unchangeable;
        this.showinthread = showinthread;
        this.selective = selective;
        this.choices = choices;
    }
    public Short getFieldid() {
        return this.fieldid;
    }
    public void setFieldid(Short fieldid) {
        this.fieldid = fieldid;
    }
    public Byte getAvailable() {
        return this.available;
    }
    public void setAvailable(Byte available) {
        this.available = available;
    }
    public Byte getInvisible() {
        return this.invisible;
    }
    public void setInvisible(Byte invisible) {
        this.invisible = invisible;
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
    public Short getSize() {
        return this.size;
    }
    public void setSize(Short size) {
        this.size = size;
    }
    public Short getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Short displayorder) {
        this.displayorder = displayorder;
    }
    public Byte getRequired() {
        return this.required;
    }
    public void setRequired(Byte required) {
        this.required = required;
    }
    public Byte getUnchangeable() {
        return this.unchangeable;
    }
    public void setUnchangeable(Byte unchangeable) {
        this.unchangeable = unchangeable;
    }
    public Byte getShowinthread() {
        return this.showinthread;
    }
    public void setShowinthread(Byte showinthread) {
        this.showinthread = showinthread;
    }
    public Byte getSelective() {
        return this.selective;
    }
    public void setSelective(Byte selective) {
        this.selective = selective;
    }
    public String getChoices() {
        return this.choices;
    }
    public void setChoices(String choices) {
        this.choices = choices;
    }
}