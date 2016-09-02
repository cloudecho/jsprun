package cn.jsprun.foreg.service;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.struts.util.MessageResources;
import cn.jsprun.dao.AttachmentsDao;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.dao.ForumRecommendDao;
import cn.jsprun.dao.ForumfieldsDao;
import cn.jsprun.dao.ForumsDao;
import cn.jsprun.dao.MembersDao;
import cn.jsprun.dao.MypostsDao;
import cn.jsprun.dao.MythreadDao;
import cn.jsprun.dao.PmsDao;
import cn.jsprun.dao.PostsDao;
import cn.jsprun.dao.RatelogDao;
import cn.jsprun.dao.ThreadsDao;
import cn.jsprun.dao.ThreadsmodDao;
import cn.jsprun.domain.Attachments;
import cn.jsprun.domain.Forumfields;
import cn.jsprun.domain.Forumrecommend;
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Pms;
import cn.jsprun.domain.Posts;
import cn.jsprun.domain.Ratelog;
import cn.jsprun.domain.RatelogId;
import cn.jsprun.domain.Threads;
import cn.jsprun.domain.Threadsmod;
import cn.jsprun.domain.ThreadsmodId;
import cn.jsprun.foreg.vo.topicadmin.BaseVO;
import cn.jsprun.foreg.vo.topicadmin.CloseOrOpenTopicVO;
import cn.jsprun.foreg.vo.topicadmin.HighLightVO;
import cn.jsprun.foreg.vo.topicadmin.OtherBaseVO;
import cn.jsprun.foreg.vo.topicadmin.ToTopAndEliteVO;
import cn.jsprun.foreg.vo.topicadmin.TopicAdmin_MoveVO;
import cn.jsprun.foreg.vo.topicadmin.TopicClassVO;
import cn.jsprun.foreg.vo.topicadmin.TopicCopyVO;
import cn.jsprun.foreg.vo.topicadmin.TopicPublicVO;
import cn.jsprun.foreg.vo.topicadmin.TopicRefundVO;
import cn.jsprun.foreg.vo.topicadmin.TopicSplitVO;
import cn.jsprun.foreg.vo.topicadmin.BaseVO.Log;
import cn.jsprun.foreg.vo.topicadmin.BaseVO.ThreadInfo;
import cn.jsprun.foreg.vo.topicadmin.TopicSplitVO.PostsInfo;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.DataParse;
import cn.jsprun.utils.ForumInit;
public class TopicAdminActionService {
	private final static String tableprefix = "jrun_";
	public BaseVO getFinalBaseVO(Map<String,Object> transfersMap,Map<String,String> operationInfoMap){
		String operation = (String)transfersMap.get("operation");
		String[] moderates = (String[])transfersMap.get("moderates"); 
		String fid = (String)transfersMap.get("fid");	
		String timeoffsetSession = (String)transfersMap.get("timeoffset"); 
		String modreasons = (String)transfersMap.get("modreasons");	
		String reasonpm = (String)transfersMap.get("reasonpm"); 
		String fromWhere = (String)transfersMap.get("fromWhere");	
		String dateformat = (String)transfersMap.get("dateformat");
		String timeformat = (String)transfersMap.get("timeformat");
		SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffsetSession);
		BaseVO baseVO = null;
		if(operation.equals("move")){
			baseVO = geFinalTopicAdmin_MoveVO((Short)transfersMap.get("groupid"),(Members)transfersMap.get("member"));
		}else if(operation.equals("close")){
			baseVO = getCloseOrOpenTopicVO(moderates);
		}else if(operation.equals("stick")||operation.equals("digest")){
			baseVO = geToTopAndEliteVO(transfersMap);
		}else if(operation.equals("type")){
			baseVO = geTopicClassVO(fid);
		}else if(operation.equals("highlight")){
			baseVO = getHighLightVO(transfersMap);
		}else if(operation.equals("delete")
				||operation.equals("bump")
				||operation.equals("delpost")
				||operation.equals("recommend")
				||operation.equals("deleteMirrorImage")){
			baseVO = new BaseVO();
		}
		baseVO.setPageInfo((String)transfersMap.get("pageInfo"));
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		List<Map<String,String>> fid_NameMapList = baseVO.getFid_NameMapList();
		List<Map<String,String>> result_SF = dataBaseDao.executeQuery("SELECT f.name, f.type, f.fup FROM "+tableprefix+"forums AS f WHERE f.fid=?",fid);
		String forumName = "";
		if(result_SF!=null&&result_SF.size()>0){
			Map<String,String> resultMap = result_SF.get(0);
			forumName = resultMap.get("name");
			if(resultMap!=null){
				if("sub".equals(resultMap.get("type"))){
					List<Map<String,String>> result_SF2 = dataBaseDao.executeQuery("SELECT f.name FROM "+tableprefix+"forums AS f WHERE f.fid=?",resultMap.get("fup"));
					if(result_SF2!=null&&result_SF2.size()>0){
						Map<String,String> tempFDB = result_SF2.get(0);
						if(tempFDB!=null){
							Map<String,String> fid_NameMap2 = new HashMap<String, String>();
							fid_NameMap2.put("fid", resultMap.get("fup"));
							fid_NameMap2.put("fName", tempFDB.get("name"));
							fid_NameMapList.add(fid_NameMap2);
						}
					}
				}
			}
		}
		Map<String,String> fid_NameMap = new HashMap<String, String>();
		fid_NameMap.put("fid", fid);
		fid_NameMap.put("fName", forumName);
		fid_NameMapList.add(fid_NameMap);
		if(operation.equals("highlight")||operation.equals("stick")||operation.equals("digest")||operation.equals("close")||operation.equals("move")||operation.equals("delete")){
			Calendar calendar = Common.getCalendar(timeoffsetSession);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd", timeoffsetSession);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			String minTime = dateFormat.format(calendar.getTime());
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			calendar.add(Calendar.MONTH, 6);
			String maxTime = dateFormat.format(calendar.getTime());
			dateFormat = null;
			baseVO.setMaxTime(maxTime);
			baseVO.setMinTime(minTime);
		}
		List<String> reasonList = baseVO.getReasonList();
		String[] modreasonsArray = modreasons.split("\n");
		if(modreasonsArray!=null){
			for(int i = 0;i<modreasonsArray.length;i++){
				reasonList.add(modreasonsArray[i]);
			}
		}
		if(reasonpm.equals("1")){
			baseVO.setNecesseryInfo(true);
		}else if(reasonpm.equals("2")){
			baseVO.setNecessaryToSendMessage(true);
		}else if(reasonpm.equals("3")){
			baseVO.setNecesseryInfo(true);
			baseVO.setNecessaryToSendMessage(true);
		}
		baseVO.setFid(fid);
		baseVO.setForumName(forumName); 
		if(fromWhere==null){
			baseVO.setShowTopicName(true);
			Threads threads = ((ThreadsDao)BeanFactory.getBean("threadsDao")).findByTid(Integer.valueOf(moderates[0]));
			if(threads==null){
				return null;
			}
			baseVO.setTopicName(threads.getSubject()); 
			baseVO.setTopicId(moderates[0]); 
		}
		List<Integer> tidList = new ArrayList<Integer>();
		for(String tid : moderates){
			tidList.add(Integer.valueOf(tid));
		}
		if(tidList.size()>1){ 
			baseVO.setSingleThread(false);
			List<ThreadInfo> threadInfoList = baseVO.getThreadInfoList();
			List<Threads> threadsList = ((ThreadsDao)BeanFactory.getBean("threadsDao")).getThreadsByThreadIdList(tidList);
			for(int i = 0;i<threadsList.size();i++){
				Threads threads = threadsList.get(i);
				String lastPostString = threads.getLastpost()+"000";
				Long lastPostLong = Long.valueOf(lastPostString);
				ThreadInfo threadInfo = baseVO.geThreadInfo();
				threadInfo.setAuthorId(threads.getAuthorid().toString());
				threadInfo.setAuthorName(threads.getAuthor());
				threadInfo.setLastpost(simpleDateFormat.format(lastPostLong));
				threadInfo.setLastPosterName(threads.getLastposter());
				threadInfo.setReplies(threads.getReplies().toString());
				threadInfo.setThreadId(threads.getTid().toString());
				threadInfo.setTitle(threads.getSubject());
				threadInfoList.add(threadInfo);
			}
		}else if(tidList.size()==1){
			baseVO.setSingleThread(true);
			baseVO.setThreadId(moderates[0]);
			String[] tempArray = new String[]{"stick", "digest", "highlight", "close", "recommend"};
			String actionCN = ""; 
			MessageResources mr = (MessageResources)transfersMap.get("messageResources");
			Locale locale = (Locale)transfersMap.get("locale");
			for(String operationInA : tempArray){
				if(operationInA.equals(operation)){
					List<Log> logList = baseVO.getLogList();
					List<Map<String,String>> resultList = dataBaseDao.executeQuery("SELECT * FROM "+tableprefix+"threadsmod WHERE tid=? ORDER BY dateline DESC",moderates[0]);
					for(int j = 0;j<resultList.size();j++){
						Map<String,String> resultMap = resultList.get(j);
						String tempAction = resultMap.get("action");
						Log log = baseVO.getLog();
						String[] actionArray= {"CLS","DIG","HLT","OPN","STK"};
						if(Arrays.binarySearch(actionArray, tempAction)>-1){
							log.setExpiretion(mr.getMessage(locale,"thread_moderations_expiration_unlimit"));
						}else{
							String expirationString = resultMap.get("expiration")+"000";
							Long expirationLong = Long.valueOf(expirationString);
							if(expirationLong>0){
								log.setExpiretion(simpleDateFormat.format(expirationLong));
							}else{
								log.setExpiretion("");
							}
						}
						if(operationInfoMap.get(tempAction)==null){
							List<Map<String,String>> magicInfoML = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"magics WHERE identifier=?",tempAction);
							if(magicInfoML!=null&&magicInfoML.size()>0){
								actionCN = mr.getMessage(locale,"magics_operation_magicsname",magicInfoML.get(0).get("name"));
							}
						}else{
							actionCN = mr.getMessage(locale, tempAction);
						}
						log.setOperation(actionCN);
						String datelineString = resultMap.get("dateline")+"000";
						log.setOperationTime(simpleDateFormat.format(Long.valueOf(datelineString)));
						log.setUid(resultMap.get("uid"));
						log.setUsername(resultMap.get("username"));
						String css = "";
						if(resultMap.get("status").equals("0")){
							css = "style=\"text-decoration: line-through\" disabled";
						}
						log.setCss(css);
						logList.add(log);
					}
					break;
				}
			}
			baseVO.setShowLogList(baseVO.getLogList().size()>0);
		}
		return baseVO;
	}
	private HighLightVO getHighLightVO(Map<String,Object> transfersMap){
		HighLightVO highLightVO = new HighLightVO();
		String[] moderates = (String[])transfersMap.get("moderates");
		if(moderates==null||moderates.length!=1){
			return highLightVO;
		}
		String sql = "SELECT highlight FROM "+tableprefix+"threads WHERE tid=?";
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		List<Map<String,String>> result = dataBaseDao.executeQuery(sql,moderates[0]);
		if(result!=null&&result.size()>0){
			Map<String,String> resultMap = result.get(0);
			int highlight = Integer.parseInt(resultMap.get("highlight"));
			int highlightTemp = highlight/10;
			highLightVO.setBchecked((highlightTemp&4)>0);
			highLightVO.setIchecked((highlightTemp&2)>0);
			highLightVO.setUchecked((highlightTemp&1)>0);
			highLightVO.setHighlight_color(highlight%10);
		}
		return highLightVO;
	}
	private TopicAdmin_MoveVO geFinalTopicAdmin_MoveVO(short groupid,Members member){
		TopicAdmin_MoveVO topicAdmin_MoveVO = new TopicAdmin_MoveVO();
		topicAdmin_MoveVO.setSelectContent(Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",null));
		return topicAdmin_MoveVO;
	}
	private CloseOrOpenTopicVO getCloseOrOpenTopicVO(String[] moderates){
		CloseOrOpenTopicVO closeOrOpenTopicVO = new CloseOrOpenTopicVO();
		if(moderates.length!=1){
			closeOrOpenTopicVO.setClose("2");
		}else{
			closeOrOpenTopicVO.setClose(((ThreadsDao)BeanFactory.getBean("threadsDao")).findByTid(Integer.valueOf(moderates[0])).getClosed().toString());
		}
		return closeOrOpenTopicVO;
	}
	private ToTopAndEliteVO geToTopAndEliteVO(Map<String,Object> transfersMap){
		String[] moderates = (String[])transfersMap.get("moderates");
		String operation = (String)transfersMap.get("operation");
		ToTopAndEliteVO toTopAndEliteVO = new ToTopAndEliteVO();
		if(operation.equals("stick")){
			Integer allowstickthread = Integer.valueOf((String)transfersMap.get("allowstickthread"));
			toTopAndEliteVO.setStickPurview(allowstickthread);
		}
		boolean showUnchain = false;
		if(moderates.length==1){
			Threads threads = ((ThreadsDao)BeanFactory.getBean("threadsDao")).findByTid(Integer.valueOf(moderates[0]));
			if(operation.equals("stick")){
				if(threads.getDisplayorder()!=0){
					showUnchain = true;
					toTopAndEliteVO.setLevel(threads.getDisplayorder().toString());
				}else{
					toTopAndEliteVO.setLevel("1");
				}
			}else{
				if(threads.getDigest()!=0){
					showUnchain = true;
					toTopAndEliteVO.setLevel(threads.getDigest().toString());
				}else{
					toTopAndEliteVO.setLevel("1");
				}
			}
			toTopAndEliteVO.setShowUnchain(showUnchain);
		}else{
			List<Integer> tidList = new ArrayList<Integer>();
			for(String moderate : moderates){
				tidList.add(Integer.valueOf(moderate));
			}
			List<Threads> threadsList = ((ThreadsDao)BeanFactory.getBean("threadsDao")).getThreadsByThreadIdList(tidList);
			for (Threads threads : threadsList) {
				if (operation.equals("stick")) {
					if (threads.getDisplayorder() != 0) {
						showUnchain = true;
						break;
					}
				}else{
					if (threads.getDigest() != 0) {
						showUnchain = true;
						break;
					}
				}
			}
			toTopAndEliteVO.setLevel("");
			toTopAndEliteVO.setShowUnchain(showUnchain);			
		}
		return toTopAndEliteVO;
	}
	public String removereward(Threads currentThread,Map<String,String> settingsMap,int currentUid){
		int tid = currentThread.getTid();
		int price = Math.abs(currentThread.getPrice());
		if(currentThread.getSpecial()!=3 || currentThread.getPrice()>=0){
			return "reward_end";
		}
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		List<Map<String,String>> sRewardlogResult = dataBaseDao.executeQuery("SELECT authorid, answererid FROM "+tableprefix+"rewardlog WHERE tid="+tid);
		String authorid = null;
		String answererid = null;
		if(sRewardlogResult != null){
			for(Map<String,String> tempMap : sRewardlogResult){
				if(tempMap!=null&&!tempMap.get("authorid").equals("0")){
					authorid = tempMap.get("authorid");
					answererid = tempMap.get("answererid");
				}
			}
		}
		String creditstrans = settingsMap.get("creditstrans");
		if(authorid!=null){
			dataBaseDao.execute("UPDATE "+tableprefix+"members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"+"+price+" WHERE uid='"+authorid+"'");
			Common.updatepostcredits(Integer.valueOf(authorid), settingsMap.get("creditsformula"));
		}
		if(answererid!=null&&!answererid.equals("0")){
			dataBaseDao.execute("UPDATE "+tableprefix+"members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"-"+price+" WHERE uid='"+answererid+"'");
			Common.updatepostcredits(Integer.valueOf(answererid), settingsMap.get("creditsformula"));
		}
		dataBaseDao.execute("UPDATE "+tableprefix+"threads SET special='0', price='0' WHERE tid='"+tid+"'");
		dataBaseDao.execute("DELETE FROM "+tableprefix+"rewardlog WHERE tid='"+tid+"'");
		return null;
	}
	public void repairTopic(Integer topicId){
		PostsDao postsDao = ((PostsDao)BeanFactory.getBean("postsDao"));
		Integer replies = postsDao.getCountOfReplyForTopic(topicId);
		if(replies==-1){
			return ;
		}
		byte attachment = 0;
		List<Map<String,String>> resultList = ((DataBaseDao)BeanFactory.getBean("dataBaseDao")).executeQuery("SELECT a.aid FROM "+tableprefix+"posts p, "+tableprefix+"attachments a WHERE a.tid="+topicId+" AND a.pid=p.pid AND p.invisible='0' LIMIT 1");
		if(resultList!=null&&resultList.size()>0&&resultList.get(0).get("aid")!=null){
			attachment = 1;
		}
		resultList = null;
		Posts firstPosts = postsDao.getFirstPosts(topicId);
		String subject = firstPosts.getSubject();
		if(subject.length()>80){
			subject = subject.substring(0,80);
		}
		firstPosts.setFirst((byte)1);
		firstPosts.setSubject(subject);
		Short rate = firstPosts.getRate();
		if(rate!=0){
			rate = Integer.valueOf((rate/Math.abs(rate.intValue()))).shortValue();
		}
		Posts lastPosts = postsDao.getLastPosts(topicId);
		ThreadsDao threadsDao = ((ThreadsDao)BeanFactory.getBean("threadsDao"));
		Threads threads = threadsDao.findByTid(topicId);
		threads.setSubject(subject);
		subject = null;
		threads.setReplies(replies);
		replies = null;
		threads.setLastpost(lastPosts.getDateline());
		threads.setLastposter(lastPosts.getAuthor());
		lastPosts = null;
		threads.setRate(rate.byteValue());
		rate = null;
		threads.setAttachment(attachment);
		threadsDao.updateThreads(threads);
		threads = null;
		threadsDao = null;
		postsDao.updatePosts(firstPosts);
		postsDao.updatePostsByHQL("UPDATE Posts SET first=0 WHERE tid="+topicId+" AND pid<>"+firstPosts.getPid());
		postsDao = null;
		firstPosts = null;
	}
	private TopicClassVO geTopicClassVO(String fid){
		TopicClassVO topicClassVO = new TopicClassVO();
		Map<Integer,String> topicClassMap = topicClassVO.getTopicClassMap();
		DataParse dataParse = new DataParse();
		Forumfields forumfields = ((ForumfieldsDao)BeanFactory.getBean("forumfieldsDao")).findById(Short.valueOf(fid));
		String threadTypes = forumfields.getThreadtypes();
		Map resultMap = dataParse.characterParse(threadTypes, false);
		if(resultMap!=null&&resultMap.size()>0){
			Map<Integer,String> specialMap = (Map<Integer,String>)resultMap.get("special");
			Map<Integer,String> typesMap = (Map<Integer,String>)resultMap.get("types");
			Iterator<Entry<Integer,String>> specialMapKeys = specialMap.entrySet().iterator();
			while(specialMapKeys.hasNext()){
				Entry<Integer,String> e = specialMapKeys.next();
				Integer key = e.getKey();
				String temp = e.getValue();
				if(temp.equals("0")){
					topicClassMap.put(key, typesMap.get(key));
				}
			}
		}
		return topicClassVO;
	}
	public TopicPublicVO geTopicMergeVO(Map<String,Object> transfersMap){
		Threads currentThread = (Threads)transfersMap.get("currentThread");
		Forums currentForum = (Forums)transfersMap.get("currentforum");
		String reasonpm = (String)transfersMap.get("reasonpm");
		String[] modreasonsArray = ((String)transfersMap.get("modreasons")).split("\r\n");
		TopicPublicVO topicMergeVO = new TopicPublicVO();
		setTopicPublicVO(topicMergeVO, currentForum, currentThread, reasonpm, modreasonsArray);
		return topicMergeVO;
	}
	public String operatingMerge(Map<String,Object> transfersMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		Integer fid = (Integer)transfersMap.get("fid");
		Threads operatingThread = (Threads)transfersMap.get("operatingThread");
		Threads targetThread = (Threads)transfersMap.get("targetThread");
		Members currentMember = (Members)transfersMap.get("currentMember");
		String targetThreadName = targetThread.getSubject();
		Integer targetTid = targetThread.getTid();
		String operatingThreadName = operatingThread.getSubject();
		Integer opseratingTid = operatingThread.getTid();
		PostsDao postsDao = ((PostsDao)BeanFactory.getBean("postsDao"));
		String operationE = "MRG";
		int timestamp = Common.time();
		List<Posts> postsList = postsDao.getPostsListByTid(targetThread.getTid());
		for(Posts posts : postsList){
			posts.setFid(operatingThread.getFid());
			posts.setTid(operatingThread.getTid());
		}
		postsDao.updatePosts(postsList);
		AttachmentsDao attachmentsDao = ((AttachmentsDao)BeanFactory.getBean("attachmentsDao"));
		List<Attachments> attachmentList = attachmentsDao.getAttachmentListByTid(targetThread.getTid());
		for(Attachments attachments : attachmentList){
			attachments.setTid(operatingThread.getTid());
		}
		attachmentsDao.updateAttachment(attachmentList);
		((ThreadsDao)BeanFactory.getBean("threadsDao")).deleteThreads(targetThread);
		((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).deleteThreadsmod(targetThread.getTid());
		((MythreadDao)BeanFactory.getBean("mythreadDao")).deleteMythread(targetThread.getTid());
		((MypostsDao)BeanFactory.getBean("mypostDao")).deleteMyposts(targetThread.getTid());
		List<Posts> postList = postsDao.getPostsListByTid(operatingThread.getTid());
		List<Posts> firstPostsList = new ArrayList<Posts>();
		for(Posts posts : postList){
			if(posts.getFirst()==1){
				firstPostsList.add(posts);
			}
			posts.setFirst((byte)0);
		}
		postsDao.updatePosts(postList);
		Posts firstPost = postsDao.getFirstPosts(operatingThread.getTid());
		firstPost.setFirst((byte)1);
		for(Posts tempPosts : firstPostsList){
			if(tempPosts.getPid().intValue()!=firstPost.getPid().intValue()){
				tempPosts.setSubject(tempPosts.getSubject()+mr.getMessage(locale,"admin_thread_hebing","     ",currentMember.getUsername()));
				postsDao.updatePosts(tempPosts);
			}
		}
		postsDao.updatePosts(firstPost);
		operatingThread.setAuthor(firstPost.getAuthor());
		operatingThread.setAuthorid(firstPost.getAuthorid());
		operatingThread.setSubject(firstPost.getSubject());
		operatingThread.setDateline(firstPost.getDateline());
		operatingThread.setViews(operatingThread.getViews()+targetThread.getViews());
		operatingThread.setReplies(operatingThread.getReplies()+targetThread.getReplies()+1);
		operatingThread.setModerated((byte)1);
		((ThreadsDao)BeanFactory.getBean("threadsDao")).updateThreads(operatingThread);
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		if(fid==targetThread.getFid().shortValue()){
			dataBaseDao.execute("UPDATE "+tableprefix+"forums SET threads=threads-1 WHERE fid='"+fid+"'");
		}else{
			dataBaseDao.execute("UPDATE "+tableprefix+"forums SET threads=threads-1, posts=posts-"+postsList.size()+" WHERE fid='"+targetThread.getFid()+"'");
			dataBaseDao.execute("UPDATE "+tableprefix+"forums SET posts=posts+"+postsList.size()+" WHERE fid='"+fid+"'");
		}
		List<Map<String,String>> tempML = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid='"+fid+"'");
		String currentForumsName = tempML!=null && tempML.size()>0 ? tempML.get(0).get("name") : "";
		transfersMap.put("username", currentMember.getUsername());
		transfersMap.put("adminId", currentMember.getAdminid());
		transfersMap.put("forumName", currentForumsName);
		transfersMap.put("uid",currentMember.getUid());
		writeLog(transfersMap,currentForumsName, operatingThreadName,opseratingTid+"", operationE,timestamp);
		List<Threads> threadsList = new ArrayList<Threads>();
		threadsList.add(operatingThread);
		sendMessageToAuthor(transfersMap,currentForumsName, (String)transfersMap.get("url"), operationE, threadsList,operationInfoMap,mr,locale);
		Forums targetForums = ((ForumsDao)BeanFactory.getBean("forumsDao")).findById(targetThread.getFid());
		transfersMap.put("fid", targetForums.getFid());
		transfersMap.put("forumName", targetForums.getName());
		if(operatingThread.getAuthorid().byteValue()!=targetThread.getAuthorid().byteValue()){
			List<Threads> threadsList2 = new ArrayList<Threads>();
			threadsList2.add(targetThread);
			sendMessageToAuthor(transfersMap,currentForumsName, (String)transfersMap.get("url"), operationE, threadsList2,operationInfoMap,mr,locale);
		}
		writeLog(transfersMap,currentForumsName, targetThreadName,targetTid+"", operationE,timestamp);
		Common.updatemodworks((Map<String,String>)transfersMap.get("settingMap"), currentMember.getUid(), timestamp, operationE, (short)1);
		Threadsmod threadsmod = new Threadsmod();
		ThreadsmodId threadsmodId = new ThreadsmodId();
		threadsmodId.setAction(operationE);
		threadsmodId.setDateline(timestamp);
		threadsmodId.setExpiration(0);
		threadsmodId.setMagicid((short)0);
		threadsmodId.setStatus((byte)1);
		threadsmodId.setTid(operatingThread.getTid());
		threadsmodId.setUid(currentMember.getUid());
		threadsmodId.setUsername(currentMember.getUsername());
		threadsmod.setId(threadsmodId);
		List<Threadsmod> threadsmodList = new ArrayList<Threadsmod>();
		threadsmodList.add(threadsmod);
		((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).saveList(threadsmodList);
		postsDao = null;
		attachmentsDao = null;
		return null;
	}
	public TopicSplitVO geTopicSplitVO(Map<String,Object> transfersMap){
		Threads currentThread = (Threads)transfersMap.get("currentThread");
		Forums currentForum = (Forums)transfersMap.get("currentforum");
		String reasonpm = (String)transfersMap.get("reasonpm");
		String[] modreasonsArray = ((String)transfersMap.get("modreasons")).split("\r\n");
		List<Posts> postsList = (List<Posts>)transfersMap.get("postsList");
		TopicSplitVO topicSplitVO = new TopicSplitVO();
		setTopicPublicVO(topicSplitVO, currentForum, currentThread, reasonpm, modreasonsArray);
		List<PostsInfo> postsInfoList = topicSplitVO.getPostsInfoList();
		PostsInfo postsInfo = null;
		for(Posts posts : postsList){
			postsInfo = topicSplitVO.getPostsInfo();
			postsInfo.setAuthor(posts.getAuthor());
			postsInfo.setContent(posts.getMessage());
			postsInfo.setPid(posts.getPid().toString());
			postsInfoList.add(postsInfo);
		}
		postsInfo = null;
		return topicSplitVO;
	}
	public String operatingSplit(Map<String,Object> transfersMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String fid = (String)transfersMap.get("fid");
		Threads passiveThread = (Threads)transfersMap.get("passiveThread");
		List<Posts> passivePostsList = (List<Posts>)transfersMap.get("postsList");
		String subject = (String)transfersMap.get("subject");
		Members currentMember = (Members)transfersMap.get("currentMember");
		String operationE = "SPL";
		Threads newThreads = new Threads();
		newThreads.setSubject(subject);
		newThreads.setFid(Short.valueOf(fid));
		((ThreadsDao)BeanFactory.getBean("threadsDao")).addThread(newThreads);
		newThreads = ((ThreadsDao)BeanFactory.getBean("threadsDao")).findByTid(newThreads.getTid());
		PostsDao postsDao = ((PostsDao)BeanFactory.getBean("postsDao"));
		StringBuffer postsIdBuffer = new StringBuffer();
		int newThreadsId = newThreads.getTid();
		for(Posts passivePosts : passivePostsList){
			postsIdBuffer.append(passivePosts.getPid()+",");
		}
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		int postsIdBufferLen = postsIdBuffer.length();
		boolean newThreadExistAtt = false;
		boolean passiveThreadExistAtt = false;
		if(postsIdBufferLen>0){
			postsIdBuffer = postsIdBuffer.delete(postsIdBufferLen-1, postsIdBufferLen);
			StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"posts SET tid="+newThreadsId+" WHERE pid IN(");
			sqlBuffer.append(postsIdBuffer);
			sqlBuffer.append(")");
			dataBaseDao.execute(sqlBuffer.toString());
			sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"attachments SET tid="+newThreadsId+" WHERE pid IN(");
			sqlBuffer.append(postsIdBuffer);
			sqlBuffer.append(")");
			dataBaseDao.execute(sqlBuffer.toString());
			sqlBuffer = new StringBuffer("SELECT COUNT(*) AS tc FROM jrun_posts WHERE pid IN(");
			sqlBuffer.append(postsIdBuffer);
			sqlBuffer.append(") AND attachment>0");
			List<Map<String,String>> tempML = dataBaseDao.executeQuery(sqlBuffer.toString());
			if( tempML!=null && tempML.size() >0){
				String tc = tempML.get(0).get("tc");
				if(tc != null && !tc.equals("") && Integer.parseInt(tc) >0){
					newThreadExistAtt = true;
				}
			}
			tempML = dataBaseDao.executeQuery("SELECT COUNT(*) AS tc FROM jrun_posts WHERE tid='"+passiveThread.getTid()+"' AND attachment>0");
			if( tempML!=null && tempML.size() >0){
				String tc = tempML.get(0).get("tc");
				if(tc != null && !tc.equals("") && Integer.parseInt(tc) >0){
					passiveThreadExistAtt = true;
				}
			}
		}
		Posts newThreadFirstPosts = postsDao.getFirstPosts(newThreads.getTid());
		newThreadFirstPosts.setFirst((byte)1);
		newThreadFirstPosts.setSubject(subject);
		postsDao.updatePosts(newThreadFirstPosts);
		Posts oldThreadFirstPosts = postsDao.getFirstPosts(passiveThread.getTid());
		passiveThread.setAuthor(oldThreadFirstPosts.getAuthor());
		passiveThread.setAuthorid(oldThreadFirstPosts.getAuthorid());
		passiveThread.setDateline(oldThreadFirstPosts.getDateline());
		passiveThread.setModerated((byte)1);
		passiveThread.setReplies(passiveThread.getReplies()-passivePostsList.size());
		((ThreadsDao)BeanFactory.getBean("threadsDao")).updateThreads(passiveThread);
		oldThreadFirstPosts.setSubject(passiveThread.getSubject());
		oldThreadFirstPosts.setFirst((byte)1);
		postsDao.updatePosts(oldThreadFirstPosts);
		Posts newThreadLastPosts = postsDao.getLastPosts(newThreads.getTid());
		newThreads.setAuthor(newThreadFirstPosts.getAuthor());
		newThreads.setAuthorid(newThreadFirstPosts.getAuthorid());
		newThreads.setDateline(newThreadFirstPosts.getDateline());
		newThreads.setRate(newThreadFirstPosts.getRate()==0?(byte)0:(byte)(newThreadFirstPosts.getRate()/Math.abs(newThreadFirstPosts.getRate())));
		newThreads.setModerated((byte)1);
		newThreads.setLastpost(newThreadLastPosts.getDateline());
		newThreads.setLastposter(newThreadLastPosts.getAuthor());
		newThreads.setReplies(passivePostsList.size()-1);
		if(newThreadExistAtt){
			newThreads.setAttachment((byte)1);
		}
		((ThreadsDao)BeanFactory.getBean("threadsDao")).updateThreads(newThreads);
		if(postsIdBufferLen>0 && !passiveThreadExistAtt){
			passiveThread.setAttachment((byte)0);
			((ThreadsDao)BeanFactory.getBean("threadsDao")).updateThreads(passiveThread);
		}
		updateForumCount(fid, dataBaseDao,mr,locale);
		List<Map<String,String>> tempML = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid=?",fid);
		String currentForumsName = tempML!=null && tempML.size()>0 ? tempML.get(0).get("name") : "";
		transfersMap.put("username", currentMember.getUsername());
		transfersMap.put("adminId", currentMember.getAdminid());
		transfersMap.put("uid", currentMember.getUid());
		transfersMap.put("forumName", currentForumsName);
		int timestamp = Common.time();
		writeLog(transfersMap,currentForumsName, newThreads.getSubject(),newThreads.getTid()+"", operationE,timestamp);
		writeLog(transfersMap,currentForumsName, passiveThread.getSubject(),passiveThread.getTid()+"", operationE,timestamp);
		List<Threads> theradsList = new ArrayList<Threads>();
		theradsList.add(passiveThread);
		sendMessageToAuthor(transfersMap,currentForumsName, (String)transfersMap.get("url"), operationE, theradsList,operationInfoMap,mr,locale);
		Common.updatemodworks((Map<String,String>)transfersMap.get("settingMap"), currentMember.getUid(), timestamp, operationE, (short)1);
		Threadsmod threadsmod = new Threadsmod();
		ThreadsmodId threadsmodId = new ThreadsmodId();
		threadsmodId.setAction(operationE);
		threadsmodId.setDateline(timestamp);
		threadsmodId.setExpiration(0);
		threadsmodId.setMagicid((short)0);
		threadsmodId.setStatus((byte)1);
		threadsmodId.setTid(passiveThread.getTid());
		threadsmodId.setUid(currentMember.getUid());
		threadsmodId.setUsername(currentMember.getUsername());
		threadsmod.setId(threadsmodId);
		List<Threadsmod> threadsmodList = new ArrayList<Threadsmod>();
		Threadsmod threadsmod2 = new Threadsmod();
		ThreadsmodId threadsmodId2 = new ThreadsmodId();
		threadsmodId2.setAction(operationE);
		threadsmodId2.setDateline(timestamp);
		threadsmodId2.setExpiration(0);
		threadsmodId2.setMagicid((short)0);
		threadsmodId2.setStatus((byte)1);
		threadsmodId2.setTid(newThreads.getTid());
		threadsmodId2.setUid(currentMember.getUid());
		threadsmodId2.setUsername(currentMember.getUsername());
		threadsmod2.setId(threadsmodId2);
		threadsmodList.add(threadsmod);
		threadsmodList.add(threadsmod2);
		((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).saveList(threadsmodList);
		postsDao = null;
		return null;
	}
	public TopicCopyVO geTopicCopyVO(Map<String,Object> parameterMap){
		String reasonpm = (String)parameterMap.get("reasonpm");
		Short fid = Short.valueOf((String)parameterMap.get("fid"));
		String tid = (String)parameterMap.get("tid");
		String[] modreasonsArray = ((String)parameterMap.get("modreasons")).split("\r\n");
		Forums forums = ((ForumsDao)BeanFactory.getBean("forumsDao")).findById(fid);
		Threads threads = ((ThreadsDao)BeanFactory.getBean("threadsDao")).findByTid(Integer.valueOf(tid));
		TopicCopyVO topicCopyVO = new TopicCopyVO();
		setTopicPublicVO(topicCopyVO, forums, threads, reasonpm, modreasonsArray);
		short groupid = (Short)parameterMap.get("groupid");
		Members member = (Members)parameterMap.get("member");
		topicCopyVO.setSelectContent(Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",null));
		return topicCopyVO;
	}
	public String operatingCopy(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String operationE = "CPY";
		Threads threads_old = (Threads)parameterMap.get("threads");
		Forums targetForums = (Forums)parameterMap.get("targetForums");
		Integer timestamp = Common.time();
		String currentForumName;
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		String fid = (String)parameterMap.get("fid");
		List<Map<String,String>> tempML = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid=?",fid);
		if(tempML != null && tempML.size() > 0){
			currentForumName = tempML.get(0).get("name");
		}else{
			return "forum_nonexistence_2";
		}
		Threads threads_new = new Threads();
		threads_new.setFid(targetForums.getFid());
		threads_new.setDateline(timestamp);
		threads_new.setLastpost(timestamp);
		threads_new.setLastposter(threads_old.getAuthor());
		threads_new.setViews(0);
		threads_new.setReplies(0);
		threads_new.setDisplayorder((byte)0);
		threads_new.setDigest((byte)0);
		threads_new.setAttachment(threads_old.getAttachment());
		threads_new.setAuthor(threads_old.getAuthor());
		threads_new.setAuthorid(threads_old.getAuthorid());
		threads_new.setBlog(threads_old.getBlog());
		threads_new.setClosed(threads_old.getClosed());
		threads_new.setHighlight(threads_old.getHighlight());
		threads_new.setIconid(threads_old.getIconid());
		threads_new.setTypeid(threads_old.getTypeid());
		threads_new.setSubscribed(threads_old.getSubscribed());
		threads_new.setSubject(threads_old.getSubject());
		threads_new.setSpecial(threads_old.getSpecial());
		threads_new.setReadperm(threads_old.getReadperm());
		threads_new.setRate(threads_old.getRate());
		threads_new.setPrice(threads_old.getPrice());
		threads_new.setModerated(threads_old.getModerated());
		((ThreadsDao)BeanFactory.getBean("threadsDao")).addThread(threads_new);
		PostsDao postsDao = ((PostsDao)BeanFactory.getBean("postsDao"));
		Posts posts_old = postsDao.findByTid(threads_old.getTid());
		posts_old.setPid(null);
		posts_old.setFid(targetForums.getFid());
		posts_old.setTid(threads_new.getTid());
		posts_old.setDateline(timestamp);
		postsDao.saveOrupdatePosts(posts_old);
		updateForumCount(targetForums.getFid(), dataBaseDao,mr,locale);
		writeLog(parameterMap,currentForumName, threads_old.getSubject(),threads_old.getTid()+"", operationE,timestamp);
		List<Threads> threadsList = new ArrayList<Threads>();
		threadsList.add(threads_old);
		sendMessageToAuthor(parameterMap,currentForumName, (String)parameterMap.get("url"), operationE, threadsList,operationInfoMap,mr,locale);
		Integer currentUserId = (Integer)parameterMap.get("uid");
		Common.updatemodworks((Map<String,String>)parameterMap.get("settingMap"), currentUserId, timestamp, operationE, (short)1);
		Map<String,String> updateField = new HashMap<String, String>();
		updateField.put("posts", "posts");
		Members members = ((MembersDao)BeanFactory.getBean("memberDao")).findMemberById(currentUserId);
		members.setPosts(members.getPosts()+1);
		PostOperating postOperating = (PostOperating)BeanFactory.getBean("postOperating");
		postOperating.setCredits((String)parameterMap.get("creditsformula"), members,updateField);
		((MembersDao)BeanFactory.getBean("memberDao")).modifyMember(members);
		postsDao = null;
		return null;
	}
	public String operatingBanPost(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String banned = (String)parameterMap.get("banned");
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		Short fid = (Short)parameterMap.get("fid");
		String currentForumName = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid="+fid).get(0).get("name");
		String operationE = null;
		if(banned.equals("1")){
			operationE = "BNP";
		}else{
			operationE = "UBN";
		}
		List<Posts> postsList = (List<Posts>)parameterMap.get("postsList");
		Threads threads = (Threads)parameterMap.get("threads");
		PostsDao postsDao = ((PostsDao)BeanFactory.getBean("postsDao"));
		int timestamp = Common.time();
		for(Posts posts : postsList){
			posts.setStatus(Byte.valueOf(banned));
			postsDao.updatePosts(posts);
			writeLog(parameterMap,currentForumName, threads.getSubject(),threads.getTid()+"", operationE,timestamp);
		}
		postsDao = null;
		sendMessageToAuthor_(parameterMap,currentForumName, (String)parameterMap.get("url"), operationE, postsList,operationInfoMap,mr,locale);
		return null;
	}
	public String operatingDelPost(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String operationE = "DLP";
		Integer losslessdel = Integer.valueOf((String)parameterMap.get("losslessdel"));
		int nowTime = (int)(System.currentTimeMillis()/1000);
		int nowSubtrationLoss = nowTime - losslessdel*24*3600;
		PostsDao postsDao = ((PostsDao)BeanFactory.getBean("postsDao"));
		String[] postIdArray = (String[])parameterMap.get("postIdArray");
		List<Integer> postsIdList = new ArrayList<Integer>();
		for(int i = 0;i<postIdArray.length;i++){
			postsIdList.add(Integer.valueOf(postIdArray[i]));
		}
		List<Posts> tempList = postsDao.getPostsListByPidList(postsIdList);
		postsDao.deletePosts(postsIdList);
		Threads threads = (Threads)parameterMap.get("threads");
		Integer threadRep = threads.getReplies()-postsIdList.size();
		threads.setReplies(threadRep<0?0:threadRep);
		Posts posts = postsDao.getLastPosts(threads.getTid());
		if(posts!=null){
			threads.setLastpost(posts.getDateline());
			threads.setLastposter(posts.getAuthor());
		}
		((ThreadsDao)BeanFactory.getBean("threadsDao")).updateThreads(threads);
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		updateForumCount(threads.getFid(), dataBaseDao,mr,locale);
		Map<String, String> updateField = new HashMap<String, String>();
		updateField.put("posts", "posts");
		String attachdir_realy = (String)parameterMap.get("attachdir_realy");
		PostOperating postOperating = (PostOperating)BeanFactory.getBean("postOperating");
		for(Posts posts_old : tempList){
			Integer postsDateline = posts_old.getDateline();
			boolean updateCredit = false;
			if(losslessdel==0||nowSubtrationLoss<postsDateline*1000L){
				updateCredit = true;
			}
			Forumfields forumfields = ((ForumfieldsDao)BeanFactory.getBean("forumfieldsDao")).findById((Short) parameterMap.get("fid"));
			Members members_reply = ((MembersDao)BeanFactory.getBean("memberDao")).findMemberById(posts_old.getAuthorid());
			if(members_reply!=null){
				Integer memberReply = members_reply.getPosts() - 1;
				members_reply.setPosts(memberReply<0?0:memberReply);
				if(updateCredit){
					postOperating.setMembersExtcredit(forumfields, (String) parameterMap.get("creditspolicy"), members_reply, updateField, postOperating.reply,false,1);
				}
				List<Ratelog> ratelogList = ((RatelogDao)BeanFactory.getBean("ratelogDao")).getRatelogByPid(posts_old.getPid());
				if (ratelogList != null) {
					for (int j = 0; j < ratelogList.size(); j++) {
						Ratelog ratelog = ratelogList.get(j);
						RatelogId ratelogId = ratelog.getId();
						Byte extcredit = ratelogId.getExtcredits();
						Short score = ratelogId.getScore();
						if(score>0){
							postOperating.setMembersExtcredit(extcredit+"", members_reply, score.intValue(), false);
						}
						updateField.put("extcredits" + extcredit, "extcredits" + extcredit);
					}
				}
				operatingAttachment(posts_old.getPid(), attachdir_realy, forumfields,parameterMap, members_reply, updateField,true,updateCredit);
				postOperating.setCredits((String) parameterMap.get("creditsformula"),members_reply, updateField);
				((MembersDao)BeanFactory.getBean("memberDao")).modifyMember(members_reply);
			}
		}
		List<Map<String,String>> tempML = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid="+threads.getFid());
		String currentForumName = tempML!=null&&tempML.size()>0?tempML.get(0).get("name"):"";
		int timestamp = Common.time();
		writeLog(parameterMap,currentForumName, threads.getSubject(),threads.getTid()+"", operationE,timestamp);
		sendMessageToAuthor_(parameterMap,currentForumName, (String)parameterMap.get("url"), operationE, tempList,operationInfoMap,mr,locale);
		deleteRatelog(postsIdList);
		deleteMyposts(postsIdList);
		deleteAttachments(postsIdList);
		if(threads.getSpecial()!=0){
			deleteTrades(postsIdList);
		}
		Common.updatemodworks((Map<String,String>)parameterMap.get("settingMap"), (Integer)parameterMap.get("uid"), timestamp, operationE, (short)tempList.size());
		postsDao = null;
		return null;
	}
	public String operatingRefund(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String modaction = "RFD";
		int totalamount = 0;
		Threads threads = (Threads)parameterMap.get("threads");
		Map<String,List<String>> amount_uidListMap = new HashMap<String, List<String>>();
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		List<Map<String,String>> tempMapList = dataBaseDao.executeQuery("SELECT * FROM jrun_paymentlog WHERE tid='"+threads.getTid()+"'");
		if(tempMapList == null || tempMapList.size()<1){
			return null;
		}
		String amount;
		for(Map<String,String> tempMap : tempMapList){
			amount = tempMap.get("amount");
			totalamount += Integer.parseInt(amount);
			List<String> uidList = amount_uidListMap.get(amount);
			if(uidList == null){
				uidList = new ArrayList<String>();
				uidList.add(tempMap.get("uid"));
				amount_uidListMap.put(amount, uidList);
			}else{
				uidList.add(tempMap.get("uid"));
			}
		}
		Map<String,String> settingMap = (Map<String,String>)parameterMap.get("settingMap");
		String creditstrans = settingMap.get("creditstrans");
		String extcreditsC = "extcredits"+creditstrans;
		dataBaseDao.execute("UPDATE jrun_members SET "+extcreditsC+"="+extcreditsC+"-"+totalamount+" WHERE uid='"+threads.getAuthorid()+"'");
		dataBaseDao.execute("UPDATE jrun_threads SET price='-1', moderated='1' WHERE tid='"+threads.getTid()+"'");
		for(Entry<String,List<String>> entry : amount_uidListMap.entrySet()){
			amount = entry.getKey();
			String uids = getuids(entry.getValue());
			dataBaseDao.execute("UPDATE jrun_members SET "+extcreditsC+"="+extcreditsC+"+"+amount+" WHERE uid IN ("+uids+")");
		}
		dataBaseDao.execute("UPDATE jrun_paymentlog SET amount='0', netamount='0' WHERE tid='"+threads.getTid()+"'");
		List<Map<String,String>> tempML = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid="+threads.getFid());
		String currentForumName = tempML!=null&&tempML.size()>0?tempML.get(0).get("name"):"";
		int timestamp = Common.time();
		writeLog(parameterMap,currentForumName, threads.getSubject(),threads.getTid()+"", modaction,timestamp);
		List<Threads> tempList = new ArrayList<Threads>();
		tempList.add(threads);
		sendMessageToAuthor(parameterMap,currentForumName, (String)parameterMap.get("url"), modaction, tempList,operationInfoMap,mr,locale);
		Common.updatemodworks(settingMap, (Integer)parameterMap.get("uid"), timestamp, modaction, 1);
		return null;
	}
	private String getuids(List<String> uidArray){
		StringBuilder builder = new StringBuilder();
		for(String uid : uidArray){
			builder.append(uid+",");
		}
		return builder.substring(0, builder.length()-1);
	}
	public TopicRefundVO goTopicRefund(Threads currentThread,String fid,Map<String,String> settingsMap,Map<String,String> usergroupMap){
		String reasonpm = usergroupMap.get("reasonpm");
		String modreasons = settingsMap.get("modreasons");
		String[] modreasonsArray = modreasons.split("\r\n");
		Forums currentForum = ((ForumsDao)BeanFactory.getBean("forumsDao")).findById(Short.valueOf(fid));
		TopicRefundVO topicRefundVO = new TopicRefundVO();
		setTopicPublicVO(topicRefundVO, currentForum, currentThread, reasonpm, modreasonsArray);
		String extcreditsFromMap = settingsMap.get("extcredits");
		String creditstrans = settingsMap.get("creditstrans");
		Map<Integer,Map> tempMap1 = ((DataParse) BeanFactory.getBean("dataParse")).characterParse(extcreditsFromMap,  true);
		Map tempMap2 = tempMap1.get(Integer.valueOf(creditstrans));
		topicRefundVO.setCreditstransTitle((String)(tempMap2.get("title")));
		topicRefundVO.setCreditstransUnit((String)(tempMap2.get("unit")));
		List<Map<String,String>> tempML = ((DataBaseDao)BeanFactory.getBean("dataBaseDao")).executeQuery("SELECT COUNT(*) AS payers, SUM(netamount) AS netincome FROM jrun_paymentlog WHERE tid='"+currentThread.getTid()+"'");
		if(tempML != null && tempML.size() > 0){
			Map<String,String> tempM = tempML.get(0);
			topicRefundVO.setPayersCount(tempM.get("payers"));
			String tempS = tempM.get("netamount");
			topicRefundVO.setNetincome(tempS == null || tempS.equals("") ? "0" : tempS);
		}else{
			topicRefundVO.setPayersCount("0");
			topicRefundVO.setNetincome("0");
		}
		return topicRefundVO;
	}
	private void setTopicPublicVO(TopicPublicVO topicPublicVO,Forums currentForum,Threads currentThread,String reasonpm,String[] modreasonsArray){
		if(currentForum.getType().equals("sub")){
			Forums superForums = ((ForumsDao)BeanFactory.getBean("forumsDao")).findById(currentForum.getFup());
			topicPublicVO.setBeingSupperForum(true);
			topicPublicVO.setSupperFid(superForums.getFid().toString());
			topicPublicVO.setSupperForumName(superForums.getName());
		}
		topicPublicVO.setFid(currentForum.getFid().toString());
		topicPublicVO.setForumName(currentForum.getName());
		topicPublicVO.setTopicId(currentThread.getTid().toString());
		topicPublicVO.setTopicName(currentThread.getSubject());
		if(reasonpm.equals("1")){
			topicPublicVO.setNecesseryInfo(true);
		}else if(reasonpm.equals("2")){
			topicPublicVO.setNecessaryToSendMessage(true);
		}else if(reasonpm.equals("3")){
			topicPublicVO.setNecesseryInfo(true);
			topicPublicVO.setNecessaryToSendMessage(true);
		}
		List<String> reasonList = topicPublicVO.getReasonList();
		for(String modreasons : modreasonsArray){
			reasonList.add(modreasons);
		}
	}
	public String operating(String operation,Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		boolean bool = true;
		String result = null;
		try {
			Method method = this.getClass().getDeclaredMethod("operating_"+operation,Map.class,Map.class,MessageResources.class,Locale.class);
			method.setAccessible(true);
			try {
				result = (String)method.invoke(this,parameterMap,operationInfoMap,mr,locale);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				bool = false;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				bool = false;
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				bool = false;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			bool = false;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			bool = false;
		}
		if(bool){
			return result;
		}else{
			return mr.getMessage(locale, "try_again_alter");
		}
	}
	private String operating_deleteMirrorImage(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		if (parameterMap.get("moderate_") == null) {
			return mr.getMessage(locale,"admin_moderate_invalid");
		}
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		List<Threads> threadsList = getThreadsList((String[])parameterMap.get("moderate_"));
		List<Threadsmod> threadsmodList = new ArrayList<Threadsmod>(); 
		Forums forums = null;
		Short fid = Short.valueOf((String) parameterMap.get("fid"));
		if (fid == null && threadsList.size() > 0) {
			fid = threadsList.get(0).getFid();
		}
		ForumsDao forumsDao = ((ForumsDao)BeanFactory.getBean("forumsDao"));
		if (fid != null) {
			forums = forumsDao.findById(fid);
		}
		if (forums == null) {
			return mr.getMessage(locale,"undefined_action");
		}
		byte invisble = forums.getRecyclebin();
		String currentForumName = forums.getName();
		String operationE = "DEL";
		int timestamp = Common.time();
		List<Integer> deleteThreadsList = new ArrayList<Integer>();
		for (Threads threads_old : threadsList) {
			Integer tid = threads_old.getTid();
			deleteThreadsList.add(tid);
			if (invisble != 0) {
				Threadsmod threadsmod = new Threadsmod();
				ThreadsmodId threadsmodId = new ThreadsmodId();
				threadsmodId.setAction(operationE);
				threadsmodId.setDateline(timestamp);
				threadsmodId.setExpiration(0);
				threadsmodId.setMagicid((short) 0);
				threadsmodId.setStatus((byte) 1);
				threadsmodId.setTid(tid);
				threadsmodId.setUid((Integer) parameterMap.get("uid"));
				threadsmodId.setUsername((String) parameterMap
						.get("username"));
				threadsmod.setId(threadsmodId);
				threadsmodList.add(threadsmod);
			}
			writeLog(parameterMap,currentForumName, threads_old.getSubject(), threads_old
					.getTid()
					+ "", operationE,timestamp);
		}
		if(deleteThreadsList.size()>0){
			if (invisble == 0) {
				StringBuffer sql = new StringBuffer("DELETE FROM "+tableprefix+"threads WHERE tid IN(");
				for(Integer tid : deleteThreadsList){
					sql.append(tid);
					sql.append(",");
				}
				int sqlL = sql.length();
				sql.replace(sqlL-1, sqlL, ")");
				dataBaseDao.executeDelete(sql.toString());
			} else {
				StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threads AS t SET t.displayorder=-1 , t.moderated=1 WHERE t.tid IN(");
				for(Integer tid : deleteThreadsList){
					sqlBuffer.append(tid);
					sqlBuffer.append(",");
				}
				int sqlBufferL = sqlBuffer.length();
				sqlBuffer.replace(sqlBufferL-1, sqlBufferL, ")");
				dataBaseDao.execute(sqlBuffer.toString());
			}
		}
		sendMessageToAuthor(parameterMap,currentForumName, (String)parameterMap.get("url"), operationE, threadsList,operationInfoMap,mr,locale);
		if (threadsmodList.size() > 0) {
			((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).saveList(threadsmodList);
		}
		threadsmodList = null;
		updateForumCount(fid, dataBaseDao,mr,locale);
		forumsDao = null;
		forums  = null;
		Common.updatemodworks((Map<String,String>)parameterMap.get("settingMap"), (Integer) parameterMap.get("uid"), timestamp, operationE, (short) threadsList.size());
		operationE = null;
		return null;
	}
	private String operating_recommend(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String[] moderate_ = (String[])parameterMap.get("moderate_");
		if(moderate_==null){
			return mr.getMessage(locale,"admin_moderate_invalid");
		}
		String isRecommend = (String)parameterMap.get("isRecommend");
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		String fid = (String)parameterMap.get("fid");
		String currentForumName = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid=?",fid).get(0).get("name");
		String operationE = null;
		if(isRecommend.equals("1")){
			operationE = "REC";
		}else{
			operationE = "URE";
			List<Integer> tidList = new ArrayList<Integer>();
			for(String moderate : moderate_){
				tidList.add(Integer.valueOf(moderate));
			}
			((ForumRecommendDao)BeanFactory.getBean("forumRecommendDao")).deleteForumrecommend(tidList);
		}
		List<Threads> threadsList = getThreadsList(moderate_);
		List<Threadsmod> threadsmodList = new ArrayList<Threadsmod>();
		List<Forumrecommend> forumrecommendList = new ArrayList<Forumrecommend>();
		int timestamp = Common.time();
		StringBuffer updateThreadIdL = new StringBuffer();
		StringBuffer updateThreadsmod = new StringBuffer();
		for(Threads threads_old : threadsList){
			Integer tid = threads_old.getTid();
			if(isRecommend.equals("1")){
				updateThreadIdL.append(tid);
				updateThreadIdL.append(",");
				Forumrecommend forumrecommend = new Forumrecommend();
				forumrecommend.setAuthor(threads_old.getAuthor());
				forumrecommend.setAuthorid(threads_old.getAuthorid());
				forumrecommend.setDisplayorder((byte)0);
				forumrecommend.setExpiration(timestamp+Integer.parseInt((String)parameterMap.get("recommendExpire")));
				forumrecommend.setFid(Short.valueOf(fid));
				forumrecommend.setModeratorid((Integer)parameterMap.get("uid"));
				forumrecommend.setTid(threads_old.getTid());
				forumrecommend.setSubject(threads_old.getSubject());
				forumrecommendList.add(forumrecommend);
			}
			Threadsmod threadsmod = new Threadsmod();
			ThreadsmodId threadsmodId = new ThreadsmodId();
			threadsmodId.setAction(operationE);
			threadsmodId.setDateline(timestamp);
			threadsmodId.setExpiration(0);
			threadsmodId.setMagicid((short)0);
			threadsmodId.setStatus((byte)1);
			threadsmodId.setTid(tid);
			threadsmodId.setUid((Integer)parameterMap.get("uid"));
			threadsmodId.setUsername((String)parameterMap.get("username"));
			threadsmod.setId(threadsmodId);
			threadsmodList.add(threadsmod);
			updateThreadsmod.append(tid);
			updateThreadsmod.append(",");
			writeLog(parameterMap,currentForumName, threads_old.getSubject(),tid+"", operationE,timestamp);
		}
		if(updateThreadIdL.length()!=0){
			StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threads AS t SET t.moderated=1 WHERE t.tid IN(");
			sqlBuffer.append(updateThreadIdL);
			int sqlBufferL = sqlBuffer.length();
			sqlBuffer.replace(sqlBufferL-1, sqlBufferL, ")");
			dataBaseDao.execute(sqlBuffer.toString());
			sqlBuffer = null;
		}
		updateThreadIdL = null;
		if(updateThreadsmod.length()!=0){
			StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threadsmod SET status=0 WHERE tid IN(");
			sqlBuffer.append(updateThreadsmod);
			int sqlBufferL = sqlBuffer.length();
			sqlBuffer.replace(sqlBufferL-1, sqlBufferL, ")");
			sqlBuffer.append(" AND action='REC' AND status=1");
			dataBaseDao.execute(sqlBuffer.toString());
			sqlBuffer = null;
		}
		updateThreadsmod = null;
		sendMessageToAuthor(parameterMap,currentForumName, (String)parameterMap.get("url"), operationE, threadsList,operationInfoMap,mr,locale);
		((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).saveList(threadsmodList);
		threadsmodList = null;
		Common.updatemodworks((Map<String,String>)parameterMap.get("settingMap"), (Integer)parameterMap.get("uid"), timestamp, operationE, (short)threadsList.size());
		operationE = null;
		threadsList = null;
		if(forumrecommendList.size()>0){
			StringBuffer sqlBuffer = new StringBuffer("REPLACE INTO "+tableprefix+"forumrecommend (fid,tid,displayorder,subject,author,authorid,moderatorid,expiration) VALUES ");
			for(Forumrecommend forumrecommend : forumrecommendList){
				sqlBuffer.append("('"+forumrecommend.getFid()+"','"+forumrecommend.getTid()+"','"+forumrecommend.getDisplayorder()+"','"+forumrecommend.getSubject()+"'" +
						",'"+forumrecommend.getAuthor()+"','"+forumrecommend.getAuthorid()+"','"+forumrecommend.getModeratorid()+"','"+forumrecommend.getExpiration()+"'),");
			}
			sqlBuffer = sqlBuffer.deleteCharAt(sqlBuffer.length()-1);
			dataBaseDao.execute(sqlBuffer.toString());
		}
		forumrecommendList = null;
		return null;
	}
	private String operating_type(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String[] moderate_ = (String[])parameterMap.get("moderate_");
		if(moderate_==null){
			return mr.getMessage(locale,"admin_moderate_invalid");
		}
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		String fid = (String)parameterMap.get("fid");
		String currentForumName = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid=?",fid).get(0).get("name");
		String operationE = "TYP";
		List<Threads> threadsList = getThreadsList(moderate_);
		List<Threadsmod> threadsmodList = new ArrayList<Threadsmod>();
		int timestamp = Common.time();
		Threadsmod threadsmod = null;
		ThreadsmodId threadsmodId  = null;
		StringBuffer tidBuffer = new StringBuffer();
		for(Threads threads_old : threadsList){
			Integer tid = threads_old.getTid();
			tidBuffer.append(tid);
			tidBuffer.append(",");
			threadsmod = new Threadsmod();
			threadsmodId = new ThreadsmodId();
			threadsmodId.setAction(operationE);
			threadsmodId.setDateline(timestamp);
			threadsmodId.setExpiration(0);
			threadsmodId.setMagicid((short)0);
			threadsmodId.setStatus((byte)1);
			threadsmodId.setTid(tid);
			threadsmodId.setUid((Integer)parameterMap.get("uid"));
			threadsmodId.setUsername((String)parameterMap.get("username"));
			threadsmod.setId(threadsmodId);
			threadsmodList.add(threadsmod);
			writeLog(parameterMap,currentForumName, threads_old.getSubject(),threads_old.getTid()+"", operationE,timestamp);
		}
		threadsmod = null;
		threadsmodId = null;
		if(tidBuffer.length()>0){
			StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threads AS t SET t.typeid="+parameterMap.get("typeId")+", t.moderated=1 WHERE t.tid IN(");
			sqlBuffer.append(tidBuffer);
			int sqlBSize = sqlBuffer.length();
			sqlBuffer.replace(sqlBSize-1, sqlBSize, ")");
			((DataBaseDao)BeanFactory.getBean("dataBaseDao")).execute(sqlBuffer.toString());
			sqlBuffer = null;
		}
		tidBuffer = null;
		sendMessageToAuthor(parameterMap,currentForumName, (String)parameterMap.get("url"), operationE, threadsList,operationInfoMap,mr,locale);
		((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).saveList(threadsmodList);
		threadsmodList = null;
		Common.updatemodworks((Map<String,String>)parameterMap.get("settingMap"), (Integer)parameterMap.get("uid"), timestamp, operationE, (short)threadsList.size());
		operationE = null;
		threadsList = null;
		return null;
	}
	private String operating_digest(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String[] moderate_ = (String[])parameterMap.get("moderate_");
		if(moderate_==null){
			return mr.getMessage(locale,"admin_moderate_invalid");
		}
		String expiration = (String)parameterMap.get("expiration");
		String timeoffsetSession = (String)parameterMap.get("timeoffset");
		Integer expirationInteger = validateExpiration(expiration==null?"":expiration,timeoffsetSession);
		expiration = null;
		if(expirationInteger==null){
			return mr.getMessage(locale,"admin_expiration_invalid");
		}
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		String fid = (String)parameterMap.get("fid");
		String currentForumName = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid=?",fid).get(0).get("name");
		String level = (String)parameterMap.get("level");
		String operationE = null;
		if(level.equals("0")){
			operationE = "UDG";
		}else{
			if(expirationInteger==0){
				operationE = "DIG";
			}else{
				operationE = "EDI";
			}
		}
		List<Threads> threadsList = getThreadsList(moderate_);
		List<Threadsmod> threadsmodList = new ArrayList<Threadsmod>();
		Map<String,String> updateField = new HashMap<String, String>();
		updateField.put("digestposts", "digestposts");
		MembersDao memberDao = ((MembersDao)BeanFactory.getBean("memberDao"));
		PostOperating postOperating = (PostOperating)BeanFactory.getBean("postOperating");
		Forumfields forumfields = ((ForumfieldsDao)BeanFactory.getBean("forumfieldsDao")).findById(Short.valueOf(fid));
		byte levelByte = Byte.parseByte(level);
		String creditspolicy = (String) parameterMap.get("creditspolicy");
		String creditsformula = (String)parameterMap.get("creditsformula");
		int timestamp = Common.time();
		StringBuffer updateThreadsId = new StringBuffer();
		for(Threads threads_old : threadsList){
			Integer tid = threads_old.getTid();
			byte digest = threads_old.getDigest();
			updateThreadsId.append(tid);
			updateThreadsId.append(",");
			if(Byte.parseByte(level)!=digest){
				Members members = memberDao.findMemberById(threads_old.getAuthorid());
				if(members!=null){
					postOperating.setMembersExtcredit(forumfields, creditspolicy, members, updateField, postOperating.stick,true,levelByte-digest);
					if(digest==0&&Byte.valueOf(level)!=0){
						members.setDigestposts((members.getDigestposts()+1));
					}else if(digest!=0&&Byte.valueOf(level)==0){
						members.setDigestposts((members.getDigestposts()-1));
					}
					postOperating.setCredits(creditsformula, members,updateField);
					memberDao.modifyMember(members);
				}
			}
			Threadsmod threadsmod = new Threadsmod();
			ThreadsmodId threadsmodId = new ThreadsmodId();
			threadsmodId.setAction(operationE);
			threadsmodId.setDateline(timestamp);
			threadsmodId.setExpiration(expirationInteger);
			threadsmodId.setMagicid((short)0);
			threadsmodId.setStatus((byte)1);
			threadsmodId.setTid(tid);
			threadsmodId.setUid((Integer)parameterMap.get("uid"));
			threadsmodId.setUsername((String)parameterMap.get("username"));
			threadsmod.setId(threadsmodId);
			threadsmodList.add(threadsmod);
			writeLog(parameterMap,currentForumName, threads_old.getSubject(),threads_old.getTid()+"", operationE,timestamp);
		}
		creditspolicy = null;
		creditsformula = null;
		expirationInteger = null;
		updateField = null;
		memberDao = null;
		postOperating = null;
		forumfields = null;
		if(updateThreadsId.length()!=0){
			StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threads AS t SET t.digest="+level+", t.moderated=1 WHERE t.tid IN(");
			sqlBuffer.append(updateThreadsId);
			int sqlL = sqlBuffer.length();
			sqlBuffer.replace(sqlL-1, sqlL, ")");
			dataBaseDao.execute(sqlBuffer.toString());
			sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threadsmod SET status=0 WHERE tid IN(");
			sqlBuffer.append(updateThreadsId);
			sqlL = sqlBuffer.length();
			sqlBuffer.replace(sqlL-1, sqlL, ")");
			sqlBuffer.append(" AND action in ('DIG', 'UDI', 'EDI', 'UED') AND status=1");
			dataBaseDao.execute(sqlBuffer.toString());
			sqlBuffer = null;
			dataBaseDao = null;
		}
		level = null;
		updateThreadsId = null;
		sendMessageToAuthor(parameterMap,currentForumName, (String)parameterMap.get("url"), operationE, threadsList,operationInfoMap,mr,locale);
		((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).saveList(threadsmodList);
		threadsmodList = null;
		Common.updatemodworks((Map<String,String>)parameterMap.get("settingMap"), (Integer)parameterMap.get("uid"), timestamp, operationE, (short)threadsList.size());
		threadsList = null;
		operationE = null;
		return null;
	}
	private String operating_delete(Map<String, Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale) {
		PostsDao postsDao = ((PostsDao)BeanFactory.getBean("postsDao"));
		if (parameterMap.get("moderate_") == null) {
			return mr.getMessage(locale,"admin_moderate_invalid");
		}
		String expiration = (String)parameterMap.get("expiration");
		String timeoffsetSession = (String)parameterMap.get("timeoffset");
		Integer expirationInteger = validateExpiration(expiration==null?"":expiration,timeoffsetSession);
		expiration = null;
		if(expirationInteger==null){
			return mr.getMessage(locale,"admin_expiration_invalid");
		}
		String operationE = expirationInteger==0?"DEL":"TDEL";
		Map<String, String> updateField = new HashMap<String, String>();
		Map<String, String> updateField_posts = new HashMap<String, String>();
		updateField.put("posts", "posts");
		updateField_posts.put("posts", "posts");
		List<Threads> threadsList = getThreadsList((String[])parameterMap.get("moderate_"));
		List<Threadsmod> threadsmodList = new ArrayList<Threadsmod>(); 
		Forums forums = null;
		Short fid = Short.valueOf((String)parameterMap.get("fid"));
		if(fid==null&&threadsList.size()>0){
			fid = threadsList.get(0).getFid();
		}
		if(fid!=null){
			forums = ((ForumsDao)BeanFactory.getBean("forumsDao")).findById(fid);
		}
		if(forums==null){
			return mr.getMessage(locale,"undefined_action");
		}
		byte invisble = forums.getRecyclebin(); 
		String currentForumName = forums.getName();
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		PostOperating postOperating = (PostOperating)BeanFactory.getBean("postOperating");
		Forumfields forumfields = ((ForumfieldsDao)BeanFactory.getBean("forumfieldsDao")).findById(fid);
		MembersDao membersDao = ((MembersDao)BeanFactory.getBean("memberDao"));
		Map<String,String> tid_delThreadMap = new HashMap<String, String>();
		Map<String,String> tid_moveThreadMap = new HashMap<String, String>();
		List<Map<String,String>> resultMapList = dataBaseDao.executeQuery("SELECT tid,action FROM "+tableprefix+"threadsmod WHERE status=1 AND (action='TDEL' OR action='TMV')");
		for(Map<String,String> temp : resultMapList){
			if(temp.get("action").equals("TDEL")){
				tid_delThreadMap.put(temp.get("tid"),""); 
			}else{
				tid_moveThreadMap.put(temp.get("tid"), ""); 
			}
		}
		int losslessdel = Integer.parseInt((String)parameterMap.get("losslessdel"));
		int nowTime = (int)(System.currentTimeMillis()/1000);
		int nowSubtrationLoss = nowTime - losslessdel*24*3600;
		int timestamp = Common.time();
		String attachdir_realy = (String)parameterMap.get("attachdir_realy");
		String creditsformula = (String) parameterMap.get("creditsformula");
		int tid = 0; 
		boolean updateCredit = false; 
		boolean updateCredit_post = false; 
		int tempPostNum = 0;
		Posts postsTemp = null;
		List<Posts> postsList = null; 
		StringBuffer tidBuffer = new StringBuffer(); 
		StringBuffer pidBuffer = new StringBuffer();
		List<Integer> postsIdList = new ArrayList<Integer>(); 
		for (Threads threads_old : threadsList) {
			tid = threads_old.getTid();
			if(tid_delThreadMap.get(String.valueOf(tid))!=null){
				dataBaseDao.executeDelete("DELETE FROM "+tableprefix+"threadsmod WHERE status=1 AND action='TDEL' AND  tid="+tid);
			}
			if(tid_moveThreadMap.get(String.valueOf(tid))!=null){
				dataBaseDao.execute("UPDATE "+tableprefix+"threadsmod SET status=0 WHERE tid="+tid+" AND status=1 AND action='TMV'");
			}
			if(expirationInteger==0){
				updateCredit = losslessdel==0||nowSubtrationLoss<threads_old.getDateline();
				tidBuffer.append(tid);
				tidBuffer.append(",");
				Members members = membersDao.findMemberById(threads_old.getAuthorid());
				if(members!=null){
					if (threads_old.getDigest() != 0) {
						updateField.put("digestposts", "digestposts");
						postOperating.setMembersExtcredit(forumfields, (String) parameterMap.get("creditspolicy"), members, updateField, postOperating.stick, false,1);
						members.setDigestposts((members.getDigestposts() - 1));
					}
					tempPostNum = members.getPosts() - 1;
					members.setPosts(tempPostNum>0?tempPostNum:0);
					if(updateCredit){
						postOperating.setMembersExtcredit(forumfields, (String) parameterMap.get("creditspolicy"), members, updateField, postOperating.posts, false,1);
					}
					postsTemp = postsDao.findByTid(tid);
					if(postsTemp!=null){
						Integer threadPid = postsTemp.getPid();
						operatingAttachment(threadPid, attachdir_realy, forumfields, parameterMap, members, updateField,invisble == 0,updateCredit);
					}
					postOperating.setCredits(creditsformula, members,updateField);
					membersDao.modifyMember(members);
				}
				postsList = postsDao.getPostsListByTid(tid);
				for (Posts posts : postsList) {
					postsIdList.add(posts.getPid());
					updateCredit_post = losslessdel==0||nowSubtrationLoss<posts.getDateline();
					pidBuffer.append(posts.getPid());
					pidBuffer.append(",");
					if (posts.getFirst().byteValue()==1) {
						continue;
					} else {
						Members members_reply = ((MembersDao)BeanFactory.getBean("memberDao")).findMemberById(posts.getAuthorid());
						if(members_reply!=null){
							members_reply.setPosts(members_reply.getPosts() >= 1?members_reply.getPosts() - 1:0);
							if(updateCredit_post){
								postOperating.setMembersExtcredit(forumfields, (String) parameterMap.get("creditspolicy"), members_reply, updateField, postOperating.reply, false,1);
							}
							operatingAttachment(posts.getPid(), attachdir_realy, forumfields,parameterMap, members_reply, updateField,invisble == 0,updateCredit_post);
							postOperating.setCredits((String) parameterMap.get("creditsformula"),members_reply, updateField_posts);
							((MembersDao)BeanFactory.getBean("memberDao")).modifyMember(members_reply);
						}
					}
				}
			}
			if (expirationInteger!=0||invisble != 0) {
					Threadsmod threadsmod = new Threadsmod();
					ThreadsmodId threadsmodId = new ThreadsmodId();
					threadsmodId.setAction(operationE);
					threadsmodId.setDateline(timestamp);
					threadsmodId.setExpiration(expirationInteger);
					threadsmodId.setMagicid((short) 0);
					threadsmodId.setStatus((byte) 1);
					threadsmodId.setTid(tid);
					threadsmodId.setUid((Integer) parameterMap.get("uid"));
					threadsmodId.setUsername((String) parameterMap.get("username"));
					threadsmod.setId(threadsmodId);
					threadsmodList.add(threadsmod);
			}
			writeLog(parameterMap,currentForumName, threads_old.getSubject(), threads_old.getTid() + "",operationE,timestamp);
		}
		postOperating = null;
		forumfields = null;
		membersDao = null;
		postsTemp = null;
		attachdir_realy = null;
		creditsformula = null;
		postsList = null;
		if(expirationInteger==0){
			if(tidBuffer.length()!=0){
				if (invisble == 0) {
					String tempS = tidBuffer.substring(0,tidBuffer.length()-1);
					dataBaseDao.execute("DELETE FROM "+tableprefix+"threads WHERE tid IN("+tempS+")");
					dataBaseDao.execute("DELETE FROM "+tableprefix+"trades WHERE tid IN ("+tempS+")");
				}else{
					StringBuffer sql = new StringBuffer("UPDATE "+tableprefix+"threads AS t SET t.displayorder=-1 , t.moderated=1 WHERE t.tid IN(");
					sql.append(tidBuffer);
					int sqlL = sql.length();
					sql.replace(sqlL-1, sqlL, ")");
					dataBaseDao.execute(sql.toString());
				}
			}
			tidBuffer = null;
			if(pidBuffer.length()!=0){
				if (invisble == 0) {
					StringBuffer sql = new StringBuffer("DELETE FROM "+tableprefix+"posts WHERE pid IN(");
					sql.append(pidBuffer);
					int sqlL = sql.length();
					sql.replace(sqlL-1, sqlL, ")");
					dataBaseDao.execute(sql.toString());
				}else{
					StringBuffer sql = new StringBuffer("UPDATE "+tableprefix+"posts AS p SET p.invisible=-1 WHERE p.pid IN(");
					sql.append(pidBuffer);
					int sqlL = sql.length();
					sql.replace(sqlL-1, sqlL, ")");
					dataBaseDao.execute(sql.toString());
				}
			}
			pidBuffer = null;
			if(forums!=null){
				updateForumCount(fid, dataBaseDao,mr,locale);
			}
			if (invisble == 0) {
				deleteRatelog(postsIdList);
				deleteMyposts(postsIdList);
				deleteAttachments(postsIdList);
			}
			postsIdList = null;
		}
		sendMessageToAuthor(parameterMap,currentForumName, (String)parameterMap.get("url"), operationE, threadsList,operationInfoMap,mr,locale);
		if (threadsmodList.size() >0) {
			((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).saveList(threadsmodList);
		}
		Common.updatemodworks((Map<String,String>)parameterMap.get("settingMap"), (Integer)parameterMap.get("uid"), timestamp, "DEL", (short)threadsList.size());
		postsDao = null;
		return null;
	}
	private String operating_stick(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String[] moderate_ = (String[])parameterMap.get("moderate_");
		if(moderate_==null){
			return mr.getMessage(locale,"admin_moderate_invalid");
		}
		String expiration = (String)parameterMap.get("expiration");
		String timeoffsetSession = (String)parameterMap.get("timeoffset");
		Integer expirationInteger = validateExpiration(expiration==null?"":expiration,timeoffsetSession);
		expiration = null;
		if(expirationInteger==null){
			return mr.getMessage(locale,"admin_expiration_invalid");
		}
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		String fid = (String)parameterMap.get("fid");
		String currentForumName = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid=?",fid).get(0).get("name");
		String level = (String)parameterMap.get("level");
		String operationE = null;
		if(level.equals("0")){
			operationE = "UST";
		}else{
			if(expirationInteger==0){
				operationE = "STK";
			}else{
				operationE = "EST";
			}
		}
		List<Threads> threadsList = getThreadsList(moderate_);
		List<Threadsmod> threadsmodList = new ArrayList<Threadsmod>();
		int timestamp = Common.time();
		byte levelB = Byte.valueOf(level);
		StringBuffer tidBuffer = new StringBuffer();
		Threadsmod threadsmod = null;
		ThreadsmodId threadsmodId = null;
		for(Threads threads_old : threadsList){
			Integer tid = threads_old.getTid();
			tidBuffer.append(tid); 
			tidBuffer.append(","); 
			threadsmod = new Threadsmod();
			threadsmodId = new ThreadsmodId();
			threadsmodId.setAction(operationE);
			threadsmodId.setDateline(timestamp);
			threadsmodId.setExpiration(expirationInteger);
			threadsmodId.setMagicid((short)0);
			threadsmodId.setStatus((byte)1);
			threadsmodId.setTid(tid);
			threadsmodId.setUid((Integer)parameterMap.get("uid"));
			threadsmodId.setUsername((String)parameterMap.get("username"));
			threadsmod.setId(threadsmodId);
			threadsmodList.add(threadsmod);
			writeLog(parameterMap,currentForumName, threads_old.getSubject(),tid+"", operationE,timestamp);
		}
		if(tidBuffer.length()!=0){
			StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threads AS t SET t.displayorder="+levelB+" , t.moderated=1 WHERE t.tid IN(");
			sqlBuffer.append(tidBuffer);
			int sqlBSize = sqlBuffer.length();
			sqlBuffer.replace(sqlBSize-1, sqlBSize, ")");
			dataBaseDao.execute(sqlBuffer.toString());
			sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threadsmod SET status=0 WHERE tid IN(");
			sqlBuffer.append(tidBuffer);
			sqlBSize = sqlBuffer.length();
			sqlBuffer.replace(sqlBSize-1, sqlBSize, ")");
			sqlBuffer.append(" AND action in ('UST', 'EST', 'STK', 'UES') AND status=1");
			dataBaseDao.execute(sqlBuffer.toString());
			dataBaseDao = null;
			sqlBuffer = null;
		}
		tidBuffer = null;
		threadsmod = null;
		threadsmodId = null;
		sendMessageToAuthor(parameterMap,currentForumName, (String)parameterMap.get("url"), operationE, threadsList,operationInfoMap,mr,locale);
		((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).saveList(threadsmodList);
		Common.updatemodworks((Map<String,String>)parameterMap.get("settingMap"), (Integer)parameterMap.get("uid"), timestamp, operationE, (short)threadsList.size());
		try {
			Cache.updateCache("forumdisplay");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private String operating_bump(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String[] moderate_ = (String[])parameterMap.get("moderate_");
		if(moderate_==null){
			return mr.getMessage(locale,"admin_moderate_invalid");
		}
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		String fid = (String)parameterMap.get("fid");
		String currentForumName = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid=?",fid).get(0).get("name");
		String isbump = (String)parameterMap.get("isbump");
		String operationE = null;
		List<Threads> threadsList = getThreadsList(moderate_);
		List<Threadsmod> threadsmodList = new ArrayList<Threadsmod>();
		int timestamp = Common.time();
		Integer lastpost = null;
		if(isbump.equals("1")){
			operationE = "BMP";
			lastpost = timestamp;
			Threads threads = threadsList.get(0);
			String lastpostString = threads.getTid()+"\t"+threads.getSubject()+"\t"+lastpost+"\t"+threads.getLastposter();
			dataBaseDao.execute("UPDATE "+tableprefix+"forums SET lastpost='"+lastpostString+"' WHERE fid=?",fid);
		}else{
			operationE = "DWN";
			lastpost = timestamp - 86400 * 730;
		}
		StringBuffer tidBuffer = new StringBuffer();
		Threadsmod threadsmod = null;
		ThreadsmodId threadsmodId = null;
		for(Threads threads_old : threadsList){
			Integer tid = threads_old.getTid();
			tidBuffer.append(tid);
			tidBuffer.append(",");
			threadsmod = new Threadsmod();
			threadsmodId = new ThreadsmodId();
			threadsmodId.setAction(operationE);
			threadsmodId.setDateline(timestamp);
			threadsmodId.setExpiration(0);
			threadsmodId.setMagicid((short)0);
			threadsmodId.setStatus((byte)1);
			threadsmodId.setTid(tid);
			threadsmodId.setUid((Integer)parameterMap.get("uid"));
			threadsmodId.setUsername((String)parameterMap.get("username"));
			threadsmod.setId(threadsmodId);
			threadsmodList.add(threadsmod);
			writeLog(parameterMap,currentForumName, threads_old.getSubject(),threads_old.getTid()+"", operationE,timestamp);
		}
		threadsmod = null;
		threadsmodId = null;
		if(tidBuffer.length()>0){
			StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threads AS t SET t.lastpost="+lastpost+" , t.moderated=1 WHERE t.tid IN(");
			sqlBuffer.append(tidBuffer);
			int sqlBSize = sqlBuffer.length();
			sqlBuffer.replace(sqlBSize-1, sqlBSize, ")");
			((DataBaseDao)BeanFactory.getBean("dataBaseDao")).execute(sqlBuffer.toString());
			sqlBuffer = null;
		}
		tidBuffer = null;
		sendMessageToAuthor(parameterMap,currentForumName, (String)parameterMap.get("url"), operationE, threadsList,operationInfoMap,mr,locale);
		((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).saveList(threadsmodList);
		Common.updatemodworks((Map<String,String>)parameterMap.get("settingMap"), (Integer)parameterMap.get("uid"), timestamp, operationE, (short)threadsList.size());
		return null;
	}
	private String operating_close(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String[] moderate_ = (String[])parameterMap.get("moderate_");
		if(moderate_==null){
			return mr.getMessage(locale,"admin_moderate_invalid");
		}
		String expiration = (String)parameterMap.get("expiration");
		String timeoffsetSession = (String)parameterMap.get("timeoffset");
		Integer expirationInteger = validateExpiration(expiration==null?"":expiration,timeoffsetSession);
		expiration = null;
		if(expirationInteger==null){
			return mr.getMessage(locale,"admin_expiration_invalid");
		}
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		String fid = (String)parameterMap.get("fid");
		String currentForumName = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid=?",fid).get(0).get("name");
		String operationE = null;
		if(expirationInteger==0){
			if(((String)parameterMap.get("close")).equals("0")){
				operationE = "OPN";
			}else{
				operationE = "CLS";
			}
		}else{
			if(((String)parameterMap.get("close")).equals("0")){
				operationE = "EOP";
			}else{
				operationE = "ECL";
			}
		}
		List<Threads> threadsList = getThreadsList(moderate_);
		List<Threadsmod> threadsmodList = new ArrayList<Threadsmod>();
		int timestamp = Common.time();
		int closedInt = Integer.parseInt((String)parameterMap.get("close"));
		StringBuffer tidBuffer = new StringBuffer();
		Threadsmod threadsmod = null;
		ThreadsmodId threadsmodId = null;
		for(Threads threads_old : threadsList){
			threads_old.setClosed(closedInt);
			threads_old.setModerated((byte)1);
			((ThreadsDao)BeanFactory.getBean("threadsDao")).modifyThreads(threads_old);
			Integer tid = threads_old.getTid();
			tidBuffer.append(tid);
			tidBuffer.append(",");
			threadsmod = new Threadsmod();
			threadsmodId = new ThreadsmodId();
			threadsmodId.setAction(operationE);
			threadsmodId.setDateline(timestamp);
			threadsmodId.setExpiration(expirationInteger);
			threadsmodId.setMagicid((short)0);
			threadsmodId.setStatus((byte)1);
			threadsmodId.setTid(tid);
			threadsmodId.setUid((Integer)parameterMap.get("uid"));
			threadsmodId.setUsername((String)parameterMap.get("username"));
			threadsmod.setId(threadsmodId);
			threadsmodList.add(threadsmod);
			writeLog(parameterMap,currentForumName, threads_old.getSubject(),threads_old.getTid()+"", operationE,timestamp);
		}
		threadsmod = null;
		threadsmodId = null;
		if(tidBuffer.length()>0){
			StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threads AS t SET t.closed="+closedInt+" , t.moderated=1 WHERE t.tid IN(");
			sqlBuffer.append(tidBuffer);
			int sqlBSize = sqlBuffer.length();
			sqlBuffer.replace(sqlBSize-1, sqlBSize, ")");
			dataBaseDao.execute(sqlBuffer.toString());
			sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threadsmod SET status=0 WHERE tid IN(");
			sqlBuffer.append(tidBuffer);
			sqlBSize = sqlBuffer.length();
			sqlBuffer.replace(sqlBSize-1, sqlBSize, ")");
			sqlBuffer.append(" AND action in ('EOP', 'CLS') AND status=1");
			dataBaseDao.execute(sqlBuffer.toString());
			dataBaseDao = null;
			sqlBuffer = null;
		}
		tidBuffer = null;
		sendMessageToAuthor(parameterMap,currentForumName, (String)parameterMap.get("url"), operationE, threadsList,operationInfoMap,mr,locale);
		((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).saveList(threadsmodList);
		threadsmodList = null;
		Common.updatemodworks((Map<String,String>)parameterMap.get("settingMap"), (Integer)parameterMap.get("uid"), timestamp, operationE, (short)threadsList.size());
		threadsList = null;
		operationE = null;
		return null;
	}
	private String operating_highlight(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String[] moderate_ = (String[])parameterMap.get("moderate_");
		if(moderate_==null){
			return mr.getMessage(locale,"admin_moderate_invalid");
		}
		String expiration = (String)parameterMap.get("expiration");
		String timeoffsetSession = (String)parameterMap.get("timeoffset");
		Integer expirationInteger = validateExpiration(expiration==null?"":expiration,timeoffsetSession);
		expiration = null;
		if(expirationInteger==null){
			return mr.getMessage(locale,"admin_expiration_invalid");
		}
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		String fid = (String)parameterMap.get("fid");
		String currentForumName = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid=?",fid).get(0).get("name");
		String operationE = expirationInteger==0?"HLT":"EHL";
		String[] highlight_style = (String[])parameterMap.get("highlight_style");
		Integer highlight = 0;
		if(highlight_style!=null){
			for(int i = 0;i<highlight_style.length;i++){
				if(highlight_style[i].equals("10")||highlight_style[i].equals("20")||highlight_style[i].equals("40")){
					highlight += Integer.valueOf(highlight_style[i]);
				}else{
					return mr.getMessage(locale,"undefined_action");
				}
			}
		}
		highlight_style = null;
		int highlight_color = Common.toDigit((String)parameterMap.get("highlight_color"));
		if(highlight_color>=Common.THREAD_COLORS.length){
			return mr.getMessage(locale,"undefined_action");
		}
		highlight += highlight_color;
		List<Threads> threadsList = getThreadsList(moderate_);
		List<Threadsmod> threadsmodList = new ArrayList<Threadsmod>();
		int timestamp = Common.time();
		StringBuffer tidBuffer = new StringBuffer();
		Threadsmod threadsmod = null;
		ThreadsmodId threadsmodId = null;
		Integer tid = null;
		for (Threads threads_old : threadsList) {
			tid = threads_old.getTid();
			tidBuffer.append(tid);
			tidBuffer.append(",");
			threadsmod = new Threadsmod();
			threadsmodId = new ThreadsmodId();
			threadsmodId.setAction(operationE);
			threadsmodId.setDateline(timestamp);
			threadsmodId.setExpiration(expirationInteger);
			threadsmodId.setMagicid((short)0);
			threadsmodId.setStatus((byte)1);
			threadsmodId.setTid(tid);
			threadsmodId.setUid((Integer)parameterMap.get("uid"));
			threadsmodId.setUsername((String)parameterMap.get("username"));
			threadsmod.setId(threadsmodId);
			threadsmodList.add(threadsmod);
			writeLog(parameterMap,currentForumName, threads_old.getSubject(),threads_old.getTid()+"", operationE,timestamp);
		}
		tid = null;
		threadsmod = null;
		threadsmodId = null;
		if(tidBuffer.length()>0){
			StringBuffer sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threads AS t SET t.highlight="+highlight+" , t.moderated=1 WHERE t.tid IN(");
			sqlBuffer.append(tidBuffer);
			int sqlBSize = sqlBuffer.length();
			sqlBuffer.replace(sqlBSize-1, sqlBSize, ")");
			dataBaseDao.execute(sqlBuffer.toString());
			sqlBuffer = new StringBuffer("UPDATE "+tableprefix+"threadsmod SET status=0 WHERE tid IN(");
			sqlBuffer.append(tidBuffer);
			sqlBSize = sqlBuffer.length();
			sqlBuffer.replace(sqlBSize-1, sqlBSize, ")");
			sqlBuffer.append(" AND action IN('HLT','EHL','CCK') AND status=1");
			dataBaseDao.execute(sqlBuffer.toString());
			dataBaseDao = null;
			sqlBuffer = null;
		}
		tidBuffer = null;
		sendMessageToAuthor(parameterMap,currentForumName, (String)parameterMap.get("url"), operationE, threadsList,operationInfoMap,mr,locale);
		((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).saveList(threadsmodList);
		threadsmodList = null;
		Common.updatemodworks((Map<String,String>)parameterMap.get("settingMap"), (Integer)parameterMap.get("uid"), timestamp, operationE, (short)threadsList.size());
		threadsList = null;
		operationE = null;
		return null;
	}
	private String operating_move(Map<String,Object> parameterMap,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String[] moderate_ = (String[])parameterMap.get("moderate_");
		if(moderate_==null){
			return mr.getMessage(locale,"admin_moderate_invalid");
		}
		String expiration = (String)parameterMap.get("expiration");
		String timeoffsetSession = (String)parameterMap.get("timeoffset");
		Integer expirationInteger = validateExpiration(expiration==null?"":expiration,timeoffsetSession);
		expiration = null;
		if(expirationInteger==null){
			return mr.getMessage(locale,"admin_expiration_invalid");
		}
		DataBaseDao dataBaseDao = ((DataBaseDao)BeanFactory.getBean("dataBaseDao"));
		String sql_selectForum = "SELECT fid, name, modnewposts, allowpostspecial FROM "+tableprefix+"forums WHERE fid=? AND status=1 AND type<>'group'";
		List<Map<String,String>> resultList = dataBaseDao.executeQuery(sql_selectForum,String.valueOf(parameterMap.get("moveto")));
		String fid = (String)parameterMap.get("fid");
		String currentForumName = dataBaseDao.executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid=?",fid).get(0).get("name");
		String moveTO = (String)parameterMap.get("moveto");
		short moveto = Short.parseShort(moveTO);
		Map<String,String> resultMap = null;
		String displayorderadd = "";
		if(resultList.size()<0){
			return mr.getMessage(locale,"admin_move_invalid");
		}else if(fid.equals(moveTO)){
			return mr.getMessage(locale,"admin_move_illegal");
		}else{
			resultMap = resultList.get(0);
			int allowdirectpostInteger = Integer.parseInt((String)parameterMap.get("allowdirectpost"));
			int modnewposts = Integer.parseInt(resultMap.get("modnewposts"));
			boolean modnewthreads = (allowdirectpostInteger==0 || allowdirectpostInteger == 1) && modnewposts>0;
			boolean modnewreplies = (allowdirectpostInteger==0 || allowdirectpostInteger == 2) && modnewposts>0;
			if(modnewthreads || modnewreplies) {
				return mr.getMessage(locale,"admin_move_have_mod");
			}
		}
		if(((Byte)parameterMap.get("adminId"))==3){
			displayorderadd = ", displayorder='0'";
			Byte accessmasks = (Byte)parameterMap.get("accessmasks");
			Integer uid = (Integer)parameterMap.get("uid");
			String accessadd1 = "";
			String accessadd2 = "";
			if(accessmasks>0){
				accessadd1 = ", a.allowview, a.allowpost, a.allowreply, a.allowgetattach, a.allowpostattach";
				accessadd2 = "LEFT JOIN "+tableprefix+"access a ON a.uid='"+uid+"' AND a.fid='"+moveto+"'";
			}
			List<Map<String,String>> resultList2 = dataBaseDao.executeQuery("SELECT ff.postperm, m.uid AS istargetmod "+accessadd1+" FROM "+tableprefix+"forumfields ff "+accessadd2+" LEFT JOIN "+tableprefix+"moderators m ON m.fid='"+moveto+"' AND m.uid='"+uid+"' WHERE ff.fid='"+moveto+"'");
			if (resultList2.size() > 0) {
				Map<String, String> resultMap2 = resultList2.get(0);
				String postperm = resultMap2.get("postperm");
				String isTargetMod = resultMap2.get("istargetmod"); 
				if(isTargetMod==null||isTargetMod.equals("")||isTargetMod.equals("0")){
					boolean beingGroupId = false;
					if(postperm!=null){
						beingGroupId = postperm.equals("")||Common.forumperm(postperm, (Short)parameterMap.get("groupId"), (String)parameterMap.get("extgroupids"));
					}else{
						System.out.println("System.out.println In TopicAdminActionService >> postperm is NULL");
					}
					String allowview = resultMap2.get("allowview");
					boolean allowviewBool = allowview!=null&&!allowview.equals("")&&!allowview.equals("0");
					String allowreply = resultMap2.get("allowreply");
					boolean allowreplyBool = allowreply!=null&&!allowreply.equals("")&&!allowreply.equals("0");
					String allowgetattach = resultMap2.get("allowgetattach");
					boolean allowgetattachBool = allowgetattach!=null&&!allowgetattach.equals("")&&!allowgetattach.equals("0");
					String allowpostattach = resultMap2.get("allowpostattach");
					boolean allowpostattachBool = allowpostattach!=null&&!allowpostattach.equals("")&&!allowpostattach.equals("0");
					String allowpost = resultMap2.get("allowpost");
					boolean allowpostBool = allowpost!=null&&!allowpost.equals("")&&!allowpost.equals("0");
					if(!beingGroupId||(accessmasks>0&&(!allowviewBool||!allowreplyBool||!allowgetattachBool||!allowpostattachBool||!allowpostBool))){
						return mr.getMessage(locale,"admin_move_nopermission");
					}
				}
			}else{
				System.out.println("System.out.println IN TopicAdminActionService resultList2.size()<1");
			}
		}
		int allowpostspecial = Integer.parseInt(resultMap.get("allowpostspecial")); 
		int[] allowpostspecials = new int[6];
		if (allowpostspecial < 127) {
			for (int i = 0; i <= 5; i++) {
                allowpostspecials[i] = allowpostspecial%2;
                allowpostspecial = allowpostspecial>>1;
            }
		}
		List<Threads> threadsList = getThreadsList(moderate_);
		moderate_ = null;
		boolean allowpostspecialBool = true;
		StringBuffer tidBuffer = new StringBuffer();
		for(Threads threads : threadsList){
			if(tidBuffer.length()==0){
				tidBuffer.append(threads.getTid()) ;
			}else{
				tidBuffer.append(", ");
				tidBuffer.append(threads.getTid());
			}
			if(threads.getSpecial()!=0&&allowpostspecials[threads.getSpecial()-1]==0){
				allowpostspecialBool = false;
				break;
			}
		}
		if(!allowpostspecialBool){
			return mr.getMessage(locale,"admin_moderate_invalid");
		}
		String type = (String) parameterMap.get("type");
		String operationE  = "MOV";	
		String remark = null;
		if(expirationInteger!=0){
			operationE = "TMV";
			if(type.equals("redirect")){
				remark = "r"+moveTO; 
			}else{
				remark = "m"+moveTO;
			}
		}
		List<Threadsmod> threadsmodList = new ArrayList<Threadsmod>();
		List<Threads> threadMirrorList = null;
		if (type.equals("redirect")&&expirationInteger==0) {
			threadMirrorList = new ArrayList<Threads>();
		}
		int replyCount = 0;
		int threadCount = threadsList.size();
		int timestamp = Common.time();
		Map<String,String> tid_moveThreadMap = new HashMap<String, String>();
		Map<String,String> tid_delThreadMap = new HashMap<String, String>();
		List<Map<String,String>> resultMapList = dataBaseDao.executeQuery("SELECT tid,action FROM "+tableprefix+"threadsmod WHERE status=1 AND (action='TMV' OR action='TDEL')");
		for(Map<String,String> temp : resultMapList){
			if(temp.get("action").equals("TDEL")){
				tid_delThreadMap.put(temp.get("tid"), "");
			}else{
				tid_moveThreadMap.put(temp.get("tid"),""); 
			}
		}
		for (Threads threads_old : threadsList) {
			Integer tid = threads_old.getTid();
			replyCount += threads_old.getReplies();
			if (type.equals("redirect")&&expirationInteger==0) {
				Threads threadsMirror = getThreadsMirror(threads_old);
				threadMirrorList.add(threadsMirror);
				threadCount = 0;
			}
			if(tid_moveThreadMap.get(tid.toString())!=null){
				dataBaseDao.execute("UPDATE "+tableprefix+"threadsmod SET status=0 WHERE tid="+tid+" AND status=1 AND action='TMV'");
			}
			if(tid_delThreadMap.get(tid.toString())!=null){
				dataBaseDao.execute("DELETE FROM "+tableprefix+"threadsmod WHERE tid="+tid+" AND status=1 AND action='TDEL'");
			}
			Threadsmod threadsmod = new Threadsmod();
			ThreadsmodId threadsmodId = new ThreadsmodId();
			threadsmodId.setAction(operationE);
			threadsmodId.setDateline(timestamp);
			threadsmodId.setExpiration(expirationInteger);
			threadsmodId.setMagicid((short)0);
			threadsmodId.setStatus((byte)1);
			threadsmodId.setTid(tid);
			threadsmodId.setUid((Integer)parameterMap.get("uid"));
			threadsmodId.setUsername((String)parameterMap.get("username"));
			threadsmodId.setRemark(remark);
			threadsmod.setId(threadsmodId);
			threadsmodList.add(threadsmod);
			writeLog(parameterMap, currentForumName,threads_old.getSubject(),threads_old.getTid()+"", operationE,timestamp);
		}
		sendMessageToAuthor(parameterMap, currentForumName,(String)parameterMap.get("url"), operationE, threadsList,operationInfoMap,mr,locale);
		if(threadMirrorList!=null&&threadMirrorList.size()>0){
			((ThreadsDao)BeanFactory.getBean("threadsDao")).addThreads(threadMirrorList);
		}
		((ThreadsmodDao)BeanFactory.getBean("threadsmodDao")).saveList(threadsmodList);
		if(expirationInteger==0){
			String update_threadsSQL = "UPDATE "+tableprefix+"threads SET fid='"+moveto+"', moderated='1' "+displayorderadd+" WHERE tid IN ("+tidBuffer.toString()+")";
			String update_postsSQL = "UPDATE "+tableprefix+"posts SET fid='"+moveto+"' WHERE tid IN ("+tidBuffer.toString()+")";
			dataBaseDao.execute(update_threadsSQL);
			dataBaseDao.execute(update_postsSQL);
			updateForumCount(fid, dataBaseDao, mr, locale);
			updateForumCount(moveTO, dataBaseDao, mr, locale);
		}
		Common.updatemodworks((Map<String,String>)parameterMap.get("settingMap"), (Integer)parameterMap.get("uid"), timestamp, "MOV", (short)threadsList.size());
		return null;
	}
	public boolean checkTid(Short nowFid,String[] moderate_) {
		if(nowFid==null||moderate_==null||moderate_.length==0){
			return false;
		}
		StringBuffer hqlBuffer = new StringBuffer("FROM Threads AS t WHERE t.fid="+nowFid+" AND t.displayorder>=0 AND t.tid IN(");
		for(String tid : moderate_){
			hqlBuffer.append(Integer.parseInt(tid));
			hqlBuffer.append(",");
		}
		String hql = hqlBuffer.replace(hqlBuffer.length()-1, hqlBuffer.length(), ")").toString();
		List<Threads> threadsList = ((ThreadsDao)BeanFactory.getBean("threadsDao")).getThreadsByHql(hql);
		return threadsList.size() == moderate_.length;
	}
	private Integer validateExpiration(String expiration,String timeoffsetSession){
		Integer expirationInteger = null;
		if(!expiration.equals("")){
			expirationInteger = chekTime(expiration,timeoffsetSession);
		}else{
			expirationInteger = 0;
		}
		return expirationInteger;
	}
	private void sendMessageToAuthor(Map<String,Object> parameterMap,String currentForumName,String url,String operation,List<Threads> threadsList,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String timeoffset = ForumInit.settings.get("timeoffset");
		String sendreasonpm = (String)parameterMap.get("sendreasonpm");
		if(sendreasonpm!=null){
			String added = "";
			if(operation.equals("MOV")||operation.equals("TDEL")||operation.equals("TMV")){
				String expiration = (String)parameterMap.get("expiration");
				if(operation.equals("MOV")||operation.equals("TMV")){
					String moveto = (String)parameterMap.get("moveto");
					List<Map<String,String>> froumInfoListMap = ((DataBaseDao)BeanFactory.getBean("dataBaseDao")).executeQuery("SELECT name FROM "+tableprefix+"forums WHERE fid=?",moveto);
					String targetForumName = "";
					if(froumInfoListMap!=null&&froumInfoListMap.size()>0){
						targetForumName = froumInfoListMap.get(0).get("name");
					}
					if(operation.equals("TMV")){
						added = mr.getMessage(locale,"admin_move_expiration_s",expiration,"[url="+url+"/forumdisplay.jsp?fid="+moveto+" ]"+Common.strip_tags(targetForumName)+"[/url]");
					}else{
						added = mr.getMessage(locale,"admin_move_s","[url="+url+"/forumdisplay.jsp?fid="+moveto+" ]"+Common.strip_tags(targetForumName)+"[/url]");
					}
				}else{
					if(expiration!=null&&!expiration.equals("")){
						added = mr.getMessage(locale,"admin_delete_expiration",expiration);
					}
				}
			}
			SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffset);
			String reason = (String)parameterMap.get("reason");
			String username = (String)parameterMap.get("username");
			Integer uid = (Integer)parameterMap.get("uid");
			List<Pms> list = new ArrayList<Pms>();
			StringBuffer hqlBuffer = new StringBuffer("UPDATE Members AS m SET m.newpm=1 WHERE m.uid IN(");
			int timestamp = Common.time();
			String[] tempSA = new String[7];
			Pms pms = null;
			StringBuilder builder1 = new StringBuilder("[url=");
			builder1.append(url);
			int budInitLen = builder1.length();
			String tempS1;
			String tempS2;
			String tempS3;
			for(Threads threads : threadsList){
				pms = new Pms();
				pms.setDateline(timestamp);
				pms.setDelstatus((byte)0);
				pms.setFolder("inbox");
				builder1.append("/space.jsp?uid=");
				builder1.append(uid);
				builder1.append(" ][i]");
				builder1.append(username);
				builder1.append("[/i][/url]");
				tempS1 = builder1.toString();
				builder1.delete(budInitLen, builder1.length());
				builder1.append("/viewthread.jsp?tid=");
				builder1.append(threads.getTid());
				builder1.append(" ]");
				builder1.append(threads.getSubject());
				builder1.append("[/url]");
				tempS2 = builder1.toString();
				builder1.delete(budInitLen, builder1.length());
				builder1.append("/forumdisplay.jsp?fid=");
				builder1.append(threads.getFid());
				builder1.append(" ]");
				builder1.append(Common.strip_tags(currentForumName));
				builder1.append("[/url]");
				tempS3 = builder1.toString();
				builder1.delete(budInitLen, builder1.length());
				tempSA[0] = tempS1;
				tempSA[1] = operationInfoMap.get(operation);
				tempSA[2] = added;
				tempSA[3] = tempS2;
				tempSA[4] = Common.gmdate(dateFormat, threads.getDateline());
				tempSA[5] = tempS3;
				tempSA[6] = reason;
				pms.setMessage(mr.getMessage(locale,"admin_thread_pm",tempSA));
				pms.setMsgfrom(username);
				pms.setMsgfromid(uid);
				pms.setMsgtoid(threads.getAuthorid());
				pms.setNew_((byte)1);
				pms.setSubject(mr.getMessage(locale,"reason_moderate_subject"));
				list.add(pms);
				hqlBuffer.append(threads.getAuthorid());
				hqlBuffer.append(",");
			}
			if(list.size()>0){
				((PmsDao)BeanFactory.getBean("pmsDao")).insertPmsList(list);
			}
			String hql = hqlBuffer.replace(hqlBuffer.length()-1, hqlBuffer.length(), ")").toString();
			((MembersDao)BeanFactory.getBean("memberDao")).updateMembers(hql);
		}
	}
	private void sendMessageToAuthor_(Map<String,Object> parameterMap,String currentForumName,String url,String operation,List<Posts> postsList,Map<String,String> operationInfoMap,MessageResources mr,Locale locale){
		String timeoffset = ForumInit.settings.get("timeoffset");
		String sendreasonpm = (String)parameterMap.get("sendreasonpm");
		if(sendreasonpm!=null){
			SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffset);
			String reason = (String)parameterMap.get("reason");
			String username = (String)parameterMap.get("username");
			Integer uid = (Integer)parameterMap.get("uid");
			List<Pms> list = new ArrayList<Pms>();
			StringBuffer hqlBuffer = new StringBuffer("UPDATE Members AS m SET m.newpm=1 WHERE m.uid IN(");
			int timestamp = Common.time();
			StringBuilder builder1 = new StringBuilder("[url=");
			builder1.append(url);
			StringBuilder builder2 = new StringBuilder();
			int budInitLen = builder1.length();
			Pms pms = null;
			String[] tempSA = new String[6];
			for(Posts posts : postsList){
				pms = new Pms();
				pms.setDateline(timestamp);
				pms.setDelstatus((byte)0);
				pms.setFolder("inbox");
				builder1.append("/space.jsp?uid=");
				builder1.append(uid);
				builder1.append(" ][i]");
				builder1.append(username);
				builder1.append("[/i][/url]");
				tempSA[0] = builder1.toString();
				builder1.delete(budInitLen, builder1.length());
				tempSA[1] = operationInfoMap.get(operation);
				builder2.append("[quote]");
				builder2.append(posts.getMessage());
				builder2.append("[/quote]");
				tempSA[2] = builder2.toString();
				builder2.delete(0, builder2.length());
				tempSA[3] = Common.gmdate(dateFormat, posts.getDateline());
				builder1.append("/forumdisplay.jsp?fid=");
				builder1.append(posts.getFid());
				builder1.append(" ]");
				builder1.append(Common.strip_tags(currentForumName));
				builder1.append("[/url]");
				tempSA[4] = builder1.toString();
				builder1.delete(budInitLen, builder1.length());
				tempSA[5] = reason;
				pms.setMessage(mr.getMessage(locale,"admin_posts_pm",tempSA));
				pms.setMsgfrom(username);
				pms.setMsgfromid(uid);
				pms.setMsgtoid(posts.getAuthorid());
				pms.setNew_((byte)1);
				pms.setSubject(mr.getMessage(locale,"reason_moderate_subject"));
				list.add(pms);
				hqlBuffer.append(posts.getAuthorid());
				hqlBuffer.append(",");
			}
			if(list.size()>0){
				((PmsDao)BeanFactory.getBean("pmsDao")).insertPmsList(list);
			}
			String hql = hqlBuffer.replace(hqlBuffer.length()-1, hqlBuffer.length(), ")").toString();
			((MembersDao)BeanFactory.getBean("memberDao")).updateMembers(hql);
		}
	}
	private List<Threads> getThreadsList(String[] moderate){
		StringBuffer hqlBuffer = new StringBuffer("FROM Threads AS t WHERE t.tid IN(");
		for(String tid : moderate){
			hqlBuffer.append(Integer.parseInt(tid));
			hqlBuffer.append(",");
		}
		String hql = hqlBuffer.replace(hqlBuffer.length()-1, hqlBuffer.length(), ")").toString();
		List<Threads> threadsList = ((ThreadsDao)BeanFactory.getBean("threadsDao")).getThreadsByHql(hql);
		return threadsList;
	}
	private void writeLog(Map<String,Object> parameterMap,String currentForumName,String threadName,String threadId,String operation,int timestamp){
		String log = timestamp+"\t"+(String)parameterMap.get("username")+"\t"
					+parameterMap.get("adminId")+"\t"+(String)parameterMap.get("IP")+"\t"
					+parameterMap.get("fid").toString()+"\t"+currentForumName+"\t"
					+threadId+"\t"+threadName+"\t"
					+operation+"\t"+parameterMap.get("reason");
		cn.jsprun.utils.Log.writelog("modslog",timestamp, log);
	}
	private Integer chekTime(String time,String timeoffsetSession){
		SimpleDateFormat dateFormat_temp = Common.getSimpleDateFormat("yyyy-MM-dd HH:mm", timeoffsetSession);
		SimpleDateFormat dateFormat = Common.getSimpleDateFormat("yyyy-MM-dd", timeoffsetSession);
		Long checkLong = null;
		try {
			checkLong = dateFormat_temp.parse(time).getTime();
		} catch (ParseException e) {
			try {
				checkLong = dateFormat.parse(time).getTime();
			} catch (ParseException e1) {
				return null;
			}
		}
		Calendar calendar = Common.getCalendar(timeoffsetSession);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		long tomorrowLong = calendar.getTimeInMillis();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar.add(Calendar.MONTH, 6);
		long halfYearLong = calendar.getTimeInMillis();
		if(checkLong<tomorrowLong||checkLong>halfYearLong){
			return null;
		}
		return (int)(checkLong/1000);
	}
	public OtherBaseVO getValueObject(Map transfersMap,MessageResources mr,Locale locale){
		String operation = (String)transfersMap.get("operation");
		OtherBaseVO valueObject = new OtherBaseVO();
		if(operation.equals("delpost")){
			valueObject.setSbuttonInfo(mr.getMessage(locale,"admin_delpost"));
		}else if(operation.equals("banpost")){
			valueObject.setSbuttonInfo(mr.getMessage(locale,"BNP"));
		}
		String[] modreasonsArray = ((String)transfersMap.get("modreasons")).split("\r\n");
		setTopicPublicVO(valueObject, (Forums)transfersMap.get("currentForum"), (Threads)transfersMap.get("currentThread"), (String)transfersMap.get("reasonpm"), modreasonsArray);
		valueObject.setCurrentPage((String)transfersMap.get("currentPage"));
		valueObject.setThreadPage((String)transfersMap.get("threadPage"));
		String[] pidArray = (String[])transfersMap.get("pidArray");
		List<String> postList = valueObject.getPostsIdList();
		for(int i = 0;i<pidArray.length;i++){
			postList.add(pidArray[i]);
		}
		return valueObject;
	}
	private void deleteRatelog(List<Integer> pidList){
		if(pidList==null){
			return ;
		}
		if(pidList.size()>0){
			StringBuffer pidBuffer = new StringBuffer();
			for(int i = 0;i<pidList.size();i++){
				if(pidList.get(i)!=null){
					pidBuffer.append(pidList.get(i));
					pidBuffer.append(",");
				}
			}
			String pidString = pidBuffer.substring(0,pidBuffer.length()-1);
			String sql = "DELETE FROM "+tableprefix+"ratelog WHERE pid IN ("+pidString+")";
			((DataBaseDao)BeanFactory.getBean("dataBaseDao")).executeDelete(sql);
		}
	}
	private void deleteMyposts(List<Integer> pidList){
		if(pidList==null){
			return ;
		}
		if(pidList.size()>0){
			StringBuffer pidBuffer = new StringBuffer();
			for(int i = 0;i<pidList.size();i++){
				pidBuffer.append(pidList.get(i));
				pidBuffer.append(",");
			}
			String pidString = pidBuffer.substring(0,pidBuffer.length()-1);
			String sql = "DELETE FROM "+tableprefix+"myposts WHERE pid IN ("+pidString+")";
			((DataBaseDao)BeanFactory.getBean("dataBaseDao")).executeDelete(sql);
		}
	}
	private void deleteAttachments(List<Integer> pidList){
		if(pidList==null){
			return ;
		}
		if(pidList.size()>0){
			StringBuffer pidBuffer = new StringBuffer();
			for(int i = 0;i<pidList.size();i++){
				pidBuffer.append(pidList.get(i));
				pidBuffer.append(",");
			}
			String pidString = pidBuffer.substring(0,pidBuffer.length()-1);
			String sql = "DELETE FROM "+tableprefix+"attachments WHERE pid IN ("+pidString+")";
			((DataBaseDao)BeanFactory.getBean("dataBaseDao")).executeDelete(sql);
		}
	}
	private void deleteTrades(List<Integer> pidList){
		if(pidList==null){
			return ;
		}
		if(pidList.size()>0){
			StringBuffer pidBuffer = new StringBuffer();
			for(int i = 0;i<pidList.size();i++){
				pidBuffer.append(pidList.get(i));
				pidBuffer.append(",");
			}
			String pidString = pidBuffer.substring(0,pidBuffer.length()-1);
			String sql = "DELETE FROM "+tableprefix+"trades WHERE pid IN ("+pidString+")";
			((DataBaseDao)BeanFactory.getBean("dataBaseDao")).executeDelete(sql);
		}
	}
	private void operatingAttachment(Integer pid,String attachdir_realy,Forumfields forumfields,Map<String,Object> parameterMap,Members members_reply,Map<String,String> updateField,boolean deleteAttachment,boolean updateCredit_post){
		List<Attachments> attachmentslist = ((AttachmentsDao)BeanFactory.getBean("attachmentsDao")).findByPostsID(pid);
		if(attachmentslist.size()<1){
			return ;
		}
		if (deleteAttachment) {
			for(int j = 0;j<attachmentslist.size();j++){
				Attachments attachments = attachmentslist.get(j);
				String uri = attachments.getAttachment();
				Common.dunlink(uri, attachments.getThumb(), attachments.getRemote(), attachdir_realy);
			}
		}
		if(updateCredit_post){
			PostOperating postOperating = (PostOperating)BeanFactory.getBean("postOperating");
			postOperating.setMembersExtcredit(forumfields, (String) parameterMap.get("creditspolicy"), members_reply, updateField, postOperating.attachment, false,1);
		}
	}
	private Threads getThreadsMirror(Threads threads){
		Threads mirror = new Threads();
		mirror.setViews(0);
		mirror.setReplies(0);
		mirror.setDisplayorder((byte) 0);
		mirror.setDigest((byte) 0);
		mirror.setClosed(threads.getTid());
		mirror.setSpecial((byte) 0);
		mirror.setAttachment((byte) 0);
		mirror.setAuthor(threads.getAuthor());
		mirror.setAuthorid(threads.getAuthorid());
		mirror.setBlog(threads.getBlog());
		mirror.setDateline(threads.getDateline());
		mirror.setFid(threads.getFid());
		mirror.setHighlight(threads.getHighlight());
		mirror.setTypeid(threads.getTypeid());
		mirror.setSubscribed(threads.getSubscribed());
		mirror.setSubject(threads.getSubject());
		mirror.setReadperm(threads.getReadperm());
		mirror.setRate(threads.getRate());
		mirror.setIconid(threads.getIconid());
		mirror.setLastpost(threads.getLastpost());
		mirror.setLastposter(threads.getLastposter());
		mirror.setModerated(threads.getModerated());
		mirror.setPrice(threads.getPrice());
		return mirror;
	}
	private void updateForumCount(Object fid,DataBaseDao dataBaseDao,MessageResources mr,Locale locale){
		List<Map<String,String>> tempML = dataBaseDao.executeQuery("SELECT COUNT(*) AS threadcount, SUM(t.replies)+COUNT(*) AS replycount " +
				"FROM "+tableprefix+"threads t, "+tableprefix+"forums f " +
				"WHERE f.fid=? AND t.fid=f.fid AND t.displayorder>='0'",String.valueOf(fid));
		Map<String,String> tempM = null;
		String threadcount = null;
		String replycount = null;
		if(tempML!=null && tempML.size()>0){
			tempM = tempML.get(0);
			threadcount = tempM.get("threadcount");
			replycount = tempM.get("replycount");
		}
		if (threadcount == null) {
			threadcount = "0";
		}
		if(replycount == null){
			replycount = "0";
		}
		tempML = dataBaseDao.executeQuery("SELECT tid, subject, author, lastpost, lastposter FROM "+tableprefix+"threads " +
				"WHERE fid=? AND displayorder>='0' ORDER BY lastpost DESC LIMIT 1",String.valueOf(fid));
		String lastpost = "";
		if(tempML!=null && tempML.size()>0){
			tempM = tempML.get(0);
			String tempS = tempM.get("author");
			if(tempS == null || tempS.equals("")){
				tempS = mr.getMessage(locale,"anonymous");
			}else{
				tempS = Common.addslashes(tempM.get("lastposter"));
			}
			lastpost = tempM.get("tid")+"\t"+Common.cutstr(Common.addslashes(tempM.get("subject").replaceAll("\t", " ")),40,null)+"\t"+tempM.get("lastpost")+"\t"+tempS;
		}
		dataBaseDao.execute("UPDATE "+tableprefix+"forums SET posts='"+replycount+"', threads='"+threadcount+"', lastpost='"+lastpost+"' WHERE fid=?",String.valueOf(fid));
	}
}
