package cn.jsprun.domain;
public class ThreadtagsId  implements java.io.Serializable {
	private static final long serialVersionUID = 5083921593913587473L;
     private String tagname;
     private Integer tid;
    public ThreadtagsId() {
    }
    public ThreadtagsId(String tagname, Integer tid) {
        this.tagname = tagname;
        this.tid = tid;
    }
    public String getTagname() {
        return this.tagname;
    }
    public void setTagname(String tagname) {
        this.tagname = tagname;
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
		 if ( !(other instanceof ThreadtagsId) ) return false;
		 ThreadtagsId castOther = ( ThreadtagsId ) other; 
		 return ( (this.getTagname()==castOther.getTagname()) || ( this.getTagname()!=null && castOther.getTagname()!=null && this.getTagname().equals(castOther.getTagname()) ) )
 && ( (this.getTid()==castOther.getTid()) || ( this.getTid()!=null && castOther.getTid()!=null && this.getTid().equals(castOther.getTid()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getTagname() == null ? 0 : this.getTagname().hashCode() );
         result = 37 * result + ( getTid() == null ? 0 : this.getTid().hashCode() );
         return result;
   }   
}