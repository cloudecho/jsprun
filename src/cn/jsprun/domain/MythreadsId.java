package cn.jsprun.domain;
public class MythreadsId  implements java.io.Serializable {
	private static final long serialVersionUID = 6515803082315035786L;
	private Integer uid;	
     private Integer tid;	
    public MythreadsId() {
    }
    public MythreadsId(Integer uid, Integer tid) {
        this.uid = uid;
        this.tid = tid;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof MythreadsId) ) return false;
		 MythreadsId castOther = ( MythreadsId ) other; 
		 return ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getTid()==castOther.getTid()) || ( this.getTid()!=null && castOther.getTid()!=null && this.getTid().equals(castOther.getTid()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getTid() == null ? 0 : this.getTid().hashCode() );
         return result;
   }   
}