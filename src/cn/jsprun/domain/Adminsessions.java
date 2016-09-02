package cn.jsprun.domain;
public class Adminsessions  implements java.io.Serializable {
	private static final long serialVersionUID = -5570214968485040429L;
	private AdminsessionsId id;	
    public Adminsessions() {
    }
    public Adminsessions(AdminsessionsId id) {
        this.id = id;
    }
    public AdminsessionsId getId() {
        return this.id;
    }
    public void setId(AdminsessionsId id) {
        this.id = id;
    }
}