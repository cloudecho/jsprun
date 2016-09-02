package cn.jsprun.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.utils.Coding;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.HibernateUtil;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.vo.FieldStatusVO;
import cn.jsprun.vo.FieldVO;
import cn.jsprun.vo.TableStatusVO;
public class DataBaseDaoImpl implements DataBaseDao {
	@SuppressWarnings("unchecked")
	public Map sqldumptable(List<String> excepttables,String table,int startfrom, long currsize,long sizelimit,boolean complete,String version,int extendins,String sqlcompat,String dumpcharset,String sqlcharset,boolean usehex) {
		int offset = 300;
		Map map=new HashMap();
		StringBuffer tabledump = new StringBuffer();
		if (table.contains("adminsessions")) {
			map.put("tabledump", tabledump);
			return map;
		}
		List<FieldStatusVO> fieldStatusVOs = this.findTableFieldStatus(table);
		if (fieldStatusVOs == null || fieldStatusVOs.size() <= 0) {
			map.put("tabledump", tabledump);
			return map;
		}
		if(startfrom==0){
			String createtable = this.showCreateSql(table).replaceAll("`", "");
			if (createtable != null && createtable.length() > 0) {
				tabledump.append("DROP TABLE IF EXISTS " + table + ";\n");
			} else {
				map.put("tabledump", tabledump);
				return map;
			}
			tabledump.append(table.indexOf(".") != -1?createtable:createtable.replaceFirst("CREATE TABLE " + table,"CREATE TABLE " + table.substring(table.indexOf(".") + 1)));
			TableStatusVO tableStatusVO = this.findTableStatus("SHOW TABLE STATUS LIKE '" + table + "';").get(0);
			if (sqlcompat.equals("MYSQL41") && version.compareTo("4.1") < 0) {
				tabledump.replace(0, tabledump.length(),tabledump.toString().replaceFirst("TYPE\\=(.+)","ENGINE="+tableStatusVO.getEngine()+" DEFAULT CHARSET=" + dumpcharset)) ;
			}else if (sqlcompat.equals("MYSQL40") && version.compareTo("4.1") >= 0&& version.compareTo("5.1") < 0) {
				tabledump.replace(0, tabledump.length(),tabledump.toString().replaceFirst("ENGINE\\=(.+)","TYPE=" + tableStatusVO.getEngine()));
			}else if (version.compareTo("4.1") > 0 && sqlcharset.length()>0) {
				tabledump.replace(0, tabledump.length(),tabledump.toString().replaceFirst("(DEFAULT)*\\s*CHARSET=.+","DEFAULT CHARSET=" + sqlcharset));
			}
			tabledump.append((tableStatusVO.getAuto_increment()!=null&&!"".equals(tableStatusVO.getAuto_increment()) ?" AUTO_INCREMENT="+tableStatusVO.getAuto_increment(): "")+";\n\n");
			if (sqlcompat.equals("MYSQL40") && version.compareTo("4.1") >= 0&& version.compareTo("5.1") < 0) {
				if (tableStatusVO.getAuto_increment()!=null&&!"".equals(tableStatusVO.getAuto_increment())) {
					tabledump.insert(tabledump.indexOf(","), " auto_increment");
				}
				if ("MEMORY".equals(tableStatusVO.getEngine())) {
					int index=tabledump.indexOf("TYPE=MEMORY");
					if(index>0){
						tabledump.replace(index, "TYPE=MEMORY".length()+index, "TYPE=HEAP");
					}
				}
			}
		}
		if(!excepttables.contains(table)){
			int tabledumped=0;
			int numrows=offset;
			FieldStatusVO firstfield=fieldStatusVOs.get(0);
			if(extendins==0) {
				while((currsize+tabledump.length())<sizelimit&&numrows==offset&&complete){
					String selectsql=null;
					if("auto_increment".equals(firstfield.getExtra())){
						selectsql="SELECT * FROM "+table+" WHERE "+firstfield.getField()+" > "+startfrom+" LIMIT "+offset+";";
					}else{
						selectsql="SELECT * FROM "+table+" LIMIT "+startfrom+", "+offset+";";
					}
					tabledumped = 1;
					List<Map<String,String>> rows=this.executeQuery(selectsql);
					if(rows!=null){
						numrows=rows.size();
						StringBuffer t=null;
						for (Map<String, String> row : rows) {
							t=new StringBuffer();
							for (FieldStatusVO fieldStatusVO : fieldStatusVOs) {
								String type=fieldStatusVO.getType();
								String value=row.get(fieldStatusVO.getField());
								if(value==null){
									if("date".equals(type)){
										value="0000-00-00";
									}else{
										value="";
									}
								}
								t.append(","+(usehex&&!value.equals("")&&(type.contains("char")||type.contains("text"))?"0x"+Coding.bin2hex(value,JspRunConfig.CHARSET):"\'"+Common.mysqlEscapeString(value)+"\'"));
							}
							if((t.length()+currsize+tabledump.length())<sizelimit)
							{
								if("auto_increment".equals(firstfield.getExtra())){
									startfrom=Integer.valueOf(row.get(firstfield.getField()));
								}else{
									startfrom++;
								}
								if(t.length()>0){
									t.deleteCharAt(0);
									tabledump.append("INSERT INTO "+table+" VALUES ("+t+");\n");
								}
							}else{
								complete=false;
								break;
							}
						}
					}else{
						break;
					}
				}
			}
			else{
				while(currsize+tabledump.length()<sizelimit&&numrows==offset&&complete){
					String selectsql=null;
					if("auto_increment".equals(firstfield.getExtra())){
						selectsql="SELECT * FROM "+table+" WHERE "+firstfield.getField()+" > "+startfrom+" LIMIT "+offset+";";
					}else{
						selectsql="SELECT * FROM "+table+" LIMIT "+startfrom+", "+offset+";";
					}
					tabledumped = 1;
					List<Map<String,String>> rows=this.executeQuery(selectsql);
					if(rows!=null){
						numrows=rows.size();
						StringBuffer t1=new StringBuffer();
						for (Map<String, String> row : rows) {
							StringBuffer t2=new StringBuffer();
							for (FieldStatusVO fieldStatusVO : fieldStatusVOs) {
								String type=fieldStatusVO.getType();
								String value=row.get(fieldStatusVO.getField());
								if(value==null){
									if("date".equals(type)){
										value="0000-00-00";
									}else{
										value="";
									}
								}
								t2.append(","+(usehex&&!value.equals("")&&(type.contains("char")||type.contains("text"))?"0x"+Coding.bin2hex(value,JspRunConfig.CHARSET):"\'"+Common.mysqlEscapeString(value)+"\'"));
							}
							if(t1.length()+currsize+tabledump.length()<sizelimit){
								if("auto_increment".equals(firstfield.getExtra())){
									startfrom=Integer.valueOf(row.get(firstfield.getField()));
								}else{
									startfrom++;
								}
								if(t2.length()>0){
									t2.deleteCharAt(0);
									t1.append(",("+t2+")");
								}
							}else{
								complete=false;
								break;
							}
						}
						if(t1.length()>0){
							t1.deleteCharAt(0);
							tabledump.append("INSERT INTO "+table+" VALUES "+t1+";\n");
						}
					}else{
						break;
					}
				}
			}
			tabledump.append("\n");
			map.put("startfrom", startfrom);
			map.put("complete", complete);
		}
		map.put("tabledump", tabledump);
		return map;
	}
	public String showCreateSql(String tableName) {
		 String sql = "SHOW CREATE TABLE " + tableName + ";";
		 return this.executeQuery(sql,2).get(0);
	}
	public List<String> executeQuery(String sql, String columnName) {
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		Transaction transaction = null;
		try{
			List<String> rows = new ArrayList<String>();
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			conn = session.connection();
			pstmt = conn.prepareStatement(sql);
			rs =pstmt.executeQuery();
			while (rs.next()) {
				rows.add(rs.getString(columnName));
			}
			transaction.commit();
			return rows;
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
			return null;
		}finally {
			try {
				if(transaction!=null){
					transaction=null;
				}
				if(rs!=null){
					rs.close();
					rs=null;
				}
				if(pstmt!=null){
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null){
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public List<Map<String,String>> executeQuery(String sql,String ...args) {
		Session session =null;
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		Transaction transaction = null;
		List<Map<String,String>> rows =null;
		try{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			conn = session.connection();
			pstmt = conn.prepareStatement(sql);
			if(args!=null&&args.length>0){
				int size = args.length;
				for(int i=0;i<size;i++){
					String value = args[i];
					pstmt.setString(i+1,value);
				}
			}
			rs =pstmt.executeQuery();
			rows= new ArrayList<Map<String,String>>();
			ResultSetMetaData rsmd=rs.getMetaData();
			int columnCount=rsmd.getColumnCount();
			String []columns=new String[columnCount];
			for(int i=1;i<=columnCount;i++){
				columns[i-1]=rsmd.getColumnLabel(i);
			}
			rsmd=null;
			Map<String,String> row=null;
			while (rs.next()){
				row=new HashMap<String,String>(columnCount);
				for (int i=1;i<=columnCount;i++) {
					row.put(columns[i-1],rs.getString(i));
				}
				rows.add(row);				
			}
			row=null;
			columns=null;
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}finally {
			try {
				if(transaction!=null){
					transaction=null;
				}
				if(rs!=null){
					rs.close();
					rs=null;
				}
				if(pstmt!=null){
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null){
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rows;
	}
	public Boolean executeUpdateByHql(String hql) {
		boolean flag = false;
		Transaction tran = null;
		Query query = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tran = session.beginTransaction();
			query = session.createQuery(hql);
			query.executeUpdate();
			flag = true;
			tran.commit();
		} catch (HibernateException e) {
			flag = false;
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}
		return flag;
	}
	private List<String> executeQuery(String sql, int columnIndex) {
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		Transaction transaction = null;
		List<String> rows = new ArrayList<String>();
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			conn = session.connection();
			pstmt = conn.prepareStatement(sql);
			rs =pstmt.executeQuery();
			while (rs.next()) {
				rows.add(rs.getString(columnIndex));
			}
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}finally {
			try {
				if(transaction!=null){
					transaction=null;
				}
				if(rs!=null){
					rs.close();
					rs=null;
				}
				if(pstmt!=null){
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null){
					conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rows;
	}
	private List<FieldStatusVO> findTableFieldStatus(String tableName) {
		List<FieldStatusVO> fieldStatusVOs =  new ArrayList<FieldStatusVO>();
		String sql = "SHOW FULL COLUMNS FROM " + tableName;
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			conn = session.connection();
			pstmt = conn.prepareStatement(sql);
			rs =pstmt.executeQuery();
			FieldStatusVO fieldStatusVO = null;
			while (rs.next()) {
				fieldStatusVO = new FieldStatusVO();
				fieldStatusVO.setField(rs.getString("Field"));
				fieldStatusVO.setType(rs.getString("Type"));
				fieldStatusVO.setCollation(rs.getString("Collation"));
				fieldStatusVO.setAllowNull(rs.getString("Null"));
				fieldStatusVO.setKey(rs.getString("Key"));
				fieldStatusVO.setDefaultValue(rs.getString("Default"));
				fieldStatusVO.setExtra(rs.getString("Extra"));
				fieldStatusVO.setPrivileges(rs.getString("Privileges"));
				fieldStatusVO.setComment(rs.getString("Comment"));
				fieldStatusVOs.add(fieldStatusVO);
			}
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}finally {
			try {
				if(transaction!=null){
					transaction=null;
				}
				if(rs!=null){
					rs.close();
					rs=null;
				}
				if(pstmt!=null){
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null){
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fieldStatusVOs;
	}
	public String findBasedir() {
		String sql = "SHOW VARIABLES LIKE 'basedir';";
		return this.executeQuery(sql, "value").get(0);
	}
	public List<String> findAllTableNames(String prefix) {
		return this.executeQuery("SHOW TABLES LIKE '" + prefix + "%';", 1);
	}
	public int insert(String sql,boolean show_last_insert_id){
		int id=0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tran = session.beginTransaction();
		Connection conn = session.connection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute(sql);
			if(show_last_insert_id){
				pstmt = conn.prepareStatement("SELECT last_insert_id()");
				rs =pstmt.executeQuery();
				while (rs.next()) {
					id=rs.getInt(1);
				}
			}
			tran.commit();
		} catch (SQLException e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			try {
				if(tran!=null){
					tran=null;
				}
				if(rs!=null){
					rs.close();
					rs=null;
				}
				if(pstmt!=null){
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	public Map<String, String> runQuery(String sql) {
		Session session = null;
		Transaction tran = null;
		Map<String, String> infos = new HashMap<String, String>();
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		tran = session.beginTransaction();
		Connection conn = session.connection();
		Statement pstmt = null;
		ResultSet rs=null;
		try {
			pstmt = conn.createStatement();
			pstmt.setEscapeProcessing(false);
			pstmt.execute(sql);
			int num = pstmt.getUpdateCount();
			if (num == -1) {
				num = 0;
				rs = pstmt.executeQuery(sql);
				while (rs.next()) {
					num++;
				}
			}
			infos.put("ok", String.valueOf(num));
			tran.commit();
		} catch (SQLException e) {
			tran.rollback();
			infos.put("error", e.getMessage());
			infos.put("errorCode", e.getErrorCode()+"");
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null){
					rs.close();
					rs=null;
				}
				if(tran!=null){
					tran=null;
				}
				if(pstmt!=null){
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return infos;
	}
	public void runQuery(String sql,boolean noUpdateCount,String ...args) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tran = session.beginTransaction();
		Connection conn = session.connection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setEscapeProcessing(false);
			if(args!=null&&args.length>0){
				int size = args.length;
				for(int i=0;i<size;i++){
					String value = args[i];
					pstmt.setString(i+1,value);
				}
			}
			pstmt.execute();
			tran.commit();
		} catch (SQLException e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			try {
				if(tran!=null){
					tran=null;
				}
				if(pstmt!=null){
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public List<FieldVO> findTableFields(String tableName) {
		List<FieldVO> fieldVOs = new ArrayList<FieldVO>();
		String sql = "SHOW FIELDS FROM " + tableName;
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			conn = session.connection();
			pstmt = conn.prepareStatement(sql);
			rs =pstmt.executeQuery();
			FieldVO fieldVO = null;
			while (rs.next()) {
				fieldVO = new FieldVO();
				fieldVO.setField(rs.getString("Field"));
				fieldVO.setType(rs.getString("Type"));
				fieldVO.setAllowNull(rs.getString("Null"));
				fieldVO.setKey(rs.getString("Key"));
				fieldVO.setDefaultValue(rs.getString("Default"));
				fieldVO.setExtra(rs.getString("Extra"));
				fieldVOs.add(fieldVO);
			}
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}finally {
			try {
				if(transaction!=null){
					transaction=null;
				}
				if(rs!=null){
					rs.close();
					rs=null;
				}
				if(pstmt!=null){
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null){
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fieldVOs;
	}
	public List<TableStatusVO> findTableStatus(String sql) {
		List<TableStatusVO> tableStatusVOs = new ArrayList<TableStatusVO>();
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		Transaction transaction = null;
		try{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			conn = session.connection();
			pstmt = conn.prepareStatement(sql);
			rs =pstmt.executeQuery();
			TableStatusVO tableStatusVO = null;
			while (rs.next()) {
				tableStatusVO = new TableStatusVO();
				tableStatusVO.setName(rs.getString("Name"));
				tableStatusVO.setEngine(rs.getString("Engine"));
				tableStatusVO.setRows(rs.getLong("Rows"));
				tableStatusVO.setData_length(rs.getLong("Data_length"));
				tableStatusVO.setIndex_length(rs.getLong("Index_length"));
				tableStatusVO.setData_free(rs.getLong("Data_free"));
				tableStatusVO.setAuto_increment(rs.getString("Auto_increment"));
				tableStatusVO.setCollation(rs.getString("Collation"));
				tableStatusVOs.add(tableStatusVO);
			}
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction!=null){
				transaction.rollback();
			}
		}finally {
			try {
				if(transaction!=null){
					transaction=null;
				}
				if(rs!=null){
					rs.close();
					rs=null;
				}
				if(pstmt!=null){
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null){
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tableStatusVOs;
	}
	public void executeDelete(String sql,String ...args) {
		this.runQuery(sql, true,args);
	}
	public void execute(String sql,String ...args) {
		executeDelete(sql,args);
	}
}