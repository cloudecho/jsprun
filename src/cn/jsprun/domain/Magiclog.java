package cn.jsprun.domain;
public class Magiclog  implements java.io.Serializable {
	private static final long serialVersionUID = 2776332626452504252L;
	private MagiclogId id;		
    public Magiclog() {
    }
    public Magiclog(MagiclogId id) {
        this.id = id;
    }
    public MagiclogId getId() {
        return this.id;
    }
    public void setId(MagiclogId id) {
        this.id = id;
    }
}