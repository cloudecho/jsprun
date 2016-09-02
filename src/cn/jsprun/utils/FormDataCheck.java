package cn.jsprun.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class FormDataCheck {
	public static boolean isValueString(String str) {
		if (str != null && !"".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean isNum(String num) {
		if (num != null && num.matches("^-?\\d+\\.?\\d*$")) {
			return true;
		}
		return false;
	}
	public static boolean isOneNum(String num) {
		if (num != null && num.matches("\\d+")) {
			return true;
		}
		return false;
	}
	public static boolean isZeroOption(String items[]) {
		if (items == null || items.length == 0) {
			return true;
		}
		return false;
	}
	public static boolean isValueDate(String dateStr) {
		if (dateStr.matches("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$")) {
			return true;
		} else {
			return false;
		}
	}
	public static String getNumberFromForm(String num) {
		Long result = (long) 0;
		if (null == num || num.equals("")) {
			return result.toString();
		} else {
			int numIndex = 0;
			int sign = 0;
			String buffer = new String();
			for (; numIndex < num.length(); numIndex++) {
				String temp = num.substring(0, numIndex + 1);
				if (isOneNum(temp)) {
					buffer = temp;
					sign++;
				} else {
					break;
				}
			}
			if (sign == 0) {
				return result.toString();
			}
			try {
				result = Long.parseLong(buffer);
			} catch (Exception exception) {
				result = Long.MAX_VALUE;
			}
			return result.toString();
		}
	}
	public static String getNumberFromFormOfDisplayorder(String num) {
		Long result = (long) 0;
		String sign = "";
		if (null == num || num.equals("")) {
			return result.toString();
		} else if (num.startsWith("-")) {
			num = num.substring(1);
			sign = "-";
		}
		return sign + getNumberFromForm(num);
	}
	public static String getDoubleString(String num) {
		String sign = "";
		if (null == num || num.equals("")) {
			return "0";
		} else if (num.startsWith("-")) {
			num = num.substring(1);
			sign = "-";
		}
		return sign + turnToDoubleString(num);
	}
	public static String validateDateFormat(String dateString) {
		Pattern pattern = Pattern.compile("^\\d{1,4}\\-\\d{1,2}\\-\\d{1,2}$");
		Matcher matcher = pattern.matcher(dateString);
		if(matcher.matches()){
			return cenvertDateFormat(dateString);
		}else{
			pattern = Pattern.compile("^\\d{4}\\-\\d{1,2}$");
			matcher = pattern.matcher(dateString);
			if(matcher.matches()){
				return cenvertDateFormat(dateString);
			}
		}
		return null;
	}
	private static String cenvertDateFormat(String dateString) {
		String[] temp = dateString.split("-");
		String year = null;
		String month = null;
		String day = null;
		if (temp.length == 3) {
			if (temp[0].length() == 3) {
				year = "2" + temp[0];
			} else if (temp[0].length() == 2) {
				year = "20" + temp[0];
			} else if (temp[0].length() == 1) {
				year = "200" + temp[0];
			} else {
				year = temp[0];
			}
			month = temp[1];
			day = temp[2];
		} else {
			year = temp[0];
			month = temp[1];
			day = "1";
		} 
		String temTime = year + "-" + month + "-" + day;
		SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat("yyyy-MM-dd", ForumInit.settings.get("timeoffset"));
		try {
			return simpleDateFormat.format(simpleDateFormat.parse(temTime));
		} catch (ParseException e) {
			return null;
		}
	}
	public static boolean isLess(String date, String modelDate, SimpleDateFormat dateFormat) {
		Date dateFront = null;
		Date dateAfter = null;
		try {
			dateFront = dateFormat.parse(date);
			dateAfter = dateFormat.parse(modelDate);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
		if (dateFront.getTime() < dateAfter.getTime()) {
			return true;
		} else {
			return false;
		}
	}
	public static String turnToDoubleString(String num){
		if(num==null||num.equals("")){
			return "0";
		}
		String reg = "[-]?[0-9][\\.]?[0-9]?[E]?[1-9][0-9]?[0-8]?";
		if(num.matches(reg)){
			return num;
		}
		char[] numArray = num.toCharArray();
		StringBuffer buffer = new StringBuffer();
		int sign = 0;
		int sign2 = 0;
		for (int i = 0; i < numArray.length; i++) {
			if (numArray[i]=='.') {
				sign++;
			}
			if(numArray[i]=='E'){
				sign2++;
			}
			if(sign<2&&sign2<2&&(isOneNum(String.valueOf(numArray[i]))||numArray[i]=='.'||numArray[i]=='E')){
				buffer.append(numArray[i]);
			}else{
				break;
			}
		}
		numArray=null;
		if(buffer.length()==0||buffer.toString().equals(".")){
			return "0";
		}else{
			if(buffer.toString().endsWith("E")){
				buffer.deleteCharAt(buffer.length()-1);
			}
			return buffer.toString();
		}
	}
}
