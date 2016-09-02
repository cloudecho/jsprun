package cn.jsprun.domain;
public class Pms  implements java.io.Serializable {
	private static final long serialVersionUID = 9170020541721831667L;
     private Integer pmid;
     private String msgfrom;
     private Integer msgfromid;
     private Integer msgtoid;
     private String folder;
     private Byte new_;
     private String subject;
     private Integer dateline;
     private String message;
     private Byte delstatus;
    public Pms() {
    }
    public Pms(String msgfrom, Integer msgfromid, Integer msgtoid, String folder, Byte new_, String subject, Integer dateline, String message, Byte delstatus) {
        this.msgfrom = msgfrom;
        this.msgfromid = msgfromid;
        this.msgtoid = msgtoid;
        this.folder = folder;
        this.new_ = new_;
        this.subject = subject;
        this.dateline = dateline;
        this.message = message;
        this.delstatus = delstatus;
    }
    public Integer getPmid() {
        return this.pmid;
    }
    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }
    public String getMsgfrom() {
        return this.msgfrom;
    }
    public void setMsgfrom(String msgfrom) {
        this.msgfrom = msgfrom;
    }
    public Integer getMsgfromid() {
        return this.msgfromid;
    }
    public void setMsgfromid(Integer msgfromid) {
        this.msgfromid = msgfromid;
    }
    public Integer getMsgtoid() {
        return this.msgtoid;
    }
    public void setMsgtoid(Integer msgtoid) {
        this.msgtoid = msgtoid;
    }
    public String getFolder() {
        return this.folder;
    }
    public void setFolder(String folder) {
        this.folder = folder;
    }
    public Byte getNew_() {
        return this.new_;
    }
    public void setNew_(Byte new_) {
        this.new_ = new_;
    }
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Byte getDelstatus() {
        return this.delstatus;
    }
    public void setDelstatus(Byte delstatus) {
        this.delstatus = delstatus;
    }
}