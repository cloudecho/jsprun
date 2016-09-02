package cn.jsprun.domain;
public class Polls  implements java.io.Serializable {
	private static final long serialVersionUID = -4192114922857066780L;
     private Integer tid;
     private Byte multiple;
     private Byte visible;
     private Short maxchoices;
     private Integer expiration;
    public Polls() {
    }
    public Polls(Integer tid, Byte multiple, Byte visible, Short maxchoices, Integer expiration) {
        this.tid = tid;
        this.multiple = multiple;
        this.visible = visible;
        this.maxchoices = maxchoices;
        this.expiration = expiration;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public Byte getMultiple() {
        return this.multiple;
    }
    public void setMultiple(Byte multiple) {
        this.multiple = multiple;
    }
    public Byte getVisible() {
        return this.visible;
    }
    public void setVisible(Byte visible) {
        this.visible = visible;
    }
    public Short getMaxchoices() {
        return this.maxchoices;
    }
    public void setMaxchoices(Short maxchoices) {
        this.maxchoices = maxchoices;
    }
    public Integer getExpiration() {
        return this.expiration;
    }
    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}