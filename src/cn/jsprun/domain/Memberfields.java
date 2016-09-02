package cn.jsprun.domain;
public class Memberfields  implements java.io.Serializable {
	private static final long serialVersionUID = 8809285866121959553L;
	private Integer uid;			
     private String nickname;		
     private String site;			
     private String alipay;			
     private String icq;			
     private String qq;				
     private String yahoo;			
     private String msn;			
     private String taobao;			
     private String location;		
     private String customstatus;	
     private String medals;			
     private String avatar;			
     private Short avatarwidth;		
     private Short avatarheight;	
     private String bio;			
     private String sightml;		
     private String ignorepm;		
     private String groupterms;		
     private String authstr;		
     private String spacename;		
     private Short buyercredit;		
     private Short sellercredit;	
    public Memberfields() {
    }
    public Memberfields(Integer uid, String nickname, String site, String alipay, String icq, String qq, String yahoo, String msn, String taobao, String location, String customstatus, String medals, String avatar, Short avatarwidth, Short avatarheight, String bio, String sightml, String ignorepm, String groupterms, String authstr, String spacename, Short buyercredit, Short sellercredit) {
        this.uid = uid;
        this.nickname = nickname;
        this.site = site;
        this.alipay = alipay;
        this.icq = icq;
        this.qq = qq;
        this.yahoo = yahoo;
        this.msn = msn;
        this.taobao = taobao;
        this.location = location;
        this.customstatus = customstatus;
        this.medals = medals;
        this.avatar = avatar;
        this.avatarwidth = avatarwidth;
        this.avatarheight = avatarheight;
        this.bio = bio;
        this.sightml = sightml;
        this.ignorepm = ignorepm;
        this.groupterms = groupterms;
        this.authstr = authstr;
        this.spacename = spacename;
        this.buyercredit = buyercredit;
        this.sellercredit = sellercredit;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getSite() {
        return this.site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public String getAlipay() {
        return this.alipay;
    }
    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }
    public String getIcq() {
        return this.icq;
    }
    public void setIcq(String icq) {
        this.icq = icq;
    }
    public String getQq() {
        return this.qq;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }
    public String getYahoo() {
        return this.yahoo;
    }
    public void setYahoo(String yahoo) {
        this.yahoo = yahoo;
    }
    public String getMsn() {
        return this.msn;
    }
    public void setMsn(String msn) {
        this.msn = msn;
    }
    public String getTaobao() {
        return this.taobao;
    }
    public void setTaobao(String taobao) {
        this.taobao = taobao;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getCustomstatus() {
        return this.customstatus;
    }
    public void setCustomstatus(String customstatus) {
        this.customstatus = customstatus;
    }
    public String getMedals() {
        return this.medals;
    }
    public void setMedals(String medals) {
        this.medals = medals;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public Short getAvatarwidth() {
        return this.avatarwidth;
    }
    public void setAvatarwidth(Short avatarwidth) {
        this.avatarwidth = avatarwidth;
    }
    public Short getAvatarheight() {
        return this.avatarheight;
    }
    public void setAvatarheight(Short avatarheight) {
        this.avatarheight = avatarheight;
    }
    public String getBio() {
        return this.bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getSightml() {
        return this.sightml;
    }
    public void setSightml(String sightml) {
        this.sightml = sightml;
    }
    public String getIgnorepm() {
        return this.ignorepm;
    }
    public void setIgnorepm(String ignorepm) {
        this.ignorepm = ignorepm;
    }
    public String getGroupterms() {
        return this.groupterms;
    }
    public void setGroupterms(String groupterms) {
        this.groupterms = groupterms;
    }
    public String getAuthstr() {
        return this.authstr;
    }
    public void setAuthstr(String authstr) {
        this.authstr = authstr;
    }
    public String getSpacename() {
        return this.spacename;
    }
    public void setSpacename(String spacename) {
        this.spacename = spacename;
    }
    public Short getBuyercredit() {
        return this.buyercredit;
    }
    public void setBuyercredit(Short buyercredit) {
        this.buyercredit = buyercredit;
    }
    public Short getSellercredit() {
        return this.sellercredit;
    }
    public void setSellercredit(Short sellercredit) {
        this.sellercredit = sellercredit;
    }
}