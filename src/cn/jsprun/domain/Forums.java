package cn.jsprun.domain;
public class Forums  implements java.io.Serializable {
	private static final long serialVersionUID = 7954704517456417373L;
	private Short fid;				
     private Short fup;				
     private String type;			
     private String name;			
     private Byte status;			
     private Short displayorder;	
     private Short styleid;			
     private Integer threads;		
     private Integer posts;			
     private Integer todayposts;	
     private String lastpost;		
     private Byte allowsmilies;		
     private Byte allowhtml;		
     private Byte allowbbcode;		
     private Byte allowimgcode;		
     private Byte allowmediacode;	
     private Byte allowanonymous;	
     private Byte allowshare;		
     private Short allowpostspecial;
     private Byte allowspecialonly;	
     private Byte alloweditrules;	
     private Byte recyclebin;		
     private Byte modnewposts;		
     private Byte jammer;			
     private Byte disablewatermark;	
     private Byte inheritedmod;		
     private Short autoclose;		
     private Short forumcolumns;	
     private Byte threadcaches;		
     private Byte allowpaytoauthor;	
     private Byte alloweditpost;	
     private Short simple;			
    public Forums() {
    }
    public Forums(Short fup, String type, String name, Byte status, Short displayorder, Short styleid, Integer threads, Integer posts, Integer todayposts, String lastpost, Byte allowsmilies, Byte allowhtml, Byte allowbbcode, Byte allowimgcode, Byte allowmediacode, Byte allowanonymous, Byte allowshare, Short allowpostspecial, Byte allowspecialonly, Byte alloweditrules, Byte recyclebin, Byte modnewposts, Byte jammer, Byte disablewatermark, Byte inheritedmod, Short autoclose, Short forumcolumns, Byte threadcaches, Byte allowpaytoauthor, Byte alloweditpost, Short simple) {
        this.fup = fup;
        this.type = type;
        this.name = name;
        this.status = status;
        this.displayorder = displayorder;
        this.styleid = styleid;
        this.threads = threads;
        this.posts = posts;
        this.todayposts = todayposts;
        this.lastpost = lastpost;
        this.allowsmilies = allowsmilies;
        this.allowhtml = allowhtml;
        this.allowbbcode = allowbbcode;
        this.allowimgcode = allowimgcode;
        this.allowmediacode = allowmediacode;
        this.allowanonymous = allowanonymous;
        this.allowshare = allowshare;
        this.allowpostspecial = allowpostspecial;
        this.allowspecialonly = allowspecialonly;
        this.alloweditrules = alloweditrules;
        this.recyclebin = recyclebin;
        this.modnewposts = modnewposts;
        this.jammer = jammer;
        this.disablewatermark = disablewatermark;
        this.inheritedmod = inheritedmod;
        this.autoclose = autoclose;
        this.forumcolumns = forumcolumns;
        this.threadcaches = threadcaches;
        this.allowpaytoauthor = allowpaytoauthor;
        this.alloweditpost = alloweditpost;
        this.simple = simple;
    }
    public Short getFid() {
        return this.fid;
    }
    public void setFid(Short fid) {
        this.fid = fid;
    }
    public Short getFup() {
        return this.fup;
    }
    public void setFup(Short fup) {
        this.fup = fup;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Byte getStatus() {
        return this.status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
    public Short getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Short displayorder) {
        this.displayorder = displayorder;
    }
    public Short getStyleid() {
        return this.styleid;
    }
    public void setStyleid(Short styleid) {
        this.styleid = styleid;
    }
    public Integer getThreads() {
        return this.threads;
    }
    public void setThreads(Integer threads) {
        this.threads = threads;
    }
    public Integer getPosts() {
        return this.posts;
    }
    public void setPosts(Integer posts) {
        this.posts = posts;
    }
    public Integer getTodayposts() {
        return this.todayposts;
    }
    public void setTodayposts(Integer todayposts) {
        this.todayposts = todayposts;
    }
    public String getLastpost() {
        return this.lastpost;
    }
    public void setLastpost(String lastpost) {
        this.lastpost = lastpost;
    }
    public Byte getAllowsmilies() {
        return this.allowsmilies;
    }
    public void setAllowsmilies(Byte allowsmilies) {
        this.allowsmilies = allowsmilies;
    }
    public Byte getAllowhtml() {
        return this.allowhtml;
    }
    public void setAllowhtml(Byte allowhtml) {
        this.allowhtml = allowhtml;
    }
    public Byte getAllowbbcode() {
        return this.allowbbcode;
    }
    public void setAllowbbcode(Byte allowbbcode) {
        this.allowbbcode = allowbbcode;
    }
    public Byte getAllowimgcode() {
        return this.allowimgcode;
    }
    public void setAllowimgcode(Byte allowimgcode) {
        this.allowimgcode = allowimgcode;
    }
    public Byte getAllowmediacode() {
        return this.allowmediacode;
    }
    public void setAllowmediacode(Byte allowmediacode) {
        this.allowmediacode = allowmediacode;
    }
    public Byte getAllowanonymous() {
        return this.allowanonymous;
    }
    public void setAllowanonymous(Byte allowanonymous) {
        this.allowanonymous = allowanonymous;
    }
    public Byte getAllowshare() {
        return this.allowshare;
    }
    public void setAllowshare(Byte allowshare) {
        this.allowshare = allowshare;
    }
    public Short getAllowpostspecial() {
        return this.allowpostspecial;
    }
    public void setAllowpostspecial(Short allowpostspecial) {
        this.allowpostspecial = allowpostspecial;
    }
    public Byte getAllowspecialonly() {
        return this.allowspecialonly;
    }
    public void setAllowspecialonly(Byte allowspecialonly) {
        this.allowspecialonly = allowspecialonly;
    }
    public Byte getAlloweditrules() {
        return this.alloweditrules;
    }
    public void setAlloweditrules(Byte alloweditrules) {
        this.alloweditrules = alloweditrules;
    }
    public Byte getRecyclebin() {
        return this.recyclebin;
    }
    public void setRecyclebin(Byte recyclebin) {
        this.recyclebin = recyclebin;
    }
    public Byte getModnewposts() {
        return this.modnewposts;
    }
    public void setModnewposts(Byte modnewposts) {
        this.modnewposts = modnewposts;
    }
    public Byte getJammer() {
        return this.jammer;
    }
    public void setJammer(Byte jammer) {
        this.jammer = jammer;
    }
    public Byte getDisablewatermark() {
        return this.disablewatermark;
    }
    public void setDisablewatermark(Byte disablewatermark) {
        this.disablewatermark = disablewatermark;
    }
    public Byte getInheritedmod() {
        return this.inheritedmod;
    }
    public void setInheritedmod(Byte inheritedmod) {
        this.inheritedmod = inheritedmod;
    }
    public Short getAutoclose() {
        return this.autoclose;
    }
    public void setAutoclose(Short autoclose) {
        this.autoclose = autoclose;
    }
    public Short getForumcolumns() {
        return this.forumcolumns;
    }
    public void setForumcolumns(Short forumcolumns) {
        this.forumcolumns = forumcolumns;
    }
    public Byte getThreadcaches() {
        return this.threadcaches;
    }
    public void setThreadcaches(Byte threadcaches) {
        this.threadcaches = threadcaches;
    }
    public Byte getAllowpaytoauthor() {
        return this.allowpaytoauthor;
    }
    public void setAllowpaytoauthor(Byte allowpaytoauthor) {
        this.allowpaytoauthor = allowpaytoauthor;
    }
    public Byte getAlloweditpost() {
        return this.alloweditpost;
    }
    public void setAlloweditpost(Byte alloweditpost) {
        this.alloweditpost = alloweditpost;
    }
    public Short getSimple() {
        return this.simple;
    }
    public void setSimple(Short simple) {
        this.simple = simple;
    }
}