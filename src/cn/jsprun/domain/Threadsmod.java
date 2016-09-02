package cn.jsprun.domain;
public class Threadsmod  implements java.io.Serializable {
	private static final long serialVersionUID = 4288487195049102112L;
    private ThreadsmodId id;
    public Threadsmod() {
    }
    public Threadsmod(ThreadsmodId id) {
        this.id = id;
    }
    public ThreadsmodId getId() {
        return this.id;
    }
    public void setId(ThreadsmodId id) {
        this.id = id;
    }
}