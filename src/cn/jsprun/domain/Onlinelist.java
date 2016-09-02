package cn.jsprun.domain;
public class Onlinelist implements java.io.Serializable {
	private static final long serialVersionUID = -7171724407156058414L;
	private OnlinelistId id;
	public Onlinelist() {
	}
	public Onlinelist(OnlinelistId id) {
		this.id = id;
	}
	public OnlinelistId getId() {
		return this.id;
	}
	public void setId(OnlinelistId id) {
		this.id = id;
	}
}