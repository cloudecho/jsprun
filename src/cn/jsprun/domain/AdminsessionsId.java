package cn.jsprun.domain;
public class AdminsessionsId  implements java.io.Serializable {
	private static final long serialVersionUID = 7071540052490771435L;
	private Integer uid;			
     private String ip;				
     private Integer dateline;		
     private Byte errorcount;		
    public AdminsessionsId() {
    }
    public AdminsessionsId(Integer uid, String ip, Integer dateline, Byte errorcount) {
        this.uid = uid;
        this.ip = ip;
        this.dateline = dateline;
        this.errorcount = errorcount;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public Byte getErrorcount() {
        return this.errorcount;
    }
    public void setErrorcount(Byte errorcount) {
        this.errorcount = errorcount;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AdminsessionsId) ) return false;
		 AdminsessionsId castOther = ( AdminsessionsId ) other; 
		 return ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getIp()==castOther.getIp()) || ( this.getIp()!=null && castOther.getIp()!=null && this.getIp().equals(castOther.getIp()) ) )
 && ( (this.getDateline()==castOther.getDateline()) || ( this.getDateline()!=null && castOther.getDateline()!=null && this.getDateline().equals(castOther.getDateline()) ) )
 && ( (this.getErrorcount()==castOther.getErrorcount()) || ( this.getErrorcount()!=null && castOther.getErrorcount()!=null && this.getErrorcount().equals(castOther.getErrorcount()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getIp() == null ? 0 : this.getIp().hashCode() );
         result = 37 * result + ( getDateline() == null ? 0 : this.getDateline().hashCode() );
         result = 37 * result + ( getErrorcount() == null ? 0 : this.getErrorcount().hashCode() );
         return result;
   }   
}