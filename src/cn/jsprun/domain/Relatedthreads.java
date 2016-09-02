package cn.jsprun.domain;
public class Relatedthreads  implements java.io.Serializable {
	private static final long serialVersionUID = -2278521573675937731L;
     private RelatedthreadsId id;
     private Integer expiration;
     private String keywords;
     private String relatedthreads;
    public Relatedthreads() {
    }
    public Relatedthreads(RelatedthreadsId id, Integer expiration, String keywords, String relatedthreads) {
        this.id = id;
        this.expiration = expiration;
        this.keywords = keywords;
        this.relatedthreads = relatedthreads;
    }
    public RelatedthreadsId getId() {
        return this.id;
    }
    public void setId(RelatedthreadsId id) {
        this.id = id;
    }
    public Integer getExpiration() {
        return this.expiration;
    }
    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
    public String getKeywords() {
        return this.keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getRelatedthreads() {
        return this.relatedthreads;
    }
    public void setRelatedthreads(String relatedthreads) {
        this.relatedthreads = relatedthreads;
    }
}