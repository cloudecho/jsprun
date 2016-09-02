package cn.jsprun.domain;
public class Threadtags  implements java.io.Serializable {
	private static final long serialVersionUID = -1995364674905700589L;
     private ThreadtagsId id;
    public Threadtags() {
    }
    public Threadtags(ThreadtagsId id) {
        this.id = id;
    }
    public ThreadtagsId getId() {
        return this.id;
    }
    public void setId(ThreadtagsId id) {
        this.id = id;
    }
}