package cn.jsprun.domain;
public class Ratelog  implements java.io.Serializable {
	private static final long serialVersionUID = -7071573373356745859L;
     private RatelogId id;
    public Ratelog() {
    }
    public Ratelog(RatelogId id) {
        this.id = id;
    }
    public RatelogId getId() {
        return this.id;
    }
    public void setId(RatelogId id) {
        this.id = id;
    }
}