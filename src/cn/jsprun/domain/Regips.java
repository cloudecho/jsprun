package cn.jsprun.domain;
public class Regips  implements java.io.Serializable {
	private static final long serialVersionUID = -1487701583095272736L;
     private RegipsId id;
    public Regips() {
    }
    public Regips(RegipsId id) {
        this.id = id;
    }
    public RegipsId getId() {
        return this.id;
    }
    public void setId(RegipsId id) {
        this.id = id;
    }
}