package cn.jsprun.taglib;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import cn.jsprun.utils.Common;
public class URLEncoder extends TagSupport {
	private static final long serialVersionUID = -6437875373651352228L;
	private String value;
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().write(Common.encode(value));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
}
