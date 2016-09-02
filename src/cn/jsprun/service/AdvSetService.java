package cn.jsprun.service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.jsprun.dao.AdvertisementsDao;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.dao.ForumsDao;
import cn.jsprun.domain.Advertisements;
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Members;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.DataParse;
import cn.jsprun.vo.otherset.AdvEditVO;
public class AdvSetService {
	private static final String tablePrefix = "jrun_";
	public boolean addAd(Advertisements adv){
		return ((AdvertisementsDao)BeanFactory.getBean("advSetDao")).addAdv(adv);
	}
	public Advertisements queryAdById(Integer id){
		return ((AdvertisementsDao)BeanFactory.getBean("advSetDao")).queryAdvById(id);
	}
	@SuppressWarnings("unchecked")
	public Map queryAdvWithPagination(String title, String startTime,String type,String orderBy,int firstResult,int maxResult,String countKey,String listKey){
		String sqlStatement = getSQLSentence(title, startTime, type, orderBy);
		return ((AdvertisementsDao)BeanFactory.getBean("advSetDao")).queryAdvWithPagination(sqlStatement, firstResult, maxResult,countKey,listKey);
	}
	public boolean updateAd(Advertisements ad){
		Advertisements newAd = ((AdvertisementsDao)BeanFactory.getBean("advSetDao")).queryAdvById(ad.getAdvid());
		newAd.setAdvid(ad.getAdvid());
		newAd.setTitle(ad.getTitle());
		newAd.setTargets(ad.getTargets());
		newAd.setType(ad.getType());
		newAd.setStarttime(ad.getStarttime());
		newAd.setEndtime(ad.getEndtime());
		newAd.setCode(ad.getCode());
		newAd.setParameters(ad.getParameters());
		return ((AdvertisementsDao)BeanFactory.getBean("advSetDao")).updateAdv(newAd);
	}
	public void updateAds(String deleteIds[],String[] ids,String[] titles,String[] displayorders,String[] availables){
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		Map<String,String> delMap = new HashMap<String, String>();
		if(deleteIds!=null){
			StringBuffer delSQLBuffer = new StringBuffer("DELETE FROM "+tablePrefix+"advertisements WHERE advid IN(");
			for(String deleteId : deleteIds){
				delMap.put(deleteId, "");
				delSQLBuffer.append(deleteId+",");
			}
			int delSQLBufferLen = delSQLBuffer.length();
			delSQLBuffer.replace(delSQLBufferLen-1, delSQLBufferLen, ")");
			dataBaseDao.executeDelete(delSQLBuffer.toString());
		}
		int idsLen = ids.length;
		for(int i=0;i<idsLen;i++ ){
			String advertisementId = ids[i];
			if(delMap.get(advertisementId)==null){
				dataBaseDao.execute("UPDATE "+tablePrefix+"advertisements AS a SET a.available="+availables[i]+", a.displayorder="+displayorders[i]+", a.title='"+Common.addslashes(titles[i])+"' WHERE a.advid="+advertisementId);
			}
		}
	}
	public List<Forums> getAllForumsWithoutGroup(){
		List<Forums> allForumsList = ((ForumsDao)BeanFactory.getBean("forumsDao")).findAll();
		List<Forums> forumsList = new ArrayList<Forums>();
		for(int i = 0;i<allForumsList.size();i++){
			Forums forums = allForumsList.get(i);
			if(!forums.getType().equals("group")){
				forumsList.add(forums);
			}
		}
		return forumsList;
	}
	@SuppressWarnings("unchecked")
	public AdvEditVO getAdvEditVO(Advertisements advertisements,short groupid,Members member,int postperpage,SimpleDateFormat simpleDateFormat){
		Map<String,String> parametersMap = ((DataParse)BeanFactory.getBean("dataParse")).characterParse(advertisements.getParameters(), false);
		String type = advertisements.getType();
		List<String> selectFidList = null;
		if(type.equals("intercat")){
			selectFidList = getSelectFidList(parametersMap);
		}else{
			selectFidList = getSelectFidList(advertisements);
		}
		AdvEditVO advEditVO = new AdvEditVO();
		setAdvEditVO(advEditVO, parametersMap, advertisements.getCode());
		setAdvEditVO(advEditVO, type, parametersMap, selectFidList, groupid, member, postperpage);
		setAdvEditVO(advEditVO, advertisements.getAdvid().toString(), advertisements.getTitle(), type, advertisements.getStarttime(), advertisements.getEndtime(), parametersMap.get("style"),simpleDateFormat);
		return advEditVO;
	}
	private List<String> getSelectFidList(Map<String,String> parametersMap){
		List<String> selectFidList = new ArrayList<String>();
		String targetForumIds = parametersMap.get("position");
		if(targetForumIds!=null&&!targetForumIds.trim().equals("")){
			String[] targetArray = targetForumIds.split(",");
			for(String target : targetArray){
				if(!target.equals("")){
					selectFidList.add(target);
				}
			}
		}
		return selectFidList;
	}
	private void setAdvEditVO(AdvEditVO advEditVO,String advid,String title,String type,Integer startTime,Integer endtime,String style,SimpleDateFormat simpleDateFormat){
		advEditVO.setAdvid(advid);
		advEditVO.setTitle(title!=null?title.replace("\"", "&quot;"):null);
		advEditVO.setType(type);
		advEditVO.setStarttime(Common.gmdate(simpleDateFormat, startTime));
		advEditVO.setEndtime(endtime==0?"":Common.gmdate(simpleDateFormat, endtime));
		advEditVO.setParameters_style(style);
	}
	private List<String> getSelectFidList(Advertisements advertisements){
		List<String> selectFidList = new ArrayList<String>();
		String targetForumIds = advertisements.getTargets();
		if(targetForumIds!=null&&!targetForumIds.trim().equals("")){
			String[] targetArray = targetForumIds.split("\t");
			for(String target : targetArray){
				if(!target.equals("")){
					selectFidList.add(target);
				}
			}
		}
		return selectFidList;
	}
	private void setAdvEditVO(AdvEditVO advEditVO,Map<String,String> parametersMap,String code){
		String style = parametersMap.get("style");
		if(style.equals("code")){
			advEditVO.setCode(code);
		}else if(style.equals("text")){
			String title = parametersMap.get("title");
			advEditVO.setParameters_title(title!=null?title.replace("\"", "&quot;"):"");
			String link = parametersMap.get("link");
			advEditVO.setParameters_link(link!=null?link.replace("\"", "&quot;"):"");
			String size = parametersMap.get("size");
			advEditVO.setParameters_size(size!=null?size.replace("\"", "&quot;"):"");
		}else if(style.equals("image")){
			String url = parametersMap.get("url");
			advEditVO.setParameters_url(url!=null?url.replace("\"", "&quot;"):"");
			String link = parametersMap.get("link");
			advEditVO.setParameters_link(link!=null?link.replace("\"", "&quot;"):"");
			String width = parametersMap.get("width");
			advEditVO.setParameters_width(width!=null?width.replace("\"", "&quot;"):"");
			String height = parametersMap.get("height");
			advEditVO.setParameters_height(height!=null?height.replace("\"", "&quot;"):"");
			String alt = parametersMap.get("alt");
			advEditVO.setParameters_alt(alt!=null?alt.replace("\"", "&quot;"):"");
		}else if(style.equals("flash")){
			String url = parametersMap.get("url");
			advEditVO.setParameters_url(url!=null?url.replace("\"", "&quot;"):"");
			String link = parametersMap.get("link");
			advEditVO.setParameters_link(link!=null?link.replace("\"", "&quot;"):"");
			String width = parametersMap.get("width");
			advEditVO.setParameters_width(width!=null?width.replace("\"", "&quot;"):"");
			String height = parametersMap.get("height");
			advEditVO.setParameters_height(height!=null?height.replace("\"", "&quot;"):"");
		}
	}
	private void setAdvEditVO(AdvEditVO advEditVO,String type,Map<String,String> parametersMap,List<String> targetFidList,short groupid,Members member,int postperpage){
		if(type.equals("intercat")){
			List<Map<String,String>> forumList = ((DataBaseDao)BeanFactory.getBean("dataBaseDao")).executeQuery("SELECT fid,name FROM "+tablePrefix+"forums WHERE type='group'");
			StringBuffer forumlistBuffer =new StringBuffer();
			for(Map<String,String> forum:forumList){
				forumlistBuffer.append("<option value='"+forum.get("fid")+"' "+(targetFidList.contains(forum.get("fid"))?"selected":"")+">"+Common.strip_tags(forum.get("name"))+"</option>");
			}
			advEditVO.setSelectContent(forumlistBuffer.toString());
			advEditVO.setSelected_all(targetFidList.contains("0"));
		}else{
			advEditVO.setSelectContent(Common.showForumWithSelected(false, false, groupid, member!=null?member.getExtgroupids():"", targetFidList));
			advEditVO.setSelected_all(targetFidList.contains("all"));
			if(type.equals("headerbanner")||type.equals("footerbanner")){
				advEditVO.setSelected_index(targetFidList.contains("0"));
				advEditVO.setSelected_register(targetFidList.contains("register"));
				advEditVO.setSelected_redirect(targetFidList.contains("redirect"));
				advEditVO.setSelected_archiver(targetFidList.contains("archiver"));
				if(type.equals("footerbanner")){
					advEditVO.setParameters_position(parametersMap.get("position"));
				}
			}else if(type.equals("text")||type.equals("couplebanner")){
				advEditVO.setSelected_index(targetFidList.contains("0"));
			}else if(type.equals("thread")){
				advEditVO.setParameters_position(parametersMap.get("position"));
				String displayorder = parametersMap.get("displayorder");
				List<String> displayorderList = new ArrayList<String>();
				if(displayorder!=null&&!displayorder.trim().equals("")){
					String[] displayorderArray = displayorder.split("\t");
					for(String num : displayorderArray){
						if(!num.equals("")){
							displayorderList.add(num);
						}
					}
				}
				StringBuffer postperpageBuffer =new StringBuffer();
				for(int i = 1;i<=postperpage;i++){
					postperpageBuffer.append("<option value='"+i+"' "+(displayorderList.contains(String.valueOf(i))?"selected":"")+"> &nbsp;&nbsp;> #"+i+"</option>");
				}
				advEditVO.setPostperpage(postperpageBuffer.toString());
				advEditVO.setPp_selectedAll(displayorderList.contains("0"));
			}else if(type.equals("interthread")){
			}else if(type.equals("float")){
				advEditVO.setSelected_index(targetFidList.contains("0"));
				advEditVO.setParameters_floath(parametersMap.get("floath"));
			}
		}
	}
	private String getSQLSentence(String title, String startTime,String type,String orderBy){
		StringBuffer sqlSb = new StringBuffer("from Advertisements ");
		boolean appAlready = false;
		if(title!=null && !title.equals("")){
			sqlSb.append(" where title like '%" + title + "%' ");
			appAlready = true;
		}
		if(startTime!=null&&!startTime.equals("") && !startTime.equals("0")){
			int st = Common.time() - Integer.parseInt(startTime);
			if(!appAlready){
				sqlSb.append(" where starttime > " + st + " ");
				appAlready = true;
			}else{
				sqlSb.append(" and starttime > " + st + " ");
			}
		}
		if(type!=null&&!type.equals("") && !type.equals("0")){
			if(!appAlready){
				sqlSb.append(" where type = '" + type + "' ");
			}else{
				sqlSb.append(" and type = '" + type + "' ");
			}
		}
		if(orderBy!=null&&!orderBy.equals("")){
			sqlSb.append(" order by " + orderBy);
		}else{
			sqlSb.append(" order by displayorder,advid desc");
		}
		return sqlSb.toString();
	}
}
