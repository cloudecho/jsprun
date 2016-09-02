package cn.jsprun.domain;
public class Stats  implements java.io.Serializable {
	private static final long serialVersionUID = -723349003919829228L;
	 private StatsId id;
     private Integer count;
    public Stats() {
    }
    public Stats(StatsId id, Integer count) {
        this.id = id;
        this.count = count;
    }
    public StatsId getId() {
        return this.id;
    }
    public void setId(StatsId id) {
        this.id = id;
    }
    public Integer getCount() {
        return this.count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
}