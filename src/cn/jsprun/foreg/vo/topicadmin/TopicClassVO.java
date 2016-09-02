package cn.jsprun.foreg.vo.topicadmin;
import java.util.HashMap;
import java.util.Map;
public class TopicClassVO extends BaseVO {
	private Map<Integer,String> topicClassMap = new HashMap<Integer, String>();
	public Map<Integer, String> getTopicClassMap() {
		return topicClassMap;
	}
	public void setTopicClassMap(Map<Integer, String> topicClassMap) {
		this.topicClassMap = topicClassMap;
	}
}
