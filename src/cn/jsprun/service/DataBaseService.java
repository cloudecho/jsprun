package cn.jsprun.service;
import java.util.List;
import java.util.Map;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.vo.FieldVO;
import cn.jsprun.vo.TableStatusVO;
public class DataBaseService {
	private DataBaseDao databaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
	public List<String> findAllTableNames(String prefix){
		return databaseDao.findAllTableNames(prefix);
	}
	public Map<String,String> runQuery(String sql){
		return databaseDao.runQuery(sql);
	}
	public void runQuery(String sql,boolean noUpdateCount){
		databaseDao.runQuery(sql,true);
	}
	public int insert(String sql,boolean show_last_insert_id){
		return databaseDao.insert(sql,show_last_insert_id);
	}
	public List<TableStatusVO> findTableStatus(String sql){
		return databaseDao.findTableStatus(sql);
	}
	public List<FieldVO> findTableFields(String tableName){
		return databaseDao.findTableFields(tableName);
	}
	public String findBasedir(){
		return databaseDao.findBasedir();
	}
	public String showCreateSql(String tableName){
		return databaseDao.showCreateSql(tableName);
	}
	public Boolean executeUpdateByHql(String hql)
	{
		return databaseDao.executeUpdateByHql(hql);
	}
	@SuppressWarnings("unchecked")
	public Map sqldumptable(List<String> excepttables,String table,int startfrom, long currsize,long sizelimit,boolean complete,String version,int extendins,String sqlcompat,String dumpcharset,String sqlcharset,boolean usehex)
	{
		return databaseDao.sqldumptable(excepttables, table, startfrom, currsize, sizelimit, complete, version, extendins, sqlcompat, dumpcharset, sqlcharset, usehex);
	}
	public List<Map<String,String>> executeQuery(String sql,String ...args)
	{
		return databaseDao.executeQuery(sql,args);
	}
	public void execute(String sql){
		databaseDao.execute(sql);
	}
	public void execute(String sql,String ...args){
		databaseDao.execute(sql,args);
	}
}
