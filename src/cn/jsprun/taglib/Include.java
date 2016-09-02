package cn.jsprun.taglib;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import cn.jsprun.utils.JspRunConfig;
public class Include extends TagSupport {
	private static final long serialVersionUID = -6437875373651352228L;
	private String value;
	private String defvalue;
	public void setValue(String value) {
		this.value = value;
	}
	public void setDefvalue(String defvalue) {
		this.defvalue = defvalue;
	}
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		File file=null;
		try {
			file=new File(JspRunConfig.realPath+value);
			if(file.exists()){
				request.getRequestDispatcher(value).include(request, response);
			}else if(!"".equals(defvalue)){
				request.getRequestDispatcher(defvalue).include(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			file=null;
		}
		return super.doStartTag();
	}
}