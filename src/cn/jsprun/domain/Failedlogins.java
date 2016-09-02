package cn.jsprun.domain;
public class Failedlogins  implements java.io.Serializable {
	private static final long serialVersionUID = -5011365280839625333L;
	private String ip;				
     private Byte count;			
     private Integer lastupdate;	
    public Failedlogins() {
    }
    public Failedlogins(String ip, Byte count, Integer lastupdate) {
        this.ip = ip;
        this.count = count;
        this.lastupdate = lastupdate;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public Byte getCount() {
        return this.count;
    }
    public void setCount(Byte count) {
        this.count = count;
    }
    public Integer getLastupdate() {
        return this.lastupdate;
    }
    public void setLastupdate(Integer lastupdate) {
        this.lastupdate = lastupdate;
    }
}