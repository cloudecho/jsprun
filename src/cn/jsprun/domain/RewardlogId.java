package cn.jsprun.domain;
public class RewardlogId  implements java.io.Serializable {
	private static final long serialVersionUID = -1813641482252427147L;
     private Integer tid;
     private Integer authorid;
     private Integer answererid;
     private Integer dateline;
     private Integer netamount;
    public RewardlogId() {
    }
    public RewardlogId(Integer tid, Integer authorid, Integer answererid, Integer netamount) {
        this.tid = tid;
        this.authorid = authorid;
        this.answererid = answererid;
        this.netamount = netamount;
    }
    public RewardlogId(Integer tid, Integer authorid, Integer answererid, Integer dateline, Integer netamount) {
        this.tid = tid;
        this.authorid = authorid;
        this.answererid = answererid;
        this.dateline = dateline;
        this.netamount = netamount;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public Integer getAuthorid() {
        return this.authorid;
    }
    public void setAuthorid(Integer authorid) {
        this.authorid = authorid;
    }
    public Integer getAnswererid() {
        return this.answererid;
    }
    public void setAnswererid(Integer answererid) {
        this.answererid = answererid;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public Integer getNetamount() {
        return this.netamount;
    }
    public void setNetamount(Integer netamount) {
        this.netamount = netamount;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RewardlogId) ) return false;
		 RewardlogId castOther = ( RewardlogId ) other; 
		 return ( (this.getTid()==castOther.getTid()) || ( this.getTid()!=null && castOther.getTid()!=null && this.getTid().equals(castOther.getTid()) ) )
 && ( (this.getAuthorid()==castOther.getAuthorid()) || ( this.getAuthorid()!=null && castOther.getAuthorid()!=null && this.getAuthorid().equals(castOther.getAuthorid()) ) )
 && ( (this.getAnswererid()==castOther.getAnswererid()) || ( this.getAnswererid()!=null && castOther.getAnswererid()!=null && this.getAnswererid().equals(castOther.getAnswererid()) ) )
 && ( (this.getDateline()==castOther.getDateline()) || ( this.getDateline()!=null && castOther.getDateline()!=null && this.getDateline().equals(castOther.getDateline()) ) )
 && ( (this.getNetamount()==castOther.getNetamount()) || ( this.getNetamount()!=null && castOther.getNetamount()!=null && this.getNetamount().equals(castOther.getNetamount()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getTid() == null ? 0 : this.getTid().hashCode() );
         result = 37 * result + ( getAuthorid() == null ? 0 : this.getAuthorid().hashCode() );
         result = 37 * result + ( getAnswererid() == null ? 0 : this.getAnswererid().hashCode() );
         result = 37 * result + ( getDateline() == null ? 0 : this.getDateline().hashCode() );
         result = 37 * result + ( getNetamount() == null ? 0 : this.getNetamount().hashCode() );
         return result;
   }   
}