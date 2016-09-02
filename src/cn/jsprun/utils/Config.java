package cn.jsprun.utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
public final class Config {
	private String charset="GBK";
	private Properties properties=null;
	private String fileName=null;
	public Config(String fileName){
		this.properties = new Properties();
		this.fileName=fileName;
		try {
			InputStream fis = new FileInputStream(fileName);
			this.properties.load(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Properties getProperties(){
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public String getValue(String key) {
		return properties.getProperty(key);
	}
	public void setValue(String key,String value) {
		properties.setProperty(key, value);
	}
	public void saveProperties(String comments){
		try {
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader fsr = new InputStreamReader(fis, charset);
			BufferedReader br = new BufferedReader(fsr);
			List<String> datas=new ArrayList<String>();
			int index=-1;
			while (br.ready()) {
				index++;
				datas.add(br.readLine());
			}
			br.close();
			fsr.close();
			fis.close();
			Set<Object> keys = properties.keySet();
			for (Object key : keys) {
				boolean isnew = true;
				for (int i = 0; i <= index; i++) {
					if (datas.get(i).matches("^\\s*" + key + "\\s*\\=.*")) {
						datas.set(i, key + " = "
								+ properties.getProperty(key.toString()).replaceAll("\n", "\\\\n"));
						isnew = false;
						break;
					}
				}
				if (isnew) {
					index++;
					datas.add(index, key + " = "
							+ properties.getProperty(key.toString()).replaceAll("\n", "\\\\n"));
				}
			}
			FileOutputStream fos = new FileOutputStream(fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
			BufferedWriter bw = new BufferedWriter(osw);
			for (String data : datas) {
				bw.write(data);
				bw.newLine();
			}
			bw.flush();
			osw.flush();
			fos.flush();
			bw.close();
			osw.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
}