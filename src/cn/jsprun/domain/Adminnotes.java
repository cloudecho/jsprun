package cn.jsprun.domain;
public class Adminnotes  implements java.io.Serializable {
	private static final long serialVersionUID = -2968173847007545597L;
	private Integer id;			
     private String admin;			
     private Short access;			
     private Short adminid;			
     private Integer dateline;		
     private Integer expiration;	
     private String message;		
    public Adminnotes() {
    }
    public Adminnotes(String admin, Short access, Short adminid, Integer dateline, Integer expiration, String message) {
        this.admin = admin;
        this.access = access;
        this.adminid = adminid;
        this.dateline = dateline;
        this.expiration = expiration;
        this.message = message;
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getAdmin() {
        return this.admin;
    }
    public void setAdmin(String admin) {
        this.admin = admin;
    }
    public Short getAccess() {
        return this.access;
    }
    public void setAccess(Short access) {
        this.access = access;
    }
    public Short getAdminid() {
        return this.adminid;
    }
    public void setAdminid(Short adminid) {
        this.adminid = adminid;
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
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}