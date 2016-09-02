package cn.jsprun.domain;
public class OrdersId  implements java.io.Serializable {
	private static final long serialVersionUID = -6182471772784532749L;
     private String orderid;
     private String status;
     private String buyer;
     private String admin;
     private Integer uid;
     private Integer amount;
     private Float price;
     private Integer submitdate;
     private Integer confirmdate;
    public OrdersId() {
    }
    public OrdersId(String orderid, String status, String buyer, String admin, Integer uid, Integer amount, Float price, Integer submitdate, Integer confirmdate) {
        this.orderid = orderid;
        this.status = status;
        this.buyer = buyer;
        this.admin = admin;
        this.uid = uid;
        this.amount = amount;
        this.price = price;
        this.submitdate = submitdate;
        this.confirmdate = confirmdate;
    }
    public String getOrderid() {
        return this.orderid;
    }
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getBuyer() {
        return this.buyer;
    }
    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
    public String getAdmin() {
        return this.admin;
    }
    public void setAdmin(String admin) {
        this.admin = admin;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getAmount() {
        return this.amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public Float getPrice() {
        return this.price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
    public Integer getSubmitdate() {
        return this.submitdate;
    }
    public void setSubmitdate(Integer submitdate) {
        this.submitdate = submitdate;
    }
    public Integer getConfirmdate() {
        return this.confirmdate;
    }
    public void setConfirmdate(Integer confirmdate) {
        this.confirmdate = confirmdate;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof OrdersId) ) return false;
		 OrdersId castOther = ( OrdersId ) other; 
		 return ( (this.getOrderid()==castOther.getOrderid()) || ( this.getOrderid()!=null && castOther.getOrderid()!=null && this.getOrderid().equals(castOther.getOrderid()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getBuyer()==castOther.getBuyer()) || ( this.getBuyer()!=null && castOther.getBuyer()!=null && this.getBuyer().equals(castOther.getBuyer()) ) )
 && ( (this.getAdmin()==castOther.getAdmin()) || ( this.getAdmin()!=null && castOther.getAdmin()!=null && this.getAdmin().equals(castOther.getAdmin()) ) )
 && ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getAmount()==castOther.getAmount()) || ( this.getAmount()!=null && castOther.getAmount()!=null && this.getAmount().equals(castOther.getAmount()) ) )
 && ( (this.getPrice()==castOther.getPrice()) || ( this.getPrice()!=null && castOther.getPrice()!=null && this.getPrice().equals(castOther.getPrice()) ) )
 && ( (this.getSubmitdate()==castOther.getSubmitdate()) || ( this.getSubmitdate()!=null && castOther.getSubmitdate()!=null && this.getSubmitdate().equals(castOther.getSubmitdate()) ) )
 && ( (this.getConfirmdate()==castOther.getConfirmdate()) || ( this.getConfirmdate()!=null && castOther.getConfirmdate()!=null && this.getConfirmdate().equals(castOther.getConfirmdate()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getOrderid() == null ? 0 : this.getOrderid().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getBuyer() == null ? 0 : this.getBuyer().hashCode() );
         result = 37 * result + ( getAdmin() == null ? 0 : this.getAdmin().hashCode() );
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getAmount() == null ? 0 : this.getAmount().hashCode() );
         result = 37 * result + ( getPrice() == null ? 0 : this.getPrice().hashCode() );
         result = 37 * result + ( getSubmitdate() == null ? 0 : this.getSubmitdate().hashCode() );
         result = 37 * result + ( getConfirmdate() == null ? 0 : this.getConfirmdate().hashCode() );
         return result;
   }   
}