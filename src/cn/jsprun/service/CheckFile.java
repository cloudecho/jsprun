package cn.jsprun.service;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import cn.jsprun.utils.Md5Token;
public class CheckFile {
	public List<String> checkFilePopedom(String path, List<String> result,String patt) {
		File file = new File(path);
		if (file.isDirectory()) {
			String[] dirs = file.list();
			String ps = file.getAbsolutePath();
			for (String dir : dirs) {
				result = checkFilePopedom(ps + "/" + dir, result,patt);
			}
		} else if(file.exists()){
			if (!file.canWrite()) {
				String paths = file.getAbsolutePath();
				paths = paths.substring(patt.length());
				paths = paths.replace('\\', '/');
				result.add("./" + paths);
			}
		}
		return result;
	}
	public List checkFileIntegrity() {
		return null;
	}
	public Map<String,String> checkFile(String path,Map<String,String> result) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				if(!path.matches(".*(attachments|customavatars|forumdata|images|install|ipdata|META-INF|mspace|plugins|WEB-INF)")){
					String[] dirs = file.list();
					String ps = file.getAbsolutePath();
					for (String dir : dirs) {
						result = checkFile(ps + "/" + dir, result);
					}
				}
			} else {
				if(path.matches(".*(jsp|js|htm)$")){
					String paths = file.getAbsolutePath();
					paths = paths.replace('\\', '/');
					paths = paths.replaceAll("E:/may/Tomcat/Tomcat_jsprun/apache-tomcat-5.5.26/webapps/jsprunutf8/","");
					long filebyte = file.length();
					String bb = Md5Token.getInstance().getLongToken(filebyte + "");
					if(paths.lastIndexOf("/")>0){
						result.put(" *" + paths, bb);
					}else{
						result.put(" *./" + paths, bb);
					}
				}
			}
			return result;
		}
		return null;
	}
	public void writejsprunFile(String filepath,Map<String,String> filelist) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(filepath);
			bw = new BufferedWriter(fw);
			Set<String> keys=filelist.keySet();
			for (String key : keys) {
				bw.write(filelist.get(key)+key);
				bw.newLine();
			}
			bw.flush();
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
			}
		}
	}
	public List<String> displist(String filename) {
		List<String> result = new ArrayList<String>();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			String newline = br.readLine();
			while (newline != null) {
				result.add(newline);
				newline = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException er) {
			}
		}
		return result;
	}
	public List<String> displistBydir(List<String> filelist, String path) {
		List<String> resultlist = new ArrayList<String>();
		for (String files:filelist) {
			int begin = files.indexOf("*");
			int end = files.lastIndexOf("/");
			String targetfile = "";
			if (end > 0) {
				targetfile = files.substring(begin + 1, end + 1);
			}
			if (targetfile.equals(path)) {
				resultlist.add(files);
			}
		}
		return resultlist;
	}
}
