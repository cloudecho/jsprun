package cn.jsprun.taglib;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
public class ShowStars extends TagSupport {
	private static final long serialVersionUID = -8940560586441587184L;
	int num;
	int starthreshold;
	String imgdir;
	public void setNum(int num){
		this.num = num;
	}
	public void setStarthreshold(int starthreshold){
		this.starthreshold = starthreshold;
	}
	public void setImgdir(String imgdir){
		this.imgdir = imgdir;
	}
	@SuppressWarnings("deprecation")
	@Override
	public int doStartTag() throws JspException {
		JspWriter out=pageContext.getOut();
		try {
			String alt="alt='Rank: "+num+"'";
			if(starthreshold>0){
				for(int i = 3; i > 0; i--) {
					int numlevel = (int)(num / Math.pow(starthreshold,i - 1));
					num=(int)(num%Math.pow(starthreshold, i - 1));
					for(int j = 0; j < numlevel; j++) {
						out.write("<img src='"+imgdir+"/star_level"+i+".gif' "+alt+" />");
					}
				}
			}else{
				for(int i = 0; i < num; i++) {
					out.write("<img src='"+imgdir+"/star_level1.gif' "+alt+" />");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
}
