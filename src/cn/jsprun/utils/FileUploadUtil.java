package cn.jsprun.utils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.config.ModuleConfig;
public class FileUploadUtil {
	private Map<String,FileItem> fileField = new TreeMap<String,FileItem>(); 
	private Map<String,String> formField = new TreeMap<String,String>(); 
	private Map<String,List<String>> formFields = new TreeMap<String,List<String>>(); 
	private int memoryBlock; 
	private File tempFolder; 
	private boolean multipart = false; 
	private HttpServletRequest request = null; 
	private long maxSize = 250 * 1024 * 1024; 
	public FileUploadUtil(File tempFolder, int memeoryBlock,ModuleConfig mc) {
		this.tempFolder = tempFolder;
		this.maxSize = convertSizeToBytes(mc.getControllerConfig().getMaxFileSize(), maxSize);
		this.memoryBlock = (int)convertSizeToBytes(mc.getControllerConfig().getMemFileSize(),memeoryBlock);
		if(memoryBlock==262144){
			memoryBlock = memeoryBlock;
		}
	}
	public FileUploadUtil() {
	}
	@SuppressWarnings("deprecation")
	public void parse(HttpServletRequest request, String charset) {
		this.request = request;
		multipart = FileUpload.isMultipartContent(request);
		if (multipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			if (memoryBlock != 0) {
				factory.setSizeThreshold(memoryBlock);
			} else {
				memoryBlock = factory.getSizeThreshold();
			}
			if (tempFolder != null) {
				factory.setRepository(tempFolder);
			} else {
				tempFolder = factory.getRepository();
			}
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(maxSize);
			List items;
			try {
				items = upload.parseRequest(request);
				Iterator iterator = items.iterator();
				while (iterator.hasNext()) {
					FileItem item = (FileItem) iterator.next();
					if (item.isFormField()) {
						processFormField(item, charset);
					} else {
						processUploadedFile(item);
					}
				}
			} catch (FileUploadException e) {
				if (e instanceof SizeLimitExceededException) {
					throw new IllegalStateException("System only support max " + maxSize + " File");
				}
			}
		}
	}
	private void processFormField(FileItem item, String charset) {
		try {
			String name = item.getFieldName();
			String value = item.getString(charset);
			String objv = formField.get(name);
			if (objv == null) {
				formField.put(name, value);
			} else {
				List<String> values = formFields.get(name);
				if (values!=null) {
					values.add(value);
				} else {
					values = new ArrayList<String>();
					values.add(objv);
					values.add(value);
				}
				formFields.put(name, values);
			}
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("the argument ¡°charset¡± missing!");
		}
	}
	private void processUploadedFile(FileItem item) {
		String name = item.getFieldName();
		fileField.put(name, item);
	}
	public FileItem getFileItem(String name) {
		if (multipart) {
			return (FileItem) fileField.get(name);
		} else {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public Map<String,FileItem> getFileItem() {
		if (multipart) {
			return fileField;
		} else {
			return null;
		}
	}
	public String getParameter(String name) {
		String value = null;
		if (multipart) {
			String obj = formField.get(name);
			value = obj!=null?obj:request.getParameter(name);
		} else if (request != null) {
			value = request.getParameter(name);
		}
		return value;
	}
	public String[] getParameterValues(String name) {
		String[] values = null;
		if (multipart) {
			List<String> obj = formFields.get(name);
			if (obj != null) {
				values = obj.toArray(new String[0]);
			}else{
				String objv = formField.get(name);
				values = objv!=null?new String[]{objv}:request.getParameterValues(name);
			}
		} else if (request != null) {
			values = request.getParameterValues(name);
		}
		return values;
	}
	public static boolean write2file(FileItem item, File file) {
		boolean flag = false;
		try {
			item.write(file);
			flag = true;
		} catch (Exception e) {
		}
		return flag;
	}
	public File getRepository() {
		return this.tempFolder;
	}
	public int getSizeThreshold() {
		return this.memoryBlock;
	}
	public boolean isMultipart() {
		return this.multipart;
	}
	private long convertSizeToBytes(String sizeString, long defaultSize) {
        int multiplier = 1;
        if (sizeString.endsWith("K")) {
            multiplier = 1024;
        } else if (sizeString.endsWith("M")) {
            multiplier = 1024 * 1024;
        } else if (sizeString.endsWith("G")) {
            multiplier = 1024 * 1024 * 1024;
        }
        if (multiplier != 1) {
            sizeString = sizeString.substring(0, sizeString.length() - 1);
        }
        long size = 0;
        try {
            size = Long.parseLong(sizeString);
        } catch (NumberFormatException nfe) {
            size = defaultSize;
            multiplier = 1;
        }
        return (size * multiplier);
    }
}