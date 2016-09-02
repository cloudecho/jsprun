package cn.jsprun.domain;
public class Stylevars  implements java.io.Serializable {
	private static final long serialVersionUID = -4103456428897253021L;
     private Short stylevarid;
     private Short styleid;
     private String variable;
     private String substitute;
    public Stylevars() {
    }
    public Stylevars(Short styleid, String variable, String substitute) {
        this.styleid = styleid;
        this.variable = variable;
        this.substitute = substitute;
    }
    public Short getStylevarid() {
        return this.stylevarid;
    }
    public void setStylevarid(Short stylevarid) {
        this.stylevarid = stylevarid;
    }
    public Short getStyleid() {
        return this.styleid;
    }
    public void setStyleid(Short styleid) {
        this.styleid = styleid;
    }
    public String getVariable() {
        return this.variable;
    }
    public void setVariable(String variable) {
        this.variable = variable;
    }
    public String getSubstitute() {
        return this.substitute;
    }
    public void setSubstitute(String substitute) {
        this.substitute = substitute;
    }
}