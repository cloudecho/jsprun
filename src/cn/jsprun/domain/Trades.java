package cn.jsprun.domain;
public class Trades  implements java.io.Serializable {
	private static final long serialVersionUID = 6902228648108758375L;
     private TradesId id;
     private Short typeid;
     private Integer sellerid;
     private String seller;
     private String account;
     private String subject;
     private Double price;
     private Short amount;
     private Byte quality;
     private String locus;
     private Byte transport;
     private Short ordinaryfee;
     private Short expressfee;
     private Short emsfee;
     private Byte itemtype;
     private Integer dateline;
     private Integer expiration;
     private String lastbuyer;
     private Integer lastupdate;
     private Short totalitems;
     private Double tradesum;
     private Byte closed;
     private Integer aid;
     private Byte displayorder;
     private Double costprice;
    public Trades() {
    }
    public Trades(TradesId id, Short typeid, Integer sellerid, String seller, String account, String subject, Double price, Short amount, Byte quality, String locus, Byte transport, Short ordinaryfee, Short expressfee, Short emsfee, Byte itemtype, Integer dateline, Integer expiration, String lastbuyer, Integer lastupdate, Short totalitems, Double tradesum, Byte closed, Integer aid, Byte displayorder, Double costprice) {
        this.id = id;
        this.typeid = typeid;
        this.sellerid = sellerid;
        this.seller = seller;
        this.account = account;
        this.subject = subject;
        this.price = price;
        this.amount = amount;
        this.quality = quality;
        this.locus = locus;
        this.transport = transport;
        this.ordinaryfee = ordinaryfee;
        this.expressfee = expressfee;
        this.emsfee = emsfee;
        this.itemtype = itemtype;
        this.dateline = dateline;
        this.expiration = expiration;
        this.lastbuyer = lastbuyer;
        this.lastupdate = lastupdate;
        this.totalitems = totalitems;
        this.tradesum = tradesum;
        this.closed = closed;
        this.aid = aid;
        this.displayorder = displayorder;
        this.costprice = costprice;
    }
    public TradesId getId() {
        return this.id;
    }
    public void setId(TradesId id) {
        this.id = id;
    }
    public Short getTypeid() {
        return this.typeid;
    }
    public void setTypeid(Short typeid) {
        this.typeid = typeid;
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
    public String getAccount() {
        return this.account;
    }
    public void setAccount(String account) {
        this.account = account;
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
    public Short getAmount() {
        return this.amount;
    }
    public void setAmount(Short amount) {
        this.amount = amount;
    }
    public Byte getQuality() {
        return this.quality;
    }
    public void setQuality(Byte quality) {
        this.quality = quality;
    }
    public String getLocus() {
        return this.locus;
    }
    public void setLocus(String locus) {
        this.locus = locus;
    }
    public Byte getTransport() {
        return this.transport;
    }
    public void setTransport(Byte transport) {
        this.transport = transport;
    }
    public Short getOrdinaryfee() {
        return this.ordinaryfee;
    }
    public void setOrdinaryfee(Short ordinaryfee) {
        this.ordinaryfee = ordinaryfee;
    }
    public Short getExpressfee() {
        return this.expressfee;
    }
    public void setExpressfee(Short expressfee) {
        this.expressfee = expressfee;
    }
    public Short getEmsfee() {
        return this.emsfee;
    }
    public void setEmsfee(Short emsfee) {
        this.emsfee = emsfee;
    }
    public Byte getItemtype() {
        return this.itemtype;
    }
    public void setItemtype(Byte itemtype) {
        this.itemtype = itemtype;
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
    public String getLastbuyer() {
        return this.lastbuyer;
    }
    public void setLastbuyer(String lastbuyer) {
        this.lastbuyer = lastbuyer;
    }
    public Integer getLastupdate() {
        return this.lastupdate;
    }
    public void setLastupdate(Integer lastupdate) {
        this.lastupdate = lastupdate;
    }
    public Short getTotalitems() {
        return this.totalitems;
    }
    public void setTotalitems(Short totalitems) {
        this.totalitems = totalitems;
    }
    public Double getTradesum() {
        return this.tradesum;
    }
    public void setTradesum(Double tradesum) {
        this.tradesum = tradesum;
    }
    public Byte getClosed() {
        return this.closed;
    }
    public void setClosed(Byte closed) {
        this.closed = closed;
    }
    public Integer getAid() {
        return this.aid;
    }
    public void setAid(Integer aid) {
        this.aid = aid;
    }
    public Byte getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Byte displayorder) {
        this.displayorder = displayorder;
    }
    public Double getCostprice() {
        return this.costprice;
    }
    public void setCostprice(Double costprice) {
        this.costprice = costprice;
    }
}