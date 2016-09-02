package cn.jsprun.struts.form;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
public class ModrepliesPageForm implements java.io.Serializable{
	private static final long serialVersionUID = 981268284524810774L;
	private DataBaseDao databaseDao = (DataBaseDao) BeanFactory.getBean("dataBaseDao");
	private int currentPage = 1;
	private int prePage = 0;
	private int nextPage = 0;
	private List<Map<String,String>> list = null;
	private int pageSize = 10;
	private int totalPage = 0;
	private int totalSize = 0;
	private String countSQL = "select count(*) count from jrun_posts as p where p.first=0 and p.invisible=-2";
	private String sql = "select p.*,t.subject as threadsubject from jrun_posts as p left join jrun_threads as t on t.tid=p.tid where p.first=0 and p.invisible=-2";
	private StringBuffer sb = null;
	public ModrepliesPageForm(String fids) {
		if(!fids.equals("")){
			countSQL = "select count(*) count from jrun_posts as p where p.first=0 and p.invisible=-2 and p.fid in ("+fids.substring(1)+")";
			sql = "select p.*,t.subject as threadsubject from jrun_posts as p left join jrun_threads as t on t.tid=p.tid where p.first=0 and p.invisible=-2 and p.fid in ("+fids.substring(1)+")";
		}
		totalSize();
		getTotalPage();
	}
	public ModrepliesPageForm(String countSql, String sql) {
		this.countSQL = countSql;
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
	private Map<String,String> postsFormat(List<Map<String,String>> smilieslist,Map<String,String> post,String path,MessageResources mr,Locale locale) {
		String message = post.get("message");
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
		if (post.get("attachment").equals("1")) {
			List<Map<String,String>> attachmentlist = databaseDao.executeQuery("select filename,filesize,attachment,isimage from jrun_attachments where pid="+post.get("pid"));
			if (attachmentlist != null && attachmentlist.size() > 0) {
				StringBuffer messageSB = new StringBuffer(message);
				for (Map<String,String> attachments : attachmentlist) {
					String type = "text.gif";
					if(attachments.get("isimage").equals("1")){
						type = "image.gif";
					}
					messageSB.append("<br /><br />"+mr.getMessage(locale, "attachment")+":<img src='"+path+"/images/attachicons/"+type+"' border='0' class='absmiddle' alt='' /> ");
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
		post.put("message", message);
		return post;
	}
	public void setList(String path,MessageResources mr,Locale locale,Map<String, Map<String,String>> forumdatas) {
		int startid = 0;
		if (list != null && list.size() > 0)
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
		list = new ArrayList<Map<String,String>>();
		List<Map<String,String>> postlist = databaseDao.executeQuery(sql+" limit "+startid+","+pageSize);
		List<Map<String,String>> smilieslist = databaseDao.executeQuery("select s.id,s.typeid,s.code,s.url,i.directory from jrun_smilies s left join jrun_imagetypes  i on s.typeid=i.typeid where s.type='smiley'");
		for(Map<String,String> posts:postlist){
			if(forumdatas!=null){
				Map<String,String> forum = forumdatas.get(posts.get("fid"));
				posts.put("name", forum.get("name"));
			}
			Map<String,String> post = postsFormat(smilieslist,posts,path,mr,locale);
			list.add(post);
		}
		sb = new StringBuffer();
		if (list != null && list.size() > 0) {
			for(Map<String,String>post:postlist){
				sb.append(post.get("pid")+",");
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
