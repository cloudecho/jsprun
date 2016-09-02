package cn.jsprun.domain;
public class Membermagics  implements java.io.Serializable {
	private static final long serialVersionUID = -5184405793929859824L;
	private MembermagicsId id;	
	private Short num;	
    public Membermagics() {
    }
    public Membermagics(MembermagicsId id,Short num) {
        this.id = id;
        this.num = num;
    }
    public MembermagicsId getId() {
        return this.id;
    }
    public void setId(MembermagicsId id) {
        this.id = id;
    }
	public Short getNum() {
		return num;
	}
	public void setNum(Short num) {
		this.num = num;
	}
}