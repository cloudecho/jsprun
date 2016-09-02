package cn.jsprun.utils;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public final class HibernateUtil {
	private static SessionFactory sessionFactory=null;
	private static String message = null;
	static{
		buildSessionFactory();
	}
	public static synchronized void buildSessionFactory()
	{
		if(sessionFactory==null){
			try {
				Properties properties = new Properties();
				InputStream fis = new FileInputStream(JspRunConfig.realPath+"config.properties");
				properties.load(fis);
				fis.close();
				String dbhost=properties.getProperty("dbhost");
				String dbport=properties.getProperty("dbport");
				String dbname=properties.getProperty("dbname");
				String dbuser=properties.getProperty("dbuser");
				String dbpw=properties.getProperty("dbpw");
				if(mysql_connect(dbhost,dbport,dbname,dbuser,dbpw))
				{
					Properties extraProperties = new Properties();
					extraProperties.setProperty("hibernate.connection.url", "jdbc:mysql://"+dbhost+":"+dbport+"/"+dbname+"?zeroDateTimeBehavior=convertToNull");
					extraProperties.setProperty("hibernate.connection.username", dbuser);
					extraProperties.setProperty("hibernate.connection.password", dbpw);
					Configuration configuration = new Configuration();
					configuration=configuration.configure("hibernate.cfg.xml");
					configuration=configuration.addProperties(extraProperties);
					sessionFactory=configuration.buildSessionFactory();
					extraProperties=null;
					configuration=null;
				}
				properties=null;
			} catch (Exception e) {
				message="Create sessionFactory Exception! "+e.getMessage();
			}
		}
	}
	public static SessionFactory getSessionFactory()
	{
		if(sessionFactory==null){
			 buildSessionFactory();
		}
		return sessionFactory;
	}
	public static Session getSession()
	{
		if(sessionFactory==null){
			buildSessionFactory();
		}
		return sessionFactory.getCurrentSession();
	}
	public static void rebuildSessionFactory()
	{
		try {
			Properties properties = new Properties();
			InputStream fis = new FileInputStream(JspRunConfig.realPath+"config.properties");
			properties.load(fis);
			fis.close();
			String dbhost=properties.getProperty("dbhost");
			String dbport=properties.getProperty("dbport");
			String dbname=properties.getProperty("dbname");
			String dbuser=properties.getProperty("dbuser");
			String dbpw=properties.getProperty("dbpw");
			if(mysql_connect(dbhost,dbport,dbname,dbuser,dbpw))
			{
				Properties extraProperties = new Properties();
				extraProperties.setProperty("hibernate.connection.url", "jdbc:mysql://"+dbhost+":"+dbport+"/"+dbname+"?zeroDateTimeBehavior=convertToNull");
				extraProperties.setProperty("hibernate.connection.username", dbuser);
				extraProperties.setProperty("hibernate.connection.password", dbpw);
				Configuration configuration = new Configuration();
				configuration=configuration.configure("hibernate.cfg.xml");
				configuration=configuration.addProperties(extraProperties);
				sessionFactory=configuration.buildSessionFactory();
				extraProperties=null;
				configuration=null;
			}
			properties=null;
		} catch (Exception e) {
			message="Create sessionFactory Exception! "+e.getMessage();
		}
	}
	public static boolean mysql_connect(String dbhost,String dbport,String dbname,String dbuser,String dbpw){
		Connection conn = null;
		boolean flag=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://"+dbhost+":"+dbport+"/"+dbname,dbuser,dbpw);
			if(conn!=null)
			{
				if(!conn.isClosed()){
					conn.close();
					conn=null;
				}
				return true;
			}
		} catch (Exception ex) {
			message = ex.getMessage();
		}
		return flag;
	}
	public static String getMessage() {
		return message;
	}
}