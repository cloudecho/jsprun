package cn.jsprun.domain;
public class Onlinetime  implements java.io.Serializable {
	private static final long serialVersionUID = -4676894485566800642L;
     private Integer uid;
     private Integer thismonth;
     private Integer total;
     private Integer lastupdate;
    public Onlinetime() {
    }
    public Onlinetime(Integer uid, Integer thismonth, Integer total, Integer lastupdate) {
        this.uid = uid;
        this.thismonth = thismonth;
        this.total = total;
        this.lastupdate = lastupdate;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getThismonth() {
        return this.thismonth;
    }
    public void setThismonth(Integer thismonth) {
        this.thismonth = thismonth;
    }
    public Integer getTotal() {
        return this.total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Integer getLastupdate() {
        return this.lastupdate;
    }
    public void setLastupdate(Integer lastupdate) {
        this.lastupdate = lastupdate;
    }
}