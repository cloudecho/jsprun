package cn.jsprun.domain;
public class Access  implements java.io.Serializable {
	private static final long serialVersionUID = 8883093033243273859L;
	private AccessId id;			
     private Byte allowview;		
     private Byte allowpost;		
     private Byte allowreply;		
     private Byte allowgetattach;	
     private Byte allowpostattach;	
    public Access() {
    }
    public Access(AccessId id, Byte allowview, Byte allowpost, Byte allowreply, Byte allowgetattach, Byte allowpostattach) {
        this.id = id;
        this.allowview = allowview;
        this.allowpost = allowpost;
        this.allowreply = allowreply;
        this.allowgetattach = allowgetattach;
        this.allowpostattach = allowpostattach;
    }
    public AccessId getId() {
        return this.id;
    }
    public void setId(AccessId id) {
        this.id = id;
    }
    public Byte getAllowview() {
        return this.allowview;
    }
    public void setAllowview(Byte allowview) {
        this.allowview = allowview;
    }
    public Byte getAllowpost() {
        return this.allowpost;
    }
    public void setAllowpost(Byte allowpost) {
        this.allowpost = allowpost;
    }
    public Byte getAllowreply() {
        return this.allowreply;
    }
    public void setAllowreply(Byte allowreply) {
        this.allowreply = allowreply;
    }
    public Byte getAllowgetattach() {
        return this.allowgetattach;
    }
    public void setAllowgetattach(Byte allowgetattach) {
        this.allowgetattach = allowgetattach;
    }
    public Byte getAllowpostattach() {
        return this.allowpostattach;
    }
    public void setAllowpostattach(Byte allowpostattach) {
        this.allowpostattach = allowpostattach;
    }
}