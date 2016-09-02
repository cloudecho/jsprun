package cn.jsprun.domain;
public class Tags  implements java.io.Serializable {
	private static final long serialVersionUID = -7513742516652257485L;
     private String tagname;
     private Byte closed;
     private Integer total;
    public Tags() {
    }
    public Tags(String tagname, Byte closed, Integer total) {
        this.tagname = tagname;
        this.closed = closed;
        this.total = total;
    }
    public String getTagname() {
        return this.tagname;
    }
    public void setTagname(String tagname) {
        this.tagname = tagname;
    }
    public Byte getClosed() {
        return this.closed;
    }
    public void setClosed(Byte closed) {
        this.closed = closed;
    }
    public Integer getTotal() {
        return this.total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
}