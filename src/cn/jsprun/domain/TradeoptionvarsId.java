package cn.jsprun.domain;
public class TradeoptionvarsId  implements java.io.Serializable {
	private static final long serialVersionUID = 6748322383273134208L;
     private Short typeid;
     private Integer pid;
     private Short optionid;
     private String value;
    public TradeoptionvarsId() {
    }
    public TradeoptionvarsId(Short typeid, Integer pid, Short optionid, String value) {
        this.typeid = typeid;
        this.pid = pid;
        this.optionid = optionid;
        this.value = value;
    }
    public Short getTypeid() {
        return this.typeid;
    }
    public void setTypeid(Short typeid) {
        this.typeid = typeid;
    }
    public Integer getPid() {
        return this.pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public Short getOptionid() {
        return this.optionid;
    }
    public void setOptionid(Short optionid) {
        this.optionid = optionid;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TradeoptionvarsId) ) return false;
		 TradeoptionvarsId castOther = ( TradeoptionvarsId ) other; 
		 return ( (this.getTypeid()==castOther.getTypeid()) || ( this.getTypeid()!=null && castOther.getTypeid()!=null && this.getTypeid().equals(castOther.getTypeid()) ) )
 && ( (this.getPid()==castOther.getPid()) || ( this.getPid()!=null && castOther.getPid()!=null && this.getPid().equals(castOther.getPid()) ) )
 && ( (this.getOptionid()==castOther.getOptionid()) || ( this.getOptionid()!=null && castOther.getOptionid()!=null && this.getOptionid().equals(castOther.getOptionid()) ) )
 && ( (this.getValue()==castOther.getValue()) || ( this.getValue()!=null && castOther.getValue()!=null && this.getValue().equals(castOther.getValue()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getTypeid() == null ? 0 : this.getTypeid().hashCode() );
         result = 37 * result + ( getPid() == null ? 0 : this.getPid().hashCode() );
         result = 37 * result + ( getOptionid() == null ? 0 : this.getOptionid().hashCode() );
         result = 37 * result + ( getValue() == null ? 0 : this.getValue().hashCode() );
         return result;
   }   
}