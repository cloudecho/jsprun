package cn.jsprun.foreg.vo.archiver;
public class Multi_inc {
	private Integer pageStart = null;	
	private Integer pageEnd = null;	
	private String link = null;	
	private Integer nowPage = null;	
	private Integer pages = null; 
	private Integer showPageNum = null; 
	private Integer total = null; 
	private Integer perpage = null; 
	public Multi_inc(Integer nowPage,Integer showPageNum,
			Integer total,Integer perpage,String link){
		this.nowPage = nowPage;
		this.showPageNum = showPageNum;
		this.total = total;
		this.perpage = perpage;
		this.link = link;
		init();
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Integer getNowPage() {
		return nowPage;
	}
	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}
	public Integer getPageStart() {
		return pageStart;
	}
	public Integer getPageEnd() {
		return pageEnd;
	}
	public void setPerpage(Integer perpage) {
		this.perpage = perpage;
	}
	public void setShowPageNum(Integer showPageNum) {
		this.showPageNum = showPageNum;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public boolean getMulti() {
		return pages > 1;
	}
	private void init(){
		pages = (int)(Math.ceil((double)total / perpage));
		pageStart = nowPage - showPageNum < 1 ? 1 : nowPage - showPageNum;
		pageEnd = nowPage + showPageNum >= pages ? pages : nowPage + showPageNum -1;
	}
}
