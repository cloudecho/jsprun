package cn.jsprun.domain;
public class Debateposts  implements java.io.Serializable {
	private static final long serialVersionUID = 3074836640563240741L;
	private Integer pid;			
     private Byte stand;			
     private Integer tid;			
     private Integer uid;			
     private Integer dateline;		
     private Integer voters;		
     private String voterids;		
    public Debateposts() {
    }
    public Debateposts(Integer pid, Byte stand, Integer tid, Integer uid, Integer dateline, Integer voters, String voterids) {
        this.pid = pid;
        this.stand = stand;
        this.tid = tid;
        this.uid = uid;
        this.dateline = dateline;
        this.voters = voters;
        this.voterids = voterids;
    }
    public Integer getPid() {
        return this.pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public Byte getStand() {
        return this.stand;
    }
    public void setStand(Byte stand) {
        this.stand = stand;
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
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public Integer getVoters() {
        return this.voters;
    }
    public void setVoters(Integer voters) {
        this.voters = voters;
    }
    public String getVoterids() {
        return this.voterids;
    }
    public void setVoterids(String voterids) {
        this.voterids = voterids;
    }
}