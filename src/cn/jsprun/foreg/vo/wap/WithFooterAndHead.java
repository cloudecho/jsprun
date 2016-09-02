package cn.jsprun.foreg.vo.wap;
public class WithFooterAndHead {
	private HeaderVO headerVO = null;
	private FooterVO footerVO = null;
	public HeaderVO getHeaderVO() {
		if(headerVO == null){
			headerVO = new HeaderVO();
		}
		return headerVO;
	}
	public void setHeaderVO(HeaderVO headerVO) {
		this.headerVO = headerVO;
	}
	public FooterVO getFooterVO() {
		if(footerVO == null){
			footerVO = new FooterVO();
		}
		return footerVO;
	}
	public void setFooterVO(FooterVO footerVO) {
		this.footerVO = footerVO;
	}
}
