package cn.jsprun.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.struts.util.MessageResources;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.dao.OnLineListDao;
import cn.jsprun.domain.Onlinelist;
import cn.jsprun.domain.OnlinelistId;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.vo.otherset.OnlinelistVO;
public class OnLineSetService {
	private final static String tablePrefix = "jrun_"; 
	public List<OnlinelistVO> queryAllSystemUserGroup(MessageResources mr,Locale locale){
		List<Map<String,String>> usergroupMapList = ((DataBaseDao)BeanFactory.getBean("dataBaseDao")).executeQuery("SELECT u.groupid,u.grouptitle FROM "+tablePrefix+"usergroups AS u WHERE u.type<>'member'");
		List<Onlinelist> onlinelist_list = ((OnLineListDao)BeanFactory.getBean("onLineListDao")).queryAllOnlineList();
		Map<String,Onlinelist> onlinelistMap = new HashMap<String, Onlinelist>();
		for(Onlinelist onlinelist : onlinelist_list){
			onlinelistMap.put(onlinelist.getId().getGroupid().toString(), onlinelist);
		}
		List<OnlinelistVO> onlinelistVO_list = new ArrayList<OnlinelistVO>();
		Onlinelist onlinelist = null;	
		OnlinelistId onlinelistId = null;	
		OnlinelistVO onlinelistVO = null;	
		String groupid = null;			
		String quondamTitle = null;		
		String displayorder = null;		
		String title = null;		
		String url = null;
		onlinelist = onlinelistMap.get("0");
		String normalUser = mr.getMessage(locale, "usergroups_system_0");
		if(onlinelist!=null){
			onlinelistId = onlinelist.getId();
			onlinelistVO = new OnlinelistVO();
			onlinelistVO.setDisplayorder(onlinelistId.getDisplayorder().toString());
			onlinelistVO.setGroupid("0");
			onlinelistVO.setQuondamTitle(normalUser);
			onlinelistVO.setTitle(onlinelistId.getTitle());
			onlinelistVO.setUrl(onlinelistId.getUrl());
			onlinelistVO_list.add(onlinelistVO);
		}else{
			onlinelistVO = new OnlinelistVO();
			onlinelistVO.setDisplayorder("");
			onlinelistVO.setGroupid("0");
			onlinelistVO.setQuondamTitle(normalUser);
			onlinelistVO.setTitle(normalUser);
			onlinelistVO.setUrl("");
			onlinelistVO_list.add(onlinelistVO);
		}
		for(Map<String,String> usergroupMap : usergroupMapList){
			onlinelistVO = new OnlinelistVO();
			groupid = usergroupMap.get("groupid");
			String grouptitle = usergroupMap.get("grouptitle");
			switch (Short.parseShort(groupid)) {
			case 1:
				quondamTitle = mr.getMessage(locale, "usergroups_system_1");
				break;
			case 2:
				quondamTitle = mr.getMessage(locale, "usergroups_system_2");
				break;
			case 3:
				quondamTitle = mr.getMessage(locale, "usergroups_system_3");
				break;
			case 4:
				quondamTitle = mr.getMessage(locale, "usergroups_system_4");
				break;
			case 5:
				quondamTitle = mr.getMessage(locale, "usergroups_system_5");
				break;
			case 6:
				quondamTitle = mr.getMessage(locale, "usergroups_system_6");
				break;
			case 7:
				quondamTitle = mr.getMessage(locale, "guest");
				break;
			case 8:
				quondamTitle = mr.getMessage(locale, "usergroups_system_8");
				break;
			default:
				quondamTitle = grouptitle;
			}
			onlinelist = onlinelistMap.get(groupid);
			if(onlinelist==null){
				displayorder="";
				title = grouptitle;
				url = "";
			}else{
				onlinelistId = onlinelist.getId();
				displayorder = onlinelistId.getDisplayorder().toString();
				title = onlinelistId.getTitle();
				url = onlinelistId.getUrl();
			}
			onlinelistVO.setDisplayorder(displayorder);
			onlinelistVO.setGroupid(groupid);
			onlinelistVO.setQuondamTitle(quondamTitle!=null?quondamTitle.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;"):null);
			onlinelistVO.setTitle(title!=null?title.replace("\"", "&quot;"):null);
			onlinelistVO.setUrl(url!=null?url.replace("\"", "&quot;"):null);
			onlinelistVO_list.add(onlinelistVO);
		}
		return onlinelistVO_list;
	}
	public void updateOnLineList(String delGroupids , List<Onlinelist> updateList, List<Onlinelist> addList) {
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		if(delGroupids!=null){
			dataBaseDao.executeDelete("DELETE FROM "+tablePrefix+"onlinelist WHERE groupid IN("+delGroupids+")");
		}
		OnlinelistId onlinelistId = null;
		for(Onlinelist onlinelist : updateList){
			onlinelistId = onlinelist.getId();
			dataBaseDao.execute("UPDATE "+tablePrefix+"onlinelist SET title='"+Common.addslashes(onlinelistId.getTitle())+"', url='"+Common.addslashes(onlinelistId.getUrl())+"', displayorder="+onlinelistId.getDisplayorder()+" WHERE groupid="+onlinelistId.getGroupid());
		}
		if(addList.size()>0){
			((OnLineListDao)BeanFactory.getBean("onLineListDao")).addOnlinelist(addList);
		}
	}
}
