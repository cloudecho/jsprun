package cn.jsprun.domain;
public class TradelogId  implements java.io.Serializable {
	private static final long serialVersionUID = -5559255261219545882L;
     private Integer tid;
     private Integer pid;
     private String orderid;
     private String tradeno;
     private String subject;
     private Double price;
     private Byte quality;
     private Byte itemtype;
     private Short number;
     private Double tax;
     private String locus;
     private Integer sellerid;
     private String seller;
     private String selleraccount;
     private Integer buyerid;
     private String buyer;
     private String buyercontact;
     private Short buyercredits;
     private String buyermsg;
     private Byte status;
     private Integer lastupdate;
     private Byte offline;
     private String buyername;
     private String buyerzip;
     private String buyerphone;
     private String buyermobile;
     private Byte transport;
     private Short transportfee;
     private Double baseprice;
     private Byte discount;
     private Byte ratestatus;
     private String message;
    public TradelogId() {
    }
    public TradelogId(Integer tid, Integer pid, String orderid, String tradeno, String subject, Double price, Byte quality, Byte itemtype, Short number, Double tax, String locus, Integer sellerid, String seller, String selleraccount, Integer buyerid, String buyer, String buyercontact, Short buyercredits, Byte status, Integer lastupdate, Byte offline, String buyername, String buyerzip, String buyerphone, String buyermobile, Byte transport, Short transportfee, Double baseprice, Byte discount, Byte ratestatus, String message) {
        this.tid = tid;
        this.pid = pid;
        this.orderid = orderid;
        this.tradeno = tradeno;
        this.subject = subject;
        this.price = price;
        this.quality = quality;
        this.itemtype = itemtype;
        this.number = number;
        this.tax = tax;
        this.locus = locus;
        this.sellerid = sellerid;
        this.seller = seller;
        this.selleraccount = selleraccount;
        this.buyerid = buyerid;
        this.buyer = buyer;
        this.buyercontact = buyercontact;
        this.buyercredits = buyercredits;
        this.status = status;
        this.lastupdate = lastupdate;
        this.offline = offline;
        this.buyername = buyername;
        this.buyerzip = buyerzip;
        this.buyerphone = buyerphone;
        this.buyermobile = buyermobile;
        this.transport = transport;
        this.transportfee = transportfee;
        this.baseprice = baseprice;
        this.discount = discount;
        this.ratestatus = ratestatus;
        this.message = message;
    }
    public TradelogId(Integer tid, Integer pid, String orderid, String tradeno, String subject, Double price, Byte quality, Byte itemtype, Short number, Double tax, String locus, Integer sellerid, String seller, String selleraccount, Integer buyerid, String buyer, String buyercontact, Short buyercredits, String buyermsg, Byte status, Integer lastupdate, Byte offline, String buyername, String buyerzip, String buyerphone, String buyermobile, Byte transport, Short transportfee, Double baseprice, Byte discount, Byte ratestatus, String message) {
        this.tid = tid;
        this.pid = pid;
        this.orderid = orderid;
        this.tradeno = tradeno;
        this.subject = subject;
        this.price = price;
        this.quality = quality;
        this.itemtype = itemtype;
        this.number = number;
        this.tax = tax;
        this.locus = locus;
        this.sellerid = sellerid;
        this.seller = seller;
        this.selleraccount = selleraccount;
        this.buyerid = buyerid;
        this.buyer = buyer;
        this.buyercontact = buyercontact;
        this.buyercredits = buyercredits;
        this.buyermsg = buyermsg;
        this.status = status;
        this.lastupdate = lastupdate;
        this.offline = offline;
        this.buyername = buyername;
        this.buyerzip = buyerzip;
        this.buyerphone = buyerphone;
        this.buyermobile = buyermobile;
        this.transport = transport;
        this.transportfee = transportfee;
        this.baseprice = baseprice;
        this.discount = discount;
        this.ratestatus = ratestatus;
        this.message = message;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public Integer getPid() {
        return this.pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public String getOrderid() {
        return this.orderid;
    }
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    public String getTradeno() {
        return this.tradeno;
    }
    public void setTradeno(String tradeno) {
        this.tradeno = tradeno;
    }
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public Double getPrice() {
        return this.price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Byte getQuality() {
        return this.quality;
    }
    public void setQuality(Byte quality) {
        this.quality = quality;
    }
    public Byte getItemtype() {
        return this.itemtype;
    }
    public void setItemtype(Byte itemtype) {
        this.itemtype = itemtype;
    }
    public Short getNumber() {
        return this.number;
    }
    public void setNumber(Short number) {
        this.number = number;
    }
    public Double getTax() {
        return this.tax;
    }
    public void setTax(Double tax) {
        this.tax = tax;
    }
    public String getLocus() {
        return this.locus;
    }
    public void setLocus(String locus) {
        this.locus = locus;
    }
    public Integer getSellerid() {
        return this.sellerid;
    }
    public void setSellerid(Integer sellerid) {
        this.sellerid = sellerid;
    }
    public String getSeller() {
        return this.seller;
    }
    public void setSeller(String seller) {
        this.seller = seller;
    }
    public String getSelleraccount() {
        return this.selleraccount;
    }
    public void setSelleraccount(String selleraccount) {
        this.selleraccount = selleraccount;
    }
    public Integer getBuyerid() {
        return this.buyerid;
    }
    public void setBuyerid(Integer buyerid) {
        this.buyerid = buyerid;
    }
    public String getBuyer() {
        return this.buyer;
    }
    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
    public String getBuyercontact() {
        return this.buyercontact;
    }
    public void setBuyercontact(String buyercontact) {
        this.buyercontact = buyercontact;
    }
    public Short getBuyercredits() {
        return this.buyercredits;
    }
    public void setBuyercredits(Short buyercredits) {
        this.buyercredits = buyercredits;
    }
    public String getBuyermsg() {
        return this.buyermsg;
    }
    public void setBuyermsg(String buyermsg) {
        this.buyermsg = buyermsg;
    }
    public Byte getStatus() {
        return this.status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
    public Integer getLastupdate() {
        return this.lastupdate;
    }
    public void setLastupdate(Integer lastupdate) {
        this.lastupdate = lastupdate;
    }
    public Byte getOffline() {
        return this.offline;
    }
    public void setOffline(Byte offline) {
        this.offline = offline;
    }
    public String getBuyername() {
        return this.buyername;
    }
    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }
    public String getBuyerzip() {
        return this.buyerzip;
    }
    public void setBuyerzip(String buyerzip) {
        this.buyerzip = buyerzip;
    }
    public String getBuyerphone() {
        return this.buyerphone;
    }
    public void setBuyerphone(String buyerphone) {
        this.buyerphone = buyerphone;
    }
    public String getBuyermobile() {
        return this.buyermobile;
    }
    public void setBuyermobile(String buyermobile) {
        this.buyermobile = buyermobile;
    }
    public Byte getTransport() {
        return this.transport;
    }
    public void setTransport(Byte transport) {
        this.transport = transport;
    }
    public Short getTransportfee() {
        return this.transportfee;
    }
    public void setTransportfee(Short transportfee) {
        this.transportfee = transportfee;
    }
    public Double getBaseprice() {
        return this.baseprice;
    }
    public void setBaseprice(Double baseprice) {
        this.baseprice = baseprice;
    }
    public Byte getDiscount() {
        return this.discount;
    }
    public void setDiscount(Byte discount) {
        this.discount = discount;
    }
    public Byte getRatestatus() {
        return this.ratestatus;
    }
    public void setRatestatus(Byte ratestatus) {
        this.ratestatus = ratestatus;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TradelogId) ) return false;
		 TradelogId castOther = ( TradelogId ) other; 
		 return ( (this.getTid()==castOther.getTid()) || ( this.getTid()!=null && castOther.getTid()!=null && this.getTid().equals(castOther.getTid()) ) )
 && ( (this.getPid()==castOther.getPid()) || ( this.getPid()!=null && castOther.getPid()!=null && this.getPid().equals(castOther.getPid()) ) )
 && ( (this.getOrderid()==castOther.getOrderid()) || ( this.getOrderid()!=null && castOther.getOrderid()!=null && this.getOrderid().equals(castOther.getOrderid()) ) )
 && ( (this.getTradeno()==castOther.getTradeno()) || ( this.getTradeno()!=null && castOther.getTradeno()!=null && this.getTradeno().equals(castOther.getTradeno()) ) )
 && ( (this.getSubject()==castOther.getSubject()) || ( this.getSubject()!=null && castOther.getSubject()!=null && this.getSubject().equals(castOther.getSubject()) ) )
 && ( (this.getPrice()==castOther.getPrice()) || ( this.getPrice()!=null && castOther.getPrice()!=null && this.getPrice().equals(castOther.getPrice()) ) )
 && ( (this.getQuality()==castOther.getQuality()) || ( this.getQuality()!=null && castOther.getQuality()!=null && this.getQuality().equals(castOther.getQuality()) ) )
 && ( (this.getItemtype()==castOther.getItemtype()) || ( this.getItemtype()!=null && castOther.getItemtype()!=null && this.getItemtype().equals(castOther.getItemtype()) ) )
 && ( (this.getNumber()==castOther.getNumber()) || ( this.getNumber()!=null && castOther.getNumber()!=null && this.getNumber().equals(castOther.getNumber()) ) )
 && ( (this.getTax()==castOther.getTax()) || ( this.getTax()!=null && castOther.getTax()!=null && this.getTax().equals(castOther.getTax()) ) )
 && ( (this.getLocus()==castOther.getLocus()) || ( this.getLocus()!=null && castOther.getLocus()!=null && this.getLocus().equals(castOther.getLocus()) ) )
 && ( (this.getSellerid()==castOther.getSellerid()) || ( this.getSellerid()!=null && castOther.getSellerid()!=null && this.getSellerid().equals(castOther.getSellerid()) ) )
 && ( (this.getSeller()==castOther.getSeller()) || ( this.getSeller()!=null && castOther.getSeller()!=null && this.getSeller().equals(castOther.getSeller()) ) )
 && ( (this.getSelleraccount()==castOther.getSelleraccount()) || ( this.getSelleraccount()!=null && castOther.getSelleraccount()!=null && this.getSelleraccount().equals(castOther.getSelleraccount()) ) )
 && ( (this.getBuyerid()==castOther.getBuyerid()) || ( this.getBuyerid()!=null && castOther.getBuyerid()!=null && this.getBuyerid().equals(castOther.getBuyerid()) ) )
 && ( (this.getBuyer()==castOther.getBuyer()) || ( this.getBuyer()!=null && castOther.getBuyer()!=null && this.getBuyer().equals(castOther.getBuyer()) ) )
 && ( (this.getBuyercontact()==castOther.getBuyercontact()) || ( this.getBuyercontact()!=null && castOther.getBuyercontact()!=null && this.getBuyercontact().equals(castOther.getBuyercontact()) ) )
 && ( (this.getBuyercredits()==castOther.getBuyercredits()) || ( this.getBuyercredits()!=null && castOther.getBuyercredits()!=null && this.getBuyercredits().equals(castOther.getBuyercredits()) ) )
 && ( (this.getBuyermsg()==castOther.getBuyermsg()) || ( this.getBuyermsg()!=null && castOther.getBuyermsg()!=null && this.getBuyermsg().equals(castOther.getBuyermsg()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getLastupdate()==castOther.getLastupdate()) || ( this.getLastupdate()!=null && castOther.getLastupdate()!=null && this.getLastupdate().equals(castOther.getLastupdate()) ) )
 && ( (this.getOffline()==castOther.getOffline()) || ( this.getOffline()!=null && castOther.getOffline()!=null && this.getOffline().equals(castOther.getOffline()) ) )
 && ( (this.getBuyername()==castOther.getBuyername()) || ( this.getBuyername()!=null && castOther.getBuyername()!=null && this.getBuyername().equals(castOther.getBuyername()) ) )
 && ( (this.getBuyerzip()==castOther.getBuyerzip()) || ( this.getBuyerzip()!=null && castOther.getBuyerzip()!=null && this.getBuyerzip().equals(castOther.getBuyerzip()) ) )
 && ( (this.getBuyerphone()==castOther.getBuyerphone()) || ( this.getBuyerphone()!=null && castOther.getBuyerphone()!=null && this.getBuyerphone().equals(castOther.getBuyerphone()) ) )
 && ( (this.getBuyermobile()==castOther.getBuyermobile()) || ( this.getBuyermobile()!=null && castOther.getBuyermobile()!=null && this.getBuyermobile().equals(castOther.getBuyermobile()) ) )
 && ( (this.getTransport()==castOther.getTransport()) || ( this.getTransport()!=null && castOther.getTransport()!=null && this.getTransport().equals(castOther.getTransport()) ) )
 && ( (this.getTransportfee()==castOther.getTransportfee()) || ( this.getTransportfee()!=null && castOther.getTransportfee()!=null && this.getTransportfee().equals(castOther.getTransportfee()) ) )
 && ( (this.getBaseprice()==castOther.getBaseprice()) || ( this.getBaseprice()!=null && castOther.getBaseprice()!=null && this.getBaseprice().equals(castOther.getBaseprice()) ) )
 && ( (this.getDiscount()==castOther.getDiscount()) || ( this.getDiscount()!=null && castOther.getDiscount()!=null && this.getDiscount().equals(castOther.getDiscount()) ) )
 && ( (this.getRatestatus()==castOther.getRatestatus()) || ( this.getRatestatus()!=null && castOther.getRatestatus()!=null && this.getRatestatus().equals(castOther.getRatestatus()) ) )
 && ( (this.getMessage()==castOther.getMessage()) || ( this.getMessage()!=null && castOther.getMessage()!=null && this.getMessage().equals(castOther.getMessage()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getTid() == null ? 0 : this.getTid().hashCode() );
         result = 37 * result + ( getPid() == null ? 0 : this.getPid().hashCode() );
         result = 37 * result + ( getOrderid() == null ? 0 : this.getOrderid().hashCode() );
         result = 37 * result + ( getTradeno() == null ? 0 : this.getTradeno().hashCode() );
         result = 37 * result + ( getSubject() == null ? 0 : this.getSubject().hashCode() );
         result = 37 * result + ( getPrice() == null ? 0 : this.getPrice().hashCode() );
         result = 37 * result + ( getQuality() == null ? 0 : this.getQuality().hashCode() );
         result = 37 * result + ( getItemtype() == null ? 0 : this.getItemtype().hashCode() );
         result = 37 * result + ( getNumber() == null ? 0 : this.getNumber().hashCode() );
         result = 37 * result + ( getTax() == null ? 0 : this.getTax().hashCode() );
         result = 37 * result + ( getLocus() == null ? 0 : this.getLocus().hashCode() );
         result = 37 * result + ( getSellerid() == null ? 0 : this.getSellerid().hashCode() );
         result = 37 * result + ( getSeller() == null ? 0 : this.getSeller().hashCode() );
         result = 37 * result + ( getSelleraccount() == null ? 0 : this.getSelleraccount().hashCode() );
         result = 37 * result + ( getBuyerid() == null ? 0 : this.getBuyerid().hashCode() );
         result = 37 * result + ( getBuyer() == null ? 0 : this.getBuyer().hashCode() );
         result = 37 * result + ( getBuyercontact() == null ? 0 : this.getBuyercontact().hashCode() );
         result = 37 * result + ( getBuyercredits() == null ? 0 : this.getBuyercredits().hashCode() );
         result = 37 * result + ( getBuyermsg() == null ? 0 : this.getBuyermsg().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getLastupdate() == null ? 0 : this.getLastupdate().hashCode() );
         result = 37 * result + ( getOffline() == null ? 0 : this.getOffline().hashCode() );
         result = 37 * result + ( getBuyername() == null ? 0 : this.getBuyername().hashCode() );
         result = 37 * result + ( getBuyerzip() == null ? 0 : this.getBuyerzip().hashCode() );
         result = 37 * result + ( getBuyerphone() == null ? 0 : this.getBuyerphone().hashCode() );
         result = 37 * result + ( getBuyermobile() == null ? 0 : this.getBuyermobile().hashCode() );
         result = 37 * result + ( getTransport() == null ? 0 : this.getTransport().hashCode() );
         result = 37 * result + ( getTransportfee() == null ? 0 : this.getTransportfee().hashCode() );
         result = 37 * result + ( getBaseprice() == null ? 0 : this.getBaseprice().hashCode() );
         result = 37 * result + ( getDiscount() == null ? 0 : this.getDiscount().hashCode() );
         result = 37 * result + ( getRatestatus() == null ? 0 : this.getRatestatus().hashCode() );
         result = 37 * result + ( getMessage() == null ? 0 : this.getMessage().hashCode() );
         return result;
   }   
}