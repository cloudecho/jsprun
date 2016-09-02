package cn.jsprun.domain;
public class BuddysId  implements java.io.Serializable {
	private static final long serialVersionUID = 254798548074119204L;
	private Integer uid; 			
     private Integer buddyid;		
     private Integer dateline;		
     private String description;	
    public BuddysId() {
    }
    public BuddysId(Integer uid, Integer buddyid, Integer dateline, String description) {
        this.uid = uid;
        this.buddyid = buddyid;
        this.dateline = dateline;
        this.description = description;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getBuddyid() {
        return this.buddyid;
    }
    public void setBuddyid(Integer buddyid) {
        this.buddyid = buddyid;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof BuddysId) ) return false;
		 BuddysId castOther = ( BuddysId ) other; 
		 return ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getBuddyid()==castOther.getBuddyid()) || ( this.getBuddyid()!=null && castOther.getBuddyid()!=null && this.getBuddyid().equals(castOther.getBuddyid()) ) )
 && ( (this.getDateline()==castOther.getDateline()) || ( this.getDateline()!=null && castOther.getDateline()!=null && this.getDateline().equals(castOther.getDateline()) ) )
 && ( (this.getDescription()==castOther.getDescription()) || ( this.getDescription()!=null && castOther.getDescription()!=null && this.getDescription().equals(castOther.getDescription()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getBuddyid() == null ? 0 : this.getBuddyid().hashCode() );
         result = 37 * result + ( getDateline() == null ? 0 : this.getDateline().hashCode() );
         result = 37 * result + ( getDescription() == null ? 0 : this.getDescription().hashCode() );
         return result;
   }   
}