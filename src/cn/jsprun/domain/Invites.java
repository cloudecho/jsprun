package cn.jsprun.domain;
public class Invites  implements java.io.Serializable {
	private static final long serialVersionUID = -1247323976159709842L;
	private InvitesId id;	
    public Invites() {
    }
    public Invites(InvitesId id) {
        this.id = id;
    }
    public InvitesId getId() {
        return this.id;
    }
    public void setId(InvitesId id) {
        this.id = id;
    }
}