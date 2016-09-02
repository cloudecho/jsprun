package cn.jsprun.domain;
public class SpacecachesId  implements java.io.Serializable {
	private static final long serialVersionUID = 2503739620355312406L;
     private Integer uid;
     private String variable;
    public SpacecachesId() {
    }
    public SpacecachesId(Integer uid, String variable) {
        this.uid = uid;
        this.variable = variable;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getVariable() {
        return this.variable;
    }
    public void setVariable(String variable) {
        this.variable = variable;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SpacecachesId) ) return false;
		 SpacecachesId castOther = ( SpacecachesId ) other; 
		 return ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getVariable()==castOther.getVariable()) || ( this.getVariable()!=null && castOther.getVariable()!=null && this.getVariable().equals(castOther.getVariable()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getVariable() == null ? 0 : this.getVariable().hashCode() );
         return result;
   }   
}