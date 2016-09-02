package cn.jsprun.domain;
public class Subscriptions  implements java.io.Serializable {
	private static final long serialVersionUID = -8584661456579847548L;
	 private SubscriptionsId id;
     private Integer lastpost;
     private Integer lastnotify;
    public Subscriptions() {
    }
    public Subscriptions(SubscriptionsId id, Integer lastpost, Integer lastnotify) {
        this.id = id;
        this.lastpost = lastpost;
        this.lastnotify = lastnotify;
    }
    public SubscriptionsId getId() {
        return this.id;
    }
    public void setId(SubscriptionsId id) {
        this.id = id;
    }
    public Integer getLastpost() {
        return this.lastpost;
    }
    public void setLastpost(Integer lastpost) {
        this.lastpost = lastpost;
    }
    public Integer getLastnotify() {
        return this.lastnotify;
    }
    public void setLastnotify(Integer lastnotify) {
        this.lastnotify = lastnotify;
    }
}