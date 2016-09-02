package cn.jsprun.domain;
public class SessionsId  implements java.io.Serializable {
	private static final long serialVersionUID = -1411043452366141231L;
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
    public SessionsId() {
    }
    public SessionsId(String sid, Short ip1, Short ip2, Short ip3, Short ip4, Integer uid, String username, Short groupid, Short styleid, Byte invisible, Short action, Integer lastactivity, Integer lastolupdate, Short pageviews, Integer seccode, Short fid, Integer tid) {
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
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SessionsId) ) return false;
		 SessionsId castOther = ( SessionsId ) other; 
		 return ( (this.getSid()==castOther.getSid()) || ( this.getSid()!=null && castOther.getSid()!=null && this.getSid().equals(castOther.getSid()) ) )
 && ( (this.getIp1()==castOther.getIp1()) || ( this.getIp1()!=null && castOther.getIp1()!=null && this.getIp1().equals(castOther.getIp1()) ) )
 && ( (this.getIp2()==castOther.getIp2()) || ( this.getIp2()!=null && castOther.getIp2()!=null && this.getIp2().equals(castOther.getIp2()) ) )
 && ( (this.getIp3()==castOther.getIp3()) || ( this.getIp3()!=null && castOther.getIp3()!=null && this.getIp3().equals(castOther.getIp3()) ) )
 && ( (this.getIp4()==castOther.getIp4()) || ( this.getIp4()!=null && castOther.getIp4()!=null && this.getIp4().equals(castOther.getIp4()) ) )
 && ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getUsername()==castOther.getUsername()) || ( this.getUsername()!=null && castOther.getUsername()!=null && this.getUsername().equals(castOther.getUsername()) ) )
 && ( (this.getGroupid()==castOther.getGroupid()) || ( this.getGroupid()!=null && castOther.getGroupid()!=null && this.getGroupid().equals(castOther.getGroupid()) ) )
 && ( (this.getStyleid()==castOther.getStyleid()) || ( this.getStyleid()!=null && castOther.getStyleid()!=null && this.getStyleid().equals(castOther.getStyleid()) ) )
 && ( (this.getInvisible()==castOther.getInvisible()) || ( this.getInvisible()!=null && castOther.getInvisible()!=null && this.getInvisible().equals(castOther.getInvisible()) ) )
 && ( (this.getAction()==castOther.getAction()) || ( this.getAction()!=null && castOther.getAction()!=null && this.getAction().equals(castOther.getAction()) ) )
 && ( (this.getLastactivity()==castOther.getLastactivity()) || ( this.getLastactivity()!=null && castOther.getLastactivity()!=null && this.getLastactivity().equals(castOther.getLastactivity()) ) )
 && ( (this.getLastolupdate()==castOther.getLastolupdate()) || ( this.getLastolupdate()!=null && castOther.getLastolupdate()!=null && this.getLastolupdate().equals(castOther.getLastolupdate()) ) )
 && ( (this.getPageviews()==castOther.getPageviews()) || ( this.getPageviews()!=null && castOther.getPageviews()!=null && this.getPageviews().equals(castOther.getPageviews()) ) )
 && ( (this.getSeccode()==castOther.getSeccode()) || ( this.getSeccode()!=null && castOther.getSeccode()!=null && this.getSeccode().equals(castOther.getSeccode()) ) )
 && ( (this.getFid()==castOther.getFid()) || ( this.getFid()!=null && castOther.getFid()!=null && this.getFid().equals(castOther.getFid()) ) )
 && ( (this.getTid()==castOther.getTid()) || ( this.getTid()!=null && castOther.getTid()!=null && this.getTid().equals(castOther.getTid()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getSid() == null ? 0 : this.getSid().hashCode() );
         result = 37 * result + ( getIp1() == null ? 0 : this.getIp1().hashCode() );
         result = 37 * result + ( getIp2() == null ? 0 : this.getIp2().hashCode() );
         result = 37 * result + ( getIp3() == null ? 0 : this.getIp3().hashCode() );
         result = 37 * result + ( getIp4() == null ? 0 : this.getIp4().hashCode() );
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getUsername() == null ? 0 : this.getUsername().hashCode() );
         result = 37 * result + ( getGroupid() == null ? 0 : this.getGroupid().hashCode() );
         result = 37 * result + ( getStyleid() == null ? 0 : this.getStyleid().hashCode() );
         result = 37 * result + ( getInvisible() == null ? 0 : this.getInvisible().hashCode() );
         result = 37 * result + ( getAction() == null ? 0 : this.getAction().hashCode() );
         result = 37 * result + ( getLastactivity() == null ? 0 : this.getLastactivity().hashCode() );
         result = 37 * result + ( getLastolupdate() == null ? 0 : this.getLastolupdate().hashCode() );
         result = 37 * result + ( getPageviews() == null ? 0 : this.getPageviews().hashCode() );
         result = 37 * result + ( getSeccode() == null ? 0 : this.getSeccode().hashCode() );
         result = 37 * result + ( getFid() == null ? 0 : this.getFid().hashCode() );
         result = 37 * result + ( getTid() == null ? 0 : this.getTid().hashCode() );
         return result;
   }   
}