package cn.jsprun.foreg.vo.wap;
public class MessageVO extends WithFooterAndHead  {
	private String message = null;
	private boolean forward = false;
	private String forwardLink = null;
	private String forwardTitle = null;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean getForward() {
		return forward;
	}
	public void setForward(boolean forward) {
		this.forward = forward;
	}
	public String getForwardLink() {
		return forwardLink;
	}
	public void setForwardLink(String forwardLink) {
		this.forwardLink = forwardLink;
	}
	public String getForwardTitle() {
		return forwardTitle;
	}
	public void setForwardTitle(String forwardTitle) {
		this.forwardTitle = forwardTitle;
	}
}
