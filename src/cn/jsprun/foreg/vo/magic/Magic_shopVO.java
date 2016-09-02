package cn.jsprun.foreg.vo.magic;
import java.util.ArrayList;
import java.util.List;
public class Magic_shopVO {
	private int id;
	private String magicName;
	private String magicInfo;
	private String price;
	private String weight;
	private String stock;
	private String numOfSale;
	private String imageName;
	private boolean isUsable; 
	private String type; 
	private List<Magic_shopVO.Module> moduleList = new ArrayList<Magic_shopVO.Module>(); 
	private List<String> usergroupNameList = new ArrayList<String>(); 
	private String extcredits; 
	public Magic_shopVO() {
	}
	public static class Module{
		private int id;
		private String name;
		private Module() {
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	public List<Magic_shopVO.Module> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<Magic_shopVO.Module> moduleList) {
		this.moduleList = moduleList;
	}
	public void setModuleList(int moduleId,String moduleName){
		Module module = new Module();
		module.setId(moduleId);
		module.setName(moduleName);
		moduleList.add(module);
	}
	public int getModuleListSize(){
		return moduleList.size();
	}
	public String getExtcredits() {
		return extcredits;
	}
	public void setExtcredits(String extcredits) {
		this.extcredits = extcredits;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public boolean getIsUsable() {
		return isUsable;
	}
	public void setUsable(boolean isUsable) {
		this.isUsable = isUsable;
	}
	public String getMagicInfo() {
		return magicInfo;
	}
	public void setMagicInfo(String magicInfo) {
		this.magicInfo = magicInfo;
	}
	public String getMagicName() {
		return magicName;
	}
	public void setMagicName(String magicName) {
		this.magicName = magicName;
	}
	public String getNumOfSale() {
		return numOfSale;
	}
	public void setNumOfSale(String numOfSale) {
		this.numOfSale = numOfSale;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getUsergroupNameList() {
		return usergroupNameList;
	}
	public void setUsergroupNameList(List<String> usergroupNameList) {
		this.usergroupNameList = usergroupNameList;
	}
	public int getUsergroupNameListSize(){
		return usergroupNameList.size();
	}
}
