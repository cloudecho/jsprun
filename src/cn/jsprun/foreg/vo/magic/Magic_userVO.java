package cn.jsprun.foreg.vo.magic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Magic_userVO  extends Magic_navbarVO implements Serializable {
	private static final long serialVersionUID = 3724355805406877530L;
	private boolean selectSendASell = true;
	private String operation;
	private String current;
	private List<MagicInfo> magicInfoList = new ArrayList<MagicInfo>();
	public Magic_userVO(){}
	public static class MagicInfo{
		private short magicId;
		private String imageName;
		private String magicName;
		private String magicExplaining;
		private short magicCount;
		private int allMagicWeight;
		private MagicInfo() {
		}
		public int getAllMagicWeight() {
			return allMagicWeight;
		}
		public void setAllMagicWeight(int allMagicWeight) {
			this.allMagicWeight = allMagicWeight;
		}
		public String getImageName() {
			return imageName;
		}
		public void setImageName(String imageName) {
			this.imageName = imageName;
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
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public List<MagicInfo> getMagicInfoList() {
		return magicInfoList;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public MagicInfo getMagicInfo(){
		return new MagicInfo();
	}
	public boolean isSelectSendASell() {
		return selectSendASell;
	}
	public void setSelectSendASell(boolean selectSendASell) {
		this.selectSendASell = selectSendASell;
	}
	public boolean isHaveMagic() {
		return magicInfoList.size()>0;
	}
}
