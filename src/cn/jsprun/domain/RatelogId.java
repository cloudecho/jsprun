package cn.jsprun.domain;
public class RatelogId  implements java.io.Serializable {
	private static final long serialVersionUID = 4475916793333240470L;
     private Integer pid;
     private Integer uid;
     private String username;
     private Byte extcredits;
     private Integer dateline;
     private Short score;
     private String reason;
    public RatelogId() {
    }
    public RatelogId(Integer pid, Integer uid, String username, Byte extcredits, Integer dateline, Short score, String reason) {
        this.pid = pid;
        this.uid = uid;
        this.username = username;
        this.extcredits = extcredits;
        this.dateline = dateline;
        this.score = score;
        this.reason = reason;
    }
    public Integer getPid() {
        return this.pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Byte getExtcredits() {
        return this.extcredits;
    }
    public void setExtcredits(Byte extcredits) {
        this.extcredits = extcredits;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public Short getScore() {
        return this.score;
    }
    public void setScore(Short score) {
        this.score = score;
    }
    public String getReason() {
        return this.reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RatelogId) ) return false;
		 RatelogId castOther = ( RatelogId ) other; 
		 return ( (this.getPid()==castOther.getPid()) || ( this.getPid()!=null && castOther.getPid()!=null && this.getPid().equals(castOther.getPid()) ) )
 && ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getUsername()==castOther.getUsername()) || ( this.getUsername()!=null && castOther.getUsername()!=null && this.getUsername().equals(castOther.getUsername()) ) )
 && ( (this.getExtcredits()==castOther.getExtcredits()) || ( this.getExtcredits()!=null && castOther.getExtcredits()!=null && this.getExtcredits().equals(castOther.getExtcredits()) ) )
 && ( (this.getDateline()==castOther.getDateline()) || ( this.getDateline()!=null && castOther.getDateline()!=null && this.getDateline().equals(castOther.getDateline()) ) )
 && ( (this.getScore()==castOther.getScore()) || ( this.getScore()!=null && castOther.getScore()!=null && this.getScore().equals(castOther.getScore()) ) )
 && ( (this.getReason()==castOther.getReason()) || ( this.getReason()!=null && castOther.getReason()!=null && this.getReason().equals(castOther.getReason()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getPid() == null ? 0 : this.getPid().hashCode() );
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getUsername() == null ? 0 : this.getUsername().hashCode() );
         result = 37 * result + ( getExtcredits() == null ? 0 : this.getExtcredits().hashCode() );
         result = 37 * result + ( getDateline() == null ? 0 : this.getDateline().hashCode() );
         result = 37 * result + ( getScore() == null ? 0 : this.getScore().hashCode() );
         result = 37 * result + ( getReason() == null ? 0 : this.getReason().hashCode() );
         return result;
   }   
}