package cn.jsprun.domain;
public class Medals  implements java.io.Serializable {
	private static final long serialVersionUID = -1306577142462305770L;
	private Short medalid;		
     private String name;		
     private Byte available;	
     private String image;		
    public Medals() {
    }
    public Medals(String name, Byte available, String image) {
        this.name = name;
        this.available = available;
        this.image = image;
    }
    public Short getMedalid() {
        return this.medalid;
    }
    public void setMedalid(Short medalid) {
        this.medalid = medalid;
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
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}