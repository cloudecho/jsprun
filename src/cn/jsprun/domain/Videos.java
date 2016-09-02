package cn.jsprun.domain;
public class Videos  implements java.io.Serializable {
	private static final long serialVersionUID = -974527803324108976L;
     private String vid;
     private Integer uid;
     private Integer dateline;
     private Integer tid;
     private Integer pid;
     private Byte vtype;
     private Integer vview;
     private Short vtime;
     private Byte visup;
     private String vthumb;
     private String vtitle;
     private String vclass;
     private Byte vautoplay;
    public Videos() {
    }
    public Videos(String vid, Integer uid, Integer dateline, Integer tid, Integer pid, Byte vtype, Integer vview, Short vtime, Byte visup, String vthumb, String vtitle, String vclass, Byte vautoplay) {
        this.vid = vid;
        this.uid = uid;
        this.dateline = dateline;
        this.tid = tid;
        this.pid = pid;
        this.vtype = vtype;
        this.vview = vview;
        this.vtime = vtime;
        this.visup = visup;
        this.vthumb = vthumb;
        this.vtitle = vtitle;
        this.vclass = vclass;
        this.vautoplay = vautoplay;
    }
    public String getVid() {
        return this.vid;
    }
    public void setVid(String vid) {
        this.vid = vid;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
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
    public Byte getVtype() {
        return this.vtype;
    }
    public void setVtype(Byte vtype) {
        this.vtype = vtype;
    }
    public Integer getVview() {
        return this.vview;
    }
    public void setVview(Integer vview) {
        this.vview = vview;
    }
    public Short getVtime() {
        return this.vtime;
    }
    public void setVtime(Short vtime) {
        this.vtime = vtime;
    }
    public Byte getVisup() {
        return this.visup;
    }
    public void setVisup(Byte visup) {
        this.visup = visup;
    }
    public String getVthumb() {
        return this.vthumb;
    }
    public void setVthumb(String vthumb) {
        this.vthumb = vthumb;
    }
    public String getVtitle() {
        return this.vtitle;
    }
    public void setVtitle(String vtitle) {
        this.vtitle = vtitle;
    }
    public String getVclass() {
        return this.vclass;
    }
    public void setVclass(String vclass) {
        this.vclass = vclass;
    }
    public Byte getVautoplay() {
        return this.vautoplay;
    }
    public void setVautoplay(Byte vautoplay) {
        this.vautoplay = vautoplay;
    }
}