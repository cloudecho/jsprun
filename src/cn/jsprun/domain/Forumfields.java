package cn.jsprun.domain;
public class Forumfields  implements java.io.Serializable {
	private static final long serialVersionUID = 2738296229441187198L;
     private Short fid;					
     private String description;		
     private String password;			
     private String icon;				
     private String postcredits;		
     private String replycredits;		
     private String getattachcredits;	
     private String postattachcredits;	
     private String digestcredits;		
     private String redirect;			
     private String attachextensions;	
     private String formulaperm;		
     private String moderators;			
     private String rules;				
     private String threadtypes;		
     private String viewperm;			
     private String postperm;			
     private String replyperm;			
     private String getattachperm;		
     private String postattachperm;		
     private String keywords;			
     private String modrecommend;		
     private String tradetypes;			
     private String typemodels;			
    public Forumfields() {
    }
    public Forumfields(Short fid, String description, String password, String icon, String postcredits, String replycredits, String getattachcredits, String postattachcredits, String digestcredits, String redirect, String attachextensions, String formulaperm, String moderators, String rules, String threadtypes, String viewperm, String postperm, String replyperm, String getattachperm, String postattachperm, String keywords, String modrecommend, String tradetypes, String typemodels) {
        this.fid = fid;
        this.description = description;
        this.password = password;
        this.icon = icon;
        this.postcredits = postcredits;
        this.replycredits = replycredits;
        this.getattachcredits = getattachcredits;
        this.postattachcredits = postattachcredits;
        this.digestcredits = digestcredits;
        this.redirect = redirect;
        this.attachextensions = attachextensions;
        this.formulaperm = formulaperm;
        this.moderators = moderators;
        this.rules = rules;
        this.threadtypes = threadtypes;
        this.viewperm = viewperm;
        this.postperm = postperm;
        this.replyperm = replyperm;
        this.getattachperm = getattachperm;
        this.postattachperm = postattachperm;
        this.keywords = keywords;
        this.modrecommend = modrecommend;
        this.tradetypes = tradetypes;
        this.typemodels = typemodels;
    }
    public Short getFid() {
        return this.fid;
    }
    public void setFid(Short fid) {
        this.fid = fid;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getPostcredits() {
        return this.postcredits;
    }
    public void setPostcredits(String postcredits) {
        this.postcredits = postcredits;
    }
    public String getReplycredits() {
        return this.replycredits;
    }
    public void setReplycredits(String replycredits) {
        this.replycredits = replycredits;
    }
    public String getGetattachcredits() {
        return this.getattachcredits;
    }
    public void setGetattachcredits(String getattachcredits) {
        this.getattachcredits = getattachcredits;
    }
    public String getPostattachcredits() {
        return this.postattachcredits;
    }
    public void setPostattachcredits(String postattachcredits) {
        this.postattachcredits = postattachcredits;
    }
    public String getDigestcredits() {
        return this.digestcredits;
    }
    public void setDigestcredits(String digestcredits) {
        this.digestcredits = digestcredits;
    }
    public String getRedirect() {
        return this.redirect;
    }
    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
    public String getAttachextensions() {
        return this.attachextensions;
    }
    public void setAttachextensions(String attachextensions) {
        this.attachextensions = attachextensions;
    }
    public String getFormulaperm() {
        return this.formulaperm;
    }
    public void setFormulaperm(String formulaperm) {
        this.formulaperm = formulaperm;
    }
    public String getModerators() {
        return this.moderators;
    }
    public void setModerators(String moderators) {
        this.moderators = moderators;
    }
    public String getRules() {
        return this.rules;
    }
    public void setRules(String rules) {
        this.rules = rules;
    }
    public String getThreadtypes() {
        return this.threadtypes;
    }
    public void setThreadtypes(String threadtypes) {
        this.threadtypes = threadtypes;
    }
    public String getViewperm() {
        return this.viewperm;
    }
    public void setViewperm(String viewperm) {
        this.viewperm = viewperm;
    }
    public String getPostperm() {
        return this.postperm;
    }
    public void setPostperm(String postperm) {
        this.postperm = postperm;
    }
    public String getReplyperm() {
        return this.replyperm;
    }
    public void setReplyperm(String replyperm) {
        this.replyperm = replyperm;
    }
    public String getGetattachperm() {
        return this.getattachperm;
    }
    public void setGetattachperm(String getattachperm) {
        this.getattachperm = getattachperm;
    }
    public String getPostattachperm() {
        return this.postattachperm;
    }
    public void setPostattachperm(String postattachperm) {
        this.postattachperm = postattachperm;
    }
    public String getKeywords() {
        return this.keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getModrecommend() {
        return this.modrecommend;
    }
    public void setModrecommend(String modrecommend) {
        this.modrecommend = modrecommend;
    }
    public String getTradetypes() {
        return this.tradetypes;
    }
    public void setTradetypes(String tradetypes) {
        this.tradetypes = tradetypes;
    }
    public String getTypemodels() {
        return this.typemodels;
    }
    public void setTypemodels(String typemodels) {
        this.typemodels = typemodels;
    }
}