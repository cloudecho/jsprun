package cn.jsprun.domain;
public class Pmsearchindex  implements java.io.Serializable {
	private static final long serialVersionUID = 2502549613263782157L;
     private Integer searchid;
     private String keywords;
     private String searchstring;
     private Integer uid;
     private Integer dateline;
     private Integer expiration;
     private Short pms;
     private String pmids;
    public Pmsearchindex() {
    }
    public Pmsearchindex(String keywords, String searchstring, Integer uid, Integer dateline, Integer expiration, Short pms, String pmids) {
        this.keywords = keywords;
        this.searchstring = searchstring;
        this.uid = uid;
        this.dateline = dateline;
        this.expiration = expiration;
        this.pms = pms;
        this.pmids = pmids;
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
    public Short getPms() {
        return this.pms;
    }
    public void setPms(Short pms) {
        this.pms = pms;
    }
    public String getPmids() {
        return this.pmids;
    }
    public void setPmids(String pmids) {
        this.pmids = pmids;
    }
}