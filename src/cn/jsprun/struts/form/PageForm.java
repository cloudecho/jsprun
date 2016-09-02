package cn.jsprun.struts.form;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.utils.HibernateUtil;
public class PageForm implements java.io.Serializable{
	private static final long serialVersionUID = 7856051824324464936L;
	public static final String TOTALPAGE = "totalpage";
	public static final String TOTALSIZE = "totalsize";
	public static final String CURRENTPAGE = "currentpage";
	public static final String LIST = "showlist";
	private int currentPage = 1;
	private int prePage = 0;
	private int nextPage = 0;
	private List list;
	private int pageSize = 5;
	private int totalPage = 0;
	private int totalSize = 0;
	private String countSQL = "select count(*) from Posts";
	private String sql = "from Posts";
	public PageForm() {
	}
	public PageForm(int currentPage, String countSQL, String sql) {
		this.currentPage = currentPage;
		this.countSQL = countSQL;
		this.sql = sql;
	}
	public PageForm(int currentPage, int pageSize, String countSQL, String sql) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.countSQL = countSQL;
		this.sql = sql;
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
		getTotalPage();
		return this.currentPage == totalPage ? currentPage : currentPage + 1;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
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
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public int getTotalPage() {
		getTotalSize();
		if (totalSize > pageSize) {
			if (totalSize % pageSize == 0) {
				setTotalPage((int) ((double) totalSize / (double) pageSize));
			} else {
				setTotalPage((int) (1.0d + (double) totalSize
						/ (double) pageSize));
			}
		} else {
			totalSize = 1;
			setTotalPage(1);
		}
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalSize() {
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Object o = (Object) session.createQuery(countSQL).uniqueResult();
			totalSize = ((Integer) o).intValue();
			tr.commit();
		} catch (Exception e) {
			if(tr!=null && tr.isActive()){
				tr.rollback();
			}
			e.printStackTrace();
		} 
		setTotalSize(totalSize);
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public PageForm getPageRecord() {
		int startid = 0;
		if (this.currentPage < 0) {
			startid = 0;
		} else {
			if (currentPage > getTotalPage()) {
				startid = getTotalPage();
				startid = pageSize * (startid - 1);
			} else {
				startid = pageSize * (currentPage - 1);
			}
		}
		setCurrentPage(currentPage);
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setFetchSize(pageSize);
			query.setFirstResult(startid);
			query.setMaxResults(pageSize);
			setList(query.list());
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return this;
	}
	public String getCountSQL() {
		return countSQL;
	}
	public void setCountSQL(String countSQL) {
		this.countSQL = countSQL;
	}
}
