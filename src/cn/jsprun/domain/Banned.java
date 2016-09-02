package cn.jsprun.domain;
public class Banned  implements java.io.Serializable {
	private static final long serialVersionUID = 2520180993447938837L;
	private Short id;			
     private Short ip1;			
     private Short ip2;			
     private Short ip3;			
     private Short ip4;			
     private String admin;		
     private Integer dateline;	
     private Integer expiration;
    public Banned() {
    }
    public Banned(Short ip1, Short ip2, Short ip3, Short ip4, String admin, Integer dateline, Integer expiration) {
        this.ip1 = ip1;
        this.ip2 = ip2;
        this.ip3 = ip3;
        this.ip4 = ip4;
        this.admin = admin;
        this.dateline = dateline;
        this.expiration = expiration;
    }
    public Short getId() {
        return this.id;
    }
    public void setId(Short id) {
        this.id = id;
    }
    public Short getIp1() {
        return this.ip1;
    }
    public void setIp1(Short ip1) {
        this.ip1 = ip1;
    }
    public Short getIp2() {
        return this.ip2;
    }
    public void setIp2(Short ip2) {
        this.ip2 = ip2;
    }
    public Short getIp3() {
        return this.ip3;
    }
    public void setIp3(Short ip3) {
        this.ip3 = ip3;
    }
    public Short getIp4() {
        return this.ip4;
    }
    public void setIp4(Short ip4) {
        this.ip4 = ip4;
    }
    public String getAdmin() {
        return this.admin;
    }
    public void setAdmin(String admin) {
        this.admin = admin;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public Integer getExpiration() {
        return this.expiration;
    }
    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}