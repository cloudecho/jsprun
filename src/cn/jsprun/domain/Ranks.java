package cn.jsprun.domain;
public class Ranks  implements java.io.Serializable {
	private static final long serialVersionUID = 7282266028015401487L;
     private Short rankid;
     private String ranktitle;
     private Integer postshigher;
     private Short stars;
     private String color;
    public Ranks() {
    }
    public Ranks(String ranktitle, Integer postshigher, Short stars, String color) {
        this.ranktitle = ranktitle;
        this.postshigher = postshigher;
        this.stars = stars;
        this.color = color;
    }
    public Short getRankid() {
        return this.rankid;
    }
    public void setRankid(Short rankid) {
        this.rankid = rankid;
    }
    public String getRanktitle() {
        return this.ranktitle;
    }
    public void setRanktitle(String ranktitle) {
        this.ranktitle = ranktitle;
    }
    public Integer getPostshigher() {
        return this.postshigher;
    }
    public void setPostshigher(Integer postshigher) {
        this.postshigher = postshigher;
    }
    public Short getStars() {
        return this.stars;
    }
    public void setStars(Short stars) {
        this.stars = stars;
    }
    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}