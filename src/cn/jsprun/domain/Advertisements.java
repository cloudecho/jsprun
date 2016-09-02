package cn.jsprun.domain;
public class Advertisements  implements java.io.Serializable {
	private static final long serialVersionUID = 4467294785358808233L;
	private Integer advid;			
     private Byte available;		
     private String type;			
     private Short displayorder;	
     private String title;			
     private String targets;		
     private String parameters;		
     private String code;			
     private Integer starttime;		
     private Integer endtime;		
    public Advertisements() {
    }
    public Advertisements(Byte available, String type, Short displayorder, String title, String targets, String parameters, String code, Integer starttime, Integer endtime) {
        this.available = available;
        this.type = type;
        this.displayorder = displayorder;
        this.title = title;
        this.targets = targets;
        this.parameters = parameters;
        this.code = code;
        this.starttime = starttime;
        this.endtime = endtime;
    }
    public Integer getAdvid() {
        return this.advid;
    }
    public void setAdvid(Integer advid) {
        this.advid = advid;
    }
    public Byte getAvailable() {
        return this.available;
    }
    public void setAvailable(Byte available) {
        this.available = available;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Short getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Short displayorder) {
        this.displayorder = displayorder;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTargets() {
        return this.targets;
    }
    public void setTargets(String targets) {
        this.targets = targets;
    }
    public String getParameters() {
        return this.parameters;
    }
    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Integer getStarttime() {
        return this.starttime;
    }
    public void setStarttime(Integer starttime) {
        this.starttime = starttime;
    }
    public Integer getEndtime() {
        return this.endtime;
    }
    public void setEndtime(Integer endtime) {
        this.endtime = endtime;
    }
}