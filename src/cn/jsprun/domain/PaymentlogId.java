package cn.jsprun.domain;
public class PaymentlogId  implements java.io.Serializable {
	private static final long serialVersionUID = -1217128807954136636L;
     private Integer tid;
     private Integer uid;
    public PaymentlogId() {
    }
    public PaymentlogId(Integer tid, Integer uid) {
        this.tid = tid;
        this.uid = uid;
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
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PaymentlogId) ) return false;
		 PaymentlogId castOther = ( PaymentlogId ) other; 
		 return ( (this.getTid()==castOther.getTid()) || ( this.getTid()!=null && castOther.getTid()!=null && this.getTid().equals(castOther.getTid()) ) )
 && ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getTid() == null ? 0 : this.getTid().hashCode() );
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         return result;
   }   
}