package cn.jsprun.utils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import sun.net.ftp.FtpLoginException;
public class FtpUtils {
	private String ftphost;
	private String ftpuser;
	private String ftppass;
	private String ftppath;
	private int ftpport;
	private int silent;
	private String pasv;
	private String ftpssl;
	public void setFtpValues(String ftphost, String ftpuser, String ftppass, String ftppath, int ftpport, String ftpssl, int silent,String pasv){
		 this.ftphost = ftphost;this.ftppass = ftppass;this.ftppath = ftppath;this.pasv = pasv;
	     this.ftpport = ftpport;this.ftpuser = ftpuser;this.silent = silent; this.ftpssl = ftpssl;
	}
	public boolean isEmpty(){
		if(ftphost==null||ftphost.equals("")){
			return true;
		}
		return false;
	}
	public FTPClient getFTPClient(){
        FTPClient fc = null;
        if("1".equals(ftpssl)){
        	try {
        		fc = new FTPSClient();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
        }else{
        	 fc = new FTPClient();
        }
        return fc;
    }
	public String connectToServer(FTPClient fc){
		if ((ftphost==null)||(ftphost.equals(""))) return "ftp_service_nameerror";
        try{
        	fc.connect(this.ftphost, this.ftpport);
        	if(!fc.login(ftpuser, ftppass)){
        		return "ftp_service_connect";
        	}
        	if(pasv.equals("1")){
        		fc.pasv();
        	}
        	if(silent!=0){
        		fc.setDataTimeout(silent);
        	}
        	if(!fc.changeWorkingDirectory(ftppath)){
        		return "ftp_directory";
        	}
        }catch(FtpLoginException e){
            return "ftp_connect_access";
        }catch(IOException e){
        	e.printStackTrace();
            return "ftp_connect_fail";
        }catch(SecurityException e){
            return "ftp_noaccess";
        }
        return "";
	}
    public void closeFtpConnect(FTPClient fc){
        if (fc!=null){
            try{
                fc.disconnect();
            }catch(Exception e){
            }finally{
                fc = null;
            }
        }
    }
    public  boolean dftp_mkdir(String newdir,FTPClient fc){
    	boolean makebool;
		try{
			makebool =  fc.makeDirectory(newdir);
			return makebool;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
    }
    public boolean dftp_rmdir(String newdir,FTPClient fc){
		try{
			fc.changeToParentDirectory();
			return fc.removeDirectory(newdir);
		}catch(Exception e){
			return false;
		}
    }
    public  boolean dftp_delete(String newdir,FTPClient fc){
		try{
			return fc.deleteFile(newdir);
		}catch(Exception e){
			return false;
		}
    }
    public boolean dftp_site(String newdir,FTPClient fc){
    	String  cmd_mkdir= " chmod 0777 "+newdir;  
		try{
			return fc.sendSiteCommand(cmd_mkdir);
		}catch(Exception e){
			return false;
		}
    }
    public  boolean dftp_chdir(String dir,FTPClient fc){
    	boolean workboolean;
		try{
			workboolean =   fc.changeWorkingDirectory(dir);
			return workboolean;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
    }
    public  boolean put(String sourcename,String targetname,FTPClient fc){
    		try {
    			fc.setBufferSize(3072);
    			fc.setFileType(FTPClient.BINARY_FILE_TYPE);
        		InputStream is = new FileInputStream(sourcename);
        		boolean strore = fc.storeFile(targetname, is);
				is.close();
				return strore;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
    }
    public  boolean get(String local_file,String remote_file,FTPClient fc){
    		try {
    			fc.setBufferSize(3076);
    			fc.setFileType(FTPClient.BINARY_FILE_TYPE);
    			FileOutputStream os = new FileOutputStream(local_file);
				fc.retrieveFile(remote_file, os);
				os.close();
				return true;
			} catch (IOException e) {
				return false;
			}
    }
    public boolean readfile(String url,OutputStream os){
    	InputStream in=null;
    	URL servletURL =null;
		try {
			servletURL = new URL(url);
			servletURL.openConnection();
			in = servletURL.openStream();
			if(os!=null){
				byte[] bytes=new byte[1024];
	    		int c;
	    		while ((c=in.read(bytes))!=-1){
	    			os.write(bytes,0,c);
	    		}
			}
    		return true;
		} catch (Exception e) {
			return false;
		}finally{
			try{
				if(in!=null){
					in.close();
				}
				if(os!=null){
					os.close();
				}
			}catch(Exception e){}
			servletURL=null;
		}
    }
}
