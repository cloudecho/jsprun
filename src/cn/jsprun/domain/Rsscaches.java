package cn.jsprun.domain;
public class Rsscaches  implements java.io.Serializable {
	private static final long serialVersionUID = -6652621749680662070L;
     private RsscachesId id;
    public Rsscaches() {
    }
    public Rsscaches(RsscachesId id) {
        this.id = id;
    }
    public RsscachesId getId() {
        return this.id;
    }
    public void setId(RsscachesId id) {
        this.id = id;
    }
}