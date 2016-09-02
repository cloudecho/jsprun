<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:directive.page import="cn.jsprun.utils.BeanFactory"/>
<jsp:directive.page import="cn.jsprun.utils.Cache"/>
<jsp:directive.page import="cn.jsprun.domain.Members"/>
<jsp:directive.page import="cn.jsprun.dao.MembersDao"/>
<jsp:directive.page import="java.lang.reflect.Method"/>
<jsp:directive.page import="cn.jsprun.utils.DataParse"/>
<%@page import="cn.jsprun.domain.Threads"%>
<%@page import="cn.jsprun.dao.ThreadsDao"%>
<%@page import="cn.jsprun.dao.PostsDao"%>
<%@page import="cn.jsprun.domain.Posts"%>
<%@page import="cn.jsprun.dao.ForumsDao"%>
<%@page import="cn.jsprun.domain.Forums"%>
<%@page import="cn.jsprun.foreg.service.PostOperating"%>
<%@page import="cn.jsprun.domain.Forumfields"%>
<%@page import="cn.jsprun.dao.ForumfieldsDao"%>
<%@page import="cn.jsprun.domain.Attachments"%>
<%@page import="cn.jsprun.dao.AttachmentsDao"%>
<%@page import="java.io.File"%>
<%@page import="cn.jsprun.utils.Common"%>
<%@page import="cn.jsprun.dao.CronsDao"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="cn.jsprun.utils.JspRunConfig"%>
<%!
	private String tablepre = "jrun_"; 
	private CronsDao cronsDao = ((CronsDao)BeanFactory.getBean("cronsSetDao"));
	private ThreadsDao threadsDao = (ThreadsDao)BeanFactory.getBean("threadsDao");
	private MembersDao membersDao = (MembersDao)BeanFactory.getBean("memberDao");
	
	private void updateCredits(Map<Integer,Integer> memberId_digestMap, Map digestMap){
	
		List<Integer> membersIdList = new ArrayList<Integer>();
		Iterator<Integer> iterator = memberId_digestMap.keySet().iterator();
		while(iterator.hasNext()){
			Integer key = iterator.next();
			membersIdList.add(key);
		}
		List<Members> membersList = membersDao.getMemberListWithMemberIdList(membersIdList);
		
		for(int i = 0;i<membersList.size();i++){
			Members members = membersList.get(i);
			Iterator<Integer> iterator2 = digestMap.keySet().iterator();
			while(iterator2.hasNext()){
				Integer kye = iterator2.next();
				try{
					Method method_get = Members.class.getMethod("getExtcredits"+kye);
					
					Method method_set = Members.class.getMethod("setExtcredits"+kye,Integer.class);
					
					method_set.invoke(members,((Integer)method_get.invoke(members)- memberId_digestMap.get(members.getUid()) * Integer.valueOf(digestMap.get(kye).toString())));
				}catch(Exception exception){
					exception.printStackTrace();
				}
			}
		}
	}
	private void createMirror(Threads threads){
		
		
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
		threadsDao.addThread(mirror);
	}
	
	
	
	private void operatingAttachment(Integer pid,String attachurl_realy,Forumfields forumfields,String creditspolicy,Members members_reply,Map<String,String> updateField,boolean deleteAttachment,boolean updateCredit_post){

		List<Attachments> attachmentslist = ((AttachmentsDao)BeanFactory.getBean("attachmentsDao")).findByPostsID(pid);
		if(attachmentslist.size()<1){
			return ;
		}
		
		if (deleteAttachment) {
			for(int j = 0;j<attachmentslist.size();j++){
				Attachments attachments = attachmentslist.get(j);
				String uri = attachments.getAttachment();
				File file = new File(attachurl_realy+"/"+uri);
				if(file.isFile()){
					if(!file.delete()){
						try{
							throw new Exception("attachment delete failed");
						}catch(Exception exception){
							exception.printStackTrace();
						}
					}
				}else{
					try{
						throw new Exception("attachment directory is error");
					}catch(Exception exception){
						exception.printStackTrace();
					}
				}
				
				if(attachments.getThumb()==1){
					String imagePostfix = uri.substring(uri.lastIndexOf("."),uri.length());
					File breviaryImage = new File(attachurl_realy+"/"+uri+".thumb"+imagePostfix);
					if(breviaryImage.isFile()){
						if(!breviaryImage.delete()){
							try{
								throw new Exception("attachment thumb delete failed");
							}catch(Exception exception){
								exception.printStackTrace();
							}
						}
					}
				}
				
			}
		}
		
		
		if(updateCredit_post){
			PostOperating postOperating = (PostOperating)BeanFactory.getBean("postOperating");
			postOperating.setMembersExtcredit(forumfields, creditspolicy, members_reply, updateField, postOperating.attachment, false,1);
		}
		
	}
	
	private void deleteRatelog(List<Integer> pidList,Connection connection) throws SQLException{
		if(pidList==null){
			try{
				throw new Exception("pidList IS NULL");
			}catch(Exception exception){
				exception.printStackTrace();
				return ;
			}
		}
		if(pidList.size()>0){
			StringBuffer pidBuffer = new StringBuffer();
			for(int i = 0;i<pidList.size();i++){
				if(pidList.get(i)!=null){
					pidBuffer.append(pidList.get(i)+",");
				}
			}
			String pidString = pidBuffer.substring(0,pidBuffer.length()-1);
			String sql = "DELETE FROM "+tablepre+"ratelog WHERE pid IN ("+pidString+")";
			cronsDao.execute(connection,sql);
		}
	}
	
	private void deleteMyposts(List<Integer> pidList,Connection connection) throws SQLException{
		if(pidList==null){
			try{
				throw new Exception("pidList IS NULL");
			}catch(Exception exception){
				exception.printStackTrace();
				return ;
			}
		}
		if(pidList.size()>0){
			StringBuffer pidBuffer = new StringBuffer();
			for(int i = 0;i<pidList.size();i++){
				pidBuffer.append(pidList.get(i)+",");
			}
			String pidString = pidBuffer.substring(0,pidBuffer.length()-1);
			String sql = "DELETE FROM "+tablepre+"myposts WHERE pid IN ("+pidString+")";
			cronsDao.execute(connection,sql);
		}
	}
	
	private void deleteAttachments(List<Integer> pidList,Connection connection) throws SQLException{
		if(pidList==null){
			try{
				throw new Exception("pidList IS NULL");
			}catch(Exception exception){
				exception.printStackTrace();
				return ;
			}
		}
		if(pidList.size()>0){
			StringBuffer pidBuffer = new StringBuffer();
			for(int i = 0;i<pidList.size();i++){
				pidBuffer.append(pidList.get(i)+",");
			}
			String pidString = pidBuffer.substring(0,pidBuffer.length()-1);
			String sql = "DELETE FROM "+tablepre+"attachments WHERE pid IN ("+pidString+")";
			cronsDao.execute(connection,sql);
		}
	}
	
	private void updateForumCount(Object fid, Connection connection) throws SQLException{
		List<Map<String,String>> tempML = cronsDao.executeQuery(connection,"SELECT COUNT(*) AS threadcount, SUM(t.replies)+COUNT(*) AS replycount " +
				"FROM "+tablepre+"threads t, "+tablepre+"forums f " +
				"WHERE f.fid='"+fid+"' AND t.fid=f.fid AND t.displayorder>='0'");
		Map<String,String> tempM = null;
		String threadcount = "0";
		String replycount = "0";
		if(tempML!=null && tempML.size()>0){
			tempM = tempML.get(0);
			String tempS = tempM.get("threadcount");
			if(tempS != null){
				threadcount = tempS;
			}
			tempS = tempM.get("replycount");
			if(tempS != null){
				replycount = tempS;
			}
		}
		
		tempML = cronsDao.executeQuery(connection,"SELECT tid, subject, author, lastpost, lastposter FROM "+tablepre+"threads " +
				"WHERE fid='"+fid+"' AND displayorder>='0' ORDER BY lastpost DESC LIMIT 1");
		String lastpost = "";
		if(tempML!=null && tempML.size()>0){
			tempM = tempML.get(0);
			String tempS = tempM.get("author");
			if(tempS == null || tempS.equals("")){
				tempS = "";
			}else{
				tempS = Common.addslashes(tempM.get("lastposter"));
			}
			lastpost = tempM.get("tid")+"\t"+Common.addslashes(tempM.get("subject"))+"\t"+tempM.get("lastpost")+"\t"+tempS;
		}
		cronsDao.execute(connection,"UPDATE "+tablepre+"forums SET posts='"+replycount+"', threads='"+threadcount+"', lastpost='"+lastpost+"' WHERE fid='"+fid+"'");
	}
	%>
