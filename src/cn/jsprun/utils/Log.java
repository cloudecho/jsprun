package cn.jsprun.utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
public final class Log {
	public static void writelog(String fileName,int timestamp,String log){
		writelog(fileName, timestamp, log, false);
	}
	public static void writelog(String fileName,int timestamp,String log,boolean isMoreLine){
		if(log.length()>0){
			Map<String,String> settings = ForumInit.settings;
			String yearmonth=Common.gmdate("yyyyMM", timestamp, settings.get("timeoffset"));
			String logdir=JspRunConfig.realPath+"forumdata/logs/";
			String logFileName=logdir+yearmonth+"_"+fileName+".jsp";
			File logFile=new File(logFileName);
			if(logFile.exists()){
				if(logFile.length()>2048000)
				{
					File[] files=new File(logdir).listFiles();
					int id=0;
					int maxid=0;
					for (File file : files) {
						if(file.isFile()){
							String name=file.getName();
							if(Common.matches(name,"^"+yearmonth+"_"+fileName+"_(\\d)*\\.jsp$")){
								id=Integer.valueOf(name.substring(name.lastIndexOf("_")+1,name.lastIndexOf(".")));
								maxid=id>maxid?id:maxid;
							}
						}
					}
					files=null;
					logFile.renameTo(new File(logdir+yearmonth+"_"+fileName+"_"+(maxid+1)+".jsp"));
				}
			}
			logFile=null;
			try {
				OutputStream out = new FileOutputStream(logFileName,true);
				OutputStreamWriter fwout = new OutputStreamWriter(out,JspRunConfig.CHARSET);
				BufferedWriter bw=new BufferedWriter(fwout);
				if(isMoreLine){
					bw.write(log);
				}else{
					bw.write("<?JSP exit;?>\t"+Common.nl2br(log)+"\n");
				}
				bw.close();
				fwout.close();
				out.close();
				bw=null;
				fwout=null;
				out=null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static List<String> readlog(String fileName,int timestamp,String keyword){
			List<String> list = new ArrayList<String>();
			Map<String,String> settings = ForumInit.settings;
			String yearmonth=Common.gmdate("yyyyMM", timestamp, settings.get("timeoffset"));
			String logdir=JspRunConfig.realPath+"forumdata/logs/";
			String logFileName=logdir+yearmonth+"_"+fileName+".jsp";
			File logFile=new File(logFileName);
			File logFile2 = null;
			if(logFile.exists()){
				if(logFile.length()<500000)
				{
					File[] files=new File(logdir).listFiles();
					int id=0;
					int maxid=0;
					for (File file : files) {
						if(file.isFile()){
							String name=file.getName();
							if(Common.matches(name,"^"+yearmonth+"_"+fileName+"_(\\d)*\\.jsp$")){
								id=Integer.valueOf(name.substring(name.lastIndexOf("_")+1,name.lastIndexOf(".")));
								maxid=id>maxid?id:maxid;
							}
						}
					}
					files=null;
					if(maxid>0){
						logFile2 = new File(logdir+yearmonth+"_"+fileName+"_"+maxid+".jsp");
					}else{
						Calendar calendar = Common.getCalendar(settings.get("timeoffset"));
						int month = calendar.get(Calendar.MONTH);
						month = month>0?month-1:11;
						calendar.set(Calendar.MONTH, month);
						int temptime = (int)(calendar.getTimeInMillis()/1000);
						String lastyearmonth = Common.gmdate("yyyyMM", temptime, settings.get("timeoffset"));
						logFile2 = new File(logdir+lastyearmonth+"_"+fileName+".jsp");
					}
				}
			}
			if (logFile2!=null&&logFile2.exists()) {
				try {
					FileInputStream fr = new FileInputStream(logFile2);
					list.addAll(readLog(fr,keyword));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			if(logFile.exists()){
				try {
					FileInputStream fr = new FileInputStream(logFile);
					list.addAll(readLog(fr,keyword));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			return list;
	}
	private static List<String> readLog(FileInputStream filereader,String keyword) {
		List<String> list = new ArrayList<String>();
		BufferedReader br = null;
		InputStreamReader ir=null;
		try {
			ir = new InputStreamReader(filereader,JspRunConfig.CHARSET);
			br = new BufferedReader(ir);
			String newline = br.readLine();
			while (newline != null) {
				if(keyword!=null){
					String []str = newline.split("\t");
					for(int i=0;i<str.length;i++){
						if(str[i].indexOf(keyword)!=-1){
							list.add(newline);
							break;
						}
					}
				}else{
					list.add(newline);
				}
				newline = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {				
					br.close();	
					ir.close();
					if (filereader != null) {
						filereader.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}