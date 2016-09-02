package cn.jsprun.domain;
public class Searchindex  implements java.io.Serializable {
	private static final long serialVersionUID = 8792133713629292083L;
	 private Integer searchid;
     private String keywords;
     private String searchstring;
     private String useip;
     private Integer uid;
     private Integer dateline;
     private Integer expiration;
     private Short threadtypeid;
     private Short threads;
     private String tids;
    public Searchindex() {
    }
    public Searchindex(String keywords, String searchstring, String useip, Integer uid, Integer dateline, Integer expiration, Short threadtypeid, Short threads, String tids) {
        this.keywords = keywords;
        this.searchstring = searchstring;
        this.useip = useip;
        this.uid = uid;
        this.dateline = dateline;
        this.expiration = expiration;
        this.threadtypeid = threadtypeid;
        this.threads = threads;
        this.tids = tids;
    }
    public Integer getSearchid() {
        return this.searchid;
    }
    public void setSearchid(Integer searchid) {
        this.searchid = searchid;
    }
    public String getKeywords() {
        return this.keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getSearchstring() {
        return this.searchstring;
    }
    public void setSearchstring(String searchstring) {
        this.searchstring = searchstring;
    }
    public String getUseip() {
        return this.useip;
    }
    public void setUseip(String useip) {
        this.useip = useip;
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
    public Integer getExpiration() {
        return this.expiration;
    }
    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
    public Short getThreadtypeid() {
        return this.threadtypeid;
    }
    public void setThreadtypeid(Short threadtypeid) {
        this.threadtypeid = threadtypeid;
    }
    public Short getThreads() {
        return this.threads;
    }
    public void setThreads(Short threads) {
        this.threads = threads;
    }
    public String getTids() {
        return this.tids;
    }
    public void setTids(String tids) {
        this.tids = tids;
    }
}