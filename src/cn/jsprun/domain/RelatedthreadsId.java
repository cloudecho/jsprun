package cn.jsprun.domain;
public class RelatedthreadsId  implements java.io.Serializable {
	private static final long serialVersionUID = -8177925650836220570L;
     private Integer tid;
     private String type;
    public RelatedthreadsId() {
    }
    public RelatedthreadsId(Integer tid, String type) {
        this.tid = tid;
        this.type = type;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RelatedthreadsId) ) return false;
		 RelatedthreadsId castOther = ( RelatedthreadsId ) other; 
		 return ( (this.getTid()==castOther.getTid()) || ( this.getTid()!=null && castOther.getTid()!=null && this.getTid().equals(castOther.getTid()) ) )
 && ( (this.getType()==castOther.getType()) || ( this.getType()!=null && castOther.getType()!=null && this.getType().equals(castOther.getType()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getTid() == null ? 0 : this.getTid().hashCode() );
         result = 37 * result + ( getType() == null ? 0 : this.getType().hashCode() );
         return result;
   }   
}