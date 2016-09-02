package cn.jsprun.domain;
public class Forumrecommend  implements java.io.Serializable {
	private static final long serialVersionUID = -6165335857450111950L;
	private Integer tid;			
     private Short fid;				
     private Byte displayorder;		
     private String subject;		
     private String author;			
     private Integer authorid;		
     private Integer moderatorid;	
     private Integer expiration;	
    public Forumrecommend() {
    }
    public Forumrecommend(Integer tid, Short fid, Byte displayorder, String subject, String author, Integer authorid, Integer moderatorid, Integer expiration) {
        this.tid = tid;
        this.fid = fid;
        this.displayorder = displayorder;
        this.subject = subject;
        this.author = author;
        this.authorid = authorid;
        this.moderatorid = moderatorid;
        this.expiration = expiration;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public Short getFid() {
        return this.fid;
    }
    public void setFid(Short fid) {
        this.fid = fid;
    }
    public Byte getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Byte displayorder) {
        this.displayorder = displayorder;
    }
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Integer getAuthorid() {
        return this.authorid;
    }
    public void setAuthorid(Integer authorid) {
        this.authorid = authorid;
    }
    public Integer getModeratorid() {
        return this.moderatorid;
    }
    public void setModeratorid(Integer moderatorid) {
        this.moderatorid = moderatorid;
    }
    public Integer getExpiration() {
        return this.expiration;
    }
    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}