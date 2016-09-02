package cn.jsprun.dao;
import java.util.List;
import java.util.Map;
import cn.jsprun.vo.FieldVO;
import cn.jsprun.vo.TableStatusVO;
public interface DataBaseDao {
	public List<String> findAllTableNames(String prefix);
	public Map<String,String> runQuery(String sql);
	public void runQuery(String sql,boolean noUpdateCount,String ...args);
	public String findBasedir();
	public String showCreateSql(String tableName);
	public List<TableStatusVO> findTableStatus(String sql);
	public List<FieldVO> findTableFields(String tableName);
	public Boolean executeUpdateByHql(String hql);
	public int insert(String sql,boolean show_last_insert_id);
	@SuppressWarnings("unchecked")
	public Map sqldumptable(List<String> excepttables,String table,int startfrom, long currsize,long sizelimit,boolean complete,String version,int extendins,String sqlcompat,String dumpcharset,String sqlcharset,boolean usehex);
	public List<Map<String,String>> executeQuery(String sql,String ...args);
	public void executeDelete(String sql,String ...args);
	public void execute(String sql,String ...args);
}