<%
	int timestamp=(Integer)request.getAttribute("timestamp");
	Connection connection = (Connection)request.getAttribute("connection");
	
	Map<String,List<String>> actionMap = new HashMap<String,List<String>>();
	List<Map<String,String>> queryList = cronsDao.executeQuery(connection,"SELECT * FROM "+tablepre+"threadsmod WHERE expiration>'0' AND expiration<='"+timestamp+"' AND status='1'");
	if(queryList!=null&&queryList.size()>0){
		List<String> ESTList = null;
		List<String> EHLList = null;
		List<String> ECLList = null;
		List<String> EOPList = null;
		List<String> EDIList = null;
		for(int i = 0;i<queryList.size();i++){
			Map<String,String> queryMap = queryList.get(i);
			if(queryMap!=null){
				String action = queryMap.get("action");
				if(action.equals("EST")||action.equals("TOK")){
					if(ESTList==null){
						ESTList = new ArrayList<String>();
					}
					ESTList.add(queryMap.get("tid"));
					if(actionMap.get("UES")==null){
						actionMap.put("UES",ESTList);
					}
				}else if(action.equals("EHL")||action.equals("CCK")){
					if(EHLList==null){
						EHLList = new ArrayList<String>();
					}
					EHLList.add(queryMap.get("tid"));
					if(actionMap.get("UEH")==null){
						actionMap.put("UEH",EHLList);
					}
				}else if(action.equals("ECL")||action.equals("CLK")){
					if(ECLList==null){
						ECLList = new ArrayList<String>();
					}
					ECLList.add(queryMap.get("tid"));
					if(actionMap.get("UEC")==null){
						actionMap.put("UEC",ECLList);
					}
				}else if(action.equals("EOP")){
					if(EOPList==null){
						EOPList = new ArrayList<String>();
					}
					EOPList.add(queryMap.get("tid"));
					if(actionMap.get("UEO")==null){
						actionMap.put("UEO",EOPList);
					}
				}else if(action.equals("EDI")){
					if(EDIList==null){
						EDIList = new ArrayList<String>();
					}
					EDIList.add(queryMap.get("tid"));
					if(actionMap.get("UED")==null){
						actionMap.put("UED",EDIList);
					}
				}
				
				  else if("TMV".equals(action)){
					String operationTid = queryMap.get("tid");
				  	String opType = queryMap.get("remark");
					Threads oldThreads = threadsDao.findThreadsBytid(Integer.valueOf(operationTid));
					int replies = 0;
					short nowFid = 0;
					int updateThreadCount = 1;
					if(oldThreads!=null){
						nowFid = oldThreads.getFid();
						replies = oldThreads.getReplies();
					}else{
						cronsDao.execute(connection,"UPDATE "+tablepre+"threadsmod SET status=0 WHERE tid="+operationTid+" AND status=1 AND action='TMV'");
						continue;
					}
					String moveTO = null;
					if(opType.startsWith("m")){
						moveTO = opType.replace("m", "");
					}else{
						moveTO = opType.replace("r", "");
						updateThreadCount = 0;
						createMirror(oldThreads);
					}
					
					String displayorderadd = "";
					String adminId = null;
					List<Map<String,String>> adminidMapList = cronsDao.executeQuery(connection,"SELECT adminid FROM "+tablepre+"members WHERE uid="+queryMap.get("uid"));
					if(adminidMapList!=null&&adminidMapList.size()>0){
						adminId = adminidMapList.get(0).get("adminid");
					}
					if(adminId!=null&&adminId.equals("3")){
						displayorderadd = ", displayorder='0'";
					}
					
					String update_threadsSQL = "UPDATE "+tablepre+"threads SET fid='"+moveTO+"', moderated='1' "+displayorderadd+" WHERE tid="+operationTid;
					String update_postsSQL = "UPDATE "+tablepre+"posts SET fid='"+moveTO+"' WHERE tid="+operationTid;
					cronsDao.execute(connection,update_threadsSQL);
					cronsDao.execute(connection,update_postsSQL);
					
					
					
					
					
					updateForumCount(nowFid,connection);
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					updateForumCount(moveTO,connection);
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					cronsDao.execute(connection,"UPDATE "+tablepre+"threadsmod SET status=0 WHERE tid="+operationTid+" AND status=1 AND action='TMV'");
					
					
				}else if(action.equals("TDEL")){
					
					Integer tid = Integer.valueOf(queryMap.get("tid"));
					
					Threads threads_old = threadsDao.findByTid(tid);
					if(threads_old==null){
						cronsDao.execute(connection,"DELETE FROM "+tablepre+"threadsmod WHERE status=1 AND action='TDEL' AND  tid="+tid);
						continue;
					}
					
					
					
					Forums forums = null;
					Short fid = threads_old.getFid();
					if(fid!=null){
						forums = ((ForumsDao)BeanFactory.getBean("forumsDao")).findById(fid);
					}else{
						cronsDao.execute(connection,"DELETE FROM "+tablepre+"threadsmod WHERE status=1 AND action='TDEL' AND  tid="+tid);
						continue;
					}
					byte invisble = 0;
					if(forums != null){
						invisble = forums.getRecyclebin(); 
					}
					
					
					
					Map<String,String> settingsMap = (Map<String,String>)application.getAttribute("settings");
					
					
					
					int losslessdel = Integer.parseInt(settingsMap.get("losslessdel"));
					Long nowSubtrationLoss = (timestamp - losslessdel*86400)*1000L; 
					
					
					
					String creditspolicy = settingsMap.get("creditspolicy");
					
					
					
					String creditsformula = settingsMap.get("creditsformula");
					
					
					
					
					boolean updateCredit = losslessdel==0||nowSubtrationLoss<threads_old.getDateline()*1000L;
					
					
					
					String attachurl_realy = JspRunConfig.realPath+settingsMap.get("attachdir");
					
					
					Map<String, String> updateField = new HashMap<String, String>();
					Map<String, String> updateField_posts = new HashMap<String, String>();
					updateField.put("posts", "posts");
					updateField_posts.put("posts", "posts");
					
					PostOperating postOperating = (PostOperating)BeanFactory.getBean("postOperating");
					Forumfields forumfields = ((ForumfieldsDao)BeanFactory.getBean("forumfieldsDao")).findById(fid);
					MembersDao membersDao = ((MembersDao)BeanFactory.getBean("memberDao"));
					PostsDao postsDao = ((PostsDao)BeanFactory.getBean("postsDao"));
					
					Members members = membersDao.findMemberById(threads_old.getAuthorid());
					
					if(members!=null){
						
						if (threads_old.getDigest() != 0) {
							updateField.put("digestposts", "digestposts");
							postOperating.setMembersExtcredit(forumfields, creditspolicy, members, updateField, postOperating.stick, false,1);
							members.setDigestposts(members.getDigestposts() - 1);
						}
						
						
						
						int tempPostNum = members.getPosts() - 1;
						members.setPosts(tempPostNum>0?tempPostNum:0);
						if(updateCredit){
							postOperating.setMembersExtcredit(forumfields, creditspolicy, members, updateField, postOperating.posts, false,1);
						}
						

						
						Posts postsTemp = postsDao.findByTid(tid);
						if(postsTemp!=null){
							Integer threadPid = postsTemp.getPid();
							operatingAttachment(threadPid, attachurl_realy, forumfields, creditspolicy, members, updateField,invisble == 0,updateCredit);
						}
						
						
						
						postOperating.setCredits(creditsformula, members,updateField);
						
						
						membersDao.modifyMember(members);
					}
					
					
					List<Posts> postsList = postsDao.getPostsListByTid(tid);
					
					
					
					List<Integer> postsIdList = new ArrayList<Integer>(); 
					StringBuffer pidBuffer = new StringBuffer();
					if(postsList!=null){
						for (Posts posts : postsList) {
							postsIdList.add(posts.getPid());
							
							
							boolean updateCredit_post = losslessdel==0||nowSubtrationLoss<posts.getDateline()*1000L;
							
							pidBuffer.append(posts.getPid()+",");
							
							if (posts.getFirst().byteValue()==1) {
								continue;
							} else {
								Members members_reply = ((MembersDao)BeanFactory.getBean("memberDao")).findMemberById(posts.getAuthorid());
								if(members_reply!=null){
									
									members_reply.setPosts(members_reply.getPosts() >= 1?members_reply.getPosts() - 1:0);
									
									
									
									if(updateCredit_post){
										postOperating.setMembersExtcredit(forumfields, creditspolicy, members_reply, updateField, postOperating.reply, false,1);
									}
									
									
									
									operatingAttachment(posts.getPid(), attachurl_realy, forumfields,creditspolicy, members_reply, updateField,invisble == 0,updateCredit_post);
									
									
									
									postOperating.setCredits(creditsformula,members_reply, updateField_posts);
									
									
									((MembersDao)BeanFactory.getBean("memberDao")).modifyMember(members_reply);
								}
							}
						}
					}
					
					if (invisble != 0) {
						cronsDao.execute(connection,"INSERT INTO "+tablepre+"threadsmod (tid, uid, username, dateline, expiration, action, status,magicid) VALUES("+tid+","+queryMap.get("uid")+",'"+queryMap.get("username")+"',"+timestamp+",0,'DEL',1,0)");
					}
					
					
					if (invisble == 0) {
						cronsDao.execute(connection,"DELETE FROM "+tablepre+"threads WHERE tid="+tid);
						cronsDao.execute(connection,"DELETE FROM "+tablepre+"trades WHERE tid="+tid);
					}else{
						cronsDao.execute(connection,"UPDATE "+tablepre+"threads AS t SET t.displayorder=-1 , t.moderated=1 WHERE t.tid="+tid);
					}
					if (invisble == 0) {
						if(pidBuffer.length()!=0){
							StringBuffer sql = new StringBuffer("DELETE FROM "+tablepre+"posts WHERE pid IN(");
							sql.append(pidBuffer);
							int sqlL = sql.length();
							sql.replace(sqlL-1, sqlL, ")");
							cronsDao.execute(connection,sql.toString());
						}
					}else{
						if(pidBuffer.length()!=0){
							StringBuffer sql = new StringBuffer("UPDATE "+tablepre+"posts AS p SET p.invisible=-1 WHERE p.pid IN(");
							sql.append(pidBuffer);
							int sqlL = sql.length();
							sql.replace(sqlL-1, sqlL, ")");
							cronsDao.execute(connection,sql.toString());
						}
					}
					
					
					
					if(forums!=null){
						updateForumCount(fid,connection);
					}
					
					
					
					if (invisble == 0) {
						deleteRatelog(postsIdList,connection);
						deleteMyposts(postsIdList,connection);
						deleteAttachments(postsIdList,connection);
					}
					
					
					
					cronsDao.execute(connection,"DELETE FROM "+tablepre+"threadsmod WHERE status=1 AND action='TDEL' AND  tid="+tid);
					
				}
			}
		}
		
		
		if(actionMap.size()>0){
			Iterator<String> iterator = actionMap.keySet().iterator();
			while(iterator.hasNext()){
				String key_action = iterator.next();
				List<String> tidList = actionMap.get(key_action);
				StringBuffer tidBuffer = new StringBuffer();
				for(int i = 0;i<tidList.size();i++){
					tidBuffer.append(tidList.get(i)+",");
				}
				String tidString = tidBuffer.substring(0,tidBuffer.length()-1);
				if(key_action.equals("UES")){
					cronsDao.execute(connection,"UPDATE "+tablepre+"threads SET displayorder='0' WHERE tid IN ("+tidString+")");
					cronsDao.execute(connection,"UPDATE "+tablepre+"threadsmod SET status='0' WHERE tid IN ("+tidString+") AND action IN ('EST', 'TOK')");
					
					Cache.updateCache("forumdisplay");
				}else if(key_action.equals("UEH")){
					cronsDao.execute(connection,"UPDATE "+tablepre+"threads SET highlight='0' WHERE tid IN ("+tidString+")");
					cronsDao.execute(connection,"UPDATE "+tablepre+"threadsmod SET status='0' WHERE tid IN ("+tidString+") AND action IN ('EHL', 'CCK')");
				}else if(key_action.equals("UEC")||key_action.equals("UEO")){
					Integer closed = key_action.equals("UEO")?-1:0;
					cronsDao.execute(connection,"UPDATE "+tablepre+"threads SET closed='"+closed+"' WHERE tid IN ("+tidString+")");
					cronsDao.execute(connection,"UPDATE "+tablepre+"threadsmod SET status='0' WHERE tid IN ("+tidString+") AND action IN ('EOP', 'ECL', 'CLK')");
				}else if(key_action.equals("UED")){
					cronsDao.execute(connection,"UPDATE "+tablepre+"threadsmod SET status='0' WHERE tid IN ("+tidString+") AND action='EDI'");
					
					StringBuffer authorIdBuffer = new StringBuffer();
					List<Integer> authorIdList = new ArrayList<Integer>();
					Map<Integer,Integer> digestMap = new HashMap<Integer,Integer>();
					List<Map<String,String>> threadsResultList = cronsDao.executeQuery(connection,"SELECT authorid, digest FROM "+tablepre+"threads WHERE tid IN ("+tidString+")");
					if(threadsResultList!=null&&threadsResultList.size()>0){
						for(int i = 0;i<threadsResultList.size();i++){
							Map<String,String> threadsResultMap = threadsResultList.get(i);
							
							authorIdList.add(Integer.valueOf(threadsResultMap.get("authorid")));
							authorIdBuffer.append(threadsResultMap.get("authorid")+",");
							
							if(digestMap.get(threadsResultMap.get("authorid"))==null){
						Integer authodId = Integer.valueOf(threadsResultMap.get("authorid"));
						digestMap.put(authodId,Integer.valueOf(threadsResultMap.get("digest")));
							}else{
						Integer authodId = Integer.valueOf(threadsResultMap.get("authorid"));
						Integer digest = Integer.valueOf(threadsResultMap.get("digest"));
						digestMap.put(authodId,digestMap.get(authodId)+digest);
							}
						}
						String authorIdString = authorIdBuffer.substring(0,authorIdBuffer.length()-1);
						cronsDao.execute(connection,"UPDATE "+tablepre+"members SET digestposts=digestposts-1 WHERE uid IN ("+authorIdString+")");
						
						Map digestMap_creditspolicy = null;
						List<Map<String,String>> tempList = cronsDao.executeQuery(connection,"SELECT value FROM "+tablepre+"settings WHERE variable='creditspolicy'");
						if(tempList!=null&&tempList.size()!=0){
							Map<String,String> tempMap = tempList.get(0);
							if(tempMap!=null){
						String value = tempMap.get("value");
						Map stringToMap = ((DataParse)BeanFactory.getBean("dataParse")).characterParse(value,false);
						digestMap_creditspolicy = (Map)stringToMap.get("digest");
							}
						}
						updateCredits(digestMap,digestMap_creditspolicy);
						cronsDao.execute(connection,"UPDATE "+tablepre+"threads SET digest='0' WHERE tid IN ("+tidString+")");
					}
				}
			}
		}
	}
	RequestDispatcher dispatcher = request.getRequestDispatcher("/include/crons/setNextrun.jsp");
	try {
		dispatcher.include(request, response);
	} catch (Exception e) {
		e.printStackTrace();
	} 
	Map<String,String> crons = (Map<String,String>)request.getAttribute("crons");
	if("0".equals(crons.get("available"))){
		cronsDao.execute(connection,"UPDATE "+tablepre+"crons SET available='0' WHERE cronid="+crons.get("cronid"));
	}else{
		cronsDao.execute(connection,"UPDATE "+tablepre+"crons SET lastrun='"+timestamp+"',nextrun='"+crons.get("nextrun")+"' WHERE cronid="+crons.get("cronid"));
	}
%>