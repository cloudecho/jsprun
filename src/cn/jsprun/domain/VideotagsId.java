package cn.jsprun.domain;
public class VideotagsId  implements java.io.Serializable {
	private static final long serialVersionUID = 779266845162490072L;
     private String tagname;
     private String vid;
     private Integer tid;
    public VideotagsId() {
    }
    public VideotagsId(String tagname, String vid, Integer tid) {
        this.tagname = tagname;
        this.vid = vid;
        this.tid = tid;
    }
    public String getTagname() {
        return this.tagname;
    }
    public void setTagname(String tagname) {
        this.tagname = tagname;
    }
    public String getVid() {
        return this.vid;
    }
    public void setVid(String vid) {
        this.vid = vid;
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
		 if ( !(other instanceof VideotagsId) ) return false;
		 VideotagsId castOther = ( VideotagsId ) other; 
		 return ( (this.getTagname()==castOther.getTagname()) || ( this.getTagname()!=null && castOther.getTagname()!=null && this.getTagname().equals(castOther.getTagname()) ) )
 && ( (this.getVid()==castOther.getVid()) || ( this.getVid()!=null && castOther.getVid()!=null && this.getVid().equals(castOther.getVid()) ) )
 && ( (this.getTid()==castOther.getTid()) || ( this.getTid()!=null && castOther.getTid()!=null && this.getTid().equals(castOther.getTid()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getTagname() == null ? 0 : this.getTagname().hashCode() );
         result = 37 * result + ( getVid() == null ? 0 : this.getVid().hashCode() );
         result = 37 * result + ( getTid() == null ? 0 : this.getTid().hashCode() );
         return result;
   }   
}