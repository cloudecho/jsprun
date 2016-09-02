package cn.jsprun.domain;
public class Validating  implements java.io.Serializable {
	private static final long serialVersionUID = -3530626443173101559L;
     private Integer uid;
     private Integer submitdate;
     private Integer moddate;
     private String admin;
     private Short submittimes;
     private Byte status;
     private String message;
     private String remark;
    public Validating() {
    }
    public Validating(Integer uid, Integer submitdate, Integer moddate, String admin, Short submittimes, Byte status, String message, String remark) {
        this.uid = uid;
        this.submitdate = submitdate;
        this.moddate = moddate;
        this.admin = admin;
        this.submittimes = submittimes;
        this.status = status;
        this.message = message;
        this.remark = remark;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getSubmitdate() {
        return this.submitdate;
    }
    public void setSubmitdate(Integer submitdate) {
        this.submitdate = submitdate;
    }
    public Integer getModdate() {
        return this.moddate;
    }
    public void setModdate(Integer moddate) {
        this.moddate = moddate;
    }
    public String getAdmin() {
        return this.admin;
    }
    public void setAdmin(String admin) {
        this.admin = admin;
    }
    public Short getSubmittimes() {
        return this.submittimes;
    }
    public void setSubmittimes(Short submittimes) {
        this.submittimes = submittimes;
    }
    public Byte getStatus() {
        return this.status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}