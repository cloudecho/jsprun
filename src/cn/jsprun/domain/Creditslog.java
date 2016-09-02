package cn.jsprun.domain;
public class Creditslog  implements java.io.Serializable {
	private static final long serialVersionUID = -4665858438170439436L;
	private CreditslogId id;		
    public Creditslog() {
    }
    public Creditslog(CreditslogId id) {
        this.id = id;
    }
    public CreditslogId getId() {
        return this.id;
    }
    public void setId(CreditslogId id) {
        this.id = id;
    }
}