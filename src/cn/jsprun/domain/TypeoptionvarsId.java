package cn.jsprun.domain;
public class TypeoptionvarsId  implements java.io.Serializable {
	private static final long serialVersionUID = 236640989626065672L;
     private Short typeid;
     private Integer tid;
     private Short optionid;
     private Integer expiration;
     private String value;
    public TypeoptionvarsId() {
    }
    public TypeoptionvarsId(Short typeid, Integer tid, Short optionid, Integer expiration, String value) {
        this.typeid = typeid;
        this.tid = tid;
        this.optionid = optionid;
        this.expiration = expiration;
        this.value = value;
    }
    public Short getTypeid() {
        return this.typeid;
    }
    public void setTypeid(Short typeid) {
        this.typeid = typeid;
    }
    public Integer getTid() {
        return this.tid;
    }
    public void setTid(Integer tid) {
        this.tid = tid;
    }
    public Short getOptionid() {
        return this.optionid;
    }
    public void setOptionid(Short optionid) {
        this.optionid = optionid;
    }
    public Integer getExpiration() {
        return this.expiration;
    }
    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
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
		 if ( !(other instanceof TypeoptionvarsId) ) return false;
		 TypeoptionvarsId castOther = ( TypeoptionvarsId ) other; 
		 return ( (this.getTypeid()==castOther.getTypeid()) || ( this.getTypeid()!=null && castOther.getTypeid()!=null && this.getTypeid().equals(castOther.getTypeid()) ) )
 && ( (this.getTid()==castOther.getTid()) || ( this.getTid()!=null && castOther.getTid()!=null && this.getTid().equals(castOther.getTid()) ) )
 && ( (this.getOptionid()==castOther.getOptionid()) || ( this.getOptionid()!=null && castOther.getOptionid()!=null && this.getOptionid().equals(castOther.getOptionid()) ) )
 && ( (this.getExpiration()==castOther.getExpiration()) || ( this.getExpiration()!=null && castOther.getExpiration()!=null && this.getExpiration().equals(castOther.getExpiration()) ) )
 && ( (this.getValue()==castOther.getValue()) || ( this.getValue()!=null && castOther.getValue()!=null && this.getValue().equals(castOther.getValue()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getTypeid() == null ? 0 : this.getTypeid().hashCode() );
         result = 37 * result + ( getTid() == null ? 0 : this.getTid().hashCode() );
         result = 37 * result + ( getOptionid() == null ? 0 : this.getOptionid().hashCode() );
         result = 37 * result + ( getExpiration() == null ? 0 : this.getExpiration().hashCode() );
         result = 37 * result + ( getValue() == null ? 0 : this.getValue().hashCode() );
         return result;
   }   
}