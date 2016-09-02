package cn.jsprun.domain;
public class Crons implements java.io.Serializable {
	private static final long serialVersionUID = 5831989246046928273L;
	private Short cronid; 
	private Byte available; 
	private String type; 
	private String name; 
	private String filename; 
	private Integer lastrun; 
	private Integer nextrun; 
	private Byte weekday; 
	private Short day; 
	private Short hour; 
	private String minute; 
	public Crons() {
	}
	public Crons(Byte available, String type, String name, String filename,
			Integer lastrun, Integer nextrun, Byte weekday, Short day,
			Short hour, String minute) {
		this.available = available;
		this.type = type;
		this.name = name;
		this.filename = filename;
		this.lastrun = lastrun;
		this.nextrun = nextrun;
		this.weekday = weekday;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	public Short getCronid() {
		return this.cronid;
	}
	public void setCronid(Short cronid) {
		this.cronid = cronid;
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
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFilename() {
		return this.filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer getLastrun() {
		return this.lastrun;
	}
	public void setLastrun(Integer lastrun) {
		this.lastrun = lastrun;
	}
	public Integer getNextrun() {
		return this.nextrun;
	}
	public void setNextrun(Integer nextrun) {
		this.nextrun = nextrun;
	}
	public Byte getWeekday() {
		return this.weekday;
	}
	public void setWeekday(Byte weekday) {
		this.weekday = weekday;
	}
	public Short getDay() {
		return this.day;
	}
	public void setDay(Short day) {
		this.day = day;
	}
	public Short getHour() {
		return this.hour;
	}
	public void setHour(Short hour) {
		this.hour = hour;
	}
	public String getMinute() {
		return this.minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
}