package cn.jsprun.domain;
public class Modworks  implements java.io.Serializable {
	private static final long serialVersionUID = -6760185827113214720L;
	private ModworksId id;		
    public Modworks() {
    }
    public Modworks(ModworksId id) {
        this.id = id;
    }
    public ModworksId getId() {
        return this.id;
    }
    public void setId(ModworksId id) {
        this.id = id;
    }
}