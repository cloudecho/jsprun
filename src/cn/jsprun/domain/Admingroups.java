package cn.jsprun.domain;
public class Admingroups  implements java.io.Serializable {
	private static final long serialVersionUID = 980086845423154264L;
	private Short admingid;			
     private Byte alloweditpost;		
     private Byte alloweditpoll;		
     private Byte allowstickthread;		
     private Byte allowmodpost;			
     private Byte allowdelpost;			
     private Byte allowmassprune;		
     private Byte allowrefund;			
     private Byte allowcensorword;		
     private Byte allowviewip;			
     private Byte allowbanip;			
     private Byte allowedituser;		
     private Byte allowmoduser;			
     private Byte allowbanuser;			
     private Byte allowpostannounce;	
     private Byte allowviewlog;			
     private Byte allowbanpost;			
     private Byte disablepostctrl;		
    public Admingroups() {
    }
    public Admingroups(Short admingid, Byte alloweditpost, Byte alloweditpoll, Byte allowstickthread, Byte allowmodpost, Byte allowdelpost, Byte allowmassprune, Byte allowrefund, Byte allowcensorword, Byte allowviewip, Byte allowbanip, Byte allowedituser, Byte allowmoduser, Byte allowbanuser, Byte allowpostannounce, Byte allowviewlog, Byte allowbanpost, Byte disablepostctrl) {
        this.admingid = admingid;
        this.alloweditpost = alloweditpost;
        this.alloweditpoll = alloweditpoll;
        this.allowstickthread = allowstickthread;
        this.allowmodpost = allowmodpost;
        this.allowdelpost = allowdelpost;
        this.allowmassprune = allowmassprune;
        this.allowrefund = allowrefund;
        this.allowcensorword = allowcensorword;
        this.allowviewip = allowviewip;
        this.allowbanip = allowbanip;
        this.allowedituser = allowedituser;
        this.allowmoduser = allowmoduser;
        this.allowbanuser = allowbanuser;
        this.allowpostannounce = allowpostannounce;
        this.allowviewlog = allowviewlog;
        this.allowbanpost = allowbanpost;
        this.disablepostctrl = disablepostctrl;
    }
    public Short getAdmingid() {
        return this.admingid;
    }
    public void setAdmingid(Short admingid) {
        this.admingid = admingid;
    }
    public Byte getAlloweditpost() {
        return this.alloweditpost;
    }
    public void setAlloweditpost(Byte alloweditpost) {
        this.alloweditpost = alloweditpost;
    }
    public Byte getAlloweditpoll() {
        return this.alloweditpoll;
    }
    public void setAlloweditpoll(Byte alloweditpoll) {
        this.alloweditpoll = alloweditpoll;
    }
    public Byte getAllowstickthread() {
        return this.allowstickthread;
    }
    public void setAllowstickthread(Byte allowstickthread) {
        this.allowstickthread = allowstickthread;
    }
    public Byte getAllowmodpost() {
        return this.allowmodpost;
    }
    public void setAllowmodpost(Byte allowmodpost) {
        this.allowmodpost = allowmodpost;
    }
    public Byte getAllowdelpost() {
        return this.allowdelpost;
    }
    public void setAllowdelpost(Byte allowdelpost) {
        this.allowdelpost = allowdelpost;
    }
    public Byte getAllowmassprune() {
        return this.allowmassprune;
    }
    public void setAllowmassprune(Byte allowmassprune) {
        this.allowmassprune = allowmassprune;
    }
    public Byte getAllowrefund() {
        return this.allowrefund;
    }
    public void setAllowrefund(Byte allowrefund) {
        this.allowrefund = allowrefund;
    }
    public Byte getAllowcensorword() {
        return this.allowcensorword;
    }
    public void setAllowcensorword(Byte allowcensorword) {
        this.allowcensorword = allowcensorword;
    }
    public Byte getAllowviewip() {
        return this.allowviewip;
    }
    public void setAllowviewip(Byte allowviewip) {
        this.allowviewip = allowviewip;
    }
    public Byte getAllowbanip() {
        return this.allowbanip;
    }
    public void setAllowbanip(Byte allowbanip) {
        this.allowbanip = allowbanip;
    }
    public Byte getAllowedituser() {
        return this.allowedituser;
    }
    public void setAllowedituser(Byte allowedituser) {
        this.allowedituser = allowedituser;
    }
    public Byte getAllowmoduser() {
        return this.allowmoduser;
    }
    public void setAllowmoduser(Byte allowmoduser) {
        this.allowmoduser = allowmoduser;
    }
    public Byte getAllowbanuser() {
        return this.allowbanuser;
    }
    public void setAllowbanuser(Byte allowbanuser) {
        this.allowbanuser = allowbanuser;
    }
    public Byte getAllowpostannounce() {
        return this.allowpostannounce;
    }
    public void setAllowpostannounce(Byte allowpostannounce) {
        this.allowpostannounce = allowpostannounce;
    }
    public Byte getAllowviewlog() {
        return this.allowviewlog;
    }
    public void setAllowviewlog(Byte allowviewlog) {
        this.allowviewlog = allowviewlog;
    }
    public Byte getAllowbanpost() {
        return this.allowbanpost;
    }
    public void setAllowbanpost(Byte allowbanpost) {
        this.allowbanpost = allowbanpost;
    }
    public Byte getDisablepostctrl() {
        return this.disablepostctrl;
    }
    public void setDisablepostctrl(Byte disablepostctrl) {
        this.disablepostctrl = disablepostctrl;
    }
}