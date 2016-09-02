package cn.jsprun.utils;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;
public class Jspruncode {
	private List<String> codelist = null;
	private int codecount = 0;
	public String parseimg(String message,boolean isparse){
		if(isparse){
			for(int i=0;i<JsprunCodeProp.bbcode_img.length;i++){
				message = message.replaceAll(JsprunCodeProp.bbcode_img[i], JsprunCodeProp.bbcode_imgreplace[i]);
			}
		}else{
			for(int i=0;i<JsprunCodeProp.bbcode_img.length;i++){
				message = message.replaceAll(JsprunCodeProp.bbcode_img[i], JsprunCodeProp.bbcode_imgsp[i]);
			}
		}
		return message;
	}
	public String parseCode(String message,int count,String copycode){
		Matcher m = JsprunCodeProp.pCode.matcher(message); 
		StringBuffer b = new StringBuffer();  
	    while(m.find()){
	    	setCodelist();
	    	String bbcode = m.group(3);
	    	bbcode = bbcode.replaceAll("\\\"", "\"").replaceAll("(?is)^[\n\r]*(.+?)[\n\r]*$", "$1");
	    	bbcode = htmlspecialchars(bbcode,1);
	    	bbcode = bbcode.replace("\n", "<br><li>");
	    	bbcode = bbcode.replaceAll("\t", "&nbsp; &nbsp; &nbsp; &nbsp; ").replace("   ", "&nbsp; &nbsp;").replace("  ", "&nbsp;&nbsp;").replace(" ", "&nbsp;");
	    	codelist.add("<div class=\"blockcodenew\" onmouseover=\"$('div_"+count+"_"+codecount+"').style.display=''\"; onmouseout=\"$('div_"+count+"_"+codecount+"').style.display='none'\"><div style=\"float:left;width:573px;\" id=\"code"+count+"_"+codecount+"\"><ol><li>"+bbcode+"</ol></div><div id=\"div_"+count+"_"+codecount+"\" style=\"display:none; padding-right:10px;\"><em onclick=\"copycode($('code"+count+"_"+codecount+"'));\" id=\"emcode_"+count+"_"+codecount+"\"><script type='text/javascript'>codeinit('emcode_"+count+"_"+codecount+"',$('code"+count+"_"+codecount+"'));</script></em></div></div>");
	    	m.appendReplacement(b,"[\tJSPRUN_CODE_"+codecount+"\t]");  
	    	codecount++;
	    }
	    m.appendTail(b);  
	    return b.toString();  
	}
	public String parseCodeP(String message,MessageResources mr,Locale locale,boolean isprintable){
		Matcher m = JsprunCodeProp.pCode.matcher(message); 
		StringBuffer b = new StringBuffer();  
	    while(m.find()){
	    	setCodelist();
	    	String bbcode = m.group(3);
	    	bbcode = bbcode.replaceAll("\\\"", "\"").replaceAll("(?is)^[\n\r]*(.+?)[\n\r]*$", "$1");
	    	bbcode = htmlspecialchars(bbcode,1);
	    	bbcode = bbcode.replace("\n", "<br>");
	    	bbcode = bbcode.replaceAll("\t", "&nbsp; &nbsp; &nbsp; &nbsp; ").replace("   ", "&nbsp; &nbsp;").replace("  ", "&nbsp;&nbsp;").replace(" ", "&nbsp;");
	    	String str = isprintable?"":"<h5>"+mr.getMessage(locale,"a_other_adv_style_code")+":</h5>";
	    	codelist.add("<div style='font-size: 12px'><br /><br /><div class='blockcode'>"+str+"<code id='code0'>"+bbcode+"</code></div></div>");
	    	m.appendReplacement(b,"[\tJSPRUN_CODE_"+codecount+"\t]");  
	    	codecount++;
	    }
	    m.appendTail(b);  
	    return b.toString();  
	}
	public String parseJsprunCode(String message,MessageResources mr,Locale locale){
		for(int i=0;i<JsprunCodeProp.bbcode_speciald.length;i++){
			message = message.replaceAll(JsprunCodeProp.bbcode_speciald[i], JsprunCodeProp.bbcode_replaced[i]);
		}
		String bbcode_str_replace[] = {"</font>","</font>","</font>","</p>","<strong>",
				"</strong>","<i>","</i>","<u>","</u>","<ul>","<ul type='1'>","<ul type='a'>",
				"<ul type='A'>","<li>","</ul>","<blockquote>","</blockquote>","</span>",
				"<div class='quote'><h5>"+mr.getMessage(locale,"reply_quote")+":</h5><blockquote>",
				"</blockquote></div>","</font>","</a>","</marquee>","<sup>","</sup>","<sub>",
				"</sub>","<marquee scrollamount='3' behavior='alternate' width='90%'>"};
		for (int i = 0; i < JsprunCodeProp.bbcode_str.length; i++) {
			message = message.replaceAll(JsprunCodeProp.bbcode_str[i], bbcode_str_replace[i]);
		}
		return message;
	}
	public String parsemedia(String message,boolean isparse){
			if(isparse){
				message = message.replaceAll("(\\B|\\b)?\\[media=ra,(\\d+),(\\d+),(\\d+)\\](.*)\\[/media\\](\\B|\\b)?", StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(JsprunCodeProp.ramessage, "{1}", "$2"), "{2}", "$3"), "{3}", "$4"),"{4}","$5"));
				message = message.replaceAll("(\\B|\\b)?\\[media=rm,(\\d+),(\\d+),(\\d+)\\](.*)\\[/media\\](\\B|\\b)?", StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(JsprunCodeProp.rmmessage, "{1}", "$2"), "{2}", "$3"), "{3}", "$4"),"{4}","$5"));
				message = message.replaceAll("(\\B|\\b)?\\[media=wma,(\\d+),(\\d+),(\\d+)\\](.*)\\[/media\\](\\B|\\b)?", StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(JsprunCodeProp.wmamessage, "{1}", "$2"), "{2}", "$3"), "{3}", "$4"),"{4}","$5"));
				message = message.replaceAll("(\\B|\\b)?\\[media=wmv,(\\d+),(\\d+),(\\d+)\\](.*)\\[/media\\](\\B|\\b)?", StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(JsprunCodeProp.wmvmessage, "{1}", "$2"), "{2}", "$3"), "{3}", "$4"),"{4}","$5"));
				message = message.replaceAll("(\\B|\\b)?\\[media=mp3,(\\d+),(\\d+),(\\d+)\\](.*)\\[/media\\](\\B|\\b)?", StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(JsprunCodeProp.mp3message, "{1}", "$2"), "{2}", "$3"), "{3}", "$4"),"{4}","$5"));
				message = message.replaceAll("(\\B|\\b)?\\[media=(\\w+),(\\d+),(\\d+),(\\d+)\\](.*)\\[/media\\](\\B|\\b)?", StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(JsprunCodeProp.othermessage, "{1}", "$3"), "{2}", "$4"), "{3}", "$5"),"{4}","$6"));
			}else{
				message = message.replaceAll("(\\B|\\b)?\\[media=ra,(\\d+),(\\d+),(\\d+)\\](.*)\\[/media\\](\\B|\\b)?", "<a href=\"$5\" target=\"_blank\">$5</a>");
				message = message.replaceAll("(\\B|\\b)?\\[media=rm,(\\d+),(\\d+),(\\d+)\\](.*)\\[/media\\](\\B|\\b)?", "<a href=\"$5\" target=\"_blank\">$5</a>");
				message = message.replaceAll("(\\B|\\b)?\\[media=wma,(\\d+),(\\d+),(\\d+)\\](.*)\\[/media\\](\\B|\\b)?", "<a href=\"$5\" target=\"_blank\">$5</a>");
				message = message.replaceAll("(\\B|\\b)?\\[media=wmv,(\\d+),(\\d+),(\\d+)\\](.*)\\[/media\\](\\B|\\b)?", "<a href=\"$5\" target=\"_blank\">$5</a>");
				message = message.replaceAll("(\\B|\\b)?\\[media=mp3,(\\d+),(\\d+),(\\d+)\\](.*)\\[/media\\](\\B|\\b)?", "<a href=\"$5\" target=\"_blank\">$5</a>");
				message = message.replaceAll("(\\B|\\b)?\\[media=(\\w+),(\\d+),(\\d+),(\\d+)\\](.*)\\[/media\\](\\B|\\b)?","<a href=\"$6\" target=\"_blank\">$6</a>");
			}
		return message;
	}
	public String parseCustomCode(String message,Map<String,Map<String, String>> bbcodelist){
		if(bbcodelist!=null && !bbcodelist.keySet().isEmpty()){
			Set<Entry<String,Map<String, String>>> bbcodeSet = bbcodelist.entrySet();
			for(Entry<String,Map<String, String>> temp : bbcodeSet){
				String key = temp.getKey();
				Map<String,String> valueMap = temp.getValue();
					Set<String> valueSet = valueMap.keySet();
					String replement = "";
					String param = "";
					for(String parmkey :valueSet){
						param  = parmkey;
						replement = valueMap.get(parmkey);
					}
					key = key.toLowerCase();
					Pattern p = null;
					Matcher m = null;
					String match = null;
					StringBuffer b = new StringBuffer();
					switch(Common.intval(param)) {
						case 2:
							match = "(?is)\\["+key+"=(['\"]?)([^\"]+?)(['\"]?)\\]([^\"]+?)\\[/"+key+"\\]";
							p = Pattern.compile(match,Pattern.DOTALL);
							m = p.matcher(message);
							while(m.find()){
								String param2 = m.group(2);
								String param3 = m.group(4);
								String repl = replement.replace("{1}", param2).replace("{2}", param3);
								if(repl.matches("\\{(RANDOM|MD5)\\}")){
									String param1 = m.group(1);
									param1 = Md5Token.getInstance().getLongToken(param1);
									String ramdom = Common.getRandStr(6, false);
									repl = repl.replace("{MD5}",param1).replace("{RANDOM}", ramdom);
								}
								m.appendReplacement(b, repl);
							}
							message = m.appendTail(b).toString();
							break;
						case 3:
							match = "(?is)\\["+key+"=(['\"]?)([^\"]+?)(['\"]?),(['\"]?)([^\"]+?)(['\"]?)\\]([^\"]+?)\\[/"+key+"\\]";
							p = Pattern.compile(match,Pattern.DOTALL);
							m = p.matcher(message);
							while(m.find()){
								String param2 = m.group(2);
								String param3 = m.group(5);
								String param4 = m.group(7);
								String repl = replement.replace("{1}", param2).replace("{2}", param3).replace("{3}", param4);
								if(repl.matches("\\{(RANDOM|MD5)\\}")){
									String param1 = m.group(1);
									param1 = Md5Token.getInstance().getLongToken(param1);
									String ramdom = Common.getRandStr(6, false);
									repl = repl.replace("{MD5}",param1).replace("{RANDOM}", ramdom);
								}
								m.appendReplacement(b, repl);
							}
							message = m.appendTail(b).toString();
							break;
						default:
							match = "(?is)\\["+key+"\\]([^\"]+?)\\[/"+key+"\\]";
							p = Pattern.compile(match,Pattern.DOTALL);
							m = p.matcher(message);
							while(m.find()){
								String param1 = m.group(1);
								String repl = replement.replace("{1}", param1);
								if(repl.matches("\\{(RANDOM|MD5)\\}")){
									String md5param1 = Md5Token.getInstance().getLongToken(param1);
									String ramdom = Common.getRandStr(6, false);
									repl = repl.replace("{MD5}",md5param1).replace("{RANDOM}", ramdom);
								}
								m.appendReplacement(b, repl);
							}
							message = m.appendTail(b).toString();
							break;
					}
				}
		}
		return message;
	}
	public String parsetable(String message){
		int count = 0;   
		while(count<50){
			count++;
			Pattern p = Pattern.compile("(?s)\\[table(?:=(\\d{1,4}%?)(?:,([\\(\\)%,#\\w ]+))?)?\\]\\s*(.+?)\\s*\\[/table\\]");
			Matcher m = p.matcher(message);
		    String width = "";String bgcolor = "";
		    String tablemessage = "";
		    if(m.find()){
		    	width = m.group(1);
		    	bgcolor = m.group(2);
		    	tablemessage = m.group(3);
		    }else{
		    	break;
		    }
		    if(!message.matches("^\\[tr(?:=([\\(\\)%,#\\w]+))?\\]\\s*\\[td(?:=(\\d{1,2}),(\\d{1,2})(?:,(\\d{1,4}%?))?)?\\]")&&!message.matches("^<tr[^>]*?>\\s*<td[^>]*?>")){
		    	message = message.replaceFirst("\\[tr(?:=([\\(\\)%,#\\w]+))?\\]|\\[td(?:=(\\d{1,2}),(\\d{1,2})(?:,(\\d{1,4}%?))?)?\\]|\\[/td\\]|\\[/tr\\]", "");
		    }
		    if(!Common.empty(width)){
		    	if(width.endsWith("%")){
		    		width = width.substring(0,width.length()-1);
		    		width = Common.toDigit(width)<=98?width+"%":"98%";
		    	}else{
		    		if(Common.toDigit(width)<=560){
		    			width = width+"px";
		    		}else{
		    			width = "98%";
		    		}
		    	}
		    }
		    String relacemessage = "<table cellspacing=\"0\" class=\"t_table\" "+(Common.empty(width)?"":" style=\"width:"+width+"\"")+(Common.empty(bgcolor)?"":" bgcolor=\""+bgcolor+"\"")+">";
		    p = Pattern.compile("\\[tr(?:=([\\(\\)%,#\\w]+))?\\]\\s*\\[td(?:=(\\d{1,2}),(\\d{1,2})(?:,(\\d{1,4}%?))?)?\\]");
		    m = p.matcher(tablemessage);
		    String colspan = "";
		    String rowspan = "";
		    while(m.find()){
		    	bgcolor = m.group(1);
		    	colspan = m.group(2);
		    	rowspan = m.group(3);
		    	width = m.group(4);
		    	 bgcolor = Common.empty(bgcolor)?"":" bgcolor=\""+bgcolor+"\"";
		 	    colspan = Common.toDigit(colspan)>1?" colspan=\""+colspan+"\"":"";
		 	    rowspan = Common.toDigit(rowspan)>1?" rowspan=\""+rowspan+"\"":"";
		 	    width = Common.toDigit(width)>0?" width=\""+width+"\"":"";
		 	    String tablereplacemessage = "<tr"+bgcolor+"><td"+colspan+rowspan+width+">";
		 	    tablemessage = tablemessage.replaceFirst("\\[tr(?:=([\\(\\)%,#\\w]+))?\\]\\s*\\[td(?:=(\\d{1,2}),(\\d{1,2})(?:,(\\d{1,4}%?))?)?\\]",tablereplacemessage);
		    }
		    p = Pattern.compile("\\[/td\\]\\s*\\[td(?:=(\\d{1,2}),(\\d{1,2})(?:,(\\d{1,4}%?))?)?\\]");
		    m = p.matcher(tablemessage);
		    while(m.find()){
		    	colspan = m.group(1);
		    	rowspan = m.group(2);
		    	width = m.group(3);
		  	    colspan = Common.toDigit(colspan)>1?" colspan=\""+colspan+"\"":"";
		  	    rowspan = Common.toDigit(rowspan)>1?" rowspan=\""+rowspan+"\"":"";
		  	    width = Common.toDigit(width)>0?" width=\""+width+"\"":"";
		  	    String tablereplacemessage = "</td><td"+colspan+rowspan+width+">";
		  	    tablemessage = tablemessage.replaceFirst("\\[/td\\]\\s*\\[td(?:=(\\d{1,2}),(\\d{1,2})(?:,(\\d{1,4}%?))?)?\\]",tablereplacemessage);
		    }
		    tablemessage =  tablemessage.replaceAll("\\[/td\\]\\s*\\[/tr\\]", "</td></tr>");
		    relacemessage = relacemessage+tablemessage+"</table>";
		    message = message.replaceFirst("(?s)\\[table(?:=(\\d{1,4}%?)(?:,([\\(\\)%,#\\w ]+))?)?\\]\\s*(.+?)\\s*\\[/table\\]", relacemessage);
		}
	    return message;
	}
	public String inset(String message,Map<String,Map<String, String>> bbcodelist){
		message = message.replaceAll("\\[(b|i|u|color|size|font|align|list|indent|url|email|code|free|table|tr|td|img|swf|attach|payto|float)=?[^]]*\\]", "");
		message = message.replaceAll("\\[/(b|i|u|color|size|font|align|list|indent|url|email|code|free|table|tr|td|img|swf|attach|payto|float)\\]", "");
		message = message.replaceAll("(?s)\\[hide\\]\\s*(.+?)\\s*\\[/hide\\]", "");
		message = message.replaceAll("(?s)\\[quote\\].*\\[/quote\\]", "");
		message = message.replaceAll("<[^<|^>]*>", "");
		message = message.replaceAll("(\\B|\\b)?\\[media=(\\w+,\\d+,\\d+,\\d+)\\](\\B|\\b)?", "");
		message = message.replaceAll("(\\B|\\b)?\\[/media\\](\\B|\\b)?", "");
		if(bbcodelist!=null && !bbcodelist.keySet().isEmpty()){
			Set<String> bbcodeSet = bbcodelist.keySet();
			for(String key:bbcodeSet){
				message = message.replaceAll("(?is)\\["+key+"=(['\"]?)([^\"]+?)(['\"]?)\\]([^\"]+?)\\[/"+key+"\\]", "");
				message = message.replaceAll("(?is)\\["+key+"=(['\"]?)([^\"]+?)(['\"]?),(['\"]?)([^\"]+?)(['\"]?)\\]([^\"]+?)\\[/"+key+"\\]", "");
				message = message.replaceAll("(?is)\\["+key+"\\]([^\"]+?)\\[/"+key+"\\]", "");
			}
		}
		return message;
	}
	public String htmlspecialchars(String text,int quotestyle){
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while(character != StringCharacterIterator.DONE){
			switch (character) {
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					if(quotestyle==1||quotestyle==2){
						sb.append("&quot;");
					}else{
						sb.append(character);
					}
					break;
				case '\'':
					if(quotestyle==2){
						sb.append("&#039;");
					}else{
						sb.append(character);
					}
					break;
				default:
					sb.append(character);
					break;
			}
			character = iterator.next();
		}
		return sb.toString();
	}
	public String parseCode(String str, String id) {  
	     Matcher m = JsprunCodeProp.pCode.matcher(str);  
	     StringBuffer b = new StringBuffer();  
	     while (m.find()) {
	       setCodelist();
	       String code = m.group(3).replace("&nbsp;", " ");    
	       code = htmlspecialchars(code,1);
	       code = code.replace("\n", "");
	       String type = m.group(2);  
	       if (type == null) {  
	        type = "java";  
	       }
	       codelist.add("<pre name='code' class='brush: " + type + ";'>\n" + code + "\n</pre>");
	       m.appendReplacement(b,"[\tJSPRUN_CODE_"+codecount+"\t]");  
	       codecount++;
	     }  
	     m.appendTail(b);  
	     return b.toString();  
	 }
	public List<String> getCodelist() {
		return codelist;
	}
	public void setCodelist() {
		if(codelist==null){
			codelist = new ArrayList<String>();
		}
	}
	public int getCodecount() {
		return codecount;
	}
}
