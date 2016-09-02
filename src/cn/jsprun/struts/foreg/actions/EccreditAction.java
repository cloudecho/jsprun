package cn.jsprun.struts.foreg.actions;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.api.Tenpayapi;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
public class EccreditAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward showEccredit(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		int uid = Common.toDigit(request.getParameter("uid"));
		int jsprun_uid = (Integer)session.getAttribute("jsprun_uid");
		int allowviewpro = jsprun_uid>0 && uid==jsprun_uid?1:Common.toDigit(usergroups.get("allowviewpro"));
		if(allowviewpro==0){
			request.setAttribute("show_message", getMessage(request, "group_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		List<Map<String,String>> members = dataBaseService.executeQuery("SELECT m.uid, mf.customstatus, m.username, m.groupid, mf.taobao, mf.alipay, mf.avatar, mf.avatarwidth, mf.avatarheight, mf.buyercredit, mf.sellercredit, m.regdate,u.groupavatar,u.allowavatar FROM jrun_members m LEFT JOIN jrun_memberfields mf USING(uid) LEFT JOIN jrun_usergroups as u ON m.groupid=u.groupid WHERE m.uid='"+uid+"'");
		if(members==null || members.size()<=0){
			request.setAttribute("errorInfo", getMessage(request, "member_nonexistence"));
			return mapping.findForward("showMessage");
		}
		Map<String,String> member = members.get(0);
		if(member.get("taobao")!=null){
			member.put("taobao", Common.addslashes(member.get("taobao")));
		}
		String avatar = "";
 		if(!Common.empty(member.get("groupavatar"))){
 			avatar = "<img src=\""+member.get("groupavatar")+"\" border=\"0\" alt=\"\" />";
 		}else if(Integer.valueOf(member.get("allowavatar"))>0&&!Common.empty(member.get("avatar"))){
 			avatar = "<div class=\"avatar\" style=\"width: "+member.get("avatarwidth")+"\"><img src=\""+member.get("avatar")+"\" width=\""+member.get("avatarwidth")+"\" height=\""+member.get("avatarheight")+"\" border=\"0\" alt=\"\" /></div>";
 		}
 		member.put("avatar", avatar);
 		int buycredit = Common.toDigit(member.get("buyercredit"));
		int shellcredit =  Common.toDigit(member.get("sellercredit"));
		String ec_credit = settings.get("ec_credit");
		Map buycreditMap = dataParse.characterParse(ec_credit, true);
		Map buysMap = (Map) buycreditMap.get("rank");
		String postbuycredit = "0";
		String postshellcredit = "0";
		if (buysMap != null) {
			Iterator it = buysMap.keySet().iterator();
			if(buycredit>0){
				while (it.hasNext()) {
					Object key = it.next();
					if (buycredit <= Integer.valueOf(buysMap.get(key).toString())) {
						postbuycredit = key.toString();
						break;
					}
				}
			}
			if(shellcredit>0){
				while (it.hasNext()) {
					Object key = it.next();
					if (shellcredit <= Integer.valueOf(buysMap.get(key).toString())) {
						postshellcredit = key.toString();
						break;
					}
				}
			}
		}
		request.setAttribute("shellcredit", postshellcredit);
		request.setAttribute("buycredit", postbuycredit);
		List<Map<String,String>> cachelist = dataBaseService.executeQuery("select variable,value,expiration from jrun_spacecaches where uid="+uid+" and variable in ('buyercredit','sellercredit')");
		Map<String,Map> caches = new HashMap<String,Map>();
		for(Map<String,String> cache:cachelist){
			Map variable = dataParse.characterParse(cache.get("value"), false);
			variable.put("expiration", cache.get("expiration"));
			caches.put(cache.get("variable"), variable);
		}
		if(caches.get("buyercredit")==null ||timestamp > Common.toDigit(caches.get("buyercredit").get("expiration").toString())){
			caches.put("buyercredit", updatecreditcache(uid,timestamp,"buyercredit"));
		}
		if(caches.get("sellercredit")==null ||timestamp > Common.toDigit(caches.get("sellercredit").get("expiration").toString())){
			caches.put("sellercredit", updatecreditcache(uid,timestamp,"sellercredit"));
		}
		float buyertotal =  Float.parseFloat(((Map)caches.get("buyercredit").get("all")).get("total").toString());
		float sellertotal = Float.parseFloat(((Map)caches.get("sellercredit").get("all")).get("total").toString());
		buyertotal=buyertotal==0?1:buyertotal;
		sellertotal=sellertotal==0?1:sellertotal;
		double buyers = Common.toDigit(((Map)caches.get("buyercredit").get("all")).get("good").toString())*100/buyertotal;
		String buyerpercent = Common.number_format(buyers, "0.00");
		double seller = Common.toDigit(((Map)caches.get("sellercredit").get("all")).get("good").toString())*100/sellertotal;
		String sellerpercent = Common.number_format(seller, "0.00");
		request.setAttribute("buyerpercent", buyerpercent);
		request.setAttribute("sellerpercent",sellerpercent);
		request.setAttribute("caches", caches);
		request.setAttribute("member",member);
		return mapping.findForward("ec_credit");
	}
	public ActionForward list(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int uid = Common.toDigit(request.getParameter("uid"));
		String from = request.getParameter("from");
		from = from == null?"":from;
		from = Common.in_array(new String[]{"buyer","seller","myself"}, from)? from:"";
		String sql = from.equals("myself")?"raterid="+uid:"rateeid="+uid;
		sql+= from.equals("buyer")?" and type=0":(from.equals("seller")?" and type=1":"");
		String filter = request.getParameter("filter");
		filter = filter==null?"":filter;
		if(filter.equals("thisweek")){
			sql += " and dateline>="+(timestamp-604800);
		}else if(filter.equals("thismonth")){
			sql += " and dateline>="+(timestamp-2592000);
		}else if(filter.equals("halfyear")){
			sql += " and dateline>="+(timestamp-15552000);
		}else if(filter.equals("before")){
			sql += " and dateline<"+(timestamp-15552000);
		}
		String level = request.getParameter("level");
		level = level==null?"":level;
		if(level.equals("good")){
			sql += " and score=1";
		}else if(level.equals("soso")){
			sql+= " and score=0";
		}else if(level.equals("bad")){
			sql+=" and score=-1";
		}
		int num = Integer.valueOf(dataBaseService.executeQuery("select count(*) as count from jrun_tradecomments where "+sql).get(0).get("count"));
		int page =Math.max(Common.intval(request.getParameter("page")), 1);
		int perpage=10;
		Map<String,Integer> multiInfo=Common.getMultiInfo(num, perpage, page);
		page=multiInfo.get("curpage");
		int start_limit=multiInfo.get("start_limit");
		String url = "eccredit.jsp?action=list&uid="+uid+(!from.equals("")?"&from="+from:"")+(!filter.equals("")?"&filter="+filter:"")+(!level.equals("")?"&level="+level:"");
		Map<String,Object> multi=Common.multi(num, perpage, page, url, 0, 10, true, false, null);
		request.setAttribute("multi", multi);
		List<Map<String,String>> comments = dataBaseService.executeQuery("SELECT tc.*, tl.subject, tl.baseprice FROM jrun_tradecomments tc LEFT JOIN jrun_tradelog tl ON tl.orderid=tc.orderid WHERE "+sql+" ORDER BY dateline DESC LIMIT "+start_limit+", "+perpage);
		if(comments!=null&&comments.size()>0){
			String dateformat = (String)session.getAttribute("dateformat");
			String timeformat = (String)session.getAttribute("timeformat");
			String timeoffset= (String)session.getAttribute("timeoffset");
			SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
			for(Map<String,String> comment:comments){
				comment.put("expiration",Common.gmdate(sdf_all, Integer.parseInt(comment.get("dateline"))+30*86400));
				comment.put("baseprice", Common.number_format(Double.valueOf(comment.get("baseprice")), "0.00"));
			}
		}
		request.setAttribute("comments", comments);
		String onversi = request.getParameter("onversi");
		if(onversi!=null&&onversi.equals("yes")){
			try {
				PrintWriter out = response.getWriter();
				out.write("&#80;&#111;&#119;&#101;&#114;&#101;&#100;&#32;&#98;&#121;&#32;&#74;&#115;&#112;&#82;&#117;&#110;&#33;&#32;&#54;&#46;&#48;&#46;&#48;&#32;&#82;&#101;&#108;&#101;&#97;&#115;&#101;&#32;<br/>");
				out.write("&#67;&#111;&#112;&#121;&#82;&#105;&#103;&#104;&#116;&#32;&#50;&#48;&#48;&#55;&#45;&#50;&#48;&#49;&#49;&#32;&#74;&#115;&#112;&#82;&#117;&#110;&#33;&#32;&#73;&#110;&#99;&#46;&#32;&#65;&#108;&#108;&#32;&#82;&#105;&#103;&#104;&#116;&#115;&#32;&#82;&#101;&#115;&#101;&#114;&#118;&#101;&#100;<br/><br/>");
				out.write("&#21271;&#20140;&#39134;&#36895;&#21019;&#24819;&#31185;&#25216;&#26377;&#38480;&#20844;&#21496;<br/>");
				out.write("&#74;&#115;&#112;&#82;&#117;&#110;&#33;&#32;&#23448;&#26041;&#32593;&#31449;&#65306;&#119;&#119;&#119;&#46;&#106;&#115;&#112;&#114;&#117;&#110;&#46;&#99;&#111;&#109;<br/>");
				out.write("&#74;&#115;&#112;&#82;&#117;&#110;&#33;&#32;&#23448;&#26041;&#35770;&#22363;&#65306;&#119;&#119;&#119;&#46;&#106;&#115;&#112;&#114;&#117;&#110;&#46;&#110;&#101;&#116;<br/>");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return mapping.findForward("ec_list");
	}
	public ActionForward explain(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int jsprun_uid = (Integer)request.getSession().getAttribute("jsprun_uid");
		int id = Common.intval(request.getParameter("id"));
		try{
			if(submitCheck(request, "explainsubmit")){
				List<Map<String,String>> comments = dataBaseService.executeQuery("SELECT explanation, dateline FROM jrun_tradecomments WHERE id='"+id+"' AND rateeid='"+jsprun_uid+"'");
				Map<String,String> comment = comments.size()>0?comments.get(0):null;
				if(comment==null){
					Common.writeMessage(response,getMessage(request, "eccredit_nofound"),true);
					return null;
				}else if(!Common.empty(comment.get("explanation"))){
					Common.writeMessage(response,getMessage(request, "eccredit_reexplanation_repeat"),true);
					return null;
				}else if(Common.toDigit(comment.get("dateline"))<timestamp-30*86400){
					Common.writeMessage(response,getMessage(request, "eccredit_reexplanation_closed"),true);
					return null;
				}
				String explanation = request.getParameter("explanation");
				explanation= explanation==null?"":explanation;
				explanation = Common.cutstr(Common.htmlspecialchars(explanation), 200, "");
				explanation = Common.addslashes(explanation);
				dataBaseService.runQuery("update jrun_tradecomments set explanation='"+explanation+"' where id="+id);
				String message = "<script type=\"text/javascript\">$('ecce_"+id+"').innerHTML = '<font class=\"lighttxt\">"+getMessage(request, "a_post_jspruncodes_edit_explanation")+" "+explanation+"</font>';hideMenu();</script>";
				Common.writeMessage(response,message,false);
				return null;
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		return mapping.findForward("ec_explain");
	}
	@SuppressWarnings("unchecked")
	public ActionForward rate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		ServletContext context=servlet.getServletContext();
		Map<String, String> settings = (Map<String, String>) context.getAttribute("settings");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int jsprun_uid = (Integer)request.getSession().getAttribute("jsprun_uid");
		String jsprun_usess = (String)request.getSession().getAttribute("jsprun_userss");
		String boardurl = (String)request.getSession().getAttribute("boardurl");
		String type = request.getParameter("type");
		String orderid = request.getParameter("orderid");
		if(type==null || Common.empty(orderid)){
			request.setAttribute("errorInfo", getMessage(request, "undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		String raterid = "";
		String ratee = "";
		String rateeid = "";
		if(type.equals("0")){
			raterid = "buyerid";
			ratee = "seller";
			rateeid = "sellerid";
		}else{
			raterid = "sellerid";
			ratee = "buyer";
			rateeid = "buyerid";
		}
		List<Map<String,String>> orders = dataBaseService.executeQuery("SELECT * FROM jrun_tradelog WHERE orderid=? AND "+raterid+"='"+jsprun_uid+"'",orderid);
		Map<String,String> order = orders.size()>0?orders.get(0):null;
		if(order==null){
			request.setAttribute("resultInfo", getMessage(request, "eccredit_order_notfound"));
			return mapping.findForward("showMessage");
		}else if(order.get("ratestatus").equals("3")||(type.equals("0")&&order.get("ratestatus").equals("1"))||(type.equals("1")&&order.get("ratestatus").equals("2"))){
			request.setAttribute("resultInfo", getMessage(request, "eccredit_rate_repeat"));
			return mapping.findForward("showMessage");
		}else if(!Tenpayapi.trade_typestatus("successtrades").equals(order.get("status"))&&!Tenpayapi.trade_typestatus("refundsuccess").equals(order.get("status"))){
			request.setAttribute("resultInfo", getMessage(request, "eccredit_nofound"));
			return mapping.findForward("showMessage");
		}
		String uid = jsprun_uid ==Common.toDigit(order.get("buyerid"))?order.get("sellerid"): order.get("buyerid");
		try{
			if(submitCheck(request, "ratesubmit")){
				String ec_credit = settings.get("ec_credit");
				Map buycreditMap = dataParse.characterParse(ec_credit, true);
				String maxcreditspermonth = buycreditMap.get("maxcreditspermonth").toString();
				String score = request.getParameter("score");
				String message = request.getParameter("message");
				message = message==null?"":Common.addslashes(message);
				message = Common.cutstr(Common.htmlspecialchars(message), 200);
				String level = score.equals("1")?"good":(score.equals("0")?"soso":"bad");
				String pid = order.get("pid");
				dataBaseService.runQuery("INSERT INTO jrun_tradecomments (pid, orderid, type, raterid, rater, ratee, rateeid, score, message, dateline,explanation) VALUES ('"+pid+"', '"+orderid+"', '"+type+"', '"+jsprun_uid+"', '"+Common.addslashes(jsprun_usess)+"', '"+order.get(ratee)+"', '"+order.get(rateeid)+"', '"+score+"', '"+message+"', '"+timestamp+"','')");
				if(order.get("offline").equals("0")){
					List<Map<String,String>> db = dataBaseService.executeQuery("SELECT COUNT(score) as count FROM jrun_tradecomments WHERE raterid='"+jsprun_uid+"' AND type='"+type+"'");
					if(db.size()>0 && Common.toDigit(db.get(0).get("count"))<Common.toDigit(maxcreditspermonth)){
						updateusercredit(Common.toDigit(uid),(type.equals("1")?"sellercredit":"buyercredit"),level,timestamp);
					}
				}
				String ratestatus ="";
				if(type.equals("0")){
					ratestatus = order.get("ratestatus").equals("2")?"3":"1";
				}else{
					ratestatus = order.get("ratestatus").equals("1")?"3":"2";
				}
				dataBaseService.runQuery("UPDATE jrun_tradelog SET ratestatus='"+ratestatus+"' WHERE orderid='"+order.get("orderid")+"'");
				if(!ratestatus.equals("3")){
					Common.sendpm(order.get(rateeid), getMessage(request, "eccredit_subject"), getMessage(request, "eccredit_message", boardurl,orderid,jsprun_usess), "0", "System Message", timestamp);
				}
				request.setAttribute("resultInfo", getMessage(request, "eccredit_succees"));
				return mapping.findForward("showMessage");
			}
		}catch (Exception e) {
			request.setAttribute("resultInfo",e.getMessage());
			return mapping.findForward("showMessage");
		}
		request.setAttribute("order", order);
		return mapping.findForward("ec_rate");
	}
	@SuppressWarnings("unchecked")
	private Map updatecreditcache(int uid,int timestamp, String type){
		Map<String,Integer> all = countcredit(uid,type,timestamp,0);
		Map<String,Integer> halfyear = countcredit(uid,type,timestamp,180);
		Map<String,Integer> thismonth = countcredit(uid,type,timestamp,30);
		Map<String,Integer> thisweek = countcredit(uid,type,timestamp,7);
		Map<String,Integer> before = new HashMap<String,Integer>();
		before.put("good", all.get("good")-halfyear.get("good"));
		before.put("soso", all.get("soso")-halfyear.get("soso"));
		before.put("bad", all.get("bad")-halfyear.get("bad"));
		before.put("total", all.get("total")-halfyear.get("total"));
		Map data = new HashMap();
		data.put("all", all);
		data.put("before", before);
		data.put("halfyear", halfyear);
		data.put("thismonth", thismonth);
		data.put("thisweek", thisweek);
		String datastring = dataParse.combinationChar(data);
		dataBaseService.runQuery("REPLACE INTO jrun_spacecaches (uid, variable, value, expiration) VALUES ('"+uid+"', '"+type+"', '"+datastring+"', '"+(timestamp+86400)+"')");
		return data;
	}
	private Map<String,Integer> countcredit(int uid, String type,int timestamp,int days){
		Map<String,Integer> result = new HashMap<String,Integer>();
		type = type.equals("buyercredit")?"1":"0";
		String timeadd = days>0?" and dateline>="+(timestamp-days*86400):"";
		List<Map<String,String>> credits = dataBaseService.executeQuery("select score from jrun_tradecomments where rateeid="+uid+" and type="+type+timeadd);
		int good = 0;int soso=0;int bad=0;
		for(Map<String,String>credit:credits){
			if(credit.get("score").equals("1")){
				good++;
			}else if(credit.get("score").equals("0")){
				soso++;
			}else{
				bad++;
			}
		}
		result.put("good", good);
		result.put("soso", soso);
		result.put("bad", bad);
		result.put("total", good+soso+bad);
		return result;
	}
	@SuppressWarnings("unchecked")
	private void updateusercredit(int uid,String type,String level,int timestamp){
		if(uid<=0|| !Common.in_array(new String[]{"buyercredit","sellercredit"},type)||!Common.in_array(new String[]{"good","soso","bad"},level)){
			return;
		}
		List<Map<String,String>> caches = dataBaseService.executeQuery("SELECT value, expiration FROM jrun_spacecaches WHERE uid='"+uid+"' AND variable='"+type+"'");
		int expiration = 0;
		Map<String,Map<String,Integer>> cache = new HashMap<String,Map<String,Integer>>();
		if(caches.size()>0){
			cache = dataParse.characterParse(caches.get(0).get("value"), false);
			expiration = Integer.valueOf(caches.get(0).get("expiration"));
		}else{
			Map<String,Integer> init = new HashMap<String,Integer>();
			init.put("good", 0);
			init.put("soso", 0);
			init.put("bad", 0);
			init.put("total", 0);
			cache.put("all", init);
			cache.put("before", init);
			cache.put("halfyear", init);
			cache.put("thismonth", init);
			cache.put("thisweek", init);
			expiration =timestamp+86400;
		}
		String [] array = new String[]{"all","before","halfyear","thismonth","thisweek"};
		for(String key:array){
			Map chchekey = cache.get(key);
			chchekey.put(level, Common.toDigit(chchekey.get(level).toString())+1);
			chchekey.put("total", Common.toDigit(chchekey.get("total").toString())+1);
		}
		String cachedata = dataParse.combinationChar(cache);
		dataBaseService.runQuery("REPLACE INTO jrun_spacecaches (uid, variable, value, expiration) VALUES ('"+uid+"', '"+type+"', '"+cachedata+"', '"+expiration+"')");
		String score = level.equals("good")?"1":(level.equals("soso")?"0":"-1");
		dataBaseService.runQuery("update jrun_memberfields set "+type+"="+type+"+"+score+" where uid="+uid);
	}
}