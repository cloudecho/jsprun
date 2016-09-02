package cn.jsprun.vo.basic;
import java.io.Serializable;
public class BKSettingVO implements Serializable {
	private static final long serialVersionUID = -73727151417813219L;
	private String fid;
	private String fup;
	private String type;
	private String name;
	private String postcredits;		
	private String replycredits;		
	private String getattachcredits;	
    private String postattachcredits;	
    private String digestcredits; 
	public BKSettingVO() {
	}
	public String getDigestcredits() {
		return digestcredits;
	}
	public void setDigestcredits(String digestcredits) {
		this.digestcredits = digestcredits;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getFup() {
		return fup;
	}
	public void setFup(String fup) {
		this.fup = fup;
	}
	public String getGetattachcredits() {
		return getattachcredits;
	}
	public void setGetattachcredits(String getattachcredits) {
		this.getattachcredits = getattachcredits;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPostattachcredits() {
		return postattachcredits;
	}
	public void setPostattachcredits(String postattachcredits) {
		this.postattachcredits = postattachcredits;
	}
	public String getPostcredits() {
		return postcredits;
	}
	public void setPostcredits(String postcredits) {
		this.postcredits = postcredits;
	}
	public String getReplycredits() {
		return replycredits;
	}
	public void setReplycredits(String replycredits) {
		this.replycredits = replycredits;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
