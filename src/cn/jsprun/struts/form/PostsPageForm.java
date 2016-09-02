package cn.jsprun.struts.form;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.HibernateUtil;
public class PostsPageForm implements java.io.Serializable{
	private static final long serialVersionUID = -4796143374983963539L;
	private DataBaseDao databaseDao = (DataBaseDao) BeanFactory.getBean("dataBaseDao");
	private int currentPage = 1;
	private int prePage = 0;
	private int nextPage = 0;
	private List<Map<String,String>> list = null;
	private int pageSize = 10;
	private int totalPage = 0;
	private int totalSize = 0;
	private String countSQL = "select count(*) count from jrun_threads as t where t.displayorder=-2 ";
	private String sql = "select t.tid, t.fid, t.author, t.authorid, t.subject, t.dateline,p.pid, p.message, p.useip, p.attachment from jrun_threads t LEFT JOIN jrun_posts p ON p.tid=t.tid WHERE t.displayorder=-2 GROUP BY t.tid ORDER BY t.dateline DESC ";
	private StringBuffer sb = new StringBuffer();
	public PostsPageForm(String fids) {
		if(!fids.equals("")){
			countSQL = "select count(*) count from jrun_threads as t where t.displayorder=-2 and t.fid in ("+fids.substring(1)+")";
			sql = "select t.tid, t.fid, t.author, t.authorid, t.subject, t.dateline,p.pid, p.message, p.useip, p.attachment from jrun_threads t LEFT JOIN jrun_posts p ON p.tid=t.tid WHERE t.displayorder=-2 AND t.fid in ("+fids.substring(1)+") GROUP BY t.tid ORDER BY t.dateline DESC ";
		}
		totalSize();
		getTotalPage();
	}
	public PostsPageForm(String countSQL, String sql) {
		this.countSQL = countSQL;
		this.sql = sql;
		totalSize();
		getTotalPage();
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
	public List<Map<String,String>> getList() {
		return this.list;
	}
	private Map<String,String> postsFormat(Map<String,String> p,String path,List<Map<String,String>> smilieslist,MessageResources mr,Locale locale) {
		String message = p.get("message");
		if(message != null){
			message = Common.htmlspecialchars(message);
		}
		for(Map<String,String> smiles:smilieslist){
			if(message.indexOf(smiles.get("code")+" ")!=-1 || message.indexOf(" "+smiles.get("code"))!=-1){
				StringBuffer buffer = new StringBuffer();
				buffer.append("<img src='"+path+"/images/smilies/");
				buffer.append(smiles.get("directory"));
				buffer.append("/");
				buffer.append(smiles.get("url"));
				buffer.append("' smilieid='");
				buffer.append(smiles.get("id"));
				buffer.append("' border='0' alt='' /> ");
				message = StringUtils.replace(message, smiles.get("code"), buffer.toString());
			}
		}
		if (p.get("attachment").equals("1")) {
			List<Map<String,String>> attachmentlist = databaseDao.executeQuery("select filename,filesize,attachment,isimage from jrun_attachments where pid="+p.get("pid"));
			if (attachmentlist != null && attachmentlist.size() > 0) {
				StringBuffer messageSB = new StringBuffer(message);
				for (Map<String,String> attachments : attachmentlist) {
					String type = "text.gif";
					if(attachments.get("isimage").equals("1")){
						type = "image.gif";
					}
					messageSB.append("<br /><br />"+mr.getMessage(locale,"attachment")+":<img src='"+path+"/images/attachicons/"+type+"' border='0' class='absmiddle' alt='' /> ");
					messageSB.append(attachments.get("filename"));
					messageSB.append("(");
					int fileSize = Common.toDigit(attachments.get("filesize"));
					if (fileSize < 1024) {
						messageSB.append(fileSize + " Bytes");
					} else if (fileSize > 1024 && fileSize < 1048576) {
						messageSB.append(fileSize / 1024 + " KB");
					} else {
						messageSB.append(fileSize / 1048576 + " MB");
					}
					messageSB.append(")");
					if(attachments.get("isimage").equals("1")){
						messageSB.append("<br /><br /><img src='"+path+"/attachments/"+ attachments.get("attachment")+ "' onload='if(this.width > 400) {this.resized=true; this.width=400;}'>");
					}
				}
				message = messageSB.toString();
			}
			attachmentlist=null;
		}
		p.put("message", message);
		return p;
	}
	private List<Map<String,String>> postsListFormat(List<Map<String,String>> postsList,String path,MessageResources mr,Locale locale) {
		List<Map<String,String>> postsFormList = new ArrayList<Map<String,String>>();
		List<Map<String,String>> smilieslist = databaseDao.executeQuery("select s.id,s.typeid,s.code,s.url,i.directory from jrun_smilies s left join jrun_imagetypes  i on s.typeid=i.typeid where s.type='smiley' order by s.displayorder");
		if (postsList.size() <= 0) {
			return null;
		} else {
			for(Map<String,String> post:postsList){
				if(post.get("pid")!=null){
					Map<String,String> posts = postsFormat(post,path,smilieslist,mr,locale);
					postsFormList.add(posts);
				}
			}
		}
		smilieslist = null;
		postsList = null;
		return postsFormList;
	}
	public void setList(String path,MessageResources mr,Locale locale,Map<String, Map<String,String>> forumdatas) {
		int startid = 0;
		if (list != null)
			list.clear();
		if (this.currentPage < 0) {
			startid = 0;
		} else {
			if (currentPage > this.totalPage) {
				startid = this.totalPage;
				startid = pageSize * (startid - 1);
			} else {
				startid = pageSize * (currentPage - 1);
			}
		}
		List<Map<String,String>> postlist = databaseDao.executeQuery(sql+" limit "+startid+","+pageSize);
		list = postsListFormat(postlist,path,mr,locale);
		sb = new StringBuffer();
		if (list != null && list.size() > 0) {
			for(Map<String,String> post:list){
				if(forumdatas!=null){
					Map<String,String> forum = forumdatas.get(post.get("fid"));
					post.put("name", forum.get("name"));
				}
				sb.append(post.get("tid")+",");
			}
		}
		postlist = null;
	}
	public int getNextPage() {
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
		if (totalSize > pageSize) {
			if (totalSize % pageSize == 0) {
				setTotalPage((int) ((double) totalSize / (double) pageSize));
			} else {
				setTotalPage((int) (1.0d + (double) totalSize/ (double) pageSize));
			}
		} else {
			setTotalPage(1);
		}
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	private void totalSize() {
		List<Map<String,String>> countlist = databaseDao.executeQuery(countSQL);
		totalSize = Common.intval(countlist.get(0).get("count"));
		setTotalSize(totalSize);
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public String getCountSQL() {
		return countSQL;
	}
	public void setCountSQL(String countSQL) {
		this.countSQL = countSQL;
	}
	public String getSb() {
		return sb.toString();
	}
	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}
}
