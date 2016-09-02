package cn.jsprun.vo.logs;
public class CreditslogVO implements java.io.Serializable{
	private static final long serialVersionUID = 8267668734345822702L;
	private String username;
	private String fromname;
	private String opertarDate;
	private String sendCrites;
	private String receiveCrites;
	private String sendunit;
	private String receiveuint;
	private int sendNum;
	private int receiverNum;
	private int sendCritesNum;     
	private int receiveCritesNum;  
	private String opertar;
	@SuppressWarnings("unchecked")
	public String getFromname() {
		return fromname;
	}
	public void setFromname(String fromname) {
		this.fromname = fromname;
	}
	public String getOpertar() {
		return opertar;
	}
	public void setOpertar(String opertar) {
		this.opertar = opertar;
	}
	public String getOpertarDate() {
		return opertarDate;
	}
	public void setOpertarDate(String opertarDate) {
		this.opertarDate = opertarDate;
	}
	public String getReceiveCrites() {
		return receiveCrites;
	}
	public void setReceiveCrites(String receiveCrites) {
		this.receiveCrites = receiveCrites;
	}
	public int getReceiverNum() {
		return receiverNum;
	}
	public void setReceiverNum(int receiverNum) {
		this.receiverNum = receiverNum;
	}
	public String getSendCrites() {
		return sendCrites;
	}
	public void setSendCrites(String sendCrites) {
		this.sendCrites = sendCrites;
	}
	public int getSendNum() {
		return sendNum;
	}
	public void setSendNum(int sendNum) {
		this.sendNum = sendNum;
	}
	public String getReceiveuint() {
		return receiveuint;
	}
	public void setReceiveuint(String receiveuint) {
		this.receiveuint = receiveuint;
	}
	public String getSendunit() {
		return sendunit;
	}
	public void setSendunit(String sendunit) {
		this.sendunit = sendunit;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getReceiveCritesNum() {
		return receiveCritesNum;
	}
	public void setReceiveCritesNum(int receiveCritesNum) {
		this.receiveCritesNum = receiveCritesNum;
	}
	public int getSendCritesNum() {
		return sendCritesNum;
	}
	public void setSendCritesNum(int sendCritesNum) {
		this.sendCritesNum = sendCritesNum;
	}
}
