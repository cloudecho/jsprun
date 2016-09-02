package cn.jsprun.domain;
public class AttachpaymentlogId  implements java.io.Serializable {
	private static final long serialVersionUID = 6967529071305792483L;
	private Integer aid;		
     private Integer uid;		
    public AttachpaymentlogId() {
    }
    public AttachpaymentlogId(Integer aid, Integer uid) {
        this.aid = aid;
        this.uid = uid;
    }
    public Integer getAid() {
        return this.aid;
    }
    public void setAid(Integer aid) {
        this.aid = aid;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AttachpaymentlogId) ) return false;
		 AttachpaymentlogId castOther = ( AttachpaymentlogId ) other; 
		 return ( (this.getAid()==castOther.getAid()) || ( this.getAid()!=null && castOther.getAid()!=null && this.getAid().equals(castOther.getAid()) ) )
 && ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getAid() == null ? 0 : this.getAid().hashCode() );
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         return result;
   }   
}