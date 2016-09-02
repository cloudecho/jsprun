package cn.jsprun.domain;
public class Spacecaches  implements java.io.Serializable {
	private static final long serialVersionUID = -2181567035616297356L;
	 private SpacecachesId id;
     private String value;
     private Integer expiration;
    public Spacecaches() {
    }
    public Spacecaches(SpacecachesId id, String value, Integer expiration) {
        this.id = id;
        this.value = value;
        this.expiration = expiration;
    }
    public SpacecachesId getId() {
        return this.id;
    }
    public void setId(SpacecachesId id) {
        this.id = id;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Integer getExpiration() {
        return this.expiration;
    }
    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}