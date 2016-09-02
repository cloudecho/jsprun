package cn.jsprun.domain;
public class Words  implements java.io.Serializable {
	private static final long serialVersionUID = -1358763477020748420L;
     private Short id;
     private String admin;
     private String find;
     private String replacement;
    public Words() {
    }
    public Words(Short id, String admin, String find, String replacement) {
        this.id = id;
        this.admin = admin;
        this.find = find;
        this.replacement = replacement;
    }
    public Short getId() {
        return this.id;
    }
    public void setId(Short id) {
        this.id = id;
    }
    public String getAdmin() {
        return this.admin;
    }
    public void setAdmin(String admin) {
        this.admin = admin;
    }
    public String getFind() {
        return this.find;
    }
    public void setFind(String find) {
        this.find = find;
    }
    public String getReplacement() {
        return this.replacement;
    }
    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }
}