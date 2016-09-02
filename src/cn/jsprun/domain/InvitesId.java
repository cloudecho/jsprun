package cn.jsprun.domain;
public class InvitesId  implements java.io.Serializable {
	private static final long serialVersionUID = 5960108274785946090L;
	private Integer uid;			
     private Integer dateline;		
     private Integer expiration;	
     private String inviteip;		
     private String invitecode;		
     private Integer reguid;		
     private Integer regdateline;	
     private Byte status;			
    public InvitesId() {
    }
    public InvitesId(Integer uid, Integer dateline, Integer expiration, String inviteip, String invitecode, Integer reguid, Integer regdateline, Byte status) {
        this.uid = uid;
        this.dateline = dateline;
        this.expiration = expiration;
        this.inviteip = inviteip;
        this.invitecode = invitecode;
        this.reguid = reguid;
        this.regdateline = regdateline;
        this.status = status;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
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
    public String getInviteip() {
        return this.inviteip;
    }
    public void setInviteip(String inviteip) {
        this.inviteip = inviteip;
    }
    public String getInvitecode() {
        return this.invitecode;
    }
    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
    }
    public Integer getReguid() {
        return this.reguid;
    }
    public void setReguid(Integer reguid) {
        this.reguid = reguid;
    }
    public Integer getRegdateline() {
        return this.regdateline;
    }
    public void setRegdateline(Integer regdateline) {
        this.regdateline = regdateline;
    }
    public Byte getStatus() {
        return this.status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof InvitesId) ) return false;
		 InvitesId castOther = ( InvitesId ) other; 
		 return ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getDateline()==castOther.getDateline()) || ( this.getDateline()!=null && castOther.getDateline()!=null && this.getDateline().equals(castOther.getDateline()) ) )
 && ( (this.getExpiration()==castOther.getExpiration()) || ( this.getExpiration()!=null && castOther.getExpiration()!=null && this.getExpiration().equals(castOther.getExpiration()) ) )
 && ( (this.getInviteip()==castOther.getInviteip()) || ( this.getInviteip()!=null && castOther.getInviteip()!=null && this.getInviteip().equals(castOther.getInviteip()) ) )
 && ( (this.getInvitecode()==castOther.getInvitecode()) || ( this.getInvitecode()!=null && castOther.getInvitecode()!=null && this.getInvitecode().equals(castOther.getInvitecode()) ) )
 && ( (this.getReguid()==castOther.getReguid()) || ( this.getReguid()!=null && castOther.getReguid()!=null && this.getReguid().equals(castOther.getReguid()) ) )
 && ( (this.getRegdateline()==castOther.getRegdateline()) || ( this.getRegdateline()!=null && castOther.getRegdateline()!=null && this.getRegdateline().equals(castOther.getRegdateline()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getDateline() == null ? 0 : this.getDateline().hashCode() );
         result = 37 * result + ( getExpiration() == null ? 0 : this.getExpiration().hashCode() );
         result = 37 * result + ( getInviteip() == null ? 0 : this.getInviteip().hashCode() );
         result = 37 * result + ( getInvitecode() == null ? 0 : this.getInvitecode().hashCode() );
         result = 37 * result + ( getReguid() == null ? 0 : this.getReguid().hashCode() );
         result = 37 * result + ( getRegdateline() == null ? 0 : this.getRegdateline().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         return result;
   }   
}