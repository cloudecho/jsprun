package cn.jsprun.domain;
public class ModworksId  implements java.io.Serializable {
	private static final long serialVersionUID = 6412710621184083770L;
	private Integer uid;		
     private String modaction;	
     private String dateline;		
     private Short count;		
     private Short posts;		
    public ModworksId() {
    }
    public ModworksId(Integer uid, String modaction, String dateline, Short count, Short posts) {
        this.uid = uid;
        this.modaction = modaction;
        this.dateline = dateline;
        this.count = count;
        this.posts = posts;
    }
    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getModaction() {
        return this.modaction;
    }
    public void setModaction(String modaction) {
        this.modaction = modaction;
    }
    public String getDateline() {
        return this.dateline;
    }
    public void setDateline(String dateline) {
        this.dateline = dateline;
    }
    public Short getCount() {
        return this.count;
    }
    public void setCount(Short count) {
        this.count = count;
    }
    public Short getPosts() {
        return this.posts;
    }
    public void setPosts(Short posts) {
        this.posts = posts;
    }
   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ModworksId) ) return false;
		 ModworksId castOther = ( ModworksId ) other; 
		 return ( (this.getUid()==castOther.getUid()) || ( this.getUid()!=null && castOther.getUid()!=null && this.getUid().equals(castOther.getUid()) ) )
 && ( (this.getModaction()==castOther.getModaction()) || ( this.getModaction()!=null && castOther.getModaction()!=null && this.getModaction().equals(castOther.getModaction()) ) )
 && ( (this.getDateline()==castOther.getDateline()) || ( this.getDateline()!=null && castOther.getDateline()!=null && this.getDateline().equals(castOther.getDateline()) ) )
 && ( (this.getCount()==castOther.getCount()) || ( this.getCount()!=null && castOther.getCount()!=null && this.getCount().equals(castOther.getCount()) ) )
 && ( (this.getPosts()==castOther.getPosts()) || ( this.getPosts()!=null && castOther.getPosts()!=null && this.getPosts().equals(castOther.getPosts()) ) );
   }
   public int hashCode() {
         int result = 17;
         result = 37 * result + ( getUid() == null ? 0 : this.getUid().hashCode() );
         result = 37 * result + ( getModaction() == null ? 0 : this.getModaction().hashCode() );
         result = 37 * result + ( getDateline() == null ? 0 : this.getDateline().hashCode() );
         result = 37 * result + ( getCount() == null ? 0 : this.getCount().hashCode() );
         result = 37 * result + ( getPosts() == null ? 0 : this.getPosts().hashCode() );
         return result;
   }   
}