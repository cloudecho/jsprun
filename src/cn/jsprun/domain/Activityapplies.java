package cn.jsprun.domain;
public class Activityapplies  implements java.io.Serializable {
	private static final long serialVersionUID = 4721182109090351325L;
	private Integer applyid;		
     private Integer tid;			
     private String username;		
     private Integer uid;			
     private String message;		
     private Byte verified;			
     private Integer dateline;		
     private Integer payment;		
     private String contact;		
    public Activityapplies() {
    }
    public Activityapplies(Integer tid, String username, Integer uid, String message, Byte verified, Integer dateline, Integer payment, String contact) {
        this.tid = tid;
        this.username = username;
        this.uid = uid;
        this.message = message;
        this.verified = verified;
        this.dateline = dateline;
        this.payment = payment;
        this.contact = contact;
    }
    public Integer getApplyid() {
        return this.applyid;
    }
    public void setApplyid(Integer applyid) {
        this.applyid = applyid;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Byte getVerified() {
        return this.verified;
    }
    public void setVerified(Byte verified) {
        this.verified = verified;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public Integer getPayment() {
        return this.payment;
    }
    public void setPayment(Integer payment) {
        this.payment = payment;
    }
    public String getContact() {
        return this.contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
}