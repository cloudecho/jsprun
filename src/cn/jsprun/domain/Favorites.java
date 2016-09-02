package cn.jsprun.domain;
public class Favorites  implements java.io.Serializable {
	private static final long serialVersionUID = 5079609025513924299L;
	private FavoritesId id;	
    public Favorites() {
    }
    public Favorites(FavoritesId id) {
        this.id = id;
    }
    public FavoritesId getId() {
        return this.id;
    }
    public void setId(FavoritesId id) {
        this.id = id;
    }
}