package cn.jsprun.struts.foreg.actions;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Threads;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
public class FooterManageAction extends BaseAction {
	private boolean cronRunning = false;
	@SuppressWarnings("unchecked")
	public ActionForward header(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		request.setAttribute("plugins", dataParse.characterParse(settings.get("plugins"), false));
		return null;
	}
	@SuppressWarnings("unchecked")
	public ActionForward footer(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Map<String, String> settings = ForumInit.settings;
		Members member = (Members) session.getAttribute("user");
		String styleid = request.getParameter("styleid");
		if (member != null && styleid != null) {
			member.setStyleid((short) Common.range(Common.intval(styleid), 255, 0));
			Common.updateMember(session, member.getUid());
		}
		if ("1".equals(settings.get("stylejump"))) {
			request.setAttribute("forumStyles", dataParse.characterParse(settings.get("forumStyles"), true));
		}
		Common.updatesession(request, settings);
		showAdvertisements(request, settings);
		int timestamp = (Integer) request.getAttribute("timestamp");
		String timeoffset = (String) session.getAttribute("timeoffset");
		String dateformat = (String) session.getAttribute("dateformat");
		String timeformat = (String) session.getAttribute("timeformat");
		int cronnextrun = Common.intval(settings.get("cronnextrun"));
		if (cronnextrun > 0 && cronnextrun <= timestamp && !cronRunning) {
			cronRunning = true;
			cronsService.cronsRun(timestamp, request, response);
			cronRunning = false;
		}
		if ("true".equals(settings.get("updatetime"))) {
			dataBaseService.runQuery("REPLACE INTO jrun_settings VALUES ('lastupdate','" + timestamp
					+ "'),('status','" + settings.get("status") + "')");
			settings.put("updatetime", "false");
		}
		Map timenow = new HashMap();
		int offset = timeoffset.compareTo("0");
		timenow.put("offset", offset >= 0 ? (offset == 0 ? "" : "+" + timeoffset) : timeoffset);
		timenow.put("time", Common.gmdate(dateformat + " " + timeformat, timestamp, timeoffset));
		request.setAttribute("timenow", timenow);
		if ("1".equals(settings.get("debug"))) {
			long starttime = (Long) request.getAttribute("starttime");
			long endtime = System.currentTimeMillis();
			Map<String, String> debuginfo = new HashMap<String, String>();
			debuginfo.put("time", Common.number_format((endtime - starttime) / 1000f, "0.000000"));
			debuginfo.put("queries", "0");
			request.setAttribute("debuginfo", debuginfo);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	private void showAdvertisements(HttpServletRequest request, Map<String, String> settings) {
		Map<String, String> _DCACHE_advsMap = (Map<String, String>) request.getAttribute("advs");
		Map globaladvs = dataParse.characterParse(settings.get("globaladvs"), false);
		String isRedirect = request.getParameter("isRedirect");
		if ("true".equals(isRedirect)) {
			Map redirectadvs = dataParse.characterParse(settings.get("redirectadvs"), false);
			if (redirectadvs != null && redirectadvs.size() > 0) {
				if (globaladvs == null) {
					globaladvs = redirectadvs;
				} else {
					globaladvs.putAll(redirectadvs);
				}
			}
		}
		Map<String, Map<String, String>> advarray = new HashMap<String, Map<String, String>>();
		Map<String, Object> advitems = new HashMap<String, Object>();
		if (_DCACHE_advsMap != null) {
			Map _DCACHE_advs = dataParse.characterParse(_DCACHE_advsMap != null ? _DCACHE_advsMap.get("advs")
					: null, false);
			_DCACHE_advsMap = null;
			Map<String, Map<String, String>> advs = (Map<String, Map<String, String>>) _DCACHE_advs
					.get("type");
			advitems = (Map<String, Object>) _DCACHE_advs.get("items");
			if (advitems == null) {
				advitems = new HashMap<String, Object>();
			}
			String curscript = (String) request.getAttribute("CURSCRIPT");
			curscript = curscript != null ? curscript : "";
			Short fid = (Short) request.getAttribute("fid");
			if (fid == null) {
				Threads thread = (Threads) request.getAttribute("thread");
				if (thread != null) {
					fid = thread.getFid();
				}
			}
			if (("forumdisplay.jsp".equals(curscript) || "viewthread.jsp".equals(curscript)) && fid != null) {
				if (advs != null && advs.size() > 0) {
					Set<String> keys = advs.keySet();
					for (String type : keys) {
						Map<String, String> advitem = advs.get(type);
						Map<String, String> map = new HashMap<String, String>();
						String advids = advitem.get("forum_" + fid);
						String advid = advitem.get("forum_all");
						if (advid != null) {
							map.put("forum_" + fid, advid);
						}
						if (advids != null) {
							advids = advid != null ? advid + "," + advids : advids;
							map.put("forum_" + fid, advids);
						}
						if (map.size() > 0) {
							if (type.length() > 5 && type.substring(0, 6).equals("thread")) {
								String threadtype = type.substring(0, 7);
								Map<String, String> advthreadtypes = advarray.get(threadtype);
								if (advthreadtypes == null) {
									advthreadtypes = new HashMap<String, String>();
								}
								advthreadtypes.put(type.substring(8), map.get("forum_" + fid));
								advarray.put(threadtype, advthreadtypes);
							} else {
								advarray.put(type, map);
							}
						}
					}
					advs = advarray;
				}
			}
			if (globaladvs != null && globaladvs.size() > 0) {
				if (advs == null) {
					advs = new HashMap<String, Map<String, String>>();
				}
				Map<String, Map<String, String>> types = (Map<String, Map<String, String>>) globaladvs
						.get("type");
				if (types != null) {
					Set<String> keys = types.keySet();
					for (String type : keys) {
						Map<String, String> advitem = advs.get(type);
						Map<String, String> typeitems = types.get(type);
						if (advitem != null && advitem.size() > 0) {
							Set<Entry<String,String>> objs = advitem.entrySet();
							for (Entry<String,String> temp : objs) {
								String obj = temp.getKey();
								String advids = temp.getValue();
								String advid = typeitems.get("all");
								if (advids != null) {
									advids += "," + advid;
									advitem.put(obj.trim(), advids);
								} else {
									advitem.put(obj.trim(), advid);
								}
							}
						} else {
							advitem = new HashMap<String, String>();
							advitem.putAll(typeitems);
						}
						advs.put(type, advitem);
					}
					advitems.putAll((Map<String, String>) globaladvs.get("items"));
				}
				types = null;
			}
			advarray = advs;
			advs = null;
		} else {
			if (globaladvs != null && globaladvs.size() > 0) {
				advarray = (Map<String, Map<String, String>>) globaladvs.get("type");
				advitems = (Map<String, Object>) globaladvs.get("items");
				if (advitems == null) {
					advitems = new HashMap<String, Object>();
				}
			}
		}
		globaladvs = null;
		if (advarray != null && advarray.size() > 0) {
			Map<String, Map<String, String>> advlist = new HashMap<String, Map<String, String>>();
			Map<String, Map<Integer, String>> advthreads = new HashMap<String, Map<Integer, String>>();
			Set<Entry<String, Map<String, String>>> keys = advarray.entrySet();
			for (Entry<String, Map<String, String>> temp : keys) {
				String advtype = temp.getKey();
				Map<String, String> advcodes = temp.getValue();
				Set<String> objs = advcodes.keySet();
				if (advtype.length() > 5 && advtype.substring(0, 6).equals("thread")) {
					Map<Integer, String> advtypes = advthreads.get(advtype);
					if (advtypes == null) {
						advtypes = new HashMap<Integer, String>();
					}
					int ppp = Common.toDigit(settings.get("postperpage"));
					for (int i = 1; i <= ppp; i++) {
						String advid = advcodes.get(String.valueOf(i));
						if (advid == null) {
							advid = advcodes.get("0");
						}
						if (advid != null) {
							String[] advids = advid.split(",");
							advtypes.put(i - 1, (String)advitems.get(advids[Common.rand(advids.length - 1)]));
						}
					}
					advthreads.put(advtype, advtypes);
				} else if ("intercat".equals(advtype)) {
					for (String obj : objs) {
						String[] advid = advcodes.get(obj).split(",");
						advcodes.put(obj, advid[Common.rand(advid.length - 1)]);
					}
					advlist.put("intercat", advcodes);
				} else {
					if ("text".equals(advtype)) {
						for (String obj : objs) {
							String[] advids = advcodes.get(obj).split(",");
							float advcols = 0;
							float advcount = advids.length;
							if (advcount > 5) {
								float minfillpercent = 0;
								for (float cols = 5; cols >= 3; cols--) {
									float remainder = advcount % cols;
									if (remainder == 0) {
										advcols = cols;
										break;
									} else if (remainder / cols > minfillpercent) {
										minfillpercent = remainder / cols;
										advcols = cols;
									}
								}
							} else {
								advcols = advcount;
							}
							StringBuffer advtypestr = new StringBuffer();
							for (int i = 0; i < advcols * Math.ceil(advcount / advcols); i++) {
								advtypestr.append(((i + 1) % advcols == 1 || advcols == 1 ? "<tr>" : "")
										+ "<td width=\"" + (100 / advcols) + "%\">"
										+ (i < advcount ? advitems.get(advids[i]) : "&nbsp;") + "</td>"
										+ ((i + 1) % advcols == 0 ? "</tr>\n" : ""));
							}
							advcodes.put(obj, advtypestr.toString());
						}
						advlist.put(advtype, advcodes);
					} else {
						int tempI = 0;
						for (String obj : objs) {
							String[] advid = advcodes.get(obj).split(",");
							tempI = Common.rand(advid.length - 1);
							if("float".equals(advtype)){
								String floatInfo = (String)advitems.get(advid[tempI]);
								Map<String,String> floatIM = dataParse.characterParse(floatInfo, false);
								advitems.put(advid[tempI], floatIM);
							}
							advcodes.put(obj, advid[tempI]);
						}
						advlist.put(advtype, advcodes);
					}
				}
			}
			request.setAttribute("advlist", advlist);
			request.setAttribute("advthreads", advthreads);
			request.setAttribute("advitems", advitems);
			advitems = null;
			advthreads = null;
			advlist = null;
		}
		advarray = null;
	}
}