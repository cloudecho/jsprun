package cn.jsprun.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.struts.util.MessageResources;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.dao.OtherSetDao;
import cn.jsprun.domain.Announcements;
import cn.jsprun.domain.Faqs;
import cn.jsprun.domain.Forumlinks;
import cn.jsprun.domain.Magicmarket;
import cn.jsprun.domain.Magics;
import cn.jsprun.domain.Medals;
import cn.jsprun.domain.Settings;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.vo.otherset.FaqInfo;
import cn.jsprun.vo.otherset.FaqsIndexVO;
public class OtherSetService {
	private final static String tablePrefix = "jrun_";
	public boolean updateMagicBasicSet(List<Settings> setBeanList,
			ServletContext context) {
		if (((OtherSetDao) BeanFactory.getBean("otherSetDao")).magicBasicSet(setBeanList)) {
			ForumInit.setSettings(context,null);
			return true;
		}
		return false;
	}
	public List<Magics> queryAllMagic() {
		return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).queryAllMagic();
	}
	public List<Magics> queryAllMagicForPage(Integer begin,Integer maxNum) {
		return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).queryAllMagic(begin,maxNum);
	}
	public List<Magics> queryAllMagicByType(Short typeNum) {
		return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).queryAllMagicByType(typeNum);
	}
	public List<Magics> queryAllMagicByType(Short typeNum,Integer begin,Integer Max) {
		return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).queryAllMagicByType(typeNum,begin,Max);
	}
	public Magics queryMagicById(Short magicId) {
		return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).queryMagicById(magicId);
	}
	public List<Magics> getMagicListByIdList(List<Short> magicIdList){
		return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).getMagicsListByMagicIdList(magicIdList);
	}
	public boolean updateMagicInfo(String deletes[],
			List<Magics> magicBeanList, Magics addMagicBean) {
		OtherSetDao osDao=(OtherSetDao) BeanFactory.getBean("otherSetDao");
		DataBaseDao dataBaseDao = (DataBaseDao) BeanFactory.getBean("dataBaseDao");
		int len = 0;
		if (deletes != null && deletes.length >= 1) {
			StringBuffer deleteSqlBuffer = new StringBuffer("DELETE FROM "+tablePrefix+"magics WHERE magicid IN(");
			for(String deleteId : deletes){
				deleteSqlBuffer.append(deleteId+",");
			}
			int deleteSqlBufferLen = deleteSqlBuffer.length();
			deleteSqlBuffer.replace(deleteSqlBufferLen-1, deleteSqlBufferLen, ")");
			dataBaseDao.executeDelete(deleteSqlBuffer.toString());
			len = deletes.length;
		}
		Iterator<Magics> magicIt = magicBeanList.iterator();
		List<Magics> newMagicBeanList = new ArrayList<Magics>();
		Magics magic = null;
		Magics newMagic = null;
		while (magicIt.hasNext()) {
			magic = magicIt.next();
			boolean flag = true;
			for (int i = 0; i < len; i++) {
				if (magic.getMagicid().equals(Short.parseShort(deletes[i]))) {
					flag = false;
					break;
				}
			}
			if (flag){
				newMagic = osDao.queryMagicById(magic.getMagicid());
				newMagic.setDisplayorder(magic.getDisplayorder());
				newMagic.setName(magic.getName());
				newMagic.setPrice(magic.getPrice());
				newMagic.setNum(magic.getNum());
				newMagic.setDescription(magic.getDescription());
				newMagic.setAvailable(magic.getAvailable());
				newMagicBeanList.add(newMagic);
			}
		}
		if (addMagicBean != null)
			newMagicBeanList.add(addMagicBean);
		boolean flag=osDao.updateMagics(newMagicBeanList);
		osDao=null;
		return flag;
	}
	@SuppressWarnings("unchecked")
	public Map queryMagicMarketInfo() {
		OtherSetDao osDao=(OtherSetDao) BeanFactory.getBean("otherSetDao");
		List mmBeanList = osDao.queryAllMagicInMarket();
		Iterator<Magicmarket> mmIt = mmBeanList.iterator();
		Magicmarket mmBean = null;
		Map magicsMap = new HashMap();
		while (mmIt.hasNext()) {
			mmBean = mmIt.next();
			magicsMap.put(mmBean.getMagicid().toString(), osDao
					.queryMagicById(mmBean.getMagicid()));
		}
		magicsMap.put("mmBeanList", mmBeanList);
		osDao=null;
		return magicsMap;
	}
	public boolean updateMagicMarketInfo(String deletes[],
			List<Magicmarket> mmBeanList) {
		OtherSetDao osDao=(OtherSetDao) BeanFactory.getBean("otherSetDao");
		DataBaseDao dataBaseDao=(DataBaseDao) BeanFactory.getBean("dataBaseDao");
		int len = 0;
		if (deletes != null && deletes.length >= 1) {
			StringBuffer sqlBuffer = new StringBuffer("DELETE FROM "+tablePrefix+"magicmarket WHERE mid IN(");
			for(String delete : deletes){
				sqlBuffer.append(delete+",");
			}
			int sqlBufferLen = sqlBuffer.length();
			sqlBuffer.replace(sqlBufferLen-1, sqlBufferLen, ")");
			dataBaseDao.executeDelete(sqlBuffer.toString());
			len = deletes.length;
		}
		Iterator<Magicmarket> mmIt = mmBeanList.iterator();
		List<Magicmarket> newMMBeanList = new ArrayList<Magicmarket>();
		Magicmarket mmBean = null;
		Magicmarket newMMBean = null;
		while (mmIt.hasNext()) {
			mmBean = mmIt.next();
			boolean flag = true;
			for (int i = 0; i < len; i++) {
				if (mmBean.getMid().equals(Short.parseShort(deletes[i]))) {
					flag = false;
					break;
				}
			}
			if (flag) {
				newMMBean = osDao.queryMagicMarketById(mmBean.getMid());
				newMMBean.setPrice(mmBean.getPrice());
				newMMBean.setNum(mmBean.getNum());
				newMMBeanList.add(newMMBean);
			}
		}
		boolean flag=osDao.updateMagicMarket(newMMBeanList);
		osDao=null;
		return flag;
	}
	public boolean isMark(String identifier){
		List list = ((OtherSetDao) BeanFactory.getBean("otherSetDao")).getMagicByIdentifier(identifier);
		if(list==null||list.size()==0){
			return false;
		}else{
			return true;
		}
	}
	public boolean isMark(String identifier,Short magicId){
		List<Magics> list = ((OtherSetDao) BeanFactory.getBean("otherSetDao")).getMagicByIdentifier(identifier);
		if (list == null || list.size() == 0) {
			return false;
		} else {
			Magics magics = list.get(0);
			if (magics.getMagicid().equals(magicId)) {
				return false;
			} else {
				return true;
			}
		}
	}
	public boolean addFormAnn(Announcements annBean) {
		return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).addAnnouncement(annBean);
	}
	public Announcements queryAnnById(Short id) {
		return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).queryAnnouncementById(id);
	}
	public boolean updateAnn(Announcements ann) {
		return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).updateAnnouncement(ann);
	}
	public void updateFormAnn(String deletes[],String aIds[],String[] newDisplayOrders) {
		DataBaseDao dataBaseDao=(DataBaseDao) BeanFactory.getBean("dataBaseDao");
		Map<String,String> deleteMap = new HashMap<String, String>();
		if(deletes!=null&&deletes.length>0){
			StringBuffer sqlDeleteBuffer = new StringBuffer("DELETE FROM "+tablePrefix+"announcements WHERE id IN(");
			for(String delete : deletes){
				sqlDeleteBuffer.append(delete+",");
				deleteMap.put(delete, "");
			}
			int sqlDeleteBufferLen = sqlDeleteBuffer.length();
			sqlDeleteBuffer.replace(sqlDeleteBufferLen-1, sqlDeleteBufferLen, ")");
			dataBaseDao.executeDelete(sqlDeleteBuffer.toString());
		}
		if(aIds!=null&&aIds.length>0){
			StringBuffer sqlUpdateBuffer = new StringBuffer("UPDATE "+tablePrefix+"announcements AS a SET a.displayorder=");
			StringBuffer sqlUpdateBuffer_w = new StringBuffer(" WHERE a.id=");
			boolean sign = true;
			String aid = null;
			int sqlUpdateBufferLen = sqlUpdateBuffer.length();
			int sqlUpdateBuffer_wLen = sqlUpdateBuffer_w.length();
			for(int i = 0;i<aIds.length;i++){
				aid = aIds[i];
				if(deleteMap.get(aid)==null){
					if(sign){
						sqlUpdateBuffer.append(newDisplayOrders[i]);
						sqlUpdateBuffer_w.append(aid);
						sign = false;
					}else{
						sqlUpdateBuffer.replace(sqlUpdateBufferLen, sqlUpdateBuffer.length(), newDisplayOrders[i]);
						sqlUpdateBuffer_w.replace(sqlUpdateBuffer_wLen,sqlUpdateBuffer_w.length(),aid);
					}
					dataBaseDao.execute(sqlUpdateBuffer.toString()+sqlUpdateBuffer_w.toString());
				}
			}
		}
	}
	public List<Medals> queryAllMedals() {
		return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).queryAllMedals();
	}
	public void updateForumMedals(String deletes[], List<Medals> mBeanList,
			Medals addMBean) {
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		Map<String,String> deleteMap = new HashMap<String, String>();
		if (deletes != null && deletes.length > 0) {
			StringBuffer dsql = new StringBuffer("DELETE FROM "+tablePrefix+"medals WHERE medalid IN(");
			for(String delete : deletes){
				dsql.append(delete+",");
				deleteMap.put(delete, "");
			}
			int dsqlLen = dsql.length();
			dsql.replace(dsqlLen-1, dsqlLen, ")");
			dataBaseDao.executeDelete(dsql.toString());
		}
		OtherSetDao osDao=(OtherSetDao) BeanFactory.getBean("otherSetDao");
		int mBeanListLen = mBeanList.size();
		for(int i = 0;i<mBeanListLen;i++){
			Medals medals = mBeanList.get(i);
			if(deleteMap.get(medals.getMedalid().toString())!=null){
				mBeanList.remove(medals);
				i--;
				mBeanListLen--;
			}
		}
		osDao.updateMedals(mBeanList);
		if (addMBean != null) {
			osDao.addMedal(addMBean);
		}
	}
	public List<Forumlinks> queryAllForumLinkInfo() {
		return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).queryAllForumLink();
	}
	public void updateForumLinks(List<String> deletes, List<Forumlinks> flList) {
		if(deletes.size()>0){
			StringBuffer delete_sqlBuffer = new StringBuffer("DELETE FROM "+tablePrefix+"forumlinks WHERE id IN(");
			for(String delete:deletes){
				delete_sqlBuffer.append(delete+",");
			}
			int delete_sqlBufferLen = delete_sqlBuffer.length();
			delete_sqlBuffer.replace(delete_sqlBufferLen-1, delete_sqlBufferLen, ")");
			((DataBaseDao) BeanFactory.getBean("dataBaseDao")).executeDelete(delete_sqlBuffer.toString());
		}
		OtherSetDao osDao=(OtherSetDao) BeanFactory.getBean("otherSetDao");
		if(flList.size()>0){
			osDao.updateForumLinks(flList);
		}
	}
	public FaqsIndexVO getFaqsIndexVO(MessageResources mr,Locale locale){
		List<Faqs> faqList = ((OtherSetDao) BeanFactory.getBean("otherSetDao")).queryAllFaq();
		FaqsIndexVO faqsIndexVO = new FaqsIndexVO();
		List<FaqInfo> allFaqList = faqsIndexVO.getAllFaqList();
		List<FaqInfo> topperFaqList = faqsIndexVO.getTopperFaqList();
		FaqInfo faqInfo = null;
		Faqs faqs = null;
		FaqInfo faqInfoSub = null;
		if(faqList!=null&&faqList.size()>0){
			int faqListSize = faqList.size();
			for(int i = 0;i<faqListSize;i++){
				faqs = faqList.get(i);
				if(faqs.getFpid()==0){
					faqInfo = new FaqInfo();
					short faqId = faqs.getId();
					faqInfo.setId(String.valueOf(faqId));
					String title = faqs.getTitle();
					title = title!=null?title.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;"):null;
					faqInfo.setTitle(title);
					faqInfo.setTopper(true);
					faqInfo.setTopperTitle(mr.getMessage(locale, "none"));
					faqInfo.setDisplayorder(faqs.getDisplayorder().toString());
					allFaqList.add(faqInfo);
					topperFaqList.add(faqInfo);
					boolean ableToDelete = true;
					for(int j = 0;j<faqListSize;j++){
						faqs = faqList.get(j);
						if(faqId==faqs.getFpid()){
							faqInfoSub = new FaqInfo();
							faqInfoSub.setId(faqs.getId().toString());
							String tempTitle = faqs.getTitle();
							faqInfoSub.setTitle(tempTitle!=null?tempTitle.replace("\"", "&quot;"):null);
							faqInfoSub.setTopper(false);
							faqInfoSub.setTopperTitle(title);
							faqInfoSub.setDisplayorder(faqs.getDisplayorder().toString());
							faqInfoSub.setAbleToDelete(true);
							allFaqList.add(faqInfoSub);
							ableToDelete = false;
						}
					}
					faqInfo.setAbleToDelete(ableToDelete);
				}
			}
		}
		return faqsIndexVO;
	}
	public boolean updateFaq(Faqs fBean) {
		OtherSetDao osDao=(OtherSetDao) BeanFactory.getBean("otherSetDao");
		Faqs newFBean = osDao.queryFaqById(fBean.getId());
		newFBean.setTitle(fBean.getTitle());
		newFBean.setFpid(fBean.getFpid());
		if (fBean.getFpid() != 0) {
			newFBean.setIdentifier(fBean.getIdentifier());
			newFBean.setKeyword(fBean.getKeyword());
			newFBean.setMessage(fBean.getMessage());
		}
		boolean flag=osDao.updateFaq(newFBean);
		osDao=null;
		return flag;
	}
	public List<Faqs> getRootFaqs(){
		return ((OtherSetDao) BeanFactory.getBean("otherSetDao")).getRootFaqs();
	}
	public boolean operateFaqs(List<Short> deleteIdList, List<Faqs> fList, Faqs addFaqs) {
		OtherSetDao osDao=(OtherSetDao) BeanFactory.getBean("otherSetDao");
		List<Faqs> faqsListFromDataBase = osDao.queryAllFaq();
		List<Faqs> faqsListDelete = new ArrayList<Faqs>();
		List<Faqs> faqsListUpdate = new ArrayList<Faqs>();
		for(int i = 0;i<faqsListFromDataBase.size();i++){
			boolean bool = true;
			Faqs faqsFromQuery = faqsListFromDataBase.get(i);
			String titlesFromQuery = faqsFromQuery.getTitle();
			Short displayorderFromQuery = faqsFromQuery.getDisplayorder();
			for(int j = 0;j<deleteIdList.size();j++){
				Short faqsIdFromDeleteIdList = deleteIdList.get(j);
				if(faqsFromQuery.getId().equals(faqsIdFromDeleteIdList)){
					faqsListDelete.add(faqsFromQuery);
					deleteIdList.remove(faqsIdFromDeleteIdList);
					faqsListFromDataBase.remove(faqsFromQuery);
					bool = false;
					i--;
					break;
				}
			}
			for(int j = 0;j<fList.size();j++){
				Faqs faqsFromPage = fList.get(j);
				String titlesFromPage = faqsFromPage.getTitle();
				Short displayorderFormPage = faqsFromPage.getDisplayorder();
				if( bool&& 
					faqsFromQuery.getId().equals(faqsFromPage.getId())&& 
					(!titlesFromQuery.equals(titlesFromPage)||
					 !displayorderFromQuery.equals(displayorderFormPage))){
					faqsFromQuery.setTitle(titlesFromPage);
					faqsFromQuery.setDisplayorder(displayorderFormPage);
					faqsListUpdate.add(faqsFromQuery);
					fList.remove(faqsFromPage);
					break;
				}
			}
		}
		boolean flag=osDao.delAnyFaqs(faqsListDelete)&&osDao.updateFaqs(faqsListUpdate)&&osDao.addFaqs(addFaqs);
		osDao=null;
		return flag;
	}
}
