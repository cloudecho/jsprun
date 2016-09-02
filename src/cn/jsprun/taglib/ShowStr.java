package cn.jsprun.taglib;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
public class ShowStr extends TagSupport {
	private static final long serialVersionUID = -8940560586441587184L;
	String str;
	String len;
	public void setStr(String str){
		this.str = str;
	}
	public void setLen(String len){
		this.len = len;
	}
	@SuppressWarnings("deprecation")
	@Override
	public int doStartTag() throws JspException {
		int length = Integer.parseInt(len);
		str = str.replace("<", "&lt");
		try {
			if(str.length()<length){
				pageContext.getOut().write(str);
			}else{
				pageContext.getOut().write(str.substring(0,length-3) + "...");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
}
