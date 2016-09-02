package cn.jsprun.domain;
public class MagiclogId  implements java.io.Serializable {
	private static final long serialVersionUID = 2042756369515912004L;
	private Integer uid;			
     private Short magicid;			
     private Byte action;			
     private Integer dateline;		
     private Short amount;			
     private Integer price;			
     private Integer targettid;		
     private Integer targetpid;		
     private Integer targetuid;		
    public MagiclogId() {
    }
    public MagiclogId(Integer uid, Short magicid, Byte action, Integer dateline, Short amount, Integer price, Integer targettid, Integer targetpid, Integer targetuid) {
        this.uid = uid;
        this.magicid = magicid;
        this.action = action;
        this.dateline = dateline;
        this.amount = amount;
        this.price = price;
        this.targettid = targettid;
        this.targetpid = targetpid;
        this.targetuid = targetuid;
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
    public Byte getAction() {
        return this.action;
    }
    public void setAction(Byte action) {
        this.action = action;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public Short getAmount() {
        return this.amount;
    }
    public void setAmount(Short amount) {
        this.amount = amount;
    }
    public Integer getPrice() {
        return this.price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getTargettid() {
        return this.targettid;
    }
    public void setTargettid(Integer targettid) {
        this.targettid = targettid;
    }
    public Integer getTargetpid() {
        return this.targetpid;
    }
    public void setTargetpid(Integer targetpid) {
        this.targetpid = targetpid;
    }
    public Integer getTargetuid() {
        return this.targetuid;
    }
    public void setTargetuid(Integer targetuid) {
        this.targetuid = targetuid;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof MagiclogId) ) return false;
		 MagiclogId castOther = ( MagiclogId ) other; 
		 return ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getMagicid()==castOther.getMagicid()) || ( this.getMagicid()!=null && castOther.getMagicid()!=null && this.getMagicid().equals(castOther.getMagicid()) ) )
 && ( (this.getAction()==castOther.getAction()) || ( this.getAction()!=null && castOther.getAction()!=null && this.getAction().equals(castOther.getAction()) ) )
 && ( (this.getDateline()==castOther.getDateline()) || ( this.getDateline()!=null && castOther.getDateline()!=null && this.getDateline().equals(castOther.getDateline()) ) )
 && ( (this.getAmount()==castOther.getAmount()) || ( this.getAmount()!=null && castOther.getAmount()!=null && this.getAmount().equals(castOther.getAmount()) ) )
 && ( (this.getPrice()==castOther.getPrice()) || ( this.getPrice()!=null && castOther.getPrice()!=null && this.getPrice().equals(castOther.getPrice()) ) )
 && ( (this.getTargettid()==castOther.getTargettid()) || ( this.getTargettid()!=null && castOther.getTargettid()!=null && this.getTargettid().equals(castOther.getTargettid()) ) )
 && ( (this.getTargetpid()==castOther.getTargetpid()) || ( this.getTargetpid()!=null && castOther.getTargetpid()!=null && this.getTargetpid().equals(castOther.getTargetpid()) ) )
 && ( (this.getTargetuid()==castOther.getTargetuid()) || ( this.getTargetuid()!=null && castOther.getTargetuid()!=null && this.getTargetuid().equals(castOther.getTargetuid()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getMagicid() == null ? 0 : this.getMagicid().hashCode() );
         result = 37 * result + ( getAction() == null ? 0 : this.getAction().hashCode() );
         result = 37 * result + ( getDateline() == null ? 0 : this.getDateline().hashCode() );
         result = 37 * result + ( getAmount() == null ? 0 : this.getAmount().hashCode() );
         result = 37 * result + ( getPrice() == null ? 0 : this.getPrice().hashCode() );
         result = 37 * result + ( getTargettid() == null ? 0 : this.getTargettid().hashCode() );
         result = 37 * result + ( getTargetpid() == null ? 0 : this.getTargetpid().hashCode() );
         result = 37 * result + ( getTargetuid() == null ? 0 : this.getTargetuid().hashCode() );
         return result;
   }   
}