<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	
	<c:when test="${param.action ==null || param.action=='home'}">
		<jsp:include page="/domain.do?action=callBackBBSData"/>
		<jsp:forward page="/home.do?action=home"/>
	</c:when>
	<c:when test="${param.action=='adminnote'}"><jsp:forward page="/home.do?action=home"/></c:when>
	<c:when test="${members.adminid==1}">
		<c:choose>
			
			<c:when test="${param.action=='settings'}">
				<c:choose>
		    		<c:when test="${param['do']=='basic'}"><jsp:forward page="/basicsettings.do?param=basic"/></c:when>
		    		<c:when test="${param['do']=='access'}"><jsp:forward page="/basicsettings.do?param=access"/></c:when>
		    		<c:when test="${param['do']=='styles'}"><jsp:forward page="/basicsettings.do?param=styles" /></c:when>
		    		<c:when test="${param['do']=='seo'}"><jsp:forward page="/basicsettings.do?param=seo" /></c:when>
		    		<c:when test="${param['do']=='cachethread'}"><jsp:forward page="/basicsettings.do?param=cachethread"/></c:when>
		    		<c:when test="${param['do']=='functions'}"><jsp:forward page="/basicsettings.do?param=functions"/></c:when>
		    		
		    		<c:when test="${param['do']=='credits'}"><jsp:forward page="/basicsettings.do?param=toCredits"/></c:when>
		    		<c:when test="${param['do']=='saveProject'}"><jsp:forward page="/basicsettings.do?param=saveProject"/></c:when>
		    		<c:when test="${param['do']=='particular'}"><jsp:forward page="/basicsettings.do?param=toCreditParticularse"/></c:when>
		    		<c:when test="${param['do']=='creditParticularse'}"><jsp:forward page="/basicsettings.do?param=creditParticularse"/></c:when>
		    		<c:when test="${param['do']=='bankuaiSetting'}"><jsp:forward page="/basicsettings.do?param=bankuaiSetting"/></c:when>
		    		<c:when test="${param['do']=='usergroupSetting'}"><jsp:forward page="/basicsettings.do?param=usergroupSetting"/></c:when>
		    		<c:when test="${param['do']=='resetCredit'}"><jsp:forward page="/basicsettings.do?param=resetCredit"/></c:when>
		    		
		    		<c:when test="${param['do']=='ecommerce'}"><jsp:forward page="/basicsettings.do?param=ecommerce" /></c:when>
		    		<c:when test="${param['do']=='serveropti'}"><jsp:forward page="/basicsettings.do?param=serveropti"/></c:when>
		    		<c:when test="${param['do']=='mail'}"><jsp:forward page="/basicsettings.do?param=mail"/></c:when>
		    		<c:when test="${param['do']=='seccode'}"><jsp:forward page="/basicsettings.do?param=seccode"/></c:when>
		    		<c:when test="${param['do']=='secqaa'}"><jsp:forward page="/basicsettings.do?param=secqaa"/></c:when>
		    		<c:when test="${param['do']=='datetime'}"><jsp:forward page="/basicsettings.do?param=datetime"/></c:when>
		    		<c:when test="${param['do']=='permissions'}"><jsp:forward page="/basicsettings.do?param=permissions"/></c:when>
		    		<c:when test="${param['do']=='attachments'}"><jsp:forward page="/basicsettings.do?param=attachments"/></c:when>
		    		<c:when test="${param['do']=='language'}"><jsp:forward page="/basicsettings.do?param=language"/></c:when>
		    		<c:when test="${param['do']=='wap'}"><jsp:forward page="/basicsettings.do?param=wap"/></c:when>
		    		<c:when test="${param['do']=='space'}"><jsp:forward page="/basicsettings.do?param=space"/></c:when>
		    	</c:choose>
			</c:when>
			<c:when test="${param.action=='imagepreview'}"><jsp:forward page="/basicsettings.do?param=imagepreview"/></c:when>
			<c:when test="${param.action=='ftpcheck'}"><jsp:forward page="/basicsettings.do?param=ftpcheck"/></c:when>
			
			<c:when test="${param.action=='forumsedit'}"><jsp:forward page="/forumsedit.do?action=forumsedit"/></c:when>
			<c:when test="${param.action=='forumadd'}"><jsp:forward page="/forumsedit.do?action=forumadd"/></c:when>
			<c:when test="${param.action=='forumdetail'}"><jsp:forward page="/forumsedit.do?action=forumdetail"/></c:when>
			<c:when test="${param.action=='projectadd'}"><jsp:forward page="/forumsedit.do?action=projectadd"/></c:when>
			<c:when test="${param.action=='forumcopy'}"><jsp:forward page="/forumsedit.do?action=forumcopy" /></c:when>
			<c:when test="${param.action=='forumdelete'}"><jsp:forward page="/forumsedit.do?action=removeForum" /></c:when>
			<c:when test="${param.action=='moderators'}"><jsp:forward page="/forumsedit.do?action=moderators" /></c:when>
			<c:when test="${param.action=='forumsmerge'}"><jsp:forward page="/forumsedit.do?action=forumsmerge" /></c:when>
			<c:when test="${param.action=='threadtypes'}"><jsp:forward page="/forumsedit.do?action=threadtypes" /></c:when>
			<c:when test="${param.action=='typedetail'}"><jsp:forward page="/forumsedit.do?action=typedetail" /></c:when>
			<c:when test="${param.action=='typemodel'}"><jsp:forward page="/forumsedit.do?action=typemodel" /></c:when>
			<c:when test="${param.action=='modeldetail'}"><jsp:forward page="/forumsedit.do?action=modeldetail" /></c:when>
			<c:when test="${param.action=='typeoption'}"><jsp:forward page="/forumsedit.do?action=typeoption" /></c:when>
			<c:when test="${param.action=='optiondetail'}"><jsp:forward page="/forumsedit.do?action=optiondetail" /></c:when>
			<c:when test="${param.action=='styles'}"><jsp:forward page="/forumsedit.do?action=styles" /></c:when>
			<c:when test="${param.action=='templates'}"><jsp:forward page="/forumsedit.do?action=templates" /></c:when>
			<c:when test="${param.action=='tpladd'}"><jsp:forward page="/forumsedit.do?action=tpladd" /></c:when>
			<c:when test="${param.action=='tpledit'}"><jsp:forward page="/forumsedit.do?action=toTpledit" /></c:when>
			<c:when test="${param.action=='modtpledit'}"><jsp:forward page="/forumsedit.do?action=tpledit" /></c:when>
			
			<c:when test="${param.action=='memberadd'}"><jsp:forward page="/user.do?useraction=memberAdd"/></c:when>
			<c:when test="${param.action=='members'&& param.deletesubmit=='yes'}"><jsp:forward page="/user.do?useraction=deleteMembers"/></c:when>
			<c:when test="${param.action=='members'&& param.searchsubmit==null&&param.submitname==null}"><jsp:forward page="/user.do?useraction=membersInit"/></c:when>
			<c:when test="${param.action=='members'&&param.searchsubmit!=null}"><jsp:forward page="/user.do?useraction=searchMembers"/></c:when>
			<c:when test="${param.action=='banmember'}"><jsp:forward page="/user.do?useraction=tobanmember"/></c:when>
			<c:when test="${param.action=='membersmerge'}"><jsp:forward page="/admin/page/members/membersmerge.jsp"/></c:when>
			<c:when test="${param.action=='ipban'}"><jsp:forward page="/user.do?useraction=banIp&init=yes"/></c:when>
			<c:when test="${param.action=='members'&&param.submitname=='creditsubmit'}"><jsp:forward page="/user.do?useraction=creditSubmit"/></c:when>
			<c:when test="${param.action=='modmembers'&&param.search=='yes'}"><jsp:forward page="/user.do?useraction=searchVlidateMemeber"/></c:when>
			<c:when test="${param.action=='modmembers'&&param.validate=='yes'}"><jsp:forward page="/user.do?useraction=validateMembers"/></c:when>
			<c:when test="${param.action=='modmembers'&&param.delete=='yes'}"><jsp:forward page="/user.do?useraction=deleteMemberAndValidate"/></c:when>
			<c:when test="${param.action=='modmembers'}"><jsp:forward page="/user.do?useraction=modMembers"/></c:when>
			<c:when test="${param.action=='profilefields'}"><jsp:forward page="/user.do?useraction=profileFields&init=yes"/></c:when>
			<c:when test="${param.action=='admingroups'}"><jsp:forward page="/user.do?useraction=adminGroups"/></c:when>
			<c:when test="${param.action=='usergroups'}"><jsp:forward page="/user.do?useraction=userGroups"/></c:when>
			<c:when test="${param.action=='ranks'}"><jsp:forward page="/user.do?useraction=goEditRanks"/></c:when>
			<c:when test="${param.action=='editranks'}"><jsp:forward page="/user.do?useraction=editRanks"/></c:when>
			<c:when test="${param.action=='toeditgroups'}"><jsp:forward page="/user.do?useraction=goEditGroupOnUser"/></c:when>
			<c:when test="${param.action=='editgroups'}"><jsp:forward page="/user.do?useraction=editMemberUsergroup"/></c:when>
			<c:when test="${param.action=='toaccess'}"><jsp:forward page="/user.do?useraction=goEditPurview"/></c:when>
			<c:when test="${param.action=='access'}"><jsp:forward page="/user.do?useraction=editPurview"/></c:when>
			<c:when test="${param.action=='toeditcredits'}"><jsp:forward page="/user.do?useraction=goEditCredits"/></c:when>
			<c:when test="${param.action=='editcredits'}"><jsp:forward page="/user.do?useraction=editCredits"/></c:when>
			<c:when test="${param.action=='toedituserinfo'}"><jsp:forward page="/user.do?useraction=goEditUserInfo"/></c:when>
			<c:when test="${param.action=='edituserinfo'}"><jsp:forward page="/user.do?useraction=editUserInfo"/></c:when>
			<c:when test="${param.action=='toeditmedal'}"><jsp:forward page="/user.do?useraction=goEditMedals"/></c:when>
			<c:when test="${param.action=='editmedal'}"><jsp:forward page="/user.do?useraction=editmedal"/></c:when>
			<c:when test="${param.action=='todeletemember'}"><jsp:forward page="/user.do?useraction=editDeleteMembers"/></c:when>
			<c:when test="${param.action=='deletemember'}"><jsp:forward page="/user.do?useraction=deleteMembers"/></c:when>
			<c:when test="${param.action=='editbanmember'}"><jsp:forward page="/user.do?useraction=banMember"/></c:when>
			<c:when test="${param.action=='editmembersmerge'}"><jsp:forward page="/user.do?useraction=membersMerge"/></c:when>
			<c:when test="${param.action=='editipban'}"><jsp:forward page="/user.do?useraction=banIp"/></c:when>
			<c:when test="${param.action=='creditsubmit'}"><jsp:forward page="/user.do?useraction=creditSubmit"/></c:when>
			<c:when test="${param.action=='editcreditsubmit'}"><jsp:forward page="/user.do?useraction=editCredit"/></c:when>
			<c:when test="${param.action=='editprofilefields'}"><jsp:forward page="/user.do?useraction=profileFields"/></c:when>
			<c:when test="${param.action=='tousergroupinfo'}"><jsp:forward page="/user.do?useraction=userGroupInfo"/></c:when>
			<c:when test="${param.action=='editusergroupinfo'}"><jsp:forward page="/user.do?useraction=editUserGroupInfo"/></c:when>
			<c:when test="${param.action=='forusergroups'}"><jsp:forward page="/user.do?useraction=forUsergroups"/></c:when>
			<c:when test="${param.action=='adduserproject'}"><jsp:forward page="/user.do?useraction=addUserProject"/></c:when>
			
			<c:when test="${param.action=='modthreads'&&param.batch=='yes'}"><jsp:forward page="/posts.do?postsaction=batchModthreads"/></c:when>
			<c:when test="${param.action=='modthreads'&&param.automod=='yes'}"><jsp:forward page="/posts.do?postsaction=auditingNewThreads"/></c:when>
			<c:when test="${param.action=='modthreads'&&param.searchpage=='yes'}"><jsp:forward page="/posts.do?postsaction=pagePosts"/></c:when>
			<c:when test="${param.action=='modthreads'}"><jsp:forward page="/posts.do?postsaction=tomodthreads"/></c:when>
			<c:when test="${param.action=='modreplies'&&param.batch=='yes'}"><jsp:forward page="/posts.do?postsaction=workAllModreplies"/></c:when>
			<c:when test="${param.action=='modreplies'&&param.automod=='yes'}"><jsp:forward page="/posts.do?postsaction=auditingNewModreplies"/></c:when>
			<c:when test="${param.action=='modreplies'&&param.searchpage=='yes'}"><jsp:forward page="/posts.do?postsaction=pageModreplies"/></c:when>
			<c:when test="${param.action=='modreplies'}"><jsp:forward page="/posts.do?postsaction=tomodreplies"/></c:when>
			<c:when test="${param.action=='threadsbatch'}"><jsp:forward page="/threads.do?threadsaction=batchThreads"/></c:when>
			<c:when test="${param.action=='threads'&&param.disposal=='yes'}"><jsp:forward page="/threads.do?threadsaction=disposalThreads"/></c:when>
			<c:when test="${param.action=='threadssearch'}"><jsp:forward page="/threads.do?threadsaction=pageThreads"/></c:when>
			<c:when test="${param.action=='threads'}"><jsp:forward page="/posts.do?postsaction=toThreadsForum"/></c:when>
			<c:when test="${param.action=='prune'&&param.search=='yes'}"><jsp:forward page="/prune.do?pruneaction=fromPrune"/></c:when>
			<c:when test="${param.action=='prune'&&param.batch=='yes'}"><jsp:forward page="/prune.do?pruneaction=batchPrune"/></c:when>
			<c:when test="${param.action=='prune'&&param.searchpage=='yes'}"><jsp:forward page="/prune.do?pruneaction=pagePrune"/></c:when>
			<c:when test="${param.action=='prune'}"><jsp:forward page="/posts.do?postsaction=toPruneForum"/></c:when>
			<c:when test="${param.action=='attachments'&&param.search=='yes'}"><jsp:forward page="/attachments.do?attachmentsaction=fromAttachments"/></c:when>
			<c:when test="${param.action=='attachments'&&param.delete=='yes'}"><jsp:forward page="/attachments.do?attachmentsaction=deleteAttachments"/></c:when>
			<c:when test="${param.action=='attachments'&&param.searchpage=='yes'}"><jsp:forward page="/attachments.do?attachmentsaction=pageAttachments"/></c:when>
			<c:when test="${param.action=='attachments'}"><jsp:forward page="/posts.do?postsaction=toAttachments"/></c:when>
			<c:when test="${param.action=='forumrecommend'}"><jsp:forward page="/posts.do?postsaction=forumrecommend"/></c:when>
			<c:when test="${param.action=='jspruncodes'&&param.batch=='yes'}"><jsp:forward page="/posts.do?postsaction=batchBbcodes"/></c:when>
			<c:when test="${param.action=='jspruncodes'&&param.child=='yes'}"><jsp:forward page="/posts.do?postsaction=toJsprunCodeChild"/></c:when>
			<c:when test="${param.action=='jspruncodes'&&param.batchchild=='yes'}"><jsp:forward page="/posts.do?postsaction=updateJsprunCodeChild"/></c:when>
			<c:when test="${param.action=='jspruncodes'}"><jsp:forward page="/posts.do?postsaction=tojspruncode"/></c:when>
			<c:when test="${param.action=='tags'&&param.search=='yes'}"><jsp:forward page="/tagManage.do?tagsaction=findByTags"/></c:when>
			<c:when test="${param.action=='tags'&&param.batch=='yes'}"><jsp:forward page="/tagManage.do?tagsaction=batchTags"/></c:when>
			<c:when test="${param.action=='tags'&&param.seachpage=='yes'}"><jsp:forward page="/tagManage.do?tagsaction=pageTags"/></c:when>
			<c:when test="${param.action=='tags'}"><jsp:forward page="/posts.do?postsaction=toTags"/></c:when>
			<c:when test="${param.action=='censor'&&param.batch=='yes'}"><jsp:forward page="/posts.do?postsaction=batchWords"/></c:when>
			<c:when test="${param.action=='censor'&&param.batcharea=='yes'}"><jsp:forward page="/posts.do?postsaction=batchWordsTextarea"/></c:when>
			<c:when test="${param.action=='censor'&&param.downs=='yes'}"><jsp:forward page="/posts.do?postsaction=downWords"/></c:when>
			<c:when test="${param.action=='censor'&&param.searchpage=='yes'}"><jsp:forward page="/posts.do?postsaction=pageWords"/></c:when>
			<c:when test="${param.action=='censor'}"><jsp:forward page="/posts.do?postsaction=toWords"/></c:when>
			<c:when test="${param.action=='smilies'&&param.batch=='yes'}"><jsp:forward page="/smilies.do?smiliesaction=batchSmiliestypes"/></c:when>
			<c:when test="${param.action=='smilies'&&param.search=='yes'}"><jsp:forward page="/smilies.do?smiliesaction=findBySmilies"/></c:when>
			<c:when test="${param.action=='smilies'&&param.update=='yes'}"><jsp:forward page="/smilies.do?smiliesaction=updateSmilies"/></c:when>
			<c:when test="${param.action=='smilies'&&param.addsmilies=='yes'}"><jsp:forward page="/smilies.do?smiliesaction=addSmilies"/></c:when>
			<c:when test="${param.action=='smilies'}"><jsp:forward page="/posts.do?postsaction=toSmilies"/></c:when>
			<c:when test="${param.action=='icons'&&param.batch=='yes'}"><jsp:forward page="/posts.do?postsaction=batchIcons"/></c:when>
			<c:when test="${param.action=='icons'&&param.add=='yes'}"><jsp:forward page="/posts.do?postsaction=addIcons"/></c:when>
			<c:when test="${param.action=='icons'}"><jsp:forward page="/posts.do?postsaction=toIcoes"/></c:when>
			<c:when test="${param.action=='attachtypes'&&param.batch=='yes'}"><jsp:forward page="/posts.do?postsaction=batchAttachtypes"/></c:when>
			<c:when test="${param.action=='attachtypes'}"><jsp:forward page="/posts.do?postsaction=toAttachtype"/></c:when>
			<c:when test="${param.action=='recyclebin'&&param.search=='yes'}"><jsp:forward page="/recyclebin.do?recyclebinaction=fromRecyclebin"/></c:when>
			<c:when test="${param.action=='recyclebin'&&param.deleteold=='yes'}"><jsp:forward page="/recyclebin.do?recyclebinaction=deleteDayOld"/></c:when>
			<c:when test="${param.action=='recyclebin'&&param.batch=='yes'}"><jsp:forward page="/recyclebin.do?recyclebinaction=batchRecyclebin"/></c:when>
			<c:when test="${param.action=='recyclebin'&&param.searchpage=='yes'}"><jsp:forward page="/recyclebin.do?recyclebinaction=pageRecyclebin"/></c:when>
			<c:when test="${param.action=='recyclebin'}"><jsp:forward page="/posts.do?postsaction=toRecyclebin"/></c:when>
			
			<c:when test="${param.action=='getSevrerInfo_SysType'}"><jsp:forward  page="/extendsAction.do?action=getSevrerInfo_SysType"/></c:when>
			<c:when test="${param.action=='getSevrerInfo_CUPCount'}"><jsp:forward page="/extendsAction.do?action=getSevrerInfo_CUPCount"/></c:when>
			<c:when test="${param.action=='getSevrerInfo_MemoryInfo'}"><jsp:forward page="/extendsAction.do?action=getSevrerInfo_MemoryInfo"/></c:when>
			<c:when test="${param.action=='plugins'}"><jsp:forward page="/extendsAction.do?action=plugins"/></c:when>
			<c:when test="${param.action=='pluginhooks'}"><jsp:forward page="/extendsAction.do?action=pluginhooks"/></c:when>
			<c:when test="${param.action=='pluginvars'}"><jsp:forward page="/extendsAction.do?action=pluginvars"/></c:when>
			<c:when test="${param.action=='pluginsedit'}"><jsp:forward page="/extendsAction.do?action=pluginsedit"/></c:when>
			<c:when test="${param.action=='pluginsconfig'}"><jsp:forward page="/extendsAction.do?action=pluginsconfig"/></c:when>
			<c:when test="${param.action=='google_config'}"><jsp:forward page="/extendsAction.do?action=showSearchEngine&do=google_config"/></c:when>
			<c:when test="${param.action=='baidu_config'}"><jsp:forward page="/extendsAction.do?action=showSearchEngine&do=baidu_config"/></c:when>
			<c:when test="${param.action=='searchEngine'}"><jsp:forward page="/extendsAction.do?action=searchEngine"/></c:when>
			<c:when test="${param.action=='areaquery'}"><jsp:forward page="pluginsconfig/areaquery.jsp"/></c:when>
			<c:when test="${param.action=='tenpay'}"><jsp:forward page="/extendsAction.do?action=tenpay" /></c:when>
			<c:when test="${param.action=='ec_credit'}"><jsp:forward page="/extendsAction.do?action=ec_credit"/></c:when>
			<c:when test="${param.action=='orders'}"><jsp:forward page="/extendsAction.do?action=orders"/></c:when>
			<c:when test="${param.action=='tradelog'}"><jsp:forward page="/extendsAction.do?action=tradelog"/></c:when>
			
			<c:when test="${param.action=='magic_config'}"><jsp:forward page="/other.do?action=magicconfig"/></c:when>
			<c:when test="${param.action=='modmagic_config'}"><jsp:forward page="/other.do?action=modmagic_config"/></c:when>
			<c:when test="${param.action=='magic'}"><jsp:forward page="/other.do?action=magic"/></c:when>
			<c:when test="${param.action=='modmagic'}"><jsp:forward page="/other.do?action=modmagic"/></c:when>
			<c:when test="${param.action=='magicedit'}"><jsp:forward page="/other.do?action=magicedit"/></c:when>
			<c:when test="${param.action=='modmagicedit'}"><jsp:forward page="/other.do?action=modmagicedit"/></c:when>
			<c:when test="${param.action=='magicmarket'}"><jsp:forward page="/other.do?action=magicmarket"/></c:when>
			<c:when test="${param.action=='modmagicmarket'}"><jsp:forward page="/other.do?action=modmagicmarket"/></c:when>
			<c:when test="${param.action=='announcements'}"><jsp:forward page="/other.do?action=announcements" /></c:when>
			<c:when test="${param.action=='updateAnns'}"><jsp:forward page="/other.do?action=updateAnns" /></c:when>
			<c:when test="${param.action=='addAnn'}"><jsp:forward page="/other.do?action=addAnn" /></c:when>
			<c:when test="${param.action=='annedit'}"><jsp:forward page="/other.do?action=annedit" /></c:when>
			<c:when test="${param.action=='editAnn'}"><jsp:forward page="/other.do?action=editAnn" /></c:when>
			<c:when test="${param.action=='medals'}"><jsp:forward page="/other.do?action=medals" /></c:when>
			<c:when test="${param.action=='adv'}"><jsp:forward page="/other.do?action=adv"/></c:when>
			<c:when test="${param.action=='advadd'}"><jsp:forward page="/other.do?action=advadd"/></c:when>
			<c:when test="${param.action=='advedit'}"><jsp:forward page="/other.do?action=advedit"/></c:when>
			<c:when test="${param.action=='forumlinks'}"><jsp:forward page="/other.do?action=forumlinks" /></c:when>
			<c:when test="${param.action=='crons'}"><jsp:forward page="/other.do?action=crons" /></c:when>
			<c:when test="${param.action=='cronsedit'}"><jsp:forward page="/other.do?action=cronsedit" /></c:when>
			<c:when test="${param.action=='faqlist'}"><jsp:forward page="/other.do?action=faqlist"/></c:when>
			<c:when test="${param.action=='faqdetail'}"><jsp:forward page="/other.do?action=faqdetail"/></c:when>
			<c:when test="${param.action=='faqedit'}"><jsp:forward page="/other.do?action=faqedit"/></c:when>
			<c:when test="${param.action=='onlinelist'}"><jsp:forward page="/other.do?action=onlinelist" /></c:when>
			
			<c:when test="${param.action=='safety'}">
				<c:choose>
					<c:when test="${param['do']=='basic'}"><jsp:forward page="/safety.do?param=basic" /></c:when>
					<c:when test="${param['do']=='cc'}"><jsp:forward page="/safety.do?param=cc" /></c:when>
					<c:when test="${param['do']=='ddos'}"><jsp:forward page="/safety.do?param=ddos" /></c:when>
					<c:when test="${param['do']=='port'}"><jsp:forward page="/safety.do?param=port" /></c:when>
				</c:choose>
			</c:when>
			
			<c:when test="${param.action=='members'&&param.submitname=='newslettersubmit'}"><jsp:forward page="/sysutil.do?useraction=newletterInit"/></c:when>
			<c:when test="${param.action=='newsletterinit'}"><jsp:forward page="/sysutil.do?useraction=newletterInit"/></c:when>
			<c:when test="${param.action=='newlettersubmit'}"><jsp:forward page="/sysutil.do?useraction=newlettersubmit"/></c:when>
			<c:when test="${param.action=='updatecache'}"><jsp:forward  page="/database.do?action=updateForumCache"/></c:when>
			<c:when test="${param.action=='counter'&&param.forum=='yes'}"><jsp:forward page="/systemtool.do?action=forumsubmit"/></c:when>
			<c:when test="${param.action=='counter'&&param.digest=='yes'}"><jsp:forward page="/systemtool.do?action=digestsubmit"/></c:when>
			<c:when test="${param.action=='counter'&&param.member=='yes'}"><jsp:forward page="/systemtool.do?action=membersubmit"/></c:when>
			<c:when test="${param.action=='counter'&&param.thread=='yes'}"><jsp:forward page="/systemtool.do?action=threadsubmit"/></c:when>
			<c:when test="${param.action=='counter'&&param.movedthread=='yes'}"><jsp:forward page="/systemtool.do?action=movedthreadsubmit"/></c:when>
			<c:when test="${param.action=='counter'&&param.cleanup=='yes'}"><jsp:forward page="/systemtool.do?action=cleanupsubmit"/></c:when>
			<c:when test="${param.action=='counter'}"><jsp:include flush="true" page="counter/counter.jsp"/></c:when>
			<c:when test="${param.action=='gojssetting'}"><jsp:forward page="/systemtool.do?action=gojsSetting"/></c:when>
			<c:when test="${param.action=='jssetting'}"><jsp:forward page="/systemtool.do?action=jsSetting"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.jsthreads=='yes'}"><jsp:forward page="/systemtool.do?action=jsthreads"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.jsforums=='yes'}"><jsp:forward page="/systemtool.do?action=jsforums"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.jsmemberrank=='yes'}"><jsp:forward page="/systemtool.do?action=jsmemberrank"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.jsstats=='yes'}"><jsp:forward page="/systemtool.do?action=jsstats"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.jsimages=='yes'}"><jsp:forward page="/systemtool.do?action=jsimages"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.jscustom=='yes'}"><jsp:forward page="/systemtool.do?action=jscustom"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.editjswizard=='yes'}"><jsp:forward page="/systemtool.do?action=jswizard"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.editjsthreads=='yes'}"><jsp:forward page="/systemtool.do?action=editjsthreads"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.editjsforums=='yes'}"><jsp:forward page="/systemtool.do?action=editjsforums"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.editjsmembers=='yes'}"><jsp:forward page="/systemtool.do?action=editjsmembers"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.editjsstats=='yes'}"><jsp:forward page="/systemtool.do?action=editjsstats"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.editjsimages=='yes'}"><jsp:forward page="/systemtool.do?action=editjsimages"/></c:when>
			<c:when test="${param.action=='jswizard'&&param.editjscoustem=='yes'}"><jsp:forward page="/systemtool.do?action=editjscoustem"/></c:when>
			<c:when test="${param.action=='jswizard'}"><jsp:forward page="/systemtool.do?action=jsinit"/></c:when>
			<c:when test="${param.action=='pmprune'&&param.batch=='yes'}"><jsp:forward page="/pmprune.do?pmpruneaction=batchPmprune"/></c:when>
			<c:when test="${param.action=='pmprune'&&param.delete=='yes'}"><jsp:forward page="/pmprune.do?pmpruneaction=deletePmprun"/></c:when>
			<c:when test="${param.action=='pmprune'}"><jsp:include flush="true" page="counter/pmprune.jsp"/></c:when>
			<c:when test="${param.action=='project'}"><jsp:forward page="/project.do?action=toProject" /></c:when>
			<c:when test="${param.action=='editproject'}"><jsp:forward page="/project.do?action=project" /></c:when>
			<c:when test="${param.action=='projectapply'}"><jsp:forward page="/project.do?action=toProjectapply" /></c:when>
			<c:when test="${param.action=='editprojectapply'}"><jsp:forward page="/project.do?action=projectapply" /></c:when>
			<c:when test="${param.action=='fileperms'}"><jsp:forward page="/sysfile.do?fileaction=checkFileperms"/></c:when>
			<c:when test="${param.action=='filecheck'}"><jsp:forward page="/sysfile.do?fileaction=checkFileIntegrity&filechek=yes"/></c:when>
			<c:when test="${param.action=='illegallog'}"><jsp:forward page="/sysutil.do?useraction=illegallogRead"/></c:when>
			<c:when test="${param.action=='ratelog'}"><jsp:forward page="/sysutil.do?useraction=userLogRead"/></c:when>
			<c:when test="${param.action=='creditslog'}"><jsp:forward page="/sysutil.do?useraction=creditslogRead"/></c:when>
			<c:when test="${param.action=='modslog'}"><jsp:forward page="/sysutil.do?useraction=modsRead"/></c:when>
			<c:when test="${param.action=='medalslog'}"><jsp:forward page="/sysutil.do?useraction=medalsLogRead"/></c:when>
			<c:when test="${param.action=='banlog'}"><jsp:forward page="/sysutil.do?useraction=banLogRead"/></c:when>
			<c:when test="${param.action=='cplog'}"><jsp:forward page="/sysutil.do?useraction=cplogRead"/></c:when>
			<c:when test="${param.action=='magiclog'}"><jsp:forward page="/sysutil.do?useraction=magiclogRead"/></c:when>
			<c:when test="${param.action=='invitelog'}"><jsp:forward page="/sysutil.do?useraction=inviteslogRead"/></c:when>
			<c:when test="${param.action=='errorlog'}"><jsp:forward page="/sysutil.do?useraction=errorlogRead"/></c:when>
			<c:when test="${param.action=='creditwizard'}"><jsp:forward page="/basicsettings.do?param=creditsGuide"/></c:when>
			<c:when test="${param.action=='toCreditExpression'}"><jsp:forward page="/basicsettings.do?param=toCreditExpression"/></c:when>
			<c:when test="${param.action=='toCreditPurpose'}"><jsp:forward page="/basicsettings.do?param=toCreditPurpose"/></c:when>
			<c:when test="${param.action=='export'}"><jsp:forward page="/database.do?action=toExport" /></c:when>
			<c:when test="${param.action=='exportData'}"><jsp:forward page="/database.do?action=exportData" /></c:when>
			<c:when test="${param.action=='import'}"><jsp:forward page="/database.do?action=toImport" /></c:when>
			<c:when test="${param.action=='importData'}"><jsp:forward page="/database.do?action=importData" /></c:when>
			<c:when test="${param.action=='importFile'}"><jsp:forward page="/database.do?action=importFile" /></c:when>
			<c:when test="${param.action=='importZipFile'}"><jsp:forward page="/database.do?action=importZipFile" /></c:when>
			<c:when test="${param.action=='runquery'}"><jsp:forward page="/database.do?action=runquery" /></c:when>
			<c:when test="${param.action=='optimize'}"><jsp:forward page="/database.do?action=optimize" /></c:when>
			<c:when test="${param.action=='dbcheck'}"><jsp:forward page="/database.do?action=toDbcheck" /></c:when>
			<c:when test="${param.action=='moddbcheck'}"><jsp:forward page="/database.do?action=dbcheck" /></c:when>
			<c:when test="${param.action=='editmembers'}"><jsp:include flush="true" page="members/editmember.jsp"/></c:when>
			<c:otherwise>
				<c:set var="message_key" value="action_noaccess" scope="request"/>
				<jsp:forward page="message.jsp"/>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${param.action=='forumrecommend'}"><jsp:forward page="/posts.do?postsaction=forumrecommend"/></c:when>
	<c:when test="${param.action=='censor'&&param.batch=='yes'&& usergroups.allowcensorword==1}"><jsp:forward page="/posts.do?postsaction=batchWords"/></c:when>
	<c:when test="${param.action=='censor'&&param.batcharea=='yes'&& usergroups.allowcensorword==1}"><jsp:forward page="/posts.do?postsaction=batchWordsTextarea"/></c:when>
	<c:when test="${param.action=='censor'&&param.searchpage=='yes'&& usergroups.allowcensorword==1}"><jsp:forward page="/posts.do?postsaction=pageWords"/></c:when>
	<c:when test="${param.action=='censor'&&usergroups.allowcensorword==1}"><jsp:forward page="/posts.do?postsaction=toWords"/></c:when>
	<c:when test="${param.action=='forumrules'}"><jsp:forward page="/forumsedit.do?action=forumrules" /></c:when>
	<c:when test="${param.action=='editmember'&&usergroups.allowedituser==1}"><jsp:forward page="/user.do?useraction=editmembers"/></c:when>
	<c:when test="${param.action=='editextmembers'&&usergroups.allowedituser==1}"><jsp:forward page="/user.do?useraction=editextmembers"/></c:when>
	<c:when test="${param.action=='banmember'&& usergroups.allowbanuser==1}"><jsp:forward page="/user.do?useraction=tobanmember"/></c:when>
	<c:when test="${param.action=='editbanmember'&& usergroups.allowbanuser==1}"><jsp:forward page="/user.do?useraction=banMember"/></c:when>
	<c:when test="${param.action=='ipban'&& usergroups.allowbanip==1}"><jsp:forward page="/user.do?useraction=banIp"/></c:when>
	<c:when test="${param.action=='editipban'&& usergroups.allowbanip==1}"><jsp:forward page="/user.do?useraction=banIp"/></c:when>
	<c:when test="${param.action=='modmembers'&&param.search=='yes'&& usergroups.allowmoduser==1}"><jsp:forward page="/user.do?useraction=searchVlidateMemeber"/></c:when>
	<c:when test="${param.action=='modmembers'&&param.validate=='yes'&& usergroups.allowmoduser==1}"><jsp:forward page="/user.do?useraction=validateMembers"/></c:when>
	<c:when test="${param.action=='modmembers'&& usergroups.allowmoduser==1}"><jsp:forward page="/user.do?useraction=modMembers"/></c:when>
	<c:when test="${param.action=='modthreads'&&param.batch=='yes'&&usergroups.allowmodpost==1}"><jsp:forward page="/posts.do?postsaction=batchModthreads"/></c:when>
	<c:when test="${param.action=='modthreads'&&param.automod=='yes'&&usergroups.allowmodpost==1}"><jsp:forward page="/posts.do?postsaction=auditingNewThreads"/></c:when>
	<c:when test="${param.action=='modthreads'&&param.searchpage=='yes'&&usergroups.allowmodpost==1}"><jsp:forward page="/posts.do?postsaction=pagePosts"/></c:when>
	<c:when test="${param.action=='modthreads'&&usergroups.allowmodpost==1}"><jsp:forward page="/posts.do?postsaction=tomodthreads"/></c:when>
	<c:when test="${param.action=='modreplies'&&param.batch=='yes'&&usergroups.allowmodpost==1}"><jsp:forward page="/posts.do?postsaction=workAllModreplies"/></c:when>
	<c:when test="${param.action=='modreplies'&&param.automod=='yes'&&usergroups.allowmodpost==1}"><jsp:forward page="/posts.do?postsaction=auditingNewModreplies"/></c:when>
	<c:when test="${param.action=='modreplies'&&param.searchpage=='yes'&&usergroups.allowmodpost==1}"><jsp:forward page="/posts.do?postsaction=pageModreplies"/></c:when>
	<c:when test="${param.action=='modreplies'&&usergroups.allowmodpost==1}"><jsp:forward page="/posts.do?postsaction=tomodreplies"/></c:when>
	<c:when test="${param.action=='prune'&&param.search=='yes'&& usergroups.allowmassprune==1}"><jsp:forward page="/prune.do?pruneaction=fromPrune"/></c:when>
	<c:when test="${param.action=='prune'&&param.batch=='yes'&& usergroups.allowmassprune==1}"><jsp:forward page="/prune.do?pruneaction=batchPrune"/></c:when>
	<c:when test="${param.action=='prune'&&param.searchpage=='yes'&& usergroups.allowmassprune==1}"><jsp:forward page="/prune.do?pruneaction=pagePrune"/></c:when>
	<c:when test="${param.action=='prune' && usergroups.allowmassprune==1}"><jsp:forward page="/posts.do?postsaction=toPruneForum"/></c:when>
	<c:when test="${param.action=='announcements'&& usergroups.allowpostannounce==1}"><jsp:forward page="/other.do?action=announcements" /></c:when>
	<c:when test="${param.action=='updateAnns'&& usergroups.allowpostannounce==1}"><jsp:forward page="/other.do?action=updateAnns" /></c:when>
	<c:when test="${param.action=='addAnn'&& usergroups.allowpostannounce==1}"><jsp:forward page="/other.do?action=addAnn" /></c:when>
	<c:when test="${param.action=='annedit'&& usergroups.allowpostannounce==1}"><jsp:forward page="/other.do?action=annedit" /></c:when>
	<c:when test="${param.action=='editAnn'&& usergroups.allowpostannounce==1}"><jsp:forward page="/other.do?action=editAnn" /></c:when>
	<c:when test="${param.action=='ratelog'&& usergroups.allowviewlog==1}"><jsp:forward page="/sysutil.do?useraction=userLogRead"/></c:when>
	<c:when test="${param.action=='editmembers'&& usergroups.allowedituser==1}"><jsp:include flush="true" page="members/editmember.jsp"/></c:when>
	<c:when test="${param.action=='banlog'&& usergroups.allowviewlog==1}"><jsp:forward page="/sysutil.do?useraction=banLogRead"/></c:when>
	<c:when test="${param.action=='modslog'&& usergroups.allowviewlog==1}"><jsp:forward page="/sysutil.do?useraction=modsRead"/></c:when>
	<c:when test="${param.action=='plugins'}"><jsp:forward page="/extendsAction.do?action=plugins"/></c:when>
	<c:otherwise>
		<c:set var="message_key" value="action_noaccess" scope="request"/>
		<jsp:forward page="message.jsp"/>
	</c:otherwise>
</c:choose>