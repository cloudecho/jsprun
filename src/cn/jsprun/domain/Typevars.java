package cn.jsprun.domain;
public class Typevars  implements java.io.Serializable {
	private static final long serialVersionUID = -4731544458915629342L;
     private TypevarsId id;
    public Typevars() {
    }
    public Typevars(TypevarsId id) {
        this.id = id;
    }
    public TypevarsId getId() {
        return this.id;
    }
    public void setId(TypevarsId id) {
        this.id = id;
    }
}