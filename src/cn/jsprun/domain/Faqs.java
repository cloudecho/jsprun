package cn.jsprun.domain;
public class Faqs  implements java.io.Serializable {
	private static final long serialVersionUID = -8468598015125256340L;
	private Short id;				
     private Short fpid;			
     private Short displayorder;	
     private String identifier;		
     private String keyword;		
     private String title;			
     private String message;		
    public Faqs() {
    }
    public Faqs(Short fpid, Short displayorder, String identifier, String keyword, String title, String message) {
        this.fpid = fpid;
        this.displayorder = displayorder;
        this.identifier = identifier;
        this.keyword = keyword;
        this.title = title;
        this.message = message;
    }
    public Short getId() {
        return this.id;
    }
    public void setId(Short id) {
        this.id = id;
    }
    public Short getFpid() {
        return this.fpid;
    }
    public void setFpid(Short fpid) {
        this.fpid = fpid;
    }
    public Short getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Short displayorder) {
        this.displayorder = displayorder;
    }
    public String getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public String getKeyword() {
        return this.keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}