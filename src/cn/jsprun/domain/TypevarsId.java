package cn.jsprun.domain;
public class TypevarsId  implements java.io.Serializable {
	private static final long serialVersionUID = -1580324978626671382L;
     private Short typeid;
     private Short optionid;
     private Byte available;
     private Byte required;
     private Byte unchangeable;
     private Byte search;
     private Short displayorder;
    public TypevarsId() {
    }
    public TypevarsId(Short typeid, Short optionid, Byte available, Byte required, Byte unchangeable, Byte search, Short displayorder) {
        this.typeid = typeid;
        this.optionid = optionid;
        this.available = available;
        this.required = required;
        this.unchangeable = unchangeable;
        this.search = search;
        this.displayorder = displayorder;
    }
    public Short getTypeid() {
        return this.typeid;
    }
    public void setTypeid(Short typeid) {
        this.typeid = typeid;
    }
    public Short getOptionid() {
        return this.optionid;
    }
    public void setOptionid(Short optionid) {
        this.optionid = optionid;
    }
    public Byte getAvailable() {
        return this.available;
    }
    public void setAvailable(Byte available) {
        this.available = available;
    }
    public Byte getRequired() {
        return this.required;
    }
    public void setRequired(Byte required) {
        this.required = required;
    }
    public Byte getUnchangeable() {
        return this.unchangeable;
    }
    public void setUnchangeable(Byte unchangeable) {
        this.unchangeable = unchangeable;
    }
    public Byte getSearch() {
        return this.search;
    }
    public void setSearch(Byte search) {
        this.search = search;
    }
    public Short getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Short displayorder) {
        this.displayorder = displayorder;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TypevarsId) ) return false;
		 TypevarsId castOther = ( TypevarsId ) other; 
		 return ( (this.getTypeid()==castOther.getTypeid()) || ( this.getTypeid()!=null && castOther.getTypeid()!=null && this.getTypeid().equals(castOther.getTypeid()) ) )
 && ( (this.getOptionid()==castOther.getOptionid()) || ( this.getOptionid()!=null && castOther.getOptionid()!=null && this.getOptionid().equals(castOther.getOptionid()) ) )
 && ( (this.getAvailable()==castOther.getAvailable()) || ( this.getAvailable()!=null && castOther.getAvailable()!=null && this.getAvailable().equals(castOther.getAvailable()) ) )
 && ( (this.getRequired()==castOther.getRequired()) || ( this.getRequired()!=null && castOther.getRequired()!=null && this.getRequired().equals(castOther.getRequired()) ) )
 && ( (this.getUnchangeable()==castOther.getUnchangeable()) || ( this.getUnchangeable()!=null && castOther.getUnchangeable()!=null && this.getUnchangeable().equals(castOther.getUnchangeable()) ) )
 && ( (this.getSearch()==castOther.getSearch()) || ( this.getSearch()!=null && castOther.getSearch()!=null && this.getSearch().equals(castOther.getSearch()) ) )
 && ( (this.getDisplayorder()==castOther.getDisplayorder()) || ( this.getDisplayorder()!=null && castOther.getDisplayorder()!=null && this.getDisplayorder().equals(castOther.getDisplayorder()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getTypeid() == null ? 0 : this.getTypeid().hashCode() );
         result = 37 * result + ( getOptionid() == null ? 0 : this.getOptionid().hashCode() );
         result = 37 * result + ( getAvailable() == null ? 0 : this.getAvailable().hashCode() );
         result = 37 * result + ( getRequired() == null ? 0 : this.getRequired().hashCode() );
         result = 37 * result + ( getUnchangeable() == null ? 0 : this.getUnchangeable().hashCode() );
         result = 37 * result + ( getSearch() == null ? 0 : this.getSearch().hashCode() );
         result = 37 * result + ( getDisplayorder() == null ? 0 : this.getDisplayorder().hashCode() );
         return result;
   }   
}