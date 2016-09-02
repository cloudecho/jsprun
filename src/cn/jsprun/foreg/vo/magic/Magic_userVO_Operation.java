package cn.jsprun.foreg.vo.magic;
import java.util.ArrayList;
import java.util.List;
public class Magic_userVO_Operation  extends Magic_navbarVO{
	private String operation;
	private short magicId;
	private String imageName;
	private String magicName;
	private String magicExplaining;
	private short magicCount;
	private int allMagicWeight;
	private String usable;
	private String magicType; 
	private List<Magic_userVO_Operation.Module> moduleList = new ArrayList<Magic_userVO_Operation.Module>(); 
	private List<String> usergroupNameList = new ArrayList<String>(); 
	private String operationInfo1;
	private String operationInfo2;
	private boolean showOperationInfo2; 
	private boolean isChangeColor;
	private List<String> colorList = new ArrayList<String>();
	private String selectContent = null;
	private boolean displayText;
	private String textName;
	private String textState;
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
	public int getModuleListSize(){
		return moduleList.size();
	}
	public int getUsergroupNameListSize(){
		return moduleList.size();
	}
	public int getAllMagicWeight() {
		return allMagicWeight;
	}
	public void setAllMagicWeight(int allMagicWeight) {
		this.allMagicWeight = allMagicWeight;
	}
	public List<String> getColorList() {
		return colorList;
	}
	public void setColorList(List<String> colorList) {
		this.colorList = colorList;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public boolean getIsChangeColor() {
		return isChangeColor;
	}
	public void setIsChangeColor(boolean isChangeColor) {
		this.isChangeColor = isChangeColor;
	}
	public short getMagicCount() {
		return magicCount;
	}
	public void setMagicCount(short magicCount) {
		this.magicCount = magicCount;
	}
	public String getMagicExplaining() {
		return magicExplaining;
	}
	public void setMagicExplaining(String magicExplaining) {
		this.magicExplaining = magicExplaining;
	}
	public short getMagicId() {
		return magicId;
	}
	public void setMagicId(short magicId) {
		this.magicId = magicId;
	}
	public String getMagicName() {
		return magicName;
	}
	public void setMagicName(String magicName) {
		this.magicName = magicName;
	}
	public String getMagicType() {
		return magicType;
	}
	public void setMagicType(String magicType) {
		this.magicType = magicType;
	}
	public List<Magic_userVO_Operation.Module> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<Magic_userVO_Operation.Module> moduleList) {
		this.moduleList = moduleList;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOperationInfo1() {
		return operationInfo1;
	}
	public void setOperationInfo1(String operationInfo1) {
		this.operationInfo1 = operationInfo1;
	}
	public String getOperationInfo2() {
		return operationInfo2;
	}
	public void setOperationInfo2(String operationInfo2) {
		this.operationInfo2 = operationInfo2;
	}
	public boolean isShowOperationInfo2() {
		return showOperationInfo2;
	}
	public void setShowOperationInfo2(boolean showOperationInfo2) {
		this.showOperationInfo2 = showOperationInfo2;
	}
	public String getUsable() {
		return usable;
	}
	public void setUsable(String usable) {
		this.usable = usable;
	}
	public List<String> getUsergroupNameList() {
		return usergroupNameList;
	}
	public void setUsergroupNameList(List<String> usergroupNameList) {
		this.usergroupNameList = usergroupNameList;
	}
	public Module getModule(){
		return new Module();
	}
	public boolean isDisplayText() {
		return displayText;
	}
	public void setDisplayText(boolean displayText) {
		this.displayText = displayText;
	}
	public String getTextName() {
		return textName;
	}
	public void setTextName(String textName) {
		this.textName = textName;
	}
	public String getTextState() {
		return textState;
	}
	public void setTextState(String textState) {
		this.textState = textState;
	}
	public String getSelectContent() {
		return selectContent;
	}
	public void setSelectContent(String selectContent) {
		this.selectContent = selectContent;
	}
}
