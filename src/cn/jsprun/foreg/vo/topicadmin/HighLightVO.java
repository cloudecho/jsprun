package cn.jsprun.foreg.vo.topicadmin;
public class HighLightVO extends BaseVO {
	private boolean bchecked = false;
	private boolean ichecked = false;
	private boolean uchecked = false;
	private int highlight_color = 0;
	public boolean getBchecked() {
		return bchecked;
	}
	public void setBchecked(boolean bchecked) {
		this.bchecked = bchecked;
	}
	public boolean getIchecked() {
		return ichecked;
	}
	public void setIchecked(boolean ichecked) {
		this.ichecked = ichecked;
	}
	public boolean getUchecked() {
		return uchecked;
	}
	public void setUchecked(boolean uchecked) {
		this.uchecked = uchecked;
	}
	public int getHighlight_color() {
		return highlight_color;
	}
	public void setHighlight_color(int highlight_color) {
		this.highlight_color = highlight_color;
	}
}
