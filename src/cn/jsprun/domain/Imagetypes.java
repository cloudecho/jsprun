package cn.jsprun.domain;
public class Imagetypes  implements java.io.Serializable {
	private static final long serialVersionUID = -3032356134365954854L;
	private Short typeid;			
     private String name;			
     private String type;			
     private Short displayorder;	
     private String directory;		
    public Imagetypes() {
    }
    public Imagetypes(String name, String type, Short displayorder, String directory) {
        this.name = name;
        this.type = type;
        this.displayorder = displayorder;
        this.directory = directory;
    }
    public Short getTypeid() {
        return this.typeid;
    }
    public void setTypeid(Short typeid) {
        this.typeid = typeid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Short getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Short displayorder) {
        this.displayorder = displayorder;
    }
    public String getDirectory() {
        return this.directory;
    }
    public void setDirectory(String directory) {
        this.directory = directory;
    }
}