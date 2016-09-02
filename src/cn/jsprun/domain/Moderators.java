package cn.jsprun.domain;
public class Moderators  implements java.io.Serializable {
	private static final long serialVersionUID = -2284733502485849876L;
	private ModeratorsId id;		
     private Short displayorder;	
     private Byte inherited;		
    public Moderators() {
    }
    public Moderators(ModeratorsId id, Short displayorder, Byte inherited) {
        this.id = id;
        this.displayorder = displayorder;
        this.inherited = inherited;
    }
    public ModeratorsId getId() {
        return this.id;
    }
    public void setId(ModeratorsId id) {
        this.id = id;
    }
    public Short getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Short displayorder) {
        this.displayorder = displayorder;
    }
    public Byte getInherited() {
        return this.inherited;
    }
    public void setInherited(Byte inherited) {
        this.inherited = inherited;
    }
}