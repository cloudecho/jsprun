package cn.jsprun.taglib;
import java.io.IOException;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
public class ShowRank extends TagSupport {
	private static final long serialVersionUID = -8940560586441587184L;
	int num;
	Map<Object,Object> ranks=null;
	public void setNum(int num){
		this.num = num;
	}
	public void setRanks(Map<Object,Object> ranks){
		this.ranks = ranks;
	}
	@SuppressWarnings("deprecation")
	@Override
	public int doStartTag() throws JspException {
		JspWriter out=pageContext.getOut();
		try {
			Object rank=ranks.get(num+1);
			out.write(rank!=null?rank+"":"");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
}
