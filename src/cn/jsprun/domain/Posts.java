package cn.jsprun.domain;
public class Posts  implements java.io.Serializable {
	private static final long serialVersionUID = 4330001393975118759L;
     private Integer pid;
     private Short fid;
     private Integer tid;
     private Byte first;
     private String author;
     private Integer authorid;
     private String subject;
     private Integer dateline;
     private String message;
     private String useip;
     private Byte invisible;
     private Byte anonymous;
     private Byte usesig;
     private Byte htmlon;
     private Byte bbcodeoff;
     private Byte smileyoff;
     private Byte parseurloff;
     private Byte attachment;
     private Short rate;
     private Short ratetimes;
     private Byte status;
    public Posts() {
    }
    public Posts(Short fid, Integer tid, Byte first, String author, Integer authorid, String subject, Integer dateline, String message, String useip, Byte invisible, Byte anonymous, Byte usesig, Byte htmlon, Byte bbcodeoff, Byte smileyoff, Byte parseurloff, Byte attachment, Short rate, Short ratetimes, Byte status) {
        this.fid = fid;
        this.tid = tid;
        this.first = first;
        this.author = author;
        this.authorid = authorid;
        this.subject = subject;
        this.dateline = dateline;
        this.message = message;
        this.useip = useip;
        this.invisible = invisible;
        this.anonymous = anonymous;
        this.usesig = usesig;
        this.htmlon = htmlon;
        this.bbcodeoff = bbcodeoff;
        this.smileyoff = smileyoff;
        this.parseurloff = parseurloff;
        this.attachment = attachment;
        this.rate = rate;
        this.ratetimes = ratetimes;
        this.status = status;
    }
    public Integer getPid() {
        return this.pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public Short getFid() {
        return this.fid;
    }
    public void setFid(Short fid) {
        this.fid = fid;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public Byte getFirst() {
        return this.first;
    }
    public void setFirst(Byte first) {
        this.first = first;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Integer getAuthorid() {
        return this.authorid;
    }
    public void setAuthorid(Integer authorid) {
        this.authorid = authorid;
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
    public String getUseip() {
        return this.useip;
    }
    public void setUseip(String useip) {
        this.useip = useip;
    }
    public Byte getInvisible() {
        return this.invisible;
    }
    public void setInvisible(Byte invisible) {
        this.invisible = invisible;
    }
    public Byte getAnonymous() {
        return this.anonymous;
    }
    public void setAnonymous(Byte anonymous) {
        this.anonymous = anonymous;
    }
    public Byte getUsesig() {
        return this.usesig;
    }
    public void setUsesig(Byte usesig) {
        this.usesig = usesig;
    }
    public Byte getHtmlon() {
        return this.htmlon;
    }
    public void setHtmlon(Byte htmlon) {
        this.htmlon = htmlon;
    }
    public Byte getBbcodeoff() {
        return this.bbcodeoff;
    }
    public void setBbcodeoff(Byte bbcodeoff) {
        this.bbcodeoff = bbcodeoff;
    }
    public Byte getSmileyoff() {
        return this.smileyoff;
    }
    public void setSmileyoff(Byte smileyoff) {
        this.smileyoff = smileyoff;
    }
    public Byte getParseurloff() {
        return this.parseurloff;
    }
    public void setParseurloff(Byte parseurloff) {
        this.parseurloff = parseurloff;
    }
    public Byte getAttachment() {
        return this.attachment;
    }
    public void setAttachment(Byte attachment) {
        this.attachment = attachment;
    }
    public Short getRate() {
        return this.rate;
    }
    public void setRate(Short rate) {
        this.rate = rate;
    }
    public Short getRatetimes() {
        return this.ratetimes;
    }
    public void setRatetimes(Short ratetimes) {
        this.ratetimes = ratetimes;
    }
    public Byte getStatus() {
        return this.status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
}