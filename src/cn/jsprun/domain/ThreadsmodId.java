package cn.jsprun.domain;
public class ThreadsmodId  implements java.io.Serializable {
	private static final long serialVersionUID = 5135063376366928035L;
     private Integer tid;
     private Integer uid;
     private String username;
     private Integer dateline;
     private Integer expiration;
     private String action;
     private Byte status;
     private Short magicid;
     private String remark;
    public ThreadsmodId() {
    }
    public ThreadsmodId(Integer tid, Integer uid, String username, Integer dateline, Integer expiration, String action, Byte status, Short magicid,String remark) {
        this.tid = tid;
        this.uid = uid;
        this.username = username;
        this.dateline = dateline;
        this.expiration = expiration;
        this.action = action;
        this.status = status;
        this.magicid = magicid;
        this.remark = remark;
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
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public Integer getExpiration() {
        return this.expiration;
    }
    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
    public String getAction() {
        return this.action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public Byte getStatus() {
        return this.status;
    }
    public void setStatus(Byte status) {
        this.status = status;
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
		 if ( !(other instanceof ThreadsmodId) ) return false;
		 ThreadsmodId castOther = ( ThreadsmodId ) other; 
		 return ( (this.getTid()==castOther.getTid()) || ( this.getTid()!=null && castOther.getTid()!=null && this.getTid().equals(castOther.getTid()) ) )
 && ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getUsername()==castOther.getUsername()) || ( this.getUsername()!=null && castOther.getUsername()!=null && this.getUsername().equals(castOther.getUsername()) ) )
 && ( (this.getDateline()==castOther.getDateline()) || ( this.getDateline()!=null && castOther.getDateline()!=null && this.getDateline().equals(castOther.getDateline()) ) )
 && ( (this.getExpiration()==castOther.getExpiration()) || ( this.getExpiration()!=null && castOther.getExpiration()!=null && this.getExpiration().equals(castOther.getExpiration()) ) )
 && ( (this.getAction()==castOther.getAction()) || ( this.getAction()!=null && castOther.getAction()!=null && this.getAction().equals(castOther.getAction()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getMagicid()==castOther.getMagicid()) || ( this.getMagicid()!=null && castOther.getMagicid()!=null && this.getMagicid().equals(castOther.getMagicid()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getTid() == null ? 0 : this.getTid().hashCode() );
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getUsername() == null ? 0 : this.getUsername().hashCode() );
         result = 37 * result + ( getDateline() == null ? 0 : this.getDateline().hashCode() );
         result = 37 * result + ( getExpiration() == null ? 0 : this.getExpiration().hashCode() );
         result = 37 * result + ( getAction() == null ? 0 : this.getAction().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getMagicid() == null ? 0 : this.getMagicid().hashCode() );
         return result;
   }
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}   
}