package cn.jsprun.domain;
public class OnlinelistId  implements java.io.Serializable {
	private static final long serialVersionUID = 5674354272330855983L;
     private Short groupid;
     private Short displayorder;
     private String title;
     private String url;
    public OnlinelistId() {
    }
    public OnlinelistId(Short groupid, Short displayorder, String title, String url) {
        this.groupid = groupid;
        this.displayorder = displayorder;
        this.title = title;
        this.url = url;
    }
    public Short getGroupid() {
        return this.groupid;
    }
    public void setGroupid(Short groupid) {
        this.groupid = groupid;
    }
    public Short getDisplayorder() {
        return this.displayorder;
    }
    public void setDisplayorder(Short displayorder) {
        this.displayorder = displayorder;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof OnlinelistId) ) return false;
		 OnlinelistId castOther = ( OnlinelistId ) other; 
		 return ( (this.getGroupid()==castOther.getGroupid()) || ( this.getGroupid()!=null && castOther.getGroupid()!=null && this.getGroupid().equals(castOther.getGroupid()) ) )
 && ( (this.getDisplayorder()==castOther.getDisplayorder()) || ( this.getDisplayorder()!=null && castOther.getDisplayorder()!=null && this.getDisplayorder().equals(castOther.getDisplayorder()) ) )
 && ( (this.getTitle()==castOther.getTitle()) || ( this.getTitle()!=null && castOther.getTitle()!=null && this.getTitle().equals(castOther.getTitle()) ) )
 && ( (this.getUrl()==castOther.getUrl()) || ( this.getUrl()!=null && castOther.getUrl()!=null && this.getUrl().equals(castOther.getUrl()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getGroupid() == null ? 0 : this.getGroupid().hashCode() );
         result = 37 * result + ( getDisplayorder() == null ? 0 : this.getDisplayorder().hashCode() );
         result = 37 * result + ( getTitle() == null ? 0 : this.getTitle().hashCode() );
         result = 37 * result + ( getUrl() == null ? 0 : this.getUrl().hashCode() );
         return result;
   }   
}