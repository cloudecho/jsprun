package cn.jsprun.api;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import org.apache.struts.util.MessageResources;
import cn.jsprun.utils.Base64;
import cn.jsprun.utils.JspRunConfig;
import com.tenpay.c2c.bean.PayRequest;
import com.tenpay.c2c.helper.PayRequestHelper;
public class Tenpayapi {
	public static int JSPRUN_COMMISION=0;
	public static int JSPRUN_DIRECTPAY=1;
	public static int STATUS_SELLER_SEND=4;
	public static int STATUS_WAIT_BUYER=5;
	public static int STATUS_TRADE_SUCCESS=7;
	public static int STATUS_REFUND_CLOSE=17;
	public static String  credit_payurl(String boardurl,String chnid,String key,String bbname,String jsprun_userss,Map creditstrans,String onlineip,int ec_ratio,int timestamp,float price,String orderid,MessageResources mr,Locale locale){
		PayRequest payRequest = new PayRequest();
		payRequest.setChnid(chnid);
		payRequest.setCmdno(12);
		payRequest.setEncode_type("UTF-8".equals(JspRunConfig.CHARSET)?2:1);
		Object unit = creditstrans.get("unit");
		payRequest.setMch_desc(mr.getMessage(locale, "credit_forum_payment")+creditstrans.get("title")+" "+(int)( price * ec_ratio )+" "+(unit!=null?unit:"")+" ("+onlineip+")");
		payRequest.setMch_name(bbname+" - "+jsprun_userss+" - "+mr.getMessage(locale, "credit_payment"));
		payRequest.setMch_price((long)(price*100));
		payRequest.setMch_returl(boardurl+"api/notify.jsp?action=ordernotify");
		payRequest.setMch_type(2);
		payRequest.setMch_vno(orderid);
		payRequest.setNeed_buyerinfo(2);
		payRequest.setSeller(chnid);
		payRequest.setShow_url(boardurl);
		payRequest.setTransport_desc(mr.getMessage(locale, "post_trade_transport_virtual"));
		payRequest.setTransport_fee(0);	
		payRequest.setVersion(2);	
		PayRequestHelper payRequestHelper = new PayRequestHelper(key,payRequest);
		return payRequestHelper.getSendUrl();
	}
	public static String trade_payurl(String boardurl,String chnid,String key,Map<String,Object> pay,Map<String,String> trade,Map<String,String> tradelog){
		PayRequest payRequest = new PayRequest();
		String seller = trade.get("account");
		payRequest.setAttach(Base64.encode("tid="+tradelog.get("tid")+"&pid="+tradelog.get("pid"),JspRunConfig.CHARSET));
		payRequest.setChnid(chnid);
		payRequest.setCmdno(12);
		payRequest.setEncode_type("UTF-8".equals(JspRunConfig.CHARSET)?2:1);
		payRequest.setMch_desc(trade.get("subject"));
		payRequest.setMch_name(trade.get("subject"));
		Float price=Float.valueOf(tradelog.get("baseprice"))*100;
		int quantity=Integer.valueOf(tradelog.get("number"));
		payRequest.setMch_price(price.longValue()*quantity);
		payRequest.setMch_returl(boardurl+"api/notify.jsp?action=tradenotify");
		int transport=Integer.valueOf(tradelog.get("transport"));
		payRequest.setMch_type(transport==3?2:1);
		payRequest.setMch_vno(tradelog.get("orderid"));
		payRequest.setNeed_buyerinfo(2);
		payRequest.setSeller(seller);
		payRequest.setShow_url(boardurl+"viewthread.jsp?do=tradeinfo");
		payRequest.setTransport_desc((String)pay.get("transport"));
		payRequest.setTransport_fee((Integer)pay.get("transportfee")*100);	
		payRequest.setVersion(2);	
		PayRequestHelper payRequestHelper = new PayRequestHelper(key,payRequest);
		return payRequestHelper.getSendUrl();
	}
	public static Object trade_offline(int jsprun_uid,Map<String,String> tradelog,boolean returndlang,MessageResources mr,Locale locale){
		Integer[] tmp=null;
		int buyerid=Integer.valueOf(tradelog.get("buyerid"));
		int sellerid=Integer.valueOf(tradelog.get("sellerid"));
		int status=Integer.valueOf(tradelog.get("status"));
		Map<Integer,Integer[]> data=new HashMap<Integer,Integer[]>();
		if(jsprun_uid==buyerid){
			data.put(0, new Integer[]{4,8});
			data.put(1, new Integer[]{4,8});
			data.put(8, new Integer[]{1,4});
			data.put(5, new Integer[]{7,10});
			data.put(11,new Integer[]{10,7});
			data.put(12,new Integer[]{13});
			tmp=data.get(status);
		}else if(jsprun_uid==sellerid){
			data.put(0, new Integer[]{1,8});
			data.put(8,new Integer[]{1});
			data.put(4,new Integer[]{5});
			data.put(10,new Integer[]{12,11});
			data.put(13,new Integer[]{17});
			tmp=data.get(status);
		}
		if(returndlang){
			Map<Integer,String> result=new TreeMap<Integer,String>();
			if(tmp!=null){
				int length=tmp.length;
				StringBuffer trade_message=new StringBuffer();
				Map<String,String> trade_offlines = getTrade_offlinesMap(mr, locale);
				for (int i = 0; i < length; i++) {
					result.put(tmp[i], trade_offlines.get("trade_offline_"+tmp[i]));
					String message=trade_offlines.get("trade_message_"+tmp[i]);
					if(message!=null){
						trade_message.append(message+"<br />");
					}
				}
				result.put(-1, trade_message.toString());
			}
			return result;
		}else{
			List<Integer> result=new ArrayList<Integer>();
			if(tmp!=null){
				int length=tmp.length;
				for (int i = 0; i < length; i++) {
					result.add(tmp[i]);
				}
			}
			return result;
		}
	}
	public static String trade_typestatus(String method){
		String methodvalue=null;
		if("buytrades".equals(method)){
			methodvalue="1, 5,11, 12";
		}else if("selltrades".equals(method)){
			methodvalue="2, 4,10, 13";
		}else if("successtrades".equals(method)){
			methodvalue="7";
		}else if("tradingtrades".equals(method)){
			methodvalue="1,2,3,4,5,6,10,11,12,13,14,15,16";
		}else if("closedtrades".equals(method)){
			methodvalue="8,17";
		}else if("refundsuccess".equals(method)){
			methodvalue="17";
		}else if("refundtrades".equals(method)){
			methodvalue="14,15,16,17,18";
		}else if("unstarttrades".equals(method)){
			methodvalue="0";
		}else if("eccredittrades".equals(method)){
			methodvalue="7, 17";
		}
		return methodvalue;
	}
	public static boolean trade_typestatus(String method,int status){
		String methodvalue=trade_typestatus(method);
		return (","+methodvalue+",").contains(","+status+",");
	}
	public static Object trade_getstatus(Object key,MessageResources mr,Locale locale){
		return trade_getstatus(key,2,mr,locale);
	}
	public static Object trade_getstatus(Object key,int method,MessageResources mr,Locale locale){
		Map<Integer,Map> status=getStarus(mr,locale);
		return method == -1?status.get(2):status.get(method).get(key);
	}
	@SuppressWarnings("unchecked")
	public static void trade_setprice(Map<String,Object> data,Float price,Map<String,Object> pay,Integer transportfee,MessageResources mr,Locale locale){
		int transport=(Integer)data.get("transport");
		switch (transport) {
			case 1:
				pay.put("transport", mr.getMessage(locale, "post_trade_transport_seller"));
				break;
			case 2:
				pay.put("transport", mr.getMessage(locale, "post_trade_transport_buyer"));
				break;
			case 3:
				pay.put("transport", mr.getMessage(locale, "post_trade_transport_virtual"));
				break;
			default:
				pay.put("transport", mr.getMessage(locale, "post_trade_transport_physical"));
				break;
		}
		if(transport!=3){
			int fee=(Integer)data.get("fee");
			Map<String,String> trade=(Map<String,String>)data.get("trade");
			switch (fee) {
				case 1:
					int ordinaryfee=Integer.valueOf(trade.get("ordinaryfee"));
					pay.put("logistics_type", "POST");
					pay.put("logistics_fee", ordinaryfee);
					if(transport==2){
						price = price + ordinaryfee;
						transportfee=ordinaryfee;
					}
					break;
				case 2:
					int emsfee=Integer.valueOf(trade.get("emsfee"));
					pay.put("logistics_type", "EMS");
					pay.put("logistics_fee", emsfee);
					if(transport==2){
						price = price + emsfee;
						transportfee=emsfee;
					}
					break;
				case 3:
					int expressfee=Integer.valueOf(trade.get("expressfee"));
					pay.put("logistics_type", "EXPRESS");
					pay.put("logistics_fee", expressfee);
					if(transport==2){
						price = price + expressfee;
						transportfee=expressfee;
					}
					break;
			}
		}
		pay.put("price", price);
		pay.put("transportfee", transportfee);
	}
	private static Map<String,String> getTrade_offlinesMap(MessageResources mr,Locale locale){
		Map<String,String> trade_offlines = new HashMap<String, String>();
		initTrade_offlinesMap(mr, locale,trade_offlines);
		return trade_offlines;
	}
	private static void initTrade_offlinesMap(MessageResources mr,Locale locale,Map<String,String> trade_offlines){
		trade_offlines.put("trade_offline_1", mr.getMessage(locale, "trade_offline_1"));
		trade_offlines.put("trade_offline_4", mr.getMessage(locale, "trade_offline_4"));
		trade_offlines.put("trade_offline_5", mr.getMessage(locale, "trade_offline_5"));
		trade_offlines.put("trade_offline_7", mr.getMessage(locale, "trade_offline_7"));
		trade_offlines.put("trade_offline_8", mr.getMessage(locale, "trade_offline_8"));
		trade_offlines.put("trade_offline_10", mr.getMessage(locale, "trade_offline_10"));
		trade_offlines.put("trade_offline_11", mr.getMessage(locale, "trade_offline_11"));
		trade_offlines.put("trade_offline_12", mr.getMessage(locale, "trade_offline_12"));
		trade_offlines.put("trade_offline_13", mr.getMessage(locale, "trade_offline_13"));
		trade_offlines.put("trade_offline_17", mr.getMessage(locale, "trade_offline_17"));
		trade_offlines.put("trade_message_4", mr.getMessage(locale, "trade_message_4"));
		trade_offlines.put("trade_message_5", mr.getMessage(locale, "trade_message_5"));
		trade_offlines.put("trade_message_13", mr.getMessage(locale, "trade_message_5"));
	}
	private static Map<Integer,Map> getStarus(MessageResources mr,Locale locale){
		Map<Integer,Map> status=new HashMap<Integer,Map>(2);
		Map<String,Integer> status1 = getStatus1();
		Map<Integer,String> status2 = getStatus2(mr,locale);
		status.put(1, status1);
		status.put(2, status2);
		return status;
	}
	private static Map<String,Integer> getStatus1(){
		Map<String,Integer> status1=new HashMap<String,Integer>();
		status1.put("WAIT_BUYER_PAY", 1);
		status1.put("WAIT_SELLER_CONFIRM_TRADE", 2);
		status1.put("WAIT_SYS_CONFIRM_PAY", 3);
		status1.put("WAIT_SELLER_SEND_GOODS", 4);
		status1.put("WAIT_BUYER_CONFIRM_GOODS", 5);
		status1.put("WAIT_SYS_PAY_SELLER", 6);
		status1.put("TRADE_FINISHED", 7);
		status1.put("TRADE_CLOSED", 8);
		status1.put("WAIT_SELLER_AGREE", 10);
		status1.put("SELLER_REFUSE_BUYER", 11);
		status1.put("WAIT_BUYER_RETURN_GOODS", 12);
		status1.put("WAIT_SELLER_CONFIRM_GOODS", 13);
		status1.put("WAIT_ALIPAY_REFUND", 14);
		status1.put("ALIPAY_CHECK", 15);
		status1.put("OVERED_REFUND", 16);
		status1.put("REFUND_SUCCESS", 17);
		status1.put("REFUND_CLOSED", 18);
		return status1;
	}
	private static Map<Integer,String> getStatus2(MessageResources mr,Locale locale){
		Map<Integer,String> status2=new TreeMap<Integer,String>();
		status2.put(0, mr.getMessage(locale, "trade_unstart"));
		status2.put(1, mr.getMessage(locale, "trade_waitbuyerpay"));
		status2.put(2, mr.getMessage(locale, "trade_waitsellerconfirm"));
		status2.put(3, mr.getMessage(locale, "trade_sysconfirmpay"));
		status2.put(4, mr.getMessage(locale, "trade_waitsellersend"));
		status2.put(5, mr.getMessage(locale, "trade_waitbuyerconfirm"));
		status2.put(6, mr.getMessage(locale, "trade_syspayseller"));
		status2.put(7, mr.getMessage(locale, "trade_finished"));
		status2.put(8, mr.getMessage(locale, "trade_closed_api"));
		status2.put(10, mr.getMessage(locale, "trade_waitselleragree"));
		status2.put(11, mr.getMessage(locale, "trade_sellerrefusebuyer"));
		status2.put(12, mr.getMessage(locale, "trade_waitbuyerreturn"));
		status2.put(13, mr.getMessage(locale, "trade_waitsellerconfirmgoods"));
		status2.put(14, mr.getMessage(locale, "trade_waitalipayrefund"));
		status2.put(15, mr.getMessage(locale, "trade_alipaycheck"));
		status2.put(16, mr.getMessage(locale, "trade_overedrefund"));
		status2.put(17, mr.getMessage(locale, "trade_refundsuccess"));
		status2.put(18, mr.getMessage(locale, "trade_refundclosed"));
		return status2;
	}
}
