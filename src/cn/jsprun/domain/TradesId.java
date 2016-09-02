package cn.jsprun.domain;
public class TradesId  implements java.io.Serializable {
	private static final long serialVersionUID = -8708904709150048668L;
     private Integer tid;
     private Integer pid;
    public TradesId() {
    }
    public TradesId(Integer tid, Integer pid) {
        this.tid = tid;
        this.pid = pid;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public Integer getPid() {
        return this.pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TradesId) ) return false;
		 TradesId castOther = ( TradesId ) other; 
		 return ( (this.getTid()==castOther.getTid()) || ( this.getTid()!=null && castOther.getTid()!=null && this.getTid().equals(castOther.getTid()) ) )
 && ( (this.getPid()==castOther.getPid()) || ( this.getPid()!=null && castOther.getPid()!=null && this.getPid().equals(castOther.getPid()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getTid() == null ? 0 : this.getTid().hashCode() );
         result = 37 * result + ( getPid() == null ? 0 : this.getPid().hashCode() );
         return result;
   }   
}