package cn.jsprun.taglib;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import cn.jsprun.utils.Common;
public class ShowTime extends TagSupport {
	private static final long serialVersionUID = -7195275797128947964L;
	private int timeInt;
	private String replErTime ;
	private String type = null;
	private String timeoffset;
	public void setTimeInt(int timeInt) {
		this.timeInt = timeInt;
	}
	public void setReplErTime(String replErTime) {
		this.replErTime = replErTime;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setTimeoffset(String timeoffset) {
		this.timeoffset = timeoffset;
	}
	@Override
	public int doStartTag() throws JspException {
		if(this.type==null){
			this.type = "yyyy-MM-dd";
		}
		if(timeoffset == null){
			timeoffset = (String)((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute("timeoffset");
		}
		try {
			if(timeInt>0){
				pageContext.getOut().write(Common.gmdate(type, timeInt,timeoffset));
			}else{
				if(replErTime != null){
					pageContext.getOut().write(replErTime);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return super.doStartTag();
	}
}