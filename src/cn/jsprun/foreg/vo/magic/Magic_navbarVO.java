package cn.jsprun.foreg.vo.magic;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class Magic_navbarVO {
	private boolean openmarket;
	private String magicWeigthNow;
	private String allowMagicWeigth;
	private String scoring;
	private String agio;
	private List<OtherScoring> otherScoringList = new ArrayList<OtherScoring>();
	public static class OtherScoring{
		private String name;
		private String value;
		private String nuit;
		private boolean business; 
		private OtherScoring(){
				}
		private OtherScoring(String name,String value,String nuit,boolean business){
			this.name = name;
			this.value = value;
			this.nuit = nuit;
			this.business = business;
		}
		public boolean isBusiness() {
			return business;
		}
		public void setBusiness(boolean business) {
			this.business = business;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getNuit() {
			return nuit;
		}
		public void setNuit(String nuit) {
			this.nuit = nuit;
		}
	}
	public OtherScoring getOtherScoring(){
		return new OtherScoring();
	}
	public OtherScoring getOtherScoring(String name,String value,String nuit,boolean business){
		return new OtherScoring(name,value,nuit,business);
	}
	public List<OtherScoring> getOtherScoringList(){
		return otherScoringList;
	}
	public void setOtherScoringList(List<Map<String,Object>> otherScoringLM) {
		for(Map<String,Object> tempMap : otherScoringLM){
			String name = (String)tempMap.get("title");
			String value = ((Integer)tempMap.get("value")).toString();
			String nuit = (String)tempMap.get("nuit");
			boolean business = (Boolean)tempMap.get("creditstrans");
			otherScoringList.add(new OtherScoring(name,value,nuit,business));
		}
	}
	public String getAllowMagicWeigth() {
		return allowMagicWeigth;
	}
	public void setAllowMagicWeigth(String allowMagicWeigth) {
		this.allowMagicWeigth = allowMagicWeigth;
	}
	public String getMagicWeigthNow() {
		return magicWeigthNow;
	}
	public void setMagicWeigthNow(String magicWeigthNow) {
		this.magicWeigthNow = magicWeigthNow;
	}
	public String getScoring() {
		return scoring;
	}
	public void setScoring(String scoring) {
		this.scoring = scoring;
	}
	public String getAgio() {
		return agio;
	}
	public void setAgio(String agio) {
		this.agio = agio;
	}
	public boolean isOpenmarket() {
		return openmarket;
	}
	public void setOpenmarket(boolean openmarket) {
		this.openmarket = openmarket;
	}
}
