package cn.jsprun.domain;
public class ModeratorsId  implements java.io.Serializable {
	private static final long serialVersionUID = 3871154027875657474L;
	private Integer uid;	
     private Short fid;		
    public ModeratorsId() {
    }
    public ModeratorsId(Integer uid, Short fid) {
        this.uid = uid;
        this.fid = fid;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Short getFid() {
        return this.fid;
    }
    public void setFid(Short fid) {
        this.fid = fid;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ModeratorsId) ) return false;
		 ModeratorsId castOther = ( ModeratorsId ) other; 
		 return ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getFid()==castOther.getFid()) || ( this.getFid()!=null && castOther.getFid()!=null && this.getFid().equals(castOther.getFid()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getFid() == null ? 0 : this.getFid().hashCode() );
         return result;
   }   
}