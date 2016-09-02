package cn.jsprun.vo.logs;
public class MagiclogVO implements java.io.Serializable {
	private static final long serialVersionUID = -2957721844575397386L;
	private String username;
	private String magicname;
	private String datetime;
	private short amount;
	private int price;
	private String opertar;
	public short getAmount() {
		return amount;
	}
	public void setAmount(short amount) {
		this.amount = amount;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getMagicname() {
		return magicname;
	}
	public void setMagicname(String magicname) {
		this.magicname = magicname;
	}
	public String getOpertar() {
		return opertar;
	}
	public void setOpertar(String opertar) {
		this.opertar = opertar;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
