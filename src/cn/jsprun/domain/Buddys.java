package cn.jsprun.domain;
public class Buddys  implements java.io.Serializable {
	private static final long serialVersionUID = 6153659592924438136L;
	private BuddysId id;	
    public Buddys() {
    }
    public Buddys(BuddysId id) {
        this.id = id;
    }
    public BuddysId getId() {
        return this.id;
    }
    public void setId(BuddysId id) {
        this.id = id;
    }
}