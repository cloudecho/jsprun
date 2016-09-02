package cn.jsprun.domain;
public class Magicmarket  implements java.io.Serializable {
	private static final long serialVersionUID = -5928028591489784543L;
	private Short mid;			
     private Short magicid;		
     private Integer uid;		
     private String username;	
     private Integer price;		
     private Short num;			
    public Magicmarket() {
    }
    public Magicmarket(Short magicid, Integer uid, String username, Integer price, Short num) {
        this.magicid = magicid;
        this.uid = uid;
        this.username = username;
        this.price = price;
        this.num = num;
    }
    public Short getMid() {
        return this.mid;
    }
    public void setMid(Short mid) {
        this.mid = mid;
    }
    public Short getMagicid() {
        return this.magicid;
    }
    public void setMagicid(Short magicid) {
        this.magicid = magicid;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getPrice() {
        return this.price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Short getNum() {
        return this.num;
    }
    public void setNum(Short num) {
        this.num = num;
    }
}