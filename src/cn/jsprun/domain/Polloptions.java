package cn.jsprun.domain;
public class Polloptions  implements java.io.Serializable {
	private static final long serialVersionUID = 7354973088876137790L;
     private Integer polloptionid;
     private Integer tid;
     private Integer votes;
     private Short displayorder;
     private String polloption;
     private String voterids;
    public Polloptions() {
    }
    public Polloptions(Integer tid, Integer votes, Short displayorder, String polloption, String voterids) {
        this.tid = tid;
        this.votes = votes;
        this.displayorder = displayorder;
        this.polloption = polloption;
        this.voterids = voterids;
    }
    public Integer getPolloptionid() {
        return this.polloptionid;
    }
    public void setPolloptionid(Integer polloptionid) {
        this.polloptionid = polloptionid;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public Integer getVotes() {
        return this.votes;
    }
    public void setVotes(Integer votes) {
        this.votes = votes;
    }
    public Short getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Short displayorder) {
        this.displayorder = displayorder;
    }
    public String getPolloption() {
        return this.polloption;
    }
    public void setPolloption(String polloption) {
        this.polloption = polloption;
    }
    public String getVoterids() {
        return this.voterids;
    }
    public void setVoterids(String voterids) {
        this.voterids = voterids;
    }
}