package cn.jsprun.service;
import java.util.List;
import java.util.Map;
import cn.jsprun.dao.AttachmentsDao;
import cn.jsprun.dao.PostsDao;
import cn.jsprun.domain.Attachments;
import cn.jsprun.domain.Posts;
import cn.jsprun.struts.form.ModrepliesPageForm;
import cn.jsprun.struts.form.PostsForm;
import cn.jsprun.struts.form.PostsPageForm;
import cn.jsprun.struts.form.PruneForm;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
public class PostsService {
	public PostsPageForm fidnByForums(PostsForm postsForm) {
		String displayorder = "";
		if (postsForm.getFilter().equals("normal")) {
			displayorder = "-2";
		}
		if (postsForm.getFilter().equals("ignore")) {
			displayorder = "-3";
		}
		return ((PostsDao) BeanFactory.getBean("postsDao")).fidnByForums(postsForm.getFid(), displayorder);
	}
	public boolean deletePostsIDArray(List deleteList) {
		if (deleteList != null && deleteList.size() > 0) {
			((PostsDao) BeanFactory.getBean("postsDao")).deletePostsIDArray(deleteList);
		}
		return true;
	}
	public boolean ignorePostsIDArray(List ignoreList) {
		if (ignoreList != null && ignoreList.size() > 0) {
			return ((PostsDao) BeanFactory.getBean("postsDao")).ignorePostsIDArray(ignoreList);
		}
		return true;
	}
	public List validatePostsIDArray(List validateList) {
		if (validateList != null && validateList.size() > 0) {
			return ((PostsDao) BeanFactory.getBean("postsDao")).validatePostsIDArray(validateList);
		}
		return null;
	}
	public Posts getPostsById(Integer pid){
		return ((PostsDao) BeanFactory.getBean("postsDao")).getPostsById(pid);
	}
	public ModrepliesPageForm fidnByModreplies(PostsForm postsForm) {
		String invisible = "";
		if (postsForm.getFilter().equals("normal")) {
			invisible="-2";
		}
		if (postsForm.getFilter().equals("ignore")) {
			invisible="-3";
		}
		return ((PostsDao) BeanFactory.getBean("postsDao")).fidnByModreplies(postsForm.getFid(),invisible);
	}
	public boolean validateModrepliesIDArray(List<Posts> modrepliesList) {
		if (modrepliesList != null && modrepliesList.size() > 0) {
			return ((PostsDao) BeanFactory.getBean("postsDao")).validateModrepliesIDArray(modrepliesList);
		}
		return true;
	}
	public boolean ignoreModrepliesIDArray(List<String> modrepliesList) {
		if (modrepliesList != null && modrepliesList.size() > 0) {
			return ((PostsDao) BeanFactory.getBean("postsDao")).ignoreModrepliesIDArray(modrepliesList);
		}
		return true;
	}
	public boolean deleteModrepliesIDArray(List<String> modrepliesList) {
		if (modrepliesList.size() <= 0) {
			return true;
		} else {
			((PostsDao) BeanFactory.getBean("postsDao")).deleteModrepliesIDArray(modrepliesList);
		}
		return true;
	}
	public boolean deletePostsByUserid(Integer uid) {
		return ((PostsDao) BeanFactory.getBean("postsDao")).deletePostsByUserUid(uid);
	}
	public Map batchPrune(String sb, boolean b) {
		return ((PostsDao) BeanFactory.getBean("postsDao")).batchPrune(sb, b);
	}
	public Integer batchForumrecommend(StringBuffer deletesb, List list) {
		PostsDao postsDao = (PostsDao) BeanFactory.getBean("postsDao");
		postsDao.deleteForumrecommend(deletesb);
		int num=postsDao.updateForumrecommendList(list);
		postsDao=null;
		return num;
	}
	public List<Posts> getPostsListByPidList(List<Integer> pidList){
		return ((PostsDao) BeanFactory.getBean("postsDao")).getPostsListByPidList(pidList);
	}
	public int findPostCountByHql(String hql){
		return ((PostsDao) BeanFactory.getBean("postsDao")).findPostCountByHql(hql);
	}
	public List<Posts> findPostByhql(String hql,int start,int max){
		return ((PostsDao) BeanFactory.getBean("postsDao")).findPostByhql(hql, start, max);
	}
	public Posts findPostByThreadId(int tid){
		return ((PostsDao) BeanFactory.getBean("postsDao")).findPostByThreadId(tid);
	}
	public boolean updatePosts(Posts post){
		return ((PostsDao) BeanFactory.getBean("postsDao")).updatePosts(post);
	}
	public int insertAttachments(Attachments attachments){
		return ((AttachmentsDao) BeanFactory.getBean("attachmentsDao")).insertAttachments(attachments);
	}
	public int saveOrupdatePosts(Posts post){
		return ((PostsDao) BeanFactory.getBean("postsDao")).saveOrupdatePosts(post);
	}
	public boolean updateAttachments(Attachments attachments){
		return ((AttachmentsDao) BeanFactory.getBean("attachmentsDao")).updateAttachments(attachments);
	}
	public boolean deleteAttachments(Attachments attachments){
		return ((AttachmentsDao) BeanFactory.getBean("attachmentsDao")).deleteAttachments(attachments);
	}
	public Attachments findAttachmentsById(int aid){
		return ((AttachmentsDao) BeanFactory.getBean("attachmentsDao")).findAttachmentsById(aid);
	}
	public List<Posts> getPostsListByTid(Integer tid){
		return ((PostsDao) BeanFactory.getBean("postsDao")).getPostsListByTid(tid);
	}
	public String prunsql(PruneForm pf,String timeoffset){
		StringBuffer sql = new StringBuffer("from jrun_posts p LEFT JOIN jrun_threads t USING(tid) ");
		String and = "";
		String where = " where ";
		String userip = pf.getUseip(); 
		String keywords = pf.getKeywords();
		if (pf.getUseip() != null) {
			userip = pf.getUseip().replace("*", "%");
			userip = Common.addslashes(userip);
		}
		if (pf.getKeywords() != null && !pf.getKeywords().equals("")) {
			Object[] key = keywords.split(",");
			sql.append(where);
			where = "";
			sql.append(and);
			and = " and ";
			String or=" ( ";
			for (int i = 0; i < key.length; i++) {
				if(Common.matches(key[i].toString().trim(), "\\{(\\d+)\\}")){
					String keyword = key[i].toString().trim().replaceAll("\\{(\\d+)\\}", ".{0,$1}");
					sql.append(or+" p.message REGEXP '"+Common.addslashes(keyword)+"'");
				}else{
					sql.append(or+" p.message like '%"+Common.addslashes(key[i].toString().trim())+"%'");
				}
				or = " or ";
			}
			sql.append(")");
		}
		if (userip != null && !userip.equals("")) {
			sql.append(where);
			where = "";
			sql.append(and);
			and = " and ";
			sql.append("p.useip LIKE '"+userip+"'");
		}
		if (Common.datecheck(pf.getEndtime())) {
			sql.append(where);
			where = "";
			sql.append(and);
			and = " and ";
			int endtime = Common.dataToInteger(pf.getEndtime(),"yyyy-MM-dd",timeoffset);
			sql.append("p.dateline<="+endtime);
		}
		if (Common.datecheck(pf.getStarttime())) {
			int startime = Common.dataToInteger(pf.getStarttime(),"yyyy-MM-dd",timeoffset);
			sql.append(where);
			where = "";
			sql.append(and);
			and = " and ";
			sql.append("p.dateline>="+startime);
		}
		if (pf.getFid()!=null) {
			sql.append(where);
			where = "";
			sql.append(and);
			and = " and ";
			sql.append("p.fid in ( "+pf.getFid()+" )");
		}else if(pf.getForums()>0){
			sql.append(where);
			where = "";
			sql.append(and);
			and = " and ";
			sql.append("p.fid = "+pf.getForums());
		}
		if (pf.getLengthlimit() != null && !pf.getLengthlimit().equals("")) {
			sql.append(where);
			where = "";
			sql.append(and);
			and = " and ";
			sql.append("length(message) >"+pf.getLengthlimit());
		}
		return sql.toString();
	}
}
