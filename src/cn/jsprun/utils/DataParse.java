package cn.jsprun.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public final class DataParse {
	@SuppressWarnings("unchecked")
	public Map characterParse(String targetString,boolean isTreeMap) {
		if (targetString == null||targetString.equals("")) {
			return new HashMap();
		}
		Map resultMap = null;
		try {
			resultMap=(Map)Serializer.unserialize(targetString,JspRunConfig.CHARSET,isTreeMap?1:2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	@SuppressWarnings("unchecked")
	public List characterParse(String targetString) {
		if (targetString == null||targetString.equals("")) {
			return new ArrayList();
		}
		List result = null;
		try {
			result=(List)Serializer.unserialize(targetString,JspRunConfig.CHARSET,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public String combinationChar(Object obj) {
		return Serializer.serialize(obj,JspRunConfig.CHARSET);
	}
}