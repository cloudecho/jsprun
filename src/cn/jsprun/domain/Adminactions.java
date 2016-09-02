package cn.jsprun.domain;
public class Adminactions  implements java.io.Serializable {
	private static final long serialVersionUID = 1799327771130456858L;
	private Short admingid;			
     private String disabledactions;	
    public Adminactions() {
    }
    public Adminactions(Short admingid, String disabledactions) {
        this.admingid = admingid;
        this.disabledactions = disabledactions;
    }
    public Short getAdmingid() {
        return this.admingid;
    }
    public void setAdmingid(Short admingid) {
        this.admingid = admingid;
    }
    public String getDisabledactions() {
        return this.disabledactions;
    }
    public void setDisabledactions(String disabledactions) {
        this.disabledactions = disabledactions;
    }
}