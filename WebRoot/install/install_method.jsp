<%@ page language="java" import="java.io.*,java.sql.*,java.util.*,cn.jsprun.utils.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.struts.util.MessageResources"%>
<%@page import="java.util.Locale"%>
<%! 
	public Connection connection=null;
	public String message=null;
	public boolean dir_writeable(String dir_Path){
		boolean writeable=false;
		File file=new File(dir_Path);
		if(!file.isDirectory())
		{
			file.mkdir();
		}
		if(file.isDirectory())
		{
			if(file.canWrite())
			{
				writeable=true;
			}
			else{
				writeable=false;
			}
		}
		return writeable;
	}
	public boolean dir_clear(String dir_Path){
		boolean writeable=false;
		File dirFile=new File(dir_Path);
		if(dirFile!=null)
		{
			File[] files=dirFile.listFiles();
			for(File file:files)
			{
				if(file.isFile())
				{
					file.delete();
					writeable=true;
				}
			}
		}
		return writeable;
	}
	
	public boolean mysql_connect(String dbhost,String dbport,String dbuser,String dbpw,MessageResources mr,Locale locale){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://"+dbhost+":"+dbport+"/?useUnicode=true&characterEncoding="+JspRunConfig.CHARSET,dbuser,dbpw);
			message=null;
		} catch (Exception ex) {
			message="<font color='red'>"+mr.getMessage(locale,"database_errno_2003")+ex.getMessage()+"</font>";
		}
		if(conn!=null){
			connection= conn;
			mysql_update("set names utf8");
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean connect(String dbhost,String dbport,String dbname,String dbuser,String dbpw,MessageResources mr,Locale locale){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://"+dbhost+":"+dbport+"/"+dbname+"?useUnicode=true&characterEncoding="+JspRunConfig.CHARSET ,dbuser,dbpw);
			message=null;
		} catch (Exception ex) {
			message=mr.getMessage(locale,"database_errno_2009")+ex.getMessage();
		}
		if(conn!=null){
			connection= conn;
			mysql_update("set names utf8");
			return true;
		}
		else{
			return false;
		}
	}
	
	public ResultSet mysql_query(String sql)
	{
		PreparedStatement pstmt = null;
		try {
			pstmt =connection.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			message=null;
			return rs;
		} catch (SQLException e) {
			message=e.getMessage();
		}
		return null;
	}
	
	public boolean mysql_update(String sql)
	{
		try {
			PreparedStatement pstmt =connection.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
			message=null;
			return true;
		} catch (SQLException e) {
			message=e.getMessage();
		}
		return false;
	}
	
	public String mysql_get_server_info()
	{
		ResultSet rs=mysql_query("select version()");
		String version="";
		try {
			while(rs!=null&&rs.next())
			{
				version=rs.getString(1);
			}
			rs.close();
			message=null;
		} catch (SQLException e) {
			message=e.getMessage();
		}
		return version;
	}
	
	public void mysql_select_db(String dbname)
	{
		mysql_update("use "+dbname);
	}
	
	public String mysql_errno()
	{
		return message;
	}
	
	public void mysql_close(){
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				connection=null;
			}
			message=null;
		} catch (Exception ex) {
			message=ex.getMessage();
		}
	}
	
	public String createtable(String sql, String dbcharset) {
		List<String> types=Common.getStr(sql,"(?i)MYISAM|HEAP");
		String type=types!=null&&types.size()>0?types.get(0):"MYISAM";
		return sql.substring(0,sql.lastIndexOf(")")+1)+(mysql_get_server_info().compareTo("4.1")> 0 ? " ENGINE="+type+" DEFAULT CHARSET="+dbcharset:" TYPE="+type);
	}
	
	public void showjsmessage(HttpServletResponse response,String message) {
		try {
			Writer writer=response.getWriter();
			writer.write("<script type=\"text/javascript\">showmessage('"+message+"');</script>\r\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void runquery(HttpServletResponse response,String sql,String tablepre,String dbcharset,MessageResources mr,Locale locale) {
		sql=sql.replaceAll("(\r\n|\r|\n)", "\n");
		sql=sql.replaceAll(" jrun_", " "+tablepre);
		String[] queries=sql.trim().split(";\n");
		if(queries!=null&&queries.length>0)
		{
			for(String query:queries)
			{
				query=query.trim();
				if(!query.equals("")) {
					if(query.startsWith("CREATE TABLE")) {
						String name = query.substring(13, query.indexOf("(")-1);
						if(mysql_update(createtable(query, dbcharset))){
							showjsmessage(response,mr.getMessage(locale,"create_table_success",name));
						}else{
							message=Common.addslashes(message);
							showjsmessage(response,mr.getMessage(locale,"create_table_failed",name)+", "+message.replaceAll("\n", "\\\\n"));
						}
					}else{
						if(!mysql_update(query)){
							message=Common.addslashes(message);
							showjsmessage(response,mr.getMessage(locale,"update_data_failed")+", "+message.replaceAll("\n", "\\\\n"));
						}
					}
				}
			}
		}
	}
	
	public void loginit(HttpServletResponse response,String logfile,int timestamp, MessageResources mr,Locale locale) {
		showjsmessage(response,mr.getMessage(locale,"init_log")+" "+logfile);
		Log.writelog(logfile,timestamp,"");
	}
	
	public void dir_clear(HttpServletResponse response,String realPath,String dir,MessageResources mr,Locale locale) {
		showjsmessage(response,mr.getMessage(locale,"clear_dir")+" "+dir);
		File basedir=new File(realPath+dir);
		if(basedir!=null&&basedir.isDirectory())
		{
			File[] files=basedir.listFiles();
			if(files!=null&&files.length>0)
			{
				for(File file:files)
				{
					file.delete();
				}
			}
		}
	}
	public long getFreeSpace(String path){
		try {
			String os_name = System.getProperty("os.name");
			if (os_name.startsWith("Windows")) {
				return getFreeSpaceOnWindows(path);
			}else if (os_name.startsWith("Linux")) {
				return getFreeSpaceOnLinux(path);
			}
		} catch (Exception e) {}
		return -1;
	}

	private long getFreeSpaceOnWindows(String path) throws Exception {
		long bytesFree=-1;
		String  command = "cmd.exe /c dir \"" + path+"\"";
		 
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		process = runtime.exec(command);
		if (process == null) {
			return bytesFree;
		}
		
		InputStreamReader isr=new InputStreamReader(process.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		String line;
		String freeSpace = null;
		while ((line = br.readLine()) != null) {
			freeSpace = line;
		}
		if (freeSpace == null) {
			return bytesFree;
		}
		process.destroy();
		isr.close();
		br.close();
		
		freeSpace = freeSpace.trim();
		freeSpace = freeSpace.replaceAll("\\.", "");
		freeSpace = freeSpace.replaceAll(",", "");
		String[] items = freeSpace.split(" ");
		
		
		int index = 1;
		while (index < items.length) {
			try {
				bytesFree = Long.parseLong(items[index++]);
				break;
			} catch (NumberFormatException nfe) {}
		}
		return bytesFree;
	}

	private long getFreeSpaceOnLinux(String path) throws Exception {
		long bytesFree = -1;
		String  command = "df " + "/" + path;
		 
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(command);
		if (process == null) {
			return bytesFree;
		}
		
		InputStreamReader isr=new InputStreamReader(process.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		String line;
		String freeSpace = null;
		while ((line = br.readLine()) != null) {
			freeSpace = line;
		}
		if (freeSpace == null) {
			return bytesFree;
		}
		process.destroy();
		isr.close();
		br.close();
		
		freeSpace = freeSpace.trim();
		freeSpace = freeSpace.replaceAll("\\.", "");
		freeSpace = freeSpace.replaceAll(",", "");
		String[] items = freeSpace.split(" ");
		
		
		if (items.length >= 4) {
			bytesFree = Long.parseLong(items[3]);
		}
		return bytesFree;
	}
%>