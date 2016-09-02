package cn.jsprun.domain;
public class Myposts  implements java.io.Serializable {
	private static final long serialVersionUID = -7634663492322187279L;
	private MypostsId id;		
     private Integer pid;		
     private Short position;	
     private Integer dateline;	
     private Byte special;		
    public Myposts() {
    }
    public Myposts(MypostsId id, Integer pid, Short position, Integer dateline, Byte special) {
        this.id = id;
        this.pid = pid;
        this.position = position;
        this.dateline = dateline;
        this.special = special;
    }
    public MypostsId getId() {
        return this.id;
    }
    public void setId(MypostsId id) {
        this.id = id;
    }
    public Integer getPid() {
        return this.pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public Short getPosition() {
        return this.position;
    }
    public void setPosition(Short position) {
        this.position = position;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public Byte getSpecial() {
        return this.special;
    }
    public void setSpecial(Byte special) {
        this.special = special;
    }
}