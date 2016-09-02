package cn.jsprun.dao.impl;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.jsprun.dao.CronsDao;
import cn.jsprun.domain.Crons;
import cn.jsprun.utils.HibernateUtil;
import cn.jsprun.utils.JspRunConfig;
public class CronsDaoImpl implements CronsDao {
	private static final String tablepre = "jrun_";
	public boolean addCrons(Crons crons) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.save(crons);
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	public boolean delAnyCrons(List<Crons> cronsListDelete) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			int len = cronsListDelete.size();
			for(int i=0;i<len;i++){
				session.delete(cronsListDelete.get(i));
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	public Crons queryCronsById(Short id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Crons crons = (Crons) session.get(Crons.class, id);
			ts.commit();
			return crons;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<Crons> queryAllCrons() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		List<Crons> cronsList = null;
		try {
			ts = session.beginTransaction();
			cronsList = session.createQuery("from Crons").list();
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return cronsList;
	}
	@SuppressWarnings("unchecked")
	public List<Crons> queryCronsByCondition(String sqlStatement) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			List<Crons> cronsList = session.createSQLQuery(sqlStatement).addEntity(Crons.class).list();
			ts.commit();
			return cronsList;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}
	public boolean updateCrons(Crons crons) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.update(crons);
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateCrons(List<Crons> cronsList) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Iterator<Crons> cronsIt = cronsList.iterator();
			Crons crons = null;
			while(cronsIt.hasNext()){
				crons = cronsIt.next();
				session.update(crons);
			}
			ts.commit();
			return true;
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	public Connection getConnection() throws Exception{
		Properties properties = new Properties();
		InputStream fis = new FileInputStream(JspRunConfig.realPath+"config.properties");
		properties.load(fis);
		fis.close();
		String dbhost=properties.getProperty("dbhost");
		String dbport=properties.getProperty("dbport");
		String dbname=properties.getProperty("dbname");
		String dbuser=properties.getProperty("dbuser");
		String dbpw=properties.getProperty("dbpw");
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://"+dbhost+":"+dbport+"/"+dbname,dbuser,dbpw);
		connection.setAutoCommit(false);
		return connection;
	}
	public void begingTransaction(Connection connection ,int level) throws SQLException{
		try {
			connection.setTransactionIsolation(level);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<Map<String,String>> getRunningCronsInfo(Connection connection,int timestamp) throws SQLException{
		List<Map<String,String>> tempML = new ArrayList<Map<String,String>>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement("SELECT cronid,name,filename,weekday,day,hour,minute FROM "+tablepre+"crons WHERE available=1 AND nextrun<="+timestamp+"");
			rs = pstmt.executeQuery();
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
				tempML.add(row);				
			}
			row=null;
			columns=null;
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return tempML;
	}
	public Map<String,String> getRunningCronInfo(Connection connection,Object cronsid) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String,String> row=null;
		try {
			pstmt = connection.prepareStatement("SELECT cronid,name,filename,weekday,day,hour,minute FROM "+tablepre+"crons WHERE available=1 AND cronid='"+cronsid+"'");
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			int columnCount=rsmd.getColumnCount();
			String []columns=new String[columnCount];
			for(int i=1;i<=columnCount;i++){
				columns[i-1]=rsmd.getColumnLabel(i);
			}
			rsmd=null;
			while (rs.next()){
				row=new HashMap<String,String>(columnCount);
				for (int i=1;i<=columnCount;i++) {
					row.put(columns[i-1],rs.getString(i));
				}
			}
			columns=null;
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return row;
	}
	public void commitTransaction(Connection connection){
		try {
			connection.commit();
		} catch (SQLException e) {
			rollbackTransaction(connection);
		}
	}
	public void rollbackTransaction(Connection connection){
		if(connection!=null){
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void closeConnection(Connection connection){
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void execute(Connection connection,String sql) throws SQLException{
		Statement pstmt = null;
		try {
			pstmt = connection.createStatement();
			pstmt.execute(sql);
		} finally {
			if(pstmt!=null){
				pstmt.close();
				pstmt=null;
			}
		}
	}
	public List<Map<String,String>> executeQuery(Connection connection,String sql) throws SQLException {
		PreparedStatement pstmt =null;
		ResultSet rs=null;
		List<Map<String,String>> rows =null;
		try{
			pstmt = connection.prepareStatement(sql);
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
		}finally {
			if(rs!=null){
				rs.close();
				rs=null;
			}
			if(pstmt!=null){
				pstmt.close();
				pstmt=null;
			}
		}
		return rows;
	}
}
