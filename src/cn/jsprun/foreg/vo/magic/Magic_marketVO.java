package cn.jsprun.foreg.vo.magic;
import java.util.ArrayList;
import java.util.List;
public class Magic_marketVO  extends Magic_navbarVO{
	private List<MagicInfo> magicInfoList = new ArrayList<MagicInfo>();
	private List<MagicOfDB> magicOfDBList = new ArrayList<MagicOfDB>();
	private Short selectMagicId;
	private String selectOrderby;
	private String selectAscdesc;
	private boolean selectFind;
	private String magicUnit; 
	private String operation;
	public static class MagicInfo{
		private Short magicMarketId;
		private short magicId; 
		private String magicName;	
		private String magicFunction; 
		private Integer magicPrice; 
		private Integer magicNumber; 
		private Integer magicWeight; 
		private Integer sellerId; 
		private String sellerName; 
		private String debusOrBuy; 
		private MagicInfo(){
		}
		public String getDebusOrBuy() {
			return debusOrBuy;
		}
		public void setDebusOrBuy(String debusOrBuy) {
			this.debusOrBuy = debusOrBuy;
		}
		public String getMagicFunction() {
			return magicFunction;
		}
		public void setMagicFunction(String magicFunction) {
			this.magicFunction = magicFunction;
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
		public Integer getMagicNumber() {
			return magicNumber;
		}
		public void setMagicNumber(Integer magicNumber) {
			this.magicNumber = magicNumber;
		}
		public Integer getMagicPrice() {
			return magicPrice;
		}
		public void setMagicPrice(Integer magicPrice) {
			this.magicPrice = magicPrice;
		}
		public Integer getMagicWeight() {
			return magicWeight;
		}
		public void setMagicWeight(Integer magicWeight) {
			this.magicWeight = magicWeight;
		}
		public Integer getSellerId() {
			return sellerId;
		}
		public void setSellerId(Integer sellerId) {
			this.sellerId = sellerId;
		}
		public String getSellerName() {
			return sellerName;
		}
		public void setSellerName(String sellerName) {
			this.sellerName = sellerName;
		}
		public Short getMagicMarketId() {
			return magicMarketId;
		}
		public void setMagicMarketId(Short magicMarketId) {
			this.magicMarketId = magicMarketId;
		}
	}
	public static class MagicOfDB{
		private short magicId; 
		private String magicName;
		private MagicOfDB(){
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
	public MagicInfo getMagicInfo(){
		return new MagicInfo();
	}
	public MagicOfDB getMagicOfDB(){
		return new MagicOfDB();
	}
	public List<MagicInfo> getMagicInfoList() {
		return magicInfoList;
	}
	public Integer getMagicInfoListSize() {
		return magicInfoList.size();
	}
	public List<MagicOfDB> getMagicOfDBList() {
		return magicOfDBList;
	}
	public String getSelectAscdesc() {
		return selectAscdesc;
	}
	public void setSelectAscdesc(String selectAscdesc) {
		this.selectAscdesc = selectAscdesc;
	}
	public Short getSelectMagicId() {
		return selectMagicId;
	}
	public void setSelectMagicId(Short selectMagicId) {
		this.selectMagicId = selectMagicId;
	}
	public String getSelectOrderby() {
		return selectOrderby;
	}
	public void setSelectOrderby(String selectOrderby) {
		this.selectOrderby = selectOrderby;
	}
	public boolean isSelectFind() {
		return selectFind;
	}
	public void setSelectFind(boolean selectFind) {
		this.selectFind = selectFind;
	}
	public String getMagicUnit() {
		return magicUnit;
	}
	public void setMagicUnit(String magicUnit) {
		this.magicUnit = magicUnit;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
}
