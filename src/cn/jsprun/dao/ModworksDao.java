package cn.jsprun.dao;
import cn.jsprun.domain.Modworks;
public interface ModworksDao {
	public Modworks getModworksByDatelineAndModaction(String dateline,String modation,Integer uid);
	public void saveModworks(Modworks modworks);
}
