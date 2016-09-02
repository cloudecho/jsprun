package cn.jsprun.domain;
public class CreditslogId  implements java.io.Serializable {
	private static final long serialVersionUID = -3855465308410988372L;
	private Integer uid;			
     private String fromto;			
     private Byte sendcredits;		
     private Byte receivecredits;	
     private Integer send;			
     private Integer receive;		
     private Integer dateline;		
     private String operation;		
    public CreditslogId() {
    }
    public CreditslogId(Integer uid, String fromto, Byte sendcredits, Byte receivecredits, Integer send, Integer receive, Integer dateline, String operation) {
        this.uid = uid;
        this.fromto = fromto;
        this.sendcredits = sendcredits;
        this.receivecredits = receivecredits;
        this.send = send;
        this.receive = receive;
        this.dateline = dateline;
        this.operation = operation;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getFromto() {
        return this.fromto;
    }
    public void setFromto(String fromto) {
        this.fromto = fromto;
    }
    public Byte getSendcredits() {
        return this.sendcredits;
    }
    public void setSendcredits(Byte sendcredits) {
        this.sendcredits = sendcredits;
    }
    public Byte getReceivecredits() {
        return this.receivecredits;
    }
    public void setReceivecredits(Byte receivecredits) {
        this.receivecredits = receivecredits;
    }
    public Integer getSend() {
        return this.send;
    }
    public void setSend(Integer send) {
        this.send = send;
    }
    public Integer getReceive() {
        return this.receive;
    }
    public void setReceive(Integer receive) {
        this.receive = receive;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public String getOperation() {
        return this.operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CreditslogId) ) return false;
		 CreditslogId castOther = ( CreditslogId ) other; 
		 return ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getFromto()==castOther.getFromto()) || ( this.getFromto()!=null && castOther.getFromto()!=null && this.getFromto().equals(castOther.getFromto()) ) )
 && ( (this.getSendcredits()==castOther.getSendcredits()) || ( this.getSendcredits()!=null && castOther.getSendcredits()!=null && this.getSendcredits().equals(castOther.getSendcredits()) ) )
 && ( (this.getReceivecredits()==castOther.getReceivecredits()) || ( this.getReceivecredits()!=null && castOther.getReceivecredits()!=null && this.getReceivecredits().equals(castOther.getReceivecredits()) ) )
 && ( (this.getSend()==castOther.getSend()) || ( this.getSend()!=null && castOther.getSend()!=null && this.getSend().equals(castOther.getSend()) ) )
 && ( (this.getReceive()==castOther.getReceive()) || ( this.getReceive()!=null && castOther.getReceive()!=null && this.getReceive().equals(castOther.getReceive()) ) )
 && ( (this.getDateline()==castOther.getDateline()) || ( this.getDateline()!=null && castOther.getDateline()!=null && this.getDateline().equals(castOther.getDateline()) ) )
 && ( (this.getOperation()==castOther.getOperation()) || ( this.getOperation()!=null && castOther.getOperation()!=null && this.getOperation().equals(castOther.getOperation()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getFromto() == null ? 0 : this.getFromto().hashCode() );
         result = 37 * result + ( getSendcredits() == null ? 0 : this.getSendcredits().hashCode() );
         result = 37 * result + ( getReceivecredits() == null ? 0 : this.getReceivecredits().hashCode() );
         result = 37 * result + ( getSend() == null ? 0 : this.getSend().hashCode() );
         result = 37 * result + ( getReceive() == null ? 0 : this.getReceive().hashCode() );
         result = 37 * result + ( getDateline() == null ? 0 : this.getDateline().hashCode() );
         result = 37 * result + ( getOperation() == null ? 0 : this.getOperation().hashCode() );
         return result;
   }   
}