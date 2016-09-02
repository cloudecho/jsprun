package cn.jsprun.domain;
public class Attachtypes  implements java.io.Serializable {
	private static final long serialVersionUID = -5656072916086726648L;
	private Short id;				
     private String extension;		
     private Integer maxsize;		
    public Attachtypes() {
    }
    public Attachtypes(String extension, Integer maxsize) {
        this.extension = extension;
        this.maxsize = maxsize;
    }
    public Short getId() {
        return this.id;
    }
    public void setId(Short id) {
        this.id = id;
    }
    public String getExtension() {
        return this.extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public Integer getMaxsize() {
        return this.maxsize;
    }
    public void setMaxsize(Integer maxsize) {
        this.maxsize = maxsize;
    }
}