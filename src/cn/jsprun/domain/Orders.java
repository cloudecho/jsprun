package cn.jsprun.domain;
public class Orders  implements java.io.Serializable {
	private static final long serialVersionUID = 911696266889756995L;
     private OrdersId id;
    public Orders() {
    }
    public Orders(OrdersId id) {
        this.id = id;
    }
    public OrdersId getId() {
        return this.id;
    }
    public void setId(OrdersId id) {
        this.id = id;
    }
}