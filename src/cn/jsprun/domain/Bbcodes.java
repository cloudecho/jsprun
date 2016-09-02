package cn.jsprun.domain;
public class Bbcodes  implements java.io.Serializable {
	private static final long serialVersionUID = 660047037973993810L;
	private Integer id;			
     private Byte available;		
     private String tag;			
     private String icon;			
     private String replacement;	
     private String example;		
     private String explanation;	
     private Byte params;			
     private String prompt;			
     private Short nest;			
    public Bbcodes() {
    }
    public Bbcodes(Byte available, String tag, String icon, String replacement, String example, String explanation, Byte params, String prompt, Short nest) {
        this.available = available;
        this.tag = tag;
        this.icon = icon;
        this.replacement = replacement;
        this.example = example;
        this.explanation = explanation;
        this.params = params;
        this.prompt = prompt;
        this.nest = nest;
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Byte getAvailable() {
        return this.available;
    }
    public void setAvailable(Byte available) {
        this.available = available;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getReplacement() {
        return this.replacement;
    }
    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }
    public String getExample() {
        return this.example;
    }
    public void setExample(String example) {
        this.example = example;
    }
    public String getExplanation() {
        return this.explanation;
    }
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    public Byte getParams() {
        return this.params;
    }
    public void setParams(Byte params) {
        this.params = params;
    }
    public String getPrompt() {
        return this.prompt;
    }
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    public Short getNest() {
        return this.nest;
    }
    public void setNest(Short nest) {
        this.nest = nest;
    }
}