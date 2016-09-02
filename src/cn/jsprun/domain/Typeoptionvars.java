package cn.jsprun.domain;
public class Typeoptionvars  implements java.io.Serializable {
	private static final long serialVersionUID = 3575212418834617811L;
     private TypeoptionvarsId id;
    public Typeoptionvars() {
    }
    public Typeoptionvars(TypeoptionvarsId id) {
        this.id = id;
    }
    public TypeoptionvarsId getId() {
        return this.id;
    }
    public void setId(TypeoptionvarsId id) {
        this.id = id;
    }
}