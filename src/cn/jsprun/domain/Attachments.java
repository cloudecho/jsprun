package cn.jsprun.domain;
public class Attachments  implements java.io.Serializable {
	private static final long serialVersionUID = 1467204491765691270L;
	private Integer aid;			
     private Integer tid;			
     private Integer pid;			
     private Integer dateline;		
     private Short readperm;		
     private Short price;			
     private String filename;		
     private String description;	
     private String filetype;		
     private Integer filesize;		
     private String attachment;		
     private Integer downloads;		
     private Byte isimage;			
     private Integer uid;			
     private Byte thumb;			
     private Byte remote;			
    public Attachments() {
    }
    public Attachments(Integer tid, Integer pid, Integer dateline, Short readperm, Short price, String filename, String description, String filetype, Integer filesize, String attachment, Integer downloads, Byte isimage, Integer uid, Byte thumb, Byte remote) {
        this.tid = tid;
        this.pid = pid;
        this.dateline = dateline;
        this.readperm = readperm;
        this.price = price;
        this.filename = filename;
        this.description = description;
        this.filetype = filetype;
        this.filesize = filesize;
        this.attachment = attachment;
        this.downloads = downloads;
        this.isimage = isimage;
        this.uid = uid;
        this.thumb = thumb;
        this.remote = remote;
    }
    public Integer getAid() {
        return this.aid;
    }
    public void setAid(Integer aid) {
        this.aid = aid;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public Integer getPid() {
        return this.pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public Short getReadperm() {
        return this.readperm;
    }
    public void setReadperm(Short readperm) {
        this.readperm = readperm;
    }
    public Short getPrice() {
        return this.price;
    }
    public void setPrice(Short price) {
        this.price = price;
    }
    public String getFilename() {
        return this.filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getFiletype() {
        return this.filetype;
    }
    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
    public Integer getFilesize() {
        return this.filesize;
    }
    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }
    public String getAttachment() {
        return this.attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public Integer getDownloads() {
        return this.downloads;
    }
    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }
    public Byte getIsimage() {
        return this.isimage;
    }
    public void setIsimage(Byte isimage) {
        this.isimage = isimage;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Byte getThumb() {
        return this.thumb;
    }
    public void setThumb(Byte thumb) {
        this.thumb = thumb;
    }
    public Byte getRemote() {
        return this.remote;
    }
    public void setRemote(Byte remote) {
        this.remote = remote;
    }
}