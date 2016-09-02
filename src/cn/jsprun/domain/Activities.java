package cn.jsprun.domain;
public class Activities  implements java.io.Serializable {
	private static final long serialVersionUID = -8754146119498048496L;
	private Integer tid;				
     private Integer uid;				
     private Integer cost;				
     private Integer starttimefrom;		
     private Integer starttimeto;		
     private String place;				
     private String class_;				
     private Byte gender;				
     private Short number;				
     private Integer expiration;		
    public Activities() {
    }
    public Activities(Integer tid, Integer uid, Integer cost, Integer starttimefrom, Integer starttimeto, String place, String class_, Byte gender, Short number, Integer expiration) {
        this.tid = tid;
        this.uid = uid;
        this.cost = cost;
        this.starttimefrom = starttimefrom;
        this.starttimeto = starttimeto;
        this.place = place;
        this.class_ = class_;
        this.gender = gender;
        this.number = number;
        this.expiration = expiration;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getCost() {
        return this.cost;
    }
    public void setCost(Integer cost) {
        this.cost = cost;
    }
    public Integer getStarttimefrom() {
        return this.starttimefrom;
    }
    public void setStarttimefrom(Integer starttimefrom) {
        this.starttimefrom = starttimefrom;
    }
    public Integer getStarttimeto() {
        return this.starttimeto;
    }
    public void setStarttimeto(Integer starttimeto) {
        this.starttimeto = starttimeto;
    }
    public String getPlace() {
        return this.place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getClass_() {
        return this.class_;
    }
    public void setClass_(String class_) {
        this.class_ = class_;
    }
    public Byte getGender() {
        return this.gender;
    }
    public void setGender(Byte gender) {
        this.gender = gender;
    }
    public Short getNumber() {
        return this.number;
    }
    public void setNumber(Short number) {
        this.number = number;
    }
    public Integer getExpiration() {
        return this.expiration;
    }
    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}