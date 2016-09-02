package cn.jsprun.utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import org.apache.struts.util.MessageResources;
public class IPSeeker {
	private class IPLocation {
		public String country;
		public String area;
		public IPLocation() {
		    country = area = "";
		}
		public IPLocation getCopy() {
		    IPLocation ret = new IPLocation();
		    ret.country = country;
		    ret.area = area;
		    return ret;
		}
	}
	private static final int IP_RECORD_LENGTH = 7;
	private static final byte AREA_FOLLOWED = 0x01;
	private static final byte NO_AREA = 0x2;
	@SuppressWarnings("unchecked")
	private Hashtable ipCache;
	private RandomAccessFile ipFile;
	private MappedByteBuffer mbb;
	private static IPSeeker instance = null;
	private long ipBegin, ipEnd;
	private IPLocation loc;
	private byte[] buf;
	private byte[] b4;
	private byte[] b3;
	@SuppressWarnings("unchecked")
	private IPSeeker()  {
		ipCache = new Hashtable();
		loc = new IPLocation();
		buf = new byte[100];
		b4 = new byte[4];
		b3 = new byte[3];
		try {
			ipFile = new RandomAccessFile(JspRunConfig.realPath+"ipdata/ipdata.Dat", "r");
		} catch (FileNotFoundException e) {
			System.out.println("IP地址信息文件没有找到，IP显示功能将无法使用");
			ipFile = null;
		}
		if(ipFile != null) {
			try {
				ipBegin = readLong4(0);
				ipEnd = readLong4(4);
				if(ipBegin == -1 || ipEnd == -1) {
					ipFile.close();
					ipFile = null;
				}
			} catch (IOException e) {
				ipFile = null;
			}
		}
	}
	public synchronized static IPSeeker getInstance() {
		if(instance == null){  
			instance= new IPSeeker();  
        }  
		return instance;
	}
	@SuppressWarnings("unchecked")
	public List getIPEntriesDebug(String s,MessageResources mr,Locale locale) {
	    List ret = new ArrayList();
	    long endOffset = ipEnd + 4;
	    for(long offset = ipBegin + 4; offset <= endOffset; offset += IP_RECORD_LENGTH) {
	        long temp = readLong3(offset);
	        if(temp != -1) {
	            IPLocation loc = getIPLocation(temp,mr,locale);
	            if(loc.country.indexOf(s) != -1 || loc.area.indexOf(s) != -1) {
	                IPEntry entry = new IPEntry();
	                entry.country = loc.country;
	                entry.area = loc.area;
	    	        readIP(offset - 4, b4);
	                entry.beginIp = getIpStringFromBytes(b4);
	                readIP(temp, b4);
	                entry.endIp = getIpStringFromBytes(b4);
	                ret.add(entry);
	            }
	        }
	    }
	    return ret;
	}
	@SuppressWarnings("unchecked")
	public List getIPEntries(String s,MessageResources mr,Locale locale) {
	    List ret = new ArrayList();
	    try {
	        if(mbb == null) {
			    FileChannel fc = ipFile.getChannel();
	            mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, ipFile.length());
	            mbb.order(ByteOrder.LITTLE_ENDIAN);
	        }
		    int endOffset = (int)ipEnd;
            for(int offset = (int)ipBegin + 4; offset <= endOffset; offset += IP_RECORD_LENGTH) {
                int temp = readInt3(offset);
                if(temp != -1) {
    	            IPLocation loc = getIPLocation(temp,mr,locale);
    	            if(loc.country.indexOf(s) != -1 || loc.area.indexOf(s) != -1) {
    	                IPEntry entry = new IPEntry();
    	                entry.country = loc.country;
    	                entry.area = loc.area;
    	    	        readIP(offset - 4, b4);
    	                entry.beginIp = getIpStringFromBytes(b4);
    	                readIP(temp, b4);
    	                entry.endIp = getIpStringFromBytes(b4);
    	                ret.add(entry);
    	            }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ret;
	}
	private int readInt3(int offset) {
	    mbb.position(offset);
	    return mbb.getInt() & 0x00FFFFFF;
	}
	private int readInt3() {
	    return mbb.getInt() & 0x00FFFFFF;
	}
	@SuppressWarnings("unchecked")
	public String getCountry(byte[] ip,MessageResources mr,Locale locale) {
		if(ipFile == null) return mr.getMessage(locale,"error_ip_file");
		String ipStr = getIpStringFromBytes(ip);
		if(ipCache.containsKey(ipStr)) {
			IPLocation loc = (IPLocation)ipCache.get(ipStr);
			return loc.country;
		} else {
			IPLocation loc = getIPLocation(ip,mr,locale);
			ipCache.put(ipStr, loc.getCopy());
			return loc.country;
		}
	}
	public String getCountry(String ip,MessageResources mr,Locale locale) {
	    return getCountry(getIpByteArrayFromString(ip),mr,locale);
	}
	@SuppressWarnings("unchecked")
	public String getArea(byte[] ip,MessageResources mr,Locale locale) {
		if(ipFile == null) return mr.getMessage(locale,"error_ip_file");
		String ipStr = getIpStringFromBytes(ip);
		if(ipCache.containsKey(ipStr)) {
			IPLocation loc = (IPLocation)ipCache.get(ipStr);
			return loc.area;
		} else {
			IPLocation loc = getIPLocation(ip,mr,locale);
			ipCache.put(ipStr, loc.getCopy());
			return loc.area;
		}
	}
	public String getArea(String ip,MessageResources mr,Locale locale) {
	    return getArea(getIpByteArrayFromString(ip),mr,locale);
	}
	private IPLocation getIPLocation(byte[] ip,MessageResources mr,Locale locale) {
		IPLocation info = null;
		long offset = locateIP(ip);
		if(offset != -1)
			info = getIPLocation(offset,mr,locale);
		if(info == null) {
			info = new IPLocation();
			info.country = mr.getMessage(locale, "unbeknown_country");
			info.area = mr.getMessage(locale, "unbeknown_area");
		}
		return info;
	}
	private long readLong4(long offset) {
		long ret = 0;
		try {
			ipFile.seek(offset);
			ret |= (ipFile.readByte() & 0xFF);
			ret |= ((ipFile.readByte() << 8) & 0xFF00);
			ret |= ((ipFile.readByte() << 16) & 0xFF0000);
			ret |= ((ipFile.readByte() << 24) & 0xFF000000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}
	private long readLong3(long offset) {
		long ret = 0;
		try {
			ipFile.seek(offset);
			ipFile.readFully(b3);
			ret |= (b3[0] & 0xFF);
			ret |= ((b3[1] << 8) & 0xFF00);
			ret |= ((b3[2] << 16) & 0xFF0000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}
	private long readLong3() {
		long ret = 0;
		try {
			ipFile.readFully(b3);
			ret |= (b3[0] & 0xFF);
			ret |= ((b3[1] << 8) & 0xFF00);
			ret |= ((b3[2] << 16) & 0xFF0000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}
	private void readIP(long offset, byte[] ip) {
		try {
			ipFile.seek(offset);
			ipFile.readFully(ip);
			byte temp = ip[0];
			ip[0] = ip[3];
			ip[3] = temp;
			temp = ip[1];
			ip[1] = ip[2];
			ip[2] = temp;
		} catch (IOException e) {
		    System.out.println(e.getMessage());
		}
	}
	private void readIP(int offset, byte[] ip) {
	    mbb.position(offset);
	    mbb.get(ip);
		byte temp = ip[0];
		ip[0] = ip[3];
		ip[3] = temp;
		temp = ip[1];
		ip[1] = ip[2];
		ip[2] = temp;
	}
	private int compareIP(byte[] ip, byte[] beginIp) {
		for(int i = 0; i < 4; i++) {
			int r = compareByte(ip[i], beginIp[i]);
			if(r != 0)
				return r;
		}
		return 0;
	}
	private int compareByte(byte b1, byte b2) {
		if((b1 & 0xFF) > (b2 & 0xFF)) 
			return 1;
		else if((b1 ^ b2) == 0)
			return 0;
		else
			return -1;
	}
	private long locateIP(byte[] ip) {
		long m = 0;
		int r;
		readIP(ipBegin, b4);
		r = compareIP(ip, b4);
		if(r == 0) return ipBegin;
		else if(r < 0) return -1;
		for(long i = ipBegin, j = ipEnd; i < j; ) {
			m = getMiddleOffset(i, j);
			readIP(m, b4);
			r = compareIP(ip, b4);
			if(r > 0)
				i = m;
			else if(r < 0) {
				if(m == j) {
					j -= IP_RECORD_LENGTH;
					m = j;
				} else
					j = m;
			} else
				return readLong3(m + 4);
		}
		m = readLong3(m + 4);
		readIP(m, b4);
		r = compareIP(ip, b4);
		if(r <= 0) return m;
		else return -1;
	}
	private long getMiddleOffset(long begin, long end) {
		long records = (end - begin) / IP_RECORD_LENGTH;
		records >>= 1;
		if(records == 0) records = 1;
		return begin + records * IP_RECORD_LENGTH;
	}
	private IPLocation getIPLocation(long offset,MessageResources mr,Locale locale) {
		try {
			ipFile.seek(offset + 4);
			byte b = ipFile.readByte();
			if(b == AREA_FOLLOWED) {
				long countryOffset = readLong3();
				ipFile.seek(countryOffset);
				b = ipFile.readByte();
				if(b == NO_AREA) {
					loc.country = readString(readLong3());
					ipFile.seek(countryOffset + 4);
				} else
					loc.country = readString(countryOffset);
				loc.area = readArea(ipFile.getFilePointer(),mr,locale);
			} else if(b == NO_AREA) {
				loc.country = readString(readLong3());
				loc.area = readArea(offset + 8,mr,locale);
			} else {
				loc.country = readString(ipFile.getFilePointer() - 1);
				loc.area = readArea(ipFile.getFilePointer(),mr,locale);
			}
			return loc;
		} catch (IOException e) {
			return null;
		}
	}
	private IPLocation getIPLocation(int offset,MessageResources mr,Locale locale) {
	    mbb.position(offset + 4);
		byte b = mbb.get();
		if(b == AREA_FOLLOWED) {
			int countryOffset = readInt3();
			mbb.position(countryOffset);
			b = mbb.get();
			if(b == NO_AREA) {
				loc.country = readString(readInt3());
				mbb.position(countryOffset + 4);
			} else
				loc.country = readString(countryOffset);
			loc.area = readArea(mbb.position(),mr,locale);
		} else if(b == NO_AREA) {
			loc.country = readString(readInt3());
			loc.area = readArea(offset + 8,mr,locale);
		} else {
			loc.country = readString(mbb.position() - 1);
			loc.area = readArea(mbb.position(),mr,locale);
		}
		return loc;
	}
	private String readArea(long offset,MessageResources mr,Locale locale) throws IOException {
		ipFile.seek(offset);
		byte b = ipFile.readByte();
		if(b == 0x01 || b == 0x02) {
			long areaOffset = readLong3(offset + 1);
			if(areaOffset == 0)
				return mr.getMessage(locale, "unbeknown_area");
			else
				return readString(areaOffset);
		} else
			return readString(offset);
	}
	private String readArea(int offset,MessageResources mr,Locale locale) {
		mbb.position(offset);
		byte b = mbb.get();
		if(b == 0x01 || b == 0x02) {
			int areaOffset = readInt3();
			if(areaOffset == 0)
				return mr.getMessage(locale, "unbeknown_area");
			else
				return readString(areaOffset);
		} else
			return readString(offset);
	}
	 private String readString(long offset) {  
         try {  
            ipFile.seek(offset);  
             int i = 0;  
             byte[] buf = new byte[256];  
             while ((buf[i] = ipFile.readByte()) != 0) {  
                 ++ i;  
                 if (i >= buf.length) {  
                     byte[] tmp = new byte[i + 100];  
                     System.arraycopy(buf, 0, tmp, 0, i);  
                     buf = tmp;  
                }  
             }  
             if (i != 0)  
                 return getString(buf, 0, i, "GBK");  
         } catch (IOException e) {  
             System.out.println(e.getMessage());  
         }  
         return "";  
    }  
	private String readString(int offset) {
	    try {
			mbb.position(offset);
			int i;
			for(i = 0, buf[i] = mbb.get(); buf[i] != 0; buf[++i] = mbb.get());
			if(i != 0)
			    return getString(buf, 0, i, "GBK");
	    } catch (IllegalArgumentException e) {
	        System.out.println(e.getMessage());
	    }
	    return "";
	}
	public String getAddress(String ip,MessageResources mr,Locale locale){
		if(ip.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$")){
			String country = getCountry(ip,mr,locale).equals(" CZ88.NET")?"":getCountry(ip,mr,locale);
			String area = getArea(ip,mr,locale).equals(" CZ88.NET")?"":getArea(ip,mr,locale);
	        String address = country+" "+area;
			return address.trim();
		}
		return mr.getMessage(locale, "nullity_address");
	}
	private byte[] getIpByteArrayFromString(String ip) {
        byte[] ret = new byte[4];
        java.util.StringTokenizer st = new java.util.StringTokenizer(ip, ".");
        try {
            ret[0] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
        } catch (Exception e) {
        }
        return ret;
    }
    private String getString(byte[] b, int offset, int len, String encoding) {
        try {
            return new String(b, offset, len, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b, offset, len);
        }
    }
    private String getIpStringFromBytes(byte[] ip) {
    	StringBuffer sb = new StringBuffer();
    	sb.append(ip[0] & 0xFF);
    	sb.append('.');   	
    	sb.append(ip[1] & 0xFF);
    	sb.append('.');   	
    	sb.append(ip[2] & 0xFF);
    	sb.append('.');   	
    	sb.append(ip[3] & 0xFF);
    	return sb.toString();
    }
}