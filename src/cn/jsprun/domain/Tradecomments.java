package cn.jsprun.domain;
public class Tradecomments  implements java.io.Serializable {
	private static final long serialVersionUID = 6992985545165581679L;
     private Integer id;
     private String orderid;
     private Integer pid;
     private Byte type;
     private Integer raterid;
     private String rater;
     private Integer rateeid;
     private String ratee;
     private String message;
     private String explanation;
     private Byte score;
     private Integer dateline;
    public Tradecomments() {
    }
    public Tradecomments(String orderid, Integer pid, Byte type, Integer raterid, String rater, Integer rateeid, String ratee, String message, String explanation, Byte score, Integer dateline) {
        this.orderid = orderid;
        this.pid = pid;
        this.type = type;
        this.raterid = raterid;
        this.rater = rater;
        this.rateeid = rateeid;
        this.ratee = ratee;
        this.message = message;
        this.explanation = explanation;
        this.score = score;
        this.dateline = dateline;
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getOrderid() {
        return this.orderid;
    }
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    public Integer getPid() {
        return this.pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public Byte getType() {
        return this.type;
    }
    public void setType(Byte type) {
        this.type = type;
    }
    public Integer getRaterid() {
        return this.raterid;
    }
    public void setRaterid(Integer raterid) {
        this.raterid = raterid;
    }
    public String getRater() {
        return this.rater;
    }
    public void setRater(String rater) {
        this.rater = rater;
    }
    public Integer getRateeid() {
        return this.rateeid;
    }
    public void setRateeid(Integer rateeid) {
        this.rateeid = rateeid;
    }
    public String getRatee() {
        return this.ratee;
    }
    public void setRatee(String ratee) {
        this.ratee = ratee;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getExplanation() {
        return this.explanation;
    }
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    public Byte getScore() {
        return this.score;
    }
    public void setScore(Byte score) {
        this.score = score;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
}