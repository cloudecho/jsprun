package cn.jsprun.foreg.vo.statistic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class Stats_creditComposidorVO {
	private Stats_navbarVO navbar = new Stats_navbarVO();
	private List<String> creditNameTopList = new ArrayList<String>();
	private List<LineObject> lineObjectTopList = new ArrayList<LineObject>();
	private List<String> creditNameDownList = new ArrayList<String>();
	private List<Map<String,Map<String,String>>> downMapList = new ArrayList<Map<String,Map<String,String>>>();
	public static class LineObject{
		private List<CreditInfo> creditInfoList = new ArrayList<CreditInfo>();
		public static class CreditInfo{
			private String username;
			private String creditNum;
			public String getCreditNum() {
				return creditNum;
			}
			public void setCreditNum(String creditNum) {
				this.creditNum = creditNum;
			}
			public String getUsername() {
				return username;
			}
			public void setUsername(String username) {
				this.username = username;
			}
		}
		public List<CreditInfo> getCreditInfoList() {
			return creditInfoList;
		}
		public void setCreditInfoList(List<CreditInfo> creditInfoList) {
			this.creditInfoList = creditInfoList;
		}
	}
	public List<String> getCreditNameTopList() {
		return creditNameTopList;
	}
	public void setCreditNameTopList(List<String> creditNameTopList) {
		this.creditNameTopList = creditNameTopList;
	}
	public List<LineObject> getLineObjectTopList() {
		return lineObjectTopList;
	}
	public void setLineObjectTopList(List<LineObject> lineObjectTopList) {
		this.lineObjectTopList = lineObjectTopList;
	}
	public List<String> getCreditNameDownList() {
		return creditNameDownList;
	}
	public void setCreditNameDownList(List<String> creditNameDownList) {
		this.creditNameDownList = creditNameDownList;
	}
	public List<Map<String, Map<String, String>>> getDownMapList() {
		return downMapList;
	}
	public void setDownMapList(List<Map<String, Map<String, String>>> downMapList) {
		this.downMapList = downMapList;
	}
	public Stats_navbarVO getNavbar() {
		return navbar;
	}
}
