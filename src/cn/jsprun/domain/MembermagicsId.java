package cn.jsprun.domain;
public class MembermagicsId  implements java.io.Serializable {
	private static final long serialVersionUID = 1486559030790111110L;
	private Integer uid;		
     private Short magicid;		
    public MembermagicsId() {
    }
    public MembermagicsId(Integer uid, Short magicid) {
        this.uid = uid;
        this.magicid = magicid;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Short getMagicid() {
        return this.magicid;
    }
    public void setMagicid(Short magicid) {
        this.magicid = magicid;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof MembermagicsId) ) return false;
		 MembermagicsId castOther = ( MembermagicsId ) other; 
		 return ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getMagicid()==castOther.getMagicid()) || ( this.getMagicid()!=null && castOther.getMagicid()!=null && this.getMagicid().equals(castOther.getMagicid()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getMagicid() == null ? 0 : this.getMagicid().hashCode() );
         return result;
   }   
}