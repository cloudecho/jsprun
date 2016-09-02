package cn.jsprun.utils;
import java.util.ArrayList;
import java.util.List;
public class LogPage {
	private int currentPage = 1;
	private List list = new ArrayList();
	private int pageSize = 5;
	private int totalPage = 0;
	private int totalSize = 0;
	private String staticurl="false";
	public LogPage() {
		countTotalPage();
	}
	public LogPage(List list,int pagesize,int currentpage){
		setList(list);
		this.setPageSize(pagesize);
		this.setTotalSize(list.size());
		countTotalPage();
		this.setCurrentPage(currentpage);
	}
	public LogPage(int size,int pagesize,int currentpage){
		this.setPageSize(pagesize);
		this.setTotalSize(size);
		countTotalPage();
		this.setCurrentPage(currentpage);
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		if (currentPage < 1) {
			currentPage = 1;
		}
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
		this.currentPage = currentPage;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getNextPage() {
		return this.currentPage == totalPage ? currentPage : currentPage + 1;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPrePage() {
		return currentPage - 1 == 0 ? 1 : currentPage - 1;
	}
	public int countTotalPage() {
		if (totalSize > pageSize) {
			if (totalSize % pageSize == 0) {
				setTotalPage((int) ((double) totalSize / (double) pageSize));
			} else {
				setTotalPage((int) (1.0d + (double) totalSize / (double) pageSize));
			}
		} else {
			setTotalPage(1);
		}
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public String getStaticurl() {
		return staticurl;
	}
	public void setStaticurl(String staticurl) {
		this.staticurl = staticurl;
	}
	public int getTotalPage() {
		return totalPage;
	}
}
