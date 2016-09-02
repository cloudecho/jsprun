package cn.jsprun.domain;
public class Tradeoptionvars  implements java.io.Serializable {
	private static final long serialVersionUID = 2596813362018266359L;
     private TradeoptionvarsId id;
    public Tradeoptionvars() {
    }
    public Tradeoptionvars(TradeoptionvarsId id) {
        this.id = id;
    }
    public TradeoptionvarsId getId() {
        return this.id;
    }
    public void setId(TradeoptionvarsId id) {
        this.id = id;
    }
}