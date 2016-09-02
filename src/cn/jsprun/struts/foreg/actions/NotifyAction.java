package cn.jsprun.struts.foreg.actions;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.jsprun.api.Tenpayapi;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import com.tenpay.c2c.bean.PayResponse;
import com.tenpay.c2c.helper.PayReponseHelper;
public class NotifyAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward tradenotify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settings=ForumInit.settings;
		PayReponseHelper payResponseHelper = new PayReponseHelper();
		payResponseHelper.setPayResponse(request);
		PayResponse payResponse = payResponseHelper.getPayResponse();
		String key = settings.get("ec_key");
		payResponseHelper.setKey(key);
		if( payResponseHelper.isTenpaySign() ) {
			if(payResponse.getCmdno()==12){
				if( "0".equals(payResponse.getRetcode()) ) {
					String orderid=payResponse.getMch_vno();
					if(orderid!=null&&orderid.length()>0){
						List<Map<String,String>> tradelogs=dataBaseService.executeQuery("SELECT status, tid, pid, price, subject, buyercredits, buyerid, buyer, sellerid, seller, number FROM jrun_tradelog WHERE orderid = ?",orderid);
						if(tradelogs!=null&&tradelogs.size()>0){
							Map<String,String> tradelog=tradelogs.get(0);
							int oldstatus=Integer.valueOf(tradelog.get("status"));
							if(oldstatus!=Tenpayapi.STATUS_TRADE_SUCCESS&&oldstatus!=Tenpayapi.STATUS_REFUND_CLOSE){
								int status=payResponse.getStatus();
								int timestamp = (Integer)(request.getAttribute("timestamp"));
								switch(status) {
									case 1: 
										status=2;
										break;
									case 2:
										status=3;
										break;
									case 3:
										status=4;
										break;
									case 4:
										status=5;
										break;
									case 5:
										status=6;
										break;
									case 6:
										status=8;
										break;
									case 7:
										break;
									case 8:
										status=10;
										break;
									case 9:
										status=17;
										break;
									case 10:
										status=18;
										break;
									default:
										status=0;
								}
								dataBaseService.runQuery("UPDATE jrun_tradelog SET status = '"+status+"', lastupdate='"+timestamp+"', tradeno='"+payResponse.getCft_tid()+"' WHERE orderid = '"+orderid+"'");
								if(status!=oldstatus){
									HttpSession session=request.getSession();
									String boardurl=(String)session.getAttribute("boardurl");
									if(status==Tenpayapi.STATUS_SELLER_SEND){
										String message=getMessage(request, "trade_seller_send_message",tradelog.get("buyer"),tradelog.get("subject"),boardurl+"trade.jsp?orderid="+orderid);
										Common.sendpm(tradelog.get("sellerid"), getMessage(request, "trade_seller_send_subject"), message, "0", "System Message", timestamp);
									}else if(status==Tenpayapi.STATUS_WAIT_BUYER){
										String message=getMessage(request, "trade_buyer_confirm_message",tradelog.get("subject"),tradelog.get("seller"),boardurl+"trade.jsp?orderid="+orderid);
										Common.sendpm(tradelog.get("buyerid"), getMessage(request, "trade_buyer_confirm_subject"), message, "0", "System Message", timestamp);
									}else if(status==Tenpayapi.STATUS_TRADE_SUCCESS){
										dataBaseService.runQuery("UPDATE jrun_trades SET lastbuyer='"+tradelog.get("buyer")+"', lastupdate='"+timestamp+"', totalitems=totalitems+'"+tradelog.get("number")+"', tradesum=tradesum+'"+tradelog.get("price")+"' WHERE tid='"+tradelog.get("tid")+"' AND pid='"+tradelog.get("pid")+"'");
										Map creditspolicys=dataParse.characterParse(settings.get("creditspolicy"),false);
										Map<Integer, Integer> tradefinished = (Map<Integer,Integer>)creditspolicys.get("tradefinished");
										int sellerid=Integer.valueOf(tradelog.get("sellerid"));
										int buyerid=Integer.valueOf(tradelog.get("buyerid"));
										if(sellerid>0){
											Common.updatepostcredits("+", sellerid, tradefinished, timestamp);
											Common.updatepostcredits(sellerid,settings.get("creditsformula"));
										}
										if(buyerid>0){
											Common.updatepostcredits("+", buyerid, tradefinished, timestamp);
											Common.updatepostcredits(buyerid,settings.get("creditsformula"));
										}
										String message=getMessage(request, "trade_success_message",tradelog.get("subject"),boardurl+"trade.jsp?orderid="+orderid);
										Common.sendpm(tradelog.get("sellerid"), getMessage(request, "trade_success_subject"), message, "0", "System Message", timestamp);
										Common.sendpm(tradelog.get("buyerid"), getMessage(request, "trade_success_subject"), message, "0", "System Message", timestamp);
									}else if(status==Tenpayapi.STATUS_REFUND_CLOSE){
										dataBaseService.runQuery("UPDATE jrun_trades SET amount=amount+'"+tradelog.get("number")+"' WHERE tid='"+tradelog.get("tid")+"' AND pid='"+tradelog.get("pid")+"'");
										String message=getMessage(request, "trade_fefund_success_message",tradelog.get("subject"),boardurl+"trade.jsp?orderid="+orderid);
										Common.sendpm(tradelog.get("sellerid"), getMessage(request, "trade_fefund_success_subject"), message, "0", "System Message", timestamp);
										Common.sendpm(tradelog.get("buyerid"), getMessage(request, "trade_fefund_success_subject"), message, "0", "System Message", timestamp);
										int creditstrans=Common.toDigit(settings.get("creditstrans"));
										int buyerid=Integer.valueOf(tradelog.get("tradelog"));
										if(creditstrans>0&&buyerid>0){
											String creditsformula=settings.get("creditsformula");
											dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"+"+tradelog.get("buyercredits")+",credits="+creditsformula+" WHERE uid='"+buyerid+"'");
										}
									}
								}
								PrintWriter out;
								try {
									out = response.getWriter();
									out.write("success");
									out.flush();
									out.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			PrintWriter out;
			try {
				out = response.getWriter();
				out.write("fail");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			PrintWriter out;
			try {
				out = response.getWriter();
				out.write("Access Denied");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public ActionForward ordernotify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> settings=ForumInit.settings;
		PayReponseHelper payResponseHelper = new PayReponseHelper();
		payResponseHelper.setPayResponse(request);
		PayResponse payResponse = payResponseHelper.getPayResponse();
		String key = settings.get("ec_key");
		payResponseHelper.setKey(key);
		if( payResponseHelper.isTenpaySign() ) {
			if(payResponse.getCmdno()==12){
				if( "0".equals(payResponse.getRetcode()) ) {
					String order_no=payResponse.getMch_vno();
					if(order_no!=null&&order_no.length()>0){
						List<Map<String,String>> orders=dataBaseService.executeQuery("SELECT o.*, m.username FROM jrun_orders o LEFT JOIN jrun_members m USING (uid) WHERE o.orderid='"+order_no+"'");
						if(orders!=null&&orders.size()>0){
							Map<String,String> order=orders.get(0);
							int oldstatus=Integer.valueOf(order.get("status"));
							if(oldstatus==1){
								HttpSession session = request.getSession();
								String creditsformula=settings.get("creditsformula");
								String dateformat = settings.get("dateformat");
								String timeformat = settings.get("gtimeformat");
								String timeoffset = settings.get("timeoffset");
								int timestamp = (Integer)(request.getAttribute("timestamp"));
								int creditstrans = Integer.valueOf(settings.get("creditstrans"));
								dataBaseService.runQuery("UPDATE jrun_orders SET status='2', buyer='"+payResponse.getBuyer_id()+"', confirmdate='"+timestamp+"' WHERE orderid='"+order_no+"'", true);
								dataBaseService.runQuery("UPDATE jrun_members SET extcredits"+creditstrans+"=extcredits"+creditstrans+"+"+order.get("amount")+",credits="+creditsformula+" WHERE uid="+order.get("uid"), true);
								dataBaseService.runQuery("DELETE FROM jrun_orders WHERE submitdate<"+(timestamp-60*86400)+"", true);
								dataBaseService.runQuery("INSERT INTO jrun_creditslog (uid, fromto, sendcredits, receivecredits, send, receive, dateline, operation) VALUES ('"+order.get("uid")+"', '"+order.get("username")+"', '"+creditstrans+"', '"+creditstrans+"', '0', '"+order.get("amount")+"', '"+timestamp+"', 'AFD')", true);
								SimpleDateFormat sdf_all=Common.getSimpleDateFormat(dateformat+" "+timeformat, timeoffset);
								String submitdate=Common.gmdate(sdf_all, Integer.valueOf(order.get("submitdate")));
								String confirmdate=Common.gmdate(sdf_all, timestamp);
								String boardurl=(String)session.getAttribute("boardurl");
								Map<String,Map> extcredits = dataParse.characterParse(settings.get("extcredits"), true);
								Map creditstran=extcredits.get(String.valueOf(creditstrans));
								String message=getMessage(request, "a_extends_addfunds_message1",order.get("orderid"),submitdate,confirmdate,order.get("price"),creditstran.get("title")+" "+order.get("amount"),String.valueOf(creditstran.get("unit")))+"[url="+boardurl+"memcp.jsp?action=creditslog&operation=creditslog]"+getMessage(request, "a_extends_addfunds_message2")+"[/url]"+getMessage(request, "a_extends_addfunds_message3");
								Common.sendpm(order.get("uid"), getMessage(request, "a_extends_addfunds_subject"), message, "0", "System Message", timestamp);
								PrintWriter out;
								try {
									out = response.getWriter();
									out.write("success");
									out.flush();
									out.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			PrintWriter out;
			try {
				out = response.getWriter();
				out.write("fail");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			PrintWriter out;
			try {
				out = response.getWriter();
				out.write("Access Denied");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}