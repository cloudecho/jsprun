package cn.jsprun.domain;
public class Templates  implements java.io.Serializable {
	private static final long serialVersionUID = -1774377029295403250L;
     private Short templateid;
     private String name;
     private String directory;
     private String copyright;
    public Templates() {
    }
    public Templates(String name, String directory, String copyright) {
        this.name = name;
        this.directory = directory;
        this.copyright = copyright;
    }
    public Short getTemplateid() {
        return this.templateid;
    }
    public void setTemplateid(Short templateid) {
        this.templateid = templateid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDirectory() {
        return this.directory;
    }
    public void setDirectory(String directory) {
        this.directory = directory;
    }
    public String getCopyright() {
        return this.copyright;
    }
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}