package cn.jsprun.domain;
public class Members  implements java.io.Serializable {
	private static final long serialVersionUID = 1531481337601673981L;
	private String grouptitle;		
	 private int radminid;			
	 private String type;		
	 private String adminname;		
     private Integer uid;			
     private String username;		
     private String password;		
     private String secques;		
     private Byte gender;			
     private Byte adminid;			
     private Short groupid;			
     private Integer groupexpiry;	
     private String extgroupids;	
     private String regip;			
     private Integer regdate;		
     private String lastip;			
     private Integer lastvisit;		
     private Integer lastactivity;	
     private Integer lastpost;		
     private Integer posts;			
     private Integer digestposts;		
     private Short oltime;			
     private Integer pageviews;		
     private Integer credits;		
     private Integer extcredits1;	
     private Integer extcredits2;	
     private Integer extcredits3;	
     private Integer extcredits4;	
     private Integer extcredits5;	
     private Integer extcredits6;	
     private Integer extcredits7;	
     private Integer extcredits8;	
     private String email;			
     private String bday;			
     private Byte sigstatus;		
     private Short tpp;				
     private Short ppp;				
     private Short styleid;			
     private Byte dateformat;		
     private Byte timeformat;		
     private Byte pmsound;			
     private Byte showemail;		
     private Byte newsletter;		
     private Byte invisible;		
     private String timeoffset;		
     private Byte newpm;			
     private Byte accessmasks;		
     private Byte editormode;		
     private Byte customshow;		
     private String salt;     
    public Members() {
    }
    public Members(String username, String password, String secques, Byte gender, Byte adminid, Short groupid, Integer groupexpiry, String extgroupids, String regip, Integer regdate, String lastip, Integer lastvisit, Integer lastactivity, Integer lastpost, Integer posts, Integer digestposts, Short oltime, Integer pageviews, Integer credits, Integer extcredits1, Integer extcredits2, Integer extcredits3, Integer extcredits4, Integer extcredits5, Integer extcredits6, Integer extcredits7, Integer extcredits8, String email, String bday, Byte sigstatus, Short tpp, Short ppp, Short styleid, Byte dateformat, Byte timeformat, Byte pmsound, Byte showemail, Byte newsletter, Byte invisible, String timeoffset, Byte newpm, Byte accessmasks, Byte editormode, Byte customshow,String salt) {
        this.username = username;
        this.password = password;
        this.secques = secques;
        this.gender = gender;
        this.adminid = adminid;
        this.groupid = groupid;
        this.groupexpiry = groupexpiry;
        this.extgroupids = extgroupids;
        this.regip = regip;
        this.regdate = regdate;
        this.lastip = lastip;
        this.lastvisit = lastvisit;
        this.lastactivity = lastactivity;
        this.lastpost = lastpost;
        this.posts = posts;
        this.digestposts = digestposts;
        this.oltime = oltime;
        this.pageviews = pageviews;
        this.credits = credits;
        this.extcredits1 = extcredits1;
        this.extcredits2 = extcredits2;
        this.extcredits3 = extcredits3;
        this.extcredits4 = extcredits4;
        this.extcredits5 = extcredits5;
        this.extcredits6 = extcredits6;
        this.extcredits7 = extcredits7;
        this.extcredits8 = extcredits8;
        this.email = email;
        this.bday = bday;
        this.sigstatus = sigstatus;
        this.tpp = tpp;
        this.ppp = ppp;
        this.styleid = styleid;
        this.dateformat = dateformat;
        this.timeformat = timeformat;
        this.pmsound = pmsound;
        this.showemail = showemail;
        this.newsletter = newsletter;
        this.invisible = invisible;
        this.timeoffset = timeoffset;
        this.newpm = newpm;
        this.accessmasks = accessmasks;
        this.editormode = editormode;
        this.customshow = customshow;
        this.salt = salt;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSecques() {
        return this.secques;
    }
    public void setSecques(String secques) {
        this.secques = secques;
    }
    public Byte getGender() {
        return this.gender;
    }
    public void setGender(Byte gender) {
        this.gender = gender;
    }
    public Byte getAdminid() {
        return this.adminid;
    }
    public void setAdminid(Byte adminid) {
        this.adminid = adminid;
    }
    public Short getGroupid() {
        return this.groupid;
    }
    public void setGroupid(Short groupid) {
        this.groupid = groupid;
    }
    public Integer getGroupexpiry() {
        return this.groupexpiry;
    }
    public void setGroupexpiry(Integer groupexpiry) {
        this.groupexpiry = groupexpiry;
    }
    public String getExtgroupids() {
        return this.extgroupids;
    }
    public void setExtgroupids(String extgroupids) {
        this.extgroupids = extgroupids;
    }
    public String getRegip() {
        return this.regip;
    }
    public void setRegip(String regip) {
        this.regip = regip;
    }
    public Integer getRegdate() {
        return this.regdate;
    }
    public void setRegdate(Integer regdate) {
        this.regdate = regdate;
    }
    public String getLastip() {
        return this.lastip;
    }
    public void setLastip(String lastip) {
        this.lastip = lastip;
    }
    public Integer getLastvisit() {
        return this.lastvisit;
    }
    public void setLastvisit(Integer lastvisit) {
        this.lastvisit = lastvisit;
    }
    public Integer getLastactivity() {
        return this.lastactivity;
    }
    public void setLastactivity(Integer lastactivity) {
        this.lastactivity = lastactivity;
    }
    public Integer getLastpost() {
        return this.lastpost;
    }
    public void setLastpost(Integer lastpost) {
        this.lastpost = lastpost;
    }
    public Integer getPosts() {
        return this.posts;
    }
    public void setPosts(Integer posts) {
        this.posts = posts;
    }
    public Integer getDigestposts() {
        return this.digestposts;
    }
    public void setDigestposts(Integer digestposts) {
        this.digestposts = digestposts;
    }
    public Short getOltime() {
        return this.oltime;
    }
    public void setOltime(Short oltime) {
        this.oltime = oltime;
    }
    public Integer getPageviews() {
        return this.pageviews;
    }
    public void setPageviews(Integer pageviews) {
        this.pageviews = pageviews;
    }
    public Integer getCredits() {
        return this.credits;
    }
    public void setCredits(Integer credits) {
        this.credits = credits;
    }
    public Integer getExtcredits1() {
        return this.extcredits1;
    }
    public void setExtcredits1(Integer extcredits1) {
        this.extcredits1 = extcredits1;
    }
    public Integer getExtcredits2() {
        return this.extcredits2;
    }
    public void setExtcredits2(Integer extcredits2) {
        this.extcredits2 = extcredits2;
    }
    public Integer getExtcredits3() {
        return this.extcredits3;
    }
    public void setExtcredits3(Integer extcredits3) {
        this.extcredits3 = extcredits3;
    }
    public Integer getExtcredits4() {
        return this.extcredits4;
    }
    public void setExtcredits4(Integer extcredits4) {
        this.extcredits4 = extcredits4;
    }
    public Integer getExtcredits5() {
        return this.extcredits5;
    }
    public void setExtcredits5(Integer extcredits5) {
        this.extcredits5 = extcredits5;
    }
    public Integer getExtcredits6() {
        return this.extcredits6;
    }
    public void setExtcredits6(Integer extcredits6) {
        this.extcredits6 = extcredits6;
    }
    public Integer getExtcredits7() {
        return this.extcredits7;
    }
    public void setExtcredits7(Integer extcredits7) {
        this.extcredits7 = extcredits7;
    }
    public Integer getExtcredits8() {
        return this.extcredits8;
    }
    public void setExtcredits8(Integer extcredits8) {
        this.extcredits8 = extcredits8;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getBday() {
        return this.bday;
    }
    public void setBday(String bday) {
        this.bday = bday;
    }
    public Byte getSigstatus() {
        return this.sigstatus;
    }
    public void setSigstatus(Byte sigstatus) {
        this.sigstatus = sigstatus;
    }
    public Short getTpp() {
        return this.tpp;
    }
    public void setTpp(Short tpp) {
        this.tpp = tpp;
    }
    public Short getPpp() {
        return this.ppp;
    }
    public void setPpp(Short ppp) {
        this.ppp = ppp;
    }
    public Short getStyleid() {
        return this.styleid;
    }
    public void setStyleid(Short styleid) {
        this.styleid = styleid;
    }
    public Byte getDateformat() {
        return this.dateformat;
    }
    public void setDateformat(Byte dateformat) {
        this.dateformat = dateformat;
    }
    public Byte getTimeformat() {
        return this.timeformat;
    }
    public void setTimeformat(Byte timeformat) {
        this.timeformat = timeformat;
    }
    public Byte getPmsound() {
        return this.pmsound;
    }
    public void setPmsound(Byte pmsound) {
        this.pmsound = pmsound;
    }
    public Byte getShowemail() {
        return this.showemail;
    }
    public void setShowemail(Byte showemail) {
        this.showemail = showemail;
    }
    public Byte getNewsletter() {
        return this.newsletter;
    }
    public void setNewsletter(Byte newsletter) {
        this.newsletter = newsletter;
    }
    public Byte getInvisible() {
        return this.invisible;
    }
    public void setInvisible(Byte invisible) {
        this.invisible = invisible;
    }
    public String getTimeoffset() {
        return this.timeoffset;
    }
    public void setTimeoffset(String timeoffset) {
        this.timeoffset = timeoffset;
    }
    public Byte getNewpm() {
        return this.newpm;
    }
    public void setNewpm(Byte newpm) {
        this.newpm = newpm;
    }
    public Byte getAccessmasks() {
        return this.accessmasks;
    }
    public void setAccessmasks(Byte accessmasks) {
        this.accessmasks = accessmasks;
    }
    public Byte getEditormode() {
        return this.editormode;
    }
    public void setEditormode(Byte editormode) {
        this.editormode = editormode;
    }
    public Byte getCustomshow() {
        return this.customshow;
    }
    public void setCustomshow(Byte customshow) {
        this.customshow = customshow;
    }
	public String getGrouptitle() {
		return grouptitle;
	}
	public void setGrouptitle(String grouptitle) {
		this.grouptitle = grouptitle;
		if(adminname==null){
			adminname=grouptitle;
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getRadminid() {
		return radminid;
	}
	public void setRadminid(int radminid) {
		this.radminid = radminid;
		if(radminid != 0){
			adminname = grouptitle;
		}else{
			adminname = "";
		}
	}
	public String getAdminname() {
		return adminname;
	}
	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
}