package cn.jsprun.domain;
public class RegipsId  implements java.io.Serializable {
	private static final long serialVersionUID = -1663702878083615927L;
     private String ip;
     private Integer dateline;
     private Short count;
    public RegipsId() {
    }
    public RegipsId(String ip, Integer dateline, Short count) {
        this.ip = ip;
        this.dateline = dateline;
        this.count = count;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public Integer getDateline() {
        return this.dateline;
    }
    public void setDateline(Integer dateline) {
        this.dateline = dateline;
    }
    public Short getCount() {
        return this.count;
    }
    public void setCount(Short count) {
        this.count = count;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RegipsId) ) return false;
		 RegipsId castOther = ( RegipsId ) other; 
		 return ( (this.getIp()==castOther.getIp()) || ( this.getIp()!=null && castOther.getIp()!=null && this.getIp().equals(castOther.getIp()) ) )
 && ( (this.getDateline()==castOther.getDateline()) || ( this.getDateline()!=null && castOther.getDateline()!=null && this.getDateline().equals(castOther.getDateline()) ) )
 && ( (this.getCount()==castOther.getCount()) || ( this.getCount()!=null && castOther.getCount()!=null && this.getCount().equals(castOther.getCount()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getIp() == null ? 0 : this.getIp().hashCode() );
         result = 37 * result + ( getDateline() == null ? 0 : this.getDateline().hashCode() );
         result = 37 * result + ( getCount() == null ? 0 : this.getCount().hashCode() );
         return result;
   }   
}