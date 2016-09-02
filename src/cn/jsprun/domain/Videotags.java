package cn.jsprun.domain;
public class Videotags  implements java.io.Serializable {
	private static final long serialVersionUID = -518154557541947641L;
     private VideotagsId id;
    public Videotags() {
    }
    public Videotags(VideotagsId id) {
        this.id = id;
    }
    public VideotagsId getId() {
        return this.id;
    }
    public void setId(VideotagsId id) {
        this.id = id;
    }
}