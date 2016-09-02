package cn.jsprun.domain;
public class Rewardlog  implements java.io.Serializable {
	private static final long serialVersionUID = 3664010107301749595L;
     private RewardlogId id;
    public Rewardlog() {
    }
    public Rewardlog(RewardlogId id) {
        this.id = id;
    }
    public RewardlogId getId() {
        return this.id;
    }
    public void setId(RewardlogId id) {
        this.id = id;
    }
}