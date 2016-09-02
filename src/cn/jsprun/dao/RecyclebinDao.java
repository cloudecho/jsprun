package cn.jsprun.dao;
import java.util.List;
import cn.jsprun.domain.Threadsmod;
import cn.jsprun.struts.form.RecyclebinForm;
public interface RecyclebinDao {
	public String findByAll(RecyclebinForm recyclebinForm,String timeoffset);
	public int undeleteArray(List<Threadsmod> updatelsit);
}
