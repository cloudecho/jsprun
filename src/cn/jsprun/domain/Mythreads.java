package cn.jsprun.domain;
public class Mythreads  implements java.io.Serializable {
	private static final long serialVersionUID = -5698926897092123004L;
	private MythreadsId id;	
     private Byte special;		
     private Integer dateline;	
    public Mythreads() {
    }
    public Mythreads(MythreadsId id, Byte special, Integer dateline) {
        this.id = id;
        this.special = special;
        this.dateline = dateline;
    }
    public MythreadsId getId() {
        return this.id;
    }
    public void setId(MythreadsId id) {
        this.id = id;
    }
    public Byte getSpecial() {
        return this.special;
    }
    public void setSpecial(Byte special) {
        this.special = special;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
}