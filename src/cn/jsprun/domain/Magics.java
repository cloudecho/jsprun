package cn.jsprun.domain;
public class Magics  implements java.io.Serializable {
	private static final long serialVersionUID = 8421526577436693669L;
	private Short magicid;			
     private Byte available;		
     private Short type;			
     private String name;			
     private String identifier;		
     private String description;	
     private Byte displayorder;	
     private Integer price;			
     private Integer num;				
     private Short salevolume;		
     private Byte supplytype;		
     private Integer supplynum;		
     private Short weight;			
     private String filename;		
     private String magicperm;		
    public Magics() {
    }
    public Magics(Byte available, Short type, String name, String identifier, String description, Byte displayorder, Integer price, Integer num, Short salevolume, Byte supplytype, Integer supplynum, Short weight, String filename, String magicperm) {
        this.available = available;
        this.type = type;
        this.name = name;
        this.identifier = identifier;
        this.description = description;
        this.displayorder = displayorder;
        this.price = price;
        this.num = num;
        this.salevolume = salevolume;
        this.supplytype = supplytype;
        this.supplynum = supplynum;
        this.weight = weight;
        this.filename = filename;
        this.magicperm = magicperm;
    }
    public Short getMagicid() {
        return this.magicid;
    }
    public void setMagicid(Short magicid) {
        this.magicid = magicid;
    }
    public Byte getAvailable() {
        return this.available;
    }
    public void setAvailable(Byte available) {
        this.available = available;
    }
    public Short getType() {
        return this.type;
    }
    public void setType(Short type) {
        this.type = type;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Byte getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Byte displayorder) {
        this.displayorder = displayorder;
    }
    public Integer getPrice() {
        return this.price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getNum() {
        return this.num;
    }
    public void setNum(Integer num) {
        this.num = num;
    }
    public Short getSalevolume() {
        return this.salevolume;
    }
    public void setSalevolume(Short salevolume) {
        this.salevolume = salevolume;
    }
    public Byte getSupplytype() {
        return this.supplytype;
    }
    public void setSupplytype(Byte supplytype) {
        this.supplytype = supplytype;
    }
    public Integer getSupplynum() {
        return this.supplynum;
    }
    public void setSupplynum(Integer supplynum) {
        this.supplynum = supplynum;
    }
    public Short getWeight() {
        return this.weight;
    }
    public void setWeight(Short weight) {
        this.weight = weight;
    }
    public String getFilename() {
        return this.filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getMagicperm() {
        return this.magicperm;
    }
    public void setMagicperm(String magicperm) {
        this.magicperm = magicperm;
    }
}