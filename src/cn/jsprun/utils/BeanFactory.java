package cn.jsprun.utils;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public final class BeanFactory {
	private static Map<String,Object> map = null;
	static {
		load();
	}
	public static Object getBean(String beanName) {
		return map.get(beanName);
	}
	public static void load() {
		map = new HashMap<String,Object>();
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("beanfactory.xml"));
			NodeList daos = doc.getElementsByTagName("bean");
			Element e=null;
			int length=daos.getLength();
			for (int i = 0; i < length; i++) {
				e = (Element) daos.item(i);
				map.put(e.getAttribute("name"), Class.forName(e.getAttribute("class")).newInstance());
			}
			e=null;
			daos=null;
			doc=null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
