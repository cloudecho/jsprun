package cn.jsprun.taglib;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
public class ShowFileSize extends TagSupport {
	private static final long serialVersionUID = -8940560586441587185L;
	String size;
	public void setSize(String size){
		this.size = size;
	}
	@Override
	public int doStartTag() throws JspException {
		long fileSize = Long.parseLong(size);
		try {
			String out=null;
			if(fileSize>=1073741824){
				out = ((double) Math.round(fileSize / 1073741824d * 100) / 100)+ " GB";
			}else if(fileSize>=1048576){
				out = ((double) Math.round(fileSize / 1048576d * 100) / 100)+ " MB";
			}else if(fileSize>=1024){
				out = ((double) Math.round(fileSize / 1024d * 100) / 100)+ " KB";
			}else{
				out = fileSize+ " Bytes";
			}
			pageContext.getOut().write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
}