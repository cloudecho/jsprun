package cn.jsprun.domain;
public class Sessions  implements java.io.Serializable {
	private static final long serialVersionUID = -7104736760939742895L;
    private String sid;
    private Short ip1;
    private Short ip2;
    private Short ip3;
    private Short ip4;
    private Integer uid;
    private String username;
    private Short groupid;
    private Short styleid;
    private Byte invisible;
    private Short action;
    private Integer lastactivity;
    private Integer lastolupdate;
    private Short pageviews;
    private Integer seccode;
    private Short fid;
    private Integer tid;
   public Sessions() {
   }
   public Sessions(String sid, Short ip1, Short ip2, Short ip3, Short ip4, Integer uid, String username, Short groupid, Short styleid, Byte invisible, Short action, Integer lastactivity, Integer lastolupdate, Short pageviews, Integer seccode, Short fid, Integer tid) {
       this.sid = sid;
       this.ip1 = ip1;
       this.ip2 = ip2;
       this.ip3 = ip3;
       this.ip4 = ip4;
       this.uid = uid;
       this.username = username;
       this.groupid = groupid;
       this.styleid = styleid;
       this.invisible = invisible;
       this.action = action;
       this.lastactivity = lastactivity;
       this.lastolupdate = lastolupdate;
       this.pageviews = pageviews;
       this.seccode = seccode;
       this.fid = fid;
       this.tid = tid;
   }
   public String getSid() {
       return this.sid;
   }
   public void setSid(String sid) {
       this.sid = sid;
   }
   public Short getIp1() {
       return this.ip1;
   }
   public void setIp1(Short ip1) {
       this.ip1 = ip1;
   }
   public Short getIp2() {
       return this.ip2;
   }
   public void setIp2(Short ip2) {
       this.ip2 = ip2;
   }
   public Short getIp3() {
       return this.ip3;
   }
   public void setIp3(Short ip3) {
       this.ip3 = ip3;
   }
   public Short getIp4() {
       return this.ip4;
   }
   public void setIp4(Short ip4) {
       this.ip4 = ip4;
   }
   public Integer getUid() {
       return this.uid;
   }
   public void setUid(Integer uid) {
       this.uid = uid;
   }
   public String getUsername() {
       return this.username;
   }
   public void setUsername(String username) {
       this.username = username;
   }
   public Short getGroupid() {
       return this.groupid;
   }
   public void setGroupid(Short groupid) {
       this.groupid = groupid;
   }
   public Short getStyleid() {
       return this.styleid;
   }
   public void setStyleid(Short styleid) {
       this.styleid = styleid;
   }
   public Byte getInvisible() {
       return this.invisible;
   }
   public void setInvisible(Byte invisible) {
       this.invisible = invisible;
   }
   public Short getAction() {
       return this.action;
   }
   public void setAction(Short action) {
       this.action = action;
   }
   public Integer getLastactivity() {
       return this.lastactivity;
   }
   public void setLastactivity(Integer lastactivity) {
       this.lastactivity = lastactivity;
   }
   public Integer getLastolupdate() {
       return this.lastolupdate;
   }
   public void setLastolupdate(Integer lastolupdate) {
       this.lastolupdate = lastolupdate;
   }
   public Short getPageviews() {
       return this.pageviews;
   }
   public void setPageviews(Short pageviews) {
       this.pageviews = pageviews;
   }
   public Integer getSeccode() {
       return this.seccode;
   }
   public void setSeccode(Integer seccode) {
       this.seccode = seccode;
   }
   public Short getFid() {
       return this.fid;
   }
   public void setFid(Short fid) {
       this.fid = fid;
   }
   public Integer getTid() {
       return this.tid;
   }
   public void setTid(Integer tid) {
       this.tid = tid;
   }
   @Override
public boolean equals(Object object) {
	if(!(object instanceof Sessions)){
		return false;
	}else{
		Sessions temp = (Sessions)object;
		return temp.sid.equals(this.sid);
	}
}
   @Override
public int hashCode() {
	return this.sid.hashCode();
}
}