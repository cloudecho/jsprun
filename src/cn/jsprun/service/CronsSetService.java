package cn.jsprun.service;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cn.jsprun.dao.CronsDao;
import cn.jsprun.dao.DataBaseDao;
import cn.jsprun.domain.Crons;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Log;
public class CronsSetService {
	public boolean addCrons(Crons crons){
		return ((CronsDao)BeanFactory.getBean("cronsSetDao")).addCrons(crons);
	}
	public List<Crons> queryAllCrons(){
		return ((CronsDao)BeanFactory.getBean("cronsSetDao")).queryAllCrons();
	}
	public Crons queryCronsById(Short id){
		return ((CronsDao)BeanFactory.getBean("cronsSetDao")).queryCronsById(id);
	}
	public List<Crons> queryCronsByCondition(String title, String startTime,String type,String orderBy){
		String sqlStatement = "select * from jrun_crons ";
		StringBuffer sqlSb = new StringBuffer();
		if(title!=null && !title.equals("")){
			sqlSb.append(" where name like '%" + title + "%' ");
		}
		if(!startTime.equals("") && !startTime.equals("0")){
			Integer st = Integer.parseInt(((new Date().getTime() - Long.parseLong(startTime+"000"))+"").substring(0,10));
			if(sqlSb.toString().trim()==null || sqlSb.toString().trim().equals("")){
				sqlSb.append(" where lastrun > " + st + " ");
			}else{
				sqlSb.append(" and nextrun > " + st + " ");
			}
		}
		if(!type.equals("") && !type.equals("0")){
			if(sqlSb.toString().trim()==null || sqlSb.toString().trim().equals("")){
				sqlSb.append(" where type = '" + type + "' ");
			}else{
				sqlSb.append(" and type = '" + type + "' ");
			}
		}
		if(!orderBy.equals("")){
			sqlSb.append(" order by " + orderBy);
		}
		sqlStatement = sqlStatement + sqlSb.toString();
		return ((CronsDao)BeanFactory.getBean("cronsSetDao")).queryCronsByCondition(sqlStatement);
	}
	public boolean updateCrons(Crons crons){
		return ((CronsDao)BeanFactory.getBean("cronsSetDao")).updateCrons(crons);
	}
	public boolean deleteCrons(List<Crons> cronsListDelete){
		return ((CronsDao)BeanFactory.getBean("cronsSetDao")).delAnyCrons(cronsListDelete);
	}
	public boolean updateCrons(List<Crons> cronsListUpdate){
		return ((CronsDao)BeanFactory.getBean("cronsSetDao")).updateCrons(cronsListUpdate);
	}
	public synchronized void cronsRun(Integer timestamp,HttpServletRequest request,HttpServletResponse response){
		Map<String, String> settings = ForumInit.settings;
		String cronnextrun = settings.get("cronnextrun");
		if(cronnextrun==null||cronnextrun.equals("0")||Integer.valueOf(cronnextrun)>timestamp){
			return;
		}
		CronsDao cronsDao = ((CronsDao)BeanFactory.getBean("cronsSetDao"));
		HttpSession session = request.getSession();
		Connection connection = null;
		List<Map<String,String>> cronsList = null;
		try{
			connection = cronsDao.getConnection();
			cronsDao.begingTransaction(connection, Connection.TRANSACTION_REPEATABLE_READ);
			cronsList = cronsDao.getRunningCronsInfo(connection, timestamp);
		}catch(Exception exception){
			exception.printStackTrace();
			cronsDao.rollbackTransaction(connection);
			cronsDao.closeConnection(connection);
			return ;
		}
		File file = null;
		int cls = cronsList.size();
		StringBuffer buffer = new StringBuffer();
		String cronDir=JspRunConfig.realPath+"include/crons/";
		for (int i = 0; i < cls; i++) {
			Map<String,String> crons = cronsList.get(i);
			String fileName = crons.get("filename");
			file = new File(cronDir+fileName);
			if(!file.isFile()){
				Log.writelog("errorlog",timestamp, timestamp+"\tCRON\t"+session.getAttribute("jsprun_userss")+"\t"+(crons.get("name")+" : Cron script("+fileName+") not found or syntax error"));
				buffer.append(crons.get("cronid")+",");
				continue;
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("/include/crons/" + fileName);
			request.setAttribute("crons", crons);
			request.setAttribute("connection", connection);
			try {
				dispatcher.include(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int bl = buffer.length();
		if(bl>0){
			try{
				cronsDao.execute(connection,"UPDATE jrun_crons SET available='0' WHERE cronid IN ("+buffer.substring(0,bl-1)+")");
			}catch(SQLException exception){
				exception.printStackTrace();
				cronsDao.rollbackTransaction(connection);
				cronsDao.closeConnection(connection);
				return ;
			}
		}
		cronsDao.commitTransaction(connection);
		cronsDao.closeConnection(connection);
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		List<Map<String,String>> nextrunInfo = dataBaseDao.executeQuery("SELECT nextrun FROM jrun_crons WHERE available=1 AND nextrun>'0' ORDER BY nextrun LIMIT 1");
		settings.put("cronnextrun", nextrunInfo!=null&&nextrunInfo.size()>0?nextrunInfo.get(0).get("nextrun"):"0");
	}
	public synchronized void cronRunning(HttpServletRequest request,HttpServletResponse response,Object cronsid){
		CronsDao cronsDao = ((CronsDao)BeanFactory.getBean("cronsSetDao"));
		HttpSession session = request.getSession();
		Connection connection = null;
		Map<String,String> crons = null;
		try{
			connection = cronsDao.getConnection();
			cronsDao.begingTransaction(connection, Connection.TRANSACTION_REPEATABLE_READ);
			crons = cronsDao.getRunningCronInfo(connection, cronsid);
		}catch(Exception exception){
			exception.printStackTrace();
			cronsDao.rollbackTransaction(connection);
			cronsDao.closeConnection(connection);
			return ;
		}
		String fileName = crons.get("filename");
		String cronDir=JspRunConfig.realPath+"include/crons/";
		File file = new File(cronDir+fileName);
		if(!file.isFile()){
			int timestamp = (Integer)(request.getAttribute("timestamp"));
			Log.writelog("errorlog",timestamp, timestamp+"\tCRON\t"+session.getAttribute("jsprun_userss")+"\t"+(crons.get("name")+" : Cron script("+fileName+") not found or syntax error"));
			try{
				cronsDao.execute(connection,"UPDATE jrun_crons SET available='0' WHERE cronid='"+crons.get("cronid")+"'");
			}catch(SQLException exception){
				exception.printStackTrace();
				cronsDao.rollbackTransaction(connection);
				cronsDao.closeConnection(connection);
				return ;
			}
		}else{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/include/crons/"+fileName);
			request.setAttribute("crons", crons);
			request.setAttribute("connection", connection);
			try {
				dispatcher.include(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		cronsDao.commitTransaction(connection);
		cronsDao.closeConnection(connection);
		DataBaseDao dataBaseDao = (DataBaseDao)BeanFactory.getBean("dataBaseDao");
		List<Map<String,String>> nextrunInfo = dataBaseDao.executeQuery("SELECT nextrun FROM jrun_crons WHERE available>'0' AND nextrun>'0' ORDER BY nextrun LIMIT 1");
		ForumInit.settings.put("cronnextrun", nextrunInfo!=null&&nextrunInfo.size()>0?nextrunInfo.get(0).get("nextrun"):"0");
	}
}
