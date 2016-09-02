package cn.jsprun.domain;
public class Paymentlog  implements java.io.Serializable {
	private static final long serialVersionUID = 5804511903829170936L;
     private PaymentlogId id;
     private Integer authorid;
     private Integer dateline;
     private Integer amount;
     private Integer netamount;
    public Paymentlog() {
    }
    public Paymentlog(PaymentlogId id, Integer authorid, Integer dateline, Integer amount, Integer netamount) {
        this.id = id;
        this.authorid = authorid;
        this.dateline = dateline;
        this.amount = amount;
        this.netamount = netamount;
    }
    public PaymentlogId getId() {
        return this.id;
    }
    public void setId(PaymentlogId id) {
        this.id = id;
    }
    public Integer getAuthorid() {
        return this.authorid;
    }
    public void setAuthorid(Integer authorid) {
        this.authorid = authorid;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public Integer getAmount() {
        return this.amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public Integer getNetamount() {
        return this.netamount;
    }
    public void setNetamount(Integer netamount) {
        this.netamount = netamount;
    }
}