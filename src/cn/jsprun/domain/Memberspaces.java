package cn.jsprun.domain;
public class Memberspaces  implements java.io.Serializable {
	private static final long serialVersionUID = 7479069594715693361L;
	private Integer uid;			
     private String style;			
     private String description;	
     private String layout;			
     private Byte side;				
    public Memberspaces() {
    }
    public Memberspaces(Integer uid, String style, String description, String layout, Byte side) {
        this.uid = uid;
        this.style = style;
        this.description = description;
        this.layout = layout;
        this.side = side;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getStyle() {
        return this.style;
    }
    public void setStyle(String style) {
        this.style = style;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLayout() {
        return this.layout;
    }
    public void setLayout(String layout) {
        this.layout = layout;
    }
    public Byte getSide() {
        return this.side;
    }
    public void setSide(Byte side) {
        this.side = side;
    }
}