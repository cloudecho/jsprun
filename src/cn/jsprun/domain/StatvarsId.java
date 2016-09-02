package cn.jsprun.domain;
public class StatvarsId  implements java.io.Serializable {
	private static final long serialVersionUID = -8653503996257344184L;
     private String type;
     private String variable;
    public StatvarsId() {
    }
    public StatvarsId(String type, String variable) {
        this.type = type;
        this.variable = variable;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
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
		 if ( !(other instanceof StatvarsId) ) return false;
		 StatvarsId castOther = ( StatvarsId ) other; 
		 return ( (this.getType()==castOther.getType()) || ( this.getType()!=null && castOther.getType()!=null && this.getType().equals(castOther.getType()) ) )
 && ( (this.getVariable()==castOther.getVariable()) || ( this.getVariable()!=null && castOther.getVariable()!=null && this.getVariable().equals(castOther.getVariable()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getType() == null ? 0 : this.getType().hashCode() );
         result = 37 * result + ( getVariable() == null ? 0 : this.getVariable().hashCode() );
         return result;
   }   
}