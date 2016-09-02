package cn.jsprun.foreg.service;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import cn.jsprun.domain.Forumfields;
import cn.jsprun.domain.Members;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.DataParse;
public class PostOperating {
	public final String reply = "reply"; 
	public final String posts = "posts"; 
	public final String stick = "stick"; 
	public final String attachment = "attachment";
	@SuppressWarnings("unchecked")
	public void setMembersExtcredit(Forumfields forumfields,String creditspolicy,Members members,Map<String,String> updateField,String operating,boolean add,Integer multiple){
		Class[] classArray = {Forumfields.class,String.class,DataParse.class};
		Object[] objectArray = {forumfields,creditspolicy,((DataParse)BeanFactory.getBean("dataParse"))};
		Method method = null;
		try {
			method = PostOperating.class.getDeclaredMethod("getCredits_"+operating, classArray);
			classArray=null;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		method.setAccessible(true);
		Map<Integer, Integer> resultMap = null;
		try {
			resultMap = (Map<Integer,Integer>)method.invoke(this, objectArray);
			objectArray=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(resultMap!=null){
			Iterator<Entry<Integer,Integer>> iterator = resultMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Integer,Integer> e = iterator.next();
				Integer key = e.getKey();
				int value = (multiple==null?1:multiple)*e.getValue();
				setMembersExtcredit(key+"", members, value, add);
				if(updateField!=null){
					updateField.put("extcredits" + key, "extcredits" + key);
				}
			}
		}
	}
	public void setMembersExtcredit(String extcredit,Members members,int value,boolean add){
		try {
			Method method_get = Members.class.getMethod("getExtcredits" + extcredit);
			int extcredits = (Integer) method_get.invoke(members);
			int temp = 0;
			if(add){
				temp = extcredits + value;
			}else{
				temp = extcredits - value;
			}
			Method method_set = Members.class.getMethod("setExtcredits" + extcredit, Integer.class);
			method_set.invoke(members, temp);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	private Map<Integer,Integer> getCredits_reply(Forumfields forumfields,String creditspolicy,DataParse dataParse){
		String replycredits = forumfields.getReplycredits();
		Map<Integer,Integer> resultMap = null;
		if(replycredits!=null&&!replycredits.equals("")){
			resultMap = dataParse.characterParse(replycredits, false);
		}else{
			resultMap = (Map<Integer,Integer>)dataParse.characterParse(creditspolicy, false).get("reply");
		}
		return resultMap;
	}
	private Map<Integer,Integer> getCredits_stick(Forumfields forumfields,String creditspolicy,DataParse dataParse){
		String digestcredits = forumfields.getDigestcredits();
		Map<Integer,Integer> resultMap = null;
		if(digestcredits!=null&&!digestcredits.equals("")){
			resultMap = dataParse.characterParse(digestcredits, false);
		}else{
			resultMap = (Map<Integer,Integer>)dataParse.characterParse(creditspolicy, false).get("digest");
		}
		return resultMap;
	}
	private Map<Integer,Integer> getCredits_attachment(Forumfields forumfields,String creditspolicy,DataParse dataParse){
		String postattachcredits = forumfields.getPostattachcredits();
		Map<Integer,Integer> resultMap = null;
		if(postattachcredits!=null&&!postattachcredits.equals("")){
			resultMap = dataParse.characterParse(postattachcredits, false);
		}else{
			resultMap = (Map<Integer,Integer>)dataParse.characterParse(creditspolicy, false).get("postattach");
		}
		return resultMap;
	}
	private Map<Integer,Integer> getCredits_posts(Forumfields forumfields,String creditspolicy,DataParse dataParse) {
		String postcredits = forumfields.getPostcredits();
		Map<Integer,Integer> resultMap = null;
		if(postcredits!=null&&!postcredits.equals("")){
			resultMap = dataParse.characterParse(postcredits, false);
		}else{
			resultMap = (Map<Integer,Integer>)dataParse.characterParse(creditspolicy, false).get("post");
		}
		return resultMap;
	}
	public void setCredits(String creditsformula,Members members,Map<String,String> updateField){
		StringBuffer operationString = new StringBuffer(creditsformula.replaceAll("\\s", ""));
		boolean sign = updateField==null;
		while (true) {
			if (operationString.indexOf("digestposts") >= 0) {
				operationString.replace(operationString.indexOf("digestposts"),operationString.indexOf("digestposts")+ "digestposts".length(), members.getDigestposts().toString());
				if(!sign&&updateField.get("digestposts")!=null){
					sign = true;
				}
			} else if (operationString.indexOf("posts") >= 0) {
				operationString.replace(operationString.indexOf("posts"),operationString.indexOf("posts") + "posts".length(),members.getPosts().toString());
				if(!sign&&updateField.get("posts")!=null){
					sign = true;
				}
			} else if (operationString.indexOf("oltime") >= 0) {
				operationString.replace(operationString.indexOf("oltime"),operationString.indexOf("oltime") + "oltime".length(),members.getOltime().toString());
				if(!sign&&updateField.get("oltime")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("pageviews")>=0){
				operationString.replace(operationString.indexOf("pageviews"), operationString.indexOf("pageviews")+"pageviews".length(), members.getPageviews().toString());
				if(!sign&&updateField.get("pageviews")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits1")>=0){
				operationString.replace(operationString.indexOf("extcredits1"), operationString.indexOf("extcredits1")+"extcredits1".length(), members.getExtcredits1().toString());
				if(!sign&&updateField.get("extcredits1")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits2")>=0){
				operationString.replace(operationString.indexOf("extcredits2"), operationString.indexOf("extcredits2")+"extcredits2".length(),members.getExtcredits2().toString());
				if(!sign&&updateField.get("extcredits2")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits3")>=0){
				operationString.replace(operationString.indexOf("extcredits3"), operationString.indexOf("extcredits3")+"extcredits3".length(), members.getExtcredits3().toString());
				if(!sign&&updateField.get("extcredits3")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits4")>=0){
				operationString.replace(operationString.indexOf("extcredits4"), operationString.indexOf("extcredits4")+"extcredits4".length(), members.getExtcredits4().toString());
				if(!sign&&updateField.get("extcredits4")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits5")>=0){
				operationString.replace(operationString.indexOf("extcredits5"), operationString.indexOf("extcredits5")+"extcredits5".length(),members.getExtcredits5().toString());
				if(!sign&&updateField.get("extcredits5")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits6")>=0){
				operationString.replace(operationString.indexOf("extcredits6"), operationString.indexOf("extcredits6")+"extcredits6".length(), members.getExtcredits6().toString());
				if(!sign&&updateField.get("extcredits6")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits7")>=0){
				operationString.replace(operationString.indexOf("extcredits7"), operationString.indexOf("extcredits7")+"extcredits7".length(), members.getExtcredits7().toString());
				if(!sign&&updateField.get("extcredits7")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits8")>=0){
				operationString.replace(operationString.indexOf("extcredits8"), operationString.indexOf("extcredits8")+"extcredits8".length(), members.getExtcredits8().toString());
				if(!sign&&updateField.get("extcredits8")!=null){
					sign = true;
				}
			}else{
				break;
			}
		}
		if(sign){
			String result = excute(operationString);
			Integer credits = Double.valueOf(result).intValue();
			members.setCredits(credits);
		}
	}
	public int getTotalCredits(String creditsformula,Map<String,String> creditMap){
		StringBuffer operationString = new StringBuffer(creditsformula.replaceAll("\\s", ""));
		String tempS;
		while (true) {
			if (operationString.indexOf("digestposts") >= 0) {
				tempS = creditMap.get("digestposts");
				tempS = tempS == null ? "0" : tempS;
				operationString.replace(operationString.indexOf("digestposts"),operationString.indexOf("digestposts")+ "digestposts".length(), tempS);
			} else if (operationString.indexOf("posts") >= 0) {
				tempS = creditMap.get("posts");
				tempS = tempS == null ? "0" : tempS;
				operationString.replace(operationString.indexOf("posts"),operationString.indexOf("posts") + "posts".length(), tempS);
			} else if (operationString.indexOf("oltime") >= 0) {
				tempS = creditMap.get("oltime");
				tempS = tempS == null ? "0" : tempS;
				operationString.replace(operationString.indexOf("oltime"),operationString.indexOf("oltime") + "oltime".length(), tempS);
			}else if(operationString.indexOf("pageviews")>=0){
				tempS = creditMap.get("pageviews");
				tempS = tempS == null ? "0" : tempS;
				operationString.replace(operationString.indexOf("pageviews"), operationString.indexOf("pageviews")+"pageviews".length(), tempS);
			}else if(operationString.indexOf("extcredits1")>=0){
				tempS = creditMap.get("extcredits1");
				tempS = tempS == null ? "0" : tempS;
				operationString.replace(operationString.indexOf("extcredits1"), operationString.indexOf("extcredits1")+"extcredits1".length(), tempS);
			}else if(operationString.indexOf("extcredits2")>=0){
				tempS = creditMap.get("extcredits2");
				tempS = tempS == null ? "0" : tempS;
				operationString.replace(operationString.indexOf("extcredits2"), operationString.indexOf("extcredits2")+"extcredits2".length(), tempS);
			}else if(operationString.indexOf("extcredits3")>=0){
				tempS = creditMap.get("extcredits3");
				tempS = tempS == null ? "0" : tempS;
				operationString.replace(operationString.indexOf("extcredits3"), operationString.indexOf("extcredits3")+"extcredits3".length(), tempS);
			}else if(operationString.indexOf("extcredits4")>=0){
				tempS = creditMap.get("extcredits4");
				tempS = tempS == null ? "0" : tempS;
				operationString.replace(operationString.indexOf("extcredits4"), operationString.indexOf("extcredits4")+"extcredits4".length(), tempS);
			}else if(operationString.indexOf("extcredits5")>=0){
				tempS = creditMap.get("extcredits5");
				tempS = tempS == null ? "0" : tempS;
				operationString.replace(operationString.indexOf("extcredits5"), operationString.indexOf("extcredits5")+"extcredits5".length(), tempS);
			}else if(operationString.indexOf("extcredits6")>=0){
				tempS = creditMap.get("extcredits6");
				tempS = tempS == null ? "0" : tempS;
				operationString.replace(operationString.indexOf("extcredits6"), operationString.indexOf("extcredits6")+"extcredits6".length(), tempS);
			}else if(operationString.indexOf("extcredits7")>=0){
				tempS = creditMap.get("extcredits7");
				tempS = tempS == null ? "0" : tempS;
				operationString.replace(operationString.indexOf("extcredits7"), operationString.indexOf("extcredits7")+"extcredits7".length(), tempS);
			}else if(operationString.indexOf("extcredits8")>=0){
				tempS = creditMap.get("extcredits8");
				tempS = tempS == null ? "0" : tempS;
				operationString.replace(operationString.indexOf("extcredits8"), operationString.indexOf("extcredits8")+"extcredits8".length(), tempS);
			}else{
				break;
			}
		}
		return Double.valueOf(excute(operationString)).intValue();
	}
	private String excute(StringBuffer expiression){
		String tempS;
		if(expiression==null||(tempS = expiression.toString()).equals("") || tempS.equals("0")){
			return "0";
		}
		int indexF = expiression.lastIndexOf("(");
		if(indexF<0){
			return count(expiression.toString());
		}else{
			int indexA = expiression.indexOf(")", indexF); 
			return excute(expiression.replace(indexF, indexA+1, count(expiression.substring(indexF+1, indexA)))); 
		}
	}
	private String count(String expiression){
		StringBuffer tempBuffer = null;
		int indexM = expiression.indexOf("*");
		if(indexM<0){ 
			int indexD = expiression.indexOf("/");
			if(indexD<0){ 
				int indexA = expiression.indexOf("+");
				if(indexA<0){
					int indexS = expiression.indexOf("-");
					if(indexS>0){
						tempBuffer = getResult(indexS, expiression, "-");
					}else if(indexS==0){
						StringBuffer buffer = new StringBuffer(expiression);
						indexS = buffer.indexOf("-", 1);
						if(indexS>1){
							tempBuffer = getResult(indexS, expiression, "-");
						}else if(indexS==1){ 
							tempBuffer = new StringBuffer(buffer.replace(0, 2, "").toString());
						}else { 
							tempBuffer = buffer;
						}
					}else{
						return expiression;
					}
				}else{
					tempBuffer = getResult(indexA, expiression, "+");
				}
			}else{
				tempBuffer = getResult(indexD, expiression, "/");
			}
		}else{
			tempBuffer = getResult(indexM, expiression, "*");
		}
		if(tempBuffer.indexOf("*")>0
				||tempBuffer.indexOf("/")>0
				||tempBuffer.indexOf("+")>0
				||tempBuffer.indexOf("-")>0
				||(tempBuffer.indexOf("-")==0
						&&tempBuffer.indexOf("-",1)>0)){
			return count(tempBuffer.toString());
		}else{
			return tempBuffer.toString();
		}
	}
	private StringBuffer getResult(int index,String expiression,String sign){
		StringBuffer tempBuffer = new StringBuffer(expiression);
		String leftNumber = getLeftNumber(index, expiression);
		String rightNumber = getRightNumber(index, expiression);
		double result = 0;
		if(sign.equals("*")){
			result = Double.parseDouble(leftNumber)*Double.parseDouble(rightNumber);
		}else if(sign.equals("/")){
			double rightNumber_ = Double.parseDouble(rightNumber);
			if(rightNumber_ != 0){
				result = Double.parseDouble(leftNumber)/rightNumber_;
			}
		}else if(sign.equals("+")){
			result = Double.parseDouble(leftNumber)+Double.parseDouble(rightNumber);
		}else if(sign.equals("-")){
			result = Double.parseDouble(leftNumber)-Double.parseDouble(rightNumber);
		}
		int firstIndexOfLeftNumber = index-leftNumber.length();
		int lastIndexOfRightNumber = index+rightNumber.length();
		tempBuffer.replace(firstIndexOfLeftNumber, lastIndexOfRightNumber+1, String.valueOf(result));
		return tempBuffer;
	}
	private String getLeftNumber(int singIndex,String expiression){
		StringBuffer tempBuffer = new StringBuffer();
		for(int i = singIndex-1;i>-1;i--){
			char temp = expiression.charAt(i);
			if(Character.isDigit(temp)||temp=='.'){
				tempBuffer.insert(0, temp);
			}else {
				if(temp=='-'&&(i==0||!Character.isDigit(expiression.charAt(i-1)))){
					tempBuffer.insert(0, temp);
				}else{
					break;
				}
			}
		}
		return tempBuffer.toString();
	}
	private String getRightNumber(int singIndex,String expiression){
		StringBuffer tempBuffer = new StringBuffer();
		for(int i = singIndex+1;i<expiression.length();i++){
			char temp = expiression.charAt(i);
			if(i==singIndex+1&&temp=='-'){
				tempBuffer.append(temp);
				continue;
			}
			if(Character.isDigit(temp)||temp=='.'){
				tempBuffer.append(temp);
			}else {
				break;
			}
		}
		return tempBuffer.toString();
	}
	public String  setCreditsbyMap(String creditsformula,Map<String,String>usermap,Map<String,String> updateField){
		StringBuffer operationString = new StringBuffer(creditsformula.replaceAll("\\s", ""));
		boolean sign = updateField==null;
		while (true) {
			if (operationString.indexOf("digestposts") >= 0) {
				operationString.replace(operationString.indexOf("digestposts"),operationString.indexOf("digestposts")+ "digestposts".length(), usermap.get("digestposts")==null?"0":usermap.get("digestposts"));
				if(!sign&&updateField.get("digestposts")!=null){
					sign = true;
				}
			} else if (operationString.indexOf("posts") >= 0) {
				operationString.replace(operationString.indexOf("posts"),operationString.indexOf("posts") + "posts".length(),usermap.get("posts")==null?"0":usermap.get("posts"));
				if(!sign&&updateField.get("posts")!=null){
					sign = true;
				}
			} else if (operationString.indexOf("oltime") >= 0) {
				operationString.replace(operationString.indexOf("oltime"), operationString.indexOf("oltime") + "oltime".length(),usermap.get("oltime")==null?"0":usermap.get("oltime"));
				if(!sign&&updateField.get("oltime")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("pageviews")>=0){
				operationString.replace(
						operationString.indexOf("pageviews"), 
						operationString.indexOf("pageviews")+"pageviews".length(), usermap.get("pageviews")==null?"0":usermap.get("pageviews"));
				if(!sign&&updateField.get("pageviews")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits1")>=0){
				operationString.replace(
						operationString.indexOf("extcredits1"), 
						operationString.indexOf("extcredits1")+"extcredits1".length(), usermap.get("extcredits1")==null?"0":usermap.get("extcredits1"));
				if(!sign&&updateField.get("extcredits1")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits2")>=0){
				operationString.replace(
						operationString.indexOf("extcredits2"), 
						operationString.indexOf("extcredits2")+"extcredits2".length(), 
						usermap.get("extcredits2")==null?"0":usermap.get("extcredits2"));
				if(!sign&&updateField.get("extcredits2")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits3")>=0){
				operationString.replace(
						operationString.indexOf("extcredits3"), 
						operationString.indexOf("extcredits3")+"extcredits3".length(), 
						usermap.get("extcredits3")==null?"0":usermap.get("extcredits3"));
				if(!sign&&updateField.get("extcredits3")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits4")>=0){
				operationString.replace(
						operationString.indexOf("extcredits4"), 
						operationString.indexOf("extcredits4")+"extcredits4".length(), 
						usermap.get("extcredits4")==null?"0":usermap.get("extcredits4"));
				if(!sign&&updateField.get("extcredits4")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits5")>=0){
				operationString.replace(
						operationString.indexOf("extcredits5"), 
						operationString.indexOf("extcredits5")+"extcredits5".length(), 
						usermap.get("extcredits5")==null?"0":usermap.get("extcredits5"));
				if(!sign&&updateField.get("extcredits5")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits6")>=0){
				operationString.replace(
						operationString.indexOf("extcredits6"), 
						operationString.indexOf("extcredits6")+"extcredits6".length(), 
						usermap.get("extcredits6")==null?"0":usermap.get("extcredits6"));
				if(!sign&&updateField.get("extcredits6")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits7")>=0){
				operationString.replace(
						operationString.indexOf("extcredits7"), 
						operationString.indexOf("extcredits7")+"extcredits7".length(), 
						usermap.get("extcredits7")==null?"0":usermap.get("extcredits7"));
				if(!sign&&updateField.get("extcredits7")!=null){
					sign = true;
				}
			}else if(operationString.indexOf("extcredits8")>=0){
				operationString.replace(
						operationString.indexOf("extcredits8"), 
						operationString.indexOf("extcredits8")+"extcredits8".length(), 
						usermap.get("extcredits8")==null?"0":usermap.get("extcredits8"));
				if(!sign&&updateField.get("extcredits8")!=null){
					sign = true;
				}
			}else{
				break;
			}
		}
		if(sign){
			String result = excute(operationString);
			try{
				Integer credits = Double.valueOf(result).intValue();
				return credits+"";
			}catch(Exception e){
				return null;
			}
		}
		return null;
	}
}
