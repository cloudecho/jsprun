package cn.jsprun.domain;
public class Tradelog  implements java.io.Serializable {
	private static final long serialVersionUID = -8043707572417376418L;
     private TradelogId id;
    public Tradelog() {
    }
    public Tradelog(TradelogId id) {
        this.id = id;
    }
    public TradelogId getId() {
        return this.id;
    }
    public void setId(TradelogId id) {
        this.id = id;
    }
}