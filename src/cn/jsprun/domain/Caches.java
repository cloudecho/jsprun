package cn.jsprun.domain;
public class Caches  implements java.io.Serializable {
	private static final long serialVersionUID = -1568596288192648070L;
	private String cachename;		
     private Short type;			
     private Integer dateline;		
     private Integer expiration;	
     private String data;			
    public Caches() {
    }
    public Caches(String cachename, Short type, Integer dateline, Integer expiration, String data) {
        this.cachename = cachename;
        this.type = type;
        this.dateline = dateline;
        this.expiration = expiration;
        this.data = data;
    }
    public String getCachename() {
        return this.cachename;
    }
    public void setCachename(String cachename) {
        this.cachename = cachename;
    }
    public Short getType() {
        return this.type;
    }
    public void setType(Short type) {
        this.type = type;
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
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
}